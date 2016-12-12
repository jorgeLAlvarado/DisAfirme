package com.afirme.afirmenet.empresas.dao.acceso;

import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;

public interface UserDao {
	
	public String getMailUsuario(String contrato);
	
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;
	
}
