package com.afirme.afirmenet.empresas.service.TDC;

import java.util.List;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientos;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noe
 * @author Noe Galarza
 * @version 1.0.0
 */
@Service
public interface ConsultaTDCService {
	
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
