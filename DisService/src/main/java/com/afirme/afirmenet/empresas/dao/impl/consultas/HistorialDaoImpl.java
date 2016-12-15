package com.afirme.afirmenet.empresas.dao.impl.consultas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.empresas.dao.consultas.HistorialDao;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.utils.AfirmeNetLog;

/**
 * Controller que atiende peticiones de saldos y movimientos
 * 
 * 
 * @author Arturo Ivan Martinez Mata
 * @author epifanio.guzman@afirme.com
 * 
 * Modificado on dic 13, 2016 11:12:21 AM by Bayron 
 * 
 * @author Bayron Gamboa Martinez
 */

@Repository
public class HistorialDaoImpl implements HistorialDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(HistorialDaoImpl.class);
	
	@Autowired
	private DB2Dao db2Dao;
	
	/**
	 * metodo para pedir el historial
	 * @param esBasicoSinToken
	 * @return regresa las consultas basicas.
	 */
	public List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken) {
		
		return null;
	}

	/**
	 * metodo para pedir las categorias del historial
	 * @param esBasicoSinToken
	 * @return regresa las categorias.
	 */
	public List<String> categorias(boolean esBasicoSinToken) {
		
		return null;
	}

}
