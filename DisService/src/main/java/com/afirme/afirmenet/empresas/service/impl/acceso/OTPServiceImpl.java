package com.afirme.afirmenet.empresas.service.impl.acceso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.acceso.ContratoDao;
import com.afirme.afirmenet.empresas.dao.acceso.OTPDao;
import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.dao.impl.acceso.OTPDaoImpl;
import com.afirme.afirmenet.empresas.service.acceso.OTPService;
import com.afirme.afirmenet.empresas.service.impl.acceso.MailService;
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
	
	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion) {
	
		return false;
	}
	
	public String obtenToken(String contrato) {
		return null;
	}

	public boolean usaTokens(String contrato) {
		return false;
	}

	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEnc) {
		return false;
	}

	public boolean enviaMailCodigoSeguridad(String contrato) {
		
		return false;
	}

	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento) {
		return false;
	}
	
	private boolean enviarCodigoSeguridad(String contrato, String mail, String codigoSeguridad) {

		return false;
		
	}
}
