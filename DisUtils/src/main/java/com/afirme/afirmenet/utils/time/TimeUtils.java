package com.afirme.afirmenet.utils.time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import com.afirme.afirmenet.utils.AfirmeNetLog;


/**
 * Clase utilireia para manejar todo lo relacionado con tiempo y fecha
 * 
 * @author jorge.canoc@gmail.com
 * 
 */
public class TimeUtils {
	static final AfirmeNetLog LOG = new AfirmeNetLog(TimeUtils.class);
	public static final String COMPLETE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String SLASH_DATE_FORMAT = "dd/MM/yyyy";
	public static final String CUSTOM_COMPLETE_DATE_FORMAT = "dd-MMM-yy',' HH:mm 'hrs.'";
	public static final String CUSTOM_COMPROBANTE_DATE_FORMAT = "dd/MM/yyyy',' HH:mm 'hrs.'";
	public static final SimpleDateFormat completeDateFormat = new SimpleDateFormat(
			COMPLETE_DATE_FORMAT);
	public static final SimpleDateFormat customnCompleteDateFormat = new SimpleDateFormat(
			CUSTOM_COMPLETE_DATE_FORMAT);
	public static final String DB2_DATE_FORMAT = "yyyyMMdd";
	public static final SimpleDateFormat db2DateFormat = new SimpleDateFormat(
			DB2_DATE_FORMAT);
	public static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
			DEFAULT_DATE_FORMAT);
	/**
	 * Este formato es para las fechas de 400
	 */
	public static final String AS400_DATE_FORMAT = "yyMMddHHmmss";

	
	public static String getDateFormat(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * Para obtener Calendar a partir de una fecha.<br>
	 * <br>
	 * Utilizado para aviso de viaje.
	 * <br>
	 * Este metodo utiliza getDate(String value, SimpleDateFormat simpleDateFormat) para obtener un Date.<br>
	 * <br>
	 * objetivo:<br>
	 * 1.- El usuario en view captura 14/Octubre/2015<br>
	 * 2.- Controller obtiene la fecha TimeUtils.getCalendar( avisoViajeDTO.getFechaInicioViajeddMMMMyyyy(), "dd/MMMM/yyyy");<br>
	 * 3.- Se obtiene el formato para pantalla confirmar: 01/02/2015 para el caso 02/febrero/2015 utilizando getCalendarDDMMYY(Calendar calendar)<br>
	 * <br>
	 * @param value ejemplo: 14/Octubre/2015
	 * @param patternSimpleDateFormat es un patron para SimpleDateFormat, por ejemplo "dd/MMMM/yyyy"
	 * @return Calendar de la fecha parametro.
	 */
	public static Calendar getCalendar(String value, String patternSimpleDateFormat) {
		
		Date date = null;
		date = getDate( value, patternSimpleDateFormat);
				
		Calendar cal=Calendar.getInstance();
		if (value == null) {
			return null;
		}
		
		try{
			SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(patternSimpleDateFormat);
			simpleDateFormat.format(date);
			cal = simpleDateFormat.getCalendar();
			
		} catch ( Exception e) {
			LOG.debug("Error al formatear la fecha " + value);
			LOG.error("Error al formatear la fecha " + value, e);
		}
		
		return cal;
	}
	
	/**
	 * Retorna un objeto de tipo fecha dado un valor string y un formateador
	 * para la fecha {@link SimpleDateFormat}, previene la excepcion
	 * {@link ParseException}
	 * 
	 * @param value
	 * @param simpleDateFormat
	 * @return
	 */
	public static Date getDate(String value, SimpleDateFormat simpleDateFormat) {
		Date date = null;
		if (value == null) {
			return null;
		}
		try {
			date = simpleDateFormat.parse(value);

		} catch (ParseException e) {
			LOG.debug("Error al formatear la fecha " + value);
			LOG.error("Error al formatear la fecha " + value, e);
		}
		return date;
	}

	/**
	 * Retorna un objeto de tipo fecha dado un valor en string y un patron de
	 * fecha, previene la excepcion {@link ParseException}
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static Date getDate(String value, String pattern) {
		return getDate(value, new SimpleDateFormat(pattern));
	}

	/**
	 * Retorna un objeto de tipo fecha dado un valor en string, previene la
	 * excepcion {@link ParseException}
	 * 
	 * @param value
	 * @return
	 */
	public static Date getDate(String value) {
		return getDate(value, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Metodo que retorna el año del calendario en curso en 2 digitos, este
	 * metodo es invocado desde la validacion de fechas para las transferencias
	 * 
	 * 
	 * @return el año en curso
	 */
	public static String getYY() {
		Calendar calendar = Calendar.getInstance();
		String year = "" + calendar.get(Calendar.YEAR);
		year = year.substring(2);
		return (year);
	}

	/**
	 * Metodo que compara una fecha segmentada contra la actual, este metodo es
	 * invocado desde la validacion de fechas para las transferencias
	 * 
	 * @param YY
	 * @param DD
	 * @param MM
	 * @return
	 */
	public static boolean compareYYMMDD(String YY, String DD, String MM) {

		boolean rerror = false;

		int VYY = Integer.parseInt(YY);
		int VMM = Integer.parseInt(MM);
		int VDD = Integer.parseInt(DD);

		int CYY = Integer.parseInt(getYY());
		int CMM = Integer.parseInt(getMM());
		int CDD = Integer.parseInt(getDD());

		if (CYY > VYY) {
			rerror = true;
		}
		if (CMM > VMM && (CYY > VYY || CYY == VYY)) {
			rerror = true;
		}
		if (CDD > VDD && (CMM > VMM || CMM == VMM) && (CYY > VYY || CYY == VYY)) {
			rerror = true;
		}

		if (VDD == CDD && VMM == CMM && CYY == VYY) {
			rerror = false;
		}

		return (rerror);
	}

	/**
	 * Este metodo es una variacion del metodo compareYYMMDD.<br>
	 * <br>
	 * Este metodo valida que la fecha parametro debe ser mayor a la fecha actual.<br>
	 * <br>
	 * Es utilizada para validar el ingreso de un aviso de viaje, en donde la fecha de inicio de viaje debe<br>
	 * ser mayo a la fecha actual.<br>
	 * <br>
	 * Envio la fecha preparada con el formato para la pantalla de confirmacion y comprobante: "03/02/15".<br>
	 * El metodo TimeUtils.getCalendarDDMMYY devuelve "03/02/15" -el mes le agrega 0 a la izquierda- pero igual funciona correctamente.<br>
	 * <br>
	 * @author epifanio.guzman@afirme.com
	 * @param fechaddMyy ejemplo "03/02/15" a 3 de febrero de 2015.
	 * @return boolean
	 */
	public static boolean validaFechaDebeSerMayorAFechaActual( String fechaddMyy ){
		boolean resultado = false;
		
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yy");
			Date date1 = sdf.parse(fechaddMyy);
			Date today = new Date();
			
			if( date1.after(today) == true ){
				resultado = true;
            }
			
		} catch (ParseException e) {
			try {
				throw new Exception("Error al validar la fecha es mayor a fecha actual.", e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return resultado;
		
	}
	
	/**
	 * Este metodo es una variacion del metodo compareYYMMDD.<br>
	 * <br>
	 * Este metodo valida que la fecha 1 parametro debe ser mayor o igual a la fecha 2.<br>
	 * <br>
	 * Es utilizada para validar el ingreso de un aviso de viaje, en donde la fecha de inicio de viaje debe<br>
	 * ser menor que la fecha de fin de viaje.<br>
	 * <br>
	 * Envio la fecha preparada con el formato para la pantalla de confirmacion y comprobante: "03/02/15".<br>
	 * El metodo TimeUtils.getCalendarDDMMYY devuelve "03/02/15" -el mes le agrega 0 a la izquierda- pero igual funciona correctamente.<br>
	 * <br>
	 * @author epifanio.guzman@afirme.com
	 * @param fecha1ddMyy ejemplo "03/02/15"
	 * @param fecha2ddMyy ejemplo "08/02/15"
	 * @return boolean
	 */
	public static boolean validaFecha1DebeSerMayorOIGualAFecha2( String fecha1ddMyy, String fecha2ddMyy ){
		boolean resultado = false;
		
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yy");
			Date date1 = sdf.parse(fecha1ddMyy);
			Date date2 = sdf.parse(fecha2ddMyy);
			
			if(date1.before(date2)){ // Fecha 1 debe ser antes que fecha 2
				resultado = true;
            }
			
			
			if(date1.equals(date2)){ // si es el mismo dia
				resultado = true;
			}
			
		} catch (ParseException e) {
			try {
				throw new Exception("Error al validar la fecha es mayor a fecha actual.", e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return resultado;
		
	}
	
	/**
	 * Metodo que retorna el dia del calendario en curso en 2 digitos, este
	 * metodo es invocado desde la validacion de fechas para las transferencias
	 * 
	 * 
	 * @return el año en curso
	 */
	public static String getDD() {

		Calendar calendar = Calendar.getInstance();
		String day = "" + calendar.get(Calendar.DAY_OF_MONTH);

		day = day.length() == 1 ? "0" + day : day;

		return (day);

	}

	/**
	 * Metodo que retorna el mes del calendario en curso en 2 digitos, este
	 * metodo es invocado desde la validacion de fechas para las transferencias
	 * 
	 * 
	 * @return el año en curso
	 */
	public static String getMM() {

		Calendar calendar = Calendar.getInstance();
		String month = "" + (calendar.get(Calendar.MONTH) + 1);

		month = month.length() == 1 ? "0" + month : month;

		return (month);

	}

	/**
	 * Metodo que retorna el minuto en curso
	 * 
	 * @return
	 */
	public static String getSS() {

		Calendar calendar = Calendar.getInstance();
		String minute = "" + calendar.get(Calendar.MINUTE);

		minute = minute.length() == 1 ? "0" + minute : minute;

		return (minute);

	}

	/**
	 * Metodo que retorna la hora en curso
	 * 
	 * @return
	 */
	public static String getHH() {

		Calendar calendar = Calendar.getInstance();
		String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);

		hour = hour.length() == 1 ? "0" + hour : hour;

		return (hour);

	}

	/**
	 * Metodo que retorna el dia de la semana dada una fecha
	 * 
	 * @param m
	 *            mes
	 * @param d
	 *            dia
	 * @param y
	 *            año
	 * @return
	 */
	public static String getDayofWeek(int m, int d, int y) {
		// return an int for any given date (0 for Sunday, ..., 6 for Saturday)

		m -= 2;
		if (m < 1) {
			m += 12;
			y--;
		}
		int cc = y / 100;
		int yy = y % 100;
		int dow = (d + (int) Math.floor(2.6 * m - 0.2) - 2 * cc + yy + yy / 4 + cc / 4) % 7;
		if (dow < 0)
			dow += 7;

		return String.valueOf(dow);
	}

	/**
	 * Metodo que compra una fecha contra la fecha actual
	 * 
	 * @param YY
	 * @param DD
	 * @param MM
	 * @param HH
	 * @param SS
	 * @return
	 */
	public static boolean compareDate(String YY, String DD, String MM,
			String HH, String SS) {

		boolean rerror = false;

		int VYY = Integer.parseInt(YY);
		int VMM = Integer.parseInt(MM);
		int VDD = Integer.parseInt(DD);
		int VHH = Integer.parseInt(HH);
		int VSS = Integer.parseInt(SS);
		int CYY = Integer.parseInt(getYY());
		int CMM = Integer.parseInt(getMM());
		int CDD = Integer.parseInt(getDD());
		int CHH = Integer.parseInt(getHH());
		int CSS = Integer.parseInt(getSS());

		if ((HH.equals("99") && SS.equals("99"))
				|| (HH.equals("00") && SS.equals("00"))) {
			VHH = Integer.parseInt(HH);
			VSS = Integer.parseInt(SS);
			CHH = Integer.parseInt(HH);
			CSS = Integer.parseInt(SS);
		}

		if (CYY > VYY) {
			rerror = true;
		}
		if (CMM > VMM && (CYY > VYY || CYY == VYY)) {
			rerror = true;
		}
		if (CDD > VDD && (CMM > VMM || CMM == VMM) && (CYY > VYY || CYY == VYY)) {
			rerror = true;
		}

		Calendar CDate = new GregorianCalendar(CMM, CDD, CYY);
		Calendar VDate = new GregorianCalendar(VMM, VDD, VYY);

		int VTime = VHH * 100 + VSS;
		int CTime = CHH * 100 + CSS;

		if ((HH.equals("99") && SS.equals("99"))
				|| (HH.equals("00") && SS.equals("00"))) {
			VTime = VHH * 100 + VSS;
			CTime = CHH * 100 + CSS;
		}

		boolean beforeerror = VDate.before(CDate);
		if (beforeerror) {
			// rerror = true;
		}

		boolean equalerror = false;

		// equalerror = VDate.equals(CDate);

		if (VDD == CDD && VMM == CMM && CYY == VYY) {
			equalerror = true;
		}

		if (VTime != 0) {

			if (equalerror && CTime > VTime) {
				rerror = true;
			}
		}

		return (rerror);
	}

	/**
	 * Metodo que retorna la fecha actual
	 * 
	 * @return
	 */
	public static Date getDate() {
		Calendar calendar = Calendar.getInstance();
		return (calendar.getTime());

	}
	
	/**
	 * Metodo que retorna un Date con parametro Calendar
	 * 
	 * @return Date
	 */
	public static Date getDate( Calendar calendar ) {
		return (calendar.getTime());
	}
	
	/**
	 * Para obtener un a partir de un Calendar.
	 * 
	 * @param calendar
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getTimestamp( Calendar calendar ) {
		Timestamp ts = new Timestamp( calendar.getTimeInMillis() );
		return ts;
	}
	
	/**
	 * Para obtener un Timestamp a partir de una cadena y un formato.
	 * 
	 * Por ejemplo fecha: "20151130" con formato: "yyyyMMdd"
	 * 
	 * @param value
	 * @param pattern
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getTimestamp(String value, String pattern) {
		Date date = null;
		Timestamp ts = null;
		if (value == null) {
			return null;
		}
		try {
			date = getDate(value, new SimpleDateFormat(pattern)); // simpleDateFormat.parse(value);
			ts = new java.sql.Timestamp( date.getTime() );
		} catch ( Exception e) {
			LOG.debug("Error en getTimestamp " + value);
			LOG.error("Error en getTimestamp " + value, e);
		}
		
		return ts;
	}
	
	/**
	 * Metodo que retorna la fecha actual
	 * 
	 * @return
	 */
	public static String getDateFormat(String pattern) {
		Calendar calendar = Calendar.getInstance();
		return getDateFormat(calendar.getTime(), pattern);

	}
	
	/**
	 * Para retornar la fecha de la forma 08/09/15
	 * 
	 * @param calendar de tipo Calendar
	 * @return String
	 */
	public static String getCalendarDDMMYY(Calendar calendar) {
        // Calendar calendar = Calendar.getInstance();
        String year = "" + calendar.get(Calendar.YEAR);
        String month = "" + (calendar.get(Calendar.MONTH) + 1);
        String day = "" + calendar.get(Calendar.DAY_OF_MONTH);

        year = year.substring(2);
        month = month.length() == 1 ? "0" + month : month;
        day = day.length() == 1 ? "0" + day : day;

        return (day + "/" + month + "/" + year);
	}
	
	/**
	 * Retorna la hora de la forma 20:03
	 * 
	 * @param calendar de tipo Calendar
	 * @return String
	 */
	public static String getCalendarHHSS(Calendar calendar) {

        // Calendar calendar = Calendar.getInstance();
        String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);
        hour = hour.length() == 1 ? "0" + hour : hour;

        String minute = "" + calendar.get(Calendar.MINUTE);
        minute = minute.length() == 1 ? "0" + minute : minute;
        
        return (hour + ":" + minute);

	}
	
	public static String traduceFecha(String fecha, String patternFrom, String patternTo){
		Calendar cal=TimeUtils.getCalendar(fecha, patternFrom);
		return TimeUtils.getDateFormat(cal.getTime(), patternTo);
	}
	
	
	
	// Suma los días recibidos a la fecha  
	
	 public static Date sumarRestarDiasFecha(Date fecha, int dias){
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); // Configuramos la fecha que se recibe
	      calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
	      return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	 }
}