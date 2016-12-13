package com.afirme.afirmenet.web.controller.acceso;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.afirme.afirmenet.utils.AfirmeNetLog;

/**
 * clase para implementar la contraseña
 *  * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */

@Controller
@SessionAttributes({"acceso","pathAvatar","intentosToken"})
@RequestMapping("/controlAccesoBasico")
public class ControlAccesoBasico  extends BaseController{
	
	static final AfirmeNetLog LOG = new AfirmeNetLog(ControlAccesoBasico.class);
	/*
	@Autowired
	private ContratoService contratoService;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private PreguntaSecretaService preguntaService;
	
	@Autowired
	private LogInService logInService;
	
	@Autowired
	private UserService userService;
	*/
	
	/**
	 * Método que valida la información de Contrato y verifica el estatus del Token.
	 * Si la información es correcta redirige a 'contratoBanca', de lo contrario, permanece en 'activaToken'
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
	 * Método que genera las lista de preguntas secretas del sistema y evalúa aquellas
	 * ya usadas por el contrato para desplegar la información en 'preguntaSeguridad' 
	 * @param login
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/preguntaSecreta.htm", method = RequestMethod.POST)
	public String preguntaSecreta(@ModelAttribute("activacion") Login login, ModelMap modelMap) {
		
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
	
	@RequestMapping(value = "/alias.htm", method = RequestMethod.POST)
	public String capturaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		
		return null;
	}
	
	@RequestMapping(value = "/aliasConfirma.htm", method = RequestMethod.POST)
	public String confirmaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		
			return null;
		}		
	}	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/activacion.htm", method = RequestMethod.POST)
	public String activacion(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		
		return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
	}
	
	@RequestMapping(value = "/validaRespuesta.htm", method = RequestMethod.POST)
	public String validaRespuesta(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		
			return null;
		}
	}
	
}
