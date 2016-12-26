package com.afirme.afirmenet.service.consultas;

import java.util.List;

import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;

public interface HistorialService {
	List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken);
	List<String> categorias(boolean esBasicoSinToken);
	void obtenerInformacionExtra(TransferenciaBase comprobante);
}
