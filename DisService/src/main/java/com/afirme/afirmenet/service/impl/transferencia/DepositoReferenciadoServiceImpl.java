package com.afirme.afirmenet.service.impl.transferencia;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.exception.MessageContextFactoryException;
import com.afirme.afirmenet.exception.SocketFactoryException;
import com.afirme.afirmenet.ibs.messages.ELEERRMessage;
import com.afirme.afirmenet.ibs.messages.FOLIODSJVMessage;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.socket.MessageContextFactory;
import com.afirme.afirmenet.utils.socket.SocketFactory;
import com.datapro.sockets.MessageContext;
import com.datapro.sockets.MessageRecord;

@Service("depositoReferenciadoServiceImpl")
public class DepositoReferenciadoServiceImpl extends
		TransferenciaGenericaServiceImpl {

	public void getFolioImpuetos(TransferenciaBase transferenciaBase){
		// Declaracion de Variables
				FOLIODSJVMessage fol  = null;
				Socket socket = null;
				MessageContext messageContext = null;

				MessageRecord messageRecord = null;
				boolean goHead = false;
				String errorNumber = "";
				String errorMessage = "";
				// Invocacion del Socket
				try {
					socket = SocketFactory.getSocket(AfirmeNetConstants.SOCKET_IP,
							AfirmeNetConstants.SOCKET_PUERTO + 1);
					messageContext = MessageContextFactory.getContext(socket);

				} catch (SocketFactoryException e) {
					throw new AfirmeNetException("0000",
							"Por el momento no se puede ejecutar su operacion.");
				} catch (MessageContextFactoryException e) {
					throw new AfirmeNetException("0001",
							"Por el momento no se puede ejecutar su operacion.");
				}

				try {
					// Incializa Bean de operacion
					fol = (FOLIODSJVMessage) messageContext
							.getMessageRecord("FOLIODSJV");
					// Setea informacion en el bean de operacion
					fol.setFOLUSR(transferenciaBase.getContractId());
					fol.setFOLRF1("");
					// Evia peticion a AS400
					LOG.setSendInfo("folio", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), "", transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), fol.getFormatName());
					fol.send();
					fol.destroy();
					// Se evalua la repsuesta de AS400
					// Se recibe info si ocurrio un error o no
					messageRecord = messageContext.receiveMessage();
					LOG.setGetInfo("folio", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), "", transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), messageRecord);
					goHead = ((ELEERRMessage) messageRecord).getERRNUM().equals("0");
					errorNumber = ((ELEERRMessage) messageRecord).getERNU01();
					errorMessage = ((ELEERRMessage) messageRecord).getERDS01();
					if (!goHead) {// Ocurrio un error
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
						transferenciaBase.setErrores(errors);
					} else {
						// Se recibe la informacion de la operacion si no se genero un
						// error previo
						messageRecord = messageContext.receiveMessage();
						LOG.setGetInfo("folio", transferenciaBase.getTransactionCode(), transferenciaBase.getAmount().toString(), transferenciaBase.getContractId(), "", transferenciaBase.getDebitAccount(), transferenciaBase.getCreditAccount(), transferenciaBase.getAfirmeNetReference(), messageRecord);
						// La transferencia puede ser SPEI o TEF
						if (messageRecord.getClass().getName().equals("com.afirme.afirmenet.ibs.messages.FOLIODSJVMessage")) {
							String folio = (((FOLIODSJVMessage) messageRecord).getFOLNUM());
							//transferenciaBase = new Comprobante(transferenciaBase);
							transferenciaBase.setTrackingCode(folio);
						}
					}
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
	
	/* 
	 * Función:  getLlavePagoV2 (Principal Generacion de Llave) 
	 * Recibe:   Fecha de Operación, Línea de Captura, Importe Pagado en la Operación, Número de Operación Banco.
	 * Devuelve: Llave de Pago (String de 10 posiciones)  
	 * */
	public String getLlavePagoV2(String sFecha, String pLineaCaptura, String pTotal, String pNoOperacion) {
		String sLlave = "";
		int i = 0;
		long x = 0;
		char cTotal[], cCadena1[] ;
		String sTotal = "";
		String sCadena1 = "";
		String sCadena2 = "";
		String sMesHex = "";
		String sCadena3 = "";
		char cResultadoDigito = ' ';
		//SimpleDateFormat sdfYMD = new SimpleDateFormat ("yyyyMMdd");
		//SimpleDateFormat sdfM = new SimpleDateFormat ("MM");
		
		// Eliminar de la fecha los separadores de fecha (diagonal, guión).Convertir la fecha al formato: aaaammdd
		//sFecha = sdfYMD.format(pFecha);
		
		// En el total efectivamente pagado recibido eliminar los separadores de miles (generalmente comas) y los decimales.
		cTotal = pTotal.toCharArray();
		for(i=0;i<cTotal.length;i++){
			if(cTotal[i] == ',' || cTotal[i] == '.') {
			} else {
				sTotal = sTotal + cTotal[i];
			}
			if(cTotal[i] == '.') {
				i = cTotal.length; // TRUNCAMOS EL PUNTO DECIMAL Y LO QUE SIGA
			}
		}
		
		// Concatenar los valores recibidos de la siguiente manera:
		// cadena1 = "S"+ numero de operación + nombre + "T" + fecha + total efectivamente pagado + "A"
		sCadena1 = "S" + pNoOperacion + pLineaCaptura + "T" + sFecha + sTotal + "A";
		
		// Convertir a mayúsculas las letras del nombre o razón social.
		sCadena1 = sCadena1.toUpperCase(); 
		
		// Eliminar los espacios en blanco de cadena1
		cCadena1 = sCadena1.toCharArray();
		sCadena1 = "";
		for(i=0;i<cCadena1.length;i++){
			if(cCadena1[i] == ' ') {
			}else if(cCadena1[i]==(char)'Á'){
				sCadena1 = sCadena1 + 'A';
			} else if(cCadena1[i] == 'É') {
				sCadena1 = sCadena1 + 'E';
			} else if(cCadena1[i] == 'Í') {
				sCadena1 = sCadena1 + 'I';
			} else if(cCadena1[i] == 'Ó') {
				sCadena1 = sCadena1 + 'O';
			} else if(cCadena1[i] == 'Ú') {
				sCadena1 = sCadena1 + 'U';
			} else if(cCadena1[i] == '\'') {
				sCadena1 = sCadena1 + (char)(39);
			} else if(cCadena1[i] == (char)(147)) {
				sCadena1 = sCadena1 + '"';
			} else if(cCadena1[i] == (char)(148)) {
				sCadena1 = sCadena1 + '"';
			} else if(cCadena1[i] == '´') {
				sCadena1 = sCadena1 + (char)(39);
			} else { 
				sCadena1 = sCadena1 + cCadena1[i];
			}
		}
		
		// Llamar_a: Llena_tabla().
		//doLlenaTabla();
		
		x = this.doCalculaCrc32(sCadena1);
		
		// cadena2 = convertir_a_cadena(convertir_a_hexadecimal(x))
		sCadena2 = Long.toHexString(x);
		
		// Si en la conversion a hexadecimal de x, hay letras minusculas contenidas en el resultado, convertir la cadena a letras mayusculas.
		sCadena2 = sCadena2.toUpperCase();
		
		// Si la longitud de cadena2 es menor a 8 caracteres, colocar ceros (0) a la izquierda hasta alcanzar la longitud de 8 caracteres.
		sCadena2 = ("00000000" + sCadena2).substring(("00000000" + sCadena2).length() - 8, ("00000000" + sCadena2).length());
		
		//if(sCadena2.length() < 8){
		//}
		//mes_hex = convertir_a_hexadecimal(x)
		//sMesHex = Long.toHexString((new Long(sdfM.format(pFecha))).longValue()).toUpperCase();
		String mes=sFecha.substring(4, 6);
		sMesHex = Long.toHexString((new Long(mes)).longValue()).toUpperCase();
		
		//cadena3 = cadena2 + convertir_a_cadena(mes_hex)
		sCadena3 = sCadena2 + sMesHex;
		
		//resultado_digito = obtener_digitoverificador (cadena3)
		cResultadoDigito = this.getDigitoVerificador(sCadena3);
		
		//resultado_final = cadena2 + mes_hex + resultado_digito
		if(cResultadoDigito != (char)(209)){
			sLlave = sCadena2 + sMesHex + cResultadoDigito;
		} else {
			sLlave = sCadena2 + sMesHex;	
		}
		//System.out.println("fecha="+sFecha);
		//System.out.println("LINE="+pLineaCaptura);
		//System.out.println("AMOUNT="+pTotal);
		//System.out.println("FOLIO="+pNoOperacion);
		//System.out.println("LINEA_PAGO="+sLlave);		
		return sLlave;
	}

	/*
	 * Función:  doCalculaCrc32 (calcula_crc32)
	 * Recibe:   cadena de longitud variable
	 * Devuelve: número (entero largo)
	 * */	
	private long doCalculaCrc32(String sCadena){
		int i;
		int t_cadena;
		long valor_decimal;
		long x;
		long x2;
		long x3;
		long x4;
		long x5;
		long resultado;
		char cDig;
		long aTabla[]=this.doLlenaTabla();
		x = 0xFFFFFFFFL;//FFFFFFFF
	
		t_cadena = sCadena.length();
	
		for(i=0;i<t_cadena;i++){	
			cDig = sCadena.charAt(i);
			valor_decimal = (long)cDig;  //convierte a valor decimal cada caracter de la cadena
			x2 = x ^ valor_decimal;
			x3 = x2 & 0xFFL;
			x4 = aTabla[(int)x3];
			x5 = x >> 8;
			x = x5 ^ x4;
		}
	
		resultado = x ^ 0xFFFFFFFFL;
	
		return resultado;
	}
	/*
	 * Función:  getDigitoVerificador (obtener_digito_verificador)
	 * Recibe:   cadena de longitud fija a 9 posiciones
	 * Devuelve: 1 caracter
	 * */
	private char getDigitoVerificador(String sCadena) {
		int i;
		int valor_decimal;
		int y = 0;
		int result;
		int x1;
		char dv = '0';
		char cDig;

		for(i=0;i<=8;i++){	
			cDig = sCadena.charAt(i);
			valor_decimal = (int)cDig; //convierte a valor decimal cada caracter de la cadena)
			y = y + valor_decimal * (14 - (i+1));
		}
		
		result = y % 11;

		if(result == 0 ){
			dv = '0';
		}
		
		if(result == 1 ){
			dv = '1';
		}
		
		if(result != 0 && result != 1){
			x1 = 48 + (11- result);
			dv = getValorASCII(x1);
		}
		
		return dv;
	}
	/* 
	 * Función:  getValorASCII (valor_ASCII) 
	 * Recibe:   número entero
	 * Devuelve: 1 carácter  
	 * */
	private char getValorASCII(int iCodigo) {
		//char sC = (char)(209); // Es la Ñ (enie) aunque deberia ser (char)(165)
		
		if(iCodigo >= 32 && iCodigo <= 127) {
			
			switch(iCodigo) {
			case 32:	return ' ';
			case 33:	return '!';
			case 34:	return '"';
			case 35:	return '#';
			case 36:	return '$';
			case 37:	return '%';
			case 38:	return '&';
			case 39:	return (char)(39);
			case 40:	return '(';		
			case 41:	return ')';		
			case 42:	return '*';		
			case 43:	return '+';		
			case 44:	return ',';		
			case 45:	return '-';		
			case 46:	return '.';		
			case 47:	return '/';		
			case 48:	return '0';		
			case 49:	return '1';		
			case 50:	return '2';		
			case 51:	return '3';		
			case 52:	return '4';		
			case 53:	return '5';		
			case 54:	return '6';		
			case 55:	return '7';		
			case 56:	return '8';		
			case 57:	return '9';		
			case 58:	return ':';		
			case 59:	return ';';		
			case 60:	return '<';		
			case 61:	return '=';		
			case 62:	return '>';	
			case 63:	return '?';	
			case 64:	return '@';	
			case 65:	return 'A';	
			case 66:	return 'B';	
			case 67:	return 'C';	
			case 68:	return 'D';	
			case 69:	return 'E';	
			case 70:	return 'F';	
			case 71:	return 'G';	
			case 72:	return 'H';	
			case 73:	return 'I';	
			case 74:	return 'J';
			case 75:	return 'K';
			case 76:	return 'L';
			case 77:	return 'M';
			case 78:	return 'N';
			case 79:	return '0';
			case 80:	return 'P';
			case 81:	return 'Q';
			case 82:	return 'R';
			case 83:	return 'S';
			case 84:	return 'T';
			case 85:	return 'U';
			case 86:	return 'V';
			case 87:	return 'W';
			case 88:	return 'X';
			case 89:	return 'Y';
			case 90:	return 'Z';
			case 91:	return '[';
			case 92:	return '\\';
			case 93:	return ']';
			case 94:	return '^';
			case 95:	return '_';
			case 96:	return '`';
			case 97:	return 'a';
			case 98:	return 'b';
			case 99:	return 'c';
			case 100:	return 'd';
			case 101:	return 'e';
			case 102:	return 'f';
			case 103:	return 'g';
			case 104:	return 'h';
			case 105:	return 'i';
			case 106:	return 'j';
			case 107:	return 'k';
			case 108:	return 'l';
			case 109:	return 'm';
			case 110:	return 'n';
			case 111:	return 'o';
			case 112:	return 'p';
			case 113:	return 'q';
			case 114:	return 'r';
			case 115:	return 's';
			case 116:	return 't';
			case 117:	return 'u';
			case 118:	return 'v';
			case 119:	return 'w';
			case 120:	return 'x';
			case 121:	return 'y';
			case 122:	return 'z';
			case 123:	return '{';
			case 124:	return '|';
			case 125:	return '}';
			case 126:	return '~';
			case 127:	return '&';
			default:	return (char)(209);
			}	
		} else {
			return (char)(209);
		}
	}
	 
	/*
	 * Función:  doLlenaTabla (llena_tabla)
	 * Recibe:   nada
	 * Devuelve: nada
	 * */
	public long[] doLlenaTabla() {
		long aTabla[] = new long [256];
		int i=0;
		int j=0;
		long x=0; 
		long x2=0;
				
		for(i=0;i<=255;i++){
			x = i;
			for(j=8;j>=1;j--){
				if((x & 1) == 1){
					x2 =(x >> 1);
					x = x2 ^ 0xEDB88320L;
				} else {
					x = (x >> 1);
				}
			}
			aTabla [i] = x;
		}
		return aTabla;
	}

}
