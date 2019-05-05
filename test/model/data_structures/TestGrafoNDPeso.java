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

	
	//Caso Vacï¿½o - Manejo de Null 
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
		
		//Funcionalidades Bï¿½sicas
		assertTrue("Deberï¿½a tener 3 Vï¿½rtices", grafo.V() == 3);
		assertTrue("Deberï¿½a tener 1 Arco", grafo.E()== 1);
		assertTrue("No encontrï¿½ correctamente la informaciï¿½n del vï¿½rtice",grafo.getInfoVertex("Daniel").equals("Ingenieria Industrial"));
		
		//Transformaciï¿½n a Int
		assertTrue("Daniel tiene el nï¿½mero 0", grafo.encontrarNumNodo("Daniel")==0);
		assertTrue("Sebastiï¿½n tiene el nï¿½mero 1", grafo.encontrarNumNodo("Sebastian")==1);
		assertTrue("Camilo tiene el nï¿½mero 2", grafo.encontrarNumNodo("Camilo")==2);
		
		//Transformaciï¿½n a K
		assertTrue("El nï¿½mero 0 tiene a Daniel", grafo.encontrarNodo(0).equals("Daniel"));
		assertTrue("El nï¿½mero 1 tiene a Sebastiï¿½n", grafo.encontrarNodo(1).equals("Sebastian"));
		assertTrue("El nï¿½mero 2 tiene a Camilo", grafo.encontrarNodo(2).equals("Camilo"));
		
		//Get Info y SetInfo
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoVertex("Daniel").equals("Ingenieria Industrial"));
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoVertex("Sebastian").equals("Matematicas"));
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoVertex("Camilo").equals("Ingenieria de Sistemas"));
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoArc("Daniel", "Sebastian").darIdArco() == 1);
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoArc("Sebastian", "Daniel").darPesoArco()== 10);

		
		//Daniel se cambia de carrera
		grafo.setInfoVertex("Daniel", "Historia");
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoVertex("Daniel").equals("Historia"));
		
		
		//Agregar Vï¿½rtices y Nodos
		grafo.addVertex("Maria", "Geociencias");
		assertTrue("Deberï¿½a tener 4 Vï¿½rtices", grafo.V() == 4);
		IdPesoArco nuevoArco = new IdPesoArco(2, 20);
		grafo.addEdge("Daniel", "Maria", nuevoArco);
		assertTrue("No encontrï¿½ la informaciï¿½n conrrectamente",grafo.getInfoArc("Maria", "Daniel").darIdArco() == 2);
		
		
		
		//Iterator
		Iterator<String> prueba = grafo.adj("Daniel");
		String actual = null;
		int contador = 0;
		while(prueba.hasNext()){
			actual = prueba.next();
			assertTrue("No funciona el iterador", actual.equals("Maria")||actual.equals("Sebastian"));
			contador++;
		}
		
		assertTrue("El nï¿½mero de nodos conectados con Daniel no es correcto", contador == 2);
		
		
		contador = 0;
		//Iterador sobre arcos
		for(Arco<IdPesoArco> s: grafo.arcos()){
			contador++;
			assertTrue("El iterador de arcos no funciona correctamete", s.weight() == 10 || s.weight() == 20);
		}
		
		assertTrue("Sólo hay 2 arcos", contador == 2);
		
	}
	
	public void testEscenario1(){
		
		setUpEscenario1();
		
		//Funcionalidades Bï¿½sicas
		assertTrue("Deberï¿½a tener 0 Vï¿½rtices", grafo.V() == 0);
		assertTrue("Deberï¿½a tener 0 Arco", grafo.E()== 0);
		assertTrue("No encontrï¿½ correctamente la informaciï¿½n del vï¿½rtice",grafo.getInfoVertex("Daniel") == null);
		
		//Transformaciï¿½n a Int
		assertTrue("Daniel no existe", grafo.encontrarNumNodo("Daniel")==-1);
		assertTrue("Sebastiï¿½n no existe", grafo.encontrarNumNodo("Sebastiï¿½n")==-1);
		
		//Transformaciï¿½n a K
		assertTrue("El nï¿½mero 0 no tiene a nadie", grafo.encontrarNodo(0)== null);
		assertTrue("El nï¿½mero 1 no tiene a nadie", grafo.encontrarNodo(1) == null);
		
		
		//Get Info y SetInfo
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoVertex("Daniel") == null);
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoVertex("Sebastiï¿½n") == null);
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoVertex("Camilo") == null);
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoArc("Daniel", "Sebastiï¿½n") == null);
		assertTrue("No encontrï¿½ la informaciï¿½n correctamente", grafo.getInfoArc("Sebastiï¿½n", "Daniel")== null);

		
		//Daniel se cambia de carrera
		grafo.setInfoVertex("Daniel", "Historia");
		assertTrue("No hay informaciï¿½n que registrar", grafo.getInfoVertex("Daniel") == null);
		
		
		//Iterator
		Iterator<String> prueba = grafo.adj("Daniel");
		String actual = null;
		int contador = 0;
		while(prueba.hasNext()){
			actual = prueba.next();
			assertTrue("No funciona el iterador", actual.equals("Marï¿½a")||actual.equals("Sebastiï¿½n"));
			contador++;
		}
		
		assertTrue("El nï¿½mero de nodos conectados con Daniel no es correcto", contador == 0);
		
	}
	


}
