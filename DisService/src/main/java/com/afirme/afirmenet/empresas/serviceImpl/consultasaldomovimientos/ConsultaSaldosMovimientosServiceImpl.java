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
	
	

	public void consultaInversion(List<ConsultaSaldosMovimientosInversiones> consultaInversion) {
		
	}

	public void detalleProducto(List<ConsultaSaldosMovimientosInversiones> detalleProducto) {
		
	}

	public void cuentas(List<ConsultaSaldosMovimientos> cuentas) {
		
	}

	public void consultaSaldosMovimientosCuentas(List<ConsultaSaldosMovimientos> consultaSaldos) {
		
	}

	public void ultimosMovimientos(List<ConsultaSaldosMovimientos> ultimosMovimientos) {
		
	}

	public void movimientosMes(List<ConsultaSaldosMovimientos> movimientoMes) {
		
	}

	public void retenidos(List<ConsultaSaldosMovimientos> retenidos) {
		
	}

	public void buscarHistorico(List<ConsultaSaldosMovimientos> buscarHistorico) {
		
	}

	public void cuentasLC(List<ConsultaSaldosMovimientosLC> cuentasLC) {
		
	}

	public void informacionPrestamo(List<ConsultaSaldosMovimientosLC> prestamo) {
		
	}

}
