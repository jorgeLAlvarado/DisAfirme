package com.afirme.afirmenet.dao.impl.consultas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.consultas.HistorialDao;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.utils.AfirmeNetLog;

@Repository
public class HistorialDaoImpl implements HistorialDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(HistorialDaoImpl.class);


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
