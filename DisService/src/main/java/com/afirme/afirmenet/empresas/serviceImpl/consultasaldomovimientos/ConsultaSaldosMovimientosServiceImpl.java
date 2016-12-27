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
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos;


/**
 * Consultas de saldos y movimientos
 * Created on Dic 14, 2016 3:39:05 PM by Noe
 * @author Noe Galarza
 * @version 1.0.0
 */
@Service
public class ConsultaSaldosMovimientosServiceImpl implements ConsultaSaldoMovimientoService{
	
	@Autowired
	private ConsultaSaldosMoviemntoCuentasDao consultaCuentas;
	
	@Autowired
	private ConsultaSaldosMovimientoInversionesDao consultaInversion;
	
	
	private ConsultaSaldosMovimientoLineaCreditoDao consultaCredito;
	
	

	public String consultaInversion(List<ConsultaSaldosMovimientosInversiones> consultaInversion) {
		return null;
		
	}

	public String detalleProducto(List<ConsultaSaldosMovimientosInversiones> detalleProducto) {
		return null;
		
	}

	public String cuentas(List<ConsultaSaldosMovimientos> cuentas) {
		return null;
		
	}

	public String consultaSaldosMovimientosCuentas(List<ConsultaSaldosMovimientos> consultaSaldos) {
		return null;
		
	}

	public String ultimosMovimientos(List<ConsultaSaldosMovimientos> ultimosMovimientos) {
		return null;
		
	}

	public String movimientosMes(List<ConsultaSaldosMovimientos> movimientoMes) {
		return null;
		
	}

	public String retenidos(List<ConsultaSaldosMovimientos> retenidos) {
		return null;
		
	}

	public String buscarHistorico(List<ConsultaSaldosMovimientos> buscarHistorico) {
		return null;
		
	}

	public String cuentasLC(List<ConsultaSaldosMovimientosLC> cuentasLC) {
		return null;
		
	}

	public String informacionPrestamo(List<ConsultaSaldosMovimientosLC> prestamo) {
		return null;
		
	}

	@Override
	public List<ConsultaSaldosMovimientosInversiones> consultaInversion(
			ConsultaSaldosMovimientosInversiones consultaInversion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientosInversiones> detalleProducto(
			ConsultaSaldosMovimientosInversiones detalleProducto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientos> cuentas(ConsultaSaldosMovimientos cuentas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientos> consultaSaldos(ConsultaSaldosMovimientos consultaSaldos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientos> ultimosMovimientos(ConsultaSaldosMovimientos ultimosMovimientos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientos> movimientosMes(ConsultaSaldosMovimientos moviminetoMes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientos> retenidos(ConsultaSaldosMovimientos retenidos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientos> buscarHistorico(ConsultaSaldosMovimientos buscarHistorico) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientosLC> cuentasLC(ConsultaSaldosMovimientosLC cuentasLC) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaSaldosMovimientosLC> informacionPrestamo(ConsultaSaldosMovimientosLC prestamo) {
		// TODO Auto-generated method stub
		return null;
	}

}
