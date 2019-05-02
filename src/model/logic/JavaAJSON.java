package model.logic;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import model.data_structures.GrafoNDPeso;


public class JavaAJSON {
	
	
	public JavaAJSON(){
		
		
		Gson gson = new Gson();
		infoArco<Integer> nuevo = new infoArco<Integer>(1, 2, 0, 1);
		infoArco<Integer> nuevo2 = new infoArco<Integer>(2, 3, 0, 2);
		
		infoArco<Integer>[] s = (infoArco<Integer>[]) new Object[2];
		s[0] = nuevo;
		s[1] = nuevo2;
		
		
		String ss = gson.toJson(s);
		System.out.println(ss);
		
		
	}
	
	
	
	
	

}
