import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Arrays;

public class SAPTwoVertices {
//    private final List<Integer> path;
//    private       String        strPath ;
    private int           ancestor;
    private int           distance;

    private static final int INITIAL = -2;


    public SAPTwoVertices(Iterable<Integer> u, Iterable<Integer> v, Digraph G) {
        int[] disU   = new int[G.V()];
        int[] traceU = new int[G.V()];
        BFS(G, u, traceU, disU);

        int[] disV   = new int[G.V()];
        int[] traceV = new int[G.V()];
        BFS(G, v, traceV, disV);

        distance = -1;
        ancestor = -1;

        for (int i = 0; i < G.V(); i++) {
            if (traceU[i] != -1 && traceV[i] != -1) {
                if (distance == -1 || distance > disU[i] + disV[i]) {
                    distance = disU[i] + disV[i];
                    ancestor = i;
                }
            }
        }

        // Constructing path
//        path = new ArrayList<>();
//        if (ancestor != -1) {
//            int node = ancestor;
//            while (node != INITIAL) {
//                path.add(node);
//                node = traceU[node]; //path from ancestor to u
//            }
//            Collections.reverse(path);
//            node = ancestor;
//            node = traceV[node];
//            while (node != INITIAL) {
//                path.add(node);
//                node = traceV[node]; // path from ancestor to v
//            }
//        }
//        strPath = "";
//        for (int i = 0; i < path.size(); i++) {
//            strPath = strPath + path.get(i).toString();
////            System.out.println("#Debug " + i + ": " + path.get(i));
//            if (i != path.size() - 1) strPath = strPath + "-";
//        }
    }

    public int getAncestor() {return ancestor;}

    public int getDistance() {return distance;}

//    public String getStrPath() {
//        return (ancestor == -1) ? "-1" : strPath;
//    }
//    public List<Integer> getPath() {return (ancestor == -1) ? null : path;}

    private void BFS(Digraph G, Iterable<Integer> initial, int[] trace, int[] dis) {
        Arrays.fill(trace, -1);

        Queue<Integer> q = new Queue<>();

        for (Integer u : initial) {
            trace[u] = INITIAL; //
            q.enqueue(u);
        }

        while (!q.isEmpty()) {
            int u = q.dequeue();
            for (int v : G.adj(u)) {
                if (trace[v] == -1) {
                    trace[v] = u;
                    dis[v] = dis[u] + 1;
                    q.enqueue(v);
                }
            }
        }
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(12);
        G.addEdge(1, 0);
        G.addEdge(2, 0);
        G.addEdge(3, 1);
        G.addEdge(4, 1);
        G.addEdge(5, 1);
        G.addEdge(6, 3);
        G.addEdge(7, 3);
        G.addEdge(8, 5);
        G.addEdge(9, 5);
        G.addEdge(10, 9);
        G.addEdge(11, 9);
        G.addEdge(5, 2);
        G.addEdge(9, 2);
        SAPTwoVertices sap = new SAPTwoVertices(new ArrayList<>() {{
            add(9);
        }}, new ArrayList<>() {{
            add(2);
        }}, G);
//        System.out.println(sap.strPath);
    }
}