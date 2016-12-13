package com.afirme.afirmenet.web.controller.acceso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
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
import com.afirme.afirmenet.service.acceso.OTPAdminService;
import com.afirme.afirmenet.service.acceso.OTPAgenteService;
import com.afirme.afirmenet.service.acceso.OTPService;
import com.afirme.afirmenet.service.acceso.PasswordService;
import com.afirme.afirmenet.service.acceso.PreguntaSecretaService;
import com.afirme.afirmenet.service.acceso.UserService;
import com.afirme.afirmenet.service.contrato.ContratoService;
import com.afirme.afirmenet.service.mail.MailService;
import com.afirme.afirmenet.service.token.TokenService;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.time.TimeUtils;
import com.afirme.afirmenet.web.controller.base.BaseController;
import com.afirme.afirmenet.web.model.AfirmeNetUser;
import com.afirme.afirmenet.web.model.Login;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

@Controller
@SessionAttributes({"acceso","pathAvatar","intentosToken"})
@RequestMapping("/controlAcceso")
public class ControlAcceso extends BaseController{

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
	public String validaContrato(@ModelAttribute("login") Login login,ModelMap modelMap) {
		LOG.info("Atendiendo Peticion = /controlAcceso/validaContrato.htm");
		
		Boolean flag = otpService.usaTokens(login.getContrato());
		LOG.debug("Consulta Retorno = " + flag);
		modelMap.put("aviso", login);
		return AfirmeNetWebConstants.MV_LOGIN_AVISO_SEGURIDAD;
	}

	@RequestMapping(value = "/sincroniza.htm", method = RequestMethod.POST)
	public String sincroniza(@ModelAttribute("login") Login login, 
			ModelMap modelMap) {
		LOG.info("Atendiendo Peticion = /controlAcceso/sincroniza.htm");
		
		modelMap.put("sincronizacion", login);
		return AfirmeNetWebConstants.MV_SYNC_TOKEN;
	}

	@RequestMapping(value = "/sincronizacionToken", method = RequestMethod.POST)
	public void sincronizacionToken(@ModelAttribute("sincronizacion") Login login, ModelMap modelMap) {
		LOG.info("Atendiendo Peticion = /controlAcceso/sincronizacionToken.htm");

		LOG.debug(login.getContrato());
		LOG.debug(login.getSerialToken());
		LOG.debug(login.getCodigoToken());
		LOG.debug(login.getCodigoToken2());
		
		ArrayList<Object> result = (ArrayList<Object>) otpAdminService.sincronizaToken(
			login.getSerialToken(),
			login.getCodigoToken(),
			login.getCodigoToken2());

		if (((Boolean) result.get(0)).booleanValue() == true) {
			
			LOG.debug("Sincronización exitosa.");
			String mail = userService.getMailUsuario(login.getContrato());
			
			try {
				mailService.sendAlertaSincrTok(mail, login.getContrato(), login.getSerialToken());
			} catch (MessagingException e) {
				e.printStackTrace();
			}

 			modelMap.put("acceso", login);
			
		} else {
			LOG.error("Error en Sincronización.");
			modelMap.addAttribute("errores", result.get(2));
			modelMap.put("sincronizacion", login);
		}
	}
	
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
		LOG.info("Atendiendo Peticion = /controlAcceso/validaUsrActivacion.htm");
				
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
			
			LOG.debug("Número de Serie Token: " + login.getSerialToken());
			LOG.debug("Número de Activación Token: " + login.getCodigoActivacion());
			
