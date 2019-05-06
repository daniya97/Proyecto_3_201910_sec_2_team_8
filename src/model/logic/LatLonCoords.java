package model.logic;

import java.util.Comparator;

public class LatLonCoords {

	private double lon;
	private double lat;
	public final static double EARTHS_MEAN_R = 6371.0088; //KM
	
	public LatLonCoords(double plat, double plon) {
		lon = plon;
		lat = plat;
	}
	
	public double getLat() {return lat;}
	
	public double getLon() {return lon;}
	
	public double haversineD(LatLonCoords coords2) {
		double lat1rad = lat * Math.PI / 180.;
		double lon1rad = lon * Math.PI / 180.;
		double lat2rad = coords2.getLat()  * Math.PI / 180.;
		double lon2rad = coords2.getLon()  * Math.PI / 180.;
		return 2*EARTHS_MEAN_R * Math.asin( Math.sqrt( Math.pow(Math.sin((lat2rad - lat1rad)/2 ), 2) + Math.cos(lat1rad)*Math.cos(lat2rad)* Math.pow(Math.sin((lon2rad - lon1rad)/2), 2) ) );
	}
	
}
