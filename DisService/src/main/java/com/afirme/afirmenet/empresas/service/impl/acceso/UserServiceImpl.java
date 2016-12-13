package com.afirme.afirmenet.empresas.service.impl.acceso;


import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.service.acceso.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	private UserDao userDao;
	
	
	
	public boolean verificarUsuarioRegistrado(String contrato) {
		return userDao.verificarUsuarioRegistrado(contrato);
	}
	
	
	public int obtenerNumeroIntento(String contrato) {
		return userDao.obtenerNumeroIntento(contrato);
	}
	

	public String getMailUsuario(String contrato) {
		return null;
	}

	
	public boolean actualizarCorreoLogin( CorreoElectronicoDTO correoElectronicoDTO)  throws Exception{
		return false;
	}

	public boolean registrarUsuario(UsuariosDTO usuarioDTO) {
		return false;
	}
	
}
