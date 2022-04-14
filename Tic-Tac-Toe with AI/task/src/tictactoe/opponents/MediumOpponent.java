package tictactoe.opponents;

import tictactoe.GameChar;

import static tictactoe.utils.GameStatus.*;
import static tictactoe.utils.TicTacGraphics.*;

public class MediumOpponent extends EasyOpponent {

    @Override
    public boolean makeMove(GameChar[][] gameField) {
        short[] missingCoordinates;

        if((missingCoordinates = isVictoryPossible(gameField, sign)) != null) {
            info();

            gameField[missingCoordinates[1]][missingCoordinates[0]] = sign;
            drawField(gameField);

            over = true;

            System.out.println(sign + " wins");
            System.out.println();

            return true;
        }
        if((missingCoordinates = isVictoryPossible(gameField, enemy.sign)) != null) {
            info();

            gameField[missingCoordinates[1]][missingCoordinates[0]] = sign;
            drawField(gameField);

            over = !isPlace(gameField);

            return true;
        }

        return super.makeMove(gameField);
    }
}