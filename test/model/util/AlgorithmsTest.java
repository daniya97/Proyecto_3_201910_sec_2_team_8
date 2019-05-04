package model.util;

import junit.framework.TestCase;
import model.data_structures.Arco;
import model.data_structures.GrafoNDPesos;
import model.data_structures.PesoArco;
import model.logic.infoArco;

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
		
		infoArco<Integer> nuevoArco = new infoArco<Integer>(1, 5, 0, 1);
		infoArco<Integer> nuevoArco1 = new infoArco<Integer>(1, 9, 0, 2);
		infoArco<Integer> nuevoArco2 = new infoArco<Integer>(1, 8, 0, 5);
		infoArco<Integer> nuevoArco3 = new infoArco<Integer>(1, 12, 0, 6);
		infoArco<Integer> nuevoArco4 = new infoArco<Integer>(1, 15, 5, 3);
		infoArco<Integer> nuevoArco5 = new infoArco<Integer>(1, 4, 5, 4);
		infoArco<Integer> nuevoArco6 = new infoArco<Integer>(1, 3, 3, 4);
		infoArco<Integer> nuevoArco7 = new infoArco<Integer>(1, 11,4, 6);
		infoArco<Integer> nuevoArco8 = new infoArco<Integer>(1, 9, 7, 8);
		infoArco<Integer> nuevoArco9 = new infoArco<Integer>(1, 4, 9, 10);
		infoArco<Integer> nuevoArco10 = new infoArco<Integer>(1,20, 9, 11);
		infoArco<Integer> nuevoArco11= new infoArco<Integer>(1, 5, 9, 12);
		infoArco<Integer> nuevoArco12 = new infoArco<Integer>(1, 1, 12, 11);
		
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
		
		
		
		infoArco<Integer> nuevoArco = new infoArco<Integer>(1, 5, 0, 1);
		infoArco<Integer> nuevoArco1 = new infoArco<Integer>(1, 9, 0, 4);
		infoArco<Integer> nuevoArco2 = new infoArco<Integer>(1, 8, 0, 7);
		infoArco<Integer> nuevoArco3 = new infoArco<Integer>(1, 12, 1, 2);
		infoArco<Integer> nuevoArco4 = new infoArco<Integer>(1, 15, 1, 3);
		infoArco<Integer> nuevoArco5 = new infoArco<Integer>(1, 4, 1, 7);
		infoArco<Integer> nuevoArco6 = new infoArco<Integer>(1, 3, 2, 3);
		infoArco<Integer> nuevoArco7 = new infoArco<Integer>(1, 11,2, 6);
		infoArco<Integer> nuevoArco8 = new infoArco<Integer>(1, 9, 3, 6);
		infoArco<Integer> nuevoArco9 = new infoArco<Integer>(1, 4, 4, 5);
		infoArco<Integer> nuevoArco10 = new infoArco<Integer>(1,20, 4, 6);
		infoArco<Integer> nuevoArco11= new infoArco<Integer>(1, 5, 4, 7);
		infoArco<Integer> nuevoArco12 = new infoArco<Integer>(1, 1, 5, 2);
		infoArco<Integer> nuevoArco13 = new infoArco<Integer>(1, 13, 5, 6);
		infoArco<Integer> nuevoArco14 = new infoArco<Integer>(1, 6, 7, 5);
		infoArco<Integer> nuevoArco15 = new infoArco<Integer>(1, 7, 7, 2);
		
		
		
		
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
	
	
	
	public void testKruskalAndPrime(){
		setUpEscenario0();

		//KRUSKAL
		
		KruskalMST<Integer, Integer> nuevo = new KruskalMST(grafo);
		
<<<<<<< HEAD
		for(Arco<Integer> s: nuevo.edges()){
			System.out.println(s);
=======
		for(Arco<PesoArco> s: nuevo.edges()){
			System.out.println(s.darInformacion().darPesoArco());
>>>>>>> c85bc2619c735a69ac9f8b1c97d0dc86fef3adea
		}
		
//		
//		Prim<Integer, Integer> nuevo2 = new Prim<>(grafo);
//		
//		for(Arco<Integer> s: nuevo2.arcos()){
//			System.out.println(s);
//		}
		
		
		
	}
	
	public void testComponentesConectadas(){
		
		setUpEscenario2();
		
		ComponentesConectadas<Integer,Integer> nuevo = new ComponentesConectadas<>(grafo);
		System.out.println(nuevo.connected(0, 6));
		System.out.println(nuevo.connected(0, 9));
		
	}
	
	
	public void testDijsktra(){
		
		
	setUpEscenario2();
		
	//Dijsktra
	
	Dijkstra<Integer, Integer> nuevo = new Dijkstra<>(grafo, 0);
		
		
		
	}
	
	
	
	
	
	
	
}