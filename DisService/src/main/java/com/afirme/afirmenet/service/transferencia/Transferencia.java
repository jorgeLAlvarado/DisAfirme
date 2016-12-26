package com.afirme.afirmenet.service.transferencia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afirme.afirmenet.dao.transferencia.ParametricasUsuarioDao;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.ComprobanteTransferencia;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TipoValidacion;
import com.afirme.afirmenet.model.transferencia.TipoValidacionEspecial;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.service.mail.MailService;

/**
 * Clase se soporte para todas las transferencias soportadas por afirmenet, en
 * esta calse se define el comportamiento principal de una tranferencia
 * 
 * @author jorge.canoc@gmail.com
 * 
 */
@Component
public abstract class Transferencia {

	static final AfirmeNetLog LOG = new AfirmeNetLog(Transferencia.class);
	@Autowired
	private ParametricasUsuarioDao parametricasTransaccionDao;
	@Autowired
	protected ValidacionTransferenciaService validacion;
	@Autowired
	protected FavoritaService favoritaService;	
	@Autowired
	private ComprobanteTransferenciaService comprobanteTransferenciaService;
	@Autowired
	private MailService mailService;	 
    											
	protected TipoTransferencia tipoTransferencia;
	

	/**
	 * Constructor default que indica que tipo de transferencia es la que sera
	 * atendida.
	 * 
	 * @param tipoTransferencia
	 */
	protected Transferencia(TipoTransferencia tipoTransferencia) {
		super();
		this.tipoTransferencia = tipoTransferencia;
	}

	/**
	 * Metodo abstracto que provee la implementacion especifica para ejecutar
	 * cada trasferencia que sea implementada
	 * 
	 * @return
	 */
	public abstract TransferenciaAfirmeNet getTransferenciaService();

	/**
	 * Metodo abstracto que provee las validaciones especificas a aplicar a cada
	 * tipo de transferencia, en caso que esta lista sea vacia o nula se
	 * ejecutan todas las transferencias definidas en {@link TipoValidacion}
	 * 
	 * @return
	 */
	public abstract List<TipoValidacion> getValidaciones();

	/**
	 * Metodo que provee las validaciones especificas para cierto tipo de
	 * transferencia son ejecutadas desde el metodo valida de esta clase
	 * 
	 * @return la lista de validacion a aplicar
	 */
	public abstract List<TipoValidacionEspecial> getValidacionesEspecificas();

	/**
	 * Metodo que permite realizar una validacion previa a la ejecucion de las
	 * transferencias contenidas en la lista de transferencias de esta clase
	 * 
	 * @param transferencias
	 */
	public void validaTransferencias(List<TransferenciaBase> transferencias) {
		validacion.applyValidations(transferencias, tipoTransferencia,
				getValidaciones());
	}

