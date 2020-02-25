import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class General extends Chess {

    private ArrayList<Move> generalMoveList = new ArrayList<>();

    protected General(boolean isFirstMove) {
        Pair<Character, List<Integer>> info = createGeneral(isFirstMove);
        updateChess(-1, info.getKey(), info.getValue().get(0), info.getValue().get(1));
        generalMoveList.add(new Move(2, 0));
        generalMoveList.add(new Move(-2, 0));
        generalMoveList.add(new Move(0, -1));
        generalMoveList.add(new Move(0, 1));
    }

    private Pair<Character, List<Integer>> createGeneral(boolean isFirstMove) {
        if (isFirstMove) {
            return new Pair('帥', Arrays.asList(9, 10));
        } else {
            return new Pair('將', Arrays.asList(9, 1));
        }
    }

    @Override
    protected void move(Player player) {
        moveGeneral(player, true);
    }

    @Override
    protected void moveWithoutDisplay(Player player) {
        moveGeneral(player, false);
    }

    private void moveGeneral(Player player, boolean printMove) {
        Coordinate currentCoordinate = this.getCoordinate();
        int legalMoveCount = 0;

        for (int i = 0; i < generalMoveList.size(); i++) {
            int changeInX = generalMoveList.get(i).getChangeInX();
            int changeInY = generalMoveList.get(i).getChangeInY();
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

        Coordinate oppGeneralCoordinate = player.getOpponentChessList().get(player.getGeneralIndex()).
                getCoordinate();

        int oppGeneralxCoordinate = oppGeneralCoordinate.getxCoordinate();
        int oppGeneralyCoordinate = oppGeneralCoordinate.getyCoordinate();

        if (currentCoordinate.getxCoordinate() == oppGeneralxCoordinate) {
            if ((player.isFirstMove() && player.UpperBound(currentCoordinate) == oppGeneralyCoordinate) ||
                    (!player.isFirstMove() && player.lowerBound(currentCoordinate) == oppGeneralyCoordinate)) {
                char appearance = player.getOpponentChessList().get(player.getGeneralIndex()).getAppearance();
                player.addLegalMove(oppGeneralCoordinate);
                if (printMove) {
                    legalMoveCount++;
                    Game.printLegalMove(legalMoveCount, oppGeneralCoordinate, appearance);
                }
            }
        }

    }
}
