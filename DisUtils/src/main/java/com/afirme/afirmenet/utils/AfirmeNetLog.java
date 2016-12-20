package com.afirme.afirmenet.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datapro.sockets.MessageRecord;

public class AfirmeNetLog {
	Logger log = null;

	public AfirmeNetLog(String clase) {
		log = LoggerFactory.getLogger(clase);
	}
	
	public AfirmeNetLog(@SuppressWarnings("rawtypes") Class clazz) {
		log = LoggerFactory.getLogger(clazz.getName());
	}
	

	public void setSendInfo(String Tipo, String CodigoTransaccion,
			String Monto, String Contrato, String Usuario,
			String DebAcc, String CreAcc, String Folio, String MensajeBean){
		try {
			Contrato = Contrato != null ? Contrato : "";
			Usuario = Usuario != null ? Usuario : "";

			String sLog = Tipo + ";" + CodigoTransaccion + ";" + Monto + ";"
					+ Contrato + ";" + Usuario + ";" + DebAcc + ";"
					+ CreAcc + ";" + Folio + ";SEND;" + MensajeBean
					+ ";TRNSLG4";
			String sSello = AfirmeNetFirmaDigital.firmaTexto(sLog);
			log.info(sLog + ";" + sSello);
		} catch (Exception e) {
			System.out.println("JOLog4j.setSendInfo " + e);
		}
		return;
	}

	public void setGetInfo(String Tipo, String CodigoTransaccion, String Monto,
			String Contrato, String Usuario, String DebAcc,
			String CreAcc, String Folio, MessageRecord MsgBean) throws  IOException {
		try {
			Contrato = Contrato != null ? Contrato : "";
			Usuario = Usuario != null ? Usuario : "";

			String sLog = Tipo + ";" + CodigoTransaccion + ";" + Monto + ";"
					+ Contrato + ";" + Usuario + ";" + DebAcc + ";"
					+ CreAcc + ";" + Folio + ";GET;" + MsgBean.getFormatName()
					+ ";TRNSLG4";
			String sSello = AfirmeNetFirmaDigital.firmaTexto(sLog);
			log.info(sLog + ";" + sSello);
		} catch (Exception e) {
			System.out.println("JOLog4j.setGetInfo " + e);
		}
		return;
	}

	public void setLogInfo(String Contrato, String Usuario,
			String MensajeBean, String RemoteAddr) {
		try {
			Contrato = Contrato != null ? Contrato : "";
			Usuario = Usuario != null ? Usuario : "";
			RemoteAddr = RemoteAddr != null ? RemoteAddr : "";

			String sLog = Contrato + ";" + Usuario + ";" + MensajeBean
					+ ";" + RemoteAddr + ";LGILG4";
			String sSello = AfirmeNetFirmaDigital.firmaTexto(sLog);
			log.info(sLog + ";" + sSello);
		} catch (Exception e) {
			System.out.println("JOLog4j.setLogInfo " + e);
		}
		return;
	}

	public void setSumInfo(String Contrato, String Usuario,
			String MensajeBean, String RemoteAddr){
		try {
			Contrato = Contrato != null ? Contrato : "";
			Usuario = Usuario != null ? Usuario : "";
			RemoteAddr = RemoteAddr != null ? RemoteAddr : "";

			String sLog = Contrato + ";" + Usuario + ";" + MensajeBean
					+ ";" + RemoteAddr + ";SMYLG4";
			String sSello = AfirmeNetFirmaDigital.firmaTexto(sLog);
			log.info(sLog + ";" + sSello);
		} catch (Exception e) {
			System.out.println("JOLog4j.setSumInfo " + e);
		}
		return;
	}

	public void setUpdInfo(String Tipo, String CodigoTransaccion, String Monto,
			String Contrato, String Usuario, String DebAcc,
			String CreAcc, String Folio, String MensajeBean) throws  IOException {
		try {
			Contrato = Contrato != null ? Contrato : "";
			Usuario = Usuario != null ? Usuario : "";

			String sLog = Tipo + ";" + CodigoTransaccion + ";" + Monto + ";"
					+ Contrato + ";" + Usuario + ";" + DebAcc + ";"
					+ CreAcc + ";" + Folio + ";UPDATE;" + MensajeBean
					+ ";TRNSLG4";
			String sSello = AfirmeNetFirmaDigital.firmaTexto(sLog);
			log.info(sLog + ";" + sSello);
		} catch (Exception e) {
			System.out.println("JOLog4j.setUpdInfo " + e);
		}
		return;
	}

