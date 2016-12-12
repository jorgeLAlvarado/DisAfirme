package com.afirme.afirmenet.model.consultaSaldosMovimientos;

import java.util.Date;

public class ConsultaSaldosMovimientosLC {
	
	String nombreCuenta;
	double cantidad;
	String clabe;
	
	int numeroPrestamo;
	Date fechaApertura;
	double saldoPrestamo;
	double saldoInteres;
	double ultimoCargo;
	double deducciones;
	double pagoTotal;
	double montoPago;
	Date fechaVencimiento;
	Date fechaProxima;
	double tasaInteres;
	double creditoDisponible;
	double interesPagadoAnual;
	double interesAnoPrevio;
	Date fechaUltimoPago;
	
	
	
	public String getNombreCuenta() {
		return nombreCuenta;
	}
	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public String getClabe() {
		return clabe;
	}
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	public int getNumeroPrestamo() {
		return numeroPrestamo;
	}
	public void setNumeroPrestamo(int numeroPrestamo) {
		this.numeroPrestamo = numeroPrestamo;
	}
	public Date getFechaApertura() {
		return fechaApertura;
	}
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	public double getSaldoPrestamo() {
		return saldoPrestamo;
	}
	public void setSaldoPrestamo(double saldoPrestamo) {
		this.saldoPrestamo = saldoPrestamo;
	}
	public double getSaldoInteres() {
		return saldoInteres;
	}
	public void setSaldoInteres(double saldoInteres) {
		this.saldoInteres = saldoInteres;
	}
	public double getUltimoCargo() {
		return ultimoCargo;
	}
	public void setUltimoCargo(double ultimoCargo) {
		this.ultimoCargo = ultimoCargo;
	}
	public double getDeducciones() {
		return deducciones;
	}
	public void setDeducciones(double deducciones) {
		this.deducciones = deducciones;
	}
	public double getPagoTotal() {
		return pagoTotal;
	}
	public void setPagoTotal(double pagoTotal) {
		this.pagoTotal = pagoTotal;
	}
	public double getMontoPago() {
		return montoPago;
	}
	public void setMontoPago(double montoPago) {
		this.montoPago = montoPago;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public Date getFechaProxima() {
		return fechaProxima;
	}
	public void setFechaProxima(Date fechaProxima) {
		this.fechaProxima = fechaProxima;
	}
	public double getTasaInteres() {
		return tasaInteres;
	}
	public void setTasaInteres(double tasaInteres) {
		this.tasaInteres = tasaInteres;
	}
	public double getCreditoDisponible() {
		return creditoDisponible;
	}
	public void setCreditoDisponible(double creditoDisponible) {
		this.creditoDisponible = creditoDisponible;
	}
	public double getInteresPagadoAnual() {
		return interesPagadoAnual;
	}
	public void setInteresPagadoAnual(double interesPagadoAnual) {
		this.interesPagadoAnual = interesPagadoAnual;
	}
	public double getInteresAnoPrevio() {
		return interesAnoPrevio;
	}
	public void setInteresAnoPrevio(double interesAnoPrevio) {
		this.interesAnoPrevio = interesAnoPrevio;
	}
	public Date getFechaUltimoPago() {
		return fechaUltimoPago;
	}
	public void setFechaUltimoPago(Date fechaUltimoPago) {
		this.fechaUltimoPago = fechaUltimoPago;
	}
	
	
	
	

}
