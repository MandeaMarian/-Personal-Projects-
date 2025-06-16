import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu {
    private static JFrame menuFrame;

    public MainMenu() {
        menuFrame = new JFrame("Tic-Tac-Toe");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(800, 700);
        menuFrame.setLayout(new BorderLayout());
        menuFrame.getContentPane().setBackground(new Color(50, 50, 50));

        JPanel titlePanel = createTitlePanel();
        JPanel buttonPanel = createButtonPanel(menuFrame);

        menuFrame.add(titlePanel, BorderLayout.CENTER);
        menuFrame.add(buttonPanel, BorderLayout.SOUTH);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(50, 50, 50));

        JLabel titleLabel = new JLabel("Tic-Tac-Toe");
        titleLabel.setFont(new Font("Ink Free", Font.BOLD, 60));
        titleLabel.setForeground(new Color(25, 255, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel authorLabel = new JLabel("Created by Mandea Marian");
        authorLabel.setFont(new Font("Ink Free", Font.BOLD, 20));
        authorLabel.setForeground(new Color(200, 200, 200));
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 30, 0));

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(authorLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createButtonPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 50, 50));
        panel.setLayout(new GridLayout(1, 2, 30, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 100));

        JButton playButton = createButton("Play", new Color(0, 150, 0));
        playButton.addActionListener(e -> {
            frame.dispose();
            showGameModeSelection();
        });

        JButton rulesButton = createButton("Rules", new Color(0, 0, 150));
        rulesButton.addActionListener(e -> showRules());

        panel.add(playButton);
        panel.add(rulesButton);

        return panel;
    }

    private void showGameModeSelection() {
        JFrame selectionFrame = new JFrame("Select Game Mode");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(600, 400);
        selectionFrame.getContentPane().setBackground(new Color(50, 50, 50));
        selectionFrame.setLayout(new BorderLayout());
        selectionFrame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Select Game Mode", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Ink Free", Font.BOLD, 40));
        titleLabel.setForeground(new Color(25, 255, 0));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JPanel modePanel = new JPanel(new GridLayout(3, 1, 0, 20));
        modePanel.setBackground(new Color(50, 50, 50));
        modePanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));

        JButton friendButton = createButton("Play with Friend", new Color(0, 100, 200));
        friendButton.addActionListener(e -> {
            selectionFrame.dispose();
            new GameFrame(false);
        });

        JButton easyButton = createButton("Easy Bot", new Color(0, 150, 0));
        easyButton.addActionListener(e -> {
            selectionFrame.dispose();
            new GameFrame(true, true);
        });

        JButton hardButton = createButton("Hard Bot", new Color(150, 0, 0));
        hardButton.addActionListener(e -> {
            selectionFrame.dispose();
            new GameFrame(true, false);
        });

        modePanel.add(friendButton);
        modePanel.add(easyButton);
        modePanel.add(hardButton);

        selectionFrame.add(titleLabel, BorderLayout.NORTH);
        selectionFrame.add(modePanel, BorderLayout.CENTER);
        selectionFrame.setVisible(true);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("MV Boli", Font.BOLD, 30));
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }

    private void showRules() {
        String rules = "<html><div style='text-align: center;'>" +
                "<h1>Tic-Tac-Toe Rules</h1>" +
                "<p>1. The game is played on a 3x3 grid</p>" +
                "<p>2. Players alternate placing X and O marks</p>" +
                "<p>3. First to get 3 in a row wins</p>" +
                "<p>4. If all squares are filled without a winner, it's a draw</p></div></html>";

        JOptionPane.showMessageDialog(null, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showMainMenu() {
        if (menuFrame != null) {
            menuFrame.dispose();
        }
        new MainMenu();
    }
}