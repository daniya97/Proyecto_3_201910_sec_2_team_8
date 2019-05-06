package model.logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import com.google.gson.Gson;
import com.opencsv.CSVReader;

import model.data_structures.GrafoNDPesos;
import model.data_structures.IGraph;
import model.logic.PesosDIVArco;
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
	 * 
	 */
	public static final String[] EXPECTEDHEADERS = new String[] {"OBJECTID_1", "OBJECTID", "ROW_", "LOCATION", "ADDRESS_ID", "STREETSEGID", "XCOORD", "YCOORD", "TICKETTYPE", "FINEAMT", "TOTALPAID", "PENALTY1", "PENALTY2", "ACCIDENTINDICATOR", "AGENCYID", "TICKETISSUEDATE", "VIOLATIONCODE", "VIOLATIONDESC", "ROW_ID", "LAT", "LON"};
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
		public static final int LON = 20;
	/**
	 * Lista donde se van a cargar los datos de los archivos
	 */
	private static IGraph<BigInteger, InfoInterseccion, PesosDIVArco> grafoIntersecciones;

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
	
	public EstadisticasCargaInfracciones cargarJsonEInfr() throws IOException {
		int[] jsonRes = cargarDeJson(NOMBRE_MAPA_JSON);
		return null; //TODO
	}
	
	private int[] cargarDeJson(String nombreJsonG) throws IOException {
		VertexSummary verticeAct;
		
		Gson gson = new Gson();
		JReader reader = new JReader(new File("data/"+nombreJsonG));
		grafoIntersecciones = new GrafoNDPesos<>();
		
		int nVertices = 0;
		// Lee linea a linea el archivo para crear las infracciones y cargarlas a la lista
		for (String json : reader) {
			verticeAct = gson.fromJson(json, VertexSummary.class);
			
			// Agrega el vertice solo si no existe ya, por si acaso
			if (grafoIntersecciones.getInfoVertex(verticeAct.getId()) == null) {
				grafoIntersecciones.addVertex(verticeAct.getId(), new InfoInterseccion(verticeAct.getLat(), verticeAct.getLon()));
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
						new PesosDIVArco(grafoIntersecciones.getInfoVertex(verticeArcId).haversineD(grafoIntersecciones.getInfoVertex(verticeAct.getId()))));
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
				String campo;
				BigInteger idVMin = null;
				double distMin;
				double distAct;
				LatLonCoords coordsAct; double latAct; double lonAct;
				int idInf;
				for (String[] row : reader) {
					distMin = Double.MAX_VALUE; // Distancia minima inicial para esta infraccion
					
					// Extraer informacion relevante de la infraccion actual
					latAct = Double.parseDouble(row[posiciones[LAT]]);
					lonAct = Double.parseDouble(row[posiciones[LON]]);
				
					coordsAct = new LatLonCoords(latAct, lonAct);
					
					// Hallar el vertice con el que la distancia de la infraccion actual es minima
					for (BigInteger interseccionID : grafoIntersecciones) {
						distAct = grafoIntersecciones.getInfoVertex(interseccionID).haversineD(coordsAct);
						
						if (distAct < distMin) {
							distMin = distAct;
							idVMin = interseccionID;
						}
					}
					
					// Agregar infraccion al vertice seleccionado
					idInf = Integer.parseInt(row[posiciones[OBJECTID_1]]);
					grafoIntersecciones.getInfoVertex(idVMin).aumentarNInfracciones(idInf);
					
					contadorInf += 1; 
					
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
		return -1;
	}
}
