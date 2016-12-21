package com.afirme.afirmenet.empresas.dao.acceso;

import java.util.List;

import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

/**
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * Created on Created on Dic 10, 2016 3:50:05 PM by Jorge
 * 
 *  
 * Modificado on dic 14, 2016 3:12:21 PM by Selene 
 * 
 * @author Selene Mena Quiñones
 * 
 */
public interface UserDao {
	
	/**
	 * Consulta para obtener el Login Acual
	 * @param contrato
	 * @return
	 */
	public String getMailUsuario(String contrato);
	
	/**
	 * Consulta para actualizar el correo y verificar errores si estos existen
	 * @param correoElectronicoDTO
	 * @return
	 * @throws Exception
	 */
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;
	
	// daos de login exitoso
	// +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+//
	/**
	 * Consulta para obtener el tipo de regimen
	 * @param contrato
	 * @return
	 */
	public String obtenerTipoRegimen(String contrato);

	/**
	 * Consulta para obtener el numero de intentos al insertar token
	 * @param contrato
	 * @return
	 */
	public int obtenerNumeroIntento(String contrato);

	/**
	 * Consulta para verificar que el usuario se encuentre registrado
	 * @param contrato
	 * @return
	 */
	public boolean verificarUsuarioRegistrado(String contrato);

	/**
	 * Consulta para dar a conocer que el usuario esta logueado
	 * @param contrato
	 * @return
	 */
	public boolean actualizarPrimerLoginConAlias(String contrato);

	/**
	 * Consulta para aumentar el numero de intentos realizados de logueo
	 * @param contrato
	 * @return
	 */
	public boolean incrementarNumeroIntentos(String contrato);

	/**
	 * Consulta para poder actualizar los alias de un usuario registrado
	 * @param contrato
	 * @param alias
	 * @return
	 */
	public boolean actualizarAlias(String contrato, String alias);

	/**
	 * Consulta para actualizar el alias modificado y este pueda ser utilizado para loguear
	 * @param contrato
	 * @param alias
	 * @param avatar
	 * @return
	 */
	boolean actualizarAliasLogin(String contrato, String alias, String avatar);

	/**
	 * Consulta para obtener el avatar asociado a una cuenta
	 * @param contrato
	 * @return
	 */
	public String obtenerAvatar(String contrato);

	/**
	 * Obtener la fecha de naciemiento del usuario registrado
	 * @param cliente
	 * @return
	 */
	public String obtenerFechaNacimiento(String cliente);
	
	/**
	 * Realizar el registro de un nuevo usuario
	 * @param usuarioDTO
	 * @return
	 */
	public boolean registrarUsuario(UsuariosDTO usuarioDTO);
	
	/**Consulta para listar usuarios en administrador
	 * @param datos
	 * @return
	 */
	public List<UsuariosDTO> getdatosUsuario(Boolean usuarios);	
	
	/**Consulta para los Tokens disponibles en administrador
	 * @param datos
	 * @return
	 */	
	
	public List<UsuariosDTO> getTokens(Boolean soloDisponibles);
	
	
}
