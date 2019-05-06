package controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.File;

import model.logic.Manager;
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
					view.printMessage("Ingrese el nombre del archivo (guardado en 'data') (OJO: escribir .json):");
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
					
				case 2:				
					view.printMessage("Ingrese el nombre del XML a cargar (guardado en 'data') (e.g. map.xml): ");
					String nombreXML = sc.next();
					model.loadXML(nombreXML);
					view.printMessage("Grafo creado satisfactoriamente");
					break;
				case 3:
					view.printMessage("Nombre del JSON (sin el .json): ");
					String nombreJsonC = sc.next();

					esSatisfactorio = model.guardarEnJson(nombreJsonC);
					view.printMessage("Archivo .json creado satisfactoriamente");
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

