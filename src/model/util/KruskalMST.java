package model.util;

import model.data_structures.Arco;
import model.data_structures.GrafoNDPeso;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IColaPrioridad;
import model.data_structures.MaxHeapCP;
import model.data_structures.MinCP;
import model.data_structures.Queue;
import model.data_structures.UnionFind;
import model.logic.infoArco;

public class KruskalMST<K, V> {
	
		//FALTA MINPQ!!!!
	
	    private double weight;                        // weight of MST
	    private Queue<Arco<K>> mst = new Queue<>();  // edges in MST

	    /**
	     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	     * @param G the edge-weighted graph
	     */
	    public KruskalMST(GrafoNDPesos<K, V> G) {
	    	
	        MinCP<Arco<K>> pq = new MinCP<Arco<K>>();
	        for (Arco<K> e : G.arcos()) {
	            pq.agregar(e);
	        }

	        // run greedy algorithm
	        UnionFind uf = new UnionFind(G.V());
	        while (!pq.esVacia() && mst.size() < G.V() - 1) {
	            Arco<K> e = pq.delMax();
	            int v = e.either();
	            int w = e.other(v);
	            if (!uf.connected(v, w)) { // v-w does not create a cycle
	                uf.union(v, w);  // merge v and w components
	                mst.enqueue(e);  // add edge e to mst
	                weight += e.weight();
	            }
	        }

	    }

	    /**
	     * Returns the edges in a minimum spanning tree (or forest).
	     * @return the edges in a minimum spanning tree (or forest) as
	     *    an iterable of edges
	     */
	    public Iterable<Arco<K>> edges() {
	        return mst;
	    }

	    /**
	     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
	     * @return the sum of the edge weights in a minimum spanning tree (or forest)
	     */
	    public double weight() {
	        return weight;
	    }
	    
	

}