			if (login.getSerialToken() != null && !login.getSerialToken().equals("") 
					&& login.getCodigoActivacion() != null && !login.getCodigoActivacion().equals("")) {
				
				if (login.isPaqSinToken()) {
					// validacion de password en AS400 por primera vez
					String status = passwordService.validaPassword(login.getContrato(), login.getCodigoToken());
					
					if (status != null) {
						LOG.debug("Código de Estatus de Contrato: " + status);
						if(status.equals("9") || status.equals("5") || status.equals("0") || status.equals("6") || status.equals("")) {
							// se cargan preguntas secretas generales
							
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
							
							LOG.debug("Validación completada. Se redirecciona a preguntas secretas.");
							modelMap.put("activacion", login);
							return preguntaSecreta(login, modelMap);
							
						} else {
							LOG.error("Error: La información proporcionada no es correcta");
							modelMap.put("error", "La información proporcionada no es correcta");
							modelMap.put("activacion", login);
							return AfirmeNetWebConstants.MV_ACTIVA_CONTRATO;
						}
					}
				}
				else {
					// paquete requiere token
					try {
						if (otpService.validaTokenXActivar(
								login.getContrato(), 
								login.getContrato(), 
								Integer.valueOf(login.getSerialToken()), 
								login.getCodigoActivacion())) {
							
							if (request.getParameter("regeneracion") != null) {
								LOG.debug("Solicitud de Regeneracion de Contraseña. Se redirecciona a Pregunta de Seguridad.");
								modelMap.put("regeneracion", true);
								modelMap.put("activacion", login);
								return preguntaSecreta(login, modelMap);
							} else {
								LOG.debug("Paquete con Token. Se redirecciona a Visualizacion de Contrato.");
								modelMap.put("activacion", login);
								return AfirmeNetWebConstants.MV_CONTRATO;
							}
						}
						else {
							modelMap.put("error", getMessage("afirmenet.activacion.incorrecto"));
							LOG.error("Error: La información proporcionada no es correcta");
						}
					} catch (Exception e) {
						modelMap.put("error", getMessage("afirmenet.activacion.incorrecto"));
						LOG.error("Error: La información proporcionada no es correcta");
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
		return AfirmeNetWebConstants.MV_ACTIVA_CONTRATO;
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
		LOG.info("Atendiendo Peticion = /controlAcceso/preguntaSecreta.htm");
		
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
		return AfirmeNetWebConstants.MV_PREG_SEGURIDAD;
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
		LOG.info("Atendiendo Peticion = /controlAcceso/guardaPregSecreta.htm");
		
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
					// TODO qué hacer con ésta validación?
					// valida paquete sin token
					if (login.isPaqSinToken()) {
						// sin token
						LOG.debug("Paquete sin Token");
						// TODO redireccionar?
					} else {
						// con token
						LOG.debug("Paquete con Token");
						// TODO redireccionar?
					}
					
					if (request.getParameter("regeneracion") != null) {
						modelMap.put("regeneracion", true);
					}
					
					modelMap.put("activacion", login);
					return AfirmeNetWebConstants.MV_ASIGNA_PWD;
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
		LOG.info("Atendiendo Peticion = /controlAcceso/establecePwd.htm");
		// TODO validacion de sesion
		if (login != null && login.getContrato() != null) {
			String password = request.getParameter("password") != null ? request.getParameter("password").trim().toUpperCase() : "";
			String confirma = request.getParameter("confirma") != null ? request.getParameter("confirma").trim().toUpperCase() : "";
			
			if(!StringUtils.isEmpty(password) && !StringUtils.isEmpty(confirma)) {
				if (password.equalsIgnoreCase(confirma)) {
					
					LOG.debug("Firma Digital completada");
					
					if (!passwordService.setPassword(login.getContrato(), password)) {
						LOG.error("Error: No se pudo cambiar su password, intente de nuevo.");
					} else {
						if (login.isPaqSinToken()) { 
							// paquete no requiere Token
							if (contratoService.setStatus(login.getContrato(), "1")) {
								LOG.debug("Cambio de Estatus para el Contrato: " + login.getContrato() + " exitoso.");
								return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
							} else {
								LOG.debug("Error: Su contrato no pudo ser activado.");
								return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
							}
						} else {
							//Activa el Token
							List<Object> activacion = (List<Object>) otpAdminService.habilitaToken(login.getSerialToken());
							if (((Boolean) activacion.get(0)).booleanValue()) {
								// Paquete que cuenta con Token.
								// Si el flujo es Regeneracion de Contraseña, actualiza estatus de Contrato y redirige a Login
								if (request.getParameter("regeneracion") != null && request.getParameter("codigoToken") != null) {
									
									modelMap.put("regeneracion", true);
									modelMap.put("activacion", login);
									
									if (validaClaveDinamica(login, modelMap)) {
										if (contratoService.setStatus(login.getContrato(), "1")) {
											LOG.debug("Regeneración de Contraseña exitosa.");
											modelMap.put("regeneracionExito", true);
											return AfirmeNetWebConstants.MV_ASIGNA_PWD;
										} else {
											modelMap.addAttribute("error", "Su contraseña no pudo ser reactivada.");
											LOG.error("Error: Contraseña no pudo ser reactivada.");
											return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
										}
									} else {
										return AfirmeNetWebConstants.MV_ASIGNA_PWD;
									}
								} else {
									// Si el flujo es Activacion, redirige a Alias
									modelMap.put("activacion", login);
									return AfirmeNetWebConstants.MV_LOGIN_ALIAS;
								}
							
							}else{
								modelMap.addAttribute("error", activacion.get(2));
								modelMap.addAttribute("exito", false);
								
							}
							
						}
					}
				} else {
					LOG.debug("Password y Confirmacion no son equivalentes.");
				}
			} else {
				LOG.debug("Password o Confirmacion no se ha definido.");
			}
		}
		LOG.info("Se redirecciona a home para mostrarle error de session.");
		return AfirmeNetWebConstants.MV_HOME;
		//return establecePassword(login, modelMap, request);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/activacionToken.htm", method = RequestMethod.POST)
	public String activacionToken(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		LOG.info("Atendiendo Peticion = /controlAcceso/activacionToken.htm");
		
		if (login.getCodigoToken() != null && !login.getCodigoToken().equals("") && login.getCodigoToken2() != null && !login.getCodigoToken2().equals("")) {
			List<Object> activacion = (List<Object>) otpAdminService.habilitaToken(login.getSerialToken());
			// activación de Token
			if (((Boolean) activacion.get(0)).booleanValue()) {
				
				List<Object> sincronizacion = (List<Object>) otpAdminService.sincronizaToken(
						login.getSerialToken(), 
						login.getCodigoToken(), 
						login.getCodigoToken2());
				// sincronización de TOken
				if (((Boolean) sincronizacion.get(0)).booleanValue()) {

					List<Object> infoToken = (List<Object>) otpAdminService.getInformacionToken(login.getSerialToken());
					
					if (((Boolean) infoToken.get(0)).booleanValue()) {
						
						if (infoToken.get(3) != null) {
							
							List tokenInfo = (List) infoToken.get(3);
							String fechaVencimiento = tokenInfo.get(6).toString().trim();
							LOG.debug("Fecha de Vencimiento: " + fechaVencimiento);
							
							if (fechaVencimiento.length() == 10) {
								
								fechaVencimiento = fechaVencimiento.substring(6, 10) + 
										fechaVencimiento.substring(0, 2) + 
										fechaVencimiento.substring(3, 5);
								// actualización de fecha de vencimiento de Token
								if (otpService.setFechaVencimiento(login.getSerialToken(), fechaVencimiento)) {
									// cambio de estatus de Contrato
									if (contratoService.setStatus(login.getContrato(), "1")) {
										LOG.debug("Activación de Contrato exitosa.");
										
										modelMap.addAttribute("exito", true);
										
										Contrato contrato = contratoService.getDatosContrato(login.getContrato());
//										if (contrato.getEUSALIAS().equals("")) {
//											userService.actualizarAliasLogin(login.getContrato(), login.getAlias(), login.getAvatar());
//										} else {
//											if(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.IR_PORTAL , String.class).equals("Y")){
//												resp.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.PORTAL_AFIRME , String.class));
//											}
//										}
										userService.actualizarAliasLogin(login.getContrato(), login.getAlias(), login.getAvatar());
										String pathAvatar = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH) + login.getAvatar() + ".jpg";
										login.setAvatar(pathAvatar);
										modelMap.addAttribute("login", login);
										//return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
										return AfirmeNetWebConstants.MV_LOGIN_ACTIVA_CONTRATO_CONFIRMA;
									} else {
										modelMap.addAttribute("error", "Su contrato no pudo ser activado.");
										LOG.error("Error: Su contrato no pudo ser activado.");
										return AfirmeNetWebConstants.MV_ACTIVA_TOKEN;
									}
								}
							}
						}
					}else{
						modelMap.addAttribute("error", infoToken.get(2));
						modelMap.addAttribute("exito", false);
						modelMap.addAttribute("activacion", login);
						return AfirmeNetWebConstants.MV_ACTIVA_TOKEN;
					}
				}else{
					modelMap.addAttribute("error", sincronizacion.get(2));
					modelMap.addAttribute("exito", false);
					modelMap.addAttribute("activacion", login);
					return AfirmeNetWebConstants.MV_ACTIVA_TOKEN;
				}
			}else{
				modelMap.addAttribute("error", activacion.get(2));
				modelMap.addAttribute("exito", false);
				modelMap.addAttribute("activacion", login);
				return AfirmeNetWebConstants.MV_ACTIVA_TOKEN;
			}
		}	
		
		if(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.IR_PORTAL , String.class).equals("Y"))
			resp.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.PORTAL_AFIRME , String.class));
		return AfirmeNetWebConstants.MV_LOGIN;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/activacion.htm", method = RequestMethod.POST)
	public String activacion(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		LOG.info("Atendiendo Peticion = /controlAcceso/activacion.htm");
		modelMap.addAttribute("login", login);
		return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
	}
	
