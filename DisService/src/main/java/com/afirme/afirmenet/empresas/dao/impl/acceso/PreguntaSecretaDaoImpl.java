package com.afirme.afirmenet.empresas.dao.impl.acceso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;


import com.afirme.afirmenet.empresas.dao.acceso.PreguntaSecretaDao;
import com.afirme.afirmenet.beas.login.PreguntaSecreta;

/**
 * clase para validar la pregunta secreta
 * 
 * @author Mayra Selene Mena Qui�ones
 *
 * @version 1.0.0
 */
@Repository
public class PreguntaSecretaDaoImpl implements PreguntaSecretaDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(PreguntaSecretaDaoImpl.class);

	


	@Override
	public List<String> getPregunta(String idContrato) {
		
		return null;
	}



	@Override
	public String getPreguntaSecretaEncrypt(String idContrato, int idPregunta) {
		
		
		return null;
	}


	@Override
	public Map<String, String> getPregUsadas(String idContrato) {
		
		
		return null;
	}


	@Override
	public List<PreguntaSecreta> getListadoPreguntas() {
		
		return null;
	}


	@Override
	public boolean setPreguntaSecreta(String idContrato, int idPregunta, String pregunta, String respuesta) {
		
		
		return false;
	}


	@Override
	public boolean guardaPreguntaUsada(String idContrato, int idPregunta) {
		
		
		return false;
	}
	
}