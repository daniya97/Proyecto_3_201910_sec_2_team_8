package model.data_structures;

public interface IArco<K> extends Comparable<IArco<K>> {

	public IArco (K nodoInicial, K nodoFinal);
	/**
	 * Cambia la informaci�n del arco
	 * 
	 * @param pNuevaInformacion nueva informaci�n a ser suministrada en el arco
	 */
	public void cambiarInformacion(IArco<K> pNuevaInformacion);

	 /**
	  * @return el peso del arco
	  */
	 public double weight();

	/**
	 * @return cualquiera de los arcos
	 */
	public K either() ;

	/**
	 * @param vertex
	 * @return retorna el otro v�rtice diferente al dado por par�metro
	 */
	public K other(K vertex);

	}
