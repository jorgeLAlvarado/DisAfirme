package com.afirme.afirmenet.empresas.dao.acceso;

import java.util.Map;

/**
 * clase para pasword
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 * Created on Created on Dic 26, 2016 3:50:05 PM by Bayron
 */
public interface PasswordDao {
	
	/**
	 * @param idContrato
	 * @param password
	 * @param nuevoPassword
	 * @return
	 */
	public Map<String, String> updatePassword(String idContrato, String password, String nuevoPassword);

	/**
	 * @param idContrato
	 * @param password
	 * @return
	 */
	public boolean setPassword(String idContrato, String password);

	/**
	 * @param idContrato
	 */
	public void mailNotificacionCambioPwd(String idContrato);

	/**
	 * @param idContrato
	 * @param nuevoPassword
	 * @return
	 */
	public Map<String, String> solicitudCambioPwd(String idContrato, String nuevoPassword);

	/**
	 * @param idContrato
	 * @param codigoToken
	 * @return
	 */
	public String validaPassword(String idContrato, String codigoToken);
}
