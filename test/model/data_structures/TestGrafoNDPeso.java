package model.data_structures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;
import model.vo.esquemaJSON;

public class TestGrafoNDPeso extends TestCase {
	
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
	private IGraph<String, String, IdPesoArco> grafo;
	private GrafoNDPesos<String, String, IdPesoArco> grafo1;
	
	/*
	 * Escenarios
	 */
	private void setUpEscenario0() {
		grafo = new GrafoNDPesos<>();
		grafo = new GrafoNDPesos<>();
		grafo.addVertex("Daniel", "Ingenieria Industrial");
		grafo.addVertex("Sebastian", "Matematicas");
		grafo.addVertex("Camilo", "Ingenieria de Sistemas");
		
		IdPesoArco nuevoArco = new IdPesoArco(1, 10);
		grafo.addEdge("Daniel", "Sebastian", nuevoArco);
		
	}

	
	//Caso Vac�o - Manejo de Null 
	private void setUpEscenario1() {
		grafo = new GrafoNDPesos<>();
	}
	
	
	private void setUpEscenario2() {
		grafo1 = new GrafoNDPesos<>();
		grafo1.addVertex("Daniel", "Ingenieria Industrial");
		grafo1.addVertex("Sebastian", "Matematicas");
		grafo1.addVertex("Camilo", "Ingenieria de Sistemas");
		IdPesoArco nuevoArco = new IdPesoArco(1, 10);
		
		grafo1.addEdge("Daniel", "Sebastian", nuevoArco);
		
	}
	
	
	public void testEscenario0(){
		
		setUpEscenario0();
		
		//Funcionalidades B�sicas
		assertTrue("Deber�a tener 3 V�rtices", grafo.V() == 3);
		assertTrue("Deber�a tener 1 Arco", grafo.E()== 1);
		assertTrue("No encontr� correctamente la informaci�n del v�rtice",grafo.getInfoVertex("Daniel").equals("Ingenieria Industrial"));
		
		//Transformaci�n a Int
		assertTrue("Daniel tiene el n�mero 0", grafo.encontrarNumNodo("Daniel")==0);
		assertTrue("Sebasti�n tiene el n�mero 1", grafo.encontrarNumNodo("Sebastian")==1);
		assertTrue("Camilo tiene el n�mero 2", grafo.encontrarNumNodo("Camilo")==2);
		
		//Transformaci�n a K
		assertTrue("El n�mero 0 tiene a Daniel", grafo.encontrarNodo(0).equals("Daniel"));
		assertTrue("El n�mero 1 tiene a Sebasti�n", grafo.encontrarNodo(1).equals("Sebastian"));
		assertTrue("El n�mero 2 tiene a Camilo", grafo.encontrarNodo(2).equals("Camilo"));
		
		//Get Info y SetInfo
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoVertex("Daniel").equals("Ingenieria Industrial"));
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoVertex("Sebastian").equals("Matematicas"));
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoVertex("Camilo").equals("Ingenieria de Sistemas"));
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoArc("Daniel", "Sebastian").darIdArco() == 1);
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoArc("Sebastian", "Daniel").darPesoArco()== 10);

		
		//Daniel se cambia de carrera
		grafo.setInfoVertex("Daniel", "Historia");
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoVertex("Daniel").equals("Historia"));
		
		
		//Agregar V�rtices y Nodos
		grafo.addVertex("Maria", "Geociencias");
		assertTrue("Deber�a tener 4 V�rtices", grafo.V() == 4);
		IdPesoArco nuevoArco = new IdPesoArco(2, 20);
		grafo.addEdge("Daniel", "Maria", nuevoArco);
		assertTrue("No encontr� la informaci�n conrrectamente",grafo.getInfoArc("Maria", "Daniel").darIdArco() == 2);
		
		
		
		//Iterator
		Iterator<String> prueba = grafo.adj("Daniel");
		String actual = null;
		int contador = 0;
		while(prueba.hasNext()){
			actual = prueba.next();
			assertTrue("No funciona el iterador", actual.equals("Maria")||actual.equals("Sebastian"));
			contador++;
		}
		
		assertTrue("El n�mero de nodos conectados con Daniel no es correcto", contador == 2);
		
	}
	
	public void testEscenario1(){
		
		setUpEscenario1();
		
		//Funcionalidades B�sicas
		assertTrue("Deber�a tener 0 V�rtices", grafo.V() == 0);
		assertTrue("Deber�a tener 0 Arco", grafo.E()== 0);
		assertTrue("No encontr� correctamente la informaci�n del v�rtice",grafo.getInfoVertex("Daniel") == null);
		
		//Transformaci�n a Int
		assertTrue("Daniel no existe", grafo.encontrarNumNodo("Daniel")==-1);
		assertTrue("Sebasti�n no existe", grafo.encontrarNumNodo("Sebasti�n")==-1);
		
		//Transformaci�n a K
		assertTrue("El n�mero 0 no tiene a nadie", grafo.encontrarNodo(0)== null);
		assertTrue("El n�mero 1 no tiene a nadie", grafo.encontrarNodo(1) == null);
		
		
		//Get Info y SetInfo
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoVertex("Daniel") == null);
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoVertex("Sebasti�n") == null);
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoVertex("Camilo") == null);
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoArc("Daniel", "Sebasti�n") == null);
		assertTrue("No encontr� la informaci�n correctamente", grafo.getInfoArc("Sebasti�n", "Daniel")== null);

		
		//Daniel se cambia de carrera
		grafo.setInfoVertex("Daniel", "Historia");
		assertTrue("No hay informaci�n que registrar", grafo.getInfoVertex("Daniel") == null);
		
		
		//Iterator
		Iterator<String> prueba = grafo.adj("Daniel");
		String actual = null;
		int contador = 0;
		while(prueba.hasNext()){
			actual = prueba.next();
			assertTrue("No funciona el iterador", actual.equals("Mar�a")||actual.equals("Sebasti�n"));
			contador++;
		}
		
		assertTrue("El n�mero de nodos conectados con Daniel no es correcto", contador == 0);
		
	}
	


}
