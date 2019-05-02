package model.vo;

public class Coordenadas implements Comparable<Coordenadas> {

	
	private int xCoord;
	private int yCoord;
	
	
	public Coordenadas(double x, double y) {
		xCoord = (int) (x*100);
		yCoord = (int) (y*100);
	}
	
	
	
	@Override
	public int compareTo(Coordenadas o) {
		if(this.xCoord>o.xCoord) return 1;
		else if (this.xCoord<o.xCoord) return -1;
		else if(this.yCoord>o.yCoord) return 1;
		else if(this.yCoord<o.yCoord) return -1;
		else return 0;
	}
	
	@Override
	public boolean equals(Object o) {   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Coordenadas)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Coordenadas c = (Coordenadas) o; 
		return this.xCoord == c.xCoord && this.yCoord == c.yCoord;
	}
	
	public int hashCode() {
		return xCoord*37 + yCoord;
	}

	
	public double darX(){
		return xCoord/100.;
	}
	
	public double darY(){
		return yCoord/100.;
	}
	
	public String toString() {
		return "(" + darX() + ", " + darY() + ") ";
	}
}
