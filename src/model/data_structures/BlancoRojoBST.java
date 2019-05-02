package model.data_structures;

import java.util.Iterator;

public class BlancoRojoBST<K extends Comparable<? super K>, V> implements ITablaSimOrd<K, V> {


	/**
	 * Guarda la ra�z del �rbol
	 */
	private NodoBST<K, V> root;

	/**
	 * Guarda el n�mero de elementos
	 */
	private int numElementos;

	/**
	 * Constantes para saber el color del "link"
	 */
	private static final boolean RED = true;
	private static final boolean BLACK = false;

	/**
	 * Constructor - Inicia una tabla Vac�a
	 */
	public BlancoRojoBST() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Iterador sobre las llaves
	 */
	@Override
	public Iterator<K> iterator() {
		if (isEmpty()) return new Queue<K>().iterator();
		return keysInRange(min(), max()).iterator();
	}


	/**
	 * M�todo para agregar una llave y su valor
	 * Si la llave ya existe se reemplaza
	 */
	@Override
	public void put(K key, V value) {
		// TODO Auto-generated method stub
		if(key==null) return;
		root = put(root,key,value);
		root.asignarColor(BLACK);
		numElementos ++;
	}


	/**
	 * M�todo auxiliar para encontrar donde se debe agregar la nueva llave
	 */
	private NodoBST<K, V> put(NodoBST<K, V> pNodo, K key, V value){
		if (pNodo == null) return new NodoBST<K,V>(key,value,RED,1);

		int cmp = key.compareTo(pNodo.darKey());
		if (cmp < 0) pNodo.asignarIzquierda(put(pNodo.darIzquierda(),  key, value)); 
		else if (cmp > 0) pNodo.asignarDerecha(put(pNodo.darDerecha(), key, value)); 
		else pNodo.asignarValor(value);

		if (isRed(pNodo.darDerecha()) && !isRed(pNodo.darIzquierda())) pNodo = rotarIzquierda(pNodo);
		if (isRed(pNodo.darIzquierda())  &&  isRed(pNodo.darIzquierda().darIzquierda())) pNodo = rotarDerecha(pNodo);
		if (isRed(pNodo.darIzquierda())  &&  isRed(pNodo.darDerecha())) cambiarColores(pNodo);
		pNodo.asignarTamano(size(pNodo.darIzquierda())+size(pNodo.darDerecha())+1);
		return pNodo;
	}

	/**
	 * M�todo que elimina la llave con el menor valor
	 * Este m�todo es necesario para realizar la eliminaci�n general
	 */
	@Override
	public void deleteMin() {
		if (isEmpty()) return;

		if (!isRed(root.darIzquierda()) && !isRed(root.darDerecha()))
			root.asignarColor(RED);

		root = deleteMin(root);
		if (!isEmpty()) root.asignarColor(BLACK);
		numElementos--;
	} 

	/**
	 * M�todo auxiliar para eliminar el menor
	 */
	private NodoBST<K, V> deleteMin(NodoBST<K, V> pNodo) { 
		if (pNodo.darIzquierda() == null)
			return null;

		if (!isRed(pNodo.darIzquierda()) && !isRed(pNodo.darIzquierda().darIzquierda()))
			pNodo = moverRojoIzquierda(pNodo);

		pNodo.asignarIzquierda(deleteMin(pNodo.darIzquierda()));
		return balance(pNodo);
	}

	/**
	 * M�todo para eliminar a partir de una llave dada
	 */
	@Override
	public V delete(K key) {
		// TODO Auto-generated method stub
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");
		if (!contains(key)) return null;
		V respuesta = get(key);

		// if both children of root are black, set root to red
		if (!isRed(root.darIzquierda()) && !isRed(root.darDerecha()))
			root.asignarColor(RED); 

		root = delete(root, key);
		if (!isEmpty()) root.asignarColor(BLACK); 
		numElementos--;
		return respuesta;
	}


