package com.afirme.afirmenet.web.empresas.controller.consultas;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.enums.TipoCliente;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.beans.consultas.Consulta;
import com.afirme.afirmenet.ibs.beans.consultas.ConsultaPuntosBonus;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.FondoInversion;
import com.afirme.afirmenet.ibs.beans.consultas.FondoMovimiento;
import com.afirme.afirmenet.ibs.beans.consultas.Prestamo;
import com.afirme.afirmenet.ibs.beans.consultas.Resumen;
import com.afirme.afirmenet.ibs.databeans.INSER;
import com.afirme.afirmenet.model.DCPermisoServicio;
import com.afirme.afirmenet.model.base.Paginacion;
import com.afirme.afirmenet.model.estadoCuenta.EstadoCuenta;
import com.afirme.afirmenet.model.tdc.PuntosBonus;
import com.afirme.afirmenet.model.transferencia.Favorita;
import com.afirme.afirmenet.service.consultas.CreditoService;
import com.afirme.afirmenet.service.consultas.CuentaService;
import com.afirme.afirmenet.service.consultas.InversionService;
import com.afirme.afirmenet.service.consultas.MovimientoService;
import com.afirme.afirmenet.service.consultas.estadoCuenta.EstadoCuentaService;
import com.afirme.afirmenet.service.transferencia.FavoritaService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.time.TimeUtils;
import com.afirme.afirmenet.web.controller.consultas.SaldosMovimientosController;
import com.afirme.afirmenet.web.empresas.controller.base.BaseController;
import com.afirme.afirmenet.web.facade.impl.CuentasRetenidosDifFacadeImpl;
import com.afirme.afirmenet.web.model.AfirmeNetUser;
import com.afirme.afirmenet.web.model.FavoritasModel;
import com.afirme.afirmenet.web.reports.consultas.ReporteMovimientosPorCuenta;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;
import com.afirme.afirmenet.service.pagos.PagosService;
/**
 * Controller que atiende peticiones de saldos y movimientos
 * 
 * 
 * @author Arturo Ivan Martinez Mata
 * 
 */
@Controller
@RequestMapping("/consultas")
@SessionAttributes({ "consulta", "fondosInversion", "lstPtosVencer" })
public class SaldosMovimientosController extends BaseController {
	
	@Autowired
	private PagosService pagosService;
	
	@Autowired
	private EstadoCuentaService estadoCuentaService;

	static final AfirmeNetLog LOG = new 
			AfirmeNetLog(SaldosMovimientosController.class);
	
	@Autowired
	private FavoritaService favoritaService;

	private final CuentaService cuentaService;
	private final MovimientoService movimientoService;
	private final CuentasRetenidosDifFacadeImpl cuentasRetenidosDifFacade;
	private final CreditoService creditoService;
	private final InversionService inversionService;

