package com.afirme.afirmenet.service.impl.consultas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.consultas.HistorialDao;
import com.afirme.afirmenet.service.consultas.HistorialService;
import com.afirme.afirmenet.utils.time.TimeUtils;


@Service
public class HistorialServiceImpl implements HistorialService {

	@Autowired
	private HistorialDao historialDao;
	
	
	
	public List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken) {
		return null;
	}
	
	
	/**
	 * Devuelve una lista de TransferenciaBase que es el tipo de objeto para mostrar la lista de resultados.
	 */
	@SuppressWarnings("unchecked")
	public List<TransferenciaBase> buscaTransferencias(String contrato, List<Cuenta> cuentas, HistorialTipo tipo, Date fechaDesde, Date fechaHasta) {
		
		return null;
	}

	
}
