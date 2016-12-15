package com.afirme.afirmenet.empresas.service.impl.acceso;

import java.util.List;

import com.afirme.afirmenet.beas.login.Notificaciones;
import com.afirme.afirmenet.empresas.dao.acceso.ActividadesPendientesDao;
import com.afirme.afirmenet.empresas.service.acceso.ActividadesPendientesService;

/**
 * clase para consultar  notificaciones y autorizaciones pendientes
 *  * 
 * Created on Dic 14, 2016 5:39:05 PM by Selene
 * @author Selene Mena Quiñones
 * @version 1.0.0
 */
public class ActividadesPendientesServiceImpl implements ActividadesPendientesService{
	
	private ActividadesPendientesDao actividadesPendientesDao;

	public List<Notificaciones> getNotificacionesPendientes(String datos) {
		
		return null;
	}

	public List<Notificaciones> getAutorizacionesPendientes(String datos) {

		return null;
	}

}
