package model.logic;

import java.math.BigInteger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.data_structures.GrafoNDPesos;
import model.data_structures.IGraph;
import model.data_structures.IQueue;
import model.data_structures.ITablaHash;
import model.data_structures.IdPesoArco;
import model.data_structures.LinProbTH;
import model.data_structures.Queue;

public class LectorXML extends DefaultHandler {
	/*
	 * Atributos
	 */
	private IGraph<BigInteger, InfoInterseccion, PesosDIVArco> grafo;
	
	private ITablaHash<BigInteger, LatLonCoords> verticesPosibles;
	public boolean insideWay = false;
	IQueue<BigInteger> wayVertexKeys = null;
	int wayId;
	
	boolean otroNdAfter = false; // Booleano que verifica que luego del tag highway no se encuentran nodos
	
	public LectorXML() { }
	
	/*
	 * Metodos de DefaultHandler
	 */
	public void startDocument() throws SAXException {
		grafo = new GrafoNDPesos<BigInteger, InfoInterseccion, PesosDIVArco>();
		verticesPosibles = new LinProbTH<BigInteger, LatLonCoords>(11);
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		String tag = localName;
		
		if(tag.equals("node")) {
			//System.out.println("Node");
			LatLonCoords info = new LatLonCoords(Double.parseDouble(atts.getValue("lat")), Double.parseDouble(atts.getValue("lon")));
			//grafo.addVertex(new BigInteger(atts.getValue("id")), info); // BigIng
			verticesPosibles.put(new BigInteger(atts.getValue("id")), info);
		}
		
		if (tag.equals("way")) {	
			insideWay = true;
			wayVertexKeys = new Queue<>(); 
			wayId = Integer.parseInt(atts.getValue("id"));
			
			otroNdAfter = false;
		}
		
		if (tag.equals("nd") && insideWay) {
			wayVertexKeys.enqueue(new BigInteger(atts.getValue("ref"))); //BigInt
			
			if (otroNdAfter) System.out.println("Se encontro un nd luego del tag highway!!!!!!");
		}
		
		if (tag.equals("tag") && insideWay && atts.getValue("k").equals("highway")) {
			BigInteger anteriorK = null;
			LatLonCoords coordsPre = new LatLonCoords(-1, -1);
			
			LatLonCoords coordsAct;
			
			for (BigInteger vertexK : wayVertexKeys) {
				if (anteriorK == null) {
					anteriorK = vertexK; 
					coordsPre = verticesPosibles.get(anteriorK);
					
					// Agregar el primer vertice si no está ya en el grafo
					if (grafo.getInfoVertex(anteriorK) == null) grafo.addVertex(anteriorK, new InfoInterseccion(coordsPre));
					continue;
				}
				
				coordsAct = verticesPosibles.get(vertexK);
				
				// Agregar vertice actual si no está ya en el grafo
				if (grafo.getInfoVertex(vertexK) == null) grafo.addVertex(vertexK, new InfoInterseccion(coordsAct));
				
				// Agregar arco entre ellos si no existe
				if (grafo.getInfoArc(anteriorK, vertexK) == null) {
					grafo.addEdge(anteriorK, vertexK, new PesosDIVArco(coordsPre.haversineD(coordsAct))); // Se esta ignorando el id del way
				}
			}
			
			otroNdAfter = true;
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equals("way")) {
			insideWay = false;
			wayVertexKeys = null;
		}
	}
	
	public void endDocument() throws SAXException {}
	
	/*
	 * Metodos para interaccion con el Manager
	 */
	public IGraph<BigInteger, InfoInterseccion, PesosDIVArco> darGrafo(){
		return grafo;
	}
}
