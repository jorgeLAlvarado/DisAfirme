package com.afirme.afirmenet.service.consultas;

import java.util.List;

import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;

/**
 * Consulta de historial
 * 
 * @author Jorge Alvarado
 *
 */
public interface HistorialService {
	/**
	 * Obtener lista de transacciones
	 * @param esBasicoSinToken
	 * @return
	 */
	List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken);
	/**
	 * Obtener la categoria de la transaccion
	 * @param esBasicoSinToken
	 * @return
	 */
	List<String> categorias(boolean esBasicoSinToken);
	/**
	 * Obtener la informacion faltante para el comprobante
	 * @param comprobante
	 */
	void obtenerInformacionExtra(TransferenciaBase comprobante);
}
