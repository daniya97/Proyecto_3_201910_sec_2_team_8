package model.data_structures;

import com.sun.xml.internal.bind.v2.runtime.Location;

import junit.framework.TestCase;
import model.vo.LocationVO;

public class CPTest extends TestCase{

	// Max CP
	private MaxColaPrioridad<LocationVO> cp1;
	
	// Max HEAP
	private MaxHeapCP<LocationVO> cp2;
	
	//Min Cp
	private MinCP<LocationVO> cp3;

	//MinIndexedCP
	private IndexMinPQ<LocationVO> cp4;

	
	//Colas Vacias
	private void setUpEscenario0() {
		cp1 = new MaxColaPrioridad<LocationVO>();
		cp2 = new MaxHeapCP<LocationVO>();
		cp3 = new MinCP<LocationVO>();
		cp4 = new IndexMinPQ<>(100);

	}

	private void setUpEscenario1() {
		cp1 = new MaxColaPrioridad<LocationVO>();
		cp2 = new MaxHeapCP<LocationVO>();
		cp3 = new MinCP<LocationVO>();
		cp4 = new IndexMinPQ<>(100);

		LocationVO auxiliar;

		for (int i = 0; i < 100; i++) {
			auxiliar = new LocationVO(i*i, "NA", i+10);
			cp1.agregar(auxiliar);
			cp2.agregar(auxiliar);
			cp3.agregar(auxiliar);
		}


	}


	public void testColaVacia(){
		setUpEscenario0();

		//Las colas de prioridad deberï¿½an estar vacï¿½as
		assertEquals("No hay elementos deberï¿½a retornar true",true, cp1.esVacia());
		assertEquals("No hay elementos deberï¿½a retornar true",true, cp2.esVacia());
		assertEquals("No hay elementos deberï¿½a retornar true",true, cp3.esVacia());
		assertEquals("No hay elementos deberï¿½a retornar true",true, cp4.esVacia());


		//Eliminar el mï¿½ximo
		assertEquals("No hay elementos deberï¿½a retornar null",null, cp1.delMax());
		assertEquals("No hay elementos deberï¿½a retornar null",null, cp2.delMax());
		assertEquals("No hay elementos deberï¿½a retornar null",null, cp3.delMin());
		assertEquals("No hay elementos deberï¿½a retornar -1",-1, cp4.delMin());

		
		
		//Nï¿½mero de elementos deberï¿½a ser 0
		assertEquals("No hay elementos deberï¿½a retornar 0",0, cp1.darNumElementos());
		assertEquals("No hay elementos deberï¿½a retornar 0",0, cp2.darNumElementos());
		assertEquals("No hay elementos deberï¿½a retornar 0",0, cp3.darNumElementos());
		assertEquals("No hay elementos deberï¿½a retornar 0",0, cp4.darNumElementos());
	}

	public void testColaVaciaAgregoyEliminoelementos(){
		setUpEscenario0();

		//Agrego elementos a las colas
		LocationVO elemento1 = new LocationVO(815694, "100 BLK MICHIGAN AVE NW E/B", 10);
		LocationVO elemento2 = new LocationVO(814983,"3RD ST TUNNEL NW N/B BY MA AVE",20);
		LocationVO elemento3 = new LocationVO(805065,"2200 BLOCK K ST NW E/B",40);

		cp1.agregar(elemento1);
		cp1.agregar(elemento2);
		cp1.agregar(elemento3);

		cp2.agregar(elemento1);
		cp2.agregar(elemento2);
		cp2.agregar(elemento3);


		cp3.agregar(elemento1);
		cp3.agregar(elemento2);
		cp3.agregar(elemento3);
		
		
		cp4.agregar(40, elemento1);
		cp4.agregar(30, elemento2);
		cp4.agregar(100, elemento3);
		
		
		
		//Ahora se verifica que se elimine el mï¿½ximo correctamente		
		assertEquals("El elemento mï¿½ximo de MaxCP es el nï¿½mero 3",elemento3, cp1.max());
		assertEquals("El elemento mï¿½ximo de HeapCP es el nï¿½mero 3",elemento3, cp2.max());
		assertEquals("El elemento minimo de MinCP es el nï¿½mero 1",elemento1, cp3.min());
		assertEquals("El elemento minimo de IndexMinPQ es el nï¿½mero 1",elemento1, cp4.minK());
		assertEquals("El índice minimo de IndexMinPQ es el nï¿½mero 30",40, cp4.minIndex());
		
		
		
		assertEquals("Deberï¿½a eliminarse de MaxCP el elemento 3",elemento3, cp1.delMax());
		assertEquals("Deberï¿½a eliminarse de HeapCP el elemento 3",elemento3, cp2.delMax());
		assertEquals("Deberï¿½a eliminarse de MinCP el elemento 1",elemento1, cp3.delMin());
		assertEquals("Deberï¿½a eliminarse de IndexMinPQ el elemento 1",40, cp4.delMin());
		
		
		assertEquals("Deberï¿½a eliminarse de MaxCP el elemento 2",elemento2, cp1.delMax());
		assertEquals("Deberï¿½a eliminarse de HepCP el elemento 2",elemento2, cp2.delMax());
		assertEquals("Deberï¿½a eliminarse de minCP el elemento 2",elemento2, cp3.delMin());
		assertEquals("Deberï¿½a eliminarse de IndexMinPQ el elemento 2",30, cp4.delMin());
		
		
		assertEquals("Deberï¿½a eliminarse de MaxCP el elemento 1",elemento1, cp1.delMax());
		assertEquals("Deberï¿½a eliminarse de HeapCP el elemento 1",elemento1, cp2.delMax());
		assertEquals("Deberï¿½a eliminarse de MinCP el elemento 3",elemento3, cp3.delMin());
		assertEquals("Deberï¿½a eliminarse  IndexMinPQ el elemento 3",100, cp4.delMin());

		
		//Métodos Adicionales IndexMin
		
		cp4.agregar(40, elemento1);
		cp4.agregar(30, elemento2);
		cp4.agregar(100, elemento3);
		
		cp4.decreaseK(0,elemento1);
		assertTrue("Se cambio K del elemento 1 ", cp4.darObjeto(0) == elemento1);
		
		cp4.increaseK(80, elemento1);
		assertTrue("Se cambio K del elemento 1 ", cp4.darObjeto(80) == elemento1);
		
		
		
	}
}