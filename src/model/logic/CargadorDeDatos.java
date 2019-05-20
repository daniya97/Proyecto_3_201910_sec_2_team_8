package model.logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import com.google.gson.Gson;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import model.data_structures.GrafoNDPesos;
import model.data_structures.IGraph;
import model.data_structures.ITablaHash;
import model.data_structures.LinProbTH;
import model.logic.PesosDIVArco;
import model.vo.EstadisticasCargaInfracciones;

public class CargadorDeDatos {
	/*
	 ***************************************************************************************
	 * Atributos
	 ***************************************************************************************
	 */
	/**
	 * Nombre de Json con mapa a cargar 
	 */
	private final String NOMBRE_MAPA_JSON = "finalMap.json";
	/**
	 * 
	 */
	public static final String[] EXPECTEDHEADERS = new String[] {"OBJECTID_1", "OBJECTID", "ROW_", "LOCATION", "ADDRESS_ID", "STREETSEGI", "XCOORD", "YCOORD", "TICKETTYPE", "FINEAMT", "TOTALPAID", "PENALTY1", "PENALTY2", "ACCIDENTIN", "AGENCYID", "TICKETISSU", "VIOLATIONC", "VIOLATIOND", "ROW_ID", "LAT", "LONG"};
	// Estos son los indice de los textos en EXPECTEDHEADERS
		public static final int OBJECTID_1 = 0;
		public static final int OBJECTID = 1;
		public static final int ROW_= 2;
		public static final int LOCATION = 3;
		public static final int ADDRESS_ID = 4;
		public static final int STREETSEGID = 5;
		public static final int XCOORD = 6;
		public static final int YCOORD = 7;
		public static final int TICKETTYPE = 8;
		public static final int FINEAMT = 9;
		public static final int TOTALPAID = 10;
		public static final int PENALTY1 = 11;
		public static final int PENALTY2 = 12;
		public static final int ACCIDENTINDICATOR = 13;
		public static final int AGENCYID = 14;
		public static final int TICKETISSUEDATE = 15;
		public static final int VIOLATIONCODE = 16;
		public static final int VIOLATIONDESC = 17;	
		public static final int ROW_ID = 18;
		public static final int LAT = 19;
		public static final int LONG = 20;
	
	//private static IGraph<BigInteger, InfoInterseccion, PesosDIVArco> grafoIntersecciones;

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
	private static Double latMin;
	/**
	 * Y minimo de infraccion
	 */
	private static Double lonMin;
	/**
	 * X maximo de infraccion
	 */
	private static Double latMax;
	/**
	 * Y maximo de infraccion
	 */
	private static Double lonMax;
	/**
	 * Tabla donde se recordara el vertice donde se guardan las coordenadas
	 */
	private static ITablaHash<Integer[], BigInteger> idVCorresp;
	
	private static final int PRECISION_COORD = 5000;
	
	public IGraph<BigInteger, InfoInterseccion, PesosDIVArco> cargarJsonMapa() throws IOException {
		return cargarDeJson(NOMBRE_MAPA_JSON);
	}
	
	public IGraph<BigInteger, InfoInterseccion, PesosDIVArco> cargarDeJson(String nombreJsonG) throws IOException {
		VertexSummary verticeAct;
		
		Gson gson = new Gson();
		JReader reader = new JReader(new File("data/"+nombreJsonG));
		IGraph<BigInteger, InfoInterseccion, PesosDIVArco> grafoIntersecciones = new GrafoNDPesos<>(); // Inicializar tabla que indica el vertice de este grafo que es mas cercano a cada lat lon
		
		int nVertices = 0;
		// Lee linea a linea el archivo para crear las infracciones y cargarlas a la lista
		for (String json : reader) {
			verticeAct = gson.fromJson(json, VertexSummary.class);
			
			// Agrega el vertice solo si no existe ya, por si acaso
			if (grafoIntersecciones.getInfoVertex(verticeAct.getId()) == null) {
				grafoIntersecciones.addVertex(verticeAct.getId(), new InfoInterseccion(verticeAct.getLat(), verticeAct.getLon(), verticeAct.getNInfracciones()));
				nVertices += 1;
			}
		}
		
		reader = new JReader(new File("data/"+nombreJsonG));
		int nArcos = 0;
		for (String json : reader) {
			verticeAct = gson.fromJson(json, VertexSummary.class);
			
			for (BigInteger verticeArcId : verticeAct.getAdj()) {
				if (grafoIntersecciones.getInfoArc(verticeAct.getId(), verticeArcId) == null) {
					grafoIntersecciones.addEdge(
						verticeAct.getId(), 
						verticeArcId, 
						new PesosDIVArco(grafoIntersecciones.getInfoVertex(verticeArcId).haversineD(grafoIntersecciones.getInfoVertex(verticeAct.getId())), // Peso: distancia
										 grafoIntersecciones.getInfoVertex(verticeArcId).getNInfracciones() + verticeAct.getNInfracciones(),	// Peso: nInfracciones
										 2)
						);
					
					nArcos += 1;
				}
			}
		}
		
		idVCorresp = new LinProbTH<>(11);
		
		return grafoIntersecciones;
	}
	
