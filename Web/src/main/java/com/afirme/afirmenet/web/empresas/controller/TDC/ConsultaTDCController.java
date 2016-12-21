package com.afirme.afirmenet.web.empresas.controller.TDC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.acceso.ControlAcceso;

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
	
	
	//private static final Logger LOG = LoggerFactory.getLogger(ConsultaTDCController.class); 
	static final AfirmeNetLog LOG = new AfirmeNetLog(ConsultaTDCController.class);
	
	@Autowired
	private ConsultaTDCService consultaTDCService;
	

	
	
	/**
	 * @return
	 */
	/*public String detalleInversion()
	List<ConsulatasSaldosMovimientos> obtenerConsulta(){
		return null;
	}
*/
	/**
	 * @return
	 */
	@RequestMapping(value = "/consulta_tdc.htm")
	public String obtenerConsulta(){
		
		
		LOG.info("<< obtenerConsulta()");
		LOG.info(">> obtenerConsulta()");
		return null;
		}
	

}
