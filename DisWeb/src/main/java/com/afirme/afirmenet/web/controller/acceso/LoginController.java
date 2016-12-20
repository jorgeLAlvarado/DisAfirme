package com.afirme.afirmenet.web.controller.acceso;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.model.AfirmeNetUser;
import com.afirme.afirmenet.model.Login;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.base.BaseController;



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
@RequestMapping("/login")
public class LoginController extends BaseController {

	
	static final AfirmeNetLog LOG = new AfirmeNetLog(LoginController.class);
	
	/**
	 * Si la peticion viene desde el portal el contrato que captura el usuario esta en UserId.
	 * 
	 * @param login viene desde el view login.
	 * @param modelMap atributos de request.
	 * @param request el contrato que captura el usuario esta en UserId.
	 * @return view contrato
	 */
	@RequestMapping(value = "/contrato.htm", method = RequestMethod.POST)
	public String mostrarAvisoSeguridad(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		
		LOG.debug("Funciona este metodo: retorna la vista del contrato del usuario");
		
		return null;
	}

	@RequestMapping(value = "/aviso_seguridad.htm", method = RequestMethod.POST)
	public String mostrarDatosAcceso(@ModelAttribute("login") Login login,ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Funciona este metodo: Muesta el aviso de seguridad de AFIRME");
		return null;
	}


	@RequestMapping(value = "/datos_acceso.htm", method = RequestMethod.POST)
	public String mostrarHome(@ModelAttribute("login") Login login,
			HttpServletRequest request, ModelMap modelMap) throws AfirmeNetException{
		LOG.debug("Funciona este metodo: entra al home de acceso");
		return null;

	}


	@RequestMapping(value = "/terminos.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String mostrarTerminos() {
		LOG.debug("Funciona este metodo: muesta los terminos y condiciones de AFIRME");
		return null;
	}
	
	/**
	 * Funcion general para la implementacion de la validacion de token
	 * @param passCode
	 * @param contrato
	 * @param usuario
	 * @param basicoSinToken
	 * @return
	 */
	public boolean validaTokenLogIn(String passCode, AfirmeNetUser afirmeNetUser, ModelMap modelMap, Login login) {
		
		LOG.debug("Funciona este metodo: valida el token");
		
		return false;
	}


}
