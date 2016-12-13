package com.afirme.afirmenet.service.impl.consultas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.consultas.HistorialDao;
import com.afirme.afirmenet.utils.time.TimeUtils;


@Service
public class HistorialServiceImpl implements HistorialService {

	@Autowired
	private HistorialDao historialDao;
	
	@Autowired
	private AsociaMovilCuentaService asociaMovilCuentaService;
	
	@Autowired
	private ComprobanteTransferenciaDao comprobanteTransferenciaDao;
	
	@Autowired
	private OperacionesProgramadasDao operacionesProgramadasDao;

	@Autowired
	ConveniosDomiciliacionDao conveniosDao;
	
	@Autowired
	CuentaTercerosDao cuentaTercerosDao;
	
	@Autowired
	private ImpuestosGDFDao impuestoGDFDao;
	
	@Autowired
	private ImpuestosGDFService impuestosGDFService;
	
	
	public List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken) {
		return null;
	}
	
	@Override
	public List<String> categorias(boolean esBasicoSinToken) {
		return null;
	}

	/**
	 * Devuelve una lista de TransferenciaBase que es el tipo de objeto para mostrar la lista de resultados.
	 */
	@SuppressWarnings("unchecked")
	public List<TransferenciaBase> buscaTransferencias(String contrato, List<Cuenta> cuentas, HistorialTipo tipo, Date fechaDesde, Date fechaHasta) {
		
		return null;
	}

	@Override
	public void obtenerInformacionExtra(TransferenciaBase comprobante) {
		
		return null;
	}

	@Override
	public ConvenioDomiciliacion buscarInfoExtraConvenioServicios(
			String contrato, String referencia) {
		
		return null;
	}
	
	@Override
	public ImpuestoGDF getComprobanteGDF(TransferenciaBase comprobante){
		return null;
	}
	
	@Override
	public ImpuestoGDF getTramiteyConcepto(ImpuestoGDF comprobante){
		return null;
	}
	
	@Override
	public String getEstatusInotr(String referencia, String contrato){
		
	      
		return null;
	}
}
