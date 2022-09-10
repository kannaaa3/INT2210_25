import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private boolean[][] grid;
  private WeightedQuickUnionUF theUF;

  private int virTop;
  private int virBot;
  private double numOpened;

  public Percolation(int n) {
    if (n <= 0) throw new IllegalArgumentException();

    numOpened = 0;
    grid = new boolean[n][n];
    theUF = new WeightedQuickUnionUF(n * n + 2); // top n * n, bot n * n + 1

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = false;
      }
    }

    //
    virTop = n * n;
    virBot = n * n + 1;
    for (int i = 1; i <= n; i++) {
      theUF.union(virTop, i-1);
      theUF.union(virBot, to1D(n, i));
    }
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    if (row <= 0 || col <= 0 || row > grid.length || col > grid.length)
      throw new IllegalArgumentException();

    if(isOpen(row, col)) return;

    grid[row - 1][col - 1] = true;

    numOpened += 1;

    int this1D = to1D(row, col);
    int n = grid.length;

    if (row > 1 && isOpen(row - 1, col)) theUF.union(this1D, to1D(row - 1, col));
    if (row < n && isOpen(row + 1, col)) theUF.union(this1D, to1D(row + 1, col));
    if (col > 1 && isOpen(row, col - 1)) theUF.union(this1D, to1D(row, col - 1));
    if (col < n && isOpen(row, col + 1)) theUF.union(this1D, to1D(row, col + 1));
  }

  public boolean isOpen(int i, int j) {
    if (i <= 0 || j <= 0 || i > grid.length || j > grid.length)
      throw new IllegalArgumentException();
    return grid[i - 1][j - 1];
  }

  private boolean isConnected(int u, int v) {
    int pu = theUF.find(u);
    int pv = theUF.find(v);
    return pu == pv;
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    if (row <= 0 || col <= 0 || row > grid.length || col > grid.length)
      throw new IllegalArgumentException();
    return (isOpen(row, col) && isConnected(to1D(row, col), virTop));
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return (int) numOpened;
  }

  public boolean percolates() {
    if(grid.length == 1) {
      return grid[0][0] == true;
    }
    return isConnected(virTop, virBot); // virTop and virBottom
  }

  private int to1D(int i, int j) {
    return (i - 1) * grid.length + (j - 1);
  }

  //  public static void main(String[] args) {}
}
