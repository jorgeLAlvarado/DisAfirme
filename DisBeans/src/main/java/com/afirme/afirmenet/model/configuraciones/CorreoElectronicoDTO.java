package com.afirme.afirmenet.model.configuraciones;

import java.util.Calendar;

import com.afirme.afirmenet.model.transferencia.ComprobanteBase;

/**
 * Created on Sep 7, 2015 11:39:05 AM by eguzher
 * <br><br>
 * La fecha de ejecucion es la fecha actual.<br>
 * La fecha de actuivación, es cuando se efecturará el cambio, en 24 horas.<br>
 * <br>
 * <br>
 * Las fechas de operacion y activacion son calendar para obtrener los datos:
 * <br><br>
 * El formato de la fecha se utiliza en la pantalla confirmar y en comprobante.
 * <br><br>
 * El calendar se se envia como objeto al template jdbc.
 * <br><br>
 * Fecha de Ejecución del cambio<br>
 * 16/09/15
 * <br><br>
 * Hora de Ejecución del cambio<br>
 * 17:42
 * <br><br>
 * Fecha de Activación del cambio<br>
 * 17/09/15
 * <br><br>
 * Hora de Activación del cambio<br>
 * 17:42
 * <br><br>
 * Created on Sep 16, 2015 11:44:40 AM by eguzher
 * <br>
 * <br>
 * @author epifanio.guzman@afirme.com
 * <br>
 *
 */
public class CorreoElectronicoDTO extends ComprobanteBase {
	private String contrato = "";
	private String correoActual = "";
	private String correoNuevo = "";
	private String correoNuevoConfirmacion = "";
	private int pasoDelProceso = 0;
	
	private String time ="";
	private String status = "";
	
	private Calendar fechaOperacion = Calendar.getInstance();
	private String fechaOperacionddMMYY = "";
	private String horaOperacionHHmm = "";
	
	private Calendar fechaActivacion = Calendar.getInstance();
	private String fechaActivacionddMMYY = "";
	private String horaActivacionHHmm = "";
	
	private String correoTOParaNotificacion = "";
	private String nombreCompleto = "";
	
	private String mensajeUsuario24Horas = ""; // para jsp .. por seguridad...
	private String cadena24horas = ""; // para enviar correo el dato ...estara disponible dentro de ${tiempo_activacion} ..
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("CorreoElectronicoDTO ");
		sb.append(" contrato=").append(contrato);
		sb.append(", correoActual=").append(correoActual);
		sb.append(", correoNuevo=").append(correoNuevo);
		sb.append(", correoNuevoConfirmacion=").append(correoNuevoConfirmacion);
		sb.append(", pasoDelProceso=").append(pasoDelProceso);
		sb.append(", time=").append(time);
		sb.append(", status=").append(status);
		
		sb.append(", fechaOperacion=").append(fechaOperacion);
		sb.append(", fechaOperacionddMMYY=").append(fechaOperacionddMMYY);
		sb.append(", horaOperacionHHmm=").append(horaOperacionHHmm);
		
		sb.append(", fechaActivacion=").append(fechaActivacion);
		sb.append(", fechaActivacionddMMYY=").append(fechaActivacionddMMYY);
		sb.append(", horaActivacionHHmm=").append(horaActivacionHHmm);
		
		sb.append(", correoTOParaNotificacion=").append(correoTOParaNotificacion);
		sb.append(", nombreCompleto=").append(nombreCompleto);
		
		sb.append(", mensajeUsuario24Horas=").append(mensajeUsuario24Horas);
		sb.append(", cadena24horas=").append(cadena24horas);
		
