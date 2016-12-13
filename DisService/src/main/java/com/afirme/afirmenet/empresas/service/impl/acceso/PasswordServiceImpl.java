package com.afirme.afirmenet.empresas.service.impl.acceso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.acceso.ContratoDao;
import com.afirme.afirmenet.dao.acceso.PasswordDao;
import com.afirme.afirmenet.dao.acceso.UserDao;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.ibs.objects.JOEncrypt;
import com.afirme.afirmenet.service.acceso.PasswordService;
import com.afirme.afirmenet.service.mail.MailService;
import com.afirme.afirmenet.utils.mail.AfirmeNetMail;

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
		return passwordDao.updatePassword(idContrato, password, nuevoPassword);
	}

	@Override
	public boolean setPassword(String idContrato, String password) {

		return null;
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
		
		
		return null;
	}
	
	private boolean enviarCodigoSeguridad(String contrato, String mail, String codigoSeguridad) {

		
			return null;
		}
	}

}
