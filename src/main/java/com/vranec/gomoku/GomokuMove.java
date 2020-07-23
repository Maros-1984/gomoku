package com.vranec.gomoku;

import java.util.Objects;
import java.util.Random;

import com.vranec.minimax.Color;
import com.vranec.minimax.Move;

public class GomokuMove extends Position implements Move {
    private final Color color;

    public GomokuMove(String moveString) {
        this(moveString.charAt(1) - '1', moveString.charAt(0) - 'a', Color.HUMAN);
    }

    public GomokuMove(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
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

    @Override
    public String toString() {
        return "[" + getX() + "," + getY() + "]";
    }

}
