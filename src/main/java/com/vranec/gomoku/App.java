package com.vranec.gomoku;

import java.util.Date;
import java.util.Scanner;

import com.vranec.minimax.ArtificialIntelligence;
import com.vranec.minimax.Color;

/**
 * Hello world!
 *
 */
public class App {
    private final ArtificialIntelligence<GomokuMove> ai = new ArtificialIntelligence<>();
    private GomokuBoard board = new GomokuBoard(9, 9);
    Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        new App().play();
    }

    private void play() {
        while (!board.isGameOver()) {
            board.display();
            System.out.print("Your move: ");
            String moveString = input.nextLine();
            GomokuMove move = new GomokuMove(moveString);
            board = (GomokuBoard) board.apply(move);
            board.display();
            board = (GomokuBoard) board.apply(ai.getBestMoveTimedIterativeDeepeningTimed(board, 100, Color.COMPUTER,
                    new Date().getTime() + 1000 * 5).getMove());
        }
        System.out.println("Game over.");
    }
}
