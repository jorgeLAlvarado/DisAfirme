package com.afirme.afirmenet.empresas.service.log;

import com.afirme.afirmenet.ibs.beans.JBLogList;

public interface LogService {

	/**
	 * Devuelve el ultimo login en un bean
	 * @param contrato
	 */
	
	public JBLogList getLastLoginPer(String contrato);

	/**
	 * Inserta acceso a Afirmenet
	 */
	public void getAddLogPer(String DTTM,String USERID,String LACTION,String REMARK,
			String ACCOUNT,String AMOUNT,String CURRENCY,String TYP_TRAN,String DCIBS_REF);

	
}
