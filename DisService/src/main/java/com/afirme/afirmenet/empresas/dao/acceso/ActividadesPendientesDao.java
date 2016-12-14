package com.afirme.afirmenet.empresas.dao.acceso;

import java.util.List;

import com.afirme.afirmenet.beas.login.Notificaciones;


public interface ActividadesPendientesDao {
	
	public List<Notificaciones> getnotifi(String datos);

}
