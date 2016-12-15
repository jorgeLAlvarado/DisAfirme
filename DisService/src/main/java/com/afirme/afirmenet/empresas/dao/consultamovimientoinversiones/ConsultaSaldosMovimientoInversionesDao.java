package com.afirme.afirmenet.empresas.dao.consultamovimientoinversiones;

import java.util.List;

import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;

/**
 * @author Noe Galarza
 *
 * Modificado on dic 14, 2016 3:12:21 PM by Noe Galarza
 * @version 1.0.0
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
