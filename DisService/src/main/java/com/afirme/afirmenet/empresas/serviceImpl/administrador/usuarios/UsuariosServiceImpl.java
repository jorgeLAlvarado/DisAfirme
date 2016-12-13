package com.afirme.afirmenet.empresas.serviceImpl.administrador.usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.administrador.usuarios.UsuariosDao;
import com.afirme.afirmenet.empresas.service.administrador.usuarios.UsuariosService;
import com.afirme.afirmenet.model.administrador.usuarios.Usuarios;




@Service
public  class UsuariosServiceImpl implements UsuariosService {
	
	
	@Autowired
	private UsuariosDao listaUsuariosDao;
	
	
	public List<Usuarios> getdatosUsuario (String datos ){
		return listaUsuariosDao.getdatosUsuario(datos);
	

    }



}