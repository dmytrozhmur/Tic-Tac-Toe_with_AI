package tictactoe.opponents;

import tictactoe.GameChar;

import java.io.BufferedReader;
import java.io.IOException;

public class Player extends Opponent {
    BufferedReader reader;

    public Player(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public boolean makeMove() {
        System.out.print("Enter the coordinates: > ");
        String inputLine = null;
        try {
            inputLine = reader.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
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

        if((coordinateX < 0 || 2 < coordinateX) || (coordinateY < 0 || 2 < coordinateY)) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }

        if(gameField[coordinateY][coordinateX] != GameChar.N) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }

        gameField[coordinateY][coordinateX] = sign;
        drawField();
        try {
            gameOver = isGameOver(coordinateX, coordinateY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
