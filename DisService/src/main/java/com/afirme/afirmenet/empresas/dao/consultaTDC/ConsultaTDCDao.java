package com.afirme.afirmenet.empresas.dao.consultaTDC;

import java.util.List;

import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientosTDC;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noé
 * @author Noe
 * @version 1.0.0
 */
public interface ConsultaTDCDao {

	/**
	 * @param consultaSaldos
	 */
	public String consultaTDC(List<ConsulatasSaldosMovimientosTDC> consultaSaldos);

	/**
	 * @param consultaDespuesCorte
	 */
	public String consultaDespuesCorte(List<ConsulatasSaldosMovimientosTDC> consultaDespuesCorte);

	/**
	 * @param consultaMovimientoCorte
	 */
	public String consultaMovimientoCorte(List<ConsulatasSaldosMovimientosTDC> consultaMovimientoCorte);

	/**
	 * @param consultaMovimientoCorteAtras
	 */
	public String consultaMovimientoCorteAtras(List<ConsulatasSaldosMovimientosTDC> consultaMovimientoCorteAtras);

	/**
	 * @param consultaPromocionesPlazos
	 */
	public String consultaPromocionesPlazos(List<ConsulatasSaldosMovimientosTDC> consultaPromocionesPlazos);

}
