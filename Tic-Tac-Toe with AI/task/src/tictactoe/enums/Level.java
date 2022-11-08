package tictactoe.enums;

import tictactoe.opponents.*;

public enum Level {
    EASY {
        @Override
        public Opponent createOpponent() {
            return new EasyOpponent();
        }
    },

    MEDIUM {
        @Override
        public Opponent createOpponent() {
            return new MediumOpponent();
        }
    },

    HARD {
        @Override
        public Opponent createOpponent() {
            return new HardOpponent();
        }
    },

    USER {
        @Override
        public Opponent createOpponent() {
            return new Player();
        }
    };

    public abstract Opponent createOpponent();
}
