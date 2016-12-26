package com.afirme.afirmenet.dao.consultas;

import java.util.List;

import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;

public interface HistorialDao {
	List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken);
	List<String> categorias(boolean esBasicoSinToken);
}
