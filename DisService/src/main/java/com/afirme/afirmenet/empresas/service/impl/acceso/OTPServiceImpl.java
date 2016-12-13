package com.afirme.afirmenet.empresas.service.impl.acceso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.acceso.ContratoDao;
import com.afirme.afirmenet.empresas.dao.acceso.OTPDao;
import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.dao.impl.acceso.OTPDaoImpl;
import com.afirme.afirmenet.empresas.service.acceso.OTPService;
import com.afirme.afirmenet.service.impl.acceso.MailService;
import com.afirme.afirmenet.utils.AfirmeNetLog;

/**
 * clase para validar la contraseña
 *  * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
@Service
public class OTPServiceImpl implements OTPService {

	static final AfirmeNetLog LOG = new AfirmeNetLog(OTPDaoImpl.class);
	
	@Autowired
	private OTPDao otpDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ContratoDao contratoDao;
	@Autowired
	MailService mailService;
	
	/**
	 * metodo para validar el token de cesion activa
	 * @param usuario
	 * @param serialToken
	 * @param codigoActivacion
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion) {
	
		return false;
	}
	

	/**
	 * metodo para validar el token de cesion activa
	 * @param usuario
	 * @param serialToken
	 * @param codigoActivacion
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public String obtenToken(String contrato) {
		return null;
	}

	/**
	 * metodo que regresa la existencia del contacto
	 * @param contrato
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public boolean usaTokens(String contrato) {
		return false;
	}

	/**
	 * metodo para validar el codigo de seguridad
	 * @param contrato
	 * @param usuario
	 * @param codigoSegEnc
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEnc) {
		return false;
	}

	/**
	 * metodo para enviar correo de seguridad
	 * @param contrato
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public boolean enviaMailCodigoSeguridad(String contrato) {
		
		return false;
	}

	/**
	 * metodo para validar el vencimiento del token
	 * @param serialToken
	 * @param fechaVencimiento
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento) {
		return false;
	}
	
	/**
	 * metodo para enviar el codigo de seguridad
	 * @param contrato
	 * @param mail
	 * @param codigoSeguridad
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	private boolean enviarCodigoSeguridad(String contrato, String mail, String codigoSeguridad) {

		return false;
		
	}
}
