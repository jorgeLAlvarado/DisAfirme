package com.afirme.afirmenet.dao.transferencia;

import java.util.List;

import com.afirme.afirmenet.enums.TipoCliente;
import com.afirme.afirmenet.ibs.beans.configuraciones.chequera.BusquedaChequeResultado;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.model.configuraciones.LimitesCuentaAfirme;
import com.afirme.afirmenet.model.configuraciones.Transacciones;
import com.afirme.afirmenet.model.configuraciones.chequera.RespuestaProteccionCheque;
import com.afirme.afirmenet.model.pagos.ConvenioDomiciliacion;
import com.afirme.afirmenet.model.pagos.ImpuestoGDF;
import com.afirme.afirmenet.model.pagos.impuestos.DepositoReferenciado;
import com.afirme.afirmenet.model.pagos.servicios.Servicio;
import com.afirme.afirmenet.model.servicios.AvisoViajeDTO;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;

public interface ComprobanteTransferenciaDao {

	public void insertaConfirmacionOperacion(Comprobante comprobante);
	
	public List<?> buscarComprobantesGenericos(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio);

	//04 y 51  Reimpresion de Transferencias Internacionales (USD y Multimoneda)
	public List<?> buscarComprobantesSWIFT(String contrato, String tipo, String fechaDesde, String fechaHasta);

	//37  Domigo Electronico
	public List<TransferenciaBase> buscarComprobantesDomingoElectronico(String contrato, String tipo, String fechaDesde, String fechaHasta);

	//49  GDF
	public List<?> buscarComprobantesGDF(String contrato, String tipo, String fechaDesde, String fechaHasta);

	//59 Credi100 
	public List<Comprobante> buscarComprobantesCredi100(String contrato, String tipo, String fechaDesde, String fechaHasta);
	
	//CLTD y CETB Lista de Reimpresion de Recibos de Cambio de Limites en Tarjeta Debito
	public List<?> buscarComprobantesCambioLimitesEstadoTDD( String contrato, String fechaDesde, String fechaHasta, String tipoMovimineto);
	
	//ECSE  Lista de Reimpresion de Comprobantes de eliminación de cuentas SERVICIOS
	public List<Comprobante> buscarComprobantesEliminacionCuentasServicios(String contrato, String fechaDesde, String fechaHasta, String tipoIni, String tipoIni2);

	//COP Cancelacion de operaciones 
	public List<Comprobante> buscarComprobantesCancelacionOperaciones(String contrato, String fechaDesde, String fechaHasta);

	//CLTR Cambio de limites de transferencias 
	public List<?> buscarComprobantesCambioLimitesTransferencias(String contrato, String tipo, String fechaDesde, String fechaHasta);
	
	//CEST Estatus de estados de cuenta 
	public List<?> buscarestatusEstadosCuentas(String contrato, String fechaDesde, String fechaHasta);
	
	//60 Emisiones de estados de cuenta 
	public List<?> buscarEmisionesEstadosCuentas(String contrato, String fechaDesde, String fechaHasta);
	
	//40 y  41 Cheques extraviados y proteccion de cheques
	public List<BusquedaChequeResultado> buscarComprobantesChequesExtraviadosProteccion(String contrato, String fechaDesde, String fechaHasta, String estado);
	
	//Generico por descripcion
	public List<Comprobante> buscarComprobantesGenericosPorDescripcion(String contrato, String fechaDesde, String fechaHasta, String descripcion);
	
	//AMPLIM Ampliacion linea de credito
	public List<Comprobante> buscarComprobantesApliacionLineaCredito(String contrato,  String fechaDesde, String fechaHasta);
	public ConvenioDomiciliacion buscarComprobanteDomiciliacionServicios(String contrato, String referencia);
	
	/**
	 * Para obtener los comprobantes de cambio de alias.
	 * 
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List&lt;Comprobante&gt;
	 */
	public List<?> buscarComprobantesCambioDeAlias(String contrato, String tipo, String fechaDesde, String fechaHasta);
	
