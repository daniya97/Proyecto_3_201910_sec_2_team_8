package model.util;

import junit.framework.TestCase;
import model.data_structures.Arco;
import model.data_structures.GrafoNDPesos;
import model.data_structures.PesoArco;

public class AlgorithmsTest  extends TestCase{

	private GrafoNDPesos<Integer, Integer, PesoArco> grafo;


	private void setUpEscenario0() {
		grafo = new GrafoNDPesos<>();

		grafo.addVertex(0, 1);
		grafo.addVertex(1, 1);
		grafo.addVertex(2, 1);
		grafo.addVertex(3, 1);
		grafo.addVertex(4, 1);
		grafo.addVertex(5, 1);
		grafo.addVertex(6, 1);
		grafo.addVertex(7, 1);



		PesoArco nuevoArco = new PesoArco(0.16);
		PesoArco nuevoArco1 = new PesoArco(0.17);
		PesoArco nuevoArco2 = new PesoArco(0.19);
		PesoArco nuevoArco3 = new PesoArco(0.26);
		PesoArco nuevoArco4 = new PesoArco(0.28);
		PesoArco nuevoArco5 = new PesoArco(0.30);
		PesoArco nuevoArco6 = new PesoArco(0.32);
		PesoArco nuevoArco7 = new PesoArco(0.34);
		PesoArco nuevoArco8 = new PesoArco(0.35);
		PesoArco nuevoArco9 = new PesoArco(0.36);
		PesoArco nuevoArco10 = new PesoArco(0.37);
		PesoArco nuevoArco11= new PesoArco(0.38);
		PesoArco nuevoArco12 = new PesoArco(0.40);
		PesoArco nuevoArco13 = new PesoArco(0.52);
		PesoArco nuevoArco14 = new PesoArco(0.58);
		PesoArco nuevoArco15 = new PesoArco(0.93);




		grafo.addEdge(0, 7, nuevoArco);
		grafo.addEdge(2, 3, nuevoArco1);
		grafo.addEdge(1, 7, nuevoArco2);
		grafo.addEdge(0, 2, nuevoArco3);
		grafo.addEdge(5, 7, nuevoArco4);
		grafo.addEdge(1, 3, nuevoArco5);
		grafo.addEdge(1, 5, nuevoArco6);
		grafo.addEdge(2, 7, nuevoArco7);
		grafo.addEdge(4, 5, nuevoArco8);
		grafo.addEdge(1, 2, nuevoArco9);
		grafo.addEdge(4, 7, nuevoArco10);
		grafo.addEdge(0, 4, nuevoArco11);
		grafo.addEdge(6, 2, nuevoArco12);
		grafo.addEdge(3, 6, nuevoArco13);
		grafo.addEdge(6, 0, nuevoArco14);
		grafo.addEdge(6, 4, nuevoArco15);


	}


	private void setUpEscenario2() {
		grafo = new GrafoNDPesos<>();


		grafo.addVertex(0, 1);
		grafo.addVertex(1, 1);
		grafo.addVertex(2, 1);
		grafo.addVertex(3, 1);
		grafo.addVertex(4, 1);
		grafo.addVertex(5, 1);
		grafo.addVertex(6, 1);
		grafo.addVertex(7, 1);



		PesoArco nuevoArco = new PesoArco(5);
		PesoArco nuevoArco1 = new PesoArco(9);
		PesoArco nuevoArco2 = new PesoArco(8);
		PesoArco nuevoArco3 = new PesoArco(12);
		PesoArco nuevoArco4 = new PesoArco(15);
		PesoArco nuevoArco5 = new PesoArco(4);
		PesoArco nuevoArco6 = new PesoArco(3);
		PesoArco nuevoArco7 = new PesoArco(11);
		PesoArco nuevoArco8 = new PesoArco(9);
		PesoArco nuevoArco9 = new PesoArco(4);
		PesoArco nuevoArco10 = new PesoArco(20);
		PesoArco nuevoArco11= new PesoArco(5);
		PesoArco nuevoArco12 = new PesoArco(1);
		PesoArco nuevoArco13 = new PesoArco(13);
		PesoArco nuevoArco14 = new PesoArco(6);
		PesoArco nuevoArco15 = new PesoArco(7);




		grafo.addEdge(0, 1, nuevoArco);
		grafo.addEdge(0, 4, nuevoArco1);
		grafo.addEdge(0, 7, nuevoArco2);
		grafo.addEdge(1, 2, nuevoArco3);
		grafo.addEdge(1, 3, nuevoArco4);
		grafo.addEdge(1, 7, nuevoArco5);
		grafo.addEdge(2, 3, nuevoArco6);
		grafo.addEdge(2, 6, nuevoArco7);
		grafo.addEdge(3, 6, nuevoArco8);
		grafo.addEdge(4, 5, nuevoArco9);
		grafo.addEdge(4, 6, nuevoArco10);
		grafo.addEdge(4, 7, nuevoArco11);
		grafo.addEdge(5, 2, nuevoArco12);
		grafo.addEdge(5, 6, nuevoArco13);
		grafo.addEdge(7, 5, nuevoArco14);
		grafo.addEdge(7, 2, nuevoArco15);

	}


