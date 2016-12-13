package com.afirme.afirmenet.empresas.serviceImpl.configuraciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.empresas.dao.configuraciones.AvatarDao;
import com.afirme.afirmenet.empresas.service.configuraciones.AvatarService;
import com.afirme.afirmenet.model.configuraciones.AliasAvatarDTO;

@Service
public class AvatarServiceImpl implements AvatarService {
	
	@Autowired
	private 
	AvatarDao mostrarAvatarDao;
	
	@Override
	public List<AliasAvatarDTO> getListAvatar(String datos){

		return mostrarAvatarDao.getListAvatar(datos);
		
	}

}
