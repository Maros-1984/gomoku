package com.vranec.gomoku;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.vranec.minimax.Board;
import com.vranec.minimax.Color;
import com.vranec.minimax.Move;

public class GomokuBoard implements Board<GomokuMove> {
    private final Color[][] board;
    private int maxLineHuman;
    private int maxLineComputer;
    private int humanTotalX;
    private int humanTotalY;
    private int humanTotalCount;
    private int computerTotalX;
    private int computerTotalY;
    private int computerTotalCount;
    private final Set<GomokuMove> movesSoFar = new HashSet<GomokuMove>();

    public GomokuBoard(int width, int height) {
        board = new Color[width][height];
        maxLineComputer = maxLineHuman = 0;
        humanTotalCount = humanTotalX = humanTotalY = 0;
        computerTotalCount = computerTotalX = computerTotalY = 0;
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
        int humanTotalCount = 0, computerTotalCount = 0, humanTotalX = 0, humanTotalY = 0, computerTotalX = 0, computerTotalY = 0;
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
                    GomokuMove move = new GomokuMove(x, y, Color.HUMAN);
                    maxLineHuman = Math.max(maxLineHuman, getMoveLength(move));
                    humanTotalCount++;
                    humanTotalX += x;
                    humanTotalY += y;
                    break;
                case 'x':
                case 'X':
                    board[x][y] = Color.COMPUTER;
                    GomokuMove move2 = new GomokuMove(x, y, Color.COMPUTER);
                    maxLineComputer = Math.max(maxLineComputer, getMoveLength(move2));
                    computerTotalCount++;
                    computerTotalX += x;
                    computerTotalY += y;
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
        this.humanTotalCount = humanTotalCount;
        this.humanTotalX = humanTotalX;
        this.humanTotalY = humanTotalY;
        this.computerTotalCount = computerTotalCount;
        this.computerTotalX = computerTotalX;
        this.computerTotalY = computerTotalY;
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
        int middleX = board.length / 2;
        int middleY = board[0].length / 2;
        int humanDistanceFromMiddle2 = 0;
        if (humanTotalCount > 0) {
            humanDistanceFromMiddle2 = (middleX - humanTotalX / humanTotalCount) * (middleX - humanTotalX / humanTotalCount)
                    + (middleY - humanTotalY / humanTotalCount) * (middleY - humanTotalY / humanTotalCount);
        }
        int computerDistanceFromMiddle2 = 0;
        if (computerTotalCount > 0) {
            computerDistanceFromMiddle2 = (middleX - computerTotalX / computerTotalCount)
                    * (middleX - computerTotalX / computerTotalCount) + (middleY - computerTotalY / computerTotalCount)
                    * (middleY - computerTotalY / computerTotalCount);
        }
        return computerDistanceFromMiddle2 - humanDistanceFromMiddle2;
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

    public Iterable<GomokuMove> getNextBoards(final Color color) {
        Set<GomokuMove> possibleMoves = new LinkedHashSet<>();
        if (board[board.length / 2][board[0].length / 2] == null) {
            possibleMoves.add(new GomokuMove(board.length / 2, board[0].length / 2, color));
        }

        for (int xx = 0; xx < board.length; xx++) {
            for (int yy = 0; yy < board[0].length; yy++) {
                if (board[xx][yy] == null) {
                    continue;
                }
                for (Direction direction : Direction.values()) {
                    int x = direction.applyToX(xx);
                    int y = direction.applyToY(yy);
                    if (insideBoard(x, y) && board[x][y] == null) {
                        possibleMoves.add(new GomokuMove(x, y, color));
                    }
                    x = direction.applyToX(x);
                    y = direction.applyToY(y);
                    if (insideBoard(x, y) && board[x][y] == null) {
                        possibleMoves.add(new GomokuMove(x, y, color));
                    }
                }

            }
        }

        return possibleMoves;
    }

    public GomokuBoard apply(GomokuMove move) {
        board[move.getX()][move.getY()] = move.getColor();
        int maxLength = getMoveLength(move);

        this.maxLineComputer = move.getColor() == Color.HUMAN ? 0 : maxLength;
        this.maxLineHuman = move.getColor() == Color.HUMAN ? maxLength : 0;

        if (move.getColor() == Color.HUMAN) {
            humanTotalCount++;
            humanTotalX += move.getX();
            humanTotalY += move.getY();
        } else {
            computerTotalCount++;
            computerTotalX += move.getX();
            computerTotalY += move.getY();
        }

        movesSoFar.add(move);

        return this;
    }

    public void undo(GomokuMove move) {
        board[move.getX()][move.getY()] = null;
        maxLineComputer = maxLineHuman = 0;
        if (move.getColor() == Color.HUMAN) {
            humanTotalCount--;
            humanTotalX -= move.getX();
            humanTotalY -= move.getY();
        } else {
            computerTotalCount--;
            computerTotalX -= move.getX();
            computerTotalY -= move.getY();
        }
        movesSoFar.remove(move);
    }

    @Override
    public long uniqueHashCode() {
        return hashCode();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((movesSoFar == null) ? 0 : movesSoFar.hashCode());
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
        if (movesSoFar == null) {
            if (other.movesSoFar != null)
                return false;
        } else if (!movesSoFar.equals(other.movesSoFar))
            return false;
        return true;
    }
}
