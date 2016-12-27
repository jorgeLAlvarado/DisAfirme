package com.afirme.afirmenet.empresas.service.token;

import java.util.List;

import com.afirme.afirmenet.model.base.TokenModel;

/**
 * clase para validar el ingreso con un token previamente asignado
 * 
 * @author Bayron Gamboa Martinez
 *	@since 13/12/2016
 *
 * @version 1.0.2
 */

public interface TokenService {
	
	/**
	 * Metodo que evalua la clave dinamica o password segun el tipo de contrato
	 * 
	 * @param passCode
	 * @param contrato
	 * @param usuario
	 * @param basicoSinToken
	 * @param tokenRSA
	 */

	public TokenModel validaClave(String passCode, String contrato, 
			String usuario, boolean basicoSinToken, List<?> tokenRSA, int intentos);
	
	/**
	 * validacion token del usuario
	 * @param passCode
	 * @param contrato
	 * @param usuario
	 * @param basicoSinToken
	 * @param tokenRSA
	 * @param intentos
	 * @return
	 */
	public TokenModel validaPasscodeLogin(String passCode, String contrato, String usuario, boolean basicoSinToken,
			List<?> tokenRSA, int intentos);

}
