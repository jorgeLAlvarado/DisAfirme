package com.afirme.afirmenet.web.controller.acceso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.beans.Contrato;
import com.afirme.afirmenet.ibs.beans.JBAvatar;
import com.afirme.afirmenet.ibs.beans.PreguntaSecreta;
import com.afirme.afirmenet.ibs.objects.JOEncrypt;
import com.afirme.afirmenet.service.acceso.LogInService;
import com.afirme.afirmenet.service.acceso.PasswordService;
import com.afirme.afirmenet.service.acceso.PreguntaSecretaService;
import com.afirme.afirmenet.service.acceso.UserService;
import com.afirme.afirmenet.service.contrato.ContratoService;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.base.BaseController;
import com.afirme.afirmenet.web.model.Login;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

@Controller
@SessionAttributes({"acceso","pathAvatar","intentosToken"})
@RequestMapping("/controlAccesoBasico")
public class ControlAccesoBasico  extends BaseController{
	
	static final AfirmeNetLog LOG = new AfirmeNetLog(ControlAccesoBasico.class);
	
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
		LOG.info("Atendiendo Peticion = /controlAccesoBasico/validaUsrActivacion.htm");
				
		Contrato contrato = contratoService.getDatosContrato(login.getContrato());
		
		if (contrato.getSTS().equals("")) {
			LOG.error("Error: No existe información relacionada con este contrato.");
		}
		else if(!contrato.getSTS().equals("4")) {
			LOG.error("Error: Para poder efectuar la activación, su contrato, debe estar con el estatus pendiente por activar.");
		}
		else {
			// validacion de paquete sin token.
			// se extrae el valor de contratos sin token de la clase de constantes y se verifica 
			// si el contrato pertenece a alguno de ellos.
			StringTokenizer paqSinToken = new StringTokenizer(
					AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.GENERAL_PAQ_SIN_TOKEN, String.class), ",");
			
			if (paqSinToken != null) {
				while (paqSinToken.hasMoreTokens()) {
					if (contrato.getGRPID() == Integer.valueOf(paqSinToken.nextToken().trim())) {
						login.setPaqSinToken(true);
						break;
					}
				}
			}
			
			LOG.debug("Número de Activación Token: " + login.getCodigoActivacion());
			
