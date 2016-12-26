package com.afirme.afirmenet.dao.transferencia;

import java.math.BigDecimal;

import com.afirme.afirmenet.enums.TransaccionAS400;
import com.afirme.afirmenet.ibs.beans.transferencia.Comision;

public interface ComisionesDao {
	public BigDecimal getNationalFee(TransaccionAS400 transaccion, BigDecimal monto);
	public BigDecimal getGeneralPIFee(String paqueteAfirmeNet);
	public Comision getEspecialPIFee(TransaccionAS400 transaccion, String numeroCliente);
}
