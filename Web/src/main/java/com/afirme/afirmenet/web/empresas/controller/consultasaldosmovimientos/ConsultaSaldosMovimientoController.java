package com.afirme.afirmenet.web.empresas.controller.consultasaldosmovimientos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.afirme.afirmenet.empresas.service.consultasaldosmovimientos.ConsultaSaldoMovimientoService;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientos;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosInversiones;
import com.afirme.afirmenet.model.consultaSaldosMovimientos.ConsultaSaldosMovimientosLC;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

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
	@RequestMapping(value = "/consultas_resumen-de-mis-cuentas.htm")
	public String cuentas(@ModelAttribute("consultaCuenta") ConsultaSaldosMovimientos consultaCuenta) {
		
		LOG.info("<<cuentas()");
		
		ConsultaSaldosMovimientos estadoCuenta = new ConsultaSaldosMovimientos();
		estadoCuenta.setNombreCuenta(null);
		estadoCuenta.setCantidad(0);
		//List<EstadoCuenta> edoCuentaActivar = estadoCuentaService.getEdoCuentaService(estadoCuenta);
		List<ConsultaSaldosMovimientos> consulta = consultaSaldoMovimientoService.saldosMovimientos(estadoCuenta);
		
		LOG.info(">>cuentas()");
		return AfirmeNetWebConstants.MV_CONSULTA_DETALLE;

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
