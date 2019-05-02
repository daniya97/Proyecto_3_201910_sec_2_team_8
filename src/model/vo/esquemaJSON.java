package model.vo;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.LinkedList;

public class esquemaJSON<K> {
	
	private K id;
	private double lat;
	private double lon;
	private  K[] adj;
	
	
	
	public esquemaJSON(K pId, K[]pAdj, double pLat, double pLon){
		id = pId;
		lat = pLat;
		lon = pLon;
		adj = pAdj;
	}
	
	
	
}
