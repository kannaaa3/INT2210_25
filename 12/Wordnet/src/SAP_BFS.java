import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.List;

public class SAP_BFS {
    private int ancestor = -1;
    private int distance = -1;

    public void BFS(int u, int v, Digraph G) {
        BFS(List.of(u), List.of(v), G);
    }

    public void BFS(Iterable<Integer> u, Iterable<Integer> v, Digraph G) {
        int[] disU = oneDirBFS(u, G);
        int[] disV = oneDirBFS(v, G);

        for (int x = 0; x < G.V(); x++) {
            if (disU[x] != -1 && disV[x] != -1 && (distance == -1 || disU[x] + disV[x] < distance)) {
                distance = disU[x] + disV[x];
                ancestor = x;
            }
        }
    }

    public int getAncestor() {return ancestor;}

    public int getDistance() {return distance;}

    private int[] oneDirBFS(Iterable<Integer> initialSet, Digraph G) {
        int[] dis = new int[G.V()];
        Arrays.fill(dis, -1);

        Queue<Integer> q = new Queue<>();
        for (int u : initialSet) {
            dis[u] = 0;
            q.enqueue(u);
        }

        while (!q.isEmpty()) {
            int head = q.dequeue();
            for (int v : G.adj(head)) {
                if (dis[v] == -1) {
                    dis[v] = dis[head] + 1;
                    q.enqueue(v);
                }
            }
        }
        return dis;
    }
}