package com.afirme.afirmenet.empresas.dao.impl.acceso;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.beas.login.JBLogin;
import com.afirme.afirmenet.empresas.dao.acceso.ContratoDao;
import com.afirme.afirmenet.utils.AfirmeNetLog;

/**
 * Controller para las pantallas de las url donde se debe mostrar la pantalla de login.
 * 
 * @author jorge.canoc@gmail.com
 * 
 * Modificado on Nov 6, 2015 10:12:21 AM by eguzher
 * <br>
 * <br>
 * @author epifanio.guzman@afirme.com
 * <br>
 * 
 * Modificado on dic 21, 2016 1:12:21 pM by Bayron 
 * 
 * @author Bayron Gamboa Martinez
 */
@Repository
public class ContratoDaoImpl implements ContratoDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(ContratoDaoImpl.class);
	
	
	
	@Autowired
	private MessageSource dibsSource;
	
	
	/**
	 * Da la cuenta del Contacto
	 * @param contacto
	 * @return
	 */
	@Override
	public String getCuentaContrato(String contrato) {
		LOG.info("Cuenta Contrato DAO Exitoso");
		return null;
	}
	
	/**
	 * Da la datos del Contacto
	 * @param contacto
	 * @return
	 */
	@Override
	public Contrato getDatosContrato(String idContrato) {
		LOG.info("Datos Contrato DAO Exitoso");

		
			return null;
	
	}

	/**
	 * Da la cuenta del Contacto
	 * @param contacto
	 * @return
	 */
	@Override
	public boolean updateContrato(Contrato contrato, String idContrato) {
		LOG.info("Actualizacion Contrato");
		
		
		
		return false;
	}

	/**
	 * Da la contrase�a del Contacto
	 * @param idContrato
	 * @param password
	 * @param status
	 * @return
	 */
	@Override
	public boolean setPassword(String idContrato, String password, String status) {
		LOG.info("Contase�a DAO Exitoso");
		
		return false;
	}
	
	/**
	 * Da la el status del Contacto
	 * @param idContrato
	 * @param status
	 * @return
	 */
	public boolean setStatus(String idContrato, String status) {
		LOG.info("Estatus DAO Exitoso");
		
		
		
		return false;
	}
	
	
	/**
	 * Da la los datos del login
	 * @param idContrato
	 * @return
	 */
	@Override
	public JBLogin getDatosLogIn(String idContrato) throws SQLException {
		LOG.info("Datos Login DAO Exitoso");

		return null;
	}
	
}














