package com.afirme.afirmenet.web.empresas.controller.acceso;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.beas.login.PreguntaSecreta;
import com.afirme.afirmenet.empresas.service.acceso.LogInService;
import com.afirme.afirmenet.empresas.service.acceso.OTPService;
import com.afirme.afirmenet.empresas.service.acceso.PasswordService;
import com.afirme.afirmenet.empresas.service.acceso.PreguntaSecretaService;
import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.empresas.service.contrato.ContratoService;
import com.afirme.afirmenet.empresas.service.token.TokenService;
import com.afirme.afirmenet.model.Login;
import com.afirme.afirmenet.utils.AfirmeNetLog;
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
 */
public class ControlAcceso{

	static final AfirmeNetLog LOG = new AfirmeNetLog(ControlAcceso.class);
	
	
	@Autowired(required=true)
	private OTPService otpService;
	@Autowired
	private OTPAdminService otpAdminService;
	@Autowired
	private OTPAgenteService otpAgenteService;
	@Autowired
	private ContratoService contratoService;
	@Autowired
	private PreguntaSecretaService preguntaService;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;	
	@Autowired
	private LogInService logInService;
	@Autowired
	protected TokenService tokenService;
	
	
	/**
	 * Metodo utilizado para la validacion del contrato
	 * @param login
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/valida_contrato.htm", method = RequestMethod.POST)
	public String validaContrato(Login login,ModelMap modelMap) {
		
		Boolean flag = otpService.usaTokens(login.getContrato());
		LOG.info(">> validaContrato()");
		LOG.info("<< validaContrato()");
		
		return AfirmeNetWebConstants.MV_LOGIN_AVISO_SEGURIDAD;
	}

	/**
	 * Metodo que redirige al jsp para sincronizar manualmente el token
	 * @param login
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/sincroniza.htm", method = RequestMethod.POST)
	public String sincroniza(@ModelAttribute("login") Login login, ModelMap modelMap) {

		modelMap.put("sincronizacion", login);
		LOG.info(">> sincroniza()");
		LOG.info("<< sincroniza()");
		return AfirmeNetWebConstants.MV_SYNC_TOKEN;
	}

	/**
	 * metodo que realiza la sincronizacion del token usado
	 * @param login
	 * @param modelMap
	 */
	@RequestMapping(value = "/sincronizacion_token", method = RequestMethod.POST)
	public void sincronizacionToken(@ModelAttribute("sincronizacion") Login login, ModelMap modelMap) {
		
		LOG.info(">> sincroniza()");
		mailService.sendAlertaSincrTok(mail, login.getContrato(), login.getSerialToken());
		LOG.info("<< sincronizacionToken()");

	}
	
	/**
	 * M�todo que valida la informaci�n de Contrato y verifica el estatus del Token.
	 * Si la informaci�n es correcta redirige a 'contratoBanca', de lo contrario, permanece en 'activaToken'
	 * @param login
	 * @param modelMap
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/valida_usr_activacion.htm", method = RequestMethod.POST)
	public String validaUsuarioActivacion(@ModelAttribute("activacion") Login login, ModelMap modelMap, HttpServletRequest request) {
		
		LOG.info(">> sincroniza()");
		Contrato contrato = contratoService.getDatosContrato(login.getContrato());
		LOG.info("<< sincronizacionToken()");
		return AfirmeNetWebConstants.MV_ACTIVA_CONTRATO;
	}
	
	/**
	 * M�todo que genera las lista de preguntas secretas del sistema y eval�a aquellas
	 * ya usadas por el contrato para desplegar la informaci�n en 'preguntaSeguridad' 
	 * @param login
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/pregunta_secreta.htm", method = RequestMethod.POST)
	public String preguntaSecreta(@ModelAttribute("activacion") Login login, ModelMap modelMap) {
		
		LOG.info(">> sincroniza()");
		// extrae lista de preguntas del sistema
		ArrayList<PreguntaSecreta> listadoPreguntas = (ArrayList<PreguntaSecreta>) preguntaService.getListadoPreguntas();
		// extrae lista de preguntas utilizadas en el contrato
		HashMap<String, String> preguntasContrato = (HashMap<String, String>) preguntaService
				.getPregUsadas(login.getContrato());
		LOG.info("<< preguntaSecreta()");
		return AfirmeNetWebConstants.MV_PREG_SEGURIDAD;
	}
	
	/**
	 * M�todo que almacena la informaci�n proporcionada de la selecci�n de pregunta secreta
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
		

		LOG.info(">> sincroniza()");
		// TODO: validacion de sesion
		String pregunta = request.getParameter("pregunta") != null ? request.getParameter("pregunta") : "";
		String respuesta = request.getParameter("respuesta") != null ? request.getParameter("respuesta") : "";
		String confirmacion = request.getParameter("confirmar") != null ? request.getParameter("confirmar") : "";
		// se extrae el id de pregunta seleccionado, si no hubo seleccion, se
		// maneja el error para no terminar la app
		LOG.info("<< guardaPreguntaSecreta()");
		return preguntaSecreta(login, modelMap);
	}
	
	
}
