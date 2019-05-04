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
	
	
	
	public void testKruskal(){
		setUpEscenario0();
		System.out.println(grafo.V());
		KruskalMST<Integer, Integer> nuevo = new KruskalMST(grafo);
		
		for(Arco<PesoArco> s: nuevo.edges()){
			System.out.println(s.darInformacion().darPesoArco());
		}
		
	}
	
	
	
	
	
	
	
}