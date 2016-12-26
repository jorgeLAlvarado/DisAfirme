package com.afirme.afirmenet.service.impl.transferencia;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.enums.ConfigProperties;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.exception.MessageContextFactoryException;
import com.afirme.afirmenet.exception.SocketFactoryException;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.ibs.messages.ECONFDSMessage;
import com.afirme.afirmenet.ibs.messages.EDL013001Message;
import com.afirme.afirmenet.ibs.messages.EDL013610Message;
import com.afirme.afirmenet.ibs.messages.EDL014002Message;
import com.afirme.afirmenet.ibs.messages.EFT000010Message;
import com.afirme.afirmenet.ibs.messages.ELEERRMessage;
import com.afirme.afirmenet.ibs.messages.INTRFDSJVMessage;
import com.afirme.afirmenet.ibs.messages.INVP01001Message;
import com.afirme.afirmenet.model.inversiones.Inversion;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.ComprobanteTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.transferencia.TransferenciaAfirmeNet;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.socket.MessageContextFactory;
import com.afirme.afirmenet.utils.socket.SocketFactory;
import com.afirme.afirmenet.utils.time.TimeUtils;
import com.datapro.sockets.MessageContext;
import com.datapro.sockets.MessageRecord;

@Service("inversionPerfectaServiceImpl")
public class InversionPerfectaServiceImpl implements TransferenciaAfirmeNet {

	static final AfirmeNetLog LOG = new 
			AfirmeNetLog(InversionPerfectaServiceImpl.class);

	@Override
	public ComprobanteTransferencia ejecutaTransferencia(
			TransferenciaBase transferencia) throws AfirmeNetException {

		Inversion transferenciaBase = (Inversion) transferencia;
		transferenciaBase.setAfirmeNetReference(Util.getRefNum());

		Comprobante comprobante = null;
		EDL014002Message msgInv = null;

		Socket socket = null;
		MessageContext messageContext = null;
		MessageRecord messageRecord = null;
		boolean goHead = false;
		String errorNumber = "";
		String errorMessage = "";
		try {
			socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP,
					AfirmeNetConstants.SOCKET_PUERTO, 11);
			messageContext = MessageContextFactory.getContext(socket);

		} catch (SocketFactoryException e) {
			throw new AfirmeNetException("0000",
					"Por el momento no se puede ejecutar su operacion.");
		} catch (MessageContextFactoryException e) {
			throw new AfirmeNetException("0001",
					"Por el momento no se puede ejecutar su operacion.");
		}



		try {
			msgInv = (EDL014002Message) messageContext
					.getMessageRecord("EDL014002");
			msgInv.setH02USERID(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.INVERSION_PERFECTA_USUARIO_OPERADOR, String.class));
			msgInv.setH02PROGRM("ESS0090");
			msgInv.setH02TIMSYS(Util.getTimeStampAAMMDD() + Util.getTimeStampHHMMSS());
			msgInv.setH02SCRCOD("");
			msgInv.setH02OPECOD("");
			msgInv.setH02FLGMAS("");
			msgInv.setH02FLGWK1("");
			msgInv.setH02FLGWK2("");
			msgInv.setH02FLGWK3("");
			msgInv.setE02DEAACC(transferenciaBase.getNumeroInversion());
			msgInv.setE02ACTION("A");
			msgInv.setE02MSGTXT(transferenciaBase.getDescription());
			
