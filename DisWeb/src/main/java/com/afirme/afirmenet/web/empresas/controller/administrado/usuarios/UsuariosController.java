package com.afirme.afirmenet.web.empresas.controller.administrado.usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.afirme.afirmenet.empresas.service.administrador.usuarios.UsuariosService;
import com.afirme.afirmenet.model.administrador.usuarios.Usuarios;


@Controller
@RequestMapping("/listUsuarios")
public class UsuariosController {
	
	
	
	@Autowired
	private UsuariosService listaUsuariosService;
	
	List<Usuarios> ObtenerdatosUsuario (){
		return listaUsuariosService.getdatosUsuario(null);
	

    }

}
