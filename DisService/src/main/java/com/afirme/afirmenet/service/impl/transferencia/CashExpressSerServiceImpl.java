package com.afirme.afirmenet.service.impl.transferencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.transferencia.CashExpressDao;
import com.afirme.afirmenet.model.base.CatalogoBase;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpress;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpressParametro;
import com.afirme.afirmenet.service.transferencia.CashExpressSerService;

@Service
public class CashExpressSerServiceImpl implements CashExpressSerService {

	 @Autowired
	private CashExpressDao cashExpressDao;
	@Override
	public List<CashExpress> buscarCashExpress(String contrato,
			String fechaDesde, String fechaHasta, String cuenta, String estado) {
		return cashExpressDao.buscarCashExpress(contrato, fechaDesde, fechaHasta, cuenta, estado);
	}
	@Override
	public void datosExtraInotr(CashExpress oOrden) {
		cashExpressDao.datosExtraInotr(oOrden);
		
	}
	@Override
	public CashExpressParametro obtenerParametro() {
		return cashExpressDao.obtenerParametro();
	}
	@Override
	public List<CatalogoBase> getEstados() {
		List<CatalogoBase> plazos= new ArrayList<CatalogoBase>();
		plazos.add(new CatalogoBase(" ", "Todos"));
		plazos.add(new CatalogoBase("1", "Pendiente de pagar"));
		plazos.add(new CatalogoBase("2", "Cobrada"));
		plazos.add(new CatalogoBase("3", "Cancelada/Vencida"));
		return plazos;
	}
	@Override
	public CashExpress seleccionar(List<CashExpress> lista, String id) {
		CashExpress sele=null;
		for (CashExpress comp : lista) {
			if (comp.getOrden().equals(id)) {
				sele=comp;
				break;
			}
		}
		return sele;
	}
	
	
}
