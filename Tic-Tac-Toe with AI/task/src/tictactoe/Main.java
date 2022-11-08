package tictactoe;

import tictactoe.enums.GameChar;
import tictactoe.enums.Level;
import tictactoe.opponents.*;

import static tictactoe.utils.GameStatus.hasFreeSpace;
import static tictactoe.utils.UserInteraction.getUserInput;
import static tictactoe.utils.TicTacGraphics.*;

public class Main {
    private static Opponent firstOpponent;
    private static Opponent secondOpponent;

    public static void main(String[] args) throws NoSuchFieldException {
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
            firstOpponent = Level.valueOf(params[1].toUpperCase()).createOpponent();
            secondOpponent = Level.valueOf(params[2].toUpperCase()).createOpponent();
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
            if (!hasFreeSpace(board)) break;
            while (!secondOpponent.makeMove(board)) {}
            if (!hasFreeSpace(board)) break;
        }
    }
}
