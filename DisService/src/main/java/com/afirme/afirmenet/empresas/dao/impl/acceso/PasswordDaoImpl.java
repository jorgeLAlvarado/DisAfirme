package com.afirme.afirmenet.empresas.dao.impl.acceso;



@Repository
public class PasswordDaoImpl implements PasswordDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(PasswordDaoImpl.class);
	
	@Override
	public Map<String, String> updatePassword(String idContrato, String password, String nuevoPassword) {
		
		
		return null;
	}

	@Override
	public boolean setPassword(String idContrato, String password) {
		
		return null;
	}

	@Override
	public void mailNotificacionCambioPwd(String idContrato) {
		
	}

	@Override
	public Map<String, String> solicitudCambioPwd(String idContrato, String nuevoPassword) {
		
		
		return null;
	}

	@Override
	public String validaPassword(String idContrato, String codigoToken) {
		
		return null;
	}
}
