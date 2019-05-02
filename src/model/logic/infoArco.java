package model.logic;

public class infoArco<K> {
	
	private int idArco;
	private double pesoArco;
	private K kEither;
	private K kOther;
	
	public infoArco(int pIdArco, double pPesoArco, K pkEither, K pkOther){
		idArco = pIdArco;
		pesoArco = pPesoArco;
		kEither = pkEither;
		kOther = pkOther;
	}
	
	public int darIdArco(){
		return idArco;
	}
	
	public double darPesoArco(){
		return pesoArco;
	}
	
	public K darKEither() {
		return kEither;
	}
	
	public K darKOther(K kEither) {
		return kEither.equals(this.kEither)? kOther : kEither;
	}
	
}
