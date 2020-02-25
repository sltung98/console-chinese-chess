import javafx.util.Pair;

import java.util.ArrayList;

public class Player {

    private boolean isFirstMove;
    private String name;
    private ArrayList<Chess> opponentChessList;
    private ArrayList<Chess> chessList;
    private ArrayList<Coordinate> legalMoveList = new ArrayList<>();
    private int generalIndex = 0;

    public ArrayList<Chess> getChessList() {
        return chessList;
    }

    public Player(boolean isFirstMove, String name) {
        this.isFirstMove = isFirstMove;
        this.name = name;
        this.chessList = new ArrayList<>();
        this.opponentChessList = new ArrayList<>();
        if (isFirstMove) {
            this.chessList.add(generalIndex, new General(true));
            this.chessList.add(new Adviser(true, 1));
            this.chessList.add(new Adviser(true, 2));
            this.chessList.add(new Elephant(true, 1));
            this.chessList.add(new Elephant(true, 2));
            this.chessList.add(new Horse(true, 1));
            this.chessList.add(new Horse(true, 2));
            this.chessList.add(new Chariot(true, 1));
            this.chessList.add(new Chariot(true, 2));
            this.chessList.add(new Cannon(true, 1));
            this.chessList.add(new Cannon(true, 2));
            this.chessList.add(new Soldier(true, 1));
            this.chessList.add(new Soldier(true, 2));
            this.chessList.add(new Soldier(true, 3));
            this.chessList.add(new Soldier(true, 4));
            this.chessList.add(new Soldier(true, 5));
        } else {
            this.chessList.add(generalIndex, new General(false));
            this.chessList.add(new Adviser(false, 1));
            this.chessList.add(new Adviser(false, 2));
            this.chessList.add(new Elephant(false, 1));
            this.chessList.add(new Elephant(false, 2));
            this.chessList.add(new Horse(false, 1));
            this.chessList.add(new Horse(false, 2));
            this.chessList.add(new Chariot(false, 1));
            this.chessList.add(new Chariot(false, 2));
            this.chessList.add(new Cannon(false, 1));
            this.chessList.add(new Cannon(false, 2));
            this.chessList.add(new Soldier(false, 1));
            this.chessList.add(new Soldier(false, 2));
            this.chessList.add(new Soldier(false, 3));
            this.chessList.add(new Soldier(false, 4));
            this.chessList.add(new Soldier(false, 5));
        }
    }

    protected int detectChess(Coordinate coordinate) {
        for (int i = 0; i < chessList.size(); i++) {
            if (coordinate.equals(chessList.get(i).getCoordinate())) {
                return 1;
            }
        }
        for (int i = 0; i < opponentChessList.size(); i++) {
            if (coordinate.equals(opponentChessList.get(i).getCoordinate())) {
                return 2;
            }
        }
        return -1;
    }

    protected int UpperBound(Coordinate coordinate) {
        int xCoordinate = coordinate.getxCoordinate();
        int yCoordinate = coordinate.getyCoordinate();
        int upperBound = ChessBoard.getUpperBound();

        for (int i = yCoordinate - 1; i > upperBound; i--) {
            int result = detectChess(new Coordinate(xCoordinate, i));
            if (result != -1) {
                return i;
            }
        }
        return upperBound;
    }

    protected int lowerBound(Coordinate coordinate) {
        int xCoordinate = coordinate.getxCoordinate();
        int yCoordinate = coordinate.getyCoordinate();
        int lowerBound = ChessBoard.getLowerBound();


        for (int i = yCoordinate + 1; i <= lowerBound; i++) {
            int result = detectChess(new Coordinate(xCoordinate, i));
            if (result != -1) {
                return i;
            }
        }
        return lowerBound;
    }

    protected int leftBound(Coordinate coordinate) {
        int xCoordinate = coordinate.getxCoordinate();
        int yCoordinate = coordinate.getyCoordinate();
        int leftBound = ChessBoard.getLeftBound();

        for (int i = xCoordinate - 2; i >= leftBound; i -= 2) {
            int result = detectChess(new Coordinate(i, yCoordinate));
            if (result != -1) {
                return i;
            }
        }
        return leftBound;
    }

