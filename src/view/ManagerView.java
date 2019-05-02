package view;

import java.io.File;


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
		System.out.println("0. Cargar mapa (grafo) a partir del XML");
		System.out.println("1. Guardar grafo en JSON");
		System.out.println("2. Cargar grafo desde JSON");
		System.out.println("3. Crear mapa del grafo cargado");
		
		System.out.println("11. Salir");
		System.out.println("Digite el numero de opcion para ejecutar la tarea, luego presione enter: (Ej., 1):");
		
	}
	
	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	public void printResumenCarga(Integer[] resultados0) {
		System.out.println("Numero de Vertices: " + resultados0[0]);
		System.out.println("Numero de Arcos: " + resultados0[1]);
		
	}

	public void printReq1(boolean esSatisfactorio) {
		if(esSatisfactorio){
			System.out.println("Se gener� correctame el archivo JSON con la informaci�n del grafo");
		}else{
			System.out.println("Ocurri� un error generando el JSON");
		}
		// TODO Auto-generated method stub
		
	}
	
	public void printReq2(int[] infoCarga) {
		System.out.println("Numero de Vertices: " + infoCarga[0]);
		System.out.println("Numero de Arcos: " + infoCarga[1]);
		
	}
	
	public void printMapa() {
		System.out.println("Archivo creado");
	}

	public void printReq3(File htmlMapa) {
		
		
	}

}
