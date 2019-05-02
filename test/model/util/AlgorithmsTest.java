package model.util;

import junit.framework.TestCase;
import model.data_structures.Arco;
import model.data_structures.GrafoNDPesos;
import model.logic.infoArco;

public class AlgorithmsTest  extends TestCase{

	private GrafoNDPesos<Integer, Integer> grafo;

	
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
		
		
		
		infoArco<Integer> nuevoArco = new infoArco<Integer>(1, 0.16, 0, 7);
		infoArco<Integer> nuevoArco1 = new infoArco<Integer>(1, 0.17, 2, 3);
		infoArco<Integer> nuevoArco2 = new infoArco<Integer>(1, 0.19, 1, 7);
		infoArco<Integer> nuevoArco3 = new infoArco<Integer>(1, 0.26, 0, 2);
		infoArco<Integer> nuevoArco4 = new infoArco<Integer>(1, 0.28, 5, 7);
		infoArco<Integer> nuevoArco5 = new infoArco<Integer>(1, 0.29, 1, 3);
		infoArco<Integer> nuevoArco6 = new infoArco<Integer>(1, 0.32, 1, 5);
		infoArco<Integer> nuevoArco7 = new infoArco<Integer>(1, 0.34, 2, 7);
		infoArco<Integer> nuevoArco8 = new infoArco<Integer>(1, 0.35, 4, 5);
		infoArco<Integer> nuevoArco9 = new infoArco<Integer>(1, 0.36, 1, 2);
		infoArco<Integer> nuevoArco10 = new infoArco<Integer>(1, 0.37, 4, 7);
		infoArco<Integer> nuevoArco11= new infoArco<Integer>(1, 0.38, 0, 4);
		infoArco<Integer> nuevoArco12 = new infoArco<Integer>(1, 0.40, 6, 2);
		infoArco<Integer> nuevoArco13 = new infoArco<Integer>(1, 0.52, 3, 6);
		infoArco<Integer> nuevoArco14 = new infoArco<Integer>(1, 0.58, 6, 0);
		infoArco<Integer> nuevoArco15 = new infoArco<Integer>(1, 0.93, 6, 4);
		
		
		
		
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
	
	
	
	public void testKruskal(){
		setUpEscenario0();
		System.out.println(grafo.V());
		KruskalMST<Integer, Integer> nuevo = new KruskalMST(grafo);
		
		for(Arco<Integer> s: nuevo.edges()){
			System.out.println(s.darInformacion().darPesoArco());
		}
		
	}
	
	
	
	
	
	
	
}