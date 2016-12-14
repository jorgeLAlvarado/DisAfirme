package com.afirme.afirmenet.web.empresas.controller.administrado.usuarios;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.model.configuraciones.UsuariosDTO;
import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.empresas.service.administrador.usuarios.UsuariosService;
import com.afirme.afirmenet.web.empresas.controller.base.BaseController;

/**
 * @author usuario
 *
 */
@Controller
@RequestMapping(value= "/Usuarios")
public class UsuariosController extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private UsuariosService listaUsuariosService;
	
	/**
	 * Cuando el usuario da clic al boton de agregar usuario en el token a asignar
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/Agregar_Usuario", method = RequestMethod.POST)
	public String agregaUsuarios(HttpServletRequest request,	ModelMap modelMap){
		return null;
	}
	
	/**
	 * Cuando el usuario da clic al boton continuar y ya a tecleado los datos
	 * @param usuariosDTO
	 * @param modelMap
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/Agregar_Usuarios_Validar_Datos", method = RequestMethod.POST)
	public String validaDatos(UsuariosDTO usuariosDTO, ModelMap modelMap, HttpServletRequest httpServletRequest){
		return null;
	}
	/**
	 * Cuando se valida que los datos ingresados sean los correctos
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/Agregar_Usuarios_Permisos", method = RequestMethod.POST)
	public String agregarPermisos(HttpServletRequest request,	ModelMap modelMap){
		return null;
	}
	/**
	 * Cuando el actor da clic al boton continuar despues de permisos se muestra la pantalla para la contraseña
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/Agregar_Usuario_Contrasenia", method = RequestMethod.POST)
	public String agregaContrasenia(HttpServletRequest request,	ModelMap modelMap){
		return null;
	}
	/**
	 * Cuando el usuario da clic al boton continuar e ingresa las contraseñas a validar
	 * @param usuariosDTO
	 * @param modelMap
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/Agregar_Usuarios_Validar_Contrasenia", method = RequestMethod.POST)
	public String validaContrasenia(UsuariosDTO usuariosDTO, ModelMap modelMap, HttpServletRequest httpServletRequest){
		return null;
	}
	/**
	 * Cuando se valida la contraseña y todos los datos estan correctos
	 * @param usuariosDTO
	 * @param modelMap
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/Agregar_Usuario_Comprobante.htm", method = RequestMethod.POST)
	public String valida(UsuariosDTO usuariosDTO, ModelMap modelMap, HttpServletRequest httpServletRequest){
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
	
	/**
	 * Consulta de usuarios
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/listUsuarios", method = RequestMethod.POST)
	public String listaUsuariosService(HttpServletRequest request,	ModelMap modelMap){
		return null;
	}
	
}
