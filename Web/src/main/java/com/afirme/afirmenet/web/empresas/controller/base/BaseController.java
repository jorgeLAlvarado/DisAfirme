package com.afirme.afirmenet.web.empresas.controller.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.afirme.afirmenet.empresas.service.consultas.CuentaService;
import com.afirme.afirmenet.empresas.service.consultas.InversionService;
import com.afirme.afirmenet.empresas.service.token.BasicoSinTokenService;
import com.afirme.afirmenet.empresas.service.token.TokenService;
import com.afirme.afirmenet.empresas.service.transferencia.ValidacionTransferenciaService;
import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.exception.AfirmeNetSessionExpiredException;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.FondoInversion;
import com.afirme.afirmenet.ibs.beans.consultas.Resumen;
import com.afirme.afirmenet.model.base.TokenModel;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.time.TimeUtils;
import com.afirme.afirmenet.web.config.InitConfig;
import com.afirme.afirmenet.model.AfirmeNetUser;
import com.afirme.afirmenet.web.util;

/**
 * Super clase {@link @Controller} que encapsula atributos generales que seran
 * utilizados por todos los controladores, dentro de estos atributos se pueden
 * listar:
 * 
 * <ul>
 * <li>{@link MessageSource}: Archivo .properties que contiene un informacion
 * basada en un locale especifico</li>
 * <li>{@link Locale}: Es un objecto que representa una ubicacion geografica
 * especifica en java</li>
 * </ul>
 * 
 * @author jorge.canoc@gmail.com
 * @tag Start date: (23/03/15 11:03:39 AM)
 */

public class BaseController {

	@Autowired
	public MessageSource messageSource;
	@Autowired
	protected TokenService tokenService;
	
	@Autowired
	protected BasicoSinTokenService basicoSinTokenService;
	
	@Autowired
	protected ValidacionTransferenciaService validaHorario;

	@Autowired
	protected CuentaService cuentaService;
	@Autowired
	protected InversionService inversionService;

	public Locale locale = LocaleContextHolder.getLocale();

	/**
	 * Metodo que permite la obtencion de los mensajes del archido de recursos
	 * en el idioma que corresponda, asi como el paso de valores cuando existan
	 * place holders en el mensaje.
	 * 
	 * @param properties
	 * @param values
	 * @return El mensaje correspondiente
	 */
	public String getMessage(String properties, Object[] values) {
		return messageSource.getMessage(properties, values, locale);
	}

	/**
	 * Metodo que permite la obtencion de los mensajes del archido de recursos
	 * en el idioma que corresponda
	 * 
	 * @param properties
	 * @return El mensaje correspondiente
	 */

	public String getMessage(String properties) {
		return this.getMessage(properties, null);
	}

	/**
	 * Metodo que obtienen el usuario de la sesion actual
	 * 
	 * @param request
	 * @return
	 */
	public AfirmeNetUser getSessionUser(HttpServletRequest request) {
		AfirmeNetUser user = (AfirmeNetUser) request.getSession().getAttribute(
				AfirmeNetWebConstants.USUARIO_SESSION);
		if (user == null) {
			throw new AfirmeNetSessionExpiredException("0000", "Session Expiro");
		}
		return user;
	}

	public void validaConfiguracion() {
		if (!AfirmeNetConstants.configuracionCargada) {
			InitConfig init = new InitConfig();
			init.onInit();
		}
	}

	
	/**
	 * Funcion general para la implementacion de la validacion de token
	 * @param passCode
	 * @param contrato
	 * @param usuario
	 * @param basicoSinToken
	 * @return
	 */
	public boolean validaToken(String passCode, AfirmeNetUser afirmeNetUser, ModelMap modelMap) {
		
		TokenModel respToken=new TokenModel();
		
		if(afirmeNetUser.isBasicoSinToken()){
			int intentos=modelMap.get("intentosToken")==null || modelMap.get("intentosToken").toString().equals("")?0:Integer.parseInt(modelMap.get("intentosToken").toString());
			respToken =  basicoSinTokenService.validaClave(passCode, afirmeNetUser.getContrato(),
										afirmeNetUser.getContrato(),intentos);
			if(!respToken.isValido()){
			modelMap.addAttribute("intentosToken", respToken.getIntentos());
			modelMap.addAttribute("erroresToken", respToken.getErrores());
			}
			
		}else{		
			int intentos=modelMap.get("intentosToken")==null || modelMap.get("intentosToken").toString().equals("")?0:Integer.parseInt(modelMap.get("intentosToken").toString());
			respToken =  tokenService.validaClave(passCode, afirmeNetUser.getContrato(),afirmeNetUser.getContrato(),
			        				afirmeNetUser.isBasicoSinToken(),afirmeNetUser.getTokenRSA(),intentos);
			if(!respToken.isValido()){
				modelMap.addAttribute("intentosToken", respToken.getIntentos());
				modelMap.addAttribute("erroresToken", respToken.getErrores());
			}
		}
		
		return respToken.isValido();
	}
	
