package com.afirme.afirmenet.service.impl.transferencia;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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
import com.afirme.afirmenet.ibs.messages.ECONFDS001Message;
import com.afirme.afirmenet.ibs.messages.ELEERRMessage;
import com.afirme.afirmenet.ibs.messages.INRECARGA1Message;
import com.afirme.afirmenet.model.servicios.Recarga;
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

@Service("telefonicaServiceImpl")
public class TelefonicaServiceImpl implements TransferenciaAfirmeNet{
	static final AfirmeNetLog LOG = new AfirmeNetLog(TelefonicaServiceImpl.class);
	private int timeOutTelefonicas = 60000;
	
	@Override
	public ComprobanteTransferencia ejecutaTransferencia(
			TransferenciaBase transferencia)throws AfirmeNetException {
		
		Recarga transferenciaBase = (Recarga)transferencia;
		transferenciaBase.setAfirmeNetReference(Util.getRefNum());
		
		Comprobante comprobante = null;
		INRECARGA1Message afitrans = null;

		Socket socket = null;
		MessageContext messageContext = null;
		MessageRecord messageRecord = null;
		boolean goHead = false;
		String errorNumber= "";
		String errorMessage= "";
	    String codigoErrorTimeout = "2509";
	    String codigoErrorAlternativo = "2511";
		
		try {
			socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP, AfirmeNetConstants.SOCKET_PUERTO+1, 0);
			socket.setSoTimeout(timeOutTelefonicas);
			messageContext = MessageContextFactory.getContext(socket); 
			
		} catch (SocketFactoryException e) {
			throw new AfirmeNetException("0000","Por el momento no se puede ejecutar su operacion.");
		} catch (MessageContextFactoryException e) {
			throw new AfirmeNetException("0001","Por el momento no se puede ejecutar su operacion.");
		} catch (SocketException e) {
			throw new AfirmeNetException("0001","Por el momento no se puede ejecutar su operacion.");
		}
		
        try {
			afitrans = (INRECARGA1Message) messageContext.getMessageRecord("INRECARGA1");		
		    afitrans.setINCOPE("A");
		    afitrans.setINTUSR(transferenciaBase.getContractId());
		    afitrans.setINTCTACGO(transferenciaBase.getDebitAccount());
		    afitrans.setINCELULAR(transferenciaBase.getNumeroCelular());
		    afitrans.setINTIMPORTE(transferenciaBase.getAmount());
		    afitrans.setINTMONEDA(transferenciaBase.getCurrency());
		    afitrans.setINEMPRESA(Integer.toString(transferenciaBase.getId()));
		    afitrans.setINTXPMTV(transferenciaBase.getTipoTransaccion());
		    afitrans.setINTFECHA(Util.getCurrentYMD());
		    afitrans.setINTHORA(Util.getCurrentHMS());
			
	         LOG.setSendInfo("cargo", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), "", transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), afitrans.getFormatName());
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
        	
            boolean huboTimeout = errorNumber.equals(codigoErrorTimeout);
            boolean huboErrorAlternativo = errorNumber.equals(codigoErrorAlternativo);
        	
            // Se considera transaccion valida si no hubo error o si hubo time out o tuvo error en componente superior
            goHead = goHead || huboTimeout || huboErrorAlternativo;
        	
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
        		
        		//Se recibe la informacion de la operacion si no se genero un error previo
        		messageRecord = messageContext.receiveMessage();
        		LOG.setGetInfo("cargo", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), transferenciaBase.getContractId(), transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), messageRecord);
        		String referenceNumber = (((ECONFDS001Message) messageRecord).getECNFREF());
        		String numeroFolio = (((ECONFDS001Message) messageRecord).getECNAUTOR());
        		
                if(numeroFolio.equals(""))
                {
                   numeroFolio = "000000";
                }
        		
                comprobante = new Comprobante(transferenciaBase);
                comprobante.setReferenceNumber(referenceNumber);
                comprobante.setAfirmeNetReference(transferenciaBase.getAfirmeNetReference());
                comprobante.setFlag(numeroFolio);
                comprobante.setTransactionCode(transferenciaBase.getTipoTransaccion());        		
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
