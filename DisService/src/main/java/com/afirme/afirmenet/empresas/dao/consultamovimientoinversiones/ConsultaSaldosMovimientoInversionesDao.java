package com.afirme.afirmenet.empresas.dao.consultamovimientoinversiones;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;

/**
 * @author Noe
 *
 */
public interface ConsultaSaldosMovimientoInversionesDao {
	
/**
 * @param consultaInversion
 */
public String consultaInversion(List<ConsultaSaldosMovimientosInversiones> consultaInversion);
	
/**
 * @param detalleProducto
 */
public String detalleProducto(List<ConsultaSaldosMovimientosInversiones> detalleProducto);
	
	

}
