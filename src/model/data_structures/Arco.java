package model.data_structures;

public class Arco<IA extends InfoArco> implements Comparable<Arco<? extends InfoArco>> {

	private int v;
	private int w;
	private IA informacion;

	/**
	 * @param v            nodo v
	 * @param w            nodo w
	 * @param pInformacion informaci�n del arco inicializa un arco de v a w
	 */
	public Arco(int v, int w, IA pInformacion) {
		this.v = v;
		this.w = w;
		this.informacion = pInformacion;
	}

	/**
	 * @return la informaci�n pertinente del arco
	 */
	public IA darInformacion() {
		return informacion;
	}

	/**
	 * Cambia la informaci�n del arco
	 * 
	 * @param pNuevaInformacion nueva informaci�n a ser suministrada en el arco
	 */
	public void cambiarInformacion(IA pNuevaInformacion) {
		informacion = pNuevaInformacion;
	}
	/*   
	*//**
		 * @return el id del arco
		 */
	/*
	 * public int darIdArco(){ return informacion.darIdArco(); }
	 * 
	 * 
	 /**
	  * @return el peso del arco
	  */
	 public double weight() { return informacion.darPesoArco(); }

	/**
	 * @return cualquiera de los arcos
	 */
	public int either() {
		return v;
	}

	/**
	 * @param vertex
	 * @return retorna el otro v�rtice diferente al dado por par�metro
	 */
	public int other(int vertex) {
		if (vertex == v)
			return w;
		else if (vertex == w)
			return v;
		else
			throw new IllegalArgumentException("Illegal endpoint");
	}

	/**
	 * Comparador entre arcos por su peso
	 */
	@Override
	public int compareTo(Arco<? extends InfoArco> arg0) {
		return Double.compare(this.informacion.darPesoArco(), arg0.informacion.darPesoArco());
	}}
