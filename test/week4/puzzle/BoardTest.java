package week4.puzzle;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: ajelimalai
 * @created: 11/04/13
 */
public class BoardTest {

    private Board resolvedBoard3x3;
    private Board resolvedBoard2x2;


    @Before
    public void setUp() {
        resolvedBoard3x3 = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
        resolvedBoard2x2 = new Board(new int[][] { { 1, 2 }, { 3, 0 } });
    }


    @Test(expected = NullPointerException.class)
    public void NullPointedExceptionWhenNullInput() {
        new Board(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void InputArrayWithOneElement() {
        new Board(new int[][] { { 2 } });
    }


    @Test(expected = IllegalArgumentException.class)
    public void notEqualRowsWithCols() {
        new Board(new int[][] { { 2, 3, 4 }, { 5, 8, 5 } });
    }


    @Test
    public void threeDimensional() {
        final Board board = new Board(new int[][] { { 2, 3, 4 }, { 1, 5, 6 }, { 8, 0, 7 } });
        Assert.assertEquals("No correct dimension", 3, board.dimension());
    }


    @Test
    public void fourDimensional() {
        final Board board = new Board(new int[][] { { 2, 3, 4, 8 }, { 1, 5, 6, 4 }, { 8, 0, 7, 7 }, { 8, 0, 7, 7 } });
        Assert.assertEquals("No correct dimension", 4, board.dimension());
    }


    @Test
    public void isGoalTrueFourElements() {
        final Board board = new Board(new int[][] { { 1, 2 }, { 3, 0 } });
        assertTrue("Identified as not the goal", board.isGoal());
    }


    @Test
    public void isGoalTrueThreeElements() {
        final Board board = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
        assertTrue("Identified as not the goal", board.isGoal());
    }


    @Test
    public void isGoalFalseThreeElements() {
        final Board board = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } });
        Assert.assertFalse("Identified as goal", board.isGoal());
    }


    @Test
    public void isGoalFalseThreeElementsNoEmpty() {
        final Board board = new Board(new int[][] { { 1, 2, 3 }, { 5, 5, 6 }, { 7, 8, 9 } });
        Assert.assertFalse("Identified as goal", board.isGoal());
    }


    @Test
    public void equalsWithNullFalse() {
        assertFalse("Not false equals when null argument", resolvedBoard3x3.equals(null));
    }


    @Test
    public void equalsOtherObjectTypeFalse() {
        assertFalse("Not false equals when another type of argument", resolvedBoard3x3.equals(new Object()));
    }


    @Test
    public void equalsBoardTrue() {
        final Board board = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
        assertTrue("The Board should be equals", resolvedBoard3x3.equals(board));
    }


    @Test
    public void equalsBoardFalse() {
        final Board board = new Board(new int[][] { { 1, 2, 3 }, { 5, 5, 6 }, { 7, 8, 9 } });
        assertFalse("Not false equals when different elements", resolvedBoard3x3.equals(board));
    }


    @Test
    public void equalsBoardFalseDifferentDimensional() {
        assertFalse("Not false equals when different elements", resolvedBoard3x3.equals(resolvedBoard2x2));
    }


    @Test
    public void twinForTwoDimensional() {
        final Board expectedBoard = new Board(new int[][] { { 2, 1 }, { 3, 0 } });
        assertTrue("Not expected board", resolvedBoard2x2.twin().equals(expectedBoard));
    }


    @Test
    public void twinForTwoDimensionalSecondRowToChange() {
        final Board board = new Board(new int[][] { { 1, 0 }, { 2, 3 } });
        final Board expectedBoard = new Board(new int[][] { { 1, 0 }, { 3, 2 } });
        assertTrue("Not expected board", board.twin().equals(expectedBoard));
    }


    @Test
    public void twinDoesNotChangeTheActual() {
        final Board expectedBoard = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
        resolvedBoard3x3.twin();
        assertTrue("The twin affect actual", resolvedBoard3x3.equals(expectedBoard));
    }


    @Test
    public void hammingForTwoDimensional() {
        final Board board = new Board(new int[][] { { 1, 0 }, { 2, 3 } });
        assertEquals("Not correct hamming", 2, board.hamming());
    }


    @Test
    public void hammingForResolvedBoard() {
        assertEquals("Not correct hamming", 0, resolvedBoard3x3.hamming());
    }


    @Test
    public void hammingForAllElementsInWrongPosition() {
        final Board expectedBoard = new Board(new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } });
        assertEquals("Not correct hamming", 8, expectedBoard.hamming());
    }


    @Test
    public void manhattanForTwoDimensional() {
        final Board board = new Board(new int[][] { { 1, 0 }, { 2, 3 } });
        assertEquals("Not correct manhattan", 3, board.manhattan());
    }


    @Test
    public void manhattanForThreeDimensional() {
        final Board expectedBoard = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
        assertEquals("Not correct manhattan", 10, expectedBoard.manhattan());
    }


//    @Test
//    public void toStringCorrect() {
//        final String expected = "3\n 1  2  3\n 4  5  6\n 7  8  0";
//        assertEquals("Wrong string format", expected.toString(), resolvedBoard3x3.toString());
//    }


    @Test
    public void neighborsForTwoDimensional() {
        List<Board> expectedNeighbors = new ArrayList<Board>();
        final Board board = new Board(new int[][] { { 1, 0 }, { 2, 3 } });
        expectedNeighbors.add(new Board(new int[][] { { 0, 1 }, { 2, 3 } }));
        expectedNeighbors.add(new Board(new int[][] { { 1, 3 }, { 2, 0 } }));
        assertNeighbors(expectedNeighbors, board);
    }


    @Test
    public void neighborsForThreeDimensional() {
        List<Board> expectedNeighbors = new ArrayList<Board>();
        final Board board = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });

        expectedNeighbors.add(new Board(new int[][] { { 8, 0, 3 }, { 4, 1, 2 }, { 7, 6, 5 } }));
        expectedNeighbors.add(new Board(new int[][] { { 8, 1, 3 }, { 4, 2, 0 }, { 7, 6, 5 } }));
        expectedNeighbors.add(new Board(new int[][] { { 8, 1, 3 }, { 0, 4, 2 }, { 7, 6, 5 } }));
        expectedNeighbors.add(new Board(new int[][] { { 8, 1, 3 }, { 4, 6, 2 }, { 7, 0, 5 } }));
        assertNeighbors(expectedNeighbors, board);
    }


    private void assertNeighbors(final List<Board> aExpectedNeighbors, final Board aBoard) {
        int neighborsCount = 0;
        for (final Board neighbor : aBoard.neighbors()) {
            neighborsCount++;
            assertTrue("Unexpected neighbor", aExpectedNeighbors.contains(neighbor));

        }
        assertEquals("Some neighbors were not returned", aExpectedNeighbors.size(), neighborsCount);
    }

}
