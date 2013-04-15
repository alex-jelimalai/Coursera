package week1.percolation;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private static String INVALID_ARGUMENTS = "There should be 2 integer arguments";
    private double[] array = new double[]{};
    private int N;
    private int T;


    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(final int aN, final int aT) {
        validateInput(aN, aT);
        this.N = aN;
        this.T = aT;
        initArray(aT);
        populateStatistics();
    }


    private void validateInput(final int aN, final int aT) {
        if (aN <= 0 || aT <= 0) {
            throw new IllegalArgumentException();
        }
    }


    private  void initArray(int aN) {
        array = new double[aN];
    }

    private void populateStatistics() {
        for (int i = 0; i < T; i++) {
            int time = 0;
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int x = StdRandom.uniform(1, N + 1);
                int y = StdRandom.uniform(1, N + 1);
                if (perc.isOpen(x, y)) {
                    continue;
                }
                perc.open(x, y);
                time++;
            }
            array[i] = ((double) time) / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(array);
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(array);
    }


    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - stddev();
    }


    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + stddev();
    }


    // test client, described below
    public static void main(final String[] args) {
        valdidateArgs(args);
        displayStatistics(new PercolationStats(getInt(args[0]), getInt(args[1])));
    }


    private static int getInt(final String s) {
        return Integer.parseInt(s);
    }



    private static void displayStatistics(PercolationStats percolationStats) {
        System.out.println("mean                     = " 
                           + percolationStats.mean());
        System.out.println("stddev                   = " 
                           + percolationStats.stddev());
        System.out.println("95% confidence interval  = "  
                           + percolationStats.confidenceLo() 
                           + ", " 
                           + percolationStats.confidenceHi());
    }


    private static void valdidateArgs(String[] aArgs) {
        if (aArgs.length != 2) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS);
        }
        try {
            Integer.parseInt(aArgs[0]);
            Integer.parseInt(aArgs[1]);
        } catch (NumberFormatException aException) {
            throw new IllegalArgumentException(INVALID_ARGUMENTS);
        }

    }
}
