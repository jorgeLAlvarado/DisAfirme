package com.afirme.afirmenet.empresas.dao.acceso;

import java.util.List;

import com.afirme.afirmenet.beas.login.Notificaciones;

/**
 * clase para consultar  notificaciones y autorizaciones pendientes
 *  * 
 * Created on Dic 14, 2016 3:39:05 PM by Selene
 * @author Selene Mena Quiñones
 * @version 1.0.0

 */
public interface ActividadesPendientesDao {
	
	public List<Notificaciones> getNotificacionesPendientes (String datos);
	
	public List<Notificaciones> getAutorizacionesPendientes (String datos);

}
