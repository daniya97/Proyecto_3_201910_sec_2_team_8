package model.data_structures;


import java.util.Iterator;


public class GrafoNDPesos<K, IV, IA extends InfoArco> implements IGraph<K, IV, IA> {

	private int V;
	private int E;
	private ITablaHash<K, Integer> tablaNodoANum;
	private IArregloDinamico<IV> informacionNodos;
	private IArregloDinamico<K> tablaNumANodo;
	private ITablaHash<Integer, LinkedList<Arco<IA>>> adj;

	
	private static final int cte = 10;



	public GrafoNDPesos() {
		V = 0;
		E = 0;

		//REVISAR QUE TABLA Y EL TAMA�O INICIAL
		tablaNodoANum = new LinProbTH<>(cte);
		informacionNodos = new ArregloDinamico<>(cte);
		tablaNumANodo = new ArregloDinamico<>(cte);
		adj = new SepChainTH<>(cte);
	}


	@Override
	public int V() {
		return V;
	}

	@Override
	public int E() {
		return E;
	}

	@Override
	public void addVertex(K idVertex, IV infoVertex) {


		// TODO Auto-generated method stub
		tablaNodoANum.put(idVertex, V);
		informacionNodos.agregar(infoVertex);
		tablaNumANodo.agregar(idVertex);
		V++;
		//NO SE SI TOCA HACER ALGO CON LOS ARCOS
	}

	@Override
	public void addEdge(K idVertexIni, K idVertexFin, IA infoArc) {
		// TODO Auto-generated method stub



		int nodoInicial = tablaNodoANum.get(idVertexIni); 
		int nodoFinal = tablaNodoANum.get(idVertexFin); 

		Arco<IA> nuevo = new Arco<IA>(nodoInicial, nodoFinal, infoArc);
		//Nodo inicial
		if(adj.get(nodoInicial) == null){
			LinkedList<Arco<IA>> nuevaLista = new LinkedList<Arco<IA>>(nuevo);
			adj.put(nodoInicial, nuevaLista);

		}else{
			adj.get(nodoInicial).agregarElementoPrimeraPosicion(nuevo);
		}

		//Nodo Final
		if(adj.get(nodoFinal) == null){
			LinkedList<Arco<IA>> nuevaLista = new LinkedList<>(nuevo);
			adj.put(nodoFinal, nuevaLista);

		}else{
			adj.get(nodoFinal).agregarElementoPrimeraPosicion(nuevo);
		}


		E++;
	}

	@Override
	public IV getInfoVertex(K idVertex) {

		int respuesta = encontrarNumNodo(idVertex);
		if(respuesta == -1) return null;
		// TODO Auto-generated method stub
		return informacionNodos.darObjeto(respuesta);
	}

	@Override
	public void setInfoVertex(K idVertex, IV infoVertex) {
		// TODO Auto-generated method stub
		int numNodo = encontrarNumNodo(idVertex);
		if(numNodo>=0) informacionNodos.cambiarEnPos(numNodo, infoVertex);
	}

	@Override
	public IA getInfoArc(K idVertexIni, K idVertexFin) {
		int nodoInicial = encontrarNumNodo(idVertexIni); 
		int nodoFinal =  encontrarNumNodo(idVertexFin); 
		LinkedList<Arco<IA>> aux = adj.get(nodoInicial);

		if(aux==null) return null;
		for(Arco<IA> e: aux){
			if(e.other(nodoInicial)==nodoFinal){
				return e.darInformacion();
			}
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInfoArc(K idVertexIni, K idVertexFin, IA infoArc) {

		int nodoInicial = tablaNodoANum.get(idVertexIni); 
		int nodoFinal = tablaNodoANum.get(idVertexFin); 
		LinkedList<Arco<IA>> aux = adj.get(nodoInicial);

		if(aux==null) return;
		for(Arco<IA> e: aux){
			if(e.other(nodoInicial)==nodoFinal){
				e.cambiarInformacion(infoArc);
				return;
			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<K> adj(K idVertex) {

		if(encontrarNumNodo(idVertex)==-1){
			return new Iterator<K>() {
				@Override
				public boolean hasNext() {
					return false;
				}

				@Override
				public K next() {
					return null;
				}
			};	




		}
		return new Iterator<K>() {

			int numNodo = tablaNodoANum.get(idVertex);
			LinkedList<Arco<IA>> aux = adj.get(numNodo);
			Nodo<Arco<IA>> siguiente = aux.darPrimerNodo();

			@Override
			public boolean hasNext() {
				if(siguiente!=null){
					return true;
				}else{
					return false;
				}
			}

			@Override
			public K next() {
				K auxiliar = 	tablaNumANodo.darObjeto(siguiente.darObjeto().other(numNodo));
				siguiente = siguiente.darSiguiente();
				return auxiliar;
				// TODO Auto-generated method stub
			}
		};
		// TODO Auto-generated method stub
	}

	public Iterator<K> iterator(){
		return tablaNumANodo.iterator();
	}

	//M�todos Tablas de Transformaci�n

	public int encontrarNumNodo(K idVertex){
		//CONVENCI�N -> -1 es por que no existe
		if(tablaNodoANum.get(idVertex) == null) return -1;
		return tablaNodoANum.get(idVertex);
	}


	public K encontrarNodo(int numNodo){
		return tablaNumANodo.darObjeto(numNodo);
	}

	
	public ITablaHash<Integer, LinkedList<Arco<IA>>> darRepresentacion(){
		return adj;
	}
	
}
