package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tictactoe.enums.GameChar;
import tictactoe.opponents.EasyOpponent;
import tictactoe.opponents.HardOpponent;
import tictactoe.opponents.MediumOpponent;
import tictactoe.opponents.Opponent;
import tictactoe.utils.GameStatus;
import tictactoe.utils.TicTacGraphics;

public class OpponentTest {
    private Opponent opponent;

    @Test
    public void makeEasyMove() {
        opponent = new EasyOpponent();
        opponent.setSign(GameChar.X);
        GameChar[][] board = TicTacGraphics.createField("OXOOXXO_X");

        while (!opponent.makeMove(board)) {}
        Assertions.assertFalse(GameStatus.hasFreeSpace(board));
    }

    @Test
    public void makeMediumMove() {
        opponent = new MediumOpponent();
        opponent.setSign(GameChar.O);
        GameChar[][] board = TicTacGraphics.createField("XOXXOO___");

        opponent.makeMove(board);
        Assertions.assertFalse(GameStatus.hasFreeSpace(board));
    }

    @Test
    public void makeHardMove() {
        opponent = new HardOpponent();
        opponent.setSign(GameChar.O);
        GameChar[][] board = TicTacGraphics.createField("XO__OX_XO");

        opponent.makeMove(board);
        short[] victoryMove = GameStatus.isVictoryPossible(board, GameChar.O);
        Assertions.assertEquals(0, victoryMove[0]);
        Assertions.assertEquals(2, victoryMove[1]);
    }
}
