package com.afirme.afirmenet.web.controller.acceso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.beas.login.PreguntaSecreta;
import com.afirme.afirmenet.empresas.service.acceso.LogInService;
import com.afirme.afirmenet.empresas.service.acceso.PasswordService;
import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.ibs.beans.JBAvatar;
import com.afirme.afirmenet.model.Login;
import com.afirme.afirmenet.service.contrato.ContratoService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.base.BaseController;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;


/**
 * clase para implementar la contraseña.
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
@SessionAttributes({"acceso","pathAvatar","intentosToken"})
@RequestMapping("/controlAccesoBasico")
public class ControlAccesoBasico  extends BaseController{
	
	static final AfirmeNetLog LOG = new AfirmeNetLog(ControlAccesoBasico.class);
	
	
	
	@Autowired
	private PasswordService passwordService;
	
	//@Autowired
	//private PreguntaSecretaService preguntaService;
	
	@Autowired
	private LogInService logInService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContratoService contratoService;
	/**
	 * Método que valida la información de Contrato y verifica el estatus del Token.
	 * Si la información es correcta redirige a 'contratoBanca', de lo contrario, permanece en 'activaToken'
	 * @param login
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/valida_usr_activacion.htm", method = RequestMethod.POST)
	public String validaUsuarioActivacion(@ModelAttribute("activacion") Login login, ModelMap modelMap, HttpServletRequest request) {

		Contrato contrato = contratoService.getDatosContrato(login.getContrato());
		LOG.info(">> validaUsuarioActivacion()");
		LOG.info("<< validaUsuarioActivacion()");
		return AfirmeNetWebConstants.MV_ACTIVA_CONTRATO_BASICO;
	}
	
	/**
	 * Método que genera las lista de preguntas secretas del sistema y evalúa aquellas
	 * ya usadas por el contrato para desplegar la información en 'preguntaSeguridad' 
	 * @param login
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/pregunta_secreta.htm", method = RequestMethod.POST)
	public String preguntaSecreta(@ModelAttribute("activacion") Login login, ModelMap modelMap) {


		// extrae lista de preguntas del sistema
		ArrayList<PreguntaSecreta> listadoPreguntas = null; //modificar//
		// extrae lista de preguntas utilizadas en el contrato
		HashMap<String, String> preguntasContrato = (HashMap<String, String>) preguntaService
				.getPregUsadas(login.getContrato());


		LOG.info(">> preguntaSecreta()");
		LOG.info("<< preguntaSecreta()");
		return AfirmeNetWebConstants.MV_PREG_SEGURIDAD_BASICO;
	}
	

	/**
	 * Método que almacena la información proporcionada de la selección de pregunta secreta
	 * y su respuesta correspondiente en la BD.
	 * Si no existen errores se redirige a 'asignaPwd'
	 * @param login
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/guarda_preg_secreta.htm", method = RequestMethod.POST)
	public String guardaPreguntaSecreta(@ModelAttribute("activacion") Login login, 
			ModelMap modelMap, HttpServletRequest request, RedirectAttributes redirect) {

		
		preguntaService.guardaPreguntaUsada(login.getContrato());
		LOG.info(">> guardaPreguntaSecreta()");
		LOG.info("<< guardaPreguntaSecreta()");
		return preguntaSecreta(login, modelMap);
	}
	
	/**
	 * establese la contraseña
	 * @param login
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/establece_pwd.htm", method = RequestMethod.POST)
	public String establecePassword(@ModelAttribute("login") Login login, 
			ModelMap modelMap, HttpServletRequest request) {
		
		passwordService.setPassword(login.getContrato());
		LOG.info(">> establecePassword()");
		LOG.info("<< establecePassword()");
		return establecePassword(login, modelMap, request);
	}
	
	/**
	 * metodo para capturar el alias
	 * @param login
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/alias.htm", method = RequestMethod.POST)
	public String capturaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {


		ArrayList<JBAvatar> lstAvatar = (ArrayList<JBAvatar>) logInService.getListAvatar();
		LOG.info(">> capturaAlias()");
		LOG.info("<< capturaAlias()");
		return AfirmeNetWebConstants.MV_LOGIN_AVATAR_BASICO;
	}
	
	/**
	 * confirmacion del alias
	 * @param login
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/alias_confirma.htm", method = RequestMethod.POST)
	public String confirmaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {

	
		userService.actualizarAliasLogin(login.getContrato(), login.getAlias(), login.getAvatar());
		LOG.info(">> confirmaAlias()");
		LOG.info("<< confirmaAlias()");
		return AfirmeNetWebConstants.MV_ACTIVA_TOKEN;
				
	}	
	
	
	/**
	 * activacion de cuenta
	 * @param login
	 * @param modelMap
	 * @param request
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/activacion.htm", method = RequestMethod.POST)
	public String activacion(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {

		modelMap.addAttribute("login", login);
		LOG.info(">> activacion()");
		LOG.info("<< activacion()");
		return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
	}
	
	/**
	 * validacion de respuesta
	 * @param login
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validaRespuesta.htm", method = RequestMethod.POST)
	public String validaRespuesta(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {

		login.setAvatar(((Login)modelMap.get("acceso")).getAvatar());
		login.setAlias(((Login)modelMap.get("acceso")).getAlias());
		LOG.info(">> validaRespuesta()");
		LOG.info("<< validaRespuesta()");
		return AfirmeNetWebConstants.MV_RECUPERA_PREG_SEGU;
	}
	
	
}
