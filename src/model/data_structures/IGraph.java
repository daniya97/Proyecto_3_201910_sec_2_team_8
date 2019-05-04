package model.data_structures;

import java.util.Iterator;


public interface IGraph<K, IV, A extends IArco<K>> extends Iterable<K> {
	
	
	/**
	 * Retorna el n�mero de vertices en el grafo
	 * @return num vertices
	 */
	int V();
	
	/**
	 * Retorna el n�mero de arcos en el grafo
	 * @return num arcos
	 */
	int E();
	
	/**
	 * Adiciona un v�rtice con un ID �nico. La informaci�n est� en infovertex
	 */
	void addVertex(K idVertex, IV infoVertex);
	
	
	/**
	 * Adiciona un arco no dirigido entre iD vertexIni y idVertexFin con un ID �nico. La informaci�n est� en infoArc
	 */
	void addEdge(K idVertexIni, K idVertexFin, A infoArc);
	
	
	/**
	 * Obtener la informaci�n de un v�rtice
	 */
	IV getInfoVertex(K idVertex);
	
	
	/**
	 * Modificar la informaci�n del v�rtice idVertex
	 */
	void setInfoVertex(K idVertex, IV infoVertex);
	
	/**
	 * Obtiene la informaci�n acerca de un arco
	 */
	A getInfoArc(K idVertexIni, K idVertexFin);
	
	/**
	 * Modificar la informaci�n del arco eentre los v�rtices idVertexIni e idVertexFin
	 */
	void setInfoArc(K idVertexIni, K idVertexFin, A infoArc);
	
	/**
	 * Retorna los identificadores de los v�rtices adyacentes a idVertex
	 */
	Iterator<K> adj(K idVertex);
	
	
	
	/**
	 * Retorna el numero (int) del nodo dado el id
	 */
	public int encontrarNumNodo(K idVertex);

	
	
	
	/**
	 * Retorna el id del nodo (K) dado el numero del nodo
	 */
	public K encontrarNodo(int numNodo);
	
	public ITablaHash<Integer, LinkedList<A>> darRepresentacion();
	

}
