package com.afirme.afirmenet.empresas.service.acceso;


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
public interface UserService {
	
	/**
	 * Se verifica que el usuario se encuentre registrado
	 * @param contrato
	 * @return
	 */
	public boolean verificarUsuarioRegistrado(String contrato);
	
	/**
	 * Obtener numero de intentos
	 * @param contrato
	 * @return
	 */
	public int obtenerNumeroIntento(String contrato);
	
	/**
	 * Publicar el nuevo correo actula asociado con la cuenta
	 * @param contrato
	 * @return
	 */
	public String getMailUsuario(String contrato);
	
	/** 
	 * Actualizar el correo y verificar errores si estos existen
	 * @param correoElectronicoDTO
	 * @return
	 * @throws Exception
	 */
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;

	/**
	 * Verifica el primer login realizado
	 * @param contrato
	 * @return
	 */
	public boolean actualizarPrimerLoginConAlias(String contrato);
	
	/**
	 * Incrementa el numero de intentos de logueo
	 * @param contrato
	 * @return
	 */
	public boolean incrementarNumeroIntentos(String contrato);
	
	/**
	 * Actualiza el alias del usuario 
	 * @param contrato
	 * @param alias
	 * @return
	 */
	public boolean actualizarAlias(String contrato, String alias);

	/**
	 * Actualiza alias para poder realizar logueo
	 * @param contrato
	 * @param alias
	 * @param avatar
	 * @return
	 */
	boolean actualizarAliasLogin(String contrato, String alias, String avatar);

	/**
	 * Se obtiene el avatar del usuario para loguear
	 * @param avatar
	 * @return
	 */
	public String getobtenerAvatar(Boolean avatar);
	
	/**
	 * Se obtiene el alias del usuario para loguear
	 * @param alias
	 * @return
	 */
	public String getobtenerAlias(Boolean alias);

	/**
	 * Se obtiene la fecha de nacimiento del usuario
	 * @param cliente
	 * @return
	 */
	public String obtenerFechaNacimiento(String cliente);
	
	/**
	 * Se registra un nuevo usuario.
	 * @param usuarioDTO
	 * @return
	 */
	public boolean registrarUsuario(UsuariosDTO usuarioDTO);
	
	
	/**
	 * consulta listar usuarios
	 * @param usuarioDTO
	 * @return
	 */	
	public List<UsuariosDTO> getdatosUsuario(Boolean usuarios);
	
	/**
	 * consulta tokens disponibles
	 * @param usuarioDTO
	 * @return
	 */		
	public List<UsuariosDTO> getTokens(Boolean soloDisponibles);
	
}
