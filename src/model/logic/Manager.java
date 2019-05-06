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
import model.data_structures.IGraph;
import model.data_structures.IQueue;
import model.data_structures.ITablaHash;
import model.data_structures.LinProbTH;
import model.data_structures.LinkedList;
import model.data_structures.MaxHeapCP;
import model.data_structures.Queue;
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
	private final String NOMBRE_MAPA_JSON = "persistenciaJsonMapa.json";
	
	/**
	 * Lista donde se van a cargar los datos de los archivos
	 */
	private static IGraph<BigInteger, InfoInterseccion, PesosDIVArco> grafoIntersecciones;

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
	 * Carga la informacion de un semestre dado a un grafo ya creado
	 * @param n
	 * @return
	 */
	public EstadisticasCargaInfracciones cargarSemestreAGrafo(int n) {
		return cargador.loadMovingViolations(n, grafoIntersecciones);
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
	    writer.write("<script>\n" + 
	    		"L.mapbox.accessToken = 'pk.eyJ1IjoianVhbnBhYmxvY29ycmVhcHVlcnRhIiwiYSI6ImNqb2FjcHNjcjFuemwzcXB1M3E0YnB4bHIifQ.oXuYfXtCqmXY52b8Ystuyw';\n" + 
	    		"var map = L.mapbox.map('map', 'mapbox.streets').setView([38.9097115, -77.0289048], 17);\n" + 
	    		"var extremos = [[38.9097115, -77.0289048],[38.9097843, -77.0288552]];\n" + 
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
	    
	    // Final
	    writer.write(
	    		"L.marker( [41.88949181977,-87.6882193648], { title: \"Nodo de salida\"} ).addTo(map);\n" + 
	    		"L.marker( [41.768726,-87.664069], { title: \"Nodo de llegada\"} ).addTo(map);\n" + 
	    		"</script>\n" + 
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
		grafoIntersecciones = cargador.cargarDeJson("persistenciaGrafoWashington.json");
		
		String[] nombreMeses = new String[] {"January_wgs84.csv", 
				//"February_wgs84.csv",
				//"March_wgs84.csv",
				//"April_wgs84.csv",
				//"May_wgs84.csv",
				//"June_wgs84.csv",
				//"July_wgs84.csv",
				//"August_wgs84.csv",
				//					"September_wgs84.csv", 
				//					"October_wgs84.csv",
				//					"November_wgs84.csv",
				//					"December_wgs84.csv"
				};
		
		for (int i = 0; i < nombreMeses.length; i++) {
			cargador.loadMovingViolations(new String[] {nombreMeses[i]}, grafoIntersecciones);
			guardarEnJson("jsonHasta"+i);
		}
	}
}
