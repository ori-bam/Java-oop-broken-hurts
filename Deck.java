import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {

    final ArrayList<Card> deck = new ArrayList<Card>(52);

    public Deck() {

        for (Suits suit : Suits.values()) {
            for (Ranks rank : Ranks.values()) {
                deck.add(Card.getCard(suit, rank));
            }
        }
        Printer.println("shuffling Deck...." + "\n");
        shuffle();

    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card give() {//drop card from deck

        return deck.remove(0);
    }

    public boolean hasCards() {//check size of deck

        return deck.size() > 0;
    }

    public boolean gatherBack(Card c) {//return card to deck
        return deck.add(c);
    }

    @Override
    public String toString() {

        return Arrays.toString(deck.toArray());

    }

}