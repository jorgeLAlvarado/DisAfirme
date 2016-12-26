package com.afirme.afirmenet.web.empresas.controller.consultas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.enums.ConfigProperties;
import com.afirme.afirmenet.enums.TipoServicio;
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.beans.configuraciones.chequera.BusquedaChequeResultado;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.ibs.databeans.TC5000PF;
import com.afirme.afirmenet.ibs.databeans.cardif.SeguroCardif;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.model.configuraciones.AliasAvatarDTO;
import com.afirme.afirmenet.model.configuraciones.ContrasenaDTO;
import com.afirme.afirmenet.model.configuraciones.CorreoElectronicoDTO;
import com.afirme.afirmenet.model.configuraciones.CuentasDestinoDTO;
import com.afirme.afirmenet.model.configuraciones.EliminarCuentaDestinoDTO;
import com.afirme.afirmenet.model.configuraciones.LimitesCuentaAfirme;
import com.afirme.afirmenet.model.configuraciones.Transacciones;
import com.afirme.afirmenet.model.configuraciones.chequera.CuentaChequera;
import com.afirme.afirmenet.model.configuraciones.chequera.RespuestaProteccionCheque;
import com.afirme.afirmenet.model.configuraciones.chequera.RespuestaSolicitudChequera;
import com.afirme.afirmenet.model.credito.Credito;
import com.afirme.afirmenet.model.credito.nomina.Nomina;
import com.afirme.afirmenet.model.estadoCuenta.EstadoCuenta;
import com.afirme.afirmenet.model.inversiones.Inversion;
import com.afirme.afirmenet.model.movil.Movil;
import com.afirme.afirmenet.model.nomina.Portabilidad;
import com.afirme.afirmenet.model.pagos.ConvenioDomiciliacion;
import com.afirme.afirmenet.model.pagos.ImpuestoGDF;
import com.afirme.afirmenet.model.pagos.impuestos.DepositoReferenciado;
import com.afirme.afirmenet.model.pagos.impuestos.Impuesto;
import com.afirme.afirmenet.model.pagos.servicios.Servicio;
import com.afirme.afirmenet.model.servicios.Alertas;
import com.afirme.afirmenet.model.servicios.AsociaMovil;
import com.afirme.afirmenet.model.servicios.AvisoViajeDTO;
import com.afirme.afirmenet.model.servicios.tarjetaDebito.TarjetaDebito;
import com.afirme.afirmenet.model.transferencia.Divisa;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.config.PropertyService;
import com.afirme.afirmenet.service.consultas.CuentaService;
import com.afirme.afirmenet.service.consultas.CuentasHistorialService;
import com.afirme.afirmenet.service.consultas.HistorialService;
import com.afirme.afirmenet.service.divisa.DivisaService;
import com.afirme.afirmenet.service.pagos.ImpuestosGDFService;
import com.afirme.afirmenet.service.pagos.PagosService;
import com.afirme.afirmenet.service.pagos.impuestos.ImpuestosService;
import com.afirme.afirmenet.service.recargas.RecargasService;
import com.afirme.afirmenet.service.tarjetas.TDCService;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.time.TimeUtils;
import com.afirme.afirmenet.web.controller.consultas.HistorialMovimientosController;
import com.afirme.afirmenet.web.empresas.controller.base.BaseController;
import com.afirme.afirmenet.web.model.AfirmeNetUser;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;

/**
 * Controller que atiende peticiones de saldos y movimientos
 * 
 * 
 * @author Arturo Ivan Martinez Mata
 * @author epifanio.guzman@afirme.com
 * 
 */
@Controller
@SessionAttributes({"listaTransacciones", "resultados", "titulo", "finalReport"})
@RequestMapping("/consultas")
public class HistorialMovimientosController extends BaseController{
	static final AfirmeNetLog LOG = new AfirmeNetLog(HistorialMovimientosController.class);

	@Autowired
	private HistorialService historialService;
	
	@Autowired
	private ComprobanteTransferenciaDao comprobanteTransferenciaDao;
	@Autowired
	PropertyService propertyService; // PropertyServiceImpl
		
	@Autowired
	DivisaService divisaService;
	
	@Autowired
	private PagosService pagosService;
	
	@Autowired
	CuentasHistorialService cuentasHistorialService;
	
	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	private ImpuestosService impuestosService;
	
	@Autowired
	private ImpuestosGDFService impuestosGDFService;
	
	@Autowired
	private RecargasService recargasService;
	
	@Autowired
	protected TDCService tdcService;
	
