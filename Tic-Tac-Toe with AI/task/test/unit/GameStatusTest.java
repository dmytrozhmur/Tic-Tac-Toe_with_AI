package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tictactoe.enums.GameChar;
import tictactoe.utils.GameStatus;
import tictactoe.utils.TicTacGraphics;

import java.util.Arrays;

public class GameStatusTest {
    private final GameChar[][] board = TicTacGraphics.createField("OOXOXX___");

    @Test
    public void victoryStatusCheck() {
        GameChar[][] board = this.board.clone();
        int victoryX = 0;
        int victoryY = 2;

        board[victoryY][victoryX] = GameChar.X;
        Assertions.assertTrue(GameStatus.isVictory(victoryX, victoryY, GameChar.X, board),
                Arrays.deepToString(board));
        Assertions.assertFalse(GameStatus.isVictory(victoryX, victoryY, GameChar.O, board),
                Arrays.deepToString(board));

        board[victoryY][victoryX] = GameChar.O;
        Assertions.assertTrue(GameStatus.isVictory(victoryX, victoryY, GameChar.O, board),
                Arrays.deepToString(board));
        Assertions.assertFalse(GameStatus.isVictory(victoryX, victoryY, GameChar.X, board),
                Arrays.deepToString(board));

        victoryX = 2;

        board[victoryY][victoryX] = GameChar.X;
        Assertions.assertTrue(GameStatus.isVictory(victoryX, victoryY, GameChar.X, board),
                Arrays.deepToString(board));

        victoryX = 1;

        Assertions.assertFalse(GameStatus.isVictory(victoryX, victoryY, GameChar.X, board),
                Arrays.deepToString(board));
        Assertions.assertFalse(GameStatus.isVictory(victoryX, victoryY, GameChar.O, board),
                Arrays.deepToString(board));
    }

    @Test
    public void possibleVictoryStatusCheck() {
        GameChar[][] board = this.board.clone();
        Assertions.assertNotNull(GameStatus.isVictoryPossible(board, GameChar.X),
                Arrays.deepToString(board));
        Assertions.assertNotNull(GameStatus.isVictoryPossible(board, GameChar.O),
                Arrays.deepToString(board));

        board[1][1] = GameChar.N;
        board[1][0] = GameChar.N;
        Assertions.assertNotNull(GameStatus.isVictoryPossible(board, GameChar.X),
                Arrays.deepToString(board));
        Assertions.assertNull(GameStatus.isVictoryPossible(board, GameChar.O),
                Arrays.deepToString(board));
    }

    @Test
    public void freeSpaceStatusCheck() {
        GameChar[][] board = this.board.clone();
        Assertions.assertTrue(GameStatus.hasFreeSpace(board));

        Arrays.fill(board[2], GameChar.O);
        Assertions.assertFalse(GameStatus.hasFreeSpace(board));
    }
}
