package com.afirme.afirmenet.empresas.service.impl.acceso;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.acceso.ContratoDao;
import com.afirme.afirmenet.dao.acceso.PasswordDao;
import com.afirme.afirmenet.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.service.acceso.PasswordService;
import com.afirme.afirmenet.service.impl.acceso.MailService;

/**
 * clase para implementar la contraseña
 *  * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
@Service
public class PasswordServiceImpl implements PasswordService {

	
	@Autowired
	private PasswordDao passwordDao;
	
	@Autowired
	private ContratoDao contratoDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	MailService mailService;
	
	@Override
	public Map<String, String> updatePassword(String idContrato, String password, String nuevoPassword) {
		return null;
	}

	@Override
	public boolean setPassword(String idContrato, String password) {

		return false;
	}

	@Override
	public void mailNotificacionCambioPwd(String idContrato) {
		
	}

	@Override
	public Map<String, String> solicitudCambioPwd(String idContrato, String nuevoPassword) {
		return null;
	}

	@Override
	public String validaPassword(String idContrato, String codigoToken) {
		return null;
	}
	
	@Override
	public boolean enviaMailCodigoSeguridad(String contrato) {
		
		
		return false;
	}
	
	@Override
	private boolean enviarCodigoSeguridad(String contrato, String mail, String codigoSeguridad) {

		
			return false;
		}
	
}
