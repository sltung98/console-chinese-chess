import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class Cannon extends Chess {

    protected Cannon(boolean isFirstMove, int number) {
        Pair<Character, List<Integer>> info = createCannon(isFirstMove, number);
        updateChess(number, info.getKey(), info.getValue().get(0), info.getValue().get(1));
    }

    private Pair<Character, List<Integer>> createCannon(boolean isFirstMove, int number) {
        if (number == 1) {
            if (isFirstMove) {
                return new Pair('\u70AE', Arrays.asList(3, 8));
            } else {
                return new Pair('\u7832', Arrays.asList(3, 3));
            }
        } else if (number == 2) {
            if (isFirstMove) {
                return new Pair('\u70AE', Arrays.asList(15, 8));
            } else {
                return new Pair('\u7832', Arrays.asList(15, 3));
            }
        } else {
            return null;
        }
    }

    @Override
    protected void move(Player player) {
        moveCannon(player, true);
    }

    @Override
    protected void moveWithoutDisplay(Player player) {
        moveCannon(player, false);
    }

    private void moveCannon(Player player, boolean printMove) {
        int xCoordinate = this.getCoordinate().getxCoordinate();
        int yCoordinate = this.getCoordinate().getyCoordinate();
        int UpperBound = player.UpperBound(this.getCoordinate());
        int lowerBound = player.lowerBound(this.getCoordinate());
        int leftBound = player.leftBound(this.getCoordinate());
        int rightBound = player.rightBound(this.getCoordinate());
        int legalMoveCount = 0;

        for (int i = UpperBound; i <= lowerBound; i++) {
            Coordinate newCoordinate = new Coordinate(xCoordinate, i);
            int type = player.detectChess(newCoordinate);
            if (type == -1 && i != yCoordinate) {
                player.addLegalMove(newCoordinate);
                if (printMove) {
                    legalMoveCount++;
                    Game.printLegalMove(legalMoveCount, newCoordinate);
                }
            } else if ((i == UpperBound && i != ChessBoard.getUpperBound())
                    || i == lowerBound && i != ChessBoard.getLowerBound()) {
                int targetyCoordinate;
                if (i == UpperBound && i != ChessBoard.getUpperBound()) {
                    targetyCoordinate = player.UpperBound(newCoordinate);
                } else {
                    targetyCoordinate = player.lowerBound(newCoordinate);
                }
                newCoordinate = new Coordinate(xCoordinate, targetyCoordinate);
                type = player.detectChess(newCoordinate);
                if (type == 2) {
                    char appearance = ChessBoard.getBoard()[targetyCoordinate][xCoordinate];
                    player.addLegalMove(newCoordinate);
                    if (printMove) {
                        legalMoveCount++;
                        Game.printLegalMove(legalMoveCount, newCoordinate, appearance);
                    }
                }
            }
        }

        for (int j = leftBound; j <= rightBound; j += 2) {
            Coordinate newCoordinate = new Coordinate(j, yCoordinate);
            int type = player.detectChess(newCoordinate);
            if (type == -1 && j != xCoordinate) {
                player.addLegalMove(newCoordinate);
                if (printMove) {
                    legalMoveCount++;
                    Game.printLegalMove(legalMoveCount, newCoordinate);
                }
            } else if ((j == leftBound && j != ChessBoard.getLeftBound())
                    || j == rightBound && j != ChessBoard.getRightBound()) {
                int targetxCoordinate;
                if (j == leftBound && j != ChessBoard.getLeftBound()) {
                    targetxCoordinate = player.leftBound(newCoordinate);
                } else {
                    targetxCoordinate = player.rightBound(newCoordinate);
                }
                newCoordinate = new Coordinate(targetxCoordinate, yCoordinate);
                type = player.detectChess(newCoordinate);
                if (type == 2) {
                    player.addLegalMove(newCoordinate);
                    char appearance = ChessBoard.getBoard()[yCoordinate][targetxCoordinate];
                    if (printMove) {
                        legalMoveCount++;
                        Game.printLegalMove(legalMoveCount, newCoordinate, appearance);
                    }
                }
            }
        }
    }


}
