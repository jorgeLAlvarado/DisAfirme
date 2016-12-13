package com.afirme.afirmenet.empresas.dao.impl.acceso;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.empresas.dao.acceso.PasswordDao;
import com.afirme.afirmenet.utils.AfirmeNetLog;

/**
 * clase para validar la contraseña
 *  * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
@Repository
public class PasswordDaoImpl implements PasswordDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(PasswordDaoImpl.class);
	
	
	/**
	 * metodo para modificar contraseña
	 * @param idContrato
	 * @param password
	 * @param nuevoPassword
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public Map<String, String> updatePassword(String idContrato, String password, String nuevoPassword) {
		
		
		return null;
	}

	
	/**
	 * metodo para validar manda la nueva contraseña
	 * @param nuevoPassword
	 * @param password
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public boolean setPassword(String idContrato, String password) {
		
		return false;
	}

	
	/**
	 * metodo para notificar el cambio de contraseña
	 * @param idContrato
	 * 
	 */
	@Override
	public void mailNotificacionCambioPwd(String idContrato) {
		
	}

	
	/**
	 * metodo para realisar el cambio de contraseña
	 * @param idContrato
	 * @param nuevoPassword
	 * @return regresa la peticion para el cambio de contraseña
	 * 
	 */
	@Override
	public Map<String, String> solicitudCambioPwd(String idContrato, String nuevoPassword) {
		
		
		return null;
	}

	
	/**
	 * metodo para valida la nuea contraseña
	 * @param idContrato
	 * @param codigoToken
	 * @return regresa la validacion del usuario autentificado.
	 * 
	 */
	@Override
	public String validaPassword(String idContrato, String codigoToken) {
		
		return null;
	}
}
