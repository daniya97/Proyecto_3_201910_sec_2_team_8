package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ <K extends Comparable<K>> implements Iterable<Integer>{

	
	
	/**
     * Máximo número de elementos en PQ
     */
	private int maxN;  
	/**
     * Número de elementos en PQ
     */
    private int n;   
	/**
     * Binary HEAP
     */
    private int[] pq;  
	/**
     * Inversa de pq
     */
    private int[] qp;    
    
	/**
     * Si el vértice ya fue marcado o no
     */
    private K[] prioridad;     

    /**
     * Constructor
     */
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        prioridad = (K[]) new Comparable[maxN + 1];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

	/**
     * Retorna TRUE si la cola está vacía
     */
    public boolean esVacia() {
        return n == 0;
    }

	/**
     * Si contiene el elemento i o no
     */
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        return qp[i] != -1;
    }

	/**
     * Retorna el tamano de la cola
     */
    public int darNumElementos() {
        return n;
    }

	/**
     * Insertar un nuevo elemento
     */
    public void agregar(int i, K K) {
        n++;
        qp[i] = n;
        pq[n] = i;
        prioridad[i] = K;
        swim(n);
    }

	/**
     * Retorna el índice del elemento más pequeno
     */
    public int minIndex() {
        return pq[1];
    }

	/**
     * Retorna el mínimo K 
     */
    public K minK() {
        return prioridad[pq[1]];
    }

	/**
     * Elimina el mínimo K
     * Retorna su Index
     */
    public int delMin() {
    	
    	if(esVacia())return -1;
    	
        int min = pq[1];
        exch(1, n--);
        sink(1);
        assert min == pq[n+1];
        qp[min] = -1;        
        prioridad[min] = null;   
        pq[n+1] = -1;        
        return min;
    }

	/**
     * Retorna K del indice i 
     */
    public K darObjeto(int i) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        else return prioridad[i];
    }

	/**
     * Cambiar K 
     */
    public void cambiarK(int i, K K) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        prioridad[i] = K;
        swim(qp[i]);
        sink(qp[i]);
    }


	/**
     * Reducir la prioridad 
     */
    public void decreaseK(int i, K K) {
        prioridad[i] = K;
        swim(qp[i]);
    }

	/**
     * Aumentar la prioridad
     */
    public void increaseK(int i, K K) {
        prioridad[i] = K;
        sink(qp[i]);
    }

	/**
     * Método para eliminar
     */
    public void delete(int i) {
        int index = qp[i];
        exch(index, n--);
        swim(index);
        sink(index);
        prioridad[i] = null;
        qp[i] = -1;
    }


    
   /***************************************************************************
    * Funciones de AYUDA - AUXILIARES
    ***************************************************************************/
    private boolean greater(int i, int j) {
    	
    	if(i<0 || i>prioridad.length) return false;
    	if(j<0 || j>prioridad.length) return true;
    	if(prioridad[pq[i]] == null)return false;
    	if(prioridad[pq[j]] == null)return true;
    	
        return prioridad[pq[i]].compareTo(prioridad[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }


   /***************************************************************************
    * Heap helper functions.
    ***************************************************************************/
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }


   /***************************************************************************
    * Iterators.
    ***************************************************************************/

    /**
     * Itera sobre los elementos en orden ascendente por prioridad
     */
    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        private IndexMinPQ<K> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<K>(pq.length - 1);
            for (int i = 1; i <= n; i++)
                copy.agregar(pq[i], prioridad[pq[i]]);
        }

        public boolean hasNext()  { return !copy.esVacia();                     }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

}