	/**
	 * M�todo auxiliar para eliminar
	 */
	private NodoBST<K, V> delete(NodoBST<K, V> pNodo, K key) { 
		// assert get(h, key) != null;

		if (key.compareTo(pNodo.darKey()) < 0)  {
			if (!isRed(pNodo.darIzquierda()) && !isRed(pNodo.darIzquierda().darIzquierda()))
				pNodo = moverRojoIzquierda(pNodo);
			pNodo.asignarIzquierda(delete(pNodo.darIzquierda(),key));
		}
		else {
			if (isRed(pNodo.darIzquierda()))
				pNodo = rotarDerecha(pNodo);
			if (key.compareTo(pNodo.darKey()) == 0 && (pNodo.darDerecha() == null))
				return null;
			if (!isRed(pNodo.darDerecha()) && !isRed(pNodo.darDerecha().darIzquierda()))
				pNodo = moverRojoDerecha(pNodo);
			if (key.compareTo(pNodo.darKey()) == 0) {
				NodoBST<K, V> x = min(pNodo.darDerecha());
				pNodo.asignarKey(x.darKey());
				pNodo.asignarValor(x.darValor());

				pNodo.asignarDerecha(deleteMin(pNodo.darDerecha()));
			}
			else pNodo.asignarDerecha(delete(pNodo.darDerecha(),key));
		}
		return balance(pNodo);
	}

	/**
	 * M�todo para buscar
	 * Se obtiene un valor a partir de una llave
	 */
	@Override
	public V get(K key) {
		// TODO Auto-generated method stub

		if(key == null) return null;
		return get(root,key);
	}

	/**
	 * M�todo auxiliar para buscar la llave en cuesti�n
	 */
	private V get(NodoBST<K, V> pNodo, K key){
		while (pNodo!=null) {
			int comparacion = key.compareTo(pNodo.darKey());
			if(comparacion<0) pNodo = pNodo.darIzquierda();
			else if(comparacion>0) pNodo =  pNodo.darDerecha();
			else return pNodo.darValor();
		}	

		return null;
	}


