package tictactoe.opponents;

import tictactoe.GameChar;

import java.io.IOException;
import java.util.Locale;

public abstract class Opponent {
    public static boolean gameOver = false;
    public static GameChar[][] gameField;
    protected GameChar sign;
    protected Opponent enemy;


    public abstract boolean makeMove();

    public static void drawField() {
        System.out.println("---------");
        for (int y = 0; y < 3; y++) {
            System.out.print("| ");
            for (int x = 0; x < 3; x++) {
                GameChar symbol = gameField[y][x];
                if (symbol == GameChar.N) System.out.print("  ");
                else System.out.print(symbol.name() + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    boolean isVictory(int lastX, int lastY, GameChar opponentSign, GameChar[][]... fields) {
        GameChar[][] board;
        if (fields.length == 0) board = gameField;
        else board = fields[0];

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

    static boolean isPlace(GameChar[][]... fields) {
        GameChar[][] board;
        if (fields.length == 0) board = gameField;
        else board = fields[0];

        for (GameChar[] row :
                board) {
            for (GameChar position :
                    row) {
                if (position == GameChar.N) return true;
            }
        }
        return false;
    }

    boolean isGameOver(int coordinateX, int coordinateY) throws IOException {
        if(isVictory(coordinateX, coordinateY, sign)) {
            System.out.println(sign + " wins");
            System.out.println();
            return true;
        } else if(!isPlace()) {
            System.out.println("Draw");
            System.out.println();
            return true;
        }
        return false;
    }

    short[] isVictoryPossible() {
        for (short i = 0; i < gameField.length; i++) {
            short[] missingHorizontal = null;
            short[] missingVertical = null;
            short counterX = 0;
            short counterY = 0;

            for (short j = 0; j < gameField.length; j++) {
                if(gameField[i][j] == this.sign) counterX++;
                else if(gameField[i][j] == GameChar.N) {
                    missingHorizontal = new short[2];
                    missingHorizontal[0] = j;
                    missingHorizontal[1] = i;
                }

                if(gameField[j][i] == this.sign) counterY++;
                else if(gameField[j][i] == GameChar.N) {
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
        for (short i = 0, j = 0; i < gameField.length; i++, j++) {
            if(gameField[i][j] == this.sign) counterLeftDiagonal++;
            else if(gameField[i][j] == GameChar.N) {
                missingLeftDiagonal = new short[2];
                missingLeftDiagonal[0] = j;
                missingLeftDiagonal[1] = i;
            }

            if(gameField[i][2-j] == sign) counterRightDiagonal++;
            else if(gameField[i][2-j] == GameChar.N) {
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

    void info() {
        System.out.printf(
                "Making move level \"%s\"",
                getClass().getSimpleName().replace("Opponent", "").toLowerCase(Locale.ROOT)
        );
        System.out.println();
    }

    public void setSign(GameChar sign) {
        this.sign = sign;
    }

    public void setEnemy(Opponent enemy) {
        this.enemy = enemy;
    }

}
