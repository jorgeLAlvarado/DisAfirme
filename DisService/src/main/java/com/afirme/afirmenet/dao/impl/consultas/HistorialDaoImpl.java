package com.afirme.afirmenet.dao.impl.consultas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.consultas.HistorialDao;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.utils.AfirmeNetLog;
/**
 * clase para el historial de operaciones
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * Created on Created on Dic 10, 2016 3:50:05 PM by Jorge
 */
@Repository
public class HistorialDaoImpl implements HistorialDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(HistorialDaoImpl.class);


	/* (non-Javadoc)
	 * @see com.afirme.afirmenet.dao.consultas.HistorialDao#listaTransacciones(boolean)
	 */
	@Override
	public List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken) {

		try {

			LOG.info("Resultados");
		} catch (Exception e) {
			LOG.info("No es posible realizar");
			return null;
		}

		List<TipoTransaccion> result = new ArrayList<TipoTransaccion>();

		return result;
	}

	@Override
	public List<String> categorias(boolean esBasicoSinToken) {
		return null;
	}

}
