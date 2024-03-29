package tictactoe.opponents;

import tictactoe.enums.GameChar;

import static tictactoe.utils.GameStatus.*;
import static tictactoe.utils.TicTacGraphics.*;
import static tictactoe.utils.UserInteraction.getUserInput;

public class Player extends Opponent {
    @Override
    public boolean makeMove(GameChar[][] gameField) {
        System.out.print("Enter the coordinates: > ");
        String coordinates = getUserInput();

        int coordinateX;
        int coordinateY;

        try {
            String[] userInput = coordinates.split(" ");
            coordinateY = Integer.parseInt(userInput[0]) - 1;
            coordinateX = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException nfe) {
            System.out.println("You should enter 2 numbers!");
            return false;
        }

        return setMove(gameField, coordinateX, coordinateY);
    }

    private boolean setMove(GameChar[][] gameField, int coordinateX, int coordinateY) {
        if(isCoordinateWrong(coordinateX) || isCoordinateWrong(coordinateY)) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        if(gameField[coordinateY][coordinateX] != GameChar.N) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }

        gameField[coordinateY][coordinateX] = sign;
        drawField(gameField);

        isGameOver(coordinateX, coordinateY, gameField, sign);

        return true;
    }

    private boolean isCoordinateWrong(int coordinate) {
        return coordinate < 0 || 2 < coordinate;
    }
}
