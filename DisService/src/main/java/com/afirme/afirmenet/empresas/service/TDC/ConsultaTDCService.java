package com.afirme.afirmenet.empresas.service.TDC;

import java.util.List;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;
import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientosTDC;

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
	
	
	public List<ConsulatasSaldosMovimientosTDC> consultaTDC (ConsulatasSaldosMovimientosTDC consultaSaldos);

	/**
	 * @param consultaDespuesCorte
	 */
	
	
	public List<ConsulatasSaldosMovimientosTDC> consultaDespuesCorte (ConsulatasSaldosMovimientosTDC consultaDespuesCorte);

	/**
	 * @param consultaMovimientoCorte
	 */
	
	
	public List<ConsulatasSaldosMovimientosTDC> consultaMovimientoCorte (ConsulatasSaldosMovimientosTDC consultaMovimientoCorte);

	/**
	 * @param consultaMovimientoCorteAtras
	 */
	
	
	public List<ConsulatasSaldosMovimientosTDC> consultaMovimientoCorteAtras (ConsulatasSaldosMovimientosTDC consultaMovimientoCorteAtras);

	/**
	 * @param consultaPromocionesPlazos
	 */
	
	public List<ConsulatasSaldosMovimientosTDC> consultaPromocionesPlazos (ConsulatasSaldosMovimientosTDC consultaPromocionesPlazos);


}
