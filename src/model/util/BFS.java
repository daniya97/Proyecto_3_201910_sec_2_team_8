package model.util;

import java.util.Iterator;

import model.data_structures.GrafoNDPesos;
import model.data_structures.InfoArco;
import model.data_structures.Queue;
import model.data_structures.Stack;

public class BFS<K, IV, IA extends InfoArco> {
	
	
	private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // Si se puede acceder al vértice v o no
    private int[] edgeTo;     //Último arco para llegar a v
    private int[] distTo;      // Número de arcos para llegar al vértice v

    /**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BFS(GrafoNDPesos<K, IV, IA> G, int s) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        bfs(G, s);
    }


    // breadth-first search from a single source
    private void bfs(GrafoNDPesos<K, IV, IA> G, int s) {
        Queue<Integer> q = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            Iterator<K> iterador = G.adj(G.encontrarNodo(v));
            K elemento;
            
            while(iterador.hasNext()){
            	elemento = iterador.next();
            	int numero = G.encontrarNumNodo(elemento);
            	if (!marked[numero]) {
                    edgeTo[numero] = v;
                    distTo[numero] = distTo[v] + 1;
                    marked[numero] = true;
                    q.enqueue(numero);
                }
            }
        }
    }


    /**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int v) {
        return distTo[v];
    }

    /**
     * Returns a shortest path between the source vertex {@code s} (or sources)
     * and {@code v}, or {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }
	

}
