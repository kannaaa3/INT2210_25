import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private boolean[][] grid;
  private WeightedQuickUnionUF theUF;
  private WeightedQuickUnionUF noVirBot; // without virtual bottom, determine which site is full

  private int virTop;
  private int virBot;
  private double numOpened;

  public Percolation(int n) {
    if (n <= 0) throw new IllegalArgumentException();

    numOpened = 0;
    grid      = new boolean[n][n];
    theUF     = new WeightedQuickUnionUF(n * n + 2); // top n * n, bot n * n + 1
    noVirBot  = new WeightedQuickUnionUF(n * n + 2);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = false;
      }
    }

    virTop = n * n;
    virBot = n * n + 1;
  }

  public void open(int row, int col) {
    if (!isValid(row, col)) throw new IllegalArgumentException();

    if (isOpen(row, col)) return;
    grid[row - 1][col - 1] = true;
    numOpened += 1;

    int this1D = to1D(row, col);
    if (row == 1) {
      theUF.union(this1D, virTop);
      noVirBot.union(this1D, virTop);
    }
    if (row == grid.length) theUF.union(this1D, virBot);

    int[] gox = {-1, 0, 0, 1};
    int[] goy = {0, -1, 1, 0};

    for (int i = 0; i < 4; i++) {
        int ni = row + gox[i], nj = col + goy[i];
        if (isValid(ni, nj) && isOpen(ni, nj)) {
          noVirBot.union(this1D, to1D(ni, nj));
          theUF.union(this1D, to1D(ni, nj));
      }
    }
  }

  private boolean isValid(int row, int col) {
    if (row <= 0 || col <= 0 || row > grid.length || col > grid.length) return false;
    return true;
  }

  public boolean isOpen(int row, int col) {
    if (!isValid(row, col)) throw new IllegalArgumentException();
    return grid[row - 1][col - 1];
  }

  private boolean isConnected(WeightedQuickUnionUF thisUF, int u, int v) {
    int pu = thisUF.find(u);
    int pv = thisUF.find(v);
    return pu == pv;
  }

  public boolean isFull(int row, int col) {
    if (!isValid(row, col)) throw new IllegalArgumentException();
    if (!isOpen(row, col)) return false;
    return isConnected(noVirBot, to1D(row, col), virTop);
  }

  public int numberOfOpenSites() {
    return (int) numOpened;
  }

  public boolean percolates() {
    if (grid.length == 1) return grid[0][0] == true;
    return isConnected(theUF, virTop, virBot);
  }

  private int to1D(int row, int col) {
    return (row - 1) * grid.length + (col - 1);
  }

  //  public static void main(String[] args) {}
}
