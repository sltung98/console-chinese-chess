import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;

public class Adviser extends Chess {

    private ArrayList<Move> adviserMoveList = new ArrayList<>();

    protected Adviser(boolean isFirstMove, int number) {
        Pair<Character, List<Integer>> info = createAdviser(isFirstMove, number);
        updateChess(number, info.getKey(), info.getValue().get(0), info.getValue().get(1));
        adviserMoveList.add(new Move(-2, -1));
        adviserMoveList.add(new Move(-2, +1));
        adviserMoveList.add(new Move(2, -1));
        adviserMoveList.add(new Move(2, 1));
    }

    private Pair<Character, List<Integer>> createAdviser(boolean isFirstMove, int number) {
        if (number == 1) {
            if (isFirstMove) {
                return new Pair('\u4ED5', Arrays.asList(7, 10));
            } else {
                return new Pair('\u58EB', Arrays.asList(7, 1));
            }
        } else if (number == 2) {
            if (isFirstMove) {
                return new Pair('\u4ED5', Arrays.asList(11, 10));
            } else {
                return new Pair('\u58EB', Arrays.asList(11, 1));
            }
        } else {
            return null;
        }
    }

    @Override
    protected void move(Player player) {
        moveAdviser(player, true);
    }

    @Override
    protected void moveWithoutDisplay(Player player) {
        moveAdviser(player, false);
    }

    private void moveAdviser(Player player, boolean printMove) {
        Coordinate currentCoordinate = this.getCoordinate();
        int legalMoveCount = 0;

        for (int i = 0; i < adviserMoveList.size(); i++) {
            int changeInX = adviserMoveList.get(i).getChangeInX();
            int changeInY = adviserMoveList.get(i).getChangeInY();
            Coordinate newCoordinate = currentCoordinate.changeCoordinate(changeInX, changeInY);
            int y = newCoordinate.getyCoordinate();
            int x = newCoordinate.getxCoordinate();

            int type = player.detectChess(newCoordinate);
            boolean withinPalace = ChessBoard.withinPalace(player, newCoordinate);
            if (withinPalace) {
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
