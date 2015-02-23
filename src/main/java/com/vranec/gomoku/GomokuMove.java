package com.vranec.gomoku;

import java.util.Random;

import com.vranec.minimax.Color;

public class GomokuMove {
    private static final int[][] HUMAN_HASHES = new int[100][100];
    private static final int[][] COMPUTER_HASHES = new int[100][100];

    static {
        Random random = new Random();
        for (int x = 0; x < HUMAN_HASHES.length; x++) {
            for (int y = 0; y < HUMAN_HASHES[x].length; y++) {
                HUMAN_HASHES[x][y] = random.nextInt();
                COMPUTER_HASHES[x][y] = random.nextInt();
            }
        }
    }

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
        return color == Color.HUMAN ? HUMAN_HASHES[x][y] : COMPUTER_HASHES[x][y];
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
