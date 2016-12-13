package com.afirme.afirmenet.empresas.service.configuraciones;

import java.util.List;

import com.afirme.afirmenet.model.configuraciones.AliasAvatarDTO;

/**
 * @author Usuario
 *
 */
public interface AvatarService {
	
	public List<AliasAvatarDTO> getListAvatar(String datos);

}
