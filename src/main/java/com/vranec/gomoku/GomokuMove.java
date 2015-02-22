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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GomokuMove other = (GomokuMove) obj;
        if (color != other.color)
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
