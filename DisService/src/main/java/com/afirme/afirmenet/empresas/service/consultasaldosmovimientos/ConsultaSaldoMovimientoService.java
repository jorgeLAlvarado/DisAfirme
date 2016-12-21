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
	
	
	
	public List<ConsultaSaldosMovimientosInversiones> consultaInversion(ConsultaSaldosMovimientosInversiones consultaInversion);
	/**
	 * @param detalleProducto
	 */
	
	public List<ConsultaSaldosMovimientosInversiones> detalleProducto (ConsultaSaldosMovimientosInversiones detalleProducto);
	/**
	 * @param cuentas
	 */
	
	public List<ConsultaSaldosMovimientos> cuentas (ConsultaSaldosMovimientos cuentas);
	/**
	 * @param consultaSaldos
	 */
	
	public List<ConsultaSaldosMovimientos> consultaSaldos(ConsultaSaldosMovimientos consultaSaldos);
	
	/**
	 * @param ultimosMovimientos
	 */
	
	public  List<ConsultaSaldosMovimientos> ultimosMovimientos (ConsultaSaldosMovimientos ultimosMovimientos);
	
	
	/**
	 * @param movimientoMes
	 */
	
	public List<ConsultaSaldosMovimientos> movimientosMes (ConsultaSaldosMovimientos moviminetoMes);
	
	/**
	 * @param retenidos
	 */
	public List<ConsultaSaldosMovimientos> retenidos (ConsultaSaldosMovimientos retenidos);
	
	/**
	 * @param buscarHistorico
	 */
	
	public List<ConsultaSaldosMovimientos> buscarHistorico (ConsultaSaldosMovimientos buscarHistorico);
	
	
	/**
	 * @param cuentasLC
	 */
	
	public List<ConsultaSaldosMovimientosLC> cuentasLC (ConsultaSaldosMovimientosLC cuentasLC);
	
	/**
	 * @param prestamo
	 */

	public List<ConsultaSaldosMovimientosLC> informacionPrestamo (ConsultaSaldosMovimientosLC prestamo);

}
