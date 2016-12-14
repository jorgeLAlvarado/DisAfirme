package com.afirme.afirmenet.empresas.service.acceso;


import com.afirme.afirmenet.empresas.dao.acceso.AliasAvatarDTO;
import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

public interface UserService {
	
	/**
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
	 * @param contrato
	 * @return
	 */
	public String obtenerAvatar(String contrato);

	/**
	 * Se registra el cambio de alias 
	 * @param aliasAvatarDTO
	 * @return
	 * @throws Exception
	 */
	public boolean registraCambioAlias(AliasAvatarDTO aliasAvatarDTO) throws Exception;

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
}
