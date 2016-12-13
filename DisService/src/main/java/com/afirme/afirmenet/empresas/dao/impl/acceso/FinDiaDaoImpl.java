package com.afirme.afirmenet.empresas.dao.impl.acceso;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.empresas.dao.acceso.FinDiaDao;
import com.afirme.afirmenet.utils.AfirmeNetLog;

/**
 * clase para dar la validacion de fin de dia
 * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
@Repository
public class FinDiaDaoImpl implements FinDiaDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(FinDiaDaoImpl.class);

	
	/**
	 * metodo para validar manda la nueva contraseña
	 * @return TRUE si el proceso no presento problemas.
	 */
	@Override
	public String getFinDia() {
		

		return null;
	}

	
	/**
	 * metodo para validar manda la nueva contraseña
	 * @param nuevoPassword
	 * @param password
	 * @return TRUE si el proceso no presento problemas.
	 */
	@Override
	public void valActivSocket() {
		
		
	}

}
