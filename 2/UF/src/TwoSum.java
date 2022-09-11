import edu.princeton.cs.algs4.*;

import java.util.Arrays;

public class TwoSum {
  public static void main(String[] args) {
    In in = new In("4Kinds.txt");
    int[] a = in.readAllInts();

    Arrays.sort(a); // assuming ar consists of distinct values

    int r = a.length - 1;

    for (int l = 0; l < r; l++) {
      while (a[l] + a[r - 1] >= 0) r--;
      if (a[l] + a[r] == 0) System.out.println(a[l] + " " + a[r]);
    }
    //    StdArrayIO.print(a);
  }
}
