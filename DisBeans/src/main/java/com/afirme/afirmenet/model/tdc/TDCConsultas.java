package com.afirme.afirmenet.model.tdc;

import java.util.List;

import com.afirme.afirmenet.model.base.Paginacion;
import com.afirme.afirmenet.model.pagos.servicios.Servicio;

public class TDCConsultas extends Servicio{  
	    
	List<?> lstMovimientos;
	private int numCargos;
	private String cargos = "";
	private int numAbonos;
	private String abonos = "";
	private String tipoCorte=""; //0002:despues del corte 0003:al corte 0004: un corte atras 0005: promociones
    private Paginacion paginacion;

	/**
	 * @return el numCargos
	 */
	public int getNumCargos() {
		return numCargos;
	}

	/**
	 * @param numCargos el numCargos a establecer
	 */
	public void setNumCargos(int numCargos) {
		this.numCargos = numCargos;
	}

	/**
	 * @return el cargos
	 */
	public String getCargos() {
		return cargos;
	}

	/**
	 * @param cargos el cargos a establecer
	 */
	public void setCargos(String cargos) {
		this.cargos = cargos;
	}

	/**
	 * @return el numAbonos
	 */
	public int getNumAbonos() {
		return numAbonos;
	}

	/**
	 * @param numAbonos el numAbonos a establecer
	 */
	public void setNumAbonos(int numAbonos) {
		this.numAbonos = numAbonos;
	}

	/**
	 * @return el abonos
	 */
	public String getAbonos() {
		return abonos;
	}

	/**
	 * @param abonos el abonos a establecer
	 */
	public void setAbonos(String abonos) {
		this.abonos = abonos;
	}

	/**
	 * @return el tipoCorte
	 */
	public String getTipoCorte() {
		return tipoCorte;
	}

	/**
	 * @param tipoCorte el tipoCorte a establecer
	 */
	public void setTipoCorte(String tipoCorte) {
		this.tipoCorte = tipoCorte;
	}

	/**
	 * @return el paginacion
	 */
	public Paginacion getPaginacion() {
		return paginacion;
	}

	/**
	 * @param paginacion el paginacion a establecer
	 */
	public void setPaginacion(Paginacion paginacion) {
		this.paginacion = paginacion;
	}

	/**
	 * @return el lstMovimientos
	 */
	public List<?> getLstMovimientos() {
		return lstMovimientos;
	}

	/**
	 * @param lstMovimientos el lstMovimientos a establecer
	 */
	public void setLstMovimientos(List<?> lstMovimientos) {
		this.lstMovimientos = lstMovimientos;
	}


	
}
