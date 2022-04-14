package tictactoe.opponents;

import tictactoe.GameChar;

import static tictactoe.utils.GameStatus.*;
import static tictactoe.utils.TicTacGraphics.*;

public class HardOpponent extends Opponent {
    private GameChar myChar;
    private GameChar enemyChar;

    @Override
    public boolean makeMove(GameChar[][] gameField) {
        info();

        Move move = minimax(gameField, myChar);
        gameField[move.coordinates[1]][move.coordinates[0]] = sign;
        drawField(gameField);

        over = isGameOver(move.coordinates[0], move.coordinates[1], gameField, sign);

        return true;
    }


    private Move minimax(GameChar[][] currState, GameChar player, int... lastMove) {
        if(lastMove.length != 0) {
            if(player == enemyChar && isVictory(lastMove[0], lastMove[1], myChar, currState))
                return new Move(lastMove, 10);
            else if(player == myChar && isVictory(lastMove[0], lastMove[1], enemyChar, currState))
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
                    if(player == myChar) moves[i][j] = minimax(reboard, enemyChar, j, i);
                    else moves[i][j] = minimax(reboard, myChar, j, i);
                }
            }
        }

        Move bestMove = null;
        if(player == myChar) {
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
        myChar = this.sign;
        enemyChar = enemy.sign;
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

