package com.afirme.afirmenet.web.empresas.controller.configuraciones.seguridad;


import javax.servlet.http.HttpServletRequest;

import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.empresas.controller.base.BaseController;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.empresas.service.acceso.UserService;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.model.AfirmeNetUser;
import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.service.contrato.ContratoService;



/**
 * Controller para el cambio de correo electronico de la cuenta de afirmenet.
 * <br><br>
 * Created on Sep 7, 2015 5:03:19 PM by eguzher
 * <br>
 * <br>
 * @author epifanio.guzman@afirme.com
 * <br>
 */
@Controller
@RequestMapping("/configuraciones/seguridad/correo")
public class ConfigSeguridadCambioCorreoController extends BaseController {
	static final AfirmeNetLog LOG = new AfirmeNetLog(ConfigSeguridadCambioCorreoController.class);
	
	@Autowired
	private ContratoService contratoService;
	@Autowired
	private UserService userService; 
	

	
	/**
	 * Inicia cuando el usuario da click en configuracion > seguridad > cambio de correo
	 * <br><br>
	 * @param request
	 * @param modelMap
	 * @return String
	 */
	@RequestMapping(value = "/seguridad_cambio_correo.htm", method = RequestMethod.POST)
	public String goCambioCorreoCuentaAfirme(HttpServletRequest request,	ModelMap modelMap) {
		LOG.info(">>goCambioCorreoCuentaAfirme()");
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.info("<<goCambioCorreoCuentaAfirme()");
		return AfirmeNetWebConstants.MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCORREO;
	}
	
	/**
	 * Cando el usuario da click en el boton continuar en la pantalla en donde se captura el nuevo correo.
	 * <br><br>
	 * @param correoElectronicoDTO
	 * @param modelMap
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/seguridad_cambio_correo_confirmar.htm", method = RequestMethod.POST)
	public String confirmarDatos(@ModelAttribute("correoCuentaAfirme") CorreoElectronicoDTO correoElectronicoDTO, ModelMap modelMap, HttpServletRequest request) {
		LOG.info(">>confirmarDatos()");
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.info("<<confirmarDatos()");
		return AfirmeNetWebConstants.MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCORREO_CONFIRMAR;	
	}
	
	/**
	 * cuando el usuario captura el token y da click en continuar.
	 * <br><br>
	 * cuando el usuario confirma la actualizacion del correo electrónico.
	 * <br><br>
	 * Para actualizar el correo:
	 * <br><br>
	 * 1.- Verifica no exista otro estado pendiente con el mismo userid y contrato,<br> 
	 * 2.- si existe lo pone con un status de cancelado<br>
	 * 3.- y luego inserta el nuevo cambio para el cliente<br>
	 * <br>
	 * @throws Exception 
	 */
	@RequestMapping(value = "/seguridad_cambio_correo_comprobante.htm", method = RequestMethod.POST)
	public String guardarDatos(@ModelAttribute("correoCuentaAfirme") CorreoElectronicoDTO correoElectronicoDTO, ModelMap modelMap, HttpServletRequest request) {
		LOG.info(">>confirmarDatos()");
		boolean resultado = false;
		try {
			resultado = userService.actualizarCorreoLogin(correoElectronicoDTO);
		} catch (Exception e) {
			LOG.error("Error al actualizar el correo.", e);
		}
		LOG.info("<<confirmarDatos()");
		return AfirmeNetWebConstants.MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCORREO_COMPROBANTE;
	}
	
	/**
	 * Utilizado para validar los datos del formaulario de lado del servidor.
	 * 
	 * @param correoElectronicoDTO
	 * @throws AfirmeNetException
	 * @throws Exception
	 */
	private void validarDatosRecibidos(CorreoElectronicoDTO correoElectronicoDTO) throws AfirmeNetException,Exception{

		try{
			if(correoElectronicoDTO.getCorreoNuevo().trim().equals("") == true){
				throw new AfirmeNetException( "0000", Util.getPropertyString("afirmenet.etiqueta.conf.correo.vista.valid.ingrese.su.nuevo.correo") );
			}
			
			if(correoElectronicoDTO.getCorreoActual().trim().toLowerCase().equals( correoElectronicoDTO.getCorreoNuevo().trim().toLowerCase() )){
				throw new AfirmeNetException( "0000", Util.getPropertyString("afirmenet.etiqueta.conf.correo.vista.valid.correo.nuevo.no.debe.ser.igual.actual") );
			}
			
			if( Util.validaCaracteresEnNuevoCorreo( correoElectronicoDTO.getCorreoNuevo().trim() ) == false){
				throw new AfirmeNetException( "0000", Util.getPropertyString("afirmenet.etiqueta.conf.correo.vista.valid.correo.es.invalido") );
			}
			
			if(correoElectronicoDTO.getCorreoNuevo().trim().equals(correoElectronicoDTO.getCorreoNuevoConfirmacion().trim()) == false){
				throw new AfirmeNetException( "0000", Util.getPropertyString("afirmenet.etiqueta.conf.correo.vista.valid.confirmacion.debe.ser.igual.a.nuevo") );
			}
			
		}catch (AfirmeNetException ae) {
			throw ae;
		}catch (Exception e) {
			throw e;
		}
		
	}
}
