package tictactoe.utils;

import tictactoe.GameChar;

public class TicTacGraphics {
    public static GameChar[][] createField(String config) {
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

    public static void drawField(GameChar[][] field) {
        System.out.println("---------");
        for (int y = 0; y < 3; y++) {
            System.out.print("| ");
            for (int x = 0; x < 3; x++) {
                GameChar symbol = field[y][x];
                if (symbol == GameChar.N) System.out.print("  ");
                else System.out.print(symbol.name() + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static void closeField(GameChar[][] field) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                field[y][x] = GameChar.X;
            }
        }
    }
}
