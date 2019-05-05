package model.util;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IndexMinPQ;
import model.data_structures.InfoArco;
import model.data_structures.LinkedList;
import model.data_structures.Stack;

public class Dijkstra<K,V, IA extends InfoArco> {


	private double[] distTo;          // distTo[v] = distance  of shortest s->v path
	private ArregloDinamico<Arco<IA>> edgeTo;            // edgeTo[v] = last edge on shortest s->v path
	private IndexMinPQ<Double> pq;    // priority queue of vertices

	/**
	 * Computes a shortest-paths tree from the source vertex {@code s} to every
	 * other vertex in the edge-weighted graph {@code G}.
	 *
	 * @param  G the edge-weighted digraph
	 * @param  s the source vertex
	 * @throws IllegalArgumentException if an edge weight is negative
	 * @throws IllegalArgumentException unless {@code 0 <= s < V}
	 */
	public Dijkstra(GrafoNDPesos<K, V, IA> G, int s) {
		for (Arco<IA> e : G.arcos()) {
			if (e.weight() < 0)
				throw new IllegalArgumentException("edge " + e + " has negative weight");
		}

		distTo = new double[G.V()];
		edgeTo = new ArregloDinamico<>();

		validateVertex(s);

		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0.0;

		// relax vertices in order of distance from s
		pq = new IndexMinPQ<Double>(G.V());
		pq.agregar(s, distTo[s]);
		while (!pq.esVacia()) {
			int v = pq.delMin();
			LinkedList<Arco<IA>> aux = G.darRepresentacion().get(v);
			for (Arco<IA> e : aux) {
				relax(e, v);
			}

		}
	}

	// relax edge e and update pq if changed
	private void relax(Arco<IA> e, int v) {
		int w = e.other(v);
		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo.cambiarEnPos(w, e); 
			if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
			else                pq.agregar(w, distTo[w]);
		}
	}

	/**
	 * Returns the length of a shortest path between the source vertex {@code s} and
	 * vertex {@code v}.
	 *
	 * @param  v the destination vertex
	 * @return the length of a shortest path between the source vertex {@code s} and
	 *         the vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public double distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}

	/**
	 * Returns true if there is a path between the source vertex {@code s} and
	 * vertex {@code v}.
	 *
	 * @param  v the destination vertex
	 * @return {@code true} if there is a path between the source vertex
	 *         {@code s} to vertex {@code v}; {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	/**
	 * Returns a shortest path between the source vertex {@code s} and vertex {@code v}.
	 *
	 * @param  v the destination vertex
	 * @return a shortest path between the source vertex {@code s} and vertex {@code v};
	 *         {@code null} if no such path
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public Iterable<Arco<IA>> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		Stack<Arco<IA>> path = new Stack<>();
		int x = v;
		for (Arco<IA> e = edgeTo.darObjeto(v); e != null; e = edgeTo.darObjeto(x)) {
			path.push(e);
			x = e.other(x);
		}
		return path;
	}


	// check optimality conditions:
	// (i) for all edges e = v-w:            distTo[w] <= distTo[v] + e.weight()
	// (ii) for all edge e = v-w on the SPT: distTo[w] == distTo[v] + e.weight()
	private boolean check(GrafoNDPesos<K, V, IA> G, int s) {

		// check that edge weights are nonnegative
		for (Arco<IA> e : G.arcos()) {
			if (e.weight() < 0) {
				System.err.println("negative edge weight detected");
				return false;
			}
		}

		// check that distTo[v] and edgeTo[v] are consistent
		if (distTo[s] != 0.0 || edgeTo.darObjeto(s) != null) {
			System.err.println("distTo[s] and edgeTo[s] inconsistent");
			return false;
		}
		for (int v = 0; v < G.V(); v++) {
			if (v == s) continue;
			if (edgeTo.darObjeto(v) == null && distTo[v] != Double.POSITIVE_INFINITY) {
				System.err.println("distTo[] and edgeTo[] inconsistent");
				return false;
			}
		}

		// check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
		for (int v = 0; v < G.V(); v++) {
			LinkedList<Arco<IA>> aux = G.darRepresentacion().get(v);
	        for (Arco<IA> e : aux) {
				int w = e.other(v);
				if (distTo[v] + e.weight() < distTo[w]) {
					System.err.println("edge " + e + " not relaxed");
					return false;
				}
			}
		}

		// check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
		for (int w = 0; w < G.V(); w++) {
			if (edgeTo.darObjeto(w) == null) continue;
			Arco<IA> e = edgeTo.darObjeto(w);
			if (w != e.either() && w != e.other(e.either())) return false;
			int v = e.other(w);
			if (distTo[v] + e.weight() != distTo[w]) {
				System.err.println("edge " + e + " on shortest path not tight");
				return false;
			}
		}
		return true;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = distTo.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}
}
