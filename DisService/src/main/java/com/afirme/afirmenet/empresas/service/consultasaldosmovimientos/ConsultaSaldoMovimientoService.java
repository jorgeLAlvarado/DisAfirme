package com.afirme.afirmenet.empresas.service.consultasaldosmovimientos;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noe
 * @author Noe Galarza
 * @version 1.0.0
 */


public interface ConsultaSaldoMovimientoService {
	
	/**
	 * @param consultaInversion
	 */
	
	
	public String consultaInversion(List<ConsultaSaldosMovimientosInversiones> consultaInversion);
	
	/**
	 * @param detalleProducto
	 */
	public String detalleProducto(List<ConsultaSaldosMovimientosInversiones> detalleProducto);
	
	/**
	 * @param cuentas
	 */
	public String cuentas(List<ConsultaSaldosMovimientos> cuentas);
	
	/**
	 * @param consultaSaldos
	 */
	public List<ConsultaSaldosMovimientos> saldosMovimientos(ConsultaSaldosMovimientos consultaSaldos);
	
	/**
	 * @param ultimosMovimientos
	 */
	public String ultimosMovimientos(List<ConsultaSaldosMovimientos> ultimosMovimientos);
	
	/**
	 * @param movimientoMes
	 */
	public String movimientosMes(List<ConsultaSaldosMovimientos> movimientoMes);
	
	/**
	 * @param retenidos
	 */
	public String retenidos(List<ConsultaSaldosMovimientos> retenidos);
	
	/**
	 * @param buscarHistorico
	 */
	public String buscarHistorico(List<ConsultaSaldosMovimientos> buscarHistorico);
	
	
	/**
	 * @param cuentasLC
	 */
	public String cuentasLC(List<ConsultaSaldosMovimientosLC> cuentasLC);
	
	/**
	 * @param prestamo
	 */
	public String informacionPrestamo(List<ConsultaSaldosMovimientosLC> prestamo);

}
