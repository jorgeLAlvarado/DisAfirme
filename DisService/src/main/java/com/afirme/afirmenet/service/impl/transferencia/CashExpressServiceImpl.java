package com.afirme.afirmenet.service.impl.transferencia;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.enums.ConfigConvenios;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.exception.MessageContextFactoryException;
import com.afirme.afirmenet.exception.SocketFactoryException;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.messages.ELEERRMessage;
import com.afirme.afirmenet.ibs.messages.ORP0104PFMessage;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.ComprobanteTransferencia;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpress;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpressOperacion;
import com.afirme.afirmenet.service.transferencia.TransferenciaAfirmeNet;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.socket.MessageContextFactory;
import com.afirme.afirmenet.utils.socket.SocketFactory;
import com.datapro.sockets.MessageContext;
import com.datapro.sockets.MessageRecord;

@Service("cashExpressServiceImpl")
public class CashExpressServiceImpl implements TransferenciaAfirmeNet {
	static final AfirmeNetLog LOG = new AfirmeNetLog(CashExpressServiceImpl.class);
	
	@Override
	public ComprobanteTransferencia ejecutaTransferencia(
			TransferenciaBase transferencia)throws AfirmeNetException {
		
		
		Comprobante comprobante =new Comprobante();
		
		if(transferencia.getInterType()==ConfigConvenios.CASH_EXPRESS_CAMBIO_CLAVE.getValor())
			comprobante=this.reenvio((CashExpress)transferencia);
		else if(transferencia.getInterType()==ConfigConvenios.CASH_EXPRESS_CANCELAR.getValor())
			comprobante=this.cancelacion((CashExpress)transferencia);
		else 
			comprobante=this.aplicacion((CashExpress)transferencia);
		
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

	public Comprobante validacion(CashExpress oOrden)  {
		return this.sendBeanToAS400(oOrden, CashExpressOperacion.VALIDACION);
	}

	public Comprobante aplicacion(CashExpress oOrden) {
		return this.sendBeanToAS400(oOrden, CashExpressOperacion.APLICACION);
	}
	public Comprobante cancelacion(CashExpress oOrden)  {
		return this.sendBeanToAS400(oOrden, CashExpressOperacion.CANCELACION);
	}
	public Comprobante reenvio(CashExpress oOrden)  {
		return this.sendBeanToAS400(oOrden, CashExpressOperacion.REENVIO);
	}
	public Comprobante sendBeanToAS400(CashExpress oOrden, CashExpressOperacion ope)  {

		Comprobante comprobante = null;
		ORP0104PFMessage afitrans = null;
		Socket socket = null;
		MessageContext messageContext = null;
		MessageRecord messageRecord = null;
		boolean goHead = false;
		String errorNumber = "";
		String errorMessage = "";
		try {
			socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP,
					AfirmeNetConstants.SOCKET_PUERTO, 16);
			messageContext = MessageContextFactory.getContext(socket);

		} catch (SocketFactoryException e) {
			throw new AfirmeNetException("0000",
					"Por el momento no se puede ejecutar su operacion.");
		} catch (MessageContextFactoryException e) {
			throw new AfirmeNetException("0001",
					"Por el momento no se puede ejecutar su operacion.");
		}


		try {
			afitrans=(ORP0104PFMessage) messageContext.getMessageRecord("ORP0104PF");
			afitrans = llenarAS400DeBean(oOrden, afitrans, ope);
			afitrans.send();
			LOG.setSendInfo("cargo", oOrden.getTransactionCode(), oOrden.getAmount().toString(), oOrden.getContractId(), oOrden.getContractId(), oOrden.getDebitAccount(), oOrden.getCreditAccount(), oOrden.getAfirmeNetReference(), afitrans.getFormatName());
			afitrans.destroy();
		} catch (Exception e) {
			throw new AfirmeNetException("0003","Por el momento no se puede ejecutar su operacion.");
		}

		try{
        	//Se recibe info si ocurrio un error o no
        	messageRecord = messageContext.receiveMessage();
        	LOG.setGetInfo("cargo", oOrden.getTransactionCode(), oOrden.getAmount().toString(), oOrden.getContractId(), oOrden.getContractId(), oOrden.getDebitAccount(), oOrden.getCreditAccount(), oOrden.getAfirmeNetReference(), messageRecord);
        	goHead = ((ELEERRMessage) messageRecord).getERRNUM().equals("0");
        	errorNumber = ((ELEERRMessage) messageRecord).getERNU01();
        	errorMessage = ((ELEERRMessage) messageRecord).getERDS01();
        	if(!goHead){//Ocurrio un error
        		//Llenar el comprobante con los errores detectados con 400
                comprobante = new Comprobante(oOrden);
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
        		LOG.setGetInfo("cargo", oOrden.getTransactionCode(), oOrden.getAmount().toString(), oOrden.getContractId(), oOrden.getContractId(), oOrden.getDebitAccount(), oOrden.getCreditAccount(), oOrden.getAfirmeNetReference(), messageRecord);
        		ORP0104PFMessage msjRespuesta = (ORP0104PFMessage) messageRecord;
				oOrden = llenarBeanDeAS400(oOrden, msjRespuesta, ope);
				
        		
                comprobante = new Comprobante(oOrden);
                comprobante.setOrden(oOrden.getOrden());
        		
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
	private ORP0104PFMessage llenarAS400DeBean(CashExpress oOrden, ORP0104PFMessage afitrans, CashExpressOperacion ope) {
		afitrans.setOPCOPE(ope.getValor());
		afitrans.setOPCON(oOrden.getContractId());
		afitrans.setOPCTE(oOrden.getClientId());
		afitrans.setOPCTA(oOrden.getDebitAccount());
		afitrans.setOPNOME(oOrden.getOrigen().getNickname());
		afitrans.setOPCEL(oOrden.getCreditAccount());
		afitrans.setOPNOMB(oOrden.getBeneficiaryName());
		
		if(oOrden.getReferenceNumber()!=null)
			afitrans.setOPFOLAU(oOrden.getReferenceNumber());
		afitrans.setOPNUMT(TipoTransferencia.ORDENES_DE_PAGO_O_ENVIO_DE_DINERO.getValor());
		if(oOrden.getOrden()!=null)
			afitrans.setOPFOLOP(oOrden.getOrden());
		
		if(CashExpressOperacion.CANCELACION!=ope)
			afitrans.setOPCVE(oOrden.getClave());
		afitrans.setOPIMP(oOrden.getAmount());
		afitrans.setOPDESC(oOrden.getDescription());
		return afitrans;
	}
	private CashExpress llenarBeanDeAS400(CashExpress oOrden, ORP0104PFMessage afitrans, CashExpressOperacion ope) {
		oOrden.setContractId(afitrans.getOPCON());
		oOrden.setClientId(afitrans.getOPCTE());
		oOrden.setDebitAccount(afitrans.getOPCTA());
		Cuenta origen= new Cuenta();
		origen.setNickname(afitrans.getOPNOME());
		origen.setNumber(afitrans.getOPCTA());
		oOrden.setOrigen(origen);
		
		
		oOrden.setCreditAccount(afitrans.getOPCEL());
		oOrden.setBeneficiaryName(afitrans.getOPNOMB());
		oOrden.setReferenceNumber(afitrans.getOPFOLAU());
		//oOrden.setNumeroTransaccion(afitrans.getOPNUMT());
		//oOrden.setFolioOperacion(afitrans.getBigDecimalOPFOLOP());
		oOrden.setOrden(afitrans.getOPFOLOP());
		//oOrden.setReferencia(afitrans.getOPREFE());
		oOrden.setClave(afitrans.getOPCVE());
		oOrden.setAmount(afitrans.getBigDecimalOPIMP());
		oOrden.setDescription(afitrans.getOPDESC());
		oOrden.setCommision(afitrans.getBigDecimalOPCOMIS());
		oOrden.setIva(afitrans.getBigDecimalOPIVA());
		oOrden.setEmailBeneficiary(afitrans.getOPMAIL());

		oOrden.setProgrammingDate(afitrans.getOPFECH());
		oOrden.setProgrammingHour(afitrans.getOPHORA());
		oOrden.setState(afitrans.getOPSTAT());

		return oOrden;
	}
	
}
