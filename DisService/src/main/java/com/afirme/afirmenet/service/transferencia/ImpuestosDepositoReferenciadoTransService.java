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
public class ImpuestosDepositoReferenciadoTransService  extends Transferencia {

	ImpuestosDepositoReferenciadoTransService() {
		super(TipoTransferencia.PAGO_DE_IMPUESTOS_DEPOSITO_REFERENCIADO);
	}

	@Autowired
	@Resource(name = "depositoReferenciadoServiceImpl")
	TransferenciaAfirmeNet service;

	@Autowired
	protected ValidacionEspecificaTransferenciaService validacionEspecifica;
	
	@Override
	public TransferenciaAfirmeNet getTransferenciaService() {
		return service;
	}

	@Override
	public List<TipoValidacion> getValidaciones() {
		TipoValidacion valida[] = new TipoValidacion[] {
				TipoValidacion.FECHA, TipoValidacion.HORARIO };
		return Arrays.asList(valida);
	}

	@Override
	public List<TipoValidacionEspecial> getValidacionesEspecificas() {
		TipoValidacionEspecial valida[]=new TipoValidacionEspecial[]{TipoValidacionEspecial.PAGO_DE_IMPUESTOS_DEPOSITO_REFERENCIADO}; 
		return Arrays.asList(valida);
	}

	
	@Override
	public void valida(List<? extends TransferenciaBase> transferencias) {
		validacionEspecifica.applicaValidaticioEspecifica(transferencias, getValidacionesEspecificas(), service);
	}
}

