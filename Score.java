
import java.util.ArrayList;
import java.util.Arrays;

public class Score {
    private int[] scores;

    public Score(int size) {
        this.scores = new int[size];
    }

    public boolean reachedTarget() {
        for (int i : scores) {
            if (i >= 100) {
                return true;
            }
        }
        return false;
    }

    public void add(Seats seat, int score) {
        scores[seat.ordinal()] += score;
    }

    public int calcFromTricks(ArrayList<Trick> tricks) {
        int total = 0;
        for (Trick trick : tricks) {
            total += trick.getScore();
        }
        return total;
    }

    @Override
    public String toString() {

        return Arrays.toString(scores);
    }
}
