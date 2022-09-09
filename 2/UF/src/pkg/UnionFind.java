package pkg;

public class UnionFind {
  private int id[];
  private int sz[];

  public UnionFind(int n) {
    id = new int[n + 1];
    sz = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }

  private int findRoot(int i) {
    while (i != id[i]) {
      id[i] = id[id[i]];
      i = id[i];
    }
    return i;
  }

  public void union(int u, int v) {
    int pu = findRoot(u);
    int pv = findRoot(v);

    if (pu == pv) return;

    if (sz[pu] > sz[pv]) swap(pu, pv);
    id[pu] = pv;
    sz[pv] += sz[pu];
  }

  public void ifConnected(int u, int v) {
    int pu = findRoot(u);
    int pv = findRoot(v);

    if (pu == pv) System.out.println(u + " and " + v + " are connected!");
    else System.out.println(u + " and " + v + " are NOT connected yet!");
  }
}
