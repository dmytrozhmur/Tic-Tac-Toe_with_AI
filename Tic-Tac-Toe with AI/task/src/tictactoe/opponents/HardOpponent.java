package tictactoe.opponents;

import tictactoe.GameChar;

import java.io.IOException;

public class HardOpponent extends Opponent {
    private GameChar aiPlayer;
    private GameChar huPlayer;

    @Override
    public boolean makeMove() {
        info();

        Move move = minimax(gameField, aiPlayer);
        gameField[move.coordinates[1]][move.coordinates[0]] = sign;
        drawField();

        try {
            gameOver = isGameOver(move.coordinates[0], move.coordinates[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    private Move minimax(GameChar[][] currState, GameChar player, int... lastMove) {
        if(lastMove.length != 0) {
            if(player == huPlayer && isVictory(lastMove[0], lastMove[1], aiPlayer, currState))
                return new Move(lastMove, 10);
            else if(player == aiPlayer && isVictory(lastMove[0], lastMove[1], huPlayer, currState))
                return new Move(lastMove, -10);
            else if(!isPlace(currState))
                return new Move(lastMove, 0);
        }

        Move[][] moves = new Move[3][3];

        for (short i = 0; i < 3; i++) {
            for (short j = 0; j < 3; j++) {
                if(currState[i][j] == GameChar.N) {
                    GameChar[][] reboard = copyBoard(currState);
                    reboard[i][j] = player;
                    if(player == aiPlayer) moves[i][j] = minimax(reboard, huPlayer, j, i);
                    else moves[i][j] = minimax(reboard, aiPlayer, j, i);
                }
            }
        }

        Move bestMove = null;
        if(player == aiPlayer) {
            int bestScore = -10000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Move move = moves[i][j];
                    if(move != null && bestScore < move.score) {
                        bestScore = move.score;
                        bestMove = move;
                        bestMove.coordinates = new int[] { j, i };
                    }
                }
            }
        } else {
            int bestScore = 10000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Move move = moves[i][j];
                    if(move != null && bestScore > move.score) {
                        bestScore = move.score;
                        bestMove = move;
                        bestMove.coordinates = new int[] { j, i };
                    }
                }
            }
        }

        return bestMove;
    }

    private static GameChar[][] copyBoard(GameChar[][] original) {
        GameChar[][] copy = new GameChar[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    @Override
    public void setEnemy(Opponent enemy) {
        super.setEnemy(enemy);
        aiPlayer = this.sign;
        huPlayer = enemy.sign;
    }

    private static class Move {
        int[] coordinates;
        int score;

        public Move(int[] coordinates, int score) {
            this.coordinates = coordinates;
            this.score = score;
        }
    }
}

