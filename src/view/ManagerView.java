package view;

import java.io.File;
import java.math.BigInteger;

import model.logic.InfoInterseccion;
import model.logic.PesosDIVArco;
import model.util.Dijkstra;
import model.vo.EstadisticasCargaInfracciones;


public class ManagerView 
{
	/**
	 * Constante con el numero maximo de datos maximo que se deben imprimir en consola
	*/
	public static final int N = 20;
	
	public ManagerView() {
		
	}
	
	public void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Proyecto 2----------------------");
		System.out.println("-3. Cargar infracciones del semestre dado al mapa (HACERLO PARA LOS 2 SEMESTRES)");
		System.out.println("-2. Crear grafo a partir de .xml");
		System.out.println("-1. Guardar grafo actual en .json");
		
		System.out.println("0. Cargar mapa (grafo) a partir del Json");
		
		System.out.println("1. Encontrar el camino de costo m�nimo para un viaje entre  dos vertices.. (REQ 1A)");
		System.out.println("2. Determinar los n v�rtices con mayor n�mero de infracciones y sus componentes conectadas (REQ 2A)");
		
		System.out.println("3. Encontrar el camino m�s corto para un viaje entre  dos vertices. (REQ 1B)");		
		System.out.println("4. Definir una cuadricula regular de N columnas por M filas.  (REQ 2B)"); 
		
		System.out.println("5. Calcular un �rbol de expansi�n m�nima (MST) con criterio distancia, utilizando el algoritmo de Kruskal (REQ 1C)");
		System.out.println("6. Calcular un �rbol de expansi�n m�nima (MST) con criterio distancia, utilizando el algoritmo de Prim. (REQ 2C)");
		System.out.println("7. Calcular los caminos de costo m�nimo con criterio distancia que conecten los v�rtices resultado "
				+ "de la aproximaci�n de las ubicaciones de la cuadricula N x M encontrados en el punto 5. (REQ 3C)");
		System.out.println("8. Encontrar el camino m�s corto para un viaje entre dos vertices. (REQ 4C)");
		
		System.out.println("9. Salir");
		System.out.println("Digite el numero de opcion para ejecutar la tarea, luego presione enter: (Ej., 1):");
	}
	
	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	public void printResumenCargaJson(int[] resultados0) {
		System.out.println("Numero de Vertices: " + resultados0[0]);
		System.out.println("Numero de Arcos: " + resultados0[1]);
		
	}
	
	public void printResumenLoadMovingViolations(EstadisticasCargaInfracciones resultados) {
		int mes = 1;
		System.out.println("Total de Infracciones :" + resultados.darTotalInfracciones());
		for (int infraccionesXMes : resultados.darNumeroDeInfraccionesXMes())
		{
			System.out.println("Infracciones mes:" + mes + " = " + infraccionesXMes);
			mes++;
		}
		double [] minimax = resultados.darMinimax();
		System.out.println("Min y max: [" + minimax[0] + ", " + minimax[1] + "], [" + minimax[2] + ", " + minimax[3] + "]");
	}

	public void resumenXML(Integer[] resumenXML) {
		System.out.println("Numero de Vertices: " + resumenXML[0]);
		System.out.println("Numero de Arcos: " + resumenXML[1]);
	}
	

	

}
