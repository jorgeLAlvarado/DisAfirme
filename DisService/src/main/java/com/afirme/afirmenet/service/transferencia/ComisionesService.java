package com.afirme.afirmenet.service.transferencia;

import java.math.BigDecimal;

import com.afirme.afirmenet.enums.TransaccionAS400;

public interface ComisionesService {
	public BigDecimal getFee(TransaccionAS400 transaccion, BigDecimal monto, String paqueteAfirmeNet, String numeroCliente);
}