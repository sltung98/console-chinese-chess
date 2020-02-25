import java.util.ArrayList;

public class ChessBoard {

    private static char[][] board = new char[11][21];
    private static int rightBound = 17;
    private static int leftBound = 1;
    private static int upperBound = 1;
    private static int lowerBound = 10;

    public static char[][] getBoard() {
        return board;
    }

    public static int getRightBound() {
        return rightBound;
    }

    public static int getLeftBound() {
        return leftBound;
    }

    public static int getUpperBound() {
        return upperBound;
    }

    public static int getLowerBound() {
        return lowerBound;
    }

    protected static void createChessBoard(Player player1, Player player2) {
        int count = 0;
        for (int j = 0; j < board[0].length - 3; j++) {
            for (int i = 0; i < board.length; i++) {
                if (j % 2 != 0) {
                    if (i == 5 || i == 6) {
                        board[i][j] = '〇';
                    } else {
                        board[i][j] = '口';
                    }
                } else {
                    board[i][j] = '-';
                }
                if (i == 0) {
                    if (j % 2 != 0) {
                        board[i][j] = (char) ('A' + count);
                        count++;
                    } else {
                        board[i][j] = '―';
                    }
                } else if (j == 0) {
                    board[i][j] = (char) (i - 1 + '0');
                }
                if (i == 2 || i == 3 || i == 8 || i == 9) {
                    if (j == 7 || j == 9 || j == 11) {
                        board[i][j] = '〇';
                    }
                }
            }
        }

        ArrayList<Chess> player1ChessList = player1.getChessList();
        ArrayList<Chess> player2ChessList = player2.getChessList();
        ArrayList<Chess> chessList = player1ChessList;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < chessList.size(); j++) {
                int x = chessList.get(j).getCoordinate().getxCoordinate();
                int y = chessList.get(j).getCoordinate().getyCoordinate();
                char c = chessList.get(j).getAppearance();
                board[y][x] = c;
            }
            chessList = player2ChessList;
        }
        board[5][18] = ' ';
        board[6][18] = ' ';
        board[5][19] = '楚';
        board[5][20] = '河';
        board[6][19] = '漢';
        board[6][20] = '界';
    }

    protected static void printBoard() {
        for (int j = 0; j < board.length; j++) {
            System.out.println(board[j]);
        }
    }


    public static boolean withinChessBoard(Coordinate newCoordinate) {
        int yCoordinate = newCoordinate.getyCoordinate();
        int xCoordinate = newCoordinate.getxCoordinate();
        if (yCoordinate >= upperBound && yCoordinate <= lowerBound && xCoordinate >= leftBound && xCoordinate <= rightBound) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean withinPalace(Player player, Coordinate newCoordinate) {
        int yCoordinate = newCoordinate.getyCoordinate();
        int xCoordinate = newCoordinate.getxCoordinate();
        if (xCoordinate >= 7 && xCoordinate <= 11) {
            if (player.isFirstMove()) {
                if (yCoordinate >= 8 && yCoordinate <= 10) {
                    return true;
                }
            } else {
                if (yCoordinate >= 1 && yCoordinate <= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean detectHorseBlock(Coordinate newCoordinate, Coordinate currentCoordinate) {
        int newxCoordinate = newCoordinate.getxCoordinate();
        int newyCoordinate = newCoordinate.getyCoordinate();
        int currentxCoordinate = currentCoordinate.getxCoordinate();
        int currentyCoordinate = currentCoordinate.getyCoordinate();
        int midPointyCoordinate = (newyCoordinate + currentyCoordinate) / 2;
        int midPointxCoordinate = (newxCoordinate + currentxCoordinate) / 2;

        if (Math.abs((newyCoordinate - currentyCoordinate)) ==
                Math.abs(newxCoordinate - currentxCoordinate)) {
            if (board[midPointyCoordinate][currentxCoordinate] == '口' ||
                    board[midPointyCoordinate][currentxCoordinate] == '〇') {
                return false;
            }
        } else {
            if (board[currentyCoordinate][midPointxCoordinate] == '口' ||
                    board[currentyCoordinate][midPointxCoordinate] == '〇') {
                return false;
            }
        }
        return true;

    }

    public static boolean detectElephantBlock(Coordinate newCoordinate, Coordinate currentCoordinate) {
        int newxCoordinate = newCoordinate.getxCoordinate();
        int newyCoordinate = newCoordinate.getyCoordinate();
        int currentxCoordinate = currentCoordinate.getxCoordinate();
        int currentyCoordinate = currentCoordinate.getyCoordinate();
        int midPointyCoordinate = (newyCoordinate + currentyCoordinate) / 2;
        int midPointxCoordinate = (newxCoordinate + currentxCoordinate) / 2;
        if (board[midPointyCoordinate][midPointxCoordinate] == '口' ||
                board[midPointyCoordinate][midPointxCoordinate] == '〇') {
            return false;
        }
        return true;
    }

    public static boolean withinElephantMoveRange(Player player, Coordinate newCoordinate) {
        int yCoordinate = newCoordinate.getyCoordinate();
        int xCoordinate = newCoordinate.getxCoordinate();
        if (xCoordinate >= leftBound && xCoordinate <= rightBound) {
            if (player.isFirstMove()) {
                if (yCoordinate >= 6 && yCoordinate <= 10) {
                    return true;
                }
            } else {
                if (yCoordinate >= 1 && yCoordinate <= 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printChessBoardCoordinate(Coordinate coordinate) {
        int xCoordinate = coordinate.getxCoordinate();
        int yCoordinate = coordinate.getyCoordinate();
        int chessBoardyCoordinate = yCoordinate - 1;
        char chessBoardxCoordinate = 0;
        int x = 0;
        for (int i = 1; i <= xCoordinate; i += 2) {
            chessBoardxCoordinate = (char) ('A' + x);
            x++;
        }
        System.out.println(chessBoardxCoordinate + "" + chessBoardyCoordinate);
    }

    public static void eraseChess(int x, int y) {
        if (y == 1 || y == 2 || y == 3 || y == 8 || y == 9 || y == 10) {
            if (x == 7 || x == 9 || x == 11) {
                board[y][x] = '〇';
            } else {
                board[y][x] = '口';
            }
        } else if (y == 5 || y == 6) {
            board[y][x] = '〇';
        } else {
            board[y][x] = '口';
        }
    }


    public static void replaceChess(int newX, int newY, char appearance) {
        board[newY][newX] = appearance;
    }

    public static boolean isCrossBridge(boolean isFirstMove, int yCoordinate) {
        if ((isFirstMove && yCoordinate <= 5) || (!isFirstMove && yCoordinate >= 6)) {
            return true;
        } else {
            return false;
        }
    }

}