	public List<Cuenta> getCuentasPropias(AfirmeNetUser afirmeNetUser) {

		List<Cuenta> listaCuentasPropias = cuentaService.getCuentasPropias(
				getCuentas(afirmeNetUser),
				AfirmeNetWebConstants.CUENTAS_PROPIAS);

		return listaCuentasPropias;
	}
	
	public List<Cuenta> getCuentasInversionesPropias(AfirmeNetUser afirmeNetUser) {

		List<Cuenta> listaCuentasPropias = cuentaService.getCuentasPropias(
				getCuentas(afirmeNetUser),
				AfirmeNetWebConstants.CUENTAS_INVERSIONES_PROPIAS);

		return listaCuentasPropias;
	}

	public List<Cuenta> getCuentasPropiasMXP(AfirmeNetUser afirmeNetUser) {

		List<Cuenta> listaCuentasPropias = cuentaService.getCuentasPropiasMXP(
				getCuentas(afirmeNetUser),
				AfirmeNetWebConstants.CUENTAS_PROPIAS);

		return listaCuentasPropias;
	}
	public List<Cuenta> getCuentasCardif(AfirmeNetUser afirmeNetUser) {

		List<Cuenta> listaCuentasPropias = cuentaService.getCuentasClienteService(afirmeNetUser.getNumCliente(), cuentaService.getCuentasPropiasExcluyentesMXP(
				getCuentas(afirmeNetUser),
				AfirmeNetWebConstants.CUENTAS_CARDIF_NO_VALIDOS));
		List<Cuenta> listaCuentasPropiasCardif=new ArrayList<Cuenta>();
		for (Cuenta cuenta : listaCuentasPropias) {
			if(!cuentaService.esCuentasJunior(cuenta.getNumber()))
				listaCuentasPropiasCardif.add(cuenta);
		}

		return listaCuentasPropiasCardif;
	}

	public List<Cuenta> getCuentas(AfirmeNetUser afirmeNetUser) {
		List<Cuenta> listaCuentas = cuentaService.getCuentas(
				afirmeNetUser.getContrato(),
				afirmeNetUser.getCuentasExcluyentes(), false);
		return listaCuentas;
	}
	
