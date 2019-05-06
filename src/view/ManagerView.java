package view;

import java.io.File;

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
		System.out.println("0. Cargar mapa (grafo) a partir del Json");
		System.out.println("1. Cargar infracciones del semestre dado al mapa");
		System.out.println("2. HACER PRIMERO (archivo grande que no puede guardarse en github):\n   Crear grafo a partir de .xml");
		System.out.println("3. Guardar grafo actual en .json");
		
		System.out.println("11. Salir");
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

	

}
