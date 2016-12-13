package com.afirme.afirmenet.web.controller.acceso;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.service.acceso.LogInService;
import com.afirme.afirmenet.service.log.LogService;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.base.BaseController;
import com.afirme.afirmenet.web.model.AfirmeNetUser;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

@Controller
@RequestMapping("/login")
public class LogoutController extends BaseController {

	static final AfirmeNetLog LOG = new AfirmeNetLog(LoginController.class);

	@Autowired
	private LogInService logInService;
	@Autowired
	private LogService logService;

	@RequestMapping(value = "/logout.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutSeguro(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.setLogInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Inicia Salida", request.getRemoteAddr());
		LOG.info("Atendiendo Peticion = /login/contrato.htm contrato ===> "
				+ afirmeNetUser.getContrato());
		logService.getAddLogPer(Util.getRefNum(),
				afirmeNetUser.getContrato(), "LOGOUT", "LOGOUT OK", afirmeNetUser.getContrato(),
				"0.00", "", "OKL", Util.getRefNum());

		logInService.updateINOUT(afirmeNetUser.getContrato(), "N");
		logInService.updateBMUser(afirmeNetUser.getContrato(), "N");
		
		request.getSession().invalidate();

		LOG.setLogInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Fin Salida", request.getRemoteAddr());
		
		if(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.IR_PORTAL , String.class).equals("Y"))
		resp.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.PORTAL_AFIRME , String.class));
		
		return AfirmeNetWebConstants.MV_LOGIN;
	}

	
	@RequestMapping(value = "/logoutEnrolamiento.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutSeguroEnrolamiento(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.setLogInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Inicia Salida", request.getRemoteAddr());
		LOG.info("Atendiendo Peticion = /login/contrato.htm contrato ===> "
				+ afirmeNetUser.getContrato());
		logService.getAddLogPer(Util.getRefNum(),
				afirmeNetUser.getContrato(), "LOGOUT", "LOGOUT OK", afirmeNetUser.getContrato(),
				"0.00", "", "OKL", Util.getRefNum());

		logInService.updateINOUT(afirmeNetUser.getContrato(), "N");
		logInService.updateBMUser(afirmeNetUser.getContrato(), "N");
		
		request.getSession().invalidate();

		LOG.setLogInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Fin Salida", request.getRemoteAddr());
		
		if(request.getParameter("tipo").equals("VISA"))
			resp.sendRedirect("https://secure4.arcot.com/vpas/afirme_vbv/enroll/index.jsp?locale=es_ES&bankid=11277");
		else
			resp.sendRedirect("https://secure4.arcot.com/vpas/afirme_mc/enroll/index.jsp?locale=es_ES&bankid=11281");
		
		return AfirmeNetWebConstants.MV_LOGIN;
	}
	
	@RequestMapping(value = "/CEP-SPEI.htm", method = RequestMethod.POST)
	public String cepSPEI(HttpServletRequest request, ModelMap modelMap) {				
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.setLogInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Inicia Salida", request.getRemoteAddr());
		LOG.debug("Atendiendo Peticion = /login/contrato.htm contrato ===> "
				+ afirmeNetUser.getContrato());
		logService.getAddLogPer(Util.getRefNum(),
				afirmeNetUser.getContrato(), "LOGOUT", "LOGOUT OK", afirmeNetUser.getContrato(),
				"0.00", "", "OKL", Util.getRefNum());

		logInService.updateINOUT(afirmeNetUser.getContrato(), "N");
		logInService.updateBMUser(afirmeNetUser.getContrato(), "N");
		
		request.getSession().invalidate();

		LOG.setLogInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Fin Salida", request.getRemoteAddr());
		
		String i = "40062",  //institucion Afirme
			   s = AfirmeNetConstants.getValorConfigPersonas( ConfigPersonas.CEP_SERIE ),
			   d = request.getParameter("d"),
			   urlBanxico = AfirmeNetConstants.getValorConfigPersonas( ConfigPersonas.CEP_URL ); 
	    modelMap.put("i", i);
	    modelMap.put("s", s);
	    modelMap.put("d", d);
		modelMap.put("urlBanxico", urlBanxico);
		
		return AfirmeNetWebConstants.MV_CEP_SPEI;		
	}
	
	@RequestMapping(value = "/logoutVersionClasica.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logOutVersionClasica(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resp) throws IOException {
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.setLogInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Inicia Salida a version clasica", request.getRemoteAddr());
		LOG.info("Atendiendo Peticion = /login/contrato.htm contrato ===> "
				+ afirmeNetUser.getContrato());
		logService.getAddLogPer(Util.getRefNum(),
				afirmeNetUser.getContrato(), "LOGOUT", "LOGOUT OK", afirmeNetUser.getContrato(),
				"0.00", "", "OKL", Util.getRefNum());

		logInService.updateINOUT(afirmeNetUser.getContrato(), "N");
		logInService.updateBMUser(afirmeNetUser.getContrato(), "N");
		
		String contrato=afirmeNetUser.getContrato();
		
		request.getSession().invalidate();

		LOG.setLogInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Fin Salida", request.getRemoteAddr());
		
		resp.sendRedirect(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.URL_VERSION_CLASICA , String.class)+"?UserId="+contrato+"&portal=1");
		
		return AfirmeNetWebConstants.MV_LOGIN;
	}
	
}
