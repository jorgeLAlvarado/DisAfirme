package com.afirme.afirmenet.empresas.service.impl.acceso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.acceso.ContratoDao;
import com.afirme.afirmenet.dao.acceso.OTPDao;
import com.afirme.afirmenet.dao.acceso.UserDao;
import com.afirme.afirmenet.dao.impl.acceso.OTPDaoImpl;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.ibs.objects.JOEncrypt;
import com.afirme.afirmenet.service.acceso.OTPService;
import com.afirme.afirmenet.service.mail.MailService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.mail.AfirmeNetMail;

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

	@Override
	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion) {
	
		return null;
	}
	
	@Override
	public String obtenToken(String contrato) {
		return null;
	}

	@Override
	public boolean usaTokens(String contrato) {
		return null;
	}

	@Override
	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEnc) {
		return null;
	}

	@Override
	public boolean enviaMailCodigoSeguridad(String contrato) {
		
		return null;
	}

	@Override
	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento) {
		return null;
	}
	
	private boolean enviarCodigoSeguridad(String contrato, String mail, String codigoSeguridad) {

		
			return null;
		}
	}
}
