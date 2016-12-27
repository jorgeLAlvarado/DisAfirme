package com.afirme.afirmenet.empresas.dao.impl.acceso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.impl.acceso.DB2Dao;
import com.afirme.afirmenet.empresas.dao.acceso.OTPDao;
import com.afirme.afirmenet.utils.AfirmeNetLog;

/**
 * clase para implementar la consulta al usuario
 * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
@Repository
public class OTPDaoImpl implements OTPDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(OTPDaoImpl.class);

	@Autowired
	private DB2Dao db2Dao;
	

	

	public String obtenToken(String contrato) {
		
		return null;
	}
	
	
	
	public boolean usaTokens(String contrato) {
		
			return false;						
		
		
	}

	

	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion) {
		
		return false;
	}

	

	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEncrypt) {
		
		return false;
	}


	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento) {
		
		
		return false;
	}
}
