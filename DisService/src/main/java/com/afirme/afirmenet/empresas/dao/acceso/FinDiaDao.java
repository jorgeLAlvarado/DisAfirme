package com.afirme.afirmenet.empresas.dao.acceso;

/**
 * clase para dar la validacion de fin de dia
 * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 * 
 * Created on Created on Dic 26, 2016 3:50:05 PM by Bayron
 */
public interface FinDiaDao {
	/**
	 * Toma valor de fin de d�a
	 * @return
	 */
	public String getFinDia();
	/**
	 * Valida conexion activa
	 * 
	 */
	public void valActivSocket();
}
