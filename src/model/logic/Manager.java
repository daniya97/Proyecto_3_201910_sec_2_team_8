package model.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.parsers.*;
import org.xml.sax.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IArregloDinamico;
import model.data_structures.IGraph;
import model.data_structures.IQueue;
import model.data_structures.ITablaHash;
import model.data_structures.LinProbTH;
import model.data_structures.LinkedList;
import model.data_structures.MaxHeapCP;
import model.data_structures.Queue;
import model.util.BFS;
import model.util.ComponentesConectadas;
import model.util.Dijkstra;
import model.util.KruskalMST;
import model.util.Prim;
import model.util.Sort;
import model.vo.EstadisticasCargaInfracciones;
import model.vo.esquemaJSON;

public class Manager {
	/*
	 ***************************************************************************************
	 * Atributos
	 ***************************************************************************************
	 */
	/**
	 * Nombre de Json con mapa a cargar 
	 */
	private final static String NOMBRE_MAPA_JSON = "persistenciaMap.json";

	/**
	 * Lista donde se van a cargar los datos de los archivos
	 */
	private static IGraph<BigInteger, InfoInterseccion, PesosDIVArco> grafoIntersecciones;
	/**
	 * Grafo de la componente conectada m�s grande
	 */
	private static GrafoNDPesos<BigInteger, InfoInterseccion,PesosDIVArco> grafoccMasGrande;
	/**
	 * Guarda los nodos encontrados a partir de la cuadricula
	 */
	private ArregloDinamico<BigInteger> nodosCuadricula;
	
	private ArregloDinamico<BigInteger> nodosMasInfracciones;

	/**
	 * Cargador de Json e Infracciones
	 */
	private static CargadorDeDatos cargador;
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

	/*
	 * ************************************************************************************
	 * 	Metodos
	 * ************************************************************************************
	 */
	/**
	 * Metodo constructor
	 */
	public Manager()
	{
		cargador = new CargadorDeDatos();
	}

	/*
	 * Carga de datos
	 */
	/**
	 * Carga el grafo guardado en el archivo Json cuyo nombre se da por parametro
	 * @param nombreJsonG
	 * @return
	 * @throws IOException
	 */
	public int[] cargarDeJson(String nombreJsonG) throws IOException {
		grafoIntersecciones = cargador.cargarDeJson(nombreJsonG);
		return new int[] {grafoIntersecciones.V(), grafoIntersecciones.E()};
	}

	/**
	 * Crea un grafo sin vertices desconectados de intersecciones y avenidas a partir de un archivo XML 
	 * @param nombreXML Nombre del archivo a crear
	 * @return Numero de Vertices y Numero de arcos del grafo creado
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Integer[] loadXML(String nombreXML) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);

		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();

		LectorXML manejadorDeEventos = new LectorXML();
		xmlReader.setContentHandler(manejadorDeEventos);
		xmlReader.parse(nombreXML);

		grafoIntersecciones = manejadorDeEventos.darGrafo();

		return new Integer[] {grafoIntersecciones.V(), grafoIntersecciones.E()};
	}

	/**
	 * Carga la informacion de un semestre dado a un grafo ya creado
	 * @param n
	 * @return
	 */
	public EstadisticasCargaInfracciones cargarSemestreAGrafo(int n) {
		return cargador.loadMovingViolations(n, grafoIntersecciones);
	}

	/**
	 * Guarda la informacion basica del grafo actualmente creado en un Json
	 * @param nombreJsonC Nombre del Json a crear
	 * @return Si fue satisfactoria la carga
	 */
	public static boolean guardarEnJson(String nombreJsonC) {


		esquemaJSON<BigInteger> auxiliar;
		BigInteger id;
		esquemaJSON<BigInteger>[] lista = new esquemaJSON[grafoIntersecciones.V()];
		LinkedList<Arco<PesosDIVArco>> aux;
		BigInteger[] lista2;
		double lat;
		double lon;
		int nInfr;
		int contador = 0;


		for (int i = 0; i < lista.length; i++) {
			id = grafoIntersecciones.encontrarNodo(i);
			aux = grafoIntersecciones.darRepresentacion().get(i);

			lista2 = new BigInteger[aux.darTamanoLista()];

			contador = 0;
			for(Arco<PesosDIVArco> s: aux){
				lista2[contador] = grafoIntersecciones.encontrarNodo(s.other(i));
				contador++;
			}

			lat = grafoIntersecciones.getInfoVertex(id).getLat();
			lon = grafoIntersecciones.getInfoVertex(id).getLon();
			nInfr = grafoIntersecciones.getInfoVertex(id).getNInfracciones();

			auxiliar = new esquemaJSON<BigInteger>(id, lista2, lat, lon, nInfr);
			lista[i] = auxiliar;
		}




		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String ss = gson.toJson(lista);


		try {
			FileWriter file = new FileWriter("."+File.separator+"data"+File.separator+nombreJsonC+".json");
			file.write(ss);
			file.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}

		// TODO Auto-generated method stub
	}




