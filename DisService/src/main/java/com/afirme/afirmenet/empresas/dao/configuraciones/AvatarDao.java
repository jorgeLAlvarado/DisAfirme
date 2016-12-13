package com.afirme.afirmenet.empresas.dao.configuraciones;

import java.util.List;

import com.afirme.afirmenet.model.configuraciones.AliasAvatarDTO;




/**
 * @author Usuario
 *
 */
public interface AvatarDao {
	
	/**
	 * @param datos
	 * @return
	 */
	List<AliasAvatarDTO> getListAvatar(String datos); 
	
}
