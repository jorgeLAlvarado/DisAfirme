package com.afirme.afirmenet.empresas.dao.administrador.usuarios;
import java.util.List;

import com.afirme.afirmenet.model.administrador.usuarios.Usuarios;


/**
 * @author Usuario
 *
 */
public interface UsuariosDao {
	
	/**
	 * @param datos
	 * @return
	 */
	List<Usuarios> getdatosUsuario(String datos);

}
