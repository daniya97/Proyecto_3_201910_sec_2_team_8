package model.logic;

import java.math.BigInteger;

public class VertexSummary {
	private BigInteger id;
	private double lat;
	private double lon;
	private BigInteger[] adj;
	private int[] infraccionesIds;
	private int nInfracciones;
	
	public BigInteger getId() {
		return id;
	}
	
	public BigInteger[] getAdj() {
		return adj;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLon() {
		return lon;
	}
	
	public int[] getInfraccionesId () {
		return infraccionesIds;
	}
	
	public int getNInfracciones() {
		return nInfracciones;
	}
}
