package model.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JReader implements Iterable<String> {
		// Clase hecha simulando el comportamiento de los Readers
		private BufferedReader bufReader;
		private int lastReadChar;
		
		public JReader(File file) throws IOException {
			bufReader = new BufferedReader(new FileReader(file));
			while (bufReader.read() == '[');
			lastReadChar = bufReader.read();
		}
		
		public String readJson() {
			// Assume last read char was a ',' o ']'
			if (lastReadChar == ']') {
				//System.out.println("Nunca deberia llegar a aca si usa hasNext()");
				return null;
			}
			// Si fue una coma, busco el siguiente '{'
			while (read()!= '{');
			
			StringBuilder jsonText = new StringBuilder();
			jsonText.append((char) '{');
			lastReadChar = read();
			
			// Busca el siguiente '}'
			while (lastReadChar != '}') { 
				jsonText.append((char) lastReadChar);
				lastReadChar = read();
			} jsonText.append('}');
			
			// To satisfy Invariant: find the next ']' or ','
			while (lastReadChar != ']' && lastReadChar != ',') lastReadChar = read(); 
			
			//System.out.println("Analizando : " + jsonText.toString());
			return jsonText.toString();
		}
		
		private int read() { // TODO mejorar
			try {
				return bufReader.read();
			} catch (IOException e) {
				e.printStackTrace();
				lastReadChar = ']';
				return ']';
			}
		}
		
		
		public void close() throws IOException {
			bufReader.close();
		}

		@Override
		public Iterator<String> iterator() {
			return new Iterator<String>() {
				public boolean hasNext() {
					//System.out.println("Entro a hasNext leyendo el caracter '" + (char)(lastReadChar) + "'");
					return lastReadChar != ']' && lastReadChar != -1;
				}
				public String next() {return readJson();}
			};
		}
	}