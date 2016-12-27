package com.afirme.afirmenet.empresas.service.impl.contrato;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.acceso.ContratoDao;
import com.afirme.afirmenet.empresas.service.contrato.ContratoService;
import com.afirme.afirmenet.beas.login.Contrato;
import com.afirme.afirmenet.beas.login.JBLogin;

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
@Service
public class ContratoServiceImpl implements ContratoService {
	
	@Autowired
	private ContratoDao contratoDao;
	

	@Override
	public Contrato getDatosContrato(String idContrato) {
		return contratoDao.getDatosContrato(idContrato);
	}


	@Override
	public JBLogin getDatosLogIn(String idContrato) throws SQLException{
		return contratoDao.getDatosLogIn(idContrato);
	}

	
	@Override
	public boolean setStatus(String idContrato, String status) {
		return contratoDao.setStatus(idContrato, status);
	}


	@Override
	public boolean updateContrato(Contrato contrato, String idContrato) {
		return contratoDao.updateContrato(contrato, idContrato);
	}


	@Override
	public boolean setPassword(String idContrato, String password, String status) {
		return contratoDao.setPassword(idContrato, password, status);
	}


	@Override
	public String getCuentaContrato(String contrato) {
		return contratoDao.getCuentaContrato(contrato);
	}
}
