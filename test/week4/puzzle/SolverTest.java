package week4.puzzle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import week4.puzzle.Solver.Node;
import week4.puzzle.Solver.NodeComparator;

/**
 * @author: ajelimalai
 * @created: 11/04/13
 */
public class SolverTest {

    public static final NodeComparator NODE_COMPARATOR = new NodeComparator();
    Board resolvedBoard2x2;
    Board resolvedBoard3x3;


    @Before
    public void setUp() {
        resolvedBoard2x2 = new Board(new int[][] { { 1, 2 }, { 3, 0 } });
        resolvedBoard3x3 = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
    }


    @Test(expected = NullPointerException.class)
    public void nullCheck() {
        new Solver(null);
    }


    @Test
    public void comparatorEqual() {
        Node node1 = new Node(resolvedBoard2x2, null);
        Node node2 = new Node(resolvedBoard2x2, null);
        assertEquals("Wrong comparator result", 0, NODE_COMPARATOR.compare(node1, node2));
    }


    @Test
    public void comparatorFirstNullReturnsNegative() {
        Node node2 = new Node(resolvedBoard2x2, null);
        assertEquals("Wrong comparator result", -1, NODE_COMPARATOR.compare(null, node2));
    }


    @Test
    public void comparatorSecondNullReturnsNegative() {
        Node node2 = new Node(resolvedBoard2x2, null);
        assertEquals("Wrong comparator result", 1, NODE_COMPARATOR.compare(node2, null));
    }


    @Test
    public void twoDimensional() {
        final Board initial = new Board(new int[][] { { 1, 2 }, { 0, 3 } });
        List<Board> expectedSolution = new ArrayList<Board>();
        expectedSolution.add(initial);
        expectedSolution.add(new Board(new int[][] { { 1, 2 }, { 3, 0 } }));
        assertSolution(initial, expectedSolution);
    }

    @Test
    public void twoDimensionalInfeasible() {
        final Board initial = new Board(new int[][] { { 2, 1 },
                                                      { 0, 3 } });
        Solver solver = new Solver(initial);
        assertEquals("There are moves in a infeasible board", 0, solver.moves());
        assertNull("There are solution in a infeasible board", solver.solution());
        assertFalse("The solver can solve an infeasible board", solver.isSolvable());
        System.out.println("No Solution");
    }


    @Test
    public void twoDimensionalComplex() {
        final Board initial = new Board(new int[][] { { 0, 1 }, { 3, 2 } });
        List<Board> expectedSolution = new ArrayList<Board>();
        expectedSolution.add(initial);
        expectedSolution.add(new Board(new int[][] { { 1, 0 }, { 3, 2 } }));
        expectedSolution.add(new Board(new int[][] { { 1, 2 }, { 3, 0 } }));
        assertSolution(initial, expectedSolution);
    }


    @Test
    public void threeDimensional() {
        final Board initial = new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } });

        List<Board> expectedSolution = new ArrayList<Board>();
        expectedSolution.add(initial);
        expectedSolution.add(new Board(new int[][] { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } }));
        expectedSolution.add(new Board(new int[][] { { 1, 2, 3 }, { 4, 0, 5 }, { 7, 8, 6 } }));
        expectedSolution.add(new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 0 }, { 7, 8, 6 } }));
        expectedSolution.add(new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }));
        assertSolution(initial, expectedSolution);
    }


    private void assertSolution(final Board aInitial, final List<Board> aExpectedSolution) {
        final Solver solver = new Solver(aInitial);
        assertTrue("The board is solvable", solver.isSolvable());
        assertEquals("The number of moves is not correct", aExpectedSolution.size() - 1, solver.moves());
        final Iterator<Board> solutionIterator = solver.solution().iterator();
        final Iterator<Board> expectedIterator = aExpectedSolution.iterator();
        while (solutionIterator.hasNext()) {
            assertEquals("Not correct board", expectedIterator.next(), solutionIterator.next());
        }
        assertFalse("Expected solution contains more boards", expectedIterator.hasNext());
        printSolution(solver);

    }


    private void printSolution(final Solver aSolver) {
        System.out.println();
        System.out.println(aSolver.moves());
        for (final Board board : aSolver.solution()) {
            System.out.println(board);
            System.out.println();
        }
    }

}
