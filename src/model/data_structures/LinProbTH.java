package model.data_structures;

import java.util.Iterator;

public class LinProbTH<K, V> implements ITablaHash<K, V> {


	/**
	 * Arreglo que guarda las llaves
	 */
	private K[] keys;
	/**
	 * Arreglo que guarda los valores de cada llave
	 */
	private V[] values;
	/**
	 * Capacidad del arreglo
	 */
	public int m; // Made public for the tests

	/**
	 * N�mero de Datos
	 */
	public int n; // Made public for the tests

	/**
	 * Factor de carga m�xmio
	 */
	public final double factorCargaMax = 0.5; // Made public for the tests
	/**
	 * Factor de carga minimo
	 */
	public final double factorCargaMin = 0.25; // Made public for the tests
	
	public int numRehash = 0; // Made public for the tests

	/**
	 * Constructor
	 */
	public LinProbTH (int pM){
		m = pM;
		keys = (K[]) new Object[m];
		values = (V[]) new Object[m];
		n = 0;
	}

	/**
	 * Iterador de la tabla
	 */
	@Override
	public Iterator<K> iterator() {
		return new Iterator<K>() {
			int iActual = siguienteNoNulo(0); // Guarda el indice del elemento a devolver -1 si no hay mas
			@Override

			//M�todo has next -> Si existe un elemento despu�s o no
			public boolean hasNext() {
				return (iActual != -1);
			}
			@Override
			// Devuelve el siguiente elemento
			public K next() {
				if (iActual == -1) return null;
				K llaveAct = keys[iActual];
				iActual = siguienteNoNulo(iActual + 1);
				return llaveAct;
			}
		};

	}

	/**
	 * Iterador de la tabla por los val
	 */
	public Iterable<V> iteratorValues() {
		return new Iterable<V>() {
			@Override
			public Iterator<V> iterator() {
				return new Iterator<V>() {
					int iActual = siguienteNoNulo(0); // Guarda el indice del elemento a devolver -1 si no hay mas
					@Override

					//M�todo has next -> Si existe un elemento despu�s o no
					public boolean hasNext() {
						return (iActual != -1);
					}
					@Override
					// Devuelve el siguiente elemento
					public V next() {
						if (iActual == -1) return null;
						V llaveAct = values[iActual];
						iActual = siguienteNoNulo(iActual + 1);
						return llaveAct;
					}
				};
			}
			
		};
	}
	
	/**
	 * Para insertar un dato en la tabla
	 * Si ya existe la llave, se reemplaza el valor
	 */
	@Override
	public void put(K key, V value) {
		boolean existe = false;
		//Verificar si se debe hace rehash antes de insertar
		if ((n + 1.) / m > factorCargaMax) rehash(siguientePrimo(2*m));

		int i;
		// Recorre la tabla desde del hash buscando la siguiente posici�n vac�a
		for(i = hash(key); keys[i] !=null; i = (i+1)%m){
			if(keys[i].equals(key)){
				existe = true;
				break;
			}
		}

		//Si no exist�a se aumenta en 1 el n�mero de llaves
		if(!existe)n++;

		//Se asigna el valor de la llave en la posici�n 
		keys[i] = key;
		//Se asigna el valor del valor en la posici�n
		values[i] = value;
	}

	/**
	 * M�todo para buscar un valor de acuerdo a una llave
	 */
	@Override
	public V get(K key) {
		//Se recorren las llaver buscando la llave en cuesti�n
		for(int i = hash(key);keys[i]!=null; i = (i+1)%m){
			if(key.equals(keys[i])){
				//Al encontrarla se retorna el valor
				return values[i];
			}
		}
		// Si no se encuentra se retorna null
		return null;
	}

	@Override
	/**
	 * M�todo para eliminar una llave
	 */
	public V delete(K key) {

		if(key == null) return null;


		//Encontrar la posici�n de i
		int i = hash(key);
		while(!key.equals(keys[i])){
			i = (i+1)%m;
		}

		V respuesta = values[i];
		//Se elimina la llave y su valor
		keys[i] = null;
		values[i] = null;


		//Reorganizar
		i = (i+1)%m;

		while(keys[i]!=null){

			K keyAOrganizar = keys[i];
			V valueAOrganizar = values[i];
			keys[i] = null;
			values[i] = null;
			n--;
			put(keyAOrganizar,valueAOrganizar);
			i = (i+1)%m;
		}

		//Se reduce el n�mero de llaves
		n--;
		if (n / m <= factorCargaMin) rehash(siguientePrimo((int)(m*factorCargaMin)));
		// Se devuelve el valor asociado a la llave eliminada
		return respuesta;
	}
	/**
	 * Hash -> Devuelve un n�mero entre 0 y M-1
	 */
	private int hash(K key){
		return (key.hashCode() & 0x7fffffff)%m;
	}


	/**
	 * M�todo para Rehash la tabla en caso de exceder el factor de carga
	 */
	private void rehash(int newM){
		numRehash++;
		int contador = 0;
		//Se crea una nueva tabla con la capacidad dada por par�metro
		LinProbTH<K, V> nueva = new LinProbTH<>(newM); 
		for (int i = 0; contador < n; i++) {
			// Se guardan todos los valores de la tabla actual
			if (keys[i] != null ) {
				nueva.put(keys[i], values[i]);
				contador += 1;
			}
		}

		this.keys = nueva.keys;
		this.values = nueva.values;
		this.m = nueva.m;
	}
	/**
	 * M�todo para obtener el n�mero de llaves en la tabla
	 */
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
		while (i < m && keys[i] == null) i++;
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