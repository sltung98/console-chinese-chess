import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class Soldier extends Chess {

    protected Soldier(boolean isFirstMove, int number) {
        Pair<Character, List<Integer>> info = createSoldier(isFirstMove, number);
        updateChess(number, info.getKey(), info.getValue().get(0), info.getValue().get(1));
    }


    private Pair<Character, List<Integer>> createSoldier(boolean isFirstMove, int number) {

        if (!isFirstMove) {
            switch (number) {
                case 1:
                    return new Pair('卒', Arrays.asList(1, 4));
                case 2:
                    return new Pair('卒', Arrays.asList(5, 4));
                case 3:
                    return new Pair('卒', Arrays.asList(9, 4));
                case 4:
                    return new Pair('卒', Arrays.asList(13, 4));
                case 5:
                    return new Pair('卒', Arrays.asList(17, 4));
                default:
                    return null;
            }
        } else {
            switch (number) {
                case 1:
                    return new Pair('兵', Arrays.asList(1, 7));
                case 2:
                    return new Pair('兵', Arrays.asList(5, 7));
                case 3:
                    return new Pair('兵', Arrays.asList(9, 7));
                case 4:
                    return new Pair('兵', Arrays.asList(13, 7));
                case 5:
                    return new Pair('兵', Arrays.asList(17, 7));
                default:
                    return null;
            }
        }

    }

    @Override
    protected void move(Player player) {
        moveSoldier(player, true);
    }

    @Override
    protected void moveWithoutDisplay(Player player) {
        moveSoldier(player, false);
    }

    private void moveSoldier(Player player, boolean printMove) {
        int xCoordinate = this.getCoordinate().getxCoordinate();
        int yCoordinate = this.getCoordinate().getyCoordinate();
        int legalMoveCount = 0;
        int newxCoordinate = xCoordinate;
        int newyCoordinate = yCoordinate - 1;
        int i = 0;
        while (i != -1) {
            if (i == 0) {
                if (!player.isFirstMove()) {
                    newyCoordinate = yCoordinate + 1;
                }
            }

            if (i >= 1) {
                boolean crossBridge = ChessBoard.isCrossBridge(player.isFirstMove(),
                        yCoordinate);
                if (!crossBridge) {
                    i = -1;
                } else if (i == 1) {
                    newxCoordinate = xCoordinate - 2;
                    newyCoordinate = yCoordinate;
                } else if (i == 2) {
                    newxCoordinate = xCoordinate + 2;
                    i = -2;
                }
            }
            Coordinate newCoordinate = new Coordinate(newxCoordinate, newyCoordinate);

            if (i != -1) {
                int type = player.detectChess(newCoordinate);
                boolean withinChessBoard = ChessBoard.withinChessBoard(newCoordinate);
                if (withinChessBoard) {
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
                            char appearance = ChessBoard.getBoard()[newyCoordinate][newxCoordinate];
                            Game.printLegalMove(legalMoveCount, newCoordinate, appearance);
                        }
                    }
                }
                i++;
            }
        }
    }


}



