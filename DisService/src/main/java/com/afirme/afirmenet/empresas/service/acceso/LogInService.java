package com.afirme.afirmenet.empresas.service.acceso;

import java.util.List;

import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.ibs.beans.JBAvatar;
import com.afirme.afirmenet.ibs.beans.JBBMuser;
import com.afirme.afirmenet.ibs.beans.JBLogin;
import com.afirme.afirmenet.ibs.beans.JBProCode;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.databeans.DC_CONFMSG;
import com.afirme.afirmenet.model.configuraciones.ContrasenaDTO;

/**
 * clase para dar la validacion de fin de dia
 * 
 * @author Bayron Gamboa Martinez
 *	@since 13/12/2016
 *
 * @version 1.0.2
 */
public interface LogInService {

	  /**
     * Valida si es el primer login con alias
     * */
	public boolean verificarPrimerLoginConAlias(String contrato);
	
    /**
     * Actualiza si es el primer login con alias
     * */
	public boolean actualizarPrimerLoginConAlias(String contrato);
	
    /**
     * Actualiza si tiene INOUT
     * */
	public boolean verificaINOUT(String contrato);
	
    /**
     * Actualiza INOUT
     * */
	public boolean updateINOUT(String UserIdJSP, String Estatus);
	
    /**
     * Consulta IP
     * */
	public String consultaIP(String contrato,String usuario);
	
    /**
     * Inserta IP
     * */
	public boolean insertaIP(String contrato,String usuario,String ip);
	
    /**
     * Actualiza IP
     * */
	public boolean actualizaIP(String contrato,String usuario,String ip,String estatus);
	
    /**
     * Verifica si esta activo en banca movil
     * */
	public JBBMuser verificaBMUser(String contrato);
	
    /**
     * Actualiza BMUser
     * */
	public boolean updateBMUser(String contrato,String estatus);
	
    /**
     * Actualiza BMUser
     * */
	public boolean updateBMUser(String contrato);
	
	
	public JBLogin getUserLogin(String UserIdJSP,String UserPassJSP) throws AfirmeNetException;
	
	  /**
	    * Método que busca si hay cambios en las transacciones o bien si existe una nueva transacción y/o pago de servicio para que se le agregue a la parametrización del usuario. Si el usuario no tiene cargado la parametrización, el método la carga con valores por default.
	    * @param userIdJSP El contrato del usuario.
	    * @param groupId El grupo o paquete al que pertenece el usuario.
	    * @param session La sessión del usuario.
	    * @author vmpermad
	 * @throws Exception 
	    */
	public void defaultSetting(String userIdJSP, String groupId, List<Cuenta> cuentas, List<JBProCode> procode);


	/**
	 * Obtiene el listado de las transacciones
	 * @param bPaqueteSinToken
	 * @return
	 */
	public List<JBProCode> getProcode(boolean bPaqueteSinToken);

	  /**
	    * Busca si hay cambios en los límites de transferencias, si es así se valida que ya se haya cumplido el tiempo de espera para actualizarlos en caso contrario se mantienen hasta que el tiempo de espera se haya cumplido.
	    * @param userIdJSP El contrato del usuario.
	    */
	public void lookupChangeParameter(String contrato);
	
	
	/**
	 * Obtiene el listado de los avatar a alegir
	 * @return
	 */
	public List<JBAvatar> getListAvatar();
	
	/**
	 * Para actualizar la contrasena.
	 * 
	 * @param contrato
	 * @param contrasenaActual
	 * @param contrasenaNueva
	 * @return
	 * @throws AfirmeNetException
	 */
	public ContrasenaDTO actualizaContrasena(ContrasenaDTO contrasenaDTO) throws AfirmeNetException;
	
	/**
	 * Metodo para guardar el registro en la tabla DC_CONFMSG.
	 * 
	 * Cuando se cambia la contrasenia:
	 * 1.- Se utiliza el socket PWDDSJV.
	 * 2.- Si la respuesta en "" entonces es ok, y se guarda un registro en DC_CONFMSG 
	 * 3.- se guarda un registro en DC_LOG
	 * 
	 * @param confmsgdatabean
	 * @return
	 * @throws Exception
	 */
	public boolean guardarRegistroEnDCCONFMSG(DC_CONFMSG confmsgdatabean) throws Exception;
	
	/**
	 * Para guardar un registro de cambio de contrasenia en la tabla DC_LOG.
	 * 
	 * Cuando se cambia la contrasenia:
	 * 1.- Se utiliza el socket PWDDSJV.
	 * 2.- Si la respuesta en "" entonces es ok, y se guarda un registro en DC_CONFMSG 
	 * 3.- se guarda un registro en DC_LOG
	 * 
	 * @param dttm
	 * @param entityID
	 * @param userID
	 * @param laction
	 * @param remark
	 * @param account
	 * @param amount
	 * @param currency
	 * @param typTran
	 * @param dcibsREF
	 * @return boolean
	 * @throws Exception
	 */
	public boolean guardarRegistroEnDCLOG( 
			String dttm,
			String entityID,
			String userID,
			String laction,
			String remark,
			String account,
			String amount,
			String currency,
			String typTran,
			String dcibsREF) throws Exception;
	
}
