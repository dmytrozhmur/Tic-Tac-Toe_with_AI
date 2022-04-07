package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static GameChar[][] gameField;
    //private static GameChar userChar;
    private static boolean gameOver = false;
    private static Opponent firstOpponent;
    private static Opponent secondOpponent;

    public static void main(String[] args) throws IOException {
        // write your code here

        while (true) {
            while (!start()) {}
        }

    }

    static boolean start() throws IOException {
        System.out.print("Input command: > ");
        String command = reader.readLine();

        String[] params = command.split(" ");
        if(params[0].equals("exit")) System.exit(0);
        else if(params.length != 3 || !params[0].equals("start")) {
            System.out.println("Bad parameters!");
            return false;
        }

        try {
            firstOpponent = createOpponent(params[1]);
            secondOpponent = createOpponent(params[2]);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad parameters!");
        }

        firstOpponent.sign = GameChar.X;
        secondOpponent.sign = GameChar.O;

        firstOpponent.enemy = secondOpponent;
        secondOpponent.enemy = firstOpponent;

        firstOpponent.setUp();
        secondOpponent.setUp();

        createField("_________");
//        defineUserChar(initialState);

        drawField();
        play();
        return true;
    }



    static void play() {
        while (true) {

            while (!firstOpponent.makeMove()) {}
            if(gameOver) break;
            while (!secondOpponent.makeMove()) {}
            if(gameOver) break;

        }
    }

    private static Opponent createOpponent(String level) {
        switch (level) {
            case "user":
                return new Player();
            case "easy":
                return new EasyOpponent();
            case "medium":
                return new MediumOpponent();
            case "hard":
                return new HardOpponent();
            default:
                throw new IllegalArgumentException();
        }
    }

    static void createField(String config) {
        gameField = new GameChar[3][3];
        config = config.replaceAll("_", GameChar.N.name());
        for (int y = 0, i = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++, i++) {
                char symbol = config.charAt(i);
                gameField[y][x] = GameChar.valueOf(symbol + "");
            }
        }
    }

    static void drawField() {
        System.out.println("---------");
        for (int y = 0; y < 3; y++) {
            System.out.print("| ");
            for (int x = 0; x < 3; x++) {
                GameChar symbol = gameField[y][x];
                if(symbol == GameChar.N) System.out.print("  ");
                else System.out.print(symbol.name() + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

//    static void defineUserChar(String config) {
//        int countX = 0;
//        int countO = 0;
//
//        for (char c:
//             config.toCharArray()) {
//            if(c == 'X') countX++;
//            else if(c == 'O') countO++;
//        }
//
//        if(countO < countX)
//            userChar = GameChar.O;
//        else
//            userChar = GameChar.X;
//    }

    static boolean isVictory(int lastX, int lastY, GameChar opponentSign, GameChar[][]... fields) {
        GameChar[][] board;
        if(fields.length == 0) board = gameField;
        else board = fields[0];

        for (int i = 0; i < board.length; i++) {
            if(board[i][lastX] != opponentSign)
                break;
            if(i == 2)
                return true;
        }
        for (int i = 0; i < board[i].length; i++) {
            if(board[lastY][i] != opponentSign)
                break;
            if(i == 2)
                return true;
        }
        if((lastX == 0 && lastY == 0) || (lastX == 2 && lastY == 2) || (lastX == 1 && lastY == 1)) {
            for (int y = 0, x = 0; y < 3; y++, x++) {
                if(board[y][x] != opponentSign)
                    break;
                if(x == 2)
                    return true;
            }
        }
        if((lastX == 2 && lastY == 0) || (lastX == 0 && lastY == 2) || (lastX == 1 && lastY == 1)) {
            for (int y = 0, x = 2; y >= 0; y++, x--) {
                if(board[y][x] != opponentSign)
                    break;
                if(x == 0)
                    return true;
            }
        }
        return false;
    }

    static boolean isPlace(GameChar[][]... fields) {
        GameChar[][] board;
        if(fields.length == 0) board = gameField;
        else board = fields[0];

        for (GameChar[] row:
             board) {
            for (GameChar position:
                 row) {
                if(position == GameChar.N) return true;
            }
        }
        return false;
    }

    private enum GameChar {
        X, O, N
    }

    private static abstract class Opponent {
        static Random random;
        GameChar sign;
        Opponent enemy;

        abstract boolean makeMove();

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

        void setUp() {}
    }

    private static class Player extends Opponent {

        @Override
        boolean makeMove() {
            System.out.print("Enter the coordinates: > ");
            String inputLine = null;
            try {
                inputLine = reader.readLine();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            String[] userInput = inputLine.split(" ");
            int coordinateX;
            int coordinateY;

            try {
                coordinateY = Integer.parseInt(userInput[0]) - 1;
                coordinateX = Integer.parseInt(userInput[1]) - 1;
            } catch (NumberFormatException nfe) {
                System.out.println("You should enter numbers!");
                return false;
            }

            if((coordinateX < 0 || 2 < coordinateX) || (coordinateY < 0 || 2 < coordinateY)) {
                System.out.println("Coordinates should be from 1 to 3!");
                return false;
            }

            if(gameField[coordinateY][coordinateX] != GameChar.N) {
                System.out.println("This cell is occupied! Choose another one!");
                return false;
            }

            gameField[coordinateY][coordinateX] = sign;
            drawField();
            try {
                gameOver = isGameOver(coordinateX, coordinateY);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private static class EasyOpponent extends Opponent {


        public EasyOpponent() {
            random = new Random();
        }

        @Override
        boolean makeMove() {
            short coordinateX = (short) random.nextInt(3);
            short coordinateY = (short) random.nextInt(3);
            if(gameField[coordinateY][coordinateX] == GameChar.N) {
                info();

                gameField[coordinateY][coordinateX] = sign;
                drawField();

                try {
                    gameOver = isGameOver(coordinateX, coordinateY);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }

            return false;
        }
    }

    private static class MediumOpponent extends EasyOpponent {

        @Override
        boolean makeMove() {
            short[] missingCoordinates;

            if((missingCoordinates = isVictoryPossible()) != null) {
                info();

                gameField[missingCoordinates[1]][missingCoordinates[0]] = sign;
                drawField();

                gameOver = true;

                System.out.println(sign + " wins");
                System.out.println();

                return true;
            }
            if((missingCoordinates = enemy.isVictoryPossible()) != null) {
                info();

                gameField[missingCoordinates[1]][missingCoordinates[0]] = sign;
                drawField();

                gameOver = !isPlace();

                return true;
            }

            return super.makeMove();
        }
    }

    private static class HardOpponent extends Opponent {
        GameChar aiPlayer;
        GameChar huPlayer;

        @Override
        boolean makeMove() {
            info();

            Move move = minimax(gameField, aiPlayer);
            gameField[move.coordinates[1]][move.coordinates[0]] = sign;
            drawField();

            try {
                gameOver = isGameOver(move.coordinates[0], move.coordinates[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;

        }


        Move minimax(GameChar[][] currState, GameChar player, int... lastMove) {
            if(lastMove.length != 0) {
                if(player == huPlayer && isVictory(lastMove[0], lastMove[1], aiPlayer, currState))
                    return new Move(lastMove, 10);
                else if(player == aiPlayer && isVictory(lastMove[0], lastMove[1], huPlayer, currState))
                    return new Move(lastMove, -10);
                else if(!isPlace(currState))
                    return new Move(lastMove, 0);
            }



            Move[][] moves = new Move[3][3];

            for (short i = 0; i < 3; i++) {
                for (short j = 0; j < 3; j++) {
                    if(currState[i][j] == GameChar.N) {
                        GameChar[][] reboard = copyBoard(currState);
                        reboard[i][j] = player;
                        if(player == aiPlayer) moves[i][j] = minimax(reboard, huPlayer, j, i);
                        else moves[i][j] = minimax(reboard, aiPlayer, j, i);
                    }
                }
            }

            Move bestMove = null;
            if(player == aiPlayer) {
                int bestScore = -10000;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Move move = moves[i][j];
                        if(move != null && bestScore < move.score) {
                            bestScore = move.score;
                            bestMove = move;
                            bestMove.coordinates = new int[] { j, i };
                        }
                    }
                }
            } else {
                int bestScore = 10000;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Move move = moves[i][j];
                        if(move != null && bestScore > move.score) {
                            bestScore = move.score;
                            bestMove = move;
                            bestMove.coordinates = new int[] { j, i };
                        }
                    }
                }
            }

            return bestMove;
        }

        private static GameChar[][] copyBoard(GameChar[][] original) {
            GameChar[][] copy = new GameChar[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    copy[i][j] = original[i][j];
                }
            }
            return copy;
        }

        static int[] getLastSpot(GameChar[][] board) {
            int[] lastCoordinates = null;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(board[i][j] == GameChar.N) {
                        if(lastCoordinates != null) return null;
                        lastCoordinates = new int[2];
                        lastCoordinates[0] = j;
                        lastCoordinates[1] = i;
                    }
                }
            }
            return lastCoordinates;
        }

        @Override
        void setUp() {
            aiPlayer = this.sign;
            huPlayer = enemy.sign;
        }

        private static class Move {
            int[] coordinates;
            int score;

            public Move(int[] coordinates, int score) {
                this.coordinates = coordinates;
                this.score = score;
            }
        }
    }

}
