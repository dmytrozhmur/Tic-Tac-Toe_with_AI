package tictactoe.opponents;

import tictactoe.enums.GameChar;

import static tictactoe.utils.GameStatus.*;
import static tictactoe.utils.TicTacGraphics.*;

public class MediumOpponent extends EasyOpponent {

    @Override
    public boolean makeMove(GameChar[][] gameField) {
        short[] missingCoordinates;

        if((missingCoordinates = isVictoryPossible(gameField, sign)) != null) {
            info();

            int x = missingCoordinates[0];
            int y = missingCoordinates[1];
            gameField[y][x] = sign;
            drawField(gameField);

            isGameOver(x, y, gameField, sign);

            System.out.println(sign + " wins");
            System.out.println();

            return true;
        }
        if((missingCoordinates = isVictoryPossible(gameField, enemy.sign)) != null) {
            info();

            int x = missingCoordinates[0];
            int y = missingCoordinates[1];
            gameField[y][x] = sign;
            drawField(gameField);

            isGameOver(x, y, gameField, sign);

            return true;
        }

        return super.makeMove(gameField);
    }
}