package com.afirme.afirmenet.model.configuraciones;

import java.util.Calendar;

import com.afirme.afirmenet.model.transferencia.ComprobanteBase;

/**
 * DTO que viaja en el wizard cambio de alias e imagen avatar.
 * 
 * Created on Sep 1, 2015 5:42:16 PM by eguzher
 * <br>
 * <br>
 * @author epifanio.guzman@afirme.com
 * <br>
 */
public class AliasAvatarDTO extends ComprobanteBase {

	private String contrato = "";
	private String aliasActual = "";
	private String aliasNuevo = "";
	private String avatarActual = "";
	private String avatarNuevo = "";
	private int pasoDelProceso = 0;
	private Calendar fechaOperacion = Calendar.getInstance();
	private String fechaOperacionddMMYY = "";
	private String horaOperacionHHmm = "";
	private String correoTOParaNotificacion = "";
	private String idReferencia = ""; // para historial en DC_CAMBIO_ALIAS.DCIBS_REF
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("AliasAvatarDTO ");
		sb.append(" contrato=").append(contrato);
		sb.append(", aliasActual=").append(aliasActual);
		sb.append(", aliasNuevo=").append(aliasNuevo);
		sb.append(", avatarActual=").append(avatarActual);
		sb.append(", avatarNuevo=").append(avatarNuevo);
		sb.append(", pasoDelProceso=").append(pasoDelProceso);
		sb.append(", fechaOperacion=").append(fechaOperacion);
		sb.append(", fechaOperacionddMMYY=").append(fechaOperacionddMMYY);
		sb.append(", horaOperacionHHmm=").append(horaOperacionHHmm);
		sb.append(", correoTOParaNotificacion=").append(correoTOParaNotificacion);
		
		return sb.toString();
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getAliasActual() {
		return aliasActual;
	}

	public void setAliasActual(String aliasActual) {
		this.aliasActual = aliasActual;
	}

	public String getAliasNuevo() {
		return aliasNuevo;
	}

	public void setAliasNuevo(String aliasNuevo) {
		this.aliasNuevo = aliasNuevo;
	}

	public String getAvatarActual() {
		return avatarActual;
	}

	public void setAvatarActual(String avatarActual) {
		this.avatarActual = avatarActual;
	}

	public String getAvatarNuevo() {
		return avatarNuevo;
	}

	public void setAvatarNuevo(String avatarNuevo) {
		this.avatarNuevo = avatarNuevo;
	}

	public int getPasoDelProceso() {
		return pasoDelProceso;
	}

	public void setPasoDelProceso(int pasoDelProceso) {
		this.pasoDelProceso = pasoDelProceso;
	}

	public Calendar getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Calendar fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getFechaOperacionddMMYY() {
		return fechaOperacionddMMYY;
	}

	public void setFechaOperacionddMMYY(String fechaOperacionddMMYY) {
		this.fechaOperacionddMMYY = fechaOperacionddMMYY;
	}

	public String getHoraOperacionHHmm() {
		return horaOperacionHHmm;
	}

	public void setHoraOperacionHHmm(String horaOperacionHHmm) {
		this.horaOperacionHHmm = horaOperacionHHmm;
	}

	public String getCorreoTOParaNotificacion() {
		return correoTOParaNotificacion;
	}

	public void setCorreoTOParaNotificacion(String correoTOParaNotificacion) {
		this.correoTOParaNotificacion = correoTOParaNotificacion;
	}

	public String getIdReferencia() {
		return idReferencia;
	}

	public void setIdReferencia(String idReferencia) {
		this.idReferencia = idReferencia;
	}
	
}