	public void setSeleInfo(String Tipo, String CodigoTransaccion,
			String Monto, String Contrato, String Usuario,
			String DebAcc, String CreAcc, String Folio, String Consulta) throws IOException {
		try {
			Contrato = Contrato != null ? Contrato : "";
			Usuario = Usuario != null ? Usuario : "";

			String sLog = Tipo + ";" + CodigoTransaccion + ";" + Monto + ";"
					+ Contrato + ";" + Usuario + ";" + DebAcc + ";"
					+ CreAcc + ";" + Folio + ";GET;" + Consulta + ";TRNSLG4";
			String sSello = AfirmeNetFirmaDigital.firmaTexto(sLog);
			log.info(sLog + ";" + sSello);
		} catch (Exception e) {
			System.out.println("JOLog4j.setSeleInfo " + e);
		}
		return;
	}

	public void setLogAccesos(String contrato, String usuario, String ipRemota,
			String idSesion, String mensajeLog, int nivel)
			throws IOException {
		try {
			contrato = contrato != null ? contrato : "";
			usuario = usuario != null ? usuario : "";
			ipRemota = ipRemota != null ? ipRemota : "";
			String sLog = contrato + ";" + usuario + ";" + ipRemota + ";"
					+ idSesion + ";  " + mensajeLog + "  ;AFIACCESOS";
			String sSello = AfirmeNetFirmaDigital.firmaTexto(sLog);
			switch (nivel) {
			case 1:
				log.trace(sLog + ";" + sSello);
				break;
			case 2:
				log.debug(sLog + ";" + sSello);
				break;
			case 3:
				log.info(sLog + ";" + sSello);
				break;
			case 4:
				log.warn(sLog + ";" + sSello);
				break;
			case 5:
				log.error(sLog + ";" + sSello);
				break;
//			case 6:
//				log.fatal(sLog + ";" + sSello);
//				break;
			}
		} catch (Exception e) {
			System.out.println("Error en JOLog4j.setLogAccesos ==> " + e);
		}
		return;
	}

	public void setLogGeneral(String contrato, String usuario, String ipRemota,
			String idSesion, String mensajeLog, int nivel) {
		try {
			contrato = contrato != null ? contrato : "";
			usuario = usuario != null ? usuario : "";
			ipRemota = ipRemota != null ? ipRemota : "";
			String sLog = contrato + ";" + usuario + ";" + ipRemota + ";"
					+ idSesion + ";  " + mensajeLog + "  ;CONSISS";
			switch (nivel) {
			case 1:
				log.trace(sLog);
				break;
			case 2:
				log.debug(sLog);
				break;
			case 3:
				log.info(sLog);
				break;
			case 4:
				log.warn(sLog);
				break;
			case 5:
				log.error(sLog);
				break;
//			case 6:
//				log.fatal(sLog);
//				break;
			}
		} catch (Exception e) {
			System.out.println("Error en JOLog4j.setLogGeneral ==> " + e);
		}
		return;
	}

	public void setLogValores(String contrato, String ipRemota,
			String idSesion, String mensajeLog, int nivel){
		try {
			contrato = contrato != null ? contrato : "Valor Null";
			ipRemota = ipRemota != null ? ipRemota : "Valor Null";
			String sLog = contrato + ";" + ipRemota + ";" + idSesion + ";  "
					+ mensajeLog + "  ;AFIVALORES";
			String sSello = AfirmeNetFirmaDigital.firmaTexto(sLog);
			switch (nivel) {
			case 1:
				log.trace(sLog + ";" + sSello);
				break;
			case 2:
				log.debug(sLog + ";" + sSello);
				break;
			case 3:
				log.info(sLog + ";" + sSello);
				break;
			case 4:
				log.warn(sLog + ";" + sSello);
				break;
			case 5:
				log.error(sLog + ";" + sSello);
				break;
//			case 6:
//				log.fatal(sLog + ";" + sSello);
//				break;
			}
		} catch (Exception e) {
			System.out.println("Error en JOLog4j.setLogValores ==> " + e);
		}
		return;
	}
	
	public void trace(String msg){
		log.trace(msg);
	}
	public void debug(String msg){
		log.debug(msg);
	}
	public void warn(String msg){
		log.warn(msg);
	}
	public void error(String msg){
		log.error(msg);
	}
	public void error(String msg, Throwable e){
		log.error(msg, e);
	}
	public void debug(String msg, Throwable e){
		log.debug(msg, e);
	}

	public void info(String msg){
		log.warn("INFO::"+msg);
	}
	public void info(String msg, Throwable e){
		log.warn("INFO::"+msg, e);
	}
	public void info(String msg, Object arg){
		log.warn("INFO::"+msg, arg);
	}
	public boolean isDebugEnabled(){
		return log.isDebugEnabled();
	}
}
