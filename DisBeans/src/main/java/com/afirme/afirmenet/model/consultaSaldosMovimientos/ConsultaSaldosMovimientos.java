package com.afirme.afirmenet.model.consultaSaldosMovimientos;

import java.util.Date;

public class ConsultaSaldosMovimientos {
	
	String nombreCuenta;
	double cantidad;
	String clabe;
	String traspaso;
	int numeroReferencia;
	Date fecha;
	String ciudad;
	double cargos;
	double abono;
	double saldo;
	
	/**
	 * @return nombreCuentas
	 */
	public String getNombreCuenta() {
		return nombreCuenta;
	}
	/**
	 * @param nombreCuenta
	 */
	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}
	/**
	 * @return cantidad
	 */
	public double getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return clabe
	 */
	public String getClabe() {
		return clabe;
	}
	/**
	 * @param clabe
	 */
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	/**
	 * @return trspaso
	 */
	public String getTraspaso() {
		return traspaso;
	}
	/**
	 * @param traspaso
	 */
	public void setTraspaso(String traspaso) {
		this.traspaso = traspaso;
	}
	/**
	 * @return numeroReferencia
	 */
	public int getNumeroReferencia() {
		return numeroReferencia;
	}
	/**
	 * @param numeroReferencia
	 */
	public void setNumeroReferencia(int numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}
	/**
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return cargos
	 */
	public double getCargos() {
		return cargos;
	}
	/**
	 * @param cargos
	 */
	public void setCargos(double cargos) {
		this.cargos = cargos;
	}
	/**
	 * @return abono
	 */
	public double getAbono() {
		return abono;
	}
	/**
	 * @param abono
	 */
	public void setAbono(double abono) {
		this.abono = abono;
	}
	/**
	 * @return saldo
	 */
	public double getSaldo() {
		return saldo;
	}
	/**
	 * @param saldo
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	

}
