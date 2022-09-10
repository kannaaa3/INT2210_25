import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double CONST_RATE = 1.96;

  private double[] resultTrial;
  private double numTrials;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

    numTrials = (double) trials;
    resultTrial = new double[trials];

    for (int t = 0; t < trials; t++) {
      //      System.out.println("# Trials " + t + ":");
      Percolation theP = new Percolation(n);

      while (!theP.percolates()) {
        int row = StdRandom.uniformInt(1, n + 1);
        int col = StdRandom.uniformInt(1, n + 1);
        if (!theP.isOpen(row, col)) theP.open(row, col);
      }

      resultTrial[t] = ((double) theP.numberOfOpenSites()) / (double) (n * n);
      //      System.out.println("resultTrials[" + (t) + "] = " + resultTrial[t]);
    }
  }

  public double mean() {
    return StdStats.mean(resultTrial);
  }

  public double stddev() {
    return StdStats.stddev(resultTrial);
  }

  public double confidenceLo() {
    return mean() - ((CONST_RATE * stddev()) / Math.sqrt(numTrials));
  }

  public double confidenceHi() {
    return mean() + ((CONST_RATE * stddev()) / Math.sqrt(numTrials));
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);

    PercolationStats thePS = new PercolationStats(n, trials);

    System.out.println("mean                    = " + thePS.mean());
    System.out.println("stddev                  = " + thePS.stddev());
    System.out.println(
        "95% confidence interval = [" + thePS.confidenceLo() + ", " + thePS.confidenceHi() + "]");
  }
}
