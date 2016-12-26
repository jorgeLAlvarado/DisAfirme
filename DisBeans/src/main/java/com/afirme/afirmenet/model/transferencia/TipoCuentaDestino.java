package com.afirme.afirmenet.model.transferencia;

public enum TipoCuentaDestino {
	CHEQUES                                    	("01"),
	TARJETA_DEBIDO                             	("03"),
	TARJETA_CREDITO                            	("05"),
	CELULAR                                    	("10"),
	CLABE_INTERBANCARIA                        	("40"),
	CREDITO_HIPOTECARIO							("11"),
	CREDITO_AUTOMOTRIZ							("12"),
	CREDITO_PERSONAL							("13"),
	
	TC_AFIRME									("3"),
	TC_OTROS									("1"),
	TC_AMERICAN									("2"),
	TC_SEGUROS									("4"),
	TC_TARJETAS									("5");
	
	
	TipoCuentaDestino(String valor){
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
	
	public static TipoCuentaDestino findByValue(String value) {

		for (TipoCuentaDestino enumObj : values()) {
			if (enumObj.getValor().equals(value)) {
				return enumObj;
			}
		}
		return null;
	}

}
