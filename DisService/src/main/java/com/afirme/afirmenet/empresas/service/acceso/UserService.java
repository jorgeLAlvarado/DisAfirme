package com.afirme.afirmenet.empresas.service.acceso;


import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;

public interface UserService {
	
	public String getMailUsuario(String contrato);
	
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;

}
