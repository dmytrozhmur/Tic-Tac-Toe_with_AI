package tictactoe;

import tictactoe.opponents.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Opponent firstOpponent;
    private static Opponent secondOpponent;

    public static void main(String[] args) throws IOException {
        // write your code here

        while (true) {
            while (!start()) {
            }
        }

    }

    static boolean start() throws IOException {
        System.out.print("Input command: > ");
        String command = reader.readLine();

        String[] params = command.split(" ");
        if (params[0].equals("exit")) System.exit(0);
        else if (params.length != 3 || !params[0].equals("start")) {
            System.out.println("Bad parameters!");
            return false;
        }

        Opponent.gameField = createField("_________");

        try {
            firstOpponent = createOpponent(params[1]);
            secondOpponent = createOpponent(params[2]);
        } catch (IllegalArgumentException iae) {
            System.out.println("Bad parameters!");
        }

        firstOpponent.setSign(GameChar.X);
        secondOpponent.setSign(GameChar.O);

        firstOpponent.setEnemy(secondOpponent);
        secondOpponent.setEnemy(firstOpponent);

        Opponent.drawField();
        play();
        return true;
    }

    static void play() {
        while (true) {
            while (!firstOpponent.makeMove()) {}
            if (Opponent.gameOver) break;
            while (!secondOpponent.makeMove()) {}
            if (Opponent.gameOver) break;
        }
    }

    private static Opponent createOpponent(String level) {
        switch (level) {
            case "user":
                return new Player(reader);
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

    static GameChar[][] createField(String config) {
        GameChar[][] gameField = new GameChar[3][3];
        config = config.replaceAll("_", GameChar.N.name());
        for (int y = 0, i = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++, i++) {
                char symbol = config.charAt(i);
                gameField[y][x] = GameChar.valueOf(symbol + "");
            }
        }
        return gameField;
    }
}
