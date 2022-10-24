package tictactoe.utils;

import tictactoe.GameChar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static tictactoe.utils.TicTacGraphics.closeField;

public class GameStatus {
    public static boolean isVictory(int lastX, int lastY, GameChar opponentSign, GameChar[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][lastX] != opponentSign)
                break;
            if (i == 2)
                return true;
        }
        for (int i = 0; i < board[i].length; i++) {
            if (board[lastY][i] != opponentSign)
                break;
            if (i == 2)
                return true;
        }
        if ((lastX == 0 && lastY == 0) || (lastX == 2 && lastY == 2) || (lastX == 1 && lastY == 1)) {
            for (int y = 0, x = 0; y < 3; y++, x++) {
                if (board[y][x] != opponentSign)
                    break;
                if (x == 2)
                    return true;
            }
        }
        if ((lastX == 2 && lastY == 0) || (lastX == 0 && lastY == 2) || (lastX == 1 && lastY == 1)) {
            for (int y = 0, x = 2; y >= 0; y++, x--) {
                if (board[y][x] != opponentSign)
                    break;
                if (x == 0)
                    return true;
            }
        }
        return false;
    }

    public static boolean isPlace(GameChar[][] board) {
        for (GameChar[] row :
                board) {
            for (GameChar position :
                    row) {
                if (position == GameChar.N) return true;
            }
        }
        return false;
    }

    public static void isGameOver(int coordinateX, int coordinateY, GameChar[][] board, GameChar sign) {
        if(isVictory(coordinateX, coordinateY, sign, board)) {
            System.out.println(sign + " wins");
            System.out.println();
            closeField(board);
        } else if(!isPlace(board)) {
            System.out.println("Draw");
            System.out.println();
        }
    }

    public static short[] isVictoryPossible(GameChar[][] board, GameChar sign) {
        for (short i = 0; i < board.length; i++) {
            short[] missingHorizontal = null;
            short[] missingVertical = null;
            short counterX = 0;
            short counterY = 0;

            for (short j = 0; j < board.length; j++) {
                if(board[i][j] == sign) counterX++;
                else if(board[i][j] == GameChar.N) {
                    missingHorizontal = new short[2];
                    missingHorizontal[0] = j;
                    missingHorizontal[1] = i;
                }

                if(board[j][i] == sign) counterY++;
                else if(board[j][i] == GameChar.N) {
                    missingVertical = new short[2];
                    missingVertical[0] = i;
                    missingVertical[1] = j;
                }
            }

            if(counterX == 2 && missingHorizontal != null)
                return missingHorizontal;
            if(counterY == 2 && missingVertical != null)
                return missingVertical;
        }

        short counterLeftDiagonal = 0;
        short counterRightDiagonal = 0;
        short[] missingLeftDiagonal = null;
        short[] missingRightDiagonal = null;
        for (short i = 0, j = 0; i < board.length; i++, j++) {
            if(board[i][j] == sign) counterLeftDiagonal++;
            else if(board[i][j] == GameChar.N) {
                missingLeftDiagonal = new short[2];
                missingLeftDiagonal[0] = j;
                missingLeftDiagonal[1] = i;
            }

            if(board[i][2-j] == sign) counterRightDiagonal++;
            else if(board[i][2-j] == GameChar.N) {
                missingRightDiagonal = new short[2];
                missingRightDiagonal[0] = (short) (2-j);
                missingRightDiagonal[1] = i;
            }
        }

        if(counterLeftDiagonal == 2)
            return missingLeftDiagonal;
        if(counterRightDiagonal == 2)
            return missingRightDiagonal;
        return null;
    }

    public static String getUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        try {
            input = reader.readLine();
        } catch (IOException ioe) {
            System.out.println("Game crashed! Console doesn't work correctly!");
            System.exit(1);
        }
        return input;
    }
}
