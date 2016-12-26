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
	
	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);
	
	@Override
	public BigDecimal getNationalFee(TransaccionAS400 transaccion, BigDecimal monto) {
		return null;
	}

	@Override
	public BigDecimal getGeneralPIFee(String paqueteAfirmeNet) {
		return null;
	}

	@Override
	public Comision getEspecialPIFee(TransaccionAS400 transaccion,
			String numeroCliente) {
		return null;
	}

	
}
