import java.util.ArrayList;

class Printer {/// class for game prints

    static void print(Player hand) {
        System.out.println(hand + " " + hand.getHand() + "\n" + "Cards indexes " + "\t   " + hand.cardsIndexes());
    }

    static void print(ArrayList<Player> hands) {
        for (Player hand : hands) {
            System.out.println(hand + " " + hand.getHand());
        }
        System.out.println();

    }

    static void print(Player hand, String what) {
        System.out.print(hand + what);
    }

    static void print(Player hand, Card card) {
        System.out.println(hand.getName() + " played " + card);
    }

    static void print(Trick trick) {
        System.out.print("\n" + "The trick is " + trick.getTopSeries().getSymbol() + "\n" + "\n");
    }

    static void print(String what) {
        System.out.print(what);
    }

    static void printTrick(Trick trick) {
        System.out.print(trick);
    }

    static void println(String what) {
        System.out.println(what);
    }

    static void printAfterExchange(ArrayList<Player> hands) {

        Printer.print("\n" + "State of cards after exchange: " + "\n" + "\n");
        Printer.print(hands);

    }

    static void print(Player toLead, int trickNumber) {
        System.out.println("\n" + toLead + "leads " + "Trick " + trickNumber + ": " + "\n");

    }

}
