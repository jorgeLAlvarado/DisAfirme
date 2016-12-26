package com.afirme.afirmenet.service.consultas;

import java.util.List;

import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
/**
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * Created on Created on Dic 10, 2016 3:50:05 PM by Jorge
 */
public interface HistorialService {
	/**
	 * @param esBasicoSinToken
	 * @return
	 */
	List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken);
	/**
	 * @param esBasicoSinToken
	 * @return
	 */
	List<String> categorias(boolean esBasicoSinToken);
	/**
	 * @param comprobante
	 */
	void obtenerInformacionExtra(TransferenciaBase comprobante);
}
