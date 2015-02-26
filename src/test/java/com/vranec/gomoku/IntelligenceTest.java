package com.vranec.gomoku;

import org.junit.Assert;
import org.junit.Test;

import com.vranec.minimax.ArtificialIntelligence;
import com.vranec.minimax.BestMove;
import com.vranec.minimax.Board;
import com.vranec.minimax.Color;

public class IntelligenceTest {
    private static final Board BOARD_GAME_OVER_IN_ONE_ = new GomokuBoard(9, 9, "     HHHH");
    private static final Board BOARD_GAME_OVER_IN_TWO = new GomokuBoard(9, 9, "     HHH ");
    private static final Board BOARD_GAME_OVER_IN_TWO_2 = new GomokuBoard(9, 9, "X       X", " X     X", "  X   X");
    private ArtificialIntelligence ai = new ArtificialIntelligence();

    @Test
    public void testGameOverInOne() {
        Assert.assertEquals(new GomokuBoard(9, 9, "    HHHHH"),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_ONE_, 1, Color.HUMAN).getBestBoard());
    }

    @Test
    public void testGameOverInTwo() {
        Assert.assertEquals(new GomokuBoard(9, 9, "    HHHH"),
                ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO, 3, Color.HUMAN).getBestBoard());
    }

    @Test
    public void testGameOverInTwo2() {
        BestMove result = ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO_2, 5, Color.COMPUTER);
        Assert.assertEquals(new GomokuBoard(9, 9, "X       X", " X     X", "  X   X", "", "    X"), result.getBestBoard());
    }

    @Test
    public void testPreventGameOverInTwo() {
        BestMove result = ai.getBestMoveIterativeDeepening(BOARD_GAME_OVER_IN_TWO_2, 4, Color.HUMAN);
        Assert.assertEquals(new GomokuBoard(9, 9, "X       X", " X     X", "  X   X", "", "    H"), result.getBestBoard());
    }
}
