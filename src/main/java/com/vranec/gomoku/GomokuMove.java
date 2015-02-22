package com.vranec.gomoku;

import com.vranec.minimax.Color;

public class GomokuMove {
    private final Color color;
    private final int x;
    private final int y;

    public GomokuMove(String moveString) {
        this(moveString.charAt(1) - '1', moveString.charAt(0) - 'a', Color.HUMAN);
    }

    public GomokuMove(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
