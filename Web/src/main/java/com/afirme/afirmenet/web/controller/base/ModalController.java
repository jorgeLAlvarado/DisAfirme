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
		modelMap.addAttribute("titulo", "Cancelar la Transacci�n");
		modelMap.addAttribute("mensaje", "Usted perder� la transacci�n actual y las guardadas  con anterioridad, dichas transacciones no podr�n recuperarse.");
		modelMap.addAttribute("pregunta", "�Est� seguro de Cancelar la Transacci�n?");
		modelMap.addAttribute("action", "consultas/resumen-de-mis-cuentas.htm");
		modelMap.addAttribute("boton", "S�, cancelar");
		return AfirmeNetWebConstants.MODAL;
	}
	
	/**
	 * Metodo para cerrar sesion
	 */
	@RequestMapping(value = "/modaLogout.htm", method = RequestMethod.GET)
	public String modalLogout(HttpServletRequest request,	ModelMap modelMap) {
		modelMap.addAttribute("titulo", "Salir de AfirmeNet");
		modelMap.addAttribute("mensaje", "Al dar click en el bot�n de Continuar usted abandonar� la sesi�n de Afirmenet. Esto lo llevar� al portal de Afirme www.afirme.com.");
		modelMap.addAttribute("pregunta", "�Est� seguro de abandonar la sesi�n?");
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
		modelMap.addAttribute("mensaje", "Por motivos de seguridad, al direccionar al sitio de enrolamiento para proteger compras por internet su sesi�n de AfirmeNet expirar�");
		modelMap.addAttribute("pregunta", "�Est� seguro de abandonar la sesi�n?");
		modelMap.addAttribute("action", "login/logoutEnrolamiento.htm?tipo="+tipo);
		modelMap.addAttribute("boton", "Continuar");
		return AfirmeNetWebConstants.MODAL;
	}
	
	/**
	 * Metodo para redireccion a consulta de CEP SPEI en la p�gina de Banxico
	 */
	@RequestMapping(value = "/modalCEPSPEI.htm", method = RequestMethod.GET)
	public String modalCEPSPEI(HttpServletRequest request,	ModelMap modelMap) {
//		String tipo=request.getParameter("tipo");
		modelMap.addAttribute("titulo", "Salir de AfirmeNet");
		modelMap.addAttribute("mensaje", "Por motivos de seguridad, al direccionar al sitio de Banxico para obtener el Certificado Electronico de Pago (CEP) su sesi�n de AfirmeNet expirar�.");
		modelMap.addAttribute("pregunta", "�Est� seguro de abandonar la sesi�n?");
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
		modelMap.addAttribute("mensaje", "Usted perder� la transacci�n actual y las guardadas  con anterioridad, dichas transacciones no podr�n recuperarse.");
		modelMap.addAttribute("pregunta", "�Est� seguro de Cancelar la Transacci�n?");
		modelMap.addAttribute("action", "consultas/resumen-de-mis-cuentas.htm");
		modelMap.addAttribute("boton", "S�, cancelar");
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
		modelMap.addAttribute("mensaje", " �Usted perder� los beneficios de n�mina Afirme como: banca electr�nica gratis, anticipo de n�mina, cr�dito de n�mina y otros servicios!");
		modelMap.addAttribute("pregunta", "�Est� seguro de solicitar la portabilidad?");
		modelMap.addAttribute("action", "nomina/portabilidad/solicitudAfirme.htm");
		modelMap.addAttribute("boton", "Si");
		return AfirmeNetWebConstants.MV_MODAL_PORTABILIDAD;
	}
	
}