    protected int rightBound(Coordinate coordinate) {
        int xCoordinate = coordinate.getxCoordinate();
        int yCoordinate = coordinate.getyCoordinate();
        int rightBound = ChessBoard.getRightBound();

        for (int i = xCoordinate + 2; i <= rightBound; i += 2) {
            int result = detectChess(new Coordinate(i, yCoordinate));
            if (result != -1) {
                return i;
            }
        }
        return rightBound;
    }

    protected boolean isFirstMove() {
        return isFirstMove;
    }

    protected void setOpponentChessList(ArrayList<Chess> chessList) {
        this.opponentChessList = chessList;
    }

    protected void addLegalMove(Coordinate coordinate) {
        legalMoveList.add(coordinate);
    }

    protected Pair<Character, Coordinate> moveChess(int chessIndex, int moveIndex) {
        int x = chessList.get(chessIndex).getCoordinate().getxCoordinate();
        int y = chessList.get(chessIndex).getCoordinate().getyCoordinate();
        char appearance = chessList.get(chessIndex).getAppearance();

        Coordinate newCoordinate = legalMoveList.get(moveIndex);
        int newX = newCoordinate.getxCoordinate();
        int newY = newCoordinate.getyCoordinate();
        char newAppearance = ChessBoard.getBoard()[newY][newX];

        ChessBoard.eraseChess(x, y);
        ChessBoard.replaceChess(newX, newY, appearance);
        resetLegalMoveList();
        return new Pair(newAppearance, newCoordinate);
    }

    protected void updateChessInformation(int chessIndex, int moveIndex) {
        int newX = legalMoveList.get(moveIndex).getxCoordinate();
        int newY = legalMoveList.get(moveIndex).getyCoordinate();

        if (detectChess(legalMoveList.get(moveIndex)) == 2) {
            int i = findOpponentChess(newX, newY);
            if (opponentChessList.get(i) instanceof General) {
                Game.endGame();
            }
            opponentChessList.get(i).takeDownChess();
            opponentChessList.remove(i);
        }
        chessList.get(chessIndex).getCoordinate().setxCoordinate(newX);
        chessList.get(chessIndex).getCoordinate().setyCoordinate(newY);
        resetLegalMoveList();
    }

    private int findOpponentChess(int newX, int newY) {
        for (int i = 0; i < opponentChessList.size(); i++) {
            int x = opponentChessList.get(i).getCoordinate().getxCoordinate();
            int y = opponentChessList.get(i).getCoordinate().getyCoordinate();
            if (newX == x && newY == y) {
                return i;
            }
        }
        return -1;
    }

    protected int getGeneralIndex() {
        return generalIndex;
    }

    protected ArrayList<Chess> getOpponentChessList() {
        return opponentChessList;
    }

    protected void detectCheckMate() {
        for (int i = 0; i < chessList.size(); i++) {
            Chess.detectCheckMate(this, i);
        }

        Coordinate oppoGeneralCoordinate = opponentChessList.get(generalIndex).getCoordinate();
        int oppoGeneralxCoordinate = oppoGeneralCoordinate.getxCoordinate();
        int oppoGeneralyCoordinate = oppoGeneralCoordinate.getyCoordinate();

        for (int i = 0; i < legalMoveList.size(); i++) {
            int xCoordinate = legalMoveList.get(i).getxCoordinate();
            int yCoordinate = legalMoveList.get(i).getyCoordinate();
            if (oppoGeneralxCoordinate == xCoordinate &&
                    oppoGeneralyCoordinate == yCoordinate) {
                System.out.println("Checkmate.");
            }
        }
        legalMoveList.clear();
    }

    protected ArrayList<Coordinate> getLegalMoveList() {
        return legalMoveList;
    }

    protected void resetLegalMoveList(){
        legalMoveList.clear();
    }

}