	/**
	 * Metodo abstracto que permite realizar valdiaciones especificas sobre la
	 * lista de transferencias contenidas en esta clase
	 * 
	 * @param transferencias
	 */
	public abstract void valida(List<? extends TransferenciaBase> transferencias);
	
	
	/**
	 * Metodo que ejecuta la lista de transferencias contenidas en esta clase.
	 * La secuencia de ejecucion es la siguiente
	 * <ul>
	 * <li>Aplica Validaciones Generales mediante el metodo applyValidations</li>
	 * <li>Aplica Validaciones Particulares mediante el metodo valida</li>
	 * <li>Ejecuta las transferencias mediante el metodo ejecutaTransferencia</li>
	 * </ul>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<? extends ComprobanteTransferencia> confirmaTransferencias( List<TransferenciaBase> transferencias ) {
		// Validaciones Generales:
		validacion.applyValidations(transferencias, tipoTransferencia, getValidaciones() );
		
		// Validaciones Particulares
		//Estas transacciones no se deben de validar al ejecutar ya que se validan en la confirmacion
		if(!(tipoTransferencia!=TipoTransferencia.VENTA_DE_SEGUROS_ANTIFRAUDE || tipoTransferencia!=TipoTransferencia.INVERSION_PERFECTA)){
			valida(transferencias);
		}
		
		// Ejecuta Transaccion
		// ciclo llama socket400 IN095701
		List<? extends ComprobanteTransferencia> comprobantes = getTransferenciaService().ejecutaTransferencia(transferencias); 
		//Actualiza informacion de limites diario y mensual
		//Estas transacciones no se actualiza informacion parametrrica
		if(tipoTransferencia!=TipoTransferencia.ACTIVACION_DE_TARJETA_DE_CREDITO){
			if(getValidaciones()!=null && getValidaciones().contains(TipoValidacion.LIMITE_DIARIO)){
				
				// esta mexicanada es para no mover codigo en el metodo actualizaInformacionParameterica()
				if(tipoTransferencia == TipoTransferencia.DOMINGO_ELECTRONICO ){
					// ciclo guardar dias en DC_ACCUM_ORIGIN_ACC_PERSON
					Iterator<TransferenciaBase> transferenciasIterator = transferencias.iterator();
					while( transferenciasIterator.hasNext() ){
						
						TransferenciaBase transferenciaBase = (TransferenciaBase) transferenciasIterator.next();
						
						List<Comprobante> listaComprobantes = (List<Comprobante>) comprobantes;
						Comprobante comprobante = listaComprobantes.get(0); //new Comprobante();
						comprobante.setAfirmeNetReference( comprobante.getReferenceNumber() ); // el lote desde el socket se guarda en reference.
						comprobante.setValidationDate( transferenciaBase.getValidationDate() );
						/*
						comprobante.getContractId(), 
						debitAccount = comprobante.getOrigen().getNumber(); 
						comprobante.getTipoTransferencia().getValor(),
						comprobante.getInterType(), comprobante.getServiceType(),
						comprobante.getAmount().doubleValue(),
						comprobante.getValidationYear(),
						comprobante.getValidationMonth(),
						comprobante.getValidationDay()
						
						comprobante.getAfirmeNetReference()
						comprobante.getContractId()
						debitAccount
						comprobante.getTipoTransferencia().getValor()
						param.getSertyp()
						param.getIntertype()
						comprobante.getTipoTransferencia().name()
						TimeUtils.getDateFormat(new Date(), "yyyy-MM-dd")
						param.getDailytransnum()
						comprobante.getAmount()
						*/
						actualizaInformacionParameterica((List<Comprobante>) comprobantes);
					}
				}else{
					actualizaInformacionParameterica((List<Comprobante>) comprobantes);
				}
				
			}
		}
		
		// Validacion si fue marcada como favoritos
		//Estas transacciones no se actualiza informacion parametrrica
		if(tipoTransferencia!=TipoTransferencia.ACTIVACION_DE_TARJETA_DE_CREDITO || tipoTransferencia!=TipoTransferencia.FAVORITAS){
			favoritaService.procesaFavoritas((List<Comprobante>) comprobantes);
		}
		
		// Inserta Informacion de Comprobantes
		// Estas transacciones no se actualiza informacion parametrica
		if(tipoTransferencia!=TipoTransferencia.FAVORITAS){
			insertaComprobate((List<Comprobante>)comprobantes);
		}
		
		// Envia notificaciones por email
		if(tipoTransferencia!=TipoTransferencia.FAVORITAS){
			enviaNotificaciones((List<Comprobante>)comprobantes);
		}

		return comprobantes;
	}
	
	
	protected void insertaComprobate(List<Comprobante> comprobantes){
		for(Comprobante comprobante: comprobantes){
			if(comprobante.getErrores() == null || comprobante.getErrores().isEmpty()){ //Transaccion Exitosa
				try{
					comprobanteTransferenciaService.insertaConfirmacionOperacion(comprobante);
				}catch(Exception e){
					LOG.info("===> Error al actualizar la informacion de comprobantes <===");
					LOG.info("===> Informacion de Comprobante <===");
					LOG.info("===> " + comprobante);
					LOG.error("===> Error al actualizar la informacion de comprobantes <===", e);
				}				
			}
		}		
	}
	
	protected void enviaNotificaciones(List<Comprobante> comprobantes){
		for(Comprobante comprobante: comprobantes){
			if(comprobante.getErrores() == null || comprobante.getErrores().isEmpty()){ //Transaccion Exitosa
				try{
					mailService.enviaTransaccionConfirm(comprobante);
				}catch(Exception e){
					LOG.info("===> Error al enviar el correo de notificacion <===");
					LOG.info("===> Informacion de Comprobante <===");
					LOG.info("===> " + comprobante);
					LOG.error("===> Error al enviar el correo de notificacion <===", e);
				}				
			}
		}		
	}
	
	private void actualizaInformacionParameterica(List<Comprobante> comprobantes){
		for(Comprobante comprobante: comprobantes){
			if(comprobante.getErrores() == null || comprobante.getErrores().isEmpty()){ //Transaccion Exitosa
				try{
					parametricasTransaccionDao.insert(comprobante);
				}catch(Exception e){
					LOG.info("===> Error al actualizar la informacion parametrica <===");
					LOG.info("===> Informacion de Comprobante <===");
					LOG.info("===> " + comprobante);
					LOG.error("===> Error al actualizar la informacion parametrica <===", e);
				}				
			}
		}

	}

	public void delete(TransferenciaBase transferencia) throws SQLException {
		parametricasTransaccionDao.delete(
				Long.valueOf(transferencia.getContractId()),
				transferencia.getReferenceNumber());
	}

	
}
