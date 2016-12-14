package com.afirme.afirmenet.ibs.beans.consultas;

public class TipoTransaccion implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//String tipo;
	HistorialTipo tipo;
	String nombre;
	String categoria;
	/**
	 * @return el tipo
	 */
	public HistorialTipo getTipo() {
		return tipo;
	}
	/**
	 * @param tipo el tipo a establecer
	 */
	public void setTipo(HistorialTipo tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return el categoria
	 */
	public String getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria el categoria a establecer
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
