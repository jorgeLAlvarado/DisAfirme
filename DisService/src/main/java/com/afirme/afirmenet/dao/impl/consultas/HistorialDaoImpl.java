package com.afirme.afirmenet.dao.impl.consultas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.dao.consultas.HistorialDao;
import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;

@Repository
public class HistorialDaoImpl implements HistorialDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(HistorialDaoImpl.class);
	
	@Autowired
	private DB2Dao db2Dao;
	
	@Override
	public List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken) {
		String sql="select TIPO, NOMBRE, CATEGORIA from DC_HISTORIAL_TIPOS where Categoria<>'' and persona in (0, 2) order by categoria desc, nombre asc";

		List<Map<String, Object>> listResult;
		
		try {
			
			listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[] {});
		} catch (Exception e) {
			LOG.error("Error en la ejecucion de query: " + e.getMessage());
			return null;
		}
		
		List<TipoTransaccion> result= new ArrayList<TipoTransaccion>();
		
		for (Map<String, Object> map : listResult) {
			TipoTransaccion tt=new TipoTransaccion();
			tt.setTipo(HistorialTipo.findByValue(DaoUtil.getString(map.get("TIPO"))));
			tt.setCategoria(DaoUtil.getString(map.get("CATEGORIA")));
			tt.setNombre(DaoUtil.getString(map.get("NOMBRE")));
			result.add(tt);
		}
		
		return result;
	}

	@Override
	public List<String> categorias(boolean esBasicoSinToken) {
		String sql="select CATEGORIA, count(tipo) from DC_HISTORIAL_TIPOS where Categoria<>'' and persona in (0, 2) group by Categoria order by Categoria desc";

		List<Map<String, Object>> listResult;
		
		try {
			
			listResult = db2Dao.getJdbcTemplate().queryForList(sql, new Object[] {});
		} catch (Exception e) {
			LOG.error("Error en la ejecucion de query: " + e.getMessage());
			return null;
		}
		
		List<String> result= new ArrayList<String>();
		
		for (Map<String, Object> map : listResult) {
			//DaoUtil.getString(row.get("DDVSBR"))
			//DaoUtil.getBigDecimal(row.get("DDVREF"))
			String categoria=DaoUtil.getString(map.get("CATEGORIA"));
			result.add(categoria);
		}
		
		return result;
	}

}