	/**
	 * @param cuentaService
	 * @param movimientoService
	 * @param cuentasBonosFacade
	 * @param creditoService
	 */
	@Autowired
	public SaldosMovimientosController(CuentaService cuentaService,
			MovimientoService movimientoService,
			CuentasRetenidosDifFacadeImpl cuentasRetenidosDifFacade,
			CreditoService creditoService,
			InversionService inversionService) {
		this.cuentaService = cuentaService;
		this.movimientoService = movimientoService;
		this.cuentasRetenidosDifFacade = cuentasRetenidosDifFacade;
		this.creditoService = creditoService;
		this.inversionService = inversionService;
	}
	private void setPost(ModelMap model, HttpServletRequest request){
		int pos=0;
		try{
			if(request.getParameter("pos")!=null)
			pos=Integer.parseInt(request.getParameter("pos").toString().trim());
		}catch(Exception e){}
		model.addAttribute("pos", pos);
	}
	/**
	 * Metodo que atiende las peticiones al contexto /resumen-de-mis-cuentas.htm
	 * En el cual se muestran el saldo de todas las cuentas del cliente de
	 * manera global
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping("/resumen-de-mis-cuentas.htm")
	public String resumen(ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		LOG.setSumInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Inicia Carga de Datos", request.getRemoteAddr());
		setPost(model, request);
		
		List<Cuenta> cuentasTodasVisibles = cuentaService.getCuentas(
			    afirmeNetUser.getContrato(),
				afirmeNetUser.getCuentasExcluyentes(), true);
		List<Cuenta> cuentasTodas = cuentaService.segmentaCuentasPorVisible(cuentasTodasVisibles);
		List<Cuenta> cuentas = cuentaService.segmentaCuentasPorTipo(
				Cuenta.TIPO_CUENTA, cuentasTodas);
		List<Cuenta> creditos = cuentaService.segmentaCuentasPorTipo(
				Cuenta.TIPO_CREDITO, cuentasTodas);
		List<Cuenta> inversiones = cuentaService.segmentaCuentasPorTipo(
				Cuenta.TIPO_INVERSION, cuentasTodas);
		List<FondoInversion> fondosInversion = inversionService.getFondosInversion( afirmeNetUser.getNumCliente() );
		cuentaService.setFondosInversionAInversiones(inversiones, fondosInversion);
			
		List<Resumen> resumen = new ArrayList<Resumen>();
		Resumen resumenCuentas = cuentaService.calculaResumenPorCuenta(
				Cuenta.TIPO_CUENTA, cuentas);
		Resumen resumenCreditos = cuentaService.calculaResumenPorCuenta(
				Cuenta.TIPO_CREDITO, creditos);
		Resumen resumenInversiones = cuentaService.calculaResumenPorCuenta(
				Cuenta.TIPO_INVERSION, inversiones);
		
		if (resumenCuentas.getTieneCuentas() > 0)
			resumen.add(resumenCuentas);
		if (resumenCreditos.getTieneCuentas() > 0)
			resumen.add(resumenCreditos);
		if (resumenInversiones.getTieneCuentas() > 0)
			resumen.add(resumenInversiones);
		model.addAttribute("cuentas", cuentas);
		model.addAttribute("creditos", creditos);
		model.addAttribute("inversiones", inversiones);
		model.addAttribute("resumen", resumen);
		model.addAttribute("consulta", new Consulta());
		
		request.getSession().setAttribute(
				AfirmeNetWebConstants.CUENTAS_SESSION, cuentas);
		request.getSession().setAttribute(
				AfirmeNetWebConstants.INVERSIONES_SESSION, inversiones);
		request.getSession().setAttribute(
				AfirmeNetWebConstants.CREDITOS_SESSION, creditos);
	    model.addAttribute("fondosInversion", fondosInversion); 
		LOG.setSumInfo(afirmeNetUser.getContrato(), afirmeNetUser.getContrato(), "Fin de Carga de Datos", request.getRemoteAddr());
		
		/**FAVORITAS**/
		FavoritasModel favoritasModel = new FavoritasModel();
		favoritasModel.setFavoritas(favoritaService.getFavoritas(
				afirmeNetUser.getContrato(), 5));
		model.addAttribute("favoritasModel", favoritasModel);
		model.addAttribute("editable", false);
		
		/**FAVORITAS**/	
		
		//OJOS
		/*BLOQUE DE INFORMACIÓN PARA PAGO SERVICIOS*/
		List<DCPermisoServicio> serviciosActivos = pagosService.getServiciosActivos(afirmeNetUser.getContrato(), TipoCliente.PERSONAS);
		List<INSER> serviciosTodos = pagosService.getConvenios400();
		List<String> lstServiciosActivos = new ArrayList<String>(0);
		List<INSER> servicios = new ArrayList<INSER>(0);
		
		List<String> lstServiciosValidos =  Arrays.asList("131","901","101","121");

		
		for(DCPermisoServicio servicio: serviciosActivos){
			lstServiciosActivos.add(String.valueOf(servicio.getServiceId()));
		}
				
		
		if(lstServiciosActivos.contains("100")){
			for (INSER serv : serviciosTodos) {		
				if(lstServiciosValidos.contains(serv.getSERCOM().toString())){
					servicios.add(serv);
				}				
			}
		}else{		
			for (INSER serv : serviciosTodos) {	
				if (lstServiciosActivos.contains(serv.getSERCOM().toString())) {
					if(lstServiciosValidos.contains(serv.getSERCOM().toString())){
						servicios.add(serv);
					}
				}
			}
		}
		
