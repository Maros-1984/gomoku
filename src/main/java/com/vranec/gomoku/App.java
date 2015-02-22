package com.vranec.gomoku;

import java.util.Scanner;

import com.vranec.minimax.ArtificialIntelligence;
import com.vranec.minimax.Color;

/**
 * Hello world!
 *
 */
public class App {
    private final ArtificialIntelligence ai = new ArtificialIntelligence();
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
            board = new GomokuBoard(board, move);
            board.display();
            board = (GomokuBoard) ai.getBestMove(board, 4, Color.COMPUTER).getBestBoard();
        }
        System.out.println("Game over.");
    }
}
