import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validate(v);
        validate(w);
        SAP_BFS bfs = new SAP_BFS();
        bfs.BFS(v, w, G);
        return bfs.getDistance();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validate(v);
        validate(w);
        SAP_BFS bfs = new SAP_BFS();
        bfs.BFS(v, w, G);
        return bfs.getAncestor();
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v);
        validate(w);
        SAP_BFS bfs = new SAP_BFS();
        bfs.BFS(v, w, G);
        return bfs.getDistance();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v);
        validate(w);
        SAP_BFS bfs = new SAP_BFS();
        bfs.BFS(v, w, G);
        return bfs.getAncestor();
    }

    private void validate(Integer vertex) {
        if (vertex == null || vertex < 0 || vertex >= G.V()) throw new IllegalArgumentException();
    }

    private void validate(Iterable<Integer> vertexSet) {
        if (vertexSet == null) throw new IllegalArgumentException();
        for (Integer x : vertexSet) validate(x);
    }
}