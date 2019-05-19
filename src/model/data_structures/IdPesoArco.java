package model.data_structures;

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

	@Override
	public double darPesoNInfr() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	
}
