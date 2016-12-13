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
	
	/**
	 * metodo para implementar el cambio de contraseña
	 * @param idContrato
	 * @param password
	 * @param nuevoPassword
	 * @return regresa la peticion del dao.
	 * 
	 */
	@Override
	public Map<String, String> updatePassword(String idContrato, String password, String nuevoPassword) {
		return null;
	}

	
	/**
	 * metodo para implementar el cambio de contraseña
	 * @param idContrato
	 * @param password
	 * @param nuevoPassword
	 * @return regresa la peticion del dao.
	 * 
	 */
	@Override
	public boolean setPassword(String idContrato, String password) {

		return false;
	}

	/**
	 * metodo para implementar el envio del correo
	 * @param idContrato
	 * 
	 */
	@Override
	public void mailNotificacionCambioPwd(String idContrato) {
		
	}

	/**
	 * metodo para implementar la nueva contraseña
	 * @param idContrato
	 * @param nuevoPassword
	 * @return regresa la peticion del dao.
	 * 
	 */
	@Override
	public Map<String, String> solicitudCambioPwd(String idContrato, String nuevoPassword) {
		return null;
	}

	/**
	 * metodo para implementar la validacion de la contraseña
	 * @param idContrato
	 * @param codigoToken
	 * @return regresa la peticion del dao.
	 * 
	 */
	@Override
	public String validaPassword(String idContrato, String codigoToken) {
		return null;
	}
	
	/**
	 * metodo para implementar el codigo de seguridad del correo
	 * @param contrato
	 * @return regresa la peticion del dao.
	 * 
	 */
	@Override
	public boolean enviaMailCodigoSeguridad(String contrato) {
		
		
		return false;
	}
	
	/**
	 * metodo para implementar el envio de codigo de seguridad al correo
	 * @param contrato
	 * @param mail
	 * @param codigoSeguridad
	 * @return regresa la peticion del dao.
	 * 
	 */
	@Override
	private boolean enviarCodigoSeguridad(String contrato, String mail, String codigoSeguridad) {

		
			return false;
		}
	
}
