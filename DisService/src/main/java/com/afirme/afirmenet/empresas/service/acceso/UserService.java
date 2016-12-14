package com.afirme.afirmenet.empresas.service.acceso;


import com.afirme.afirmenet.empresas.dao.acceso.AliasAvatarDTO;
import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

public interface UserService {
	
	public boolean verificarUsuarioRegistrado(String contrato);
	
	public int obtenerNumeroIntento(String contrato);
	
	public String getMailUsuario(String contrato);
	
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;

	public boolean actualizarPrimerLoginConAlias(String contrato);
	
	public boolean incrementarNumeroIntentos(String contrato);
	
	public boolean actualizarAlias(String contrato, String alias);

	boolean actualizarAliasLogin(String contrato, String alias, String avatar);

	public String obtenerAvatar(String contrato);

	public boolean registraCambioAlias(AliasAvatarDTO aliasAvatarDTO) throws Exception;

	public String obtenerFechaNacimiento(String cliente);
	
	public boolean registrarUsuario(UsuariosDTO usuarioDTO);
}
