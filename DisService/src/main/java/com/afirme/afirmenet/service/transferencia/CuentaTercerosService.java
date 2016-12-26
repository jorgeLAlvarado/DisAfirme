package com.afirme.afirmenet.service.transferencia;

import java.util.List;

import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.ibs.databeans.SPABANPF;
import com.afirme.afirmenet.model.base.CatalogoBase;
import com.afirme.afirmenet.model.transferencia.AccionCuentaTercero;
import com.afirme.afirmenet.model.transferencia.Divisa;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;

public interface CuentaTercerosService {	
	/**
	 * Servicio para obtener la lista de cuentas de Terceros asociados a una Cuenta
	 * @param idContrato
	 * @param tipoContrato
	 * @param idUsuario
	 * @return
	 */
	public List<ACCTHIRDUSER> getListaTerceros(String idContrato, String tipoContrato, String idUsuario);
	
	/**
	 * Servicio para obtener la informacion de una cuenta de Terceros especifica
	 * @param idContrato
	 * @param cuentaTercero
	 * @param tipoContrato
	 * @param idUsuario
	 * @param tiempoEsperaCuentas
	 * @return
	 */
	public ACCTHIRDUSER getCuentaTercero(String idContrato, String cuentaTercero, String tipoContrato, String idUsuario);
	
	void activaCuentaTercero(ACCTHIRDUSER oBene);
	
	void agregaCuentaTercero(ACCTHIRDUSER oBene);
	
	void eliminaCuentaTercero(ACCTHIRDUSER oBene);
	
	List<ACCTHIRDUSER> consultaBeneficiarios(String contrato, String estado);
	
	List<CatalogoBase> getTipoCuentasValidas(TipoTransferencia tipoTransferencia);
	
	List<ACCTHIRDUSER> getAdminCuentas(String contrato, String idUsuario, AccionCuentaTercero accion, TipoTransferencia tipoTransferencia);
	
	ACCTHIRDUSER seleccionar(String numero, List<ACCTHIRDUSER> cuentas);
	
	/**
	 * Para listar los tipos de operaciones en el combo para eliminar cuentas destino.
	 * 
	 * @param bPaqueteSinToken
	 * @return
	 * @throws Exception
	 */
	public List<CatalogoBase> getProCodeList(Boolean bPaqueteSinToken) throws Exception;

	/**
	 * Para eliminar una cuenta de terceros afirme.
	 * 
	 * @param contrato nuemero contrato del usuario logueado.
	 * @param accthird de tipo ACCTHIRDUSER
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delAccPersonas(String contrato, ACCTHIRDUSER accthird) throws Exception;


	/**
	 * Para obtener la lista swift.
	 * 
	 * @param contrato numero de contrato del usuario logueado.
	 * @param esIntermediario true si es intermediario agrega una condicion mas en el wehere sql.
	 * @return List<Divisa>
	 * @throws Exception
	 */
	public List<Divisa> obtenerListaCuentaSwift(String contrato, boolean esIntermediario) throws Exception;
	
	/**
	 * Para eliminar cuenta swift y swift intermediarios.<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * 2.- borrar la cuenta terceros de DC_ACCSWIFTUSER<br>
	 * En caso de que falle (2.-) se borra el insert que se hizo en DC_CONVENIO_LOG<br>
	 * <br>
	 * @param contrato
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	public boolean borraCuentaSWIFTPersonas(Divisa cuentaSWIFT) throws Exception;
	
	
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
	public List<DC_CONVENIO> getConvList(String numeroContrato, String tiempoEsperaCuentas) throws Exception;
	
	/**
	 * Para eliminar cuentas destino tarjetas credito y seguros afirme<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b><br>
	 * 2.- borrar la cuenta tarjetas de credito y seguros afirme DC_CONVENIO<br>
	 * <b>Esta harcode "Pago Servicios" en campo TIPO_TRANSACCION</b><br>
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
	public boolean borrarConvAdmon(String contrato, DC_CONVENIO convenioUsuario) throws Exception;
	
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
	 * @return List&lt;ACCTHIRDUSER&gt;
	 */
	public List<ACCTHIRDUSER> getListaTercerosNoAlteraLista(String idContrato, String tipoContrato, String idUsuario);
	
	public ACCTHIRDUSER seleccionarPorRecNum(String numero, List<ACCTHIRDUSER> cuentas);		
	
	public boolean validaClaveActivacion (ACCTHIRDUSER oCuenta) ;
	
	public List<ACCTHIRDUSER> consultaCuentas(String contrato, String estado);

	public boolean validaReenvioClave(ACCTHIRDUSER oCuenta);
	
	public List<SPABANPF> getListaBancos();
	
	public boolean getValCelularBanco(String contrato, String celular, String banco);
	
}
