package com.afirme.afirmenet.web.empresas.controller.administrado.usuarios;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.model.AfirmeNetUser;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.web.controller.base.BaseController;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;
/**
 * Controlador para administrar usuarios
 * 
 * @author Selene Mena Quiñones
 *
 * Modificado on dic 14, 2016 3:12:21 PM by Selene 
 * 
 * @author Selene Mena Quiñones
 * 
 * Modificado on dic 20, 2016 1:28:21 PM by Jorge
 * 
 *  @author Jorge Alvarado
 */
@Controller
@RequestMapping(value= "/usuarios")
public class UsuariosController extends BaseController{

	@Autowired
	private UserService userService;
	
	static final AfirmeNetLog LOG = new AfirmeNetLog(UsuariosController.class);
	/**
	 * Consulta de usuarios
	 * @param request
	 * @param modelMap
	 * @return
	 */
	
	@RequestMapping(value = "/admin_home-usuarios.htm", method = RequestMethod.POST)
	public String listaUsuarios(HttpServletRequest request,	ModelMap modelMap){
		//Se verifica logueo para extraer datos
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.info("<<listaUsuarios()");
		Boolean usuarios = false;
		userService.getdatosUsuario(usuarios);
		LOG.info(">>listaUsuarios()");
		userService.getdatosUsuario(usuarios);
		
		return AfirmeNetWebConstants.LISTAR_USUARIOS;
	}
	/**
	 * Consulta de tokens
	 * @param request
	 * @param modelMap
	 * @return
	 */
	
	@RequestMapping(value = "/admin_home-usuarios-tokens.htm", method = RequestMethod.POST)
	public String tokenDisponibles(HttpServletRequest request,	ModelMap modelMap){
		//Se verifica logueo para extraer datos
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.info("<<tokenDisponibles()");
		Boolean soloDisponibles = false;
		userService.getTokens(soloDisponibles);
		LOG.info(">>tokenDisponibles()");
		return AfirmeNetWebConstants.TOKENS_DISPONIBLES;
	}
	/**
	 * Cuando el usuario da clic al boton de agregar usuario en el token a asignar
	 * @param request
	 * @param modelMap
	 * @return
	 */
	
	@RequestMapping(value="/agregar_usuario.htm", method = RequestMethod.POST)
	public String agregaUsuarios(HttpServletRequest request,	ModelMap modelMap){
		//Se verifica logueo para extraer datos
		LOG.info("<<agregaUsuarios()");
		LOG.info(">>agregaUsuarios()");
		return AfirmeNetWebConstants.MV_AGREGAR_USUARIO_PASO_1;
	}
	/**
	 * Cuando el usuario da clic al boton continuar
	 * @param request
	 * @param modelMap
	 * @return
	 */
	
	@RequestMapping(value="/agregar_permisos.htm", method = RequestMethod.POST)
	public String agregaPermisos(HttpServletRequest request,	ModelMap modelMap){
		//Se verifica que el usuario esta logueado para extraer datos
		LOG.info("<<agregaPermisos()");
		LOG.info(">>agregaPermisos()");
		return AfirmeNetWebConstants.MV_AGREGAR_USUARIO_PASO_2;
	}
	/**
	 * Cuando el actor da clic al boton continuar despues de permisos se muestra la pantalla para la contraseña
	 * @param request
	 * @param modelMap
	 * @return
	 */
	
	@RequestMapping(value="/agregar_contrasena.htm", method = RequestMethod.POST)
	public String agregaContrasenia(HttpServletRequest request,	ModelMap modelMap){
		LOG.info("<<agregaCotrasenia()");
		LOG.info(">>agregaContrasenia()");
		return AfirmeNetWebConstants.MV_AGREGAR_USUARIO_PASO_3;
	}
	/**
	 * Cuando se valida la contraseña y todos los datos estan correctos
	 * @param usuariosDTO
	 * @param modelMap
	 * @param httpServletRequest
	 * @return
	 */
	
	@RequestMapping(value = "/confirmacion.htm", method = RequestMethod.POST)
	public String confirmacion(UsuariosDTO usuariosDTO, ModelMap modelMap, HttpServletRequest httpServletRequest){
		LOG.info("<<confirmacion()");
		userService.registrarUsuario(usuariosDTO);
		LOG.info("<<confirmacion()");
		return AfirmeNetWebConstants.MV_AGREGAR_USUARIO_PASO_4;
	}
	
	/**
	 * Utilizado para validar los datos del formaulario de lado del servidor.
	 * @param usuariosDTO
	 * @throws AfirmeNetException
	 * @throws Exception
	 */
	
	private void validarDatosRecibidos(UsuariosDTO usuariosDTO) throws AfirmeNetException,Exception{
		
	}
	
	/**
	 * Metodo local para preparar el objeto afirmeNetMail que se va a enviar
	 * en la notificacion del correo.
	 * 
	 * @param usuariosDTO
	 */
	
	private void enviarComprobante(UsuariosDTO usuariosDTO){
		
	}
	

}
