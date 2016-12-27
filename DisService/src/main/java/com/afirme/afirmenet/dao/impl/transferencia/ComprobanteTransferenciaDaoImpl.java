package com.afirme.afirmenet.dao.impl.transferencia;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.empresas.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.model.transferencia.Divisa;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;

/**
 * @author Usuario
 *
 */
@Repository
public class ComprobanteTransferenciaDaoImpl implements ComprobanteTransferenciaDao {



	

	
	@Override
	public List<TransferenciaBase> buscarComprobantesGenericos(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio) {
		return null;

	

	}
	
	
	@Override
	public List<Divisa> buscarComprobantesSWIFT(String contrato, String tipo, String fechaDesde, String fechaHasta) {


		return null;
	}


	@Override
	public String buscarAtributoINOTR(String ref, String contrato, String atrib){
		return null;

	}
	
	

	
	
	
}
