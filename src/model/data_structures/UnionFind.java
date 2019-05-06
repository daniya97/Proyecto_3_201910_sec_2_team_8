package model.data_structures;

public class UnionFind {


	/**
	 * Guarda el padre de i 
	 */
	private int[] padre; 
	
	/**
     * Devuelve el rank del subtree con raíz en i 
     */
	private byte[] rank; 
	
	/**
     * Número de componentes
     */
	private int num;     // number of components

	/**
     * Constructor
     */
	public UnionFind(int n) {
		num = n;
		padre = new int[n];
		rank = new byte[n];
		for (int i = 0; i < n; i++) {
			padre[i] = i;
			rank[i] = 0;
		}
	}

	/**
     * Retorna el identificar del 'site' 
     */
	public int find(int p) {
		while (p != padre[p]) {
			padre[p] = padre[padre[p]];    
			p = padre[p];
		}
		return p;
	}

	/**
     * Retorna el número de componentes
     */
	public int num() {
		return num;
	}

	/**
     * Retorna TRUE si p y q están conectadas
     */
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	/**
     * Método para unir p y q
     */
	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ) return;

		if      (rank[rootP] < rank[rootQ]) padre[rootP] = rootQ;
		else if (rank[rootP] > rank[rootQ]) padre[rootQ] = rootP;
		else {
			padre[rootQ] = rootP;
			rank[rootP]++;
		}
		num--;
	}


}
