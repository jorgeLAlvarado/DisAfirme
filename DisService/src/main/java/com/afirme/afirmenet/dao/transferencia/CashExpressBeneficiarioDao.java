package com.afirme.afirmenet.dao.transferencia;

import java.math.BigDecimal;
import java.util.List;

import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;

public interface CashExpressBeneficiarioDao {
	void activaBeneficiario(ACCTHIRDUSER oBene);
	void agregaEliminaBeneficiario(ACCTHIRDUSER oBene, BigDecimal accion);
	List<ACCTHIRDUSER> consultaBeneficiarios(String contrato, String estado, String tiempoEsperaCuentas);
}
