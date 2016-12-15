package com.afirme.afirmenet.empresas.dao.consultaTDC;

import java.util.List;

import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientos;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noé
 * @author Noe
 * @version 1.0.0
 */
public interface ConsultaTDCDao {

	/**
	 * @param consultaSaldos
	 */
	public String consultaTDC(List<ConsulatasSaldosMovimientos> consultaSaldos);

	/**
	 * @param consultaDespuesCorte
	 */
	public String consultaDespuesCorte(List<ConsulatasSaldosMovimientos> consultaDespuesCorte);

	/**
	 * @param consultaMovimientoCorte
	 */
	public String consultaMovimientoCorte(List<ConsulatasSaldosMovimientos> consultaMovimientoCorte);

	/**
	 * @param consultaMovimientoCorteAtras
	 */
	public String consultaMovimientoCorteAtras(List<ConsulatasSaldosMovimientos> consultaMovimientoCorteAtras);

	/**
	 * @param consultaPromocionesPlazos
	 */
	public String consultaPromocionesPlazos(List<ConsulatasSaldosMovimientos> consultaPromocionesPlazos);

}
