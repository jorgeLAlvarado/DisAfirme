package com.afirme.afirmenet.empresas.serviceImpl.TDC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.consultaTDC.ConsultaTDCDao;
import com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService;
import com.afirme.afirmenet.model.tdc.ConsulatasSaldosMovimientos;

/**
 * @author Noe
 *
 */
@Service
public class ConsultaTDCServiceImpl implements ConsultaTDCService{
	
	
	@Autowired
	private ConsultaTDCDao consultaTDC;

	/* (non-Javadoc)
	 * @see com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService#consultaTDC(java.util.List)
	 */
	@Override
	public void consultaTDC(List<ConsulatasSaldosMovimientos> consultaSaldos) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService#consultaDespuesCorte(java.util.List)
	 */
	@Override
	public void consultaDespuesCorte(List<ConsulatasSaldosMovimientos> consultaDespuesCorte) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService#consultaMovimientoCorte(java.util.List)
	 */
	@Override
	public void consultaMovimientoCorte(List<ConsulatasSaldosMovimientos> consultaMovimientoCorte) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService#consultaMovimientoCorteAtras(java.util.List)
	 */
	@Override
	public void consultaMovimientoCorteAtras(List<ConsulatasSaldosMovimientos> consultaMovimientoCorteAtras) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.afirme.afirmenet.empresas.service.TDC.ConsultaTDCService#consultaPromocionesPlazos(java.util.List)
	 */
	
	@Override
	public void consultaPromocionesPlazos(List<ConsulatasSaldosMovimientos> consultaPromocionesPlazos) {
		// TODO Auto-generated method stub
		
	}

}
