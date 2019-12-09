/* *****************************************************************************
 *  Name:   Kennedy Ho
 *  Date:   2019/11/09
 *  Description: Slider Puzzle assignment for Algorithms I Week 4 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Board {

    private int[][] myBoard;    // current board
    private int N;              // dimension of board N

    /**
     * Constructor to create a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     */
    public Board(int[][] tiles) {
        // Edge case - null input
        if (tiles == null) {
            throw new NullPointerException("The tiles parameter is null!");
        }

        // If row and column are not of same length
        for (int row = 0; row < tiles.length; row++) {
            if (tiles.length != tiles[row].length) {
                throw new IllegalArgumentException("The board is not n*n!");
            }

            // If there is null element in the array
            if (tiles[row] == null) {
                throw new NullPointerException("One of the rows is null!");
            }
        }

        // new instance of 2D array
        myBoard = new int[tiles.length][];
        N = myBoard.length;

        // Copy the parameter array to myBoard
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; row < tiles[row].length; col++) {
                myBoard[row][col] = tiles[row][col];
            }
        }
    }

    /**
     * @return string representation of this board
     */
    public String toString() {
        StdOut.println(dimension());
        for (int row = 0; row < myBoard.length; row++) {
            for (int col = 0; col < myBoard[row].length; col++) {
                StdOut.print(" " + formatWidth(myBoard[row][col], 5));
            }
            StdOut.println();
        }
    }

    /**
     * @return board dimension n
     */
    public int dimension() {
        return N;
    }

    /**
     * Total number of tiles that are at the wrong position
     *
     * @return number of tiles out of place
     */
    public int hamming() {
        int outOfPosCnt = 0;
        for (int row = 0; row < myBoard.length; row++) {
            for (int col = 0; col < myBoard[row].length; col++) {
                int correctPos = (row == N - 1 && col == N - 1) ? 0 :
                                 (row * N + col + 1);
                if (correctPos != myBoard[row][col]) {
                    outOfPosCnt++;
                }
            }
        }
        return outOfPosCnt;
    }

    /**
     * Manhattan distance is the total distance for a tile from its goal both horizontally and
     * vertically
     *
     * @return sum of the Manhattan distances between tiles and goals
     */
    public int manhattan() {

    }

    /**
     * Is this board the goal board?
     *
     * @return true if goal is reached
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Does this board equal y?
     *
     * @param y board for comparison
     * @return true if board is same size with same tiles location
     */
    public boolean equals(Object y) {

    }

    /**
     * All neighboring boards, aka all possible moves from current board
     *
     * @return neighbor boards
     */
    public Iterable<Board> neighbors() {

    }

    /**
     * A board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {

    }

    private String formatWidth(int value, int width) {
        return String.format("%" + "-" + width + "s" + value);
    }

    /**
     * Unit testing
     */
    public static void main(String[] args) {

    }
}
