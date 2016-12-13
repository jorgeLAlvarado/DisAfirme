package com.afirme.afirmenet.web.empresas.controller.consultasaldosmovimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.afirme.afirmenet.empresas.service.consultasaldosmovimientos.ConsultaSaldoMovimientoService;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;

@Controller
public class ConsultaSaldosMovimientoController {

	@Autowired
	private ConsultaSaldoMovimientoService consultaInversion;

	/**
	 * @param consultaCuenta
	 */
	public void Cuentas(@ModelAttribute("consultaCuenta") ConsultaSaldosMovimientos consultaCuenta) {

	}

	/**
	 * @param consultaCredito
	 */
	public void Credito(ConsultaSaldosMovimientosLC consultaCredito) {

	}

	/**
	 * @param consultaInversion
	 */
	public void inversion(ConsultaSaldosMovimientosInversiones consultaInversion) {

	}

	/**
	 * @param consultaDetalle
	 */
	public void DetalleMovimiento(ConsultaSaldosMovimientos consultaDetalle) {

	}

	/**
	 * @param prestamo
	 */
	public void Prestamo(ConsultaSaldosMovimientosLC prestamo) {

	}

	/**
	 * @param detalleProducto
	 */
	public void DetalleProducto(ConsultaSaldosMovimientosInversiones detalleProducto) {

	}

}
