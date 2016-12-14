package com.afirme.afirmenet.dao.consultas;

import java.util.List;

import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;

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

public interface HistorialDao {
	List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken);
	List<String> categorias(boolean esBasicoSinToken);
}
