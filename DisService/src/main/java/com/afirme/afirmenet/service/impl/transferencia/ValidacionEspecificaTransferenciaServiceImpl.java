package com.afirme.afirmenet.service.impl.transferencia;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.transferencia.TasaCambioPreferencialDao;
import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.beans.JBSIC001PF;
import com.afirme.afirmenet.ibs.databeans.cardif.SeguroCardif;
import com.afirme.afirmenet.model.inversiones.Inversion;
import com.afirme.afirmenet.model.pagos.impuestos.DepositoReferenciado;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TipoValidacionEspecial;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.model.transferencia.TransferenciaCuentasPropias;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpress;
import com.afirme.afirmenet.service.transferencia.TransferenciaAfirmeNet;
import com.afirme.afirmenet.service.transferencia.ValidacionEspecificaTransferenciaService;
import com.afirme.afirmenet.service.transferencia.ValidacionImpuestosDepositoRefenenciado;

@Service
public class ValidacionEspecificaTransferenciaServiceImpl implements
		ValidacionEspecificaTransferenciaService {

	@Autowired
	TasaCambioPreferencialDao tasaCambioPreferencialDao;

	@Autowired
	ValidacionImpuestosDepositoRefenenciado depositoReferenciadoService;
	
	@Override
	public void applyValidationsEspecific(
			List<TransferenciaCuentasPropias> tranferencias,
			TipoTransferencia tipo, List<TipoValidacionEspecial> validaciones) {
		Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
				0);
		for (TransferenciaCuentasPropias tx : tranferencias) {

			for (TipoValidacionEspecial validacion : validaciones) {

				switch (validacion) {
				case CLAVE_CAMBIO_PREFERENCIAL:
					try {
						if (!tx.getOrigen().getCcy().trim()
								.equals(tx.getDestino().getCcy().trim())) {
							if(tx.getCveTasaPref()!=null && !tx.getCveTasaPref().equals("")){
								if (!this.isValidClave(
										(TransferenciaCuentasPropias) tx, tipo)) {
									// Almacena Error
									errors.put(
											CodigoExcepcion.ERR_3000,
											"La clave de tipo de cambio especial ingresada es incorrecta, favor de verificar");
								} else {
									if (!this.isClaveVencida(
											(TransferenciaCuentasPropias) tx, tipo)) {
										// Almacena Error
										errors.put(
												CodigoExcepcion.ERR_3000,
												"La clave de tipo de cambio especial ingresada está vencida, favor de verificar");
								       }
									tx.setAmount(tx.getCambioDolares().getOrigenAmt());
							   }
							}
						}

					} catch (Exception e) {
						errors.put(
								CodigoExcepcion.ERR_3000,
								"La clave de tipo de cambio especial ingresada es incorrecta, favor de verificar");
					}
					break;

				default:
					break;
				}

			}

			if (!errors.isEmpty()) {
				tx.setErrores(errors);
			}
		}
	}

	@Override
	public boolean isValidClave(TransferenciaCuentasPropias tranferenciaBase,
			TipoTransferencia tipo) throws AfirmeNetException {
		boolean goHead = true;

		JBSIC001PF transferTasaBean = tasaCambioPreferencialDao
				.validateCveTasaPreferencial(tranferenciaBase.getCveTasaPref());

		if (transferTasaBean == null) {
			return false;
		} else {
			transferTasaBean.setAmountOrigin(tranferenciaBase.getAmount());
			transferTasaBean.setCcyOrigin(tranferenciaBase.getOrigen().getCcy());
			tranferenciaBase.setPlazaReceiving(transferTasaBean.getTRate());
			tranferenciaBase.setCambioDolares(transferTasaBean);
		}

		return goHead;

	}

	@Override
	public boolean isClaveVencida(TransferenciaCuentasPropias tranferenciaBase,
			TipoTransferencia tipo) throws AfirmeNetException {
		boolean goHead = true;

		if (tranferenciaBase
				.getCambioDolares()
				.getSICDAT()
				.compareTo(
						new BigDecimal(20
								+ tranferenciaBase.getValidationYear()
								+ tranferenciaBase.getValidationMonth()
								+ tranferenciaBase.getValidationDay())) != 0)
			return false;

		return goHead;

	}

	@Override
	public void applicaValidaticioEspecifica(
			List<? extends TransferenciaBase> tranferencias,
			List<TipoValidacionEspecial> validaciones) {
		this.applicaValidaticioEspecifica(tranferencias, validaciones, null);
	}

	@Override
	public void applicaValidaticioEspecifica(
			List<? extends TransferenciaBase> tranferencias,
			List<TipoValidacionEspecial> validaciones, TransferenciaAfirmeNet servicio) {
		Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
				0);
		for (TransferenciaBase tx : tranferencias) {

			for (TipoValidacionEspecial validacion : validaciones) {

				switch (validacion) {
				case SEGURO_CARDIF:
					try {
						SeguroCardifServiceImpl segurosPagoService= (SeguroCardifServiceImpl) servicio;
						
						SeguroCardif oBeanCardif =(SeguroCardif)tx;
						Comprobante com=segurosPagoService.validacion(oBeanCardif);
						if(com!=null){
							tx.setErrores(com.getErrores());
						}
					} catch (Exception e) {
						errors.put(
								CodigoExcepcion.ERR_3000,
								"No se ha podido validar la compra del seguro");
						
						tx.setErrores(errors);
						
					}
					break;
				case CASH_EXPRESS:
					try {
						CashExpressServiceImpl cashExpressService= (CashExpressServiceImpl) servicio;
						
						CashExpress oOrden =(CashExpress)tx;
						Comprobante com=cashExpressService.validacion(oOrden);
						if(com!=null){
							tx.setErrores(com.getErrores());
						}
					} catch (Exception e) {
						errors.put(
								CodigoExcepcion.ERR_3000,
								"No se ha podido validar el envio");
						
						tx.setErrores(errors);
						
					}
					break;
				case INVERSION_PERFECTA:
					try {
						InversionPerfectaServiceImpl inversionPerfectaService= (InversionPerfectaServiceImpl) servicio;
						
						Inversion inversion =(Inversion)tx;
						if(inversion.getCuentaPasiva()==null || inversion.getCuentaPasiva().trim().length()==0){
							errors.put(CodigoExcepcion.ERR_3000, "Error! Ocurrió un error al consultar la cuenta pasiva. Intente nuevamente");
							inversion.setErrores(errors);
						}
						if(inversion.getCuentaContable()==null || inversion.getCuentaContable().trim().length()==0){
							errors.put(CodigoExcepcion.ERR_3000, "Error! Ocurrió un error al consultar la cuenta contable. Intente nuevamente");
							inversion.setErrores(errors);
						}
						inversionPerfectaService.enviaSolicitud(inversion);
						if(inversion.getErrores()==null){
							inversionPerfectaService.enviaApertura(inversion);
						}
					} catch (Exception e) {
						errors.put(
								CodigoExcepcion.ERR_3000,
								"No se ha podido validar la inversión");
						tx.setErrores(errors);
					}
					break;
				case PAGO_DE_IMPUESTOS_DEPOSITO_REFERENCIADO:
					try {
						DepositoReferenciado pago= (DepositoReferenciado)tx;
						depositoReferenciadoService.esServicioValido(pago);
						DepositoReferenciadoServiceImpl service= (DepositoReferenciadoServiceImpl) servicio;
						if(pago.getErrores()==null){
							String fecha = "20" + pago.getValidationYear() + pago.getValidationMonth() + pago.getValidationDay();
							service.getFolioImpuetos(pago);
							pago.setLineaPago(service.getLlavePagoV2(fecha, pago.getLineaCaptura(), pago.getAmount().toString(), pago.getTrackingCode()));
						}
					} catch (Exception e) {
						errors.put( CodigoExcepcion.ERR_3000, "No se ha podido validar el pago");
						tx.setErrores(errors);
					}
					break;
				default:
					break;
				}

				
			}

		}
	}
}
