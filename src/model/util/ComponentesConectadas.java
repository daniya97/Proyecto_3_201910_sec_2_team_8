package model.util;

import java.math.BigInteger;

import model.data_structures.Arco;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IGraph;
import model.data_structures.InfoArco;
import model.data_structures.LinkedList;
import model.logic.InfoInterseccion;
import model.logic.PesosDIVArco;

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
	public <IA extends InfoArco> ComponentesConectadas(IGraph<K, V, IA> grafo) {
		marcado = new boolean[grafo.V()];
		id = new int[grafo.V()];
		tamano = new int[grafo.V()];
		for (int v = 0; v < grafo.V(); v++) {
			if (!marcado[v]) {
				dfs(grafo, v);
				num++;
			}
		}
	}


	/**
	 * DEPTH FIRST SEARCH  sobre el grafo 
	 */
	private <IA extends InfoArco> void dfs(IGraph<K, V, IA> G, int v) {
		marcado[v] = true;
		id[v] = num;
		tamano[num]++;

		LinkedList<Arco<IA>> aux = G.darRepresentacion().get(v);
		//CAMBIOOOOOOO
		if(aux!=null){
			for (Arco<IA> e : aux) {
				int w = e.other(v);
				if (!marcado[w]) {
					dfs(G, w);
				}
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

	public int idComponenteMasGrande(){

		int maximo = 0;
		int idMax = -1;
		for (int i = 0; i < tamano.length; i++) {
			if(tamano[i]>=maximo){
				maximo = tamano[i];
				idMax = i;
			}
		}
		return idMax;
	}


}
