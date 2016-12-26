package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.transferencia.CashExpressBeneficiarioDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.utils.AfirmeNetConstants;

@Repository
public class CashExpressBeneficiarioDaoImpl implements
		CashExpressBeneficiarioDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(CashExpressBeneficiarioDaoImpl.class);
	
	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);
	
	@Autowired
	private AS400Dao as400Dao;
	
	@Override
	public void activaBeneficiario(ACCTHIRDUSER oBene) {
		Connection cnx = null;
		String resDes="Sin respuesta del servidor";
		BigDecimal res=BigDecimal.ONE;
		oBene.setErrores(null);
		Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
		try{
			cnx = as400Dao.getAs400DataSource().getConnection();
			String sql="Call "+ AS400_LIBRARY + "ACT_ORPBEN(?, ?, ?, ?, ?)";
			CallableStatement   ps = cnx.prepareCall(sql);
			int i = 1;
			ps.setString(i++, oBene.getENTITYID());
			ps.setBigDecimal(i++, new BigDecimal(oBene.getACCNUM()));
			ps.setString(i++, oBene.getCodigo());
			//ps.setBigDecimal(i++, new BigDecimal(1));
			int iPar=i;
	        ps.registerOutParameter (i++, Types.DECIMAL);
	        ps.registerOutParameter (i++, Types.VARCHAR);
	        ps.execute();
	        res=ps.getBigDecimal(iPar++);
	        resDes=ps.getString(iPar++);
	        //oBene.setRespuesta(res);
	        //oBene.setDescripcion(resDes);
			
	        ps.close();
		}catch(Exception ex){
			LOG.error("Exception en CashExpressBeneficiarioDaoImpl.activaBeneficiario() : " + ex.getMessage());
		} finally {
			try {
		        if(res.intValue()!=0){
		        	errors.put(CodigoExcepcion.ERR_3000, "["+res+"] "+ resDes);
		        	oBene.setErrores(errors);
		        }
				if (cnx!=null){
				cnx.close();
				}
			} catch (SQLException e) {
				LOG.error("Exception al cerrar conexion en CashExpressBeneficiarioDaoImpl.activaBeneficiario() : " + e.getMessage());
			}
		}

	}

	@Override
	public void agregaEliminaBeneficiario(ACCTHIRDUSER oBene, BigDecimal accion) {
		Connection cnx = null;
		String resDes="Sin respuesta del servidor";
		Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
		BigDecimal res=BigDecimal.ONE;
		oBene.setErrores(null);
		try{
			cnx = as400Dao.getAs400DataSource().getConnection();
			String sql="Call "+ AS400_LIBRARY + "RB_ORPBEN(?, ?, ?, ?, ?, ?, ?, ?)";
			CallableStatement   ps = cnx.prepareCall(sql);
			int i = 1;
			ps.setString(i++, oBene.getENTITYID());
			ps.setString(i++, oBene.getACCOWNER());
			ps.setBigDecimal(i++, new BigDecimal(oBene.getACCNUM()));
			ps.setString(i++, oBene.getEMAIL());
			ps.setString(i++, oBene.getCodigo());
			ps.setBigDecimal(i++, accion);
			int iPar=i;
	        ps.registerOutParameter (i++, Types.DECIMAL);
	        ps.registerOutParameter (i++, Types.VARCHAR);
	        ps.execute();
	        res=ps.getBigDecimal(iPar++);
	        resDes=ps.getString(iPar++);
	      //oBene.setRespuesta(res);
	        //oBene.setDescripcion(resDes);
	        ps.close();
		}catch(Exception ex){
			LOG.error("Exception en CashExpressBeneficiarioDaoImpl.agregaEliminaBeneficiario() : " + ex.getMessage());
		} finally {
			try {
		        if(res.intValue()!=0){
		        	errors.put(CodigoExcepcion.ERR_3000, "["+res+"] "+ (resDes.trim().equals("Registro Invalido")?"El beneficiario ya fue registrado anteriormente.":resDes));
		        	oBene.setErrores(errors);
		        }
				if (cnx!=null){
				cnx.close();
				}
			} catch (SQLException e) {
				LOG.error("Exception al cerrar conexion en CashExpressBeneficiarioDaoImpl.agregaEliminaBeneficiario() : " + e.getMessage());
			}
		}

	}


	@Override
	public List<ACCTHIRDUSER> consultaBeneficiarios(String contrato,
			String estado, String tiempoEsperaCuentas) {
		LOG.info("Obteniendo beneficiarios de: " + contrato +  ", tiempoEsperaCuentas: " + tiempoEsperaCuentas + ", estado: " + estado);
		List<ACCTHIRDUSER> listaBene= new ArrayList<ACCTHIRDUSER>();
		String sql = "Call "+ AS400_LIBRARY + "OP_CO2('"+contrato+"', '" + estado + "')";
		List<Object> args = new ArrayList<Object>();
		
		
		List<Map<String, Object>> usrList = new ArrayList<Map<String, Object>>();
		
		try {
			usrList = as400Dao.getJdbcTemplate().queryForList(sql, args.toArray());
		} catch (EmptyResultDataAccessException e) {
			LOG.debug("Resultado del query vacio. " + e.getMessage());
			return null;
		}
		
		if (usrList != null) {
			
			GregorianCalendar fechaValidacion = new GregorianCalendar();
			fechaValidacion.add(Calendar.SECOND, 1 + Integer.parseInt(tiempoEsperaCuentas) * -1);
			
			//Set<ACCTHIRDUSER> setAccThirdUser = null;
			
			//setAccThirdUser = new HashSet<ACCTHIRDUSER>();
			// al convertir la Lista a Set se remueven los duplicados
			Set<Map<String, Object>> usrSet = new HashSet<Map<String, Object>>(usrList);
			
			for (Map<String, Object> map : usrSet) {
				
				ACCTHIRDUSER accThird = new ACCTHIRDUSER();
				
				
				
				accThird.setENTITYID(DaoUtil.getString(map.get("BENCONC")));
				accThird.setACCNUM(DaoUtil.getBigDecimal(map.get("BENCELC")).toString());
				accThird.setACCTYPE("10");
				accThird.setTRANSDESC("Celular");
				accThird.setTRANSTYPE(TipoTransferencia.ORDENES_DE_PAGO_O_ENVIO_DE_DINERO.getValor());
				accThird.setACCOWNER(DaoUtil.getString(map.get("BENNOMC")));
				accThird.setNICKNAME(DaoUtil.getString(map.get("BENNOMC")));
				accThird.setSTS(DaoUtil.getString(map.get("BENSTSC")));
				accThird.setEMAIL(DaoUtil.getString(map.get("BENMAIC")));
				String fechaCreacion = DaoUtil.getBigDecimal(map.get("BENFALC")).toString();
				String hora=DaoUtil.getBigDecimal(map.get("BENHALC")).toString();
				fechaCreacion+=(hora.length()<6?"0":"")+hora;
				
				if("A".equalsIgnoreCase(accThird.getSTS())){
					//System.out.println("------"+celular+"------");
					BigDecimal fechaCompleta=new BigDecimal(fechaCreacion);
					//System.out.println("Fecha completa: "+fechaCompleta);
					int minutos=Integer.parseInt(tiempoEsperaCuentas)/60;
					//System.out.println("minutos: "+minutos);
					BigDecimal fechaAltaMin=Util.sumarMinutosFecha(fechaCompleta.toString(), minutos);
					//System.out.println("Fecha Alta min: "+fechaAltaMin);
					BigDecimal fechaActual=new BigDecimal(Util.getYYMDHMS());
					//System.out.println("Fecha Actual: "+fechaActual);
					if(fechaAltaMin.compareTo(fechaActual)>0){
						//estadoInterno=0;
						//Si esta buscando los activos le ponemos c para que no lo muestre
						accThird.setSTS("E");
						if("A".equalsIgnoreCase(estado))
							accThird.setSTS("C");
					}
					//System.out.println("------"+"fin"+"------");
				}
				
				if(!"C".equals(accThird.getSTS()))
					listaBene.add(accThird);
				
				
				
				
			}
			
		}
		return listaBene;
	}

}
