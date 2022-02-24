
enum Suits {
    Clubs("\u2663"), Diamonds("\u2666"), Hearts("\u2665"), Spades("\u2660");

    static final int NUMBER_OF_SUITS = Spades.ordinal() + 1;

    private final String symbol;

    Suits(final String unicodeSymbol) {
        this.symbol = unicodeSymbol;

    }

    public String getSymbol() {
        return symbol;
    }
}

enum Ranks {
    Deuce('2'), Three('3'), Four('4'), Five('5'), Six('6'), Seven('7'), Eight('8'), Nine('9'), Ten('T'), Jack('J'),
    Queen('Q'), King('K'), Ace('A');

    static final int NUMBER_OF_RANKS = Ace.ordinal() + 1;

    private final String symbol;

    Ranks(final char oneChar) {
        this.symbol = String.valueOf(oneChar);

    }

    public String getSymbol() {
        return symbol;
    }
}

public class Card implements Comparable<Card> {
    private static final Suits DEF_SUITS = Suits.Clubs;
    private static final Ranks DEF_RANKS = Ranks.Deuce;

    public static final int NUMBER_OF_CARDS = Suits.NUMBER_OF_SUITS * Ranks.NUMBER_OF_RANKS;
    private static final Card[] cards = new Card[NUMBER_OF_CARDS];

    static {
        int i = 0;
        for (Suits suit : Suits.values()) {
            for (Ranks rank : Ranks.values()) {
                cards[i++] = new Card(suit, rank);
            }
        }
    }

    private final Suits suit;
    private final Ranks rank;

    public Card(final Suits suit, final Ranks rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Card() {
        this(DEF_SUITS, DEF_RANKS);
    }

    public Suits getSuit() {
        return this.suit;
    }

    public Ranks getRank() {
        return this.rank;
    }

    public static Card getCard(Suits suit, Ranks ranks) {
        return cards[ordinal(suit, ranks)];
    }

    public static boolean isHearts(Card card) {
        return card.getSuit().equals(Suits.Hearts);
    }

    public static boolean isQueenOfSpades(Card card) {
        return card.equals(getCard(Suits.Spades, Ranks.Queen));
    }

    public int ordinal() {
        return ordinal(this.suit, this.rank);
    }

    public boolean higherRank(Card card) {
        return card.getRank().compareTo(this.rank) < 0;
    }

    public boolean sameSuit(Suits suit) {
        return this.suit.equals(suit);
    }

    public boolean sameSuit(Card card) {
        return this.suit.equals(card.getSuit());
    }

    private static int ordinal(Suits suit, Ranks rank) {
        return suit.ordinal() * Ranks.NUMBER_OF_RANKS + rank.ordinal();
    }

    public boolean equals(final Card other) {
        return this.suit == other.suit && this.rank == other.rank;
    }

    @Override
    public int compareTo(final Card other) {
        return this.suit == other.suit ? this.rank.compareTo(other.rank) : this.suit.compareTo(other.suit);
    }

    public String getFullName() {
        return rank.toString() + " of " + suit.toString();
    }

    public String getShortName() {
        return rank.getSymbol() + suit.getSymbol();
    }

    @Override
    public String toString() {
        return getShortName();
    }

}
