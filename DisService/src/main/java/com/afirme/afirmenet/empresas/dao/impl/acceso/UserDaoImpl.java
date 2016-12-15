package com.afirme.afirmenet.empresas.dao.impl.acceso;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.empresas.dao.acceso.AliasAvatarDTO;
//import com.afirme.afirmenet.dao.impl.acceso.AS400Dao;
//import com.afirme.afirmenet.dao.impl.acceso.DB2Dao;
//import com.afirme.afirmenet.dao.AS400Dao;
//import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.empresas.dao.impl.token.Token;
import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

/**
 * clase para validar la contraseña
 *  * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 * 
 *   
 * Modificado on dic 13, 2016 4:12:11 PM by Selene 
 * 
 * @author Selene Mena Quiñones
 * 
 */
@Repository
public class UserDaoImpl implements UserDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(UserDaoImpl.class);

	@Autowired
	private AS400Dao as400Dao;
	@Autowired
	private DB2Dao db2Dao;
	
	/**
	 * metodo para obtener el correo del usuario
	 * @param contrato
	 * @return el correo del contacto.
	 * 
	 */
	public String getMailUsuario(String contrato) {
		return null;
	}

	/**
	 * metodo para actualizar el correo del usuario
	 * @param correoElectronicoDTO
	 * @return TRUE si el proceso no presento problemas.
	 */
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception{
		return false;
	}
	
	public boolean registrarUsuario(UsuariosDTO usuarioDTO) {
		return false;
	}

	public String obtenerTipoRegimen(String contrato) {
		// TODO Auto-generated method stub
		return null;
	}

	public int obtenerNumeroIntento(String contrato) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean verificarUsuarioRegistrado(String contrato) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean registrarUsuarioAlias(String contrato) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean actualizarPrimerLoginConAlias(String contrato) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean incrementarNumeroIntentos(String contrato) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean actualizarAlias(String contrato, String alias) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean actualizarAliasLogin(String contrato, String alias, String avatar) {
		// TODO Auto-generated method stub
		return false;
	}

	public String obtenerAvatar(String contrato) {
		// TODO Auto-generated method stub
		return null;
	}

	public String obtenerFechaNacimiento(String cliente) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public List<UsuariosDTO> getTokens(Boolean soloDiponibles) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public List<UsuariosDTO> getdatosUsuario(String datos) {
		// TODO Auto-generated method stub
		return null;
	}
	


	
}
