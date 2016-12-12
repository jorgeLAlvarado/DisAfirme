package com.afirme.afirmenet.model.consultaSaldosMovimientos;

import java.util.Date;

public class ConsultaSaldosMovimientosInversiones {
	
	String nombreCuenta;
	double cantidad;
	String clabe;
	
	String tipoProducto;
	int numeroContratoProducto;
	Date fechaEmision;
	Date fechaVencimiento;
	int plazoInversion;
	double tasaInteres;
	double importeInversion;
	double apertutacontrato;
	double deducciones;
	
	
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
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public int getNumeroContratoProducto() {
		return numeroContratoProducto;
	}
	public void setNumeroContratoProducto(int numeroContratoProducto) {
		this.numeroContratoProducto = numeroContratoProducto;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public int getPlazoInversion() {
		return plazoInversion;
	}
	public void setPlazoInversion(int plazoInversion) {
		this.plazoInversion = plazoInversion;
	}
	public double getTasaInteres() {
		return tasaInteres;
	}
	public void setTasaInteres(double tasaInteres) {
		this.tasaInteres = tasaInteres;
	}
	public double getImporteInversion() {
		return importeInversion;
	}
	public void setImporteInversion(double importeInversion) {
		this.importeInversion = importeInversion;
	}
	public double getApertutacontrato() {
		return apertutacontrato;
	}
	public void setApertutacontrato(double apertutacontrato) {
		this.apertutacontrato = apertutacontrato;
	}
	public double getDeducciones() {
		return deducciones;
	}
	public void setDeducciones(double deducciones) {
		this.deducciones = deducciones;
	}
	
	
	
	

}
