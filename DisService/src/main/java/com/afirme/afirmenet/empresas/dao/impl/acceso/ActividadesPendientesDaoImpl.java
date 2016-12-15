package com.afirme.afirmenet.empresas.dao.impl.acceso;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.beas.login.Notificaciones;
import com.afirme.afirmenet.empresas.dao.acceso.ActividadesPendientesDao;
/**
 * clase para consultar  notificaciones y autorizaciones pendientes
 *  * 
 * @author Selene Mena
 *
 * @version 1.0.0
 */
@Repository
public class ActividadesPendientesDaoImpl implements ActividadesPendientesDao{
	
	
	public List<Notificaciones> getNotificacionesPendientes (String datos){
		
		return null;
	}
	public List<Notificaciones> getAutorizacionesPendientes (String datos){
		
		return null;
	}

}
