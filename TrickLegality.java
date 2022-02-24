public class TrickLegality {
    private Trick trick;
    private UserInput use;

    // Round class uses TrickLegality class to check players tricks
    public TrickLegality(Trick trick, UserInput use) {
        this.trick = trick;
        this.use = use;
    }

    public Card validateTrick(Player toFollow) throws InterruptedException {
        Card selected;
        if (toFollow.getStateBOT()) {
            selected = toFollow.follow(trick);

        } else {
            selected = use.ask4Card(toFollow);
            while (!checkSuit(toFollow, selected)) {
                toFollow.takeCard(selected);
                selected = use.ask4Card(toFollow);
            }
        }
        Printer.print(toFollow, selected);
        return selected;
    }

    private boolean checkSuit(Player hand, Card card) {// Check if player trick suit matches the trick leading suit

        if (trick.isEmpty()) {
            return check4Hearts(card, hand);
        }
        if (!card.sameSuit(trick.getTopSeries())) {

            if (hand.hasSuit(trick.getTopSeries())) {
                Printer.println("You must play " + trick.getTopSeries());
                return false;
            }
        }
        return true;
    }

    private boolean check4Hearts(Card card, Player hand) {// If player played hearts check if hearts has been played

        if (Card.isHearts(card)) {

            if (!trick.getHeartsPlayed()) {
                Printer.println("Hearts didn't broken yet");
                return false;
            }

        }

        return true;
    }

    public boolean isHeartsOnly(Player hand) {// If leader started with hearts round ends immediately

        if (hand.HasOnlyHearts()) {
            Printer.println("Hand has only hearts round ended ");
            return true;
        }

        return false;
    }
}