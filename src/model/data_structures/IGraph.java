package model.data_structures;

import java.util.Iterator;


public interface IGraph<K, IV, IA extends InfoArco> extends Iterable<K> {
	
	
	/**
	 * Retorna el nï¿½mero de vertices en el grafo
	 * @return num vertices
	 */
	int V();
	
	/**
	 * Retorna el nï¿½mero de arcos en el grafo
	 * @return num arcos
	 */
	int E();
	
	/**
	 * Adiciona un vï¿½rtice con un ID ï¿½nico. La informaciï¿½n estï¿½ en infovertex
	 */
	void addVertex(K idVertex, IV infoVertex);
	
	
	/**
	 * Adiciona un arco no dirigido entre iD vertexIni y idVertexFin con un ID ï¿½nico. La informaciï¿½n estï¿½ en infoArc
	 */
	void addEdge(K idVertexIni, K idVertexFin, IA infoArc);
	
	
	/**
	 * Obtener la informaciï¿½n de un vï¿½rtice
	 */
	IV getInfoVertex(K idVertex);
	
	
	/**
	 * Modificar la informaciï¿½n del vï¿½rtice idVertex
	 */
	void setInfoVertex(K idVertex, IV infoVertex);
	
	/**
	 * Obtiene la informaciï¿½n acerca de un arco
	 */
	IA getInfoArc(K idVertexIni, K idVertexFin);
	
	/**
	 * Modificar la informaciï¿½n del arco eentre los vï¿½rtices idVertexIni e idVertexFin
	 */
	void setInfoArc(K idVertexIni, K idVertexFin, IA infoArc);
	
	/**
	 * Retorna los identificadores de los vï¿½rtices adyacentes a idVertex
	 */
	Iterator<K> adj(K idVertex);
	
	/**
	 * Permite iterar sobre los arcos
	 */
	Iterable<Arco<IA>> arcos();
	
	/**
	 * Retorna el numero (int) del nodo dado el id
	 */
	public int encontrarNumNodo(K idVertex);

	
	
	
	/**
	 * Retorna el id del nodo (K) dado el numero del nodo
	 */
	public K encontrarNodo(int numNodo);
	
	
	/**
	 * Retorna la representación de matrix de adyacencia
	 */
	public ITablaHash<Integer, LinkedList<Arco<IA>>> darRepresentacion();
	
	
	/**
	 * Retorna la información de los nodos
	 */
	public  IArregloDinamico<IV> vertices();

}
