package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.dao.impl.inversiones.PagareDaoImpl;
import com.afirme.afirmenet.dao.pagos.ImpuestosGDFDao;
import com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.enums.TipoCliente;
import com.afirme.afirmenet.ibs.beans.configuraciones.chequera.BusquedaChequeResultado;
import com.afirme.afirmenet.ibs.beans.configuraciones.chequera.Cheque;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;
import com.afirme.afirmenet.ibs.databeans.BancoPortabilidad;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.ibs.databeans.TD5000PF;
import com.afirme.afirmenet.ibs.databeans.cardif.SeguroCardif;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.model.configuraciones.LimitesCuentaAfirme;
import com.afirme.afirmenet.model.configuraciones.Transacciones;
import com.afirme.afirmenet.model.configuraciones.chequera.RespuestaProteccionCheque;
import com.afirme.afirmenet.model.credito.Credito;
import com.afirme.afirmenet.model.credito.nomina.Nomina;
import com.afirme.afirmenet.model.estadoCuenta.EstadoCuenta;
import com.afirme.afirmenet.model.inversiones.Inversion;
import com.afirme.afirmenet.model.movil.Movil;
import com.afirme.afirmenet.model.nomina.Portabilidad;
import com.afirme.afirmenet.model.pagos.ConvenioDomiciliacion;
import com.afirme.afirmenet.model.pagos.ImpuestoGDF;
import com.afirme.afirmenet.model.pagos.Tesoreria;
import com.afirme.afirmenet.model.pagos.impuestos.ConceptoTesoreria;
import com.afirme.afirmenet.model.pagos.impuestos.DepositoReferenciado;
import com.afirme.afirmenet.model.pagos.servicios.Servicio;
import com.afirme.afirmenet.model.servicios.Alertas;
import com.afirme.afirmenet.model.servicios.AsociaMovil;
import com.afirme.afirmenet.model.servicios.AvisoViajeDTO;
import com.afirme.afirmenet.model.servicios.TarjetaAvisoViajeDTO;
import com.afirme.afirmenet.model.servicios.tarjetaDebito.TarjetaDebito;
import com.afirme.afirmenet.model.transferencia.ClaveMisCreditos;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.Divisa;
import com.afirme.afirmenet.model.transferencia.DomingoElectronico;
import com.afirme.afirmenet.model.transferencia.TipoCuentaDestino;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpress;
import com.afirme.afirmenet.security.AES128;
import com.afirme.afirmenet.service.avisoviaje.AvisoViajeService;
import com.afirme.afirmenet.service.pagos.ImpuestosGDFService;
import com.afirme.afirmenet.service.programadas.ProgramacionDomingoService;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.time.TimeUtils;

@Repository
public class ComprobanteTransferenciaDaoImpl implements ComprobanteTransferenciaDao {

