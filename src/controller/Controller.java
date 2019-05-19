package controller;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.File;

import model.logic.InfoInterseccion;
import model.logic.Manager;
import model.logic.PesosDIVArco;
import model.util.Dijkstra;
import model.vo.EstadisticasCargaInfracciones;
import view.ManagerView;

public class Controller {
	/*
	 * Atributos
	 */
	/**
	 * Objeto de la Vista
	 */
	private ManagerView view;

	private Manager model;

	/*
	 * Constructor
	 */
	public Controller()
	{
		view = new ManagerView();
		model = new Manager();
	}

	/*
	 * Metodos
	 */
	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		//Controller controller = new Controller();
		int option = -1;
		boolean numeroEncontrado = false;
		boolean esSatisfactorio;

		long startTime;
		long endTime;
		long duration;

		while(!fin)
		{
			view.printMenu();
			int idVertice1 = 0;
			int idVertice2 = 0;
			// Para tener que reiniciar el programa si no se da una opcion valida
			while (!numeroEncontrado){
				try {
					option = sc.nextInt();
					numeroEncontrado = true;
				} catch (InputMismatchException e) {
					System.out.println("Esa no es una opcion valida");
					view.printMenu();
					sc = new Scanner(System.in);
				}
			} numeroEncontrado = false;

			try { // Este try se usa para no tener que reiniciar el programa en caso de que 
				// ocurra un error pequenio al ejecutar como ingresar mal la fecha  

				switch(option)
				{
				case 0:
					view.printMessage("Ingrese el nombre del archivo (guardado en 'data') (Mapa grande: persistenciaMap.json; Centro Washington: persistenciaGrafoWashington.json):");
					String nombreJsonG = sc.next();
					int[] resultados0 = model.cargarDeJson(nombreJsonG);
					view.printResumenCargaJson(resultados0);
					break;
					
				case 1:				
					view.printMessage("Ingrese el Semestre (1 -[Enero - Junio], 2[Julio - Diciembre])");
					int numeroSemestre = sc.nextInt();
					EstadisticasCargaInfracciones resultados1 = model.cargarSemestreAGrafo(numeroSemestre);
					view.printResumenLoadMovingViolations(resultados1);
					break;
					
				case -2:				
					view.printMessage("Ingrese el nombre del XML a cargar (guardado en 'data') (e.g. map.xml): ");
					String nombreXML = sc.next();
					Integer[] resumenXML = model.loadXML("./data/" + nombreXML);
					view.resumenXML(resumenXML);
					break;
				case -1:
					view.printMessage("Nombre del JSON (sin el .json): ");
					String nombreJsonC = sc.next();

					esSatisfactorio = model.guardarEnJson(nombreJsonC);
					view.printMessage("Archivo .json creado satisfactoriamente");
					break;
				

				case -4:

					view.printMessage("Ingrese El id del primer vertice (Ej. 901839): ");
					BigInteger idInicio = new BigInteger(sc.next());
					view.printMessage("Ingrese El id del segundo vertice (Ej. 901839): ");
					BigInteger idDestino = new BigInteger(sc.next());

					
					startTime = System.currentTimeMillis();
					Dijkstra<BigInteger, InfoInterseccion, PesosDIVArco> resultadoA1 = model.caminoCostoMinimoA1(idInicio, idDestino);
					
					
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
					/* 
					TODO Consola: Mostrar el camino a seguir con sus vértices (Id, Ubicación Geográfica),
					el costo mínimo (menor cantidad de infracciones), y la distancia estimada (en Km).
					TODO Google Maps: Mostrar el camino resultante en Google Maps 
					(incluyendo la ubicación de inicio y la ubicación de destino).
					 */
					break;

				case 2:
					view.printMessage("2A. Consultar los N v�rtices con mayor n�mero de infracciones. Ingrese el valor de N: ");
					int n = sc.nextInt();

					
					startTime = System.currentTimeMillis();
					model.mayorNumeroVerticesA2(n);
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
					/* 
					TODO Consola: Mostrar la informacion de los n vertices 
					(su identificador, su ubicación (latitud, longitud), y el total de infracciones) 
					Mostra el número de componentes conectadas (subgrafos) y los  identificadores de sus vertices 
					TODO Google Maps: Marcar la localización de los vértices resultantes en un mapa en
					Google Maps usando un color 1. Destacar la componente conectada más grande (con
					más vértices) usando un color 2. 
					 */
					break;

				case 3:			

					view.printMessage("Ingrese El id del primer vertice (Ej. 901839): ");
					idVertice1 = sc.nextInt();
					view.printMessage("Ingrese El id del segundo vertice (Ej. 901839): ");
					idVertice2 = sc.nextInt();

					
					startTime = System.currentTimeMillis();
					model.caminoLongitudMinimoB1(idVertice1, idVertice2);
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");

					/*
					   TODO Consola: Mostrar  el camino a seguir, informando
						el total de vértices, sus vértices (Id, Ubicación Geográfica) y la distancia estimada (en Km).
					   TODO Google Maps: Mostre el camino resultante en Google Maps (incluyendo la
						ubicación de inicio y la ubicación de destino).
					 */
					break;

				case 4:		
					double lonMin;
					double lonMax;
					view.printMessage("Ingrese la longitud minima (Ej. -87,806): ");
					lonMin = sc.nextDouble();
					view.printMessage("Ingrese la longitud m�xima (Ej. -87,806): ");
					lonMax = sc.nextDouble();

					view.printMessage("Ingrese la latitud minima (Ej. 44,806): ");
					double latMin = sc.nextDouble();
					view.printMessage("Ingrese la latitud m�xima (Ej. 44,806): ");
					double latMax = sc.nextDouble();

					view.printMessage("Ingrese el n�mero de columnas");
					int columnas = sc.nextInt();
					view.printMessage("Ingrese el n�mero de filas");
					int filas = sc.nextInt();

					
					startTime = System.currentTimeMillis();
					model.definirCuadriculaB2(lonMin,lonMax,latMin,latMax,columnas,filas);
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
					/*
					   TODO Consola: Mostrar el número de vértices en el grafo
						resultado de la aproximación. Mostar el identificador y la ubicación geográfica de cada
						uno de estos vértices. 
					   TODO Google Maps: Marcar las ubicaciones de los vértices resultantes de la
						aproximación de la cuadrícula en Google Maps.
					 */
					break;

				case 5:
					
					startTime = System.currentTimeMillis();
					model.arbolMSTKruskalC1();
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
					/*
					   TODO Consola: Mostrar los vértices (identificadores), los arcos incluidos (Id vértice inicial e Id vértice
						final), y el costo total (distancia en Km) del árbol.
					   TODO Google Maps: Mostrar el árbol generado resultante en Google Maps: sus vértices y sus arcos.
					 */

					break;

				case 6:
					
					startTime = System.currentTimeMillis();
					model.arbolMSTPrimC2();
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
					/*
					   TODO Consola: Mostrar los vértices (identificadores), los arcos incluidos (Id vértice inicial e Id vértice
					 	final), y el costo total (distancia en Km) del árbol.
					   TODO Google Maps: Mostrar el árbol generado resultante en Google Maps: sus vértices y sus arcos.
					 */
					break;

				case 7:
					
					startTime = System.currentTimeMillis();
					model.caminoCostoMinimoDijkstraC3();
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
					/*
					   TODO Consola: Mostrar de cada camino resultante: su secuencia de vértices (identificadores) y su costo (distancia en Km).
					   TODO Google Maps: Mostrar los caminos de costo mínimo en Google Maps: sus vértices
						y sus arcos. Destaque el camino más largo (en distancia) usando un color diferente
					 */
					break;

				case 8:
					view.printMessage("Ingrese El id del primer vertice (Ej. 901839): ");
					idVertice1 = sc.nextInt();
					view.printMessage("Ingrese El id del segundo vertice (Ej. 901839): ");
					idVertice2 = sc.nextInt();
					
					startTime = System.currentTimeMillis();
					model.caminoMasCortoC4(idVertice1, idVertice2);
					endTime = System.currentTimeMillis();
					duration = endTime - startTime;
					view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
					/*
					   TODO Consola: Mostrar del camino resultante: su secuencia de vértices (identificadores), 
					   el total de infracciones y la distancia calculada (en Km).
					   TODO Google Maps: Mostrar  el camino resultante en Google Maps: sus vértices y sus arcos.	  */
					break;	
					
				
					
				case 11:
					fin=true;
					sc.close();
					break;
				}
			} catch(Exception e) { // Si ocurrio un error al ejecutar algun metodo
				e.printStackTrace(); System.out.println("Ocurrio un error. Se recomienda reiniciar el programa.");}
		}
	}

}

