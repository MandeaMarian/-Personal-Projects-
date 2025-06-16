import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomBot implements BotStrategy {
    private Random random = new Random();

    @Override
    public int makeMove(JButton[] buttons) {
        ArrayList<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText().isEmpty()) {
                emptyCells.add(i);
            }
        }
        return emptyCells.isEmpty() ? -1 : emptyCells.get(random.nextInt(emptyCells.size()));
    }
}