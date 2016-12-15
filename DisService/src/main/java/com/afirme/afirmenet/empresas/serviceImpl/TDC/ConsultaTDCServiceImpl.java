package com.afirme.afirmenet.empresas.serviceImpl.TDC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.consultaTDC.ConsultaTDCDao;
import com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService;
import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientos;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noe
 * @author Noe Galarza
 * @version 1.0.0
 */
@Service
public class ConsultaTDCServiceImpl implements ConsultaTDCService{
	
	
	@Autowired
	private ConsultaTDCDao consultaTDC;
	
	public String consultaTDC(List<ConsulatasSaldosMovimientos> consultaSaldos) {
		return null;
		
	}
	public String consultaDespuesCorte(List<ConsulatasSaldosMovimientos> consultaDespuesCorte) {
		return null;
		
	}
	public String consultaMovimientoCorte(List<ConsulatasSaldosMovimientos> consultaMovimientoCorte) {
		return null;
		
	}
	public String consultaMovimientoCorteAtras(List<ConsulatasSaldosMovimientos> consultaMovimientoCorteAtras) {
		return null;
		
	}
	public String consultaPromocionesPlazos(List<ConsulatasSaldosMovimientos> consultaPromocionesPlazos) {
		return null;
		
	}

}
