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
	

	
	/**
	 * metodo para obtener el toquen del usuario
	 * @param contrato
	 * @return regresa el token del usuario.
	 */
	@Override
	public String obtenToken(String contrato) {
		
		return null;
	}
	
	
	/**
	 * metodo para usar el token del usuario
	 * @param contrato
	 * @return TRUE si el proceso no presento problemas.
	 */
	@Override
	public boolean usaTokens(String contrato) {
		
			return false;						
		
		
	}

	
	/**
	 * metodo para valida la activacion del token
	 * @param contrato
	 * @param usuario
	 * @param serialToken
	 * @param codigoActivacion
	 * @return TRUE si el proceso no presento problemas.
	 */
	@Override
	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion) {
		
		return false;
	}

	
	/**
	 * metodo para resube el codigo de seguridad
	 * @param contrato
	 * @param usuario
	 * @param codigoSegEncrypt
	 * @return TRUE si el proceso no presento problemas.
	 */
	@Override
	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEncrypt) {
		
		return false;
	}

	
	/**
	 * metodo para tomar la fecha de vencimiento 
	 * @param serialToken
	 * @param fechaVencimiento
	 * @return TRUE si el proceso no presento problemas.
	 * 
	 */
	@Override
	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento) {
		
		
		return false;
	}
}
