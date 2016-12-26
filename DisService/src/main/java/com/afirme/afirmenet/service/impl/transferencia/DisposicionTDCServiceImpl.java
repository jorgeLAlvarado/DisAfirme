package com.afirme.afirmenet.service.impl.transferencia;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.exception.MessageContextFactoryException;
import com.afirme.afirmenet.exception.SocketFactoryException;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.ibs.messages.ELEERRMessage;
import com.afirme.afirmenet.ibs.messages.INTSY02PFMessage;
import com.afirme.afirmenet.model.pagos.servicios.Servicio;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.ComprobanteTransferencia;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.transferencia.TransferenciaAfirmeNet;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.socket.MessageContextFactory;
import com.afirme.afirmenet.utils.socket.SocketFactory;
import com.datapro.sockets.MessageContext;
import com.datapro.sockets.MessageRecord;

@Service("disposicionTDCServiceImpl")
public class DisposicionTDCServiceImpl implements TransferenciaAfirmeNet{

	static final AfirmeNetLog LOG = new AfirmeNetLog(DisposicionTDCServiceImpl.class);
	@Override
	public <T extends TransferenciaBase> ComprobanteTransferencia ejecutaTransferencia(
			T TransferenciaBase) throws AfirmeNetException {
		Servicio transferenciaBase = (Servicio) TransferenciaBase;
		transferenciaBase.setAfirmeNetReference(Util.getRefNum());
		
		Comprobante comprobante = null;
		INTSY02PFMessage afitrans = null;
		
		//Invocacion del Socket
		Socket socket = null;
		MessageContext messageContext = null;
		MessageRecord messageRecord = null;
		boolean goHead = false;
		String errorNumber= "";
		String errorMessage= "";
		try {
			socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP, AfirmeNetConstants.SOCKET_PUERTO+16, 0);
			messageContext = MessageContextFactory.getContext(socket); 
			
		} catch (SocketFactoryException e) {
			throw new AfirmeNetException("0000","Por el momento no se puede ejecutar su operacion.");
		} catch (MessageContextFactoryException e) {
			throw new AfirmeNetException("0001","Por el momento no se puede ejecutar su operacion.");
		}
		
	      String benefAcc = "";
				
        try {
			  afitrans = (INTSY02PFMessage) messageContext.getMessageRecord("INTSY02PF");		
		         afitrans.setINCLIE(transferenciaBase.getTransferTDC().getBean().getINCLIE());
		         afitrans.setINCONI(transferenciaBase.getTransferTDC().getBean().getINCONI());
		         afitrans.setINUSRI(transferenciaBase.getTransferTDC().getBean().getINUSRI());
		         afitrans.setINTDCC(transferenciaBase.getTransferTDC().getBean().getINTDCC());
		         afitrans.setINNUTR("95");		         
		         afitrans.setINTPOP("A");
		         afitrans.setINCVV1(transferenciaBase.getCvv()); //cvv
	        	 afitrans.setINFEVE(transferenciaBase.getAnioVenc()+transferenciaBase.getMesVenc()); //fechaVencimiento
	        	 afitrans.setINCTAA(transferenciaBase.getDestino().getNumber()); //CuentaDestino
	        	 afitrans.setINIMDI(transferenciaBase.getAmount()); //Importe
	        	 afitrans.setINHOTR(transferenciaBase.getTransferTDC().getBean().getINHOTR());
	        	 afitrans.setINFETR(transferenciaBase.getTransferTDC().getBean().getINFETR());
	        	 LOG.setSendInfo("cargo", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), transferenciaBase.getContractId(), transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), afitrans.getFormatName());
		      afitrans.send();
		      afitrans.destroy();		
			
		} catch (Exception e) {
			throw new AfirmeNetException("0003","Por el momento no se puede ejecutar su operacion.");
		} 
        
        try{
        	//Se recibe info si ocurrio un error o no
        	messageRecord = messageContext.receiveMessage();
        	LOG.setGetInfo("cargo", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), transferenciaBase.getContractId(), transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), messageRecord);
        	goHead = ((ELEERRMessage) messageRecord).getERRNUM().equals("0");
        	errorNumber = ((ELEERRMessage) messageRecord).getERNU01();
        	errorMessage = ((ELEERRMessage) messageRecord).getERDS01();
        	if(!goHead){//Ocurrio un error
        		//Llenar el comprobante con los errores detectados con 400
                comprobante = new Comprobante(transferenciaBase);
                Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
                //Busca la Excepcion 
                CodigoExcepcion error = CodigoExcepcion.findByValue(Integer.valueOf(errorNumber));
                if(error == null){
                	//Error generico
                	error = CodigoExcepcion.ERR_3000;
                	errors.put(error, errorMessage);
                }else{
                	errors.put(error, errorMessage);
                }
                comprobante.setErrores(errors);
        	}else{
        		messageRecord = messageContext.receiveMessage();
        		LOG.setGetInfo("cargo", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), transferenciaBase.getContractId(), transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), messageRecord);
        		INTSY02PFMessage msjMQ = (INTSY02PFMessage) messageRecord;
        		transferenciaBase.getTransferTDC().setBean(msjMQ);
                comprobante = new Comprobante(transferenciaBase);
                comprobante.setDescription("DISPOSICION DE TDC");
                comprobante.setNarrative("DISPOSICION DE TDC");
                comprobante.setAfirmeNetReference(Util.getRefNum());
                comprobante.setCommision(transferenciaBase.getTransferTDC().getComision());
                comprobante.setPlazaReceiving(transferenciaBase.getTransferTDC().getComision().toString());
                comprobante.setReferenceNumber(transferenciaBase.getTransferTDC().getFolioAuditoria());
                comprobante.setUserReference(transferenciaBase.getTransferTDC().getAutorizacionTSYS());
                comprobante.setTipoTransferencia(TipoTransferencia.DISPOSICION_DE_EFECTIVO_TDC);
                comprobante.setBeneficiaryName(transferenciaBase.getBeneficiaryName());
        	}
        	
        }catch(SocketTimeoutException e){
        	throw new AfirmeNetException("0003","Ha ocurrido un error al intentar validar la informacion. Por favor, intente dentro de 15 minutos.");
        }catch(Exception e){
        	throw new AfirmeNetException("0003","Por el momento no se puede ejecutar su operacion.");
        }finally{
        	if(socket != null){
        		try {
					socket.close();
				} catch (IOException e) {
				}
        	}
         }
		return comprobante;
	}

	@Override
	public List<? extends ComprobanteTransferencia> ejecutaTransferencia(
			List<TransferenciaBase> transferencias) {

		List<ComprobanteTransferencia> comprobantes = new ArrayList<ComprobanteTransferencia>(0);
		for(TransferenciaBase tx: transferencias){
			try {
				if(tx.getErrores() == null || tx.getErrores().isEmpty()){
					comprobantes.add(ejecutaTransferencia(tx));
				}else{
					comprobantes.add(new Comprobante(tx));
				}
				
			} catch (AfirmeNetException e) {
				Comprobante comprobante = new Comprobante(tx);
				 Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
				CodigoExcepcion error = CodigoExcepcion.findByValue(Integer.valueOf(e.getErrCode()));
                if(error == null){
                	//Error generico
                	error = CodigoExcepcion.ERR_3000;
                	errors.put(error, e.getMessage());
                }else{
                	errors.put(error, e.getMessage());
                }
                comprobante.setErrores(errors);
                comprobantes.add(comprobante);
			}
		}
		return comprobantes;
	}

}
