package model.data_structures;

public class PesoArco implements InfoArco {
	
	private double peso;
	
	public PesoArco(double peso) {
		this.peso = peso;
	}
	
	public double darPesoArco() {
		return peso;
	}

	@Override
	public double darPesoNInfr() {
		// TODO Auto-generated method stub
		return 0;
	}
}
