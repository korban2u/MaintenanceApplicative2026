package trivia;

public class Player {
    private final String name;
    private int place = 1;
    private int purse = 0;
    private boolean inPenaltyBox = false;
    private boolean isGettingOutOfPenaltyBox = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public int getPlace() { return place; }
    public void setPlace(int place) { this.place = place; }

    public int getPurse() { return purse; }
    public void addCoin() { this.purse++; }

    public boolean isInPenaltyBox() { return inPenaltyBox; }
    public void setInPenaltyBox(boolean inPenaltyBox) { this.inPenaltyBox = inPenaltyBox; }

    public boolean isGettingOutOfPenaltyBox() { return isGettingOutOfPenaltyBox; }
    public void setGettingOutOfPenaltyBox(boolean gettingOut) { this.isGettingOutOfPenaltyBox = gettingOut; }
}