			LOG.setSendInfo("inversionE", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), "", transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), msgInv.getFormatName());			
			LOG.setSendInfo("cargo", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), transferenciaBase.getContractId(), transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), msgInv.getFormatName());
			msgInv.send();
			msgInv.destroy();

		} catch (Exception e) {
			throw new AfirmeNetException("0003",
					"Por el momento no se puede ejecutar su operacion.");
		}

		try {
			// Se recibe info si ocurrio un error o no
			messageRecord = messageContext.receiveMessage();
			LOG.setGetInfo("cargo", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), transferenciaBase.getContractId(), transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), messageRecord);
			goHead = ((ELEERRMessage) messageRecord).getERRNUM().equals("0");
			errorNumber = ((ELEERRMessage) messageRecord).getERNU01();
			errorMessage = ((ELEERRMessage) messageRecord).getERDS01();
			if (!goHead) {// Ocurrio un error
				// Llenar el comprobante con los errores detectados con 400
				comprobante = new Comprobante(transferenciaBase);
				Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
						0);
				// Busca la Excepcion
				CodigoExcepcion error = CodigoExcepcion.findByValue(Integer
						.valueOf(errorNumber));
				if (error == null) {
					// Error generico
					error = CodigoExcepcion.ERR_3000;
					errors.put(error, errorMessage);
				} else {
					errors.put(error, errorMessage);
				}
				comprobante.setErrores(errors);
				transferenciaBase.setErrores(errors);
			} else {
				transferenciaBase.setOperationDate(TimeUtils.getDateFormat(transferenciaBase.getFechaVencimientoDate(), TimeUtils.DEFAULT_DATE_FORMAT));
				// Llenar el comprobante con la operacion efectuada
				comprobante = new Comprobante(transferenciaBase);
				comprobante.setAfirmeNetReference(transferenciaBase.getNumeroInversion().toString());
				comprobante.setPlazaReceiving(String.valueOf(transferenciaBase.getPlazo()));
				comprobante.setIva(transferenciaBase.getIntereses());
				comprobante.setFlag(transferenciaBase.getTasa());
				comprobante.setCommision(transferenciaBase.getTasaInteres());
				//comprobante.setTrackingCode(transferenciaBase.getFolioAuditoria().toString());
				comprobante.setBankReceiving(transferenciaBase.getClaveProducto());
			}

		} catch (SocketTimeoutException e) {
			throw new AfirmeNetException(
					"0003",
					"Ha ocurrido un error al intentar validar la informacion. Por favor, intente dentro de 15 minutos.");
		} catch (Exception e) {
			throw new AfirmeNetException("0003",
					"Por el momento no se puede ejecutar su operacion.");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
		//Se obtiene el folio
		if(transferenciaBase.getErrores()==null){
			obtenerFolio(transferenciaBase);
			if(transferenciaBase.getFolioAuditoria()!=null)
				comprobante.setTrackingCode(transferenciaBase.getFolioAuditoria().toString());
		}
		return comprobante;
	}

	public void enviaSolicitud(Inversion inversion) {
		
		//inversion.setAfirmeNetReference(Util.getRefNum());

		
		EDL013610Message msgInv = null;

		// Invocacion del Socket
		Socket socket = null;
		MessageContext messageContext = null;
		MessageRecord messageRecord = null;
		//boolean goHead = false;
		String errorNumber = "";
		String errorMessage = "";
		try {
			socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP,
					AfirmeNetConstants.SOCKET_PUERTO, 11);
			messageContext = MessageContextFactory.getContext(socket);

		} catch (SocketFactoryException e) {
			throw new AfirmeNetException("0000",
					"Por el momento no se puede ejecutar su operacion.");
		} catch (MessageContextFactoryException e) {
			throw new AfirmeNetException("0001",
					"Por el momento no se puede ejecutar su operacion.");
		}

		try {
			msgInv = (EDL013610Message) messageContext.getMessageRecord("EDL013610");
			msgInv.setH10USERID(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.INVERSION_PERFECTA_USUARIO_OPERADOR, String.class));
			msgInv.setH10PROGRM("EDL0136");
			msgInv.setH10TIMSYS(Util.getTimeStampAAMMDD() + Util.getTimeStampHHMMSS());
			msgInv.setH10FLGWK3("P");
			msgInv.setE10DEATRM(String.valueOf(inversion.getPlazo()));
			msgInv.setE10DEATRC("D");
			msgInv.setE10DEAMD1("0");
			msgInv.setE10DEAMD2("0");
			msgInv.setE10DEAMD3("0");
			msgInv.setE10DEAAMT(inversion.getAmount());// importe
			msgInv.setE10DEARTE("0");
			msgInv.setE10DEAPRO(inversion.getClaveProducto());
			msgInv.setE10DESCR1("E01DEAMD1");
			msgInv.setE10DESCR2("E01DEAMD2");
			msgInv.setE10DESCR3("E01DEAMD3");
			msgInv.setE10DESCR4("E01DEARTE");
			msgInv.setE10ACMACC(inversion.getOrigen().getNumber());
			msgInv.setE10APCCCY("");
			msgInv.setE10APCCDT("");
			msgInv.setE10DESCR5("E01DEAPRO");
			msgInv.setE10DESCR6("E01DEACCY");
			msgInv.setE10DESCR7("E01DEARTB");
			msgInv.setE10DEATYP("CDS");
			msgInv.setE10DEAF01("F");
			msgInv.setE10CUSCUN(inversion.getCliente());
			msgInv.setE10CUSNA1(inversion.getOrigen().getDescription());
			msgInv.setE10ACMMNB("0");
			LOG.setSendInfo("inversionS", inversion.getTransactionCode(), inversion.getAmount().toString(), inversion.getContractId(), "", inversion.getDebitAccount(), inversion.getCreditAccount(), inversion.getAfirmeNetReference(), msgInv.getFormatName());
			msgInv.send();
			msgInv.destroy();

		} catch (Exception e) {
			throw new AfirmeNetException("0003",
					"Por el momento no se puede ejecutar su operacion.");
		}

		try {
			// Se recibe info si ocurrio un error o no
			messageRecord = messageContext.receiveMessage();
			LOG.setGetInfo("inversionS", inversion.getTransactionCode(), inversion.getAmount().toString(), inversion.getContractId(), "", inversion.getDebitAccount(), inversion.getCreditAccount(), inversion.getAfirmeNetReference(), messageRecord);
			if (messageRecord.getFormatName().equals("ELEERR")) {
				ELEERRMessage msgError = (ELEERRMessage) messageRecord;
				errorNumber = msgError.getERNU01();
				errorMessage = msgError.getERDS01();
				if (!errorNumber.trim().equals("0")) {
					LOG.error("Error ["+ errorNumber.trim() + "]: "+ errorMessage);
					
					Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
							0);
					// Busca la Excepcion
					CodigoExcepcion error = CodigoExcepcion.findByValue(Integer
							.valueOf(errorNumber));
					if (error == null) {
						// Error generico
						error = CodigoExcepcion.ERR_3000;
						errors.put(error, errorMessage);
					} else {
						errors.put(error, errorMessage);
					}
					inversion.setErrores(errors);
					
				}
			}else {
				// no devuelve bean de error cuando es correcto
				// Receive Data
				try {
					// newmessage = mc.receiveMessage();
					
					if (messageRecord.getFormatName().equals("EDL013610")) {

						msgInv = new EDL013610Message();
						System.out.println("INVPERFECT Message Received");
						msgInv = (EDL013610Message) messageRecord;
						final String STRYY = "20"; // dos digitos iniciales
						// del año
						int regalo = 0;

						/*
						jInvResp.setDeaP(jInvP.getDeaP());
						jInvResp.setCtaContable(jInvP.getCtaContable());
						jInvResp.setMoneda(jInvP.getMoneda());
						jInvResp.setCtaPas(jInvP.getCtaPas());
						jInvResp.setConcepto(jInvP.getConcepto());

						jInvResp.setCantidad(msgInv.getE10DEAAMT().trim());
						jInvResp.setCliente(msgInv.getE10CUSCUN().trim());
						jInvResp.setCuentaCargo(msgInv.getE10ACMACC()
								.trim());
						jInvResp.setNombre(msgInv.getE10CUSNA1().trim());
						jInvResp.setPlazo(msgInv.getE10DEATRM().trim());
						 */
						BigDecimal fechaFenc= new BigDecimal(STRYY+msgInv.getE10DEAMD3().trim()+msgInv.getE10DEAMD1().trim()+msgInv.getE10DEAMD2().trim());
						inversion.setFechaVencimiento(fechaFenc);
						// jInvP.setInteres(msgInv.getE10DEARTE().trim());

						regalo = Integer.parseInt(msgInv.getE10DEATRM()
								.trim());
						if (regalo < 90) {
							inversion.setMesesRegalo(AfirmeNetConstants.getValorConfigProperties(ConfigProperties.PROPERTYID_INV_PERFECTA_60, String.class)+" de regalo incluidos en la tasa de interés");
						} else if (regalo >= 90 && regalo < 180) {
							inversion.setMesesRegalo(AfirmeNetConstants.getValorConfigProperties(ConfigProperties.PROPERTYID_INV_PERFECTA_90, String.class)+" de regalo incluidos en la tasa de interés");
						} else if (regalo >= 180 && regalo < 360) {
							inversion.setMesesRegalo(AfirmeNetConstants.getValorConfigProperties(ConfigProperties.PROPERTYID_INV_PERFECTA_180, String.class)+" de regalo incluidos en la tasa de interés");
						} else if (regalo >= 360) {
							inversion.setMesesRegalo(AfirmeNetConstants.getValorConfigProperties(ConfigProperties.PROPERTYID_INV_PERFECTA_360, String.class)+"  de regalo incluidos en la tasa de interés");
						}

						

						System.out.println("fecha venc: "
								+ msgInv.getE10DEAMD2().trim()
								+ msgInv.getE10DEAMD1().trim()
								+ msgInv.getE10DEAMD3().trim());

						inversion.setTasaInteres(msgInv.getBigDecimalE10DEARTE());
						inversion.setTasa(msgInv.getE10DEAF01().trim());

						// lo seteamos a la sesion
						//ses.setAttribute("jInvResp", jInvResp);
						//band = true;

					} else {
						LOG.error("Message "
										+ messageRecord.getFormatName()
										+ " received.");
					}

				} catch (Exception e) {
					LOG.error("Error! Ha ocurrido un error al procesar "
							+ "La Inversión Perfecta. Al tratar de recibir la información desde el 400 "
							+ "(Receive Data). "+ e.getMessage());
					
					Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
							0);
					// Busca la Excepcion
					CodigoExcepcion error =  CodigoExcepcion.ERR_3000;
					errors.put(error, "Error! Ha ocurrido un error al procesar "
							+ "La Inversión Perfecta. Al tratar de recibir la información desde el 400 "
							+ "(Receive Data). ");
					
					inversion.setErrores(errors);

									
					throw new RuntimeException(
							"Socket Communication Error in receive data");
				}

			}
			
			

		} catch (SocketTimeoutException e) {
			throw new AfirmeNetException(
					"0003",
					"Ha ocurrido un error al intentar validar la informacion. Por favor, intente dentro de 15 minutos.");
		} catch (Exception e) {
			throw new AfirmeNetException("0003",
					"Por el momento no se puede ejecutar su operacion.");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public void enviaApertura(Inversion inversion) {
		EDL013001Message msgInv = null;

		// Invocacion del Socket
		Socket socket = null;
		MessageContext messageContext = null;
		MessageRecord messageRecord = null;
		boolean goHead = false;
		String errorNumber = "";
		String errorMessage = "";
		try {
			socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP,
					AfirmeNetConstants.SOCKET_PUERTO, 10);
			messageContext = MessageContextFactory.getContext(socket);

		} catch (SocketFactoryException e) {
			throw new AfirmeNetException("0000",
					"Por el momento no se puede ejecutar su operacion.");
		} catch (MessageContextFactoryException e) {
			throw new AfirmeNetException("0001",
					"Por el momento no se puede ejecutar su operacion.");
		}

		try {
			msgInv = (EDL013001Message) messageContext.getMessageRecord("EDL013001");

			msgInv.setH01USERID(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.INVERSION_PERFECTA_USUARIO_OPERADOR, String.class));
			msgInv.setH01PROGRM("EDL0130");
			msgInv.setH01TIMSYS(Util.getTimeStampAAMMDD() + Util.getTimeStampHHMMSS());
			msgInv.setH01SCRCOD("01");
			msgInv.setH01OPECOD("0005"); // codigo de operacion
			msgInv.setH01FLGMAS("N");

			msgInv.setE01DEAACC("999999999999"); // ?
			msgInv.setE01DEAACD("11");
			msgInv.setE01DEAPRO(inversion.getClaveProducto());
			msgInv.setE01DEACUN(inversion.getCliente());
			msgInv.setE01CUSNA1(inversion.getOrigen().getDescription());

			msgInv.setE01DEAOD1(Util.getTimeStampAAMMDD().substring(4, 6)); // DD
			msgInv.setE01DEAOD2(Util.getTimeStampAAMMDD().substring(2, 4)); // MM
			msgInv.setE01DEAOD3(Util.getTimeStampAAMMDD().substring(0, 2)); // YY
			msgInv.setE01DEATRM(String.valueOf(inversion.getPlazo()));
			msgInv.setE01DEATRC("D");
			//Se setea el tipo de servicio de inversión seleccionado por el cliente
			msgInv.setE01DEADIB(inversion.getServicioInversion());

			msgInv.setE01DEAMD1(inversion.getFechaVencimiento().toString().substring(7)); // fecha Vencimiento
			msgInv.setE01DEAMD2(inversion.getFechaVencimiento().toString().substring(4,6));
			msgInv.setE01DEAMD3(inversion.getFechaVencimiento().toString().substring(2,4));

			msgInv.setE01DEARTE(inversion.getTasaInteres()); // tasa de interes
			msgInv.setE01RATE("0");
			msgInv.setE01FLTRTE("0");
			msgInv.setE01DEARD1("0");
			msgInv.setE01DEARD2("0");
			msgInv.setE01DEARD3("0");
			msgInv.setE01DEAEXR("0");
			msgInv.setE01DEABNK("01"); // bank
			msgInv.setE01DEABRN("501"); // branch
			msgInv.setE01DEAGLN(inversion.getCuentaContable()); // cuenta contable
			msgInv.setE01DEACCY(inversion.getCurrency());
			msgInv.setE01DEATYP("CDS");
			msgInv.setE01DEAOAM(inversion.getAmount()); // cantidad??
			msgInv.setE01DEABAS("360"); // base
			msgInv.setE01DEACLF("");
			msgInv.setE01DEAICT("P");
			msgInv.setE01DEAIFL("1");

			msgInv.setE01DEAWHF("3");
			msgInv.setE01DEADLC("1");
			msgInv.setE01DEACPE("0");
			msgInv.setE01DEACCN("0");
			msgInv.setE01DEAMLA("0");
			msgInv.setE01DEAMEP("0");
			msgInv.setE01DEAREA("0");
			msgInv.setE01DEAMEI("0");
			msgInv.setE01NEWMD1("0");
			msgInv.setE01NEWMD2("0");
			msgInv.setE01NEWMD3("0");
			msgInv.setE01DEARTB("P");
			msgInv.setE01APCTNU("0");
			msgInv.setE01DEAIDT("");
			msgInv.setE01NOMCOM("INVERSION PERFECTA");
			msgInv.setE01DEAOPI("0");
			msgInv.setE01DEAF01(inversion.getTasa()); // tipo de tasa
			msgInv.setE01DEASOF("N");
			msgInv.setE01OFFOP1("01");

			msgInv.setE01OFFBK1("01");
			msgInv.setE01OFFBR1("1");
			msgInv.setE01OFFBR2("0");
			msgInv.setE01OFFBR3("0");
			msgInv.setE01OFFBR4("0");
			msgInv.setE01OFFBR5("0");
			msgInv.setE01OFFBR6("0");
			msgInv.setE01OFFBR7("0");
			msgInv.setE01OFFBR8("0");
			msgInv.setE01OFFBR9("0");
			msgInv.setE01OFFCY1(inversion.getCurrency());
			msgInv.setE01OFFGL1(inversion.getCuentaPasiva()); // cuenta pasiva
			msgInv.setE01OFFGL2("0");
			msgInv.setE01OFFGL3("0");
			msgInv.setE01OFFGL4("0");
			msgInv.setE01OFFGL5("0");
			msgInv.setE01OFFGL6("0");
			msgInv.setE01OFFGL7("0");
			msgInv.setE01OFFGL8("0");

			msgInv.setE01OFFGL9("0");
			msgInv.setE01OFFAC1(inversion.getOrigen().getNumber());
			msgInv.setE01OFFAC2("0");
			msgInv.setE01OFFAC3("0");
			msgInv.setE01OFFAC4("0");
			msgInv.setE01OFFAC5("0");
			msgInv.setE01OFFAC6("0");
			msgInv.setE01OFFAC7("0");
			msgInv.setE01OFFAC8("0");
			msgInv.setE01OFFAC9("0");
			msgInv.setE01OFFAM1("0");
			msgInv.setE01OFFAM2("0");
			msgInv.setE01OFFAM3("0");
			msgInv.setE01OFFAM4("0");
			msgInv.setE01OFFAM5("0");
			msgInv.setE01OFFAM6("0");
			msgInv.setE01OFFAM7("0");
			msgInv.setE01OFFAM8("0");
			msgInv.setE01OFFAM9("0");

			msgInv.setE01OFFEQV(inversion.getAmount()); // cantidad
			msgInv.setE01DEAXRL("0");
			msgInv.setE01DEAXRP("0");
			msgInv.setE01DEANFR("0");
			msgInv.setE01DEAAFR("0");
			msgInv.setE01GPOECO("0");
			msgInv.setE01GPOAMT("0");
			msgInv.setE01DEAAVI("0");
			msgInv.setE01DEAIRT("0");
			msgInv.setE01REMARK("");
			msgInv.setE01DEAROY("0");
			LOG.setSendInfo("inversionA", inversion.getTransactionCode(), inversion.getAmount().toString(), inversion.getContractId(), "", inversion.getDebitAccount(), inversion.getCreditAccount(), inversion.getAfirmeNetReference(), msgInv.getFormatName());
			msgInv.send();
			//System.out.println("INVPERFECT Message Sent: "+msgInv.toString());
			msgInv.destroy();
			//System.out.println("INVPERFECT Message Sent");

		} catch (Exception e) {
			throw new AfirmeNetException("0003",
					"Por el momento no se puede ejecutar su operacion.");
		}

		try {
			// Se recibe info si ocurrio un error o no
			messageRecord = messageContext.receiveMessage();
			LOG.setGetInfo("inversionA", inversion.getTransactionCode(), inversion.getAmount().toString(), inversion.getContractId(), "", inversion.getDebitAccount(), inversion.getCreditAccount(), inversion.getAfirmeNetReference(), messageRecord);
			goHead = ((ELEERRMessage) messageRecord).getERRNUM().equals("0");
			errorNumber = ((ELEERRMessage) messageRecord).getERNU01();
			errorMessage = ((ELEERRMessage) messageRecord).getERDS01();
			if (!goHead) {// Ocurrio un error
				// Llenar el comprobante con los errores detectados con 400
				
				Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
						0);
				// Busca la Excepcion
				CodigoExcepcion error = CodigoExcepcion.findByValue(Integer
						.valueOf(errorNumber));
				if (error == null) {
					// Error generico
					error = CodigoExcepcion.ERR_3000;
					errors.put(error, errorMessage);
				} else {
					errors.put(error, errorMessage);
				}
				inversion.setErrores(errors);
			} else {
				// Se recibe la informacion de la operacion si no se genero un
				// error previo
				messageRecord = messageContext.receiveMessage();
				LOG.setGetInfo("inversionA", inversion.getTransactionCode(), inversion.getAmount().toString(), inversion.getContractId(), "", inversion.getDebitAccount(), inversion.getCreditAccount(), inversion.getAfirmeNetReference(), messageRecord);
				if (messageRecord.getFormatName().equals("EFT000010")) {

					EFT000010Message msgResp = new EFT000010Message();
					System.out.println("INVPERFECT Message Received");
					msgResp = (EFT000010Message) messageRecord;
					final String STRYY = "20";
					String dia = "", mes = "";

					//JBInvPDatos_E jInvApp = new JBInvPDatos_E();

//					jInvApp.setCuentaCargo(jInvConf.getCuentaCargo()); 
//					jInvApp.setConcepto(jInvConf.getConcepto()); 
//					jInvApp.setTasa(jInvConf.getTasa());
//					jInvApp.setMRegalo(jInvConf.getMRegalo());

					inversion.setTasaInteres(msgResp.getBigDecimalE10DEARTE());
					inversion.setNumeroInversion(msgResp.getBigDecimalE10DEAACC());
					inversion.setCliente(msgResp.getE10DEACUN().trim());
					//inversion.setNombre(msgResp.getE10CUSNA1().trim()); 
					inversion.setAmount(msgResp.getBigDecimalE10DEAOAM());
					inversion.setCantidadLetra(msgResp.getE10LETOAM().trim());
					//inversion.setBanco(msgResp.getE10BANKNM().trim());
					//inversion.setSucursal(msgResp.getE10BRNADR().trim());
					//inversion.setSucCiudad(msgResp.getE10BRNCIT().trim());
					inversion.setPlazo(msgResp.getBigDecimalE10DEATRM().intValue());
					inversion.setClaveProducto(msgResp.getE10DEAPRO().trim());
					mes = msgResp.getE10DEAMD2().trim().length() == 1 ? "0"
							+ msgResp.getE10DEAMD2().trim() : msgResp
							.getE10DEAMD2().trim();
					dia = msgResp.getE10DEAMD1().trim().length() == 1 ? "0"
							+ msgResp.getE10DEAMD1().trim() : msgResp
							.getE10DEAMD1().trim();

					BigDecimal fechaFenc= new BigDecimal(STRYY+msgResp.getE10DEAMD3().trim()+mes+dia);
					inversion.setFechaVencimiento(fechaFenc);
					
					System.out.println(inversion.getNumeroInversion());

					// lo seteamos a la sesion

				} else {
					LOG.error("Message "
							+ messageRecord.getFormatName() + " received.");
					throw new AfirmeNetException(
							"0003",
							"Error! La operación no pudo ser registrada. "
									+ "Intente nuevamente. ");
				}

			}

		} catch (SocketTimeoutException e) {
			throw new AfirmeNetException(
					"0003",
					"Ha ocurrido un error al intentar validar la informacion. Por favor, intente dentro de 15 minutos.");
		} catch (Exception e) {
			throw new AfirmeNetException("0003",
					"Por el momento no se puede ejecutar su operacion.");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
	@Override
	public List<? extends ComprobanteTransferencia> ejecutaTransferencia(
			List<TransferenciaBase> transferencias) {
		List<ComprobanteTransferencia> comprobantes = new ArrayList<ComprobanteTransferencia>(
				0);
		for (TransferenciaBase tx : transferencias) {
			try {
				if (tx.getErrores() == null || tx.getErrores().isEmpty()) {
					comprobantes.add(ejecutaTransferencia(tx));
				} else {
					comprobantes.add(new Comprobante(tx));
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
					errors.put(error, e.getMessage());
				}
				comprobante.setErrores(errors);
				comprobantes.add(comprobante);
			}
		}
		return comprobantes;
	}

	public void obtenerFolio(Inversion inversion) {
		

		INVP01001Message msgInv = null;

		// Invocacion del Socket
		Socket socket = null;
		MessageContext messageContext = null;
		MessageRecord messageRecord = null;
		//boolean goHead = false;
		String errorNumber = "";
		String errorMessage = "";
		try {
			socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP,
					AfirmeNetConstants.SOCKET_PUERTO, 11);
			messageContext = MessageContextFactory.getContext(socket);

		} catch (SocketFactoryException e) {
			throw new AfirmeNetException("0000",
					"Por el momento no se puede ejecutar su operacion.");
		} catch (MessageContextFactoryException e) {
			throw new AfirmeNetException("0001",
					"Por el momento no se puede ejecutar su operacion.");
		}

		try {
			msgInv = (INVP01001Message) messageContext.getMessageRecord("INVP01001");
			msgInv.setH01USERID(AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.INVERSION_PERFECTA_USUARIO_OPERADOR, String.class));
			msgInv.setH01PROGRM("INVP010");
			msgInv.setH01TIMSYS(Util.getTimeStampAAMMDD() + Util.getTimeStampHHMMSS());
			msgInv.setH01OPECOD("0001");
			
			LOG.setSendInfo("inversionFo", inversion.getTransactionCode(), inversion.getAmount().toString(), inversion.getContractId(), "", inversion.getDebitAccount(), inversion.getCreditAccount(), inversion.getAfirmeNetReference(), msgInv.getFormatName());
			msgInv.send();
			msgInv.destroy();

		} catch (Exception e) {
			throw new AfirmeNetException("0003",
					"Por el momento no se puede ejecutar su operacion.");
		}

		try {
			// Se recibe info si ocurrio un error o no
			messageRecord = messageContext.receiveMessage();
			LOG.setGetInfo("inversionFo", inversion.getTransactionCode(), inversion.getAmount().toString(), inversion.getContractId(), "", inversion.getDebitAccount(), inversion.getCreditAccount(), inversion.getAfirmeNetReference(), messageRecord);
			if (messageRecord.getFormatName().equals("ELEERR")) {
				ELEERRMessage msgError = (ELEERRMessage) messageRecord;
				errorNumber = msgError.getERRNUM();
				errorMessage = msgError.getERDS01();
				if (!errorNumber.trim().equals("0")) {
					errorNumber = msgError.getERNU01();
					LOG.error("Error ["+ errorNumber.trim() + "]: "+ errorMessage);
					
					Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
							0);
					// Busca la Excepcion
					CodigoExcepcion error = CodigoExcepcion.findByValue(Integer
							.valueOf(errorNumber));
					if (error == null) {
						// Error generico
						error = CodigoExcepcion.ERR_3000;
						errors.put(error, errorMessage);
					} else {
						errors.put(error, errorMessage);
					}
					inversion.setErrores(errors);
					
				//}
			}else {
				// no devuelve bean de error cuando es correcto
				// Receive Data
				try {
					messageRecord = messageContext.receiveMessage();
					
					if (messageRecord.getFormatName().equals("INVP01001")) {

						msgInv = new INVP01001Message();
						System.out.println("INVPERFECT Message Received");
						msgInv = (INVP01001Message) messageRecord;
						inversion.setFolioAuditoria(msgInv.getBigDecimalS1FOLIO());

					} else {
						LOG.error("Message "
										+ messageRecord.getFormatName()
										+ " received.");
					}

				} catch (Exception e) {
					LOG.error("Error! Ha ocurrido un error al procesar "
							+ "La Inversión Perfecta. Al tratar de recibir la información desde el 400 "
							+ "(Receive Data). "+ e.getMessage());
					
					Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(
							0);
					// Busca la Excepcion
					CodigoExcepcion error =  CodigoExcepcion.ERR_3000;
					errors.put(error, "Error! Ha ocurrido un error al procesar "
							+ "La Inversión Perfecta. Al tratar de recibir la información desde el 400 "
							+ "(Receive Data). ");
					
					inversion.setErrores(errors);

									
					throw new RuntimeException(
							"Socket Communication Error in receive data");
				}

			}
			}
			

		} catch (SocketTimeoutException e) {
			throw new AfirmeNetException(
					"0003",
					"Ha ocurrido un error al intentar validar la informacion. Por favor, intente dentro de 15 minutos.");
		} catch (Exception e) {
			throw new AfirmeNetException("0003",
					"Por el momento no se puede ejecutar su operacion.");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
