package com.afirme.afirmenet.empresas.dao.consultamovimientoinversiones;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;

public interface ConsultaSaldosMovimientoInversionesDao {
	
public void consultaInversion(List<ConsultaSaldosMovimientosInversiones> consultaInversion);
	
public void detalleProducto(List<ConsultaSaldosMovimientosInversiones> detalleProducto);
	
	

}
