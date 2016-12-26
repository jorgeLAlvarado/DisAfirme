package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.dao.transferencia.ParametricasUsuarioDao;
import com.afirme.afirmenet.ibs.objects.parameter.JOParamAccumAcc;
import com.afirme.afirmenet.ibs.objects.parameter.JOParamOwnAcc;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.utils.time.TimeUtils;

@Repository
public class ParametricasUsuarioDaoImpl implements ParametricasUsuarioDao {
	static final AfirmeNetLog LOG = new AfirmeNetLog(ParametricasUsuarioDaoImpl.class);

	@Override
	public void insert(Comprobante comprobante) throws SQLException {
	}

	@Override
	public boolean delete(long contract, String bxiRef) {
		return (Boolean) null;
	}

	@Override
	public List<JOParamOwnAcc> getParameters(long contract, long account, String transtype, int serviceType,
			int intertype) {
		return null;
	}

	@Override
	public List<JOParamAccumAcc> getAcumulado(long contract, long account, String transtype, int serviceType,
			int intertype, Date initialD, Date finalD) {
		return null;
	}

	@Override
	public boolean trasnferenciaEnTiempo(TipoTransferencia tipo, int tiempo, String dia) {
		return (Boolean) null;
	}

	@Override
	public int insertParamOwnAcc(List<JOParamOwnAcc> parametricas) {
		return (Integer) null;
	}

	@Override
	public int updateParamOwnAcc(List<JOParamOwnAcc> parametricas) {
		return null;
	}

	@Override
	public List<JOParamOwnAcc> findRegisterChanged(long contract) {
		return null;
	}

}
