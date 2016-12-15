package com.afirme.afirmenet.empresas.dao.consultasaldosmovimiento;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noé
 * @author Noe
 * @version 1.0.0
 */

public interface ConsultaSaldosMoviemntoCuentasDao {
	
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
	


}
