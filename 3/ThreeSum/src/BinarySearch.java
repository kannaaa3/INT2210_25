public class Two {
  public static int binarySearch(int[] a, int number) {
    int l = 0, r = a.length;
    int mid = (l + r + 1) / 2;

    while (l < r) {
      mid = (l + r) / 2;
      if (a[mid] >= number) r = mid;
      else l = mid + 1;
    }
    if (a[mid] == number) return mid;
    return -1;
  }
}
