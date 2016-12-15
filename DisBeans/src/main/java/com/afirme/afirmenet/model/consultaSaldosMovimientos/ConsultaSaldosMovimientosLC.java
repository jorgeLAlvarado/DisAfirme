package com.afirme.afirmenet.model.consultaSaldosMovimientos;

import java.util.Date;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Noé
 * @author Noe
 * @version 1.0.0
 */
public class ConsultaSaldosMovimientosLC {
	
	
	private String nombreCuenta;
	private double cantidad;
	private String clabe;
	
	private int numeroPrestamo;
	private Date fechaApertura;
	private double saldoPrestamo;
	private double saldoInteres;
	private double ultimoCargo;
	private double deducciones;
	private double pagoTotal;
	private double montoPago;
	private Date fechaVencimiento;
	private Date fechaProxima;
	private double tasaInteres;
	private double creditoDisponible;
	private double interesPagadoAnual;
	private double interesAnoPrevio;
	private Date fechaUltimoPago;
	
	
	
	/**
	 * @return nombreCuenta
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
	 * @return numeroPrestamo
	 */
	public int getNumeroPrestamo() {
		return numeroPrestamo;
	}
	/**
	 * @param numeroPrestamo
	 */
	
	public void setNumeroPrestamo(int numeroPrestamo) {
		this.numeroPrestamo = numeroPrestamo;
	}
	/**
	 * @return fechaApertura
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}
	/**
	 * @param fechaApertura
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	/**
	 * @return saldoPrestamo
	 */
	public double getSaldoPrestamo() {
		return saldoPrestamo;
	}
	/**
	 * @param saldoPrestamo
	 */
	public void setSaldoPrestamo(double saldoPrestamo) {
		this.saldoPrestamo = saldoPrestamo;
	}
	/**
	 * @return saldoInteres
	 */
	public double getSaldoInteres() {
		return saldoInteres;
	}
	/**
	 * @param saldoInteres
	 */
	public void setSaldoInteres(double saldoInteres) {
		this.saldoInteres = saldoInteres;
	}
	/**
	 * @return ultimoCargo
	 */
	public double getUltimoCargo() {
		return ultimoCargo;
	}
	/**
	 * @param ultimoCargo
	 */
	public void setUltimoCargo(double ultimoCargo) {
		this.ultimoCargo = ultimoCargo;
	}
	/**
	 * @return deducciones
	 */
	public double getDeducciones() {
		return deducciones;
	}
	/**
	 * @param deducciones
	 */
	public void setDeducciones(double deducciones) {
		this.deducciones = deducciones;
	}
	/**
	 * @return pagoTotal
	 */
	public double getPagoTotal() {
		return pagoTotal;
	}
	/**
	 * @param pagoTotal
	 */
	public void setPagoTotal(double pagoTotal) {
		this.pagoTotal = pagoTotal;
	}
	/**
	 * @return montoPago
	 */
	public double getMontoPago() {
		return montoPago;
	}
	/**
	 * @param montoPago
	 */
	public void setMontoPago(double montoPago) {
		this.montoPago = montoPago;
	}
	/**
	 * @return fechaVencimiento
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	/**
	 * @param fechaVencimiento
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	/**
	 * @return fechaProxima
	 */
	public Date getFechaProxima() {
		return fechaProxima;
	}
	/**
	 * @param fechaProxima
	 */
	public void setFechaProxima(Date fechaProxima) {
		this.fechaProxima = fechaProxima;
	}
	/**
	 * @return tasaInteres
	 */
	public double getTasaInteres() {
		return tasaInteres;
	}
	/**
	 * @param tasaInteres
	 */
	public void setTasaInteres(double tasaInteres) {
		this.tasaInteres = tasaInteres;
	}
	/**
	 * @return creditoDisponible
	 */
	public double getCreditoDisponible() {
		return creditoDisponible;
	}
	/**
	 * @param creditoDisponible
	 */
	public void setCreditoDisponible(double creditoDisponible) {
		this.creditoDisponible = creditoDisponible;
	}
	/**
	 * @return interesPagoAnual
	 */
	public double getInteresPagadoAnual() {
		return interesPagadoAnual;
	}
	/**
	 * @param interesPagadoAnual
	 */
	public void setInteresPagadoAnual(double interesPagadoAnual) {
		this.interesPagadoAnual = interesPagadoAnual;
	}
	/**
	 * @return interesAnoPrevio
	 */
	public double getInteresAnoPrevio() {
		return interesAnoPrevio;
	}
	/**
	 * @param interesAnoPrevio
	 */
	public void setInteresAnoPrevio(double interesAnoPrevio) {
		this.interesAnoPrevio = interesAnoPrevio;
	}
	/**
	 * @return fechaUltimoPago
	 */
	public Date getFechaUltimoPago() {
		return fechaUltimoPago;
	}
	/**
	 * @param fechaUltimoPago
	 */
	public void setFechaUltimoPago(Date fechaUltimoPago) {
		this.fechaUltimoPago = fechaUltimoPago;
	}
	
}
