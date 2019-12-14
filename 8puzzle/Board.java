/* *****************************************************************************
 *  Name:   Kennedy Ho
 *  Date:   2019/11/09
 *  Description: Slider Puzzle assignment for Algorithms I Week 4 Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private int[][] myBoard;    // current board
    private int dim;              // dimension of board N

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
        myBoard = new int[tiles.length][tiles.length];
        dim = myBoard.length;

        // Copy the parameter array to myBoard
        myBoard = deepCopy(tiles);
    }

    /**
     * @return string representation of this board
     */
    public String toString() {
        StringBuilder representation = new StringBuilder(dimension() + "\n");
        for (int row = 0; row < myBoard.length; row++) {
            for (int col = 0; col < myBoard[row].length; col++) {
                String tile = Integer.toString(myBoard[row][col]);
                representation.append(String.format(" %4s", tile));
            }
            representation.append("\n");
        }
        return representation.toString();
    }

    /**
     * @return board dimension n
     */
    public int dimension() {
        return dim;
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
                int correctPos = (row == dimension() - 1 && col == dimension() - 1) ? 0 :
                                 (row * dimension() + col + 1);
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
        int sum = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                int rowDist;
                int colDist;
                if (myBoard[row][col] == 0) {
                    rowDist = row - dimension() - 1;
                    colDist = col - dimension() - 1;
                }
                else {
                    rowDist = row - ((myBoard[row][col] - 1) / dimension());    // row distance
                    colDist = col - ((myBoard[row][col] - 1)
                            % dimension());    // column distance
                }
                sum += Math.abs(rowDist) + Math
                        .abs(colDist);  // absolute number, no negative
            }
        }
        return sum;
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
        if (y == null) {    // null exception
            return false;
        }
        if (y.getClass() != this.getClass()) {  // check if the parameter is of class Board
            return false;
        }
        if (this == y) {    // same object
            return true;
        }
        Board that = (Board) y;
        return Arrays.equals(this.myBoard, that.myBoard);
    }

    /**
     * All neighboring boards, aka all possible moves from current board
     *
     * @return neighbor boards
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborBoards = new ArrayList<>();
        int moveRow = 0;
        int moveCol = 0;

        outerloop:
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (myBoard[row][col] == 0) {
                    moveRow = row;
                    moveCol = col;
                    break outerloop;
                }
            }
        }

        if (moveRow != 0) {
            int[][] array = deepCopy(myBoard);
            array[moveRow][moveCol] = array[moveRow - 1][moveCol];
            array[moveRow - 1][moveCol] = 0;
            neighborBoards.add(new Board(array));
        }
        if (moveRow != dimension() - 1) {
            int[][] array = deepCopy(myBoard);
            array[moveRow][moveCol] = array[moveRow + 1][moveCol];
            array[moveRow + 1][moveCol] = 0;
            neighborBoards.add(new Board(array));
        }
        if (moveCol != 0) {
            int[][] array = deepCopy(myBoard);
            array[moveRow][moveCol] = array[moveRow][moveCol - 1];
            array[moveRow][moveCol - 1] = 0;
            neighborBoards.add(new Board(array));
        }
        if (moveCol != dimension() - 1) {
            int[][] array = deepCopy(myBoard);
            array[moveRow][moveCol] = array[moveRow][moveCol + 1];
            array[moveRow][moveCol + 1] = 0;
            neighborBoards.add(new Board(array));
        }
        return neighborBoards;
    }

    /**
     * A board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {
        int[][] array = deepCopy(myBoard);
        int random1 = StdRandom.uniform(dimension() * dimension());
        int random2 = StdRandom.uniform(dimension() * dimension());
        while (array[random1 / dimension()][random1 % dimension()] == 0
                || array[random2 / dimension()][random2 % dimension()] == 0
                || random1 == random2) {
            random1 = StdRandom.uniform(dimension() * dimension());
            random2 = StdRandom.uniform(dimension() * dimension());
        }
        int temp = array[random1 / dimension()][random1 % dimension()];
        array[random1 / dimension()][random1 % dimension()] = array[random2
                / dimension()][random2 % dimension()];
        array[random2 / dimension()][random2 % dimension()] = temp;

        return new Board(array);
    }

    private int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    /**
     * Unit testing
     */
    public static void main(String[] args) {
        int[][] puzzle = new int[4][4];
        int value = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                puzzle[i][j] = value++;
            }
        }
        Board board = new Board(puzzle);

        StdOut.println(board.toString());
        StdOut.println(board.twin().toString());

        Iterable<Board> listOfBoards = board.neighbors();
        for (Board b : listOfBoards) {
            StdOut.println(b.toString());
        }
    }
}
