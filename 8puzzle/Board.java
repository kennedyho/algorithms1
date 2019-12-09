/* *****************************************************************************
 *  Name:   Kennedy Ho
 *  Date:   2019/11/09
 *  Description: Slider Puzzle assignment for Algorithms I Week 4 Assignment
 **************************************************************************** */

public class Board {

    private int[][] myBoard;

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
        for (int i = 0; i < tiles.length; i++) {
            if (tiles.length != tiles[i].length) {
                throw new IllegalArgumentException("The board is not n*n!");
            }

            // If there is null element in the array
            if (tiles[i] == null) {
                throw new NullPointerException("One of the rows is null!");
            }
        }

        // new instance of 2D array
        myBoard = new int[tiles.length][];

        // Copy the parameter array to myBoard
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; i < tiles[i].length; j++) {
                myBoard[i][j] = tiles[i][j];
            }
        }
    }

    /**
     * @return string representation of this board
     */
    public String toString() {


    }

    /**
     * @return board dimension n
     */
    public int dimension() {
        return myBoard.length;
    }

    /**
     * Total number of tiles that are at the wrong position
     *
     * @return number of tiles out of place
     */
    public int hamming() {

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

    /**
     * Unit testing
     */
    public static void main(String[] args) {

    }
}
