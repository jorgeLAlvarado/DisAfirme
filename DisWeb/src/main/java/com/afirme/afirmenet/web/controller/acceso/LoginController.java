package com.afirme.afirmenet.web.controller.acceso;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.enums.ConfigLogIn;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.enums.TipoCliente;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.beans.JBBMuser;
import com.afirme.afirmenet.ibs.beans.JBLogList;
import com.afirme.afirmenet.ibs.beans.JBLogin;
import com.afirme.afirmenet.ibs.beans.JBProCode;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.model.base.TokenModel;
import com.afirme.afirmenet.service.FinDiaService;
import com.afirme.afirmenet.service.acceso.CampaniaService;
import com.afirme.afirmenet.service.acceso.LogInService;
import com.afirme.afirmenet.service.acceso.MenuService;
import com.afirme.afirmenet.service.acceso.OTPAdminService;
import com.afirme.afirmenet.service.acceso.OTPAgenteService;
import com.afirme.afirmenet.service.acceso.OTPService;
import com.afirme.afirmenet.service.acceso.UserService;
import com.afirme.afirmenet.service.config.PropertyService;
import com.afirme.afirmenet.service.consultas.CuentaService;
import com.afirme.afirmenet.service.contrato.ContratoService;
import com.afirme.afirmenet.service.log.LogService;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.time.TimeUtils;
import com.afirme.afirmenet.web.controller.base.BaseController;
import com.afirme.afirmenet.web.model.AfirmeNetUser;
import com.afirme.afirmenet.web.model.Login;
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
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	//static final AfirmeNetLog LOG = new AfirmeNetLog(LoginController.class);
	static final AfirmeNetLog LOG = new AfirmeNetLog(LoginController.class);

	@Autowired
	CampaniaService campaniaService;
	@Autowired
	private ContratoService contratoService;
	@Autowired
	private LogInService logInService;
	@Autowired
	private LogService logService;
	@Autowired
	private OTPAdminService oTPAdminService;
	@Autowired
	private OTPAgenteService oTPAgenteService;
	@Autowired
	private OTPService otpService;
	@Autowired
	private FinDiaService finDiaService;
	@Autowired
	private MenuService menuService;
	@Autowired(required = true)
	private CuentaService cuentaService;	
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private UserService userService;

	/**
	 * Si la peticion viene desde el portal el contrato que captura el usuario esta en UserId.
	 * 
	 * @param login viene desde el view login.
	 * @param modelMap atributos de request.
	 * @param request el contrato que captura el usuario esta en UserId.
	 * @return view contrato
	 */
	@RequestMapping(value = "/contrato.htm", method = RequestMethod.POST)
	public String mostrarAvisoSeguridad(@ModelAttribute("login") Login login, ModelMap modelMap, HttpServletRequest request) {
		
		LOG.info("Atendiendo Peticion = "+request.getServletPath());
		String contrato = request.getParameter("UserId") == null ? "" : request.getParameter("UserId").trim();
		if( Util.isBlank( contrato ) == false){
			login.setContrato( contrato );
		}
		
		LOG.debug("Atendiendo Peticion = /login/contrato.htm contrato ===> " + login.getContrato());
		modelMap.addAttribute("login", login);
		
		return AfirmeNetWebConstants.MV_LOGIN_AVISO_SEGURIDAD;
	}

	@RequestMapping(value = "/avisoSeguridad.htm", method = RequestMethod.POST)
	public String mostrarDatosAcceso(@ModelAttribute("login") Login login,ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Atendiendo Peticion = /login/avisoSeguridad.htm contrato ===> " + login.getContrato());
		
		try{
		
			JBLogin loginUser = contratoService.getDatosLogIn(login.getContrato());
	
			boolean basicoSinToken = false;
			
			StringTokenizer paqSinToken = new StringTokenizer(
					AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.GENERAL_PAQ_SIN_TOKEN, String.class), ",");
	
			if (paqSinToken != null) {
				while (paqSinToken.hasMoreTokens()) {
					if (loginUser.getPaquete().equals(paqSinToken.nextToken().trim())) {
						basicoSinToken = true;
						break;
					}
				}
			}
	
			if (loginUser.getEstatus().equals(ConfigLogIn.USER_INACTIVO.getValor())
					|| loginUser.getEstatus().equals(
							ConfigLogIn.USER_SUSPENDIDO.getValor())
					|| loginUser.getEstatus().equals(
							ConfigLogIn.USER_NUEVO.getValor())
					|| loginUser.getEstatus().equals(
							ConfigLogIn.USER_INACTIVIDAD.getValor())
					|| loginUser.getEstatus().equals(
							ConfigLogIn.USER_CUENTA_CANCELADA.getValor())
					|| loginUser.getEstatus().equals(
							ConfigLogIn.USER_PAQUETE_PYME.getValor())) {
				
				// REDIRECCIONA A EMPRESAS VIEJO	-- INICIO --
				if (loginUser.getTipo().equals(ConfigLogIn.TIPO_EMPRESAS.getValor())){
					
					String PageToCall = "";
					
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_INACTIVO.getValor())) {
						PageToCall = "login_userinactive.jsp";
					}
		
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_SUSPENDIDO.getValor())) {
						PageToCall = "login_usersuspended.jsp";
					}
		
					if (loginUser.getEstatus()
							.equals(ConfigLogIn.USER_NUEVO.getValor())) {
						PageToCall = "login_user_new.jsp";
					}
		
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_INACTIVIDAD.getValor())) {
						PageToCall = "login_inactividad.jsp";
					}
		
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_CUENTA_CANCELADA.getValor())) {
						PageToCall = "login_acc_inactive.jsp";
					}
		
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_PAQUETE_PYME.getValor())) {
						PageToCall = "login_acc_inactive_PYME.jsp";
					}
					response.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.WEB_APP_EMPRESAS , String.class)+"/pages/s/"+PageToCall);
				}
				
				// REDIRECCIONA A EMPRESAS VIEJO --FIN--
				if (loginUser.getTipo().equals(ConfigLogIn.TIPO_PERSONAS.getValor())){				
	
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_INACTIVO.getValor())) {
						modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.inactive"));
						modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.inactive.label"));
						return AfirmeNetWebConstants.MV_LOGIN_USERINACTIVO;
					}
		
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_SUSPENDIDO.getValor())) {
						modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.suspended"));
						modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.suspended.label"));
						return AfirmeNetWebConstants.MV_LOGIN_USERSUSPENDIDO;
					}
		
					if (loginUser.getEstatus()
							.equals(ConfigLogIn.USER_NUEVO.getValor())) {
						modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.new"));
						modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.new.label"));				
						return AfirmeNetWebConstants.MV_LOGIN_USERNUEVO;
					}
		
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_INACTIVIDAD.getValor())) {
						modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.inactividad"));
						modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.inactividad.label"));		
						return AfirmeNetWebConstants.MV_LOGIN_USERINACTIVIDAD;
					}
		
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_CUENTA_CANCELADA.getValor())) {
						
						modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.cuentacancelada"));
						modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.cuentacancelada.label"));
						return AfirmeNetWebConstants.MV_LOGIN_ACCCANCELADA;
					}
		
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_PAQUETE_PYME.getValor())) {
						modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.pyme"));
						modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.pyme.label"));
						return AfirmeNetWebConstants.MV_LOGIN_AVISO_ACCPAGOPYME;
					}
				}
			}
	
			// Si el contrato se encuentra pendiente de activar
			if (loginUser.getEstatus().equals(
					ConfigLogIn.USER_PTEACTIVAR.getValor())) {
				// revisar si es personas o empresas
				if (loginUser.getTipo()
						.equals(ConfigLogIn.TIPO_EMPRESAS.getValor())) {

					String UserPatrimonial = "1";
					String UserPatrimonialAdd="?patrimonial=";
				      if(loginUser.getPatrimonial().equalsIgnoreCase("S")) {
				    	  UserPatrimonialAdd = UserPatrimonialAdd + UserPatrimonial; 
				      }
					response.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(
							ConfigPersonas.WEB_APP_EMPRESAS , String.class)+"/pages/s/act_frame.jsp"+ UserPatrimonialAdd + "&contrato=" + login.getContrato());
					
				} else if (loginUser.getTipo().equals(ConfigLogIn.TIPO_PERSONAS.getValor())) {
					
					// Verifica si el contrato ha tenido sessiones previas. 
					// Si alguna vez ha iniciado sesion, se indica que el flujo será Regeneracion de Contraseña
					JBLogList ultimoLogIn = logService.getLastLoginPer(login.getContrato());
					if (ultimoLogIn != null && ultimoLogIn.getDTTM() != null) {
						modelMap.put("regeneracion", true);
					}
					
					// revisar si es basico sin token o no
					if (!basicoSinToken) {
						modelMap.put("activacion", login);
						return AfirmeNetWebConstants.MV_ACTIVA_CONTRATO;
					} else {
						modelMap.put("activacion", login);
						return AfirmeNetWebConstants.MV_ACTIVA_CONTRATO_BASICO;
					}
				} else {
					modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.invalid"));
					modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.invalid.label"));		
					return AfirmeNetWebConstants.MV_LOGIN_USERIVALIDO;
				}
			}
	
			if (loginUser.getTipo().equals(ConfigLogIn.TIPO_EMPRESAS.getValor())) {
	
				String UserPatrimonial = "1";
				String UserPatrimonialAdd="?patrimonial=";
			      if(loginUser.getPatrimonial().equalsIgnoreCase("S")) {
			    	  UserPatrimonialAdd = UserPatrimonialAdd + UserPatrimonial; 
			      }
				
				if (loginUser.getEstatus().equals(
						ConfigLogIn.USER_AVISO_CUENTA_CANCELADA)) {	
					modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginaviso.user.cuentacancelada"));
					modelMap.addAttribute("errMsg", getMessage("afirmenet.loginaviso.user.cuentacancelada.label"));		
					modelMap.addAttribute("action", "");		
					return AfirmeNetWebConstants.MV_LOGIN_AVISO_ACCCANCELADA;
//					response.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(
//							ConfigPersonas.WEB_APP_EMPRESAS , String.class)+"/pages/s/loginPortalEmpAvisoAccCancel.jsp"+ UserPatrimonialAdd);
					
//					resp.sendRedirect(SuperServlet.WebAppCTR + Path + "loginPortalEmpAvisoAccCancel.jsp" + UserPatrimonialAdd);
					
				} else if (loginUser.getEstatus().equals(
						ConfigLogIn.USER_AVISO_PAQUETE_PYME.getValor())) {
					modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginaviso.user.pyme"));
					modelMap.addAttribute("errMsg", getMessage("afirmenet.loginaviso.user.pyme.label"));		
					modelMap.addAttribute("action", "");
					return AfirmeNetWebConstants.MV_LOGIN_AVISO_ACCPAGOPYME;
					
//					resp.sendRedirect(SuperServlet.WebAppCTR + Path + "loginPortalEmpAvisoPYME.jsp" + UserPatrimonialAdd);
					
				} else {
					response.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(
							ConfigPersonas.WEB_APP_EMPRESAS , String.class)+"/pages/s/loginPortal.jsp"+ UserPatrimonialAdd);	
				}
	
				// session.invalidate();
			} else if (loginUser.getTipo().equals(
					ConfigLogIn.TIPO_PERSONAS.getValor())) {
				// Revisar si es basico sin token
				if (basicoSinToken) {
	
					login.setAlias(loginUser.getAlias());
	
					if (loginUser.getAlias() != null
							&& !loginUser.getAlias().equals("")) {
						if (logInService.verificarPrimerLoginConAlias(login
								.getContrato())) {
							logInService.actualizarPrimerLoginConAlias(login
									.getContrato());
						}
						if (loginUser.getEstatus().equals(
								ConfigLogIn.USER_AVISO_CUENTA_CANCELADA.getValor())) {
							modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginaviso.user.cuentacancelada"));
							modelMap.addAttribute("errMsg", getMessage("afirmenet.loginaviso.user.cuentacancelada.label"));		
							modelMap.addAttribute("action", "");	
							return AfirmeNetWebConstants.MV_LOGIN_AVISO_ACCCANCELADA;
						} else {
	
							String contratoMask = "&bull;&bull;&bull;&bull;&bull;&bull;"
									+ login.getContrato().substring(
											login.getContrato().length() - 4,
											login.getContrato().length());
	
							login.setAlias(loginUser.getAlias());
							login.setPatrimonial(loginUser.getPatrimonial());
							login.setGrpId(loginUser.getPaquete());
							login.setAvatar(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH)+userService.obtenerAvatar(login.getContrato())+".jpg");
							modelMap.addAttribute("login", login);
							modelMap.addAttribute("basicoSinToken", basicoSinToken);
							modelMap.addAttribute("contratoMask", contratoMask);
	
							return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO; // validar
						}
					} else {
						modelMap.addAttribute("login", login);
						return AfirmeNetWebConstants.MV_LOGIN_ALIAS;
					}
	
				} else {
					
					if (loginUser.getAlias() == null
							|| loginUser.getAlias().equals("")) {
						return AfirmeNetWebConstants.MV_LOGIN_ALIAS;
					}
					
					if (loginUser.getEstatus().equals(
							ConfigLogIn.USER_AVISO_CUENTA_CANCELADA.getValor())) {
						modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginaviso.user.cuentacancelada"));
						modelMap.addAttribute("errMsg", getMessage("afirmenet.loginaviso.user.cuentacancelada.label"));		
						modelMap.addAttribute("action", "");
						return AfirmeNetWebConstants.MV_LOGIN_AVISO_ACCCANCELADA;
					} else {
						String contratoMask = "&bull;&bull;&bull;&bull;&bull;&bull;"
								+ login.getContrato().substring(
										login.getContrato().length() - 4,
										login.getContrato().length());
	
						login.setAlias(loginUser.getAlias());
						login.setPatrimonial(loginUser.getPatrimonial());
						login.setGrpId(loginUser.getPaquete());
						login.setAvatar(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH)+userService.obtenerAvatar(login.getContrato())+".jpg");
						modelMap.addAttribute("login", login);
						modelMap.addAttribute("basicoSinToken", basicoSinToken);
						modelMap.addAttribute("contratoMask", contratoMask);
						
	
						return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
					}
	
				}
			} else {
				modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.invalid"));
				modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.invalid.label"));		
				return AfirmeNetWebConstants.MV_LOGIN_USERIVALIDO;
			}
			modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.invalid"));
			modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.invalid.label"));		
			return AfirmeNetWebConstants.MV_LOGIN_USERIVALIDO;
		}catch (Exception e){
			throw new AfirmeNetException("0000", getMessage("afirmenet.noDisponible"));
		}
	}


	@RequestMapping(value = "/datosAcceso.htm", method = RequestMethod.POST)
	public String mostrarHome(@ModelAttribute("login") Login login,
			HttpServletRequest request, ModelMap modelMap) throws AfirmeNetException{
		LOG.info("Atendiendo Peticion = /login/datosAcceso.htm contrato ===> "
				+ login.getContrato());
		LOG.setLogInfo("", "", "Entrada al Login", request.getRemoteAddr());
		JBLogin loginUser = null;
		
		AfirmeNetUser usuario = new AfirmeNetUser();
		usuario.setContrato(login.getContrato());

		usuario.setCuentaContrato(contratoService.getCuentaContrato(usuario.getContrato()));
		//OJOS AQUI VA LA CONSULTA DE LA CUENTA RELACIONADA AL CONTRATO
		
		// VALIDA QUE EL CONTRATO O PASS NO SEAN VACIOS
		if (login.getContrato() == null || login.getPassword() == null) {
			LOG.setLogInfo("", "", "Session terminada", request.getRemoteAddr());
			return AfirmeNetWebConstants.MV_LOGIN;
		}
		LOG.setLogInfo(login.getContrato(), login.getContrato(), "Inicio Validacion del Login", request.getRemoteAddr());
		// VALIDA QUE ESTEN ACTIVOS LOS SOCKET
		String finDiaError = finDiaService.getFinDia();

		if (finDiaError.equals("1")) {
			LOG.setLogInfo(login.getContrato(), login.getContrato(), "El sistema no responde ["+finDiaError+"]", request.getRemoteAddr());
			// Timeout
			return null;
		}

		if (finDiaError.equals("2")) {
			LOG.setLogInfo(login.getContrato(), login.getContrato(), "El sistema no responde ["+finDiaError+"]", request.getRemoteAddr());
			// Error
			return null;
		}

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
		
		usuario.setBasicoSinToken(bPaqueteSinToken);
		usuario.setTokenRSA(tokenRSA);
		
		
		/*****************************************************************************************************************************/
		
		//VALIDA TOKEN
		
		if(validaTokenLogIn(request.getParameter("token"), usuario,modelMap, login )){
		// OBTIENE USUARIO DE 400
		if (!(login.getPassword().equals(""))) {

			loginUser = logInService.getUserLogin(login.getContrato(),
						login.getPassword());


			if (loginUser.getFormatName().equals("INLOGIN02") == false
					|| loginUser.getPWDUSR().equals(login.getContrato()) == false) {
				LOG.setLogInfo(login.getContrato(), login.getContrato(), "Message " + loginUser.getFormatName() + " received.", request.getRemoteAddr());
				return AfirmeNetWebConstants.MV_LOGIN;
			}

			if (!loginUser.gettCliente().equals("2")) {		
				LOG.setLogInfo(login.getContrato(), login.getContrato(), "Usuario Invalido", request.getRemoteAddr());
				modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.invalid"));
			    modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.invalid.label"));	
				return AfirmeNetWebConstants.MV_LOGIN_USERIVALIDO;
			}
		} else {
			return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;
		}

		// VALIDA ESTATUS INVALIDOS DE 400
		if (loginUser.getStatus().equals(
				ConfigLogIn.USER_400_INVALIDO.getValor())) {
			modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.invalid"));
		    modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.invalid.label"));	
			return AfirmeNetWebConstants.MV_LOGIN_USERIVALIDO;
		} else if (loginUser.getStatus().equals(
				ConfigLogIn.USER_400_PASSINCORRECTO.getValor())) {
			if (loginUser.getEussts().equals(
					ConfigLogIn.USER_400_INACTIVO.getValor())) {
				// enviarCorreoAlertaBloqueo(loginUser.getEmail(),
				// loginUser.getCliente());
			}
			modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.invalidpass"));
		    modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.invalidpass.label"));	
			return AfirmeNetWebConstants.MV_LOGIN_INVALIDPASS;
		}



		// Se obtiene el estatus del contrato de Afirmenet
		String status2 = loginUser.getEussts();

		if (status2.equals(ConfigLogIn.USER_PTEACTIVAR.getValor())
				&& usaTok == true) {
			modelMap.put("activacion", login);
			LOG.setLogInfo(login.getContrato(), login.getContrato(), "Se Reenvio a activacion de token", request.getRemoteAddr());
			return AfirmeNetWebConstants.MV_ACTIVA_CONTRATO;
		} else if (status2.equals(ConfigLogIn.USER_PTEACTIVAR.getValor())
				&& bPaqueteSinToken == true) {
			return AfirmeNetWebConstants.MV_ACTIVA_CONTRATOSINTOKEN;
		}
		// Estatus bloqueado por inactividad
		if (status2.equals(ConfigLogIn.USER_INACTIVIDAD.getValor())) {
			modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.inactividad"));
			modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.inactividad.label"));	
			return AfirmeNetWebConstants.MV_LOGIN_USERINACTIVIDAD;
		}

		if (loginUser.getStatus().equals("")) {

			String ipActual = request.getRemoteAddr();

			JBBMuser bmUser = new JBBMuser();

			// Se verifica el inout
			if (AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.INOUT , String.class).equals("Y")
					&& !login.getPassword().equals("")) {

				boolean activo = logInService
						.verificaINOUT(login.getContrato());
				// Si ya esta activa una sesion con este usuario
				if (activo == true) {

					String ipAnterior = logInService.consultaIP(
							login.getContrato(), login.getContrato());

					if (ipAnterior.trim().equals("") == false
							&& ipActual.equals(ipAnterior) == false) {
						// Envia correo por doble sesion
						// enviarCorreoAlertaDobleSession(UserIdJSP);
					} else {
						// enviarCorreoAlertaContratoBloqueado(UserIdJSP);
					}
					
					modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.inout"));
					modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.inout.label"));	
					return AfirmeNetWebConstants.MV_LOGIN_INOUT;

				} else {
					// Inserta o actualiza IP del usuario

					if (logInService
							.consultaIP(login.getContrato(),
									login.getContrato()).trim().equals("") == true) {
						// Si no existe, inserta
						logInService.insertaIP(login.getContrato(),
								login.getContrato(), ipActual);
					} else {
						// Si existe actualiza
						logInService.actualizaIP(login.getContrato(),
								login.getContrato(), ipActual, "0");
					}

				}
			}

			// VALIDA BANCAMOVIL
			if (AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.INOUT , String.class).equals("Y")
					&& !login.getPassword().equals("")) {
				bmUser = logInService.verificaBMUser(login.getContrato());
				boolean activo = bmUser.isActivo();
				// Si ya esta activa una sesion con este usuario
				if (activo == true) {
					// enviarCorreoAlertaAfirmeMovilBloqueado(UserIdJSP);
					modelMap.addAttribute("errEstatus", getMessage("afirmenet.loginmsg.user.bmuser"));
					modelMap.addAttribute("errMsg", getMessage("afirmenet.loginmsg.user.bmuser.label"));	
					LOG.setLogInfo(login.getContrato(), login.getContrato(), "Usuario Banca Movil", request.getRemoteAddr());
					return AfirmeNetWebConstants.MV_LOGIN_BANCAMOVIL; 

				}
			}

			usuario.setNumCliente(loginUser.getCliente());
			usuario.setNombreCompleto(loginUser.getNombreCliente());
			usuario.setNombreLargo(loginUser.getNombreCliente());
			usuario.setNombreCorto(loginUser.getNombreCliente());
			usuario.setCorreoElectronico(loginUser.getEmail());
			usuario.setPaqueteAfirmeNet(loginUser.getGrpId());
			usuario.setAvatar(login.getAvatar());
			usuario.setPatrimonial(loginUser.getEsPatrimonial().equals("0")?"N":"S");
			usuario.setAlias( loginUser.getAlias() );
			usuario.setAlias( loginUser.getAlias() );


			// CREA JBSUMMARY
			// CARGA CUENTAS DE TERCEROS
			// CARGA PROCODE
			List<JBProCode> procode = logInService.getProcode(bPaqueteSinToken);
			// CARGA JBSUMMARY
			List<Cuenta> cuentas=this.calculaResumeCuentas(usuario, modelMap);
			request.getSession().setAttribute(AfirmeNetWebConstants.CUENTAS_SESSION, cuentas);
			// CARGA ULTIMO LOGIN
			JBLogList ultimoLogIn = new JBLogList();
			ultimoLogIn = logService.getLastLoginPer(usuario.getContrato());
			
			if(ultimoLogIn!=null && ultimoLogIn.getDTTM()!=null)				
				usuario.setUltimoAcceso(TimeUtils.getDate(ultimoLogIn.getDTTM(),TimeUtils.AS400_DATE_FORMAT));
			else
				usuario.setUltimoAcceso(TimeUtils.getDate(Util.getRefNum(),TimeUtils.AS400_DATE_FORMAT));
			// CARGA LAS CONFIGURACIONES DE LA TABLA DC_PROPERTIES
			AfirmeNetConstants.setPropertiesConfig(propertyService.getConfiguracionesMap());
			// CARGA TIEMPO DE ESPERA CUENTA Y ACTUALIZA EN EL MAPA DE CONFIGURACIONES
			//propertyService.setTiempoEspera(ConfigProperties.PROPERTYID_THIRD_ACCOUNT_TIME_PER);
			
			// CARGA NUMERO DE INTENTOS CUENTA
			// CARGA VIGENCIA CUENTAS
			// INSERTA USUARIO ACCESO A AFIRMENET
			// logbean.getAddLogPer(Util.getRefNum(), UserIdJSP, LACTION, RMK,ACCOUNT, AMOUNT, CURRENCY, TYP_TRAN, Util.getRefNum());
			logService.getAddLogPer(Util.getRefNum(), usuario.getContrato(),
					"LOGIN", "USER LOGIN OK", usuario.getContrato(), "0.00", "", "OKL",
					Util.getRefNum());
			// ACTUALIZA BMUSER LOGEADO EN AFIRMENET
			logInService.updateBMUser(usuario.getContrato());

			// Se carga el menú
			String menu = menuService.getMenu(loginUser.getGroupID(), 1,usuario.getContrato());
			request.getSession().setAttribute("menu", menu);
			// Carga, actualiza o elimina los límites de transferencia
			logInService.defaultSetting(usuario.getContrato(),
					loginUser.getGroupID(), cuentas, procode);
			// Busca cambios en los límites de transferencia, si ya pasaron el
			// tiempo de espera los actualiza, de lo contrario los deja como
			// están.
			logInService.lookupChangeParameter(usuario.getContrato());
			// Buscar capañas a morstrar por usuario
			String token = "0";
			if(!bPaqueteSinToken) {
				try {
					token = (String) tokenRSA.get(1);
				} catch (Exception e) { }
			}
			usuario.setCampania(campaniaService.getCampania(
					TipoCliente.PERSONAS, login.getContrato(),
					login.getContrato(), token));

			String bgImg = AfirmeNetWebConstants.BACKGROUD_PATH_IMAGE;
			
			if(usuario.getPatrimonial().equals("S"))
				bgImg = AfirmeNetWebConstants.BACKGROUD_PATRIMONIAL_PATH_IMAGE;
			
			// Se valida si existe campaña de imagen, de lo contrario se setea
			// la
			// default
			LOG.setLogInfo(usuario.getContrato(), usuario.getContrato(), "Fin de la Validacion del Login", request.getRemoteAddr());
			if (usuario.getCampania() != null
					&& usuario.getCampania().getImagen() != null
					&& !usuario.getCampania().getImagen().equals("null")
					&& StringUtils
							.isNotEmpty(usuario.getCampania().getImagen())) {
				bgImg = AfirmeNetConstants
						.getValorConfigPersonas(ConfigPersonas.CAMPANIAS_IMG_URL)
						+ usuario.getCampania().getImagen();
			}
			request.getSession().setAttribute(
					AfirmeNetWebConstants.BACKGROUD_IMAGE, bgImg);

			// Se elimina el usuario de session y se coloca el nuevo
			request.getSession().removeAttribute(
					AfirmeNetWebConstants.USUARIO_SESSION);
			request.getSession().setAttribute(
					AfirmeNetWebConstants.USUARIO_SESSION, usuario);
			LOG.setLogInfo(usuario.getContrato(),usuario.getContrato(), "Fin del Login", request.getRemoteAddr());
			return AfirmeNetWebConstants.MV_HOME;

		}
		
	}
		modelMap.addAttribute("login", login);
		return AfirmeNetWebConstants.MV_LOGIN_DATOS_ACCESO;

	}


	@RequestMapping(value = "/terminos.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String mostrarTerminos() {
		return AfirmeNetWebConstants.MV_HOME_TERMINOS;
	}
	
	/**
	 * Funcion general para la implementacion de la validacion de token
	 * @param passCode
	 * @param contrato
	 * @param usuario
	 * @param basicoSinToken
	 * @return
	 */
	public boolean validaTokenLogIn(String passCode, AfirmeNetUser afirmeNetUser, ModelMap modelMap, Login login) {
		
		if(afirmeNetUser.isBasicoSinToken())
			return true;
		
		TokenModel respToken =  tokenService.validaClave(passCode, afirmeNetUser.getContrato(),afirmeNetUser.getContrato(),
		        				afirmeNetUser.isBasicoSinToken(),afirmeNetUser.getTokenRSA(),login.getIntentosToken());
		if(!respToken.isValido()){
			login.setIntentosToken(respToken.getIntentos());
			modelMap.addAttribute("intentosToken", respToken.getIntentos());
			modelMap.addAttribute("erroresToken", respToken.getErrores());
		}
		
		return respToken.isValido();
	}


}
