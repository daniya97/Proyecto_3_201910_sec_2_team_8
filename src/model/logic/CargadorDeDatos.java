package model.logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import com.google.gson.Gson;
import com.opencsv.CSVReader;

import model.data_structures.GrafoNDPesos;
import model.data_structures.IGraph;
import model.data_structures.IdPesoArco;
import model.vo.EstadisticasCargaInfracciones;
import model.vo.VOMovingViolations;

public class CargadorDeDatos {
	/*
	 ***************************************************************************************
	 * Atributos
	 ***************************************************************************************
	 */
	/**
	 * Nombre de Json con mapa a cargar 
	 */
	private final String NOMBRE_MAPA_JSON = "persistenciaJsonMap.json";
	
	/**
	 * Lista donde se van a cargar los datos de los archivos
	 */
	private static IGraph<BigInteger, LatLonCoords, IdPesoArco> grafoIntersecciones;

	/**
	 * Numero actual del semestre cargado
	 */
	private static int semestreCargado = -1;

	/**
	 * Numero infracciones cargadas
	 */
	private static int nInfraccionesCargadas = -1;

	/**
	 * X minimo de infraccion
	 */
	private static double xMin;
	/**
	 * Y minimo de infraccion
	 */
	private static double yMin;
	/**
	 * X maximo de infraccion
	 */
	private static double xMax;
	/**
	 * Y maximo de infraccion
	 */
	private static double yMax;
	
	public int[] cargarJsonMapa() throws IOException {
		return cargarDeJson(NOMBRE_MAPA_JSON);
	}
	
	public int[] cargarDeJson(String nombreJsonG) throws IOException {
		// TODO Auto-generated method stub
		VertexSummary verticeAct;
		
		Gson gson = new Gson();
		JReader reader = new JReader(new File("data/"+nombreJsonG));
		grafoIntersecciones = new GrafoNDPesos<>();
		
		int nVertices = 0;
		// Lee linea a linea el archivo para crear las infracciones y cargarlas a la lista
		for (String json : reader) {
			verticeAct = gson.fromJson(json, VertexSummary.class);
			
			/*
			  System.out.println(verticeAct.getId()); for (int i = 0; i <
			  verticeAct.getAdj().length; i++) { System.out.println("Adj " + i + ": " +
			  verticeAct.getAdj()[i]); 
			  }
			 */
			if (grafoIntersecciones.getInfoVertex(verticeAct.getId()) == null) {
				grafoIntersecciones.addVertex(verticeAct.getId(), new LatLonCoords(verticeAct.getLat(), verticeAct.getLon()));
				nVertices += 1;
			}
		}
		
		reader = new JReader(new File("data/"+nombreJsonG));
		int nArcos = 0;
		for (String json : reader) {
			verticeAct = gson.fromJson(json, VertexSummary.class);
			
			for (BigInteger verticeArcId : verticeAct.getAdj()) {
				if (grafoIntersecciones.getInfoArc(verticeAct.getId(), verticeArcId) == null) {
					grafoIntersecciones.addEdge(verticeAct.getId(), verticeArcId, 
						new IdPesoArco(-1, grafoIntersecciones.getInfoVertex(verticeArcId).haversineD(grafoIntersecciones.getInfoVertex(verticeAct.getId()))));
					nArcos += 1;
				}
			}
		}
		
		return new int[] {nVertices, nArcos};
	}
	
	/**
	 * Carga los datos del semestre dado
	 * @param n Numero del semestre del anio (1 � 2)
	 * @return Cola con el numero de datos cargados por mes del semestre
	 */
	public EstadisticasCargaInfracciones loadMovingViolations(int n)
	{
		EstadisticasCargaInfracciones numeroDeCargas;
		if(n == 1)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_January_2018.csv", 
					"Moving_Violations_Issued_in_February_2018.csv",
										"Moving_Violations_Issued_in_March_2018.csv",
										"Moving_Violations_Issued_in_April_2018.csv",
										"Moving_Violations_Issued_in_May_2018.csv",
										"Moving_Violations_Issued_in_June_2018.csv"
			});
			semestreCargado = 1;
		}
		else if(n == 2)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_July_2018.csv",
					"Moving_Violations_Issued_in_August_2018.csv",
										"Moving_Violations_Issued_in_September_2018.csv", 
										"Moving_Violations_Issued_in_October_2018.csv",
										"Moving_Violations_Issued_in_November_2018.csv",
										"Moving_Violations_Issued_in_December_2018.csv"
			});
			semestreCargado = 2;
		}
		else
		{
			throw new IllegalArgumentException("No existe ese semestre en un annio.");
		}
		// Las siguientes 3 lineas pueden ser comentadas o eliminadas sin modificar el correcto funcionamiento del programa
		// Crear al momento de la carga de datos las estructuras usadas en la parte C

		
		return numeroDeCargas;
	}

	/**
	 * Metodo ayudante
	 * Carga la informacion sobre infracciones de los archivos a una pila y una cola ordenadas por fecha.
	 * Dado un arreglo con los nombres de los archivos a cargar
	 * @returns Cola con el numero de datos cargados por mes del cuatrimestre
	 */
	private EstadisticasCargaInfracciones loadMovingViolations(String[] movingViolationsFilePaths){
		CSVReader reader = null;

		int totalInf = 0;
		int contadorInf; // Cuenta numero de infracciones en cada archivo
		int nMeses = movingViolationsFilePaths.length;
		int[] infPorMes = new int[nMeses];

		try {
			//movingVOLista = new ArregloDinamico<VOMovingViolations>(670000);

			int nArchivoActual = 0;
			for (String filePath : movingViolationsFilePaths) {
				reader = new CSVReader(new FileReader("data/"+filePath));

				contadorInf = 0;
				// Deduce las posiciones de las columnas que se reconocen de acuerdo al header
				String[] headers = reader.readNext();
				int[] posiciones = new int[VOMovingViolations.EXPECTEDHEADERS.length];
				for (int i = 0; i < VOMovingViolations.EXPECTEDHEADERS.length; i++) {
					posiciones[i] = buscarArray(headers, VOMovingViolations.EXPECTEDHEADERS[i]);
				}

				// Lee linea a linea el archivo para crear las infracciones y cargarlas a la lista
				VOMovingViolations infraccion;
				for (String[] row : reader) {
					infraccion = new VOMovingViolations(posiciones, row);
					movingVOLista.agregar(infraccion);
					contadorInf += 1;
					if(xMin<=0 || yMin<=0){
						xMin= infraccion.getXCoord();
						yMin = infraccion.getYCoord();
					}

					// Se actualizan las coordenadas extremas
					xMin = Math.min(xMin, infraccion.getXCoord());
					xMax = Math.max(xMax, infraccion.getXCoord());
					yMin = Math.min(yMin, infraccion.getYCoord());
					yMax = Math.max(yMax, infraccion.getYCoord());			
				}
				// Se agrega el numero de infracciones cargadas en este archivo al resultado 
				totalInf += contadorInf;
				infPorMes[nArchivoActual++] = contadorInf;
			}
			nInfraccionesCargadas = totalInf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return new EstadisticasCargaInfracciones(totalInf, nMeses, infPorMes, new double[] {xMin, yMin, xMax, yMax});
	}
	
	/**
	 * Metodo para buscar strings en un array de strings, usado para deducir la posicion
	 * de las columnas esperadas en cada archivo.
	 * @param array
	 * @param string
	 * @return
	 */
	private int buscarArray(String[] array, String string) {
		int i = 0;

		while (i < array.length) {
			if (array[i].equalsIgnoreCase(string)) return i;
			i += 1;
		}
		return -1;
	}
}
