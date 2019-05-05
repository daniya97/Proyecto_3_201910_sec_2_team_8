package model.util;

import model.data_structures.Arco;
import model.data_structures.GrafoNDPesos;
import model.data_structures.InfoArco;
import model.data_structures.LinkedList;

public class ComponentesConectadas<K, V> {

	/**
	 * Si el vértice ya fue marcado o no
	 */
	private boolean[] marcado;   
	/**
	 *ID de la componente asociada al vértice
	 */
	private int[] id;          
	/**
	 * Número de vértices en la componente
	 */
	private int[] tamano;        
	/**
	 * Número de componentes conectadas en el grafo
	 */
	private int num;         



	/**
	 * Obtiene las componentes conectadas dado un grafo no dirigido
	 */
	public <IA extends InfoArco> ComponentesConectadas(GrafoNDPesos<K, V, IA> G) {
		marcado = new boolean[G.V()];
		id = new int[G.V()];
		tamano = new int[G.V()];
		for (int v = 0; v < G.V(); v++) {
			if (!marcado[v]) {
				dfs(G, v);
				num++;
			}
		}
	}


	/**
	 * DEPTH FIRST SEARCH  sobre el grafo 
	 */
	private <IA extends InfoArco> void dfs(GrafoNDPesos<K, V, IA> G, int v) {
		marcado[v] = true;
		id[v] = num;
		tamano[num]++;

		LinkedList<Arco<IA>> aux = G.darRepresentacion().get(v);
		for (Arco<IA> e : aux) {
			int w = e.other(v);
			if (!marcado[w]) {
				dfs(G, w);
			}
		}
	}


	/**
	 * Retorna el id de la componente que contiene al vértice v
	 */
	public int id(int v) {
		if(id == null) return 0;
		return id[v];
	}

	/**
	 * Retorna el número de vértices en la componente que contiene al vértice dado por parámetro
	 */
	public int tamano(int v) {
		if(tamano.length == 0) return 0;
		return tamano[id[v]];
	}

	/**
	 * Retorna el número de componentes conectadas en el grafo
	 */
	public int numComponentes() {
		if(id == null) return 0;
		return num;
	}

	/**
	 * Retorna TRUE si los vértices v y w están en la misma componente conectada
	 * Verifica si dos vértices están o no conectados
	 */
	public boolean connected(int v, int w) {
		
		if(id == null|| id.length == 0) return false;
		if(v>id.length || w >id.length) return false;
		return id(v) == id(w);
	}


}
