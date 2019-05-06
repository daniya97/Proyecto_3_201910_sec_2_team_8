package model.logic;

import model.data_structures.ArregloDinamico;

public class InfoInterseccion {
	private int nInfracciones;
	private LatLonCoords coords;
	private ArregloDinamico<Integer> arregloInfracciones;
	
	public InfoInterseccion(double plat, double plon) {
		nInfracciones = 0;
		coords = new LatLonCoords(plat, plon);
		arregloInfracciones = new ArregloDinamico<>();
	}
	
	//public InfoInterseccion(int pnInfracciones, double plat, double plon) {
	//	nInfracciones = pnInfracciones;
	//	coords = new LatLonCoords(plat, plon);
	//}
	
	/*
	 * Setters
	 */
	public int aumentarNInfracciones(int idInf) {
		arregloInfracciones.agregar(idInf);
		return ++nInfracciones;
	}
	
	public void setNInfracciones(int n) {
		nInfracciones = n;
	}
	
	/*
	 * Getters
	 */
	
	public int darNInfracciones() {
		return nInfracciones;
	}
	
	public LatLonCoords darCoords() {
		return coords;
	}
	
	public double darLat() {
		return coords.getLat();
	}
	
	public double darLon() {
		return coords.getLon();
	}

	public double haversineD(InfoInterseccion infoVertex) {
		return coords.haversineD(infoVertex.darCoords());
	}
	
	public double haversineD(LatLonCoords coords2) {
		return coords.haversineD(coords2);
	}
}
