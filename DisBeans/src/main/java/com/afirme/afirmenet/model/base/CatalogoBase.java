package com.afirme.afirmenet.model.base;

public class CatalogoBase {
	String valor;
	String descripcion;
	String extra;

	public CatalogoBase(){
		
	}
	public CatalogoBase(String valor, String descripcion){
		this.valor = valor;
		this.descripcion = descripcion;
	}
	public CatalogoBase(String valor, String descripcion, String extra){
		this.valor = valor;
		this.descripcion = descripcion;
		this.extra = extra;
	}
	/**
	 * @return el valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor el valor a establecer
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion el descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return el extra
	 */
	public String getExtra() {
		return extra;
	}
	/**
	 * @param extra el extra a establecer
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
}
