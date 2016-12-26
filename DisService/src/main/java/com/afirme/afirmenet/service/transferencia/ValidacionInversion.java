package com.afirme.afirmenet.service.transferencia;

import java.util.List;

import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;

public interface ValidacionInversion {
	/**
	 * Valida las inversiones diarias que sean de cuentas correctas
	 * 
	 * @param pago
	 * @return
	 * @throws AfirmeNetException
	 */
	void sonInversionesDiairiasValidas(List<TransferenciaBase>  inversiones) throws AfirmeNetException;

	/**
	 * Valida la inversion diaria que sea de cuentas correctas
	 * 
	 * @param pago
	 * @return
	 * @throws AfirmeNetException
	 */
	void esInversionDiairiaValida(TransferenciaBase inversion) throws AfirmeNetException;
}
