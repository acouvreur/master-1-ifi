package fr.unice.masterifi.graph;

public class DirectedDFS {
    private boolean[] marked;  // marked[v] = true iff v is reachable from source(s)
    private int count;         // number of vertices reachable from source(s)

    public DirectedDFS(DirectedGraph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    public DirectedDFS(DirectedGraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        validateVertices(sources);
        for (int v : sources) {
            if (!marked[v]) dfs(G, v);
        }
    }

    private void dfs(DirectedGraph G, int v) {
        count++;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    public int count() {
        return count;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }

}
