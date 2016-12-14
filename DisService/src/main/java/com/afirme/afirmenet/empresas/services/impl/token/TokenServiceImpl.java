package com.afirme.afirmenet.empresas.services.impl.token;

import java.math.BigInteger;
import java.util.List;

import com.afirme.afirmenet.empresas.dao.token.TokenDao;
import com.afirme.afirmenet.empresas.services.token.TokenService;
import com.afirme.afirmenet.model.token.Token;

@Service
public class TokenServiceImpl implements TokenService {

	
	@Autowired
	private TokenDao tokenDao; 
	
	public List<Token> getTokens(Boolean soloDisponibles){
		return tokenDao.getTokens(soloDisponibles);
	}

}

