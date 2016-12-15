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

}
