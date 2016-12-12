package com.afirme.afirmenet.model.tdc;

import java.util.Date;

import com.afirme.afirmenet.model.pagos.servicios.Servicio;

/**
 * @author Noe
 *
 */
public class ConsulatasSaldosMovimientos {
	
	private String nombre;
	private String tipoTarjeta;
	private int numerTarjeta;
	private double creditoDisponible;
	private double limiteCredito;
	private double tasaInteres;
	private double pagoMinimo;
	private double pagoDia;
	private double pagoMinimoPromos;
	private Date fechaLimitePago;
	private Date fechaCorte;
	private double pagoNoGenerarIntereses;
	
	private double saldoCorte;
	private double compras;
	private double otrosCargos;
	private double pagosOCreditos;
	private double saldoActual;
	
	
	
	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}
	/**
	 * @param tipoTarjeta
	 */
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	/**
	 * @return
	 */
	public int getNumerTarjeta() {
		return numerTarjeta;
	}
	/**
	 * @param numerTarjeta
	 */
	public void setNumerTarjeta(int numerTarjeta) {
		this.numerTarjeta = numerTarjeta;
	}
	public double getCreditoDisponible() {
		return creditoDisponible;
	}
	/**
	 * @param creditoDisponible
	 */
	public void setCreditoDisponible(double creditoDisponible) {
		this.creditoDisponible = creditoDisponible;
	}
	public double getLimiteCredito() {
		return limiteCredito;
	}
	/**
	 * @param limiteCredito
	 */
	public void setLimiteCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
	public double getTasaInteres() {
		return tasaInteres;
	}
	/**
	 * @param tasaInteres
	 */
	public void setTasaInteres(double tasaInteres) {
		this.tasaInteres = tasaInteres;
	}
	public double getPagoMinimo() {
		return pagoMinimo;
	}
	/**
	 * @param pagoMinimo
	 */
	public void setPagoMinimo(double pagoMinimo) {
		this.pagoMinimo = pagoMinimo;
	}
	public double getPagoDia() {
		return pagoDia;
	}
	/**
	 * @param pagoDia
	 */
	public void setPagoDia(double pagoDia) {
		this.pagoDia = pagoDia;
	}
	public double getPagoMinimoPromos() {
		return pagoMinimoPromos;
	}
	/**
	 * @param pagoMinimoPromos
	 */
	public void setPagoMinimoPromos(double pagoMinimoPromos) {
		this.pagoMinimoPromos = pagoMinimoPromos;
	}
	public Date getFechaLimitePago() {
		return fechaLimitePago;
	}
	/**
	 * @param fechaLimitePago
	 */
	public void setFechaLimitePago(Date fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}
	public Date getFechaCorte() {
		return fechaCorte;
	}
	/**
	 * @param fechaCorte
	 */
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	public double getPagoNoGenerarIntereses() {
		return pagoNoGenerarIntereses;
	}
	/**
	 * @param pagoNoGenerarIntereses
	 */
	public void setPagoNoGenerarIntereses(double pagoNoGenerarIntereses) {
		this.pagoNoGenerarIntereses = pagoNoGenerarIntereses;
	}
	public double getSaldoCorte() {
		return saldoCorte;
	}
	/**
	 * @param saldoCorte
	 */
	public void setSaldoCorte(double saldoCorte) {
		this.saldoCorte = saldoCorte;
	}
	public double getCompras() {
		return compras;
	}
	/**
	 * @param compras
	 */
	public void setCompras(double compras) {
		this.compras = compras;
	}
	public double getOtrosCargos() {
		return otrosCargos;
	}
	/**
	 * @param otrosCargos
	 */
	public void setOtrosCargos(double otrosCargos) {
		this.otrosCargos = otrosCargos;
	}
	public double getPagosOCreditos() {
		return pagosOCreditos;
	}
	/**
	 * @param pagosOCreditos
	 */
	public void setPagosOCreditos(double pagosOCreditos) {
		this.pagosOCreditos = pagosOCreditos;
	}
	public double getSaldoActual() {
		return saldoActual;
	}
	
	/**
	 * @param saldoActual
	 */
	public void setSaldoActual(double saldoActual) {
		this.saldoActual = saldoActual;
	}
	
	
	
	
	

}
