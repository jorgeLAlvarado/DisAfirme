package com.afirme.afirmenet.web.empresas.controller.TDC;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos;
import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientosTDC;
import com.afirme.afirmenet.web.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.acceso.ControlAcceso;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

/**
 * Controller para generar las consultas de TDC.
 * <br><br>
 * @author Noe Galarza
 *
 *Modificado on dic 14, 2016 3:12:21 PM by Noe Galarza
 * @version 1.0.0
 */
@Controller
@RequestMapping(value= "/consulta_saldos_movimiento_tdc")
public class ConsultaTDCController {
	
	

	static final AfirmeNetLog LOG = new AfirmeNetLog(ConsultaTDCController.class);
	
	@Autowired
	private ConsultaTDCService consultaTDCService;
	

	
	


	/**
	 *  Realiza la consulta de sueldos
	 * 
	 * @return AfirmeNetWebConstants.PRUEBA
	 */
	@RequestMapping(value = "/consulta_tdc.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String obtenerConsulta(){
		
		
		LOG.info("<< obtenerConsulta()");
		
		ConsulatasSaldosMovimientosTDC consultaMovimiento = new ConsulatasSaldosMovimientosTDC();
		consultaMovimiento.setNombre(null);
		consultaMovimiento.setNumerTarjeta(0);
		List<ConsulatasSaldosMovimientosTDC> consultaTDC = consultaTDCService.consultaTDC(consultaMovimiento);
		
		LOG.info(">> obtenerConsulta()");
		return AfirmeNetWebConstants.MV_CONSULTA_TDC;
		}


	
	/**
	 * Consulta los movimientos despues de corte
	 * 
	 * @returnAfirmeNetWebConstants.PRUEBA;
	 */
	@RequestMapping(value = "/consulta_tdc_despues_corte.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String obtenerMovimientoDespuesCorte(){
		
		
		LOG.info("<< obtenerMovimientoDespuesCorte()");
		
		ConsulatasSaldosMovimientosTDC consultaMovimiento = new ConsulatasSaldosMovimientosTDC();
		consultaMovimiento.setNombre(null);
		consultaMovimiento.setNumerTarjeta(0);
		List<ConsulatasSaldosMovimientosTDC> consultaTDC = consultaTDCService. consultaDespuesCorte(consultaMovimiento);
		
		LOG.info(">> obtenerMovimientoDespuesCorte()");
		return AfirmeNetWebConstants.MV_CONSULTA_MOVIMIENTO_DESPUES_CORTE;
		}
	
	/**
	 * consulta los movimientos tipo corte
	 * 
	 * @return AfirmeNetWebConstants.PRUEBA;
	 */
	@RequestMapping(value = "/consulta_tdc_despues_corte.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String obtenerMovimientoCorte(){
		
		
		LOG.info("<< obtenerMovimientoCorte()");
		
		ConsulatasSaldosMovimientosTDC consultaMovimiento = new ConsulatasSaldosMovimientosTDC();
		consultaMovimiento.setNombre(null);
		consultaMovimiento.setNumerTarjeta(0);
		List<ConsulatasSaldosMovimientosTDC> consultaTDC = consultaTDCService.consultaMovimientoCorte(consultaMovimiento);
		
		LOG.info(">> obtenerMovimientoCorte()");
		return AfirmeNetWebConstants.MV_CONSULTA_MOVIMIENTO_DESPUES_CORTE;
		}
	
	
	/**
	 * consulta lo moviminetos corte atras
	 * 
	 * @return AfirmeNetWebConstants.PRUEBA;
	 */
	@RequestMapping(value = "/consulta_tdc_despues_corte_atras.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String obtenerMovimientoCorteAtras(){
		
		
		LOG.info("<< obtenerMovimientoCorteAtras()");
		
		ConsulatasSaldosMovimientosTDC consultaMovimiento = new ConsulatasSaldosMovimientosTDC();
		consultaMovimiento.setNombre(null);
		consultaMovimiento.setNumerTarjeta(0);
		List<ConsulatasSaldosMovimientosTDC> consultaTDC = consultaTDCService.consultaMovimientoCorteAtras(consultaMovimiento);
		
		LOG.info(">> obtenerMovimientoCorteAtras()");
		return AfirmeNetWebConstants.MV_CONSULTA_MOVIMIENTO_CORTE_ATRAS;
		}
	
	
	/**
	 * Consulta Promociones plazo
	 * 
	 * @return AfirmeNetWebConstants.PRUEBA;
	 */
	@RequestMapping(value = "/consulta_tdc_promociones.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String obtenerPromocionesPlazos(){
		
		
		LOG.info("<<obtenerPromocionesPlazos()");
		
		ConsulatasSaldosMovimientosTDC consultaMovimiento = new ConsulatasSaldosMovimientosTDC();
		consultaMovimiento.setNombre(null);
		consultaMovimiento.setNumerTarjeta(0);
		List<ConsulatasSaldosMovimientosTDC> consultaTDC = consultaTDCService.consultaPromocionesPlazos(consultaMovimiento);
		
		LOG.info(">>obtenerPromocionesPlazos()");
		return AfirmeNetWebConstants.MV_CONSULTA_PROMOCIONES_PLAZO;
		}
	
	

}
