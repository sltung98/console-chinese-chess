public class Move {

    private int changeInX;
    private int changeInY;

    protected Move(int x, int y) {
        this.changeInX = x;
        this.changeInY = y;
    }

    protected int getChangeInX() {
        return changeInX;
    }

    protected int getChangeInY() {
        return changeInY;
    }

}