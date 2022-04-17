package tictactoe;

import tictactoe.opponents.*;

import static tictactoe.utils.GameStatus.*;
import static tictactoe.utils.TicTacGraphics.*;

public class Main {
    private static Opponent firstOpponent;
    private static Opponent secondOpponent;

    public static void main(String[] args) {
        // write your code here

        while (true) {
            while (!start()) {}
        }
    }

    static boolean start() {
        System.out.print("Input command: > ");
        String command = getUserInput();

        GameChar[][] gameBoard = createField("_________");

        String[] params = command.split(" ");
        if (params[0].equals("exit")) System.exit(0);
        else if (params.length != 3 || !params[0].equals("start")) {
            System.out.println("Bad parameters!");
            return false;
        }

        try {
            firstOpponent = LEVEL.valueOf(params[1].toUpperCase()).createOpponent();
            secondOpponent = LEVEL.valueOf(params[2].toUpperCase()).createOpponent();
        } catch (Exception exc) {
            System.out.println("Bad parameters!");
            return false;
        }

        firstOpponent.setSign(GameChar.X);
        secondOpponent.setSign(GameChar.O);

        firstOpponent.setEnemy(secondOpponent);
        secondOpponent.setEnemy(firstOpponent);

        drawField(gameBoard);
        play(gameBoard);
        return true;
    }

    static void play(GameChar[][] board) {
        while (true) {
            while (!firstOpponent.makeMove(board)) {}
            if (!isPlace(board)) break;
            while (!secondOpponent.makeMove(board)) {}
            if (!isPlace(board)) break;
        }
    }
}
