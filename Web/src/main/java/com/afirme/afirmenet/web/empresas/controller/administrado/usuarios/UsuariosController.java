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
	
	@RequestMapping(value = "/lista_usuarios.htm", method = RequestMethod.POST)
	public String listaUsuarios(HttpServletRequest request,	ModelMap modelMap){
		//Se verifica logueo para extraer datos
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.info("<<listaUsuarios()");
		LOG.info(">>listaUsuarios()");
		
		return null;
	}
	/**
	 * Consulta de tokens
	 * @param request
	 * @param modelMap
	 * @return
	 */
	
	@RequestMapping(value = "/tokens_disponibles.htm", method = RequestMethod.POST)
	public String tokendisponibles(HttpServletRequest request,	ModelMap modelMap){
		//Se verifica logueo para extraer datos
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.info("<<tokendisponibles()");
		LOG.info(">>");
		return null;
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
		LOG.debug("Se enlistan usuarios");
		return null;
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
		LOG.debug("Se enlistan usuarios");
		return null;
	}
	/**
	 * Cuando el actor da clic al boton continuar despues de permisos se muestra la pantalla para la contraseña
	 * @param request
	 * @param modelMap
	 * @return
	 */
	
	@RequestMapping(value="/agregar_contrasena.htm", method = RequestMethod.POST)
	public String agregaContrasenia(HttpServletRequest request,	ModelMap modelMap){
		LOG.debug("Contraseña guardada");
		return null;
	}
	/**
	 * Cuando se valida la contraseña y todos los datos estan correctos
	 * @param usuariosDTO
	 * @param modelMap
	 * @param httpServletRequest
	 * @return
	 */
	
	@RequestMapping(value = "/confirmacion.htm", method = RequestMethod.POST)
	public String valida(UsuariosDTO usuariosDTO, ModelMap modelMap, HttpServletRequest httpServletRequest){
		LOG.debug("Se confirman datos de nuevo usuario");
		userService.registrarUsuario(usuariosDTO);
		return null;
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