			if (login.getCodigoActivacion() != null && !login.getCodigoActivacion().equals("")) {

					// validacion de password en AS400 por primera vez
					String status = passwordService.validaPassword(login.getContrato(), login.getCodigoActivacion());
					
					if (status != null) {
						LOG.debug("Código de Estatus de Contrato: " + status);
						if(status.equals("9") || status.equals("5") || status.equals("0") || status.equals("6") || status.equals("")) {
							if (request.getParameter("regeneracion") != null) {
								LOG.debug("Solicitud de Regeneracion de Contraseña. Se redirecciona a Pregunta de Seguridad.");
								modelMap.put("regeneracion", true);
								modelMap.put("activacion", login);
								return preguntaSecreta(login, modelMap);
							} else {
								LOG.debug("Paquete con Token. Se redirecciona a Visualizacion de Contrato.");
								modelMap.put("activacion", login);
								return AfirmeNetWebConstants.MV_CONTRATO_BASICO;
							}
							
						} else {
							LOG.error("Error: La información proporcionada no es correcta");
							modelMap.put("error", "La información proporcionada no es correcta");
							modelMap.put("activacion", login);
						}
					}

			} else {
				modelMap.put("error", getMessage("afirmenet.activacion.incorrecto"));
				LOG.error("Error: La información proporcionada no es correcta");
			}
		}
		
		if (request.getParameter("regeneracion") != null)
			modelMap.put("regeneracion", true);
		
		modelMap.put("activacion", login);
		return AfirmeNetWebConstants.MV_ACTIVA_CONTRATO_BASICO;
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
		LOG.info("Atendiendo Peticion = /controlAccesoBasico/preguntaSecreta.htm");
		
		// extrae lista de preguntas del sistema
		ArrayList<PreguntaSecreta> listadoPreguntas = (ArrayList<PreguntaSecreta>) preguntaService.getListadoPreguntas();
		// extrae lista de preguntas utilizadas en el contrato
		HashMap<String, String> preguntasContrato = (HashMap<String, String>) preguntaService.getPregUsadas(login.getContrato());
		
		ArrayList<PreguntaSecreta> preguntasContestadas = new ArrayList<PreguntaSecreta>();
		
		for (PreguntaSecreta preguntaSecreta : listadoPreguntas) {
			if (preguntasContrato.containsKey(preguntaSecreta.getIdPregunta())) {
				preguntasContestadas.add(preguntaSecreta);
			}
		}
		// se remueven de la lista de preguntas aquellas que ya han sido utilizadas
		listadoPreguntas.removeAll(preguntasContestadas);
				
		modelMap.addAttribute("listadoPreguntas", listadoPreguntas);
		modelMap.put("activacion", login);
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
	@RequestMapping(value = "/guardaPregSecreta.htm", method = RequestMethod.POST)
	public String guardaPreguntaSecreta(@ModelAttribute("activacion") Login login, 
			ModelMap modelMap, HttpServletRequest request, RedirectAttributes redirect) {
		LOG.info("Atendiendo Peticion = /controlAccesoBasico/guardaPregSecreta.htm");
		
		// TODO: validacion de sesion
		String pregunta = request.getParameter("pregunta") != null ? request.getParameter("pregunta") : "";
		String respuesta = request.getParameter("respuesta") != null ? request.getParameter("respuesta") : "";
		String confirmacion = request.getParameter("confirmar") != null ? request.getParameter("confirmar") : "";
		// se extrae el id de pregunta seleccionado, si no hubo seleccion, se maneja el error para no terminar la app
		Integer idPregunta = null;
		try {
			idPregunta = Integer.valueOf(pregunta);
		} catch (NumberFormatException numberex) {
			pregunta = "";
		}
		// valida pregunta, respuesta y confirmacion no sean nulos; respuesta y confirmacion deben ser equivalentes
		if(!StringUtils.isEmpty(pregunta) && !StringUtils.isEmpty(respuesta) && !StringUtils.isEmpty(confirmacion)) {
			if (respuesta.equalsIgnoreCase(confirmacion)) {
				
				ArrayList<PreguntaSecreta> listadoPreguntas = (ArrayList<PreguntaSecreta>) preguntaService.getListadoPreguntas();
				
				for (PreguntaSecreta preguntaSecreta : listadoPreguntas) {
					if (preguntaSecreta.getIdPregunta().trim().equals(pregunta)) {
						pregunta = preguntaSecreta.getPregunta().toUpperCase();
						break;
					}
				}
				
				// se guarda la pregunta como seleccion histórica del contrato 
				boolean resultado = preguntaService.setPreguntaPersSecreta(login.getContrato(), idPregunta, pregunta, respuesta);
				
				if (resultado) {
					/* se almacena pregunta como usada en el contrato.
					 * en este punto, el ID de la pregunta seleccionada no debe existir en la BD al momento de hacer la inserción */
					preguntaService.guardaPreguntaUsada(login.getContrato(), idPregunta);
					
					if (request.getParameter("regeneracion") != null) {
						modelMap.put("regeneracion", true);
					}
					
					modelMap.put("activacion", login);
					return AfirmeNetWebConstants.MV_ASIGNA_PWD_BASICO;
				} else {
					modelMap.put("error", "No se pudo grabar la respuesta a la pregunta secreta, intente de nuevo");
					LOG.error("Error: No se pudo grabar la respuesta a la pregunta secreta, intente de nuevo");
				}
			}
			else {
				modelMap.put("error", "La respuesta y confirmación no coinciden.");
				LOG.error("Error: La respuesta y confirmación no coinciden.");
			}
		}
		else {
			modelMap.put("error", "Ni la pregunta, ni la respuesta deben de quedar en blanco");
			LOG.error("Error: Ni la pregunta, ni la respuesta deben de quedar en blanco");
		} 
		
		modelMap.put("activacion", login);
		return preguntaSecreta(login, modelMap);
	}
	
	@RequestMapping(value = "/establecePwd.htm", method = RequestMethod.POST)
	public String establecePassword(@ModelAttribute("login") Login login, 
			ModelMap modelMap, HttpServletRequest request) {
		LOG.info("Atendiendo Peticion = /controlAccesoBasico/establecePwd.htm");
		// TODO validacion de sesion
		if (login != null && login.getContrato() != null) {
			String password = request.getParameter("password") != null ? request.getParameter("password").trim().toUpperCase() : "";
			String confirma = request.getParameter("confirma") != null ? request.getParameter("confirma").trim().toUpperCase() : "";
			
			if(!StringUtils.isEmpty(password) && !StringUtils.isEmpty(confirma)) {
				if (password.equalsIgnoreCase(confirma)) {
					
					if (!passwordService.setPassword(login.getContrato(), password)) {
						LOG.error("Error: No se pudo cambiar su password, intente de nuevo.");
					} else {
						
							// Si el flujo es Regeneracion de Contraseña, actualiza estatus de Contrato y redirige a Login
							if (request.getParameter("regeneracion") != null) {								
								modelMap.put("regeneracion", true);
								modelMap.put("activacion", login);
								
								if (contratoService.setStatus(login.getContrato(), "1")) {
									LOG.debug("Cambio de Estatus para el Contrato: " + login.getContrato() + " exitoso.");
									modelMap.put("regeneracionExito", true);
									return AfirmeNetWebConstants.MV_ASIGNA_PWD_BASICO;
								} else {
									LOG.debug("Error: Su contrato no pudo ser activado.");
									return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
								}
							} else {
								// Si el flujo es Activacion, redirige a Alias
								modelMap.put("activacion", login);
								return AfirmeNetWebConstants.MV_LOGIN_ALIAS_BASICO;
							}
					}
				} else {
					LOG.debug("Password y Confirmacion no son equivalentes.");
				}
			} else {
				LOG.debug("Password o Confirmacion no se ha definido.");
			}
		}
		
		return establecePassword(login, modelMap, request);
	}
	
	@RequestMapping(value = "/alias.htm", method = RequestMethod.POST)
	public String capturaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		LOG.info("Atendiendo Peticion = /controlAccesoBasico/alias.htm");
		
		String alias = request.getParameter("alias") == null ? "" : request.getParameter("alias");
		login.setAlias(alias);
		
		if (request.getParameter("activacion") != null)
			modelMap.addAttribute("activacion", true);
		
		ArrayList<JBAvatar> lstAvatar = (ArrayList<JBAvatar>) logInService.getListAvatar();
		modelMap.addAttribute("lstAvatar", lstAvatar);
		
		modelMap.addAttribute("pathAvatar", AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH));		
		modelMap.addAttribute("login", login);

		return AfirmeNetWebConstants.MV_LOGIN_AVATAR_BASICO;
	}
	
	@RequestMapping(value = "/aliasConfirma.htm", method = RequestMethod.POST)
	public String confirmaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		LOG.info("Atendiendo Peticion = /controlAccesoBasico/aliasConfirma.htm");
		// Verifica si usa tokens o no
		boolean usaTok = false;
		
		if (!usaTok){
			login.setbPaqueteSinToken(true);
			login.setGrpId(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.GENERAL_PAQ_SIN_TOKEN, String.class));
		}
		
		String avatar = request.getParameter("avatar") == null ? "" : request.getParameter("avatar");
		login.setAvatar(avatar);
			
		
		if (contratoService.setStatus(login.getContrato(), "1")) {
			LOG.debug("Activación de Contrato exitosa.");
			
			modelMap.addAttribute("exito", true);
			modelMap.addAttribute("pathAvatar", AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH));		
			userService.actualizarAliasLogin(login.getContrato(), login.getAlias(), login.getAvatar());
			String pathAvatar = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH) + login.getAvatar() + ".jpg";
			login.setAvatar(pathAvatar);
			modelMap.addAttribute("login", login);
			return AfirmeNetWebConstants.MV_LOGIN_ACTIVA_CONTRATO_CONFIRMA_BASICO;
		} else {
			modelMap.addAttribute("error", "Su contrato no pudo ser activado.");
			LOG.error("Error: Su contrato no pudo ser activado.");
			return AfirmeNetWebConstants.MV_ACTIVA_TOKEN;
		}		
	}	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/activacion.htm", method = RequestMethod.POST)
	public String activacion(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		LOG.info("Atendiendo Peticion = /controlAccesoBasico/activacion.htm");
		modelMap.addAttribute("login", login);
		modelMap.addAttribute("basicoSinToken", true);
		return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
	}
	
	@RequestMapping(value = "/validaRespuesta.htm", method = RequestMethod.POST)
	public String validaRespuesta(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		LOG.info("Atendiendo Peticion = /controlAccesoBasico/validaRespuesta.htm");
		
		//OJOS
		login.setAvatar(((Login)modelMap.get("acceso")).getAvatar());
		login.setAlias(((Login)modelMap.get("acceso")).getAlias());
		
		String respuesta = request.getParameter("respuesta") == null ? "" : request.getParameter("respuesta");
		ArrayList<String> datosPregunta = (ArrayList<String>) preguntaService.getPregunta(login.getContrato());
		
		// encripta la respuesta recibida por request para validarla con la respuesta en la BD
		JOEncrypt encbean = new JOEncrypt();
		String respEncrip = encbean.getEncrypt(respuesta.toUpperCase());
		
		LOG.debug("Parametros de envio:");
		LOG.debug("idContrato: " + datosPregunta.get(2));
		LOG.debug("idPregunta: " + datosPregunta.get(0));
		
		String respEncripBD = preguntaService.getPreguntaPersSecretaEncrypt(datosPregunta.get(2), Integer.valueOf(datosPregunta.get(0)));
		
		LOG.debug("Respuesta: " + respuesta.toUpperCase());
		LOG.debug("Respuesta encriptada: " + respEncrip);
		LOG.debug("Respuesta encriptada de BD: " + respEncripBD);
		
		if(respEncrip.equals(respEncripBD)) {
			// envia correo de generacion de nuevo codigo de seguridad
			if (passwordService.enviaMailCodigoSeguridad(login.getContrato())) {
				LOG.debug("Recuperacion de Password exitosa.");
				modelMap.addAttribute("exito", true);
				modelMap.addAttribute("acceso", login);
				// regresa a la pagina de pregunta de seguridad, con el mensaje de envio de correo exitoso
				return AfirmeNetWebConstants.MV_RECUPERA_PREG_SEGU;
			} else {
				LOG.error("Error al realizar el envio del nuevo código de seguridad.");
				return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
			}
		} else {
			String error = "Su respuesta no es correcta, intente de nuevo.";
			LOG.error("Error: " + error);
			modelMap.addAttribute("acceso", login);
			modelMap.addAttribute("datosPreg", datosPregunta);
			modelMap.addAttribute("errores", error);
			return AfirmeNetWebConstants.MV_RECUPERA_PREG_SEGU;
		}
	}
	
}
