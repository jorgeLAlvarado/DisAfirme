package com.afirme.afirmenet.web.controller.acceso;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.model.Login;
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
	
	
	@RequestMapping(value = "/validaContrato.htm", method = RequestMethod.POST)
	public String validaContrato(Login login,ModelMap modelMap) {
		
		return null;
	}

	@RequestMapping(value = "/sincroniza.htm", method = RequestMethod.POST)
	public String sincroniza(@ModelAttribute("login") Login login, ModelMap modelMap) {
		
		return null;
	}

	@RequestMapping(value = "/sincronizacionToken", method = RequestMethod.POST)
	public void sincronizacionToken(@ModelAttribute("sincronizacion") Login login, ModelMap modelMap) {
		
	}
	
	/**
	 * M�todo que valida la informaci�n de Contrato y verifica el estatus del Token.
	 * Si la informaci�n es correcta redirige a 'contratoBanca', de lo contrario, permanece en 'activaToken'
	 * @param login
	 * @param modelMap
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/validaUsrActivacion.htm", method = RequestMethod.POST)
	public String validaUsuarioActivacion(@ModelAttribute("activacion") Login login, ModelMap modelMap, HttpServletRequest request) {
		
		return null;
	}
	
	/**
	 * M�todo que genera las lista de preguntas secretas del sistema y eval�a aquellas
	 * ya usadas por el contrato para desplegar la informaci�n en 'preguntaSeguridad' 
	 * @param login
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/preguntaSecreta.htm", method = RequestMethod.POST)
	public String preguntaSecreta(@ModelAttribute("activacion") Login login, ModelMap modelMap) {
		
		return null;
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
	
	@RequestMapping(value = "/guardaPregSecreta.htm", method = RequestMethod.POST)
	public String guardaPreguntaSecreta(@ModelAttribute("activacion") Login login, 
			ModelMap modelMap, HttpServletRequest request, RedirectAttributes redirect) {
		
		return null;
	}
	
	@RequestMapping(value = "/establecePwd.htm", method = RequestMethod.POST)
	public String establecePassword(@ModelAttribute("login") Login login, 
			ModelMap modelMap, HttpServletRequest request) {
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/activacionToken.htm", method = RequestMethod.POST)
	public String activacionToken(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/activacion.htm", method = RequestMethod.POST)
	public String activacion(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		
	}
	
	@RequestMapping(value = "/recuperaPwd.htm", method = RequestMethod.POST)
	public String mostrarRecuperacionPwd(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletResponse resp) throws IOException {
	}
	
	@RequestMapping(value = "/validaDatosRec.htm", method = RequestMethod.POST)
	public String validaDatosRecuperacion(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletResponse resp) throws IOException {
	
		return null;
	}
	
	@RequestMapping(value = "/validaRespuesta.htm", method = RequestMethod.POST)
	public String validaRespuesta(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		
			return null;
		}
	}
	
	@RequestMapping(value = "/alias.htm", method = RequestMethod.POST)
	public String capturaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		

		return null;
	}
	
	@RequestMapping(value = "/aliasConfirma.htm", method = RequestMethod.POST)
	public String confirmaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		
			return null;
		}
	}
	
	@RequestMapping(value = "/aliasComprobante.htm", method = RequestMethod.POST)
	public String comprobanteAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		
			return null;
		}
	}
	
	@RequestMapping(value = "/cancelar.htm", method = RequestMethod.POST)
	public String cancelar(@ModelAttribute("login") Login login, ModelMap modelMap,HttpServletResponse resp, HttpServletRequest request) throws IOException {
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean validaClaveDinamica(Login login, ModelMap modelMap) {
		
		
	}
}
