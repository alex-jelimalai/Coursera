package week4.puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ajelimalai
 * @created: 11/04/13
 */
public class Board {

    private int[] blocks;
    private static int UNINIT_POSITION = -1;
    private int dimension;


    private Board(int aDimension) {
        dimension = aDimension;
    }


    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (getLength(blocks) <= 1) {
            throw new IllegalArgumentException(" Board with one or less elements is not acceptable");
        }
        if (!isSquare(blocks)) {
            throw new IllegalArgumentException(" The board should be square");
        }
        dimension = blocks.length;
        initBlock(blocks);
    }


    private void initBlock(final int[][] aBlocks) {
        int length = aBlocks.length;
        blocks = new int[length * length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(aBlocks[i], 0, blocks, i * length, aBlocks[i].length);
        }
    }


    private int getLength(final int[][] aBlocks) {
        int length = 0;
        for (final int[] block : aBlocks) {
            length += block.length;
        }
        return length;
    }


    private boolean isSquare(final int[][] aBlocks) {
        boolean isSquare = true;
        for (final int[] block : aBlocks) {
            if (block.length != aBlocks.length) {
                isSquare = false;
            }
        }
        return isSquare;
    }


    // board dimension N
    public int dimension() {
        return dimension;
    }


    // number of blocks out of place
    public int hamming() {
        int outOfPlaceCount = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (isNotInCorrectPosition(i)) {
                outOfPlaceCount++;
            }
        }
        return outOfPlaceCount;
    }


    private boolean isNotInCorrectPosition(final int aI) {
        return blocks[aI] > 0 && blocks[aI] != aI + 1;
    }


    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distancesToRightPos = 0;
        for (int i = 0; i < blocks.length; i++) {
            int value = blocks[i];
            if (value != 0) {
                int differenceI = Math.abs((blocks[i] -1) / dimension() - i / dimension());
                int differenceJ = Math.abs((blocks[i] - 1) % dimension() - i  % dimension());
                distancesToRightPos += differenceI + differenceJ;
            }
        }
        return distancesToRightPos;
    }


    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < blocks.length - 1; i++) {
            if (blocks[i] != i + 1) {
                return false;
            }
        }
        return blocks[blocks.length - 1] == 0;
    }


    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board twin;
        int first = UNINIT_POSITION;
        int second = UNINIT_POSITION;
        for (int i = 0; i < blocks.length && (first < 0 || second < 0); i++) {
            if (i % dimension() == 0) {
                first = UNINIT_POSITION;
            }
            if (blocks[i] > 0 && isNotInitedPosition(first)) {
                first = i;
                continue;
            }
            if (blocks[i] > 0) {
                second = i;
                break;
            }
            first = UNINIT_POSITION;

        }
        twin = getTwinBoard(first, second);
        return twin;

    }


    private boolean isNotInitedPosition(int position) {
        return position == UNINIT_POSITION;
    }


    private Board getTwinBoard(final int aFirst, final int aSecond) {
        final Board twin = getSimilarBoard();
        changeElements(twin.blocks, aFirst, aSecond);
        return twin;
    }


    private void copyArrayTo(final Board aBoard) {
        System.arraycopy(blocks, 0, aBoard.blocks, 0, blocks.length);
    }


    private void changeElements(int[] array, int firstPosition, int secondPosition) {
        int temp = array[firstPosition];
        array[firstPosition] = array[secondPosition];
        array[secondPosition] = temp;
    }


    // does this board equal y?
    public boolean equals(Object aObject) {
        if (!(aObject instanceof Board)) {
            return false;
        }
        Board other = (Board)aObject;
        if (this == other) {
            return true;
        }
        if (dimension() != other.dimension()) {
            return false;
        }
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != other.blocks[i]) {
                return false;
            }
        }
        return true;
    }


    private Board getSimilarBoard() {
        Board board = new Board(dimension);
        board.blocks = new int[blocks.length];
        copyArrayTo(board);
        return board;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        int emptyI = 0;
        final List<Board> neighbors = new ArrayList<Board>();
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                emptyI = i;
            }
        }
        addUpNeighbors(emptyI, neighbors);
        addDownNeighbors(emptyI, neighbors);
        addLeftNeighbors(emptyI, neighbors);
        addRightNeighbors(emptyI, neighbors);
        return neighbors;
    }


    private void addUpNeighbors(final int aEmptyI, final List<Board> aNeighbors) {
        int i = aEmptyI - dimension();
        if (i < 0) {
            return;
        }
        populateNeighbor(i, aEmptyI, aNeighbors);
    }


    private void addDownNeighbors(final int aEmptyI, final List<Board> aNeighbors) {
        int i = aEmptyI + dimension();
        if (i >= blocks.length) {
            return;
        }
        populateNeighbor(i, aEmptyI, aNeighbors);
    }


    private void addLeftNeighbors(final int aEmptyI, final List<Board> aNeighbors) {
        int i = aEmptyI - 1;
        if (i < 0 || aEmptyI % dimension() == 0) {
            return;
        }
        populateNeighbor(i, aEmptyI, aNeighbors);
    }


    private void addRightNeighbors(final int aEmptyI, final List<Board> aNeighbors) {
        int i = aEmptyI + 1;
        if (i >= blocks.length || i % dimension() == 0) {
            return;
        }
        populateNeighbor(i, aEmptyI, aNeighbors);
    }


    private void populateNeighbor(final int oldEmptyIndex, final int aEmptyNewIndex, final List<Board> aNeighbors) {
        Board board = getSimilarBoard();
        changeElements(board.blocks, aEmptyNewIndex, oldEmptyIndex);
        aNeighbors.add(board);
    }


    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder builder = new StringBuilder();
        buildingString(builder);
        return builder.toString();
    }


    private void buildingString(final StringBuilder aBuilder) {
        aBuilder.append(dimension());
        for (int i = 0; i < blocks.length; i++) {
            buildNewLines(aBuilder, i);
            final int val = blocks[i];
            appentSpaces(aBuilder, val);
            aBuilder.append(val);
        }
    }


    private void appentSpaces(final StringBuilder aBuilder, final int aVal) {
        if (aVal < 10) {
            aBuilder.append(" ");
        }
        aBuilder.append(" ");
    }


    private void buildNewLines(final StringBuilder aBuilder, final int aI) {
        if (aI % dimension() == 0) {
            aBuilder.append("\n");
        }
    }
}
