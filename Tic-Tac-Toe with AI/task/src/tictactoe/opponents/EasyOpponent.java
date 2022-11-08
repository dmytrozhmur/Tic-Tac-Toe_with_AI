package tictactoe.opponents;

import java.util.Random;
import tictactoe.enums.GameChar;

import static tictactoe.utils.GameStatus.*;
import static tictactoe.utils.TicTacGraphics.*;

public class EasyOpponent extends Opponent {
    protected static Random random;

    public EasyOpponent() {
        random = new Random();
    }

    @Override
    public boolean makeMove(GameChar[][] gameField) {
        short coordinateX = (short) random.nextInt(3);
        short coordinateY = (short) random.nextInt(3);
        if(gameField[coordinateY][coordinateX] == GameChar.N) {
            info();

            gameField[coordinateY][coordinateX] = sign;
            drawField(gameField);
            
            isGameOver(coordinateX, coordinateY, gameField, sign);

            return true;
        }

        return false;
    }
}
