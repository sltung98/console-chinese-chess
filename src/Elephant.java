import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Elephant extends Chess {

    private ArrayList<Move> elephantMoveList = new ArrayList<>();

    protected Elephant(boolean isFirstMove, int number) {
        Pair<Character, List<Integer>> info = createElephant(isFirstMove, number);
        updateChess(number, info.getKey(), info.getValue().get(0), info.getValue().get(1));
        elephantMoveList.add(new Move(-4, -2));
        elephantMoveList.add(new Move(-4, +2));
        elephantMoveList.add(new Move(+4, -2));
        elephantMoveList.add(new Move(+4, +2));
    }

    private Pair<Character, List<Integer>> createElephant(boolean isFirstMove, int number) {
        if (number == 1) {
            if (isFirstMove) {
                return new Pair('相', Arrays.asList(5, 10));
            } else {
                return new Pair('象', Arrays.asList(5, 1));
            }
        } else if (number == 2) {
            if (isFirstMove) {
                return new Pair('相', Arrays.asList(13, 10));
            } else {
                return new Pair('象', Arrays.asList(13, 1));
            }
        } else {
            return null;
        }
    }

    @Override
    protected void move(Player player) {
        moveElephant(player, true);
    }

    @Override
    protected void moveWithoutDisplay(Player player) {
        moveElephant(player, false);
    }

    private void moveElephant(Player player, boolean printMove) {
        int legalMoveCount = 0;
        for (int i = 0; i < elephantMoveList.size(); i++) {
            int changeInX = elephantMoveList.get(i).getChangeInX();
            int changeInY = elephantMoveList.get(i).getChangeInY();
            Coordinate currentCoordinate = this.getCoordinate();
            Coordinate newCoordinate = currentCoordinate.changeCoordinate(changeInX, changeInY);
            int y = newCoordinate.getyCoordinate();
            int x = newCoordinate.getxCoordinate();

            int type = player.detectChess(newCoordinate);
            boolean withinElephantMoveRange = ChessBoard.withinElephantMoveRange(player, newCoordinate);

            if (withinElephantMoveRange) {
                boolean detectElephantBlock = ChessBoard.detectElephantBlock(newCoordinate, currentCoordinate);
                if (!detectElephantBlock) {
                    if (type == -1) {
                        player.addLegalMove(newCoordinate);
                        if (printMove) {
                            legalMoveCount++;
                            Game.printLegalMove(legalMoveCount, newCoordinate);
                        }
                    } else if (type == 2) {
                        player.addLegalMove(newCoordinate);
                        if (printMove) {
                            legalMoveCount++;
                            char appearance = ChessBoard.getBoard()[y][x];
                            Game.printLegalMove(legalMoveCount, newCoordinate, appearance);
                        }
                    }
                }
            }
        }
    }
}



