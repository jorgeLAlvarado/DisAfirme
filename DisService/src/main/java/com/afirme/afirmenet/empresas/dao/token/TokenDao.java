package com.afirme.afirmenet.empresas.dao.token;
import java.util.List;

import com.afirme.afirmenet.model.token.Token;

public interface TokenDao {

	List<Token> getTokens(Boolean soloDisponibles);
}
