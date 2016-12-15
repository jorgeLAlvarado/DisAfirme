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
	public void consultaTDC(List<ConsulatasSaldosMovimientos> consultaSaldos);

	/**
	 * @param consultaDespuesCorte
	 */
	public void consultaDespuesCorte(List<ConsulatasSaldosMovimientos> consultaDespuesCorte);

	/**
	 * @param consultaMovimientoCorte
	 */
	public void consultaMovimientoCorte(List<ConsulatasSaldosMovimientos> consultaMovimientoCorte);

	/**
	 * @param consultaMovimientoCorteAtras
	 */
	public void consultaMovimientoCorteAtras(List<ConsulatasSaldosMovimientos> consultaMovimientoCorteAtras);

	/**
	 * @param consultaPromocionesPlazos
	 */
	public void consultaPromocionesPlazos(List<ConsulatasSaldosMovimientos> consultaPromocionesPlazos);

}
