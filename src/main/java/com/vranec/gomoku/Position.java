package com.vranec.gomoku;

import java.util.Objects;
import java.util.Random;

import com.vranec.minimax.Color;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        return y == other.y;
    }
}
