package com.afirme.afirmenet.dao.impl.transferencia;

import java.util.List;
import java.util.Map;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.dao.impl.acceso.PreguntaSecretaDaoImpl;
import com.afirme.afirmenet.dao.transferencia.TasaCambioPreferencialDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.beans.JBSIC001PF;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.utils.AfirmeNetConstants;

@Repository
public class TasaCambioPreferencialDaoImpl implements TasaCambioPreferencialDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(PreguntaSecretaDaoImpl.class);

	@Override
	public JBSIC001PF validateCveTasaPreferencial(String cveTasaPref) {
		return null;
	}

}
