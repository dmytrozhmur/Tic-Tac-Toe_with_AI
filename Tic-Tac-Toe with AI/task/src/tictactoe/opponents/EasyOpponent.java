package tictactoe.opponents;

import tictactoe.GameChar;

import java.io.IOException;
import java.util.Random;

public class EasyOpponent extends Opponent {
    protected static Random random;

    public EasyOpponent() {
        random = new Random();
    }

    @Override
    public boolean makeMove() {
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
