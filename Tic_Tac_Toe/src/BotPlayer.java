import javax.swing.*;
import java.awt.*;

public class BotPlayer {
    private javax.swing.Timer botTimer;
    private JButton[] buttons;
    private Runnable onMoveComplete;
    private BotStrategy strategy;

    public BotPlayer(BotStrategy strategy, JButton[] buttons, Runnable onMoveComplete) {
        this.strategy = strategy;
        this.buttons = buttons;
        this.onMoveComplete = onMoveComplete;
    }

    public void makeMove() {
        int move = strategy.makeMove(buttons);
        if (move != -1) {
            botTimer = new javax.swing.Timer(1000, e -> {
                executeMove(move);
                botTimer.stop();
            });
            botTimer.setRepeats(false);
            botTimer.start();
        }
    }

    private void executeMove(int move) {
        buttons[move].setForeground(new Color(0, 0, 255));
        buttons[move].setText("O");
        onMoveComplete.run();
    }

    public void stop() {
        if (botTimer != null && botTimer.isRunning()) {
            botTimer.stop();
        }
    }
}