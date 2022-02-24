import java.util.Random;

public enum Seats {

    North("N"), East("E"), South("S"), West("W");

    static final int NUM_SEATS = Seats.West.ordinal() + 1;

    private final String letter;

    Seats(final String letter) {
        this.letter = String.valueOf(letter);

    }

    public String getSname() {
        return letter;
    }

    public Seats next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public Seats who(int from, int to) {
        return Seats.values()[(from + to) % values().length];
    }

    public static Seats random() {
        Random rnd = new Random();
        return values()[rnd.nextInt(Seats.values().length)];

    }

    @Override
    public String toString() {

        return getSname();
    }

}
