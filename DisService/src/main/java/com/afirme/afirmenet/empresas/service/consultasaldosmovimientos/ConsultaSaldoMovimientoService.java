package com.afirme.afirmenet.empresas.service.consultasaldosmovimientos;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;

public interface ConsultaSaldoMovimientoService {
	
	/**
	 * @param consultaInversion
	 */
	public void consultaInversion(List<ConsultaSaldosMovimientosInversiones> consultaInversion);
	
	/**
	 * @param detalleProducto
	 */
	public void detalleProducto(List<ConsultaSaldosMovimientosInversiones> detalleProducto);
	
	/**
	 * @param cuentas
	 */
	public void cuentas(List<ConsultaSaldosMovimientos> cuentas);
	
	/**
	 * @param consultaSaldos
	 */
	public void consultaSaldosMovimientosCuentas(List<ConsultaSaldosMovimientos> consultaSaldos);
	
	/**
	 * @param ultimosMovimientos
	 */
	public void ultimosMovimientos(List<ConsultaSaldosMovimientos> ultimosMovimientos);
	
	/**
	 * @param movimientoMes
	 */
	public void movimientosMes(List<ConsultaSaldosMovimientos> movimientoMes);
	
	/**
	 * @param retenidos
	 */
	public void retenidos(List<ConsultaSaldosMovimientos> retenidos);
	
	/**
	 * @param buscarHistorico
	 */
	public void buscarHistorico(List<ConsultaSaldosMovimientos> buscarHistorico);
	
	
	/**
	 * @param cuentasLC
	 */
	public void cuentasLC(List<ConsultaSaldosMovimientosLC> cuentasLC);
	
	/**
	 * @param prestamo
	 */
	public void informacionPrestamo(List<ConsultaSaldosMovimientosLC> prestamo);

}
