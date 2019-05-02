package model.data_structures;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class SepChainTHTest {
	/*
	 * Atributos 
	 */
	private ITablaHash<String, Integer> tabla;
	private final int numeroEscenarios = 100;
	private final int tamanoMax = 100;
	
	/*
	 * Escenarios
	 */
	// Arreglo con n elementos
	private void setUpEscenario(int n, int max) {
		// En caso de no especificarse un tamanio inicial para la tabla, se elige (n+1)/2, 
		// donde n es el numero de elementos a agregar
		if (max == -1) max = (n+1)/2;
		
		tabla = new SepChainTH<String, Integer>(max); // TODO Unica linea a modificar para cambiar tabla en test
		for (int i = 0; i < n; i++) {
			tabla.put("Elemento " + i, i);
		}
	}

	/*
	 * Metodos para Pruebas
	 */
	/**
	 * Prueba el constructor con diferentes tamanios iniciales para cada escenario. Asume que darTamano() funciona correctamente
	 */

	@Test
	public void testLinProbTH() {
		for (int n = 1; n < numeroEscenarios; n++) {
			setUpEscenario(n, 1);
			assertTrue("Escenario: " + n + " con tamanio inicial 1. El arreglo deberia tener tamano " + n, tabla.darTamano() == n);
			
			setUpEscenario(n, 10);
			assertTrue("Escenario: " + n + " con tamanio inicial 10. El arreglo deberia tener tamano " + n, tabla.darTamano() == n);
			
			setUpEscenario(n, tamanoMax);
			assertTrue("Escenario: " + n + " con tamanio inicial " + tamanoMax + ". El arreglo deberia tener tamano " + n, tabla.darTamano() == n);
			
			setUpEscenario(n, -1);
			assertTrue("Escenario: " + n + " con tamanio inicial " + (n+1)/2 + ". El arreglo deberia tener tamano " + n, tabla.darTamano() == n);
		}
	}
	
	/**
	 * Prueba el metodo darTamano()
	 */
	@Test
	public void testDarTamano() {
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, -1);
			assertTrue("Escenario: " + n + " con tamanio inicial " + (n+1)/2 + ". El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			System.out.println("darTamano() funciona para el escenario " + "(" + n + ", " + ((n+1)/2) + ")");
			
			setUpEscenario(n, 1);
			assertTrue("Escenario: " + n + " con tamanio inicial 1. El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			System.out.println("darTamano() funciona para el escenario " + "(" + n + ", " + 1 + ")");
			
			setUpEscenario(n, 10);
			assertTrue("Escenario: " + n + " con tamanio inicial 10. El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			System.out.println("darTamano() funciona para el escenario " + "(" + n + ", " + 10 + ")");
			
			setUpEscenario(n, tamanoMax);
			assertTrue("Escenario: " + n + " con tamanio inicial " + tamanoMax + ". El arreglo deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			System.out.println("darTamano() funciona para el escenario " + "(" + n + ", " + tamanoMax + ")");
		}
	}
	
	/**
	 * Prueba el metodo get(). Asume que darTamano() funciona correctamente
	 */
	@Test
	public void testGet() {
		Integer[] tamanosInic = new Integer[] {-1, 1, 10, tamanoMax};// Arreglo con los tamanos iniciales de las tablas para cada escenario
		for (int n = 1; n <= numeroEscenarios; n++) {	
			for (Integer tamano : tamanosInic) {
				System.out.println("\n\nEntrando a probar get()  para el escenario N, m = " + n + ", " + tamano);
				setUpEscenario(n, tamano);
				
				// Obtener los elementos
				Integer valor;
				for (int i = 0; i < n; i++) {
					valor = tabla.get("Elemento " + i);
					// Verificar que el objeto es el esperado
					assertTrue("Escenario: " + n + " de tamanio " + tamano + ". El dato esperado era: " + i
							+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
					// Verificar que no ha cambiado el tamano del arreglo
					assertTrue("Escenario: " + n + " de tamanio " + tamano + ". El arreglo deberia tener " + n + " elementos."
							+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
				}
				
				System.out.println("get() funciona para el escenario " + "(" + n + ", " + tamano + ")");
			}
		}
		System.out.println("get() funciona!\n");
	}
	
	/**
	 * Prueba el metodo put. Asume que el metodo get() funciona correctamente
	 */
	@Test
	public void testPut() {
		int nAgregar;
		Integer valor;
		
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, -1);
			nAgregar = 2*n;
			
			// Agrega nAgregar elementos nuevos
			for (int i = 0; i < nAgregar; i++) {
				tabla.put("Nuevo elemento " + i, i);
				
				// Comprobar que el elemento fue agregado
				valor = tabla.get("Nuevo elemento " + i);
				assertTrue("Escenario: " + n + ". Se espera que al conseguir el elemento recien colocado se obtenga " + i + 
						", pero se obtiene " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				System.out.println("put() funciona para el escenario " + "(" + n + ", " + ((n+1)/2) + "), agregando nuevos elementos");
			}
			// Comprobar que el tamanio de la tabla es el esperado
			assertTrue("Escenario: " + n + ". El arreglo deberia tener " + (n + nAgregar) + " elementos."
					+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n + nAgregar));
			System.out.println("put() funciona para el escenario " + "(" + n + ", " + ((n+1)/2) + "), agregando nuevos elementos, Y identifica el tamanio adecuado");
			
			
			// Modifica nAgregar/2 elementos existentes
			for (int i = 0; i < nAgregar/2; i++) {
				tabla.put("Nuevo elemento " + i, -i);
				
				// Comprobar que el elemento fue modificado correctamente
				valor = tabla.get("Nuevo elemento " + i);
				assertTrue("Escenario: " + n + ". Se espera que al conseguir el elemento recien colocado se obtenga " + (-i) + 
						", pero se obtiene " + (valor != null? "nulo": valor), valor == -i);
				System.out.println("put() funciona para el escenario " + "(" + n + ", " + ((n+1)/2) + "), reemplazando elementos");
			}
			// Comprobar que no se cambio el tamanio del arreglo
			assertTrue("Escenario: " + n + ". No debio cambiar de tamanio. El arreglo deberia tener " + (n + nAgregar) + " elementos."
					+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n + nAgregar));
			System.out.println("put() funciona para el escenario " + "(" + n + ", " + ((n+1)/2) + "), reemplazando elementos, Y identifica el tamanio adecuado");
		}
	}
	
	/**
	 * Prueba el metodo delete()
	 */
	@Test
	public void testDelete() {
		int nEliminar;
		Integer valor;
		int nEliminados;
		
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, -1);
			nEliminar = n;
			nEliminados = 0;
			
			// Eliminar nEliminar elementos
			for (int i = 0; i < nEliminar; i++) {
				valor = tabla.delete("Elemento " + i);
						
				// Verificar que se elimino el elemento correcto
				assertTrue("Escenario: " + n + ". El dato esperado era: " + i
						+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				nEliminados += 1;
				
				// Comprobar que el total de elementos disminuye en 1
				assertTrue("Escenario: " + n + ". El arreglo deberia tener " + (n - nEliminados) + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n - nEliminados));
			}
			System.out.println("delete() funciona para el escenario " + n + ", eliminando todos los elementos.");			
		}
	}
	
	/**
	 * Prueba el metodo iterator().
	 */
	@Test
	public void testIterator() {
		int n = numeroEscenarios; // Realiza el test solo para un numero grande, con diferentes constructores
		boolean[] elementosVistos; // Inicializado en false
		int llaveAct;
		int totalVistos;
		
		for (Integer inic : new Integer[]{-1, 1, 10, tamanoMax}){
			setUpEscenario(n, inic);
			elementosVistos = new boolean[n];
			totalVistos = 0;
			
			for(String llave: tabla) {
				// Cada elemento de la tabla es de la forma "Elemento i" : i, asi que aprovechamos esto para numerarlos por i				
				llaveAct = tabla.get(llave);
				assertTrue("Escenario: " + n + ", " + inic + ". El valor de las llaves deberia estar entre " + 0 + " y " + n + ", pero se obtuvo que este valor era " + llaveAct, 0 <= llaveAct && llaveAct <= n);
				
				// Comprobar que el elemento no ha sido visto antes
				if (elementosVistos[llaveAct]) {
					fail("Escenario: " + n + ", " + inic + ". El elemento " + llave + " deberia haber sido visto MAXIMO una vez.");
				} else {
					totalVistos += 1;
					elementosVistos[llaveAct] = true;
				}				
			}
			// Comprobar que se vieron todas las llaves (exactamente una vez)
			assertTrue("Escenario: " + n + ", " + inic + ". Deberian haberse encontrado " + n + " llaves, pero se encontraron " + totalVistos, totalVistos == n);
		}
		System.out.println("iterator() funciona para el escenario " + n + "");
	}
}