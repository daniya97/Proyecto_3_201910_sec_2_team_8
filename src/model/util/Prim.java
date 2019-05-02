package model.util;

import java.util.concurrent.LinkedBlockingDeque;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IndexMinPQ;
import model.data_structures.LinkedList;
import model.data_structures.Queue;
import model.data_structures.UnionFind;

public class Prim<K,V> {
	
	//FALTA INDEXMINPQ
	
	 private static final double FLOATING_POINT_EPSILON = 1E-12;

	    private ArregloDinamico<Arco<K>> edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
	    private double[] distTo;      // distTo[v] = weight of shortest such edge
	    private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
	    private IndexMinPQ<Double> pq;

	    /**
	     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	     * @param G the edge-weighted graph
	     */
		public Prim(GrafoNDPesos<K, V> G) {
	    	edgeTo = new ArregloDinamico<>(G.V());
	        distTo = new double[G.V()];
	        marked = new boolean[G.V()];
	        pq = new IndexMinPQ<Double>(G.V());
	        for (int v = 0; v < G.V(); v++){
	            distTo[v] = Double.POSITIVE_INFINITY;
	            edgeTo.agregar(null);
	        }
	        
	        
	        System.out.println("T1:" +edgeTo.darTamano());
	        System.out.println("T2:" +distTo.length);
	        System.out.println("T3:" +marked.length);

	        for (int v = 0; v < G.V(); v++)      // run from each vertex to find
	            if (!marked[v]) prim(G, v);      // minimum spanning forest
	    }

	    // run Prim's algorithm in graph G, starting from vertex s
	    private void prim(GrafoNDPesos<K, V> G, int s) {
	        distTo[s] = 0.0;
	        pq.agregar(s, distTo[s]);
	        while (!pq.esVacia()) {
	            int v = pq.delMin();
	            scan(G, v);
	        }
	    }

	    // scan vertex v
	    private void scan(GrafoNDPesos<K, V> G, int v) {
	        marked[v] = true;
	        LinkedList<Arco<K>> aux = G.darRepresentacion().get(v);
	        for (Arco<K> e : aux) {
	            int w = e.other(v);
	            if (marked[w]) continue;         // v-w is obsolete edge
	            if (e.weight() < distTo[w]) {
	                distTo[w] = e.weight();
	                edgeTo.cambiarEnPos(w, e);
	                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
	                else                pq.agregar(w, distTo[w]);
	            }
	        }
	    }

	    /**
	     * Returns the edges in a minimum spanning tree (or forest).
	     * @return the edges in a minimum spanning tree (or forest) as
	     *    an iterable of edges
	     */
	    public Iterable<Arco<K>> arcos() {
	        Queue<Arco<K>> mst = new Queue<>();
	        for (int v = 0; v < edgeTo.darTamano(); v++) {
	        	Arco<K> e = edgeTo.darObjeto(v);
	            if (e != null) {
	                mst.enqueue(e);
	            }
	        }
	        return mst;
	    }

	    /**
	     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
	     * @return the sum of the edge weights in a minimum spanning tree (or forest)
	     */
	    public double weight() {
	        double weight = 0.0;
	        for (Arco<K> e : arcos())
	            weight += e.weight();
	        return weight;
	    }


	    // check optimality conditions (takes time proportional to E V lg* V)
	    private boolean check(GrafoNDPesos<K, V> G) {

	        // check weight
	        double totalWeight = 0.0;
	        for (Arco<K> e : G.arcos()) {
	            totalWeight += e.weight();
	        }
	        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
	            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
	            return false;
	        }

	        // check that it is acyclic
	        UnionFind uf = new UnionFind(G.V());
	        for (Arco<K> e : arcos()) {
	            int v = e.either(), w = e.other(v);
	            if (uf.connected(v, w)) {
	                System.err.println("Not a forest");
	                return false;
	            }
	            uf.union(v, w);
	        }

	        // check that it is a spanning forest
	        for (Arco<K> e : G.arcos()) {
	            int v = e.either(), w = e.other(v);
	            if (!uf.connected(v, w)) {
	                System.err.println("Not a spanning forest");
	                return false;
	            }
	        }

	        // check that it is a minimal spanning forest (cut optimality conditions)
	        for (Arco<K> e : G.arcos()) {

	            // all edges in MST except e
	            uf = new UnionFind(G.V());
	            for (Arco<K> f : arcos()) {
	                int x = f.either(), y = f.other(x);
	                if (f != e) uf.union(x, y);
	            }

	            // check that e is min weight edge in crossing cut
	            for (Arco<K> f : G.arcos()) {
	                int x = f.either(), y = f.other(x);
	                if (!uf.connected(x, y)) {
	                    if (f.weight() < e.weight()) {
	                        System.err.println("Edge " + f + " violates cut optimality conditions");
	                        return false;
	                    }
	                }
	            }

	        }

	        return true;
	    }

	

}
