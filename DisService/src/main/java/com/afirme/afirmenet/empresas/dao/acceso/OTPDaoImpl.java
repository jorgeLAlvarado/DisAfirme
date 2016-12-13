package com.afirme.afirmenet.empresas.dao.acceso;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.dao.acceso.OTPDao;

@Repository
public class OTPDaoImpl implements OTPDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(OTPDaoImpl.class);

	

	@Override
	public String obtenToken(String contrato) {
		
		return null;
	}
	
	@Override
	public boolean usaTokens(String contrato) {
		
			return null;						
		}

		
	}

	@Override
	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion) {
		
		return null;
	}

	@Override
	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEncrypt) {
		
		return null;
	}

	@Override
	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento) {
		
		
		return null;
	}
}
