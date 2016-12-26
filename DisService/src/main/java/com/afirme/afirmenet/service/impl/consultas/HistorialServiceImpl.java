package com.afirme.afirmenet.service.impl.consultas;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.consultas.HistorialDao;
import com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.consultas.HistorialService;

@Service
public class HistorialServiceImpl implements HistorialService {

	@Autowired
	private HistorialDao historialDao;
	
	@Autowired
	private ComprobanteTransferenciaDao comprobanteTransferenciaDao;
	
	
	public List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken) {
		return historialDao.listaTransacciones(esBasicoSinToken);
	}
	
	@Override
	public List<String> categorias(boolean esBasicoSinToken) {
		return historialDao.categorias(esBasicoSinToken);
	}

	/**
	 * Devuelve una lista de TransferenciaBase que es el tipo de objeto para mostrar la lista de resultados.
	 */
	@SuppressWarnings("unchecked")
	public List<TransferenciaBase> buscaTransferencias(String contrato, List<Cuenta> cuentas, HistorialTipo tipo, Date fechaDesde, Date fechaHasta) {
		String fD="";
		String fH="";
		List<TransferenciaBase> lista=null;
		
		HistorialTipo tipoParaComparar;
		
		switch (tipo) {
		case TRANSFERENCIAS_INTERNACIONALES_DOLARES:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesSWIFT(contrato, tipo.getValor(), fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		
	
		default:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGenericos(contrato, tipo.getValor(), fD, fH, null);
			break;
		}
		return lista;
	}

	@Override
	public void obtenerInformacionExtra(TransferenciaBase comprobante) {
		HistorialTipo tipo= HistorialTipo.findByValue(comprobante.getTransactionCode());
		switch(tipo){
			case TRANSFERENCIA_SPEI:
			break;
		default:		
			break;
		}
		
		comprobante.setSts(getEstatusInotr(comprobante.getReferenceNumber(), comprobante.getContractId()));
	}
	public String getEstatusInotr(String referencia, String contrato){
		String STS=comprobanteTransferenciaDao.buscarAtributoINOTR(referencia, contrato, "INOSTS");

	      if(STS.equals("A"))
	      {
	         STS = "Pendiente";
	      }
	      else if(STS.equals("P"))
	      {
	         STS = "Exitosa";
	      }
	      else if(STS.equals("R"))
	      {
	         STS = "Rechazada";
	      }
	      else if(STS.equals("X"))
	      {
	         STS = "Cancelada";
	      }
	      else if(STS.equals("V"))
	      {
	         STS = "Reversada";
	      }
	      else if(STS.equals("I"))
	      {
	         STS = "Incompleta";
	      }
	      
		return STS;
	}
}
