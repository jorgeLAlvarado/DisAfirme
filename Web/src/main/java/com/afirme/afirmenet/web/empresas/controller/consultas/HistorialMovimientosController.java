package com.afirme.afirmenet.web.empresas.controller.consultas;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;
import com.afirme.afirmenet.model.AfirmeNetUser;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.consultas.HistorialService;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.web.empresas.controller.base.BaseController;
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
	 * Para validar una operacion seleccionada, llenar los objetos que utiliza el view del comprobante.
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/historial_comprobante.htm")
	public String comprobante(ModelMap model, HttpServletRequest request) {
		LOG.info("<< comprobante()");
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
		LOG.info(">> comprobante()");
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
		
		LOG.info("<< urlComprobante()");
		HistorialTipo tipo=HistorialTipo.findByValue(comprobante.getTransactionCode());
		String modelView = AfirmeNetWebConstants.MV_CONSULTAS_HISTORIAL_RESULTADOS;
		List<TransferenciaBase> comprobantesExito = new ArrayList<TransferenciaBase>(0);
		comprobante.setEsReimpresion(true);

		comprobantesExito.add(comprobante);
		
		switch (tipo) {
		case TRANSFERENCIAS_INTERNACIONALES_DOLARES:
			LOG.info("Atendiendo Peticion: TRANSFERENCIAS_INTERNACIONALES_DOLARES");
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_DOLARES_COMPROBANTE;
			break;
		case TRANSFERENCIA_SPEI:
			LOG.info("Atendiendo Peticion: TRANSFERENCIA_SPEI");
			modelView = AfirmeNetWebConstants.MV_TRANSFERENCIAS_NACIONALES_COMPROBANTE;
			}
		LOG.info(">> urlComprobante()");
		return modelView;	
	}
	
	/**
	 * 
	 * Metodo utilizado para la impresion de un archivo PDF.
	 * 
	 * 
	 * @param modelMap
	 * @param sessionStatus
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/printPDF", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getReimprimirPDF(ModelMap modelMap,
			SessionStatus sessionStatus, HttpServletRequest request) {

		LOG.info("<<ResponseEntity<byte[]> getReimprimirPDF");
		LOG.info(">>ResponseEntity<byte[]> getReimprimirPDF");
		return null;

	}
	
}