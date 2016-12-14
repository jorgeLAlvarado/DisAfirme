package com.afirme.afirmenet.empresas.service.administrador.usuarios;

import java.util.List;


import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;



/**
 * clase para consultar  lista de usuarios
 *  * 
 * @author Selene Mena
 *
 * @version 1.0.0
 */
public interface UsuariosService {
	
	List<UsuariosDTO> getdatosUsuario(String datos);

}
