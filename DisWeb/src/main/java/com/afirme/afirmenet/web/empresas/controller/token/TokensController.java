package com.afirme.afirmenet.web.empresas.controller.token;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.afirme.afirmenet.empresas.services.token.TokenService;
import com.afirme.afirmenet.model.token.Token;
import com.afirme.afirmenet.web.empresas.controller.base.BaseController;



@Controller
@RequestMapping("/tokensDisponibles")
public class TokensController extends BaseController{
	
	
	
	@Autowired
	private TokenService tokenService;

	List<Token> obtenerTokensDisponibles(){
		return tokenService.getTokens(true);
	}
	
}
