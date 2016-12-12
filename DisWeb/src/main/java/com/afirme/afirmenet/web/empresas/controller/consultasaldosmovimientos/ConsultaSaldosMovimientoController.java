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

	public void Cuentas(@ModelAttribute("consultaCuenta") ConsultaSaldosMovimientos consultaCuenta) {

	}

	public void Credito(ConsultaSaldosMovimientosLC consultaCredito) {

	}

	public void inversion(ConsultaSaldosMovimientosInversiones consultaInversion) {

	}

	public void DetalleMovimiento(ConsultaSaldosMovimientos consultaDetalle) {

	}

	public void Prestamo(ConsultaSaldosMovimientosLC prestamo) {

	}

	public void DetalleProducto(ConsultaSaldosMovimientosInversiones detalleProducto) {

	}

}
