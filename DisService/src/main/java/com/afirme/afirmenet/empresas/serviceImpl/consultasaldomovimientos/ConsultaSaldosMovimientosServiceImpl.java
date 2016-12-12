package com.afirme.afirmenet.empresas.serviceImpl.consultasaldomovimientos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.consultamovimientoinversiones.ConsultaSaldosMovimientoInversionesDao;
import com.afirme.afirmenet.empresas.dao.consultasaldosmovimiento.ConsultaSaldosMoviemntoCuentasDao;
import com.afirme.afirmenet.empresas.dao.consultasaldosmovimientolineacredito.ConsultaSaldosMovimientoLineaCreditoDao;
import com.afirme.afirmenet.empresas.service.consultasaldosmovimientos.ConsultaSaldoMovimientoService;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;


@Service
public class ConsultaSaldosMovimientosServiceImpl implements ConsultaSaldoMovimientoService{
	
	@Autowired
	private ConsultaSaldosMoviemntoCuentasDao consultaCuentas;
	
	@Autowired
	private ConsultaSaldosMovimientoInversionesDao consultaInversion;
	
	
	private ConsultaSaldosMovimientoLineaCreditoDao consultaCredito;
	
	

	@Override
	public void consultaInversion(List<ConsultaSaldosMovimientosInversiones> consultaInversion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detalleProducto(List<ConsultaSaldosMovimientosInversiones> detalleProducto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cuentas(List<com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos> cuentas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void consultaSaldosMovimientosCuentas(
			List<com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos> consultaSaldos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ultimosMovimientos(
			List<com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos> ultimosMovimientos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movimientosMes(
			List<com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos> movimientoMes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retenidos(
			List<com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos> retenidos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buscarHistorico(
			List<com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos> buscarHistorico) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cuentasLC(List<ConsultaSaldosMovimientosLC> cuentasLC) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informacionPrestamo(List<ConsultaSaldosMovimientosLC> prestamo) {
		// TODO Auto-generated method stub
		
	}

}
