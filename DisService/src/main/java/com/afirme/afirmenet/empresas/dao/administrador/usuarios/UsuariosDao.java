package com.afirme.afirmenet.empresas.dao.administrador.usuarios;
import java.util.List;

import com.afirme.afirmenet.model.administrador.usuarios.Usuarios;


public interface UsuariosDao {
	
	List<Usuarios> getdatosUsuario(String datos);

}
