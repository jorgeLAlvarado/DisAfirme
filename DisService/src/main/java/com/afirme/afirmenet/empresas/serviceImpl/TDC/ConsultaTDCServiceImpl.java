package com.afirme.afirmenet.empresas.serviceImpl.TDC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.consultaTDC.ConsultaTDCDao;
import com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService;
import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientosTDC;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noe
 * @author Noe Galarza
 * @version 1.0.0
 */
@Service
public class ConsultaTDCServiceImpl implements ConsultaTDCService{
	
	
	@Autowired
	private ConsultaTDCDao consultaTDC;
	
	@Override
	public List<ConsulatasSaldosMovimientosTDC> consultaTDC(ConsulatasSaldosMovimientosTDC consultaSaldos) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ConsulatasSaldosMovimientosTDC> consultaDespuesCorte(
			ConsulatasSaldosMovimientosTDC consultaDespuesCorte) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ConsulatasSaldosMovimientosTDC> consultaMovimientoCorte(
			ConsulatasSaldosMovimientosTDC consultaMovimientoCorte) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ConsulatasSaldosMovimientosTDC> consultaMovimientoCorteAtras(
			ConsulatasSaldosMovimientosTDC consultaMovimientoCorteAtras) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ConsulatasSaldosMovimientosTDC> consultaPromocionesPlazos(
			ConsulatasSaldosMovimientosTDC consultaPromocionesPlazos) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
