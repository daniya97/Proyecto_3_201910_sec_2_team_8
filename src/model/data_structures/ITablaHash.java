package model.data_structures;

public interface ITablaHash<K, V> extends Iterable<K> {
	
	void put(K key,	V value);
	
	V get(K key);
	
	V delete(K key);
	
	int darTamano();
}
