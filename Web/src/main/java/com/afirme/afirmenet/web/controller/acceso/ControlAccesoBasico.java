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

import com.afirme.afirmenet.empresas.service.acceso.LogInService;
import com.afirme.afirmenet.empresas.service.acceso.PasswordService;
import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.beas.login.PreguntaSecreta;
import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.model.Login;
import com.afirme.afirmenet.service.contrato.ContratoService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.base.BaseController;


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
	
	@Autowired
	private PreguntaSecretaService preguntaService;
	
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
		return null;
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
		ArrayList<PreguntaSecreta> listadoPreguntas = (ArrayList<PreguntaSecreta>) preguntaService
				.getListadoPreguntas();
		// extrae lista de preguntas utilizadas en el contrato
		HashMap<String, String> preguntasContrato = (HashMap<String, String>) preguntaService
				.getPregUsadas(login.getContrato());
		LOG.info(">> preguntaSecreta()");
		LOG.info("<< preguntaSecreta()");
		return null;
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


		LOG.info(">> guardaPreguntaSecreta()");
		LOG.info("<< guardaPreguntaSecreta()");
		return null;
	}
	
	@RequestMapping(value = "/establece_pwd.htm", method = RequestMethod.POST)
	public String establecePassword(@ModelAttribute("login") Login login, 
			ModelMap modelMap, HttpServletRequest request) {
		
		LOG.info(">> establecePassword()");
		LOG.info("<< establecePassword()");
		return null;
	}
	
	@RequestMapping(value = "/alias.htm", method = RequestMethod.POST)
	public String capturaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {


		LOG.info(">> capturaAlias()");
		LOG.info("<< capturaAlias()");
		return null;
	}
	
	@RequestMapping(value = "/alias_confirma.htm", method = RequestMethod.POST)
	public String confirmaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {


		LOG.info(">> confirmaAlias()");
		LOG.info("<< confirmaAlias()");
		return null;
				
	}	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/activacion.htm", method = RequestMethod.POST)
	public String activacion(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {


		LOG.info(">> activacion()");
		LOG.info("<< activacion()");
		return null;
	}
	
	@RequestMapping(value = "/validaRespuesta.htm", method = RequestMethod.POST)
	public String validaRespuesta(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {


		LOG.info(">> validaRespuesta()");
		LOG.info("<< validaRespuesta()");
		return null;
	}
	
	
}
