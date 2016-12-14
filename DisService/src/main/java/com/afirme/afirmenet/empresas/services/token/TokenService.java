package com.afirme.afirmenet.empresas.services.token;
import java.util.List;

import com.afirme.afirmenet.model.token.Token;

public interface TokenService {

	List<Token> getTokens(Boolean soloDisponibles);
	
}
