package model.data_structures;

import java.util.Iterator;
/**
 * Lista encadenada implementa ILinkedList
 */
public class LinkedList <K> implements ILinkedList<K>{
	
	
	private int tamano;
	
	/**
	 * Guarda el primer nodo
	 */
	private Nodo<K> primero;
	
	/**
	 * Constructor
	 */
	public LinkedList(K primerNodo) {
		primero = new Nodo<>(primerNodo);
		tamano = 1;
	}
	
	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<K>() {

			Nodo<K> actual = primero;
			
			@Override
			public boolean hasNext() {
				if(actual!=null){
					return true;
				}else{
					return false;
				}
			}

			@Override
			public K next() {
			
			Nodo<K> auxiliar = actual;
			actual = actual.darSiguiente();
			return auxiliar.darObjeto();
				// TODO Auto-generated method stub
			}
		};
	}

	
	/**
	 * Se agregan elementos en la primera posición pues es más eficiente
	 */
	@Override
	public void agregarElementoPrimeraPosicion(K pNuevo){
		
		Nodo<K> nuevo = new Nodo<>(pNuevo);
		K auxiliar = primero.darObjeto();
		
		
		if (primero.equals(null))
			primero = nuevo;
		else
		{
		nuevo.cambiarSiguiente(primero);
		primero = nuevo;
		}
		
		tamano++;
	}
	

	/**
	 * Se obtienen el tamaño de la lista
	 */
	@Override
	public int darTamanoLista() {
		return tamano;
	}


	/**
	 * Se obtiene el primer nodo
	 */
	@Override
	public Nodo<K> darPrimerNodo() {
		return primero;
	}
	

}
