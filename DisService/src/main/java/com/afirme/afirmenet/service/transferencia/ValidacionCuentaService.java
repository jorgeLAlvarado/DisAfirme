package com.afirme.afirmenet.service.transferencia;

import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;

public interface ValidacionCuentaService {

	public boolean getValCLABE(String ACC, String BNK);
	
	public boolean validacionAltaCuentaTerceros(ACCTHIRDUSER oCuenta, boolean esDomingoElectronico);
	
	public boolean validacionAltaNacionales(ACCTHIRDUSER oCuenta);
	
	public boolean validacionAltaTarjetas(DC_CONVENIO oTarjeta) ;
}
