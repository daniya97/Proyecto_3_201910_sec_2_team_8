package model.data_structures;

import java.math.BigInteger;

import junit.framework.TestCase;
import model.logic.InfoInterseccion;
import model.logic.Manager;
import model.logic.PesosDIVArco;

public class RequerimientosTest extends TestCase  {
	
	private class IdPesoArco implements InfoArco {

		private int id;
		private double peso;
		
		public IdPesoArco(int id, double peso) {
			this.id = id;
			this.peso = peso;
		}

		@Override
		public double darPesoArco() {
			return peso;
		}
		
		public int darIdArco() {
			return id;
		}
		
	}
	
	/*
	 * Atributos 
	 */
	private IGraph<Integer, InfoInterseccion, PesosDIVArco> grafo;
	private Manager model;

	/*
	 * Escenarios
	 */
	private void setUpEscenario0() {
		model = new Manager();
		grafo = new GrafoNDPesos<>();
		grafo = new GrafoNDPesos<>();
		
		InfoInterseccion nueva = new InfoInterseccion(10,15);
		nueva.setNInfracciones(30);

		InfoInterseccion nueva2 = new InfoInterseccion(10,15);
		nueva2.setNInfracciones(50);
		

		InfoInterseccion nueva3 = new InfoInterseccion(10,15);
		nueva3.setNInfracciones(20);
		
		grafo.addVertex(0, nueva);
		grafo.addVertex(1, nueva2);
		grafo.addVertex(2, nueva3);
		
		PesosDIVArco nuevoArco = new PesosDIVArco(10);
		grafo.addEdge(0, 1, nuevoArco);
		
	}

	
	public void testRequerimiento3(){
		
		
		setUpEscenario0();
		assertTrue(grafo.V()==3);
		assertTrue(grafo.E()==1);
		model.mayorNumeroVerticesA2(1, grafo);
		
		
	}
	
	
	
	

}
