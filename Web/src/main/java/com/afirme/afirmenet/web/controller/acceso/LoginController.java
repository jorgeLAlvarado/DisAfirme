package com.afirme.afirmenet.web.controller.acceso;


import java.sql.SQLException;

import java.io.IOException;

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

import com.afirme.afirmenet.empresas.service.acceso.ActividadesPendientesService;
import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.model.AfirmeNetUser;
import com.afirme.afirmenet.model.Login;
import com.afirme.afirmenet.model.base.TokenModel;
import com.afirme.afirmenet.service.FinDiaService;
import com.afirme.afirmenet.empresas.service.acceso.LogInService;
import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.ibs.beans.JBLogList;
import com.afirme.afirmenet.ibs.beans.JBLogin;
import com.afirme.afirmenet.service.contrato.ContratoService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.base.BaseController;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;



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
 * Modificado on dic 21, 2016 3:21:21 PM by Selene 
 * 
 * @author Selene Mena Quiñones
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	
	static final AfirmeNetLog LOG = new AfirmeNetLog(LoginController.class);
	
	@Autowired

	private ContratoService contratoService;
	@Autowired
	private LogInService logInService;
	@Autowired
	private FinDiaService finDiaService;
	@Autowired

	private UserService userService;
	
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
		
		String contrato = request.getParameter("UserId") == null ? "" : request.getParameter("UserId").trim();
		login.setContrato( contrato );
		LOG.info(">> mostrarAvisoSeguridad()");
		LOG.info("<< mostrarAvisoSeguridad()");
		
		return AfirmeNetWebConstants.MV_LOGIN_AVISO_SEGURIDAD;
	}

	/**
	 * muestra los datos de acceso
	 * @param login
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/aviso_seguridad.htm", method = RequestMethod.POST)
	public String mostrarDatosAcceso(@ModelAttribute("login") Login login,ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			com.afirme.afirmenet.beas.login.JBLogin loginUser = contratoService.getDatosLogIn(login.getContrato());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOG.info(">> mostrarDatosAcceso()");
		LOG.info("<< mostrarDatosAcceso()");
		
		return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
	}


	/**
	 * Muesta el HOME de inicio
	 * @param login
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws AfirmeNetException
	 */
	@RequestMapping(value = "/datos_acceso.htm", method = RequestMethod.POST)
	public String mostrarHome(@ModelAttribute("login") Login login,
			HttpServletRequest request, ModelMap modelMap) throws AfirmeNetException{

		//modificar
		private String usuario = login.getContrato();
		//modificar
		logInService.lookupChangeParameter(usuario);
		
		LOG.info(">> mostrarHome()");
		LOG.info("<< mostrarHome()");
		
		return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;

	}


	/**
	 * muestra los terminos y condiciones de AFIRME
	 * @return
	 */
	@RequestMapping(value = "/terminos.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String mostrarTerminos() {


		
		LOG.info(">> mostrarTerminos()");
		LOG.info("<< mostrarTerminos()");

		return AfirmeNetWebConstants.MV_HOME_TERMINOS;
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
		
		TokenModel respToken = null;
		login.setIntentosToken(respToken.getIntentos());
		LOG.info(">> validaTokenLogIn()");
		LOG.info("<< validaTokenLogIn()");

		return false;
	}
	
	/**
	 * Metodo para mostrar las autorizaciones pendientes
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	
	@RequestMapping(value = "/avatar.htm", method = RequestMethod.GET)
	public String mostraravatar(HttpServletRequest request, HttpServletResponse resp, ModelMap model) throws IOException{
		LOG.info("<<mostraravatar()");
		Boolean avatar = false;
		userService.getobtenerAvatar(avatar);
		LOG.info(">>mostraravatar()");
		userService.getobtenerAvatar(avatar);
		
		return AfirmeNetWebConstants.MV_MOSTRAR_AVATAR;
	}

	@RequestMapping(value = "/alias.htm", method = RequestMethod.GET)
	public String mostraralias(HttpServletRequest request, HttpServletResponse resp, ModelMap model) throws IOException{
		LOG.info("<<mostraralias()");
		Boolean alias = false;
		userService.getobtenerAlias(alias);
		LOG.info(">>mostraralias()");
		userService.getobtenerAlias(alias);
		
		return AfirmeNetWebConstants.MV_MOSTRAR_ALIAS;
	}
}
