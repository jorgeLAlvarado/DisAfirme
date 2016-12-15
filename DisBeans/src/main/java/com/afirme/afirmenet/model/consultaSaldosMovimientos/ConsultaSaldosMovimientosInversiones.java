package com.afirme.afirmenet.model.consultaSaldosMovimientos;

import java.util.Date;


/**
 * Created on Dic 14, 2016 3:39:05 PM by Noé
 * <br><br>
 * @author Noe
 * @version 1.0.0
 *
 */
public class ConsultaSaldosMovimientosInversiones {
	
	private String nombreCuenta;
	private double cantidad;
	private String clabe;
	
	private String tipoProducto;
	private int numeroContratoProducto;
	private Date fechaEmision;
	private Date fechaVencimiento;
	private int plazoInversion;
	private double tasaInteres;
	private double importeInversion;
	private double apertutacontrato;
	private double deducciones;
	
	
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
	 * @return tipoProducto
	 */
	public String getTipoProducto() {
		return tipoProducto;
	}
	/**
	 * @param tipoProducto
	 */
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	/**
	 * @return numeroContratoProducto
	 */
	public int getNumeroContratoProducto() {
		return numeroContratoProducto;
	}
	/**
	 * @param numeroContratoProducto
	 */
	public void setNumeroContratoProducto(int numeroContratoProducto) {
		this.numeroContratoProducto = numeroContratoProducto;
	}
	/**
	 * @return fechaEmision
	 */
	public Date getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
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
	 * @return plazoInversion
	 */
	public int getPlazoInversion() {
		return plazoInversion;
	}
	/**
	 * @param plazoInversion
	 */
	public void setPlazoInversion(int plazoInversion) {
		this.plazoInversion = plazoInversion;
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
	 * @return importyeInversion
	 */
	public double getImporteInversion() {
		return importeInversion;
	}
	/**
	 * @param importeInversion
	 */
	public void setImporteInversion(double importeInversion) {
		this.importeInversion = importeInversion;
	}
	/**
	 * @return apertutacontrato
	 */
	public double getApertutacontrato() {
		return apertutacontrato;
	}
	/**
	 * @param apertutacontrato
	 */
	public void setApertutacontrato(double apertutacontrato) {
		this.apertutacontrato = apertutacontrato;
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
	
	
	
	

}
