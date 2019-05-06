package model.data_structures;



import junit.framework.TestCase;

public class UnionFindTest extends TestCase {
	
	
	private UnionFind uf;
	
	private void setUpEscenario0() {
		
		uf = new UnionFind(10);
	}
	
	
	public void testDemo(){
		
		setUpEscenario0();
		assertTrue("Error en la estructura de UF", uf.num() == 10);
		
		for (int i = 0; i < 10; i++) {
			assertTrue("No hay elementos conectados", uf.connected(i, 9-i) == false);
		}
		
		// Demo Libro
		uf.union(4, 3);
		uf.union(3, 8);
		uf.union(6, 5);
		uf.union(9, 4);
		uf.union(2, 1);
		uf.union(4, 3);
		
		
		assertTrue("Los elementos están conectados", uf.connected(8, 9));
		assertTrue("Los elementos no están conectados", !uf.connected(5, 4));
		assertTrue("Hay 5 componenetes", uf.num() == 5);
		
		uf.union(5, 0);
		uf.union(7, 2);
		uf.union(6, 1);
		uf.union(7, 3);
		
		//Se verifica el resultado final
		assertTrue("Hay 1 componente", uf.num() == 1);

		// Como es Quick-Find, todoss tienen el mismo ID
		for (int i = 0; i < 10; i++) {
			assertTrue("Problemas con el ID", uf.find(i)==6);
		}
	
	}
	
	
	
	
	
	

}
