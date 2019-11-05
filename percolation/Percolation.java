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
    private final int topVirtualSiteIndex; // index of the top virtual site
    private final int bottomVirtualSiteIndex; // index of the bottom virtual site
    private boolean[] grid;    // the model of the n-by-n grid
    private final WeightedQuickUnionUF wquf; // Weighted Quick Union Find object
    private final int sitesPerRow; // keep track of the number of grid per row (n)
    private int numberOfOpenSites; // counter to keep track of the number of open sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n = " + Integer.toString(n));
        }
        this.sitesPerRow = n;
        wquf = new WeightedQuickUnionUF(
                sitesPerRow * sitesPerRow + 2); // with 2 extra virtual sites
        grid = new boolean[sitesPerRow * sitesPerRow];
        topVirtualSiteIndex = sitesPerRow * sitesPerRow;
        bottomVirtualSiteIndex = sitesPerRow * sitesPerRow + 1;
        numberOfOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > sitesPerRow || row <= 0 || col > sitesPerRow || col <= 0) {
            throw new IllegalArgumentException(
                    "Row: " + Integer.toString(row) + "\tColumn: " + Integer.toString(col));
        }
        int index = getIndex(row, col);
        if (!isOpen(row, col)) {
            grid[index] = true; // change the size to 1 to indicate an opened site

            // If not top row, union with the open site above
            if (row != 1 && isOpen(row - 1, col)) {
                wquf.union(index, index - sitesPerRow);
            }

            if (row == 1) { // if top row, connect to top virtual site
                wquf.union(index, topVirtualSiteIndex);
            }

            // If not bottom row, union with the open site below
            if (row != sitesPerRow && isOpen(row + 1, col)) {
                wquf.union(index, index + sitesPerRow);
            }

            if (row == sitesPerRow) { // if bottom row, connect to bottom virtual site
                wquf.union(index, bottomVirtualSiteIndex);
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

        return grid[getIndex(row, col)];
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

        // checks if this site is connected to the virtual site on top
        return wquf.connected(getIndex(row, col), topVirtualSiteIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.connected(topVirtualSiteIndex, bottomVirtualSiteIndex);
    }

    // map row and col to get array index
    private int getIndex(int row, int col) {
        return (col - 1) + ((row - 1) * sitesPerRow);
    }
}
