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
public class TelefonicaService extends Transferencia{
	
	@Autowired
	@Resource(name = "telefonicaServiceImpl")
	TransferenciaAfirmeNet service;
	
	TelefonicaService() {
		super(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_TELCEL);
	}

	@Override
	public TransferenciaAfirmeNet getTransferenciaService() {
		return service;
	}
	
	@Override
	public void valida(List<? extends TransferenciaBase> transferencias) {
		
	}

	@Override
	public List<TipoValidacion> getValidaciones() {
		TipoValidacion valida[]=new TipoValidacion[]{TipoValidacion.LIMITE_DIARIO,TipoValidacion.LIMITE_MENSUAL}; 
		return Arrays.asList(valida);
	}

	@Override
	public List<TipoValidacionEspecial> getValidacionesEspecificas() {
		return null;
	}



}
