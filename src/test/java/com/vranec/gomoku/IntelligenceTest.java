package com.vranec.gomoku;

import com.vranec.minimax.ArtificialIntelligence;
import com.vranec.minimax.BestMove;
import com.vranec.minimax.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntelligenceTest {
    private static final GomokuBoard BOARD_GAME_OVER_IN_ONE_ = new GomokuBoard(9, 9, "     HHHH");
    private static final GomokuBoard BOARD_GAME_OVER_IN_ONE_2 = new GomokuBoard(9, 9, "  XHHHH  ");
    private static final GomokuBoard BOARD_GAME_OVER_IN_ONE_3 = new GomokuBoard(9, 9,
            "",
            "       X",
            "      O ",
            "  XOOOO ",
            "   XOX  ",
            "   O X  ",
            "  X     ");

    private static final GomokuBoard BOARD_GAME_OVER_IN_TWO = new GomokuBoard(9, 9, "     HHH ");
    private static final GomokuBoard BOARD_GAME_OVER_IN_TWO_2 = new GomokuBoard(9, 9, "X       X", " X     X", "  X   X");
    private static final GomokuBoard BOARD_GAME_OVER_IN_TWO_3 = new GomokuBoard(9, 9, "", "    O",
            "   OOOX", "  O X", "   X X");
    private final ArtificialIntelligence<GomokuMove> ai = new ArtificialIntelligence<>();

    @Test
    public void testGameOverInOne() {
        Assertions.assertEquals(new GomokuMove(4, 0, Color.HUMAN),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_ONE_, 1, Color.HUMAN).getMove());
    }

    @Test
    public void testGameOverInOne2_givenComputerOnTurn_preventsHumanWin() {
        Assertions.assertEquals(new GomokuMove(7, 0, Color.COMPUTER),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_ONE_2, 2, Color.COMPUTER).getMove());
    }

    @Test
    public void testGameOverInOne2_givenHumanOnTurn_winsTheGame() {
        Assertions.assertEquals(new GomokuMove(7, 0, Color.HUMAN),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_ONE_2, 1, Color.HUMAN).getMove());
    }

    @Test
    public void testGameOverInOne3_givenComputerOnTurn_preventsHumanWin() {
        Assertions.assertEquals(new GomokuMove(7, 3, Color.COMPUTER),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_ONE_3, 5, Color.COMPUTER).getMove());
    }

    @Test
    public void testGameOverInOne3_givenHumanOnTurn_winsTheGame() {
        Assertions.assertEquals(new GomokuMove(7, 3, Color.HUMAN),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_ONE_3, 1, Color.HUMAN).getMove());
    }

    @Test
    public void testGameOverInTwo3_givenComputerOnTurn_preventsHumanWin() {
        Assertions.assertEquals(new GomokuMove(1, 4, Color.COMPUTER),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO_3, 4, Color.COMPUTER)
                        .getMove());
    }

    @Test
    public void testGameOverInTwo3_givenHumanOnTurn_winsTheGame() {
        Assertions.assertEquals(new GomokuMove(1, 4, Color.HUMAN),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO_3, 3, Color.HUMAN).getMove());
    }

    @Test
    public void testGameOverInTwo() {
        Assertions.assertEquals(new GomokuMove(4, 0, Color.HUMAN),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO, 3, Color.HUMAN).getMove());
    }

    @Test
    public void testGameOverInTwo2() {
        BestMove<GomokuMove> result = ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO_2, 5, Color.COMPUTER);
        Assertions.assertEquals(new GomokuMove(4, 4, Color.COMPUTER), result.getMove());
    }

    @Test
    public void testPreventGameOverInTwo() {
        BestMove<GomokuMove> result = ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO_2, 4, Color.HUMAN);
        Assertions.assertEquals(new GomokuMove(4, 4, Color.HUMAN), result.getMove());
    }

    @Test
    public void testPerformance() {
        long start = System.currentTimeMillis();
        int depth = 10;
        ai.getBestMoveIterativeDeepening(new GomokuBoard(10, 10), depth, Color.HUMAN);
        System.out.println("Searched in depth " + depth + " for " + (System.currentTimeMillis() - start) + "ms.");
    }

    @Test
    public void ai_givenEmptyBoard_shouldStartNearTheMiddle() {
        BestMove<GomokuMove> result = ai.getBestMoveIterativeDeepening(new GomokuBoard(9, 9), 4, Color.COMPUTER);
        Assertions.assertEquals(new GomokuMove(4, 4, Color.COMPUTER), result.getMove());
    }

    @Test
    public void ai_givenComputerIsSecondToMove_shouldStartNearTheMiddle() {
        GomokuMove result = ai.getBestMoveIterativeDeepening(
                new GomokuBoard(9, 9, "", "", "", "", "    O"), 5, Color.COMPUTER).getMove();

        Assertions.assertTrue(Math.abs(result.getX() - 4) <= 1, "Sub-optimal move: " + result);
        Assertions.assertTrue(Math.abs(result.getY() - 4) <= 1, "Sub-optimal move: " + result);
    }

    @Test
    public void ai_givenComputerIsSecondToMove_shouldContinueNearTheMiddle() {
        GomokuMove result = ai.getBestMoveIterativeDeepening(
                new GomokuBoard(9, 9, "", "", "", "", "   O O"), 4, Color.COMPUTER).getMove();

        Assertions.assertTrue(Math.abs(result.getX() - 4) <= 1);
        Assertions.assertTrue(Math.abs(result.getY() - 4) <= 1);
    }
}