	//69 Historial  Servicio Afirme Movil
	public List<?> buscarComprobantesAfirmeMovil(String contrato,  String fechaDesde, String fechaHasta);
	

	//70 y 72 Anticipo y credito de nomina
	public List<?> buscarComprobantesAnticipoCreditoNomina(String contrato, String tipo, String fechaDesde, String fechaHasta);

	//74 Reposicion de tarjeta
	public List<Comprobante> buscarComprobantesReposicionTarjeta(String contrato, String tipo, String fechaDesde, String fechaHasta);

	//65 InversionPerfecta
	public List<Comprobante> buscarComprobantesInversionPerfecta(String contrato, String fechaDesde, String fechaHasta);

	//67 Activacion de servicios
	public List<?> buscarComprobantesActivacionServicios(String contrato, String fechaDesde, String fechaHasta);

	//68, 88, 89 Pagare ahorrafirme
	public List<?> buscarComprobantesPagare(String contrato, String fechaDesde, String fechaHasta, String tipos);

	//82 Domiciliacion de servicios
	public List<?> buscarComprobantesDomiciliacionServicios(String contrato, String fechaDesde, String fechaHasta);
	

	//94 Asociacion de numero movil SPEI
	public List<?> buscarComprobantesAsociacionNumeroMovil(String contrato, String fechaDesde, String fechaHasta);
	
	//Varios INOTR Generico
	public List<Comprobante> buscarComprobantesINOTRGenerico(String contrato, String tipo, String fechaDesde, String fechaHasta);
	
	//290 291 
	public List<Comprobante> buscarComprobantesProgramacionPagosTDC(String contrato, String fechaDesde, String fechaHasta, String programacionCancelacion);

	//91 Alertas
	public List<?> buscarComprobantesAlertas(String contrato, String tipo, String fechaDesde, String fechaHasta);
	
	//80 Orden Pago
	public List<?> buscarComprobantesOrdenPago(String contrato, List<Cuenta> cuenta, String fechaDesde, String fechaHasta);
	
	//292 Alta de cuentas (tarjeta de credito y seguros afirme)
	public List<?> buscarComprobantesAltaCuentas(String contrato, String fechaDesde, String fechaHasta);

	// 86 aviso viaje
	public  List<?> buscarComprobantesAvisoDeViaje(String contrato, String tipo, String fechaDesde, String fechaHasta);

	//Valida si ya existe informacion del SPEI en DB2
	public boolean existeInformacionSPEI(TransferenciaBase comprobante);

	//Obtiene la inforacion del SPEI de AS400
	public void obtenerInformacionSPEI(TransferenciaBase comprobante);

	//Actualuiza la inforacion del SPEI en DB2
	public void actualizarInformacionSPEI(TransferenciaBase comprobante);
	
	//Inserta la inforacion del SPEI en DB2
	public void insertaInformacionSPEI(TransferenciaBase comprobante);
	
	//Inserta la confirmacion de la activacion de tdc en DB2
	public void insertaConfirmacionActivacionTDC(Comprobante comprobante);

	public String buscarAtributoINOTR(String ref, String contrato, String atrib);
	public String buscarNombreServicio(String tipo);
	public String getOrdenCashExpress(String ref, String contrato);
	public List<Transacciones> getInfoLimTrans(LimitesCuentaAfirme comprobante);
	
	/**
	 * Se hace necesario para formar el objeto que se le va a aenviar al view del comprobante Aviso de viaje.
	 * 
	 * @param avisoViajeDTO
	 * @return AvisoViajeDTO
	 */
	public AvisoViajeDTO completarDatosAvisoViaje( AvisoViajeDTO avisoViajeDTO);
	
	public DC_CONVENIO buscarComprobanteRegistroTarjetas(String contrato, String convnum);
	
	public Servicio buscarComprobanteActivacionServicios(String contrato, TipoCliente tipoCliente, int tipo);
	
