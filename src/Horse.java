import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Horse extends Chess {

    private ArrayList<Move> horseMoveList = new ArrayList<>();

    protected Horse(boolean isFirstMove, int number) {
        Pair<Character, List<Integer>> info = createHorse(isFirstMove, number);
        updateChess(number, info.getKey(), info.getValue().get(0), info.getValue().get(1));
        horseMoveList.add(new Move(-2, -2));
        horseMoveList.add(new Move(-2, +2));
        horseMoveList.add(new Move(+2, -2));
        horseMoveList.add(new Move(+2, +2));
        horseMoveList.add(new Move(-4, -1));
        horseMoveList.add(new Move(-4, +1));
        horseMoveList.add(new Move(+4, -1));
        horseMoveList.add(new Move(+4, +1));
    }

    private Pair<Character, List<Integer>> createHorse(boolean isFirstMove, int number) {
        if (number == 1) {
            if (isFirstMove) {
                return new Pair('\u508C', Arrays.asList(3, 10));
            } else {
                return new Pair('\u99AC', Arrays.asList(3, 1));
            }
        } else if (number == 2) {
            if (isFirstMove) {
                return new Pair('\u508C', Arrays.asList(15, 10));
            } else {
                return new Pair('\u99AC', Arrays.asList(15, 1));
            }
        } else {
            return null;
        }
    }

    @Override
    protected void move(Player player) {
        moveHorse(player, true);
    }

    @Override
    protected void moveWithoutDisplay(Player player) {
        moveHorse(player, false);
    }

    private void moveHorse(Player player, boolean printMove) {
        int legalMoveCount = 0;
        Coordinate currentCoordinate = this.getCoordinate();
        for (int i = 0; i < horseMoveList.size(); i++) {
            int changeInX = horseMoveList.get(i).getChangeInX();
            int changeInY = horseMoveList.get(i).getChangeInY();
            Coordinate newCoordinate = currentCoordinate.changeCoordinate(changeInX, changeInY);
            int y = newCoordinate.getyCoordinate();
            int x = newCoordinate.getxCoordinate();

            int type = player.detectChess(newCoordinate);
            boolean withinChessBoard = ChessBoard.withinChessBoard(newCoordinate);

            if (withinChessBoard) {
                boolean detectHorseBlock = ChessBoard.detectHorseBlock(newCoordinate, currentCoordinate);
                if (!detectHorseBlock) {
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





