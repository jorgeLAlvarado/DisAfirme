package com.afirme.afirmenet.web.empresas.controller.TDC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService;

/**
 * @author Noe
 *
 */
@Controller
public class ConsultaTDCController {
	
	
	@Autowired
	private ConsultaTDCService consultaSueldo;
	

	
	
	/**
	 * @return
	 */
	/*public String detalleInversion()
	List<ConsulatasSaldosMovimientos> obtenerConsulta(){
		return null;
	}
*/
	@RequestMapping(value = "/ConsultaTDC.htm")
	public String obtenerConsulta(){
		
		return null;}
	

}