	/**
	 * Para mostrar los resultados de los comprobantes de cambio de contrasenia.
	 * 
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param descripcion
	 * @return List&lt;Comprobante&gt;
	 */
	public List<?> buscarComprobantesCambioDeContrasenia(String contrato, String tipo, String fechaDesde, String fechaHasta, String descripcion);
	
	/**
	 * Para obtener la lista de resultados de comprobantes de cambio de correo electronico. 
	 * 
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List&lt;Comprobante&gt;
	 */
	public List<?> buscarComprobantesCambioDeCorreo(String contrato, String tipo, String fechaDesde, String fechaHasta);

	
	/**
	 * Para obtener la lista de cheques
	 * 
	 * @param contrato
	 * @param fD
	 * @param fH
	 *  @param estatus P=proteccion B=reporte
	 * @return
	 */
	public List<TransferenciaBase> buscarComprobantesCheques(	String contrato, String fD, String fH, String estatus, String tipo);

	/**
	 * Para obtener comprobante proteccion de cheques
	 */
	public RespuestaProteccionCheque buscarProteccionCheque( String referenceNumber, String contrato, String cuenta);
	/**
	 * Para obtener comprobante cancelar cheques
	 */
	public BusquedaChequeResultado buscarCancelaCheque(String referenceNumber, String contrato, String cuenta);

	/**
	 * Para buscar los comprobantes de eliminacion de cuentas destino.
	 * 
	 * Tiene valores hardcode.
	 * 
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List&lt;Comprobante&gt;
	 */
	public List<?> buscarComprobantesEliminacionCuentasDestino(String contrato, String tipo, String fechaDesde, String fechaHasta);

	public List<?> buscarComprobantesGenericosServicio(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio);
	public List<?> buscarComprobantesGenericosImpuestoSAT(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio);
	public List<?> buscarComprobantesGenericosCardif(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio);
	public List<?> buscarComprobantesGenericosCredito(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio);
	public List<?> buscarComprobantesInversionPerfecta(String contrato, String tipo, String fechaDesde, String fechaHasta);

	//Obtiene la inforacion del PAgo de impuestos deposito Refernenciado
	public void obtenerInformacionDepositoReferenciado(DepositoReferenciado pago);
	
	public void insertaComprobanteProgramacionPagos(TransferenciaBase transferenciaBase);

	ImpuestoGDF buscarComprobanteGDF(String referencia, String contrato);
	public void insertaComprobanteMisCreditos(Comprobante comprobante);
	
	/**
	 *  Para obtener los comprobantes de domingo electronico en la tabla DC_PROGCONCEN.
	 *  Y se completa la hora desde la tabla VW_DC_CONFMSG.
	 *  
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List&lt;TransferenciaBase&gt;
	 */
	public List<?> obtenerComprobantesDomingoElectronico(String contrato, String tipo, String fechaDesde, String fechaHasta);
	

	public void insertaComprobanteInversionPerfecta(Comprobante comprobante);
	
	/**
	 * Inserta confirmacion de recarga
	 * @param comprobante
	 */
	public void almacenarRecargaDB2(Comprobante comprobante);

	public void insertaConfirmacionRecarga(Comprobante comprobante);
	
	/**
	 * 
	 * @param contrato     Contrato afirmenet
	 * @param fechaInicio  rango de fecha inferior 20160101
	 * @param fechaFinal   rango de fecha superior 20160113
	 * @param tipoMovimiento 0 Ambas - 1 Enviadas - 2 recibidas
	 * @return
	 */
	public String obtenerSPEICEP(String contrato, int fechaInicial, int fechaFinal, int tipoMovimiento, String claveRastreo);
	
	//21 Portabilidad de nomina
	public List<?> buscarComprobantesPortabilidadNomina(String contrato, String tipo, String fechaDesde, String fechaHasta);

	public void insertaConfirmacionDepositoReferenciado(Comprobante comprobante);
	
	
}
