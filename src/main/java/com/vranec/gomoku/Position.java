package com.vranec.gomoku;

import java.util.Random;

import com.vranec.minimax.Color;

public class Position {
    private static final int[][] HASHES = new int[100][100];

    static {
        Random random = new Random();
        for (int x = 0; x < HASHES.length; x++) {
            for (int y = 0; y < HASHES[x].length; y++) {
                HASHES[x][y] = random.nextInt();
            }
        }
    }

    private final int x;
    private final int y;
    private final int hashCode;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.hashCode = HASHES[x][y];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
