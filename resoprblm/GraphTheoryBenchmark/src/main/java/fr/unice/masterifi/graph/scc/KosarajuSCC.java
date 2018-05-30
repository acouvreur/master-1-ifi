package fr.unice.masterifi.graph.scc;

import fr.unice.masterifi.graph.DirectedGraph;
import fr.unice.masterifi.graph.Graph;
import fr.unice.masterifi.graph.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class KosarajuSCC implements SCC {

    private Stack<Integer> S;
    private boolean[] visited;
    private int[] id;
    private int count;

    public KosarajuSCC(Graph g) {

        S = new Stack<>();
        id = new int[g.V()];
        for (int i = 0; i < g.V(); i++) {
            id[i] = 0;
        }
        visited = new boolean[g.V()];
        count = 0;

        List<Integer> elems = new ArrayList<>();
        for (int i = 0; i < g.V(); i++) {
            elems.add(i);
        }

        int current;
        while(!S.containsAll(elems)) {
            elems.removeAll(S);
            current = elems.get(0);
            dfs(g, current);
        }

        Graph transposed = g.reverse();

        for (int i = 0; i < g.V(); i++) {
            visited[i] = false;
        }
        while(!S.empty()) {
            count++;
            int v = S.pop();
            id[v] = count;
            dfs2(transposed, v);
        }
    }

    private void dfs2(Graph transposed, int v) {
        visited[v] = true;
        for(Integer a : transposed.adj(v)) {
            if(!visited[a]) {
                dfs2(transposed, a);
            }
        }
        id[v] = count;
        S.remove(((Integer) v));
    }

    private void dfs(Graph g, int u) {
        visited[u] = true;
        for(Integer a : g.adj(u)) {
            if(!visited[a]) {
                dfs(g, a);
            }
        }
        S.add(u);
    }

    @Override
    public boolean isStronglyConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    private void validateVertex(int v) {
        /*int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));*/
    }
}
