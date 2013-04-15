package week4.puzzle;

import java.util.Comparator;
import java.util.Date;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 * @author: ajelimalai
 * @created: 11/04/13
 */
public class Solver {

    private NodeComparator nodeComparator = new NodeComparator();
    private MinPQ<Node> minPQ = new MinPQ<Node>(nodeComparator);
    private MinPQ<Node> minTwinPQ = new MinPQ<Node>(nodeComparator);
    private Stack<Board> solution;
    private int moves = -1;
    private boolean isSolvable = true;
    private Node node;
    private Node twinNode;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Initial Board cannot be null");
        }

        final Date date1 = new Date();
        findSolutionNode(new Node(initial, null));
        if (isSolvable) {
            populateSolution(node);
        }
        final Date date2 = new Date();
        System.out.println("Time: " + (date2.getTime() - date1.getTime()) / 1000);

    }


    private void populateSolution(final Node aNode) {
        solution = new Stack<Board>();
        moves = aNode.step;
        Node node = aNode;
        while (node != null) {
            solution.push(node.getBoard());
            node = node.previous;
        }
    }


    private void findSolutionNode(final Node aInitialNode) {
        node = aInitialNode;
        minPQ.insert(node);
        twinNode = new Node(node.getBoard().twin(), null);
        minTwinPQ.insert(twinNode);
        while (isGoalNotFound()) {
            populateCollections(node, twinNode);
            node = minPQ.delMin();
            twinNode = minTwinPQ.delMin();
        }
    }


    private boolean isGoalNotFound() {
        if (isGoalNode(node)) {
            return false;
        }
        if (isGoalNode(twinNode)) {
            isSolvable = false;
            return false;
        }
        return true;
    }


    private void populateCollections(final Node aNode, final Node aTwinNode) {
        populateNeighbors(aNode, minPQ);
        populateNeighbors(aTwinNode, minTwinPQ);
    }


    private void populateNeighbors(final Node aNode, final MinPQ<Node> aMinPQ1) {
        for (final Board neighborBoard : aNode.getBoard().neighbors()) {
            if (!neighborBoard.equals(getPreviousBoard(aNode))) {
                aMinPQ1.insert(new Node(neighborBoard, aNode));
            }
        }
    }


    private boolean isGoalNode(final Node aNode) {
        return aNode.getBoard().isGoal();
    }


    private Board getPreviousBoard(final Node aNode) {
        return aNode.previous == null ? null : aNode.previous.getBoard();
    }


    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }


    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return moves;
    }


    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return solution;
    }


    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


    protected static class Node {

        private Board board;
        private int step;
        private Node previous;


        protected Node(final Board aBoard, final Node aPrevious) {
            board = aBoard;
            previous = aPrevious;
            step = getNewStep();
        }


        private int getNewStep() {
            if (previous == null) {
                return 0;
            }
            return previous.step + 1;
        }


        private int getPriority() {
            return board.manhattan() + step;
        }


        private Board getBoard() {
            return board;
        }
    }


    protected static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(final Node aFirstNode, final Node aSecondNode) {
            if (aFirstNode == null) {
                return -1;
            }
            if (aSecondNode == null) {
                return 1;
            }
            return aFirstNode.getPriority() - aSecondNode.getPriority();
        }
    }

}
