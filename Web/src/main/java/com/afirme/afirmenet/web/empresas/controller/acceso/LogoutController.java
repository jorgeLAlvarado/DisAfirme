package com.afirme.afirmenet.web.empresas.controller.acceso;

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
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.acceso.LoginController;
import com.afirme.afirmenet.web.empresas.controller.base.BaseController;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;
import com.afirme.afirmenet.model.AfirmeNetUser;


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

	/**
	 * Validacion de sesion correcta
	 * @param modelMap
	 * @param request
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutSeguro(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {

		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.info(">> logOutSeguro()");
		LOG.info("<< logOutSeguro()");
		
		return AfirmeNetWebConstants.MV_LOGIN;
	}

	
	/**
	 * validacion de sesion incorrecta
	 * @param modelMap
	 * @param request
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout_enrolamiento.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutSeguroEnrolamiento(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {

		request.getSession().invalidate();
		LOG.info(">> logOutSeguroEnrolamiento()");
		LOG.info("<< logOutSeguroEnrolamiento()");
		
		return AfirmeNetWebConstants.MV_LOGIN;
	}
	
	/**
	 * manda la seccion de SPEI
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/cep_spei.htm", method = RequestMethod.POST)
	public String cepSPEI(HttpServletRequest request, ModelMap modelMap) {				
		
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		logInService.updateINOUT(afirmeNetUser.getContrato(), "N");
		request.getSession().invalidate();
		LOG.info(">> cepSPEI()");
		LOG.info("<< cepSPEI()");
		
		return AfirmeNetWebConstants.MV_LOGIN;		
	}
	
	/**
	 * manda al cierre de seccion clasica
	 * @param modelMap
	 * @param request
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout_version_clasica.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutVersionClasica(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {

		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.info(">> logOutVersionClasica()");
		LOG.info("<< logOutVersionClasica()");
		
		return AfirmeNetWebConstants.MV_LOGIN;
	}
	
}
