
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private static Player player1;
    private static Player player2;
    private static String player1Name;
    private static String player2Name;
    private static ChessBoard chessBoard = new ChessBoard();
    private static boolean isEnd;
    private static Scanner sc = new Scanner(System.in);
    private static int chessChoice;
    private static int moveChoice;

    protected static void endGame() {
        Game.isEnd = true;
    }

    public static void startGame() {
        System.out.println("Game started");
        enterNames();
        enterChessChoice(player1, player1Name);
    }

    private static void createPlayers(String player1Name, String player2Name) {
        player1 = new Player(true, player1Name);
        player2 = new Player(false, player2Name);
        player1.setOpponentChessList(player2.getChessList());
        player2.setOpponentChessList(player1.getChessList());
    }

    protected static void printLegalMove(int legalMoveCount, Coordinate newCoordinate) {
        System.out.print(legalMoveCount + ". ");
        ChessBoard.printChessBoardCoordinate(newCoordinate);
    }

    protected static void printLegalMove(int legalMoveCount, Coordinate newCoordinate, char appearance) {
        System.out.print(legalMoveCount + ". ");
        ChessBoard.printChessBoardCoordinate(newCoordinate);
        System.out.println(" (Take down " + appearance + ")");
    }

    private static void printChessList(Player player) {
        ArrayList<Chess> chessList = player.getChessList();
        for (int i = 0; i < chessList.size(); i++) {
            System.out.print(i + 1 + "." + chessList.get(i).getAppearance() + " ");
            Coordinate chessCoordinate = chessList.get(i).getCoordinate();
            ChessBoard.printChessBoardCoordinate(chessCoordinate);
        }
    }

    protected static void declareWinner(String name) {
        System.out.println(name + " wins.");
        sc.close();
    }

    private static void enterNames() {
        do {
            System.out.println("Name of player who moves first:");
            do {
                player1Name = sc.nextLine();
            } while (player1Name.isEmpty());

            System.out.println("Name of another player:");
            do {
                System.out.println("(Make sure it's different from another player)");
                player2Name = sc.nextLine();
            } while (player2Name.isEmpty());

        } while (player1Name.equals(player2Name));
        createPlayers(player1Name, player2Name);
        chessBoard.createChessBoard(player1, player2);
    }

    private static void enterChessChoice(Player player, String playerName) {
        boolean continueInput = true;
        do {
            try {
                do {
                    System.out.println(playerName + "'s turn");
                    chessBoard.printBoard();
                    printChessList(player);
                    chessChoice = sc.nextInt();
                } while (chessChoice <= 0 || chessChoice > player.getChessList().size());
                continueInput = false;
            } catch (InputMismatchException ex) {
                sc.nextLine();
            }
        } while (continueInput);
        enterMoveChoice(player, playerName);
    }

    private static void enterMoveChoice(Player player, String playerName) {
        boolean continueInput = true;
        do {
            try {
                do {
                    System.out.println(playerName + "'s turn");
                    chessBoard.printBoard();
                    player.getChessList().get(chessChoice - 1).move(player);

                    System.out.println("Enter 0: go to the previous step");
                    moveChoice = sc.nextInt();
                    if (moveChoice == 0) {
                        enterChessChoice(player, playerName);
                    }
                    if (moveChoice < 0 || moveChoice > player.getLegalMoveList().size()) {
                        player.resetLegalMoveList();
                    }

                } while (moveChoice < 0 || moveChoice > player.getLegalMoveList().size());
                continueInput = false;
            } catch (InputMismatchException ex) {
                player.resetLegalMoveList();
                sc.nextLine();
            }
        } while(continueInput);
        Pair info = player.moveChess(chessChoice - 1, moveChoice - 1);
        chessBoard.printBoard();
        confirmMoveChoice(player, playerName, info);
    }

    private static void confirmMoveChoice(Player player, String playerName, Pair info) {
        int choice;
        do {
            System.out.println("Enter 0: go to the previous step");
            System.out.println("Enter 1: proceed");
            choice = sc.nextInt();
            if (choice == 0) {
                reverseMove(player, info);
                enterMoveChoice(player, playerName);
            }
        } while (choice != 0 && choice != 1);
        
        player.getChessList().get(chessChoice - 1).moveWithoutDisplay(player);
        player.updateChessInformation(chessChoice - 1, moveChoice - 1);
        player.detectCheckMate();

        if (isEnd) {
            declareWinner(playerName);
        } else {
            newTurn(player);
        }
    }

    protected static void reverseMove(Player player, Pair info) {
        int postMoveX = ((Coordinate) info.getValue()).getxCoordinate();
        int postMoveY = ((Coordinate) info.getValue()).getyCoordinate();
        char targetAppearance = (char) info.getKey();


        int preMoveX = player.getChessList().get(chessChoice - 1).getCoordinate().getxCoordinate();
        int preMoveY = player.getChessList().get(chessChoice - 1).getCoordinate().getyCoordinate();
        char chessChoiceAppearance = player.getChessList().get(chessChoice - 1).getAppearance();

        ChessBoard.replaceChess(postMoveX, postMoveY, targetAppearance);
        ChessBoard.replaceChess(preMoveX, preMoveY, chessChoiceAppearance);
    }

    private static void newTurn(Player player) {
        if (player.equals(player1)) {
            enterChessChoice(player2, player2Name);
        } else {
            enterChessChoice(player1, player1Name);
        }
    }
}

