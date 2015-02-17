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
    private GomokuBoard board = new GomokuBoard();
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
            board.perform(move);
            board.display();
            GomokuMove bestComputerMove = (GomokuMove) ai.negamax(board, 10, Color.BLACK).getMove();
            bestComputerMove.display();
        }
        System.out.println("Game over.");
    }
}