	private final String TABLA_SPEI_DIA = "speimovpf";
	private final String TABLA_SPEI_HISTORICA = "speihmopf";
	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);

	@Override
	public void insertaConfirmacionOperacion(Comprobante transferenciaBase) {
	}

	@Override
	public List<?> buscarestatusEstadosCuentas(String contrato, String fechaDesde, String fechaHasta) {
		return null;
	}

	@Override
	public List<?> buscarEmisionesEstadosCuentas(String contrato, String fechaDesde, String fechaHasta) {
		return null;
	}

	@Override
	public void insertaComprobanteProgramacionPagos(TransferenciaBase transferenciaBase) {
	}

	@Override
	public List<TransferenciaBase> buscarComprobantesGenericos(String contrato, String tipo, String fechaDesde,
			String fechaHasta, String numeroServicio) {

		return null;
	}

	@Override
	public List<Divisa> buscarComprobantesSWIFT(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		return null;
	}

	/**
	 * Para obtener los comprobantes de domingo electronico en la tabla
	 * DC_PROGCONCEN. Y se completa la hora desde la tabla VW_DC_CONFMSG.
	 * 
	 * 
	 * @see com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao#obtenerComprobantesDomingoElectronico(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TransferenciaBase> obtenerComprobantesDomingoElectronico(String contrato, String tipo,
			String fechaDesde, String fechaHasta) {

		return null;
	}

	/**
	 * Para completar el dato hora al comprobante.
	 * 
	 * @param transferenciaBase
	 * @return
	 */
	private DomingoElectronico completarDatosDomingoElectronico(DomingoElectronico transferenciaBase) {
		return null;
	}

	@Override
	public List<TransferenciaBase> buscarComprobantesDomingoElectronico(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	@Override
	public List<ImpuestoGDF> buscarComprobantesGDF(String contrato, String tipo, String fechaDesde, String fechaHasta) {

		return null;

	}

	@Override
	public ImpuestoGDF buscarComprobanteGDF(String referencia, String contrato) {
		return null;
	}

	@Override
	public List<Comprobante> buscarComprobantesCredi100(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	@Override
	public List<TarjetaDebito> buscarComprobantesCambioLimitesEstadoTDD(String contrato, String fechaDesde,
			String fechaHasta, String tipoMovimineto) {
		return null;
	}

	@Override
	public List<Comprobante> buscarComprobantesEliminacionCuentasServicios(
			return null;
	}

	@Override
	public List<Comprobante> buscarComprobantesCancelacionOperaciones(String contrato, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	@Override
	public List<LimitesCuentaAfirme> buscarComprobantesCambioLimitesTransferencias(String contrato, String tipo,
			String fechaDesde, String fechaHasta) {
		return null;
	}

	@Override
	public List<Transacciones> getInfoLimTrans(LimitesCuentaAfirme comp) {
		return null;
	}

	@Override
	public List<BusquedaChequeResultado> buscarComprobantesChequesExtraviadosProteccion(String contrato,
			String fechaDesde, String fechaHasta, String estado) {
		return null;
	}

	@Override
	public List<Comprobante> buscarComprobantesGenericosPorDescripcion(String contrato, String fechaDesde,
			String fechaHasta, String descripcion) {
		return null;
	}

	@Override
	public List<Comprobante> buscarComprobantesApliacionLineaCredito(String contrato, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	@Override
	public List<Movil> buscarComprobantesAfirmeMovil(String contrato, String fechaDesde, String fechaHasta) {
		return null;
	}

	@Override
	public List<Nomina> buscarComprobantesAnticipoCreditoNomina(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	@Override
	public List<Comprobante> buscarComprobantesReposicionTarjeta(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	@Override
	public List<Comprobante> buscarComprobantesInversionPerfecta(String contrato, String fechaDesde,
			String fechaHasta) {

		return null;
	}

	@Override
	public List<Servicio> buscarComprobantesActivacionServicios(String contrato, String fechaDesde, String fechaHasta) {

		return null;
	}

	@Override
	public List<Inversion> buscarComprobantesPagare(String contrato, String fechaDesde, String fechaHasta,
			String tipoPagare) {
		return null;
	}

	@Override
	public List<ConvenioDomiciliacion> buscarComprobantesDomiciliacionServicios(String contrato, String fechaDesde,
			String fechaHasta) {

		return null;
	}

	public ConvenioDomiciliacion buscarComprobanteDomiciliacionServicios(String contrato, String referencia) {

		return null;
	}

	@Override
	public List<AsociaMovil> buscarComprobantesAsociacionNumeroMovil(String contrato, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	public String buscarAtributoINOTR(String ref, String contrato, String atrib) {

		return null;
	}

	public String buscarNombreServicio(String tipo) {

		return null;
	}

	@Override
	public List<Comprobante> buscarComprobantesINOTRGenerico(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {

		return null;

	}

	@Override
	public List<Comprobante> buscarComprobantesProgramacionPagosTDC(String contrato, String fechaDesde,
			String fechaHasta, String programacionCancelacion) {
		return null;
	}

	@Override
	public List<Alertas> buscarComprobantesAlertas(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		return null;
	}

	@Override
	public List<TransferenciaBase> buscarComprobantesOrdenPago(String contrato, List<Cuenta> cuentas, String fechaDesde,
			String fechaHasta) {
		List<TransferenciaBase> mapReferences = new ArrayList<TransferenciaBase>();

		return null;

	}

	@Override
	public List<Comprobante> buscarComprobantesAltaCuentas(String contrato, String fechaDesde, String fechaHasta) {
		return null;
	}

	@Override
	public DC_CONVENIO buscarComprobanteRegistroTarjetas(String contrato, String convnum) {
		return null;
	}

	@Override
	public boolean existeInformacionSPEI(TransferenciaBase comprobante) {
		return (Boolean) null;
	}

	public String getOrdenCashExpress(String ref, String contrato) {

		return null;
	}

	@Override
	public void obtenerInformacionSPEI(TransferenciaBase comprobante) {

		return;

	}

	@Override
	public void actualizarInformacionSPEI(TransferenciaBase comprobante) {
	}

	@Override
	public void insertaInformacionSPEI(TransferenciaBase comprobante) {
	}

	@Override
	public void insertaConfirmacionActivacionTDC(Comprobante transferenciaBase) {
	}

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
	public List<Comprobante> buscarComprobantesEliminacionCuentasDestino(String contrato, String tipo,
			String fechaDesde, String fechaHasta) {

		return null;
	}

	/**
	 * Para obtener la lista de resultados de comprobantes de cambio de correo
	 * electronico.
	 * 
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List&lt;Comprobante&gt;
	 */
	public List<Comprobante> buscarComprobantesCambioDeCorreo(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {

		return null;
	}

	/**
	 * Para mostrar los resultados de los comprobantes de cambio de contrasenia.
	 * 
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param descripcion
	 * @return List&lt;Comprobante&gt;
	 * 
	 * @see com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao#buscarComprobantesCambioDeContrasenia(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 *
	 */
	@Override
	public List<Comprobante> buscarComprobantesCambioDeContrasenia(String contrato, String tipo, String fechaDesde,
			String fechaHasta, String descripcion) {
		return null;
	}

	/**
	 * Para obtener los comprobantes de cambio de alias.
	 * 
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List&lt;Comprobante&gt;
	 * 
	 * @see com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao#buscarComprobantesCambioDeAlias(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 *
	 */
	public List<Comprobante> buscarComprobantesCambioDeAlias(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {
		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		return null;
	}

	/**
	 * Para la lista de resultados de la busqueda de comprobantes de tipo 86=
	 * aviso de viaje.
	 * 
	 * @see com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao#buscarComprobantesAvisoDeViaje(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<Comprobante> buscarComprobantesAvisoDeViaje(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {

		return null;
	}

	/**
	 * Se hace necesario para formar el objeto que se le va a aenviar al view
	 * del comprobante Aviso de viaje.
	 */
	@Override
	public AvisoViajeDTO completarDatosAvisoViaje(AvisoViajeDTO avisoViajeDTO) {
		return null;
	}

	/**
	 * Para agregar el nombre de tipo de tarjeta de credito.
	 * 
	 * Itera la lista de tarjetas registradas en el aviso de viaje y busca cada
	 * tarjeta en la lista de tarjetas del contrato.
	 * 
	 * @param avisoViajeDTO
	 * @return
	 */
	private AvisoViajeDTO agregarNombreTipoTarjeta(AvisoViajeDTO avisoViajeDTO) {
		return null;
	}

	@Override
	public Servicio buscarComprobanteActivacionServicios(String contractId, TipoCliente cliente, int tipo) {
		return null;
	}

	@Override
	public List<TransferenciaBase> buscarComprobantesCheques(String contrato, String fD, String fH, String estatus,
			String tipo) {
		return null;
	}

	@Override
	public RespuestaProteccionCheque buscarProteccionCheque(String referenceNumber, String contrato, String cuenta) {
		return null;
	}

	@Override
	public BusquedaChequeResultado buscarCancelaCheque(String referenceNumber, String contrato, String cuenta) {
		return null;
	}

	@Override
	public List<Servicio> buscarComprobantesGenericosServicio(String contrato, String tipo, String fechaDesde,
			String fechaHasta, String numeroServicio) {
		return null;
	}

	@Override
	public List<DepositoReferenciado> buscarComprobantesGenericosImpuestoSAT(String contrato, String tipo,
			String fechaDesde, String fechaHasta, String numeroServicio) {
		return null;
	}

	@Override
	public List<SeguroCardif> buscarComprobantesGenericosCardif(String contrato, String tipo, String fechaDesde,
			String fechaHasta, String numeroServicio) {
		return null;
	}

	private List<Map<String, Object>> ListResultComprobantesGenericos(String contrato, String tipo, String fechaDesde,
			String fechaHasta, String numeroServicio) {
		return null;
	}

	@Override
	public void obtenerInformacionDepositoReferenciado(DepositoReferenciado pago) {
		return;
	}

	@Override
	public void insertaComprobanteMisCreditos(Comprobante transferenciaBase) {
	}

	@Override
	public List<Credito> buscarComprobantesGenericosCredito(String contrato, String tipo, String fechaDesde,
			String fechaHasta, String numeroServicio) {
		return null;
	}

	@Override
	public void insertaComprobanteInversionPerfecta(Comprobante transferenciaBase) {
	}

	@Override
	public List<Inversion> buscarComprobantesInversionPerfecta(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	@Override
	public void almacenarRecargaDB2(Comprobante comprobante) {
	}

	@Override
	public void insertaConfirmacionRecarga(Comprobante transferenciaBase) {
	}

	@Override
	public String obtenerSPEICEP(String contrato, int fechaInicial, int fechaFinal, int tipoMovimiento,
			String claveRastreo) {
		return null;
	}

	@Override
	public List<Portabilidad> buscarComprobantesPortabilidadNomina(String contrato, String tipo, String fechaDesde,
			String fechaHasta) {
		return null;
	}

	@Override
	public void insertaConfirmacionDepositoReferenciado(Comprobante transferenciaBase) {
	}

}
