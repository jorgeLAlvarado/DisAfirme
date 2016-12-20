package com.afirme.afirmenet.model;

import java.util.Date;
import java.util.List;

import com.afirme.afirmenet.model.DCMessageIni;
import com.afirme.afirmenet.utils.time.TimeUtils;

public class AfirmeNetUser {
	private String cuentaContrato;
	private int notificaciones;
	private String nombreLargo;
	private String nombreCompleto;
	private String nombreCorto;
	private String contrato;
	private String avatar;
	private String alias;
	private String numCliente;
	private Date ultimoAcceso;
	private String cuentasExcluyentes;
	private DCMessageIni campania;
	private String correoElectronico;
	private String paqueteAfirmeNet;
	private boolean basicoSinToken;
	private List<?> tokenRSA;
	private String patrimonial;

	/**
	 * @return el cuentaContrato
	 */
	public String getCuentaContrato() {
		return cuentaContrato;
	}
	/**
	 * @param cuentaContrato cuenta relacionada al contrato que accede
	 */
	public void setCuentaContrato(String cuentaContrato) {
		this.cuentaContrato = cuentaContrato;
	}

	/**
	 * @return el patrimonial
	 */
	public String getPatrimonial() {
		return patrimonial;
	}

	/**
	 * @param patrimonial el patrimonial a establecer
	 */
	public void setPatrimonial(String patrimonial) {
		this.patrimonial = patrimonial;
	}

	/**
	 * @return the notificaciones
	 */
	public int getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @param notificaciones
	 *            the notificaciones to set
	 */
	public void setNotificaciones(int notificaciones) {
		this.notificaciones = notificaciones;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto
	 *            the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
//		 return nombreCorto;
//	     StringTokenizer st = new StringTokenizer(this.nombreLargo);	
//	     return st.nextToken();	 
	     
	    String[] result = this.nombreLargo.split("\\s");

		if(result.length==3)
			return result[0];
		else if(result.length==4)
			return  result[0]+" "+ result[1];
		else 
			return  result[0];
	}

	/**
	 * @param nombreCorto
	 *            the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	/**
	 * @return the contrato
	 */
	public String getContrato() {
		return contrato;
	}

	/**
	 * @param contrato
	 *            the contrato to set
	 */
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	/**
	 * @return the ultimoAcceso
	 */
	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}

	public String getUltimoAccesoStr() {
		return TimeUtils.customnCompleteDateFormat.format(ultimoAcceso);
	}

	/**
	 * @param ultimoAcceso
	 *            the ultimoAcceso to set
	 */
	public void setUltimoAcceso(Date ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}

	/**
	 * @return the nombreLargo
	 */
	public String getNombreLargo() {
		return nombreLargo;
	}

	/**
	 * @param nombreLargo
	 *            the nombreLargo to set
	 */
	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	/**
	 * @return the campania
	 */
	public DCMessageIni getCampania() {
		return campania;
	}

	/**
	 * @param campania the campania to set
	 */
	public void setCampania(DCMessageIni campania) {
		this.campania = campania;
	}
	/**
	 * @return el cuentasExcluyentes
	 */
	public String getCuentasExcluyentes() {
		if(cuentasExcluyentes==null)
			return "";
		return cuentasExcluyentes;
	}

	/**
	 * @param cuentasExcluyentes el cuentasExcluyentes a establecer
	 */
	public void setCuentasExcluyentes(String cuentasExcluyentes) {
		this.cuentasExcluyentes = cuentasExcluyentes;
	}

	/**
	 * @return el correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * @param correoElectronico el correoElectronico a establecer
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/**
	 * @return el numCliente
	 */
	public String getNumCliente() {
		return numCliente;
	}

	/**
	 * @param numCliente el numCliente a establecer
	 */
	public void setNumCliente(String numCliente) {
		this.numCliente = numCliente;
	}

	/**
	 * @return el paqueteAfirmeNet
	 */
	public String getPaqueteAfirmeNet() {
		return paqueteAfirmeNet;
	}

	/**
	 * @param paqueteAfirmeNet el paqueteAfirmeNet a establecer
	 */
	public void setPaqueteAfirmeNet(String paqueteAfirmeNet) {
		this.paqueteAfirmeNet = paqueteAfirmeNet;
	}

	/**
	 * @return el basicoSinToken
	 */
	public boolean isBasicoSinToken() {
		return basicoSinToken;
	}

	/**
	 * @param basicoSinToken el basicoSinToken a establecer
	 */
	public void setBasicoSinToken(boolean basicoSinToken) {
		this.basicoSinToken = basicoSinToken;
	}

	/**
	 * @return el tokenRSA
	 */
	public List<?> getTokenRSA() {
		return tokenRSA;
	}

	/**
	 * @param tokenRSA el tokenRSA a establecer
	 */
	public void setTokenRSA(List<?> tokenRSA) {
		this.tokenRSA = tokenRSA;
	}

	/**
	 * @return el avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar el avatar a establecer
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}



	
	
}
