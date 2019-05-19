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
	private GrafoNDPesos<BigInteger, InfoInterseccion, PesosDIVArco> grafo;
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

		InfoInterseccion nueva4 = new InfoInterseccion(10,15);
		nueva4.setNInfracciones(5);

		InfoInterseccion nueva5 = new InfoInterseccion(10,15);
		nueva5.setNInfracciones(1);
		
		
		
		grafo.addVertex(new BigInteger("0"), nueva);
		grafo.addVertex(new BigInteger("1"), nueva2);
		grafo.addVertex(new BigInteger("2"), nueva3);
		grafo.addVertex(new BigInteger("3"), nueva4);
		grafo.addVertex(new BigInteger("4"), nueva5);

		PesosDIVArco nuevoArco = new PesosDIVArco(10);
		grafo.addEdge(new BigInteger("0"), new BigInteger("1"), nuevoArco);
		
		PesosDIVArco nuevoArco1 = new PesosDIVArco(15);
		grafo.addEdge(new BigInteger("0"), new BigInteger("3"), nuevoArco1);
		
		PesosDIVArco nuevoArco2 = new PesosDIVArco(25);
		grafo.addEdge(new BigInteger("1"), new BigInteger("3"), nuevoArco2);
		
		
		PesosDIVArco nuevoArco3 = new PesosDIVArco(10);
		grafo.addEdge(new BigInteger("3"), new BigInteger("4"), nuevoArco3);
		
		
		PesosDIVArco nuevoArco4 = new PesosDIVArco(5);
		grafo.addEdge(new BigInteger("3"), new BigInteger("2"), nuevoArco4);

		PesosDIVArco nuevoArco5 = new PesosDIVArco(15);
		grafo.addEdge(new BigInteger("2"), new BigInteger("4"), nuevoArco5);


	}


	public void testRequerimiento3(){

		setUpEscenario0();
		model.mayorNumeroVerticesA2(1, grafo);

	}

	public void testRequerimiento4(){

		setUpEscenario0();
		assertTrue(grafo.V()==3);
		assertTrue(grafo.E()==1);
		model.caminoLongitudMinimoB1(0, 1, grafo);	
	}
	
	public void testRequerimiento6(){
		
		
		
	}





}
