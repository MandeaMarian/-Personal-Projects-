import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameFrame implements ActionListener {
    private Random random = new Random();
    private JFrame frame = new JFrame();
    private JPanel titlePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel textfield = new JLabel();
    private JButton[] buttons = new JButton[9];
    private JButton resetButton;
    private JButton exitButton;
    private boolean player1Turn;
    private boolean againstBot;
    private javax.swing.Timer botTimer;
    private BotPlayer botPlayer;
    private boolean easyMode;
    private int botDifficulty;

    public GameFrame(boolean againstBot){
        this(againstBot, 1);
    }

    public GameFrame(boolean againstBot, int botDifficulty) {
        this.againstBot = againstBot;
        this.botDifficulty = botDifficulty;

        initializeFrame();
        initializeTitlePanel();
        initializeGameButtons();
        initializeControlButtons();

        if (againstBot) {
            BotStrategy strategy;
            switch (botDifficulty) {
                case 1:
                    strategy = new RandomBot();
                    break;
                case 2:
                    strategy = new SmartBot();
                    break;
                case 3:
                    strategy = new ImpossibleBot();
                    break;
                default:
                    strategy = new RandomBot();
            }
            this.botPlayer = new BotPlayer(strategy, buttons, () -> {
                player1Turn = true;
                textfield.setText("X turn");
                check();
            });
        }

        firstTurn();
        frame.setVisible(true);

        if (this.againstBot && !player1Turn) {
            makeBotMove();
        }
    }

    private void initializeFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
    }

    private void initializeTitlePanel() {
        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);
        titlePanel.add(textfield);
        frame.add(titlePanel, BorderLayout.NORTH);
    }

    private void initializeGameButtons() {
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setBackground(new Color(200, 200, 200));
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    private void initializeControlButtons() {
        JPanel controlPanel = new JPanel(new GridLayout(1, 2));
        controlPanel.setBackground(new Color(50, 50, 50));

        resetButton = new JButton("Play Again");
        styleControlButton(resetButton, new Color(0, 150, 0));
        resetButton.addActionListener(e -> resetGame());
        resetButton.setVisible(false);

        exitButton = new JButton("Exit to Menu");
        styleControlButton(exitButton, new Color(150, 0, 0));
        exitButton.addActionListener(e -> showExitConfirmation());

        controlPanel.add(resetButton);
        controlPanel.add(exitButton);

        frame.add(controlPanel, BorderLayout.SOUTH);
    }

    private void showExitConfirmation() {
        boolean gameInProgress = false;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].isEnabled() && buttons[i].getText().equals("")) {
                gameInProgress = true;
                break;
            }
        }

        if (!gameInProgress) {
            frame.dispose();
            MainMenu.showMainMenu();
            return;
        }

        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel message = new JLabel("Are you sure?", SwingConstants.CENTER);
        message.setFont(new Font("MV Boli", Font.BOLD, 20));
        confirmPanel.add(message, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton yesButton = new JButton("Yes");
        styleConfirmButton(yesButton, new Color(0, 150, 0));
        yesButton.addActionListener(e -> {
            frame.dispose();
            MainMenu.showMainMenu();
        });

        JButton noButton = new JButton("No");
        styleConfirmButton(noButton, new Color(150, 0, 0));
        noButton.addActionListener(e -> {
            ((JDialog)SwingUtilities.getWindowAncestor((Component)e.getSource())).dispose();
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        confirmPanel.add(buttonPanel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(frame, "Confirm Exit", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(confirmPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void styleConfirmButton(JButton button, Color color) {
        button.setFont(new Font("MV Boli", Font.BOLD, 16));
        button.setFocusable(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleControlButton(JButton button, Color color) {
        button.setFont(new Font("MV Boli", Font.BOLD, 25));
        button.setFocusable(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (againstBot && !player1Turn) return;

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1Turn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        player1Turn = false;
                        textfield.setText("O turn");
                        check();

                        if (againstBot && !checkWin("X") && !checkDraw()) {
                            makeBotMove();
                        }
                    }
                } else {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        player1Turn = true;
                        textfield.setText("X turn");
                        check();
                    }
                }
            }
        }
    }

    private void makeBotMove() {
        botPlayer.makeMove();
    }

    public void firstTurn() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1Turn = true;
            textfield.setText("X turn");
        } else {
            player1Turn = false;
            textfield.setText("O turn");
        }
    }

    public void check() {
        if (checkWin("X")) {
            return;
        }

        if (checkWin("O")) {
            return;
        }

        checkDraw();
    }

    private boolean checkWin(String player) {
        if ((buttons[0].getText().equals(player)) && (buttons[1].getText().equals(player)) && (buttons[2].getText().equals(player))) {
            highlightWin(0, 1, 2, player);
            return true;
        }
        if ((buttons[3].getText().equals(player)) && (buttons[4].getText().equals(player)) && (buttons[5].getText().equals(player))) {
            highlightWin(3, 4, 5, player);
            return true;
        }
        if ((buttons[6].getText().equals(player)) && (buttons[7].getText().equals(player)) && (buttons[8].getText().equals(player))) {
            highlightWin(6, 7, 8, player);
            return true;
        }

        if ((buttons[0].getText().equals(player)) && (buttons[3].getText().equals(player)) && (buttons[6].getText().equals(player))) {
            highlightWin(0, 3, 6, player);
            return true;
        }
        if ((buttons[1].getText().equals(player)) && (buttons[4].getText().equals(player)) && (buttons[7].getText().equals(player))) {
            highlightWin(1, 4, 7, player);
            return true;
        }
        if ((buttons[2].getText().equals(player)) && (buttons[5].getText().equals(player)) && (buttons[8].getText().equals(player))) {
            highlightWin(2, 5, 8, player);
            return true;
        }

        if ((buttons[0].getText().equals(player)) && (buttons[4].getText().equals(player)) && (buttons[8].getText().equals(player))) {
            highlightWin(0, 4, 8, player);
            return true;
        }
        if ((buttons[2].getText().equals(player)) && (buttons[4].getText().equals(player)) && (buttons[6].getText().equals(player))) {
            highlightWin(2, 4, 6, player);
            return true;
        }

        return false;
    }

    private void highlightWin(int a, int b, int c, String player) {
        if (botTimer != null && botTimer.isRunning()) {
            botTimer.stop();
        }

        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }

        textfield.setText(player + " wins");
        resetButton.setVisible(true);
        botPlayer.stop();
    }

    private boolean checkDraw() {
        boolean isDraw = true;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().equals("")) {
                isDraw = false;
                break;
            }
        }

        if (isDraw) {
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(false);
                buttons[i].setBackground(Color.YELLOW);
            }
            textfield.setText("Draw!");
            resetButton.setVisible(true);
            return true;
        }
        return false;
    }

    public void resetGame() {
        if (botTimer != null && botTimer.isRunning()) {
            botTimer.stop();
        }

        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackground(new Color(200, 200, 200));
            buttons[i].setForeground(Color.BLACK);
        }

        resetButton.setVisible(false);
        firstTurn();

        if (againstBot) {
            BotStrategy strategy;
            switch (botDifficulty) {
                case 1:
                    strategy = new RandomBot();
                    break;
                case 2:
                    strategy = new SmartBot();
                    break;
                case 3:
                    strategy = new ImpossibleBot();
                    break;
                default:
                    strategy = new RandomBot();
            }
            this.botPlayer = new BotPlayer(strategy, buttons, () -> {
                player1Turn = true;
                textfield.setText("X turn");
                check();
            });

            if (!player1Turn) {
                makeBotMove();
            }
        }
    }
}