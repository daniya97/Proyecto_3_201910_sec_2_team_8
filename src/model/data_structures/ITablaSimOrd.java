package model.data_structures;

import java.util.Iterator;

public interface ITablaSimOrd<K extends Comparable<? super K>, V> extends Iterable<K> {// extends ITablaHash<K, V> {
	
	
	/**
	 * Retorna true si la tabla est� vac�a
	 * Retorna false si hay al menos un elemento
	 */
	boolean isEmpty();
	
	
	/**
	 * Inserta un nuevo elemento en la tabla 
	 * @param Key la llave del nuevo elemento
	 * @param Value el valor asociado a la llave
	 */
	void put (K key, V value);
	
	
	/**
	 * Busca un elemento dentro de la tabla 
	 * @param Key la llave a buscar en la tabla
	 */
	V get(K key);

	/**
	 * Elimina un elemento de la tabla
	 * @param Key la llave a eliminar en la tabla
	 */
	V delete(K key);
	
	
	/**
	 * Da el tama�o de la tabla
	 */
	int darTamano();
	
	
	/**
	 * Retorna true si la tabla contiene la llave
	 * Retorna false si la tabla no contiene la llave
	 * @param key: la llave a buscar
	 */
	boolean contains(K key);
	
	
	/**
	 * Retorna la llave de menor valor
	 */
	K min();
	
	/**
	 * Retorna la llave de mayor valor
	 */
	K max();
	
	/**
	 * Elimina la llave del menor valor
	 */
	void deleteMin();
	
	
	/**
	 * Retorna la llave m�s grande menor o igual a la llave dada por par�metro
	 */
	K floor(K key);
	
	/**
	 * Retorna la llave m�s peque�a mayor o igual a la llave dada por par�metro
	 */
	K ceiling(K key);
	
	
	/**
	 * Selecciona la llave de rank num
	 */
	K select(int num);
	
	
	/**
	 * N�mero de llaves menores que llave dada por par�metro
	 */
	int rank(K key);
	
	
	/**
	 * Altura del �rbol
	 */
	int height();
	
	
	/**
	 * Altura de una llave
	 */
	int getHeight(K key);
	
	
	/**
	 * N�mero de elementos en el �rbol 
	 */
	int darNumeroParejas();
	
	
	/**
	 * Las llaves entre las llaves init y end, en orden
	 */
	Iterable<K> keysInRange(K init, K end); 
	
	
	/**
	 * Los valores entre las llaves init y end, en orden
	 */
	Iterable<V> valuesInRange(K init, K end);
}
