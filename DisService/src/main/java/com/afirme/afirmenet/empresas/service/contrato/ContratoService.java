package com.afirme.afirmenet.empresas.service.contrato;

import java.sql.SQLException;


import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.beas.login.JBLogin;
/**
 * clase para validar la contraseña
 *  * 
 * @author Bayron Gamboa Martinez
 *
 * @version 1.0.0
 */
public interface ContratoService {

	/**
	 * @param idContrato
	 * @return
	 */
	public Contrato getDatosContrato(String idContrato);
	/**
	 * @param idContrato
	 * @return
	 * @throws SQLException
	 */
	/**
	 * @param idContrato
	 * @return
	 * @throws SQLException
	 */
	public JBLogin getDatosLogIn(String idContrato) throws SQLException;
	/**
	 * @param idContrato
	 * @param status
	 * @return
	 */
	public boolean setStatus(String idContrato, String status);
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
	 * @param contrato
	 * @return
	 */
	public String getCuentaContrato(String contrato);
	/* TODO: JBEntity aún tiene métodos por implementar. Agregar conforme se vayan necesitando.*/
}
