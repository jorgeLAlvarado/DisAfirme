package com.afirme.afirmenet.empresas.service.impl.acceso;


import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;
import com.afirme.afirmenet.utils.time.TimeUtils;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.service.acceso.AliasAvatarDTO;
import com.afirme.afirmenet.empresas.service.acceso.UserService;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Jorge
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * 
 *
 */
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


	public boolean registrarUsuario(UsuariosDTO usuarioDTO) {
		return false;
	}
	
	
	public boolean actualizarPrimerLoginConAlias(String contrato) {
		return false;
	}
	
	
	public boolean incrementarNumeroIntentos(String contrato) {
		return false;
	}
	
	public boolean actualizarAlias(String contrato, String alias) {
		return false;
	}
	

	public boolean actualizarAliasLogin(String contrato, String alias, String avatar) {
		return false;
	}

	
	public String obtenerAvatar(String contrato) {
		return null;
	}

	
	public boolean actualizarCorreoLogin( CorreoElectronicoDTO correoElectronicoDTO)  throws Exception{
		return false;
	}

	
	public String obtenerFechaNacimiento(String cliente) {
		return null;
	}


	public boolean registraCambioAlias(AliasAvatarDTO aliasAvatarDTO) throws Exception {
		return false;
	}
}
