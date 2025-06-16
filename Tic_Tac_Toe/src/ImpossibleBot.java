import javax.swing.*;
import java.util.*;

public class ImpossibleBot implements BotStrategy {
    private static final int[][] WINNING_COMBINATIONS = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    public int makeMove(JButton[] buttons) {

        int winningMove = findWinningMove(buttons, "O");
        if (winningMove != -1) return winningMove;

        int blockingMove = findWinningMove(buttons, "X");
        if (blockingMove != -1) return blockingMove;

        int forkMove = createFork(buttons);
        if (forkMove != -1) return forkMove;

        int blockForkMove = blockPotentialFork(buttons);
        if (blockForkMove != -1) return blockForkMove;

        if (buttons[4].getText().isEmpty()) return 4;

        int oppositeCorner = takeOppositeCorner(buttons);
        if (oppositeCorner != -1) return oppositeCorner;

        int emptyCorner = getEmptyCorner(buttons);
        if (emptyCorner != -1) return emptyCorner;

        return getEmptySide(buttons);
    }

    private int findWinningMove(JButton[] buttons, String player) {
        for (int[] combo : WINNING_COMBINATIONS) {
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

    private int createFork(JButton[] buttons) {
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().isEmpty()) {
                buttons[i].setText("O");
                int winningPaths = countPotentialWins(buttons, "O");
                buttons[i].setText("");

                if (winningPaths >= 2) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int blockPotentialFork(JButton[] buttons) {
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().isEmpty()) {
                buttons[i].setText("X");
                int winningPaths = countPotentialWins(buttons, "X");
                buttons[i].setText("");

                if (winningPaths >= 2) {

                    for (int j = 0; j < 9; j++) {
                        if (buttons[j].getText().isEmpty()) {
                            buttons[j].setText("O");
                            if (findWinningMove(buttons, "O") != -1) {
                                buttons[j].setText("");
                                return j;
                            }
                            buttons[j].setText("");
                        }
                    }
                    return i;
                }
            }
        }
        return -1;
    }

    private int countPotentialWins(JButton[] buttons, String player) {
        int count = 0;
        for (int[] combo : WINNING_COMBINATIONS) {
            boolean hasOpponent = false;
            int playerCount = 0;
            int emptyCount = 0;

            for (int pos : combo) {
                if (buttons[pos].getText().equals(player)) {
                    playerCount++;
                } else if (buttons[pos].getText().isEmpty()) {
                    emptyCount++;
                } else {
                    hasOpponent = true;
                }
            }

            if (!hasOpponent && playerCount == 1 && emptyCount == 2) {
                count++;
            }
        }
        return count;
    }

    private int takeOppositeCorner(JButton[] buttons) {
        int[][] oppositeCorners = {{0,8}, {2,6}, {6,2}, {8,0}};
        for (int[] pair : oppositeCorners) {
            if (buttons[pair[0]].getText().equals("X") && buttons[pair[1]].getText().isEmpty()) {
                return pair[1];
            }
        }
        return -1;
    }

    private int getEmptyCorner(JButton[] buttons) {
        int[] corners = {0, 2, 6, 8};
        for (int corner : corners) {
            if (buttons[corner].getText().isEmpty()) {
                return corner;
            }
        }
        return -1;
    }

    private int getEmptySide(JButton[] buttons) {
        int[] sides = {1, 3, 5, 7};
        for (int side : sides) {
            if (buttons[side].getText().isEmpty()) {
                return side;
            }
        }
        return -1;
    }
}