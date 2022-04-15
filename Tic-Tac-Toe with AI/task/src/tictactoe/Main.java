package tictactoe;

import tictactoe.opponents.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import tictactoe.utils.GameStatus;

import static tictactoe.utils.TicTacGraphics.*;

public class Main {
    private static Opponent firstOpponent;
    private static Opponent secondOpponent;

    public static void main(String[] args) {
        // write your code here

        while (true) {
            try {
                while (!start()) {}
            } catch (IOException ioe) {
                System.out.println("Game crashed! Check integrity of files!");
                return;
            }
        }
    }

    static boolean start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        GameStatus.over = false;

        System.out.print("Input command: > ");

        GameChar[][] gameBoard = createField("_________");
        String command = reader.readLine();

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
            if (GameStatus.over) break;
            while (!secondOpponent.makeMove(board)) {}
            if (GameStatus.over) break;
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
}
