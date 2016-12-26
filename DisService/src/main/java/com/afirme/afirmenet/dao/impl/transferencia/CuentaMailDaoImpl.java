package com.afirme.afirmenet.dao.impl.transferencia;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.transferencia.CuentaMailDao;
import com.afirme.afirmenet.utils.AfirmeNetLog;

@Repository
public class CuentaMailDaoImpl implements CuentaMailDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(CuentaTercerosDaoImpl.class);

	

	@Override
	public Map<String, String> getEmailReferences(String idContrato) {
		return null;
	}

	@Override
	public String getCorreoCuenta(String contrato, String cuenta) {
		return null;
	}

	@Override
	public boolean updateCorreoCuenta(String contrato, String cuenta, String correo) {
		return (Boolean) null;
	}

	@Override
	public boolean tieneCorreo(String contrato, String cuenta) {
		return (Boolean) null;
	}

	@Override
	public boolean setCorreoCuenta(String contrato, String cuenta, String correo) {
		return (Boolean) null;
	}

}
