package com.afirme.afirmenet.dao.consultas;

import java.util.List;

import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;

/**
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * Created on Created on Dic 10, 2016 3:50:05 PM by Jorge
 */
public interface HistorialDao {
	/**
	 * @param esBasicoSinToken
	 * @return
	 */
	List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken);
	/**
	 * @param esBasicoSinToken
	 * @return
	 */
	List<String> categorias(boolean esBasicoSinToken);
}
