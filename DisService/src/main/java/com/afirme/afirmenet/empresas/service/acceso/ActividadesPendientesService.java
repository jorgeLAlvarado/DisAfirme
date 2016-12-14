package com.afirme.afirmenet.empresas.service.acceso;

import java.util.List;

import com.afirme.afirmenet.beas.login.Notificaciones;
/**
 * clase para consultar  notificaciones y autorizaciones pendientes
 *  * 
 * @author Selene Mena
 *
 * @version 1.0.0
 */
public interface ActividadesPendientesService {
	
	
	public List<Notificaciones> getNotificacionesPendientes (String datos);
	
	public List<Notificaciones> getAutorizacionesPendientes (String datos);

}
