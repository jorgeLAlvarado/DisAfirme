package com.afirme.afirmenet.empresas.service.impl.acceso;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.service.acceso.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public String getMailUsuario(String contrato) {
		return userDao.getMailUsuario(contrato);
	}

	@Override
	public boolean actualizarCorreoLogin( CorreoElectronicoDTO correoElectronicoDTO)  throws Exception{
		return userDao.actualizarCorreoLogin(correoElectronicoDTO);
	}
	
}
