
import java.util.ArrayList;

public class Game {
    public final static int NUMBER_OF_PLAYERS = 4;
    private final ArrayList<Player> players;
    private Score score;
    private final Deck deck;
    private int roundNumber;

    public Game(ArrayList<Player> players) throws InterruptedException {
        this.players = players;
        this.deck = new Deck();
        this.score = new Score(players.size());
        this.roundNumber = 1;
        play();

    }

    private void play() throws InterruptedException {

        while (!score.reachedTarget()) {

            Round round = new Round(deck, players, score, roundNumber);
            round.play();
            System.out.println(round);
            roundNumber++;
        }

    }

    @Override
    public String toString() {

        return "Game over" + "\n" + "\n" + "final score: " + "\n" + players + "\n" + score + "\n";
    }

    public static void main(String[] args) throws InterruptedException {

        Game game = new Game(placingPlayers());
        System.out.println(game);

    }

    public static ArrayList<Player> placingPlayers() {
        UserInput use = new UserInput();
        ArrayList<Player> players = new ArrayList<>(4);

        for (Seats seat : Seats.values()) {
            Printer.print("Player " + seat.ordinal() + " BOT true/false: ");
            players.add(new Player(seat, "player " + seat.ordinal(), use.nextBoolean()));
        }
        return players;
    }

}
