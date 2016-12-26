package com.afirme.afirmenet.web.empresas.controller.consultas;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.afirme.afirmenet.ibs.beans.consultas.Consulta;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.Resumen;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.web.controller.transferencia.BonusTransferenciasController;
import com.afirme.afirmenet.web.empresas.controller.base.BaseController;
import com.afirme.afirmenet.web.model.AfirmeNetUser;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

@Controller
@RequestMapping("/consultas")
@SessionAttributes({ "consulta" })
public class PuntosBonusController extends BaseController{
	
	private static final AfirmeNetLog LOG = new 
			AfirmeNetLog(BonusTransferenciasController.class);
	
	@RequestMapping(value = "/consultabonus.htm")
	public String cuentasTransferencia(HttpServletRequest request,
			ModelMap modelMap) {
		LOG.debug("Atendiendo Peticion = /consultas/consultabonus.htm");
		
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		
		List<Cuenta> cuentasTodas = cuentaService.getCuentas(
				afirmeNetUser.getContrato(),
				afirmeNetUser.getCuentasExcluyentes(), true);
		List<Cuenta> cuentas = cuentaService.segmentaCuentasPorTipo(
				Cuenta.TIPO_CUENTA, cuentasTodas);
		List<Cuenta> creditos = cuentaService.segmentaCuentasPorTipo(
				Cuenta.TIPO_CREDITO, cuentasTodas);
		List<Cuenta> inversiones = cuentaService.segmentaCuentasPorTipo(
				Cuenta.TIPO_INVERSION, cuentasTodas);
		List<Resumen> resumen = new ArrayList<Resumen>();
		Resumen resumenCuentas = cuentaService.calculaResumenPorCuenta(
				Cuenta.TIPO_CUENTA, cuentas);
		Resumen resumenCreditos = cuentaService.calculaResumenPorCuenta(
				Cuenta.TIPO_CREDITO, creditos);
		Resumen resumenInversiones = cuentaService.calculaResumenPorCuenta(
				Cuenta.TIPO_INVERSION, inversiones);
		if (resumenCuentas.getTieneCuentas() > 0)
			resumen.add(resumenCuentas);
		if (resumenCreditos.getTieneCuentas() > 0)
			resumen.add(resumenCreditos);
		if (resumenInversiones.getTieneCuentas() > 0)
			resumen.add(resumenInversiones);
		modelMap.addAttribute("cuentas", cuentas);
		modelMap.addAttribute("creditos", creditos);
		modelMap.addAttribute("inversiones", inversiones);
		modelMap.addAttribute("resumen", resumen);
		request.getSession().setAttribute(
				AfirmeNetWebConstants.CUENTAS_SESSION, cuentas);
		request.getSession().setAttribute(
				AfirmeNetWebConstants.INVERSIONES_SESSION, inversiones);
		request.getSession().setAttribute(
				AfirmeNetWebConstants.CREDITOS_SESSION, creditos);

		List<Cuenta> cuentasBonus = FluentIterable
				.from(cuentasTodas)
				.filter(new Predicate<Cuenta>() {
					public boolean apply(Cuenta cuenta) {
						return "BON".equalsIgnoreCase(cuenta.getType());
					}
				}).toList();
		
		for(Cuenta bonus: cuentasBonus){
			bonus.setCusNumber(Util.getDigitoVerificador("4130980"+bonus.getNumber()));
		}
		
		if( cuentasBonus != null && cuentasBonus.isEmpty() ){
			modelMap.addAttribute( "errorNoCuentasBonus", Util.getPropertyString("afirmenet.consultas.bonus.resumen.err_nocuentasbonus") );
		}
		
		modelMap.addAttribute("consulta", new Consulta() );
		modelMap.addAttribute("cuentasBonus", cuentasBonus);
		return AfirmeNetWebConstants.MV_CONSULTAS_BONUS;
	}

}
