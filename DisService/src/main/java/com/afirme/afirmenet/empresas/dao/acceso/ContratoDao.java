package com.afirme.afirmenet.empresas.dao.acceso;

import java.sql.SQLException;

import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.beas.login.JBLogin;

public interface ContratoDao {
	public Contrato getDatosContrato(String idContrato);
	public boolean updateContrato(Contrato contrato, String idContrato);
	public boolean setPassword(String idContrato, String password, String status);
	public boolean setStatus(String idContrato, String status);
	public JBLogin getDatosLogIn(String idContrato) throws SQLException;
	public String getCuentaContrato(String contrato);
}
