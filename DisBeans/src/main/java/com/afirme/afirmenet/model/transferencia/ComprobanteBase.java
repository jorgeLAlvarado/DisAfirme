/**
 * 
 */
package com.afirme.afirmenet.model.transferencia;

/**
 * @author 0cdcarguz
 *
 */
public class ComprobanteBase {

	private String afirmeNetReference;
	private String trackingCode;
	private String speiReference="";
	private boolean esReimpresion=false;
	private String sts;
	/**
	 * @return the afirmeNetReference
	 */
	public String getAfirmeNetReference() {
		return afirmeNetReference;
	}

	/**
	 * @param afirmeNetReference
	 *            the afirmeNetReference to set
	 */
	public void setAfirmeNetReference(String afirmeNetReference) {
		this.afirmeNetReference = afirmeNetReference;
	}

	/**
	 * @return the trackingCode
	 */
	public String getTrackingCode() {
		return trackingCode;
	}

	/**
	 * @param trackingCode
	 *            the trackingCode to set
	 */
	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}

	/**
	 * @return the speiReference
	 */
	public String getSpeiReference() {
		return speiReference;
	}

	/**
	 * @param speiReference
	 *            the speiReference to set
	 */
	public void setSpeiReference(String speiReference) {
		this.speiReference = speiReference;
	}

	/**
	 * @return el esReimpresion
	 */
	public boolean isEsReimpresion() {
		return esReimpresion;
	}

	/**
	 * @param esReimpresion el esReimpresion a establecer
	 */
	public void setEsReimpresion(boolean esReimpresion) {
		this.esReimpresion = esReimpresion;
	}

	/**
	 * @return el sts
	 */
	public String getSts() {
		return sts;
	}

	/**
	 * @param sts el sts a establecer
	 */
	public void setSts(String sts) {
		this.sts = sts;
	}
	
	
}
