package com.afirme.afirmenet.service.impl.transferencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.service.transferencia.ComprobanteTransferenciaService;

@Service
public class ComprobanteTransferenciaServiceImpl implements ComprobanteTransferenciaService{
	
	@Autowired
	private ComprobanteTransferenciaDao comprobanteTransferenciaDao;

	@Override
	public void insertaConfirmacionOperacion(Comprobante comprobante) {
		
		TipoTransferencia tipo =comprobante.getTipoTransferencia();
		
		switch (tipo) {
		case ACTIVACION_DE_TARJETA_DE_CREDITO: //CAMBIO_DE_LIMITES_TARJETA_CREDITO_ADICIONAL
			comprobanteTransferenciaDao.insertaConfirmacionActivacionTDC(comprobante);
			break;
		case TRANSFERENCIA_SPEI:
			if(comprobante.getTransactionCode().equalsIgnoreCase(TipoTransferencia.PAGO_INTERBANCARIO.getValor()))
				comprobante.setTipoTransferencia(TipoTransferencia.PAGO_INTERBANCARIO);
			comprobanteTransferenciaDao.insertaConfirmacionOperacion(comprobante);
			if(comprobante.getTransactionCode().equalsIgnoreCase(TipoTransferencia.TRANSFERENCIA_SPEI.getValor()))
				comprobanteTransferenciaDao.insertaInformacionSPEI(comprobante);
			break;
		case PROGRAMACION_DE_PAGO_AUTOMATICO_TDC:
			comprobanteTransferenciaDao.insertaComprobanteProgramacionPagos(comprobante);
		break;
		case MIS_CREDITOS_AFIRME:
			comprobanteTransferenciaDao.insertaComprobanteMisCreditos(comprobante);
		break;
		case INVERSION_PERFECTA:
			comprobanteTransferenciaDao.insertaComprobanteInversionPerfecta(comprobante);
		break;
		case RECARGA_DE_TIEMPO_AIRE_TELCEL:
			comprobanteTransferenciaDao.almacenarRecargaDB2(comprobante);
			comprobanteTransferenciaDao.insertaConfirmacionRecarga(comprobante);
		break;
		case RECARGA_DE_TIEMPO_AIRE_MOVISTAR:
			comprobanteTransferenciaDao.almacenarRecargaDB2(comprobante);
			comprobanteTransferenciaDao.insertaConfirmacionRecarga(comprobante);
		break;
		case PAGO_DE_IMPUESTOS_DEPOSITO_REFERENCIADO:
			comprobanteTransferenciaDao.insertaConfirmacionOperacion(comprobante);
			comprobanteTransferenciaDao.insertaConfirmacionDepositoReferenciado(comprobante);
		break;
		default:
			comprobanteTransferenciaDao.insertaConfirmacionOperacion(comprobante);
			break;
		}
		
	}

}
