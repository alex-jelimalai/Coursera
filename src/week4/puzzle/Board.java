package week4.puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ajelimalai
 * @created: 11/04/13
 */
public class Board {

    private int[][] blocks;
    private static int UNINIT_POSITION = -1;


    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (getLength(blocks) <= 1) {
            throw new IllegalArgumentException(" Board with one or less elements is not acceptable");
        }
        if (!isSquare(blocks)) {
            throw new IllegalArgumentException(" The board should be square");
        }
        initBlock(blocks);
    }


    private void initBlock(final int[][] aBlocks) {
        int length = aBlocks.length;
        blocks = new int[length][aBlocks.length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(aBlocks[i], 0, blocks[i], 0, aBlocks[i].length);
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
        return blocks.length;
    }


    // number of blocks out of place
    public int hamming() {
        int outOfPlaceCount = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] > 0 && isNotInCorrectPosition(i, j)) {
                    outOfPlaceCount++;
                }
            }
        }
        return outOfPlaceCount;
    }


    private boolean isNotInCorrectPosition(final int aI, final int aJ) {
        return blocks[aI][aJ] != aI * dimension() + aJ + 1;
    }


    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distancesToRightPos = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int value = blocks[i][j];
                if (value != 0) {
                    int correctI = (value - 1) / dimension();
                    int correctJ = (value - 1) % dimension();
                    distancesToRightPos += Math.abs(correctI - i) + Math.abs(correctJ - j);
                }
            }
        }
        return distancesToRightPos;
    }


    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension() && isNotLastElement(i, j); j++) {
                if (blocks[i][j] != (i * dimension() + j) + 1) {
                    return false;
                }
            }
        }
        return isElementEmpty(dimension() - 1, dimension() - 1);
    }


    private boolean isElementEmpty(final int aI1, final int aI2) {
        return blocks[aI1][aI2] == 0;
    }


    private boolean isNotLastElement(final int aI, final int aJ) {
        return aI * dimension() + aJ != dimension() * dimension() - 1;
    }


    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board twin;
        int first = UNINIT_POSITION;
        int second = UNINIT_POSITION;
        int[] changedBlock = null;
        for (int i = 0; i < dimension() && (first < 0 || second < 0); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] > 0 && isNotInitedPosition(first)) {
                    first = j;
                    continue;
                }
                if (blocks[i][j] > 0) {
                    second = j;
                    changedBlock = blocks[i];
                    break;
                }
                first = UNINIT_POSITION;
            }
        }
        twin = getTwinBoard(first, second, changedBlock);
        return twin;

    }


    private boolean isNotInitedPosition(int position) {
        return position == UNINIT_POSITION;
    }


    private Board getTwinBoard(final int aFirst, final int aSecond, final int[] aChangedBlock) {
        final Board twin;
        changeElements(aChangedBlock, aFirst, aSecond);
        twin = new Board(blocks);
        changeElements(aChangedBlock, aFirst, aSecond);
        return twin;
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
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        int emptyI = 0;
        int emptyJ = 0;
        final List<Board> neighbors = new ArrayList<Board>();
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    emptyI = i;
                    emptyJ = j;
                }
            }
        }
        addUpNeighbors(emptyI, emptyJ, neighbors);
        addDownNeighbors(emptyI, emptyJ, neighbors);
        addLeftNeighboars(emptyI, emptyJ, neighbors);
        addRightNeighboars(emptyI, emptyJ, neighbors);
        return neighbors;
    }


    private void addLeftNeighboars(final int aEmptyI, final int aEmptyJ, final List<Board> aNeighbors) {
        int j = aEmptyJ - 1;
        if (j < 0) {
            return;
        }
        changeElements(blocks[aEmptyI], aEmptyJ, j);
        aNeighbors.add(new Board(blocks));
        changeElements(blocks[aEmptyI], aEmptyJ, j);
    }


    private void addRightNeighboars(final int aEmptyI, final int aEmptyJ, final List<Board> aNeighbors) {
        int j = aEmptyJ + 1;
        if (j >= dimension()) {
            return;
        }
        changeElements(blocks[aEmptyI], aEmptyJ, j);
        aNeighbors.add(new Board(blocks));
        changeElements(blocks[aEmptyI], aEmptyJ, j);
    }


    private void addDownNeighbors(final int aEmptyI, final int aEmptyJ, final List<Board> aNeighbors) {
        int i = aEmptyI + 1;
        if (i >= dimension()) {
            return;
        }
        changeUpDown(aEmptyI, aEmptyJ, i);
        aNeighbors.add(new Board(blocks));
        changeUpDown(aEmptyI, aEmptyJ, i);
    }


    private void addUpNeighbors(final int aEmptyI, final int aEmptyJ, final List<Board> aNeighbors) {
        int i = aEmptyI - 1;
        if (i < 0) {
            return;
        }
        changeUpDown(aEmptyI, aEmptyJ, i);
        aNeighbors.add(new Board(blocks));
        changeUpDown(aEmptyI, aEmptyJ, i);
    }


    private void changeUpDown(final int aEmptyI, final int aEmptyJ, final int aI) {
        int temp = blocks[aI][aEmptyJ];
        blocks[aI][aEmptyJ] = blocks[aEmptyI][aEmptyJ];
        blocks[aEmptyI][aEmptyJ] = temp;
    }


    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension());
        builder.append("\n");
        for (final int[] block : blocks) {
            for (final int val : block) {
                if (val < 10) {
                    builder.append(" ");
                }
                builder.append(" ");
                builder.append(val);
            }
            builder.append("\n");
        }
        removeLastCharFrom(builder);
        return builder.toString();
    }


    private void removeLastCharFrom(final StringBuilder aBuilder) {
        aBuilder.replace(aBuilder.length() - 1, aBuilder.length(), "");
    }
}