	public File crearMapa(String nombreHTML) throws IOException{
		// TODO
		File archivo = new File(nombreHTML);
		if (!archivo.exists()) {
			archivo.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

		// Escribir Cabeza

		writer.write("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=utf-8 />\n" + 
				"<title>Grafo generado</title>\n" + 
				"<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\n" + 
				"<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>\n" + 
				"<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' /> \n" + 
				"<style>\n" + 
				" body { margin:0; padding:0; }\n" + 
				"#map { position:absolute; top:0; bottom:0; width:100%; }\n" + 
				"</style>\n" + 
				"</head>\n" +
				"<body>\n" + 
				"<div id='map'>\n" + 
				"</div>\n");

		// Inicio del script
		Double centerLat = 38.9097115;
		Double centerLon = -77.0289048;

		Double leftLat = 38.9097115;
		Double leftLon = -77.0289048;
		Double rightLat = 38.9097843;
		Double rightLon =-77.0288552;

		writer.write("<script>\n" + 
				"L.mapbox.accessToken = 'pk.eyJ1IjoianVhbnBhYmxvY29ycmVhcHVlcnRhIiwiYSI6ImNqb2FjcHNjcjFuemwzcXB1M3E0YnB4bHIifQ.oXuYfXtCqmXY52b8Ystuyw';\n" + 
				"var map = L.mapbox.map('map', 'mapbox.streets').setView(["+ centerLat + ", "+ centerLon +"], 17);\n" + 
				"var extremos = [["+ leftLat +", "+ leftLon + "],[" + rightLat + ", " + rightLon + "]];\n" + 
				"map.fitBounds(extremos);\n");

		// Agregar edges del grafo como lineas en el mapa	    
		ITablaHash<BigInteger[], Boolean> edgesAgregados = new LinProbTH<>(11); // Para agregar solo una vez cada edge

		Iterable<BigInteger> iterableAdj;
		BigInteger id1; LatLonCoords coords1;
		BigInteger id2; LatLonCoords coords2;
		PesosDIVArco infoArcoAct;

		//boolean primerEl = true;
		for (BigInteger id : grafoIntersecciones) {

			iterableAdj = new Iterable<BigInteger>() {		
				@Override public Iterator<BigInteger> iterator() { 
					return grafoIntersecciones.adj(id); } };

					for (BigInteger verAdj : iterableAdj) {
						infoArcoAct = grafoIntersecciones.getInfoArc(id, verAdj);

						//id1 = arcoAct.darKEither();
						coords1 = grafoIntersecciones.getInfoVertex(id).getCoords();
						//id2 = arcoAct.darKOther(id1);
						coords2 = grafoIntersecciones.getInfoVertex(verAdj).getCoords();

						if (   edgesAgregados.get(new BigInteger[] {id, verAdj}) != null
								|| edgesAgregados.get(new BigInteger[] {verAdj, id}) != null ) continue;
						else edgesAgregados.put(new BigInteger[] {id, verAdj}, true);

						writer.write("var line_points = [[" + coords1.getLat() + ", " + coords1.getLon() + "] "
								+ ",[" + coords2.getLat() + ", " + coords2.getLon() + "]];\n");
						writer.write("var polyline_options = {color: '#ff2fc6'};\n" + 
								"L.polyline(line_points, polyline_options).addTo(map);\n\n");
					}
		}

		// Markers

		writer.write(
				"L.marker( [" + 41.88949181977 + ", " + -87.6882193648 + "], { title: \"Nodo de salida\"} ).addTo(map);\n" + 
						"L.marker( [" + 41.768726 + ", " + -87.664069 + "], { title: \"Nodo de llegada\"} ).addTo(map);\n");

		// Final
		writer.write("</script>\n" + 
				"</body>\n" + 
				"</html>");

		writer.close();


		return archivo;
	}

	/*
	 * Metodos ayudantes 
	 */

	public static void main(String[] args) throws IOException {
		cargador = new CargadorDeDatos();
		grafoIntersecciones = cargador.cargarDeJson(NOMBRE_MAPA_JSON);
		System.out.println("json sin infracciones cargado: " + grafoIntersecciones.V());

		String[] nombreMeses = new String[] {"January_wgs84.csv", 
				"February_wgs84.csv",
				"March_wgs84.csv",
				"April_wgs84.csv",
				"May_wgs84.csv",
				"June_wgs84.csv",
				"July_wgs84.csv",
				"August_wgs84.csv",
				"September_wgs84.csv", 
				"October_wgs84.csv",
				"November_wgs84.csv",
				"December_wgs84.csv"
		};

		for (int i = 0; i < nombreMeses.length; i++) {
			cargador.loadMovingViolations(new String[] {nombreMeses[i]}, grafoIntersecciones);
			guardarEnJson("jsonHasta"+i);
		}
	}

	/*
	 * Requerimientos
	 */

	/*
	 * Requerimiento2
	 */
	/**
	 * Encuentra el camino m�s corto (en infracciones) entre idVertice1 y idVertice 2
	 */
	public void caminoCostoMinimoA1(BigInteger idVertice1, BigInteger idVertice2) throws IOException{

		
		// Se utiliza Dijkstra - el 2 en el constructor hace referencia a que se usan como pesos el n�mero de infracciones
		int verticeInicio = grafoIntersecciones.encontrarNumNodo(idVertice1);
		int verticeDestino = grafoIntersecciones.encontrarNumNodo(idVertice2);
		Dijkstra<BigInteger, InfoInterseccion, PesosDIVArco> nuevo = new Dijkstra<BigInteger, InfoInterseccion, PesosDIVArco> (grafoIntersecciones, verticeInicio,2);
		// Se verifica que exista una camino
		
		
		if(nuevo.existeCaminoHasta(verticeDestino)){
			
			//Resultados
			ArregloDinamico<InfoInterseccion> resultadosVertices = new ArregloDinamico<>();
			ArregloDinamico<BigInteger> resultadosVerticesID = new ArregloDinamico<>();
			System.out.println("El camino sigue la siguiente secuencia: ");
			
			int ini = 0;
			int fini = 0;
			boolean primera = true;
			
			// Se obtiene un arreglo con los v�rtices
			for(Arco<PesosDIVArco> s: nuevo.caminoA(verticeDestino)){
				if(primera){
				ini = verticeInicio;
				BigInteger inicial = grafoIntersecciones.encontrarNodo(ini);
				fini = s.other(ini);
				BigInteger destinoFinal = grafoIntersecciones.encontrarNodo(fini);
				resultadosVertices.agregar(grafoIntersecciones.getInfoVertex(inicial));
				resultadosVertices.agregar(grafoIntersecciones.getInfoVertex(destinoFinal));
				resultadosVerticesID.agregar(inicial);
				resultadosVerticesID.agregar(destinoFinal);
				ini = fini;
				primera = false;
				}else{
					BigInteger destinoFinal = grafoIntersecciones.encontrarNodo(s.other(ini));
					ini = s.other(ini);
					resultadosVertices.agregar(grafoIntersecciones.getInfoVertex(destinoFinal));
					resultadosVerticesID.agregar(destinoFinal);
				}
			}
			
			// Impresi�n de resultados
			int contador = 0;
			for(InfoInterseccion s: resultadosVertices){
				System.out.println("El v�rtice: "+ resultadosVerticesID.darObjeto(contador) + " Lon: "+s.getLon()+ " Lat: "+s.getLat());
				contador++;
			}
			System.out.println("La distancia estimada del camino es de: " + encontrarDistancia(resultadosVerticesID));
			System.out.println("El n�mero de infracciones del camino es: " + encontrarInfracciones(resultadosVerticesID));
			// Generar mapa para Google Maps
			
			crearMapa("Requerimiento 1A", nuevo.caminoA(verticeDestino), new LatLonCoords[] {grafoIntersecciones.getInfoVertex(idVertice1).getCoords(), grafoIntersecciones.getInfoVertex(idVertice2).getCoords()}, new String[] {"Inicio Recorrido", "Fin Recorrido"});
			
		}
		else{
			System.out.println("No existe un camino entre los dos v�rtices");
		}
	
	}


	/*
	 * Requerimiento3
	 */
	/**
	 * Encuentra los n nodos con mayor cantidad de infracciones
	 * @throws IOException 
	 */
	public void mayorNumeroVerticesA2(int n) throws IOException{


			
			// Guarda el nuevo grafo
			nodosMasInfracciones = new ArregloDinamico<>();
			//Arreglo auxiliar que guarda la informaciï¿½n sobre los vï¿½rtices
			IArregloDinamico<InfoInterseccion> auxiliar = new ArregloDinamico<>();
			// Tabla para guardas los identificadores de los vertices
			ITablaHash<InfoInterseccion, BigInteger> ayudaIdVertice = new LinProbTH<>(grafoIntersecciones.V());

			int contador = 0;
			for(InfoInterseccion s: grafoIntersecciones.vertices()){
				auxiliar.agregar(s);
				//CAMBIOOOOOS
				BigInteger idVertice = grafoIntersecciones.encontrarNodo(contador);
				ayudaIdVertice.put(s, idVertice);
				contador++;
			}

			// Se ordenan los vertices por nï¿½mero de infracciones
			Sort.ordenarShellSort(auxiliar, new InfoInterseccion.comparadorPorInfracciones().reversed());
			// Se agregan los n vertices al nuevo grafo
			int numVertices = 0;
			while(numVertices<n){
				InfoInterseccion actual = auxiliar.darObjeto(numVertices);
				nodosMasInfracciones.agregar(ayudaIdVertice.get(actual));
				numVertices++;
			}

			
			// Resultados - IMPRESIï¿½N
			for(BigInteger s: nodosMasInfracciones){
				//CAMBIOOOOOS
				System.out.println("ID: " + s+ " Lon: "+grafoIntersecciones.getInfoVertex(s).getLon()+ " Lat: "+grafoIntersecciones.getInfoVertex(s).getLat()+" #Infracciones: "+grafoIntersecciones.getInfoVertex(s).getNInfracciones());
			}
			componentesConectadasReqA2();
			

		
			// 	Generar grafo
		
			crearMapa2A("Requerimiento2A", grafoccMasGrande.arcos(), nodosMasInfracciones);

	}

	/**
	 * Encuentras las componentes conectadas dado un grafo
	 */
	private void componentesConectadasReqA2(){

		LinProbTH<Integer, Integer> aux = new LinProbTH<Integer, Integer>(nodosMasInfracciones.darTamano());
		LinProbTH<Integer, ArregloDinamico<BigInteger>> aux2 = new LinProbTH<>(nodosMasInfracciones.darTamano());
		int numComMasGrande = 0;
		int numComponentes = 0;
		int maxTamano = 0;
		ComponentesConectadas<BigInteger, InfoInterseccion> cc = new ComponentesConectadas<BigInteger, InfoInterseccion>(grafoIntersecciones);


		for (int i = 0; i < nodosMasInfracciones.darTamano(); i++) {

			BigInteger nodoAct = nodosMasInfracciones.darObjeto(i);
			int idActual = cc.id(grafoIntersecciones.encontrarNumNodo(nodoAct));
			if(aux.get(idActual) == null){
				aux.put(idActual, cc.tamano(grafoIntersecciones.encontrarNumNodo(nodoAct)));
				numComponentes++;
				if(cc.tamano(grafoIntersecciones.encontrarNumNodo(nodoAct))>maxTamano){
					numComMasGrande = idActual;
					maxTamano = cc.tamano(grafoIntersecciones.encontrarNumNodo(nodoAct));
				}
			}
		}

		//Se obtienen los vÃ©rtices
		for(BigInteger s: grafoIntersecciones){
			if(aux.get(cc.id(grafoIntersecciones.encontrarNumNodo(s)))!=null){
				if(aux2.get(cc.id(grafoIntersecciones.encontrarNumNodo(s))) == null){
					ArregloDinamico<BigInteger> nuevo = new ArregloDinamico<>();
					nuevo.agregar(s);
					aux2.put(cc.id(grafoIntersecciones.encontrarNumNodo(s)), nuevo);
				}else{
				aux2.get(cc.id(grafoIntersecciones.encontrarNumNodo(s))).agregar(s);
				}
			}
		}



		for(Integer s: aux){
			aux.put(s, aux2.get(s).darTamano());
			if(aux.get(s)>maxTamano){
				maxTamano = aux.get(s);
				numComMasGrande = s;
			}
			System.out.println("La componente id: "+ s +" con tamaÃ±o de: "+aux.get(s));
			for(BigInteger g:aux2.get(s)){
				System.out.println("VÃ©rtice ID: "+g);
			}
		}

		System.out.println("El nÃºmero de componentes conectadas es: " + numComponentes);
		System.out.println("La componente mÃ¡s grande es: "+ numComMasGrande);
		System.out.println("CANTIDAD CC: "+ cc.numComponentes());
		//Se crea el grafo de la componente mÃ¡s graande
		grafoccMasGrande = new GrafoNDPesos<>(); 
		grafoccMasGrande = obtenerGrafo(aux2.get(numComMasGrande));

	}

	/*
	 * Requerimiento4
	 */
	/**
	 * Encuentra el camino m�s corto en cuanto a m�nima cantidad de arcos
	 */
	public void caminoLongitudMinimoB1(BigInteger idVertice1, BigInteger idVertice2) throws IOException{

		int verticeInicio = grafoIntersecciones.encontrarNumNodo(idVertice1);
		int verticeDestino = grafoIntersecciones.encontrarNumNodo(idVertice2);
		
		BFS<BigInteger,InfoInterseccion, PesosDIVArco> respuesta = new BFS<BigInteger,InfoInterseccion, PesosDIVArco>(grafoIntersecciones, verticeInicio);	
		System.out.println("El camino se muestra a continuaci�n: ");
		for (int s: respuesta.pathTo(verticeDestino)) {
			InfoInterseccion info = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(s));
			System.out.println("V�rtice: "+grafoIntersecciones.encontrarNodo(s) +" Lon: "+info.getLon()+" Lat: " +info.getLat());
		}
		
		System.out.println("El camino tiene una distancia de: " + encontrarDistanciaConInt(respuesta.pathTo(verticeDestino)));
		System.out.println("Total de V�rtices: "+ respuesta.distTo(verticeDestino));
		// Generar mapa para Google Maps
		crearMapaInt("Requerimiento 1B", respuesta.pathTo(verticeDestino), new LatLonCoords[] {grafoIntersecciones.getInfoVertex(idVertice1).getCoords(), grafoIntersecciones.getInfoVertex(idVertice2).getCoords()}, new String[] {"Inicio Recorrido", "Fin Recorrido"});
	}

	
	/*
	 * Requerimiento5
	 */
	/**
	 * M�todo que define la cuadr�cula dado los par�metros dado por el usuario
	 */
	public void definirCuadriculaB2(double lonMin, double lonMax, double latMin, double latMax, int columnas, int filas) throws IOException{

		//Columnas = Y
		// Filas = X

		// Lon = X
		// Lat = Y


		ArregloDinamico<LatLonCoords> ubicacionesGeograficas  = new ArregloDinamico<>();
		double enX = Math.abs(lonMax - lonMin);
		double enY = Math.abs(latMax-latMin);

		
		double deltaX = enX/(columnas-1);
		double deltaY =  enY/(filas-1);

		
		double lonActual = lonMin;
		double latActual = latMin;

		
		System.out.println("Ubicaciones Cuadricula: ");
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {

				LatLonCoords nueva = new LatLonCoords(latActual, lonActual);
				ubicacionesGeograficas.agregar(nueva);
				System.out.println(" Lat: " + latActual +" Lon: " + lonActual);
				
				latActual +=deltaY;
			}
			lonActual += deltaX;
			latActual = latMin;
		}

