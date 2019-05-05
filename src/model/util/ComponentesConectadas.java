package model.util;

import model.data_structures.Arco;
import model.data_structures.GrafoNDPesos;
import model.data_structures.InfoArco;
import model.data_structures.LinkedList;

public class ComponentesConectadas<K, V> {
	
	private boolean[] marked;   // marked[v] = has vertex v been marked?
    private int[] id;           // id[v] = id of connected component containing v
    private int[] size;         // size[id] = number of vertices in given component
    private int count;          // number of connected components



    /**
     * Computes the connected components of the edge-weighted graph {@code G}.
     *
     * @param G the edge-weighted graph
     */
    public <IA extends InfoArco> ComponentesConectadas(GrafoNDPesos<K, V, IA> G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }


    // depth-first search for an EdgeWeightedGraph
    private <IA extends InfoArco> void dfs(GrafoNDPesos<K, V, IA> G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        
        LinkedList<Arco<IA>> aux = G.darRepresentacion().get(v);
        for (Arco<IA> e : aux) {
            int w = e.other(v);
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }


    /**
     * Returns the component id of the connected component containing vertex {@code v}.
     *
     * @param  v the vertex
     * @return the component id of the connected component containing vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int id(int v) {
        return id[v];
    }

    /**
     * Returns the number of vertices in the connected component containing vertex {@code v}.
     *
     * @param  v the vertex
     * @return the number of vertices in the connected component containing vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int size(int v) {
        return size[id[v]];
    }

    /**
     * Returns the number of connected components in the graph {@code G}.
     *
     * @return the number of connected components in the graph {@code G}
     */
    public int count() {
        return count;
    }

    /**
     * Returns true if vertices {@code v} and {@code w} are in the same
     * connected component.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @return {@code true} if vertices {@code v} and {@code w} are in the same
     *         connected component; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @throws IllegalArgumentException unless {@code 0 <= w < V}
     */
    public boolean connected(int v, int w) {
        return id(v) == id(w);
    }




}
