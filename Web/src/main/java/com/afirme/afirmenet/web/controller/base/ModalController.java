package com.afirme.afirmenet.web.controller.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

@Controller
public class ModalController extends BaseController{
	
	/**
	 * Metodo para cancelar la transaccion
	 */
	@RequestMapping(value = "/cancela.htm", method = RequestMethod.GET)
	public String modalCancela(HttpServletRequest request,	ModelMap modelMap) {
		modelMap.addAttribute("titulo", "Cancelar la Transacción");
		modelMap.addAttribute("mensaje", "Usted perderá la transacción actual y las guardadas  con anterioridad, dichas transacciones no podrán recuperarse.");
		modelMap.addAttribute("pregunta", "¿Está seguro de Cancelar la Transacción?");
		modelMap.addAttribute("action", "consultas/resumen-de-mis-cuentas.htm");
		modelMap.addAttribute("boton", "Sí, cancelar");
		return AfirmeNetWebConstants.MODAL;
	}
	
	/**
	 * Metodo para cerrar sesion
	 */
	@RequestMapping(value = "/modaLogout.htm", method = RequestMethod.GET)
	public String modalLogout(HttpServletRequest request,	ModelMap modelMap) {
		modelMap.addAttribute("titulo", "Salir de AfirmeNet");
		modelMap.addAttribute("mensaje", "Al dar click en el botón de Continuar usted abandonará la sesión de Afirmenet. Esto lo llevará al portal de Afirme www.afirme.com.");
		modelMap.addAttribute("pregunta", "¿Está seguro de abandonar la sesión?");
		modelMap.addAttribute("action", "login/logout.htm");
		modelMap.addAttribute("boton", "Continuar");
		return AfirmeNetWebConstants.MODAL;
	}
	
	/**
	 * Metodo para redireccion a enrolamiento verifed by visa y MasterCard Security
	 */
	@RequestMapping(value = "/modalEnrolamiento.htm", method = RequestMethod.GET)
	public String modalEnrolamiento(HttpServletRequest request,	ModelMap modelMap) {
		String tipo=request.getParameter("tipo");
		modelMap.addAttribute("titulo", "Salir de AfirmeNet");
		modelMap.addAttribute("mensaje", "Por motivos de seguridad, al direccionar al sitio de enrolamiento para proteger compras por internet su sesión de AfirmeNet expirará");
		modelMap.addAttribute("pregunta", "¿Está seguro de abandonar la sesión?");
		modelMap.addAttribute("action", "login/logoutEnrolamiento.htm?tipo="+tipo);
		modelMap.addAttribute("boton", "Continuar");
		return AfirmeNetWebConstants.MODAL;
	}
	
	/**
	 * Metodo para redireccion a consulta de CEP SPEI en la página de Banxico
	 */
	@RequestMapping(value = "/modalCEPSPEI.htm", method = RequestMethod.GET)
	public String modalCEPSPEI(HttpServletRequest request,	ModelMap modelMap) {
//		String tipo=request.getParameter("tipo");
		modelMap.addAttribute("titulo", "Salir de AfirmeNet");
		modelMap.addAttribute("mensaje", "Por motivos de seguridad, al direccionar al sitio de Banxico para obtener el Certificado Electronico de Pago (CEP) su sesión de AfirmeNet expirará.");
		modelMap.addAttribute("pregunta", "¿Está seguro de abandonar la sesión?");
		modelMap.addAttribute("action", "");
		modelMap.addAttribute("boton", "Continuar");
		return AfirmeNetWebConstants.MODAL;
	}
	
	/**
	 * Modal con publicidad de easy solutions
	 */
	@RequestMapping(value = "/modalEasySolutions.htm", method = RequestMethod.GET)
	public String modalEasySolutions(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("titulo", "Easy solutions");
		modelMap.addAttribute("mensaje", "Usted perderá la transacción actual y las guardadas  con anterioridad, dichas transacciones no podrán recuperarse.");
		modelMap.addAttribute("pregunta", "¿Está seguro de Cancelar la Transacción?");
		modelMap.addAttribute("action", "consultas/resumen-de-mis-cuentas.htm");
		modelMap.addAttribute("boton", "Sí, cancelar");
		return AfirmeNetWebConstants.MODAL_EASY_SOLUTIONS;
	}
		
	/**
	 * Metodo para cancelar e ir al portal
	 * @return 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/portal.htm", method = RequestMethod.GET)
	public String Portal(HttpServletRequest request, HttpServletResponse resp,	ModelMap modelMap) throws IOException {
		
		if(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.IR_PORTAL , String.class).equals("Y"))
		resp.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.PORTAL_AFIRME , String.class));

		return AfirmeNetWebConstants.MV_LOGIN;
	}
	
	/**
	 * Metodo para cancelar e ir al portal
	 * @return 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/errorHorario.htm", method = RequestMethod.POST)
	public String errorHorario(HttpServletRequest request, HttpServletResponse resp, ModelMap modelMap) throws IOException {
		modelMap.addAttribute("horario", request.getAttribute("horario"));
		return AfirmeNetWebConstants.MV_HORARIO_ERROR;
	}
	
	/**
	 * MODAL PARA AVISO
	 */
	@RequestMapping(value = "/portabilidadAviso.htm", method = RequestMethod.GET)
	public String modalPortabilidad(HttpServletRequest request,	ModelMap modelMap) {
		modelMap.addAttribute("titulo", "Solicitud de portabilidad a otros bancos");
		modelMap.addAttribute("mensaje", " ¡Usted perderá los beneficios de nómina Afirme como: banca electrónica gratis, anticipo de nómina, crédito de nómina y otros servicios!");
		modelMap.addAttribute("pregunta", "¿Está seguro de solicitar la portabilidad?");
		modelMap.addAttribute("action", "nomina/portabilidad/solicitudAfirme.htm");
		modelMap.addAttribute("boton", "Si");
		return AfirmeNetWebConstants.MV_MODAL_PORTABILIDAD;
	}
	
}
