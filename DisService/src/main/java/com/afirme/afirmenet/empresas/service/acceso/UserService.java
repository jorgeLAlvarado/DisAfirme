package com.afirme.afirmenet.empresas.service.acceso;


import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

public interface UserService {
	
	public boolean verificarUsuarioRegistrado(String contrato);
	
	public int obtenerNumeroIntento(String contrato);
	
	public String getMailUsuario(String contrato);
	
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;
	
	public boolean registrarUsuario(UsuariosDTO usuarioDTO);

}
