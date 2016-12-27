package com.afirme.afirmenet.empresas.service.transferencia;

import com.afirme.afirmenet.model.transferencia.Comprobante;

/**
 * @author Usuario
 *
 */
public interface ComprobanteTransferenciaService {
	
	/**
	 * @param comprobante
	 */
	public void insertaConfirmacionOperacion(Comprobante comprobante);

}
