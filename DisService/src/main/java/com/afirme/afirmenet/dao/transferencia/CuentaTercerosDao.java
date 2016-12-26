package com.afirme.afirmenet.dao.transferencia;

import java.math.BigDecimal;
import java.util.List;

import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.ibs.databeans.SPABANPF;
import com.afirme.afirmenet.model.base.CatalogoBase;
import com.afirme.afirmenet.model.transferencia.Divisa;

public interface CuentaTercerosDao {
	
	// TODO Terminar por migrar resto de metodos de JBAccThird
	
	/**
	 * Metodo DAO para obtencion de lista de Terceros de una cuenta especifica 
	 * Referencia: JBAccThird.getAccThirdListEntity
	 * @param idContrato
	 * @param tipoContrato
	 * @param flag
	 * @return lista de cuentas de terceros
	 */
	//public List<ACCTHIRD> getListaTercerosDeCuenta(String idContrato, String tipoContrato, String flag);
	
	/**
	 * Metodo DAO para obtencion de lista de Terceros y sus usuarios referentes
	 * Referencia: JBAccThird.getAccThirdListUser
	 * @param idContrato
	 * @param tipoContrato
	 * @param idUsuario
	 * @param tiempoEsperaCuentas
	 * @return Lista de usuarios y cuenta de terceros
	 */
	public List<ACCTHIRDUSER> getListaTerceros(String idContrato, String tipoContrato, String idUsuario, String tiempoEsperaCuentas);
	
	/**
	 * Metodo para obtener una cuenta de Tercero especifica
	 * @param idContrato
	 * @param tipoContrato
	 * @param idUsuario
	 * @param tiempoEsperaCuentas
	 * @return
	 */
	public ACCTHIRDUSER getCuentaTercero(String idContrato, String cuentaTercero, String tipoContrato, String idUsuario, String tiempoEsperaCuentas);
	
	/**
	 * Para listar los tipos de operaciones en el combo para eliminar cuentas destino.
	 * 
	 * @param bPaqueteSinToken
	 * @return
	 * @throws Exception
	 */
	public List<CatalogoBase> getProCodeList(Boolean bPaqueteSinToken) throws Exception;
	
	/**
	 * Para borrar cuentas terceros afirme.
	 * 
	 * @param contrato
	 * @param accthird
	 * @return
	 * @throws Exception
	 */
	public boolean delAccPersonas(String contrato, ACCTHIRDUSER accthird) throws Exception;
	
	/**
	 * Para eliminar cuenta swift y swift intermediarios.<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * 2.- borrar la cuenta terceros de DC_ACCSWIFTUSER<br>
	 * En caso de que falle (2.-) se borra el insert que se hizo en DC_CONVENIO_LOG<br>
	 * <br>
	 * @see com.afirme.afirmenet.service.transferencia.CuentaTercerosService#borraCuentaSWIFTPersonas(com.afirme.afirmenet.model.transferencia.Divisa)
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
	 * @param tiempoEsperaCuentas
	 * @return
	 */
	public List<ACCTHIRDUSER> getListaTercerosNoAlteraLista(String idContrato, String tipoContrato, String idUsuario, String tiempoEsperaCuentas);
	
	/**
	 * Método para obtener el siguiente consecutivo de cuenta de terceros
	 * @return
	 */
	public String getSiguienteRecNum();
	
	/**
	 * Metodo para insertar cuenta de terceros en DB2
	 * @param oCuenta  Datos de la cuenta de terceros
	 * @return  
	 */
	public boolean agregaCuentaTerceros(ACCTHIRDUSER oCuenta);

	/**
	 * Metodo para activar una cuenta de terceros
	 * @param oCuenta Datos de la cuenta de terceros
	 * @return
	 */
	public boolean activaCuentaTerceros(ACCTHIRDUSER oCuenta);
	
	/**
	 * Registro intento fallido de clave de activacion
	 * @param oCuenta Datos de la cuenta de terceros
	 * @return
	 */
	public boolean registraIntentoFallido(ACCTHIRDUSER oCuenta); 
		
	/**
	 * Registro reenvio de clave de activacion
	 * @param oCuenta Datos de la cuenta de terceros
	 * @return
	 */
	public boolean registraReenvioClave(ACCTHIRDUSER oCuenta);
	
	/**
	 * Actualiza el estatus de alta de cuenta
	 * @param oCuenta Datos de la cuenta de terceros
	 * @return
	 */
	public boolean actualizaEstatus(ACCTHIRDUSER oCuenta);
	
	/**
	 * Metodo para consultar la lista de cuenta de terceros por estatus
	 * @param contrato Contrato
	 * @param estado Estatus de la lista de cuentas a consutar
	 * @return
	 */
	public List<ACCTHIRDUSER> consultaCuentas(String contrato, String estado)  ;
	
	/**
	 * Obtiene la lista de bancos para nuevas cuentas de transferencias nacionales
	 * @return
	 */
	public List<SPABANPF> getListaBancos();
	
	/**
	 * Valida si el celular ya fue dado de alta como cuenta de terceros para dicho banco
	 * @param contrato El contrato de afirmenet
	 * @param celular Celular a validar
	 * @param banco Banco
	 * @return
	 */
	public boolean getValCelularBanco(String contrato, String celular, String banco);
	
	/**
	 * Valida si la cuenta ya esta dada de alta
	 * @param contrato Contrato de afirmenet
	 * @param cuenta El numero de cuenta a validar
	 * @return
	 */
	public boolean getExisteCuenta(String contrato, String cuenta) ;

	/**
	 * Obtiene el banco de transferencias nacionales con el numero
	 * @return
	 */
	public SPABANPF getBanco(BigDecimal numero);
	
}
