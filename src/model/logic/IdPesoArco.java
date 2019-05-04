package model.logic;

import model.data_structures.InfoArco;

public class IdPesoArco implements InfoArco {

	private int idArco;
	private double pesoArco;
	
	public IdPesoArco(int pIdArco, double pPesoArco){
		idArco = pIdArco;
		pesoArco = pPesoArco;
	}
	
	public int darIdArco(){
		return idArco;
	}
	
	public double darPesoArco(){
		return pesoArco;
	}

}
