package com.afirme.afirmenet.empresas.dao.administrador.usuarios;
import java.util.List;


import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;

/**
 * clase para consultar  lista de usuarios 
 *  * 
 * @author Selene Mena
 *
 * @version 1.0.0
 */
public interface UsuariosDao {
	
	/**
	 * @param datos
	 * @return
	 */
	public List<UsuariosDTO> getdatosUsuario(String datos);

}
