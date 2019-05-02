package model.data_structures;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.util.Sort;

public class BTSTest {
	/*
	 * Atributos 
	 */
	private ITablaSimOrd<Integer, Integer> tabla;
	private final int numeroEscenarios = 100;
	
	/*
	 * Escenarios
	 */
	// Arreglo con los elementos 0:0, 1:1, ..., n-1:n-1
	private void setUpEscenario(int n, boolean aleatorio) {
		tabla = new BlancoRojoBST<Integer, Integer>();
		if (!aleatorio) {
			for (int i = 0; i < n; i++) {
				tabla.put(i, i);
			}
		} else {
			IArregloDinamico<Integer> ordenPos = new ArregloDinamico<>(n);
			for (int i = 0; i < n; i++) ordenPos.agregar(i);
			Sort.shuffle(ordenPos);
			
			for (Integer i: ordenPos) {
				tabla.put(i, i);
			}
		}
	}

	/*
	 * Metodos para Pruebas
	 */
	/**
	 * Prueba el constructor. Asume que darTamano() funciona correctamente
	 */

	@Test
	public void testBTS() {
		for (int n = 0; n < numeroEscenarios; n++) {
			setUpEscenario(n, true);
			assertTrue("Escenario: " + n + " creado en desorden. El arbol deberia tener tamano " + n, tabla.darTamano() == n);
			
			setUpEscenario(n, false);
			assertTrue("Escenario: " + n + " creado en orden. El arbol deberia tener tamano " + n, tabla.darTamano() == n);
		}
		System.out.println("El constructor funciona!\n");
	}
	