		return sb.toString();
	}

	/**
	 * @return contrato
	 */
	public String getContrato() {
		return contrato;
	}

	/**
	 * @param contrato
	 */
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	/**
	 * @return correo actual
	 */
	public String getCorreoActual() {
		return correoActual;
	}

	/**
	 * @param correoActual
	 */
	public void setCorreoActual(String correoActual) {
		this.correoActual = correoActual;
	}

	/**
	 * @return correo nuevo
	 */
	public String getCorreoNuevo() {
		return correoNuevo;
	}

	/**
	 * @param correoNuevo
	 */
	public void setCorreoNuevo(String correoNuevo) {
		this.correoNuevo = correoNuevo;
	}

	/**
	 * @return correo nuevo confirmacion
	 */
	public String getCorreoNuevoConfirmacion() {
		return correoNuevoConfirmacion;
	}

	/**
	 * @param correoNuevoConfirmacion
	 */
	public void setCorreoNuevoConfirmacion(String correoNuevoConfirmacion) {
		this.correoNuevoConfirmacion = correoNuevoConfirmacion;
	}

	/**
	 * @return paso del proceso
	 */
	public int getPasoDelProceso() {
		return pasoDelProceso;
	}

	/**
	 * @param pasoDelProceso
	 */
	public void setPasoDelProceso(int pasoDelProceso) {
		this.pasoDelProceso = pasoDelProceso;
	}

	/**
	 * @return tiempo
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return estatus
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return fecha de operacion
	 */
	public Calendar getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @param fechaOperacion
	 */
	public void setFechaOperacion(Calendar fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	/**
	 * @return fechaoperacion ddMMYY
	 */
	public String getFechaOperacionddMMYY() {
		return fechaOperacionddMMYY;
	}

	/**
	 * @param fechaOperacionddMMYY
	 */
	public void setFechaOperacionddMMYY(String fechaOperacionddMMYY) {
		this.fechaOperacionddMMYY = fechaOperacionddMMYY;
	}

	/**
	 * @return hora de operacion HHmm
	 */
	public String getHoraOperacionHHmm() {
		return horaOperacionHHmm;
	}

	/**
	 * @param horaOperacionHHmm
	 */
	public void setHoraOperacionHHmm(String horaOperacionHHmm) {
		this.horaOperacionHHmm = horaOperacionHHmm;
	}

	/**
	 * @return fecha de activacion
	 */
	public Calendar getFechaActivacion() {
		return fechaActivacion;
	}

	/**
	 * @param fechaActivacion
	 */
	public void setFechaActivacion(Calendar fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	/**
	 * @return fecha de activacion ddMMYY
	 */
	public String getFechaActivacionddMMYY() {
		return fechaActivacionddMMYY;
	}

	/**
	 * @param fechaActivacionddMMYY
	 */
	public void setFechaActivacionddMMYY(String fechaActivacionddMMYY) {
		this.fechaActivacionddMMYY = fechaActivacionddMMYY;
	}

	/**
	 * @return hora de activacion HHmm
	 */
	public String getHoraActivacionHHmm() {
		return horaActivacionHHmm;
	}

	/**
	 * @param horaActivacionHHmm
	 */
	public void setHoraActivacionHHmm(String horaActivacionHHmm) {
		this.horaActivacionHHmm = horaActivacionHHmm;
	}

	/**
	 * @return Correo para notificaciones
	 */
	public String getCorreoTOParaNotificacion() {
		return correoTOParaNotificacion;
	}

	/**
	 * @param correoTOParaNotificacion
	 */
	public void setCorreoTOParaNotificacion(String correoTOParaNotificacion) {
		this.correoTOParaNotificacion = correoTOParaNotificacion;
	}

	/**
	 * @return nombre completo
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return mensajeUsuario24Horas
	 */
	public String getMensajeUsuario24Horas() {
		return mensajeUsuario24Horas;
	}

	/**
	 * @param mensajeUsuario24Horas
	 */
	public void setMensajeUsuario24Horas(String mensajeUsuario24Horas) {
		this.mensajeUsuario24Horas = mensajeUsuario24Horas;
	}

	/**
	 * @return cadena24horas
	 */
	public String getCadena24horas() {
		return cadena24horas;
	}

	/**
	 * @param cadena24horas
	 */
	public void setCadena24horas(String cadena24horas) {
		this.cadena24horas = cadena24horas;
	}
		
}
