package com.afirme.afirmenet.service.transferencia;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TipoValidacion;
import com.afirme.afirmenet.model.transferencia.TipoValidacionEspecial;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;

@Component
@Scope("prototype")
public class CashExpressService extends Transferencia{
	@Autowired
	@Resource(name = "cashExpressServiceImpl")
	TransferenciaAfirmeNet service;
	@Autowired
	protected ValidacionEspecificaTransferenciaService validacionEspecifica;

	CashExpressService() {
		super(TipoTransferencia.ORDENES_DE_PAGO_O_ENVIO_DE_DINERO);
	}

	@Override
	public TransferenciaAfirmeNet getTransferenciaService() {
		return service;
	}

	@Override
	public void valida(List<? extends TransferenciaBase> transferencias) {
		validacionEspecifica.applicaValidaticioEspecifica(transferencias, getValidacionesEspecificas(), getTransferenciaService());

	}

	@Override
	public List<TipoValidacion> getValidaciones() {
		return Arrays.asList(TipoValidacion.HORARIO, TipoValidacion.FECHA);
	}

	@Override
	public List<TipoValidacionEspecial> getValidacionesEspecificas() {
		TipoValidacionEspecial valida[]=new TipoValidacionEspecial[]{TipoValidacionEspecial.CASH_EXPRESS}; 
		return Arrays.asList(valida);
	}


}
