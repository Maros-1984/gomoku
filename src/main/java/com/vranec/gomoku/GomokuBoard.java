package com.vranec.gomoku;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.vranec.minimax.Board;
import com.vranec.minimax.Color;
import com.vranec.minimax.Move;

public class GomokuBoard implements Board {
    private final Color[][] board;
    private final int maxLineHuman;
    private final int maxLineComputer;
    private final int humanTotalX;
    private final int humanTotalY;
    private final int humanTotalCount;
    private final int computerTotalX;
    private final int computerTotalY;
    private final int computerTotalCount;
    private final int hashCode;
    private final Set<Position> possibleMoves = new LinkedHashSet<Position>();

    public GomokuBoard(int width, int height) {
        board = new Color[width][height];
        maxLineComputer = maxLineHuman = 0;
        hashCode = 0;
        humanTotalCount = humanTotalX = humanTotalY = 0;
        computerTotalCount = computerTotalX = computerTotalY = 0;
        possibleMoves.add(new Position(width / 2, height / 2));
    }

    public GomokuBoard(GomokuBoard from, GomokuMove move) {
        hashCode = from.hashCode ^ move.hashCode();
        board = allocateBoardMemory(from);
        fillBoardWithData(from, move);
        int maxLength = getMoveLength(move);

        this.maxLineComputer = move.getColor() == Color.HUMAN ? from.maxLineComputer : Math
                .max(maxLength, from.maxLineComputer);
        this.maxLineHuman = move.getColor() == Color.COMPUTER ? from.maxLineHuman : Math.max(maxLength, from.maxLineHuman);

        updatePossibleMoves(from.possibleMoves, move);

        if (move.getColor() == Color.HUMAN) {
            humanTotalCount = from.humanTotalCount + 1;
            humanTotalX = from.humanTotalX + move.getX();
            humanTotalY = from.humanTotalY + move.getY();
            computerTotalCount = from.computerTotalCount;
            computerTotalX = from.computerTotalX;
            computerTotalY = from.computerTotalY;
        } else {
            computerTotalCount = from.computerTotalCount + 1;
            computerTotalX = from.computerTotalX + move.getX();
            computerTotalY = from.computerTotalY + move.getY();
            humanTotalCount = from.humanTotalCount;
            humanTotalX = from.humanTotalX;
            humanTotalY = from.humanTotalY;
        }
    }

    private void fillBoardWithData(GomokuBoard from, GomokuMove move) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                board[x][y] = from.board[x][y];
            }
        }
        board[move.getX()][move.getY()] = move.getColor();
    }

    private Color[][] allocateBoardMemory(GomokuBoard from) {
        return new Color[from.getWidth()][from.getHeight()];
    }

    private void updatePossibleMoves(Set<Position> fromPossibleMoves, GomokuMove move) {
        possibleMoves.addAll(fromPossibleMoves);
        possibleMoves.remove(new Position(move.getX(), move.getY()));
        for (Direction direction : Direction.values()) {
            int x = direction.applyToX(move.getX());
            int y = direction.applyToY(move.getY());
            if (insideBoard(x, y) && board[x][y] == null) {
                possibleMoves.add(new Position(x, y));
            }
            x = direction.applyToX(x);
            y = direction.applyToY(y);
            if (insideBoard(x, y) && board[x][y] == null) {
                possibleMoves.add(new Position(x, y));
            }
        }
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
        int hashCode = 0, humanTotalCount = 0, computerTotalCount = 0, humanTotalX = 0, humanTotalY = 0, computerTotalX = 0, computerTotalY = 0;
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
                    hashCode ^= move.hashCode();
                    updatePossibleMoves(possibleMoves, move);
                    maxLineHuman = Math.max(maxLineHuman, getMoveLength(move));
                    humanTotalCount++;
                    humanTotalX += x;
                    humanTotalY += y;
                    break;
                case 'x':
                case 'X':
                    board[x][y] = Color.COMPUTER;
                    GomokuMove move2 = new GomokuMove(x, y, Color.COMPUTER);
                    hashCode ^= move2.hashCode();
                    updatePossibleMoves(possibleMoves, move2);
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
        this.hashCode = hashCode;
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

    public Iterable<Move> getNextBoards(final Color color) {
        return new Iterable<Move>() {
            public Iterator<Move> iterator() {
                return new Iterator<Move>() {
                    private final Iterator<Position> iterator = GomokuBoard.this.possibleMoves.iterator();

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    public Move next() {
                        Position position = iterator.next();
                        return new GomokuMove(position.getX(), position.getY(), color);
                    }

                    public boolean hasNext() {
                        return iterator.hasNext();
                    }
                };
            }
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + hashCode;
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
        if (hashCode != other.hashCode)
            return false;
        return true;
    }

    public Board apply(Move move) {
        return new GomokuBoard(this, (GomokuMove) move);
    }

    public void undo(Move move) {
    }
}
