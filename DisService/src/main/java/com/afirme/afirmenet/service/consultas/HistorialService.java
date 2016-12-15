package com.afirme.afirmenet.service.consultas;

import java.util.Date;
import java.util.List;

import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;

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

public interface HistorialService  {
	
	List<TransferenciaBase> buscaTransferencias(String contrtato, List<Cuenta> tipo, Date fechaDesde, Date fechaHasta);
	List<TransferenciaBase> buscaTransferencias(String contrtato, List<Cuenta> cuentas, HistorialTipo tipo, Date fechaDesde, Date fechaHasta);
}
