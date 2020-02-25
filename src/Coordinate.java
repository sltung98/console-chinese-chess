public class Coordinate {
    private int xCoordinate;
    private int yCoordinate;

    protected Coordinate(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    protected int getxCoordinate() {
        return xCoordinate;
    }

    protected int getyCoordinate() {
        return yCoordinate;
    }

    protected void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    protected void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    protected Coordinate changeCoordinate(int changeInX, int changeInY) {
        int xCoordinate = this.xCoordinate + changeInX;
        int yCoordinate = this.yCoordinate + changeInY;
        return new Coordinate(xCoordinate, yCoordinate);
    }


    @Override
    public String toString() {
        return xCoordinate + "" + yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate c = (Coordinate) o;

        if ((xCoordinate == c.xCoordinate) && (yCoordinate == c.yCoordinate)) {
            return true;
        }else{
            return false;
        }

        }

        /*hashCode() method not overidden*/
    }

