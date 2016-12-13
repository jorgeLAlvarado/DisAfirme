package com.afirme.afirmenet.empresas.dao.consultamovimientoinversiones;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;

public interface ConsultaSaldosMovimientoInversionesDao {
	
/**
 * @param consultaInversion
 */
public void consultaInversion(List<ConsultaSaldosMovimientosInversiones> consultaInversion);
	
/**
 * @param detalleProducto
 */
public void detalleProducto(List<ConsultaSaldosMovimientosInversiones> detalleProducto);
	
	

}