	private void setUpEscenario3(){

		grafo = new GrafoNDPesos<>();

		grafo.addVertex(0, 1);
		grafo.addVertex(1, 1);
		grafo.addVertex(2, 1);
		grafo.addVertex(3, 1);
		grafo.addVertex(4, 1);
		grafo.addVertex(5, 1);
		grafo.addVertex(6, 1);
		grafo.addVertex(7, 1);
		grafo.addVertex(8, 1);
		grafo.addVertex(9, 1);
		grafo.addVertex(10, 1);
		grafo.addVertex(11, 1);
		grafo.addVertex(12, 1);

		PesoArco nuevoArco = new PesoArco(5);
		PesoArco nuevoArco1 = new PesoArco(9);
		PesoArco nuevoArco2 = new PesoArco(8);
		PesoArco nuevoArco3 = new PesoArco(12);
		PesoArco nuevoArco4 = new PesoArco(15);
		PesoArco nuevoArco5 = new PesoArco(4);
		PesoArco nuevoArco6 = new PesoArco(3);
		PesoArco nuevoArco7 = new PesoArco(11);
		PesoArco nuevoArco8 = new PesoArco(9);
		PesoArco nuevoArco9 = new PesoArco(4);
		PesoArco nuevoArco10 = new PesoArco(20);
		PesoArco nuevoArco11= new PesoArco(5);
		PesoArco nuevoArco12 = new PesoArco(1);

		grafo.addEdge(0, 1, nuevoArco);
		grafo.addEdge(0, 2, nuevoArco1);
		grafo.addEdge(0, 5, nuevoArco2);
		grafo.addEdge(1, 6, nuevoArco3);
		grafo.addEdge(5, 3, nuevoArco4);
		grafo.addEdge(5, 4, nuevoArco5);
		grafo.addEdge(3, 4, nuevoArco6);
		grafo.addEdge(4, 6, nuevoArco7);
		grafo.addEdge(7, 8, nuevoArco8);
		grafo.addEdge(9, 10, nuevoArco9);
		grafo.addEdge(9, 11, nuevoArco10);
		grafo.addEdge(9, 12, nuevoArco11);
		grafo.addEdge(12, 11, nuevoArco12);

	}
	
	
	
	//CASO VACIO - MANEJO DE NULL
	private void setUpEscenario4(){
		grafo = new GrafoNDPesos<>();
	}
	
	private void setUpEscenario5(){
		
	grafo = new GrafoNDPesos<>();
	
	grafo.addVertex(0, 1);
	grafo.addVertex(1, 1);
	grafo.addVertex(2, 1);
	grafo.addVertex(3, 1);
	grafo.addVertex(4, 1);
	grafo.addVertex(5, 1);
	PesoArco nuevoArco = new PesoArco(5);
	PesoArco nuevoArco1 = new PesoArco(9);
	PesoArco nuevoArco2 = new PesoArco(8);
	PesoArco nuevoArco3 = new PesoArco(12);
	PesoArco nuevoArco4 = new PesoArco(15);
	PesoArco nuevoArco5 = new PesoArco(4);
	PesoArco nuevoArco6 = new PesoArco(3);
	PesoArco nuevoArco7 = new PesoArco(11);
	
	
	grafo.addEdge(0, 1, nuevoArco);
	grafo.addEdge(0, 2, nuevoArco1);
	grafo.addEdge(2, 1, nuevoArco2);
	grafo.addEdge(2, 3, nuevoArco3);
	grafo.addEdge(2, 4, nuevoArco4);
	grafo.addEdge(4, 3, nuevoArco5);
	grafo.addEdge(3, 5, nuevoArco6);
	grafo.addEdge(0, 5, nuevoArco7);
	
		
	}




