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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.afirme.afirmenet.model.Login;
import com.afirme.afirmenet.model.configuraciones.AliasAvatarDTO;
import com.afirme.afirmenet.service.consultas.HistorialService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.controller.base.BaseController;



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
	
		
	/**
	 * Metodo que atiende las peticiones al contexto /resumen-de-mis-cuentas.htm
	 * En el cual se muestran el saldo de todas las cuentas del cliente de manera global 
	 * 
	 * cuando el usuario da click en en menu Consultas > Historial de operaciones
	 * 
	 * @param model
	 * @return pagina JSP
	 */
	
	
	@RequestMapping("/historial_operaciones.htm")
	public String historial(ModelMap model, HttpServletRequest request) {
		LOG.debug("Funciona este metodo");
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
	@RequestMapping(value ="/historial_resultados.htm", method = RequestMethod.POST)
	public String resultados(ModelMap model, HttpServletRequest request) {
		LOG.debug("Funciona este metodo");
		return null;
	}
	
	/**
	 * Para validar una operacion seleccionada, llenar los objetos que utiliza el view del comprobante.
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/historial_comprobante.htm")
	public String comprobante(ModelMap model, HttpServletRequest request) {
		LOG.debug("Funciona este metodo");
		return null;
	}
	
	
	
}