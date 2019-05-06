package model.logic;

import model.data_structures.InfoArco;

public class PesosDIVArco implements InfoArco {
	
	private double pesoPpalDistancia;
	private double pesoNInfracciones;
	private double pesoNVertices;
	
	/*
	 * Constructores
	 */
	public PesosDIVArco() {
		pesoNVertices = 2;
	}
	
	public PesosDIVArco(double pDist) {
		pesoPpalDistancia = pDist;
		pesoNVertices = 2;
	}
	
	public PesosDIVArco(double pDist, double pNInfr, double pNVert) {
		pesoPpalDistancia = pDist;
		pesoNInfracciones = pNInfr;
		pesoNVertices = pNVert;
	}
	
	/*
	 * Setters
	 */
	public void setPesoDist(double pDist) {
		pesoPpalDistancia = pDist;
	}
	
	public void setPesoInfr(double pNInfr) {
		pesoNInfracciones = pNInfr;
	}
	
	public void setPesoVert(double pNVert) {
		pesoNVertices = pNVert;
	}
	
	/*
	 * Getters
	 */
	@Override
	public double darPesoArco() {
		// TODO Auto-generated method stub
		return pesoPpalDistancia;
	}
	
	public double darPesoDist() {
		return pesoPpalDistancia;
	}
	
	public double darPesoNInfr() {
		return pesoNInfracciones;
	}
	
	public double darPesoNVert() {
		return pesoNVertices;
	}

}
