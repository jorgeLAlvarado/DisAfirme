package com.afirme.afirmenet.web.empresas.controller.TDC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService;
import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientos;

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
	List<ConsulatasSaldosMovimientos> obtenerConsulta(){
		return null;
	}

	

}
