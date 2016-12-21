package com.afirme.afirmenet.web.controller.acceso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.afirme.afirmenet.beas.login.JBAvatar;
import com.afirme.afirmenet.empresas.service.acceso.LogInService;
import com.afirme.afirmenet.empresas.service.acceso.OTPService;
import com.afirme.afirmenet.empresas.service.acceso.PasswordService;
import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.beas.login.PreguntaSecreta;
import com.afirme.afirmenet.model.Login;
import com.afirme.afirmenet.service.contrato.ContratoService;
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
	
	
	@RequestMapping(value = "/valida_contrato.htm", method = RequestMethod.POST)
	public String validaContrato(Login login,ModelMap modelMap) {
		
		Boolean flag = otpService.usaTokens(login.getContrato());
		LOG.info(">> validaContrato()");
		LOG.info("<< validaContrato()");
		
		return null;
	}

	@RequestMapping(value = "/sincroniza.htm", method = RequestMethod.POST)
	public String sincroniza(@ModelAttribute("login") Login login, ModelMap modelMap) {

		modelMap.put("sincronizacion", login);
		LOG.info(">> sincroniza()");
		LOG.info("<< sincroniza()");
		return null;
	}

	@RequestMapping(value = "/sincronizacion_token", method = RequestMethod.POST)
	public void sincronizacionToken(@ModelAttribute("sincronizacion") Login login, ModelMap modelMap) {
		
		mailService.sendAlertaSincrTok(mail, login.getContrato(), login.getSerialToken());
		LOG.info(">> sincronizacionToken()");
		LOG.info("<< sincronizacionToken()");
	}
	
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
		LOG.info(">> sincronizacionToken()");
		LOG.info("<< sincronizacionToken()");
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
		
		// TODO: validacion de sesion
		String pregunta = request.getParameter("pregunta") != null ? request.getParameter("pregunta") : "";
		String respuesta = request.getParameter("respuesta") != null ? request.getParameter("respuesta") : "";
		String confirmacion = request.getParameter("confirmar") != null ? request.getParameter("confirmar") : "";
		// se extrae el id de pregunta seleccionado, si no hubo seleccion, se
		// maneja el error para no terminar la app
		LOG.info(">> guardaPreguntaSecreta()");
		LOG.info("<< guardaPreguntaSecreta()");
		return null;
	}
	
	
}
