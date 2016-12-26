package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.transferencia.CashExpressBeneficiarioDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.utils.AfirmeNetConstants;

@Repository
public class CashExpressBeneficiarioDaoImpl implements
		CashExpressBeneficiarioDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(CashExpressBeneficiarioDaoImpl.class);
	
	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);
	
	@Autowired
	private AS400Dao as400Dao;
	
	@Override
	public void activaBeneficiario(ACCTHIRDUSER oBene) {

	}

	@Override
	public void agregaEliminaBeneficiario(ACCTHIRDUSER oBene, BigDecimal accion) {

	}


	@Override
	public List<ACCTHIRDUSER> consultaBeneficiarios(String contrato,
			String estado, String tiempoEsperaCuentas) {
	
		return null;
	}

}
