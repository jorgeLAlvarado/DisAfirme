package com.afirme.afirmenet.web.empresas.controller.configuraciones.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.afirme.afirmenet.empresas.service.configuraciones.AvatarService;
import com.afirme.afirmenet.model.configuraciones.AliasAvatarDTO;

@Controller
@RequestMapping("/login")
public class AvatarController {
	
	@Autowired
	private AvatarService mostrarAvatarService;
	
	
	
	List<AliasAvatarDTO> getListAvatar(){

		return mostrarAvatarService.getListAvatar(null);
		
	}
	

}
