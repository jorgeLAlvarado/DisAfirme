package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.dao.transferencia.CuentaTercerosDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.ibs.databeans.SPABANPF;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.model.base.CatalogoBase;
import com.afirme.afirmenet.model.transferencia.Divisa;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;

@Repository
public class CuentaTercerosDaoImpl implements CuentaTercerosDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(CuentaTercerosDaoImpl.class);
	
	@Autowired
	private DB2Dao db2Dao;
	@Autowired
	private AS400Dao as400Dao;
	
	private final String FECHA_CREACION = "20070301000000";
	
	@Override
	public List<ACCTHIRDUSER> getListaTerceros(String idContrato, String tipoContrato, String idUsuario, String tiempoEsperaCuentas) {
		
		LOG.info("Obteniendo cuentas de Terceros de: " + idContrato + ", tipo de Contrato: " + tipoContrato + ", idUsuario: " +
				"" + idUsuario + ", tiempoEsperaCuentas: " + tiempoEsperaCuentas);
		return getListaTercerosNoAlteraLista(idContrato, tipoContrato, idUsuario, tiempoEsperaCuentas);
		/*
		String sql = "";
		List<Object> args = new ArrayList<Object>();
		
		if (tipoContrato.equals("03")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.TRANSTYPE = '03' " +
				"AND STS = 'A' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
		}
		// idUsuario
		if (tipoContrato.equals("10")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRDUSER A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.USERID = ? " +
				"ANDA.TRANSTYPE = '10' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(idUsuario);
		}
		// idUsuario
		if (tipoContrato.equals("04")) {
			sql =
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRDUSER A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE AND A.ENTITYID = ? " +
				"AND A.USERID = ? " +
				"AND A.TRANSTYPE = '04' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(idUsuario);
		}
		if (tipoContrato.equals("05")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '09' " +
				"OR A.TRANSTYPE = '05') " +
				"AND A.STS = 'A' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
		}
		// Para Domiciliacion
		if (tipoContrato.equals("06")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '03' " +
				"OR A.TRANSTYPE = '05' " +
				"OR A.TRANSTYPE = '09') " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
		}
		if (tipoContrato.equals("09")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '09' " +
				"OR A.TRANSTYPE = '05') " +
				"AND A.STS = 'A' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
		}
		// idUsuario
		if (tipoContrato.equals("39")) {
			sql =
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRDUSER A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.USERID = ? " +
				"AND (A.TRANSTYPE = '03' " +
				"OR A.TRANSTYPE = '09') " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(idUsuario);
		}
		
		List<Map<String, Object>> usrList = new ArrayList<Map<String, Object>>();
		
		try {
			usrList = db2Dao.getJdbcTemplate().queryForList(sql, args.toArray());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio. " + e.getMessage());
			return null;
		}
		
		if (usrList != null) {
			
			GregorianCalendar fechaValidacion = new GregorianCalendar();
			fechaValidacion.add(Calendar.SECOND, 1 + Integer.parseInt(tiempoEsperaCuentas) * -1);
			
			//Set<ACCTHIRDUSER> setAccThirdUser = null;
			List<ACCTHIRDUSER> listAccThirdUser = new ArrayList<ACCTHIRDUSER>();
			//setAccThirdUser = new HashSet<ACCTHIRDUSER>();
			// al convertir la Lista a Set se remueven los duplicados
			Set<Map<String, Object>> usrSet = new HashSet<Map<String, Object>>(usrList);
			
			for (Map<String, Object> map : usrSet) {
				
				ACCTHIRDUSER accThird = new ACCTHIRDUSER();
				
				accThird.setENTITYID(map.get("ENTITYID") != null ? map.get("ENTITYID").toString() : null);
				accThird.setACCNUM(map.get("ACCNUM") != null ? map.get("ACCNUM").toString() : null);
				accThird.setACCTYPE(map.get("ACCTYPE") != null ? map.get("ACCTYPE").toString() : null);
				accThird.setACCOWNER(map.get("ACCOWNER") != null ? map.get("ACCOWNER").toString() : null);
				accThird.setNICKNAME(map.get("NICKNAME") != null ? map.get("NICKNAME").toString() : null);
				accThird.setSTS(map.get("STS") != null ? map.get("STS").toString() : null);
				accThird.setTRANSTYPE(map.get("TRANSTYPE") != null ? map.get("TRANSTYPE").toString() : null);
				accThird.setTRANSDESC(map.get("TRANSDESC") != null ? map.get("TRANSDESC").toString() : null);
				accThird.setBNKCODE(map.get("BNKCODE") != null ? map.get("BNKCODE").toString() : null);
				accThird.setBNKNAME(map.get("BNKNAME") != null ? map.get("BNKNAME").toString() : null);
				accThird.setCURRENCY(map.get("CURRENCY") != null ? map.get("CURRENCY").toString() : null);
				
				accThird.setACCTYPE(map.get("ACCTYPE") != null ? map.get("ACCTYPE").toString().trim() : "");
				accThird.setRECNUM(map.get("RECNUM") != null ? (BigDecimal)map.get("RECNUM") : new BigDecimal("0"));
				
				//String fechaCreacion = map.get("FECHACREACION").toString();
				String fechaCreacion = (String)map.get("FECHACREACION");
				
				if (fechaCreacion == null)
					fechaCreacion = FECHA_CREACION;
				
				GregorianCalendar fechaCreac=new GregorianCalendar();
				fechaCreac.set(Integer.parseInt(fechaCreacion.substring(0,4)),Integer.parseInt(fechaCreacion.substring(4,6))-1,Integer.parseInt(fechaCreacion.substring(6,8)),Integer.parseInt(fechaCreacion.substring(8,10)),Integer.parseInt(fechaCreacion.substring(10,12)),Integer.parseInt(fechaCreacion.substring(12,14)));
				
				fechaCreac.add(Calendar.SECOND,1);
				
				if (fechaCreac.before(fechaValidacion)) {
					//LOG.debug(accThird.getACCNUM());
					//setAccThirdUser.add(accThird);
					listAccThirdUser.add(accThird);
				}
			}
			
			//List<ACCTHIRDUSER> listAccThirdUser = new ArrayList<ACCTHIRDUSER>(setAccThirdUser);
			return listAccThirdUser;
		}
		
		return null;
		*/
	}
	
	/**
	 * Este metodo es una version alternativa para:<br>
	 * public List<ACCTHIRDUSER> getListaTerceros(String idContrato, String tipoContrato, String idUsuario, String tiempoEsperaCuentas)<br>
	 * <br>
	 * @see com.afirme.afirmenet.dao.transferencia.CuentaTercerosDao#getListaTerceros(java.lang.String, java.lang.String, java.lang.String, java.lang.String)<br>
	 * <br>
	 * Este metodo es para consultar las cuentas terceros, para el modulo configuracion > Cuentas destino > Eliminar.<br>
	 * <br>
	 * Se hace necesario duplicar este codigo porque no quiero alterar el metodo <b>getListaTerceros</b> quitando el SET que utiliza y que altera el orden en el view.<br>
	 * <br>
	 * Este metodo no altera la lista de resultados. En el metodo: <b>getListaTerceros</b> se utiliza un set para quitar los duplicados utilizando un SET que no garantiza ordenamiento.<br>
	 * <br>
	 * @param idContrato
	 * @param tipoContrato
	 * @param idUsuario
	 * @param tiempoEsperaCuentas
	 * @return
	 */
	public List<ACCTHIRDUSER> getListaTercerosNoAlteraLista(String idContrato, String tipoContrato, String idUsuario, String tiempoEsperaCuentas) {
		LOG.info("Obteniendo cuentas de Terceros de: " + idContrato + ", tipo de Contrato: " + tipoContrato + ", idUsuario: " +
				"" + idUsuario + ", tiempoEsperaCuentas: " + tiempoEsperaCuentas);
		
		List<ACCTHIRDUSER> listAccThirdUser = new ArrayList<ACCTHIRDUSER>();
		String sql = "";
		List<Object> args = new ArrayList<Object>();
		
		if (tipoContrato.equals("03")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.TRANSTYPE = '03' " +
				"AND STS = 'A' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
		}
		// idUsuario
		if (tipoContrato.equals("10")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRDUSER A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.USERID = ? " +
				"ANDA.TRANSTYPE = '10' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(idUsuario);
		}
		// idUsuario
		if (tipoContrato.equals("04")) {
			sql =
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRDUSER A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE AND A.ENTITYID = ? " +
				"AND A.USERID = ? " +
				"AND A.TRANSTYPE = '04' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(idUsuario);
		}
		if (tipoContrato.equals("05")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '09' " +
				"OR A.TRANSTYPE = '05') " +
				"AND A.STS = 'A' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
		}
		// Para Domiciliacion
		if (tipoContrato.equals("06")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '03' " +
				"OR A.TRANSTYPE = '05' " +
				"OR A.TRANSTYPE = '09') " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
		}
		if (tipoContrato.equals("09")) {
			sql = 
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '09' " +
				"OR A.TRANSTYPE = '05') " +
				"AND A.STS = 'A' " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
		}
		// idUsuario
		if (tipoContrato.equals("39")) {
			sql =
				"SELECT A.RECNUM,A.ACCTYPE,A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRDUSER A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.USERID = ? " +
				"AND (A.TRANSTYPE = '03' " +
				"OR A.TRANSTYPE = '09') " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(idUsuario);
		}
		
		List<Map<String, Object>> usrList = new ArrayList<Map<String, Object>>();
		
		try {
			usrList = db2Dao.getJdbcTemplate().queryForList(sql, args.toArray());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio. " + e.getMessage());
			return null;
		}
	
		if (usrList != null) {
			
			GregorianCalendar fechaValidacion = new GregorianCalendar();
			fechaValidacion.add(Calendar.SECOND, 1 + Integer.parseInt(tiempoEsperaCuentas) * -1);
			
//			Set<ACCTHIRDUSER> setAccThirdUser = null;
//			
//			setAccThirdUser = new HashSet<ACCTHIRDUSER>();
			// al convertir la Lista a Set se remueven los duplicados
//			Set<Map<String, Object>> usrSet = new HashSet<Map<String, Object>>(usrList);
//			
//			for (Map<String, Object> map : usrSet) {
			
			Iterator<Map<String, Object>> listaRespuestaIterator = usrList.iterator();
			while (listaRespuestaIterator.hasNext()) {
				Map<String, Object> map = listaRespuestaIterator.next();
				
				ACCTHIRDUSER accThird = new ACCTHIRDUSER();
				
				accThird.setENTITYID(map.get("ENTITYID") != null ? map.get("ENTITYID").toString().trim() : null);
				accThird.setACCNUM(map.get("ACCNUM") != null ? map.get("ACCNUM").toString().trim() : null);
				accThird.setACCTYPE(map.get("ACCTYPE") != null ? map.get("ACCTYPE").toString().trim() : null);
				accThird.setACCOWNER(map.get("ACCOWNER") != null ? map.get("ACCOWNER").toString().trim() : null);
				accThird.setNICKNAME(map.get("NICKNAME") != null ? map.get("NICKNAME").toString().trim() : null);
				accThird.setSTS(map.get("STS") != null ? map.get("STS").toString().trim() : null);
				accThird.setTRANSTYPE(map.get("TRANSTYPE") != null ? map.get("TRANSTYPE").toString().trim() : null);
				accThird.setTRANSDESC(map.get("TRANSDESC") != null ? map.get("TRANSDESC").toString().trim() : null);
				accThird.setBNKCODE(map.get("BNKCODE") != null ? map.get("BNKCODE").toString().trim() : null);
				accThird.setBNKNAME(map.get("BNKNAME") != null ? map.get("BNKNAME").toString().trim() : null);
				accThird.setCURRENCY(map.get("CURRENCY") != null ? map.get("CURRENCY").toString().trim() : null);
				
				accThird.setACCTYPE(map.get("ACCTYPE") != null ? map.get("ACCTYPE").toString().trim() : "");
				accThird.setRECNUM(map.get("RECNUM") != null ? (BigDecimal)map.get("RECNUM") : new BigDecimal("0"));
				
				//String fechaCreacion = map.get("FECHACREACION").toString();
				String fechaCreacion = (String)map.get("FECHACREACION");
				
				if (fechaCreacion == null)
					fechaCreacion = FECHA_CREACION;
				
				GregorianCalendar fechaCreac=new GregorianCalendar();
				fechaCreac.set(Integer.parseInt(fechaCreacion.substring(0,4)),Integer.parseInt(fechaCreacion.substring(4,6))-1,Integer.parseInt(fechaCreacion.substring(6,8)),Integer.parseInt(fechaCreacion.substring(8,10)),Integer.parseInt(fechaCreacion.substring(10,12)),Integer.parseInt(fechaCreacion.substring(12,14)));
				
				fechaCreac.add(Calendar.SECOND,1);
				
				if (fechaCreac.before(fechaValidacion)) {
					//LOG.debug(accThird.getACCNUM());
//					setAccThirdUser.add(accThird);
					listAccThirdUser.add(accThird);
				}
			}
			
//			List<ACCTHIRDUSER> listAccThirdUser = new ArrayList<ACCTHIRDUSER>(setAccThirdUser);
			return listAccThirdUser;
		}
		
		return null;
	}

	@Override
	public ACCTHIRDUSER getCuentaTercero(String idContrato, String cuentaTercero, String tipoContrato, String idUsuario, String tiempoEsperaCuentas) {
		LOG.info("Obteniendo cuentas de Terceros: " + cuentaTercero + ", del Contrato: " + idContrato + "tipo de Contrato: " + tipoContrato + ", idUsuario: " +
				"" + idUsuario + ", tiempoEsperaCuentas: " + tiempoEsperaCuentas);
		
		String sql = "";
		List<Object> args = new ArrayList<Object>();
		
		if (tipoContrato.equals("03")) {
			sql = 
				"SELECT A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.TRANSTYPE = '03' " +
				"AND STS = 'A' " +
				"AND A.ACCNUM = ? " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(cuentaTercero);
		}
		// idUsuario
		if (tipoContrato.equals("10")) {
			sql = 
				"SELECT A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRDUSER A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.USERID = ? " +
				"ANDA.TRANSTYPE = '10' " +
				"AND A.ACCNUM = ? " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(idUsuario);
			args.add(cuentaTercero);
		}
		// idUsuario
		if (tipoContrato.equals("04")) {
			sql =
				"SELECT A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE AND A.ENTITYID = ? " +
				
				"AND A.TRANSTYPE = '04' " +
				"AND A.ACCNUM = ? " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(cuentaTercero);
		}
		if (tipoContrato.equals("05")) {
			sql = 
				"SELECT A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '09' " +
				"OR A.TRANSTYPE = '05') " +
				"AND A.STS = 'A' " +
				"AND A.ACCNUM = ? " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(cuentaTercero);
		}
		// Para Domiciliacion
		if (tipoContrato.equals("06")) {
			sql = 
				"SELECT A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '03' " +
				"OR A.TRANSTYPE = '05' " +
				"OR A.TRANSTYPE = '09') " +
				"AND A.ACCNUM = ? " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(cuentaTercero);
		}
		if (tipoContrato.equals("09")) {
			sql = 
				"SELECT A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRD A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND (A.TRANSTYPE = '09' " +
				"OR A.TRANSTYPE = '05') " +
				"AND A.STS = 'A' " +
				"AND A.ACCNUM = ? " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(cuentaTercero);
		}
		// idUsuario
		if (tipoContrato.equals("39")) {
			sql =
				"SELECT A.ENTITYID, A.ACCNUM, A.ACCOWNER, A.ACCTYPE, A.TRANSTYPE, A.NICKNAME, A.STS, " +
				"A.BNKCODE, A.BNKNAME, B.TRANSDESC, A.FECHACREACION, A.CURRENCY " +
				"FROM DC_ACCTHIRDUSER A, DC_PROCODE B " +
				"WHERE B.TRANSTYPE = A.TRANSTYPE " +
				"AND A.ENTITYID = ? " +
				"AND A.USERID = ? " +
				"AND (A.TRANSTYPE = '03' " +
				"OR A.TRANSTYPE = '09') " +
				"AND A.ACCNUM = ? " +
				"ORDER BY A.ACCOWNER";
			
			args.add(idContrato);
			args.add(idUsuario);
			args.add(cuentaTercero);
		}
		if(args.isEmpty())
			return new ACCTHIRDUSER();
		/*Map<String, Object> usrMap = new HashMap<String, Object>();
		
		try {
			usrMap = db2Dao.getJdbcTemplate().queryForMap(sql, args.toArray());
			*/
		List<Map<String, Object>> listaRespuesta = new ArrayList<Map<String, Object>>();
		
		try {
			listaRespuesta = db2Dao.getJdbcTemplate().queryForList(sql, args.toArray());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio. " + e.getMessage());
			return null;
		}
		Map<String, Object> usrMap = null;
		if(listaRespuesta.size()>0)
			usrMap=listaRespuesta.get(0);
		if (usrMap != null) {
			
			GregorianCalendar fechaValidacion = new GregorianCalendar();
			fechaValidacion.add(Calendar.SECOND, 1 + Integer.parseInt(tiempoEsperaCuentas) * -1);
			
			ACCTHIRDUSER accThird = new ACCTHIRDUSER();
			
			accThird.setENTITYID(usrMap.get("ENTITYID") != null ? usrMap.get("ENTITYID").toString() : null);
			accThird.setACCNUM(usrMap.get("ACCNUM") != null ? usrMap.get("ACCNUM").toString() : null);
			accThird.setACCTYPE(usrMap.get("ACCTYPE") != null ? usrMap.get("ACCTYPE").toString() : null);
			accThird.setACCOWNER(usrMap.get("ACCOWNER") != null ? usrMap.get("ACCOWNER").toString() : null);
			accThird.setNICKNAME(usrMap.get("NICKNAME") != null ? usrMap.get("NICKNAME").toString() : null);
			accThird.setSTS(usrMap.get("STS") != null ? usrMap.get("STS").toString() : null);
			accThird.setTRANSTYPE(usrMap.get("TRANSTYPE") != null ? usrMap.get("TRANSTYPE").toString() : null);
			accThird.setTRANSDESC(usrMap.get("TRANSDESC") != null ? usrMap.get("TRANSDESC").toString() : null);
			accThird.setBNKCODE(usrMap.get("BNKCODE") != null ? usrMap.get("BNKCODE").toString() : null);
			accThird.setBNKNAME(usrMap.get("BNKNAME") != null ? usrMap.get("BNKNAME").toString() : null);
			accThird.setCURRENCY(usrMap.get("CURRENCY") != null ? usrMap.get("CURRENCY").toString() : null);
			
			String fechaCreacion = usrMap.get("FECHACREACION") != null ?usrMap.get("FECHACREACION").toString():null;
			
			if (fechaCreacion == null)
				fechaCreacion = FECHA_CREACION;
			
			GregorianCalendar fechaCreac=new GregorianCalendar();
			fechaCreac.set(Integer.parseInt(fechaCreacion.substring(0,4)),Integer.parseInt(fechaCreacion.substring(4,6))-1,Integer.parseInt(fechaCreacion.substring(6,8)),Integer.parseInt(fechaCreacion.substring(8,10)),Integer.parseInt(fechaCreacion.substring(10,12)),Integer.parseInt(fechaCreacion.substring(12,14)));
			
			fechaCreac.add(Calendar.SECOND,1);
			
			if (fechaCreac.before(fechaValidacion)) {
				LOG.debug(accThird.getACCNUM());
				return accThird;
			}
		}
		
		return null;
	}

	/**
	 * Para listar los tipos de operaciones en el combo para eliminar cuentas destino.
	 * 
	 * @param bPaqueteSinToken
	 * @return List<CatalogoBase>
	 * @throws Exception
	 */
	@Override
	public List<CatalogoBase> getProCodeList(Boolean bPaqueteSinToken) throws Exception {
		LOG.debug("Inicia getTipoOperaciones()");
		if(LOG.isDebugEnabled()){
			LOG.debug("--- parametros ---");
			LOG.debug("\tbPaqueteSinToken=" + bPaqueteSinToken);
		}
		
		List<CatalogoBase> listaCatalogoBase = new ArrayList<CatalogoBase>();
		
		String sql ="SELECT TRANSTYPE,TRANSDESC from DC_PROCODE";
		if(bPaqueteSinToken){
			sql += " WHERE TRANSTYPE IN ('36','02', '90') ";
    	}
		sql += " order by TRANSDESC";
		
		List<Map<String, Object>> listaRespuesta = new ArrayList<Map<String, Object>>();
		try {
			listaRespuesta = db2Dao.getJdbcTemplate().queryForList(sql);
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al consultar las operaciones.", e);
		}

		Iterator<Map<String, Object>> listaRespuestaIterator = listaRespuesta.iterator();
		while (listaRespuestaIterator.hasNext()) {
			Map<String, Object> map = listaRespuestaIterator.next();
			
			CatalogoBase catalogoBase = new CatalogoBase();
			catalogoBase.setValor(       map.get("TRANSTYPE") != null ? map.get("TRANSTYPE").toString().trim() : null );
			catalogoBase.setDescripcion( map.get("TRANSDESC") != null ? map.get("TRANSDESC").toString().trim() : null);
			listaCatalogoBase.add(catalogoBase);
		}
		
		return listaCatalogoBase;
	}
	
	/**
	 * Para eliminar cuenta terceros afirme.<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * 2.- borrar la cuenta terceros de DC_ACCTHIRD<br>
	 * En caso de que falle (2.-) se borra el insert que se hizo en DC_CONVENIO_LOG<br>
	 * <br>
	 * @param contrato
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delAccPersonas(String contrato, ACCTHIRDUSER accthird) throws Exception{
		boolean seHaBorradoLacuentaTerceros = false;
		StringBuffer sb = new StringBuffer();
		int affectedRows = 0;
		boolean guardadoEnHistorialTerceros = false;
		
		try{
			// set fecha actual para registrar fecha borrado
			Calendar test = Util.convertCalendar(Calendar.getInstance(), TimeZone.getTimeZone("Mexico/General")); // Convierte la fecha a la actual en Mexico
			Timestamp today = new Timestamp(test.getTimeInMillis());
			accthird.setFechaCreacionTS(today);
			
			// Estatus D
			accthird.setSTS("D");
			
			// agregar a historial terceros
			guardadoEnHistorialTerceros = this.addHistorialTerceros(contrato, accthird);
			
			if(guardadoEnHistorialTerceros == true){
				// borrar cuenta terceros
				sb.append("delete from DC_ACCTHIRD where ENTITYID=? and ACCNUM=?");
				Object[] args = {
						contrato,
						accthird.getACCNUM()
					};
				
				affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
				
				if (affectedRows > 0){
					seHaBorradoLacuentaTerceros = true;
				}
			}
			
			//Si fallo al borrar el registro se borra el registro insertado en el Log
			if(seHaBorradoLacuentaTerceros == false){
				this.delHistorialTerceros(accthird);
			}
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al borrar una cuenta de terceros.", e);
		}
		
		
			
		LOG.debug("Resultado obtenido:" + seHaBorradoLacuentaTerceros);
		
		return seHaBorradoLacuentaTerceros;
	}
	
	/**
	 * Cuando se elimina una cuenta de terceros afirme, se registra en DC_CONVENIO_LOG.<br>
	 * <br>
	 * @param contrato
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	private boolean addHistorialTerceros(String contrato, ACCTHIRDUSER accthird) throws Exception{
		boolean resultado = false;
		int affectedRows = 0;
		
		try{
			StringBuffer sb = new StringBuffer();
			//                                       1       2      3             4                5        6      7                       8            9            10              11          12                 
			sb.append(" INSERT INTO DC_CONVENIO_LOG (CONVNUM,STATUS,FECHA_BORRADO,TIPO_TRANSACCION,ENTITYID,CUENTA,DESCRIPCION_TRANSACCION,TIPO_EMPRESA,NOMBRE_BANCO,PROPIETARIO_CTA,TITULAR_CTA,ESTATUS_PT)");
			sb.append(" VALUES (?,?,?,?,?, ?,?,?,?,? ,?,?)" );
			
			Object[] args = {
					accthird.getRECNUM(),         // 1
					"C",                          // 2
					accthird.getFechaCreacionTS(),// 3
					accthird.getTRANSDESC(),      // 4
					accthird.getENTITYID(),       // 5
					accthird.getACCNUM(),         // 6
					"",                           // 7
					"",                           // 8
					accthird.getBNKNAME(),        // 9
					accthird.getACCOWNER(),       // 10
					accthird.getNICKNAME(),       // 11
					accthird.getSTS()             // 12
				};
			
			affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al agregar a historial terceros.", e);
		}
		
		if (affectedRows != 0){
			resultado = true;
		}
		
		LOG.debug("Resultado obtenido:" + resultado);
		
		return resultado;
	}
	
	/**
	 * Este metodo es utilizado para rollback. <br>
	 * <br> 
	 * Cuando se elimina una cuenta terceros, <br> 
	 * se guarda en la tabla DC_CONVENIO_LOG, utilizando el metodo addHistorialTerceros,<br>
	 * en caso de alguna falla al eliminar la cuenta terceros, se elimina el registro en DC_CONVENIO_LOG.<br>
	 * 
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	private boolean delHistorialTerceros(ACCTHIRDUSER accthird) throws Exception{
		boolean resultado = false;
		int affectedRows = 0;
		StringBuffer sb = new StringBuffer(); 
		
		try{
			sb.append("DELETE FROM DC_CONVENIO_LOG WHERE CONVNUM=? AND TIPO_TRANSACCION =? AND FECHA_BORRADO =? AND ENTITYID =?");
			Object[] args = {
					accthird.getRECNUM(),
					accthird.getTRANSDESC(),
					accthird.getFechaCreacionTS(),
					accthird.getENTITYID()
				};
			
			affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al borrar de historial terceros.", e);
		}
		
		if (affectedRows != 0){
			resultado = true;
		}
		
		LOG.debug("Resultado obtenido:" + resultado);
		
		return resultado;
		
	}
	
	
	/**
	 * Para eliminar cuenta swift y swift intermediarios.<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * 2.- borrar la cuenta swift o swift intermediario Divisa<br>
	 * En caso de que falle (2.-) se borra el insert que se hizo en DC_CONVENIO_LOG<br>
	 * <br>
	 * @see com.afirme.afirmenet.model.transferencia.Divisa
	 * 
	 * @param cuentaSWIFT de tipo Divisa
	 * @return boolean
	 * @throws Exception
	 */
	public boolean borraCuentaSWIFTPersonas(Divisa cuentaSWIFT) throws Exception{
		boolean seHaBorradoLacuentaTerceros = false;
		StringBuffer sb = new StringBuffer();
		int affectedRows = 0;
		boolean guardadoEnHistorialTerceros = false;
		
		try{
			// set fecha actual para registrar fecha borrado
			Calendar test = Util.convertCalendar(Calendar.getInstance(), TimeZone.getTimeZone("Mexico/General")); // Convierte la fecha a la actual en Mexico
			Timestamp today = new Timestamp(test.getTimeInMillis());
			cuentaSWIFT.setFechaBorradoTS(today);
			
			// Estatus A seteado hardcod en controller
			
			// agregar a historial terceros
			guardadoEnHistorialTerceros = this.setAddHistorialSWIFT(cuentaSWIFT);
			
			if(guardadoEnHistorialTerceros == true){
				// borrar cuenta terceros
				// terceros sb.append("delete from DC_ACCTHIRD where ENTITYID=? and ACCNUM=?");
				sb.append("DELETE FROM DC_ACCSWIFTUSER WHERE  USERID=? AND ACCNUM=? AND TIPOSWIFT=? AND SWIFTCODE=?");
				Object[] args = {
						cuentaSWIFT.getDebitAccount(), // contrato
						cuentaSWIFT.getCreditAccount(), // cuenta destino
						cuentaSWIFT.getTipoSWIFT(),
						cuentaSWIFT.getCodigoSWIFToCodigoABA(),
					};
				
				affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
				
				if (affectedRows > 0){
					seHaBorradoLacuentaTerceros = true;
				}
			}
			
			//Si fallo al borrar el registro se borra el registro insertado en el Log
			if(seHaBorradoLacuentaTerceros == false){
				this.delHistorialTercerosSWIFT(cuentaSWIFT);
			}
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al borrar una cuenta de swift o swift intermediario.", e);
		}
		
		
			
		LOG.debug("Resultado obtenido:" + seHaBorradoLacuentaTerceros);
		
		return seHaBorradoLacuentaTerceros;
	}
	
	/**
	 * Para agregar al log.<br>
	 * <br>
	 * Podria otimizarse para utilizar un generic. Es el mismo metodo para guardar terceros log.<br>
	 * <br>
	 * @see com.afirme.afirmenet.model.transferencia.Divisa
	 * 
	 * @param contrato
	 * @param cuentaSwift de tipo Divisa
	 * @return
	 * @throws Exception
	 */
	private boolean setAddHistorialSWIFT(Divisa cuentaSWIFT) throws Exception{
		boolean resultado = false;
		int affectedRows = 0;
		
		try{
			StringBuffer sb = new StringBuffer();
			//                                       1       2      3             4                5        6      7                        8            9            10              11          12
			sb.append(" INSERT INTO DC_CONVENIO_LOG (CONVNUM,STATUS,FECHA_BORRADO,TIPO_TRANSACCION,ENTITYID,CUENTA,DESCRIPCION_TRANSACCION,TIPO_EMPRESA,NOMBRE_BANCO,PROPIETARIO_CTA,TITULAR_CTA,ESTATUS_PT)");
			sb.append(" VALUES (?,?,?,?,?, ?,?,?,?,? ,?,?)" );
			
			Object[] args = {
					Util.getStringMaxLen(cuentaSWIFT.getCreditAccount(),20), // 1
					"C",                                                     // 2
					cuentaSWIFT.getFechaBorradoTS(),                         // 3
					cuentaSWIFT.getTipoSWIFT(),                              // 4 S o F
					cuentaSWIFT.getDebitAccount(),                           // 5 contrato
					Util.getStringMaxLen(cuentaSWIFT .getCreditAccount(),35),// 6 cuenta destino
					"",                                                      // 7
					"",                                                      // 8
					Util.getStringMaxLen(cuentaSWIFT.getBankName(), 70),     // 9
					Util.getStringMaxLen(cuentaSWIFT.getBankName(), 70),     // 10
					cuentaSWIFT.getBankEstado(),                             // 11
					cuentaSWIFT.getEstatus()                                 // 12
				};
			
			affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al agregar a historial la cuenta swift o swift intermediario.", e);
		}
		
		if (affectedRows != 0){
			resultado = true;
		}
		
		LOG.debug("Resultado obtenido:" + resultado);
		
		return resultado;
	}
	
	/**
	 * Este metodo es utilizado para rollback. <br>
	 * Este metodo es el mismo para borrar terceros, buscar forma optimizar.
	 * <br> 
	 * Cuando se elimina una cuenta terceros, <br> 
	 * se guarda en la tabla DC_CONVENIO_LOG, utilizando el metodo addHistorialTerceros,<br>
	 * en caso de alguna falla al eliminar la cuenta terceros, se elimina el registro en DC_CONVENIO_LOG.<br>
	 * 
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	private boolean delHistorialTercerosSWIFT(Divisa cuentaSWIFT) throws Exception{
		boolean resultado = false;
		int affectedRows = 0;
		StringBuffer sb = new StringBuffer(); 
		
		try{
			sb.append("DELETE FROM DC_CONVENIO_LOG WHERE CONVNUM=? AND TIPO_TRANSACCION =? AND FECHA_BORRADO =? AND ENTITYID =?");
			Object[] args = {
					Util.getStringMaxLen(cuentaSWIFT.getCreditAccount(),20),
					cuentaSWIFT.getTipoSWIFT(),
					cuentaSWIFT.getFechaBorradoTS(),
					cuentaSWIFT.getDebitAccount()
				};
			
			affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al borrar de historial la cuenta swift o swift intermediario.", e);
		}
		
		if (affectedRows != 0){
			resultado = true;
		}
		
		LOG.debug("Resultado obtenido:" + resultado);
		
		return resultado;
		
	}
	
	/**
	 * Obtiene todos los convenios del usuario (Excluyendo Pago Pemex,COMISION DIA ACTUAL FAX,COMISION MES ACTUAL FAX,COMISION MES ANTERIOR FAX y servicios basicos)
	 * <br><br>
	 * Utilizado para consultar las tarjetas de credito para eliminar. 
	 *  <br><br>
	 * @see com.afirme.afirmenet.dao.transferencia.CuentaTercerosDao#getConvList(java.lang.String, java.lang.String)
	 *  
	 * @param numeroContrato de tipo String
	 * @param tiempoEsperaCuentas de tipo String
	 * @return List&lt;DC_CONVENIO&gt;
	 * @throws Exception
	 */
	@Override
	public List<DC_CONVENIO> getConvList(String numeroContrato, String tiempoEsperaCuentas) throws Exception{
		LOG.debug("Inicia getConvList()");
		if(LOG.isDebugEnabled()){
			LOG.debug("\t--- parametros ---");
			LOG.debug("\tnumeroContrato=" + numeroContrato);
			LOG.debug("\ttiempoEsperaCuentas=" + tiempoEsperaCuentas);
		}
		
		List<DC_CONVENIO> listaConveniosUsuario = new ArrayList<DC_CONVENIO>();
		String sql = "SELECT ENTITYID,SERTYP,SERCOM,SERACC,SERNOM,SERNAM,SERDS1,SERDS2,SERRTY,SERMIN,SERMAX,SERFLG,SERTAX,SERDTR,SERHLP,CONVNUM,FECHACREACION,TCNOMBRE " +
					 "from DC_CONVENIO " +
					 "WHERE ENTITYID=? AND SERTYP NOT IN (1,7,9,10,12,13,20,21,22,23) ORDER BY SERCOM,SERTYP";
		
		List<Map<String, Object>> listaRespuesta = new ArrayList<Map<String, Object>>();
		try{
			Object[] args = { 
					numeroContrato 
					};
			
			listaRespuesta = db2Dao.getJdbcTemplate().queryForList(sql, args);
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio." + e.getMessage());
		}catch(Exception e){
			throw new Exception("Error al consultar los convenios del usuario.", e);
		}
		
		GregorianCalendar fechaValidacion = new GregorianCalendar();
		fechaValidacion.add(Calendar.SECOND, 1 + Integer.parseInt(tiempoEsperaCuentas) * -1);
					
		Iterator<Map<String, Object>> listaRespuestaIterator = listaRespuesta.iterator();
		while (listaRespuestaIterator.hasNext()) {
			Map<String, Object> map = listaRespuestaIterator.next();
			
			DC_CONVENIO convenioUsuario = new DC_CONVENIO();
			
			convenioUsuario.setCONVNUM( map.get("CONVNUM") != null ? new BigDecimal(map.get("CONVNUM").toString().trim()) : new BigDecimal("0") );
			convenioUsuario.setENTITYID( map.get("ENTITYID") != null ? map.get("ENTITYID").toString().trim(): "" );
//			convenioUsuario.setFECHACREACION( map.get("FECHACREACION") != null ? map.get("FECHACREACION").toString().trim(): "" );
			convenioUsuario.setSERACC( map.get("SERACC") != null ? map.get("SERACC").toString().trim(): "" );
			convenioUsuario.setSERCOM( map.get("SERCOM") != null ? Integer.parseInt( map.get("SERCOM").toString().trim() ): 0 );
			convenioUsuario.setSERDS1( map.get("SERDS1") != null ? map.get("SERDS1").toString().trim(): "" );
			convenioUsuario.setSERDS2( map.get("SERDS2") != null ? map.get("SERDS2").toString().trim(): "" );
			convenioUsuario.setSERDTR( map.get("SERDTR") != null ? Integer.parseInt(map.get("SERDTR").toString().trim()): 0 );
			convenioUsuario.setSERFLG( map.get("SERFLG") != null ? map.get("SERFLG").toString().trim(): "" );
			convenioUsuario.setSERHLP( map.get("SERHLP") != null ? map.get("SERHLP").toString().trim(): "" );
			convenioUsuario.setSERMAX( map.get("SERMAX") != null ? Integer.parseInt(map.get("SERMAX").toString().trim()): 0 );
			convenioUsuario.setSERMIN( map.get("SERMIN") != null ? Integer.parseInt(map.get("SERMIN").toString().trim()): 0 );
			convenioUsuario.setSERNAM( map.get("SERNAM") != null ? map.get("SERNAM").toString().trim(): "" );
			convenioUsuario.setSERNOM( map.get("SERNOM") != null ? map.get("SERNOM").toString().trim(): "" );
			convenioUsuario.setSERRTY( map.get("SERRTY") != null ? map.get("SERRTY").toString().trim(): "" );
			convenioUsuario.setSERTAX( map.get("SERTAX") != null ? map.get("SERTAX").toString().trim(): "" );
			convenioUsuario.setSERTYP( map.get("SERTYP") != null ? Integer.parseInt(map.get("SERTYP").toString().trim()): 0 );
			convenioUsuario.setTCNOMBRE( map.get("TCNOMBRE") != null ? map.get("TCNOMBRE").toString().trim(): "" );
			
			String fechaCreacion = ( map.get("FECHACREACION") != null ? map.get("FECHACREACION").toString().trim(): null ); // usar null
			
			if (fechaCreacion == null){
				fechaCreacion = this.FECHA_CREACION;
			}
			
			convenioUsuario.setFECHACREACION(fechaCreacion);
			
			//Si la fecha de Creacion es antes de la fecha actual mas el periodo de espera entonces se muestra
			GregorianCalendar fechaCreac=new GregorianCalendar();
			fechaCreac.set(Integer.parseInt(fechaCreacion.substring(0,4)),Integer.parseInt(fechaCreacion.substring(4,6))-1,Integer.parseInt(fechaCreacion.substring(6,8)),Integer.parseInt(fechaCreacion.substring(8,10)),Integer.parseInt(fechaCreacion.substring(10,12)),Integer.parseInt(fechaCreacion.substring(12,14)));
			
			//Se realiza un add para que recalcule todos los campos
			fechaCreac.add(Calendar.SECOND,1);
			
			if (fechaCreac.before(fechaValidacion)) {
				convenioUsuario.setSTS("A"); // esta harcode en afirmenet anterior.
				listaConveniosUsuario.add(convenioUsuario);
			}
			
			
			
		}
		
		return listaConveniosUsuario;
	}

	/**
	 * Para eliminar cuentas destino tarjetas credito y seguros afirme<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b>
	 * 2.- borrar la cuenta tarjetas de credito y seguros afirme DC_CONVENIO<br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b>
	 * En caso de que falle (2.-) se borra el insert que se hizo en DC_CONVENIO_LOG<br>
	 * <br>
	 * @see com.afirme.afirmenet.ibs.databeans.DC_CONVENIO
	 * @see com.afirme.afirmenet.dao.impl.transferencia.CuentaTercerosDao#CuentaTercerosDaoImpl(java.lang.String, com.afirme.afirmenet.ibs.databeans.DC_CONVENIO)
	 * 
	 * @param contrato
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	public boolean borrarConvAdmon(String contrato, DC_CONVENIO convenioUsuario) throws Exception{
		boolean seHaBorradoLacuentaTerceros = false;
		StringBuffer sb = new StringBuffer();
		int affectedRows = 0;
		boolean guardadoEnHistorialTerceros = false;
		
		try{
			// set fecha actual para registrar fecha borrado
			Calendar test = Util.convertCalendar(Calendar.getInstance(), TimeZone.getTimeZone("Mexico/General")); // Convierte la fecha a la actual en Mexico
			Timestamp today = new Timestamp(test.getTimeInMillis());
			convenioUsuario.setFechaBorradoTS(today);
			
			// Estatus D
			convenioUsuario.setSTS("D");
			
			// agregar a historial terceros
			guardadoEnHistorialTerceros = this.setAddHistorialConv(convenioUsuario);
			
			if(guardadoEnHistorialTerceros == true){
				// borrar cuenta terceros
				//sb.append("delete from DC_ACCTHIRD where ENTITYID=? and ACCNUM=?");
				sb.append("delete from DC_CONVENIO where ENTITYID=? AND CONVNUM=?");
				Object[] args = {
						contrato,
						convenioUsuario.getCONVNUM()
					};
				
				affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
				
				if (affectedRows > 0){
					seHaBorradoLacuentaTerceros = true;
				}
			}
			
			//Si fallo al borrar el registro se borra el registro insertado en el Log
			if(seHaBorradoLacuentaTerceros == false){
				this.delHistorialConv(convenioUsuario);
			}
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al borrar una cuenta de terceros.", e);
		}
		
		
			
		LOG.debug("Resultado obtenido:" + seHaBorradoLacuentaTerceros);
		
		return seHaBorradoLacuentaTerceros;
	}
	
	
	
	/**
	 * Para agregar al log.<br>
	 * <br>
	 * Podria otimizarse para utilizar un generic. Es el mismo metodo para guardar terceros log.<br>
	 * <br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b>
	 * <br>
	 * @see 
	 * 
	 * @param contrato
	 * @param cuentaSwift de tipo Divisa
	 * @return
	 * @throws Exception
	 */
	private boolean setAddHistorialConv(DC_CONVENIO convenioUsuario) throws Exception{
		boolean resultado = false;
		int affectedRows = 0;
		
		try{
			StringBuffer sb = new StringBuffer();
			//                                       1       2      3             4                5        6      7                        8            9            10              11          12
			sb.append(" INSERT INTO DC_CONVENIO_LOG (CONVNUM,STATUS,FECHA_BORRADO,TIPO_TRANSACCION,ENTITYID,CUENTA,DESCRIPCION_TRANSACCION,TIPO_EMPRESA,NOMBRE_BANCO,PROPIETARIO_CTA,TITULAR_CTA,ESTATUS_PT)");
			sb.append(" VALUES (?,?,?,?,?, ?,?,?,?,? ,?,?)" );
			
			Object[] args = {
					convenioUsuario.getCONVNUM(),                            // 1
					"C",                                                     // 2
					convenioUsuario.getFechaBorradoTS(),                     // 3
					"Pago Servicios",                                        // 4
					convenioUsuario.getENTITYID(),                           // 5 contrato
					convenioUsuario.getSERACC(),                             // 6 cuenta destino
					convenioUsuario.getSERNAM(),                             // 7
					convenioUsuario.getSERNOM(),                             // 8
					"",                                                      // 9
					"",                                                      // 10
					"",                                                      // 11
					convenioUsuario.getSTS()                                 // 12
				};
			
			affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al agregar a historial el convenio usuario.", e);
		}
		
		if (affectedRows != 0){
			resultado = true;
		}
		
		LOG.debug("Resultado obtenido:" + resultado);
		
		return resultado;
	}
	
	/**
	 * Este metodo es utilizado para rollback. <br>
	 * Este metodo es el mismo para borrar terceros, buscar forma optimizar.
	 * <br> 
	 * Cuando se elimina una cuenta terceros, <br> 
	 * se guarda en la tabla DC_CONVENIO_LOG, utilizando el metodo borrarConvAdmon,<br>
	 * en caso de alguna falla al eliminar la cuenta terceros, se elimina el registro en DC_CONVENIO_LOG.<br>
	 * <br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b>
	 * 
	 * 
	 * @param convenioUsuario de tipo DC_CONVENIO
	 * @return boolean
	 * @throws Exception
	 */
	private boolean delHistorialConv(DC_CONVENIO convenioUsuario) throws Exception{
		boolean resultado = false;
		int affectedRows = 0;
		StringBuffer sb = new StringBuffer(); 
		
		try{
			sb.append("DELETE FROM DC_CONVENIO_LOG WHERE CONVNUM=? AND TIPO_TRANSACCION =? AND FECHA_BORRADO =? AND ENTITYID =?");
			Object[] args = {
					convenioUsuario.getCONVNUM(),
					"Pago Servicios",
					convenioUsuario.getFechaBorradoTS(),
					convenioUsuario.getENTITYID()
				};
			
			affectedRows = this.db2Dao.getJdbcTemplate().update(sb.toString(), args);
			
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio.");
		}catch(Exception e){
			throw new Exception("Error al borrar de historial la cuenta convenio usuario.", e);
		}
		
		if (affectedRows != 0){
			resultado = true;
		}
		
		LOG.debug("Resultado obtenido:" + resultado);
		
		return resultado;
		
	}
	
	@Override
	public String getSiguienteRecNum() {
		LOG.debug("Obteniendo el maximo RecNum para cuenta terceros");
		
		String sql = "SELECT MAX(RECNUM)+1 from DC_ACCTHIRD";
		
		String respuesta;
		try {
			respuesta = db2Dao.getJdbcTemplate().queryForObject(sql, String.class);
		} catch (EmptyResultDataAccessException e) {
			LOG.error("No existen registros.");
			return null;
		}
		
		return respuesta.trim();
	}

	@Override
	public boolean agregaCuentaTerceros(ACCTHIRDUSER oCuenta) {
		String sql = "insert into DC_ACCTHIRD " +
				"(ACCTYPE,ENTITYID,ACCNUM,ACCOWNER,NICKNAME,STS,TRANSTYPE,BNKCODE," +
				"BNKNAME,RECNUM,FECHACREACION,CURRENCY,CLAVEACT,intentos,noreenvios) values " +
				"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0) ";
		
		if(oCuenta.getACCOWNER()!=null && oCuenta.getACCOWNER().trim().length()>35){
			oCuenta.setACCOWNER(oCuenta.getACCOWNER().substring(0,34));
		}
		
		if(oCuenta.getNICKNAME()!=null && oCuenta.getNICKNAME().trim().length()>35){
			oCuenta.setNICKNAME(oCuenta.getNICKNAME().substring(0,34));
		}
		
		if(!oCuenta.getTRANSTYPE().equals("05") && !oCuenta.getTRANSTYPE().equals("09") && (oCuenta.getCodigo()==null || oCuenta.getCodigo().equals(""))) 
			oCuenta.setSTS("A");
		else
			oCuenta.setSTS("N");
		
		oCuenta.setRECNUM(new BigDecimal(getSiguienteRecNum()));
		oCuenta.setFECHACREACION(Util.getYYMDHMS());
		
		Object[] args = {
				oCuenta.getACCTYPE(),
				oCuenta.getENTITYID(),
				oCuenta.getACCNUM(),
				oCuenta.getACCOWNER(),
				oCuenta.getACCOWNER(),
				oCuenta.getSTS(),
				oCuenta.getTRANSTYPE(),
				oCuenta.getBNKCODE(),
				oCuenta.getBNKNAME(),
				oCuenta.getRECNUM(),
				oCuenta.getFECHACREACION(),
				oCuenta.getCURRENCY() ,
				oCuenta.getCodigo() 
			};
		
		int resultado=db2Dao.getJdbcTemplate().update(sql, args);
		
		return resultado!=0?true:false;

	}	
	
	@Override
	public boolean activaCuentaTerceros(ACCTHIRDUSER oCuenta) {
		String sql = "UPDATE DC_ACCTHIRD SET sts = ?, fechaactiva = ?, fechacreacion = ?" +
	      	" WHERE entityid=? AND accnum=? AND transtype=? AND recnum=?";
		
		oCuenta.setFECHACREACION(Util.getYYMDHMS());
		oCuenta.setFECHAACTIVA(Util.getYYMDHMS());
		
		Object[] args = {
				oCuenta.getSTS(),
				oCuenta.getFECHAACTIVA(),
				oCuenta.getFECHACREACION(),
				oCuenta.getENTITYID(),
				oCuenta.getACCNUM(),
				oCuenta.getTRANSTYPE(),
				oCuenta.getRECNUM() 
			};
		
		int resultado=db2Dao.getJdbcTemplate().update(sql, args);
		
		return resultado!=0?true:false;

	}	
	
	@Override
	public boolean actualizaEstatus(ACCTHIRDUSER oCuenta) {
		String sql = "UPDATE DC_ACCTHIRD SET sts = ? , fechaactiva = ? " +
	      	" WHERE entityid=? AND accnum=? AND transtype=? AND recnum=?";
		
		Object[] args = {
				oCuenta.getSTS(),
				oCuenta.getFECHAACTIVA(),
				oCuenta.getENTITYID(),
				oCuenta.getACCNUM(),
				oCuenta.getTRANSTYPE(),
				oCuenta.getRECNUM() 
			};
		
		int resultado=db2Dao.getJdbcTemplate().update(sql, args);
		
		return resultado!=0?true:false;

	}	
	
	@Override
	public boolean registraIntentoFallido(ACCTHIRDUSER oCuenta) {
		String sql = "UPDATE DC_ACCTHIRD SET intentos = ? " +
	      	" WHERE entityid=? AND accnum=? AND transtype=? AND recnum=?";
		
		Object[] args = {
				oCuenta.getINTENTOS(),
				oCuenta.getENTITYID(),
				oCuenta.getACCNUM(),
				oCuenta.getTRANSTYPE(),
				oCuenta.getRECNUM() 
			};
		
		int resultado=db2Dao.getJdbcTemplate().update(sql, args);
		
		return resultado!=0?true:false;
	}	

	@Override
	public boolean registraReenvioClave(ACCTHIRDUSER oCuenta) {
		String sql = "UPDATE DC_ACCTHIRD SET noreenvios = ? " +
	      	" WHERE entityid=? AND accnum=? AND transtype=? AND recnum=?";
		
		Object[] args = {
				oCuenta.getNOREENVIOS(),
				oCuenta.getENTITYID(),
				oCuenta.getACCNUM(),
				oCuenta.getTRANSTYPE(),
				oCuenta.getRECNUM() 
			};
		
		int resultado=db2Dao.getJdbcTemplate().update(sql, args);
		
		return resultado!=0?true:false;
	}	

	
	@Override
	public List<ACCTHIRDUSER> consultaCuentas(String contrato, String estado) {
		LOG.info("Obteniendo cuentas de: " + contrato +  " estado: " + estado);
		List<ACCTHIRDUSER> listaCuentas= new ArrayList<ACCTHIRDUSER>();

		String sql = "SELECT A.ACCNUM, A.ACCOWNER, A.NICKNAME, A.STS, A.TRANSTYPE, B.TRANSDESC, A.BNKCODE, A.BNKNAME, " +
					"A.ACCTYPE, A.RECNUM, A.CLAVEACT, A.FECHACREACION, A.FECHAACTIVA, A.INTENTOS, A.NOREENVIOS " +
					"FROM DC_ACCTHIRD A INNER JOIN DC_PROCODE B ON (A.TRANSTYPE = B.TRANSTYPE) " +
					"WHERE A.ENTITYID = ? AND STS= ? order by A.ACCOWNER";

		Object[] args = {contrato, estado};
		
		
		List<Map<String, Object>> usrList = new ArrayList<Map<String, Object>>();
		
		try {
			usrList = db2Dao.getJdbcTemplate().queryForList(sql, args);
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio. " + e.getMessage());
			return null;
		}
		
		if (usrList != null) {
			
			// al convertir la Lista a Set se remueven los duplicados
			Set<Map<String, Object>> usrSet = new HashSet<Map<String, Object>>(usrList);
			
			for (Map<String, Object> map : usrSet) {
				
				ACCTHIRDUSER accThird = new ACCTHIRDUSER();
				accThird.setENTITYID(contrato);
				accThird.setACCNUM(DaoUtil.getString(map.get("ACCNUM")));
				accThird.setACCTYPE(DaoUtil.getString(map.get("ACCTYPE")));
				accThird.setTRANSDESC(DaoUtil.getString(map.get("TRANSDESC")));
				accThird.setTRANSTYPE(DaoUtil.getString(map.get("TRANSTYPE")));
				accThird.setACCOWNER(DaoUtil.getString(map.get("ACCOWNER")));
				accThird.setNICKNAME(DaoUtil.getString(map.get("NICKNAME")));
				accThird.setBNKCODE(DaoUtil.getString(map.get("BNKCODE")));
				accThird.setBNKNAME(DaoUtil.getString(map.get("BNKNAME")));
				accThird.setSTS(DaoUtil.getString(map.get("STS")));
				//accThird.setCodigo(DaoUtil.getString(map.get("CLAVEACT")));
				accThird.setCodigoCorrecto(DaoUtil.getString(map.get("CLAVEACT")));
				accThird.setEMAIL("");
				accThird.setFECHACREACION(DaoUtil.getString(map.get("FECHACREACION")));
				accThird.setRECNUM(DaoUtil.getBigDecimal(map.get("RECNUM")));
				
				accThird.setINTENTOS(DaoUtil.getInt(map.get("INTENTOS")));
				accThird.setNOREENVIOS(DaoUtil.getInt(map.get("NOREENVIOS")));
				
				listaCuentas.add(accThird);
			}
			
		}
		return listaCuentas;
	}

	@Override
	public List<SPABANPF> getListaBancos() {
		LOG.info("Obteniendo lista de bancos para transferencias nacionales");
		List<SPABANPF> listaBancos= new ArrayList<SPABANPF>();

		String sql = "SELECT BANREC,BANNOM,BANLAR,BANBTD,BANBTM,BANBTY,BANCLS,BANSTS,BANFLG  " +
				 	"from " + AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME)+ "SPABANPF " +
				 	"where BANREC <> 62 order by BANNOM";

		List<Map<String, Object>> usrList = new ArrayList<Map<String, Object>>();
		
		try {
			usrList = as400Dao.getJdbcTemplate().queryForList(sql);
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio. " + e.getMessage());
			return null;
		}
		
		if (usrList != null) {
			// al convertir la Lista a Set se remueven los duplicados
			//Set<Map<String, Object>> usrSet = new HashSet<Map<String, Object>>(usrList);
			
			for (Map<String, Object> map : usrList) {
				
				SPABANPF banbean = new SPABANPF();
				
				banbean.setBANREC(DaoUtil.getBigDecimal(map.get("BANREC")));
				banbean.setBANNOM(DaoUtil.getString(map.get("BANNOM")));
				banbean.setBANLAR(DaoUtil.getString(map.get("BANLAR")));
				banbean.setBANBTD(DaoUtil.getBigDecimal(map.get("BANBTD")));
				banbean.setBANBTM(DaoUtil.getBigDecimal(map.get("BANBTM")));
				banbean.setBANBTY(DaoUtil.getBigDecimal(map.get("BANBTY")));
				banbean.setBANCLS(DaoUtil.getString(map.get("BANCLS")));
				banbean.setBANSTS(DaoUtil.getString(map.get("BANSTS")));
				banbean.setBANFLG(DaoUtil.getString(map.get("BANFLG")));

				listaBancos.add(banbean);
			}
			
		}
		return listaBancos;

	}
	
	public boolean getValCelularBanco(String contrato, String celular, String banco) {
		boolean existe = false;
		try {
			String sql = "select * from DC_ACCTHIRD where entityid=? and accnum=? and BNKCODE=? and acctype='10' and sts IN ('A', 'N')";
			Map<String, Object> result = db2Dao.getJdbcTemplate().queryForMap(
					sql, new Object[] { contrato, celular, banco });
			if (result!=null && result.size() > 0) {
				existe = true;
			}
		} catch (Exception ex) {
			LOG.debug("Resultado del query vacio. " + ex.getMessage());
		}

		return existe;
	}	
	
	public boolean getExisteCuenta(String contrato, String cuenta) {
		boolean existe = false;
		try {
			String sql = "select * from DC_ACCTHIRD where entityid=? and accnum=? and sts IN ('A', 'N')";
			Map<String, Object> result = db2Dao.getJdbcTemplate().queryForMap(
					sql, new Object[] { contrato, cuenta });
			if (result!=null && result.size() > 0) {
				existe = true;
			}
		} catch (Exception ex) {
			LOG.debug("Resultado del query vacio. " + ex.getMessage());
		}

		return existe;
	}	
	
	@Override
	public SPABANPF getBanco(BigDecimal numero) {
		LOG.info("Obteniendo el banco para transferencias nacionales");
		List<SPABANPF> listaBancos= new ArrayList<SPABANPF>();

		String sql = "SELECT BANREC,BANNOM,BANLAR,BANBTD,BANBTM,BANBTY,BANCLS,BANSTS,BANFLG  " +
				 	"from " + AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME)+ "SPABANPF " +
				 	"where BANREC = ? order by BANNOM";

		List<Map<String, Object>> usrList = new ArrayList<Map<String, Object>>();
		
		try {
			usrList = as400Dao.getJdbcTemplate().queryForList(sql, new Object[] {numero});
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio. " + e.getMessage());
			return null;
		}
		
		if (usrList != null) {
			// al convertir la Lista a Set se remueven los duplicados
			//Set<Map<String, Object>> usrSet = new HashSet<Map<String, Object>>(usrList);
			
			for (Map<String, Object> map : usrList) {
				
				SPABANPF banbean = new SPABANPF();
				
				banbean.setBANREC(DaoUtil.getBigDecimal(map.get("BANREC")));
				banbean.setBANNOM(DaoUtil.getString(map.get("BANNOM")));
				banbean.setBANLAR(DaoUtil.getString(map.get("BANLAR")));
				banbean.setBANBTD(DaoUtil.getBigDecimal(map.get("BANBTD")));
				banbean.setBANBTM(DaoUtil.getBigDecimal(map.get("BANBTM")));
				banbean.setBANBTY(DaoUtil.getBigDecimal(map.get("BANBTY")));
				banbean.setBANCLS(DaoUtil.getString(map.get("BANCLS")));
				banbean.setBANSTS(DaoUtil.getString(map.get("BANSTS")));
				banbean.setBANFLG(DaoUtil.getString(map.get("BANFLG")));

				listaBancos.add(banbean);
				return banbean;
			}
		}

		return null;

	}
}
