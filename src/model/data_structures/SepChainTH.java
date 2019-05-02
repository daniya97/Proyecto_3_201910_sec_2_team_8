package model.data_structures;

import java.util.Deque;
import java.util.Iterator;

public class SepChainTH<K, V> implements ITablaHash<K, V> {


	/**
	 * Arreglo que guarda los nodos (cada nodo tiene una llave y un valor)
	 */
	private Nodo<K>[] nodos;
	/**
	 * Capacidad de la tabla
	 */
	public int m;  // Made public for the tests
	/**
	 * N�mero de llaves en la tabla
	 */
	public int n;  // Made public for the tests
	/**
	 * Factor de carga (n/m) m�ximo
	 */
	public final int factorCargaMax = 5; // Made public for the tests
	/**
	 * Factor de carga (n/m) míximo
	 */
	public final double factorCargaMin = 0.25; // Made public for the tests

	
	public int numRehash = 0; // Made public for the tests

	/**
	 * Constructor
	 */
	public SepChainTH (int pM) {
		m = pM;		
		nodos = (Nodo<K>[]) new Nodo[m];
	}


	/**
	 * Iterador de la tabla
	 */
	@Override
	public Iterator<K> iterator() {

		return new Iterator<K>() {
			int iActual = -1; // Ultima posicion vista
			Nodo<K> nActual = null; // Ultimo nodo visto
			int contador = 0; // Cuantos ha visto hasta ahora

			/**
			 * Retorna si existe o no un elemento despu�s del actual
			 */
			@Override
			public boolean hasNext() {
				if (contador >= n) return false;
				return true;
			}
			@Override
			/**
			 * Retorna el siguiente elemento
			 */
			public K next() {
				if(contador>=n){
					return null;
				}
				if (contador == 0) {
					iActual = siguienteNoNulo(iActual + 1);
					nActual = nodos[iActual];
					contador += 1;
					return nActual.darObjeto();
				}
				//if(nActual!=null){
				if(nActual.darSiguiente()!=null){
					contador ++;
					nActual = nActual.darSiguiente();
					return nActual.darObjeto();
				}
				//}
				// En caso de necesitar pasar al siguiente hash del arreglo
				while(contador<n){
					iActual++;
					if(nodos[iActual]!=null){
						nActual = nodos[iActual];
						contador++;
						return nActual.darObjeto();
					}
				}
				return null;
			}
		};
	}


	/**
	 *M�todo para agregar una nueva llave y su valor
	 */
	@Override
	public void put(K key, V value) {
		//Verificar en caso de tener que hacer rehash
		if ((n + 1.) / m >= factorCargaMax) rehash(siguientePrimo(2*m));


		//Guarda el �ndice hash de la llave
		int i = hash(key);
		//Se guarda el nodo previo
		Nodo<K> nodoPrevio = nodos[i];

		//Caso en el que la posici�n hash del arreglo este vac�a
		if (nodoPrevio == null) {
			n++;
			nodos[i] = new Nodo<K>(key, value);
			return;
		}

		//Caso de que ya haya un elemento en la posici�n del arreglo, se agrega en una lista encadenada
		while (true) {
			if(key.equals(nodoPrevio.darObjeto())){
				nodoPrevio.cambiarValor(value);
				return;
			}

			if (nodoPrevio.darSiguiente() == null) break;
			nodoPrevio = nodoPrevio.darSiguiente();
		} 

		// Se aumenta el n�mero de llaves y se cambia el siguiente
		n++;
		nodoPrevio.cambiarSiguiente(new Nodo<K>(key, value));
	}

	/**
	 * M�todo para obtenter un valor a partir de una llave
	 */
	@Override
	public V get(K key) {


		//Se busca el valor con el m�todo hash
		int i = hash(key);
		for(Nodo<K> x = nodos[i];x!=null;x = x.darSiguiente()){
			if(key.equals(x.darObjeto())){
				return (V)x.darValor();
			}
		}
		return null;
	}

	/**
	 * M�todo para eliminar una llave
	 */
	@Override
	public V delete(K key) {

		// Busca la llave actual con el hash
		int i = hash(key);		
		V auxiliar = null;
		Nodo<K> actual = nodos[i];
		if(actual == null)return null;
		// En caso de ser el nodo que esta en la posici�n hash de la lista
		
		if(actual.darObjeto().equals(key)){
			auxiliar = (V) nodos[i].darValor();
			nodos[i] = actual.darSiguiente();
			n--;
			if (n / m < factorCargaMin) rehash(siguientePrimo(m/2));
			return auxiliar;
		}
		else{
			//Si no se debe recorrer la lista encadenada buscando la llave
			while(actual.darSiguiente()!=null){
				if(actual.darSiguiente().darObjeto().equals(key)){
					auxiliar = (V) actual.darSiguiente().darValor();
					actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());
					n--;
					if (n / m < factorCargaMin) rehash(siguientePrimo(m/2));
					return auxiliar;
				}
				actual = actual.darSiguiente();
			}
		}
		return null;
	}
	/**
	 * Retorna el hash de la llave valor entre 0 y M-1
	 */
	private int hash(K key){
		return (key.hashCode() & 0x7fffffff)%m;
	}

	
	/**
	 * Rehash de la tabla en caso de que exceda el factor de carga
	 */
	private void rehash(int newM){

		numRehash++;
		// Estructura auxiliar para guardar la informaci�n de la tabla
		Queue<K> llaves = new Queue<>();
		Queue<V> valores = new Queue<>();
		Iterator<K> iterador = iterator();
		K actual;

		//Se guardan todos los datos
		while (iterador.hasNext()){   // Cambiar el lugar donde se definia actual fue fundamental
			actual = iterador.next();
			llaves.enqueue(actual);
			valores.enqueue(get(actual));
			//delete(actual);
		}
		//System.out.println(llaves.size() + " vs esperado: " + n);
		//Se crea una nueva tabla aumentando la capacidad
		SepChainTH<K, V> nueva = new SepChainTH<>(newM);
		for (int i = 0; i < n; i++) {
			nueva.put(llaves.dequeue(), valores.dequeue());
		}

		//Se actualiza la tabla actual
		this.m = nueva.m;
		this.nodos = nueva.nodos;
	}

	public int darTamano(){
		return n;
	}

	/**
	 * Busca el siguiente indice de la lista que no sea nula a partir y contando i
	 * @param i Se asume entre 0 y m-1
	 * @return El siguiente indice no nulo
	 */
	private int siguienteNoNulo(int i) {
		if (i >= m) return -1;
		while (i < m && nodos[i] == null) i++;
		if (i == m) i = -1;
		return i;
	}
	
	
	
	/**
	 * M�todo para encontrar el siguiente n�mero primo
	 */
	public int siguientePrimo(int numero){
		  int contador;
		  numero++;   
		  while(true){
		    contador = 0;
		    for(int i = 2; i <= Math.sqrt(numero); i ++){
		      if(numero % i == 0)  contador++;
		    }
		    if(contador == 0)
		      return numero;
		    else{
		      numero++;
		      continue;
		    }
		  }
		}


}
