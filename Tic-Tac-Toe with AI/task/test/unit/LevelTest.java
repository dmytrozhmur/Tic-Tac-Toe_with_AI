package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tictactoe.enums.Level;
import tictactoe.opponents.EasyOpponent;
import tictactoe.opponents.HardOpponent;
import tictactoe.opponents.MediumOpponent;
import tictactoe.opponents.Opponent;

public class LevelTest {
    @Test
    public void getEasyOpponent() {
        Opponent opponent = Level.valueOf("EASY").createOpponent();
        Assertions.assertInstanceOf(EasyOpponent.class, opponent);
    }

    @Test
    public void getMediumOpponent() {
        Opponent opponent = Level.valueOf("MEDIUM").createOpponent();
        Assertions.assertInstanceOf(MediumOpponent.class, opponent);
    }

    @Test
    public void getHardOpponent() {
        Opponent opponent = Level.valueOf("HARD").createOpponent();
        Assertions.assertInstanceOf(HardOpponent.class, opponent);
    }
}
