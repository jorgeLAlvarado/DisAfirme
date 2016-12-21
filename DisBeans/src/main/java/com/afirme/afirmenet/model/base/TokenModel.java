package com.afirme.afirmenet.model.base;

public class TokenModel {
	
	private boolean valido=false;
	private String errores="";
	private int intentos=0;
	private int maxIntentos=3;
	private String redirect;
	/**
	 * @return el valido
	 */
	public boolean isValido() {
		return valido;
	}
	/**
	 * @param valido el valido a establecer
	 */
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	/**
	 * @return el errores
	 */
	public String getErrores() {
		return errores;
	}
	/**
	 * @param errores el errores a establecer
	 */
	public void setErrores(String errores) {
		this.errores = errores;
	}
	/**
	 * @return el intentos
	 */
	public int getIntentos() {
		return intentos;
	}
	/**
	 * @param intentos el intentos a establecer
	 */
	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}
	/**
	 * @return el maxIntentos
	 */
	public int getMaxIntentos() {
		return maxIntentos;
	}
	/**
	 * @param maxIntentos el maxIntentos a establecer
	 */
	public void setMaxIntentos(int maxIntentos) {
		this.maxIntentos = maxIntentos;
	}
	/**
	 * @return el redirect
	 */
	public String getRedirect() {
		return redirect;
	}
	/**
	 * @param redirect el redirect a establecer
	 */
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	
	

}