	public void testKruskalAndPrime(){
		setUpEscenario0();

		//KRUSKAL

		KruskalMST<Integer, Integer, PesoArco> nuevo = new KruskalMST<>(grafo);
		System.out.println("MST: Kruskal");
		for(Arco<PesoArco> s: nuevo.arcos()){
			int primero = s.either();
			System.out.println(primero + " hasta "+ s.other(primero));
		}

		//Resultado LIBRO
		assertTrue("Error en el algoritmo Kruskal" ,nuevo.weight() == 1.81);

		
		Prim<Integer, Integer, PesoArco> nuevo1 = new Prim<>(grafo);
		System.out.println("MST: PRIM");
		for(Arco<PesoArco> s: nuevo1.arcos()){
			int primero = s.either();
			System.out.println(primero + " hasta "+ s.other(primero));
		}
		
		assertTrue("Error en el algoritmo PRIM" ,nuevo1.weight() > 1.80 || nuevo1.weight()<1.81);


	}

	public void testComponentesConectadas(){

		setUpEscenario3();

		//VERIFICACIÓN CON EL DEMO DEL LIBRO

		ComponentesConectadas<Integer,Integer> nuevo = new ComponentesConectadas<>(grafo);

		
		// VERIFICACIÓN GENERAL
		for (int i = 0; i < grafo.V(); i++) {

			if(i<=6){
				assertTrue("Están mal las componentes conectadas", nuevo.id(i) == 0);
			}
			else if(i<=8){
				assertTrue("Están mal las componentes conectadas", nuevo.id(i) == 1);
			}
			else{
				assertTrue("Están mal las componentes conectadas", nuevo.id(i) == 2);
			}
		}
		
		
		// OTROS MÉTODOS IMPORTANTES
		assertTrue("No corresponde al numero de componentes", nuevo.numComponentes() == 3);
		assertTrue("No corresponde al numero de componentes", nuevo.tamano(0) == 7);
		assertTrue("No corresponde al numero de componentes", nuevo.tamano(8) == 2);
		assertTrue("No corresponde al numero de componentes", nuevo.tamano(10) == 4);
		
	}


	public void testDijsktra(){


		setUpEscenario2();
		//Dijsktra
		Dijkstra<Integer, Integer, PesoArco> nuevo = new Dijkstra<>(grafo, 0);
		assertTrue("Error en el SP",nuevo.distTo(6) == 25);
		assertTrue("Error en el SP",nuevo.distTo(3) == 17);
		assertTrue("Error en el SP",nuevo.distTo(5) == 13);
		
		//Verificar el Shortest Path de 0 hasta 6
		
		for(Arco<PesoArco> s: nuevo.caminoA(6)){
			//Vertices en la ruta
			assertTrue("Error en el cálculo del SP", s.either()== 2 || s.either() == 6 || s.either() == 5 || s.either() == 4|| s.either() == 0  );
		}
	}
	
	public void testCasoNull(){
		setUpEscenario4();
		
		// SE UTILIZO PARA MANEJAR EL NULL 
		Dijkstra<Integer, Integer, PesoArco> nuevo = new Dijkstra<>(grafo, 0);
		Prim<Integer, Integer, PesoArco> nuevo1 = new Prim<>(grafo);
		KruskalMST<Integer, Integer, PesoArco> nuevo2 = new KruskalMST<>(grafo);
		ComponentesConectadas<Integer,Integer> nuevo3 = new ComponentesConectadas<>(grafo);
		
		
		// MANEJO DE PRIM Y KRUSKAL
		assertTrue("Es un grafo vacío ", nuevo1.arcos() !=null);
		assertTrue("Es un grafo vacío ", nuevo2.arcos() !=null);
		
		//Verifiacción Dijkstra
		for (int i = 0; i < 20; i++) {
			assertTrue("No hay caminos", nuevo.caminoA(i) == null);
			assertTrue("No hay caminos", nuevo.existeCaminoHasta(i) == false);
		}
		
		//Verificaciones componenetes Conectadas
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j <100; j++) {
				assertTrue("Eror en CC" , !nuevo3.connected(i, j));
				assertTrue("Eror en CC" , nuevo3.tamano(i) == 0);
			}
			
		}
		
		
	}
	
	
	public void testBFS(){
		
		setUpEscenario5();
		BFS<Integer, Integer, PesoArco> distMin = new BFS<Integer, Integer, PesoArco>(grafo, 0);

		//Verificar
		assertTrue("La distancia no corresponde" , distMin.distTo(1) == 1);
		assertTrue("La distancia no corresponde" , distMin.distTo(2) == 1);
		assertTrue("La distancia no corresponde" , distMin.distTo(5) == 1);
		assertTrue("La distancia no corresponde" , distMin.distTo(3) == 2);
		assertTrue("La distancia no corresponde" , distMin.distTo(4) == 2);
	
		
		// Todos están conectados
		for (int i = 0; i < 6; i++) {
				assertTrue("Todos los vértices están conectados" , distMin.hasPathTo(i));
		}
		
	}


}