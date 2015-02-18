package com.vranec.gomoku;

import com.vranec.minimax.Color;

public class GomokuMove {
    private final Color color;
    private final int x;
    private final int y;

    public GomokuMove(String moveString) {
        this.color = Color.HUMAN;
        y = moveString.charAt(0) - 'a';
        x = moveString.charAt(1) - '1';
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
