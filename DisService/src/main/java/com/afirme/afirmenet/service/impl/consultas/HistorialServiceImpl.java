package com.afirme.afirmenet.service.impl.consultas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.consultas.HistorialDao;
import com.afirme.afirmenet.dao.pagos.ConveniosDomiciliacionDao;
import com.afirme.afirmenet.dao.pagos.ImpuestosGDFDao;
import com.afirme.afirmenet.dao.programadas.OperacionesProgramadasDao;
import com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.dao.transferencia.CuentaTercerosDao;
import com.afirme.afirmenet.enums.TipoServicio;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.ibs.databeans.SPABANPF;
import com.afirme.afirmenet.model.pagos.ConvenioDomiciliacion;
import com.afirme.afirmenet.model.pagos.ImpuestoGDF;
import com.afirme.afirmenet.model.pagos.impuestos.DepositoReferenciado;
import com.afirme.afirmenet.model.servicios.AsociaMovil;
import com.afirme.afirmenet.model.servicios.CiaTelefonica;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.asociaMovilCuenta.AsociaMovilCuentaService;
import com.afirme.afirmenet.service.consultas.HistorialService;
import com.afirme.afirmenet.service.pagos.ImpuestosGDFService;
import com.afirme.afirmenet.utils.time.TimeUtils;

@Service
public class HistorialServiceImpl implements HistorialService {

	@Autowired
	private HistorialDao historialDao;
	
	@Autowired
	private AsociaMovilCuentaService asociaMovilCuentaService;
	
	@Autowired
	private ComprobanteTransferenciaDao comprobanteTransferenciaDao;
	
	@Autowired
	private OperacionesProgramadasDao operacionesProgramadasDao;

	@Autowired
	ConveniosDomiciliacionDao conveniosDao;
	
	@Autowired
	CuentaTercerosDao cuentaTercerosDao;
	
	@Autowired
	private ImpuestosGDFDao impuestoGDFDao;
	
	@Autowired
	private ImpuestosGDFService impuestosGDFService;
	
