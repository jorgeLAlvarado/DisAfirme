package com.afirme.afirmenet.ibs.beans.consultas;


public enum HistorialTipo {
	 PAGO_DE_IMPUESTOS ("12"),
	 PAGO_DE_SERVICIOS ("28"),
	 INVERSION_DIARIA ("36"),
	 DOMINGO_ELECTRONICO ("37"),
	 CHEQUES_EXTRAVIADOS ("40"),
	 PROTECCION_DE_CHEQUES ("41"),
	 SOLICITUD_DE_CHEQUERAS ("42"),
	 PAGO_REFERENCIADO ("47"),
	 PAGO_TESORERIA_GOBIERNO_DISTRITO_FEDERAL ("49"),
	 TRANSFERENCIAS_INTERNACIONALES_MULTIMONEDA ("51"),
	 BAJA_DE_EMPLEADOS ("55"),
	 RECARGA_DE_TIEMPO_AIRE_TELCEL ("57"),
	 EMISION_DE_ESTADO_DE_CUENTA ("60"),
	 INVERSION_PERFECTA ("65"),
	 PAGO_DE_IMPUESTOS_DEPOSITO_REFERENCIADO ("66"),
	 ACTIVACION_DE_SERVICIOS ("67"),
	 PAGARE_AHORRAFIRME ("68"),
	 AFIRME_MOVIL ("69"),
	 ANTICIPO_DE_NOMINA ("70"),
	 CREDITO_DE_NOMINA ("72"),
	 REPOSICION_DE_TARJETAS ("74"),
	 RECARGA_DE_TIEMPO_AIRE_MOVISTAR ("78"),
	 ORDENES_DE_PAGO_CASH_EXPRESS ("80"),
	 TRASPASO_PUNTOS_BONUS ("81"),
	 DOMICILIACION_DE_SERVICIOS_BASICOS ("82"),
	 AVISO_DE_VIAJE ("86"),
	 MIS_CREDITOS_AFIRME ("87"),
	 PAGARE_GRADUAL ("88"),
	 PAGARE_MULTIPLE ("89"),
	 VENTA_DE_SEGUROS_ANTIFRAUDE ("90"),
	 ALERTAS_AFIRME ("91"),
	 ASOCIACION_DE_CUENTA_A_NUMERO_MOVIL ("94"),
	 DISPOSICION_DE_EFECTIVO_TDC ("95"),
	 ACTIVACION_DE_TARJETA_DE_CREDITO ("96"),
	 CAMBIO_DE_LIMITES_TARJETA_CREDITO_ADICIONAL ("97"),
	 PAGO_TARJETAS_DE_CREDITO ("288"),
	 PAGO_SEGUROS_AFIRME ("289"),
	 PROGRAMACION_DE_PAGOS_AFIRME_DOMICILIACION ("52"),
	 CANCELACION_DE_PROGRAMACION_DE_PAGOS_AFIRME_DOMICILIACION ("291"),
	 REGISTRO_DE_TARJETAS_DE_CREDITO_Y_SEGUROS_AFIRME ("292"),
	 TRASPASO_PROPIAS ("02"),
	 TRASPASO_TERCEROS ("03"),
	 TRANSFERENCIAS_INTERNACIONALES_DOLARES ("04"),
	 TRANSFERENCIA_SPEI ("05"),
	 DOMICILIACION ("06"),
	 NOMINA ("07"),
	 PAGO_INTERBANCARIO ("09"),
	 CAMBIO_DE_ALIAS ("ALIAS"),
	 CAMBIO_DE_CORREO_ELECTRONICO ("CC"),
	 ESTATUS_SERVICIO_EMISION_ESTADO_DE_CUENTA ("CEST"),
	 CAMBIO_DE_ESTATUS_TARJETA_DE_DEBITO ("CETB"),
	 CAMBIO_DE_LIMITES_TARJETA_DE_DEBITO ("CLTD"),
	 CAMBIO_DE_LIMITES_DE_TRANSFERENCIAS ("CLTR"),
	 CANCELACION_DE_OPERACIONES_PROGRAMADAS ("COP"),
	 CAMBIO_DE_CONTRASENA ("CPAS"),
	 ELIMINACION_DE_CUENTAS_DESTINO ("ECTT"),
	 PORTABILIDAD_DE_NOMINA ("21");
	
	HistorialTipo(String valor){
		this.valor = valor;
	}
	
	private String valor;

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor the value to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public static HistorialTipo findByValue(String value) {

		for (HistorialTipo enumObj : values()) {
			if (enumObj.getValor().equals(value)) {
				return enumObj;
			}
		}
		return null;
	}
}