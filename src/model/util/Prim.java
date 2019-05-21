package model.util;

import java.util.concurrent.LinkedBlockingDeque;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IndexMinPQ;
import model.data_structures.InfoArco;
import model.data_structures.LinkedList;
import model.data_structures.Queue;
import model.data_structures.UnionFind;

public class Prim<K,V, IA extends InfoArco> {


	/**
	 * Guarda el arco con m�nimo peso desde un v�rtice del �rbol a uno fuera de el
	 */
	private ArregloDinamico<Arco<IA>> edgeTo;      
	
	/**
     *Peso del arco guardado en edgeTo
     */
	private double[] distTo;      
	
	/**
     * TRUE si v es parte del arbol, false de lo contrario
     */
	private boolean[] marcado;    
	
	
	/**
     * Cola de prioridad
     */
	private IndexMinPQ<Double> pq;

	/**
	 * MST a trav�s del algoritmo de PRIM
	 */
	public Prim(GrafoNDPesos<K, V, IA> G) {
		edgeTo = new ArregloDinamico<>(G.V());
		distTo = new double[G.V()];
		marcado = new boolean[G.V()];
		pq = new IndexMinPQ<Double>(G.V());
		for (int v = 0; v < G.V(); v++){
			distTo[v] = Double.POSITIVE_INFINITY;
			edgeTo.agregar(null);
		}

		
		//Se recorrer los v�rtices  
		for (int v = 0; v < G.V(); v++)     
			if (!marcado[v]) prim(G, v);     
	}

	/**
     * M�todo que realiza el procedimiento b�sico del algoritmo
     */
	private void prim(GrafoNDPesos<K, V, IA> G, int s) {
		distTo[s] = 0.0;
		pq.agregar(s, distTo[s]);
		while (!pq.esVacia()) {
			int v = pq.delMin();
			scan(G, v);
		}
	}

	/**
	 * Escanear el vértice v
	 */
	private void scan(GrafoNDPesos<K, V, IA> G, int v) {
		marcado[v] = true;
		LinkedList<Arco<IA>> aux = G.darRepresentacion().get(v);
		if(aux != null){
			for (Arco<IA> e : aux) {
				int w = e.other(v);
				if (marcado[w]) continue;         
				if (e.weight(1) < distTo[w]) {
					distTo[w] = e.weight(1);
					edgeTo.cambiarEnPos(w, e);
					if (pq.contains(w)) pq.decreaseK(w, distTo[w]);
					else                pq.agregar(w, distTo[w]);
				}
			}
		}

	}

	/**
     * Retorna los arcos pertenecientes al MST
     */
	public Iterable<Arco<IA>> arcos() {
		Queue<Arco<IA>> mst = new Queue<>();
		for (int v = 0; v < edgeTo.darTamano(); v++) {
			Arco<IA> e = edgeTo.darObjeto(v);
			if (e != null) {
				mst.enqueue(e);
			}
		}
		return mst;
	}

	/**
     * Retorna el peso del MST
     */
	public double weight() {
		double weight = 0.0;
		for (Arco<IA> e : arcos())
			weight += e.weight(1);
		return weight;
	}


}
