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
	
	

	public Map<String, String> updatePassword(String idContrato, String password, String nuevoPassword) {
		
		
		return null;
	}

	

	public boolean setPassword(String idContrato, String password) {
		
		return false;
	}


	public void mailNotificacionCambioPwd(String idContrato) {
		
	}

	

	public Map<String, String> solicitudCambioPwd(String idContrato, String nuevoPassword) {
		
		
		return null;
	}

	

	public String validaPassword(String idContrato, String codigoToken) {
		
		return null;
	}
}