	public List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken) {
		return historialDao.listaTransacciones(esBasicoSinToken);
	}
	
	@Override
	public List<String> categorias(boolean esBasicoSinToken) {
		return historialDao.categorias(esBasicoSinToken);
	}

	/**
	 * Devuelve una lista de TransferenciaBase que es el tipo de objeto para mostrar la lista de resultados.
	 */
	@SuppressWarnings("unchecked")
	public List<TransferenciaBase> buscaTransferencias(String contrato, List<Cuenta> cuentas, HistorialTipo tipo, Date fechaDesde, Date fechaHasta) {
		String fD=TimeUtils.getDateFormat(fechaDesde, TimeUtils.DB2_DATE_FORMAT);
		String fH=TimeUtils.getDateFormat(fechaHasta, TimeUtils.DB2_DATE_FORMAT);
		List<TransferenciaBase> lista=null;
		
		HistorialTipo tipoParaComparar;
		
		switch (tipo) {
			case ANTICIPO_DE_NOMINA:
			case CREDITO_DE_NOMINA:
				lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesAnticipoCreditoNomina(contrato, tipo.getValor(), fD, fH);
				for(TransferenciaBase comp: lista)
					comp.setTransactionCode(tipo.getValor());
				break;

//		case DOMICILIACION:
//			
//			break;
		case DOMINGO_ELECTRONICO:
			// lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesDomingoElectronico(contrato, tipo.getValor(), fD, fH);
			
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.obtenerComprobantesDomingoElectronico(contrato, tipo.getValor(), fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case TRANSFERENCIAS_INTERNACIONALES_MULTIMONEDA:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesSWIFT(contrato, tipo.getValor(), fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case EMISION_DE_ESTADO_DE_CUENTA:
			//OJOS
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarEmisionesEstadosCuentas(contrato, fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case ORDENES_DE_PAGO_CASH_EXPRESS:
			//OJOS
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesOrdenPago(contrato, cuentas, fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case ESTATUS_SERVICIO_EMISION_ESTADO_DE_CUENTA:
			//OJOS
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarestatusEstadosCuentas(contrato, fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case CAMBIO_DE_LIMITES_DE_TRANSFERENCIAS:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesCambioLimitesTransferencias(contrato, tipo.getValor(), fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case CAMBIO_DE_LIMITES_TARJETA_DE_DEBITO:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesCambioLimitesEstadoTDD(contrato, fD, fH, "1");
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case CAMBIO_DE_ESTATUS_TARJETA_DE_DEBITO:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesCambioLimitesEstadoTDD(contrato, fD, fH, "2");
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case ASOCIACION_DE_CUENTA_A_NUMERO_MOVIL:
			List<CiaTelefonica> ciaTelefonica = asociaMovilCuentaService.getCiasMovilesService();
			lista = (List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesAsociacionNumeroMovil(contrato, fD, fH);
			for(TransferenciaBase comp: lista){
				comp.setTransactionCode(tipo.getValor());
				for(CiaTelefonica ciaTelMov: ciaTelefonica){
					if(ciaTelMov.getCiaNumero().equals(((AsociaMovil)comp).getCiaTelMov())){
						((AsociaMovil)comp).setCiaTelMovDescri(ciaTelMov.getCiaDescri());
					}
				}
			}
			break;
		case ALERTAS_AFIRME:
			lista = (List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesAlertas(contrato, tipo.getValor(), fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case AFIRME_MOVIL:
			lista = (List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesAfirmeMovil(contrato, fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
//		case DOMICILIACION:
//			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesProgramacionPagosTDC(contrato, fD, fH, "");
//			for(TransferenciaBase comp: lista)
//				comp.setTransactionCode(tipo.getValor());
//			break;	
		case AVISO_DE_VIAJE:
			lista = (List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesAvisoDeViaje(contrato, tipo.getValor(), fD, fH );
			break;
			
		case CAMBIO_DE_ALIAS:
			fD=TimeUtils.getDateFormat(fechaDesde, TimeUtils.COMPLETE_DATE_FORMAT);
			fH=TimeUtils.getDateFormat(fechaHasta, TimeUtils.COMPLETE_DATE_FORMAT);
			lista = (List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesCambioDeAlias(contrato, tipo.getValor(), fD, fH );
			break;
		
		case CAMBIO_DE_CONTRASENA:
			String LOCAL_DESC_CAMBIO_CONTRASENIA = "CAMBIO CONTRASENIA";
			lista = (List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesCambioDeContrasenia(contrato, tipo.getValor(), fD, fH, LOCAL_DESC_CAMBIO_CONTRASENIA);
			break;
		
		case CAMBIO_DE_CORREO_ELECTRONICO:
			lista = (List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesCambioDeCorreo(contrato, tipo.getValor(), fD, fH );
			break;
		
		case ELIMINACION_DE_CUENTAS_DESTINO:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesEliminacionCuentasDestino(contrato, tipo.getValor(), fD, fH );
			break;
		
		case REGISTRO_DE_TARJETAS_DE_CREDITO_Y_SEGUROS_AFIRME:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesAltaCuentas(contrato, fD, fH);
			break;
		case ACTIVACION_DE_SERVICIOS:
			fD=TimeUtils.getDateFormat(fechaDesde, TimeUtils.COMPLETE_DATE_FORMAT);
			fH=TimeUtils.getDateFormat(fechaHasta, TimeUtils.COMPLETE_DATE_FORMAT);
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesActivacionServicios(contrato, fD, fH);
			break;
		case PROTECCION_DE_CHEQUES:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesCheques(contrato, fD, fH,"P", tipo.getValor());
			break;
		case CHEQUES_EXTRAVIADOS:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesCheques(contrato, fD, fH,"B", tipo.getValor());
			break;
		case PAGO_SEGUROS_AFIRME:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGenericos(contrato, TipoTransferencia.PAGO_DE_SERVICIOS.getValor(), fD, fH, "20");
			break;
		case CANCELACION_DE_OPERACIONES_PROGRAMADAS:
			lista = (List<TransferenciaBase>) operacionesProgramadasDao.getListOperacionesCancel(contrato, fD, fH);
			break;
		case PAGO_TARJETAS_DE_CREDITO:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGenericos(contrato, TipoTransferencia.PAGO_DE_SERVICIOS.getValor(), fD, fH, "10");
			break;
		case PAGO_REFERENCIADO:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGenericosServicio(contrato, tipo.getValor(), fD, fH, null);
			break;
		case PAGO_DE_IMPUESTOS_DEPOSITO_REFERENCIADO:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGenericosImpuestoSAT(contrato, tipo.getValor(), fD, fH, null);
			break;
		
//		case DOMICILIACION_DE_SERVICIOS_BASICOS:
//			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesDomiciliacionServicios(contrato, fD, fH);
//			for(TransferenciaBase comp: lista)
//				comp.setTransactionCode(tipo.getValor());
//			break;
	
		case TRANSFERENCIAS_INTERNACIONALES_DOLARES:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesSWIFT(contrato, tipo.getValor(), fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case PAGARE_AHORRAFIRME:
		case PAGARE_GRADUAL:
		case PAGARE_MULTIPLE:
			fD=TimeUtils.getDateFormat(fechaDesde, TimeUtils.COMPLETE_DATE_FORMAT);
			fH=TimeUtils.getDateFormat(fechaHasta, TimeUtils.COMPLETE_DATE_FORMAT);
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesPagare(contrato, fD, fH, tipo.getValor());
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
		case PORTABILIDAD_DE_NOMINA:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesPortabilidadNomina(contrato, tipo.getValor(), fD, fH);
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;

		case VENTA_DE_SEGUROS_ANTIFRAUDE:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGenericosCardif(contrato, tipo.getValor(), fD, fH, null);
			break;
		case MIS_CREDITOS_AFIRME:
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGenericosCredito(contrato, tipo.getValor(), fD, fH, null);
			break;
		case INVERSION_PERFECTA:
			fD=TimeUtils.getDateFormat(fechaDesde, TimeUtils.COMPLETE_DATE_FORMAT);
			fH=TimeUtils.getDateFormat(fechaHasta, TimeUtils.COMPLETE_DATE_FORMAT);
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesInversionPerfecta(contrato, tipo.getValor(), fD, fH);
			break;
			
		case PAGO_TESORERIA_GOBIERNO_DISTRITO_FEDERAL:
			
			lista = (List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGDF(contrato, tipo.getValor(), fD, fH);
			//lista = new ArrayList<TransferenciaBase>();
			
			for(TransferenciaBase comp: lista)
				comp.setTransactionCode(tipo.getValor());
			break;
			
		default:
			//Todo lo demas
			lista=(List<TransferenciaBase>) comprobanteTransferenciaDao.buscarComprobantesGenericos(contrato, tipo.getValor(), fD, fH, null);
			break;
		}
		return lista;
	}

	@Override
	public void obtenerInformacionExtra(TransferenciaBase comprobante) {
		//Comprobante comp=new Comprobante(comprobante);
		HistorialTipo tipo= HistorialTipo.findByValue(comprobante.getTransactionCode());
		switch(tipo){
			case TRANSFERENCIA_SPEI:
				//SPEI
				// Se verifica si ya existe en la tabla de informacion de spei
				if(comprobanteTransferenciaDao.existeInformacionSPEI(comprobante)){
					//Revisamos si los datos no estan vacios
					if(comprobante.getSpeiReference().trim().length()==0){
						comprobanteTransferenciaDao.obtenerInformacionSPEI(comprobante);
						if(comprobante.getSpeiReference().trim().length()==0)
							comprobanteTransferenciaDao.actualizarInformacionSPEI(comprobante);
					}
				}else{
					comprobanteTransferenciaDao.obtenerInformacionSPEI(comprobante);
					if(comprobante.getSpeiReference().trim().length()==0)
						comprobanteTransferenciaDao.insertaInformacionSPEI(comprobante);
				}
			break;
			case PAGO_DE_SERVICIOS:
				String tipoServicio=comprobanteTransferenciaDao.buscarAtributoINOTR(comprobante.getReferenceNumber(), comprobante.getContractId(), "inoba3");
				String nombreServicio= comprobanteTransferenciaDao.buscarNombreServicio(tipoServicio);
				comprobante.setTipoServicio(TipoServicio.findByValorComercial(tipoServicio.trim()));
				DC_CONVENIO conv = new DC_CONVENIO();
				conv.setSERNOM(nombreServicio);
				comprobante.setConvenio(conv);
				if(comprobante.getTipoServicio()==TipoServicio.SEGUROS_AFIRME)
					comprobante.setType("4");
			break;
			case ORDENES_DE_PAGO_CASH_EXPRESS:
				String orden=comprobanteTransferenciaDao.getOrdenCashExpress(comprobante.getReferenceNumber(), comprobante.getContractId());
				//String nombreServicio= comprobanteTransferenciaDao.buscarNombreServicio(tipoServicio);
				comprobante.setUserReference(orden);
			break;
			case ACTIVACION_DE_SERVICIOS:
				//Servicio servicio = comprobanteTransferenciaDao.buscarComprobanteActivacionServicios(comprobante.getContractId(), TipoCliente.PERSONAS, comprobante.getServiceType());
		
			break;
			case PAGO_REFERENCIADO:
				String referencia=comprobanteTransferenciaDao.buscarAtributoINOTR(comprobante.getReferenceNumber(), comprobante.getContractId(), "inoba1");
				comprobante.setUserReference(referencia.trim());
			break;
			case PAGO_DE_IMPUESTOS_DEPOSITO_REFERENCIADO:
				comprobanteTransferenciaDao.obtenerInformacionDepositoReferenciado((DepositoReferenciado)comprobante);
			break;
			case CANCELACION_DE_PROGRAMACION_DE_PAGOS_AFIRME_DOMICILIACION:
				comprobante.setAccion("C");
			break;
			case PROGRAMACION_DE_PAGOS_AFIRME_DOMICILIACION:
				comprobante.setAccion("A");
			break;
			
			
				
			
		default:
			
			break;
		}
		
		comprobante.setSts(getEstatusInotr(comprobante.getReferenceNumber(), comprobante.getContractId()));
	}

	@Override
	public SPABANPF getBanco(BigDecimal numeroBanco) {
		return cuentaTercerosDao.getBanco(numeroBanco);
	}

	@Override
	public ConvenioDomiciliacion buscarInfoExtraConvenioServicios(
			String contrato, String referencia) {
		
		return comprobanteTransferenciaDao.buscarComprobanteDomiciliacionServicios(contrato, referencia);
	}
	
	
	public ImpuestoGDF getComprobanteGDF(TransferenciaBase comprobante){
		return comprobanteTransferenciaDao.buscarComprobanteGDF(comprobante.getReferenceNumber(), comprobante.getContractId());
	}
	
	public ImpuestoGDF getTramiteyConcepto(ImpuestoGDF comprobante){
		return impuestoGDFDao.getInfoImpuestoGDF(comprobante);
	}
	
	public String getEstatusInotr(String referencia, String contrato){
		String STS=comprobanteTransferenciaDao.buscarAtributoINOTR(referencia, contrato, "INOSTS");

	      if(STS.equals("A"))
	      {
	         STS = "Pendiente";
	      }
	      else if(STS.equals("P"))
	      {
	         STS = "Exitosa";
	      }
	      else if(STS.equals("R"))
	      {
	         STS = "Rechazada";
	      }
	      else if(STS.equals("X"))
	      {
	         STS = "Cancelada";
	      }
	      else if(STS.equals("V"))
	      {
	         STS = "Reversada";
	      }
	      else if(STS.equals("I"))
	      {
	         STS = "Incompleta";
	      }
	      
		return STS;
	}
}
