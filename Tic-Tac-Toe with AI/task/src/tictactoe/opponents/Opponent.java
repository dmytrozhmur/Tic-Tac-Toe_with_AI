package tictactoe.opponents;

import tictactoe.GameChar;

import java.util.Locale;

public abstract class Opponent {
    protected GameChar sign;
    protected Opponent enemy;

    public abstract boolean makeMove(GameChar[][] gameField);

    void info() {
        System.out.printf(
                "Making move level \"%s\"",
                getClass().getSimpleName().replace("Opponent", "").toLowerCase(Locale.ROOT)
        );
        System.out.println();
    }

    public void setSign(GameChar sign) {
        this.sign = sign;
    }

    public void setEnemy(Opponent enemy) {
        this.enemy = enemy;
    }

}
