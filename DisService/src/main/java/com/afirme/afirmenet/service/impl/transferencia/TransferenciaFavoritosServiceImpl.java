package com.afirme.afirmenet.service.impl.transferencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.enums.TipoServicio;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.ibs.databeans.INSER;
import com.afirme.afirmenet.model.pagos.servicios.Servicio;
import com.afirme.afirmenet.model.servicios.Recarga;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.ComprobanteTransferencia;
import com.afirme.afirmenet.model.transferencia.Favorita;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.model.transferencia.TransferenciaCuentasPropias;
import com.afirme.afirmenet.service.pagos.PagosService;
import com.afirme.afirmenet.service.transferencia.BancosNacionalesService;
import com.afirme.afirmenet.service.transferencia.Pago;
import com.afirme.afirmenet.service.transferencia.TelefonicaService;
import com.afirme.afirmenet.service.transferencia.TransferenciaAfirmeNet;
import com.afirme.afirmenet.service.transferencia.TransferenciaPagoServiciosService;
import com.afirme.afirmenet.service.transferencia.TransferenciaPropiasService;
import com.afirme.afirmenet.service.transferencia.TransferenciaTercerosService;

@Service("transferenciaFavoritosServiceImpl")
public class TransferenciaFavoritosServiceImpl implements
		TransferenciaAfirmeNet {

	@Autowired
	TransferenciaTercerosService tercerosService;
	
	@Autowired
	TransferenciaPropiasService propiasService;
	
	@Autowired
	private TelefonicaService telefonicaService;
	
	@Autowired
	TransferenciaPagoServiciosService pagoServiciosService;
	
	@Autowired
	private BancosNacionalesService bancosNacionalesService;
	
	@Autowired
	PagosService pagosService;
	
	@Autowired
	private Pago pagoTDC;
	
	@Override
	public ComprobanteTransferencia ejecutaTransferencia(
			TransferenciaBase transferenciaBase) throws AfirmeNetException {
		return null;
	}

	@Override
	public List<? extends ComprobanteTransferencia> ejecutaTransferencia(
			List<TransferenciaBase> transferencias) {
		List<ComprobanteTransferencia> comprobantes = new ArrayList<ComprobanteTransferencia>(
				0);
		List<TransferenciaBase> base = null;
		
		for (TransferenciaBase tx : transferencias) {
			try {
				if(tx.getErrores() == null || tx.getErrores().isEmpty()){
					base =  new ArrayList<TransferenciaBase>(0);
					
					if(tx.getTipoTransferencia().equals(TipoTransferencia.TRASPASO_PROPIAS)){
						//Se cambia el tipo de transferencia de favorita a propia
						TransferenciaCuentasPropias propias = new TransferenciaCuentasPropias();
						BeanUtils.copyProperties(tx, propias);

						base.add(propias);
						
						comprobantes.addAll(propiasService.confirmaTransferencias( base ));
					}if(tx.getTipoTransferencia().equals(TipoTransferencia.TRANSFERENCIA_SPEI)){
						base.add(tx);
						
						comprobantes.addAll(bancosNacionalesService.confirmaTransferencias( base ));
					}else if(tx.getTipoTransferencia().equals(TipoTransferencia.TRASPASO_TERCEROS)){						
						base.add(tx);
						
						comprobantes.addAll(tercerosService.confirmaTransferencias( base ));
					}else if(tx.getTipoTransferencia().equals(TipoTransferencia.PAGO_DE_SERVICIOS) && !tx.getTipoServicio().equals(TipoServicio.TARJETA_DE_CREDITO_BANCOS_RED)){ //SOLO PAGOS DE SERVICIOS
						Favorita fav = (Favorita) tx;
						Servicio servicio =new Servicio();
						BeanUtils.copyProperties(tx, servicio);
						servicio.setServiceType(Integer.valueOf(servicio.getTipoServicio().getValor()));
						//Set info favoritas
						if(fav.getTipoServicio() != null && fav.getTipoServicio().equals(TipoServicio.TELEFONOS_DE_MEXICO)){
							servicio.setCreditAccount(fav.getServiceNumber());
							servicio.setServiceNumber(fav.getServiceNumber());	
							servicio.setLada(fav.getServiceNumber().substring(0,2));
							servicio.setNumeroTelefonico(fav.getServiceNumber().substring(2,fav.getServiceNumber().length()));
							servicio.setEsFavoritas(true);
						}else{
							servicio.setReferenceNumber(fav.getServiceNumber());
						}
						
						servicio.setIva(new BigDecimal(0));
						servicio.setCommision(new BigDecimal(0));
						//Obtiene informacion de servicio
						INSER inser = pagosService.getConvenio(servicio.getTipoServicio().getValorComercial());						
						servicio.setIdServicio400(inser.getSERDTR().intValue());
						servicio.setUserId(servicio.getContractId());
						servicio.setBeneficiaryName(fav.getBeneficiaryName());
						base.add(servicio);
						
						comprobantes.addAll(pagoServiciosService.confirmaTransferencias( base ));
					
				}else if(tx.getTipoTransferencia().equals(TipoTransferencia.PAGO_DE_SERVICIOS) && tx.getTipoServicio().equals(TipoServicio.TARJETA_DE_CREDITO_BANCOS_RED)){ // PAGOS DE TARJETAS
					Favorita fav = (Favorita) tx;
					Servicio servicio =new Servicio();
					BeanUtils.copyProperties(tx, servicio);
					servicio.setServiceType(Integer.valueOf(servicio.getTipoServicio().getValor()));
					servicio.setInterType(fav.getAccountType()==null||fav.getAccountType().equals("")?0:Integer.parseInt(fav.getAccountType()));
					
					DC_CONVENIO convenio=pagosService.getConvenioTDC(fav.getContractId(), fav.getCreditAccount());
					//Obtiene informacion de servicio
					servicio.setConvenio(convenio);
					servicio.setUserId(servicio.getContractId());
					servicio.setBeneficiaryName(fav.getBeneficiaryName());
					base.add(servicio);
					
					comprobantes.addAll(pagoTDC.confirmaPagos( base ));

				}else if(tx.getTipoTransferencia().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_TELCEL) || tx.getTipoTransferencia().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_MOVISTAR)){ // PAGOS DE TARJETAS
					//Se cambia el tipo de transferencia de favorita a recargas
					Favorita fav = (Favorita) tx;
					Recarga recarga = new Recarga();
					BeanUtils.copyProperties(tx, recarga);
					
					int id=1; //TELCEL
					if(tx.getTipoTransferencia().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_MOVISTAR))
						id=2; 
					
					recarga.setId(id);
					recarga.setState(Integer.toString(id));
					recarga.setNumeroCelular(fav.getCreditAccount());
					recarga.setTipoTransaccion(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_TELCEL.getValor());
					
					if(tx.getTipoTransferencia().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_MOVISTAR))
						recarga.setTipoTransaccion(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_MOVISTAR.getValor());
					
					base.add(recarga);
					
					comprobantes.addAll(telefonicaService.confirmaTransferencias( base ));
				
				}				
			  }
			} catch (AfirmeNetException e) {
				Comprobante comprobante = new Comprobante(tx);
				Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
						0);
				CodigoExcepcion error = CodigoExcepcion.findByValue(Integer
						.valueOf(e.getErrCode()));
				if (error == null) {
					// Error generico
					error = CodigoExcepcion.ERR_3000;
					errors.put(error, e.getMessage());
				} else {
					errors.put(error, e.getErrMsg());
				}
				comprobante.setErrores(errors);
				comprobantes.add(comprobante);
			}
		}
		return comprobantes;
	}

}
