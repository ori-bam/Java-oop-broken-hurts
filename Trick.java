import java.util.ArrayList;

public class Trick {

    private static final int SCORE_FOR_HEARTS = 1;
    private static final int SCORE_FOR_QOS = 13;

    private ArrayList<Card> tricks;
    private Seats leader;
    private Seats trickWinner;
    private int score;
    private Suits TopSeries;
    private boolean HeartsPlayed;

    public Trick() {

        tricks = new ArrayList<>();
        this.score = 0;
    }

    public void setTopSeries(Suits topSeries) {
        TopSeries = topSeries;
    }

    public Seats getTrickWinner() {
        return trickWinner;
    }

    public Suits getTopSeries() {
        return this.TopSeries;
    }

    public Seats getLeader() {
        return leader;
    }

    public int getScore() {
        return this.score;
    }

    public void setHeartsPlayed(boolean heartsPlayed) {
        HeartsPlayed = heartsPlayed;
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public void setScore() {
        this.score = 0;
    }

    public ArrayList<Card> getTricks() {
        return tricks;
    }

    public void setLeader(Seats leader) {
        this.leader = leader;
    }

    private void add(Card card, Seats seat) {// add card to trick
        if (tricks.size() == 0) {
            setTopSeries(card.getSuit());
            setLeader(seat);
        }

        calcTrick(card);
        tricks.add(card);
    }

    public void discard() {
        this.tricks = new ArrayList<>();
    }

    private void calcTrick(Card card) {// Set trick score by hearts or QOS

        if (card.sameSuit(Suits.Hearts)) {
            if (!getHeartsPlayed()) {
                HeartsPlayed = true;
            }
            updateScore(SCORE_FOR_HEARTS);
        }
        if (Card.isQueenOfSpades(card)) {
            updateScore(SCORE_FOR_QOS);
        }

    }

    public void setTrickWinner(Seats trickWinner) {
        this.trickWinner = trickWinner;
    }

    public boolean getHeartsPlayed() {
        return HeartsPlayed;
    }

    public Seats getWinner() {// Find who's the trick winner

        Card current = tricks.get(0);
        Seats winner = getLeader();

        for (int i = 1; i < tricks.size(); i++) {
            if (tricks.get(i).sameSuit(current) && tricks.get(i).higherRank(current)) {
                current = tricks.get(i);
                winner = winner.who(getLeader().ordinal(), i);
            }
        }
        setTrickWinner(winner);
        Printer.println("\n" + winner + " took the trick");
        return winner;
    }

    public void collect(Card card, Seats seat) {
        add(card, seat);
    }

    public boolean isEmpty() {// check if trick is empty for the first hand
        return this.tricks.size() == 0;
    }

    @Override
    public String toString() {
        return "Last Trick: " + tricks;
    }

}
