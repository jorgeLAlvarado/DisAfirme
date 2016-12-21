package com.afirme.afirmenet.service.token;

import com.afirme.afirmenet.model.base.TokenModel;

public interface BasicoSinTokenService {
	
	/** Metodo que valida las contraseñas para los paquetes basicos sin token
	 * @param passCode
	 * @param contrato
	 * @param usuario
	 * @param intentos
	 * @return
	 */
	public TokenModel validaClave(String passCode, String contrato, 
			String usuario, int intentos);

}
