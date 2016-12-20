package com.afirme.afirmenet.daoUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.afirme.afirmenet.utils.AfirmeNetLog;


public class DaoUtil {
	
	static final AfirmeNetLog LOG = new AfirmeNetLog(DaoUtil.class);
	
	public DaoUtil() {
		super();
	}

	public static String getString(Object campo){
		
		if(campo!=null){
			String aux=String.valueOf(campo);
			return aux.trim();
		}				
		
		return "";
		
	}
	public static BigDecimal getBigDecimalEspecial(Object campo){
		
		if(campo!=null){
			String aux=(String) campo;
			aux=aux.trim();
			aux=aux.replaceAll("\\,", "");
			if(aux.contains(" ")){
				aux=aux.substring(0, aux.indexOf(" "));
			}
			return new BigDecimal(aux.trim());
		}				
		
		return null;
		
	}
	
	public static BigDecimal getBigDecimal(Object campo){
		
		if(campo!=null){
			return (BigDecimal) campo;
		}				
		
		return null;		
	}
	
	public static Integer getInt(Object campo){
		
		if(campo!=null){
			return (Integer) campo;
		}				
		
		return 0;		
	}
	
	public static Double getDouble(Object campo){
		
		if(campo!=null){
			return Double.parseDouble( campo.toString().trim() );
		}				
		
		return 0.0;		
	}
	
	public static Date getDate(Object campo){
		final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
		if(campo!=null){
			Date aux = null;
			try {
				aux = formatoFecha.parse(campo.toString());
			} catch (ParseException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			return aux;
		}				
		
		return null;
		
	}
	
	public static Date getDate(Object campo, String format){
		final SimpleDateFormat formatoFecha = new SimpleDateFormat(format);
		if(campo!=null){
			Date aux = null;
			try {
				aux = formatoFecha.parse(campo.toString());
			} catch (ParseException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			return aux;
		}				
		
		return null;
		
	}
	
	public static <T> T getValor(Object campo,
			Class<T> type) {
		LOG.debug("Obteniendo valor del campo: " + campo + " de tipo: " + type.getName());
		try {			
				if (!campo.equals(null)) {					
					return type.cast(campo);
				}			
		} catch (Exception e) {
			LOG.error("Error al tratar de obtener el valor del campo: "
					+ campo + " de tipo: " + type.getName());
		}
		return null;

	}


}
