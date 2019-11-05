/* *****************************************************************************
 *  Name:    Kennedy Ho
 *  NetID:   kennedyho
 *  Precept: P00
 *
 *  Description:  Percolation assignment program.
 *
 **************************************************************************** */

public class Percolation {
    private int[] grid;    // the model of the n-by-n grid
    private int[] size;     // size of the tree for Weighted Union Find
    private int sitesPerRow;          // keep track of the number of grid per row (n)

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n = " + Integer.toString(n));
        }
        this.sitesPerRow = n;
        grid = new int[n * n];
        size = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            grid[i] = i;
            size[i] = 0;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > sitesPerRow || row <= 0 || col > sitesPerRow || col <= 0) {
            throw new IllegalArgumentException(
                    "Row: " + Integer.toString(row) + "\tColumn: " + Integer.toString(col));
        }
        int index = (col - 1) + ((row - 1) * sitesPerRow);
        if (size[index] == 0) {
            size[index] = 1; // change the size to 1 to indicate an opened site

            // If not top row, union with the open site above
            if (row != 1 && isOpen(row - 1, col)) {
                union(index, index - sitesPerRow);
            }

            // If not bottom row, union with the open site below
            if (row != sitesPerRow && isOpen(row + 1, col)) {
                union(index, index + sitesPerRow);
            }

            // If not leftmost column, union with the open left site
            if (col != 1 && isOpen(row, col - 1)) {
                union(index, index - 1);
            }

            // If not rightmost column, union with the open right site
            if (col != sitesPerRow && isOpen(row, col + 1)) {
                union(index, index + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > sitesPerRow || row <= 0 || col > sitesPerRow || col <= 0) {
            throw new IllegalArgumentException(
                    "Row: " + Integer.toString(row) + "\tColumn: " + Integer.toString(col));
        }
        int index = (col - 1) + ((row - 1) * sitesPerRow);
        if (size[index] == 0) {
            return false;
        }
        return true;
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

        // checks if any of the sites in first row is connected to with this site
        for (int i = 0; i < sitesPerRow; i++) {
            if (connected(i, index)) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < size.length; i++) {
            if (size[i] != 0) {
                count++;
            }
        }
        return count;
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

    // find the root of a site
    private int root(int i) {
        while (i != grid[i]) {
            grid[i] = grid[grid[i]];
            i = grid[i];
        }
        return i;
    }

    // check if two sites are connected or not
    private boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    // Weighted Quick Union with Path Compression
    private void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (size[i] < size[j]) {
            grid[i] = j;
            size[j] += size[i];
        }
        else {
            grid[j] = i;
            size[i] += size[j];
        }
    }
}
