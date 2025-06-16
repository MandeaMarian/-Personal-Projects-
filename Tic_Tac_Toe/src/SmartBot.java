import javax.swing.*;
import java.util.ArrayList;

public class SmartBot implements BotStrategy {
    @Override
    public int makeMove(JButton[] buttons) {
        int winningMove = findWinningMove(buttons, "O");
        if (winningMove != -1) return winningMove;

        int blockingMove = findWinningMove(buttons, "X");
        if (blockingMove != -1) return blockingMove;

        if (buttons[4].getText().isEmpty()) return 4;

        ArrayList<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText().isEmpty()) {
                emptyCells.add(i);
            }
        }
        return emptyCells.isEmpty() ? -1 : emptyCells.get((int)(Math.random() * emptyCells.size()));
    }

    private int findWinningMove(JButton[] buttons, String player) {
        int[][] winningCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : winningCombinations) {
            int emptyCount = 0;
            int emptyPos = -1;
            int playerCount = 0;

            for (int pos : combo) {
                if (buttons[pos].getText().equals(player)) {
                    playerCount++;
                } else if (buttons[pos].getText().isEmpty()) {
                    emptyCount++;
                    emptyPos = pos;
                }
            }

            if (playerCount == 2 && emptyCount == 1) {
                return emptyPos;
            }
        }
        return -1;
    }
}