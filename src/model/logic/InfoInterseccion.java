package model.logic;

public class InfoInterseccion {
	private int nInfracciones;
	private LatLonCoords coords;
	
	public InfoInterseccion(double plat, double plon) {
		nInfracciones = 0;
		coords = new LatLonCoords(plat, plon);
	}
	
	public InfoInterseccion(int pnInfracciones, double plat, double plon) {
		nInfracciones = pnInfracciones;
		coords = new LatLonCoords(plat, plon);
	}
	
	/*
	 * Setters
	 */
	public int aumentarNInfracciones() {
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
