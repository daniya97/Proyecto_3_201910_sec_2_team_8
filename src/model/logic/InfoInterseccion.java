package model.logic;

import model.data_structures.ArregloDinamico;

public class InfoInterseccion {
	private int nInfracciones;
	private LatLonCoords coords;
	private ArregloDinamico<Integer> arregloInfracciones;
	
	/*
	 * Constructores
	 */
	public InfoInterseccion(LatLonCoords pCoords) {
		nInfracciones = 0;
		coords = pCoords;
		arregloInfracciones = new ArregloDinamico<>();
	}
	
	public InfoInterseccion(LatLonCoords pCoords, int nInf) {
		nInfracciones = nInf;
		coords = pCoords;
		arregloInfracciones = new ArregloDinamico<>();
	}
	
	public InfoInterseccion(double plat, double plon) {
		nInfracciones = 0;
		coords = new LatLonCoords(plat, plon);
		arregloInfracciones = new ArregloDinamico<>();
	}
	
	public InfoInterseccion(double plat, double plon, int nInf) {
		nInfracciones = nInf;
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
	
	public int getNInfracciones() {
		return nInfracciones;
	}
	
	public LatLonCoords getCoords() {
		return coords;
	}
	
	public double getLat() {
		return coords.getLat();
	}
	
	public double getLon() {
		return coords.getLon();
	}

	public double haversineD(InfoInterseccion infoVertex) {
		return coords.haversineD(infoVertex.getCoords());
	}
	
	public double haversineD(LatLonCoords coords2) {
		return coords.haversineD(coords2);
	}
}
