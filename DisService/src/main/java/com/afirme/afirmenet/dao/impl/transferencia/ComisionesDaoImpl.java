package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.transferencia.ComisionesDao;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.enums.TransaccionAS400;
import com.afirme.afirmenet.ibs.beans.transferencia.Comision;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
@Service
public class ComisionesDaoImpl implements ComisionesDao{
	
	@Autowired
	private AS400Dao as400Dao;
	
	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);
	
	@Override
	public BigDecimal getNationalFee(TransaccionAS400 transaccion, BigDecimal monto) {
		BigDecimal fee= new BigDecimal(0);
		try {
			String sql="SELECT RANCOMIS FROM "+AS400_LIBRARY+"INRANGOPF where RANTRAN=? AND RANGOCCY='MXP' AND RANGOINI<=? and RANGOFIN>=?";
			Map<String, Object> result = as400Dao.getJdbcTemplate().queryForMap(
					sql, new Object[] { transaccion.getValue(), monto, monto });
			if (result.size() > 0) {
				fee = new BigDecimal(result.get("RANCOMIS").toString());
			}
		} catch (EmptyResultDataAccessException ex) {}
		return fee;
	}

	@Override
	public BigDecimal getGeneralPIFee(String paqueteAfirmeNet) {
		BigDecimal fee= new BigDecimal(0);
		try {
			String sql="SELECT EUSTNA FROM "+AS400_LIBRARY+"GRPUSR where GRPUID=?";
			Map<String, Object> result = as400Dao.getJdbcTemplate().queryForMap(
					sql, new Object[] { paqueteAfirmeNet });
			if (result.size() > 0) {
				fee = new BigDecimal(result.get("EUSTNA").toString());
			}
		} catch (EmptyResultDataAccessException ex) {}
		return fee;
	}

	@Override
	public Comision getEspecialPIFee(TransaccionAS400 transaccion,
			String numeroCliente) {
		Comision fee= null;
		try {
			String sql="SELECT ESPPOR, ESPFIJ FROM "+AS400_LIBRARY+"coaesppf where ESPCUN=? and ESPCDE=?";
			Map<String, Object> result = as400Dao.getJdbcTemplate().queryForMap(
					sql, new Object[] { new BigDecimal(numeroCliente), new BigDecimal(transaccion.getValue()) });
			if (result.size() > 0) {
				fee= new Comision();
				fee.setFija(new BigDecimal(result.get("ESPFIJ").toString()));
				fee.setPorcentaje(new BigDecimal(result.get("ESPPOR").toString()));
			}
		} catch (EmptyResultDataAccessException ex) {}
		return fee;
	}

	
}
