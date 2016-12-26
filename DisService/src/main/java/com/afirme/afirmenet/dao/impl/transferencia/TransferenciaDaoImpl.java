package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.dao.transferencia.TransferenciaDao;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.databeans.GRPUSR;
import com.afirme.afirmenet.model.transferencia.DomingoElectronico;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.time.TimeUtils;

@Repository
public class TransferenciaDaoImpl implements TransferenciaDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(TransferenciaDaoImpl.class);

	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);

	@Override
	public GRPUSR getGroupCosts(String idGrupo, String tipoRegimen) {
		return null;
	}

	public void actualizaRegistroPagoConcentrado(DomingoElectronico domingoElectronico) {
		
	}

	@Override
	public void generaRegistroPagoConcentrado(DomingoElectronico domingoElectronico) {
		
	}

}
