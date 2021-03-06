package com.afirme.afirmenet.empresas.service.impl.acceso;


import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.service.acceso.UserService;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Jorge
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * 
 * Modificado on dic 14, 2016 3:12:21 PM by Selene 
 * 
 * @author Selene Mena Qui�ones
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

	
	public String getobtenerAvatar(Boolean avatar) {
		
		return null;
	}
	
	public String getobtenerAlias(Boolean alias) {
		
		return null;
	}

	
	public boolean actualizarCorreoLogin( CorreoElectronicoDTO correoElectronicoDTO)  throws Exception{
		return false;
	}

	
	public String obtenerFechaNacimiento(String cliente) {
		return null;
	}
	
	
	@Autowired
	private UserService listaUsuarios;
	
	
	public List<UsuariosDTO> getdatosUsuario (Boolean usuarios){
		return listaUsuarios.getdatosUsuario(usuarios);
	

    }
	
	@Autowired
	private UserService tokendisponibles; 
	
	public List<UsuariosDTO> getTokens(Boolean soloDisponibles){
		return tokendisponibles.getTokens(soloDisponibles);
	}
	
	
	
	
	
}
