
public class Player extends Hand {

    private String name;
    private Seats seat;
    private boolean stateBOT;// Define if player will be bot or human

    public boolean getStateBOT() {
        return this.stateBOT;
    }

    public Player(Seats seat, String name, boolean stateBOT) {
        this.seat = seat;
        this.name = name;
        this.stateBOT = stateBOT;
    }

    public Seats Seat() {
        return this.seat;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {

        return Seat().getSname() + " ";
    }

}