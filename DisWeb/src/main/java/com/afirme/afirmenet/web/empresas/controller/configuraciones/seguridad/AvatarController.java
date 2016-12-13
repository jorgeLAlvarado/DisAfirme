package com.afirme.afirmenet.web.empresas.controller.configuraciones.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.afirme.afirmenet.beas.login.JBAvatar;
import com.afirme.afirmenet.empresas.service.configuraciones.AvatarService;

@Controller
@RequestMapping("/login")
public class AvatarController {
	
	@Autowired
	private AvatarService mostrarAvatarService;
	
	
	
	List<JBAvatar> getListAvatar(){

		return mostrarAvatarService.getListAvatar(null);
		
	}
	

}
