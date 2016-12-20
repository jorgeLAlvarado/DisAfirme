package com.afirme.afirmenet.enums;

/**
 * Enumerador que almacena todas las configuraciones validas y almacenadas en el
 * mapa de configuraciones por aplicacion
 * 
 * @author jorge.canoc@gmail.com
 * 
 */
public enum ConfigPersonas {
	SOCKET_IP("ip"), 
	SOCKET_PUERTO("port1"), 
	SOCKET_PORT_SUMARY("portSummary"), 
	SOCKET_PUERTO_E("portE"), 
	SOCKET_TIMEOUT("timeout"),
	KEY_PRIVADA("rutaLLavePrivada"),
	KEY_PUBLICA("rutaLLavePublica"),
	KEY_BANCO("llaveBancaria"),
	KEY_BANCO_MD5("llaveBancoMD5"),
	JDBC_LIBNAME("libname"),
	CAMPANIAS_IMG_URL("urlForImages"),
	GENERAL_PAQ_SIN_TOKEN("paquetesSinToken"),
	RSA_OTP_END_POINT("OTPWSAddress"),
	INOUT("inout"),
	EMAIL_SERVER("emailserver"),
	EMAIL_MUEMAIL("muemail"),
	EMAIL_CCEMAIL("ccemail"),
	SSL_SOCKET_IP("sgip"), 
	SSL_SOCKET_PUERTO("sdPort"), 	
	SSL_CERNUM("cernum"),
	PAGARE_AHORRAFIRME_TIPO("tipoPagareAhorraAfirme"),
	PAGARE_AHORRAFIRME_CODIGO("codigoPagareAhorraAfirme"),
	PAGARE_GRADUAL_TIPO("tipoPagareGradual"),
	PAGARE_GRADUAL_CODIGO("codigoPagareGradual"),
	PAGARE_MULTIPLE_TIPO("tipoPagareMultiple"),
	PAGARE_MULTIPLE_CODIGO("codigoPagareMultiple"),
	WS_FRONT_ARENA("endPointWS_FrontArena"),
	WS_PIC_ENDPOINT("endPointWS_PIC"),
	PAGARE_GM_MIN("pagareGMMin"),
	PAGARE_GM_MAX("pagareGMMax"),
	INVERSION_PERFECTA_TIPO("tipoInversionPerfecta"),
	INVERSION_PERFECTA_CODIGO("codigoInversionPerfecta"),
	INVERSION_PERFECTA_USUARIO_OPERADOR("invPerfectaUsuarioB"),
	PAGARE_MIN("pagareMin"),
	PAGARE_MAX_PATRI("pagareMaxPatri"),
	PAGARE_MAX("pagareMax"),
	TOKEN_ENABLED("tokenEnabled"),
	AVATAR_PATH("avatarPath"),
	AVATAR_DEFAULT("avatarDefault"),
	PORTAL_AFIRME("PortalAfirme"),
	DOMINIO("dominio"),
	ID_BANCO_AFIRME("62"),
	DESC_BANCO_AFIRME("BANCA AFIRME"),
	IR_PORTAL("IrPortal"),
	USUARIO_AFI("usuarioAFIValores"),
	WS_EDO_CTA_CONSULTA("urlWebServiceEdoCuenta"),
	EDO_CUENTA_IMG("EDO_CUENTA_IMG"),
	WEB_APP_EMPRESAS("WebAppCTREmpresas"),
	CEP_LLAVE_SIMETRICA("cep.llavesimetrica"),
	CEP_SERIE("cep.serie"),
	CEP_URL("cep.url"),
	CORREO_FRAUDES("correoFraude"),
	URL_VERSION_CLASICA("urlClasico"),
	EMAIL_PORT("mailPort"),
	EMAIL_TSLPROTOCOLE("mailTLSProtocol");

	ConfigPersonas(String valor) {
		this.valor = valor;
	}

	private String valor;

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}
