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
import com.afirme.afirmenet.ibs.messages.ECONFDSMessage;
import com.afirme.afirmenet.ibs.messages.ELEERRMessage;
import com.afirme.afirmenet.ibs.messages.INPROGRA01Message;
import com.afirme.afirmenet.model.pagos.servicios.Servicio;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.ComprobanteTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.impl.configuraciones.chequera.ChequeraServiceImpl;
import com.afirme.afirmenet.service.transferencia.TransferenciaAfirmeNet;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.socket.MessageContextFactory;
import com.afirme.afirmenet.utils.socket.SocketFactory;
import com.datapro.sockets.MessageContext;
import com.datapro.sockets.MessageRecord;

@Service("domiciliacionTDCServiceImpl")
public class DomiciliacionTDCServiceImpl implements TransferenciaAfirmeNet{

	static final AfirmeNetLog LOG = new AfirmeNetLog(DomiciliacionTDCServiceImpl.class);
	@Override
	public ComprobanteTransferencia ejecutaTransferencia(
			TransferenciaBase transferencia) throws AfirmeNetException {
		Servicio transferenciaBase = (Servicio) transferencia;
		transferenciaBase.setAfirmeNetReference(Util.getRefNum());
		
		Comprobante comprobante = null;
		INPROGRA01Message afitrans = null;
		//Obtiene email
		//String EMAIL = valobj.procEMAIL(UserIdJSP);
		String email="JOSE.CORPUS@AFIRME.COM";
		//Valida Fin de Dia

//	      String eoderror = eodbean.getEOD();
//
//	      if(eoderror.equals("1"))
//	      {
//	         sqlerror = "1";
//	         return sqlerror;
//	      }
//	      if(eoderror.equals("2"))
//	      {
//	         sqlerror = "2";
//	         return sqlerror;
//	      }
//		Obtiene firma digital
//		JBFirmaDigital oFirma = (JBFirmaDigital) session.getAttribute("oFirma");
		
		//Invocacion del Socket
		Socket socket = null;
		MessageContext messageContext = null;
		MessageRecord messageRecord = null;
		boolean goHead = false;
		String errorNumber= "";
		String errorMessage= "";
		try {
			socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP, AfirmeNetConstants.SOCKET_PUERTO+1, 0);
			messageContext = MessageContextFactory.getContext(socket); 
			
		} catch (SocketFactoryException e) {
			throw new AfirmeNetException("0000","Por el momento no se puede ejecutar su operacion.");
		} catch (MessageContextFactoryException e) {
			throw new AfirmeNetException("0001","Por el momento no se puede ejecutar su operacion.");
		}
		
	      String benefAcc = "";
				
        try {
			afitrans = (INPROGRA01Message) messageContext.getMessageRecord("INPROGRA01");		
			afitrans.setTCIUSR(transferenciaBase.getContractId());
			afitrans.setTCITIP("P");
			afitrans.setTCMAIL(transferencia.getEmail());
			//afitrans.setTCCLIE();//numero de cliente banca
			afitrans.setTCTARJ(transferenciaBase.getCreditAccount());
			afitrans.setTCTPPA(transferenciaBase.getTipoProg());
			afitrans.setTCCTAC(transferenciaBase.getDebitAccount());
			//afitrans.setTCFPCA(beanTransaction.getProgramacionFecha());
			afitrans.setTCFPCA("20"+transferenciaBase.getValidationYear()+transferenciaBase.getValidationMonth()+transferenciaBase.getValidationDay());
			afitrans.setTCTSTS(transferenciaBase.getAccion());//Status programación  
			
			
			if(transferenciaBase.getAccion().equals("A")){
				afitrans.setTCFACT("20"+transferenciaBase.getValidationYear()+transferenciaBase.getValidationMonth()+transferenciaBase.getValidationDay());
				afitrans.setTCHACT(transferenciaBase.getValidationHour() + transferenciaBase.getValidationMinute() + "00");
				afitrans.setTCIMPO(transferenciaBase.getAmount());
			}else{
				 afitrans.setTCFCAN("20"+transferenciaBase.getValidationYear()+transferenciaBase.getValidationMonth()+transferenciaBase.getValidationDay() );
				 afitrans.setTCHCAN (transferenciaBase.getValidationHour() + transferenciaBase.getValidationMinute() + "00");
			}
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
        		//Se recibe la informacion de la operacion si no se genero un error previo
        		messageRecord = messageContext.receiveMessage();
        		LOG.setGetInfo("cargo", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), transferenciaBase.getContractId(), transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), messageRecord);
        		String referenceNumber = (((ECONFDSMessage) messageRecord).getECNFREF());
        		//Actualiza DC_TAFIRME  
        		//valobj.procUPREFEAFI(REFENUM, transbean.getDCIBS_REF(), transbean.getENTITYID());
                //Llenar el comprobante con la operacion efectuada
                comprobante = new Comprobante(transferenciaBase);
                comprobante.setReferenceNumber(referenceNumber);
                comprobante.setAfirmeNetReference(transferenciaBase.getAfirmeNetReference());
        		
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
