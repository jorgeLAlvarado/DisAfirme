package com.afirme.afirmenet.utils.socket;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import com.afirme.afirmenet.utils.AfirmeNetLog;


import com.afirme.afirmenet.exception.SocketFactoryException;
import com.afirme.afirmenet.utils.AfirmeNetConstants;

/**
 * Clase Factory que provee conexiones de tipo {@link Socket}, si ocurre un
 * error durante la generacion de la conexion se lanzara una {@link Exception}
 * de tipo {@link SocketFactoryException}.
 * 
 * 
 * @author jorge.canoc@gmail.com
 * 
 */

public class SocketFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final AfirmeNetLog LOG = new AfirmeNetLog(SocketFactory.class);

	public static String UNKNOWNHOST = "E001";
	public static String NULL_SOCKET = "E002";

	/**
	 * Metodo estatico que provee una conexion de tipo {@link Socket}
	 * proporcionando atributos generales como IP, Puerto y Puerto summary
	 * 
	 * @param IP
	 * @param PORT
	 * @param PORT_SUMMARY
	 * @return
	 */
	public static Socket getSocket(String IP, int PORT) {
		return getSocket(IP, PORT, 0, AfirmeNetConstants.SOCKET_TIMEOUT);
	}

	/**
	 * Metodo estatico que provee una conexion de tipo {@link Socket}
	 * proporcionando atributos generales como IP, Puerto y Puerto summary
	 * 
	 * @param IP
	 * @param PORT
	 * @param PORT_SUMMARY
	 * @return
	 */
	public static Socket getSocket(String IP, int PORT, int PORT_SUMMARY) {
		return getSocket(IP, PORT, PORT_SUMMARY, AfirmeNetConstants.SOCKET_TIMEOUT);
	}

	/**
	 * Metodo estatico que provee una conexion de tipo {@link Socket} mediante
	 * la obtencion de atributos default
	 * 
	 */
	public static Socket getDefaultSocket() {
		return getSocket(AfirmeNetConstants.SOCKET_IP, AfirmeNetConstants.SOCKET_PUERTO, AfirmeNetConstants.SOCKET_PORT_SUMARY);
	}

	/**
	 * Metodo estatico que provee una conexion de tipo {@link Socket}
	 * proporcionando atributos generales como IP, Puerto, Puerto summary y
	 * TimeOut
	 * 
	 * @param IP
	 * @param PORT
	 * @param PORT_SUMMARY
	 * @param TIME_OUT
	 * @return
	 */
	public static Socket getSocket(String IP, int PORT, int PORT_SUMMARY,
			int TIME_OUT) {
		Socket socket = null;
		String errorCode = null;
		try {
			socket = new Socket(IP, PORT + PORT_SUMMARY);
			socket.setSoTimeout(TIME_OUT);
		} catch (UnknownHostException e) {
			LOG.error("Error al realizar la conexion via socket", e);
			errorCode = UNKNOWNHOST;
		} catch (IOException e) {
			LOG.error("Error al realizar la conexion via socket", e);
			errorCode = NULL_SOCKET;
		}

		if (socket == null) {
			throw new SocketFactoryException(
					"Error al generar la conexion con el socket, el socket retorno nulo",
					errorCode);
		}

		if (errorCode != null) {
			throw new SocketFactoryException(
					"Error al generar la conexion con el socket", errorCode);
		}

		return socket;

	}

}
