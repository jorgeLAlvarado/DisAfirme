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
	
	/**consulta para obtener notificaciones pendientes
	 * @param notificaciones
	 * @return
	 */
	public List<Notificaciones> getNotificacionesPendientes (Boolean notificaciones);
	
	/**
	 * consulta para obtener autoriazaciones pendientes
	 * @param autorizaciones
	 * @return
	 */
	public List<Notificaciones> getAutorizacionesPendientes (Boolean autorizaciones);

}
