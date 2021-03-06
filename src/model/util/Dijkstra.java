package model.util;

import java.math.BigInteger;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IGraph;
import model.data_structures.IndexMinPQ;
import model.data_structures.InfoArco;
import model.data_structures.LinkedList;
import model.data_structures.Stack;
import model.logic.InfoInterseccion;
import model.logic.PesosDIVArco;

public class Dijkstra<K,IV, IA extends InfoArco> {

	
	private static final int PESO_DISTANCIA = 1;
	private static final int PESO_INFRACCIONES = 2;
	
	
	/**
	 * Guarda la Distancia m�nima desde s hasta v
	 */
	private double[] distTo;          
	/**
	 * �ltimo v�rtices del SP de s hasta v
	 */
	private Arco<IA>[] edgeTo;            

	/**
	 * Cola de prioridad con los v�rtices
	 */
	private IndexMinPQ<Double> pq;   
	
	
	private int tipoPeso;

	/**
	 *Algoritmo de Dijkstra. Obtiene la ruta m�s corta desde el v�rtice s (SOURCE) hasta
	 *todos los dem�s v�rtices del grafo
	 */
	public Dijkstra(IGraph<K, IV, IA> grafoIntersecciones, int s, int pTipoPeso) {

		tipoPeso = pTipoPeso;
		if(grafoIntersecciones.V() == 0){}
		else{
		
			distTo = new double[grafoIntersecciones.V()];
			edgeTo = (Arco<IA>[]) new Arco[grafoIntersecciones.V()];
			

			for (int v = 0; v < grafoIntersecciones.V(); v++)
				distTo[v] = Double.POSITIVE_INFINITY;
			distTo[s] = 0.0;

			// Relajar los v�rtices
			pq = new IndexMinPQ<Double>(grafoIntersecciones.V());
			pq.agregar(s, distTo[s]);
			while (!pq.esVacia()) {
				int v = pq.delMin();
				for(Arco<IA> e: grafoIntersecciones.darRepresentacion().get(v)){
					relax(e, v);
				}
			}

		}
	}
	// Relajar el v�rtice y cambiar la PQ si cambia
	private void relax(Arco<IA> e, int v) {
		int w = e.other(v);
		if (distTo[w] > distTo[v] + e.weight(tipoPeso)) {
			distTo[w] = distTo[v] + e.weight(tipoPeso);
			edgeTo[w] = e;
			if (pq.contains(w)) pq.decreaseK(w, distTo[w]);
			else                pq.agregar(w, distTo[w]);
		}
	}

	/**
	 * Retorna la distancia desde s hasta v
	 */
	public double distTo(int v) {
		if(distTo == null) return Double.POSITIVE_INFINITY;
		if(v>distTo.length) return Double.POSITIVE_INFINITY;
		return distTo[v];
	}

	/**
	 * Retorna TRUE si existe un camino desde s hasta v
	 */
	public boolean existeCaminoHasta(int v) {
		if(distTo == null) return false;
		if(v>distTo.length) return false;
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	/**
	 * Retorna los arcos del camino m�s corto entre s y v
	 */
	public Iterable<Arco<IA>> caminoA(int v) {
		if (!existeCaminoHasta(v)) return null;
		Stack<Arco<IA>> camino = new Stack<>();
		int x = v;
		for (Arco<IA> e = edgeTo[v]; e != null; e = edgeTo[x]) {
			camino.push(e);
			x = e.other(x);
		}
		return camino;
	}

}
