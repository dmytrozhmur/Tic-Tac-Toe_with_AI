package tictactoe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInteraction {
    public static String getUserInput() {
        String input = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            input = reader.readLine();
        } catch (IOException ioe) {
            System.out.println("Game crashed! Console doesn't work correctly!");
            System.exit(1);
        }
        return input;
    }
}

