import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Hand {

    private SortedSet<Card> hand;
    private ArrayList<Trick> tricks;

    public Hand() {
        this.hand = new TreeSet<>();
        tricks = new ArrayList<>();
    }

    public String cardsIndexes() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        int[] indexes = new int[hand.size()];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
            sb.append(i + " ");
        }
        sb.append("] ");
        return sb.toString();
    }

    public Card getByIndex(int idx) {
        return new ArrayList<Card>(hand).get(idx);
    }

    public ArrayList<Trick> getTricks() {
        return tricks;
    }

    public ArrayList<Card> getHighRankCards(int quantity) {
        ArrayList<Card> topThree = new ArrayList<Card>();
        while (quantity > 0) {
            Card highest = hand.first();
            for (Card card : hand) {
                if (card.higherRank(highest)) {
                    highest = card;
                }
            }
            topThree.add(highest);
            hand.remove(highest);
            quantity--;
        }

        return topThree;

    }

    public Card dispose(int card) {
        Card toReturn = new ArrayList<Card>(hand).get(card);
        hand.remove(toReturn);
        return toReturn;
    }

    public Card dispose() {
        Card toReturn = hand.first();
        hand.remove(hand.first());
        return toReturn;
    }

    public Card follow(Trick trick) {
        Card temp = null;
        for (Card card : hand) {
            if (card.getSuit().equals(trick.getTopSeries())) {
                temp = card;
                hand.remove(card);
                return temp;
            }
        }
        temp = hand.first();
        hand.remove(hand.first());
        return temp;
    }

    public int size() {
        return hand.size();
    }

    public void clear() {
        this.tricks.clear();
    }

    public void addTrick(Trick trick) {
        tricks.add(trick);
    }

    public boolean hasCard(int card) {

        if (card >= 0 && card < hand.size()) {
            return true;
        }
        Printer.println("Invalid index");
        return false;
    }

    public boolean hasSuit(Suits suit) {
        return hand.stream().filter(hand -> hand.sameSuit(suit)).findAny().isPresent();

    }

    public boolean HaveDeuceOfClubs() {
        return hand.contains(Card.getCard(Suits.Clubs, Ranks.Deuce));
    }

    public boolean HasOnlyHearts() {

        for (Card card : hand) {
            if (!card.sameSuit(Suits.Hearts)) {
                return false;
            }
        }
        return true;

    }

    public void takeCard(Card other) {
        this.hand.add(other);
    }

    public void takeCards(ArrayList<Card> cards) {
        this.hand.addAll(cards);
    }

    public SortedSet<Card> getHand() {
        return hand;
    }

    public boolean hasCards() {
        return this.hand.size() > 0;
    }

    @Override
    public String toString() {
        return "Hand " + hand;

    }
}
