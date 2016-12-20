package com.afirme.afirmenet.web.empresas.controller.consultasaldosmovimientos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afirme.afirmenet.empresas.service.consultasaldosmovimientos.ConsultaSaldoMovimientoService;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;
import com.afirme.afirmenet.web.empresas.controller.TDC.ConsultaTDCController;

/**
 * @author Noe Galarza
 *
 * Modificado on dic 14, 2016 3:12:21 PM by Noe Galarza
 * @version 1.0.0
 */
@Controller
public class ConsultaSaldosMovimientoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaSaldosMovimientoController.class); 

	@Autowired
	private ConsultaSaldoMovimientoService consultaInversion;

	/**
	 * @param consultaCuenta
	 */
	@RequestMapping(value = "/consultacuenta.htm")
	public String Cuentas(@ModelAttribute("consultaCuenta") ConsultaSaldosMovimientos consultaCuenta) {
		
		LOGGER.debug("Funciona este metodo consultaCuenta");
		return null;

	}

	/**
	 * @param consultaCredito
	 */
	@RequestMapping(value = "/consultacredito.htm")
	public String Credito(ConsultaSaldosMovimientosLC consultaCredito) {
		LOGGER.debug("Funciona este metodo consultaCredito");
		return null;

	}

	/**
	 * @param consultaInversion
	 */
	@RequestMapping(value = "/consultaInversion.htm")
	public String inversion(ConsultaSaldosMovimientosInversiones consultaInversion) {
		LOGGER.debug("Funciona este metodo consultaInversion");
		return null;

	}

	/**
	 * @param consultaDetalle
	 */
	@RequestMapping(value = "/consultaDetalle.htm")
	public String DetalleMovimiento(ConsultaSaldosMovimientos consultaDetalle) {
		LOGGER.debug("Funciona este metodo consultaDetalle");
		return null;

	}

	/**
	 * @param prestamo
	 */
	@RequestMapping(value = "/Prestamo.htm")
	public String Prestamo(ConsultaSaldosMovimientosLC prestamo) {
		LOGGER.debug("Funciona este metodo prestamo");
		return null;

	}

	/**
	 * @param detalleProducto
	 */
	@RequestMapping(value = "/detalleProducto.htm")
	public String DetalleProducto(ConsultaSaldosMovimientosInversiones detalleProducto) {
		LOGGER.debug("Funciona este metodo detalleProducto");
		return null;

	}

}
