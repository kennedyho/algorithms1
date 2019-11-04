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
    private Percolation percolation; // Object of the class Percolation
    private double[] percolationThresholds; // an array of percolation thresholds
    private int numberOfSitesPerRow; // number of sites per row entered by user
    private double meanVal;
    private double stddevVal;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.numberOfSitesPerRow = n;
        percolation = new Percolation(n);
        percolationThresholds = new double[trials];
    }

    // Getter for the number of sites per row
    private int getNumberOfSitesPerRow() {
        return numberOfSitesPerRow;
    }

    // sample mean of percolation threshold
    public double mean() {
        this.meanVal = StdStats.mean(percolationThresholds);
        return this.meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        this.stddevVal = StdStats.stddev(percolationThresholds);
        return this.stddevVal;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.meanVal - ((1.96 * Math.sqrt(this.stddevVal)) / Math
                .sqrt(percolationThresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.meanVal + ((1.96 * Math.sqrt(this.stddevVal)) / Math
                .sqrt(percolationThresholds.length));
    }

    // do multiple experiments based on the number of trials
    public void doMultipleExperiments() {
        for (int i = 0; i < percolationThresholds.length; i++) {
            percolation.reset();
            percolationThresholds[i] = getPercolationThreshold();
        }
    }

    // do the percolation experiment and get threshold
    private double getPercolationThreshold() {
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(getNumberOfSitesPerRow());
            int col = StdRandom.uniform(getNumberOfSitesPerRow());
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

        pstats.doMultipleExperiments();

        System.out.println("mean                     = " + pstats.mean());
        System.out.println("stddev                   = " + pstats.stddev());
        System.out.println("95%% confidence interval = [" + pstats.confidenceLo() + ", " + pstats
                .confidenceHi() + "]");
    }
}
