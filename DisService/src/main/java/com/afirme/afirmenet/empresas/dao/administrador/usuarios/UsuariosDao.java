package com.afirme.afirmenet.empresas.dao.administrador.usuarios;
import java.util.List;


import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;


/**
 * @author Usuario
 *
 */
public interface UsuariosDao {
	
	/**
	 * @param datos
	 * @return
	 */
	public List<UsuariosDTO> getdatosUsuario(String datos);

}
