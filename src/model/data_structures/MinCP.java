package model.data_structures;

import java.util.Iterator;

import model.util.Sort;

public class MinCP <T extends Comparable<T>> {
	
	
	/**
     * Guarda la cola de prioridad
     */
	private ArregloDinamico<T> cp;
	/**
     * Guarda la cola de forma ordenada
     */
	private ArregloDinamico<T> ordenado;
	
	
	
	/**
     * Constructor
     */
	public MinCP(){
		cp = new ArregloDinamico<T>();
		cp.agregar(null);
	}
	
	/**
     * Iterador sobre los elementos de la cola
     */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int iActual = 1;
			@Override
			public boolean hasNext() {
				return iActual < cp.darTamano();
			}
			@Override
			public T next() {
				return cp.darObjeto(iActual++);
			}
		};
	}

	
	/**
     * TRUE si la cola no tiene elementos
     */
	public boolean esVacia() {
		if(cp.darTamano() == 1) return true;
		return false;
	}
	
	/**
     * Dar número de elementos
     */
	public int darNumElementos() {
		return cp.darTamano()-1;
	}
	

	
	/**
     * Agregar un elemento a la cola
     */
	public void agregar(T t){
		cp.agregar(t);
		swim(darNumElementos());
		ordenado = null;
	}

	
	/**
     * Método que elimina el menor elemento
     */
	public T delMin() {
		if(darNumElementos() ==0) return null;
		T min = cp.darObjeto(1);
		exch(1,darNumElementos());
		cp.eliminarEnPos(darNumElementos());
		sink(1);
		ordenado = null;
		
		return min;
	}
	
	/**
     * Da el menor elemento
     */
	public T min() {
		return cp.darObjeto(1);
	}
	
	
	/**
     * SWIM 
     */
	private void swim (int k){
		
		while(k>1 && greater(k/2,k)){
			exch(k,k/2);
			k = k/2;
		}
		
	}
	
	/**
     * SINK
     */
	private void sink(int k){
		
		int N = darNumElementos();
		while(2*k<=N){
			int j = 2*k;
			if(j<N && greater(j,j+1)) j++;
			if(!greater(k,j)) break;
			exch(k,j);
			k = j;
		}
	}
	
	
	/**
     * Para saber si un elemento es más grande que otro
     */
	private boolean greater (int i, int j){
		return cp.darObjeto(i).compareTo(cp.darObjeto(j))>0;
	}
	
	
	
	/**
     * Para cambiar dos elementos
     */
	private void exch(int i, int j){
	T auxiliar = cp.darObjeto(i);
	cp.cambiarEnPos(i, cp.darObjeto(j));	
	cp.cambiarEnPos(j, auxiliar);
	}

	
	/**
     *Itera en orden sobre los elementos
     */
	public Iterable<T> iterableEnOrden(){
		return new Iterable<T>() {
			
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					int iSiguiente = cp.darTamano() - 2;
					
					@Override
					public boolean hasNext() {
						if (ordenado == null) {
							hacerCopiaOrdenada();
						}
							
						return iSiguiente >= 0;
					}

					@Override
					public T next() {
						return ordenado.darObjeto(iSiguiente--);
					}
					
					private void hacerCopiaOrdenada(){
						ordenado = new ArregloDinamico<T>(cp.darTamano());
						
						// Crea una copia del arreglo en el sentido que contiene los mismos objetos, pero si utilizo cambiarEnPos o agregar en cp, no me afecta en nada esta copia 
						for (T dato : cp){
							if (dato != null) ordenado.agregar(dato);
						}
						//int n = copiaOrdenada.darTamano();
						
						// Ordena de menor a mayor
						Sort.ordenarHeapSorted(ordenado);
					}
					
				};
			}
		};
	}
	
	

}
