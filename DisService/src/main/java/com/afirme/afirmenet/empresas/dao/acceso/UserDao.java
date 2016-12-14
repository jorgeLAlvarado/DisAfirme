package com.afirme.afirmenet.empresas.dao.acceso;

import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

public interface UserDao {
	
	public String getMailUsuario(String contrato);
	
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;
	
	// daos de login exitoso
	// +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+//
	public String obtenerTipoRegimen(String contrato);

	public int obtenerNumeroIntento(String contrato);

	public boolean verificarUsuarioRegistrado(String contrato);

	public boolean registrarUsuarioAlias(String contrato);

	public boolean actualizarPrimerLoginConAlias(String contrato);

	public boolean incrementarNumeroIntentos(String contrato);

	public boolean actualizarAlias(String contrato, String alias);

	boolean actualizarAliasLogin(String contrato, String alias, String avatar);

	public String obtenerAvatar(String contrato);

	public String obtenerFechaNacimiento(String cliente);
	
	public boolean registrarUsuario(UsuariosDTO usuarioDTO);
	// +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+//
}
