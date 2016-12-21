package com.afirme.afirmenet.service.token;

import java.util.List;

import com.afirme.afirmenet.model.base.TokenModel;



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
	
//	public void ejecutaSincronizacionToken(String Path, HttpServletRequest req, HttpServletResponse resp, HttpSession session);
//    
//	public void ejecutaActivaciónToken(String Path, HttpServletRequest req, HttpServletResponse resp, HttpSession session);
//	
//	public ArrayList verificaPasscode(String userID, String passcode);
//	
   public TokenModel validaPasscodeLogin(String passCode, String contrato, 
			String usuario, boolean basicoSinToken, List<?> tokenRSA, int intentos);
//
//	public void validaPasscodeLoginInit(String Path, HttpServletRequest req, 
//										HttpServletResponse resp, HttpSession session);
//	
//	public void validaPasscodeLoginAlias(String Path, HttpServletRequest req, HttpServletResponse resp, 
//										 HttpSession session);
//    	
//	public void validaPasscodeTransaccion(String Path, HttpServletRequest req, HttpServletResponse resp, HttpSession session);
//    
//	public void validaPasscodeLoginAliasPost(String Path, HttpServletRequest req, HttpServletResponse resp, HttpSession session, String LNG);
//    

}
