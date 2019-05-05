package model.vo;

import model.data_structures.IArregloDinamico;

/**
 * Clase que representa los datos de reporte de la lectura de las infracciones 
 * 
 */
public class EstadisticasCargaInfracciones {
	
	// total de infracciones cargadas
	private int totalInfracciones;
	
	// numero de meses cargados
	private int numeroMeses;
	
	// numero de infraccioens cargadas x cada mes
	private int [] numeroInfraccionesxMes;
	
	// minX es minimax[0], minY es minimax[1], maxX es minimax[2], maxY es minimax[3]
	private double [] minimax;
	
	/**
	 * Constructor con el resumen de la carga de la lectura de las infracciones
	 // TODO definir los parametros necesarios para inicializar los atributos de la clase
	 * @param parametro1 con valor para un dato de la carga de archivos
	 * @param parametro2 con valor para un dato de la carga de archivos
	 * 
	 */
	public EstadisticasCargaInfracciones(int totalInfracciones, int numeroMeses, int[] numeroInfraccionesxMes, double[] minimax)
	{
		this.totalInfracciones = totalInfracciones;
		this.numeroMeses = numeroMeses;
		this.numeroInfraccionesxMes = numeroInfraccionesxMes;
		this.minimax = minimax;
	}
	
	/**
	 * Retorna el numero de meses que fueron cargados en la lectura de archivos
	 * @return numero de meses que fueron cargados en la lectura de archivos
	 */
	public int darNumeroDeMesesCargados()
	{
		return numeroMeses;
	}
	
	/**
	 * Retorna el numero total de infracciones cargadas 
	 */
	public int darTotalInfracciones()
	{
		return totalInfracciones;
	}
	
	/** 
	 * Retorna el numero de infracciones cargadas por cada mes de lectura de archivos
	 * @return arreglo con el numero de infracciones cargadas por cada mes de lectura de archivos
	 */
	public int [] darNumeroDeInfraccionesXMes()
	{
		return numeroInfraccionesxMes;
	}
	
	/**
	 * Retorna el area Minimax de las infracciones cargadas en la lectura de archivos
	 * @return arreglo con 4 valores double: minX en posicion 0, minY en posicion 1, maxX en posicion 2 y maxY en posicion 3
	 */
	
	public double [] darMinimax()
	{
		return minimax;
	}

	
}
