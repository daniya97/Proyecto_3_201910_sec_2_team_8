package model.util;

import model.data_structures.Arco;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IColaPrioridad;
import model.data_structures.InfoArco;
import model.data_structures.MaxHeapCP;
import model.data_structures.MinCP;
import model.data_structures.Queue;
import model.data_structures.UnionFind;

public class KruskalMST<K, V,IA extends InfoArco> {

	/**
	 * weight de MST
	 */	
	private double weight;                        // weight of MST

	/**
	 * Arcos en MST
	 */
	private Queue<Arco<IA>> mst = new Queue<>();  // edges in MST

	/**
	 * Obtener el MST de un grafo no dirigido con weights
	 */
	public KruskalMST(GrafoNDPesos<K, V, IA> G) {

		MinCP<Arco<IA>> pq = new MinCP<Arco<IA>>();
		for (Arco<IA> e : G.arcos()) {
			pq.agregar(e);
		}

		// Utiliza UNION FIND
		UnionFind uf = new UnionFind(G.V());
		while (!pq.esVacia() && mst.size() < G.V() - 1) {
			Arco<IA> e = pq.delMin();
			int v = e.either();
			int w = e.other(v);
			if (!uf.connected(v, w)) {

				//EVITAR CICLOS
				uf.union(v, w); 
				mst.enqueue(e);  
				weight += e.weight(1);
			}
		}

	}

	/**
	 * Retorna los arcos del Minnimum spanning tree
	 */
	public Iterable<Arco<IA>> arcos() {
		return mst;
	}

	/**
	 * Retorna el weight del MST
	 */
	public double weight() {
		return weight;
	}



}
