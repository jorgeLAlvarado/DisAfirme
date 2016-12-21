package com.afirme.afirmenet.web.controller.acceso;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.afirme.afirmenet.empresas.service.acceso.ActividadesPendientesService;
import com.afirme.afirmenet.empresas.service.acceso.PasswordService;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.base.BaseController;
import com.afirme.afirmenet.web.empresas.model.AfirmeNetUser;
import com.afirme.afirmenet.web.empresas.utils.AfirmeNetWebConstants;

/**
 * Controller principal que atiende las peticiones mas genericas como /, /index,
 * /home, /inicio
 * 
 * @author jorge.canoc@gmail.com
 * 
 * Modificado on dic 14, 2016 3:12:21 PM by Selene 
 * 
 * @author Selene Mena Quiñones
 * 
 */
@Controller
@RequestMapping("/home")
public class HomeController  extends BaseController{
	
	static final AfirmeNetLog LOG = new AfirmeNetLog(HomeController.class);
	
	@Autowired
	private ActividadesPendientesService actividadesPendientesService;

	@RequestMapping("test/dispatcher")
	public String dispatcher(
			ModelMap model, HttpServletRequest request) {
		return null;
	}

	@RequestMapping("/esperando")
	public String esperando(@RequestParam(required = true) String nextAction,
			ModelMap model) {
		return null;
	}
	
	/**
	 * Metodo que atiende las peticiones al contexto /
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse resp, ModelMap model) throws IOException{
		
		return null;
	}
	
	/**
	 * Metodo que atiende las peticiones al contexto /login.htm
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse resp, ModelMap model) throws IOException{
		LOG.debug("Funciona este metodo");
		return null;
	}
	
	/**
	 * Metodo que atiende las peticiones al contexto /index.htm
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/index.htm", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse resp, ModelMap model) throws IOException{
		LOG.debug("Funciona este metodo");
		return null;
	}
	
	@RequestMapping(value = "/home.htm", method = RequestMethod.GET)
	public String home(HttpServletRequest request, ModelMap modelMap) {
		LOG.debug("Funciona este metodo");
		return null;
	}
	
	
	/**
	 * Metodo de las notificaciones y autorizaciones
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	
	@RequestMapping(value = "/notificaciones.htm", method = RequestMethod.GET)
	public String notificaciones(HttpServletRequest request, HttpServletResponse resp, ModelMap model) throws IOException{
		LOG.info("<<notificaciones()");
		Boolean usuarios = false;
		actividadesPendientesService.getdatosUsuario(usuarios);
		LOG.info(">>notificaciones()");
		actividadesPendientesService.getdatosUsuario(usuarios);
		
		return AfirmeNetWebConstants.LISTAR_USUARIOS;
	}
	
}
