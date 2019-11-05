/* *****************************************************************************
 *  Name:    Kennedy Ho
 *  NetID:   kennedyho
 *  Precept: P00
 *
 *  Description:  Percolation assignment program.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;    // the model of the n-by-n grid
    private WeightedQuickUnionUF wquf; // Weighted Quick Union Find object
    private final int sitesPerRow; // keep track of the number of grid per row (n)
    private int numberOfOpenSites; // keep track of the number of sites that are opened

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n = " + Integer.toString(n));
        }
        this.sitesPerRow = n;
        wquf = new WeightedQuickUnionUF(n * n + 1); // with 1 extra virtual site
        grid = new boolean[n * n];
        numberOfOpenSites = 0;

        // connect top rows to virtual site n*n,
        for (int i = 0; i < n; i++) {
            wquf.union(i, n * n);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > sitesPerRow || row <= 0 || col > sitesPerRow || col <= 0) {
            throw new IllegalArgumentException(
                    "Row: " + Integer.toString(row) + "\tColumn: " + Integer.toString(col));
        }
        int index = (col - 1) + ((row - 1) * sitesPerRow);
        if (!grid[index]) {
            grid[index] = true; // change the size to 1 to indicate an opened site

            // If not top row, union with the open site above
            if (row != 1 && isOpen(row - 1, col)) {
                wquf.union(index, index - sitesPerRow);
            }

            // If not bottom row, union with the open site below
            if (row != sitesPerRow && isOpen(row + 1, col)) {
                wquf.union(index, index + sitesPerRow);
            }

            // If not leftmost column, union with the open left site
            if (col != 1 && isOpen(row, col - 1)) {
                wquf.union(index, index - 1);
            }

            // If not rightmost column, union with the open right site
            if (col != sitesPerRow && isOpen(row, col + 1)) {
                wquf.union(index, index + 1);
            }
            numberOfOpenSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > sitesPerRow || row <= 0 || col > sitesPerRow || col <= 0) {
            throw new IllegalArgumentException(
                    "Row: " + Integer.toString(row) + "\tColumn: " + Integer.toString(col));
        }
        int index = (col - 1) + ((row - 1) * sitesPerRow);

        return grid[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > sitesPerRow || row <= 0 || col > sitesPerRow || col <= 0) {
            throw new IllegalArgumentException(
                    "Row: " + Integer.toString(row) + "\tColumn: " + Integer.toString(col));
        }

        if (!isOpen(row, col)) { // can't be full if site is not even opened
            return false;
        }

        int index = (col - 1) + ((row - 1) * sitesPerRow);

        // checks if this site is connected to the virtual site on top
        return wquf.connected(index, grid.length);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = grid.length - sitesPerRow; i < grid.length; i++) {
            int row = i / sitesPerRow + 1;
            int col = i % sitesPerRow + 1;
            if (isFull(row, col)) {
                return true;
            }
        }
        return false;
    }
}
