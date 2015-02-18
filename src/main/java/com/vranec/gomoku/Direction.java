package com.vranec.gomoku;

public enum Direction {
    EAST(1, 0, "WEST"), SOUTHEAST(1, 1, "NORTHWEST"), SOUTH(0, 1, "NORTH"), SOUTHWEST(-1, 1, "NORTHEAST"), WEST(-1, 0, "EAST"), NORTHWEST(
            -1, -1, "SOUTHEAST"), NORTH(0, -1, "SOUTH"), NORTHEAST(1, -1, "SOUTHWEST");

    private final int xOffset;
    private final int yOffset;
    private final String oppositeName;
    private Direction opposite;

    private Direction(int xOffset, int yOffset, String opposite) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.oppositeName = opposite;
    }

    public int applyToX(int x) {
        return x + xOffset;
    }

    public int applyToY(int y) {
        return y + yOffset;
    }

    public Direction opposite() {
        if (opposite == null) {
            opposite = Direction.valueOf(oppositeName);
        }
        return opposite;
    }
}
