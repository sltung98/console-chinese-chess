import java.util.ArrayList;

public abstract class Chess {
    public char appearance;
    private Coordinate coordinate;
    private boolean isAlive;
    private int number;

    protected void updateChess(int number, char appearance, int xCoordinate, int yCoordinate) {
        this.number = number;
        this.appearance = appearance;
        this.coordinate = new Coordinate(xCoordinate, yCoordinate);
        this.isAlive = true;
    }

    public void takeDownChess() {
        isAlive = false;
    }

    public char getAppearance() {
        return appearance;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    protected abstract void move(Player player);

    protected abstract void moveWithoutDisplay(Player player);

    public static void detectCheckMate(Player player, int i) {
        ArrayList<Chess> chessList = player.getChessList();
        chessList.get(i).moveWithoutDisplay(player);
    }


}