	/**
	 * Metodo que atiende las peticiones al contexto /resumen-de-mis-cuentas.htm
	 * En el cual se muestran el saldo de todas las cuentas del cliente de manera global 
	 * 
	 * cuando el usuario da click en en menu Consultas > Historial de operaciones
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	@RequestMapping("/historial-operaciones.htm")
	public String historial(ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = "+request.getServletPath());
		List<String> categorias=historialService.categorias(false);
		List<TipoTransaccion> listaTransacciones=historialService.listaTransacciones(false);
		model.addAttribute("categorias", categorias);
		model.addAttribute("listaTransacciones", listaTransacciones);
		return AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_BUSQUEDA;
	}

	/**
	 * Para mostrar los resultados de la busqueda por tipo de comprobantes seleccionado.
	 * 
	 * El usuario selecciona un tipo de operacion y rango de fechas y da click en buscar.
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/historial-resultados.htm", method = RequestMethod.POST)
	public String resultados(ModelMap model, HttpServletRequest request) {
		LOG.debug("Atendiendo Peticion = "+request.getServletPath());
		AfirmeNetUser afirmeNetUser=getSessionUser(request);
		String tipo=(String)request.getParameter("rdoTipo");
		String strFechaDe=(String)request.getParameter("fechaDesde");
		String strFechaA=(String)request.getParameter("fechaHasta");
		String nombre="";
		String pageCall = "";
		
		List<TipoTransaccion> listaTransacciones = (List<TipoTransaccion>) model.get("listaTransacciones");
		for (TipoTransaccion tipoT : listaTransacciones) {
			if (tipoT.getTipo().getValor().equals(tipo)) {
				nombre=tipoT.getNombre();
				break;
			}
		}
		
		
		Date fechaDesde = TimeUtils.getDate(strFechaDe + " 01:01" , "dd/MMMM/yyyy HH:mm");
		Date fechaHasta = TimeUtils.getDate(strFechaA + " 23:59" , "dd/MMMM/yyyy HH:mm");
		HistorialTipo st=HistorialTipo.findByValue(tipo);
		List<Cuenta> cuentas = null;
		if(HistorialTipo.ORDENES_DE_PAGO_CASH_EXPRESS.equals(st)){
			cuentas = getCuentasPropiasMXP(afirmeNetUser);
		}
		
		List<TransferenciaBase> resultados = historialService.buscaTransferencias(afirmeNetUser.getContrato(), cuentas, st, fechaDesde, fechaHasta);
		
		model.addAttribute("resultados", resultados);
		model.addAttribute("titulo", nombre);
		switch (st) {
			case CAMBIO_DE_LIMITES_DE_TRANSFERENCIAS:
				model.addAttribute("listLimTrans", resultados);
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_CAMBIOLIMTRANS;
				break;
			case EMISION_DE_ESTADO_DE_CUENTA:
				//OJOS
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_EDOCUENTAESTATUS;
				break;
			case ORDENES_DE_PAGO_CASH_EXPRESS:
				//OJOS
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_CASHEXPRESS;
				break;
			case ESTATUS_SERVICIO_EMISION_ESTADO_DE_CUENTA:
				//OJOS
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_EDOCUENTAESTATUS;
				break;
			case CAMBIO_DE_LIMITES_TARJETA_DE_DEBITO:
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_CAMBIOESTATUSTDD;
				break;
			case CAMBIO_DE_ESTATUS_TARJETA_DE_DEBITO:
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_CAMBIOESTATUSTDD;
				break;
			case ASOCIACION_DE_CUENTA_A_NUMERO_MOVIL:
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_ASOCIA;
				break;
			case ALERTAS_AFIRME:
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_ALERTAS;
				break;
			case AFIRME_MOVIL:
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_SERVICIOS;
				break;
			case ACTIVACION_DE_SERVICIOS:
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS_SERVICIOS;
				break;
			default:
				pageCall = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS; 
				break;
		}
		return pageCall;
	}
	
	/**
	 * Para validar una operacion seleccionada, llenar los objetos que utiliza el view del comprobante.
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/historial-comprobante.htm")
	public String comprobante(ModelMap model, HttpServletRequest request) {
		AfirmeNetUser afirmeNetUser=getSessionUser(request);
		LOG.debug("Atendiendo Peticion = "+request.getServletPath());
 		String idSe=(String)request.getParameter("radio-res");
		TransferenciaBase comprobante=null;
		@SuppressWarnings("unchecked")
		List<TransferenciaBase> resultados = (List<TransferenciaBase>) model.get("resultados");
		for (TransferenciaBase comp : resultados) {
			if (comp.getAfirmeNetReference().trim().equals(idSe.trim())) {
				comprobante=comp;
				break;
			}
		}
		
		//AQUI ESTA FALLANDO WEY ¿Donde?
		
		comprobante.setAccountOwner(afirmeNetUser.getNombreCorto());
		if(comprobante!=null){
			//Se obtienes datos adicionales de la transaccion si es que tiene
			historialService.obtenerInformacionExtra(comprobante);
		}
		
		String modelView=urlComprobante(comprobante, model, afirmeNetUser);
		return modelView;
	}
	
	/**
	 * Forma el modelmap con los objetos que ocupa el view, y la url de view correspondiente.
	 * 
	 * @param comprobante
	 * @param modelMap
	 * @param contrato
	 * @return
	 */
	private String urlComprobante(TransferenciaBase comprobante, ModelMap modelMap, AfirmeNetUser afirmeNetUser){
		
		HistorialTipo tipo=HistorialTipo.findByValue(comprobante.getTransactionCode());
		String modelView = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS;
		List<TransferenciaBase> comprobantesExito = new ArrayList<TransferenciaBase>(0);
		comprobante.setEsReimpresion(true);
		
		cuentasHistorialService.setInfoCuentaOrigen(comprobante, afirmeNetUser.getContrato(), tipo.getValor());
		cuentasHistorialService.setInfoCuentaDestino(comprobante, afirmeNetUser.getContrato(), tipo);

		if(tipo==HistorialTipo.PAGO_DE_SERVICIOS){
			
			if(comprobante.getTipoServicio().getValor().equals("2")){
			
				DC_CONVENIO convenio=pagosService.getConvenio(afirmeNetUser.getContrato(), comprobante.getCreditAccount(), Integer.parseInt(comprobante.getTipoServicio().getValor()));
				comprobante.setConvenio(convenio);
				if(comprobante.getConvenio().getTCNOMBRE()!=null && comprobante.getConvenio().getTCNOMBRE().trim().length()>0)
					comprobante.setBeneficiaryName(comprobante.getConvenio().getTCNOMBRE());
				
				if(!"4".equals(comprobante.getType())){
					if(convenio.getSERNAM() !=null && convenio.getSERNAM().trim().equals("TARJETA DE CREDITO AFIRME TERCEROS")){
						comprobante.setType("3");
					}else if(convenio.getSERNAM() !=null && convenio.getSERNAM().trim().equals("TARJETA DE CREDITO BANCOS RED")){
						if(convenio.getSERACC().trim().length() == 16){
							comprobante.setType("1");
						}else{
							comprobante.setType("2");
						}
					}else {
						comprobante.setType("0");
					}
				}
			}
		}
		comprobantesExito.add(comprobante);
		
		switch (tipo) {
		case ANTICIPO_DE_NOMINA:
		case CREDITO_DE_NOMINA:
			modelMap.addAttribute("listComprobantes",comprobantesExito);
			modelMap.addAttribute("nomina",(Nomina)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_NOMINA_COMPROBANTE;
			break;
		case DOMICILIACION:
			modelMap.addAttribute("listTransferencias",comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TDCDOMI_COMPROBANTE;
			break;
		case DOMINGO_ELECTRONICO:
			modelMap.addAttribute("transferencia",comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_DOMINGO_ELEC_COMPROBANTE;
			break;
		case TRASPASO_PROPIAS:
			modelMap.addAttribute("listTransferencias",comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_PROPIAS_COMPROBANTE;
			break;
		case TRASPASO_TERCEROS:
			modelMap.addAttribute("listComprobantes", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_COMPROBANTE;
			break;
		case TRANSFERENCIAS_INTERNACIONALES_DOLARES:
			modelMap.addAttribute("dolares", comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_DOLARES_COMPROBANTE;
			break;
		case PAGO_DE_SERVICIOS:
			if (comprobante.getServiceType() == 201) {
				modelMap.addAttribute("listTransferencias", comprobantesExito);
				modelView = AfirmeNetWebConstants.MV_PAGOS_TDCPROPIAS_COMPROBANTE;
			}else if(comprobante.getTipoServicio() == TipoServicio.SEGUROS_AFIRME || comprobante.getTipoServicio() == TipoServicio.TARJETA_DE_CREDITO_BANCOS_RED){
				modelMap.addAttribute("listTransferencias", comprobantesExito);
				modelView = AfirmeNetWebConstants.MV_PAGOS_TDC_COMPROBANTE;
			}else{
				modelMap.addAttribute("comprobante", comprobantesExito.get(0));
				modelView = AfirmeNetWebConstants.MV_SERVICIOS_COMPROBANTE;
			}
			break;
		case TRANSFERENCIAS_INTERNACIONALES_MULTIMONEDA:
			modelMap.addAttribute("transferencia",(Divisa)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_MULTIMONEDA_COMPROBANTE;
			break;
		case TRANSFERENCIA_SPEI:
//			int fechaInicial = Integer.parseInt( "20"+ comprobante.getProgrammingYear() + comprobante.getProgrammingMonth() + comprobante.getProgrammingDay() ),
			int fechaInicial = Integer.parseInt( "20"+ comprobante.getValidationYear() + comprobante.getValidationMonth() + comprobante.getValidationDay() ),
			    fechaFinal = Integer.parseInt( "20"+ comprobante.getValidationYear() + comprobante.getValidationMonth() + comprobante.getValidationDay() ),
			    tipoMovimiento = 1;
			String claveRastreo = comprobante.getTrackingCode();
			String cadenaEncriptada = comprobanteTransferenciaDao.obtenerSPEICEP(afirmeNetUser.getContrato(), fechaInicial, fechaFinal, tipoMovimiento, claveRastreo);       									
			modelMap.addAttribute("d", cadenaEncriptada);			
			modelMap.addAttribute("listComprobantes", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_NACIONALES_COMPROBANTE;
			break;
		case PAGO_INTERBANCARIO:
//			for(TransferenciaBase comp: comprobantesExito)
//				comp.getDestino().setBankName(historialService.getBanco(new BigDecimal(comprobante.getBankReceiving())).getBANNOM());
			modelMap.addAttribute("listComprobantes", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_NACIONALES_COMPROBANTE;
			break;
		case PAGO_TESORERIA_GOBIERNO_DISTRITO_FEDERAL:
			
			modelMap.addAttribute("listComprobantes", comprobantesExito);
			
			ImpuestoGDF compGDF = historialService.getComprobanteGDF(comprobantesExito.get(0));
			
			cuentasHistorialService.setInfoCuentaOrigen(compGDF, afirmeNetUser.getContrato(), tipo.getValor());
			cuentasHistorialService.setInfoCuentaDestino(compGDF, afirmeNetUser.getContrato(), tipo);
			
			compGDF.setProgrammingHour(comprobantesExito.get(0).getProgrammingHour());
			compGDF.setProgrammingMinute(comprobantesExito.get(0).getProgrammingMinute());
			compGDF.setConceptoGDF(impuestosGDFService.getConcepto(compGDF.getLineaCaptura()));
			compGDF.setLineaCapturaBase(impuestosGDFService.getBaseLineaCaptura(compGDF.getLineaCaptura(), compGDF.getImporte()));
			
			//historialService.getTramiteyConcepto(compGDF);
			
			compGDF.getAtributosGenericos().put("tipoOperacion", compGDF.getConcepto());
			modelMap.addAttribute("pago", compGDF);
			
			modelView = AfirmeNetWebConstants.MV_PAGOS_IMPUESTOS_GDF_COMPROBANTE;
			break;
			
		case VENTA_DE_SEGUROS_ANTIFRAUDE:
			modelMap.addAttribute("listComprobantes", comprobantesExito);
			SeguroCardif seguroCardif=(SeguroCardif)comprobante;

			seguroCardif.setPaquete(comprobante.getDescription());
			seguroCardif.setPaqueteDescripcion(comprobante.getNarrative());
			seguroCardif.setPoliza(comprobante.getBeneficiaryName());
			modelMap.addAttribute("seguroCardif", (SeguroCardif)comprobante);
			modelView = AfirmeNetWebConstants.MV_PAGOS_SEGURO_COMPROBANTE;
			break;
		case DOMICILIACION_DE_SERVICIOS_BASICOS:
			modelMap.addAttribute("listComprobantes", comprobantesExito);
			ConvenioDomiciliacion conv=	historialService.buscarInfoExtraConvenioServicios(afirmeNetUser.getContrato(), comprobantesExito.get(0).getReferenceNumber());
			cuentasHistorialService.setInfoCuentaOrigen(conv, afirmeNetUser.getContrato(), tipo.getValor());
			cuentasHistorialService.setInfoCuentaDestino(conv, afirmeNetUser.getContrato(), tipo);
			modelMap.addAttribute("convenio", conv);
			modelView = AfirmeNetWebConstants.MV_PAGOS_DOMICILIACION_COMPROBANTE;
			break;
		case PROGRAMACION_DE_PAGOS_AFIRME_DOMICILIACION:
			modelMap.addAttribute("accion", comprobantesExito.get(0).getAccion());
			modelMap.addAttribute("listTransferencias", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TDCDOMI_COMPROBANTE;
			break;		
		case CANCELACION_DE_PROGRAMACION_DE_PAGOS_AFIRME_DOMICILIACION:
			modelMap.addAttribute("accion", comprobantesExito.get(0).getAccion());
			modelMap.addAttribute("listTransferencias", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TDCDOMI_COMPROBANTE;
			break;
		case PAGO_DE_IMPUESTOS_DEPOSITO_REFERENCIADO:
			modelMap.addAttribute("pago",(DepositoReferenciado)comprobante);
			modelMap.addAttribute("listTransferencias", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_PAGOS_IMPUESTOS_REFERENCIADO_COMPROBANTE;
			break;
		case INVERSION_DIARIA:
			modelMap.addAttribute("listTransferencias", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_INVERSIONES_DIARIA_COMPROBANTE;
			break;
		case EMISION_DE_ESTADO_DE_CUENTA:
			//OJOS
			List<EstadoCuenta> edosCuentaEmitidos = new ArrayList<EstadoCuenta>();
			EstadoCuenta edoCtaEmi = (EstadoCuenta) comprobantesExito.get(0);
			
			Cuenta cueEdoCtaEmi = cuentaService.getCuenta(edoCtaEmi.getContrato(), edoCtaEmi.getNumCuenta().trim(), afirmeNetUser.getCuentasExcluyentes());
			edoCtaEmi.setDescriCuenta(cueEdoCtaEmi.getDescription());
			
			edosCuentaEmitidos.add(edoCtaEmi);
			
			modelMap.addAttribute("estadoCuenta", edoCtaEmi);
			modelMap.addAttribute("edosCuentaActivados", edosCuentaEmitidos);
			modelView = AfirmeNetWebConstants.MV_CONSULTAS_EDO_CTA_ACTIVACION_COMPROBANTE;
			break;
		case ESTATUS_SERVICIO_EMISION_ESTADO_DE_CUENTA:
			List<EstadoCuenta> edosCuentaActivados = new ArrayList<EstadoCuenta>();
			EstadoCuenta edoCta = (EstadoCuenta) comprobantesExito.get(0);
			boolean flag = true;
			
			Cuenta cueEdoCta = cuentaService.getCuenta(edoCta.getContrato(), edoCta.getNumCuenta().trim(), afirmeNetUser.getCuentasExcluyentes());
			if (cueEdoCta != null){
				edoCta.setDescriCuenta(cueEdoCta.getDescription());
				edoCta.setNickName(cueEdoCta.getNickname());
				flag = false;
			}
			if(flag){
				// TIRA BUSQUEDA A TARJETAS DE CRÉDITO				
				List<TC5000PF> listTdc = tdcService.getListaTDC(afirmeNetUser.getNumCliente());
				
				//COMPARA EL RESULTADO CON EL VALOR DE edoCuentasList Y SETEA LLOS VALORES FALTANTES (descricuenta, nickName)
				for(TC5000PF tdc : listTdc){
					if(edoCta.getNumCuenta().trim().equals(tdc.getTCTARJ().trim())){
						edoCta.setDescriCuenta("TARJETA DE CREDITO".concat(" - " + tdc.getTCCLTA()));
						edoCta.setNickName(tdc.getTCNOMB());
						flag = false;
						break;
					}
				}
			}
//			SECCION PARA FONDOS
//			if(flag){
//				// TIRA BUSQUEDA A TARJETAS DE CRÉDITO				
//				List<TC5000PF> listTdc = tdcService.getListaTDC(afirmeNetUser.getNumCliente());
//				
//				//COMPARA EL RESULTADO CON EL VALOR DE edoCuentasList Y SETEA LLOS VALORES FALTANTES (descricuenta, nickName)
//				for(TC5000PF tdc : listTdc){
//					if(edoCta.getNumCuenta().trim().equals(tdc.getTCTARJ().trim())){
//						edoCta.setDescriCuenta("TARJETA DE CREDITO".concat(" - " + tdc.getTCCLTA()));
//						edoCta.setNickName(tdc.getTCNOMB());
//						flag = false;
//						break;
//					}
//				}
//			}
			
			if(edoCta.getAccion().equals("D")){
				edoCta.setFolio(edoCta.getNumRefere());
			}
			
			edosCuentaActivados.add(edoCta);
			
			modelMap.addAttribute("estadoCuenta", edoCta);
			modelMap.addAttribute("edosCuentaActivados", edosCuentaActivados);
			modelView = AfirmeNetWebConstants.MV_CONSULTAS_EDO_CTA_ACTIVACION_COMPROBANTE;
			break;
		case CAMBIO_DE_LIMITES_DE_TRANSFERENCIAS:
			LimitesCuentaAfirme limCue = (LimitesCuentaAfirme)comprobantesExito.get(0);
			
			//Asigna informacion faltante de la cuenta
			Cuenta cue = cuentaService.getCuenta(limCue.getContrato(), limCue.getCuenta().getNumber(), afirmeNetUser.getCuentasExcluyentes());			
			limCue.getCuenta().setDescription(cue.getDescription());
			
			//Consulta todas las transacciones y las enlista
			List<Transacciones> listLimites = comprobanteTransferenciaDao.getInfoLimTrans(limCue);
			
			modelMap.addAttribute("valTransacciones",listLimites);
			modelMap.addAttribute("cuentaAfirme",limCue);
			
			if (limCue.getAccion().toString().equals("Afirme")){
				modelView = AfirmeNetWebConstants.MV_CONFIGURACIONES_LIMITES_CUENTASAFIRME_COMPROBANTELIMITES;
			}else if (limCue.getAccion().toString().equals("Otros")){
				modelView = AfirmeNetWebConstants.MV_CONFIGURACIONES_LIMITES_CUENTASOTRSBANCOS_COMPROBANTELIMITES;
			}else if (limCue.getAccion().toString().equals("TDC")){
				modelView = AfirmeNetWebConstants.MV_CONFIGURACIONES_LIMITES_CUENTASTDC_COMPROBANTELIMITES;
			}else if (limCue.getAccion().toString().equals("Servicios")){
				modelView = AfirmeNetWebConstants.MV_CONFIGURACIONES_LIMITES_CUENTASSERVICIOS_COMPROBANTELIMITES;
			}else if (limCue.getAccion().toString().equals("Impuestos")){
				modelView = AfirmeNetWebConstants.MV_CONFIGURACIONES_LIMITES_CUENTASPAGOIMPUESTOS_COMPROBANTELIMITES;
			}
			
			break;
		case CAMBIO_DE_LIMITES_TARJETA_DE_DEBITO:
			modelMap.addAttribute("tarjetasDebito",(TarjetaDebito)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_SERVICIOS_TARJETADEBITO_CAMBIOLIMITES_COMPROBANTE;
			break;
		case CAMBIO_DE_ESTATUS_TARJETA_DE_DEBITO:
			modelMap.addAttribute("tarjDeb",(TarjetaDebito)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_SERVICIOS_TARJETADEBITO_CAMBIOESTATUS_COMPROBANTEESTATUS;
			break;
		case ASOCIACION_DE_CUENTA_A_NUMERO_MOVIL:
			AsociaMovil asociaMovil = (AsociaMovil)comprobantesExito.get(0);
			modelMap.addAttribute("asociaMovil", asociaMovil);
			if(asociaMovil.getAccion().equalsIgnoreCase("M") && asociaMovil.getNumCelAnt().equalsIgnoreCase("0")){
				//Asociación
				modelView = AfirmeNetWebConstants.MV_SERVICIOS_MOVIL_ASOCIA_COMPROBANTE;
			}else if(asociaMovil.getAccion().equalsIgnoreCase("M")  && !asociaMovil.getNumCelAnt().equalsIgnoreCase("0")){
				//Modificacion
				modelView = AfirmeNetWebConstants.MV_SERVICIOS_MOVIL_MODIFICA_ASOCIA_COMPROBANTE;
			}else if(asociaMovil.getAccion().equalsIgnoreCase("D")){
				//Desasocia
				modelView = AfirmeNetWebConstants.MV_SERVICIOS_MOVIL_DESASOCIA_COMPROBANTE;				
			}
			break;
		case ALERTAS_AFIRME:
			modelMap.addAttribute("alertas",(Alertas)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_SERVICIOS_ALERTAS_COMPROBANTE;
			break;
		case AFIRME_MOVIL:
			modelMap.addAttribute("movil",(Movil)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_AFIRMEMOVIL_COMPROBANTE;
			break;	
		case ACTIVACION_DE_TARJETA_DE_CREDITO:
			modelMap.addAttribute("listTransferencias", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TDCACTIVA_COMPROBANTE;
			break;
			
		case ORDENES_DE_PAGO_CASH_EXPRESS:
			modelMap.addAttribute("listComprobantes", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_CASH_EXPRESS_EMITIR_COMPROBANTE;
			break;
		
		case AVISO_DE_VIAJE:
			modelMap.addAttribute("avisoViaje", obtenerAvisoViajeDTO(comprobante) );
			modelView = AfirmeNetWebConstants.MV_SERVICIOS_TARJETAS_AVISO_DE_VIAJE_COMPROBANTE;
			break;
			
		case CAMBIO_DE_ALIAS:
			modelMap.addAttribute("aliasCuentaAfirme", obtenerAliasAvatarDTO(comprobante) );
			modelMap.addAttribute("pathAvatar", AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.AVATAR_PATH));
			modelView = AfirmeNetWebConstants.MV_CONFIGURACIONES_SEGURIDAD_CAMBIOALIASAVATAR_COMPROBANTE;
			break;
		
		case CAMBIO_DE_CONTRASENA:
			ContrasenaDTO contrasenaDTO = new ContrasenaDTO();
			
			contrasenaDTO.setEsReimpresion(true);
			contrasenaDTO.setFechaOperacionddMMYY( (String)comprobante.getAtributosGenericos().get("fecha") );                      
			contrasenaDTO.setHoraOperacionHHmm( (String)comprobante.getAtributosGenericos().get("hora") );
			contrasenaDTO.setContrato( comprobante.getClientId() );
			
			modelMap.addAttribute("contrasenaCuentaAfirme",contrasenaDTO);
			modelView = AfirmeNetWebConstants.MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCONTRASENA_COMPROBANTE;
			break;
		
		case CAMBIO_DE_CORREO_ELECTRONICO:
			CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
			
			correoElectronicoDTO.setEsReimpresion(true);
			correoElectronicoDTO.setFechaOperacionddMMYY( (String)comprobante.getAtributosGenericos().get("fechaOperacion") );
			correoElectronicoDTO.setHoraOperacionHHmm( (String)comprobante.getAtributosGenericos().get("horaOperacion") );
			correoElectronicoDTO.setFechaActivacionddMMYY( (String)comprobante.getAtributosGenericos().get("fechaActivacion") );                    
			correoElectronicoDTO.setHoraActivacionHHmm( (String)comprobante.getAtributosGenericos().get("horaActivacion") );
			correoElectronicoDTO.setCorreoActual( (String)comprobante.getAtributosGenericos().get("correoAnterior") );
			correoElectronicoDTO.setCorreoNuevo( (String)comprobante.getAtributosGenericos().get("correoNuevo") );
			correoElectronicoDTO.setContrato( comprobante.getClientId() );
			String cadena24horas = propertyService.obtenerTiempoEspera(ConfigProperties.PROPERTYID_EMAIL_CHANGE);
			String mensajeUsuario24Horas = Util.getPropertyString("afirmenet.etiqueta.conf.correo.comprobante.nota.cambio.realizara.en.24.horas", new Object[]{cadena24horas});
			correoElectronicoDTO.setMensajeUsuario24Horas(mensajeUsuario24Horas);
			
			modelMap.addAttribute("correoCuentaAfirme",correoElectronicoDTO);
			modelView = AfirmeNetWebConstants.MV_CONFIGURACIONES_SEGURIDAD_CAMBIOCORREO_COMPROBANTE;
			break;
		
		case REGISTRO_DE_TARJETAS_DE_CREDITO_Y_SEGUROS_AFIRME:
			DC_CONVENIO convenio = comprobanteTransferenciaDao.
					buscarComprobanteRegistroTarjetas(comprobantesExito.get(0).getContractId(), comprobantesExito.get(0).getReferenceNumber());
			convenio.setEsReimpresion(true);
			modelMap.addAttribute("convenio", convenio);
			modelView = AfirmeNetWebConstants.MV_CTALTATARJ_COMPROBANTE;
			break;
		
		case ACTIVACION_DE_SERVICIOS:
			modelMap.addAttribute("servicio", comprobante);
			modelView = AfirmeNetWebConstants.MV_SERVICIOS_ACTIVA_COMPROBANTE;
			break;
		case TRASPASO_PUNTOS_BONUS:			
			 comprobantesExito.get(0).getDestino().setNumber( comprobante.getCreditAccount() );
			 comprobantesExito.get(0).getDestino().setNickname( comprobante.getBeneficiaryName() );
			modelMap.addAttribute("listTransferencias", comprobantesExito);
			modelMap.addAttribute("conversionPuntosAPesos", comprobante.getAmount().multiply( comprobante.getOrigen().getFactorBonus() ) );
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_BONUS_COMPROBANTE;
			break;
		case CANCELACION_DE_OPERACIONES_PROGRAMADAS:
			modelMap.addAttribute("listComprobantes", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_OPERACIONES_PROGRAMADAS_COMPROBANTE;
			break;			
		case CAMBIO_DE_LIMITES_TARJETA_CREDITO_ADICIONAL:
			modelMap.addAttribute("listTransferencias", comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_TDCLIMITE_COMPROBANTE;
			break;
		case ELIMINACION_DE_CUENTAS_DESTINO:
			String LOCAL_ESTATUS_ELIMINACION_CUENTA = "AUTORIZADA POR BANCO";
			CuentasDestinoDTO cuentasDestinoDTO = new CuentasDestinoDTO();
			
			cuentasDestinoDTO.setEsReimpresion(true);
			cuentasDestinoDTO.setFechaOperacionddMMYY( (String)comprobante.getAtributosGenericos().get("fecha") );
			cuentasDestinoDTO.setHoraOperacionHHmm( (String)comprobante.getAtributosGenericos().get("hora") );
			cuentasDestinoDTO.setNombreTipoOperacionSeleccionada( (String)comprobante.getAtributosGenericos().get("nombreTipoOperacionSeleccionada") );
			cuentasDestinoDTO.setContrato( comprobante.getClientId() );
			
			List<EliminarCuentaDestinoDTO> listaCuentasSeleccionadas = new ArrayList<EliminarCuentaDestinoDTO>();
			EliminarCuentaDestinoDTO eliminarCuentaDestinoDTO = new EliminarCuentaDestinoDTO();
			eliminarCuentaDestinoDTO.setNombreTransaccion( (String)comprobante.getAtributosGenericos().get("nombreTransaccion") );
			eliminarCuentaDestinoDTO.setNombreTipoOperacion( (String)comprobante.getAtributosGenericos().get("nombreTipoOperacion") );
			eliminarCuentaDestinoDTO.setNumeroCuenta( (String)comprobante.getAtributosGenericos().get("numeroCuenta") );
			eliminarCuentaDestinoDTO.setNombreBanco( (String)comprobante.getAtributosGenericos().get("nombreBanco") );
			eliminarCuentaDestinoDTO.setNombreDueno((String)comprobante.getAtributosGenericos().get("nombreDueno") );
			eliminarCuentaDestinoDTO.setNombreEstatus( LOCAL_ESTATUS_ELIMINACION_CUENTA );
			eliminarCuentaDestinoDTO.setNumeroTipoOperacion( (String)comprobante.getAtributosGenericos().get("numeroTipoOperacion") );
			listaCuentasSeleccionadas.add( eliminarCuentaDestinoDTO );
			cuentasDestinoDTO.setListaCuentasSeleccionadas(listaCuentasSeleccionadas); // listaCuentasSeleccionadas
			
			modelMap.addAttribute("cuentasDestino",cuentasDestinoDTO);
			modelView = AfirmeNetWebConstants.MV_CT_ELIMINAR_CUENTA_COMPROBANTE;
			break;
		case PAGO_REFERENCIADO:
			Servicio pago= (Servicio)comprobante;
			modelMap.addAttribute("pago", pago);
			modelMap.addAttribute("listTransferencias",comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_PAGOS_SERVICIO_REFERENCIADO_COMPROBANTE;
			break;			
		case PROTECCION_DE_CHEQUES:
			RespuestaProteccionCheque respuestaProteccion  = comprobanteTransferenciaDao.buscarProteccionCheque (comprobantesExito.get(0).getReferenceNumber(),
					comprobantesExito.get(0).getContractId(),comprobantesExito.get(0).getDebitAccount());
			respuestaProteccion.setValidationDay(comprobantesExito.get(0).getProgrammingDay());
			respuestaProteccion.setValidationMonth(comprobantesExito.get(0).getProgrammingMonth());
			respuestaProteccion.setValidationYear(comprobantesExito.get(0).getProgrammingYear());
			CuentaChequera cuenta= new CuentaChequera(comprobantesExito.get(0).getDebitAccount(), comprobantesExito.get(0).getBeneficiaryName(), "", null);
			modelMap.addAttribute("esReimpresion", true);
			modelMap.addAttribute("cuenta", cuenta);
			modelMap.addAttribute("respuestaProteccion", respuestaProteccion);
			modelView = AfirmeNetWebConstants.MV_PROTECCION_CHEQUE_COMPROBANTE;
			break;			
		case CHEQUES_EXTRAVIADOS:
			BusquedaChequeResultado respuestaCancelar  = comprobanteTransferenciaDao.buscarCancelaCheque (comprobantesExito.get(0).getReferenceNumber(),
					comprobantesExito.get(0).getContractId(),comprobantesExito.get(0).getDebitAccount());
			respuestaCancelar.setValidationDay(comprobantesExito.get(0).getProgrammingDay());
			respuestaCancelar.setValidationMonth(comprobantesExito.get(0).getProgrammingMonth());
			respuestaCancelar.setValidationYear(comprobantesExito.get(0).getProgrammingYear());
			modelMap.addAttribute("esReimpresion", true);			
			modelMap.addAttribute("respuesta", respuestaCancelar);
			modelView = AfirmeNetWebConstants.MV_CANCELAR_CHEQUE_COMPROBANTE;
			break;
		case SOLICITUD_DE_CHEQUERAS:
			RespuestaSolicitudChequera respuestaSolicitudChequera = 
			new RespuestaSolicitudChequera(DaoUtil.getDate(comprobantesExito.get(0).getValidationYear()+comprobantesExito.get(0).getValidationMonth()+comprobantesExito.get(0).getValidationDay()+comprobantesExito.get(0).getValidationHour()+comprobantesExito.get(0).getValidationMinute(), "yyMMddhhmm"),
										   null, "", "", comprobantesExito.get(0).getReferenceNumber(), comprobantesExito.get(0).getDebitAccount(), comprobantesExito.get(0).getContractId());
						
			modelMap.addAttribute("esReimpresion", true);			
			modelMap.addAttribute("respuesta", respuestaSolicitudChequera);
			modelView = AfirmeNetWebConstants.MV_SOLICITUD_CHEQUERA_COMPROBANTE_CUENTA;
			break;
		case PAGARE_AHORRAFIRME:
			modelMap.addAttribute("listComprobantes",comprobantesExito);
			modelMap.addAttribute("pagare",(Inversion)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_PAGARE_AHORRAFIRME_COMPROBANTE;
			break;
		case PAGARE_GRADUAL:
			modelMap.addAttribute("listComprobantes",comprobantesExito);
			modelMap.addAttribute("pagare",(Inversion)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_PAGARE_GRADUAL_COMPROBANTE;
			break;
		case PAGARE_MULTIPLE:
			modelMap.addAttribute("listComprobantes",comprobantesExito);
			modelMap.addAttribute("pagare",(Inversion)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_PAGARE_MULTIPLE_COMPROBANTE;
			break;
		case PAGO_DE_IMPUESTOS:
			
			byte[] finalReport =impuestosService.getPDF(comprobantesExito.get(0).getReferenceNumber());
			modelMap.addAttribute("finalReport", finalReport);
			Impuesto impuesto = new Impuesto();
			modelMap.addAttribute("impuesto", impuesto);
			modelView = AfirmeNetWebConstants.MV_PAGOS_IMPUESTOS_FED_REIMP_COMPROBANTE;
			break;
		case MIS_CREDITOS_AFIRME:
			Credito credito=(Credito)comprobante;
			try{
			BigDecimal bd=new BigDecimal(credito.getBankReceiving().trim());
			credito.setSaldoCapital(bd);
			}catch(Exception ex){}

			modelMap.addAttribute("credito", credito);
			modelView = AfirmeNetWebConstants.MV_MIS_CREDITOS_COMPROBANTE;
			break;
		case INVERSION_PERFECTA:
			Inversion inversion=(Inversion)comprobante;
			modelMap.addAttribute("inversion", inversion);
			modelView = AfirmeNetWebConstants.MV_INVERSIONES_PERFECTA_COMPROBANTE;
			break;
		case PORTABILIDAD_DE_NOMINA:
			modelMap.addAttribute("listComprobantes",comprobantesExito);
			modelMap.addAttribute("nomina",(Portabilidad)comprobantesExito.get(0));
			modelView = AfirmeNetWebConstants.MV_PORTABILIDAD_COMPROBANTE;
			break;
		case RECARGA_DE_TIEMPO_AIRE_TELCEL:
			String nombre=recargasService.obtenerNombreEmpresaTelefonicaPorId("1");
			
			for(TransferenciaBase comprobanteRecarga:comprobantesExito){
				comprobanteRecarga.setState("1");
				comprobanteRecarga.setBeneficiaryName(nombre);
				comprobanteRecarga.setFlag(recargasService.obtenerFolioRecarga(comprobanteRecarga.getAfirmeNetReference()));
			}
			
			modelMap.addAttribute("listTransferencias",comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_RECARGAS_COMPROBANTE;
			break;
		case RECARGA_DE_TIEMPO_AIRE_MOVISTAR:
			String nombreM=recargasService.obtenerNombreEmpresaTelefonicaPorId("2");
			
			for(TransferenciaBase comprobanteRecarga:comprobantesExito){
				comprobanteRecarga.setState("1");
				comprobanteRecarga.setBeneficiaryName(nombreM);
				comprobanteRecarga.setFlag(recargasService.obtenerFolioRecarga(comprobanteRecarga.getAfirmeNetReference()));
			}
			modelMap.addAttribute("listTransferencias",comprobantesExito);
			modelView = AfirmeNetWebConstants.MV_RECARGAS_COMPROBANTE;
			break;
		default:
			break;
		}
		return modelView;
	}
	
	/**
	 * Obtener los valores para avisoViajeDTO que es el objeto que utiliza el
	 * comprobante de Aviso de Viaje.
	 * 
	 * @param comprobante de tipo TransferenciaBase
	 * @return avisoViajeDTO de tipo AvisoViajeDTO
	 */
	private AvisoViajeDTO obtenerAvisoViajeDTO( TransferenciaBase comprobante){
		AvisoViajeDTO avisoViajeDTO = new AvisoViajeDTO();
		
		avisoViajeDTO.setEsReimpresion(true);
		avisoViajeDTO.setNumeroDeReferencia400( comprobante.getReferenceNumber() );
		avisoViajeDTO.setRemarkParaDCLOG( comprobante.getTransaccionDescrip() );
		
		// completar datos del comprobante
		comprobanteTransferenciaDao.completarDatosAvisoViaje(avisoViajeDTO);
		
		return avisoViajeDTO;
	}
	
	/**
	 * Para formar el objeto AliasAvatarDTO que utiliza el comprobante de cambio de alias.
	 * 
	 * @param comprobante de tipo TransferenciaBase
	 * @return AliasAvatarDTO
	 */
	private AliasAvatarDTO obtenerAliasAvatarDTO( TransferenciaBase comprobante ){
		
		AliasAvatarDTO aliasAvatarDTO = new AliasAvatarDTO();
		
		aliasAvatarDTO.setEsReimpresion(true);
		aliasAvatarDTO.setFechaOperacionddMMYY( (String)comprobante.getAtributosGenericos().get("dia") );
		aliasAvatarDTO.setHoraOperacionHHmm( (String)comprobante.getAtributosGenericos().get("hora") );
		aliasAvatarDTO.setAliasActual( (String)comprobante.getAtributosGenericos().get("aliasAnterior") );
		aliasAvatarDTO.setAliasNuevo( (String)comprobante.getAtributosGenericos().get("aliasNuevo") );
		aliasAvatarDTO.setContrato( comprobante.getClientId() );

		return aliasAvatarDTO;		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/printPDF", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getReimprimirPDF(ModelMap modelMap,
			SessionStatus sessionStatus, HttpServletRequest request) {

		byte[] reporteBytes;
		try {
			reporteBytes = (byte[]) modelMap.get("finalReport");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.setContentDispositionFormData("reimpresion.pdf","reimpresion.pdf");
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
					reporteBytes, headers, HttpStatus.OK);
			
			return response;
		} catch (Exception e) {
			throw new AfirmeNetException("0000",
					"Ocurrio un error al generar el comprobante de pago de impuestos: "
							+ e.getMessage());
		}

	}
	
}