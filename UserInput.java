import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class UserInput {

    private Scanner in;

    public UserInput() {

        in = new Scanner(System.in);

    }

    public String nextString() {
        return in.next();
    }

    public boolean nextBoolean() {
        return in.nextBoolean();
    }

    private int nextInt() {

        int val = Integer.MIN_VALUE;
        boolean valid = false;
        do {
            try {
                val = in.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.print("Thats not a number try again:");
                in = new Scanner(System.in);
            }
        } while (!valid);
        return val;
    }

    public Card ask4Card(Player hand) throws InterruptedException {

        int idx = 0;
        do {
            Printer.print(hand.getName() + " drop card: ");

            idx = nextInt();

        } while (!hand.hasCard(idx));

        return hand.dispose(idx);
    }

    public ArrayList<Card> askCards4Exchange(Player hand, int quantity) throws InterruptedException {
        ArrayList<Card> ofPlayer = new ArrayList<>();
        if (hand.getStateBOT()) {
            ofPlayer.addAll(hand.getHighRankCards(quantity));

        } else {
            while (quantity > 0) {
                Printer.print(hand);
                ofPlayer.add(ask4Card(hand));
                quantity--;
            }
        }
        Printer.print(hand);
        Thread.sleep(1000);
        Printer.println(hand.getName() + " selected " + ofPlayer + "\n");
        Thread.sleep(1000);

        return ofPlayer;
    }

}