		model.addAttribute("servicios", servicios);
		
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_RESUMEN;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-ultimos-movimientos.htm En el cual se muestran los ultimos X
	 * movimientos de la cuenta seleccionada
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-ultimos-movimientos.htm", method = RequestMethod.POST)
	public String ultimos(@ModelAttribute("consulta") Consulta consulta,
			ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		setPost(model, request);
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta.setPaginaActual(1);
		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		consulta.setCuentaClabe(tempCuenta.getClabe());
		consulta.setCuentaDesc(tempCuenta.getDescription());
		consulta.setCuentaMoneda(tempCuenta.getCcy());
		consulta.setCuentaNombre(tempCuenta.getNickname());

		consulta = movimientoService.ultimosMovimientos(afirmeNetUser.getContrato(),
				consulta);
		model.addAttribute("consulta", consulta);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_DETALLE_ULTIMOS;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-prestamo.htm En el cual se muestran todos los
	 * detalles del prestamo
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-prestamo.htm", method = RequestMethod.POST)
	public String detallePrestamo(@ModelAttribute("consulta") Consulta consulta,
			ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		setPost(model, request);
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta.setPaginaActual(1);
		Cuenta tempCuenta = getPrestamoTemporal(consulta, request);
		List<Prestamo> prestamos=creditoService.getPrestamos(afirmeNetUser.getContrato(), tempCuenta);
		creditoService.detallePrestamo(afirmeNetUser.getContrato(), prestamos);
		
		//OJOS - Consulta si esta activo el estado de cuenta para la tarjeta a consultar sus movimientos
		boolean activo = false;
		EstadoCuenta estadoCuenta = new EstadoCuenta();
		estadoCuenta.setClienteid(afirmeNetUser.getNumCliente().trim());
		estadoCuenta.setAccion(EstadoCuenta.ACCION_ACTIVAR);
		List<EstadoCuenta> edoCuentaActivar = estadoCuentaService.getEdoCuentaService(estadoCuenta);
		
		if(edoCuentaActivar != null && !edoCuentaActivar.isEmpty()){
			for(EstadoCuenta estCue: edoCuentaActivar){
				if(prestamos.get(0).getNumber().trim().equals(estCue.getNumCuenta().trim())){
					model.addAttribute("estCueAct", false);
					activo = true;
					break;
				}
			}
		}
		if(activo == false){
			estadoCuenta.setAccion(EstadoCuenta.ACCION_EMISION);
			List<EstadoCuenta> edoCuentaEmision = estadoCuentaService.getEdoCuentaService(estadoCuenta);
			if(edoCuentaEmision != null && !edoCuentaEmision.isEmpty()){
				for(EstadoCuenta estCueEmi: edoCuentaEmision){
					if(prestamos.get(0).getNumber().trim().equals(estCueEmi.getNumCuenta().trim())){
						model.addAttribute("estCueAct", true);
						activo = true;
						break;
					}
				}
				if(activo == false){
					model.addAttribute("estCueAct", "error");
					Map<CodigoExcepcion, String> errores = new HashMap<CodigoExcepcion, String>();
					errores.put(CodigoExcepcion.ERR_3000, "No se puede emitir este estado de cuenta de este crédito.");
					tempCuenta.setErrores(errores);
					model.addAttribute("advertencias", tempCuenta.getErrores());
				}
			}else{
				model.addAttribute("estCueAct", "error");
				Map<CodigoExcepcion, String> errores = new HashMap<CodigoExcepcion, String>();
				errores.put(CodigoExcepcion.ERR_3000, "No se puede emitir este estado de cuenta de este crédito.");
				tempCuenta.setErrores(errores);
				model.addAttribute("advertencias", tempCuenta.getErrores());
			}
		}
		
		model.addAttribute("credito", tempCuenta);
		model.addAttribute("prestamos", prestamos);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_PRESTAMO;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-prestamo_mis.htm En el cual se muestran todos los
	 * detalles del prestamo
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-prestamo_mis.htm", method = RequestMethod.POST)
	public String detallePrestamoMis(@ModelAttribute("consulta") Consulta consulta,
			ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		setPost(model, request);
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta.setPaginaActual(1);
		Cuenta tempCuenta = getPrestamoTemporal(consulta, request);
		List<Prestamo> prestamos=new ArrayList<Prestamo>();

		Prestamo prestamo=new Prestamo();
		prestamo.setDescription(tempCuenta.getDescription());
		prestamo.setNumber(tempCuenta.getNumber());
		prestamo.setAvailable(tempCuenta.getAvailable());
		prestamo.setCcy(tempCuenta.getCcy());
		prestamos.add(prestamo);
		
		creditoService.detallePrestamo(afirmeNetUser.getContrato(), prestamos);
		
		//OJOS - Consulta si esta activo el estado de cuenta para la tarjeta a consultar sus movimientos
		boolean activo = false;
		EstadoCuenta estadoCuenta = new EstadoCuenta();
		estadoCuenta.setClienteid(afirmeNetUser.getNumCliente().trim());
		estadoCuenta.setAccion(EstadoCuenta.ACCION_ACTIVAR);
		List<EstadoCuenta> edoCuentaActivar = estadoCuentaService.getEdoCuentaService(estadoCuenta);
		
		if(edoCuentaActivar != null && !edoCuentaActivar.isEmpty()){
			for(EstadoCuenta estCue: edoCuentaActivar){
				if(tempCuenta.getNumber().trim().equals(estCue.getNumCuenta().trim())){
					model.addAttribute("estCueAct", false);
					activo = true;
					break;
				}
			}
		}
		if(activo == false){
			estadoCuenta.setAccion(EstadoCuenta.ACCION_EMISION);
			List<EstadoCuenta> edoCuentaEmision = estadoCuentaService.getEdoCuentaService(estadoCuenta);
			if(edoCuentaEmision != null && !edoCuentaEmision.isEmpty()){
				for(EstadoCuenta estCueEmi: edoCuentaEmision){
					if(tempCuenta.getNumber().trim().equals(estCueEmi.getNumCuenta().trim())){
						model.addAttribute("estCueAct", true);
						activo = true;
						break;
					}
				}
				if(activo == false){
					model.addAttribute("estCueAct", "error");
					Map<CodigoExcepcion, String> errores = new HashMap<CodigoExcepcion, String>();
					errores.put(CodigoExcepcion.ERR_3000, "No se puede emitir este estado de cuenta de este crédito.");
					tempCuenta.setErrores(errores);
					model.addAttribute("advertencias", tempCuenta.getErrores());
				}
			}else{
				model.addAttribute("estCueAct", "error");
				Map<CodigoExcepcion, String> errores = new HashMap<CodigoExcepcion, String>();
				errores.put(CodigoExcepcion.ERR_3000, "No se puede emitir este estado de cuenta de este crédito.");
				tempCuenta.setErrores(errores);
				model.addAttribute("advertencias", tempCuenta.getErrores());
			}
		}
		
		model.addAttribute("credito", tempCuenta);
		model.addAttribute("prestamos", prestamos);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_PRESTAMO;
	}
	
	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-inversion.htm En el cual se muestran todos los
	 * detalles del prestamo
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-inversion.htm", method = RequestMethod.POST)
	public String detalleInversion(@ModelAttribute("consulta") Consulta consulta,
			ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		setPost(model, request);
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta.setPaginaActual(1);
		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		consulta=inversionService.datosInversion(afirmeNetUser.getContrato(), tempCuenta);
		//creditoService.detallePrestamo(afirmeNetUser.getContrato(), prestamos);
		model.addAttribute("inversion", tempCuenta);
		model.addAttribute("consulta", consulta);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_INVERSION;
	}
	
	@RequestMapping(value = "/detalle-saldos-bonus.htm")
	public String detallePuntosBonus( @ModelAttribute("consulta") Consulta consulta, ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		
		setPost(model, request);
		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		final BigDecimal factorBonus = tempCuenta.getFactorBonus();
		factorBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta = movimientoService.mesActualBonus( afirmeNetUser.getContrato(), consulta, factorBonus );
		ArrayList<PuntosBonus> lstPtosVencer = movimientoService.fechasVencePuntosService(tempCuenta.getNumber());

		model.addAttribute("consultaPuntosBonos", getConsultaPuntosBonos(tempCuenta, factorBonus));
		model.addAttribute("lstPtosVencer", lstPtosVencer);
		model.addAttribute("consulta", consulta);
		
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_SALDOS_BONUS;
	}
	
	@RequestMapping(value = "/detalle-saldos-bonus-anterior.htm", method = RequestMethod.POST)
	public String detallePuntosBonusMesAnterior(@ModelAttribute("consulta") Consulta consulta, ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		
		setPost(model, request);
		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		final BigDecimal factorBonus = tempCuenta.getFactorBonus();
		factorBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta = movimientoService.mesAnteriorBonus( afirmeNetUser.getContrato(), consulta, factorBonus );
		model.addAttribute("consultaPuntosBonos", getConsultaPuntosBonos(tempCuenta, factorBonus));
		model.addAttribute("consulta", consulta);
		
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_SALDOS_BONUS;
	}

	@RequestMapping(value = "detalle-retenidos-diferidos-bonus.htm")
	public String detalleRetenidosDiferidosBonus(
			@ModelAttribute("consulta") Consulta consulta, ModelMap model,
			HttpServletRequest request) {
		DecimalFormat format = new DecimalFormat("0");
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		final BigDecimal factorBonus = tempCuenta.getFactorBonus();
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		model.addAttribute("paginacion", cuentasRetenidosDifFacade.getRetenciones(
				afirmeNetUser.getContrato(),
				format.format(consulta.getCuenta()), factorBonus,
				consulta.getPaginaActual()));
		model.addAttribute("consultaPuntosBonos",
				getConsultaPuntosBonos(tempCuenta, factorBonus));
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_DETALLE_RETENIDOS_BONUS;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-todos-movimientos.htm En el cual se muestran todos los
	 * movimientos del mes en curso de la cuenta seleccionada
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-todos-movimientos.htm", method = RequestMethod.POST)
	public String todos(@ModelAttribute("consulta") Consulta consulta,
			ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta = movimientoService.mesActual(afirmeNetUser.getContrato(),
				consulta);
		model.addAttribute("consulta", consulta);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_DETALLE_TODOS;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-todos-movimientos-anterior.htm En el cual se muestran todos los
	 * movimientos del mes anterior de la cuenta seleccionada
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-todos-movimientos-anterior.htm", method = RequestMethod.POST)
	public String mesAnterior(@ModelAttribute("consulta") Consulta consulta,
			ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta = movimientoService.mesAnterior(afirmeNetUser.getContrato(),
				consulta);

		model.addAttribute("consulta", consulta);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_DETALLE_TODOS;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-movimientos-retenidos.htm En el cual se muestran los movimientos
	 * retenidos y diferidos del mes en curso de la cuenta seleccionada
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-movimientos-retenidos.htm", method = RequestMethod.POST)
	public String retenidos(@ModelAttribute("consulta") Consulta consulta, ModelMap model,
			HttpServletRequest request) {
		DecimalFormat format = new DecimalFormat("0");
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		final BigDecimal factorBonus = BigDecimal.ONE;
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		Paginacion paginacion=cuentasRetenidosDifFacade.getRetenciones(
				afirmeNetUser.getContrato(),
				format.format(consulta.getCuenta()), factorBonus,
				consulta.getPaginaActual());
		model.addAttribute("paginacion", paginacion);
		consulta.setRegistros(paginacion.getNumMovimientos());
		consulta.setSaldoDisponible(tempCuenta.getBalance());
		consulta.setFechaInicio(new BigDecimal(TimeUtils.getDateFormat(Calendar.getInstance().getTime(), TimeUtils.DB2_DATE_FORMAT)));
		model.addAttribute("consulta", consulta);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_DETALLE_RETENIDOS;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /buscar-movimientos-filtro.htm En el cual se muestran los filtros para la
	 * busqueda avanzada de movimientos
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/buscar-movimientos-filtro.htm", method = RequestMethod.POST)
	public String buscarFiltro(@ModelAttribute("consulta") Consulta consulta,
			ModelMap model, HttpServletRequest request) {

		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		consulta.setSaldoDisponible(tempCuenta.getBalance());
		consulta.setFechaInicio(new BigDecimal(TimeUtils.getDateFormat(Calendar.getInstance().getTime(), TimeUtils.DB2_DATE_FORMAT)));
		model.addAttribute("consulta", consulta);

		return AfirmeNetWebConstants.MV_CONSULTAS_SM_BUSQUEDA_FILTROS;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /buscar-movimientos-filtro-bonus.htm En el cual se muestran los filtros
	 * para la busqueda avanzada de movimientos de cuentas bonus
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/buscar-movimientos-filtro-bonus.htm", method = RequestMethod.POST)
	public String buscarFiltroBonus(
			@ModelAttribute("consulta") Consulta consulta, ModelMap model,
			HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		final BigDecimal factorBonus = tempCuenta.getFactorBonus();
		factorBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta = movimientoService.mesActualBonus(
				afirmeNetUser.getContrato(), consulta, factorBonus);

		model.addAttribute("consultaPuntosBonos",
				getConsultaPuntosBonos(tempCuenta, factorBonus));
		model.addAttribute("consulta", consulta);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_BUSQUEDA_FILTROS_BONUS;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /buscar-movimientos-resultado.htm En el cual se muestran los resultados
	 * de la busqueda avanzada de movimientos
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/buscar-movimientos-resultado.htm", method = RequestMethod.POST)
	public String buscarResultado(
			@ModelAttribute("consulta") Consulta consulta, ModelMap model,
			HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		AfirmeNetUser afirmeNetUser = getSessionUser(request);

		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		if (request.getParameter("fechaInicioStr") != null) {

			Date fecha = TimeUtils.getDate(request.getParameter("fechaInicioStr") + " 01:01", "dd/MMMM/yyyy HH:mm");	
			consulta.setFechaInicio(new BigDecimal(TimeUtils.getDateFormat(fecha, TimeUtils.DB2_DATE_FORMAT)));
			fecha = TimeUtils.getDate(request.getParameter("fechaFinStr") + " 01:01", "dd/MMMM/yyyy HH:mm");
			
			consulta.setFechaFin(new BigDecimal(TimeUtils.getDateFormat(fecha, TimeUtils.DB2_DATE_FORMAT)));
		}
		consulta = movimientoService.busqueda(afirmeNetUser.getContrato(),
				consulta);

		consulta.setSaldoDisponible(tempCuenta.getBalance());
		model.addAttribute("consulta", consulta);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_BUSQUEDA_RESULTADO;
	}

	/**
	 * Metodo que atiende las peticiones al contexto
	 * /buscar-movimientos-resultado.htm En el cual se muestran los resultados
	 * de la busqueda avanzada de movimientos
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/buscar-movimientos-resultado-bonus.htm", method = RequestMethod.POST)
	public String buscarResultadoBonus(
			@ModelAttribute("consulta") Consulta consulta, ModelMap model,
			HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		Cuenta tempCuenta = getCuentaTemporal(consulta, request);
		final BigDecimal factorBonus = tempCuenta.getFactorBonus();
		factorBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		if (request.getParameter("fechaInicioStr") != null) {

			Date fecha = TimeUtils.getDate(request.getParameter("fechaInicioStr") + " 01:01", "dd/MMMM/yyyy HH:mm");	
			consulta.setFechaInicio(new BigDecimal(TimeUtils.getDateFormat(fecha, TimeUtils.DB2_DATE_FORMAT)));
			fecha = TimeUtils.getDate(request.getParameter("fechaFinStr") + " 01:01", "dd/MMMM/yyyy HH:mm");
			
			consulta.setFechaFin(new BigDecimal(TimeUtils.getDateFormat(fecha, TimeUtils.DB2_DATE_FORMAT)));
		}
		consulta = movimientoService.busquedaBonus(afirmeNetUser.getContrato(),
				consulta, factorBonus);
		model.addAttribute("consultaPuntosBonos",
				getConsultaPuntosBonos(tempCuenta, factorBonus));
		model.addAttribute("consulta", consulta);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_BUSQUEDA_RESULTADO_BONUS;
	}

	@RequestMapping("/edo-cta-activacion.htm")
	public String activacionEstadoCuenta() {
		return AfirmeNetWebConstants.MV_CONSULTAS_EDO_CTA_ACTIVACION_TERMCOND;
	}
	
	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-fondo-inversion.htm En el cual se muestra el detalle 
	 * del fondo de inversión seleccionado
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-fondo-inversion.htm", method = RequestMethod.POST)
	public String detalleFondosInversion(ModelMap model, HttpServletRequest request) {						
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
//		List<FondoInversion> listaFondosInversion = inversionService.getFondosInversion( afirmeNetUser.getNumCliente() ); 
		List<FondoInversion> listaFondosInversion = (List<FondoInversion>) request.getSession().getAttribute("fondosInversion");
		FondoInversion fondoInversion = new FondoInversion();
		
//		Date initDate = new Date();
//		Date endDate = new Date();
		String contractId = request.getParameter("contrato");
		String pos = request.getParameter("pos");
		
//		List<FondoMovimiento> movimientos = inversionService.getMovimientosFondo(initDate, endDate, contractId);
		
		for (FondoInversion fi : listaFondosInversion) {
			if(fi.getContrato().equals(contractId)){				
				fondoInversion = fi;
				break;
			}
		}
		
		model.addAttribute("contrato", contractId);
		model.addAttribute("pos", pos);
		model.addAttribute("fondoInversion", fondoInversion);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_FONDO_INVERSION;
	}
	
	/**
	 * Metodo que atiende las peticiones al contexto
	 * /detalle-fondo-inversion-movimientos.htm En el cual se muestra el detalle 
	 * del fondo de inversión seleccionado
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping(value = "/detalle-fondo-inversion-movimientos.htm", method = RequestMethod.POST)
	public String detalleMovimientosFondosInversion(ModelMap model, HttpServletRequest request) {						
		LOG.debug("Atendiendo Peticion = " + request.getServletPath());
		
		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		List<FondoInversion> listaFondosInversion = inversionService.getFondosInversion( afirmeNetUser.getNumCliente() ); 
		FondoInversion fondoInversion = new FondoInversion();
		
		String contractId = request.getParameter("contrato");
		String pos = request.getParameter("pos");
		int mes = Integer.parseInt( request.getParameter("mes") );
		String nombreMes = "";
		
		SimpleDateFormat df = new SimpleDateFormat("MMMM");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
		Date initDate = new Date(), 
			 endDate = new Date();
				
		Calendar c = Calendar.getInstance();   // this takes current date
		switch (mes){
			case 0:
				c.set(Calendar.DAY_OF_MONTH, 1);
				initDate = c.getTime();
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = c.getTime();
			break;
			case 1:
				c.set(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH, 1);
				initDate = c.getTime();
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));		
				endDate = c.getTime();
			break;
			case 2:
				c.set(Calendar.MONTH, -2);
				c.set(Calendar.DAY_OF_MONTH, 1);
				initDate = c.getTime();
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));	
				endDate = c.getTime();
			break;
			case 3:
				c.set(Calendar.MONTH, -3);
				c.set(Calendar.DAY_OF_MONTH, 1);	
				initDate = c.getTime();
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = c.getTime();
			break;
		}
		
		List<FondoMovimiento> movimientos = inversionService.getMovimientosFondo(initDate, endDate, contractId);
		nombreMes = df.format(initDate) + " " + df2.format(initDate);
		nombreMes = nombreMes.substring(0,1).toUpperCase() + nombreMes.substring(1).toLowerCase();
		
//		
		for (FondoInversion fi : listaFondosInversion) {
			if(fi.getContrato().equals(contractId)){
				fi.setMovimientos(movimientos);
				fondoInversion = fi;
				break;
			}
		}
		
		
		model.addAttribute("contrato", contractId);
		model.addAttribute("pos", pos);
		model.addAttribute("fondoInversion", fondoInversion);
		model.addAttribute("mes", mes);
		model.addAttribute("nombreMes", nombreMes);
		return AfirmeNetWebConstants.MV_CONSULTAS_SM_FONDO_INVERSION_MOVIMIENTOS;
	}
	
	private ConsultaPuntosBonus getConsultaPuntosBonos(Cuenta tempCuenta,
			BigDecimal factorBonus) {
		final BigDecimal balance = tempCuenta.getBalance();
		balance.setScale(2, BigDecimal.ROUND_HALF_UP);
		return new ConsultaPuntosBonus(tempCuenta.getNickname(),
				cuentaService.getDigitoVerificador("4130980"
						+ tempCuenta.getNumber()), balance.divide(factorBonus),
				balance, factorBonus);
	}

	/**
	 * 
	 * @param consulta
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Cuenta getCuentaTemporal(Consulta consulta,
			HttpServletRequest request) {
 		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		cuentas.addAll((List<Cuenta>) request.getSession().getAttribute(
				AfirmeNetWebConstants.CUENTAS_SESSION));
		cuentas.addAll((List<Cuenta>) request.getSession().getAttribute(
				AfirmeNetWebConstants.INVERSIONES_SESSION));
		return cuentaService.seleccionaCuenta(consulta.getCuenta().toString(),
				cuentas);
	}
	/**
	 * 
	 * @param consulta
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Cuenta getPrestamoTemporal(Consulta consulta,
			HttpServletRequest request) {
		List<Cuenta> cuentas = (List<Cuenta>) request.getSession().getAttribute(
				AfirmeNetWebConstants.CREDITOS_SESSION);
		return cuentaService.seleccionaCuenta(consulta.getCuenta().toString(),
				cuentas);
	}

	//@SuppressWarnings("resource")
	@ResponseBody
	@RequestMapping(value = "/printPDF", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getPDF(
			@ModelAttribute("consulta") Consulta consulta, ModelMap modelMap,
			HttpServletRequest request) {

		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta.setUsuario(afirmeNetUser.getContrato());
		consulta=movimientoService.todosLosRegistro(consulta);
		ReporteMovimientosPorCuenta reporte = new ReporteMovimientosPorCuenta(consulta);

		byte[] reporteBytes;
		try {

		    //String pathReporte="";
			//if(new File(request.getSession().getServletContext().getRealPath("/") + reporte.getPATH()+reporte.getREPORTE()).exists())
			//	pathReporte=request.getSession().getServletContext().getRealPath("/") + reporte.getPATH()+reporte.getREPORTE();
			//else
			//	pathReporte=request.getSession().getServletContext().getRealPath("/") + File.separator+reporte.getPATH()+reporte.getREPORTE();
			
			String strReporte=reporte.getPATH()+reporte.getREPORTE();
			String strAbsoluta=request.getSession().getServletContext().getRealPath(strReporte);
			reporte.setPATH(strAbsoluta);
			reporteBytes = reporte.getReportPDF();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			//headers.setContentDispositionFormData(reporte.getFileName(),reporte.getFileName());
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");;
			headers.add("Content-Disposition", "attachment;filename="+reporte.getFileName());
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
					reporteBytes, headers, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			throw new AfirmeNetException("0000",
					"Ocurrio un error al generar el pdf: "
							+ e.getMessage());
		}
	}	
	@ResponseBody
	@RequestMapping(value = "/printCSV", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getCSV(
			@ModelAttribute("consulta") Consulta consulta, ModelMap modelMap,
			HttpServletRequest request) {

		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta.setUsuario(afirmeNetUser.getContrato());
		consulta=movimientoService.todosLosRegistro(consulta);
		ReporteMovimientosPorCuenta reporte = new ReporteMovimientosPorCuenta(consulta);

		byte[] reporteBytes;
		try {
			reporteBytes = reporte.getReportCSV();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("text/csv"));

			headers.add("Content-Disposition", "attachment;filename="+reporte.getFileName());
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
					reporteBytes, headers, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			throw new AfirmeNetException("0000",
					"Ocurrio un error al generar el csv: "
							+ e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping(value = "/printExcel", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getExcel(
			@ModelAttribute("consulta") Consulta consulta, ModelMap modelMap,
			HttpServletRequest request) {

		AfirmeNetUser afirmeNetUser = getSessionUser(request);
		consulta.setUsuario(afirmeNetUser.getContrato());
		consulta=movimientoService.todosLosRegistro(consulta);
		ReporteMovimientosPorCuenta reporte = new ReporteMovimientosPorCuenta(consulta);

		byte[] reporteBytes;
		try {
			reporteBytes = reporte.getReportExcel();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

			headers.add("Content-Disposition", "attachment;filename="+reporte.getFileName());
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
					reporteBytes, headers, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			throw new AfirmeNetException("0000",
					"Ocurrio un error al generar el excel: "
							+ e.getMessage());
		}
	}
}
