package com.afirme.afirmenet.empresas.dao.impl.acceso;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.acceso.FinDiaDao;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.messages.EODDSJVMessage;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.socket.MessageContextFactory;
import com.afirme.afirmenet.utils.socket.SocketFactory;
import com.datapro.sockets.MessageContext;
import com.datapro.sockets.MessageRecord;

@Repository
public class FinDiaDaoImpl implements FinDiaDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(FinDiaDaoImpl.class);

	@Override
	public String getFinDia() {
		

		return null;
	}

	@Override
	public void valActivSocket() {
		
		return null;
	}

}
