package com.afirme.afirmenet.empresas.dao.acceso;

import java.sql.SQLException;

import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.beas.login.JBLogin;
/**
 * clase para informacion del contrato
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * Created on Created on Dic 10, 2016 3:50:05 PM by Jorge
 */
public interface ContratoDao {
	
	/**
	 * @param idContrato
	 * @return
	 */
	public Contrato getDatosContrato(String idContrato);
	/**
	 * @param contrato
	 * @param idContrato
	 * @return
	 */
	public boolean updateContrato(Contrato contrato, String idContrato);
	/**
	 * @param idContrato
	 * @param password
	 * @param status
	 * @return
	 */
	public boolean setPassword(String idContrato, String password, String status);
	/**
	 * @param idContrato
	 * @param status
	 * @return
	 */
	public boolean setStatus(String idContrato, String status);
	/**
	 * @param idContrato
	 * @return
	 * @throws SQLException
	 */
	public JBLogin getDatosLogIn(String idContrato) throws SQLException;
	/**
	 * @param contrato
	 * @return
	 */
	public String getCuentaContrato(String contrato);
}
