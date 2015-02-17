package com.vranec.gomoku;

import com.vranec.minimax.Board;
import com.vranec.minimax.Color;
import com.vranec.minimax.Move;

public class GomokuBoard implements Board {
    private int blackValue = 0;
    private int whiteValue = 0;

    public boolean isGameOver() {
        return false;
    }

    public int getBoardValue(Color color) {
        switch (color) {
        case BLACK:
            return blackValue;
        case WHITE:
            return whiteValue;
        default:
            throw new IllegalStateException();
        }
    }

    public Iterable<Move> getNextMoves(Color color) {
        // TODO Auto-generated method stub
        return null;
    }

    public void perform(Move move) {
        // TODO Auto-generated method stub

    }

    public void undo(Move move) {
        // TODO Auto-generated method stub

    }

    public void display() {
        // TODO Auto-generated method stub

    }

}
