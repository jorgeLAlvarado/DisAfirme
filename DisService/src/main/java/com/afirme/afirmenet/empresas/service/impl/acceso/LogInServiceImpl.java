package com.afirme.afirmenet.empresas.service.impl.acceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.acceso.LogInDao;
import com.afirme.afirmenet.dao.convenios.ConveniosDao;
import com.afirme.afirmenet.dao.log.LogDao;
import com.afirme.afirmenet.empresas.service.acceso.ContrasenaDTO;
import com.afirme.afirmenet.empresas.service.acceso.DC_CONFMSG;
import com.afirme.afirmenet.empresas.service.acceso.LogInService;
import com.afirme.afirmenet.empresas.service.acceso.ParametricasLoginService;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.beans.JBAvatar;
import com.afirme.afirmenet.ibs.beans.JBBMuser;
import com.afirme.afirmenet.ibs.beans.JBLogin;
import com.afirme.afirmenet.ibs.beans.JBProCode;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.utils.AfirmeNetLog;

@Service
public class LogInServiceImpl implements LogInService {
	static final AfirmeNetLog LOG = new AfirmeNetLog(LogInServiceImpl.class);

	@Autowired
	private ConveniosDao conveniosDao;

	@Autowired
	private LogInDao loginDao;	
	
	@Autowired
	private LogDao logDao;	
	
	@Autowired
	private ParametricasLoginService parametricasLoginService;

	@Override
	public boolean verificarPrimerLoginConAlias(String contrato) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean actualizarPrimerLoginConAlias(String contrato) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verificaINOUT(String contrato) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateINOUT(String UserIdJSP, String Estatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String consultaIP(String contrato, String usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertaIP(String contrato, String usuario, String ip) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean actualizaIP(String contrato, String usuario, String ip, String estatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JBBMuser verificaBMUser(String contrato) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateBMUser(String contrato, String estatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBMUser(String contrato) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JBLogin getUserLogin(String UserIdJSP, String UserPassJSP) throws AfirmeNetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void defaultSetting(String userIdJSP, String groupId, List<Cuenta> cuentas, List<JBProCode> procode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JBProCode> getProcode(boolean bPaqueteSinToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lookupChangeParameter(String contrato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JBAvatar> getListAvatar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContrasenaDTO actualizaContrasena(ContrasenaDTO contrasenaDTO) throws AfirmeNetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean guardarRegistroEnDCCONFMSG(DC_CONFMSG confmsgdatabean) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean guardarRegistroEnDCLOG(String dttm, String entityID, String userID, String laction, String remark,
			String account, String amount, String currency, String typTran, String dcibsREF) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	
}
