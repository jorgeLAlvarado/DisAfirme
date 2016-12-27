package com.afirme.afirmenet.empresas.service.token;

import com.afirme.afirmenet.model.base.TokenModel;

/**
 * clase para validar el ingreso sin token
 * 
 * @author Bayron Gamboa Martinez
 *	@since 13/12/2016
 *
 * @version 1.0.2
 */
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
