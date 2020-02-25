import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class Chariot extends Chess {

    protected Chariot(boolean isFirstMove, int number) {
        Pair<Character, List<Integer>> info = createChariot(isFirstMove, number);
        updateChess(number, info.getKey(), info.getValue().get(0), info.getValue().get(1));
    }

    private Pair<Character, List<Integer>> createChariot(boolean isFirstMove, int number) {
        if (number == 1) {
            if (isFirstMove) {
                return new Pair('俥', Arrays.asList(1, 10));
            } else {
                return new Pair('車', Arrays.asList(1, 1));
            }
        } else if (number == 2) {
            if (isFirstMove) {
                return new Pair('俥', Arrays.asList(17, 10));
            } else {
                return new Pair('車', Arrays.asList(17, 1));
            }
        } else {
            return null;
        }
    }

    @Override
    protected void move(Player player) {
        moveChariot(player, true);
    }

    @Override
    protected void moveWithoutDisplay(Player player) {
        moveChariot(player, false);
    }

    private void moveChariot(Player player, boolean printMove) {
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
            } else if (type == 2) {
                player.addLegalMove(newCoordinate);
                char appearance = ChessBoard.getBoard()[i][xCoordinate];
                if (printMove) {
                    legalMoveCount++;
                    Game.printLegalMove(legalMoveCount, newCoordinate, appearance);
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
            } else if (type == 2) {
                player.addLegalMove(newCoordinate);
                char appearance = ChessBoard.getBoard()[yCoordinate][j];
                if (printMove) {
                    legalMoveCount++;
                    Game.printLegalMove(legalMoveCount, newCoordinate, appearance);
                }
            }
        }
    }
}
