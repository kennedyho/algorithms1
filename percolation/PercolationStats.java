/* *****************************************************************************
 *  Name:    Kennedy Ho
 *  NetID:   kennedyho
 *  Precept: P00
 *
 *  Description:  Testing Percolation to do Monte Carlo Simulation.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96; // confidence constant for calculation

    private double[] percolationThresholds; // an array of percolation thresholds
    private final int numberOfSitesPerRow; // number of sites per row entered by user
    private final double meanVal; // mean value of percolation thresholds
    private final double stddevVal; // std dev value of percolation thresholds

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.numberOfSitesPerRow = n;
        percolationThresholds = new double[trials];

        doMultipleExperiments();

        meanVal = StdStats.mean(percolationThresholds);
        stddevVal = StdStats.stddev(percolationThresholds);
    }

    // Getter for the number of sites per row
    private int getNumberOfSitesPerRow() {
        return numberOfSitesPerRow;
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddevVal;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.meanVal - CONFIDENCE * Math.sqrt(this.stddevVal) / Math
                .sqrt(percolationThresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.meanVal + CONFIDENCE * Math.sqrt(this.stddevVal) / Math
                .sqrt(percolationThresholds.length);
    }

    // do multiple experiments based on the number of trials
    private void doMultipleExperiments() {
        for (int i = 0; i < percolationThresholds.length; i++) {
            Percolation percolation = new Percolation(numberOfSitesPerRow);
            percolationThresholds[i] = getPercolationThreshold(percolation);
        }
    }

    // do the percolation experiment and get threshold
    private double getPercolationThreshold(Percolation percolation) {
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(getNumberOfSitesPerRow()) + 1;
            int col = StdRandom.uniform(getNumberOfSitesPerRow()) + 1;
            percolation.open(row, col);
        }
        double openedSitesNo = percolation.numberOfOpenSites();
        return openedSitesNo / (getNumberOfSitesPerRow()
                * getNumberOfSitesPerRow());
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pstats = new PercolationStats(n, trials);

        System.out.println("mean                     = " + pstats.mean());
        System.out.println("stddev                   = " + pstats.stddev());
        System.out.println("95%% confidence interval = [" + pstats.confidenceLo() + ", " + pstats
                .confidenceHi() + "]");
    }
}
