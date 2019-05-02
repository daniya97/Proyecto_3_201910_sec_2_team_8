package model.data_structures;

import model.logic.infoArco;

public class Arco implements Comparable<Arco> {

	private int v;
	private int w;
	private infoArco informacion;
	
	 /**
	  * @param v nodo v
	  * @param w nodo w
	  * @param pInformacion información del arco
	  * inicializa un arco de v a w 
	  */
    public Arco(int v, int w, infoArco pInformacion) {
        this.v = v;
        this.w = w;
        this.informacion = pInformacion;
    }

    /**
     * @return la información pertinente del arco
     */
    public infoArco darInformacion(){
    	return informacion;
    }
    
    /**
     * Cambia la información del arco
     * @param pNuevaInformacion nueva información a ser suministrada en el arco
     */
    public void cambiarInformacion(infoArco pNuevaInformacion){
    	informacion = pNuevaInformacion;
    }
    
    /**
     * @return el id del arco
     */
    public int darIdArco(){
    	return informacion.darIdArco();
    }
    
    
    /**
     * @return el peso del arco
     */
    public double weight() {
        return informacion.darPesoArco();
    }

    /**
     * @return cualquiera de los arcos
     */
    public int either() {
        return v;
    }


    /**
     * @param vertex 
     * @return retorna el otro vértice diferente al dado por parámetro
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

	
	/**
	 * Comparador entre arcos por su pedo
	 */
	@Override
	public int compareTo(Arco arg0) {
		// TODO Auto-generated method stub
		return Double.compare(this.informacion.darPesoArco(), arg0.informacion.darPesoArco());
	}
		

}
