package com.afirme.afirmenet.web.controller.acceso;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller para las pantallas de las url donde se debe mostrar la pantalla de login.
 * 
 * @author jorge.canoc@gmail.com
 * 
 * Modificado on Nov 6, 2015 10:12:21 AM by eguzher
 * <br>
 * <br>
 * @author epifanio.guzman@afirme.com
 * <br>
 * 
 * Modificado on dic 13, 2016 11:12:21 AM by Bayron 
 * 
 * @author Bayron Gamboa Martinez
 */
@Controller
@RequestMapping("/cambioPwd")
public class PasswordController {

	static final AfirmeNetLog LOG = new AfirmeNetLog(ControlAcceso.class);

	@Autowired
	private PasswordService passwordService;
	@Autowired
	private FinDiaService finDiaService;

	@RequestMapping(value = "/cambioPwd.htm", method = RequestMethod.POST)
	public String cambioPwd(@ModelAttribute("login") Login login, ModelMap modelMap) {
		
	
		return null;
	}

}
