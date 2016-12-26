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
	public List<ACCTHIRDUSER> getListaTerceros(String idContrato, String tipoContrato, String idUsuario,
			String tiempoEsperaCuentas) {
		return null;
	}

	/**
	 * Este metodo es una version alternativa para:<br>
	 * public List<ACCTHIRDUSER> getListaTerceros(String idContrato, String
	 * tipoContrato, String idUsuario, String tiempoEsperaCuentas)<br>
	 * <br>
	 * 
	 * @see com.afirme.afirmenet.dao.transferencia.CuentaTercerosDao#getListaTerceros(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)<br>
	 *      <br>
	 *      Este metodo es para consultar las cuentas terceros, para el modulo
	 *      configuracion > Cuentas destino > Eliminar.<br>
	 *      <br>
	 *      Se hace necesario duplicar este codigo porque no quiero alterar el
	 *      metodo <b>getListaTerceros</b> quitando el SET que utiliza y que
	 *      altera el orden en el view.<br>
	 *      <br>
	 *      Este metodo no altera la lista de resultados. En el metodo:
	 *      <b>getListaTerceros</b> se utiliza un set para quitar los duplicados
	 *      utilizando un SET que no garantiza ordenamiento.<br>
	 *      <br>
	 * @param idContrato
	 * @param tipoContrato
	 * @param idUsuario
	 * @param tiempoEsperaCuentas
	 * @return
	 */
	public List<ACCTHIRDUSER> getListaTercerosNoAlteraLista(String idContrato, String tipoContrato, String idUsuario,
			String tiempoEsperaCuentas) {
		return null;
	}

	@Override
	public ACCTHIRDUSER getCuentaTercero(String idContrato, String cuentaTercero, String tipoContrato, String idUsuario,
			String tiempoEsperaCuentas) {
		return null;
	}

	/**
	 * Para listar los tipos de operaciones en el combo para eliminar cuentas
	 * destino.
	 * 
	 * @param bPaqueteSinToken
	 * @return List<CatalogoBase>
	 * @throws Exception
	 */
	@Override
	public List<CatalogoBase> getProCodeList(Boolean bPaqueteSinToken) throws Exception {
		return null;
	}

	/**
	 * Para eliminar cuenta terceros afirme.<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * 2.- borrar la cuenta terceros de DC_ACCTHIRD<br>
	 * En caso de que falle (2.-) se borra el insert que se hizo en
	 * DC_CONVENIO_LOG<br>
	 * <br>
	 * 
	 * @param contrato
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delAccPersonas(String contrato, ACCTHIRDUSER accthird) throws Exception {
		return (Boolean) null;
	}

	/**
	 * Cuando se elimina una cuenta de terceros afirme, se registra en
	 * DC_CONVENIO_LOG.<br>
	 * <br>
	 * 
	 * @param contrato
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	private boolean addHistorialTerceros(String contrato, ACCTHIRDUSER accthird) throws Exception {
		return (Boolean) null;
	}

	/**
	 * Este metodo es utilizado para rollback. <br>
	 * <br>
	 * Cuando se elimina una cuenta terceros, <br>
	 * se guarda en la tabla DC_CONVENIO_LOG, utilizando el metodo
	 * addHistorialTerceros,<br>
	 * en caso de alguna falla al eliminar la cuenta terceros, se elimina el
	 * registro en DC_CONVENIO_LOG.<br>
	 * 
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	private boolean delHistorialTerceros(ACCTHIRDUSER accthird) throws Exception {
		return (Boolean) null;
	}

	/**
	 * Para eliminar cuenta swift y swift intermediarios.<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * 2.- borrar la cuenta swift o swift intermediario Divisa<br>
	 * En caso de que falle (2.-) se borra el insert que se hizo en
	 * DC_CONVENIO_LOG<br>
	 * <br>
	 * 
	 * @see com.afirme.afirmenet.model.transferencia.Divisa
	 * 
	 * @param cuentaSWIFT
	 *            de tipo Divisa
	 * @return boolean
	 * @throws Exception
	 */
	public boolean borraCuentaSWIFTPersonas(Divisa cuentaSWIFT) throws Exception {
		return (Boolean) null;
	}

	/**
	 * Para agregar al log.<br>
	 * <br>
	 * Podria otimizarse para utilizar un generic. Es el mismo metodo para
	 * guardar terceros log.<br>
	 * <br>
	 * 
	 * @see com.afirme.afirmenet.model.transferencia.Divisa
	 * 
	 * @param contrato
	 * @param cuentaSwift
	 *            de tipo Divisa
	 * @return
	 * @throws Exception
	 */
	private boolean setAddHistorialSWIFT(Divisa cuentaSWIFT) throws Exception {
		return (Boolean) null;
	}

	/**
	 * Este metodo es utilizado para rollback. <br>
	 * Este metodo es el mismo para borrar terceros, buscar forma optimizar.
	 * <br>
	 * Cuando se elimina una cuenta terceros, <br>
	 * se guarda en la tabla DC_CONVENIO_LOG, utilizando el metodo
	 * addHistorialTerceros,<br>
	 * en caso de alguna falla al eliminar la cuenta terceros, se elimina el
	 * registro en DC_CONVENIO_LOG.<br>
	 * 
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	private boolean delHistorialTercerosSWIFT(Divisa cuentaSWIFT) throws Exception {
		return (Boolean) null;
	}

	/**
	 * Obtiene todos los convenios del usuario (Excluyendo Pago Pemex,COMISION
	 * DIA ACTUAL FAX,COMISION MES ACTUAL FAX,COMISION MES ANTERIOR FAX y
	 * servicios basicos) <br>
	 * <br>
	 * Utilizado para consultar las tarjetas de credito para eliminar. <br>
	 * <br>
	 * 
	 * @see com.afirme.afirmenet.dao.transferencia.CuentaTercerosDao#getConvList(java.lang.String,
	 *      java.lang.String)
	 * 
	 * @param numeroContrato
	 *            de tipo String
	 * @param tiempoEsperaCuentas
	 *            de tipo String
	 * @return List&lt;DC_CONVENIO&gt;
	 * @throws Exception
	 */
	@Override
	public List<DC_CONVENIO> getConvList(String numeroContrato, String tiempoEsperaCuentas) throws Exception {
		return null;
	}

	/**
	 * Para eliminar cuentas destino tarjetas credito y seguros afirme<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b> 2.- borrar
	 * la cuenta tarjetas de credito y seguros afirme DC_CONVENIO<br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b> En caso de
	 * que falle (2.-) se borra el insert que se hizo en DC_CONVENIO_LOG<br>
	 * <br>
	 * 
	 * @see com.afirme.afirmenet.ibs.databeans.DC_CONVENIO
	 * @see com.afirme.afirmenet.dao.impl.transferencia.CuentaTercerosDao#CuentaTercerosDaoImpl(java.lang.String,
	 *      com.afirme.afirmenet.ibs.databeans.DC_CONVENIO)
	 * 
	 * @param contrato
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	public boolean borrarConvAdmon(String contrato, DC_CONVENIO convenioUsuario) throws Exception {
		return (Boolean) null;
	}

	/**
	 * Para agregar al log.<br>
	 * <br>
	 * Podria otimizarse para utilizar un generic. Es el mismo metodo para
	 * guardar terceros log.<br>
	 * <br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b> <br>
	 * 
	 * @see
	 * 
	 * @param contrato
	 * @param cuentaSwift
	 *            de tipo Divisa
	 * @return
	 * @throws Exception
	 */
	private boolean setAddHistorialConv(DC_CONVENIO convenioUsuario) throws Exception {
		return (Boolean) null;
	}

	/**
	 * Este metodo es utilizado para rollback. <br>
	 * Este metodo es el mismo para borrar terceros, buscar forma optimizar.
	 * <br>
	 * Cuando se elimina una cuenta terceros, <br>
	 * se guarda en la tabla DC_CONVENIO_LOG, utilizando el metodo
	 * borrarConvAdmon,<br>
	 * en caso de alguna falla al eliminar la cuenta terceros, se elimina el
	 * registro en DC_CONVENIO_LOG.<br>
	 * <br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b>
	 * 
	 * 
	 * @param convenioUsuario
	 *            de tipo DC_CONVENIO
	 * @return boolean
	 * @throws Exception
	 */
	private boolean delHistorialConv(DC_CONVENIO convenioUsuario) throws Exception {
		return (Boolean) null;
	}

	@Override
	public String getSiguienteRecNum() {
		return null;
	}

	@Override
	public boolean agregaCuentaTerceros(ACCTHIRDUSER oCuenta) {
		return (Boolean) null;
	}

	@Override
	public boolean activaCuentaTerceros(ACCTHIRDUSER oCuenta) {
		return (Boolean) null;
	}

	@Override
	public boolean actualizaEstatus(ACCTHIRDUSER oCuenta) {
		return (Boolean) null;
	}

	@Override
	public boolean registraIntentoFallido(ACCTHIRDUSER oCuenta) {
		return (Boolean) null;
	}

	@Override
	public boolean registraReenvioClave(ACCTHIRDUSER oCuenta) {
		return (Boolean) null;
	}

	@Override
	public List<ACCTHIRDUSER> consultaCuentas(String contrato, String estado) {
		return null;
	}

	@Override
	public List<SPABANPF> getListaBancos() {
		return null;
	}

	public boolean getValCelularBanco(String contrato, String celular, String banco) {
		return (Boolean) null;
	}

	public boolean getExisteCuenta(String contrato, String cuenta) {
		return (Boolean) null;
	}

	@Override
	public SPABANPF getBanco(BigDecimal numero) {
		return null;
	}
}
