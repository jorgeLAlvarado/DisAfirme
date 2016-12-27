package com.afirme.afirmenet.service.impl.transferencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.service.transferencia.ComprobanteTransferenciaService;

@Service
public class ComprobanteTransferenciaServiceImpl implements ComprobanteTransferenciaService{
	
	@Autowired
	private ComprobanteTransferenciaDao comprobanteTransferenciaDao;

	@Override
	public void insertaConfirmacionOperacion(Comprobante comprobante) {
		// TODO Auto-generated method stub
		
	}

	
}
