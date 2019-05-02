package model.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.parsers.*;
import org.xml.sax.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.GrafoNDPesos;
import model.data_structures.IGraph;
import model.data_structures.ITablaHash;
import model.data_structures.LinProbTH;
import model.data_structures.LinkedList;
import model.vo.esquemaJSON;

public class Manager {

	/**
	 * Lista donde se van a cargar los datos de los archivos
	 */
	private static IGraph<BigInteger, LatLonCoords> grafoIntersecciones;


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
		

	}


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


	public boolean guardarEnJson(String nombreJsonC) {
		
		
		esquemaJSON<BigInteger> auxiliar;
		BigInteger id;
		esquemaJSON<BigInteger>[] lista = new esquemaJSON[grafoIntersecciones.V()];
		LinkedList<Arco> aux;
		BigInteger[] lista2;
		double lat;
		double lon;
		int contador = 0;


		for (int i = 0; i < lista.length; i++) {
			id = grafoIntersecciones.encontrarNodo(i);
			aux = grafoIntersecciones.darRepresentacion().get(i);
			
			lista2 = new BigInteger[aux.darTamanoLista()];
			
			contador = 0;
			for(Arco s: aux){
				lista2[contador] = grafoIntersecciones.encontrarNodo(s.other(i));
				contador++;
			}
			
			lat = grafoIntersecciones.getInfoVertex(id).getLat();
			lon = grafoIntersecciones.getInfoVertex(id).getLon();
			auxiliar = new esquemaJSON<BigInteger>(id, lista2, lat, lon);
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
						new infoArco<BigInteger>(-1, grafoIntersecciones.getInfoVertex(verticeArcId).haversineD(grafoIntersecciones.getInfoVertex(verticeAct.getId())), verticeAct.getId(), verticeArcId));
					nArcos += 1;
				}
			}
		}
		
		return new int[] {nVertices, nArcos};
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
	    infoArco<BigInteger> arcoAct;
	    
	    //boolean primerEl = true;
	    for (BigInteger id : grafoIntersecciones) {
			
			iterableAdj = new Iterable<BigInteger>() {		
				@Override public Iterator<BigInteger> iterator() { 
				return grafoIntersecciones.adj(id); } };

			for (BigInteger verAdj : iterableAdj) {
				arcoAct = grafoIntersecciones.getInfoArc(id, verAdj);
				
				id1 = arcoAct.darKEither();
				coords1 = grafoIntersecciones.getInfoVertex(id1);
				id2 = arcoAct.darKOther(id1);
				coords2 = grafoIntersecciones.getInfoVertex(id2);
				
				if (edgesAgregados.get(new BigInteger[] {id1, id2}) != null) continue;
				else edgesAgregados.put(new BigInteger[] {id1, id2}, true);
				
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
}
