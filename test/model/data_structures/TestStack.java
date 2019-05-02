/**
 * 
 */
package model.data_structures;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author cohnan daniya97
 *
 */
public class TestStack extends TestCase{
	/*
	 * Atributos 
	 */
	private Stack<String> pila;

	/*
	 * Escenarios
	 */
	// Pila vacia
	private void setUpEscenario0() {
		pila = new Stack<String>();
	}

	// Pila con 1 elemento
	private void setUpEscenario1() {
		pila = new Stack<String>();
		pila.push("Elemento 1");
	}

	// Pila con 2 elementos
	private void setUpEscenario2() {
		pila = new Stack<String>();
		pila.push("Elemento 1");
		pila.push("Elemento 2");
	}

	/*
	 * Metodos para Pruebas
	 */
	/**
	 * Prueba el constructor
	 */
	public void testStack() {
		setUpEscenario0();
		assertTrue("La pila deberia estar vacia.", pila.isEmpty());
		assertTrue("La pila deberia tener tamano 0.", pila.size() == 0);
		assertTrue("La pila, al hacer pop, deberia retornal null", pila.pop() == null);
	}

	/**
	 * Prueba el metodo iterator()
	 */
	public void testIterator() {
		setUpEscenario2();

		int i = 0;
		//for(String dato : pila.iterator()) {
		Iterator<String> iterador = pila.iterator();
		String dato;
		while (iterador.hasNext()) {
			dato = iterador.next();
			assertTrue("El elemento siguiente no es identificado correctamente", dato.equals("Elemento " + (2-i)));
			i += 1;
		}
		assertTrue("El iterador deberia identificar y devolver 2 elementos", i == 2);
	}

	/**
	 * Prueba el metodo isEmpty()
	 */
	public void testIsEmpty() {
		for (int i = 0; i <= 2; i++) {
			if 		(i == 0) {
				setUpEscenario0();
				assertTrue("La pila deberia estar vacia.", pila.isEmpty());
			}
			else if (i == 1) {
				setUpEscenario1();
				assertTrue("La pila deberia NO deberia vacia.", !pila.isEmpty());
			}
			else if (i == 2) {
				setUpEscenario2();
				assertTrue("La pila deberia NO deberia vacia.", !pila.isEmpty());
			}

		}
	}

	/**
	 * Prueba el metodo size
	 */
	public void testSize() {
		for (int i = 0; i <= 2; i++) {
			if 		(i == 0) {
				setUpEscenario0();
				assertTrue("La pila deberia estar vacia.", pila.size() == 0);
			}
			else if (i == 1) {
				setUpEscenario1();
				assertTrue("La pila deberia deberia tener 1 elemento.", pila.size() == 1);
			}
			else if (i == 2) {
				setUpEscenario2();
				assertTrue("La pila deberia deberia tener 2 elementos.", pila.size() == 2);
			}

		}
	}

	/**
	 * Prueba el metodo push
	 */
	public void testPush() {
		for (int i = 0; i <= 1; i++) {
			if 		(i == 0) {
				setUpEscenario0();
				pila.push("Nuevo elemento");
				Iterator<String> it = pila.iterator();
				assertTrue("Deberia tener elementos sobre los cuales iterar.", it.hasNext());
				String dato = it.next();
				assertTrue("El primero dato deberia ser el recien anadido", dato.equals("Nuevo elemento"));
				assertTrue("La pila deberia tener 1 elemento.", pila.size() == 1);
			}
			else if (i == 1) {
				setUpEscenario2();
				pila.push("Nuevo elemento");
				Iterator<String> it = pila.iterator();
				assertTrue("Deberia tener elementos sobre los cuales iterar.", it.hasNext());
				String dato = it.next();
				assertTrue("El primero dato deberia ser el recien anadido", dato.equals("Nuevo elemento"));				
				assertTrue("La pila deberia tener 3 elementos.", pila.size() == 3);
			}

		}
	}

	/**
	 * Prueba el metodo pop
	 */
	public void testPop() {
		for (int i = 0; i <= 2; i++) {
			if 		(i == 0) {
				setUpEscenario0();
				pila.pop();
				assertTrue("La pila deberia seguir de tamano 0.", pila.size() == 0);
			}
			else if (i == 1) {
				setUpEscenario1();
				String dato = pila.pop();
				assertTrue("La pila deberia deberia estar vacia.", pila.isEmpty());
				assertTrue("El elemento eliminado no es el esperado.", dato.equals("Elemento 1"));
			}
			else if (i == 2) {
				setUpEscenario2();
				String dato = pila.pop();
				assertTrue("La pila deberia deberia tener 1 elementos.", pila.size() == 1);
				assertTrue("El elemento eliminado no es el esperado.", dato.equals("Elemento 2"));
				
				dato = pila.pop();
				assertTrue("La pila deberia deberia tener 1 elementos.", pila.size() == 0);
				assertTrue("El elemento eliminado no es el esperado.", dato.equals("Elemento 1"));
			}

		}
	}
}
