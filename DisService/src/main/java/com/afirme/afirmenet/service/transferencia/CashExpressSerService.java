package com.afirme.afirmenet.service.transferencia;

import java.util.List;

import com.afirme.afirmenet.model.base.CatalogoBase;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpress;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpressParametro;

public interface CashExpressSerService {
	List<CashExpress> buscarCashExpress(String contrato, String fechaDesde, String fechaHasta, String cuenta, String estado);
	void datosExtraInotr(CashExpress oOrden);
	CashExpressParametro obtenerParametro();
	List<CatalogoBase> getEstados();
	CashExpress seleccionar(List<CashExpress> lista, String id);
}
