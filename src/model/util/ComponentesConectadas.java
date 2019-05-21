package model.util;

import java.math.BigInteger;

import model.data_structures.Arco;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IGraph;
import model.data_structures.InfoArco;
import model.data_structures.LinkedList;
import model.data_structures.Queue;
import model.logic.InfoInterseccion;
import model.logic.PesosDIVArco;

public class ComponentesConectadas<K, V> {

	/**
	 * Si el v�rtice ya fue marcado o no
	 */
	private boolean[] marcado;   
	/**
	 *ID de la componente asociada al v�rtice
	 */
	private int[] id;          
	/**
	 * N�mero de v�rtices en la componente
	 */
	private int[] tamano;        
	/**
	 * N�mero de componentes conectadas en el grafo
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
	private <IA extends InfoArco> void dfs(IGraph<K, V, IA> G, int s) {
		Queue<Integer> q = new Queue<Integer>();
        marcado[s] = true;
        id[s] = num;
     
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            LinkedList<Arco<IA>> aux = G.darRepresentacion().get(s);
            for (Arco<IA> e : aux) {
            	int w = e.other(s);
                if (!marcado[w]) {
                    marcado[w] = true;
                    q.enqueue(w);
                }
            }
        }
	}


	/**
	 * Retorna el id de la componente que contiene al v�rtice v
	 */
	public int id(int v) {
		if(id == null) return 0;
		return id[v];
	}

	/**
	 * Retorna el n�mero de v�rtices en la componente que contiene al v�rtice dado por par�metro
	 */
	public int tamano(int v) {
		if(tamano.length == 0) return 0;
		return tamano[id[v]];
	}

	/**
	 * Retorna el n�mero de componentes conectadas en el grafo
	 */
	public int numComponentes() {
		if(id == null) return 0;
		return num;
	}

	/**
	 * Retorna TRUE si los v�rtices v y w est�n en la misma componente conectada
	 * Verifica si dos v�rtices est�n o no conectados
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