	/**
	 * M�todo para determinar si la tabla contiene o no una llave
	 */
	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		if(key == null) return false;
		return get(key)!=null;
	}

	/**
	 * Retorna la llave con el menor valor
	 */
	@Override
	public K min() {
		if (isEmpty())return null;
		return min(root).darKey();
	}

	/**
	 * M�todo auxiliar para buscar el m�nimo
	 */
	private NodoBST<K, V> min(NodoBST<K, V> x) { 
		if (x.darIzquierda() == null) return x; 
		else return min(x.darIzquierda()); 
	} 

	/**
	 * Retorna la llave con mayor valor
	 */
	public K max() {
		if (isEmpty())return null;
		return max(root).darKey();
	} 


	/**
	 * M�todo auxiliar para buscar la m�xima llave
	 */
	private NodoBST<K, V> max(NodoBST<K, V> x) { 
		if (x.darDerecha() == null) return x; 
		else return max(x.darDerecha()); 
	}

	/**
	 * Retorna el tama�o del �rbol
	 */
	@Override
	public int darTamano() {
		// TODO Auto-generated method stub
		return size(root);
	}

	/**
	 * M�todo para verificar si el �rbol est� vac�o
	 */
	@Override
	public boolean isEmpty() {
		return darTamano() == 0;
		// TODO Auto-generated method stub
	}


	/**
	 * Retorna la llave m�s grande menor o igual a la llave dada por par�metro
	 */
	@Override
	public K floor(K key) {
		// TODO Auto-generated method stub
		if(key == null || !contains(key)) return null;
		NodoBST<K, V> x = floor(root,key);
		if(x == null) return null;
		else return x.darKey();
	}
	/**
	 * M�todo auxiliar para encontrar la llave floor
	 */
	private NodoBST<K, V> floor(NodoBST<K, V> x, K key){
		if(x == null) return null;
		int comparacion = key.compareTo(x.darKey());
		if(comparacion == 0) return x;
		if(comparacion<0) return floor(x.darIzquierda(),key);
		NodoBST<K, V> auxiliar = floor(x.darDerecha(), key);
		if(auxiliar!=null) return auxiliar;
		else return x;
	}

	/**
	 * Retorna la llave m�s peque�a mayor o igual a la llave dada por par�metro
	 */
	@Override
	public K ceiling(K key) {
		if(key == null || !contains(key)) return null;
		NodoBST<K, V> x = ceiling(root,key);
		if(x == null) return null;
		else return x.darKey();
	}

	/**
	 * M�todo auxiliar para encontrar la llave ceiling
	 */
	private NodoBST<K, V> ceiling(NodoBST<K, V> x, K key){
		if(x == null) return null;
		int comparacion = key.compareTo(x.darKey());
		if(comparacion == 0) return x;
		if(comparacion>0) return ceiling(x.darDerecha(),key);
		NodoBST<K, V> auxiliar = ceiling(x.darIzquierda(), key);
		if(auxiliar!=null) return auxiliar;
		else return x;
	}


	/**
	 * Selecciona la llave de rank 'num'
	 */
	@Override
	public K select(int num) {
		// TODO Auto-generated method stub
		if(num<0 || num>= darTamano()){return null;}
		NodoBST<K, V> x = select(root,num);
		return x.darKey();
	}

	/**
	 * M�todo auxiliar para el select
	 */
	private NodoBST<K, V> select(NodoBST<K, V>x, int num){
		int aux = size(x.darIzquierda());
		if(aux>num) return select(x.darIzquierda(),num);
		else if(aux<num) return select(x.darDerecha(), num-aux-1);
		else return x;
	}


	/**
	 * Retorna el n�mero de llaves menor o iguales que la llave dada por par�metro
	 */
	@Override
	public int rank(K key) {
		if(key == null) return 0;
		return rank(key, root);
		// TODO Auto-generated method stub
	}


	/**
	 *  M�todo auxiliar para encontrar el rank
	 */
	private int rank(K key, NodoBST<K, V> x){
		if(x == null) return 0;
		int comparacion = key.compareTo(x.darKey());
		if(comparacion<0) return rank(key,x.darIzquierda());
		else if(comparacion>0) return 1 +size(x.darIzquierda()) + rank(key,x.darDerecha());
		else return size(x.darIzquierda());
	}


	/**
	 * M�todo para obtener el iterador
	 */
	public Iterable<K> keysInRange(K min, K max){
		if(min == null || max == null) return null;
		Queue<K> cola = new Queue<>();
		keys(root, cola, min, max);
		return cola;
	}



	/**
	 * M�todo auxiliar iterador
	 */
	private void keys(NodoBST<K, V> x, Queue<K> cola, K min,K max){
		if (x == null) return; 
		int aux = min.compareTo(x.darKey());
		int aux2 = max.compareTo(x.darKey());

		if(aux<0)keys(x.darIzquierda(),cola, min, max);
		if(aux<=0 && aux2 >= 0) cola.enqueue(x.darKey());
		if(aux2>0) keys(x.darDerecha(),cola,min,max);
	}

	/**
	 * M�todo para obtener el iterador sobre los valores
	 */
	public Iterable<V> valuesInRange(K min, K max){
		if(min == null || max == null) return null;
		Queue<V> cola = new Queue<>();
		valuesRange(root, cola, min, max);
		return cola;
	}



	/**
	 * M�todo auxiliar iterador
	 */
	private void valuesRange(NodoBST<K, V> x, Queue<V> cola, K min,K max){
		if (x == null) return; 
		int aux = min.compareTo(x.darKey());
		int aux2 = max.compareTo(x.darKey());

		if(aux<0)valuesRange(x.darIzquierda(),cola, min, max);
		if(aux<=0 && aux2 >= 0) cola.enqueue(x.darValor());
		if(aux2>0) valuesRange(x.darDerecha(),cola,min,max);
	}





	/**
	 * retorna el n�mero de elementos en el �rbol 
	 */
	public int darNumeroParejas(){
		return numElementos;
	}




	/**
	 * Se obtiene la altura dado una llave
	 */
	public int getHeight(K key){
		if(!contains(key)) return -1;
		else return getHeightAux(key);
	}



	/**
	 * Se obtiene la altura del �rbol 
	 */
	public int height() {
		int maxAltura = -1;
		int alturaAct;
		for (K key : this) {
			alturaAct = getHeight(key);
			maxAltura = maxAltura < alturaAct? alturaAct: maxAltura;  
		}
		
		return maxAltura;
	}


	/**
	 * M�todo auxiliar para obtener la altura dado una llave
	 */
	private int getHeightAux(K key){
		NodoBST<K, V> pNodo = root;
		int contador = 0;
		while (pNodo.darKey()!=key) {
			contador ++;
			int comparacion = key.compareTo(pNodo.darKey());
			if(comparacion<0) pNodo = pNodo.darIzquierda();
			else if(comparacion>0) pNodo =  pNodo.darDerecha();
			else return contador;
		}	

		return contador;
	}

	//------------------------------------------------------------------------
	// --------------------------M�TODOS PARA VALIDAR CHECK-------------------
	// -----------------------------------------------------------------------


	/**
	 * Verifica las condiciones b�sicas del �rbol Rojo - Negro
	 */
	public boolean check(){

		return(estaBalanceado() && validacionRedLinks() && verificacionOrdenamientoDerecha() && verificacionOrdenamientoIzquierda());
	}




	/**
	 * Verifica si todos los caminos tienen el mismo n�mero de enlaces negros
	 */
	private boolean estaBalanceado() { 
		int numNegros = 0;  
		NodoBST<K, V> x = root;
		while (x != null) {
			if (!isRed(x)) numNegros++;
			x = x.darIzquierda();
		}
		return estaBalanceado(root, numNegros);
	}
	private boolean estaBalanceado(NodoBST<K, V> x, int numNegros) {
		if (x == null) return numNegros == 0;
		if (!isRed(x)) numNegros--;
		return estaBalanceado(x.darIzquierda(), numNegros) && estaBalanceado(x.darDerecha(), numNegros);
	} 


	/**
	 * Verifica las condiciones de los red links:
	 * a) Un nodo no puede tener enlace rojo a su hijo derecho
	 * b) No puede haber dos enlaces rojos consecutivos
	 */
	private boolean validacionRedLinks() { return validacionRedLinks(root); }
	private boolean validacionRedLinks(NodoBST<K, V> x) {
		if (x == null) return true;
		if (isRed(x.darDerecha())) return false;
		if (x != root && isRed(x) && isRed(x.darIzquierda()))
			return false;
		return validacionRedLinks(x.darIzquierda()) && validacionRedLinks(x.darDerecha());
	} 

	/**
	 * Verifica que todas las llaves de la izquierda sean menores o iguales al padre
	 */
	private boolean verificacionOrdenamientoIzquierda(){
		return verificacionOrdenamientoIzquierda(root);
	}

	private boolean verificacionOrdenamientoIzquierda(NodoBST<K, V> x){

		if(x.darIzquierda() == null) return true;
		else if(x.darKey().compareTo(x.darIzquierda().darKey())<=0){return false;}
		else{
			return(verificacionOrdenamientoIzquierda(x.darIzquierda()));
		}
	}

	/**
	 * Verifica que todas las llaver de la derecha sean menores o iguales
	 */
	private boolean verificacionOrdenamientoDerecha(){
		return verificacionOrdenamientoDerecha(root);
	}

	private boolean verificacionOrdenamientoDerecha(NodoBST<K, V> x){

		if(x.darDerecha() == null) return true;
		else if(x.darKey().compareTo(x.darDerecha().darKey())>=0){return false;}
		else{
			return(verificacionOrdenamientoDerecha(x.darDerecha()));
		}
	}


	//------------------------------------------------------------------------
	// -------------------------------M�TODOS AUXILIARES----------------------
	// -----------------------------------------------------------------------


	/**
	 * M�todo auxiliar para rotar a la izquierda el �rbol
	 */
	private NodoBST<K, V> rotarIzquierda(NodoBST<K, V> pNodo) {
		NodoBST<K, V> x = pNodo.darDerecha();
		pNodo.asignarDerecha(x.darIzquierda());
		x.asignarIzquierda(pNodo);
		x.asignarColor(x.darIzquierda().darColor());
		x.darIzquierda().asignarColor(RED);
		x.asignarTamano(pNodo.darTamano());
		pNodo.asignarTamano(size(pNodo.darIzquierda())+size(pNodo.darDerecha())+1);
		return x;
	}

	/**
	 * M�todo auxiliar para rotar a la derecha el �rbol
	 */
	private NodoBST<K, V> rotarDerecha(NodoBST<K, V> pNodo) {
		NodoBST<K, V> x = pNodo.darIzquierda();
		pNodo.asignarIzquierda(x.darDerecha());
		x.asignarDerecha(pNodo);
		x.asignarColor(x.darDerecha().darColor());
		x.darDerecha().asignarColor(RED);
		x.asignarTamano(pNodo.darTamano());
		pNodo.asignarTamano(size(pNodo.darIzquierda())+size(pNodo.darDerecha())+1);
		return x;
	}

	/**
	 * M�todo para cambiar los colores de los links de 3 nodos
	 */
	private void cambiarColores(NodoBST<K, V> pNodo) {
		pNodo.asignarColor(!pNodo.darColor());
		pNodo.darIzquierda().asignarColor(!pNodo.darIzquierda().darColor());
		pNodo.darDerecha().asignarColor(!pNodo.darDerecha().darColor());
	}


	/**
	 * M�todo para determinar si el arco que llega a un nodo es rojo
	 */
	private boolean isRed(NodoBST<K, V> pNodo){
		if (pNodo == null) return false;
		return pNodo.darColor() == RED;
	}




	/**
	 * Restaura el �rbol balanceado
	 */
	private NodoBST<K, V> balance(NodoBST<K, V> pNodo) {

		if (isRed(pNodo.darDerecha()))pNodo = rotarIzquierda(pNodo);
		if (isRed(pNodo.darIzquierda()) && isRed(pNodo.darIzquierda().darIzquierda())) pNodo =rotarDerecha(pNodo);
		if (isRed(pNodo.darIzquierda()) && isRed(pNodo.darDerecha()))cambiarColores(pNodo);


		pNodo.asignarTamano(size(pNodo.darIzquierda())+size(pNodo.darDerecha())+1);
		return pNodo;
	}

	/**
	 * Mueve los nodos de tal forma que el link rojo quede a la izquierda
	 */
	private NodoBST<K, V> moverRojoIzquierda(NodoBST<K, V> pNodo) {
		cambiarColores(pNodo);
		if (isRed(pNodo.darDerecha().darIzquierda())) { 
			pNodo.asignarDerecha(rotarDerecha(pNodo.darDerecha()));
			pNodo = rotarIzquierda(pNodo);
			cambiarColores(pNodo);
		}
		return pNodo;
	}

	/**
	 * Mueve los nodos de tal forma que el link rojo quede a la derecha
	 */
	private NodoBST<K, V> moverRojoDerecha(NodoBST<K, V> pNodo) {
		cambiarColores(pNodo);

		if (isRed(pNodo.darIzquierda().darIzquierda())) { 
			pNodo = rotarDerecha(pNodo);
			cambiarColores(pNodo);
		}
		return pNodo;
	}


	/**
	 * M�todo para obtener la altura a partir de un nodo
	 */
	private int size(NodoBST<K, V> pNodo){
		if(pNodo == null) return 0;
		else{ return pNodo.darTamano();}
	}


}
