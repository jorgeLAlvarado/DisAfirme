package com.afirme.afirmenet.empresas.dao.impl.acceso;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import com.afirme.afirmenet.dao.impl.acceso.AS400Dao;
//import com.afirme.afirmenet.dao.impl.acceso.DB2Dao;
//import com.afirme.afirmenet.dao.AS400Dao;
//import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;

/**
 * clase para validar la contraseña
 *  * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
@Repository
public class UserDaoImpl implements UserDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(UserDaoImpl.class);

	@Autowired
	private AS400Dao as400Dao;
	@Autowired
	private DB2Dao db2Dao;
	
	/**
	 * metodo para pide el corero del usuario
	 * @param contrato
	 * @return el correo del contacto.
	 * 
	 */
	@Override
	public String getMailUsuario(String contrato) {
		return null;
	}

	/**
	 * metodo para actualizar el correo del usuario
	 * @param correoElectronicoDTO
	 * @return TRUE si el proceso no presento problemas.
	 */
	@Override
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception{
		return false;
	}
	
}
