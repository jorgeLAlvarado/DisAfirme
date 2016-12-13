package com.afirme.afirmenet.empresas.dao.configuraciones;

import java.util.List;

import com.afirme.afirmenet.beas.login.JBAvatar;




/**
 * @author Usuario
 *
 */
public interface AvatarDao {
	
	/**
	 * @param datos
	 * @return
	 */
	List<JBAvatar> getListAvatar(String datos); 
	
}
