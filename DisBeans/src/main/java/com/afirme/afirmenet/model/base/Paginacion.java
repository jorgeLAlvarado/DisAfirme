package com.afirme.afirmenet.model.base;

import java.util.List;

public class Paginacion {
	
	//Numero de movimientos del reporte
	private int numMovimientos;
	
	//Lista de movimientos
	List<?> lstMovTotales;
	
	//Lista de movimientos por pagina
	List<?> lstMovPagina;
	
	//lista de las paginas a mostrar
	List<Integer> lstPaginas;
	
	//pagina actual a mostrar
	int paginaActual;
	
	//numero de paginas del reporte
	int paginas;
	
	//Numero de movimientos por pagina
	int movPagina;

	/**
	 * @return el numMovimientos
	 */
	public int getNumMovimientos() {
		return numMovimientos;
	}

	/**
	 * @param numMovimientos el numMovimientos a establecer
	 */
	public void setNumMovimientos(int numMovimientos) {
		this.numMovimientos = numMovimientos;
	}

	/**
	 * @return el lstMovTotales
	 */
	public List<?> getLstMovTotales() {
		return lstMovTotales;
	}

	/**
	 * @param lstMovTotales el lstMovTotales a establecer
	 */
	public void setLstMovTotales(List<?> lstMovTotales) {
		this.lstMovTotales = lstMovTotales;
	}

	/**
	 * @return el lstMovPagina
	 */
	public List<?> getLstMovPagina() {
		return lstMovPagina;
	}

	/**
	 * @param lstMovPagina el lstMovPagina a establecer
	 */
	public void setLstMovPagina(List<?> lstMovPagina) {
		this.lstMovPagina = lstMovPagina;
	}

	/**
	 * @return el lstPaginas
	 */
	public List<Integer> getLstPaginas() {
		return lstPaginas;
	}

	/**
	 * @param lstPaginas el lstPaginas a establecer
	 */
	public void setLstPaginas(List<Integer> lstPaginas) {
		this.lstPaginas = lstPaginas;
	}

	/**
	 * @return el paginaActual
	 */
	public int getPaginaActual() {
		return paginaActual;
	}

	/**
	 * @param paginaActual el paginaActual a establecer
	 */
	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	/**
	 * @return el paginas
	 */
	public int getPaginas() {
		return paginas;
	}

	/**
	 * @param paginas el paginas a establecer
	 */
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	/**
	 * @return el movPagina
	 */
	public int getMovPagina() {
		return movPagina;
	}

	/**
	 * @param movPagina el movPagina a establecer
	 */
	public void setMovPagina(int movPagina) {
		this.movPagina = movPagina;
	}



	

}
