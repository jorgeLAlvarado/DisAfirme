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
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.acceso.ControlAcceso;
import com.afirme.afirmenet.web.empresas.controller.TDC.ConsultaTDCController;

/**
 * Controller para generar las consultas.
 * <br><br>
 * @author Noe Galarza
 *
 * Modificado on dic 14, 2016 3:12:21 PM by Noe Galarza
 * @version 1.0.0
 */
@Controller
@RequestMapping(value= "/consulta_saldos_movimentos")
public class ConsultaSaldosMovimientoController {
	
	static final AfirmeNetLog LOG = new AfirmeNetLog(ConsultaSaldosMovimientoController.class);

	@Autowired
	private ConsultaSaldoMovimientoService consultaSaldoMovimientoService;

	
	/**
	 * Consulta de cuenta
	 * @param consultaCuenta
	 * @return
	 */
	@RequestMapping(value = "/consulta_cuenta.htm")
	public String cuentas(@ModelAttribute("consultaCuenta") ConsultaSaldosMovimientos consultaCuenta) {
		
		LOG.info("<<cuentas()");
		LOG.info(">>cuentas()");
		return null;

	}

	/**
	 * Consulta de credito
	 * @param consultaCredito
	 */
	@RequestMapping(value = "/consulta_credito.htm")
	public String credito(ConsultaSaldosMovimientosLC consultaCredito) {
		LOG.info("<<credito()");
		LOG.info(">>credito()");
		return null;

	}

	/**
	 * Consulta de inversion
	 * @param consultaInversion
	 */
	@RequestMapping(value = "/consulta_inversion.htm")
	public String inversion(ConsultaSaldosMovimientosInversiones consultaInversion) {
		LOG.info("<<inversion()");
		LOG.info(">>inversion()");
		
		return null;

	}

	/**
	 * Consulta de Detalle
	 * @param consultaDetalle
	 */
	@RequestMapping(value = "/consulta_detalle.htm")
	public String detalleMovimiento(ConsultaSaldosMovimientos consultaDetalle) {
		LOG.info("<<detalleMovimiento()");
		LOG.info(">>detalleMovimiento()");
		return null;

	}

	/**
	 * Consulta de prestamo
	 * @param prestamo
	 */
	@RequestMapping(value = "/prestamo.htm")
	public String prestamo(ConsultaSaldosMovimientosLC prestamo) {
		LOG.info("<<prestamo()");
		LOG.info(">>prestamo()");
		return null;

	}

	/**
	 * Consulta de prestamo
	 * @param detalleProducto
	 */
	@RequestMapping(value = "/detalle_producto.htm")
	public String detalleProducto(ConsultaSaldosMovimientosInversiones detalleProducto) {
		LOG.info("<<detalleProducto()");
		LOG.info(">>detalleProducto()");
		return null;

	}

}
