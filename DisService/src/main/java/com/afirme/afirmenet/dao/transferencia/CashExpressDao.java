package com.afirme.afirmenet.dao.transferencia;

import java.util.List;

import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpress;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpressParametro;

public interface CashExpressDao {
	List<CashExpress> buscarCashExpress(String contrato, String fechaDesde, String fechaHasta, String cuenta, String estado);
	void datosExtraInotr(CashExpress oOrden);
	CashExpressParametro obtenerParametro();
}
