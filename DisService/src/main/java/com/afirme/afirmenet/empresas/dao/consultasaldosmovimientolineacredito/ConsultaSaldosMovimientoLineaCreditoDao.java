package com.afirme.afirmenet.empresas.dao.consultasaldosmovimientolineacredito;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noé
 * @author Noe
 * @version 1.0.0
 */
public interface ConsultaSaldosMovimientoLineaCreditoDao {
	
	/**
	 * @param cuentasLC
	 */
	public String cuentasLC(List<ConsultaSaldosMovimientosLC> cuentasLC);
	
	/**
	 * @param prestamo
	 */
	public String informacionPrestamo(List<ConsultaSaldosMovimientosLC> prestamo);
	
	

}
