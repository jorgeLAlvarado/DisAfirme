package com.afirme.afirmenet.web.controller.acceso;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.empresas.service.acceso.LogInService;
import com.afirme.afirmenet.empresas.service.log.LogService;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;


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
 * 
 */

@Controller
@RequestMapping("/login")
public class LogoutController extends BaseController {

	static final AfirmeNetLog LOG = new AfirmeNetLog(LoginController.class);

	@Autowired
	private LogInService logInService;
	@Autowired
	private LogService logService;

	@RequestMapping(value = "/logout.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutSeguro(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		
		return null;
	}

	
	@RequestMapping(value = "/logoutEnrolamiento.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutSeguroEnrolamiento(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		
		return null;
	}
	
	@RequestMapping(value = "/CEP-SPEI.htm", method = RequestMethod.POST)
	public String cepSPEI(HttpServletRequest request, ModelMap modelMap) {				
		
		
		return null;		
	}
	
	@RequestMapping(value = "/logoutVersionClasica.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutVersionClasica(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		
		return null;
	}
	
}
