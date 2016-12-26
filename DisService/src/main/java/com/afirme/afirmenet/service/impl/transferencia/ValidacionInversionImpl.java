package com.afirme.afirmenet.service.impl.transferencia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.transferencia.ValidacionInversion;
import com.afirme.afirmenet.utils.AfirmeNetConstants;

@Service
public class ValidacionInversionImpl implements ValidacionInversion{

	@Override
	public void sonInversionesDiairiasValidas(
			List<TransferenciaBase> inversiones) throws AfirmeNetException {
		for (TransferenciaBase inversion : inversiones) {
			esInversionDiairiaValida(inversion);
		}
		
	}

	@Override
	public void esInversionDiairiaValida(TransferenciaBase inversion)
			throws AfirmeNetException {
		Map<CodigoExcepcion, String> errores = new HashMap<CodigoExcepcion, String>(
				0);
		//Revisamos la linea de captura

	      String DEBACC = inversion.getDebitAccount();
	      String CREACC = inversion.getCreditAccount();
	      boolean acerror = false;
	      String ACCSTR = DEBACC.substring(0, 1);

	      if(DEBACC.startsWith(ACCSTR) && CREACC.startsWith(ACCSTR))
	      {
	         acerror = true;
	      }

	      if(DEBACC.startsWith("8") && CREACC.startsWith("1"))
	      {
	         acerror = true;
	      }

	      if(DEBACC.startsWith("1") && CREACC.startsWith("8"))
	      {
	         acerror = true;
	      }

	      if(acerror)
	      {
	    	  errores.put(CodigoExcepcion.ERR_2919, AfirmeNetConstants.errores
						.get(CodigoExcepcion.ERR_2919.getValue()));
	      }
		
		if (!errores.isEmpty()) {
			inversion.setErrores(errores);
		}
		
	}

}
