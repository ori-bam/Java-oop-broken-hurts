import java.util.ArrayList;
import java.util.Stack;

public class Round {
    private final Card DEUCE_OF_CLUBS = Card.getCard(Suits.Clubs, Ranks.Deuce);
    private final int CARDS_TO_PASS = 3;
    private final int NUMBER_OF_TRICKS = Card.NUMBER_OF_CARDS / Game.NUMBER_OF_PLAYERS;
    private final int ROUND_MAX_SCORE = 26;

    private final Deck deck;
    private final ArrayList<Player> hands;
    private final Stack<Trick> tricks;
    private UserInput use;
    private Score score;
    private TrickLegality validator;

    public Round(final Deck deck, final ArrayList<Player> players, Score score, int roundNumber)
            throws InterruptedException {
        this.hands = players;
        this.deck = deck;
        this.score = score;
        use = new UserInput();
        tricks = new Stack<>();
        deal(Seats.random());
        exchange(roundNumber);

    }

    public void deal(Seats seat) {// deal cards

        while (deck.hasCards()) {
            hands.get(seat.ordinal()).takeCard(deck.give());
            seat = seat.next();
        }
        Printer.print(hands);
    }

    public Score play() throws InterruptedException {// main function of the game
        Trick trick = new Trick();
        validator = new TrickLegality(trick, use);
        Player toLead = null;

        for (int trickNumber = 1; trickNumber <= NUMBER_OF_TRICKS; trickNumber++) {

            toLead = lead(trickNumber, toLead, trick);
            Player toFollow = nextPlayer(toLead);
            follow(toLead, toFollow, trick);
            toLead = findWinner(toLead, trick);

            if (hasHeartsTopSeries(toLead)) {
                return score;
            }
            trick = initializeTrick(trick);

        }
        updateScore();
        returnToDeckFromHands();
        return score;
    }

    private void follow(Player toLead, Player toFollow, Trick trick) throws InterruptedException {// other players
                                                                                                  // follow the trick
        while (toLead != toFollow) {
            Printer.print(toFollow);
            Thread.sleep(1000);
            trick.collect(validator.validateTrick(toFollow), toFollow.Seat());
            toFollow = nextPlayer(toFollow);
        }
    }

    private Player lead(int trickNumber, Player toLead, Trick trick) throws InterruptedException {// leader plays trick
        if (trickNumber == 1) {
            toLead = firstTrick(trick, 1);
        } else {
            Printer.print(toLead, trickNumber);
            Printer.print(toLead);
            trick.collect(validator.validateTrick(toLead), toLead.Seat());
            Printer.print(trick);
        }
        return toLead;
    }

    private Player findWinner(Player toLead, Trick trick) {// Resolving trick winner and by that pass him the trick
        toLead = winner(trick);
        toLead.addTrick(trick);
        tricks.push(trick);
        return toLead;
    }

    private boolean hasHeartsTopSeries(Player toLead) {// Figure if the trick leader has only hearts and by that ending
                                                       // the round with score distribution same as "Shoot the moon"
        if (toLead.hasCards()) {
            if (hasOnlyHearts(validator, toLead)) {
                returnToDeckFromTricks();
                return true;
            }
        }
        return false;
    }

    private void returnToDeckFromTricks() {// after round ends collect all the cards from the tricks back to deck
        for (Trick trick : tricks) {
            for (Card card : trick.getTricks()) {
                deck.gatherBack(card);
            }
        }
    }

    private void returnToDeckFromHands() {// If round ended with hearts collect all the cards remained in players hands

        returnToDeckFromTricks();

        for (Player player : hands) {
            while (player.hasCards()) {
                deck.gatherBack(player.dispose());
            }
        }
    }

    private Player nextPlayer(Player p) {
        return hands.get(p.Seat().next().ordinal());
    }

    private Player firstTrick(Trick trick, int trickNumber) {

        Player toLead = whosDeuceOfClubs();

        int deuceOfClubs = DEUCE_OF_CLUBS.ordinal();
        trick.collect(toLead.dispose(deuceOfClubs), toLead.Seat());
        Printer.print(toLead, trickNumber);
        Printer.print(trick);
        return toLead;

    }

    private boolean hasOnlyHearts(TrickLegality validator, Player hand) {

        if (validator.isHeartsOnly(hand)) {
            updateSpecialScore(hand);
            return true;
        }
        return false;
    }

    private Trick initializeTrick(Trick trick) {
        Trick temp = trick;
        trick = new Trick();
        trick.setHeartsPlayed(temp.getHeartsPlayed());
        trick.setLeader(temp.getTrickWinner());
        validator = new TrickLegality(trick, use);
        Printer.printTrick(tricks.peek());
        return trick;
    }

    private void updateScore() {// Update scores for players after each round

        if (!shootTheMoon()) {
            for (Player player : hands) {
                score.add(player.Seat(), score.calcFromTricks(player.getTricks()));
                player.clear();
            }
        }
    }

    private boolean shootTheMoon() {// Check in the end of the round if any player holds all cards that has score
                                    // values

        for (Player player : hands) {
            if (score.calcFromTricks(player.getTricks()) == ROUND_MAX_SCORE) {
                updateSpecialScore(player);
                return true;
            }
        }
        return false;
    }

    private void updateSpecialScore(Player hand) {// Update score for shoot the moon and hearts opening trick
        for (Player han : hands) {
            if (!han.equals(hand)) {
                score.add(han.Seat(), ROUND_MAX_SCORE);

            }
            han.clear();
        }

    }

    private Player winner(Trick trick) {
        return hands.get(trick.getWinner().ordinal());
    }

    private Player whosDeuceOfClubs() {// Which hand holds DeuceOfClubs
        return hands.stream().filter(hand -> hand.HaveDeuceOfClubs()).findAny().get();
    }

    public void exchange(int roundNumber) throws InterruptedException {// Exchange 3 cards to each player by round
                                                                       // number

        if (!isExchangeNeeded(ExchangeTypes.fromOrdinal(roundNumber))) {
            return;
        }
        Printer.print("Players please select 3 cards to exchange:  " + "\n" + "\n");
        WhereTo(dropCards4Exchange(), ExchangeTypes.direction(ExchangeTypes.fromOrdinal(roundNumber)));
        Printer.printAfterExchange(hands);

    }

    private boolean isExchangeNeeded(ExchangeTypes type) {

        if (type == ExchangeTypes.NONE) {
            Printer.print("No need to exchange this round");
            return false;
        }
        return true;
    }

    private ArrayList<? extends ArrayList<Card>> dropCards4Exchange() throws InterruptedException {

        final ArrayList<ArrayList<Card>> toMove = new ArrayList<>();

        for (Player hand : hands) {

            toMove.add(use.askCards4Exchange(hand, CARDS_TO_PASS));

        }
        return toMove;
    }

    private void WhereTo(final ArrayList<? extends ArrayList<Card>> toMove, int shift) {// Pass cards after exchange

        for (int i = 0; i < hands.size(); i++) {

            hands.get(i).takeCards(toMove.get(where(i, shift)));

        }

    }

    private int where(int from, int shift) {
        return (from + shift) % hands.size();
    }

    @Override
    public String toString() {

        return "\n" + "\n" + "Round score: " + "\n" + hands + "\n" + score + "\n";
    }

}
