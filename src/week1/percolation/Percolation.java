package week1.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] sites;
    private WeightedQuickUnionUF quickUnionPercolate;
    private WeightedQuickUnionUF quickUnionFull;
    private int sitesLength;
    private int topVirtual;
    private int bottomVirtual;


    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        int sitesCount = N * N;
        sitesLength = N;
        topVirtual = sitesCount;
        bottomVirtual = sitesCount + 1;
        quickUnionPercolate = new WeightedQuickUnionUF(sitesCount + 2);
        quickUnionFull = new WeightedQuickUnionUF(sitesCount + 1);
        sites = new boolean[sitesCount];
    }


    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        validatePositions(i, j);
        sites[xyTo1D(i, j)] = true;
        connectToOpenNeighbors(i, j);
    }


    private void connectToOpenNeighbors(int aI, int aJ) {
        connectToVirtualPositions(aI, aJ);
        int i = aI - 1;
        int j = aJ;

        connectIfOpen(aI, aJ, i, j);
        connectIfOpen(aI, aJ, ++i, --j);
        j += 2;
        connectIfOpen(aI, aJ, i, j);
        connectIfOpen(aI, aJ, ++i, --j);

    }


    private void connectIfOpen(int aI, int aJ, int i, int j) {
        if (isPositionsValid(i, j) && isOpen(i, j)) {
            int cell1 = xyTo1D(aI, aJ);
            int cell2 = xyTo1D(i, j);
            union(cell1, cell2);
        }
    }


    private void union(int cell1, int cell2) {
        quickUnionPercolate.union(cell1, cell2);
        quickUnionFull.union(cell1, cell2);
    }


    private boolean isPositionsValid(int x, int y) {
        return !isPositionsInvalid(x, y);
    }


    private void connectToVirtualPositions(int aI, int aJ) {
        if (isFirstLine(aI)) {
            union(xyTo1D(aI, aJ), topVirtual);
        }
        if (isLastLine(aI)) {
            quickUnionPercolate.union(xyTo1D(aI, aJ), bottomVirtual);
        }
    }


    private boolean isFirstLine(int aJ) {
        return aJ == 1;
    }


    private boolean isLastLine(int aJ) {
        return aJ == sitesLength;
    }


    private void validatePositions(int aI, int aJ) {
        if (isPositionsInvalid(aI, aJ)) {
            throw new IndexOutOfBoundsException();
        }
    }


    private boolean isPositionsInvalid(int aI, int aJ) {
        return isPositionInvalid(aI) || isPositionInvalid(aJ);
    }


    private boolean isPositionInvalid(int aI) {
        return aI <= 0 || aI > sitesLength;
    }


    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validatePositions(i, j);
        return sites[xyTo1D(i, j)];
    }


    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validatePositions(i, j);
        return quickUnionFull.connected(xyTo1D(i, j), topVirtual);
    }


    // does the system percolate?
    public boolean percolates() {
        return quickUnionPercolate.connected(topVirtual, bottomVirtual);
    }


    private int xyTo1D(int x, int y) {
        return sitesLength * (x-1) + (y-1);
    }



    public static void main(String [] arg){

    }
}
