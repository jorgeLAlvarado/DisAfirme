package com.afirme.afirmenet.empresas.service.acceso;

import java.util.List;

import com.afirme.afirmenet.beas.login.Notificaciones;
/**
 * clase para consultar  notificaciones y autorizaciones pendientes
 *  * 
 * Created on Dic 14, 2016 4:37:05 PM by Selene
 * @author Selene Mena Quiñones
 * @version 1.0.0
 */
public interface ActividadesPendientesService {
	
	
	public List<Notificaciones> getNotificacionesPendientes (Boolean notificaciones);
	
	public List<Notificaciones> getAutorizacionesPendientes (Boolean autorizaciones);

}
