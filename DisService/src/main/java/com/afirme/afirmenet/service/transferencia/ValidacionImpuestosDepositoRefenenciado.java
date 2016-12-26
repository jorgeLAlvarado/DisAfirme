package com.afirme.afirmenet.service.transferencia;

import java.util.List;

import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.model.pagos.impuestos.DepositoReferenciado;

public interface ValidacionImpuestosDepositoRefenenciado {
	/**
	 * Valida el servicio de acuerdo al tipo de servicio se validara,
	 * referencia, fecha de vencimiento, monto
	 * 
	 * @param pago
	 * @return
	 * @throws AfirmeNetException
	 */
	void sonServiciosValidos(List<DepositoReferenciado> pago) throws AfirmeNetException;

	/**
	 * Valida el servicio de acuerdo al tipo de servicio se validara,
	 * referencia, fecha de vencimiento, monto
	 * 
	 * @param pago
	 * @return
	 * @throws AfirmeNetException
	 */
	void esServicioValido(DepositoReferenciado pago) throws AfirmeNetException;
}
