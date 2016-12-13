package com.afirme.afirmenet.empresas.dao.impl.acceso;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.acceso.PasswordDao;
import com.afirme.afirmenet.ibs.messages.PWDDSJVMessage;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.socket.MessageContextFactory;
import com.afirme.afirmenet.utils.socket.SocketFactory;
import com.datapro.sockets.MessageContext;
import com.datapro.sockets.MessageRecord;

@Repository
public class PasswordDaoImpl implements PasswordDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(PasswordDaoImpl.class);
	
	@Override
	public Map<String, String> updatePassword(String idContrato, String password, String nuevoPassword) {
		
		
		return null;
	}

	@Override
	public boolean setPassword(String idContrato, String password) {
		
		return null;
	}

	@Override
	public void mailNotificacionCambioPwd(String idContrato) {
		
	}

	@Override
	public Map<String, String> solicitudCambioPwd(String idContrato, String nuevoPassword) {
		
		
		return null;
	}

	@Override
	public String validaPassword(String idContrato, String codigoToken) {
		
		return null;
	}
}
