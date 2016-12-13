package com.afirme.afirmenet.empresas.dao.acceso;

/**
 *  
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
public interface OTPDao {
	
	public String obtenToken(String contrato);
	public boolean usaTokens(String contrato);
	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion);
	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEncrypt);
	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento);
}
