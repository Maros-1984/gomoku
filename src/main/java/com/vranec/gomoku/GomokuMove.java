package com.vranec.gomoku;

import java.util.Random;

import com.vranec.minimax.Color;

public class GomokuMove extends Position {
    private static final int[][] HUMAN_HASH = new int[100][100];
    private static final int[][] COMPUTER_HASH = new int[100][100];

    static {
        Random random = new Random();
        for (int x = 0; x < HUMAN_HASH.length; x++) {
            for (int y = 0; y < HUMAN_HASH[x].length; y++) {
                HUMAN_HASH[x][y] = random.nextInt();
                COMPUTER_HASH[x][y] = random.nextInt();
            }
        }
    }

    private final Color color;
    private final int hashCode;

    public GomokuMove(String moveString) {
        this(moveString.charAt(1) - '1', moveString.charAt(0) - 'a', Color.HUMAN);
    }

    public GomokuMove(int x, int y, Color color) {
        super(x, y);
        this.color = color;
        this.hashCode = (color == Color.HUMAN ? HUMAN_HASH[x][y] : COMPUTER_HASH[x][y]);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        GomokuMove other = (GomokuMove) obj;
        if (color != other.color)
            return false;
        return true;
    }
}