	public List<Cuenta> calculaResumeCuentas(AfirmeNetUser usuario, ModelMap modelMap){
		//Carga informacion de cuentas
		List<Cuenta> cuentasTodasVisibles=cuentaService.getCuentas(usuario.getContrato(), usuario.getCuentasExcluyentes(), false);
		List<Cuenta> cuentasTodas = cuentaService.segmentaCuentasPorVisible(cuentasTodasVisibles);		
		List<Cuenta> cuentas=cuentaService.segmentaCuentasPorTipo(Cuenta.TIPO_CUENTA, cuentasTodas);
		List<Cuenta> creditos=cuentaService.segmentaCuentasPorTipo(Cuenta.TIPO_CREDITO, cuentasTodas);
		List<Cuenta> inversiones=cuentaService.segmentaCuentasPorTipo(Cuenta.TIPO_INVERSION, cuentasTodas);
		 List<FondoInversion> fondosInversion = inversionService.getFondosInversion( usuario.getNumCliente() );
		 cuentaService.setFondosInversionAInversiones(inversiones, fondosInversion);
		List<Resumen> resumen=new ArrayList<Resumen>();
		Resumen resumenCuentas=cuentaService.calculaResumenPorCuenta(Cuenta.TIPO_CUENTA, cuentas);
		Resumen resumenCreditos=cuentaService.calculaResumenPorCuenta(Cuenta.TIPO_CREDITO, creditos);
		Resumen resumenInversiones=cuentaService.calculaResumenPorCuenta(Cuenta.TIPO_INVERSION, inversiones);
		if(resumenCuentas.getTieneCuentas()>0)
			resumen.add(resumenCuentas);
		if(resumenCreditos.getTieneCuentas()>0)
			resumen.add(resumenCreditos);
		if(resumenInversiones.getTieneCuentas()>0)
			resumen.add(resumenInversiones);
		modelMap.addAttribute("resumen", resumen);
		return cuentas;
	}
	
	
	public String validaHorarioGeneral(TipoTransferencia tipo, HttpServletRequest request,
				HttpServletResponse response){
		
		TransferenciaBase horario= new TransferenciaBase();
		String fechaDia=TimeUtils.getDateFormat(new Date(), "dd/MMMM/yyyy")+" 00:00";
		Date fecha = TimeUtils.getDate(fechaDia, "dd/MMMM/yyyy HH:mm");		
		horario.setValidationDate(TimeUtils.getDateFormat(fecha,
				"dd/MM/yy"));
		horario.setValidationYear(TimeUtils.getDateFormat(fecha, "yy"));
		horario.setValidationMonth(TimeUtils.getDateFormat(fecha, "MM"));
		horario.setValidationDay(TimeUtils.getDateFormat(fecha, "dd"));
		horario.setValidationHour(TimeUtils.getDateFormat(fecha, "HH"));
		horario.setValidationMinute(TimeUtils.getDateFormat(fecha, "mm"));
		
		
		if(!validaHorario.isValidTime(horario, tipo)){
			request.setAttribute("horario", AfirmeNetConstants.horarios.get(tipo).toString());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/errorHorario.htm");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return AfirmeNetConstants.horarios.get(tipo).toString();
	}
	
	public String validaHorarioConProgramacion(ModelMap modelMap,TipoTransferencia tipo){
	
	TransferenciaBase horario= new TransferenciaBase();
	String fechaDia=TimeUtils.getDateFormat(new Date(), "dd/MMMM/yyyy")+" 00:00";
	Date fecha = TimeUtils.getDate(fechaDia, "dd/MMMM/yyyy HH:mm");		
	horario.setValidationDate(TimeUtils.getDateFormat(fecha,
			"dd/MM/yy"));
	horario.setValidationYear(TimeUtils.getDateFormat(fecha, "yy"));
	horario.setValidationMonth(TimeUtils.getDateFormat(fecha, "MM"));
	horario.setValidationDay(TimeUtils.getDateFormat(fecha, "dd"));
	horario.setValidationHour(TimeUtils.getDateFormat(fecha, "HH"));
	horario.setValidationMinute(TimeUtils.getDateFormat(fecha, "mm"));
	
	
	if(!validaHorario.isValidTime(horario, tipo)){
		Map<CodigoExcepcion, String> advertencias = new HashMap<CodigoExcepcion, String>(0);
		advertencias.put(CodigoExcepcion.ERR_3005, getMessage("afirmenet.ERR_3005"));
		modelMap.addAttribute("advertencias", advertencias);
	}
	return AfirmeNetConstants.horarios.get(tipo).toString();
}
	
	public List<?> validaCuentasGeneral(ModelMap modelMap,List<?> cuentas){
		
		if(cuentas.isEmpty()){
			Map<CodigoExcepcion, String> advertencias = new HashMap<CodigoExcepcion, String>(0);
			advertencias.put(CodigoExcepcion.ERR_3004, getMessage("afirmenet.ERR_3004"));
			modelMap.addAttribute("advertencias", advertencias);
		}	
		
		return cuentas;

	}
	
	public List<?> validaCuentasInversion(ModelMap modelMap,List<?> cuentas){
		
		if(cuentas.isEmpty()){
			Map<CodigoExcepcion, String> advertencias = new HashMap<CodigoExcepcion, String>(0);
			advertencias.put(CodigoExcepcion.ERR_3007, getMessage("afirmenet.ERR_3007"));
			modelMap.addAttribute("advertencias", advertencias);
		}	
		
		return cuentas;

	}

	public List<Cuenta> getCuentasSinBonus(AfirmeNetUser afirmeNetUser) {

		List<Cuenta> listaCuentasPropias = 
				getCuentas(afirmeNetUser);
		List<Cuenta> listaCuentasPropiasSinBonus=new ArrayList<Cuenta>();
		for (Cuenta cuenta : listaCuentasPropias) {
			if(AfirmeNetWebConstants.CUENTAS_INVERSIONES_PROPIAS.contains(cuenta.getType()) && !cuenta.getNumber().trim().substring(0,3).equals("900"))
				listaCuentasPropiasSinBonus.add(cuenta);
		}

		return listaCuentasPropiasSinBonus;
	}
}
