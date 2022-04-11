package tictactoe.opponents;

public class MediumOpponent extends EasyOpponent {

    @Override
    public boolean makeMove() {
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