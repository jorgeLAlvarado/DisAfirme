package com.afirme.afirmenet.empresas.service;

/**
 * clase para dar la validacion de fin de dia
 * 
 * @author Bayron Gamboa Martinez
 *	@since 13/12/2016
 *
 * @version 1.0.2
 */
public interface FinDiaService {
	/**
	 * Toma valor de fin de día
	 * @return
	 */
	public String getFinDia();
	/**
	 * Valida conexion activa
	 */
	public void valActivSocket();
}
