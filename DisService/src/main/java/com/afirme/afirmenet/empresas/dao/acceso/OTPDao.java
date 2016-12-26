package com.afirme.afirmenet.empresas.dao.acceso;

/**
 *  clase para segirodad del usuario
 * @author Bayron Gamboa Martinez
 * @version 1.0.0
 * Created on Created on Dic 26, 2016 3:50:05 PM by Bayron
 */
public interface OTPDao {
	
	/**
	 * @param contrato
	 * @return
	 */
	public String obtenToken(String contrato);

	/**
	 * @param contrato
	 * @return
	 */
	public boolean usaTokens(String contrato);

	/**
	 * @param contrato
	 * @param usuario
	 * @param serialToken
	 * @param codigoActivacion
	 * @return
	 */
	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion);

	/**
	 * @param contrato
	 * @param usuario
	 * @param codigoSegEncrypt
	 * @return
	 */
	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEncrypt);

	/**
	 * @param serialToken
	 * @param fechaVencimiento
	 * @return
	 */
	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento);
}
