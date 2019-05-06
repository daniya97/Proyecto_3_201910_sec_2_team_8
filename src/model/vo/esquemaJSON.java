package model.vo;

import com.sun.javafx.iio.png.PNGIDATChunkInputStream;

import model.data_structures.Arco;
import model.data_structures.ArregloDinamico;
import model.data_structures.LinkedList;

public class esquemaJSON<K> {
	
	private K id;
	private double lat;
	private double lon;
	private  K[] adj;
	private int nInfracciones;
	
	
	
	public esquemaJSON(K pId, K[]pAdj, double pLat, double pLon, int pNInfracciones){
		id = pId;
		lat = pLat;
		lon = pLon;
		nInfracciones = pNInfracciones;
		adj = pAdj;
	}
	
	
	
}