	/**
	 * Prueba el metodo darTamano()
	 */
	@Test
	public void testDarTamano() {
		for (int n = 0; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			assertTrue("Escenario: " + n + " creado en desorden. El arbol deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			
			setUpEscenario(n, false);
			assertTrue("Escenario: " + n + " creado en orden. El arbol deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			
			System.out.println("darTamano() funciona para el escenario " + n);
		}
		System.out.println("darTamano() funciona!\n");
	}
	
	/**
	 * Prueba el metodo get(). Asume que darTamano() funciona correctamente
	 */
	@Test
	public void testGet() {
		for (int n = 0; n <= numeroEscenarios; n++) {	
			for (boolean desordenado: new boolean[]{true, false}) {
				System.out.println("\n\nEntrando a probar get()  para el escenario N = " + n);
				setUpEscenario(n, desordenado);
				
				// Obtener los elementos
				Integer valor;
				for (int i = 0; i < n; i++) {
					valor = tabla.get(i);
					// Verificar que el objeto es el esperado
					assertTrue("Escenario: " + n + " creado en desorden?: " + desordenado + ". El dato esperado era: " + i
							+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
					// Verificar que no ha cambiado el tamano del arbol
					assertTrue("Escenario: " + n + " creado en desorden?: " + desordenado + ". El arbol deberia tener " + n + " elementos."
							+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
				}
				
				System.out.println("get() funciona para el escenario " + n);
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
		IArregloDinamico<Integer> ordenPos; // Arreglo con el orden en que se agregan los nuevos elementos
		
		for (int n = 0; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			nAgregar = 2*n;
			
			// Agrega nAgregar elementos nuevos
			ordenPos = new ArregloDinamico<>(nAgregar);
			for (int i = 1; i <= nAgregar; i++) ordenPos.agregar(i);
			Sort.shuffle(ordenPos);
			for (int i: ordenPos) {
				tabla.put(-i, i);
				
				// Comprobar que el elemento fue agregado
				valor = tabla.get(-i);
				assertTrue("Escenario: " + n + ". Se espera que al conseguir el elemento recien colocado se obtenga " + i + 
						", pero se obtiene " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				System.out.println("put() funciona para el escenario " + n + ", agregando nuevos elementos");
			}
			// Comprobar que el tamanio de la tabla es el esperado
			assertTrue("Escenario: " + n + ". El arbol deberia tener " + (n + nAgregar) + " elementos."
					+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n + nAgregar));
			System.out.println("put() funciona para el escenario " + n + "), agregando nuevos elementos, Y identifica el tamanio adecuado");
			
			
			// Modifica nAgregar/2 elementos existentes
			for (int i = 0; i < nAgregar/2; i++) {
				tabla.put(-i, -i);
				
				// Comprobar que el elemento fue modificado correctamente
				valor = tabla.get(-i);
				assertTrue("Escenario: " + n + ". Se espera que al conseguir el elemento recien colocado se obtenga " + (-i) + 
						", pero se obtiene " + (valor != null? "nulo": valor), valor == -i);
				System.out.println("put() funciona para el escenario " + n + ", reemplazando elementos");
			}
			// Comprobar que no se cambio el tamanio del arbol
			assertTrue("Escenario: " + n + ". No debio cambiar de tamanio. El arbol deberia tener " + (n + nAgregar) + " elementos."
					+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n + nAgregar));
			System.out.println("put() funciona para el escenario " + n + ", reemplazando elementos, Y identifica el tamanio adecuado");
		}
		System.out.println("put() funciona!\n");
	}
	
	/**
	 * Prueba el metodo delete()
	 */
	@Test
	public void testDelete() {
		int nEliminar;
		Integer valor;
		int nEliminados;
		
		for (int n = 0; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			nEliminar = n;
			nEliminados = 0;
			
			// Eliminar nEliminar elementos
			for (int i = 0; i < nEliminar; i++) {
				valor = tabla.delete(i);
						
				// Verificar que se elimino el elemento correcto
				assertTrue("Escenario: " + n + ". El dato esperado era: " + i
						+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				nEliminados += 1;
				
				// Comprobar que el total de elementos disminuye en 1
				assertTrue("Escenario: " + n + ". El arbol deberia tener " + (n - nEliminados) + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n - nEliminados));
			}
			System.out.println("delete() funciona para el escenario " + n + ", eliminando todos los elementos.");			
		}
		System.out.println("delete() funciona!");
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
		
		for (boolean desordenado: new boolean[] {true, false}){
			setUpEscenario(n, desordenado);
			elementosVistos = new boolean[n];
			totalVistos = 0;
			
			for(Integer llave: tabla) {
				// Cada elemento de la tabla es de la forma "Elemento i" : i, asi que aprovechamos esto para numerarlos por i				
				llaveAct = tabla.get(llave);
				assertTrue("Escenario: " + n + ", desordenado?: " + desordenado + ". El valor de las llaves deberia estar entre " + 0 + " y " + n + ", pero se obtuvo que este valor era " + llaveAct, 0 <= llaveAct && llaveAct <= n);
				
				// Comprobar que el elemento no ha sido visto antes
				if (elementosVistos[llaveAct]) {
					fail("Escenario: " + n + ", desordenado?: " + desordenado + ". El elemento " + llave + " deberia haber sido visto MAXIMO una vez.");
				} else {
					totalVistos += 1;
					elementosVistos[llaveAct] = true;
				}				
			}
			// Comprobar que se vieron todas las llaves (exactamente una vez)
			assertTrue("Escenario: " + n + ",  desordenado?: " + desordenado + ". Deberian haberse encontrado " + n + " llaves, pero se encontraron " + totalVistos, totalVistos == n);
		}
		System.out.println("iterator() funciona para el escenario " + n + "");
	}
	
	/**
	 * Prueba el metodo isEmpty().
	 */
	@Test
	public void testIsEmpty() {
		setUpEscenario(0, true);
		assertTrue("Escenario: " + 0 + " creado en desorden. El arbol deberia estar vacio."
					+ " Pero tiene " + tabla.darTamano(), tabla.isEmpty());
		
		setUpEscenario(0, false);
		assertTrue("Escenario: " + 0 + " creado en orden. El arbol deberia estar vacio."
				+ " Pero tiene " + tabla.darTamano(), tabla.isEmpty());
		
		System.out.println("isEmpty() funciona para el escenario " + 0);
		
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			assertTrue("Escenario: " + n + " creado en desorden. El arbol deberia no estar vacio."
						+ " Pero tiene " + tabla.darTamano(), !tabla.isEmpty());
			
			setUpEscenario(n, false);
			assertTrue("Escenario: " + n + " creado en orden. El arbol deberia no estar vacio."
					+ " Pero tiene " + tabla.darTamano(), !tabla.isEmpty());
			
			System.out.println("isEmpty() funciona para el escenario " + n);
		}
		System.out.println("isEmpty() funciona!\n");
	}
	
	/**
	 * Prueba el metodo contains().
	 */
	@Test
	public void testcontains() {
		int nRevisar;
		
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			nRevisar = n;
			
			// Revisar nRevisar elementos
			for (int i = 1; i < nRevisar; i++) {
				// Verificar que el  Elemento i se encuentra 
				assertTrue("Escenario: " + n + ". Se esperaba que el elemento Elemento " + i + " estuviera contenido.",
						tabla.contains(i));
				
				// Comprobar que Elemento -i no se encuentra
				assertTrue("Escenario: " + n + ". Se esperaba que el elemento Elemento " + (-i) + " no estuviera contenido.",
						!tabla.contains((-i)));
			}
			System.out.println("contains() funciona para el escenario " + n);			
		}
		System.out.println("contains() funciona!");
	}
	
	/**
	 * Prueba el metodo testMin().
	 */
	@Test
	public void testmin() {
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			assertTrue("Escenario: " + n + " creado en desorden. El arbol elemento minimo deberia ser "
					+ "Elemento 0, pero es " + tabla.min(), tabla.min().equals(0));
			
			setUpEscenario(n, false);
			assertTrue("Escenario: " + n + " creado en orden. El arbol elemento minimo deberia ser "
					+ "Elemento 0, pero es " + tabla.min(), tabla.min().equals(0));
			
			System.out.println("min() funciona para el escenario " + n);
		}
		System.out.println("min() funciona!\n");
	}
	
	/**
	 * Prueba el metodo testMax().
	 */
	@Test
	public void testmax() {
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			assertTrue("Escenario: " + n + " creado en desorden. El arbol elemento maximo deberia ser "
					+ (n-1) + ", pero es " + tabla.max(), tabla.max().equals((n-1)));
			
			setUpEscenario(n, false);
			assertTrue("Escenario: " + n + " creado en orden. El arbol elemento maximo deberia ser "
					+ (n-1) + ", pero es " + tabla.max(), tabla.max().equals((n-1)));
			
			System.out.println("max() funciona para el escenario " + n);
		}
		System.out.println("max() funciona!\n");
	}
	
	/**
	 * Prueba el metodo deleteMin().
	 */
	@Test
	public void testdeleteMin() {
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			
			// Eliminar 
				tabla.deleteMin();
						
				// Verificar que se elimino el elemento correcto
				assertTrue("Escenario: " + n + ". El dato Elemento " + 0
						+ " no deberia estar contenido.", !tabla.contains(0));
				
				// Comprobar que el total de elementos disminuye en 1
				assertTrue("Escenario: " + n + ". El arbol deberia tener " + (n - 1) + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n - 1));
				System.out.println("deleteMin() funciona para el escenario " + n + ", eliminando todos los elementos.");
			}
						
		
		System.out.println("deleteMin() funciona!");
	}
	
	/**
	 * Prueba el metodo rank().
	 */
	@Test
	public void testrank() {

		int valor;
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			
			// 
			for (int i = 0; i < n; i++) {
				// 
				valor = tabla.rank(i);
				assertTrue("Escenario: " + n + ". El dato esperado era: " + i
						+ ", pero se obtuvo " + (valor), valor == i);
				
			}
			System.out.println("rank() funciona para el escenario " + n + ", para todos los elementos.");			
		}
		System.out.println("rank() funciona!");
	}
	
	/**
	 * Prueba el metodo select().
	 */
	@Test
	public void testselect() {
		Integer valor;
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			
			// 
			for (int i = 1; i < n; i++) {
				// 
				valor = tabla.select(i);
				assertTrue("Escenario: " + n + ". El dato esperado era: Elemento " + i
						+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				
			}
			System.out.println("select() funciona para el escenario " + n + ", para todos los elementos.");			
		}
		System.out.println("select() funciona!");
	}

	/**
	 * Prueba el metodo floor().
	 */
	@Test
	public void testfloor() {
		Integer valor;
		for (int n = 0; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			
			// 
			for (int i = 0; i < n; i++) {
				// 
				valor = tabla.floor(i);
				assertTrue("Escenario: " + n + ". El dato esperado era: Elemento " + i
						+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				
			}
			System.out.println("testFloor() funciona para el escenario " + n + ", para todos los elementos.");			
		}
		System.out.println("testFloor() funciona!");
	}
	
	/**
	 * Prueba el metodo floor().
	 */
	@Test
	public void testceiling() {
		Integer valor;
		for (int n = 0; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			
			// 
			for (int i = 0; i < n; i++) {
				// 
				valor = tabla.ceiling(i);
				assertTrue("Escenario: " + n + ". El dato esperado era: Elemento " + i
						+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				
			}
			System.out.println("testCeiling() funciona para el escenario " + n + ", para todos los elementos.");			
		}
		System.out.println("testCeiling() funciona!");
	}
	
	/**
	 * Prueba el metodo getHeight()
	 */
	@Test
	public void testGetHeight() {
		setUpEscenario(22, false); // Escenario hecho a mano que se conoce con detalle
		
		int alturaObtenida;
		int[] alturasEsperadas = new int[] {3, 2, 3, 1, 3, 2, 3, 0, 4, 3, 4, 2, 4, 3, 4, 1, 4, 3, 4, 2, 4, 3};
		
		for (int i = 0; i < 22; i++) {
			alturaObtenida = tabla.getHeight(i);
			assertTrue("Escenario: " + 22 + ". La altura esperada para " + i + " era: " + alturasEsperadas[i]
					+ ", pero se obtuvo " + alturaObtenida, alturaObtenida == alturasEsperadas[i]);
			System.out.println("Altura correcta para " + i);
		}	
		
		// Elementos inexistentes
		alturaObtenida = tabla.getHeight(-10);
		assertTrue("Escenario: " + 22 + ". La altura esperada para " + -10 + " era: " + (-1)
				+ ", pero se obtuvo " + alturaObtenida, alturaObtenida == -1);
		System.out.println("Altura correcta para elementos inexistentes.");
	}
	
	/**
	 * Prueba el metodo height()
	 */
	@Test
	public void testHeight() {
		setUpEscenario(22, false); // Escenario hecho a mano que se conoce con detalle
		int alturaObtenida = tabla.height();
		assertTrue("Escenario: " + 22 + ". La altura esperada para el arbol era: " + 4
				+ ", pero se obtuvo " + alturaObtenida, alturaObtenida == 4);
	}
	
	/**
	 * Prueba el metodo darNumeroParejas()
	 */
	@Test
	public void testDarNumeroParejas() {
		int numeroParejas;
		
		for (int n = 0; n < numeroEscenarios; n++) {
			setUpEscenario(n, true);
			
			numeroParejas = tabla.darNumeroParejas();
			assertTrue("Escenario: " + n + ". El numero de parejas esperado en el arbol era: " + n
					+ ", pero se obtuvieron " + numeroParejas, numeroParejas == n);
		}
	}
	
	/**
	 * Prueba el metodo keysInRange()
	 */
	@Test
	public void testKeysInRange() {
		int nTests = 15; // Numero de intervalos con los que se probara el metodo para cada escenario
		int minKey;
		int maxKey;
		int temp;
		IArregloDinamico<Integer> llavesEncontradas = new ArregloDinamico<>();
		
		for (int n = 1; n < numeroEscenarios; n++) {
			setUpEscenario(n, true);
			
			// Probar varios rangos por cada escenario
			for (int i = 0; i < nTests; i++) {
				llavesEncontradas = new ArregloDinamico<>();
				// Eleccion de los rangos
				minKey = (int)(Math.random()*n);
				maxKey = (int)(Math.random()*n);
				
				if (maxKey < minKey) {
					temp = minKey;
					minKey = maxKey;
					maxKey = temp;
				}
				
				// Extraccion de llaves en rango en formato ordenable
				for (Integer key : tabla.keysInRange(minKey, maxKey)) llavesEncontradas.agregar(key);
				Sort.ordenarQuickSort(llavesEncontradas);
				
							
				// Revisar que todas las llaves en ese rango fueron encontradas
				for (int j = minKey; j <= maxKey; j++) {
					assertTrue("Escenario: " + n + ". Se esperaba encontrar a la llave " + j + " en el rango de busqueda"
							+ " cuando en cambio se encontro la llave " + llavesEncontradas.darObjeto(j - minKey)
							, llavesEncontradas.darObjeto(j - minKey).equals(j));
					System.out.println("Se hallo la llave correcto en el escenacio " + n + " para minKey = " + minKey + ", maxKey =  " + maxKey + " y la llave " + j);
				}
				
				// Asegurarse de que no hay llaves adicionales encontradas en ese rango
				assertTrue("Escenario: " + n + ". No se esperaban llaves adicionales en el rango dado, pero se obtuvieron " 
						+ (llavesEncontradas.darTamano() - (maxKey - minKey + 1)) + " llaves adicionales.", llavesEncontradas.darTamano() == (maxKey - minKey + 1));
			}
		}
	}
	
	/**
	 * Prueba el metodo valuesInRange()
	 */
	@Test
	public void testValuesInRange() {
		int nTests = 15; // Numero de intervalos con los que se probara el metodo para cada escenario
		int minvalue;
		int maxvalue;
		int temp;
		IArregloDinamico<Integer> valoresEncontradas = new ArregloDinamico<>();
		
		for (int n = 1; n < numeroEscenarios; n++) {
			setUpEscenario(n, true);
			
			// Probar varios rangos por cada escenario
			for (int i = 0; i < nTests; i++) {
				valoresEncontradas = new ArregloDinamico<>();
				// Eleccion de los rangos
				minvalue = (int)(Math.random()*n);
				maxvalue = (int)(Math.random()*n);
				
				if (maxvalue < minvalue) {
					temp = minvalue;
					minvalue = maxvalue;
					maxvalue = temp;
				}
				
				// Extraccion de valores en rango en formato ordenable
				for (Integer value : tabla.valuesInRange(minvalue, maxvalue)) valoresEncontradas.agregar(value);
				Sort.ordenarQuickSort(valoresEncontradas);
				
							
				// Revisar que todas las valores en ese rango fueron encontradas
				for (int j = minvalue; j <= maxvalue; j++) {
					assertTrue("Escenario: " + n + ". Se esperaba encontrar a la valor" + j + " en el rango de busqueda"
							+ " cuando en cambio se encontro la valor" + valoresEncontradas.darObjeto(j - minvalue)
							, valoresEncontradas.darObjeto(j - minvalue).equals(j));
					System.out.println("Se hallo la valorcorrecto en el escenacio " + n + " para minvalue = " + minvalue + ", maxvalue =  " + maxvalue + " y la valor" + j);
				}
				
				// Asegurarse de que no hay valores adicionales encontradas en ese rango
				assertTrue("Escenario: " + n + ". No se esperaban valores adicionales en el rango dado, pero se obtuvieron " 
						+ (valoresEncontradas.darTamano() - (maxvalue - minvalue + 1)) + " valores adicionales.", valoresEncontradas.darTamano() == (maxvalue - minvalue + 1));
			}
		}
	}
}