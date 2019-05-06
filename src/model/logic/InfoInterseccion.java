package model.logic;

public class InfoInterseccion {
	private int objectId;
	private int objectId1;
	private int nInfracciones;
	private LatLonCoords coords;
	
	public InfoInterseccion(int pobjectId, int pobjectId1, int pnInfracciones, double plat, double plon) {
		objectId = pobjectId;
		objectId1 = pobjectId1;
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
	public int darObjectId() {
		return objectId;
	}
	
	public int darObjectId1() {
		return objectId1;
	}
	
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
}