		nodosCuadricula = new ArregloDinamico<>();
		LinProbTH<BigInteger, Integer> verificacion = new LinProbTH<>((filas)*(columnas));
		for (int i = 0; i < ubicacionesGeograficas.darTamano(); i++) {
			BigInteger nodo = encontrarNodoMasCercano(ubicacionesGeograficas.darObjeto(i));
			if(verificacion.get(nodo) == null){
			verificacion.put(nodo, 1);
			nodosCuadricula.agregar(nodo);
			}
		}
		
		System.out.println("En total se encontraron: "+ nodosCuadricula.darTamano()+ " vertices");
		System.out.println("Los vertices son: ");
		//Impresiï¿½n de resultados
		for (int i = 0; i < nodosCuadricula.darTamano(); i++) {
			BigInteger id = nodosCuadricula.darObjeto(i);
			System.out.println("ID: "+ id + " Lat:" +grafoIntersecciones.getInfoVertex(id).getLat() + " Lon: " + grafoIntersecciones.getInfoVertex(id).getLon());
		}

		// Generar mapa
		crearMapaId("Requerimiento 2B", nodosCuadricula);
	}

	private BigInteger encontrarNodoMasCercano(LatLonCoords coordenadas){

		BigInteger resultado = new BigInteger("0");
		double minimaDistancia = 0;
		int contador = 0;
		boolean primero = true;

		for(InfoInterseccion s: grafoIntersecciones.vertices()){
			if(primero){
				minimaDistancia = Math.abs(s.haversineD(coordenadas));
				resultado = grafoIntersecciones.encontrarNodo(contador);
				primero = false;
			}else if(Math.abs(s.haversineD(coordenadas))<minimaDistancia){
				minimaDistancia =  Math.abs(s.haversineD(coordenadas));
				resultado = grafoIntersecciones.encontrarNodo(contador);
			}
			contador++;
		}
		return resultado;
	}
	
	/*
	 * Requerimiento6
	 */

	public void arbolMSTKruskalC1() throws IOException{
		if(grafoccMasGrande == null){
			System.out.println("Debe primer correr el requerimeinto 2A");
			return;
		}
		
		GrafoNDPesos<BigInteger, InfoInterseccion, PesosDIVArco> grafoArbol = grafoccMasGrande;
		KruskalMST<BigInteger, InfoInterseccion, PesosDIVArco> kruskal  = new KruskalMST<>(grafoArbol);

		int contador = 0;
		System.out.println("Los v�rtices del �rbol generado son: ");
		for(InfoInterseccion s: grafoArbol.vertices()){
			System.out.println("ID: "+grafoArbol.encontrarNodo(contador));
			contador++;
		}
		System.out.println("Los arcos del �rbol generado son: ");
		for (Arco<PesosDIVArco> s:kruskal.arcos()) {
			int primero = s.either();
			int segundo = s.other(primero);
			System.out.println("Desde "+grafoArbol.encontrarNodo(primero)+" Hasta "+ grafoArbol.encontrarNodo(segundo));
		}
		System.out.println("Con una distancia total de: "  + kruskal.weight());
		
		// Generar mapa
		crearMapa("Requerimiento1C", kruskal.arcos(), null, null);
	}


	/*
	 * Requerimiento7
	 */
	public void arbolMSTPrimC2() throws IOException{
		if(grafoccMasGrande == null){
			System.out.println("Debe primer correr el requerimeinto 2A");
			return;
		}
		
		GrafoNDPesos<BigInteger, InfoInterseccion, PesosDIVArco> grafoArbol = grafoccMasGrande;
		Prim<BigInteger, InfoInterseccion, PesosDIVArco> prim = new Prim<>(grafoArbol);
		
		int contador = 0;
		System.out.println("Los v�rtices del �rbol generado son: ");
		for(InfoInterseccion s: grafoArbol.vertices()){
			System.out.println("ID: "+grafoArbol.encontrarNodo(contador));
			contador++;
		}
		System.out.println("Los arcos del �rbol generado son: ");
		for (Arco<PesosDIVArco> s:prim.arcos()) {
			int primero = s.either();
			int segundo = s.other(primero);
			System.out.println("Desde "+grafoArbol.encontrarNodo(primero)+" Hasta "+ grafoArbol.encontrarNodo(segundo));
		}
		
		System.out.println("Con una distancia total de: "  + prim.weight());
		// Generar mapa
		crearMapa("Requerimiento2C", prim.arcos(), null, null);
	}



	/*
	 * Requerimiento8
	 */
	/**
	 * Encuentra los caminos m�s cortos a partir del requerimiento 2B 
	 * @throws IOException 
	 */
	public void caminoCostoMinimoDijkstraC3() throws IOException{
		if(nodosCuadricula == null){
			System.out.println("Debe correr primero el requerimiento 2B (4)");
			return;
		}

		LinProbTH<Integer, ArregloDinamico<BigInteger>> rutas = new LinProbTH<>(nodosCuadricula.darTamano()*2);
			ArregloDinamico<Iterable<Arco<PesosDIVArco>>> iterablesCaminos = new ArregloDinamico<>();
		LinProbTH<Integer, Double> distancias = new LinProbTH<>(nodosCuadricula.darTamano()*2);	
		
			// Para el mapa: para conocer el camino mas largo
			Iterable<Arco<PesosDIVArco>> caminoMasLargo;
			double distanciaMasLarga = -1;
			int contadorMasLargo = 0;
			boolean inicializarExtremos = true;
		
			int numNodoAct;
			double distAct;
		int contador = 0;
		for (int i = 0; i < nodosCuadricula.darTamano()-1; i++) {
			Dijkstra<BigInteger, InfoInterseccion, PesosDIVArco> dijkstra = new Dijkstra<>(grafoIntersecciones, grafoIntersecciones.encontrarNumNodo(nodosCuadricula.darObjeto(i)), 1);
			
			for (int j = i+1; j < nodosCuadricula.darTamano(); j++) {
				
				numNodoAct = grafoIntersecciones.encontrarNumNodo(nodosCuadricula.darObjeto(j));
								
				if(i!=j && dijkstra.existeCaminoHasta(numNodoAct)){
					distAct = dijkstra.distTo(numNodoAct);
					iterablesCaminos.agregar(dijkstra.caminoA(numNodoAct));
					
					ArregloDinamico<BigInteger> aux = new ArregloDinamico<>();
					int primero = 0;
					int segundo = 0;
					boolean primera = true;
					
					for(Arco<PesosDIVArco> s: dijkstra.caminoA(grafoIntersecciones.encontrarNumNodo(nodosCuadricula.darObjeto(j)))){
						if(primera){
							primero = grafoIntersecciones.encontrarNumNodo(nodosCuadricula.darObjeto(i));
							segundo = s.other(primero);
							primera = false;
							aux.agregar(grafoIntersecciones.encontrarNodo(primero));
							aux.agregar(grafoIntersecciones.encontrarNodo(segundo));
						}else{
							segundo = s.other(segundo);
							aux.agregar(grafoIntersecciones.encontrarNodo(segundo));
						}
					}

					rutas.put(contador,aux);
					distancias.put(contador, dijkstra.distTo(j));
					contador++;
					
					if (inicializarExtremos) { // Para el mapa: actualizar camino mas largo
						caminoMasLargo = dijkstra.caminoA(numNodoAct);
						distanciaMasLarga = distAct;
						contadorMasLargo = contador;
						inicializarExtremos = false;
					} else {
						if (distanciaMasLarga < distAct) {
							caminoMasLargo = dijkstra.caminoA(numNodoAct);
							distanciaMasLarga = distAct;
							contadorMasLargo = contador;
						}
					}
				}
			}
		}


		
		System.out.println("A continuaci�n se muestran los camino EXISTENTES: ");
		// Impresi�n de Resultados:
		for (int i = 0; i < contador; i++) {
			System.out.println("Ruta: "+ i + " Distancia: " + distancias.get(i)+" Tiene la secuencia: ");
			for(BigInteger s: rutas.get(i)){
				System.out.println("   " +"V�rtice ID: "+s);
			}
		}
		
		System.out.println("Total de Rutas: " + contador);
		
		// Generar mapa
		
		// Asignar colores
		String colorNormal = "#ff2fc6";
		String colorMasLargo = "#000000";
		
		ArregloDinamico<String> colores = new ArregloDinamico<>();
		Iterable<Arco<PesosDIVArco>> camino;
		for (int i = 0; i < iterablesCaminos.darTamano(); i++) {
			camino = iterablesCaminos.darObjeto(i);
			if (i == contadorMasLargo) {
				colores.agregar(colorMasLargo);
			}
			else {
				colores.agregar(colorNormal);
			}
		}
		
		// Llamar metodo generador
		crearMapaCaminosCol("Requerimiento 3C", iterablesCaminos, colores, nodosCuadricula);
	}

	/*
	 * Requerimiento9
	 */
	/**
	 * Camino m�s corto entre dos v�rtices
	 * Tiene en cuenta dos criterios: n�mero infracciones y cantidad de v�rtices
	 * @throws IOException 
	 */
	public void caminoMasCortoC4(BigInteger idVertice1, BigInteger idVertice2) throws IOException{

		
		// Se utiliza Dijkstra - el 2 en el constructor hace referencia a que se usan como pesos el n�mero de infracciones
		int verticeInicio = grafoIntersecciones.encontrarNumNodo(idVertice1);
		int verticeDestino = grafoIntersecciones.encontrarNumNodo(idVertice2);
		Dijkstra<BigInteger, InfoInterseccion, PesosDIVArco> nuevo = new Dijkstra<BigInteger, InfoInterseccion, PesosDIVArco> (grafoIntersecciones, verticeInicio,3);
		// Se verifica que exista una camino
		
		
		if(nuevo.existeCaminoHasta(verticeDestino)){
			
			//Resultados
			ArregloDinamico<InfoInterseccion> resultadosVertices = new ArregloDinamico<>();
			ArregloDinamico<BigInteger> resultadosVerticesID = new ArregloDinamico<>();
			System.out.println("El camino sigue la siguiente secuencia: ");
			
			int ini = 0;
			int fini = 0;
			boolean primera = true;
			
			// Se obtiene un arreglo con los v�rtices
			for(Arco<PesosDIVArco> s: nuevo.caminoA(verticeDestino)){
				if(primera){
				ini = verticeInicio;
				BigInteger inicial = grafoIntersecciones.encontrarNodo(ini);
				fini = s.other(ini);
				BigInteger destinoFinal = grafoIntersecciones.encontrarNodo(fini);
				resultadosVertices.agregar(grafoIntersecciones.getInfoVertex(inicial));
				resultadosVertices.agregar(grafoIntersecciones.getInfoVertex(destinoFinal));
				resultadosVerticesID.agregar(inicial);
				resultadosVerticesID.agregar(destinoFinal);
				ini = fini;
				primera = false;
				}else{
					BigInteger destinoFinal = grafoIntersecciones.encontrarNodo(s.other(ini));
					ini = s.other(ini);
					resultadosVertices.agregar(grafoIntersecciones.getInfoVertex(destinoFinal));
					resultadosVerticesID.agregar(destinoFinal);
				}
			}
			
			// Impresi�n d resultados
			int contador = 0;
			for(InfoInterseccion s: resultadosVertices){
				System.out.println("El v�rtice: "+ resultadosVerticesID.darObjeto(contador) + " Lon: "+s.getLon()+ " Lat: "+s.getLat());
				contador++;
			}
			System.out.println("La distancia estimada del camino es de: " + encontrarDistancia(resultadosVerticesID));
			System.out.println("El n�mero de infracciones del camino es: " + encontrarInfracciones(resultadosVerticesID));
			
			// Generar mapa para Google Maps
			
			crearMapa("Requerimiento 4C", nuevo.caminoA(verticeDestino), new LatLonCoords[] {grafoIntersecciones.getInfoVertex(idVertice1).getCoords(), grafoIntersecciones.getInfoVertex(idVertice2).getCoords()}, new String[] {"Inicio Recorrido", "Fin Recorrido"});
		}
		else{
			System.out.println("No existe un camino entre los dos v�rtices");
		}
	
		
	
	}


	/*
	 * M�todos Auxiliares
	 */
	public int encontrarInfracciones(ArregloDinamico<BigInteger> pNodos){
		int respuesta = 0;
		
		// VERIFICAR PESO DIST!
		
		for (int i = 0; i < pNodos.darTamano(); i++) {
			respuesta +=grafoIntersecciones.getInfoVertex(pNodos.darObjeto(i)).getNInfracciones();
		}
		
		return respuesta;
	}

	
	public double encontrarDistancia(ArregloDinamico<BigInteger> pNodos){
		double respuesta = 0;
		
		// VERIFICAR PESO DIST!
		
		for (int i = 0; i < pNodos.darTamano()-1; i++) {
			respuesta+=grafoIntersecciones.getInfoArc(pNodos.darObjeto(i), pNodos.darObjeto(i+1)).darPesoDist();
		}
		
		return respuesta;
	}
	
	public double encontrarDistanciaConInt(Iterable<Integer> pNodos){
		double respuesta = 0;
		
		// VERIFICAR PESO DIST!
		ArregloDinamico<BigInteger> nodos = new ArregloDinamico<>();
		
		for(Integer s: pNodos){
			nodos.agregar(grafoIntersecciones.encontrarNodo(s));
		}
		
		for (int i = 0; i < nodos.darTamano()-1; i++) {
			respuesta=+grafoIntersecciones.getInfoArc(nodos.darObjeto(i), nodos.darObjeto(i+1)).darPesoDist();
		}
		
		return respuesta;
	}


	public GrafoNDPesos<BigInteger, InfoInterseccion, PesosDIVArco> obtenerGrafo(ArregloDinamico<BigInteger> vertices){
		GrafoNDPesos<BigInteger, InfoInterseccion, PesosDIVArco> respuesta = new GrafoNDPesos<>();
		int numVertices = 0;
		for (int i = 0; i < vertices.darTamano(); i++) {
			respuesta.addVertex(vertices.darObjeto(i), grafoIntersecciones.getInfoVertex(vertices.darObjeto(i)));
		}


		for(BigInteger s: vertices){

			//for(Arco<PesosDIVArco> arco: grafoIntersecciones.darRepresentacion().get(grafoIntersecciones.encontrarNumNodo(s))){
			//	BigInteger destino = grafoIntersecciones.encontrarNodo(arco.other(grafoIntersecciones.encontrarNumNodo(s)));
			//	// Se verifica que los dos vertices existan en el nuevo grafo
			//	if(respuesta.encontrarNumNodo(s)!=-1 && respuesta.encontrarNumNodo(destino)!=-1 ){
			//		if(respuesta.getInfoArc(s, destino)==null){
			//			respuesta.addEdge(s, destino, arco.darInformacion());
			//		}
			//	}

			//}
			
			Iterator<BigInteger> iteradorAdj = grafoIntersecciones.adj(s);
			BigInteger idAdj;
			while (iteradorAdj.hasNext()) {
				idAdj = iteradorAdj.next();
				if (respuesta.getInfoVertex(idAdj) != null && respuesta.getInfoArc(s, idAdj) == null) {
					respuesta.addEdge(s, idAdj, grafoIntersecciones.getInfoArc(s, idAdj));
				}
			}

		}

		return respuesta;
	}

	private void crearMapa(String nombreHTML, Iterable<Arco<PesosDIVArco>> arcos, LatLonCoords[] marcadores, String[] nomMarc) throws IOException {
		File archivo = new File(nombreHTML + ".html");
		if (!archivo.exists()) {
			archivo.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

		// Escribir Cabeza

		writer.write("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=utf-8 />\n" + 
				"<title>Grafo generado</title>\n" + 
				"<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\n" + 
				"<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>\n" + 
				"<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' /> \n" + 
				"<style>\n" + 
				" body { margin:0; padding:0; }\n" + 
				"#map { position:absolute; top:0; bottom:0; width:100%; }\n" + 
				"</style>\n" + 
				"</head>\n" +
				"<body>\n" + 
				"<div id='map'>\n" + 
				"</div>\n");

		// Inicio del script
		Double centerLat = 38.9097115;
		Double centerLon = -77.0289048;

		Double leftLat = 38.9097115;
		Double leftLon = -77.0289048;
		Double rightLat = 38.9097843;
		Double rightLon =-77.0288552;

		writer.write("<script>\n" + 
				"L.mapbox.accessToken = 'pk.eyJ1IjoianVhbnBhYmxvY29ycmVhcHVlcnRhIiwiYSI6ImNqb2FjcHNjcjFuemwzcXB1M3E0YnB4bHIifQ.oXuYfXtCqmXY52b8Ystuyw';\n" + 
				"var map = L.mapbox.map('map', 'mapbox.streets').setView(["+ centerLat + ", "+ centerLon +"], 17);\n" + 
				"var extremos = [["+ leftLat +", "+ leftLon + "],[" + rightLat + ", " + rightLon + "]];\n" + 
				"map.fitBounds(extremos);\n");

		// Agregar edges del grafo como lineas en el mapa	    
		ITablaHash<BigInteger[], Boolean> edgesAgregados = new LinProbTH<>(11); // Para agregar solo una vez cada edge

		Iterable<BigInteger> iterableAdj;
		int iden1; LatLonCoords coords1;
		int iden2; LatLonCoords coords2;
		PesosDIVArco infoArcoAct;

		// Crear una linea por cada arco
		for (Arco<PesosDIVArco> arcoAct : arcos) {

			iden1 = arcoAct.either(); 
			coords1 = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(iden1)).getCoords();
			
			iden2 = arcoAct.other(iden1); 
			coords2 = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(iden2)).getCoords();
			

			writer.write("var line_points = [[" + coords1.getLat() + ", " + coords1.getLon() + "] "
								+ ",[" + coords2.getLat() + ", " + coords2.getLon() + "]];\n");
			writer.write("var polyline_options = {color: '#ff2fc6'};\n" + 
								"L.polyline(line_points, polyline_options).addTo(map);\n\n");
		}
		

		// Markers
		if (marcadores != null) {
			LatLonCoords coordenadaAct;
			String nombreAct;
			
			for (int i = 0; i < marcadores.length; i++) {
				coordenadaAct = marcadores[i];
				nombreAct = nomMarc[i];
				writer.write(
						"L.marker( [" + coordenadaAct.getLat() + ", " + coordenadaAct.getLon() + "], { title: \"" + nombreAct + "\"} ).addTo(map);\n");
			}
		}
		
			

		// Final
		writer.write("</script>\n" + 
				"</body>\n" + 
				"</html>");

		writer.close();


		//return archivo;
	}
	
	private void crearMapa2A(String nombreHTML, Iterable<Arco<PesosDIVArco>> arcos, ArregloDinamico<BigInteger> marcadores) throws IOException {
		File archivo = new File(nombreHTML + ".html");
		if (!archivo.exists()) {
			archivo.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

		// Escribir Cabeza

		writer.write("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=utf-8 />\n" + 
				"<title>Grafo generado</title>\n" + 
				"<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\n" + 
				"<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>\n" + 
				"<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' /> \n" + 
				"<style>\n" + 
				" body { margin:0; padding:0; }\n" + 
				"#map { position:absolute; top:0; bottom:0; width:100%; }\n" + 
				"</style>\n" + 
				"</head>\n" +
				"<body>\n" + 
				"<div id='map'>\n" + 
				"</div>\n");

		// Inicio del script
		Double centerLat = 38.9097115;
		Double centerLon = -77.0289048;

		Double leftLat = 38.9097115;
		Double leftLon = -77.0289048;
		Double rightLat = 38.9097843;
		Double rightLon =-77.0288552;

		writer.write("<script>\n" + 
				"L.mapbox.accessToken = 'pk.eyJ1IjoianVhbnBhYmxvY29ycmVhcHVlcnRhIiwiYSI6ImNqb2FjcHNjcjFuemwzcXB1M3E0YnB4bHIifQ.oXuYfXtCqmXY52b8Ystuyw';\n" + 
				"var map = L.mapbox.map('map', 'mapbox.streets').setView(["+ centerLat + ", "+ centerLon +"], 17);\n" + 
				"var extremos = [["+ leftLat +", "+ leftLon + "],[" + rightLat + ", " + rightLon + "]];\n" + 
				"map.fitBounds(extremos);\n");

		// Agregar edges del grafo como lineas en el mapa	    
		ITablaHash<BigInteger[], Boolean> edgesAgregados = new LinProbTH<>(11); // Para agregar solo una vez cada edge

		Iterable<BigInteger> iterableAdj;
		int iden1; LatLonCoords coords1;
		int iden2; LatLonCoords coords2;
		PesosDIVArco infoArcoAct;

		// Crear una linea por cada arco
		for (Arco<PesosDIVArco> arcoAct : arcos) {

			iden1 = arcoAct.either(); 
			coords1 = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(iden1)).getCoords();
			
			iden2 = arcoAct.other(iden1); 
			coords2 = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(iden2)).getCoords();
			

			writer.write("var line_points = [[" + coords1.getLat() + ", " + coords1.getLon() + "] "
								+ ",[" + coords2.getLat() + ", " + coords2.getLon() + "]];\n");
			writer.write("var polyline_options = {color: '#ff2fc6'};\n" + 
								"L.polyline(line_points, polyline_options).addTo(map);\n\n");
		}
		

		// Markers
		if (marcadores != null) {
			LatLonCoords coordenadaAct;
			String nombreAct;
			
			for (int i = 0; i < marcadores.darTamano(); i++) {
				coordenadaAct = grafoIntersecciones.getInfoVertex(marcadores.darObjeto(i)).getCoords();
				
				writer.write(
						"L.marker( [" + coordenadaAct.getLat() + ", " + coordenadaAct.getLon() + "], { title: \"" + "" + "\"} ).addTo(map);\n");
			}
		}
		
			

		// Final
		writer.write("</script>\n" + 
				"</body>\n" + 
				"</html>");

		writer.close();


		//return archivo;
	}
	
	private void crearMapaInt(String nombreHTML, Iterable<Integer> secVert, LatLonCoords[] marcadores, String[] nomMarc) throws IOException {
		File archivo = new File(nombreHTML + ".html");
		if (!archivo.exists()) {
			archivo.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

		// Escribir Cabeza

		writer.write("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=utf-8 />\n" + 
				"<title>Grafo generado</title>\n" + 
				"<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\n" + 
				"<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>\n" + 
				"<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' /> \n" + 
				"<style>\n" + 
				" body { margin:0; padding:0; }\n" + 
				"#map { position:absolute; top:0; bottom:0; width:100%; }\n" + 
				"</style>\n" + 
				"</head>\n" +
				"<body>\n" + 
				"<div id='map'>\n" + 
				"</div>\n");

		// Inicio del script
		Double centerLat = 38.9097115;
		Double centerLon = -77.0289048;

		Double leftLat = 38.9097115;
		Double leftLon = -77.0289048;
		Double rightLat = 38.9097843;
		Double rightLon =-77.0288552;

		writer.write("<script>\n" + 
				"L.mapbox.accessToken = 'pk.eyJ1IjoianVhbnBhYmxvY29ycmVhcHVlcnRhIiwiYSI6ImNqb2FjcHNjcjFuemwzcXB1M3E0YnB4bHIifQ.oXuYfXtCqmXY52b8Ystuyw';\n" + 
				"var map = L.mapbox.map('map', 'mapbox.streets').setView(["+ centerLat + ", "+ centerLon +"], 17);\n" + 
				"var extremos = [["+ leftLat +", "+ leftLon + "],[" + rightLat + ", " + rightLon + "]];\n" + 
				"map.fitBounds(extremos);\n");

		// Agregar edges del grafo como lineas en el mapa	    
		ITablaHash<BigInteger[], Boolean> edgesAgregados = new LinProbTH<>(11); // Para agregar solo una vez cada edge

		Iterable<BigInteger> iterableAdj;
		int iden1; LatLonCoords coords1;
		int iden2 = -1; LatLonCoords coords2;
		PesosDIVArco infoArcoAct;

		// Crear una linea por cada arco
		boolean primera = true;
		for (Integer idenAct : secVert) {
			if (primera) {
				iden2 = idenAct;
				primera = false;
				continue;
			}
			
			iden1 = iden2; 
			coords1 = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(iden1)).getCoords();
			
			iden2 = idenAct; 
			coords2 = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(iden2)).getCoords();
			

			writer.write("var line_points = [[" + coords1.getLat() + ", " + coords1.getLon() + "] "
								+ ",[" + coords2.getLat() + ", " + coords2.getLon() + "]];\n");
			writer.write("var polyline_options = {color: '#ff2fc6'};\n" + 
								"L.polyline(line_points, polyline_options).addTo(map);\n\n");
		}
		

		// Markers
		
		LatLonCoords coordenadaAct;
		String nombreAct;
		for (int i = 0; i < marcadores.length; i++) {
			coordenadaAct = marcadores[i];
			nombreAct = nomMarc[i];
			writer.write(
					"L.marker( [" + coordenadaAct.getLat() + ", " + coordenadaAct.getLon() + "], { title: \"" + nombreAct + "\"} ).addTo(map);\n");
		}
			

		// Final
		writer.write("</script>\n" + 
				"</body>\n" + 
				"</html>");

		writer.close();


		//return archivo;
	}
	
	private void crearMapaId(String nombreHTML, Iterable<BigInteger> marcadores) throws IOException {
		File archivo = new File(nombreHTML + ".html");
		if (!archivo.exists()) {
			archivo.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

		// Escribir Cabeza

		writer.write("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=utf-8 />\n" + 
				"<title>Grafo generado</title>\n" + 
				"<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\n" + 
				"<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>\n" + 
				"<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' /> \n" + 
				"<style>\n" + 
				" body { margin:0; padding:0; }\n" + 
				"#map { position:absolute; top:0; bottom:0; width:100%; }\n" + 
				"</style>\n" + 
				"</head>\n" +
				"<body>\n" + 
				"<div id='map'>\n" + 
				"</div>\n");

		// Inicio del script
		Double centerLat = 38.9097115;
		Double centerLon = -77.0289048;

		Double leftLat = 38.9097115;
		Double leftLon = -77.0289048;
		Double rightLat = 38.9097843;
		Double rightLon =-77.0288552;

		writer.write("<script>\n" + 
				"L.mapbox.accessToken = 'pk.eyJ1IjoianVhbnBhYmxvY29ycmVhcHVlcnRhIiwiYSI6ImNqb2FjcHNjcjFuemwzcXB1M3E0YnB4bHIifQ.oXuYfXtCqmXY52b8Ystuyw';\n" + 
				"var map = L.mapbox.map('map', 'mapbox.streets').setView(["+ centerLat + ", "+ centerLon +"], 17);\n" + 
				"var extremos = [["+ leftLat +", "+ leftLon + "],[" + rightLat + ", " + rightLon + "]];\n" + 
				"map.fitBounds(extremos);\n");	

		// Markers
		
		LatLonCoords coordenadaAct;
		String nombreAct;
		int i = 0;
		for (BigInteger idVertex : marcadores) {
			coordenadaAct = grafoIntersecciones.getInfoVertex(idVertex).getCoords();//marcadores[i];
			//nombreAct = nomMarc[i];
			writer.write(
					"L.marker( [" + coordenadaAct.getLat() + ", " + coordenadaAct.getLon() + "], { title: \"" + "" + "\"} ).addTo(map);\n");
			i++;
		}
			

		// Final
		writer.write("</script>\n" + 
				"</body>\n" + 
				"</html>");

		writer.close();


		//return archivo;
	}
	
	private void crearMapaCaminosCol(String nombreHTML, ArregloDinamico<Iterable<Arco<PesosDIVArco>>> listaArcos, ArregloDinamico<String> colores, Iterable<BigInteger> marcadores) throws IOException {
		File archivo = new File(nombreHTML + ".html");
		if (!archivo.exists()) {
			archivo.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

		// Escribir Cabeza

		writer.write("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=utf-8 />\n" + 
				"<title>Grafo generado</title>\n" + 
				"<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\n" + 
				"<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>\n" + 
				"<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' /> \n" + 
				"<style>\n" + 
				" body { margin:0; padding:0; }\n" + 
				"#map { position:absolute; top:0; bottom:0; width:100%; }\n" + 
				"</style>\n" + 
				"</head>\n" +
				"<body>\n" + 
				"<div id='map'>\n" + 
				"</div>\n");

		// Inicio del script
		Double centerLat = 38.9097115;
		Double centerLon = -77.0289048;

		Double leftLat = 38.9097115;
		Double leftLon = -77.0289048;
		Double rightLat = 38.9097843;
		Double rightLon =-77.0288552;

		writer.write("<script>\n" + 
				"L.mapbox.accessToken = 'pk.eyJ1IjoianVhbnBhYmxvY29ycmVhcHVlcnRhIiwiYSI6ImNqb2FjcHNjcjFuemwzcXB1M3E0YnB4bHIifQ.oXuYfXtCqmXY52b8Ystuyw';\n" + 
				"var map = L.mapbox.map('map', 'mapbox.streets').setView(["+ centerLat + ", "+ centerLon +"], 17);\n" + 
				"var extremos = [["+ leftLat +", "+ leftLon + "],[" + rightLat + ", " + rightLon + "]];\n" + 
				"map.fitBounds(extremos);\n");

		// Agregar edges del grafo como lineas en el mapa	    
		ITablaHash<BigInteger[], Boolean> edgesAgregados = new LinProbTH<>(11); // Para agregar solo una vez cada edge

		Iterable<BigInteger> iterableAdj;
		int iden1; LatLonCoords coords1;
		int iden2; LatLonCoords coords2;
		PesosDIVArco infoArcoAct;

		// Generar mapa de cada camino
		int i = 0;
		for (Iterable<Arco<PesosDIVArco>> arcos : listaArcos) {
			String colorAct = colores.darObjeto(i);
			
			// Crear una linea por cada arco
			for (Arco<PesosDIVArco> arcoAct : arcos) {
	
				iden1 = arcoAct.either(); 
				coords1 = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(iden1)).getCoords();
				
				iden2 = arcoAct.other(iden1); 
				coords2 = grafoIntersecciones.getInfoVertex(grafoIntersecciones.encontrarNodo(iden2)).getCoords();
				
	
				writer.write("var line_points = [[" + coords1.getLat() + ", " + coords1.getLon() + "] "
									+ ",[" + coords2.getLat() + ", " + coords2.getLon() + "]];\n");
				writer.write("var polyline_options = {color: '"+ colorAct +"'};\n" + 
									"L.polyline(line_points, polyline_options).addTo(map);\n\n");
			}
			i++;
		}

		// Markers
		LatLonCoords coordenadaAct;
		String nombreAct;
		int j = 0;
		for (BigInteger idVertex : marcadores) {
			coordenadaAct = grafoIntersecciones.getInfoVertex(idVertex).getCoords();//marcadores[i];
			//nombreAct = nomMarc[i];
			writer.write(
					"L.marker( [" + coordenadaAct.getLat() + ", " + coordenadaAct.getLon() + "], { title: \"" + "" + "\"} ).addTo(map);\n");
			j++;
		}
		
			

		// Final
		writer.write("</script>\n" + 
				"</body>\n" + 
				"</html>");

		writer.close();


		//return archivo;
	}
}
