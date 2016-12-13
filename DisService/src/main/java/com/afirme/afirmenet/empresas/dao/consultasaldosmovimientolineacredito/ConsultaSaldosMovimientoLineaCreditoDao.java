package com.afirme.afirmenet.empresas.dao.consultasaldosmovimientolineacredito;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;

public interface ConsultaSaldosMovimientoLineaCreditoDao {
	
	/**
	 * @param cuentasLC
	 */
	public void cuentasLC(List<ConsultaSaldosMovimientosLC> cuentasLC);
	
	/**
	 * @param prestamo
	 */
	public void informacionPrestamo(List<ConsultaSaldosMovimientosLC> prestamo);
	
	

}
