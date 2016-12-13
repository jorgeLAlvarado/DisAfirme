package com.afirme.afirmenet.empresas.service.impl.acceso;


import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.service.acceso.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	private UserDao userDao;
	
	
	@Override
	public boolean verificarUsuarioRegistrado(String contrato) {
		return userDao.verificarUsuarioRegistrado(contrato);
	}
	
	@Override
	public int obtenerNumeroIntento(String contrato) {
		return userDao.obtenerNumeroIntento(contrato);
	}
	
	@Override
	public boolean verificarUsuarioRegistrado(String contrato) {
		return userDao.verificarUsuarioRegistrado(contrato);
	}
	
	@Override
	public String getMailUsuario(String contrato) {
		return null;
	}

	@Override
	public boolean actualizarCorreoLogin( CorreoElectronicoDTO correoElectronicoDTO)  throws Exception{
		return false;
	}
	
}