	@RequestMapping(value = "/recuperaPwd.htm", method = RequestMethod.POST)
	public String mostrarRecuperacionPwd(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletResponse resp) throws IOException {
		LOG.info("Atendiendo Peticion = /controlAcceso/recuperaPwd.htm");
		
		Contrato contrato = contratoService.getDatosContrato(login.getContrato());
		
		if (contrato == null) {
			LOG.debug("No existen datos de Contrato.");
			if(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.IR_PORTAL , String.class).equals("Y"))
				resp.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.PORTAL_AFIRME , String.class));
			
			return AfirmeNetWebConstants.MV_LOGIN;
		} else {
			Boolean usaToken = otpService.usaTokens(login.getContrato());
			// verifica si el contrato tiene asociado un Token 
			if (usaToken) {				
				login.setAlias(contrato.getEUSALIAS());
				modelMap.put("acceso", login);
				return AfirmeNetWebConstants.MV_RECUPERA_PWD;
			}else{
				login.setAlias(contrato.getEUSALIAS());
				modelMap.put("acceso", login);
				ArrayList<String> datosPregunta = (ArrayList<String>) preguntaService.getPregunta(login.getContrato());
				
				if (((String) datosPregunta.get(1)).equals("")) {
					LOG.error("Error: Ocurrio un error al extraer las preguntas del servidor.");
					modelMap.addAttribute("errores", "Ocurrió un error al extraer la información de Preguntas Secretas.");
					return AfirmeNetWebConstants.MV_RECUPERA_PWD_BASICO;
				} else {
					LOG.debug("Redireccionamiento a Pregunta de Seguridad.");
					modelMap.addAttribute("acceso", login);
					modelMap.addAttribute("datosPreg", datosPregunta);
					return AfirmeNetWebConstants.MV_RECUPERA_PWD_BASICO;
				}
			}
		}
		
		//return mostrarRecuperacionPwd(login, modelMap, resp);
	}
	
	@RequestMapping(value = "/validaDatosRec.htm", method = RequestMethod.POST)
	public String validaDatosRecuperacion(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletResponse resp) throws IOException {
		LOG.info("Atendiendo Peticion = /controlAcceso/validaDatosRec.htm");
		
		login.setAvatar(((Login)modelMap.get("acceso")).getAvatar());
		login.setAlias(((Login)modelMap.get("acceso")).getAlias());
		
		Contrato contrato = contratoService.getDatosContrato(login.getContrato());
		
		if (contrato == null) {
			LOG.error("Error: No existe información relacionada con este contrato.");
			if (AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.IR_PORTAL , String.class).equals("Y"))
				resp.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.PORTAL_AFIRME , String.class));
			return AfirmeNetWebConstants.MV_LOGIN;
			
		} else {
			// se valida la clave dinamica
			if (validaClaveDinamica(login, modelMap)) {
				
				ArrayList<String> datosPregunta = (ArrayList<String>) preguntaService.getPregunta(login.getContrato());
				
				if (((String) datosPregunta.get(1)).equals("")) {
					LOG.error("Error: Ocurrio un error al extraer las preguntas del servidor.");
					modelMap.addAttribute("errores", "Ocurrió un error al extraer la información de Preguntas Secretas.");
				} else {
					LOG.debug("Redireccionamiento a Pregunta de Seguridad.");
					modelMap.addAttribute("acceso", login);
					modelMap.addAttribute("datosPreg", datosPregunta);
					return AfirmeNetWebConstants.MV_RECUPERA_PREG_SEGU;
				}
			}
		}
		
		login.setAlias(contrato.getEUSALIAS());
		modelMap.put("acceso", login);
		return AfirmeNetWebConstants.MV_RECUPERA_PWD;
	}
	
	@RequestMapping(value = "/validaRespuesta.htm", method = RequestMethod.POST)
	public String validaRespuesta(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		LOG.info("Atendiendo Peticion = /controlAcceso/validaRespuesta.htm");
		
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
			if (otpService.enviaMailCodigoSeguridad(login.getContrato())) {
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
	
	@RequestMapping(value = "/alias.htm", method = RequestMethod.POST)
	public String capturaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		LOG.info("Atendiendo Peticion = /controlAcceso/alias.htm");
		
		String alias = request.getParameter("alias") == null ? "" : request.getParameter("alias");
		login.setAlias(alias);
		
		if (request.getParameter("activacion") != null)
			modelMap.addAttribute("activacion", true);
		
		ArrayList<JBAvatar> lstAvatar = (ArrayList<JBAvatar>) logInService.getListAvatar();
		modelMap.addAttribute("lstAvatar", lstAvatar);
		
		modelMap.addAttribute("pathAvatar", AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH));		
		modelMap.addAttribute("login", login);

		return AfirmeNetWebConstants.MV_LOGIN_AVATAR;
	}
	
	@RequestMapping(value = "/aliasConfirma.htm", method = RequestMethod.POST)
	public String confirmaAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		LOG.info("Atendiendo Peticion = /controlAcceso/aliasConfirma.htm");
		// Verifica si usa tokens o no
		boolean usaTok = otpService.usaTokens(login.getContrato());
		
		if (!usaTok){
			login.setbPaqueteSinToken(true);
			login.setGrpId(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.GENERAL_PAQ_SIN_TOKEN, String.class));
		}
		
		String avatar = request.getParameter("avatar") == null ? "" : request.getParameter("avatar");
		login.setAvatar(avatar);
		
		modelMap.addAttribute("pathAvatar", AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH));		
		
		if (request.getParameter("activacion") != null) {
			modelMap.addAttribute("activacion", login);
			return AfirmeNetWebConstants.MV_ACTIVA_TOKEN;
		} else {
			modelMap.addAttribute("login", login);
			return AfirmeNetWebConstants.MV_LOGIN_ALIAS_CONFIRMA;
		}
	}
	
	@RequestMapping(value = "/aliasComprobante.htm", method = RequestMethod.POST)
	public String comprobanteAlias(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		LOG.info("Atendiendo Peticion = /controlAcceso/aliasComprobante.htm");
	
		AfirmeNetUser afirmeNetUser = new AfirmeNetUser();
		afirmeNetUser.setContrato(login.getContrato());

		/*****************************************************************************************************************************/
		
		// Verifica si usa tokens o no
		boolean usaTok = otpService.usaTokens(login.getContrato());
		ArrayList tokenRSA = new ArrayList();
		tokenRSA.add(new Boolean(usaTok));

		StringTokenizer paqSinToken = new StringTokenizer(
				AfirmeNetConstants.getValorConfigPersonas(
						ConfigPersonas.GENERAL_PAQ_SIN_TOKEN, String.class),
				",");

		boolean bPaqueteSinToken = false;

		if (paqSinToken != null) {
			while (paqSinToken.hasMoreTokens()) {
				if (login.getGrpId().equals(paqSinToken.nextToken().trim())) {
					bPaqueteSinToken = true;
					usaTok = false;
				}
			}
		}

		// GUARDA SERIE DEL TOKEN
		if (usaTok == true && bPaqueteSinToken == false) {
			tokenRSA.add(otpService.obtenToken(login.getContrato()));
		}
		
		afirmeNetUser.setBasicoSinToken(bPaqueteSinToken);
		afirmeNetUser.setTokenRSA(tokenRSA);
		
		/*****************************************************************************************************************************/
		
		if (validaToken(request.getParameter("token"), afirmeNetUser, modelMap)) {
			
			userService.actualizarAliasLogin(login.getContrato(), login.getAlias(), login.getAvatar());
			
			modelMap.addAttribute("login", login);
			String fechaDia = TimeUtils.getDateFormat(new Date(), "dd/MMMM/yyyy HH:mm");
			modelMap.addAttribute("fechaEjecucion", fechaDia);
			modelMap.addAttribute("pathAvatar", AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH));		
			return AfirmeNetWebConstants.MV_LOGIN_ALIAS_CONFIRMA;
			
		} else {
			modelMap.addAttribute("login", login);
			return AfirmeNetWebConstants.MV_LOGIN_ALIAS_CONFIRMA;
		}
	}
	
	@RequestMapping(value = "/cancelar.htm", method = RequestMethod.POST)
	public String cancelar(@ModelAttribute("login") Login login, ModelMap modelMap,HttpServletResponse resp, HttpServletRequest request) throws IOException {
		LOG.info("Atendiendo Peticion = /controlAcceso/cancelar.htm");
		
		if(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.IR_PORTAL , String.class).equals("Y"))
		resp.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.PORTAL_AFIRME , String.class));
		return AfirmeNetWebConstants.MV_LOGIN;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean validaClaveDinamica(Login login, ModelMap modelMap) {
		
		Contrato contrato = contratoService.getDatosContrato(login.getContrato());
		
		// se extrae informacion de Token
		ArrayList<Object> tokenResult = (ArrayList<Object>) otpAdminService.getInformacionToken(otpService.obtenToken(contrato.getENTITYID()));
		
		if (((Boolean) tokenResult.get(0)).booleanValue()) {
			if (tokenResult.get(3) != null) {
				List tokenInfo = (List) tokenResult.get(3);
				
				String deshabilitado = tokenInfo.get(12).toString();
				if (deshabilitado.equals("0")) {
					LOG.debug("Token deshabilitado.");
					modelMap.addAttribute("errores", "Su Token esta deshabilitado, repórtelo al Call Center.");
				}
				String bloqueado = tokenInfo.get(15).toString();
				if (!bloqueado.equals("0")) {
					LOG.debug("Token bloqueado.");
					modelMap.addAttribute("errores", "Su Token se encuentra bloqueado, sincronize su Token para poder usarlo con normalidad.");
				}
			}
		}
		// validacion de codigo de seguridad de Token
		String userRSA = login.getContrato() + "_" + login.getContrato();
		
		ArrayList<Object> rsaResult = (ArrayList<Object>) otpAgenteService.verificaPasscode(userRSA, login.getCodigoToken());
		String errorDesc = rsaResult.get(2).toString();
		
		if (((Boolean) rsaResult.get(0)).booleanValue()) {
			return true;
		} else {
			LOG.error("Error: " + errorDesc);
			modelMap.addAttribute("errores", errorDesc);
			return false;
		}
	}
}
