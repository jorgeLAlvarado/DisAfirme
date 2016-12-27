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
	 * Consulta para obtener las inversiones
	 * @param consultaInversion
	 */
	
	
	
	public List<ConsultaSaldosMovimientosInversiones> consultaInversion(ConsultaSaldosMovimientosInversiones consultaInversion);
	/**
	 * Consulta para ver los detalles de los productos
	 * @param detalleProducto
	 */
	
	public List<ConsultaSaldosMovimientosInversiones> detalleProducto (ConsultaSaldosMovimientosInversiones detalleProducto);
	/**
	 * Consulta las cuentas
	 * @param cuentas
	 */
	
	public List<ConsultaSaldosMovimientos> cuentas (ConsultaSaldosMovimientos cuentas);
	/**
	 * Consulta el saldo disponible
	 * @param consultaSaldos
	 */
	
	public List<ConsultaSaldosMovimientos> consultaSaldos(ConsultaSaldosMovimientos consultaSaldos);
	
	/**
	 * Consulta los ultimos movimientos del mes
	 * @param ultimosMovimientos
	 */
	
	public  List<ConsultaSaldosMovimientos> ultimosMovimientos (ConsultaSaldosMovimientos ultimosMovimientos);
	
	
	/**
	 * consulta los ultimos movimientos del mes
	 * @param movimientoMes
	 */
	
	public List<ConsultaSaldosMovimientos> movimientosMes (ConsultaSaldosMovimientos moviminetoMes);
	
	/**
	 * Consulta los movimientos retenidos
	 * @param retenidos
	 */
	public List<ConsultaSaldosMovimientos> retenidos (ConsultaSaldosMovimientos retenidos);
	
	/**
	 * Consulta los datos historicos
	 * @param buscarHistorico
	 */
	
	public List<ConsultaSaldosMovimientos> buscarHistorico (ConsultaSaldosMovimientos buscarHistorico);
	
	
	/**
	 * Consulta los movimientos del LC
	 * @param cuentasLC
	 */
	
	public List<ConsultaSaldosMovimientosLC> cuentasLC (ConsultaSaldosMovimientosLC cuentasLC);
	
	/**
	 * Consulta la informacion de prestamos
	 * @param prestamo
	 */

	public List<ConsultaSaldosMovimientosLC> informacionPrestamo (ConsultaSaldosMovimientosLC prestamo);

}
