package com.vranec.gomoku;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;

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
        int maxLength = getMoveLength(move);

        this.maxLineComputer = move.getColor() == Color.HUMAN ? from.maxLineComputer : Math
                .max(maxLength, from.maxLineComputer);
        this.maxLineHuman = move.getColor() == Color.COMPUTER ? from.maxLineHuman : Math.max(maxLength, from.maxLineHuman);
    }

    private int getMoveLength(GomokuMove move) {
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
        return maxLength;
    }

    public GomokuBoard(int width, int height, String... lines) {
        board = new Color[width][height];

        int maxLineComputer = 0;
        int maxLineHuman = 0;
        for (int y = 0; y < lines.length; y++) {
            for (int x = 0; x < lines[y].length(); x++) {
                switch (lines[y].charAt(x)) {
                case 'o':
                case 'O':
                case 'h':
                case 'H':
                    board[x][y] = Color.HUMAN;
                    maxLineHuman = Math.max(maxLineHuman, getMoveLength(new GomokuMove(x, y, Color.HUMAN)));
                    break;
                case 'x':
                case 'X':
                    board[x][y] = Color.COMPUTER;
                    maxLineComputer = Math.max(maxLineComputer, getMoveLength(new GomokuMove(x, y, Color.COMPUTER)));
                    break;
                case ' ':
                    break;
                default:
                    throw new IllegalArgumentException();
                }
            }
        }

        this.maxLineComputer = maxLineComputer;
        this.maxLineHuman = maxLineHuman;
    }

    private boolean insideBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    public boolean isGameOver() {
        return maxLineComputer >= 5 || maxLineComputer >= 5;
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
            return -getHumanBoardValue();
        case HUMAN:
            return getHumanBoardValue();
        default:
            throw new IllegalStateException();
        }
    }

    private int getHumanBoardValue() {
        if (maxLineHuman == 5) {
            return Integer.MAX_VALUE;
        }
        if (maxLineComputer == 5) {
            return -Integer.MAX_VALUE;
        }
        return maxLineHuman - maxLineComputer;
    }

    @Override
    public String toString() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(stream);
        output(out);
        try {
            return stream.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return super.toString();
        }
    }

    private void output(PrintStream out) {
        out.print(' ');
        for (int x = 0; x < getWidth(); x++) {
            out.print(x + 1);
        }
        out.println();

        for (int y = 0; y < getHeight(); y++) {
            out.print((char) ('a' + y));
            for (int x = 0; x < getWidth(); x++) {
                out.print(display(board[x][y]));
            }
            out.println();
        }
    }

    public void display() {
        output(System.out);
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

    public Iterable<Board> getNextBoards(final Color color) {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    private int currentX = -1;
                    private int currentY = 0;

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    public Board next() {
                        return new GomokuBoard(GomokuBoard.this, new GomokuMove(currentX, currentY, color));
                    }

                    public boolean hasNext() {
                        currentX++;
                        if (currentX >= getWidth()) {
                            currentX = 0;
                            currentY++;
                        }
                        for (; currentY < getHeight(); currentY++) {
                            for (; currentX < getWidth(); currentX++) {
                                if (board[currentX][currentY] == null) {
                                    return true;
                                }
                            }
                            currentX = 0;
                        }
                        return false;
                    }
                };
            }
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(board);
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
        GomokuBoard other = (GomokuBoard) obj;
        if (!Arrays.deepEquals(board, other.board))
            return false;
        return true;
    }
}
