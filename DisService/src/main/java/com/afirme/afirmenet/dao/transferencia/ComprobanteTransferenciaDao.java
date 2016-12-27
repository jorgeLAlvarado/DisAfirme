package com.afirme.afirmenet.dao.transferencia;

import java.util.List;

/**
 * @author Usuario
 *
 */
public interface ComprobanteTransferenciaDao {



	/**
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param numeroServicio
	 * @return
	 */
	public List<?> buscarComprobantesGenericos(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio);


	/**
	 * Reimpresion de Transferencias Internacionales (USD y Multimoneda)
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public List<?> buscarComprobantesSWIFT(String contrato, String tipo, String fechaDesde, String fechaHasta);

	
	
	/**
	 * @param ref
	 * @param contrato
	 * @param atrib
	 * @return
	 */
	public String buscarAtributoINOTR(String ref, String contrato, String atrib);
	
	
	
	
	
	
}
