package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ <T extends Comparable<T>> implements Iterable<Integer>{

	private ArregloDinamico<Integer> cp;
	private ArregloDinamico<Integer> pc;
	private ArregloDinamico<T> keys;
	private int n;
	private int maxN;        // maximum number of elements on PQ

	public IndexMinPQ(int maxN) {
		if (maxN < 0) throw new IllegalArgumentException();
		this.maxN = maxN;
		n = 0;
		keys =  new ArregloDinamico<>(maxN+1);
		cp = new ArregloDinamico<>(maxN+1);
		pc = new ArregloDinamico<>(maxN+1);
		
		for(int i = 0; i<= maxN;i++){
			pc.agregar(-1);
			cp.agregar(-1);
			keys.agregar(null);
			
		}
	}


	public boolean esVacia() {
		// TODO Auto-generated method stub
		return n == 0;
	}

	public int darNumElementos() {
		// TODO Auto-generated method stub
		return n;
	}

	public boolean contains(int i) {
		if (i < 0 || i >= maxN) throw new IllegalArgumentException();
		return pc.darObjeto(i) != -1;
	}

	public void agregar(int i, T llave) {

		if (i < 0 || i >= maxN) throw new IllegalArgumentException();
		if (contains(i)){
			throw new IllegalArgumentException("index is already in the priority queue");
		}
		n++;

		pc.cambiarEnPos(i,n);
		cp.cambiarEnPos(n, i);
		keys.cambiarEnPos(i, llave);
		swim(n);
		// TODO Auto-generated method stub

	}

	public int minIndex() {
		if (n == 0) throw new NoSuchElementException("Priority queue underflow");
		return cp.darObjeto(1);
	}

	public T minKey() {
		if (n == 0) throw new NoSuchElementException("Priority queue underflow");
		return keys.darObjeto(cp.darObjeto(1));
	}


	public int delMin() {
		if (n == 0) throw new NoSuchElementException("Priority queue underflow");

		int min = cp.darObjeto(1);
		exch(1,n--);
		sink(1);
		pc.cambiarEnPos(min, -1);
		keys.cambiarEnPos(min, null);
		cp.cambiarEnPos(n+1, -1);
		return min;
	}


	public T obtenerLlave(int i) {
		if (i < 0 || i >= maxN) throw new IllegalArgumentException();
		if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
		else return keys.darObjeto(i);
	}

	public void changeKey(int i, T key) {
		if (i < 0 || i >= maxN) throw new IllegalArgumentException();
		if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");

		keys.cambiarEnPos(i, key);
		swim(pc.darObjeto(i));
		sink(pc.darObjeto(i));
	}


	
	 public void decreaseKey(int i, T key) {
	        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
	        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
	        
	        if(keys.darObjeto(i).compareTo(key)<=0)
	        	 throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
	        keys.cambiarEnPos(i, key);

	        swim(pc.darObjeto(i));
	    }

	 
	  public void increaseKey(int i, T key) {
	        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
	        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
	        if (keys.darObjeto(i).compareTo(key) >= 0)
	            throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
	        keys.cambiarEnPos(i, key);
	        sink(pc.darObjeto(i));
	    }

	    /**
	     * Remove the key associated with index {@code i}.
	     *
	     * @param  i the index of the key to remove
	     * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	     * @throws NoSuchElementException no key is associated with index {@code i}
	     */
	    public void delete(int i) {
	        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
	        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
	        int index = pc.darObjeto(i); 
	        exch(index, n--);
	        swim(index);
	        sink(index);
	        keys.cambiarEnPos(i, null);
	        pc.cambiarEnPos(i, -1);
	    }


	   /***************************************************************************
	    * General helper functions.
	    ***************************************************************************/
	    private boolean greater(int i, int j) {
	    		return keys.darObjeto(cp.darObjeto(i)).compareTo(keys.darObjeto(cp.darObjeto(j)))>0;
	    }

	    private void exch(int i, int j) {
	    	
	    	int swap = cp.darObjeto(i);
	    	
	    	cp.cambiarEnPos(i, cp.darObjeto(j));
	    	cp.cambiarEnPos(i, swap);
	    	
	    	pc.cambiarEnPos(cp.darObjeto(i), i);
	    	pc.cambiarEnPos(cp.darObjeto(j), j);
	    	
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
	            System.out.println("K:" +k +" J: "+j);
	            if (!greater(k, j)) break;
	            exch(k, j);
	            k = j;
	        }
	    }


	   /***************************************************************************
	    * Iterators.
	    ***************************************************************************/

	    /**
	     * Returns an iterator that iterates over the keys on the
	     * priority queue in ascending order.
	     * The iterator doesn't implement {@code remove()} since it's optional.
	     *
	     * @return an iterator that iterates over the keys in ascending order
	     */
	    public Iterator<Integer> iterator() { return new HeapIterator(); }

	    private class HeapIterator implements Iterator<Integer> {
	        // create a new pq
	        private IndexMinPQ<T> copy;

	        // add all elements to copy of heap
	        // takes linear time since already in heap order so no keys move
	        public HeapIterator() {
	            copy = new IndexMinPQ<T>(cp.darTamano() - 1);
	            for (int i = 1; i <= n; i++)
	            	copy.agregar(cp.darObjeto(i), keys.darObjeto(cp.darObjeto(i)));
	        }

	        public boolean hasNext()  { return !copy.esVacia();                     }
	        public void remove()      { throw new UnsupportedOperationException();  }

	        public Integer next() {
	            if (!hasNext()) throw new NoSuchElementException();
	            return copy.delMin();
	        }
	    }



}
