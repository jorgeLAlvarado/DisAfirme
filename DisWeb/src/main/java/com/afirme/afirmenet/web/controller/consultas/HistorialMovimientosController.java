package com.afirme.afirmenet.web.controller.consultas;

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

import com.afirme.afirmenet.model.configuraciones.AliasAvatarDTO;



/**
 * Controller que atiende peticiones de saldos y movimientos
 * 
 * 
 * @author Arturo Ivan Martinez Mata
 * @author epifanio.guzman@afirme.com
 * 
 * Modificado on dic 13, 2016 11:12:21 AM by Bayron 
 * 
 * @author Bayron Gamboa Martinez
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
		
		return null;
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
		
		return null;
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
		
		return null;
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
		
		
		return null;
	}
	
	/**
	 * Obtener los valores para avisoViajeDTO que es el objeto que utiliza el
	 * comprobante de Aviso de Viaje.
	 * 
	 * @param comprobante de tipo TransferenciaBase
	 * @return avisoViajeDTO de tipo AvisoViajeDTO
	 */
	private AvisoViajeDTO obtenerAvisoViajeDTO( TransferenciaBase comprobante){
		
		
		return null;
	}
	
	/**
	 * Para formar el objeto AliasAvatarDTO que utiliza el comprobante de cambio de alias.
	 * 
	 * @param comprobante de tipo TransferenciaBase
	 * @return AliasAvatarDTO
	 */
	private AliasAvatarDTO obtenerAliasAvatarDTO( TransferenciaBase comprobante ){
		
	}
	
}