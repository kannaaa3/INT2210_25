import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int     n;
    private final int[][] board;
    private       int     blankX;
    private       int     blankY;

    private int hammingVal   = -1;
    private int manhattanVal = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
    }

    // string representation of this board
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append(n).append("\n");

        for (int[] line : board) {
            for (int j = 0; j < n; j++) {
                ans.append(line[j]).append(' ');
            }
            ans.append('\n');
        }
        return ans.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingVal != -1) return hammingVal;
        hammingVal = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == 0) continue;

                int rowGoal = (board[row][col] - 1) / n;
                int colGoal = (board[row][col] - 1) % n;

                if (rowGoal != row || colGoal != col)
                    hammingVal++;
            }
        }
        return hammingVal;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanVal != -1) return manhattanVal;
        manhattanVal = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == 0) continue;

                int rowGoal = (board[row][col] - 1) / n;
                int colGoal = (board[row][col] - 1) % n;

                manhattanVal += Math.abs(rowGoal - row) + Math.abs(colGoal - col);
            }
        }

        return manhattanVal;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board _y = (Board) y;
        if(_y.n != n) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != _y.board[i][j]) return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborsList = new ArrayList<>();

        int[] gX = {-1, 0, 0, 1};
        int[] gY = {0, -1, 1, 0};

        for (int i = 0; i < 4; i++)
            mkNeighborSwap(blankX + gX[i], blankY + gY[i], neighborsList);

        return neighborsList;
    }

    private void mkNeighborSwap(int row, int col, List<Board> neighborsList) {
        if (!(0 <= row && row < n &&
                0 <= col && col < n)) return;
        int[][] copy = copy2D(board);

        copy[row][col] = 0; // blank
        copy[blankX][blankY] = board[row][col];

        neighborsList.add(new Board(copy));
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = copy2D(board);

        if (board[0][0] != 0 && board[0][1] != 0) {
            twin[0][0] = board[0][1];
            twin[0][1] = board[0][0];
        } else {
            twin[1][0] = board[1][1];
            twin[1][1] = board[1][0];
        }

        return new Board(twin);
    }

    private int[][] copy2D(int[][] original) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] arr = {{8, 1, 3},
                       {4, 0, 2},
                       {7, 6, 5}};

        Board mb = new Board(arr);
        System.out.println(mb);

        System.out.println(mb.hamming());
        System.out.println(mb.manhattan());

        for (Board bb : mb.neighbors()) {
            System.out.println(bb);
        }

        System.out.println(mb.twin());
    }
}
