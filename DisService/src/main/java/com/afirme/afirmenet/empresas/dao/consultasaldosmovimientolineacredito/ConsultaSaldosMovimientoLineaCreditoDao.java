package com.afirme.afirmenet.empresas.dao.consultasaldosmovimientolineacredito;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;

public interface ConsultaSaldosMovimientoLineaCreditoDao {
	
	public void cuentasLC(List<ConsultaSaldosMovimientosLC> cuentasLC);
	
	public void informacionPrestamo(List<ConsultaSaldosMovimientosLC> prestamo);
	
	

}
