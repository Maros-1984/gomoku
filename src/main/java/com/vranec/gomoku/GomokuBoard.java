package com.vranec.gomoku;

import com.vranec.minimax.Board;
import com.vranec.minimax.Color;

public class GomokuBoard implements Board {
    private final Color[][] board;
    private final int maxLineHuman;
    private final int maxLineComputer;

    public GomokuBoard(int width, int height) {
        board = new Color[width][height];
        maxLineComputer = maxLineHuman = 0;
    }

    public GomokuBoard(GomokuBoard from, GomokuMove move) {
        board = new Color[from.getWidth()][from.getHeight()];
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                board[x][y] = from.board[x][y];
            }
        }
        board[move.getX()][move.getY()] = move.getColor();
        int maxLength = 0;
        for (Direction direction : Direction.values()) {
            int x = move.getX();
            int y = move.getY();
            while (insideBoard(x, y) && board[x][y] == move.getColor()) {
                x = direction.applyToX(x);
                y = direction.applyToY(y);
            }
            direction = direction.opposite();
            x = direction.applyToX(x);
            y = direction.applyToY(y);
            int length = 0;
            while (insideBoard(x, y) && board[x][y] == move.getColor()) {
                x = direction.applyToX(x);
                y = direction.applyToY(y);
                length++;
            }
            if (length > maxLength) {
                maxLength = length;
            }
        }

        this.maxLineComputer = move.getColor() == Color.HUMAN ? from.maxLineComputer : Math
                .max(maxLength, from.maxLineComputer);
        this.maxLineHuman = move.getColor() == Color.COMPUTER ? from.maxLineHuman : Math.max(maxLength, from.maxLineHuman);
    }

    private boolean insideBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    public boolean isGameOver() {
        return false;
    }

    public int getWidth() {
        return board.length;
    }

    public int getHeight() {
        return board[0].length;
    }

    public int getBoardValue(Color color) {

        switch (color) {
        case COMPUTER:
            return maxLineComputer;
        case HUMAN:
            return maxLineHuman;
        default:
            throw new IllegalStateException();
        }
    }

    public void display() {
        for (int x = 0; x < getWidth(); x++) {
            System.out.print(' ');
            System.out.print(x + 1);
        }
        System.out.println();

        for (int y = 0; y < getHeight(); y++) {
            System.out.print((char) ('a' + y));
            for (int x = 0; x < getWidth(); x++) {
                System.out.print(display(board[x][y]));
            }
            System.out.println();
        }
    }

    private String display(Color color) {
        if (color == null) {
            return " ";
        }
        switch (color) {
        case COMPUTER:
            return "X";
        case HUMAN:
            return "O";
        default:
            throw new IllegalStateException();
        }
    }

    public Iterable<Board> getNextBoards(Color color) {
        // TODO Auto-generated method stub
        return null;
    }

}
