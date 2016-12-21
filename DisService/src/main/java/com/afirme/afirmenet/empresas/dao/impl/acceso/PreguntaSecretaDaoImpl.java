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

@Repository
public class PreguntaSecretaDaoImpl implements PreguntaSecretaDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(PreguntaSecretaDaoImpl.class);

	

	/**
	 * Da la pregunta secreta
	 * @param contacto
	 * @return
	 */
	@Override
	public List<String> getPregunta(String idContrato) {
		
		return null;
	}


	/**
	 * Da la cuenta del Contacto
	 * @param contacto
	 * @return
	 */
	@Override
	public String getPreguntaSecretaEncrypt(String idContrato, int idPregunta) {
		
		
		return null;
	}

	/**
	 * Mapeae de la pregunta secreta
	 * @param contacto
	 * @return
	 */
	@Override
	public Map<String, String> getPregUsadas(String idContrato) {
		
		
		return null;
	}

	/**
	 * Da una lista de la preguntas secretas
	 * @param contacto
	 * @return
	 */
	@Override
	public List<PreguntaSecreta> getListadoPreguntas() {
		
		return null;
	}

	/**
	 * da resupesta de confirmacion de la pregunta secreta
	 * @param contacto
	 * @return
	 */
	@Override
	public boolean setPreguntaSecreta(String idContrato, int idPregunta, String pregunta, String respuesta) {
		
		
		return false;
	}

	/**
	 * guarda la pregunta secreta del contacto
	 * @param contacto
	 * @return
	 */
	@Override
	public boolean guardaPreguntaUsada(String idContrato, int idPregunta) {
		
		
		return false;
	}
	
}