package com.afirme.afirmenet.empresas.dao.acceso;

import java.util.Map;

/**
 * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
public interface PasswordDao {
	
	public Map<String, String> updatePassword(String idContrato, String password, String nuevoPassword);

	public boolean setPassword(String idContrato, String password);

	public void mailNotificacionCambioPwd(String idContrato);

	public Map<String, String> solicitudCambioPwd(String idContrato, String nuevoPassword);

	public String validaPassword(String idContrato, String codigoToken);
}
