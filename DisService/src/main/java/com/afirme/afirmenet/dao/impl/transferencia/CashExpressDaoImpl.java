package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.AS400Dao;
import com.afirme.afirmenet.dao.transferencia.CashExpressDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpress;
import com.afirme.afirmenet.model.transferencia.cashExpress.CashExpressParametro;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
@Service
public class CashExpressDaoImpl implements CashExpressDao {

	@Autowired
	private AS400Dao as400Dao;
	
	final private String AS400_LIBRARY = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.JDBC_LIBNAME);

	@Override
	public List<CashExpress> buscarCashExpress(String contrato,
			String fechaDesde, String fechaHasta, String cuenta, String estado) {
		String sql = "Call "+AS400_LIBRARY+"OP_HCO2(?, ?, ?, ?, ?)";
	       List<Map<String, Object>> listResult;

			try {
				listResult = as400Dao.getJdbcTemplate().queryForList(sql,
						new Object[] {contrato, estado, fechaDesde, fechaHasta, cuenta });
			} catch (EmptyResultDataAccessException e) {
				return null;
			}

			List<CashExpress> mapReferences = new ArrayList<CashExpress>();

			for (Map<String, Object> map : listResult) {
				
				CashExpress oOrden=new CashExpress();
					oOrden.setContractId(DaoUtil.getString(map.get("OPCON")));
					oOrden.setDebitAccount(DaoUtil.getBigDecimal(map.get("OPCTA")).toString());
					oOrden.setClientId(DaoUtil.getBigDecimal(map.get("OPCUN")).toString());
					Cuenta origen= new Cuenta();
					origen.setNumber(DaoUtil.getBigDecimal(map.get("OPCTA")).toString());
					origen.setNickname(DaoUtil.getString(map.get("OPEMI")));
					oOrden.setOrigen(origen);
					oOrden.setOrden(DaoUtil.getString(map.get("OPORD")));
					oOrden.setBeneficiaryName(DaoUtil.getString(map.get("OPBEN")));
					oOrden.setCreditAccount(DaoUtil.getBigDecimal(map.get("OPCEL")).toString());
					oOrden.setAmount(DaoUtil.getBigDecimal(map.get("OPIMP")));
					oOrden.setProgrammingDate(DaoUtil.getBigDecimal(map.get("OPFEM")).toString());
					oOrden.setProgrammingHour(DaoUtil.getBigDecimal(map.get("OPHEM")).toString());
					
					oOrden.setState(DaoUtil.getString(map.get("OPEST")));
					oOrden.setDescription(DaoUtil.getString(map.get("OPINS")));
					oOrden.setEstadoCajero(DaoUtil.getString(map.get("OPSTS")));
					oOrden.setFechaPago(DaoUtil.getBigDecimal(map.get("OPFCO")));
					//Seteamos la fecha y hora para los jsp
					oOrden.setValidationDateFull(oOrden.getProgrammingDate());
					oOrden.setValidationHourFull(oOrden.getProgrammingHour());
					try{
						String lugar="";
						if(DaoUtil.getString(map.get("OPSUC")).trim().length()>0)
							lugar+="Sucursal "+DaoUtil.getString(map.get("OPSUC")).trim();
						if(DaoUtil.getString(map.get("OPATM")).trim().length()>0)
							lugar+="Cajero "+DaoUtil.getString(map.get("OPATM")).trim();
						oOrden.setLugarPago(lugar);
					}catch(Exception ex){}
					try{
						oOrden.setReferenceNumber(DaoUtil.getString(map.get("OPREF")).trim());
					}catch(Exception ex){}
					mapReferences.add(oOrden);
			}
			return mapReferences;
	}

	@Override
	public void datosExtraInotr(CashExpress oOrden) {
		try {
			String sql = "select INOCOM, INOIVA from "+ AS400_LIBRARY + "inotr where inoref=?";
			Map<String, Object> result = as400Dao.getJdbcTemplate().queryForMap(
					sql, new Object[] { new BigDecimal(oOrden.getReferenceNumber()) });
			if (result.size() > 0) {
				oOrden.setCommision(DaoUtil.getBigDecimal(result.get("INOCOM")));
				oOrden.setIva(DaoUtil.getBigDecimal(result.get("INOIVA")));
			}
		} catch (EmptyResultDataAccessException ex) {}
		
	}

	@Override
	public CashExpressParametro obtenerParametro() {
		CashExpressParametro parametro=null;
		try {
			String sql = "select PARLMP as MINIMO, PARLOP as MAXIMO, PARLOD as DIARIO, PARLOM as MENSUAL, PARDVE as DIAS  from "+ AS400_LIBRARY  + "odpparpf ";
			
			Map<String, Object> result = as400Dao.getJdbcTemplate().queryForMap(
					sql, new Object[] {  });
			if (result.size() > 0) {
				parametro= new CashExpressParametro();
				parametro.setDias(DaoUtil.getBigDecimal(result.get("DIAS")));
				parametro.setMinimo(DaoUtil.getBigDecimal(result.get("MINIMO")));
				parametro.setMaximo(DaoUtil.getBigDecimal(result.get("MAXIMO")));
				parametro.setDiario(DaoUtil.getBigDecimal(result.get("DIARIO")));
				parametro.setMensual(DaoUtil.getBigDecimal(result.get("MENSUAL")));
			}
		} catch (EmptyResultDataAccessException ex) {}
		return parametro;
	}
}
