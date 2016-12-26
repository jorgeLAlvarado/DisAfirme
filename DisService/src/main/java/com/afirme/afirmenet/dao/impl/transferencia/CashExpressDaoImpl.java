package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.transferencia.CashExpressDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpress;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpressParametro;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
@Service
public class CashExpressDaoImpl implements CashExpressDao {

	@Autowired
	private AS400Dao as400Dao;
	
	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);

	@Override
	public List<CashExpress> buscarCashExpress(String contrato,
			String fechaDesde, String fechaHasta, String cuenta, String estado) {
		return null;
	}

	@Override
	public void datosExtraInotr(CashExpress oOrden) {
		
	}

	@Override
	public CashExpressParametro obtenerParametro() {
		CashExpressParametro parametro=null;
		return null;
	}
}
