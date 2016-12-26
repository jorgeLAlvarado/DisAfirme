package com.afirme.afirmenet.service.impl.transferencia;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.model.pagos.impuestos.DepositoReferenciado;
import com.afirme.afirmenet.service.transferencia.ValidacionImpuestosDepositoRefenenciado;

@Service
public class ValidacionImpuestosDepositoRefenenciadoImpl implements ValidacionImpuestosDepositoRefenenciado{

	@Override
	public void sonServiciosValidos(List<DepositoReferenciado> pagos)
			throws AfirmeNetException {
		for (DepositoReferenciado pago : pagos) {
			esServicioValido(pago);
		}

	}

	@Override
	public void esServicioValido(DepositoReferenciado pago)
			throws AfirmeNetException {
		Map<CodigoExcepcion, String> errores = new HashMap<CodigoExcepcion, String>(
				0);
		//Revisamos la linea de captura

		GregorianCalendar dLFecha = new GregorianCalendar(Integer.parseInt("20" + pago.getValidationYear()), Integer.parseInt(pago.getValidationMonth())-1, Integer.parseInt(pago.getValidationDay()));
		errores=this.doValidLine(pago.getLineaCaptura(), pago.getAmount().toString(), dLFecha);
		if (!errores.isEmpty()) {
			pago.setErrores(errores);
		}
	}
	private Map<CodigoExcepcion, String> doValidLine(String sLine, String sImport, GregorianCalendar dDate){
		Map<CodigoExcepcion, String> errores = new HashMap<CodigoExcepcion, String>(
				0);
		boolean bVTT = true;
		String sError = "";
		String sData = "";
		int iLDigImp = 0; // D√≠gito de Importe de la L√≠nea
		int iCDigImp = 0; // D√≠gito de Importe Calculado
		int iLDateJul = 0; // Fecha Juliana de la L√≠nea
		GregorianCalendar dCDate = new GregorianCalendar(); 
		int iLDigGlb = 0; // D√≠gito Globales de la L√≠nea
		int iCDigGlb = 0; // D√≠gito Globales Calculados
		
		// Primero empezamos con las validaciones de tipos de datos y valores permitidos
		sLine = tTrim(sLine);
		
		// Primera validaci√≥n, que la cadena sea menor o mayor a 20
		if(sLine.length()!=20){
			sError = "Por favor verifique la LÌnea de Captura, Longitud menor a 20.";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			sData = sLine; 
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		//AIMM:: Nueva version de la linea
		String tipoPago=sLine.substring(0, 2);
		boolean esV2=false;
		if(tipoPago.equals("02"))
			esV2=true;
		
		// El tipo de Pago
		//if(!sLine.substring(0, 2).equals("01")){
		if(!"|01|02|".contains("|"+tipoPago+"|")){
			sError = "Por favor verifique la LÌnea de Captura, Tipo de Pago no definido.";
			sData = sLine.substring(0, 2);
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		//::AIMM:: Este tipo de dato no cambio
		// El Identificador
		if(!isAZ09(sLine.substring(2, 10))){
			sError = "Por favor verifique la LÌnea de Captura, Existen car√°cteres especiales no v·lidos en el identificador.";
			sData = sLine.substring(2, 10);
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		//::AIMM:: Este tipo de dato no cambio
		// La Referencia 
		if(!isAZ09(sLine.substring(10, 12))){
			sError = "Por favor verifique la LÌnea de Captura, Existen car√°cteres especiales no v·lidos en la referencia.";
			sData = sLine.substring(10, 12);
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		//::AIMM:: Este tipo de dato no cambio
		// La Vigencia 
		if(!is09(sLine.substring(12, 16))){
			sError = "Por favor verifique la LÌnea de Captura, Vigencia no v·lida.";
			sData = sLine.substring(12, 16);
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		//::AIMM:: Este tipo de dato no cambio
		// El D√≠gito del Importe
		if(!is09(sLine.substring(16, 17))){
			sError = "La LÌnea de Captura o el Importe son incorrectos";
			sData = sLine.substring(16, 17);
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		//::AIMM:: Este tipo de dato no cambio
		// El Tipo de Validaci√≥n 
		if(!sLine.substring(17, 18).equals("2") && !sLine.substring(17, 18).equals("4") ){
			sError = "LÌnea de Captura incorrecta";
			sData = sLine.substring(17, 18);
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		//::AIMM:: Este tipo de dato no cambio
		// Los D√≠gitos globales
		if(!is09(sLine.substring(18, 20))){
			sError = "Por favor verifique la Lpinea de Captura, DÌgitos globales no v·lidos.";
			sData = sLine.substring(18, 20);
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		
		// Validaciones de la L√≠nea de Captura
		int iPos = sImport.indexOf(".");
		sImport = iPos>-1?sImport.substring(0, iPos) + sImport.substring(iPos+1) : sImport;
		// Validaci√≥n del D√≠gito del Importe 
		iLDigImp = Integer.parseInt(sLine.substring(16, 17)); // D√çGITO DE LA LINEA

		//::AIMM:: En las dos cersiones se valida igual
		iCDigImp = getDigVImp(sImport); // D√çGITO CALCULADO
		
		bVTT = iLDigImp == iCDigImp;
		if(!bVTT){
			sError = "Por favor verifique la LÌnea de Captura, DÌgito de importe no corresponde.";
			sData = iLDigImp + ":" + iCDigImp + ":" + sImport;
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		
		// Validaci√≥n de Fecha de Vigencia
		// dDate QUE ES LA FECHA DEL SISTEMA
		iLDateJul = Integer.parseInt(sLine.substring(12, 16)); // VIGENCIA DE LA LINEA
		//::AIMM:: Si es version dos la fecha base es 2013/01/01
		if(esV2){
			dCDate = getFSJToGreg(iLDateJul, 2013);
		}else{
			dCDate = getFSJToGreg(iLDateJul);
		}
		
		bVTT = dDate.equals(dCDate) || dDate.before(dCDate);
		if(!bVTT){
			sError = "Por favor verifique la LÌnea de Captura, Vigencia est· vencida.";
			sData = dDate.get(Calendar.DAY_OF_MONTH) + "-" + (dDate.get(Calendar.MONTH) + 1) + "-" + dDate.get(Calendar.YEAR) + ":" +
				dCDate.get(Calendar.DAY_OF_MONTH) + "-" + (dCDate.get(Calendar.MONTH) + 1) + "-" + dCDate.get(Calendar.YEAR);
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		
		// Validaci√≥n de los D√≠gitos Globales
		iLDigGlb = Integer.parseInt(sLine.substring(18, 20)); // D√çGITOS DE LA LINEA
		if(esV2){
			iCDigGlb = getDigVGlbV2(sLine.substring(0, 18) + sImport.trim()); // D√çGITOS CALCULADOS (LINEA + IMPORTE)
		}else{
			iCDigGlb = getDigVGlb(sLine.substring(0, 18) + sImport.trim()); // D√çGITOS CALCULADOS (LINEA + IMPORTE)
		}
		
		bVTT = iLDigGlb == iCDigGlb;
		if(!bVTT){
			sError = "LÌnea de Captura incorrecta";
			sData = iLDigGlb + ":" + iCDigGlb;
			sError+= " ("+sData+")";
			errores.put(CodigoExcepcion.ERR_3000, sError);
			return errores;
		}
		
		return errores;
	}

	private String tTrim(String str){
		char cCadena[] = str.toCharArray();
		String strT = "";
		for(int i=0;i<cCadena.length;i++){
			strT = strT + (cCadena[i] == ' '?"":cCadena[i]+"");
		}
		return strT;
	}

	public boolean isAZ09(String Str){
		char cCadena[] = Str.toCharArray();
		for(int i=0;i<cCadena.length;i++){
			char cVal = getValue(cCadena[i]);
			if(cVal=='0' && cCadena[i]!='0'){// ES UN CARACTER INV√ÅLIDO DEL 0-9 Y A-Z
				return false;
			}
		}	
		return true;
	}
	
	public boolean is09(String Str){
		char cCadena[] = Str.toCharArray();
		for(int i=0;i<cCadena.length;i++){
			char cVal = getValue(cCadena[i]);
			if(cVal!=cCadena[i]){// ES UN CARACTER INV√ÅLIDO DEL 0-9 
				return false;
			}
		}	
		return true;
	}
	public GregorianCalendar getFSJToGreg(int iFSJ){
		return getFSJToGreg(iFSJ, 1988);
	}
	public GregorianCalendar getFSJToGreg(int iFSJ, int anioBase) {
		String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
		if (ids.length == 0)
			System.exit(0);
		SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
		pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		
		GregorianCalendar gcFecha = new GregorianCalendar(pdt);
		
		//int iAnio = (iFSJ / 372) + 1988;
		int iAnio = (iFSJ / 372) + anioBase;
		int iMes = (mod(iFSJ, 372) / 31) + 1;
		int iDia = mod(mod(iFSJ, 372), 31) + 1;
		
		gcFecha = new GregorianCalendar(iAnio, iMes - 1, iDia);
		
		return gcFecha;
	}

	public char getValue(char cCodigo) {
		char cV = 0;
		switch(cCodigo) {
			case '1':	cV = '1'; break;
			case '2':	cV = '2'; break;
			case '3':	cV = '3'; break;
			case '4':	cV = '4'; break;
			case '5':	cV = '5'; break;
			case '6':	cV = '6'; break;
			case '7':	cV = '7'; break;
			case '8':	cV = '8'; break;
			case '9':	cV = '9'; break;
			case '0':	cV = '0'; break;
			case 'A':	cV = '1'; break;
			case 'B':	cV = '2'; break;
			case 'C':	cV = '3'; break;
			case 'D':	cV = '4'; break;
			case 'E':	cV = '5'; break;
			case 'F':	cV = '6'; break;
			case 'G':	cV = '7'; break;
			case 'H':	cV = '8'; break;
			case 'I':	cV = '9'; break;
			case 'J':	cV = '1'; break;
			case 'K':	cV = '2'; break;
			case 'L':	cV = '3'; break;
			case 'M':	cV = '4'; break;
			case 'N':	cV = '5'; break;
			case 'O':	cV = '6'; break;
			case 'P':	cV = '7'; break;
			case 'Q':	cV = '8'; break;
			case 'R':	cV = '9'; break;
			case 'S':	cV = '2'; break;
			case 'T':	cV = '3'; break;
			case 'U':	cV = '4'; break;
			case 'V':	cV = '5'; break;
			case 'W':	cV = '6'; break;
			case 'X':	cV = '7'; break;
			case 'Y':	cV = '8'; break;
			case 'Z':	cV = '9'; break;
			default:	cV = '0';
		}
		
		return cV;
	}

	public int getDigVImp(String sImporte){
		int iDigV = 0;
		int iPnd[] = {7, 3, 1};
		int iVxD[] = new int [20];
		int j = 0;
		int i = 0;
		int k = 0;
		
		char cImporte[] = sImporte.toCharArray(); 
		for(i=cImporte.length-1;i>=0;i--){
			iVxD[k] = Integer.parseInt(String.valueOf(cImporte[i])) * iPnd[j];
			j = j<2 ? j+1 : 0;
			k++;
		}
		
		for(j=0;j<cImporte.length;j++){
			iDigV = iDigV + iVxD[j]; 
		}
		
		iDigV = mod(iDigV, 10);
		
		return iDigV;
	}

	public int getDigVGlb(String sLinea){
		int iDG = 0;
		int iPnd[] = {11, 13, 17, 19, 23};
		int iVxD[] = new int [40];
		int j = 0;
		int i = 0;
		int k = 0;
		
		sLinea = tTrim(sLinea);
		
		char cLinea[] = sLinea.toCharArray(); 
		for(i=cLinea.length-1;i>=0;i--){
			iVxD[k] = Integer.parseInt(String.valueOf(getValue(cLinea[i]))) * iPnd[j];
			j = j<4 ? j+1 : 0;
			k++;
		}
		
		for(j=0;j<cLinea.length;j++){
			iDG = iDG + iVxD[j]; 
		}
		
		iDG = mod(iDG, 97) + 1;
		
		return iDG;
	}
	public int getDigVGlbV2(String sLinea){
		int iDG = 0;
		int iPnd[] = {11, 13, 17, 19, 23, 29, 31, 37, 41, 43};
		int iVxD[] = new int [60];
		String sLineaV2="";
		int j = 0;
		int i = 0;
		int k = 0;
		
		sLinea = tTrim(sLinea);
		
		char cLinea[] = sLinea.toCharArray();
		//
		for(i=0;i<cLinea.length;i++){
			sLineaV2 += String.valueOf(getValueV2(cLinea[i]));
		}

		char cLineaV2[] = sLineaV2.toCharArray();
		for(i=cLineaV2.length-1;i>=0;i--){
			//System.out.println("");
			iVxD[k] = Integer.parseInt(String.valueOf(getValueV2(cLineaV2[i]))) * iPnd[j];
			j = j<9 ? j+1 : 0;
			k++;
		}
		
		for(j=0;j<cLineaV2.length;j++){
			iDG = iDG + iVxD[j]; 
		}
		
		iDG = mod(iDG, 97) + 1;
		
		return iDG;
	}

	public int getValueV2(char cCodigo) {
		int cV = 0;
		switch(cCodigo) {
			case '1':	cV = 1; break;
			case '2':	cV = 2; break;
			case '3':	cV = 3; break;
			case '4':	cV = 4; break;
			case '5':	cV = 5; break;
			case '6':	cV = 6; break;
			case '7':	cV = 7; break;
			case '8':	cV = 8; break;
			case '9':	cV = 9; break;
			case '0':	cV = 0; break;
			case 'A':	cV = 10; break;
			case 'B':	cV = 11; break;
			case 'C':	cV = 12; break;
			case 'D':	cV = 13; break;
			case 'E':	cV = 14; break;
			case 'F':	cV = 15; break;
			case 'G':	cV = 16; break;
			case 'H':	cV = 17; break;
			case 'I':	cV = 18; break;
			case 'J':	cV = 19; break;
			case 'K':	cV = 20; break;
			case 'L':	cV = 21; break;
			case 'M':	cV = 22; break;
			case 'N':	cV = 23; break;
			case 'O':	cV = 24; break;
			case 'P':	cV = 25; break;
			case 'Q':	cV = 26; break;
			case 'R':	cV = 27; break;
			case 'S':	cV = 28; break;
			case 'T':	cV = 29; break;
			case 'U':	cV = 30; break;
			case 'V':	cV = 31; break;
			case 'W':	cV = 32; break;
			case 'X':	cV = 33; break;
			case 'Y':	cV = 34; break;
			case 'Z':	cV = 35; break;
			default:	cV = 0;
		}
		
		return cV;
	}

	private int mod(int iNum, int iDiv){
		int iMod = 0;
		int iEnt = iNum / iDiv;
		iMod = iNum - (iEnt * iDiv);
		
		return iMod;
	}
	
}
