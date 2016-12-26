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

	static final AfirmeNetLog LOG = new 
			AfirmeNetLog(ComprobanteTransferenciaDaoImpl.class);
	@Autowired
	private DB2Dao db2Dao;
	@Autowired
	private AS400Dao as400Dao;
	@Autowired
	AvisoViajeService avisoViajeService;
	@Autowired
	PagareDaoImpl pagareDao;
	@Autowired
	ProgramacionDomingoService programacionDomingoService;
	@Autowired
	private ImpuestosGDFService impuestosGDFService;
	@Autowired
	private ImpuestosGDFDao impuestoGDFDao;
	
	
	private final String TABLA_SPEI_DIA = "speimovpf";
	private final String TABLA_SPEI_HISTORICA = "speihmopf";
	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);

	
	@Override
	public void insertaConfirmacionOperacion(Comprobante transferenciaBase) {
		String sql = "INSERT INTO DC_CONFMSG (TYPTRF, NARRA, DEBACC, CREACC, DESCRIP, ENTITYID,  USERID, PMM, PSS, "
				+ "PYY, VDD, BENEADD, VMM, VHH, SERVNUM, IVA, PLZRECPTOR, FEES,"
				+ "VSS, VYY, BNKRECPTOR, RFC, DCIBS_REF, AMOUNT, CCY, BENENAME, REFENUM,"
				+ "PDD, PHH, FEE_IVA, PSECONDS, REFUSR) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		String rEFUSR=transferenciaBase.getUserReference()== null || transferenciaBase.getUserReference().equals("") ?  "0" : transferenciaBase.getUserReference();
		if(rEFUSR.length()>11)
			rEFUSR=rEFUSR.substring(0, 10);
		Object[] params = new Object[] {
				transferenciaBase.getTipoTransferencia().getValor(),//TYPTRF
				(transferenciaBase.getNarrative()!=null && transferenciaBase.getNarrative().trim().length()>0)? transferenciaBase.getNarrative(): transferenciaBase.getDescription(),//NARRA
				transferenciaBase.getOrigen() != null ? transferenciaBase.getOrigen().getNumber() : transferenciaBase.getDebitAccount(),//DEBACC
				transferenciaBase.getDestino() !=null ? transferenciaBase.getDestino().getNumber() : transferenciaBase.getCreditAccount(),//CREACC
				transferenciaBase.getDescription(),//DESCRIP
				transferenciaBase.getContractId(),//ENTITYID
				transferenciaBase.getUserId(),//USERID
				Util.getMM(),//PMM
				Util.getSS(),//PSS
				Util.getYY(),//PYY
				transferenciaBase.getValidationDay(),//VDD
				transferenciaBase.getBeneficiaryName(),//BENEADD
				transferenciaBase.getValidationMonth(),//VMM
				transferenciaBase.getValidationHour(),//VHH
				transferenciaBase.getServiceNumber(),//SERVNUM
				transferenciaBase.getIva(),//IVA
				transferenciaBase.getPlazaReceiving(),//PLZRECPTOR
				transferenciaBase.getCommision(),// FEES COMISION
				transferenciaBase.getValidationMinute(),//VSS
				transferenciaBase.getValidationYear(),//VYY
				transferenciaBase.getBankReceiving(),//BNKRECPTOR
				transferenciaBase.getRfc(),//RFC
				transferenciaBase.getAfirmeNetReference(),//DCIBS_REF
				transferenciaBase.getAmount()+" "+transferenciaBase.getCurrency() ,// AMOUNT
				transferenciaBase.getCurrency(),//CCY
				transferenciaBase.getBeneficiaryName(),//BENENAME
				transferenciaBase.getReferenceNumber(), //REFENUM
				Util.getDD(),//PDD
				Util.getHH(), //PHH 
				transferenciaBase.getTaxCommision(),// FEE_IVA//
				Util.getSeconds(), // PSECONDS
				rEFUSR };//REFUSR
		
		db2Dao.getJdbcTemplate().update(sql, params);
	}

	@Override
	public List<?> buscarestatusEstadosCuentas(String contrato, String fechaDesde, String fechaHasta) {
		String sql = "select entityid, userid, creacc,descrip,narra, pdd, pmm, pyy, phh, pss, refenum, dcibs_ref"
		   		+ " FROM DC_CONFMSG where TYPTRF = '60' and narra = '01' and descrip != ''"
		   		+ " and entityid = '"
				+ contrato
				+ "' and dcibs_ref >= '"
				+ fechaDesde.substring(2, 8) +"000000"
				+ "' and dcibs_ref <= '"
				+ fechaHasta.substring(2, 8) + "240000'"
		   		+ " order by refenum desc";
		
	    List<Map<String, Object>> listResult;
	
		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[] {});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	
		List<EstadoCuenta> mapReferences = new ArrayList<EstadoCuenta>();
	
		for (Map<String, Object> map : listResult) {
			EstadoCuenta edoCta=new EstadoCuenta();
			
			edoCta.setContrato(map.get("ENTITYID").toString());
			edoCta.setUsuario(map.get("USERID").toString());
			edoCta.setNumCuenta(map.get("CREACC").toString());
			edoCta.setAccion(map.get("DESCRIP").toString().trim());
			edoCta.setProducto(map.get("NARRA").toString());
			edoCta.setPdd(map.get("PDD").toString());
			edoCta.setPmm(map.get("PMM").toString());
			edoCta.setPyy(map.get("PYY").toString());
			edoCta.setPhh(map.get("PHH").toString());
			edoCta.setPss(map.get("PSS").toString());
			edoCta.setFolio(map.get("REFENUM").toString());
			edoCta.setNumRefere(map.get("DCIBS_REF").toString());
			edoCta.setAfirmeNetReference(map.get("REFENUM").toString());
			if (edoCta.getAccion().trim().equals("A")){
				edoCta.setStaEjec("ACTIVADO");
			}else if (edoCta.getAccion().trim().equals("D")){
				edoCta.setStaEjec("CANCELADO");
			}
			
			int Pyy = Integer.parseInt(edoCta.getPyy());
			int Pmm = Integer.parseInt(edoCta.getPmm());
			int Pdd = Integer.parseInt(edoCta.getPdd());
			
			String fechaOpera = (Pyy < 10 ? ("200" + Pyy) : (Pyy >= 90 ? "19" + Pyy : "20" + Pyy)) + (Pmm < 10 ? "0" + Pmm : "" + Pmm) + (Pdd < 10 ? "0" + Pdd : "" + Pdd);
			
			edoCta.setFechaOpera(fechaOpera);
			edoCta.setHoraOpera(edoCta.getPhh() + edoCta.getPss());
			
			mapReferences.add(edoCta);
	
		}
	
		return mapReferences;
	}
	
		@Override
	public List<?> buscarEmisionesEstadosCuentas(String contrato, String fechaDesde, String fechaHasta) {
		String sql = "select entityid, userid, creacc,descrip,narra, pdd, pmm, pyy, phh, pss, refenum, dcibs_ref, fees, fee_iva"
		   		+ " FROM DC_CONFMSG where TYPTRF = '60' and narra = '01' and descrip = ''"
		   		+ " and entityid = '"
				+ contrato
				+ "' and dcibs_ref >= '"
				+ fechaDesde.substring(2, 8) +"000000"
				+ "' and dcibs_ref <= '"
				+ fechaHasta.substring(2, 8) + "240000'"
		   		+ " order by refenum desc";
		
	    List<Map<String, Object>> listResult;
	
		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[] {});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	
		List<EstadoCuenta> mapReferences = new ArrayList<EstadoCuenta>();
	
		for (Map<String, Object> map : listResult) {
			EstadoCuenta edoCta=new EstadoCuenta();
			
			edoCta.setContrato(map.get("ENTITYID").toString());
			edoCta.setUsuario(map.get("USERID").toString());
			edoCta.setNumCuenta(map.get("CREACC").toString());
			edoCta.setAccion(map.get("DESCRIP") == null ? "" : map.get("DESCRIP").toString().trim());
			edoCta.setProducto(map.get("NARRA").toString());
			edoCta.setPdd(map.get("PDD").toString());
			edoCta.setPmm(map.get("PMM").toString());
			edoCta.setPyy(map.get("PYY").toString());
			edoCta.setPhh(map.get("PHH").toString());
			edoCta.setPss(map.get("PSS").toString());
			edoCta.setFolio(map.get("REFENUM").toString());
			edoCta.setNumRefere(map.get("DCIBS_REF").toString());
			edoCta.setAfirmeNetReference(map.get("DCIBS_REF").toString());
			if (edoCta.getAccion().trim().equals("A")){
				edoCta.setStaEjec("ACTIVADO");
			}else if (edoCta.getAccion().trim().equals("D")){
				edoCta.setStaEjec("CANCELADO");
			}else{
				edoCta.setStaEjec("");
			}
			edoCta.setComision(map.get("FEES").toString());
			edoCta.setIva(new BigDecimal(map.get("FEE_IVA").toString()));
		
			String fechaOpera = (Integer.parseInt(edoCta.getPyy()) < 10 ? ("200" + Integer.parseInt(edoCta.getPyy())) : (Integer.parseInt(edoCta.getPyy()) >= 90 ? "19" + Integer.parseInt(edoCta.getPyy()) : "20" + Integer.parseInt(edoCta.getPyy()))) + (Integer.parseInt(edoCta.getPmm()) < 10 ? "0" + Integer.parseInt(edoCta.getPmm()) : "" + Integer.parseInt(edoCta.getPmm())) + (Integer.parseInt(edoCta.getPdd()) < 10 ? "0" + Integer.parseInt(edoCta.getPdd()) : "" + Integer.parseInt(edoCta.getPdd()));
			
			edoCta.setFechaOpera(fechaOpera);
			edoCta.setHoraOpera(edoCta.getPhh() + edoCta.getPss());
			
			mapReferences.add(edoCta);
	
		}
	
		return mapReferences;
	}
	
	@Override
	public void insertaComprobanteProgramacionPagos(TransferenciaBase transferenciaBase) {
		String sql = "INSERT INTO DC_CONFMSG (TYPTRF, NARRA, DEBACC, CREACC, DESCRIP, ENTITYID,  USERID, PMM, PSS, "
				+ "PYY, VDD, BENEADD, VMM, VHH, SERVNUM, IVA, PLZRECPTOR, FEES,"
				+ "VSS, VYY, BNKRECPTOR, RFC, DCIBS_REF, AMOUNT, CCY, BENENAME, REFENUM,"
				+ "PDD, PHH, FEE_IVA, PSECONDS, REFUSR) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		Object[] params = new Object[] {
				
				transferenciaBase.getAccion().equalsIgnoreCase("C")?HistorialTipo.CANCELACION_DE_PROGRAMACION_DE_PAGOS_AFIRME_DOMICILIACION.getValor():transferenciaBase.getTipoTransferencia().getValor(),//TYPTRF
					
				transferenciaBase.getDescription(),//NARRA
				transferenciaBase.getOrigen() != null ? transferenciaBase.getOrigen().getNumber() : transferenciaBase.getDebitAccount(),//DEBACC
				transferenciaBase.getDestino() !=null ? transferenciaBase.getDestino().getNumber() : transferenciaBase.getCreditAccount(),//CREACC
				transferenciaBase.getDescription(),//DESCRIP
				transferenciaBase.getContractId(),//ENTITYID
				transferenciaBase.getUserId(),//USERID
				Util.getMM(),//PMM
				Util.getSS(),//PSS
				Util.getYY(),//PYY
				transferenciaBase.getValidationDay(),//VDD
				transferenciaBase.getBeneficiaryName(),//BENEADD
				transferenciaBase.getValidationMonth(),//VMM
				transferenciaBase.getValidationHour(),//VHH
				transferenciaBase.getServiceNumber(),//SERVNUM
				transferenciaBase.getIva(),//IVA
				transferenciaBase.getPlazaReceiving(),//PLZRECPTOR
				transferenciaBase.getCommision(),// FEES COMISION
				transferenciaBase.getValidationMinute(),//VSS
				transferenciaBase.getValidationYear(),//VYY
				transferenciaBase.getBankReceiving(),//BNKRECPTOR
				transferenciaBase.getRfc(),//RFC
				transferenciaBase.getAfirmeNetReference(),//DCIBS_REF
				transferenciaBase.getAmount()+" "+transferenciaBase.getCurrency() ,// AMOUNT
				transferenciaBase.getCurrency(),//CCY
				transferenciaBase.getBeneficiaryName(),//BENENAME
				transferenciaBase.getReferenceNumber(), //REFENUM
				Util.getDD(),//PDD
				Util.getHH(), //PHH 
				transferenciaBase.getTaxCommision(),// FEE_IVA//
				Util.getSeconds(), // PSECONDS
				transferenciaBase.getUserReference()== null || transferenciaBase.getUserReference().equals("") ?  "0" : transferenciaBase.getUserReference()  };//REFUSR
		
		db2Dao.getJdbcTemplate().update(sql, params);
	}
	
	@Override
	public List<TransferenciaBase> buscarComprobantesGenericos(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio) {
		
		List<Map<String, Object>> listResult= this.ListResultComprobantesGenericos(contrato, tipo, fechaDesde, fechaHasta, numeroServicio);
		
		List<TransferenciaBase> mapReferences = new ArrayList<TransferenciaBase>();

		for (Map<String, Object> map : listResult) {
			TransferenciaBase transferenciaBase=new TransferenciaBase();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TYPTRF"))));
			transferenciaBase.setTransactionCode(DaoUtil.getString(map.get("TYPTRF")));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("NARRA")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("DEBACC")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CREACC")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("DESCRIP")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			transferenciaBase.setServiceNumber(DaoUtil.getString(map.get("SERVNUM")));
			transferenciaBase.setIva(DaoUtil.getBigDecimal(map.get("IVA")));
			transferenciaBase.setPlazaReceiving(DaoUtil.getString(map.get("PLZRECPTOR")));
			transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("FEES")));// FEES COMISION
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setBankReceiving(DaoUtil.getString(map.get("BNKRECPTOR")));
			transferenciaBase.setRfc(DaoUtil.getString(map.get("RFC")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimalEspecial(map.get("AMOUNT"))); 
			transferenciaBase.setCurrency(DaoUtil.getString(map.get("CCY")));
			transferenciaBase.setBeneficiaryName(DaoUtil.getString(map.get("BENENAME")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("REFENUM"))); 
			transferenciaBase.setTaxCommision(DaoUtil.getBigDecimal(map.get("FEE_IVA")));// FEE_IVA
			transferenciaBase.setUserReference(DaoUtil.getString(map.get("REFUSR")));
			mapReferences.add(transferenciaBase);
		}
		
		return mapReferences;
	}
	
	@Override
	public List<Divisa> buscarComprobantesSWIFT(String contrato,
			String tipo, String fechaDesde, String fechaHasta) {
		String sql = "SELECT cast(('20'||PYY || PMM || PDD) as decimal(8, 0)) as FECHA, ENTITYID, USERID, DEBACC, CREACC, CCY, CCYCONV, AMTINCCY, EXRATE, AMOUNT, INSTRUCCIONES1, "
				+ " INSTRUCCIONES2, BENENAME, DESCRIP1, DESCRIP2, SWIFTNUM, SWIFTNOM, SWIFTARE, SWIFTEDO, SWIFTCD, SWIFTPAI, "
				+ " SWIFTFULL, ABANUM, ABANOM, ABACTY, ABAEDO, VMM, VDD, VYY, VHH, VSS, PMM, PDD, PYY, PHH, PSS, OUSER,"
				+ "  VUSER, AUSER, ODATE, VDATE, ADATE, NUMAPR, DCIBS_REF, STS, VIA, BY_CODE, CONFNUM, CLAVE, SWIFTACC_INTER, "
				+ " SWIFTCOD_INTER, EMAIL, SWIFTNOM_INTER, SWIFTFULL_INTER, ES_SWIFT_DIVISAS, CCY_DSC, CONVERSION, MONTOUSD, "
				+ " EXRUSD, STATUS, FEESORI"
				+ "  from DC_SWIFTTRANS "
				+ " where USERID = '" + contrato + "' ";
		if(tipo.equals("04"))
        {
			sql += " and CCY = 'USD' ";
        }
        else
        {
        	sql += " and CCY <> 'USD' ";
        }
		sql += " AND cast(('20'||PYY || PMM || PDD) as decimal(8, 0))>=" + fechaDesde + " AND cast(('20'||PYY || PMM || PDD) as decimal(8, 0))<="
				+ fechaHasta + " order by DCIBS_REF desc";
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Divisa> mapReferences = new ArrayList<Divisa>();

		for (Map<String, Object> map : listResult) {
			/*
			 , , 
			*/
			Divisa transferenciaBase=new Divisa();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(tipo));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("INSTRUCCIONES1")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("DEBACC")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CREACC")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("INSTRUCCIONES1")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			
			transferenciaBase.setBeneficiaryName(DaoUtil.getString(map.get("BENENAME")));
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			//transferenciaBase.setServiceNumber(DaoUtil.getString(map.get("SERVNUM")));
			//transferenciaBase.setIva(DaoUtil.getBigDecimal(map.get("IVA")));
			transferenciaBase.setPlazaReceiving(DaoUtil.getString(map.get("SWIFTARE")));
			transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("FEESORI")));// FEES COMISION
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setBankReceiving(DaoUtil.getString(map.get("SWIFTNOM")));
			//transferenciaBase.setRfc(DaoUtil.getString(map.get("RFC")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimal(map.get("AMOUNT"))); 
//			transferenciaBase.setCurrency(DaoUtil.getString(map.get("CCY")));// para reimpresion se pone moneda en currency
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("CONFNUM"))); 
			//transferenciaBase.setTaxCommision(DaoUtil.getBigDecimal(map.get("FEE_IVA")));// FEE_IVA
//			transferenciaBase.setBankName(DaoUtil.getString(map.get("SWIFTACC_INTER")));//para reimpresion se pone el intermediario en el campo del nombre del banco 
			transferenciaBase.setUserReference(DaoUtil.getString(map.get("DCIBS_REF")));
