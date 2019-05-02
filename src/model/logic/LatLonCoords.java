package model.logic;

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
		double lat2 = coords2.getLat();
		double lon2 = coords2.getLon();
		return 2*EARTHS_MEAN_R * Math.asin( Math.sqrt( Math.pow(Math.sin((lat2 - lat)/2), 2) + Math.cos(lat)*Math.cos(lat2)* Math.pow(Math.sin((lon2 - lon)/2), 2) ) );
	}
	
}
