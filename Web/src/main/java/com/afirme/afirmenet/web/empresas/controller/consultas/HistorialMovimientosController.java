package com.afirme.afirmenet.web.empresas.controller.consultas;

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
import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.consultas.HistorialService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.time.TimeUtils;
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

		comprobantesExito.add(comprobante);
		
		switch (tipo) {
		case TRANSFERENCIA_SPEI:
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_NACIONALES_COMPROBANTE;
			break;
	
			}	
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