	/**
	 * Carga los datos del semestre dado
	 * @param n Numero del semestre del anio (1 � 2)
	 * @return Cola con el numero de datos cargados por mes del semestre
	 */
	public EstadisticasCargaInfracciones loadMovingViolations(int n, IGraph<BigInteger, InfoInterseccion, PesosDIVArco> grafoIntersecciones)
	{
		EstadisticasCargaInfracciones numeroDeCargas;
		if(n == 1)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"January_wgs84.csv", 
					"February_wgs84.csv",
										"March_wgs84.csv",
										"April_wgs84.csv",
										"May_wgs84.csv",
										"June_wgs84.csv"
			}, grafoIntersecciones);
			semestreCargado = 1;
		}
		else if(n == 2)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"July_wgs84.csv",
					"August_wgs84.csv",
										"September_wgs84.csv", 
										"October_wgs84.csv",
										"November_wgs84.csv",
										"December_wgs84.csv"
			}, grafoIntersecciones);
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
	//TODO hacer privado
	public EstadisticasCargaInfracciones loadMovingViolations(String[] movingViolationsFilePaths, IGraph<BigInteger, InfoInterseccion, PesosDIVArco> grafoIntersecciones){
		CSVReader reader = null;

		int totalInf = 0;
		int contadorInf; // Cuenta numero de infracciones en cada archivo
		int nMeses = movingViolationsFilePaths.length;
		int[] infPorMes = new int[nMeses];

		try {
			//movingVOLista = new ArregloDinamico<VOMovingViolations>(670000);

			int nArchivoActual = 0;
			CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
			for (String filePath : movingViolationsFilePaths) {
				reader = new CSVReaderBuilder(new FileReader("data/"+filePath)).withCSVParser(parser).build();

				contadorInf = 0;
				// Deduce las posiciones de las columnas que se reconocen de acuerdo al header
				String[] headers = reader.readNext();
				//for (String head : headers) System.out.print(head + ";");
				//System.out.println();
				int[] posiciones = new int[EXPECTEDHEADERS.length];
				//System.out.println(posiciones.length);
				for (int i = 0; i < EXPECTEDHEADERS.length; i++) {
					posiciones[i] = buscarArray(headers, EXPECTEDHEADERS[i]);
					//System.out.println("Posiciones " + i + " " +posiciones[i]);
				}
				
				// Lee linea a linea el archivo para crear las infracciones y cargarlas a la lista
				String campo;
				BigInteger idVMin = null;
				double distMin;
				double distAct;
				LatLonCoords coordsAct; double latAct; double lonAct;
				int idInf;
				for (String[] row : reader) {
					distMin = Double.MAX_VALUE; // Distancia minima inicial para esta infraccion
					
					// Extraer informacion relevante de la infraccion actual
					latAct = Double.parseDouble(row[posiciones[LAT]].replaceAll(",","."));
					lonAct = Double.parseDouble(row[posiciones[LONG]].replaceAll(",","."));
				
					coordsAct = new LatLonCoords(latAct, lonAct);
					
					// Hallar el vertice con el que la distancia de la infraccion actual es minima
					idVMin = idVCorresp.get(new Integer[] {(int) latAct*PRECISION_COORD, (int) lonAct*PRECISION_COORD});
					
					if (idVMin == null) { // Buscar si no esta en la tabla
						for (BigInteger interseccionID : grafoIntersecciones) {
							distAct = grafoIntersecciones.getInfoVertex(interseccionID).haversineD(coordsAct);
							
							if (distAct < distMin) {
								distMin = distAct;
								idVMin = interseccionID;
							}
						}
						
						idVCorresp.put(new Integer[] {(int) latAct*PRECISION_COORD, (int) lonAct*PRECISION_COORD}, idVMin);
					}
					
					// Agregar infraccion al vertice seleccionado
					idInf = Integer.parseInt(row[posiciones[OBJECTID]]);
					grafoIntersecciones.getInfoVertex(idVMin).aumentarNInfracciones(idInf);
					
					contadorInf += 1;
					if (contadorInf%100 == 0) System.out.println("Infracciones cargadas: " + contadorInf);
					
					// Inicializa las coordenadas extremas si no se ha hecho
					if(latMin == null || lonMin == null){
						latMin = latAct;
						lonMin = lonAct;
						latMax = latAct;
						lonMax = lonAct;
					}

					// Se actualizan las coordenadas extremas
					latMin = Math.min(latMin, latAct);
					latMax = Math.max(latMax, latAct);
					lonMin = Math.min(lonMin, lonAct);
					lonMax = Math.max(lonMax, lonAct);			
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
		return new EstadisticasCargaInfracciones(totalInf, nMeses, infPorMes, new double[] {latMin, lonMin, latMax, lonMax});
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
		//System.out.println("No se encontro: " + string + " en un array de " + array.length);
		return -1;
	}
}
