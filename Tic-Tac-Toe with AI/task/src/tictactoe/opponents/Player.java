package tictactoe.opponents;

import tictactoe.GameChar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static tictactoe.utils.GameStatus.*;
import static tictactoe.utils.TicTacGraphics.*;

public class Player extends Opponent {
    @Override
    public boolean makeMove(GameChar[][] gameField) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the coordinates: > ");
        String inputLine;
        try {
            inputLine = reader.readLine();
        } catch (IOException ioe) {
            return false;
        }

        int coordinateX;
        int coordinateY;

        try {
            String[] userInput = inputLine.split(" ");
            coordinateY = Integer.parseInt(userInput[0]) - 1;
            coordinateX = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException nfe) {
            System.out.println("You should enter 2 numbers!");
            return false;
        }

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

        over = isGameOver(coordinateX, coordinateY, gameField, sign);

        return true;
    }

    private boolean isCoordinateWrong(int coordinate) {
        return coordinate < 0 || 2 < coordinate;
    }
}
