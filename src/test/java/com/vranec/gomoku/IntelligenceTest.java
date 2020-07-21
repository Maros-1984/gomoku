package com.vranec.gomoku;

import org.junit.Assert;
import org.junit.Test;

import com.vranec.minimax.ArtificialIntelligence;
import com.vranec.minimax.BestMove;
import com.vranec.minimax.Board;
import com.vranec.minimax.Color;

public class IntelligenceTest {
    private static final GomokuBoard BOARD_GAME_OVER_IN_ONE_ = new GomokuBoard(9, 9, "     HHHH");
    private static final GomokuBoard BOARD_GAME_OVER_IN_TWO = new GomokuBoard(9, 9, "     HHH ");
    private static final GomokuBoard BOARD_GAME_OVER_IN_TWO_2 = new GomokuBoard(9, 9, "X       X", " X     X", "  X   X");
    private ArtificialIntelligence<GomokuMove> ai = new ArtificialIntelligence<>();

    @Test
    public void testGameOverInOne() {
        Assert.assertEquals(new GomokuMove(4, 0, Color.HUMAN),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_ONE_, 1, Color.HUMAN).getMove());
    }

    @Test
    public void testGameOverInTwo() {
        Assert.assertEquals(new GomokuMove(4, 0, Color.HUMAN),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO, 3, Color.HUMAN).getMove());
    }

    @Test
    public void testGameOverInTwo2() {
        BestMove result = ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO_2, 5, Color.COMPUTER);
        Assert.assertEquals(new GomokuMove(4, 4, Color.COMPUTER), result.getMove());
    }

    @Test
    public void testPreventGameOverInTwo() {
        BestMove result = ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO_2, 4, Color.HUMAN);
        Assert.assertEquals(new GomokuMove(4, 4, Color.HUMAN), result.getMove());
    }

    @Test
    public void testPerformance() {
        long start = System.currentTimeMillis();
        int depth = 10;
        ai.getBestMoveIterativeDeepening(new GomokuBoard(50, 50), depth, Color.HUMAN);
        System.out.println("Searched in depth " + depth + " for " + (System.currentTimeMillis() - start) + "ms.");
    }
}
