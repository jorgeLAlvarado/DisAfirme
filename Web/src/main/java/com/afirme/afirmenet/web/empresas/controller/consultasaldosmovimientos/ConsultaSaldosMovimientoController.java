package com.afirme.afirmenet.web.empresas.controller.consultasaldosmovimientos;

import java.util.List;

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
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

/**
 * Controller para generar las consultas. <br>
 * <br>
 * 
 * @author Noe Galarza
 *
 *         Modificado on dic 14, 2016 3:12:21 PM by Noe Galarza
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/consulta_saldos_movimentos",method = { RequestMethod.POST,
		RequestMethod.GET })
public class ConsultaSaldosMovimientoController {

	static final AfirmeNetLog LOG = new AfirmeNetLog(ConsultaSaldosMovimientoController.class);

	@Autowired
	private ConsultaSaldoMovimientoService consultaSaldoMovimientoService;

	/**
	 * Consulta de cuenta
	 * 
	 * @param consultaCuenta
	 * @return AfirmeNetWebConstants.MV_CONSULTA_DETALLE
	 */
	@RequestMapping(value = "/consultas_rdmc_detalle.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String cuentas(@ModelAttribute("consultaCuenta") ConsultaSaldosMovimientos consultaCuenta) {

		LOG.info("<< cuentas()");

		ConsultaSaldosMovimientos consultaMovimiento = new ConsultaSaldosMovimientos();
		consultaMovimiento.setNombreCuenta(null);
		consultaMovimiento.setCantidad(0);
		List<ConsultaSaldosMovimientos> consulta = consultaSaldoMovimientoService.consultaSaldos(consultaMovimiento);

		LOG.info(">> cuentas()");
		return AfirmeNetWebConstants.MV_CONSULTA_DETALLE;

	}

	/**
	 * Consulta de credito
	 * 
	 * @param consultaCredito
	 * @return AfirmeNetWebConstants.PRUEBA
	 */

	@RequestMapping(value = "/consulta_credito.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String credito(ConsultaSaldosMovimientosLC consultaCredito) {
		LOG.info("<< credito()");
		ConsultaSaldosMovimientosLC consultaMovimiento = new ConsultaSaldosMovimientosLC();
		consultaMovimiento.setNombreCuenta(null);
		consultaMovimiento.setCantidad(0);
		List<ConsultaSaldosMovimientosLC> consulta = consultaSaldoMovimientoService.cuentasLC(consultaMovimiento);
		LOG.info(">> credito()");
		return AfirmeNetWebConstants.MV_CONSULTA_CREDITO ;

	}

	/**
	 * Consulta de inversion
	 * 
	 * @param consultaInversion
	 * @return AfirmeNetWebConstants.PRUEBA
	 */
	@RequestMapping(value = "/consulta_inversion.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String inversion(ConsultaSaldosMovimientosInversiones consultaInversion) {
		LOG.info("<< inversion()");
		ConsultaSaldosMovimientosInversiones consultaMovimiento = new ConsultaSaldosMovimientosInversiones();
		consultaMovimiento.setNombreCuenta(null);
		consultaMovimiento.setCantidad(0);
		List<ConsultaSaldosMovimientosInversiones> consulta = consultaSaldoMovimientoService
				.consultaInversion(consultaMovimiento);
		LOG.info(">> inversion()");

		return AfirmeNetWebConstants.MV_CONSULTA_INVERSION;

	}

	/**
	 * Consulta de Detalle
	 * 
	 * @param consultaDetalle
	 * @return AfirmeNetWebConstants.PRUEBA
	 */
	@RequestMapping(value = "/consulta_detalle_movimiento.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String detalleMovimiento(ConsultaSaldosMovimientos consultaDetalle) {
		LOG.info("<< detalleMovimiento()");
		ConsultaSaldosMovimientos consultaMovimiento = new ConsultaSaldosMovimientos();
		consultaMovimiento.setNombreCuenta(null);
		consultaMovimiento.setCantidad(0);
		List<ConsultaSaldosMovimientos> consulta = consultaSaldoMovimientoService.movimientosMes(consultaMovimiento);
		LOG.info(">> detalleMovimiento()");
		return AfirmeNetWebConstants.MV_CONSULTA_DETALLE_MOVIMIENTO;

	}

	/**
	 * Consulta de prestamo
	 * 
	 * @param prestamo
	 * @return AfirmeNetWebConstants.PRUEBA
	 */
	@RequestMapping(value = "/consulta_prestamo.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String prestamo(ConsultaSaldosMovimientosLC prestamo) {
		LOG.info("<< prestamo()");
		ConsultaSaldosMovimientosLC consultaMovimiento = new ConsultaSaldosMovimientosLC();
		consultaMovimiento.setNombreCuenta(null);
		consultaMovimiento.setCantidad(0);
		List<ConsultaSaldosMovimientosLC> consulta = consultaSaldoMovimientoService
				.informacionPrestamo(consultaMovimiento);
		LOG.info(">> prestamo()");
		return AfirmeNetWebConstants.MV_CONSULTA_PRESTAMO;

	}

	/**
	 * Consulta de detalle producto
	 * 
	 * @param detalleProducto
	 * @return AfirmeNetWebConstants.PRUEBA
	 */
	@RequestMapping(value = "/consulta_detalle_producto.htm",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String detalleProducto(ConsultaSaldosMovimientosInversiones detalleProducto) {
		LOG.info("<< detalleProducto()");
		ConsultaSaldosMovimientosInversiones consultaMovimiento = new ConsultaSaldosMovimientosInversiones();
		consultaMovimiento.setNombreCuenta(null);
		consultaMovimiento.setCantidad(0);
		List<ConsultaSaldosMovimientosInversiones> consulta = consultaSaldoMovimientoService
				.detalleProducto(consultaMovimiento);
		LOG.info(">> detalleProducto()");
		return AfirmeNetWebConstants.MV_CONSULTA_DETALLE_PRODUCTO;

	}

}
