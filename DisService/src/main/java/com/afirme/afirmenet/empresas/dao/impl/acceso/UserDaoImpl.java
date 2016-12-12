package com.afirme.afirmenet.empresas.dao.impl.acceso;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.stereotype.Repository;

//import com.afirme.afirmenet.dao.AS400Dao;
//import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.empresas.dao.acceso.UserDao;
import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;


@Repository
public class UserDaoImpl implements UserDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(UserDaoImpl.class);

//	@Autowired
//	private AS400Dao as400Dao;
//	@Autowired
//	private DB2Dao db2Dao;
	
	/**
	 * Consulta correo
	 */
	@Override
	public String getMailUsuario(String contrato) {
		return null;
	}

	/**
	 * Actualizar el correo:
	 */
	@Override
	public boolean actualizarCorreoLogin(CorreoElectronicoDTO correoElectronicoDTO) throws Exception{
		return (Boolean) null;
	}
	
}
