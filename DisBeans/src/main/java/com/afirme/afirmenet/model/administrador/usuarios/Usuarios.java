package com.afirme.afirmenet.model.administrador.usuarios;

import java.math.BigInteger;

/**
 * @author Usuario
 *
 */
/**
 * @author Usuario
 *
 */
public class Usuarios {
	
	private String Usuario;
	private String Puesto;
	private BigInteger numeroToken;
	private String estatus;
	
	/**
	 * @return
	 */
	public String getUsuario() {
		return Usuario;
	}
	public void setUsuario(String usuario) {
		Usuario = usuario;
	}
	public String getPuesto() {
		return Puesto;
	}
	public void setPuesto(String puesto) {
		Puesto = puesto;
	}
	public BigInteger getNumeroToken() {
		return numeroToken;
	}
	public void setNumeroToken(BigInteger numeroToken) {
		this.numeroToken = numeroToken;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	

}