//			transferenciaBase.setBankCode(DaoUtil.getString(map.get("SWIFTNUM")));//para reimpresion se pone el codigo swift en el campo del codigo del banco 
			transferenciaBase.setIntermediarioCodigo(DaoUtil.getString(map.get("SWIFTACC_INTER")));
			transferenciaBase.setBankName(DaoUtil.getString(map.get("SWIFTNOM")));
			transferenciaBase.setDescMoneda(DaoUtil.getString(map.get("CCY")));
			transferenciaBase.setCodigoSWIFT(DaoUtil.getString(map.get("SWIFTNUM")));
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}

	/**
	 * Para obtener los comprobantes de domingo electronico en la tabla DC_PROGCONCEN.
	 * Y se completa la hora desde la tabla VW_DC_CONFMSG.
	 *  
	 *  
	 * @see com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao#obtenerComprobantesDomingoElectronico(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TransferenciaBase> obtenerComprobantesDomingoElectronico(String contrato, String tipo, String fechaDesde, String fechaHasta){
		
		String LOCAL_PROGRAMACION_MENSUAL = "0";
		String LOCAL_PROGRAMACION_POR_DIA = "1";
		List<TransferenciaBase> mapReferences = new ArrayList<TransferenciaBase>();
		
		String sql = " SELECT " +
				" substr(ODATE, 7,2) as PYY, substr(ODATE, 4,2) as PMM,  substr(ODATE, 1,2) as PDD, " +
				" substr(VDATE, 7,2) as VYY, substr(VDATE, 4,2) as VMM,  substr(VDATE, 1,2) as VDD, " +
				" (CASE WHEN TIPO_PROG = 0 THEN 'MENSUAL' ELSE '' END) AS PERIODO, " +
				" LOTE,ENTITYID,ACC,DESC,TIPO_PROG,DAY1_HH,DAY1_SS,DAY2_HH,DAY2_SS,DAY3_HH,DAY3_SS,DAY4_HH,DAY4_SS," + " DAY5_HH,DAY5_SS,DAY6_HH,DAY6_SS,DAY7_HH,DAY7_SS,INI_DD,INI_MM,INI_YY,FIN_DD,FIN_MM,FIN_YY,MONTHLY_HH," + " MONTHLY_SS,MONTHLY_DAY,ANTES_HABIL,SUM(AMOUNT) AMOUNT,STATUS,ID,OUSER,VUSER,AUSER,ODATE,VDATE,ADATE," + " TRANSTYPE,TIPOCUENTA,MAX(CREACC) CREACC"
	               + " FROM DC_PROGCONCEN " + " WHERE ENTITYID = '" + contrato + "' " + " AND TRANSTYPE = '" + tipo + "'" + " GROUP BY LOTE,ENTITYID,ACC,DESC,TIPO_PROG,DAY1_HH,DAY1_SS,DAY2_HH,DAY2_SS,DAY3_HH,DAY3_SS,DAY4_HH,DAY4_SS," + " DAY5_HH,DAY5_SS,DAY6_HH,DAY6_SS,DAY7_HH,DAY7_SS,INI_DD,INI_MM,INI_YY,FIN_DD,FIN_MM,FIN_YY,MONTHLY_HH,"
	               + " MONTHLY_SS,MONTHLY_DAY,ANTES_HABIL,30,STATUS,ID,OUSER,VUSER,AUSER,ODATE,VDATE,ADATE," + " TRANSTYPE,TIPOCUENTA,41 " + " ORDER BY ID DESC";
		
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		for (Map<String, Object> map : listResult) {
			/*
			 , , 
			*/
			DomingoElectronico transferenciaBase=new DomingoElectronico();
			// transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TYPTRF"))));
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TRANSTYPE"))));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("DESC")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("ACC")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CREACC")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("DESC")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("OUSER")));
			//transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			//transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			
			transferenciaBase.setBeneficiaryName("");
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			//transferenciaBase.setServiceNumber(DaoUtil.getString(map.get("SERVNUM")));
			//transferenciaBase.setIva(DaoUtil.getBigDecimal(map.get("IVA")));
			transferenciaBase.setPlazaReceiving("");
			//transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("FEES")));// FEES COMISION
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setBankReceiving("");
			//transferenciaBase.setRfc(DaoUtil.getString(map.get("RFC")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("LOTE")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimal(map.get("AMOUNT"))); 
			transferenciaBase.setCurrency("");
			
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("ID"))); 
			//transferenciaBase.setTaxCommision(DaoUtil.getBigDecimal(map.get("FEE_IVA")));// FEE_IVA
			
			//transferenciaBase.setUserReference(DaoUtil.getString(map.get("REFUSR")));
			
			// MONTHLY_DAY
			transferenciaBase.setProgramacion( DaoUtil.getString(map.get("PERIODO")) ); // hardcode MENSUAL para obtener los dias.
			// dias
			transferenciaBase.setDay1_hh( DaoUtil.getString(map.get("DAY1_HH")) );
			transferenciaBase.setDay1_ss( DaoUtil.getString(map.get("DAY1_SS")) );
			transferenciaBase.setDay2_hh( DaoUtil.getString(map.get("DAY2_HH")) );
			transferenciaBase.setDay2_ss( DaoUtil.getString(map.get("DAY2_SS")) );
			transferenciaBase.setDay3_hh( DaoUtil.getString(map.get("DAY3_HH")) );
			transferenciaBase.setDay3_ss( DaoUtil.getString(map.get("DAY3_SS")) );
			transferenciaBase.setDay4_hh( DaoUtil.getString(map.get("DAY4_HH")) );
			transferenciaBase.setDay4_ss( DaoUtil.getString(map.get("DAY4_SS")) );
			transferenciaBase.setDay5_hh( DaoUtil.getString(map.get("DAY5_HH")) );
			transferenciaBase.setDay5_ss( DaoUtil.getString(map.get("DAY5_SS")) );
			transferenciaBase.setDay6_hh( DaoUtil.getString(map.get("DAY6_HH")) );
			transferenciaBase.setDay6_ss( DaoUtil.getString(map.get("DAY6_SS")) );
			transferenciaBase.setDay7_hh( DaoUtil.getString(map.get("DAY7_HH")) );
			transferenciaBase.setDay7_ss( DaoUtil.getString(map.get("DAY7_SS")) );
			transferenciaBase.setLstDias(programacionDomingoService.getLstDates(transferenciaBase)); // Transaccion programada: dias
			
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				String pattern = "dd 'de' MMMM 'del' yyyy";
				String fechaInicio;
				fechaInicio = TimeUtils.getDateFormat( formatter.parse("20"+ DaoUtil.getString(map.get("INI_YY"))+DaoUtil.getString(map.get("INI_MM"))+DaoUtil.getString(map.get("INI_DD")) ), pattern);
				String fechaFin =    TimeUtils.getDateFormat( formatter.parse("20"+ DaoUtil.getString(map.get("FIN_YY"))+DaoUtil.getString(map.get("FIN_MM"))+DaoUtil.getString(map.get("FIN_DD")) ), pattern);
				transferenciaBase.setInicioProgramacion( fechaInicio ); // Fecha de Inicio: 17 de noviembre del 2015
				transferenciaBase.setFinProgramacion( fechaFin );       // Fecha de Fin: 17 de noviembre del 2016
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			int DDD = Integer.parseInt( DaoUtil.getString(map.get("PDD")) );
            int DMM = Integer.parseInt( DaoUtil.getString(map.get("PMM")) );
            int DYY = Integer.parseInt( DaoUtil.getString(map.get("PYY")) );

            String sdDate = (DYY < 10 ? ("200" + DYY) : (DYY >= 90 ? "19" + DYY : "20" + DYY)) + (DMM < 10 ? "0" + DMM : "" + DMM) + (DDD < 10 ? "0" + DDD : "" + DDD);

            int manDate = Integer.parseInt(sdDate);
            int mansfDate = Integer.parseInt( fechaDesde );
            int manstDate = Integer.parseInt( fechaHasta );

            if((manDate >= mansfDate) && (manDate <= manstDate)){
            	this.completarDatosDomingoElectronico(transferenciaBase); // Solicitud de operacion: hora
            	mapReferences.add(transferenciaBase);
            }
		}
		return mapReferences;
	}
	
	/**
	 * Para completar el dato hora al comprobante.
	 * 
	 * @param transferenciaBase
	 * @return
	 */
	private DomingoElectronico completarDatosDomingoElectronico(DomingoElectronico transferenciaBase) {

		String sql = " select ENTITYID,USERID,DEBACC,CREACC,AMOUNT,CCY,BNKRECPTOR,PLZRECPTOR,BENENAME,BENEADD,"
				+ "DESCRIP,RFC,NARRA,PDD,PMM,PYY,PHH,PSS,FECHACAPTURA,VDD,VMM,VYY,VHH,VSS,TYPTRF,REFENUM,SERVNUM,"
				+ "FEES,IVA,DCIBS_REF,FEE_IVA,PSECONDS,REFUSR "
				+ " from VW_DC_CONFMSG where ENTITYID = '" + transferenciaBase.getContractId() + "' AND TYPTRF = '"
				+ transferenciaBase.getTipoTransferencia().getValor() + "' and REFENUM='"
				+ transferenciaBase.getAfirmeNetReference() + "'";

		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		for (Map<String, Object> map : listResult) {
			transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH"))); // Solicitud  de operación: hora
			transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			transferenciaBase
					.setHoraProgramacion(DaoUtil.getString(map.get("VHH")) + ":" + DaoUtil.getString(map.get("VSS"))); // Transacción
																														// Programada:
																														// hora
		}

		return transferenciaBase;
	}
	
	@Override
	public List<TransferenciaBase> buscarComprobantesDomingoElectronico(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		String sql = "SELECT cast(('20'||substr(ODATE, 7,2) || substr(ODATE, 4,2) || substr(ODATE, 1,2)) as decimal(8, 0)) as FECHA, " +
				"substr(ODATE, 7,2) as PYY, substr(ODATE, 4,2) as PMM,  substr(ODATE, 1,2) as PDD, " +
				"substr(VDATE, 7,2) as VYY, substr(VDATE, 4,2) as VMM,  substr(VDATE, 1,2) as VDD," +
				"LOTE,ENTITYID,ACC,DESC,TIPO_PROG,DAY1_HH,DAY1_SS,DAY2_HH,DAY2_SS,DAY3_HH,DAY3_SS,DAY4_HH,DAY4_SS,"
				+ " DAY5_HH,DAY5_SS,DAY6_HH,DAY6_SS,DAY7_HH,DAY7_SS,INI_DD,INI_MM,INI_YY,FIN_DD,FIN_MM,FIN_YY,MONTHLY_HH,"
				+ " MONTHLY_SS,MONTHLY_DAY,ANTES_HABIL,SUM(AMOUNT) AMOUNT,STATUS,ID,OUSER,VUSER,AUSER,ODATE,VDATE,ADATE,"
				+ " TRANSTYPE,TIPOCUENTA,MAX(CREACC) CREACC"
				+ " FROM DC_PROGCONCEN "
				+ " WHERE ENTITYID = '"
				+ contrato
				+ "' "
				+ " AND TRANSTYPE = '"
				+ tipo
				+ "'"
				+ " GROUP BY LOTE,ENTITYID,ACC,DESC,TIPO_PROG,DAY1_HH,DAY1_SS,DAY2_HH,DAY2_SS,DAY3_HH,DAY3_SS,DAY4_HH,DAY4_SS,"
				+ " DAY5_HH,DAY5_SS,DAY6_HH,DAY6_SS,DAY7_HH,DAY7_SS,INI_DD,INI_MM,INI_YY,FIN_DD,FIN_MM,FIN_YY,MONTHLY_HH,"
				+ " MONTHLY_SS,MONTHLY_DAY,ANTES_HABIL,30,STATUS,ID,OUSER,VUSER,AUSER,ODATE,VDATE,ADATE,"
				+ " TRANSTYPE,TIPOCUENTA,41 " + " ORDER BY ID DESC";
		if (tipo.equals("36"))
			sql += " and TYPTRF IN ( '36','4')"; // PODER VER LOS RECIBOS
													// ANTERIORES
		else
			sql += " and TYPTRF = '" + tipo + "'";
		sql += " AND cast(('20'||substr(ODATE, 7,2) || substr(ODATE, 4,2) || substr(ODATE, 1,2)) as decimal(8, 0))>=" + fechaDesde + " AND cast(('20'||substr(ODATE, 7,2) || substr(ODATE, 4,2) || substr(ODATE, 1,2)) as decimal(8, 0))<="
				+ fechaHasta + " order by REFENUM desc";
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<TransferenciaBase> mapReferences = new ArrayList<TransferenciaBase>();

		for (Map<String, Object> map : listResult) {
			/*
			 , , 
			*/
			DomingoElectronico transferenciaBase=new DomingoElectronico();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TYPTRF"))));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("DESC")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("ACC")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CREACC")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("DESC")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("OUSER")));
			//transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			//transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			
			transferenciaBase.setBeneficiaryName("");
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			//transferenciaBase.setServiceNumber(DaoUtil.getString(map.get("SERVNUM")));
			//transferenciaBase.setIva(DaoUtil.getBigDecimal(map.get("IVA")));
			transferenciaBase.setPlazaReceiving("");
			//transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("FEES")));// FEES COMISION
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setBankReceiving("");
			//transferenciaBase.setRfc(DaoUtil.getString(map.get("RFC")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("LOTE")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimal(map.get("AMOUNT"))); 
			transferenciaBase.setCurrency("");
			
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("ID"))); 
			//transferenciaBase.setTaxCommision(DaoUtil.getBigDecimal(map.get("FEE_IVA")));// FEE_IVA
			
			//transferenciaBase.setUserReference(DaoUtil.getString(map.get("REFUSR")));
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}

	@Override
	public List<ImpuestoGDF> buscarComprobantesGDF(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		
		//return buscarComprobantesGenericos(contrato, tipo, fechaDesde, fechaHasta, null);
		
		String sql = "select ENTITYID, USERID, DCIBS_REF, INCOPE, INTUSR, INTREF, INTXPMTV, INTFECHA, INTHORA, INTGRUPO, " +
				"INTCLAVE, INTBANCO, INTCTACGO, INTAUTOR, INTFECPAGO, INTLCAPT, INTMONEDA, INTIMPORTE, INTNOMBRE, INTDIREC, " +
				"INTCOLONIA, INTCODPOS, INTDELEGA, INTESTADO, INTCVETIPO, INTTIPO, INPERIODO, INTRFC, INTPLACA, INTMODELO, " +
				"INCVEMARCA, INTMARCA, INTFOLIO, INTANOINF, INTCVEVER, INTCVEHOLO, INTHOLOGR, INTCANT, INTFOLHOL, INTFOLREG, " +
				"INTPREUNI, INTFOLFACT, INTCUENTA, INTEJERC, INTBIM, INTEJEFIS, INSUBMARCA, INTVALFACT, INTNCILIND, INTEJEIMP, " +
				"INTMESIMP, INTIPODECL, INTRENUM, INTNUMTRAB, INTFECEVEN, INCVEOPER, INTIPOPER, INTFECESC, INTFOLPROP, INCVETRAM, " +
				"INTRAMITE, INTNUMESC, INVIGENCIA, INTNOTARIO, INTCERTIF, INTTIF, INTSTS, INTORIGEN, INTSUBCPTO, INTREFPAGO, " +
				"INTCVEADTR, STATUS, INTREFINOTR from DC_INFORMU01 where ENTITYID = ? and INTFECHA between " + fechaDesde + " AND " + fechaHasta + " and STATUS = '1' order by INTREFINOTR desc";
			
			
        List<Map<String, Object>> listResult;

			try {
				listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[] {contrato});
			} catch (EmptyResultDataAccessException e) {
				LOG.info("No se encontraron resultados para Comprobantes Genericos GDF: " + e.getMessage());
				return null;
			}
			

			List<ImpuestoGDF> mapReferences = new ArrayList<ImpuestoGDF>();

			for (Map<String, Object> map : listResult) {
				ImpuestoGDF transferenciaBase = new ImpuestoGDF();
				
				transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID"))); // ENTITYID
				transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID"))); // USERID
				transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF"))); // DCIBS_REF
				//transferenciaBase.ADaoUtil.getString(map.get("INCOPE"))); // INCOPE
				transferenciaBase.setUserId(DaoUtil.getString(map.get("INTUSR"))); // INTUSR
				transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("INTREF"))); // INTREF
				transferenciaBase.setTipoTransferencia(TipoTransferencia.PAGO_TESORERIA_GOBIERNO_DISTRITO_FEDERAL); // INTXPMTV
				transferenciaBase.setTransactionCode(DaoUtil.getString(map.get("INTXPMTV"))); // INTXPMTV
				transferenciaBase.setValidationDate(DaoUtil.getString(map.get("INTFECHA"))); // INTFECHA
				
				String horaTransaccion = DaoUtil.getString(map.get("INTHORA"));
				transferenciaBase.setProgrammingHour(horaTransaccion.substring(0, 2));
				transferenciaBase.setProgrammingMinute(horaTransaccion.substring(2, 4));
				
				String fechaTransaccion = DaoUtil.getString(map.get("INTFECHA"));
				
				if (fechaTransaccion != null) {
					if (fechaTransaccion.length() > 7) {
						transferenciaBase.setProgrammingYear(fechaTransaccion.substring(0,4));
						transferenciaBase.setProgrammingMonth(fechaTransaccion.substring(4,6));
						transferenciaBase.setProgrammingDay(fechaTransaccion.substring(6,8));
					} else {
						transferenciaBase.setProgrammingYear(fechaTransaccion.substring(0,2));
						transferenciaBase.setProgrammingMonth(fechaTransaccion.substring(2,4));
						transferenciaBase.setProgrammingDay(fechaTransaccion.substring(4,6));
					}
				}
				
				transferenciaBase.setIdGrupo(DaoUtil.getBigDecimal(map.get("INTGRUPO")).intValue()); // INTGRUPO
				transferenciaBase.setIdClave(DaoUtil.getBigDecimal(map.get("INTCLAVE")).intValue()); // INTCLAVE
				transferenciaBase.setBankCode(DaoUtil.getString(map.get("INTBANCO"))); // INTBANCO
				transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("INTCTACGO"))); // INTCTACGO
				//transferenciaBase.setAuthorizationUser(DaoUtil.getString(map.get("INTAUTOR"))); // INTAUTOR
				transferenciaBase.setFechaEvento(DaoUtil.getString(map.get("INTFECPAGO"))); // INTFECPAGO
				transferenciaBase.setLineaCaptura(DaoUtil.getString(map.get("INTLCAPT"))); // INTLCAPT
				transferenciaBase.setCurrency(DaoUtil.getString(map.get("INTMONEDA"))); // INTMONEDA
				transferenciaBase.setAmount(DaoUtil.getBigDecimal(map.get("INTIMPORTE"))); // INTIMPORTE
				
				//transferenciaBase.setConceptoGDF(impuestosGDFService.getConcepto(transferenciaBase.getLineaCaptura()));
				//transferenciaBase.setLineaCapturaBase(impuestosGDFService.getBaseLineaCaptura(transferenciaBase.getLineaCaptura(), transferenciaBase.getImporte()));

				ConceptoTesoreria conceptoTesoreria = impuestoGDFDao.getConceptoTesoreria(transferenciaBase.getIdClave()); 
				transferenciaBase.setConcepto(conceptoTesoreria.getDfconcepto());
				transferenciaBase.setNombreGrupo(conceptoTesoreria.getDfNombreGrupo());
				transferenciaBase.getAtributosGenericos().put("tipoOperacion", transferenciaBase.getConcepto());
				
				
				mapReferences.add(transferenciaBase);
			}

			return mapReferences;
		
	}
	
	@Override
	public ImpuestoGDF buscarComprobanteGDF(String referencia, String contrato) {
		String sql = "select ENTITYID, USERID, DCIBS_REF, INCOPE, INTUSR, INTREF, INTXPMTV, INTFECHA, INTHORA, INTGRUPO, " +
			"INTCLAVE, INTBANCO, INTCTACGO, INTAUTOR, INTFECPAGO, INTLCAPT, INTMONEDA, INTIMPORTE, INTNOMBRE, INTDIREC, " +
			"INTCOLONIA, INTCODPOS, INTDELEGA, INTESTADO, INTCVETIPO, INTTIPO, INPERIODO, INTRFC, INTPLACA, INTMODELO, " +
			"INCVEMARCA, INTMARCA, INTFOLIO, INTANOINF, INTCVEVER, INTCVEHOLO, INTHOLOGR, INTCANT, INTFOLHOL, INTFOLREG, " +
			"INTPREUNI, INTFOLFACT, INTCUENTA, INTEJERC, INTBIM, INTEJEFIS, INSUBMARCA, INTVALFACT, INTNCILIND, INTEJEIMP, " +
			"INTMESIMP, INTIPODECL, INTRENUM, INTNUMTRAB, INTFECEVEN, INCVEOPER, INTIPOPER, INTFECESC, INTFOLPROP, INCVETRAM, " +
			"INTRAMITE, INTNUMESC, INVIGENCIA, INTNOTARIO, INTCERTIF, INTTIF, INTSTS, INTORIGEN, INTSUBCPTO, INTREFPAGO, " +
			"INTCVEADTR, STATUS, INTREFINOTR from DC_INFORMU01 where INTREF = ? and ENTITYID = ? and STATUS = '1' order by INTREFINOTR desc";
		
		
		Map<String, Object> map;

		try {
			map = db2Dao.getJdbcTemplate().queryForMap(sql, new Object[] {referencia, contrato});
		} catch (EmptyResultDataAccessException e) {
			LOG.info("No se encontraron resultados para Comprobantes Genericos GDF: " + e.getMessage());
			return null;
		}
		
		ImpuestoGDF transferenciaBase = new ImpuestoGDF();
		
		transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID"))); // ENTITYID
		transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID"))); // USERID
		transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF"))); // DCIBS_REF
		//transferenciaBase.ADaoUtil.getString(map.get("INCOPE"))); // INCOPE
		transferenciaBase.setUserId(DaoUtil.getString(map.get("INTUSR"))); // INTUSR
		transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("INTREF"))); // INTREF
		transferenciaBase.setTipoTransferencia(TipoTransferencia.PAGO_TESORERIA_GOBIERNO_DISTRITO_FEDERAL); // INTXPMTV
		transferenciaBase.setTransactionCode(DaoUtil.getString(map.get("INTXPMTV"))); // INTXPMTV
		transferenciaBase.setValidationDate(DaoUtil.getString(map.get("INTFECHA"))); // INTFECHA
		
		String horaTransaccion = DaoUtil.getString(map.get("INTHORA"));
		transferenciaBase.setProgrammingHour(horaTransaccion.substring(0, 2));
		transferenciaBase.setProgrammingMinute(horaTransaccion.substring(2, 4));
		
		String fechaTransaccion = DaoUtil.getString(map.get("INTFECHA"));
		
		if (fechaTransaccion != null) {
			if (fechaTransaccion.length() > 7) {
				transferenciaBase.setProgrammingYear(fechaTransaccion.substring(0,4));
				transferenciaBase.setProgrammingMonth(fechaTransaccion.substring(4,6));
				transferenciaBase.setProgrammingDay(fechaTransaccion.substring(6,8));
			} else {
				transferenciaBase.setProgrammingYear(fechaTransaccion.substring(0,2));
				transferenciaBase.setProgrammingMonth(fechaTransaccion.substring(2,4));
				transferenciaBase.setProgrammingDay(fechaTransaccion.substring(4,6));
			}
		}
		
		transferenciaBase.setIdGrupo(DaoUtil.getBigDecimal(map.get("INTGRUPO")).intValue()); // INTGRUPO
		transferenciaBase.setIdClave(DaoUtil.getBigDecimal(map.get("INTCLAVE")).intValue()); // INTCLAVE
		transferenciaBase.setBankCode(DaoUtil.getString(map.get("INTBANCO"))); // INTBANCO
		transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("INTCTACGO"))); // INTCTACGO
		//transferenciaBase.setAuthorizationUser(DaoUtil.getString(map.get("INTAUTOR"))); // INTAUTOR
		transferenciaBase.setFechaEvento(DaoUtil.getString(map.get("INTFECPAGO"))); // INTFECPAGO
		transferenciaBase.setLineaCaptura(DaoUtil.getString(map.get("INTLCAPT"))); // INTLCAPT
		transferenciaBase.setCurrency(DaoUtil.getString(map.get("INTMONEDA"))); // INTMONEDA
		transferenciaBase.setAmount(DaoUtil.getBigDecimal(map.get("INTIMPORTE"))); // INTIMPORTE
		
		transferenciaBase.setNombre(DaoUtil.getString(map.get("INTNOMBRE"))); // INTNOMBRE
		transferenciaBase.setDomicilio(DaoUtil.getString(map.get("INTDIREC"))); // INTDIREC
		transferenciaBase.setColonia(DaoUtil.getString(map.get("INTCOLONIA"))); // INTCOLONIA
		transferenciaBase.setCodigoPostal(DaoUtil.getString(map.get("INTCODPOS"))); // INTCODPOS
		transferenciaBase.setDelegacion(DaoUtil.getString(map.get("INTDELEGA"))); // INTDELEGA
		transferenciaBase.setEstado(DaoUtil.getString(map.get("INTESTADO"))); // INTESTADO
		transferenciaBase.setClaveTipoDeclaracion(DaoUtil.getString(map.get("INTCVETIPO"))); // INTCVETIPO
		transferenciaBase.setTipoLicencia(DaoUtil.getString(map.get("INTTIPO"))); // INTTIPO
		transferenciaBase.setTipoDeclaracion(DaoUtil.getString(map.get("INTTIPO"))); // INTTIPO
		transferenciaBase.setTipoOperacion(DaoUtil.getString(map.get("INTTIPO"))); // INTTIPO
		transferenciaBase.setPeriodo(DaoUtil.getString(map.get("INPERIODO"))); // INPERIODO
		transferenciaBase.setRfc(DaoUtil.getString(map.get("INTRFC"))); // INTRFC
		transferenciaBase.setPlaca(DaoUtil.getString(map.get("INTPLACA"))); // INTPLACA
		transferenciaBase.setModelo(DaoUtil.getString(map.get("INTMODELO"))); // INTMODELO
		transferenciaBase.setClaveMarca(DaoUtil.getString(map.get("INCVEMARCA"))); // INCVEMARCA
		transferenciaBase.setMarca(DaoUtil.getString(map.get("INTMARCA"))); // INTMARCA
		transferenciaBase.setFolio(Long.parseLong(DaoUtil.getString(map.get("INTFOLIO")))); // INTFOLIO
		transferenciaBase.setFolioInfraccion(Long.parseLong(DaoUtil.getString(map.get("INTFOLIO")))); // INTFOLIO
		transferenciaBase.setAnioInfraccion(DaoUtil.getBigDecimal(map.get("INTANOINF")).intValue()); // INTANOINF
		transferenciaBase.setClaveVerifiCentro(DaoUtil.getBigDecimal(map.get("INTCVEVER")).intValue()); // INTCVEVER
		//transferenciaBase.DaoUtil.getString(map.get("INTCVEHOLO"))); // INTCVEHOLO
		transferenciaBase.setTipoHolograma(DaoUtil.getString(map.get("INTHOLOGR"))); // INTHOLOGR
		transferenciaBase.setCantidad(DaoUtil.getBigDecimal(map.get("INTCANT")).intValue()); // INTCANT
		//transferenciaBase.sethoDaoUtil.getString(map.get("INTFOLHOL"))); // INTFOLHOL
		//transferenciaBase.DaoUtil.getString(map.get("INTFOLREG"))); // INTFOLREG
		transferenciaBase.setPrecioUnitario(DaoUtil.getDouble(map.get("INTPREUNI"))); // INTPREUNI
		transferenciaBase.setFolioFactura(Long.parseLong(DaoUtil.getString(map.get("INTFOLFACT")))); // INTFOLFACT
		transferenciaBase.setCuentaPredial(DaoUtil.getString(map.get("INTCUENTA"))); // INTCUENTA
		transferenciaBase.setCuentaAgua(DaoUtil.getString(map.get("INTCUENTA"))); // INTCUENTA
		//transferenciaBase.DaoUtil.getString(map.get("INTEJERC"))); // INTEJERC
		transferenciaBase.setBimestre(DaoUtil.getString(map.get("INTBIM"))); // INTBIM
		transferenciaBase.setSubMarca(DaoUtil.getString(map.get("INSUBMARCA"))); // INSUBMARCA
		transferenciaBase.setValorFactura(Double.parseDouble(DaoUtil.getString(map.get("INTVALFACT")))); // INTVALFACT
		transferenciaBase.setCilindros(Integer.parseInt(DaoUtil.getString(map.get("INTNCILIND")))); // INTNCILIND
		if(transferenciaBase.getIdClave()==88 || transferenciaBase.getIdClave()==96) {
			transferenciaBase.setEjercicio(DaoUtil.getBigDecimal(map.get("INTEJEFIS")).intValue()); // INTEJEFIS
			String tipoDeclaracion = transferenciaBase.getTipoDeclaracion();
			String tipo="Normal";
            
			if(tipoDeclaracion.equals("1")) {
                  tipo = "Normal";
			} else if(tipoDeclaracion.equals("2")) {
                  tipo = "Complementaria";
			} else if(tipoDeclaracion.equals("3")) {
                  tipo = "Autocorrección";
            }			
            transferenciaBase.setTipoDeclaracion(tipo);
		} else if(transferenciaBase.getIdClave()==89 || transferenciaBase.getIdClave()==90 || 
				transferenciaBase.getIdClave()==91 || transferenciaBase.getIdClave()==97) {
			transferenciaBase.setEjercicio(DaoUtil.getBigDecimal(map.get("INTEJEFIS")).intValue()); // INTEJEFIS
		} else {
			transferenciaBase.setEjercicio(Integer.parseInt(DaoUtil.getString(map.get("INTEJEIMP")))); // INTEJEIMP
		}
		transferenciaBase.setMes(DaoUtil.getString(map.get("INTMESIMP"))); // INTMESIMP
		//transferenciaBase.DaoUtil.getString(map.get("INTIPODECL"))); // INTIPODECL
		transferenciaBase.setRemuneracionesPagadas(Double.parseDouble(DaoUtil.getString(map.get("INTRENUM")))); // INTRENUM
		transferenciaBase.setNumeroTrabajadores(DaoUtil.getBigDecimal(map.get("INTNUMTRAB")).intValue()); // INTNUMTRAB
		transferenciaBase.setFechaEvento(DaoUtil.getString(map.get("INTFECEVEN"))); // INTFECEVEN
		//transferenciaBase.DaoUtil.getString(map.get("INCVEOPER"))); // INCVEOPER
		//transferenciaBase.DaoUtil.getString(map.get("INTIPOPER"))); // INTIPOPER
		transferenciaBase.setFechaEscrituras(DaoUtil.getString(map.get("INTFECESC"))); // INTFECESC
		//transferenciaBase.DaoUtil.getString(map.get("INTFOLPROP"))); // INTFOLPROP
		//transferenciaBase.DaoUtil.getString(map.get("INCVETRAM"))); // INCVETRAM
		transferenciaBase.setTramite(DaoUtil.getString(map.get("INTRAMITE"))); // INTRAMITE
		transferenciaBase.setNumeroEscritura(DaoUtil.getString(map.get("INTNUMESC"))); // INTNUMESC
		transferenciaBase.setVigencia(DaoUtil.getString(map.get("INVIGENCIA"))); // INVIGENCIA
		String notario = DaoUtil.getString(map.get("INTNOTARIO"));
		transferenciaBase.setNumeroNotario(notario!=null && !notario.equals("")?Integer.parseInt(notario):0); // INTNOTARIO
		transferenciaBase.setCertificacion(DaoUtil.getString(map.get("INTCERTIF")));
		//transferenciaBase.DaoUtil.getString(map.get("INTTIF"))); // INTTIF
		//transferenciaBase.DaoUtil.getString(map.get("INTSTS"))); // INTSTS
		transferenciaBase.setOrigenTramite(DaoUtil.getString(map.get("INTORIGEN"))); // INTORIGEN
		if(transferenciaBase.getIdClave()>=36 && transferenciaBase.getIdClave()<=45) {
			Tesoreria tesoreria = impuestoGDFDao.getInfoTesoreria(900, transferenciaBase.getLineaCaptura().substring(2,3));
			// Set origenTramiteDescripcion
			if (tesoreria != null &&  tesoreria.getConcepto()!=null)
				transferenciaBase.setOrigenTramiteDescripcion(tesoreria.getConcepto());
			else
				transferenciaBase.setOrigenTramiteDescripcion("Otros");
		}
		
		
		transferenciaBase.setClaveSubConcepto(DaoUtil.getString(map.get("INTSUBCPTO"))); // INTSUBCPTO
		transferenciaBase.setReferenciaPago(DaoUtil.getString(map.get("INTREFPAGO"))); // INTREFPAGO
		transferenciaBase.setClaveAdminTributaria(DaoUtil.getString(map.get("INTCVEADTR"))); // INTCVEADTR
		transferenciaBase.setState(DaoUtil.getString(map.get("STATUS"))); // STATUS

		ConceptoTesoreria conceptoTesoreria = impuestoGDFDao.getConceptoTesoreria(transferenciaBase.getIdClave()); 
		transferenciaBase.setConcepto(conceptoTesoreria.getDfconcepto());
		transferenciaBase.setNombreGrupo(conceptoTesoreria.getDfNombreGrupo());
		transferenciaBase.getAtributosGenericos().put("tipoOperacion", transferenciaBase.getConcepto());
		
		if(transferenciaBase.getClaveSubConcepto().equals("1")) {
			transferenciaBase.setSubConcepto("Impuesto sobre la Renta por Enajenación 5% (ISR)");
		} else if(transferenciaBase.getClaveSubConcepto().equals("2")) {
			transferenciaBase.setSubConcepto("I.E.P.S");
		}
		
		//transferenciaBase.set DaoUtil.getString(map.get("INTREFINOTR"))); // INTREFINOTR
		//transferenciaBase.setDescription(DaoUtil.getString(map.get("DESCRIP")));
		
		return transferenciaBase;
	}

	@Override
	public List<Comprobante> buscarComprobantesCredi100(String contrato,
			String tipo, String fechaDesde, String fechaHasta) {
		String sql = "SELECT * FROM DC_CREDI100 WHERE ENTITYID='" + contrato
				+ "' AND USERID='" + contrato + "' AND FDISPOSICION between '"
				+ fechaDesde + "' AND '" + fechaHasta + "'";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			/*
			JBDispo jb = new JBDispo();
               jb.setCliente(rs.getString("USERID"));
               jb.setNombre(rs.getString("NOMBRE"));
               jb.setBanco(rs.getString("BANCO"));
               jb.setSucursal(rs.getString("SUCURSAL"));
               jb.setMoneda(rs.getString("MONEDA"));
               jb.setProducto(rs.getString("PRODUCTO"));
               jb.setDescripcionProducto(rs.getString("DESCRIPCION"));
               jb.setMatricula(rs.getString("MATRICULA"));
               jb.setCampus(rs.getString("CAMPUS"));
               jb.setCostoSemestre(rs.getString("COSTOSEMESTRE"));
               jb.setNoCredito(rs.getString("NOCREDITO"));
               jb.setMmApertura(rs.getString("MMAPERTURA"));
               jb.setDdApertura(rs.getString("DDAPERTURA"));
               jb.setAaApertura(rs.getString("AAAPERTURA"));
               jb.setTasaAnual(rs.getString("TASA"));
               jb.setLineaCredito(rs.getString("LINEACREDITO"));
               jb.setCantidadAprobada(rs.getString("CANTAPROBADA"));
               jb.setCantidadUsada(rs.getString("CANTUSADA"));
               jb.setCantidadDisponible(rs.getString("CANTDISPONIBLE"));
               jb.setMmVencimiento(rs.getString("MMVENCIMIENTO"));
               jb.setDdVencimiento(rs.getString("DDVENCIMIENTO"));
               jb.setAaVencimiento(rs.getString("AAVENCIMIENTO"));
               jb.setMontoDispMax(rs.getString("MONTOMAX"));
               jb.setMontoDispuesto(rs.getString("MONTODIS"));
               jb.setMmLimDisp(rs.getString("MMLIMDISP"));
               jb.setDdLimDisp(rs.getString("DDLIMDISP"));
               jb.setAaLimDisp(rs.getString("AALIMDISP"));
               jb.setPorFinanciamiento(rs.getString("PORFINANCIAMIENTO"));
               jb.setMmProxPago(rs.getString("MMPROXPAGO"));
               jb.setDdProxPago(rs.getString("DDPROXPAGO"));
               jb.setAaProxPago(rs.getString("AAPROXPAGO"));
               jb.setPagoNivelado(rs.getString("PAGONIVELADO"));
               jb.setMontoVencido(rs.getString("MONTOVENCIDO"));
               jb.setCat(rs.getString("CAT")); 
               jb.setFecha(rs.getTimestamp("FDISPOSICION"));
               jb.setRef(rs.getString("FDISPOSICION"));
               
			*/
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN

		}

		return mapReferences;
	}

	@Override
	public List<TarjetaDebito> buscarComprobantesCambioLimitesEstadoTDD(String contrato, String fechaDesde, String fechaHasta, String tipoMovimineto) {
		String sql = "SELECT * FROM DB2INST1.DC_TARJETA_DEBITO WHERE FECHA_EJEC >= '"
				+ fechaDesde
				+ "' AND FECHA_EJEC <= '"
				+ fechaHasta
				+ "' AND TIPO_MOVIMIENTO = '"+tipoMovimineto+"' "
				+ " AND ENTITYID = '"
				+ contrato + "' AND USERID = '" + contrato + "' ";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<TarjetaDebito> mapReferences = new ArrayList<TarjetaDebito>();

		for (Map<String, Object> rs : listResult) {
			TarjetaDebito datosComprobante = new TarjetaDebito();
			
			datosComprobante.setTd5000pf(new TD5000PF());
			
            datosComprobante.setDcibs_ref(rs.get("FOLIO").toString());
            datosComprobante.setAfirmeNetReference(rs.get("FOLIO").toString());
            datosComprobante.getTd5000pf().setTDSTAT(new BigDecimal(rs.get("ESTATUS_COD").toString()));
            datosComprobante.getTd5000pf().setTDNOMB(rs.get("NOMBRE_TARJETA").toString());
            datosComprobante.setEntityId(rs.get("ENTITYID").toString());
            datosComprobante.getTd5000pf().setTDTARJ(new BigDecimal(rs.get("NUMERO_TARJETA").toString()));
            datosComprobante.getTd5000pf().setTDCT10(new BigDecimal(rs.get("CUENTA_ASOCIADA").toString()));
            datosComprobante.getTd5000pf().setTDCT20(new BigDecimal(rs.get("CUENTA_ASOCIADA").toString()));
            String fechaEjecucion = rs.get("FECHA_EJEC").toString();
            datosComprobante.setPdd(fechaEjecucion.substring(6, 8).toString());
            datosComprobante.setPmm(fechaEjecucion.substring(4, 6).toString());
            datosComprobante.setPyy(fechaEjecucion.substring(0, 4).toString());
            datosComprobante.setUserId(rs.get("USERID").toString());
            datosComprobante.setNameCuenta(rs.get("NOMBRE_CUENTA_ASOCIADA").toString());
            datosComprobante.setPhh(rs.get("HORA_EJEC").toString());
            datosComprobante.setPss(rs.get("MIN_EJEC").toString());
            
            datosComprobante.getTd5000pf().setTDLDA1(new BigDecimal(rs.get("LIMITE_CAJ_DIARIO").toString().equals("")?"0":rs.get("LIMITE_CAJ_DIARIO").toString().replace(",", "")));
            datosComprobante.getTd5000pf().setTDLDA2(new BigDecimal(rs.get("LIMITE_CAJ_DIARIO").toString().equals("")?"0":rs.get("LIMITE_CAJ_DIARIO").toString().replace(",", "")));
            
            datosComprobante.getTd5000pf().setTDLMA1(new BigDecimal(rs.get("LIMITE_CAJ_MENSUAL").toString().equals("")?"0":rs.get("LIMITE_CAJ_MENSUAL").toString().replace(",", "")));
            datosComprobante.getTd5000pf().setTDLMA2(new BigDecimal(rs.get("LIMITE_CAJ_MENSUAL").toString().equals("")?"0":rs.get("LIMITE_CAJ_MENSUAL").toString().replace(",", "")));
            
            datosComprobante.getTd5000pf().setTDLDP1(new BigDecimal(rs.get("LIMITE_COM_DIARIO").toString().equals("")?"0":rs.get("LIMITE_COM_DIARIO").toString().replace(",", "")));
            datosComprobante.getTd5000pf().setTDLDP2(new BigDecimal(rs.get("LIMITE_COM_DIARIO").toString().equals("")?"0":rs.get("LIMITE_COM_DIARIO").toString().replace(",", "")));
            
            datosComprobante.getTd5000pf().setTDLMA1(new BigDecimal(rs.get("LIMITE_COM_MENSUAL").toString().equals("")?"0":rs.get("LIMITE_COM_MENSUAL").toString().replace(",", "")));
            datosComprobante.getTd5000pf().setTDLMA2(new BigDecimal(rs.get("LIMITE_COM_MENSUAL").toString().equals("")?"0":rs.get("LIMITE_COM_MENSUAL").toString().replace(",", "")));
            
            mapReferences.add(datosComprobante);
		}

		return mapReferences;
	}

	

	@Override
	public List<Comprobante> buscarComprobantesEliminacionCuentasServicios(
			String contrato, String fechaDesde, String fechaHasta,
			String tipoIni, String tipoIni2) {
		String sql = "SELECT CONVNUM,STATUS,FECHA_BORRADO,TIPO_TRANSACCION,ENTITYID,CUENTA,DESCRIPCION_TRANSACCION,"
				+" TIPO_EMPRESA,NOMBRE_BANCO,PROPIETARIO_CTA,TITULAR_CTA,ESTATUS_PT FROM DC_CONVENIO_LOG"
				+" WHERE ENTITYID = '" + contrato + "'"
				+" AND FECHA_BORRADO >= TO_DATE('" + fechaDesde + " 00:00:00', 'YYYYMMDD HH24:MI:SS')"
				+" AND FECHA_BORRADO <= TO_DATE('" + fechaHasta + " 24:00:00', 'YYYYMMDD HH24:MI:SS')";
		if(tipoIni2.equals(""))
		{
			sql += " AND TIPO_TRANSACCION = '" + tipoIni
				+"' ORDER BY FECHA_BORRADO ASC";
		}else
		{
			sql += " AND (TIPO_TRANSACCION = '" + tipoIni + "' OR TIPO_TRANSACCION = '" + tipoIni2
				+"') ORDER BY FECHA_BORRADO ASC";
		}
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			/*
			convenioLog = new DC_CONVENIO_LOG();
				convenioLog.setCONVNUM(rs.getString("CONVNUM"));
				convenioLog.setSTATUS(rs.getString("STATUS"));
				convenioLog.setFECHA_BORRADO(rs.getString("FECHA_BORRADO"));
				convenioLog.setTIPO_TRANSACCION(rs.getString("TIPO_TRANSACCION"));
				convenioLog.setENTITYID(rs.getString("ENTITYID"));
				convenioLog.setCUENTA(rs.getString("CUENTA"));
				convenioLog.setDESCRIPCION_TRANSACCION(rs.getString("DESCRIPCION_TRANSACCION"));
				convenioLog.setTIPO_EMPRESA(rs.getString("TIPO_EMPRESA"));
				convenioLog.setNOMBRE_BANCO(rs.getString("NOMBRE_BANCO"));
				convenioLog.setPROPIETARIO_CTA(rs.getString("PROPIETARIO_CTA"));
				convenioLog.setTITULAR_CTA(rs.getString("TITULAR_CTA"));
				convenioLog.setESTATUS_PT(rs.getString("ESTATUS_PT"));
				listConvLog.addRow(convenioLog);
			*/
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN

		}

		return mapReferences;
	}

	@Override
	public List<Comprobante> buscarComprobantesCancelacionOperaciones(
			String contrato, String fechaDesde, String fechaHasta) {
		String sql = "SELECT INOUSR,INOREF,INOMRE,INOACC,INOCCY,INOAMT,INOVD1,INOVD2,"
        		+" INOVD3,INOPMV,INODSC,INOBAC,INOBA1,INOCOM,INOSTS,INOTSC,INOTSX,INORMR,INOTIN,INOTIF"
        		+" FROM DC_OPERACIONESPROG_LOG WHERE INOUSR = '" + contrato + "'"
        		+" AND INOTSX >= '" +fechaDesde + "000000'"
        		+" AND INOTSX <= '" + fechaHasta + "240000'";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			/*
			inotr = new INOTR();
	        	inotr.setINOUSR(rs.getString("INOUSR"));
	        	inotr.setINOREF(rs.getString("INOREF"));
	        	inotr.setINOMRE(rs.getString("INOMRE"));
	        	inotr.setINOACC(rs.getBigDecimal("INOACC"));
	        	inotr.setINOCCY(rs.getString("INOCCY"));
	        	inotr.setINOAMT(rs.getBigDecimal("INOAMT"));
	        	inotr.setINOVD1(BigDecimal.valueOf(Long.parseLong(rs.getString("INOVD1"))));
	        	inotr.setINOVD2(BigDecimal.valueOf(Long.parseLong(rs.getString("INOVD2"))));
	        	inotr.setINOVD3(BigDecimal.valueOf(Long.parseLong(rs.getString("INOVD3"))));
	        	inotr.setINOPMV(rs.getString("INOPMV"));
	        	inotr.setINODSC(rs.getString("INODSC"));
	        	inotr.setINOBAC(rs.getString("INOBAC"));
	        	inotr.setINOBA1(rs.getString("INOBA1"));
	        	inotr.setINOCOM(rs.getBigDecimal("INOCOM"));
	        	inotr.setINOSTS(rs.getString("INOSTS"));
	        	inotr.setINOTSC(BigDecimal.valueOf(Long.parseLong(rs.getString("INOTSC"))));
	        	inotr.setINOTSX(BigDecimal.valueOf(Long.parseLong(rs.getString("INOTSX"))));
	        	inotr.setINORMR(rs.getString("INORMR"));
	        	inotr.setINOTIN(BigDecimal.valueOf(Long.parseLong(rs.getString("INOTIN"))));
	        	inotr.setINOTIF(BigDecimal.valueOf(Long.parseLong(rs.getString("INOTIF"))));
	        	
	        	listINOTR.addRow(inotr);
			*/
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN

		}

		return mapReferences;
	}

	@Override
	public List<LimitesCuentaAfirme> buscarComprobantesCambioLimitesTransferencias(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		String sql = " SELECT DISTINCT ENTITYID, ACCOUNT, DCIBS_REF, TIPO_OPERACION"
				+ " FROM DC_LIMTRANSFER_LOG"
				+ " WHERE ENTITYID = '"
				+ contrato
				+ "' AND DCIBS_REF >= '"
				+ fechaDesde.substring(2, 8) +"000000"
				+ "' AND DCIBS_REF <= '"
				+ fechaHasta.substring(2, 8) + "240000'";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<LimitesCuentaAfirme> mapReferences = new ArrayList<LimitesCuentaAfirme>();

		for (Map<String, Object> rs : listResult) {
			LimitesCuentaAfirme comp = new LimitesCuentaAfirme();
			comp.setContrato(rs.get("ENTITYID").toString());
			comp.setCuenta(new Cuenta());
			comp.getCuenta().setNumber(rs.get("ACCOUNT").toString());
			comp.setReferenceNumber(rs.get("DCIBS_REF").toString());
			comp.setAfirmeNetReference(rs.get("DCIBS_REF").toString());
			comp.setAccion(rs.get("TIPO_OPERACION").toString());
			comp.setFecha(Util.formatDate(rs.get("DCIBS_REF").toString().substring(0, 6)));
			comp.setHora(Util.getHoraFormat(rs.get("DCIBS_REF").toString().substring(6, 12)));
			
			mapReferences.add(comp);
		}

		return mapReferences;
	}
	
	@Override
	public List<Transacciones> getInfoLimTrans(LimitesCuentaAfirme comp) {
		//OJOS
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM DC_LIMTRANSFER_LOG");
		sb.append(" WHERE ENTITYID = '").append(comp.getContrato());
		sb.append("' AND ACCOUNT = '").append(comp.getCuenta().getNumber());
		sb.append("' AND DCIBS_REF = '").append(comp.getAfirmeNetReference());
		sb.append("' AND TIPO_OPERACION = '").append(comp.getAccion());
		sb.append("'");
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sb.toString(), new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Transacciones> mapReferences = new ArrayList<Transacciones>();

		for (Map<String, Object> rs : listResult) {
			Transacciones own = new Transacciones();
			own.setTransname(rs.get("TRANSNAME").toString());
			own.setDailyamount_c(rs.get("DAILYAMOUNT_C").toString());
			own.setMonthlyamount_c(rs.get("MONTHLYAMOUNT_C").toString());
			
			mapReferences.add(own);
		}
		
		return mapReferences;
	}

	@Override
	public List<BusquedaChequeResultado> buscarComprobantesChequesExtraviadosProteccion(
			String contrato, String fechaDesde, String fechaHasta, String estado) {
		String sql = "SELECT DISTINCT ENTITYID, USERID, CUENTA, TITULARCTA, NUMCHKINI, NUMCHKFIN, DCIBS_REF, ESTATUS, MASIVO, MOTIVO," +
				"FECHA_OPERACION, NUMCHK FROM DC_CHEQUES_LOG WHERE ENTITYID = '" + contrato + "' AND USERID = '" + contrato +
				"' AND ESTATUS = '" + estado +
				"' AND DCIBS_REF >= '" + fechaDesde + "000000"+
				"' AND DCIBS_REF <= '" + fechaHasta + "240000"+
				"' ORDER BY DCIBS_REF";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<BusquedaChequeResultado> mapReferences = new ArrayList<BusquedaChequeResultado>();
		final List<Cheque> cheques = new ArrayList<Cheque>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		for (Map<String, Object> map : listResult) {
			//obtener detalle de la respuesta
			//TODO ggleegon adecuar respuesta
			try {
				cheques.add(new Cheque(sdf.parse(DaoUtil.getString(map.get("FECHA_OPERACION"))), 
						DaoUtil.getString(map.get("NUMCHK")), DaoUtil.getString(map.get("NUMCHK"))));
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			
			final BusquedaChequeResultado transferenciaBase = new BusquedaChequeResultado(
					DaoUtil.getString(map.get("TITULARCTA")),
					DaoUtil.getString(map.get("CUENTA")), 
					null, null, cheques);
			
			mapReferences.add(transferenciaBase);
			
			
			/*
			chkbean = new DC_CHEQUE_LOG();
				chkbean.setUSERID(rs.getString("USERID"));
				chkbean.setENTITYID(rs.getString("ENTITYID"));
				chkbean.setCUENTA(rs.getString("CUENTA"));
				chkbean.setNUMCHKINI(rs.getString("NUMCHKINI"));
				chkbean.setNUMCHKFIN(rs.getString("NUMCHKFIN"));
				chkbean.setTITULAR_CTA(rs.getString("TITULARCTA"));
				chkbean.setDCIBS_REF(rs.getString("DCIBS_REF"));
				chkbean.setMASIVO(rs.getString("MASIVO"));
				chkbean.setMOTIVO(rs.getString("MOTIVO"));
				chkbean.setESTATUS(rs.getString("ESTATUS"));
				listChk.addRow(chkbean);
			
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN
	*/
		}

		return mapReferences;
	}

	@Override
	public List<Comprobante> buscarComprobantesGenericosPorDescripcion(
			String contrato, String fechaDesde, String fechaHasta,
			String descripcion) {
		String sql = "SELECT ENTITYID, USERID, DESCRIP, PDD, PMM, PYY, VDD, VMM, VYY, PHH, PSS, VHH, VSS, DCIBS_REF, REFENUM, DEBACC" +
		   		" FROM DC_CONFMSG " +
		   		" WHERE ENTITYID = '" + contrato + "' " + " AND DESCRIP = '" + descripcion + "'" + 
		   		" AND DCIBS_REF >= '" + fechaDesde + "000000" + 
				"' AND DCIBS_REF <= '" + fechaHasta + "240000" + 
		   		"' ORDER BY REFENUM DESC";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			Comprobante transferenciaBase=new Comprobante();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TYPTRF"))));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("DESCRIP")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("DESCRIP")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("DEBACC")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("REFENUM"))); 

			mapReferences.add(transferenciaBase);

		}

		return mapReferences;
	}

	@Override
	public List<Comprobante> buscarComprobantesApliacionLineaCredito(
			String contrato, String fechaDesde, String fechaHasta) {
		String sql = "SELECT * FROM DC_AMPLIMCRE WHERE "+
		"ENTITYID = '" + contrato + "' AND ACEPTO='A' AND NARRA='P' AND "+
		"FECHAR >='"+fechaDesde+"' AND FECHAR <='"+fechaHasta+"' "+
		"ORDER BY DCIBS_REF DESC";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			/*
			jbLimCre = new JBAmpliacionLC();
				jbLimCre.setContrato(rs.getString("ENTITYID"));
				jbLimCre.setFolio(rs.getString("REFENUM"));
				jbLimCre.setDcibs_ref(rs.getString("DCIBS_REF"));
				jbLimCre.setCliente(rs.getString("CLIENTE"));
				jbLimCre.setTarjeta(rs.getString("TARJETA"));
				jbLimCre.setAcepto(rs.getString("ACEPTO"));
				jbLimCre.setLimiteAnt(rs.getDouble("LIMITEANT"));
				jbLimCre.setLimiteNue(rs.getDouble("LIMITENUE"));
				jbLimCre.setFechaR(Util.getFechaFormat(rs.getString("FECHAR")));
				jbLimCre.setHoraR(Util.getHoraFormat(rs.getString("HORAR")));
				result.add(jbLimCre);
			*/
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN

		}

		return mapReferences;
	}

	@Override
	public List<Movil> buscarComprobantesAfirmeMovil(String contrato, String fechaDesde, String fechaHasta) {
		String sql  = "select *  from " + AS400_LIBRARY + "BMLOGUPF inner join "+ AS400_LIBRARY + "EUSER on eususr=LGUSRID where LGUSRID = '" + contrato+ "' ";
 			   sql += " AND LGFEOPE >= '" + fechaDesde + "' AND LGFEOPE <= '" + fechaHasta + "' AND LGFOLIO <> ''";
			   sql += " order by LGFEOPE desc, LGHROPE desc";
			   
			    Map<String, String> listaTelefonos = new LinkedHashMap<String, String>();
					listaTelefonos.put("IP", "iPhone");
					listaTelefonos.put("AN", "Android");
					listaTelefonos.put("BB", "Blackberry");
					listaTelefonos.put("OT", "Otros Teléfonos");
			   
			   List<Map<String, Object>> listResult;

			   try {
					listResult = as400Dao.getJdbcTemplate().queryForList(sql,new Object[] { });
				} catch (EmptyResultDataAccessException e) {
					return null;
				}

				List<Movil> mapReferences = new ArrayList<Movil>();

				for (Map<String, Object> rs : listResult) {
					Movil beanAfirmeMovil = new Movil();
					beanAfirmeMovil.setAfirmeNetReference(rs.get("LGFOLIO").toString());
					beanAfirmeMovil.setReferenceNumber(rs.get("LGFOLIO").toString());
					beanAfirmeMovil.setContractId(rs.get("LGUSRID").toString());
					beanAfirmeMovil.setClientId(rs.get("LGCLIEN").toString());
					beanAfirmeMovil.setCreditAccount(rs.get("EUSACC").toString());
					beanAfirmeMovil.setProgrammingYear(rs.get("LGFEOPE").toString().substring(0, 4));
					beanAfirmeMovil.setProgrammingMonth(rs.get("LGFEOPE").toString().substring(4, 6));
					beanAfirmeMovil.setProgrammingDay(rs.get("LGFEOPE").toString().substring(6, 8));
					String fecha=rs.get("LGFEALT").toString();
					if(fecha!=null && fecha.length()>=8){
						beanAfirmeMovil.setFechaOperacion(fecha.substring(6) + "/" + fecha.substring(4, 6) + "/" + fecha.substring(0, 4));
					}
					String hora = Util.formatHora(rs.get("LGHRALT").toString());
					beanAfirmeMovil.setHoraOperacion(hora.substring(0, 2) + ":" + hora.substring(2, 4));

					hora = Util.formatHora(rs.get("LGHROPE").toString());
					beanAfirmeMovil.setProgrammingHour(hora.substring(0, 2));
					beanAfirmeMovil.setProgrammingMinute(hora.substring(2, 4));

					// Tipo de transaccion
					beanAfirmeMovil.setTipoTransferencia(TipoTransferencia.AFIRME_MOVIL);
					// tipo de operacion
					beanAfirmeMovil.setAccion(rs.get("LGESTAT").toString().equalsIgnoreCase("C") ? "D" : rs.get("LGESTAT").toString());
					
					if (beanAfirmeMovil.getAccion().equalsIgnoreCase("A")) {
						beanAfirmeMovil.setDescription("Contratación del Servicio Afirme Móvil");
					} else if (beanAfirmeMovil.getAccion().equalsIgnoreCase("D")) {
						beanAfirmeMovil.setDescription("Cancelación del Servicio Afirme Móvil");
					} else if (beanAfirmeMovil.getAccion().equalsIgnoreCase("T")) {
						beanAfirmeMovil.setDescription("Cambio de Télefono del Servicio Afirme Móvil");
					}else {
						beanAfirmeMovil.setDescription("Cambio de Parámetros del Servicio Afirme Móvil");
					}

					beanAfirmeMovil.setNumeroCelular(Long.parseLong(rs.get("LGNOCEL").toString()));
					beanAfirmeMovil.setTipoTelefono(rs.get("LGTICEL").toString());
					beanAfirmeMovil.setDescTelefono(listaTelefonos.get(beanAfirmeMovil.getTipoTelefono()));
					beanAfirmeMovil.setEmail(rs.get("EUSIAD").toString());

					String varios =rs.get("LGNAMES").toString().trim();
				    int indexof = varios.indexOf("#");
				    while (indexof>-1) {
				    	varios = varios.substring(0, indexof) + "Ñ" + varios.substring(indexof + 2);
				    	indexof=varios.indexOf("#");
				    }
				    
				    indexof = varios.indexOf("Ã");
				    while (indexof>-1) {
				    	varios = varios.substring(0, indexof) + "Ñ" + varios.substring(indexof + 2);
				    	indexof=varios.indexOf("Ã");
				    }
					beanAfirmeMovil.setFraseSeguridad(varios);

					// alertas
					beanAfirmeMovil.setAlertaCorreo(rs.get("LGINDAL").toString());
					if (beanAfirmeMovil.getAlertaCorreo() != "" && beanAfirmeMovil.getAlertaCorreo().equalsIgnoreCase("m")) {
						beanAfirmeMovil.setDescAlerta("Correo electrónico y SMS");
					} else {
						beanAfirmeMovil.setDescAlerta("Correo electrónico");
						beanAfirmeMovil.setAlertaCorreo("c");
					}
					
					beanAfirmeMovil.setPublicidad(rs.get("LGAUTEP").toString());
					if (!StringUtils.isEmpty(beanAfirmeMovil.getPublicidad()) && beanAfirmeMovil.getPublicidad().equalsIgnoreCase("s"))
						beanAfirmeMovil.setDescPublicidad("Sí");
					else
						beanAfirmeMovil.setDescPublicidad("No");

					if (rs.get("LGALIAS").toString().trim().equals(beanAfirmeMovil.getContractId().trim())) {
						// ingreso por contrato
						beanAfirmeMovil.setTipoUsuario("c");
						beanAfirmeMovil.setDescUsuario("Contrato AfirmeNet");
					} else {
						// ingreso por usuario
						beanAfirmeMovil.setTipoUsuario("u");
						beanAfirmeMovil.setDescUsuario("Usuario asignado:  ");
						varios =rs.get("LGALIAS").toString().trim();
					    indexof = varios.indexOf("#");
					    while (indexof>-1) {
					    	varios = varios.substring(0, indexof) + "Ñ" + varios.substring(indexof + 2);
					    	indexof=varios.indexOf("#");
					    }
					    indexof = varios.indexOf("Ã");
					    while (indexof>-1) {
					    	varios = varios.substring(0, indexof) + "Ñ" + varios.substring(indexof + 2);
					    	indexof=varios.indexOf("Ã");
					    }
						beanAfirmeMovil.setUsuarioIngreso(varios);
					}

					beanAfirmeMovil.setLimiteDiarioPropias(Double.parseDouble(rs.get("LGLDTRP").toString()));
					beanAfirmeMovil.setLimiteMensualPropias(Double.parseDouble(rs.get("LGLMTRP").toString()));
					beanAfirmeMovil.setLimiteDiarioInversion(Double.parseDouble(rs.get("LGLDTIN").toString()));
					beanAfirmeMovil.setLimiteMensualInversion(Double.parseDouble(rs.get("LGLMTIN").toString()));
					beanAfirmeMovil.setLimiteDiarioCredito(Double.parseDouble(rs.get("LGLDPTC").toString()));
					beanAfirmeMovil.setLimiteMensualCredito(Double.parseDouble(rs.get("LGLMPTC").toString()));
					beanAfirmeMovil.setLimiteDiarioRecarga(Double.parseDouble( rs.get("LGLDRCE").toString()));
					beanAfirmeMovil.setLimiteMensualRecarga(Double.parseDouble(rs.get("LGLMRCE").toString()));
					beanAfirmeMovil.setCommision(new BigDecimal(rs.get("LGCOMIS").toString()));
					beanAfirmeMovil.setIva(new BigDecimal(rs.get("LGIVACO").toString()));
					//beanAfirmeMovil.set(rs.get("LGCOSMS"));
	
					mapReferences.add(beanAfirmeMovil);
				}

				return mapReferences;
	}


	@Override
	public List<Nomina> buscarComprobantesAnticipoCreditoNomina(
			String contrato, String tipo, String fechaDesde, String fechaHasta) {
		
		String sql = "select *  from DC_AnticipoNom where entityid = '"
				+ contrato + "' ";
		sql += " AND fecha >= '" + fechaDesde + "' AND fecha <= '" + fechaHasta
				+ "'  and narra='P' " // personas
				+ " AND typtrf='" + tipo;
		sql += "' order by fecha desc, hora desc";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Nomina> mapReferences = new ArrayList<Nomina>();

		for (Map<String, Object> map : listResult) {
			
			Nomina transferenciaBase = new Nomina();
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("NARRA")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("REFENUM")));
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(tipo));
			String fecha = DaoUtil.getString(map.get("FECHA"));
			transferenciaBase.setProgrammingYear(fecha.substring(0, 4));
			transferenciaBase.setProgrammingMonth(fecha.substring(4, 6));
			transferenciaBase.setProgrammingDay(fecha.substring(6, 8));
			String hora = DaoUtil.getString(map.get("HORA"));
			transferenciaBase.setProgrammingHour(hora.substring(0, 2));
			transferenciaBase.setProgrammingMinute(hora.substring(2, 4));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CUENTA")));
			
			transferenciaBase.setDestino(new Cuenta());
			transferenciaBase.getDestino().setNumber(transferenciaBase.getCreditAccount());
			
			transferenciaBase.setNickName(DaoUtil.getString(map.get("NICKNAME")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimal(map.get("IMPORTE")));// FEES COMISION
			transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("COMIS")));// FEES COMISION
			//transferenciaBase.setOrigen(new Cuenta ();)
			if(tipo.equals(TipoTransferencia.ANTICIPO_DE_NOMINA.getValor())) {
				//anticipo de nomina
				transferenciaBase.setClave(ClaveMisCreditos.VALIDA_NOMINA_V2);
				transferenciaBase.setCAT(DaoUtil.getBigDecimal(map.get("CAT")).toString());
			} else {
				transferenciaBase.setMontoAutorizado(DaoUtil.getBigDecimal(map.get("LINAUT")));
				transferenciaBase.setCreditoUtilizado(DaoUtil.getBigDecimal(map.get("CREDOTOR"))); 
				transferenciaBase.setAnticipoUtilizado(DaoUtil.getBigDecimal(map.get("SALDANT")));
				transferenciaBase.setCreditoDisponible(DaoUtil.getBigDecimal(map.get("CREDISP")));
				transferenciaBase.setTasa(DaoUtil.getBigDecimal(map.get("TASA")));
				transferenciaBase.setMoratoria(DaoUtil.getBigDecimal(map.get("MORA")));
				transferenciaBase.setSeguro(DaoUtil.getBigDecimal(map.get("SEGURO")));
				transferenciaBase.setPlazo(DaoUtil.getBigDecimal(map.get("PLAZO")));
				transferenciaBase.setImporteDescuento(DaoUtil.getBigDecimal(map.get("CUOTA")));
				transferenciaBase.setSaldoCapital(transferenciaBase.getCreditoUtilizado());
				//credito de nomina
				if(DaoUtil.getString(map.get("CLAVE")).equals(ClaveMisCreditos.CREDITO.getValor())) {
					transferenciaBase.setClave(ClaveMisCreditos.CREDITO);
					transferenciaBase.setCAT(DaoUtil.getBigDecimal(map.get("CAT")).toString());
				} else if(DaoUtil.getString(map.get("CLAVE")).equals(ClaveMisCreditos.ABONO.getValor())) {
					transferenciaBase.setClave(ClaveMisCreditos.ABONO);
				}
			}
			
			
			transferenciaBase.setTasa(DaoUtil.getBigDecimal(map.get("TASA")));
			transferenciaBase.setMoratoria(DaoUtil.getBigDecimal(map.get("MORA")));
			
			
			mapReferences.add(transferenciaBase);
			
		}

		return mapReferences;
	}

	@Override
	public List<Comprobante> buscarComprobantesReposicionTarjeta(
			String contrato, String tipo, String fechaDesde, String fechaHasta) {
		String sql = "select * from DC_REPTARJETA " + 
		         " where EntityID = '" + contrato + "' AND ESTATUS = 3" +
		         " and FECSOL BETWEEN '" + fechaDesde + "' AND '" + fechaHasta + "'" +
		         " and narra='P'" + //personas
		         " order by REFENUM desc";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			/*
			 beanRepTarjeta = new JBRepTarjeta();
	        	 beanRepTarjeta.setTyptrf(TYPTRF);
	        	 beanRepTarjeta.setFolio(rs.getString("REFENUM"));
	        	 beanRepTarjeta.setEntityId(rs.getString("ENTITYID"));
	        	 beanRepTarjeta.setUserId(rs.getString("USRSOL"));

	        	 beanRepTarjeta.setPyy(rs.getString("FECSOL").substring(0, 4));
	        	 beanRepTarjeta.setPmm(rs.getString("FECSOL").substring(4, 6));
	        	 beanRepTarjeta.setPdd(rs.getString("FECSOL").substring(6, 8));
	        	 beanRepTarjeta.setCuentaEmpresa(rs.getString("CTAEMPR"));
	        	 beanRepTarjeta.setCuentaEmpleado(rs.getString("CTAEMPL"));
	        	 beanRepTarjeta.setNombreCuentaEmpleado(rs.getString("NOMBRE"));
				result.add(beanRepTarjeta);
			*/
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN

		}

		return mapReferences;
	}

	@Override
	public List<Comprobante> buscarComprobantesInversionPerfecta(
			String contrato, String fechaDesde, String fechaHasta) {

		String sql = "select * from "+
				"DC_INVPERFECT"+
				" where contrato='" + contrato + "'"+
				" AND FECHA>=TIMESTAMP('" + fechaDesde
						+ "') AND FECHA<=TIMESTAMP('" + fechaHasta + "')"+
				" and emp_pers='P'";
		
       List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			/*
			 jb = new JBInvPDatos_E();
				final String STRYY = "20";
				String dia = "", mes = "";
				Double ints = 0.00;
				String intS = "";

				 / ****
				 * folio CHAR(14) NOT NULL, contrato CHAR(10), usuario CHAR(10),
				 * operacion CHAR(20), ctaCargo CHAR(20), nombre CHAR(60),
				 * cantidad DECIMAL(10,2), plazo CHAR(4), interes DECIMAL(10,2),
				 * mRegalo CHAR(60), ctaAbono CHAR(20), nombreA CHAR(60), fecha
				 * TIMESTAMP, fechVenc CHAR(10), moneda CHAR(10), tasa CHAR(10),
				 * tasaInt DECIMAL(10,2), status CHAR(1), concepto CHAR(100)
				 * ***** /

				jb.setFolio(rs.getString("folio"));
				jb.setFolio_aud(rs.getString("folio_aud"));
				jb.setContrato(rs.getString("contrato"));
				jb.setUsuario(rs.getString("usuario"));
				jb.setCuentaCargo(rs.getString("ctaCargo"));
				jb.setPlazo(rs.getString("plazo"));
				ints = rs.getDouble("cantidad");
				if (!ints.equals("")) {
					DecimalFormat df1 = new DecimalFormat("$###,###.00");
					intS = df1.format(ints);
				}
				jb.setCantidad(intS);
				jb.setFecha(rs.getString("fecha"));
				jb.setFchVec(rs.getString("fechVenc"));
				jb.setConcepto(rs.getString("concepto"));
				jb.setFechaT(rs.getTimestamp("fecha"));

				result.add(jb);
			*/
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN

		}

		return mapReferences;
	}

	@Override
	public List<Servicio> buscarComprobantesActivacionServicios(
			String contrato,  String fechaDesde, String fechaHasta) {
		
		
		String sql = "select ENTITYID, USERID, USERTYPE, SERVICEID, STATUS, DESCRIPCION, "+
			      " replace(CHAR (date(FECHACREACION), eur),'.','/') as FECHA, "+ 
			      " TIME(FECHACREACION) as HORA, FECHACREACION "+      
			      " FROM DC_PERMISOS_SERVICIOS "+
			      " WHERE ENTITYID='"+contrato+"'"+ 
			      " AND FECHACREACION>=TIMESTAMP('"+fechaDesde+"') AND FECHACREACION<=TIMESTAMP('"+fechaHasta+"')"+
			      " and USERTYPE='P' ";
				
		       List<Map<String, Object>> listResult;

				try {
					listResult = db2Dao.getJdbcTemplate().queryForList(sql,
							new Object[] { });
				} catch (EmptyResultDataAccessException e) {
					return null;
				}

		List<Servicio> mapReferences = new ArrayList<Servicio>();
		
		for (Map<String, Object> map : listResult) {
			Servicio servicio = new Servicio();
			servicio.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			servicio.setUserId(DaoUtil.getString(map.get("USERID")));
			servicio.setServiceType(DaoUtil.getInt(map.get("SERVICEID")));
			String strFecha = DaoUtil.getString(map.get("FECHACREACION"));
			servicio.setProgrammingDate(DaoUtil.getString(map.get("FECHACREACION")));
			servicio.setProgrammingDay(strFecha.substring(8, 10));      
			servicio.setProgrammingMonth(strFecha.substring(5, 7));
			servicio.setProgrammingYear(strFecha.substring(0, 4));
			servicio.setValidationDay(strFecha.substring(8, 10));      
			servicio.setValidationMonth(strFecha.substring(5, 7));
			servicio.setValidationYear(strFecha.substring(0, 4));
			servicio.setProgrammingHour(strFecha.substring(11, 13));
			servicio.setProgrammingMinute(strFecha.substring(14, 16));
			servicio.setTransactionCode(HistorialTipo.ACTIVACION_DE_SERVICIOS.getValor());
			servicio.setDescription(DaoUtil.getString(map.get("DESCRIPCION")));
			servicio.setReferenceNumber(servicio.getProgrammingDate()+"|"+servicio.getServiceType());
			servicio.setAfirmeNetReference(servicio.getProgrammingDate()+"|"+servicio.getServiceType());
			mapReferences.add(servicio);
		}
			
		return mapReferences;
	}

	@Override
	public List<Inversion> buscarComprobantesPagare(String contrato,
			String fechaDesde, String fechaHasta, String tipoPagare) {
		String sql = "select * from " + "DC_AHORRAFIRME" + " where contrato='"
				+ contrato + "' ";

		if (tipoPagare.equals(TipoTransferencia.PAGARE_GRADUAL.getValor()))
			sql += "AND IDPROD IN ('IG01','IG02') ";
		else if (tipoPagare.equals(TipoTransferencia.PAGARE_MULTIPLE.getValor()))
			sql += "AND IDPROD IN ('DT01','DT02') ";
		else
			sql += "AND IDPROD NOT IN ('IG01','IG02','DT01','DT02') ";
		
		sql += " AND FECHA>=TIMESTAMP('" + fechaDesde
				+ "') AND FECHA<=TIMESTAMP('" + fechaHasta + "')"
				+ " and emp_pers='P'";
		
       List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Inversion> mapReferences = new ArrayList<Inversion>();

		for (Map<String, Object> map : listResult) {
			
			Inversion pagare = new Inversion();

			pagare.setNumeroInversion(new BigDecimal(DaoUtil.getString(map.get("FOLIO"))));
			pagare.setAfirmeNetReference(DaoUtil.getString(map.get("FOLIO")));
			pagare.setReferenceNumber(DaoUtil.getString(map.get("FOLIO")));
			pagare.setNarrative(DaoUtil.getString(map.get("FOLIO_AUD")));
			pagare.setContractId(DaoUtil.getString(map.get("CONTRATO")));
			pagare.setUserReference(DaoUtil.getString(map.get("USUARIO")));
			
			pagare.setDebitAccount(DaoUtil.getString(map.get("CTACARGO")));
			pagare.setOrigen(new Cuenta());
			pagare.getOrigen().setNumber(pagare.getDebitAccount());
			
			pagare.setPlazo(Integer.parseInt(DaoUtil.getString(map.get("PLAZO"))));
			
			pagare.setAmount(DaoUtil.getBigDecimal(map.get("CANTIDAD")));
			
			String fechaOperacion = DaoUtil.getString(map.get("FECHA"));
			
			if (!StringUtils.isEmpty(fechaOperacion)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date operacion;
				try {
					operacion = format.parse(fechaOperacion);
					
					SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
					pagare.setProgrammingDate(fecha.format(operacion)); // Fecha Operacion
					pagare.setProgrammingDay(pagare.getProgrammingDate().substring(0,2));
					pagare.setProgrammingMonth(pagare.getProgrammingDate().substring(3,5));
					pagare.setProgrammingYear(pagare.getProgrammingDate().substring(8,10));
					
					SimpleDateFormat hora = new SimpleDateFormat("HH");
					//pagare.setValidationHour(hora.format(operacion)); // Hora Operacion
					pagare.setProgrammingHour(hora.format(operacion)); // Hora Operacion
					SimpleDateFormat minuto = new SimpleDateFormat("mm");
					//pagare.setValidationMinute(minuto.format(operacion)); // Minuto Operacion
					pagare.setProgrammingMinute(minuto.format(operacion)); // Minuto Operacion
					
				} catch (ParseException e) {
					LOG.error(e.getMessage());
				}
			}
			pagare.setValidationDate(DaoUtil.getString(map.get("FECHVENC"))); // Fecha Vencimiento
			pagare.setDescription(DaoUtil.getString(map.get("CONCEPTO")));
			
			pagare.setTasaInteres(DaoUtil.getBigDecimal(map.get("TASAINT")));
			pagare.setTaxCommision(DaoUtil.getBigDecimal(map.get("TASAINT")));
			
			String intereses = pagareDao.getIntereses(pagare.getAmount().intValue(), pagare.getTaxCommision().floatValue(), pagare.getPlazo());
			if (!StringUtils.isEmpty(intereses))
				pagare.setCommision(new BigDecimal(intereses));
			else
				pagare.setCommision(BigDecimal.ZERO);
			
			pagare.setCurrency(DaoUtil.getString(map.get("MONEDA")));
			pagare.setServicioInversion(DaoUtil.getString(map.get("OPERACION")));
			pagare.setCodigoRenovacion(DaoUtil.getString(map.get("TINVERSION")));
			
			if (!StringUtils.isEmpty(pagare.getCodigoRenovacion())) {
				
				String codigoRenovacion = pagare.getCodigoRenovacion();
				
				if (codigoRenovacion.equals("A")) {
					pagare.setTipoRenovacion("Capital + Interes");
				} else if (codigoRenovacion.equals("B") || codigoRenovacion.equals("W")) {
					pagare.setTipoRenovacion("Capital");
				} else if (codigoRenovacion.equals("E")) {
					pagare.setTipoRenovacion("Sin renovación");
				}
			}
			
			
			mapReferences.add(pagare);

		}

		return mapReferences;
	}

	@Override
	public List<ConvenioDomiciliacion> buscarComprobantesDomiciliacionServicios(
			String contrato, String fechaDesde, String fechaHasta) {
		
		
	//	select i.PSREFI, i.PSCONS, i.PSTITS, i.PSCONI, i.PSESTP, i.PSFULT, i.PSREFS, i.PSDSC1, p.padsce  from AFICYFILES.INHISTPF i,  AFICYFILES.inparmpf p where i.PSCONS =  p.PACONS and i.PSCONI = '1311266001'  AND i.PSFULT >= '20151116' AND i.PSFULT <= '20151116' AND i.PSCONI <> '' order by i.PSFULT desc, i.PSHULT desc

		String sql = "select i.PSREFI, i.PSCONS, i.PSTITS, i.PSCONI, i.PSESTP, i.PSFULT, i.PSREFS, i.PSDSC1, i.PSCTAC, i.PSHULT, p.padsce  from  "+ AS400_LIBRARY + "INHISTPF i, "+ AS400_LIBRARY + "inparmpf p where i.PSCONS =  p.PACONS and i.PSCONI = '"
				+contrato
	//	String sql = "select *  from "+ AS400_LIBRARY + "INHISTPF where PSCONI = '" + contrato
				+ "' ";
		sql += " AND i.PSFULT >= '" + fechaDesde + "' AND i.PSFULT <= '"
				+ fechaHasta + "' AND i.PSCONI <> ''";
		sql += " order by i.PSFULT desc, i.PSHULT desc";
		
       List<Map<String, Object>> listResult;

		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<ConvenioDomiciliacion> mapReferences = new ArrayList<ConvenioDomiciliacion>();

		for (Map<String, Object> map : listResult) {
			
			ConvenioDomiciliacion beanDomiciliacion = new ConvenioDomiciliacion();
				beanDomiciliacion.setDebitAccount(DaoUtil.getString(map.get("PSCTAC")));
				beanDomiciliacion.setFolio(DaoUtil.getString(map.get("PSREFI")));
				beanDomiciliacion.setAfirmeNetReference(DaoUtil.getString(map.get("PSREFI")));
				beanDomiciliacion.setReferenceNumber(DaoUtil.getString(map.get("PSREFI"))); 
				beanDomiciliacion.setServicio(DaoUtil.getString(map.get("padsce")));
				beanDomiciliacion.setTitular(DaoUtil.getString(map.get("PSTITS"))); 
				beanDomiciliacion.setContractId(DaoUtil.getString(map.get("PSCONI")));
				beanDomiciliacion.setTipoMovimiento(DaoUtil.getString(map.get("PSESTP")).equalsIgnoreCase("C") ? "D" : DaoUtil.getString(map.get("PSESTP")));
				if (beanDomiciliacion.getTipoMovimiento().equalsIgnoreCase("A")||beanDomiciliacion.getTipoMovimiento().equalsIgnoreCase("P")) {
					beanDomiciliacion.setNarrative("Contratación del Servicio Domiciliacion de pagos");
					beanDomiciliacion.setTipoMovimiento("A");
				} else if (beanDomiciliacion.getTipoMovimiento().equalsIgnoreCase(
						"D")) {
					beanDomiciliacion.setNarrative("Cancelación del Servicio Domiciliacion de pagos");
				} else {
					beanDomiciliacion.setNarrative("Cambio de Parámetros del Servicio Domiciliacion de pagos");
				}
				Util.getYear();
				beanDomiciliacion.setProgrammingYear(DaoUtil.getString(map.get("PSFULT")).substring(0, 4));
				beanDomiciliacion.setProgrammingMonth(DaoUtil.getString(map.get("PSFULT")).substring(4, 6));
				beanDomiciliacion.setProgrammingDay(DaoUtil.getString(map.get("PSFULT")).substring(6, 8));
				beanDomiciliacion.setProgrammingHour(DaoUtil.getString(map.get("PSHULT")).substring(0, 2));
				beanDomiciliacion.setProgrammingMinute(DaoUtil.getString(map.get("PSHULT")).substring(2, 4));
				beanDomiciliacion.setValidationYear(DaoUtil.getString(map.get("PSFULT")).substring(0, 4));
				beanDomiciliacion.setValidationMonth(DaoUtil.getString(map.get("PSFULT")).substring(4, 6));
				beanDomiciliacion.setValidationDay(DaoUtil.getString(map.get("PSFULT")).substring(6, 8));
				beanDomiciliacion.setValidationHour("00");
				beanDomiciliacion.setValidationMinute("00");
				beanDomiciliacion.setReferencia(DaoUtil.getString(map.get("PSREFS")));
				beanDomiciliacion.setDescripcion(DaoUtil.getString(map.get("PSDSC1")));

				mapReferences.add(beanDomiciliacion);

		}

		return mapReferences;
	}
	
	public ConvenioDomiciliacion buscarComprobanteDomiciliacionServicios(
			String contrato, String referencia) {
		
		
	//	select i.PSREFI, i.PSCONS, i.PSTITS, i.PSCONI, i.PSESTP, i.PSFULT, i.PSREFS, i.PSDSC1, p.padsce  from AFICYFILES.INHISTPF i,  AFICYFILES.inparmpf p where i.PSCONS =  p.PACONS and i.PSCONI = '1311266001'  AND i.PSFULT >= '20151116' AND i.PSFULT <= '20151116' AND i.PSCONI <> '' order by i.PSFULT desc, i.PSHULT desc

		String sql = "select i.PSREFI, i.PSCONS, i.PSTITS, i.PSCONI, i.PSESTP, i.PSFULT, i.PSREFS, i.PSDSC1, i.PSCTAC, i.PSHULT, i.PSPAGMA, p.padsce  from  "+ AS400_LIBRARY + "INHISTPF i, "+ AS400_LIBRARY + "inparmpf p where i.PSCONS =  p.PACONS and i.PSCONI = '"
				+contrato
	//	String sql = "select *  from "+ AS400_LIBRARY + "INHISTPF where PSCONI = '" + contrato
				+ "' ";
		sql += " AND i.PSREFI = '" + referencia + "'";
	
		
       List<Map<String, Object>> listResult;

		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<ConvenioDomiciliacion> mapReferences = new ArrayList<ConvenioDomiciliacion>();

		for (Map<String, Object> map : listResult) {
			
			ConvenioDomiciliacion beanDomiciliacion = new ConvenioDomiciliacion();
				beanDomiciliacion.setDebitAccount(DaoUtil.getString(map.get("PSCTAC")));
				beanDomiciliacion.setFolio(DaoUtil.getString(map.get("PSREFI")));
				beanDomiciliacion.setAfirmeNetReference(DaoUtil.getString(map.get("PSREFI")));
				beanDomiciliacion.setReferenceNumber(DaoUtil.getString(map.get("PSREFI"))); 
				beanDomiciliacion.setServicio(DaoUtil.getString(map.get("padsce")));
				beanDomiciliacion.setTitular(DaoUtil.getString(map.get("PSTITS"))); 
				beanDomiciliacion.setContractId(DaoUtil.getString(map.get("PSCONI")));
				beanDomiciliacion.setTipoMovimiento(DaoUtil.getString(map.get("PSESTP")).equalsIgnoreCase("C") ? "D" : DaoUtil.getString(map.get("PSESTP")));
				if (beanDomiciliacion.getTipoMovimiento().equalsIgnoreCase("A")||beanDomiciliacion.getTipoMovimiento().equalsIgnoreCase("P")) {
					beanDomiciliacion.setNarrative("Contratación del Servicio Domiciliacion de pagos");
					beanDomiciliacion.setTipoMovimiento("A");
				} else if (beanDomiciliacion.getTipoMovimiento().equalsIgnoreCase(
						"D")) {
					beanDomiciliacion.setNarrative("Cancelación del Servicio Domiciliacion de pagos");
				} else {
					beanDomiciliacion.setNarrative("Cambio de Parámetros del Servicio Domiciliacion de pagos");
				}
				Util.getYear();
				beanDomiciliacion.setProgrammingYear(DaoUtil.getString(map.get("PSFULT")).substring(0, 4));
				beanDomiciliacion.setProgrammingMonth(DaoUtil.getString(map.get("PSFULT")).substring(4, 6));
				beanDomiciliacion.setProgrammingDay(DaoUtil.getString(map.get("PSFULT")).substring(6, 8));
				beanDomiciliacion.setProgrammingHour(DaoUtil.getString(map.get("PSHULT")).substring(0, 2));
				beanDomiciliacion.setProgrammingMinute(DaoUtil.getString(map.get("PSHULT")).substring(2, 4));
				beanDomiciliacion.setValidationYear(DaoUtil.getString(map.get("PSFULT")).substring(0, 4));
				beanDomiciliacion.setValidationMonth(DaoUtil.getString(map.get("PSFULT")).substring(4, 6));
				beanDomiciliacion.setValidationDay(DaoUtil.getString(map.get("PSFULT")).substring(6, 8));
				beanDomiciliacion.setValidationHour("00");
				beanDomiciliacion.setValidationMinute("00");
				beanDomiciliacion.setReferencia(DaoUtil.getString(map.get("PSREFS")));
				beanDomiciliacion.setDescripcion(DaoUtil.getString(map.get("PSDSC1")));
				beanDomiciliacion.setPagoMax(Double.parseDouble(DaoUtil.getString(map.get("PSPAGMA"))));
				beanDomiciliacion.setCommision(DaoUtil.getBigDecimal(map.get("PSIMCO")));
				mapReferences.add(beanDomiciliacion);

		}

		return mapReferences.get(0);
	}

	

	@Override
	public List<AsociaMovil> buscarComprobantesAsociacionNumeroMovil(String contrato, String fechaDesde, String fechaHasta) {
		String sql = "call " + AS400_LIBRARY + "SP0001 ('"+contrato+"','"+fechaDesde+"','"+fechaHasta+"')";
		
		List<Map<String, Object>> listResult;

		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql, new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<AsociaMovil> mapReferences = new ArrayList<AsociaMovil>();

		for (Map<String, Object> rs : listResult) {
			AsociaMovil NumCelForSPEI = new AsociaMovil();
				NumCelForSPEI.setClientId(rs.get("SPHCUN").toString()); //cliente
				NumCelForSPEI.setCuenta(new Cuenta()); //cuenta
				NumCelForSPEI.getCuenta().setNumber(rs.get("SPHACC").toString());
				NumCelForSPEI.setContractId(rs.get("SPHAFN").toString()); //contrato
				NumCelForSPEI.setNumeroCelular(rs.get("SPHCEL").toString()); //celular
				NumCelForSPEI.setCiaTelMov(rs.get("SPHEMP").toString()); //cia celular
				NumCelForSPEI.setEstatus(rs.get("SPHSTS").toString()); //estatus
				NumCelForSPEI.setReferenceNumber(rs.get("SPHFOL").toString()); //folio de auditoria
				NumCelForSPEI.setAfirmeNetReference(rs.get("SPHFOL").toString()); //folio de auditoria
				NumCelForSPEI.setAccion(rs.get("SPHOPE").toString()); //tipo de operacion
				NumCelForSPEI.setNumCelAnt(rs.get("SPHCEA").toString()); //num cel anterior
				
				if(NumCelForSPEI.getAccion().equalsIgnoreCase("M") && NumCelForSPEI.getNumCelAnt().equalsIgnoreCase("0")){
					NumCelForSPEI.setDescription("Asociación");
				}else if(NumCelForSPEI.getAccion().equalsIgnoreCase("M")  && !NumCelForSPEI.getNumCelAnt().equalsIgnoreCase("0")){
					NumCelForSPEI.setDescription("Modificación");
				}else if(NumCelForSPEI.getAccion().equalsIgnoreCase("D")){
					NumCelForSPEI.setDescription("Desasociación");
				}
				
				String fechaYYYYMMDD=rs.get("SPHFSI").toString();
				NumCelForSPEI.setProgrammingDay(fechaYYYYMMDD.substring(6, 8)); //AÑO
				NumCelForSPEI.setProgrammingMonth(fechaYYYYMMDD.substring(4, 6)); //MES
				NumCelForSPEI.setProgrammingYear(fechaYYYYMMDD.substring(2, 4)); //DIA
				NumCelForSPEI.setFecha(NumCelForSPEI.getProgrammingDay()+"/"+NumCelForSPEI.getProgrammingMonth()+"/"+NumCelForSPEI.getProgrammingYear());
				NumCelForSPEI.setFechaCaptura(Util.getCurrentYMD()+Util.getCurrentHMS());
				
				String horaYYYYMMDD=Util.formatHora(rs.get("SPHHSI").toString());
				NumCelForSPEI.setProgrammingMinute(horaYYYYMMDD.substring(2, 4)); // hora
				NumCelForSPEI.setProgrammingHour(horaYYYYMMDD.substring(0, 2)); //minuto
				NumCelForSPEI.setHora(Util.formatHour(NumCelForSPEI.getProgrammingHour(), NumCelForSPEI.getProgrammingMinute()));
				
                if(!NumCelForSPEI.getEstatus().equalsIgnoreCase("4")&&!NumCelForSPEI.getEstatus().equalsIgnoreCase("5")&&!NumCelForSPEI.getEstatus().equalsIgnoreCase("6")){
                	 if(!(NumCelForSPEI.getEstatus().equalsIgnoreCase("2")&&NumCelForSPEI.getAccion().equalsIgnoreCase("M"))){
                		 mapReferences.add(NumCelForSPEI);
                	 }
                }
		}

		return mapReferences;
	}

	public String buscarAtributoINOTR(String ref, String contrato, String atrib){
		
		String sql = "select "+atrib+" from "+ AS400_LIBRARY + "inotrl0 where inoref = ? and inousr = ?";
		List<Map<String, Object>> listResult= new ArrayList<Map<String,Object>>();
		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { ref, contrato });
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error al consultar el tipo de servicio.");
		}
		if(listResult.size()==0)
			return "";
		return (String) listResult.get(0).get(atrib);
	}
	
	
	public String buscarNombreServicio(String tipo){
		
		String sql = "select sernam from "+ AS400_LIBRARY + "inser where sercom= ?";
		List<Map<String, Object>> listResult= new ArrayList<Map<String,Object>>();
		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { Integer.parseInt(tipo.trim()) });
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error al consultar el tipo de servicio.");
		}
		
		return (String) listResult.get(0).get("sernam");
	}
	
	@Override
	public List<Comprobante> buscarComprobantesINOTRGenerico(String contrato,
			String tipo, String fechaDesde, String fechaHasta) {
		String sql = "SELECT INOREF,INOVD1,INOVD2,INOVD3,INODSC FROM "+ AS400_LIBRARY + "inotrl0 WHERE INOUSR = '" + contrato + "' AND INOPMV = '"+tipo+"' ";       
		sql = sql + "AND ((inovd3*10000)+ (inovd1*100) + inovd2) >= "+fechaDesde +" and ((inovd3*10000)+ (inovd1*100) + inovd2) <= "+fechaHasta ;
		sql= sql+"ORDER BY INOREF DESC";;
		
       List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			/*
			 numRef.addElement(rs.getString("INOREF"));
        	 fechaReg.addElement(rs.getString("INOVD2")+"/"+rs.getString("INOVD1")+"/"+rs.getString("INOVD3"));
        	 descrip.addElement(rs.getString("INODSC"));
        	

         }
         
         oAviso.setNumRef(numRef);
         oAviso.setFechaReg(fechaReg);
         oAviso.setDescrip(descrip);
        
			*/
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN

		}

		return mapReferences;
	}

	@Override
	public List<Comprobante> buscarComprobantesProgramacionPagosTDC(
			String contrato, String fechaDesde, String fechaHasta,
			String programacionCancelacion) {
		String sql = "select A.TCREFE,A.TCCTAC,A.TCTARJ,B.TCNOMB,A.TCTPPA,A.TCFSCA,A.TCIMPO,";
		if (programacionCancelacion != null
				&& programacionCancelacion.trim().equals("A")) {
			sql = sql + " A.TCHACT, A.TCFACT ";
		} else {
			sql = sql + " A.TCHCAN, A.TCFCAN ";
		}
		sql = sql + " from "+ AS400_LIBRARY + "TC6000PF A join "+ AS400_LIBRARY + "TC5000L1 B  "
				+ "on A.TCTARJ=B.TCTARJ and A.TCTSTS='"
				+ programacionCancelacion
				+ "' and A.TCITIP='P' where a.tciusr='" + contrato + "'"
				+ " and A.TCFSCA >=" + fechaDesde + " and A.TCFSCA<= "
				+ fechaHasta;
		
       List<Map<String, Object>> listResult;

		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		for (Map<String, Object> map : listResult) {
			/*
			 try{
	      		   format = new SimpleDateFormat("yyyyMMdd");
	      		   //fecha = format.parse(rs.getString(6));
	      		   fecha = format.parse(rs.getString(9));
	      		   format.applyPattern("dd/MM/yy");
	      		   strFecha = format.format(fecha);
	      	   }catch(Exception e){
	      		 strFecha ="";
	      	   }
            	 Programacion_TC = new JBDomPagTC();
            	 Programacion_TC.setReferencia(rs.getString(1));
            	 Programacion_TC.setCuentaCargo(rs.getString(2));            	 
            	 Programacion_TC.setNumTC(rs.getString(3));
            	 Programacion_TC.setOUSER(entityId);
            	 Programacion_TC.setNameTC(rs.getString(4));            	 
            	 Programacion_TC.setTipoProg(rs.getString(5));
            	 Programacion_TC.setODATE(strFecha);
            	 Programacion_TC.setImporte(rs.getString(7));
            	 String hora = rs.getString(8);
            	 if(hora !=null && hora.trim().length() ==5){
            		 hora = "0"+hora;
            	 }
            	 try{
  	      		   format = new SimpleDateFormat("HHmmss");
  	      		   fecha = format.parse(hora);
  	      		   format.applyPattern("HH:mm");
  	      		   hora = format.format(fecha);
  	      	   }catch(Exception e){
  	      		   hora ="";
  	      	   }
            	 Programacion_TC.setProgramacionHora(hora);

        	 /  *record.add(rs.getString(1));
        	 record.add(rs.getString(2));            	 
        	 record.add(rs.getString(3));
        	 record.add(entityId);
        	 record.add(rs.getString(4));            	 
        	 record.add(rs.getString(5));
        	 record.add(rs.getString(6));
        	 record.add(rs.getString(7));
        	 *   /
                listMsg.add(Programacion_TC);
			*/
			Comprobante transferenciaBase=new Comprobante(); 
			//TODO: Setear los datos en BEAN

		}

		return mapReferences;
	}

	@Override
	public List<Alertas> buscarComprobantesAlertas(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		String sql = "select * from DC_ALERTAS where entityid='"+contrato+"' AND typtrf='"+ tipo + "' AND " +
					 "((descrip = 'A' AND fechac >= '"+fechaDesde+"' AND  fechac <= '" + fechaHasta + "' ) OR " +
					 "((descrip = 'M' OR descrip='D') AND fecham >= '"+fechaDesde+"' AND  fecham <= '" + fechaHasta + "' )) ";
		
       List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Alertas> mapReferences = new ArrayList<Alertas>();

		for (Map<String, Object> rs : listResult) {
			Alertas oBeanAlertas = new Alertas();
			
			oBeanAlertas.setContractId(rs.get("ENTITYID").toString());
			oBeanAlertas.setDcibs_ref(rs.get("DCIBS_REF").toString());
			oBeanAlertas.setTipoTransferencia(TipoTransferencia.findByValue(rs.get("TYPTRF").toString()));
			//oBeanAlertas.setFolio(rs.get("REFENUM").toString());
			oBeanAlertas.setReferenceNumber(rs.get("REFENUM").toString());
			oBeanAlertas.setAfirmeNetReference(rs.get("REFENUM").toString());
			oBeanAlertas.setCobroSMS(rs.get("FEE_IVA").toString());
			oBeanAlertas.setComision(rs.get("FEES").toString());
			oBeanAlertas.setIVA(rs.get("IVA").toString());
			oBeanAlertas.setEmail(rs.get("CORREO1").toString());
			oBeanAlertas.setEmail2(rs.get("CORREO2").toString());
			oBeanAlertas.setCelular(rs.get("CELULAR").toString());
			oBeanAlertas.setClientId(rs.get("CLIENTE").toString());
			oBeanAlertas.setDescripcion(rs.get("DESCRIP").toString());
			if(oBeanAlertas.getDescripcion().equalsIgnoreCase("A")) {
				oBeanAlertas.setTipOpe("Contratación de Alertas Afirme");
			} else if (oBeanAlertas.getDescripcion().equalsIgnoreCase("M")) {
				oBeanAlertas.setTipOpe("Modificación de Alertas Afirme");
			} else {
				oBeanAlertas.setTipOpe("Cancelación de Alertas Afirme");
			}
			
			if(oBeanAlertas.getCelular()!=null && !oBeanAlertas.getCelular().equals("") && !oBeanAlertas.getCelular().equals("0")) {
				oBeanAlertas.setTieneCelular("Y");
			}else {
				oBeanAlertas.setTieneCelular("N");
				oBeanAlertas.setCelular("");
			}
				   
			oBeanAlertas.setFechaContrata(rs.get("FECHAC").toString());
			oBeanAlertas.setHoraContrata(rs.get("HORAC").toString());
			oBeanAlertas.setFechaModifica(rs.get("FECHAM").toString());
			oBeanAlertas.setHoraModifica(rs.get("HORAM").toString());
			 
			oBeanAlertas.setTieneCuentas(rs.get("TIENECUENTAS").toString());
			oBeanAlertas.setTieneTarjetas(rs.get("TIENETARJ").toString());
			oBeanAlertas.setTieneInversiones(rs.get("TIENEINV").toString());
			oBeanAlertas.setTieneCreditos(rs.get("TIENECRED").toString());
			oBeanAlertas.setCuentaDeposito(new BigDecimal(rs.get("CUENTADEPOSITO").toString()));
			oBeanAlertas.setCuentaSaldo(new BigDecimal(rs.get("CUENTASALDO").toString()));
			oBeanAlertas.setCuentaComerC1(rs.get("CUENTACOMERC1").toString());
			oBeanAlertas.setCuentaComerC2(rs.get("CUENTACOMERC2").toString());
			oBeanAlertas.setCuentaComerCel(rs.get("CUENTACOMERCEL").toString());
			oBeanAlertas.setCuentaOtrosC1(rs.get("CUENTAOTROSC1").toString());
			oBeanAlertas.setCuentaOtrosC2(rs.get("CUENTAOTROSC2").toString());
			oBeanAlertas.setCuentaOtrosCel(rs.get("CUENTAOTROSCEL").toString());
			oBeanAlertas.setCuentaRetC1(rs.get("CUENTARETC1").toString());
			oBeanAlertas.setCuentaRetC2(rs.get("CUENTARETC2").toString());
			oBeanAlertas.setCuentaRetCel(rs.get("CUENTARETCEL").toString());
			oBeanAlertas.setCuentaDepC1(rs.get("CUENTADEPC1").toString());
			oBeanAlertas.setCuentaDepC2(rs.get("CUENTADEPC2").toString());
			oBeanAlertas.setCuentaDepCel(rs.get("CUENTADEPCEL").toString());
			oBeanAlertas.setCuentaSaldoC1(rs.get("CUENTASALDOC1").toString());
			oBeanAlertas.setCuentaSaldoC2(rs.get("CUENTASALDOC2").toString());
			oBeanAlertas.setCuentaSaldoCel(rs.get("CUENTASALDOCEL").toString());
			oBeanAlertas.setCuentaSolChC1(rs.get("CUENTASOLCHC1").toString());
			oBeanAlertas.setCuentaSolChC2(rs.get("CUENTASOLCHC2").toString());
			oBeanAlertas.setCuentaSolChCel(rs.get("CUENTASOLCHCEL").toString());
			oBeanAlertas.setCuentaChDispC1(rs.get("CUENTACHDISPC1").toString());
			oBeanAlertas.setCuentaChDispC2(rs.get("CUENTACHDISPC2").toString());
			oBeanAlertas.setCuentaChDispCel(rs.get("CUENTACHDISPCEL").toString());
			oBeanAlertas.setCuentaConsSC1(rs.get("CUENTACONSSC1").toString());
			oBeanAlertas.setCuentaConsSC2(rs.get("CUENTACONSSC2").toString());
			oBeanAlertas.setCuentaConsSCel(rs.get("CUENTACONSSCEL").toString());
			oBeanAlertas.setTcDisponible(new BigDecimal(rs.get("TCDISPONIBLE").toString()));
			oBeanAlertas.setTcComerC1(rs.get("TCCOMERC1").toString());
			oBeanAlertas.setTcComerC2(rs.get("TCCOMERC2").toString());
			oBeanAlertas.setTcComerCel(rs.get("TCCOMERCEL").toString());
			oBeanAlertas.setTcRetC1(rs.get("TCRETC1").toString());
			oBeanAlertas.setTcRetC2(rs.get("TCRETC2").toString());
			oBeanAlertas.setTcRetCel(rs.get("TCRETCEL").toString());
			oBeanAlertas.setTcPagoC1(rs.get("TCPAGOC1").toString());
			oBeanAlertas.setTcPagoC2(rs.get("TCPAGOC2").toString());
			oBeanAlertas.setTcPagoCel(rs.get("TCPAGOCEL").toString());
			oBeanAlertas.setTcDispC1(rs.get("TCDISPC1").toString());
			oBeanAlertas.setTcDispC2(rs.get("TCDISPC2").toString());
			oBeanAlertas.setTcDispCel(rs.get("TCDISPCEL").toString());
			oBeanAlertas.setInvApertC1(rs.get("INVAPERTC1").toString());
			oBeanAlertas.setInvApertC2(rs.get("INVAPERTC2").toString());
			oBeanAlertas.setInvApertCel(rs.get("INVAPERTCEL").toString());
			oBeanAlertas.setInvLiquidC1(rs.get("INVLIQUIDC1").toString());
			oBeanAlertas.setInvLiquidC2(rs.get("INVLIQUIDC2").toString());
			oBeanAlertas.setInvLiquidCel(rs.get("INVLIQUIDCEL").toString());
			oBeanAlertas.setInvRenovC1(rs.get("INVRENOVC1").toString());
			oBeanAlertas.setInvRenovC2(rs.get("INVRENOVC2").toString());
			oBeanAlertas.setInvRenovCel(rs.get("INVRENOVCEL").toString());
			oBeanAlertas.setCreditoDias(Integer.parseInt(rs.get("CREDITODIAS").toString()));
			oBeanAlertas.setCreditoApertC1(rs.get("CREDITOAPERTC1").toString());
			oBeanAlertas.setCreditoApertC2(rs.get("CREDITOAPERTC2").toString());
			oBeanAlertas.setCreditoApertCel(rs.get("CREDITOAPERTCEL").toString());
			oBeanAlertas.setCreditoDispC1(rs.get("CREDITODISPC1").toString());
			oBeanAlertas.setCreditoDispC2(rs.get("CREDITODISPC2").toString());
			oBeanAlertas.setCreditoDispCel(rs.get("CREDITODISPCEL").toString());
			oBeanAlertas.setCreditoFechaC1(rs.get("CREDITOFECHAC1").toString());
			oBeanAlertas.setCreditoFechaC2(rs.get("CREDITOFECHAC2").toString());
			oBeanAlertas.setCreditoFechaCel(rs.get("CREDITOFECHACEL").toString());
			oBeanAlertas.setCreditoLiquidC1(rs.get("CREDITOLIQUIDC1").toString());
			oBeanAlertas.setCreditoLiquidC2(rs.get("CREDITOLIQUIDC2").toString());
			oBeanAlertas.setCreditoLiquidCel(rs.get("CREDITOLIQUIDCEL").toString());
			oBeanAlertas.setCreditoPagoC1(rs.get("CREDITOPAGOC1").toString());
			oBeanAlertas.setCreditoPagoC2(rs.get("CREDITOPAGOC2").toString());
			oBeanAlertas.setCreditoPagoCel(rs.get("CREDITOPAGOCEL").toString());
			
			mapReferences.add(oBeanAlertas);
		}

		return mapReferences;
	}

	@Override
	public List<TransferenciaBase> buscarComprobantesOrdenPago(String contrato, List<Cuenta> cuentas, String fechaDesde, String fechaHasta) {
		List<TransferenciaBase> mapReferences = new ArrayList<TransferenciaBase>();
		
		for(Cuenta cue : cuentas){
			String sql = "Call "+ AS400_LIBRARY + "OP_HCO2('"+contrato+"', '', '"+fechaDesde+"', '"+fechaHasta+"', '" + cue.getNumber().trim() + "')";
			
			List<Map<String, Object>> listResult;

			try {
				listResult = as400Dao.getJdbcTemplate().queryForList(sql, new Object[] { });
			} catch (EmptyResultDataAccessException e) {
				return null;
			}

			for (Map<String, Object> map : listResult) {
				
				 CashExpress oOrden=new CashExpress();
				 	oOrden.setReferenceNumber(map.get("OPREF").toString());
				 	oOrden.setAfirmeNetReference(map.get("OPREF").toString());
					oOrden.setContractId(contrato);
					oOrden.setOrigen(new Cuenta());
					oOrden.getOrigen().setNumber(DaoUtil.getString(map.get("OPCTA")));
					oOrden.setClientId(DaoUtil.getString(map.get("OPCUN")));
					oOrden.setNickName(DaoUtil.getString(map.get("OPEMI")));
					oOrden.setOrden(DaoUtil.getString(map.get("OPORD")));
					oOrden.setUserReference(oOrden.getOrden());
					oOrden.setBeneficiaryName(DaoUtil.getString(map.get("OPBEN")));
					oOrden.setCelular(map.get("OPCEL").toString());
					oOrden.setDestino(new Cuenta());
					oOrden.getDestino().setNumber(DaoUtil.getString(map.get("OPCEL")));
					oOrden.setCreditAccount(DaoUtil.getString(map.get("OPCEL")));
					oOrden.setAmount(DaoUtil.getBigDecimal(map.get("OPIMP")));
					oOrden.setFechaEmi(Util.getFechaFormat_ddMMyyy(map.get("OPFEM").toString()));
					
						oOrden.setValidationDay(map.get("OPFEM").toString().substring(6, 8));
						oOrden.setValidationMonth(map.get("OPFEM").toString().substring(4, 6));
						oOrden.setValidationYear(map.get("OPFEM").toString().substring(0, 4));
						
						oOrden.setProgrammingHour(map.get("OPHEM").toString().substring(0, 2));
						oOrden.setProgrammingMinute(map.get("OPHEM").toString().substring(2, 4));
					
					oOrden.setState(DaoUtil.getString(map.get("OPEST")));
					oOrden.setDescription(DaoUtil.getString(map.get("OPINS")));
					oOrden.setEstadoCajero(DaoUtil.getString(map.get("OPSTS")));
					oOrden.setFechaPago(DaoUtil.getBigDecimal(map.get("OPFCO")));
					
					try{
						String lugar="";
						if(DaoUtil.getString(map.get("OPSUC")).trim().length()>0)
							lugar+="Sucursal "+DaoUtil.getString(map.get("OPSUC")).trim();
						if(DaoUtil.getString(map.get("OPATM")).trim().length()>0)
							lugar+="Cajero "+DaoUtil.getString(map.get("OPATM")).trim();
						oOrden.setLugarPago(lugar);
					}catch(Exception ex){}
					try{
						oOrden.setApprobationNumber(DaoUtil.getInt(map.get("OPREF")));
					}catch(Exception ex){
						ex.printStackTrace();
					}
					mapReferences.add(oOrden);

			}
		}
		return mapReferences;
	}

	@Override
	public List<Comprobante> buscarComprobantesAltaCuentas(String contrato,
			String fechaDesde, String fechaHasta) {
		String sql = "SELECT convNum, entityId, sernam, fechaCreacion, seracc, tcNombre  from DC_CONVENIO "+
         " WHERE ENTITYID='"+contrato+"' and  fechaCreacion >= '"+fechaDesde+"' and fechaCreacion <= '"+fechaHasta+
         "' and (sernam = 'TARJETA DE CREDITO BANCOS RED' or sernam = 'TARJETA DE CREDITO AFIRME TERCEROS' or sernam = 'SEGUROS AFIRME') ";
		
       List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Comprobante> mapReferences = new ArrayList<Comprobante>();
		String strFecha="";
		
		for (Map<String, Object> map : listResult) {
			Comprobante comprobante = new Comprobante();
			comprobante.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			comprobante.setAfirmeNetReference(DaoUtil.getBigDecimal(map.get("CONVNUM")).toString());
			comprobante.setReferenceNumber(DaoUtil.getBigDecimal(map.get("CONVNUM")).toString());
			comprobante.setAccount(DaoUtil.getString(map.get("SERACC")));
			comprobante.setCreditAccount(DaoUtil.getString(map.get("SERACC")));
			
			comprobante.setTransactionCode(HistorialTipo.REGISTRO_DE_TARJETAS_DE_CREDITO_Y_SEGUROS_AFIRME.getValor());    // 
			comprobante.setTransaccionDescrip(DaoUtil.getString(map.get("SERNAM")));

        	 try{
        		 strFecha = DaoUtil.getString(map.get("FECHACREACION"));
     			// fecha
        		 comprobante.setProgrammingDay(strFecha.substring(6, 8));      
        		 comprobante.setProgrammingMonth(strFecha.substring(4, 6));
        		 comprobante.setProgrammingYear(strFecha.substring(2, 4));
        		 //comprobante.setHORACREACION(strHora.substring(10,14));

        	 }catch(Exception e){
        		 strFecha="";
        	 }
        	 
        	 mapReferences.add(comprobante);  
		}

		return mapReferences;
	}

	@Override
	public DC_CONVENIO buscarComprobanteRegistroTarjetas(String contrato, String convnum) {
		String sql = "SELECT convNum, entityId, sernam, fechaCreacion, seracc, tcNombre, sercom  from DC_CONVENIO "+
         " WHERE ENTITYID='" + contrato + "' and  convnum = " + convnum;
		
       List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String strFecha="";
		
		DC_CONVENIO dcConv = new DC_CONVENIO();
		for (Map<String, Object> map : listResult) {
        	 dcConv.setCONVNUM(DaoUtil.getBigDecimal(map.get("CONVNUM")));
			 dcConv.setENTITYID(DaoUtil.getString(map.get("ENTITYID")));
			 dcConv.setSERNAM(DaoUtil.getString(map.get("SERNAM")));
			 if(DaoUtil.getString(map.get("SERCOM")).equals("601"))
				 dcConv.setTipoOperacion(TipoCuentaDestino.TC_SEGUROS.getValor());
			 else
				 dcConv.setTipoOperacion(TipoCuentaDestino.TC_TARJETAS.getValor());
			 
        	 dcConv.setFECHACREACION(DaoUtil.getString(map.get("FECHACREACION")));
        	 dcConv.setSERACC(DaoUtil.getString(map.get("SERACC")));
        	 dcConv.setTCNOMBRE(DaoUtil.getString(map.get("TCNOMBRE")));
		}

		return dcConv;
	}

	@Override
	public boolean existeInformacionSPEI(TransferenciaBase comprobante) {
		//Si existe de una vez le ponemos los datos al comprobante
		boolean encontro=false;
		String sql = "select TRACKING_CODE,SPEI_REF from DC_SPEI_INFO where REFENUM = ? ";
		List<Map<String, Object>> listResult;
		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { comprobante.getReferenceNumber() });
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		for (Map<String, Object> map : listResult) {
			encontro = true;
			comprobante.setTrackingCode(DaoUtil.getString(map.get("TRACKING_CODE")));
			comprobante.setSpeiReference(DaoUtil.getString(map.get("SPEI_REF")));
		}
		return encontro;
	}

	public String getOrdenCashExpress(String ref, String contrato) {
		//Si existe de una vez le ponemos los datos al comprobante
		String sql = "select ORPORD from "+ AS400_LIBRARY + "opi002pf where orpref='"+ref+"' and orpcto='"+contrato+"'";
		List<Map<String, Object>> listResult;
		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return "";
		}
		return DaoUtil.getString(listResult.get(0).get("ORPORD"));
	}
	
	@Override
	public void obtenerInformacionSPEI(TransferenciaBase comprobante) {
		//Si existe de una vez le ponemos los datos al comprobante
		String sql = "select MOCVRA, MOFOLI from "+ AS400_LIBRARY + TABLA_SPEI_DIA+" where MOREF = ? union select HMCVRA, HMFOLI from "+ AS400_LIBRARY +  TABLA_SPEI_HISTORICA+ " where HMREF = ?";
		List<Map<String, Object>> listResult;
		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { comprobante.getReferenceNumber(), comprobante.getReferenceNumber() });
		} catch (EmptyResultDataAccessException e) {
			return;
		}
		for (Map<String, Object> map : listResult) {

			comprobante.setTrackingCode(DaoUtil.getString(map.get("MOCVRA")));
			comprobante.setSpeiReference(DaoUtil.getString(map.get("MOFOLI")));
		}
		return;
		
	}
	@Override
	public void actualizarInformacionSPEI(TransferenciaBase comprobante) {
		String sql = "update DC_SPEI_INFO set TRACKING_CODE = ?, SPEI_REF = ? where REFENUM = ?";
		Object[] params = new Object[] {
				comprobante.getTrackingCode(),
				comprobante.getSpeiReference(),
				comprobante.getReferenceNumber()};
		
		db2Dao.getJdbcTemplate().update(sql, params);
	}
	@Override
	public void insertaInformacionSPEI(TransferenciaBase comprobante) {
		String sql = "insert into DC_SPEI_INFO (TRACKING_CODE, SPEI_REF, REFENUM) values (?, ?, ?)";
		Object[] params = new Object[] {
				comprobante.getTrackingCode(),
				comprobante.getSpeiReference(),
				comprobante.getReferenceNumber()};
		db2Dao.getJdbcTemplate().update(sql, params);
	}

	@Override
	public void insertaConfirmacionActivacionTDC(Comprobante transferenciaBase) {
		String sql = "INSERT INTO DC_CONFMSG" +
          		"(ENTITYID,USERID,DEBACC,BENENAME,BENEADD,DESCRIP,PDD,PMM,PYY,VDD,VMM,VYY,PHH,PSS,VHH,VSS,TYPTRF,REFENUM,SERVNUM,DCIBS_REF,AMOUNT)" +
          		" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] params = new Object[] {
				transferenciaBase.getContractId(),
				transferenciaBase.getUserId(),
				transferenciaBase.getOrigen() != null ? transferenciaBase.getOrigen().getNumber() : transferenciaBase.getDebitAccount(),
				transferenciaBase.getBeneficiaryName(),
				transferenciaBase.getClientId(),
				transferenciaBase.getFlag(),
				transferenciaBase.getValidationDay(),				
				transferenciaBase.getValidationMonth(),
				transferenciaBase.getValidationYear(),
				transferenciaBase.getValidationDay(),				
				transferenciaBase.getValidationMonth(),
				transferenciaBase.getValidationYear(),
				transferenciaBase.getValidationHour(),
				transferenciaBase.getValidationMinute(),
				transferenciaBase.getValidationHour(),
				transferenciaBase.getValidationMinute(),
				transferenciaBase.getTipoTransferencia().getValor(),
				transferenciaBase.getReferenceNumber(),
				transferenciaBase.getAccion(),
				transferenciaBase.getAfirmeNetReference(),
				transferenciaBase.getAmount()
				
};
		
		db2Dao.getJdbcTemplate().update(sql, params);
		
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
	public List<Comprobante> buscarComprobantesEliminacionCuentasDestino(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		
		List<Comprobante> mapReferences = new ArrayList<Comprobante>();
		
		// ------------------------------------ fix obtener numeros de la fecha ------------------
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date fechaDesdeDate = null;
		Date fechaHastaDate = null;
		try {
			fechaDesdeDate = format.parse(fechaDesde); // fechaDesde="20150101"
			fechaHastaDate = format.parse(fechaHasta); // fechaHasta="20151130"
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		format.applyPattern("yyyy-MM-dd");
//		String[] strFechaDesde = format.format( fechaDesdeDate ).split("/"); // 01/01/15
//		String[] strFechaHasta = format.format( fechaHastaDate ).split("/"); // 30/11/15
		
//		String strFechaDesde = format.format( fechaDesdeDate ); // 2014-11-14
//		String strFechaHasta = format.format( fechaHastaDate );
//		
//		// 
//        String fechaIni = strFechaDesde[2] + strFechaDesde[1] + strFechaDesde[0] + "000000"; // fechaIni	2014-11-14 00:00:00
//		String fechaFin = strFechaHasta[2] + strFechaHasta[1] + strFechaHasta[0] + "240000"; // fechaFin	2015-11-14 24:00:00'
//		
		String fechaIni = format.format( fechaDesdeDate ) + " 00:00:00"; // fechaIni	2014-11-14 00:00:00
		String fechaFin = format.format( fechaHastaDate ) + " 24:00:00"; // fechaFin	2015-11-14 24:00:00'
        // ------------------------------------ // fix obtener numeros de la fecha ------------------

		// NumeroTipoOperacion valor el combo donde se eliminan las cuentas
		String LOCAL_VALOR_SE_COMBO_TC_Y_SEGURO_AFIRME = "se";
		
		// Nombre tipo de operacion.
		String LOCAL_NOMBRE_TRANSACCION_TC_Y_SEGURO_AFIRME = "Tarjetas de Crédito y Seguro Afirme";
		
		// Tipo de transaccion.
		String LOCAL_TIPO_TRANSACCION_NOMINA = "NOMINA";
		String LOCAL_TIPO_TRANSACCION_PAGO_INTERBANCARIO = "PAGO INTERBANCARIO";
		String LOCAL_TIPO_TRANSACCION_PAGO_SERVICIOS = "Pago Servicios";
		String LOCAL_TIPO_TRANSACCION_TRANSFERENCIA_SPEI = "TRANSFERENCIA SPEI";
		String LOCAL_TIPO_TRANSACCION_TRASPASO_TERCEROS = "TRASPASO TERCEROS";
		String LOCAL_TIPO_TRANSACCION_DOMINGO_ELECTRONICO = "DOMINGO ELECTRÓNICO";
		// swift le pone S o F
		
		String sql = "SELECT CONVNUM,STATUS,FECHA_BORRADO,TIPO_TRANSACCION,ENTITYID,CUENTA,DESCRIPCION_TRANSACCION,"
					+" TIPO_EMPRESA,NOMBRE_BANCO,PROPIETARIO_CTA,TITULAR_CTA,ESTATUS_PT FROM DC_CONVENIO_LOG"
					+" WHERE ENTITYID = '" + contrato + "'"
					+" AND FECHA_BORRADO >= TO_DATE('" + fechaIni + "', 'YYYY-MM-DD HH24:MI:SS')"
					+" AND FECHA_BORRADO <= TO_DATE('" + fechaFin + "', 'YYYY-MM-DD HH24:MI:SS')"
					+" AND ("
					+" TIPO_TRANSACCION    = '" + LOCAL_TIPO_TRANSACCION_NOMINA              + "'" 
					+" OR TIPO_TRANSACCION = '" + LOCAL_TIPO_TRANSACCION_PAGO_INTERBANCARIO  +"'"
					+" OR TIPO_TRANSACCION = '" + LOCAL_TIPO_TRANSACCION_PAGO_SERVICIOS      +"'"
					+" OR TIPO_TRANSACCION = '" + LOCAL_TIPO_TRANSACCION_TRANSFERENCIA_SPEI  +"'"
					+" OR TIPO_TRANSACCION = '" + LOCAL_TIPO_TRANSACCION_TRASPASO_TERCEROS   +"'"
					+" OR TIPO_TRANSACCION = '" + LOCAL_TIPO_TRANSACCION_DOMINGO_ELECTRONICO +"'"
					+") ORDER BY FECHA_BORRADO ASC";
		
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		for (Map<String, Object> map : listResult) {
			
			Comprobante transferenciaBase=new Comprobante();
			
			// fecha para simular folio.
			String cuenta = DaoUtil.getString(map.get("CUENTA"));
			String horaOperacion = new SimpleDateFormat("HH:mm").format( map.get("FECHA_BORRADO"));     // 2015-09-17 20:03:10.0
			String fechaOperacion = new SimpleDateFormat("dd/MM/yy").format( map.get("FECHA_BORRADO")); // 2015-09-17 20:03:10.0
			String[] strFecha = fechaOperacion.split("/"); // 01/01/15
						
			// folio
			transferenciaBase.setReferenceNumber( fechaOperacion.replace("/", "") + horaOperacion.replace(":", "") ); // no tiene foli, simula folio.
			
			// fecha
			transferenciaBase.setProgrammingDay( strFecha[0] );      
			transferenciaBase.setProgrammingMonth( strFecha[1] );
			transferenciaBase.setProgrammingYear( strFecha[2] );
			
			// cuenta abono
			transferenciaBase.setCreditAccount( cuenta );
			
			// datos extra
			transferenciaBase.setAfirmeNetReference(  cuenta + "_" + fechaOperacion.replace("/", "") + horaOperacion.replace(":", "") );     // valor de radio button
			transferenciaBase.getAtributosGenericos().put("hora",horaOperacion );                                            // 20:03
			transferenciaBase.getAtributosGenericos().put("fecha", fechaOperacion );                                         // 17/09/2015
			transferenciaBase.getAtributosGenericos().put("tipoOperacion", DaoUtil.getString(map.get("TIPO_TRANSACCION")) ); // nombre tipoOperacion de cada tarjeta borrada
			transferenciaBase.setTransactionCode( tipo );                                                                    // "" evita nullpointer en get datos extra.
			transferenciaBase.setClientId( DaoUtil.getString(map.get("ENTITYID")) );                                         // contrato no se muestra porque esta en header
			
			transferenciaBase.getAtributosGenericos().put("nombreTipoOperacionSeleccionada", DaoUtil.getString(map.get("TIPO_TRANSACCION")) ); // valor del combo en comprobante
			transferenciaBase.getAtributosGenericos().put("nombreTransaccion", DaoUtil.getString(map.get("TIPO_TRANSACCION")) );               // de cada tarjeta
			transferenciaBase.getAtributosGenericos().put("nombreTipoOperacion", DaoUtil.getString(map.get("TIPO_TRANSACCION")) );
			transferenciaBase.getAtributosGenericos().put("numeroCuenta", DaoUtil.getString(map.get("CUENTA")) );
			transferenciaBase.getAtributosGenericos().put("nombreBanco", DaoUtil.getString(map.get("NOMBRE_BANCO")) );
			transferenciaBase.getAtributosGenericos().put("nombreDueno", DaoUtil.getString(map.get("PROPIETARIO_CTA")) );
			
			// mexicanada
			// hardcode
			if( LOCAL_TIPO_TRANSACCION_PAGO_SERVICIOS.equals( DaoUtil.getString(map.get("TIPO_TRANSACCION")) ) == true ){
				transferenciaBase.getAtributosGenericos().put("nombreTipoOperacionSeleccionada", LOCAL_NOMBRE_TRANSACCION_TC_Y_SEGURO_AFIRME ); // para comprobante jsp
				transferenciaBase.getAtributosGenericos().put("tipoOperacion", LOCAL_NOMBRE_TRANSACCION_TC_Y_SEGURO_AFIRME );                   // para historial_resultados.jsp en lugar de ${titulo}
				transferenciaBase.getAtributosGenericos().put("numeroTipoOperacion", LOCAL_VALOR_SE_COMBO_TC_Y_SEGURO_AFIRME );                 // para eliminar_cuenta_comprobante.jsp para validar que informacion mostrar.
				transferenciaBase.getAtributosGenericos().put("nombreTransaccion", DaoUtil.getString(map.get("DESCRIPCION_TRANSACCION")) );     // valor para etiqueta "Transaccion" TARJETA DE CREDITO AFIRME TERCEROS
				transferenciaBase.getAtributosGenericos().put("nombreTipoOperacion", DaoUtil.getString(map.get("TIPO_EMPRESA")) );              // valor para etiqueta "Tipo de Transaccion" PAGO DE TARJETA DE CREDITO
			}
			
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}
	
	/**
	 * Para obtener la lista de resultados de comprobantes de cambio de correo electronico. 
	 * 
	 * @param contrato
	 * @param tipo
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return List&lt;Comprobante&gt;
	 */
	public List<Comprobante> buscarComprobantesCambioDeCorreo(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		
		List<Comprobante> mapReferences = new ArrayList<Comprobante>();
		
		// fechaDesde va de 0 '2014-11-01 00:00:00.0' al 59 '2015-11-30 23:59:59.0'
		// ------------------------------------ fix obtener numeros de la fecha ------------------
		Timestamp fechaInicial = TimeUtils.getTimestamp( fechaDesde, "yyyyMMdd"); // fechaDesde="20150101"
		Timestamp fechaFinal = TimeUtils.getTimestamp(fechaHasta, "yyyyMMdd");    // fechaHasta="20151130"
		// ------------------------------------ // fix obtener numeros de la fecha ------------------
		String sql = "SELECT ENTITYID, USERID, MAILOLD, MAILNEW, FEXECUTE, FACTIVATE, STATUS, TIME " +
					 " FROM DC_NEWMAIL WHERE ENTITYID=? AND USERID=? AND FEXECUTE between ? AND ?";
		
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[]{contrato,contrato, fechaInicial, fechaFinal});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		for (Map<String, Object> map : listResult) {
			
			Comprobante transferenciaBase=new Comprobante();
			
			// fecha
			String horaOperacion = new SimpleDateFormat("HH:mm").format( map.get("FEXECUTE"));     // 2015-11-12 12:49:04.507
			String fechaOperacion = new SimpleDateFormat("dd/MM/yy").format( map.get("FEXECUTE")); // 2015-11-12 12:49:04.507
			String[] strFecha = fechaOperacion.split("/"); // 01/01/15
			
			// folio
			// solo para id de radio buton
			transferenciaBase.setReferenceNumber( fechaOperacion.replace("/", "") + horaOperacion.replace(":", "") ); 
			
			transferenciaBase.setProgrammingDay( strFecha[0] );      
			transferenciaBase.setProgrammingMonth( strFecha[1] );
			transferenciaBase.setProgrammingYear( strFecha[2] );
					
			// datos extra
			transferenciaBase.setAfirmeNetReference( fechaOperacion.replace("/", "") + horaOperacion.replace(":", "") );  // valor de radio button. Para que se pueda encontrar en la lista de resultados.
			
			transferenciaBase.getAtributosGenericos().put("fechaOperacion", fechaOperacion );
			transferenciaBase.getAtributosGenericos().put("horaOperacion", horaOperacion );
			
			String horaActivacion = new SimpleDateFormat("HH:mm").format( map.get("FACTIVATE"));     // 2015-11-12 12:49:04.507
			String fechaActivacion = new SimpleDateFormat("dd/MM/yy").format( map.get("FACTIVATE")); // 2015-11-12 12:49:04.507
			transferenciaBase.getAtributosGenericos().put("fechaActivacion", fechaActivacion );
			transferenciaBase.getAtributosGenericos().put("horaActivacion", horaActivacion );
			
			transferenciaBase.getAtributosGenericos().put("correoAnterior", DaoUtil.getString(map.get("MAILOLD")) );
			transferenciaBase.getAtributosGenericos().put("correoNuevo", DaoUtil.getString(map.get("MAILNEW")) );
			
			
			transferenciaBase.setTransactionCode( tipo );                                                            // "CPAS" evita nullpointer en get datos extra.
			transferenciaBase.setClientId( DaoUtil.getString(map.get("USERID")) );                                   // contrato no se muestra porque esta en header
			
			mapReferences.add(transferenciaBase);
		}
		
		return mapReferences;
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
	 * @see com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao#buscarComprobantesCambioDeContrasenia(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 *
	 */
	@Override
	public List<Comprobante> buscarComprobantesCambioDeContrasenia(String contrato, String tipo, String fechaDesde, String fechaHasta, String descripcion) {
		
		List<Comprobante> mapReferences = new ArrayList<Comprobante>();
		
		// ------------------------------------ fix obtener numeros de la fecha ------------------
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date fechaDesdeDate = null;
		Date fechaHastaDate = null;
		try {
			fechaDesdeDate = format.parse(fechaDesde); // fechaDesde="20150101"
			fechaHastaDate = format.parse(fechaHasta); // fechaHasta="20151130"
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		format.applyPattern("dd/MM/yy");
		String[] strFechaDesde = format.format( fechaDesdeDate ).split("/"); // 01/01/15
		String[] strFechaHasta = format.format( fechaHastaDate ).split("/"); // 30/11/15
		
		// dia= strFechaDesde[0] mes=strFechaDesde[1] anio=strFechaDesde[2]
		// columna DCIBS_REF tiene valores de la forma 151113130958
        String fechaIni = strFechaDesde[2] + strFechaDesde[1] + strFechaDesde[0] + "000000"; // fechaIni	"150601000000"
		String fechaFin = strFechaHasta[2] + strFechaHasta[1] + strFechaHasta[0] + "240000"; // fechaFin	"151130240000"
        // ------------------------------------ // fix obtener numeros de la fecha ------------------
		
		String sql = "SELECT ENTITYID, USERID, DESCRIP, PDD, PMM, PYY, VDD, VMM, VYY, PHH, PSS, VHH, VSS, DCIBS_REF, REFENUM, DEBACC" +
		   			 " FROM DC_CONFMSG " +
		   			 " WHERE ENTITYID = '" + contrato + "' " + " AND DESCRIP = '" + descripcion + "'" + 
		   			 " AND DCIBS_REF >= '" + fechaIni + 
		   			 "' AND DCIBS_REF <= '" + fechaFin + 
		   			 "' ORDER BY REFENUM DESC";
				
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		for (Map<String, Object> map : listResult) {
			
			Comprobante transferenciaBase=new Comprobante();
			// folio
			transferenciaBase.setReferenceNumber( DaoUtil.getString(map.get("DCIBS_REF")) ); 
			
			// fecha
			transferenciaBase.setProgrammingDay( DaoUtil.getString(map.get("VDD")) );      
			transferenciaBase.setProgrammingMonth( DaoUtil.getString(map.get("VMM")) );
			transferenciaBase.setProgrammingYear( DaoUtil.getString(map.get("VYY")) );
			
			// datos extra
			transferenciaBase.setAfirmeNetReference( DaoUtil.getString(map.get("DCIBS_REF")) );                      // valor de radio button
			transferenciaBase.getAtributosGenericos().put("hora", DaoUtil.getString(map.get("PHH")) + ":" + DaoUtil.getString(map.get("PSS")) );  // 13:09
			transferenciaBase.getAtributosGenericos().put("fecha", DaoUtil.getString(map.get("VDD")) + "/" + DaoUtil.getString(map.get("VMM"))+ "/" + DaoUtil.getString(map.get("VYY")) );  // 13/11/2015
			transferenciaBase.getAtributosGenericos().put("tipoOperacion", DaoUtil.getString(map.get("DESCRIP")) );  // CAMBIO CONTRASENIA
			transferenciaBase.setTransactionCode( tipo );                                                            // "CPAS" evita nullpointer en get datos extra.
			transferenciaBase.setClientId( DaoUtil.getString(map.get("USERID")) );                                   // contrato no se muestra porque esta en header
			
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
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
	 * @see com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao#buscarComprobantesCambioDeAlias(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 *
	 */
	public List<Comprobante> buscarComprobantesCambioDeAlias(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		List<Comprobante> mapReferences = new ArrayList<Comprobante>();
		
		String sql = "SELECT ENTITYID,USERID,OLDALIAS,NEWALIAS,CHANGEDATE,DCIBS_REF" +
				" FROM DC_CAMBIO_ALIAS WHERE ENTITYID=? AND USERID=?" +
				" AND CHANGEDATE between TIMESTAMP('" + fechaDesde + "') AND TIMESTAMP('" + fechaHasta + "') ORDER BY CHANGEDATE DESC";
				
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[]{ contrato, contrato } );
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		

		for (Map<String, Object> map : listResult) {
			
			Comprobante transferenciaBase=new Comprobante();
			// folio
			transferenciaBase.setReferenceNumber( DaoUtil.getString(map.get("DCIBS_REF")) ); 
			
			// fecha
			String hora = new SimpleDateFormat("HH:mm").format( map.get("CHANGEDATE")); // 2015-11-12 12:49:04.507
			String fecha = new SimpleDateFormat("dd/MM/yy").format( map.get("CHANGEDATE")); // 2015-11-12 12:49:04.507
			String[] strFecha = fecha.split("/"); // 01/01/15
			
			transferenciaBase.setProgrammingDay( strFecha[0] );      
			transferenciaBase.setProgrammingMonth( strFecha[1] );
			transferenciaBase.setProgrammingYear( strFecha[2] );
			
			// datos extra
			transferenciaBase.setAfirmeNetReference( DaoUtil.getString(map.get("DCIBS_REF")) ); // valor de radio button
			transferenciaBase.getAtributosGenericos().put("aliasNuevo", DaoUtil.getString(map.get("NEWALIAS")) );
			transferenciaBase.getAtributosGenericos().put("aliasAnterior", DaoUtil.getString(map.get("OLDALIAS")) );
			transferenciaBase.getAtributosGenericos().put("dia", fecha );                    // 12/11/2015
			transferenciaBase.getAtributosGenericos().put("hora", hora );                    // 12:49
			transferenciaBase.setTransactionCode( tipo );                                     // "ALIAS"   evita nullpointer en get datos extra.
			transferenciaBase.setClientId( DaoUtil.getString(map.get("USERID")) );           // contrato no se muestra porque esta en header
			
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}

	/**
	 * Para la lista de resultados de la busqueda de comprobantes de tipo 86= aviso de viaje.
	 * 
	 * @see com.afirme.afirmenet.dao.transferencia.ComprobanteTransferenciaDao#buscarComprobantesAvisoDeViaje(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Comprobante> buscarComprobantesAvisoDeViaje(String contrato, String tipo, String fechaDesde, String fechaHasta) {
			
		List<Comprobante> mapReferences = new ArrayList<Comprobante>();

		// ------------------------------------ fix obtener numeros de la fecha ------------------
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date fechaDesdeDate = null;
		Date fechaHastaDate = null;
		try {
			fechaDesdeDate = format.parse(fechaDesde); // fechaDesde="20150101"
			fechaHastaDate = format.parse(fechaHasta); // fechaHasta="20151130"
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		format.applyPattern("dd/MM/yy");
		String[] strFechaDesde = format.format( fechaDesdeDate ).split("/"); // 01/01/15
		String[] strFechaHasta = format.format( fechaHastaDate ).split("/"); // 30/11/15
		 
		int ddInicial = Integer.parseInt( strFechaDesde[0] ); // 01
        int mmInicial = Integer.parseInt( strFechaDesde[1] ); // 01
        int yyInicial = Integer.parseInt( strFechaDesde[2] ); // 15
        int ddFinal   = Integer.parseInt( strFechaHasta[0] );
        int mmFinal   = Integer.parseInt( strFechaHasta[1] );
        int yyFinal   = Integer.parseInt( strFechaHasta[2] );
        // ------------------------------------ // fix obtener numeros de la fecha ------------------
        
		String sql = "SELECT INOREF,INOVD1,INOVD2,INOVD3,INODSC,INOPMV,INOUSR FROM  "+ this.AS400_LIBRARY + "INOTR WHERE INOUSR = '" + contrato + "' AND INOPMV = '"+tipo+"' ";       
		sql = sql + "AND ( (INOVD3 BETWEEN "+ yyInicial +" AND "+ yyFinal +") AND (INOVD1 BETWEEN "+ mmInicial +" AND "+ mmFinal +") AND (INOVD2 BETWEEN " + ddInicial +" AND "+ ddFinal +") ) ";
		sql= sql + "ORDER BY INOREF DESC";
        
		List<Map<String, Object>> listResult;

		try {
			// listResult = as400Dao.getJdbcTemplate().queryForList(sql, new Object[] { });
			listResult = as400Dao.getJdbcTemplate().queryForList(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		

		for (Map<String, Object> map : listResult) {
			
			Comprobante transferenciaBase=new Comprobante();
			// folio
			transferenciaBase.setReferenceNumber( DaoUtil.getString(map.get("INOREF")) ); 
			
			// fecha
			transferenciaBase.setProgrammingDay( DaoUtil.getString(map.get("INOVD1")) );      
			transferenciaBase.setProgrammingMonth( DaoUtil.getString(map.get("INOVD2")) );
			transferenciaBase.setProgrammingYear( DaoUtil.getString(map.get("INOVD3")) );
			
			// datos extra
			transferenciaBase.setAfirmeNetReference( DaoUtil.getString(map.get("INOREF")) ); // valor de radio button
			transferenciaBase.setTransactionCode( DaoUtil.getString(map.get("INOPMV")) );    // 86
			transferenciaBase.setTransaccionDescrip( DaoUtil.getString(map.get("INODSC")) ); // tipo operacion lo obtiene del titulo en controller
			transferenciaBase.setClientId( DaoUtil.getString(map.get("INOUSR")) );           // contrato no se muestra porque esta en header
			
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}
	
	/**
	 * Se hace necesario para formar el objeto que se le va a aenviar al view del comprobante Aviso de viaje.
	 */
	@Override
	public AvisoViajeDTO completarDatosAvisoViaje( AvisoViajeDTO avisoViajeDTO){
		
		String sql = "SELECT AVITRJ,AVIPAI,AVICIU,AVIFIV,AVIFFV,AVIFEC,AVIHRA,AVINOM,AVITEL,AVICOM,AVICON FROM "+ this.AS400_LIBRARY + "INTAVIPF WHERE AVIREF = '"+ avisoViajeDTO.getNumeroDeReferencia400() +"'";
		
		List<Map<String, Object>> listResult;

		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		// List<TarjetaAvisoViajeDTO> listaTarjetasSeleccionadas = new ArrayList<TarjetaAvisoViajeDTO>();
		for (Map<String, Object> map : listResult) {
			
			TarjetaAvisoViajeDTO tarjetaAvisoViajeDTO = new TarjetaAvisoViajeDTO();
			
			tarjetaAvisoViajeDTO.setNumeroTarjeta( DaoUtil.getString(map.get("AVITRJ")) );
			// no viene en esta tabla                    tarjetaAvisoViajeDTO.setNombreTipoTarjeta( DaoUtil.getString(map.get("INOUSR")) ); debito credito
			tarjetaAvisoViajeDTO.setNombreTarjetaHabiente( DaoUtil.getString(map.get("AVINOM")) );
			
			// datos extra
			avisoViajeDTO.setNombrePaisDestino( DaoUtil.getString(map.get("AVIPAI")) );
			avisoViajeDTO.setNombreCiudadDestino( DaoUtil.getString(map.get("AVICIU")) );
			avisoViajeDTO.setComentarios( DaoUtil.getString(map.get("AVICOM")) );
			
			Calendar fecha = TimeUtils.getCalendar( DaoUtil.getString(map.get("AVIFIV")), "yyyyMMdd"); // fecha inicio viaje en db 20151111
			avisoViajeDTO.setFechaInicioViajeddMMYY( TimeUtils.getCalendarDDMMYY( fecha ) );
			
			fecha = TimeUtils.getCalendar( DaoUtil.getString(map.get("AVIFFV")), "yyyyMMdd"); // fecha fin viaje en db 20151130
			avisoViajeDTO.setFechaFinViajeddMMYY( TimeUtils.getCalendarDDMMYY( fecha ) );
			
			avisoViajeDTO.setTelefonoContacto( DaoUtil.getString(map.get("AVITEL")) );
			avisoViajeDTO.setContrato( DaoUtil.getString(map.get("AVICON")) );
			
			fecha = TimeUtils.getCalendar( DaoUtil.getString(map.get("AVIFEC")), "yyyyMMdd"); // Dia Solicitud de operación en db 20151110
			avisoViajeDTO.setFechaOperacionddMMYY( TimeUtils.getCalendarDDMMYY( fecha ) ); // Dia Solicitud de operación
			
			avisoViajeDTO.setHoraOperacionHHmm( Util.getHoraFormat( DaoUtil.getString(map.get("AVIHRA"))) ); // Hora Solicitud de operación de 134304 a 13:43
			
			avisoViajeDTO.getListaTarjetasSeleccionadas().add( tarjetaAvisoViajeDTO );
		}
		
		this.agregarNombreTipoTarjeta(avisoViajeDTO); // agrega el nombre de tipo de targeta
		
		return avisoViajeDTO;
	}
	
	/**
	 * Para agregar el nombre de tipo de tarjeta de credito.
	 * 
	 * Itera la lista de tarjetas registradas en el aviso de viaje y busca cada tarjeta en la lista de tarjetas del contrato.
	 * 
	 * @param avisoViajeDTO
	 * @return
	 */
	private AvisoViajeDTO agregarNombreTipoTarjeta( AvisoViajeDTO avisoViajeDTO ){
		
		List<TarjetaAvisoViajeDTO> listaTarjetasParaComprobante = new ArrayList<TarjetaAvisoViajeDTO>();
		List<TarjetaAvisoViajeDTO> listaTarjetas = new ArrayList<TarjetaAvisoViajeDTO>();
		try {
			listaTarjetas = avisoViajeService.obtenerListaTarjetas( avisoViajeDTO.getContrato() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		java.util.Iterator<TarjetaAvisoViajeDTO> listaTarjetasIterator = avisoViajeDTO.getListaTarjetasSeleccionadas().iterator();
		while (listaTarjetasIterator.hasNext()) {
			
			TarjetaAvisoViajeDTO tarjetaAvisoViajeDTO = new TarjetaAvisoViajeDTO();
			tarjetaAvisoViajeDTO = listaTarjetasIterator.next();
			
			for(int i=0; i< listaTarjetas.size(); i++){
				
				if( listaTarjetas.get(i).getNumeroTarjeta().equals( tarjetaAvisoViajeDTO.getNumeroTarjeta() ) == true){
					tarjetaAvisoViajeDTO.setNombreTipoTarjeta( listaTarjetas.get(i).getNombreTipoTarjeta() );
					break;
				}
			}
			
			listaTarjetasParaComprobante.add( tarjetaAvisoViajeDTO );
		}
		
		avisoViajeDTO.setListaTarjetasSeleccionadas( listaTarjetasParaComprobante );
		
		return avisoViajeDTO;
	}

	@Override
	public Servicio buscarComprobanteActivacionServicios(String contractId,
			TipoCliente cliente, int tipo) {
       List<Map<String, Object>> listResult;
		
		try {
			String query = "SELECT  * FROM DC_PERMISOS_SERVICIOS WHERE ENTITYID = ? AND USERTYPE = ? AND SERVICEID = ? ";
			String userType = "P";
			userType = cliente.equals(TipoCliente.EMPRESAS) ? "E" : "P";

			Object[] params = new Object[] { contractId, userType,
					tipo };
			
			listResult = db2Dao.getJdbcTemplate().queryForList(query,
					params);
			Servicio servicio = new Servicio();
			for (Map<String, Object> map : listResult) {
				servicio.setContractId(DaoUtil.getString(map.get("ENTITYID")));
				servicio.setUserId(DaoUtil.getString(map.get("USERID")));
				
				String strFecha = DaoUtil.getString(map.get("FECHACREACION"));
				servicio.setProgrammingDay(strFecha.substring(8, 9));      
				servicio.setProgrammingMonth(strFecha.substring(5, 7));
				servicio.setProgrammingYear(strFecha.substring(2, 4));
				servicio.setValidationDay(strFecha.substring(8, 9));      
				servicio.setValidationMonth(strFecha.substring(5, 7));
				servicio.setValidationYear(strFecha.substring(2, 4));
				servicio.setProgrammingHour(strFecha.substring(10, 11));
				servicio.setProgrammingMinute(strFecha.substring(12, 13));
				
				servicio.setDescription(DaoUtil.getString(map.get("DESCRIPCION")));
				
			}
			
			
			return servicio;

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		
	}

	@Override
	public List<TransferenciaBase> buscarComprobantesCheques(
			String contrato, String fD, String fH, String estatus, String tipo) {
		
		fD=fD.substring(2);
		fH=fH.substring(2);
		
		String sql = "SELECT DISTINCT ENTITYID, USERID, CUENTA, TITULARCTA, NUMCHKINI, NUMCHKFIN, DCIBS_REF, ESTATUS, MASIVO, MOTIVO" +
				" FROM DC_CHEQUES_LOG WHERE ENTITYID = '" + contrato + "' AND USERID = '" + contrato +
				"' AND ESTATUS = '" + estatus +
				"' AND DCIBS_REF >= '" + fD+"000000" + 
				"' AND DCIBS_REF <= '" + fH+"240000" + 
				"' ORDER BY DCIBS_REF";

		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<TransferenciaBase> mapReferences = new ArrayList<TransferenciaBase>();

		for (Map<String, Object> map : listResult) {

			TransferenciaBase transferenciaBase=new TransferenciaBase();
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("CUENTA")));
			transferenciaBase.setBeneficiaryName(DaoUtil.getString(map.get("TITULARCTA")));
			transferenciaBase.setAccountNumber(DaoUtil.getString(map.get("NUMCHKINI")));//NUMCHKINI
			transferenciaBase.setAccountOwner(DaoUtil.getString(map.get("NUMCHKFIN")));//NUMCHKFIN
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setState(DaoUtil.getString(map.get("ESTATUS")));//ESTATUS
			transferenciaBase.setAccion(DaoUtil.getString(map.get("MASIVO")));//MASIVO
			transferenciaBase.setDescription(DaoUtil.getString(map.get("MOTIVO")));//MOTIVO
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("DCIBS_REF")).substring(0, 2));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("DCIBS_REF")).substring(2, 4));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("DCIBS_REF")).substring(4, 6));
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(tipo));
			transferenciaBase.setTransactionCode(tipo);
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}
	
	@Override
	public RespuestaProteccionCheque buscarProteccionCheque(String referenceNumber, String contrato, String cuenta) {
	       List<Map<String, Object>> listResult;
	       RespuestaProteccionCheque respuestaProteccionCheque=null;
			try {
				String STM = "SELECT ENTITYID, USERID, CUENTA, TITULARCTA, NUMCHK, MOTIVO, MONTO, FECHA_OPERACION, MASIVO" +
						" FROM DC_CHEQUES_LOG WHERE ENTITYID = ? AND USERID = ? AND DCIBS_REF = ? AND CUENTA = ?";
				
				Object[] params = new Object[] { contrato, contrato,referenceNumber,cuenta };
				
				listResult = db2Dao.getJdbcTemplate().queryForList(STM,	params);
				for (Map<String, Object> map : listResult) {
					
					 respuestaProteccionCheque =
							new RespuestaProteccionCheque(DaoUtil.getDate(map.get("FECHA_OPERACION")), DaoUtil.getString(map.get("NUMCHK")), DaoUtil.getString(map.get("MONTO")),
									DaoUtil.getString(map.get("MOTIVO")), "", "");
					
				}
				
				
				return respuestaProteccionCheque;

			} catch (EmptyResultDataAccessException e) {
				return null;
			}
			
	}

	@Override
	public BusquedaChequeResultado buscarCancelaCheque(String referenceNumber,
			String contrato, String cuenta) {
	       List<Map<String, Object>> listResult;
		  final List<Cheque> cheques = new ArrayList<Cheque>();
		  String nickName="";
		   BusquedaChequeResultado busquedaChequeResultado=null;
			try {
				String STM = "SELECT ENTITYID, USERID, CUENTA, TITULARCTA, NUMCHK, MOTIVO, MONTO, FECHA_OPERACION, MASIVO" +
						" FROM DC_CHEQUES_LOG WHERE ENTITYID = ? AND USERID = ? AND DCIBS_REF = ? AND CUENTA = ?";
				
				Object[] params = new Object[] { contrato, contrato,referenceNumber,cuenta };
				
				listResult = db2Dao.getJdbcTemplate().queryForList(STM,	params);
				for (Map<String, Object> map : listResult) {
					
					cheques.add(new Cheque(DaoUtil.getDate(map.get("FECHA_OPERACION")), DaoUtil.getString(map.get("NUMCHK")), DaoUtil.getString(map.get("MOTIVO"))));
					nickName=DaoUtil.getString(map.get("TITULARCTA"));
				}
				
				busquedaChequeResultado =
						new BusquedaChequeResultado(nickName, cuenta, "", "", cheques);
				
				
				return busquedaChequeResultado;

			} catch (EmptyResultDataAccessException e) {
				return null;
			}
	}

	@Override
	public List<Servicio> buscarComprobantesGenericosServicio(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio) {
		List<Map<String, Object>> listResult= this.ListResultComprobantesGenericos(contrato, tipo, fechaDesde, fechaHasta, numeroServicio);


		List<Servicio> mapReferences = new ArrayList<Servicio>();

		for (Map<String, Object> map : listResult) {
			/*
			 , , 
			*/
			Servicio transferenciaBase=new Servicio();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TYPTRF"))));
			transferenciaBase.setTransactionCode(DaoUtil.getString(map.get("TYPTRF")));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("NARRA")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("DEBACC")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CREACC")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("DESCRIP")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			
			
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			transferenciaBase.setServiceNumber(DaoUtil.getString(map.get("SERVNUM")));
			transferenciaBase.setIva(DaoUtil.getBigDecimal(map.get("IVA")));
			transferenciaBase.setPlazaReceiving(DaoUtil.getString(map.get("PLZRECPTOR")));
			transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("FEES")));// FEES COMISION
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setBankReceiving(DaoUtil.getString(map.get("BNKRECPTOR")));
			transferenciaBase.setRfc(DaoUtil.getString(map.get("RFC")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimalEspecial(map.get("AMOUNT"))); 
			transferenciaBase.setCurrency(DaoUtil.getString(map.get("CCY")));
			transferenciaBase.setBeneficiaryName(DaoUtil.getString(map.get("BENENAME")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("REFENUM"))); 
			transferenciaBase.setTaxCommision(DaoUtil.getBigDecimal(map.get("FEE_IVA")));// FEE_IVA
			
			transferenciaBase.setUserReference(DaoUtil.getString(map.get("REFUSR")));
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}
	

	@Override
	public List<DepositoReferenciado> buscarComprobantesGenericosImpuestoSAT(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio) {
		
		List<Map<String, Object>> listResult= this.ListResultComprobantesGenericos(contrato, tipo, fechaDesde, fechaHasta, numeroServicio);

		List<DepositoReferenciado> mapReferences = new ArrayList<DepositoReferenciado>();

		for (Map<String, Object> map : listResult) {
			/*
			 , , 
			*/
			DepositoReferenciado transferenciaBase=new DepositoReferenciado();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TYPTRF"))));
			transferenciaBase.setTransactionCode(DaoUtil.getString(map.get("TYPTRF")));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("NARRA")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("DEBACC")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CREACC")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("DESCRIP")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			
			
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			transferenciaBase.setServiceNumber(DaoUtil.getString(map.get("SERVNUM")));
			transferenciaBase.setIva(DaoUtil.getBigDecimal(map.get("IVA")));
			transferenciaBase.setPlazaReceiving(DaoUtil.getString(map.get("PLZRECPTOR")));
			transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("FEES")));// FEES COMISION
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setBankReceiving(DaoUtil.getString(map.get("BNKRECPTOR")));
			transferenciaBase.setRfc(DaoUtil.getString(map.get("RFC")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimalEspecial(map.get("AMOUNT"))); 
			transferenciaBase.setCurrency(DaoUtil.getString(map.get("CCY")));
			transferenciaBase.setBeneficiaryName(DaoUtil.getString(map.get("BENENAME")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("REFENUM"))); 
			transferenciaBase.setTaxCommision(DaoUtil.getBigDecimal(map.get("FEE_IVA")));// FEE_IVA
			
			transferenciaBase.setUserReference(DaoUtil.getString(map.get("REFUSR")));
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}
	@Override
	public List<SeguroCardif> buscarComprobantesGenericosCardif(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio) {
		
		List<Map<String, Object>> listResult= this.ListResultComprobantesGenericos(contrato, tipo, fechaDesde, fechaHasta, numeroServicio);

		List<SeguroCardif> mapReferences = new ArrayList<SeguroCardif>();

		for (Map<String, Object> map : listResult) {
			/*
			 , , 
			*/
			SeguroCardif transferenciaBase=new SeguroCardif();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TYPTRF"))));
			transferenciaBase.setTransactionCode(DaoUtil.getString(map.get("TYPTRF")));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("NARRA")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("DEBACC")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CREACC")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("DESCRIP")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			
			
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			transferenciaBase.setServiceNumber(DaoUtil.getString(map.get("SERVNUM")));
			transferenciaBase.setIva(DaoUtil.getBigDecimal(map.get("IVA")));
			transferenciaBase.setPlazaReceiving(DaoUtil.getString(map.get("PLZRECPTOR")));
			transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("FEES")));// FEES COMISION
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setBankReceiving(DaoUtil.getString(map.get("BNKRECPTOR")));
			transferenciaBase.setRfc(DaoUtil.getString(map.get("RFC")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimalEspecial(map.get("AMOUNT"))); 
			transferenciaBase.setCurrency(DaoUtil.getString(map.get("CCY")));
			transferenciaBase.setBeneficiaryName(DaoUtil.getString(map.get("BENENAME")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("REFENUM"))); 
			transferenciaBase.setTaxCommision(DaoUtil.getBigDecimal(map.get("FEE_IVA")));// FEE_IVA
			
			transferenciaBase.setUserReference(DaoUtil.getString(map.get("REFUSR")));
			
			if(transferenciaBase.getDebitAccount()==null || transferenciaBase.getDebitAccount().trim().length()==0){
				transferenciaBase.setDebitAccount(transferenciaBase.getCreditAccount());
			}
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}
	private List<Map<String, Object>> ListResultComprobantesGenericos(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio) {
		String sql = "select ENTITYID, USERID, DEBACC, CREACC, AMOUNT, CCY, BNKRECPTOR, PLZRECPTOR, BENENAME, BENEADD, "
				+ " DESCRIP, RFC, NARRA, PDD, PMM, PYY, VDD, VMM, VYY, PHH, "
				+ " PSS, VHH, VSS, TYPTRF, REFENUM, "
				+ " SERVNUM, FEES, IVA, DCIBS_REF, PSECONDS, REFUSR  from VW_DC_CONFMSG "
				+ " where ENTITYID = '" + contrato + "' ";
		if (tipo.equals("36"))
			sql += " and TYPTRF IN ( '36','4')"; // PODER VER LOS RECIBOS
													// ANTERIORES
		else
			sql += " and TYPTRF = '" + tipo + "'";
		if(numeroServicio!=null)
			sql += " and servNum='"+numeroServicio+"'";
		else if(tipo.equals("28"))
			sql += " and servNum not in ('20', '10') "; 
		sql += " AND FECHACAPTURA>='" + fechaDesde + "0001' AND FECHACAPTURA<='"
				+ fechaHasta + "2359' order by REFENUM desc";
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return listResult;
	}

	@Override
	public void obtenerInformacionDepositoReferenciado(DepositoReferenciado pago) {
		//Si existe de una vez le ponemos los datos al comprobante
		String sql = "select INODSC, INORFN, INORFC from "+ AS400_LIBRARY + "inotrl0 where inoref = ? and inousr = ?";
		List<Map<String, Object>> listResult= new ArrayList<Map<String,Object>>();
		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { pago.getReferenceNumber(), pago.getContractId() });
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error al consultar el tipo de servicio.");
		}
		for (Map<String, Object> map : listResult) {

			pago.setLineaCaptura(DaoUtil.getString(map.get("INODSC")));
			pago.setLineaPago(DaoUtil.getString(map.get("INORFC")));
		}
		return;
		
	}

	
	
	@Override
	public void insertaComprobanteMisCreditos(Comprobante transferenciaBase) {
		String sql = "INSERT INTO DC_CONFMSG (TYPTRF, NARRA, DEBACC, CREACC, DESCRIP, ENTITYID,  USERID, PMM, PSS, "
				+ "PYY, VDD, BENEADD, VMM, VHH, SERVNUM, IVA, PLZRECPTOR, FEES,"
				+ "VSS, VYY, BNKRECPTOR, RFC, DCIBS_REF, AMOUNT, CCY, BENENAME, REFENUM,"
				+ "PDD, PHH, FEE_IVA, PSECONDS, REFUSR) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		String rEFUSR=transferenciaBase.getUserReference()== null || transferenciaBase.getUserReference().equals("") ?  "0" : transferenciaBase.getUserReference();
		if(rEFUSR.length()>11)
			rEFUSR=rEFUSR.substring(0, 10);
		Object[] params = new Object[] {
				transferenciaBase.getTipoTransferencia().getValor(),//TYPTRF
				(transferenciaBase.getNarrative()!=null && transferenciaBase.getNarrative().trim().length()>0)? transferenciaBase.getNarrative(): transferenciaBase.getDescription(),//NARRA
				transferenciaBase.getOrigen() != null ? transferenciaBase.getOrigen().getNumber() : transferenciaBase.getDebitAccount(),//DEBACC
				transferenciaBase.getDestino() !=null ? transferenciaBase.getDestino().getNumber() : transferenciaBase.getCreditAccount(),//CREACC
				transferenciaBase.getDescription(),//DESCRIP
				transferenciaBase.getContractId(),//ENTITYID
				transferenciaBase.getUserId(),//USERID
				Util.getMM(),//PMM
				Util.getSS(),//PSS
				Util.getYY(),//PYY
				transferenciaBase.getValidationDay(),//VDD
				transferenciaBase.getBeneficiaryName(),//BENEADD
				transferenciaBase.getValidationMonth(),//VMM
				transferenciaBase.getValidationHour(),//VHH
				transferenciaBase.getServiceNumber(),//SERVNUM
				transferenciaBase.getIva(),//IVA
				transferenciaBase.getPlazaReceiving(),//PLZRECPTOR
				transferenciaBase.getCommision(),// FEES COMISION
				transferenciaBase.getValidationMinute(),//VSS
				transferenciaBase.getValidationYear(),//VYY
				transferenciaBase.getBankReceiving(),//BNKRECPTOR
				transferenciaBase.getRfc(),//RFC
				transferenciaBase.getAfirmeNetReference(),//DCIBS_REF
				transferenciaBase.getAmount() ,// AMOUNT
				transferenciaBase.getCurrency(),//CCY
				transferenciaBase.getBeneficiaryName(),//BENENAME
				transferenciaBase.getReferenceNumber(), //REFENUM
				Util.getDD(),//PDD
				Util.getHH(), //PHH 
				transferenciaBase.getTaxCommision(),// FEE_IVA//
				Util.getSeconds(), // PSECONDS
				rEFUSR };//REFUSR
		
		db2Dao.getJdbcTemplate().update(sql, params);
		
	}
	@Override
	public List<Credito> buscarComprobantesGenericosCredito(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio) {
		
		List<Map<String, Object>> listResult= this.ListResultComprobantesGenericos(contrato, tipo, fechaDesde, fechaHasta, numeroServicio);

		List<Credito> mapReferences = new ArrayList<Credito>();

		for (Map<String, Object> map : listResult) {
			/*
			 , , 
			*/
			Credito transferenciaBase=new Credito();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(DaoUtil.getString(map.get("TYPTRF"))));
			transferenciaBase.setTransactionCode(DaoUtil.getString(map.get("TYPTRF")));
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("NARRA")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("DEBACC")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CREACC")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("DESCRIP")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setProgrammingMinute(DaoUtil.getString(map.get("PSS")));
			transferenciaBase.setProgrammingHour(DaoUtil.getString(map.get("PHH")));
			transferenciaBase.setProgrammingYear(DaoUtil.getString(map.get("PYY")));
			transferenciaBase.setProgrammingMonth(DaoUtil.getString(map.get("PMM")));
			transferenciaBase.setProgrammingDay(DaoUtil.getString(map.get("PDD")));
			
			
			transferenciaBase.setValidationMonth(DaoUtil.getString(map.get("VMM")));
			transferenciaBase.setValidationHour(DaoUtil.getString(map.get("VHH")));
			transferenciaBase.setValidationDay(DaoUtil.getString(map.get("VDD")));
			transferenciaBase.setServiceNumber(DaoUtil.getString(map.get("SERVNUM")));
			transferenciaBase.setIva(DaoUtil.getBigDecimal(map.get("IVA")));
			transferenciaBase.setPlazaReceiving(DaoUtil.getString(map.get("PLZRECPTOR")));
			transferenciaBase.setCommision(DaoUtil.getBigDecimal(map.get("FEES")));// FEES COMISION
			transferenciaBase.setValidationMinute(DaoUtil.getString(map.get("VSS")));
			transferenciaBase.setValidationYear(DaoUtil.getString(map.get("VYY")));
			transferenciaBase.setBankReceiving(DaoUtil.getString(map.get("BNKRECPTOR")));
			transferenciaBase.setRfc(DaoUtil.getString(map.get("RFC")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setAmount(DaoUtil.getBigDecimalEspecial(map.get("AMOUNT"))); 
			transferenciaBase.setCurrency(DaoUtil.getString(map.get("CCY")));
			transferenciaBase.setBeneficiaryName(DaoUtil.getString(map.get("BENENAME")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("REFENUM"))); 
			transferenciaBase.setTaxCommision(DaoUtil.getBigDecimal(map.get("FEE_IVA")));// FEE_IVA
			
			transferenciaBase.setUserReference(DaoUtil.getString(map.get("REFUSR")));
			
			if(transferenciaBase.getDebitAccount()==null || transferenciaBase.getDebitAccount().trim().length()==0){
				transferenciaBase.setDebitAccount(transferenciaBase.getCreditAccount());
			}
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}

	@Override
	public void insertaComprobanteInversionPerfecta(Comprobante transferenciaBase) {
		String sql = "INSERT INTO DC_INVPERFECT (folio,contrato,usuario," +
				"operacion,ctaCargo, nombre," +
				"cantidad,plazo,interes," +
				"mRegalo,ctaAbono,nombreA," +
				"fecha,fechVenc,moneda," +
				"tasa,tasaInt,status," +
				"concepto,folio_aud,emp_pers," +
				"idProd) VALUES "
				+ "(?, ?, ?, " +
				"?, ?, ?, " +
				"?, ?, ?, " +
				"?, ?, ?, " +
				"?, ?, ?, " +
				"?, ?, ?, " +
				"?, ?, ?, " +
				"?)";
		String tipo = "Inversión Perfecta";
		String status = "1";
		String regalo = "";
		String emp_per = "P"; // empresas
		Object[] params = new Object[] {
				transferenciaBase.getAfirmeNetReference(),//folio
				transferenciaBase.getContractId(),//contrato
				transferenciaBase.getUserId(),//usuario
				tipo,//operacion
				transferenciaBase.getDebitAccount(),//ctaCargo
				transferenciaBase.getOrigen().getNickname(),//nombre
				transferenciaBase.getAmount(),//cantidad
				transferenciaBase.getPlazaReceiving(),//plazo
				transferenciaBase.getIva(),//interes
				regalo,//mRegalo
				transferenciaBase.getDebitAccount(),//ctaAbono
				transferenciaBase.getOrigen().getNickname(),//nombreA
				TimeUtils.getTimestamp(Calendar.getInstance()),//fecha
				transferenciaBase.getOperationDate(),//fechVenc
				transferenciaBase.getCurrency(),//moneda
				transferenciaBase.getFlag(),//tasa
				transferenciaBase.getCommision(),//tasaInt
				status,//status
				transferenciaBase.getDescription(),// concepto
				transferenciaBase.getTrackingCode(),//folio_aud
				emp_per,//emp_pers
				transferenciaBase.getBankReceiving()//idProd
				 };
		
		db2Dao.getJdbcTemplate().update(sql, params);
		
	}

	@Override
	public List<Inversion> buscarComprobantesInversionPerfecta(String contrato, String tipo, String fechaDesde, String fechaHasta) {
		
		
		String sql = "select *  from DC_INVPERFECT "
				+ " where CONTRATO = '" + contrato + "' ";
		sql += " AND FECHA>='" + fechaDesde + "' AND FECHA<='"
				+ fechaHasta + "' order by FOLIO desc";
		List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		List<Inversion> mapReferences = new ArrayList<Inversion>();

		for (Map<String, Object> map : listResult) {
			/*
			 , , 
			*/
			Inversion transferenciaBase=new Inversion();
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(tipo));
			transferenciaBase.setTransactionCode(tipo);
			transferenciaBase.setNarrative(DaoUtil.getString(map.get("CONCEPTO")));
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("CTACARGO")));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CTAABONO")));
			transferenciaBase.setDescription(DaoUtil.getString(map.get("CONCEPTO")));
			transferenciaBase.setContractId(DaoUtil.getString(map.get("CONTRATO")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USUARIO")));
			String strFecha = DaoUtil.getString(map.get("FECHA"));
			transferenciaBase.setProgrammingDay(strFecha.substring(8, 10));      
			transferenciaBase.setProgrammingMonth(strFecha.substring(5, 7));
			transferenciaBase.setProgrammingYear(strFecha.substring(0, 4));
			transferenciaBase.setValidationDay(strFecha.substring(8, 10));      
			transferenciaBase.setValidationMonth(strFecha.substring(5, 7));
			transferenciaBase.setValidationYear(strFecha.substring(0, 4));
			transferenciaBase.setProgrammingHour(strFecha.substring(11, 13));
			transferenciaBase.setProgrammingMinute(strFecha.substring(14, 16));
			transferenciaBase.setValidationHour(strFecha.substring(11, 13));
			transferenciaBase.setValidationMinute(strFecha.substring(14, 16));
			transferenciaBase.setNumeroInversion(new BigDecimal(DaoUtil.getString(map.get("FOLIO"))));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("FOLIO")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("FOLIO")));
			Cuenta origen=new Cuenta();
			origen.setNickname(DaoUtil.getString(map.get("NOMBRE")));
			origen.setNumber(transferenciaBase.getDebitAccount());
			transferenciaBase.setOrigen(origen);
			transferenciaBase.setAmount(DaoUtil.getBigDecimal(map.get("CANTIDAD"))); 
			transferenciaBase.setPlazo(Integer.parseInt(DaoUtil.getString(map.get("PLAZO"))));
			transferenciaBase.setIntereses(DaoUtil.getBigDecimal(map.get("INTERES")));
			transferenciaBase.setMesesRegalo(DaoUtil.getString(map.get("MREGALO")));
			transferenciaBase.setCuentaPasiva(transferenciaBase.getDebitAccount());
			transferenciaBase.setFechaVencimiento(new BigDecimal(TimeUtils.traduceFecha(DaoUtil.getString(map.get("FECHVENC")), TimeUtils.SLASH_DATE_FORMAT, TimeUtils.DB2_DATE_FORMAT)));
			transferenciaBase.setCurrency(DaoUtil.getString(map.get("MONEDA")));
			transferenciaBase.setTasa(DaoUtil.getString(map.get("TASA")));
			transferenciaBase.setTasaInteres(DaoUtil.getBigDecimal(map.get("TASAINT")));
			transferenciaBase.setFolioAuditoria(new BigDecimal(DaoUtil.getString(map.get("FOLIO_AUD"))));
			if(transferenciaBase.getDebitAccount()==null || transferenciaBase.getDebitAccount().trim().length()==0){
				transferenciaBase.setDebitAccount(transferenciaBase.getCreditAccount());
			}
			mapReferences.add(transferenciaBase);
		}

		return mapReferences;
	}

	@Override
	public void almacenarRecargaDB2(Comprobante comprobante) {
		
		String stm = "INSERT INTO DC_RECARGAS  " 
				   + "(ENTITYID, DEBACC , CELL_NUMBER, DESCRIP, AMOUNT, CCY, PDD, PMM, PYY, VDD, VMM, VYY, VHH, VSS," 
				   + " OUSER, VUSER, AUSER, ODATE, VDATE, ADATE, STS, NUMAPR, FLG, DCIBS_REF, FOLIO, REFENUM, ID_TELEFONICA) " 
				   + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] params = new Object[] {
				comprobante.getContractId(),//ENTITYID
				comprobante.getOrigen() != null ? comprobante.getOrigen().getNumber() : comprobante.getDebitAccount(),//DEBACC
				comprobante.getDestino() !=null ? comprobante.getDestino().getNumber() : comprobante.getCreditAccount(),//CELL_NUMBER
				comprobante.getDescription(),//DESCRIP
				comprobante.getAmount(),//AMOUNT
				comprobante.getCurrency(),//CCY
				comprobante.getValidationDay(),//PMM
				comprobante.getValidationMonth(),//PSS
				comprobante.getValidationYear(),//PYY
				comprobante.getValidationDay(),//VDD
				comprobante.getValidationMonth(),//VMM
				comprobante.getValidationYear(),//VYY
				comprobante.getValidationHour(),//VHH
				comprobante.getValidationMinute(),//VSS
				comprobante.getContractId(),//OUSER
				comprobante.getContractId(),//VUSER
				comprobante.getContractId(),// AUSER
				comprobante.getValidationDay()+"/"+comprobante.getValidationMonth()+"/"+comprobante.getValidationYear(),//ODATE
				comprobante.getValidationDay()+"/"+comprobante.getValidationMonth()+"/"+comprobante.getValidationYear(),//VDATE
				comprobante.getValidationDay()+"/"+comprobante.getValidationMonth()+"/"+comprobante.getValidationYear(),//ADATE
				"D",//STS
				0,//NUMAPR
				"" ,// FLG
				comprobante.getAfirmeNetReference(),//DCIBS_REF
				comprobante.getFlag(),//FOLIO
				comprobante.getReferenceNumber(), //REFENUM
				comprobante.getState() };//REFUSR
		
		db2Dao.getJdbcTemplate().update(stm, params);
		
	}

	@Override
	public void insertaConfirmacionRecarga(Comprobante transferenciaBase) {
		String sql = "INSERT INTO DC_CONFMSG (TYPTRF, NARRA, DEBACC, CREACC, DESCRIP, ENTITYID,  USERID, PMM, PSS, "
				+ "PYY, VDD, BENEADD, VMM, VHH, SERVNUM, IVA, PLZRECPTOR, FEES,"
				+ "VSS, VYY, BNKRECPTOR, RFC, DCIBS_REF, AMOUNT, CCY, BENENAME, REFENUM,"
				+ "PDD, PHH, FEE_IVA, PSECONDS, REFUSR) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		String rEFUSR=transferenciaBase.getUserReference()== null || transferenciaBase.getUserReference().equals("") ?  "0" : transferenciaBase.getUserReference();
		if(rEFUSR.length()>11)
			rEFUSR=rEFUSR.substring(0, 10);
		Object[] params = new Object[] {
				transferenciaBase.getTipoTransferencia().getValor(),//TYPTRF
				(transferenciaBase.getNarrative()!=null && transferenciaBase.getNarrative().trim().length()>0)? transferenciaBase.getNarrative(): transferenciaBase.getDescription(),//NARRA
				transferenciaBase.getOrigen() != null ? transferenciaBase.getOrigen().getNumber() : transferenciaBase.getDebitAccount(),//DEBACC
				transferenciaBase.getDestino() !=null ? transferenciaBase.getDestino().getNumber() : transferenciaBase.getCreditAccount(),//CREACC
				transferenciaBase.getDescription(),//DESCRIP
				transferenciaBase.getContractId(),//ENTITYID
				transferenciaBase.getUserId(),//USERID
				Util.getMM(),//PMM
				Util.getSS(),//PSS
				Util.getYY(),//PYY
				transferenciaBase.getValidationDay(),//VDD
				transferenciaBase.getBeneficiaryName(),//BENEADD
				transferenciaBase.getValidationMonth(),//VMM
				transferenciaBase.getValidationHour(),//VHH
				transferenciaBase.getState(),//SERVNUM
				transferenciaBase.getIva(),//IVA
				transferenciaBase.getPlazaReceiving(),//PLZRECPTOR
				transferenciaBase.getCommision(),// FEES COMISION
				transferenciaBase.getValidationMinute(),//VSS
				transferenciaBase.getValidationYear(),//VYY
				transferenciaBase.getBankReceiving(),//BNKRECPTOR
				transferenciaBase.getRfc(),//RFC
				transferenciaBase.getAfirmeNetReference(),//DCIBS_REF
				transferenciaBase.getAmount()+" "+transferenciaBase.getCurrency() ,// AMOUNT
				transferenciaBase.getCurrency(),//CCY
				transferenciaBase.getBeneficiaryName(),//BENENAME
				transferenciaBase.getReferenceNumber(), //REFENUM
				Util.getDD(),//PDD
				Util.getHH(), //PHH 
				transferenciaBase.getTaxCommision(),// FEE_IVA//
				Util.getSeconds(), // PSECONDS
				rEFUSR };//REFUSR
		
		db2Dao.getJdbcTemplate().update(sql, params);
		
	}

	@Override
	public String obtenerSPEICEP(String contrato, int fechaInicial, int fechaFinal, int tipoMovimiento, String claveRastreo) {
		
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		String cadenaEncriptada = "";								
		String query="Call "+ AS400_LIBRARY + "CONS_ORDENES_SPEI(?, ?, ?, ?)";
		List<Map<String, Object>> listResult;
		Object[] params = new Object[] {
			contrato,
			fechaInicial,
			fechaFinal,
			tipoMovimiento
		};
		try {
			listResult = as400Dao.getJdbcTemplate().queryForList(query, params);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}						
									        
		for (Map<String, Object> rs : listResult) {
			
			HashMap<String, String> map = new HashMap<String, String>();	
			try{
				if(!claveRastreo.equals( rs.get("MOVCVRA").toString().trim() ))	continue;
				
	        	map.put("contrato", contrato);  	       
	        	map.put("folio", rs.get("MOVFOLAF").toString().trim());
	        	map.put("descripcion", rs.get("MOVCONC").toString().trim()); 
	        	map.put("fecha", rs.get("MOVFOPER").toString().trim());         	
	        	map.put("claveRastreo", rs.get("MOVCVRA").toString().trim()); 	        	
		        map.put("claveSpeiEmisor", rs.get("MOVBAOR").toString().trim());		        	
		        map.put("claveSpeiReceptor", rs.get("MOVBABE").toString().trim()); 
		        map.put("nombreBancoEmisor", rs.get("MOVNOMO").toString().trim()); 
		        map.put("nombreBancoReceptor", rs.get("MOVNOMB").toString().trim());   //MOVCTAO por regulacion bancaria no se muestra la cuenta cargo de spei entrantes
		        map.put("cuentaAbono", rs.get("MOVCTAB").toString().trim()); 
	        	map.put("cuentaBeneficiario", rs.get("MOVCTAB").toString().trim()); 
	        	map.put("monto", rs.get("MOVIMPO").toString().trim()); 
	        	map.put("sentidoOperacion", rs.get("MOVSENT").toString().trim()); //1 Enviado, 2 Recibido
        	
        		map.put("cadenaEncriptada", AES128.encryptAES_128( map.get("fecha") +"|"+
												                   "T" +"|"+
												                   map.get("claveRastreo") +"|"+
												                   map.get("claveSpeiEmisor") +"|"+
												                   map.get("claveSpeiReceptor") +"|"+
												                   map.get("cuentaBeneficiario") +"|"+
												                   map.get("monto")  
										                         ));        	
        	}catch(Exception e){
        		map.put("cadenaEncriptada", "");
        		LOG.error( e.getMessage() );
        	}
        	cadenaEncriptada = map.get("cadenaEncriptada");        				
			lista.add(map);
			
			if(!cadenaEncriptada.isEmpty()) break;
		}		
		return cadenaEncriptada;
	}

	@Override
	public List<Portabilidad> buscarComprobantesPortabilidadNomina(
			String contrato, String tipo, String fechaDesde, String fechaHasta) {
		
		String sql = "select *  from DC_Portabilidad where entityid = '"
				+ contrato + "' ";
		sql += " AND fecha >= '" + fechaDesde + "' AND fecha <= '" + fechaHasta
				+ "' AND typtrf='" + tipo;
		sql += "' order by fecha desc, hora desc";
		
        List<Map<String, Object>> listResult;

		try {
			listResult = db2Dao.getJdbcTemplate().queryForList(sql,
					new Object[] { });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		List<Portabilidad> mapReferences = new ArrayList<Portabilidad>();

		for (Map<String, Object> map : listResult) {
			
			Portabilidad transferenciaBase = new Portabilidad();
			transferenciaBase.setContractId(DaoUtil.getString(map.get("ENTITYID")));
			transferenciaBase.setUserId(DaoUtil.getString(map.get("USERID")));
			transferenciaBase.setAfirmeNetReference(DaoUtil.getString(map.get("DCIBS_REF")));
			transferenciaBase.setReferenceNumber(DaoUtil.getString(map.get("REFENUM")));
			transferenciaBase.setTipoTransferencia(TipoTransferencia.findByValue(tipo));
			String fecha = DaoUtil.getString(map.get("FECHA"));
			transferenciaBase.setProgrammingYear(fecha.substring(0, 4));
			transferenciaBase.setProgrammingMonth(fecha.substring(4, 6));
			transferenciaBase.setProgrammingDay(fecha.substring(6, 8));
			String hora = DaoUtil.getString(map.get("HORA"));
			transferenciaBase.setProgrammingHour(hora.substring(0, 2));
			transferenciaBase.setProgrammingMinute(hora.substring(2, 4));
			transferenciaBase.setTipoOperacion(DaoUtil.getString(map.get("TIPO_OPERACION")));
			transferenciaBase.setDescripcion(DaoUtil.getString(map.get("TIPO_OPDESC")));
			transferenciaBase.setAccion(DaoUtil.getString(map.get("ESTATUS")));
			
			transferenciaBase.setDebitAccount(DaoUtil.getString(map.get("CUENTAO")));
			transferenciaBase.setBancoOrdenante(new BancoPortabilidad(
					DaoUtil.getString(map.get("BANCOO")),
					DaoUtil.getString(map.get("BANCOO")),
					DaoUtil.getString(map.get("BANCOO_COMP")),
					DaoUtil.getString(map.get("BANCOO_COMP"))));
			transferenciaBase.setCreditAccount(DaoUtil.getString(map.get("CUENTAR")));
			transferenciaBase.setBancoReceptor(new BancoPortabilidad(
					DaoUtil.getString(map.get("BANCOR")),
					DaoUtil.getString(map.get("BANCOR")),
					DaoUtil.getString(map.get("BANCOR_COMP")),
					DaoUtil.getString(map.get("BANCOR_COMP"))));
			
			
			fecha = DaoUtil.getString(map.get("FECHA_NAC"));
			transferenciaBase.setFechaNac(fecha!=null&&fecha.length()>8?Util.getFechaFormat(fecha):fecha);
			transferenciaBase.setRfcPatron(DaoUtil.getString(map.get("RFC_PATRON")));			
			
			transferenciaBase.getAtributosGenericos().put("tipoOperacion", transferenciaBase.getDescripcion());
			
			mapReferences.add(transferenciaBase);
			
		}

		return mapReferences;
	}

	@Override
	public void insertaConfirmacionDepositoReferenciado(Comprobante transferenciaBase) {
		String sql ="INSERT INTO DC_TAXREF (ENTITYID, DEBACC, AMOUNT, VDD, VMM, VYY, " +
				"VHH, VSS, LINE, FOLIO, OUSER, VUSER, AUSER, ODATE, VDATE, ADATE, " + 
				"STS, NUMAPR, DCIBS_REF, LINEA_PAGO) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + 
				       "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		
		String rEFUSR=transferenciaBase.getUserReference()== null || transferenciaBase.getUserReference().equals("") ?  "0" : transferenciaBase.getUserReference();
		if(rEFUSR.length()>11)
			rEFUSR=rEFUSR.substring(0, 10);
		Object[] params = new Object[] {
				transferenciaBase.getContractId(),
				transferenciaBase.getOrigen() != null ? transferenciaBase.getOrigen().getNumber() : transferenciaBase.getDebitAccount(),//DEBACC
				transferenciaBase.getAmount(),
				transferenciaBase.getValidationDay(),//VDD
				transferenciaBase.getValidationMonth(),//VMM
				transferenciaBase.getValidationYear(),//VYY
				transferenciaBase.getValidationHour(),//VHH
				transferenciaBase.getValidationMinute(),//VSS
						
				transferenciaBase.getCreditAccount(), //LINE
				transferenciaBase.getReferenceNumber(), //FOLIO
				transferenciaBase.getContractId(), //OUSER
				transferenciaBase.getContractId(), //VUSER
				transferenciaBase.getContractId(), //AUSER
				transferenciaBase.getOperationDate(), //ODATE
				transferenciaBase.getValidationDate(), //VDATE
				transferenciaBase.getAuthorizationDate(), //ADATE
				"1",  //STS
				transferenciaBase.getApprobationNumber(),  //NUMAPR
				transferenciaBase.getAfirmeNetReference(),//DCIBS_REF

				transferenciaBase.getBeneficiaryName() //LINEA_PAGO
				};
		
		db2Dao.getJdbcTemplate().update(sql, params);
		
	}

	
}
