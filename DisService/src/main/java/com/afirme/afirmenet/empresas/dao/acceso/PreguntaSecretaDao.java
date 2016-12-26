package com.afirme.afirmenet.empresas.dao.acceso;

import java.util.List;
import java.util.Map;

import com.afirme.afirmenet.beas.login.PreguntaSecreta;
/**
 * clase de pregunta secreta
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * Created on Created on Dic 26, 2016 3:50:05 PM by Jorge
 */
public interface PreguntaSecretaDao {
	
	/**
	 * @param idContrato
	 * @return
	 */
	public List<String> getPregunta(String idContrato);
	/**
	 * @param idContrato
	 * @param idPregunta
	 * @return
	 */
	public String getPreguntaSecretaEncrypt(String idContrato, int idPregunta);
	/**
	 * @param idContrato
	 * @return
	 */
	public Map<String, String> getPregUsadas(String idContrato);
	/**
	 * @return
	 */
	public List<PreguntaSecreta> getListadoPreguntas();
	/**
	 * @param idContrato
	 * @param idPregunta
	 * @param pregunta
	 * @param respuesta
	 * @return
	 */
	/**
	 * @param idContrato
	 * @param idPregunta
	 * @param pregunta
	 * @param respuesta
	 * @return
	 */
	public boolean setPreguntaSecreta(String idContrato, int idPregunta, String pregunta, String respuesta);
	/**
	 * @param idContrato
	 * @param idPregunta
	 * @return
	 */
	public boolean guardaPreguntaUsada(String idContrato, int idPregunta);
}
