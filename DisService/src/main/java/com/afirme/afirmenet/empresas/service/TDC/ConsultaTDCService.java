package com.afirme.afirmenet.empresas.service.TDC;

import java.util.List;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientos;

/**
 * @author Noe
 *
 */
@Service
public interface ConsultaTDCService {
	
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
