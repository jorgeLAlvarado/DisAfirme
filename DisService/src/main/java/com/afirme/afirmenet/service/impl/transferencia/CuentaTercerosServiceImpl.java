package com.afirme.afirmenet.service.impl.transferencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.divisas.DivisaDao;
import com.afirme.afirmenet.dao.impl.transferencia.CashExpressBeneficiarioDaoImpl;
import com.afirme.afirmenet.dao.transferencia.CashExpressBeneficiarioDao;
import com.afirme.afirmenet.dao.transferencia.CuentaTercerosDao;
import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.enums.ConfigProperties;
import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.ibs.databeans.SPABANPF;
import com.afirme.afirmenet.ibs.generics.Util;
import com.afirme.afirmenet.model.base.CatalogoBase;
import com.afirme.afirmenet.model.transferencia.AccionCuentaTercero;
import com.afirme.afirmenet.model.transferencia.Divisa;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.service.config.PropertyService;
import com.afirme.afirmenet.service.transferencia.CuentaTercerosService;
import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;

@Service
public class CuentaTercerosServiceImpl implements CuentaTercerosService {

	static final AfirmeNetLog LOG = new AfirmeNetLog(CuentaTercerosServiceImpl.class);
	@Autowired
	CuentaTercerosDao cuentaTercerosDao;
	@Autowired
	PropertyService propertyService;
	@Autowired
	DivisaDao divisaDao;
	@Autowired
	private CashExpressBeneficiarioDao cashExpressBeneficiarioDao;

	@Override
	public List<ACCTHIRDUSER> getListaTerceros(String idContrato, String tipoContrato, String idUsuario) {
		String tiempoEsperaCuentas = propertyService.obtenerTiempoEsperaSegundos(ConfigProperties.PROPERTYID_THIRD_ACCOUNT_TIME_PER);
		return cuentaTercerosDao.getListaTerceros(idContrato, tipoContrato, idUsuario, tiempoEsperaCuentas);
	}
	
	@Override
	public ACCTHIRDUSER getCuentaTercero(String idContrato, String cuentaTercero, String tipoContrato, String idUsuario) {
		String tiempoEsperaCuentas = propertyService.obtenerTiempoEsperaSegundos(ConfigProperties.PROPERTYID_THIRD_ACCOUNT_TIME_PER);
		return cuentaTercerosDao.getCuentaTercero(idContrato, cuentaTercero, tipoContrato, idUsuario, tiempoEsperaCuentas);
	}

	@Override
	public void agregaCuentaTercero(ACCTHIRDUSER oCuenta) {
		//Calculamos el codigo de activacion
		oCuenta.setCodigo(Util.getRandom(6));
		if(oCuenta.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.ORDENES_DE_PAGO_O_ENVIO_DE_DINERO.getValor())){
			cashExpressBeneficiarioDao.agregaEliminaBeneficiario(oCuenta, new BigDecimal(1));
			oCuenta.setFECHACREACION(Util.getYYMDHMS());
		} else if(oCuenta.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.TRASPASO_TERCEROS.getValor())) {
			oCuenta.setBNKCODE(ConfigPersonas.ID_BANCO_AFIRME.getValor());
			oCuenta.setBNKNAME(ConfigPersonas.DESC_BANCO_AFIRME.getValor());
			oCuenta.setSTS("P");
			oCuenta.setCodigoCorrecto(oCuenta.getCodigo());
			oCuenta.setNOREENVIOS(0);
			oCuenta.setINTENTOS(0);
			if(!cuentaTercerosDao.agregaCuentaTerceros(oCuenta)) {
				Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
				errors.put(CodigoExcepcion.ERR_3000, "Error al insertar la cuenta de terceros");
	        	oCuenta.setErrores(errors);
			}
		}else if (oCuenta.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.TRANSFERENCIA_SPEI.getValor()) || 
				oCuenta.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.PAGO_INTERBANCARIO.getValor()) ){
			oCuenta.setSTS("P");
			oCuenta.setCodigoCorrecto(oCuenta.getCodigo());
			oCuenta.setNOREENVIOS(0);
			oCuenta.setINTENTOS(0);
			if(!cuentaTercerosDao.agregaCuentaTerceros(oCuenta)) {
				Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
				errors.put(CodigoExcepcion.ERR_3000, "Error al insertar la cuenta de bancos nacionales");
	        	oCuenta.setErrores(errors);
			}
			
		} else {
			//TODO: otras alta de cuenta
			
		}
	}

	@Override
	public void activaCuentaTercero(ACCTHIRDUSER oCuenta) {
		if(oCuenta.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.ORDENES_DE_PAGO_O_ENVIO_DE_DINERO.getValor())){
			cashExpressBeneficiarioDao.activaBeneficiario(oCuenta);
		} else if(oCuenta.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.TRASPASO_TERCEROS.getValor()) ||
				oCuenta.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.TRANSFERENCIA_SPEI.getValor()) || 
				oCuenta.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.PAGO_INTERBANCARIO.getValor())){
			// valida clave de activacion 
			if(validaClaveActivacion(oCuenta)) {
				oCuenta.setSTS("A");
				if (!cuentaTercerosDao.activaCuentaTerceros(oCuenta)) {
					Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
					errors.put(CodigoExcepcion.ERR_3000, "Error al activar la cuenta");
					oCuenta.setErrores(errors);
				}
			}
		} else {
			//otras tranascciones
		}
		
	}

	@Override
	public void eliminaCuentaTercero(ACCTHIRDUSER oBene) {
		if(oBene.getTRANSTYPE().equalsIgnoreCase(TipoTransferencia.ORDENES_DE_PAGO_O_ENVIO_DE_DINERO.getValor())){
			cashExpressBeneficiarioDao.agregaEliminaBeneficiario(oBene, new BigDecimal(2));
		}else{
			//TODO: eliminacion de terceros normales
		}
		LOG.setSendInfo("borra cuenta", oBene.getTRANSTYPE(), "0", oBene.getENTITYID(), oBene.getENTITYID(), oBene.getACCNUM(), "", "", "");
	}

	@Override
	public List<ACCTHIRDUSER> consultaCuentas(String contrato, String estado) {
		return cuentaTercerosDao.consultaCuentas(contrato, estado);
	}

	@Override
	public List<ACCTHIRDUSER> consultaBeneficiarios(String contrato, String estado) {
		String tiempoEsperaCuentas = propertyService.obtenerTiempoEsperaSegundos(ConfigProperties.PROPERTYID_THIRD_ACCOUNT_TIME_PER);
		return cashExpressBeneficiarioDao.consultaBeneficiarios(contrato, estado, tiempoEsperaCuentas);
	}

	@Override
	public List<CatalogoBase> getTipoCuentasValidas(TipoTransferencia tipoTransferencia) {
		List<CatalogoBase> lista=new ArrayList<CatalogoBase>();
		switch (tipoTransferencia){
		case ORDENES_DE_PAGO_O_ENVIO_DE_DINERO:
			lista.add(new CatalogoBase("10", "Celular", "10"));
			break;
		case TRASPASO_TERCEROS:
			lista.add(new CatalogoBase("01", "Cuenta de Cheques", "9-11"));
			lista.add(new CatalogoBase("03", "Tarjeta de Débito", "16"));
			break;
		case TRANSFERENCIA_SPEI:
			lista.add(new CatalogoBase("03", "Tarjeta Destino", "16"));
			lista.add(new CatalogoBase("40", "CLABE", "18"));
			lista.add(new CatalogoBase("10", "Celular", "10"));
			break;
		case PAGO_INTERBANCARIO:
			lista.add(new CatalogoBase("03", "Tarjeta Destino", "16"));
			lista.add(new CatalogoBase("11", "Crédito Hipotecario", "1-18"));
			lista.add(new CatalogoBase("12", "Crédito Automotriz", "1-18"));
			lista.add(new CatalogoBase("13", "Crédito Personal", "1-18"));
			lista.add(new CatalogoBase("40", "CLABE", "18"));
			lista.add(new CatalogoBase("10", "Celular", "10"));
			break;
		default:
			//no hace nada
			break;
		}
		return lista;
	}

	@Override
	public List<ACCTHIRDUSER> getAdminCuentas(String contrato,
			String idUsuario, AccionCuentaTercero accion,
			TipoTransferencia tipoTransferencia) {
		switch (tipoTransferencia) {
		case ORDENES_DE_PAGO_O_ENVIO_DE_DINERO:
			//Solo se pueden activar las que estan pendientes de activar
			if(accion==AccionCuentaTercero.ACTIVAR)
				return this.consultaBeneficiarios(contrato, "P");
			//Mandamos todas
			return this.consultaBeneficiarios(contrato, " ");
		case TRASPASO_TERCEROS:
		case TRANSFERENCIA_SPEI:
		case PAGO_INTERBANCARIO:
			//Solo se pueden activar las que estan pendientes de activar
			//if(accion==AccionCuentaTercero.ACTIVAR)
				return this.consultaCuentas(contrato, "N");
		default:
			break;
		}
		return null;
	}

	@Override
	public ACCTHIRDUSER seleccionar(String numero, List<ACCTHIRDUSER> cuentas) {
		ACCTHIRDUSER sele=null;
		for (ACCTHIRDUSER comp : cuentas) {
			if (comp.getACCNUM().equals(numero)) {
				sele=comp;
				break;
			}
		}
		return sele;
	}
	
	/**
	 * 
	 * @see com.afirme.afirmenet.service.transferencia.CuentaTercerosService#getProCodeList(java.lang.Boolean)
	 */
	@Override
	public List<CatalogoBase> getProCodeList(Boolean bPaqueteSinToken) throws Exception {
		return cuentaTercerosDao.getProCodeList(bPaqueteSinToken);
	}

	/**
	 * Para eliminar una cuenta de terceros afirme.
	 * 
	 * @param contrato nuemero contrato del usuario logueado.
	 * @param accthird de tipo ACCTHIRDUSER
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delAccPersonas(String contrato, ACCTHIRDUSER accthird) throws Exception{
		LOG.setSendInfo("borra cuenta", accthird.getTRANSTYPE(), "0", accthird.getENTITYID(), accthird.getENTITYID(), accthird.getACCNUM(), "", "", "");
		return cuentaTercerosDao.delAccPersonas(contrato, accthird);
	}

	/**
	 * Para obtener la lista swift.
	 * 
	 * @param contrato numero de contrato del usuario logueado.
	 * @param esIntermediario true si es intermediario agrega una condicion mas en el wehere sql.
	 * @return List<Divisa>
	 * @throws Exception
	 */
	public List<Divisa> obtenerListaCuentaSwift(String contrato, boolean esIntermediario) throws Exception{
		return divisaDao.getListaCuentasSWIFT(contrato, esIntermediario);
	}
	
	/**
	 * Para eliminar cuenta swift y swift intermediarios.<br>
	 * <br>
	 * 1.- insertar en DC_CONVENIO_LOG<br>
	 * 2.- borrar la cuenta terceros de DC_ACCSWIFTUSER<br>
	 * En caso de que falle (2.-) se borra el insert que se hizo en DC_CONVENIO_LOG<br>
	 * <br>
	 * @see com.afirme.afirmenet.service.transferencia.CuentaTercerosService#borraCuentaSWIFTPersonas(com.afirme.afirmenet.model.transferencia.Divisa)
	 * 
	 * @param contrato
	 * @param accthird
	 * @return boolean
	 * @throws Exception
	 */
	public boolean borraCuentaSWIFTPersonas(Divisa cuentaSWIFT) throws Exception{
		LOG.setSendInfo("borra cuenta", cuentaSWIFT.getTransactionCode(), "0", cuentaSWIFT.getContractId(), cuentaSWIFT.getContractId(), cuentaSWIFT.getAccountNumber(), "", "", "");
		return cuentaTercerosDao.borraCuentaSWIFTPersonas(cuentaSWIFT);
	}
	
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
	public List<DC_CONVENIO> getConvList(String numeroContrato, String tiempoEsperaCuentas) throws Exception{
		return cuentaTercerosDao.getConvList(numeroContrato, tiempoEsperaCuentas);
	}
	
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
	public boolean borrarConvAdmon(String contrato, DC_CONVENIO convenioUsuario) throws Exception{
		LOG.setSendInfo("borra cuenta", convenioUsuario.getSERRTY(), "0", contrato, contrato, convenioUsuario.getSERACC(), "", "", "");
		return cuentaTercerosDao.borrarConvAdmon(contrato, convenioUsuario);
	}
	
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
	 *  @see com.afirme.afirmenet.service.transferencia.CuentaTercerosService#getListaTercerosNoAlteraLista(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 *  <br>
	 * @param idContrato
	 * @param tipoContrato
	 * @param idUsuario
	 * @return List&lt;ACCTHIRDUSER&gt;
	 */
	public List<ACCTHIRDUSER> getListaTercerosNoAlteraLista(String idContrato, String tipoContrato, String idUsuario){
		String xtiempoEsperaCuentas = propertyService.obtenerTiempoEsperaSegundos(ConfigProperties.PROPERTYID_THIRD_ACCOUNT_TIME_PER);
		return cuentaTercerosDao.getListaTercerosNoAlteraLista(idContrato, tipoContrato, idUsuario, xtiempoEsperaCuentas);
	}
	
	@Override
	public ACCTHIRDUSER seleccionarPorRecNum(String numero, List<ACCTHIRDUSER> cuentas) {
		ACCTHIRDUSER sele=null;
		for (ACCTHIRDUSER comp : cuentas) {
			if (comp.getRECNUM().toString().equals(numero)) {
				sele=comp;
				break;
			}
		}
		return sele;
	}
	
	@Override
	public boolean validaClaveActivacion (ACCTHIRDUSER oCuenta) {
		boolean valida = false;
		
		String numeroIntentosCuenta = AfirmeNetConstants.getPropertiesConfig().get(ConfigProperties.PROPERTYID_CLAVE_INTENTOS.getValor()).toString();
		String tiempoVigenciaCuentas = AfirmeNetConstants.getPropertiesConfig().get(ConfigProperties.PROPERTYID_CLAVE_VIGENCIA.getValor()).toString();

		Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
		// revisar que no este expirada la clave
		GregorianCalendar fechaValidacion = new GregorianCalendar();

		GregorianCalendar fechaCreac = new GregorianCalendar();
		String fechaCreacion = oCuenta.getFECHACREACION();
		fechaCreac.set(Integer.parseInt(fechaCreacion.substring(0, 4)),
				Integer.parseInt(fechaCreacion.substring(4, 6)) - 1,
				Integer.parseInt(fechaCreacion.substring(6, 8)),
				Integer.parseInt(fechaCreacion.substring(8, 10)),
				Integer.parseInt(fechaCreacion.substring(10, 12)),
				Integer.parseInt(fechaCreacion.substring(12, 14)));
		// Se realiza un add con el numero de horas de vigencia
		fechaCreac.add(Calendar.HOUR, Integer.parseInt(tiempoVigenciaCuentas));

		if (fechaCreac.before(fechaValidacion)) {
			// actualizar estatus a E (Cancelado por Expiracion)
			oCuenta.setSTS("E");
			cuentaTercerosDao.actualizaEstatus(oCuenta);

			errors.put(CodigoExcepcion.ERR_3000,
					"Se ha cancelado el alta de cuenta por expiración de clave de activación. Favor de registrarla nuevamente.");
			oCuenta.setErrores(errors);
		} else {
			// valida clave de activacion
			if (!oCuenta.getCodigo().equals(oCuenta.getCodigoCorrecto())) {
				// registrar intento erroneo en base de datos
				oCuenta.setINTENTOS(oCuenta.getINTENTOS() + 1);
				cuentaTercerosDao.registraIntentoFallido(oCuenta);

				// validar número de intentos fallidos
				if (oCuenta.getINTENTOS() >= Integer.parseInt(numeroIntentosCuenta)) {
					// actualizar el estatus a I (Cancelado por intentos excedido)
					oCuenta.setSTS("I");
					cuentaTercerosDao.actualizaEstatus(oCuenta);

					errors.put(CodigoExcepcion.ERR_3000,
							"Se ha cancelado el alta de cuenta por exceder el numero de intentos erróneos de la clave de activación. Favor de registrarla nuevamente.");
					oCuenta.setErrores(errors);
				} else {
					
					errors.put(CodigoExcepcion.ERR_3000,
							"La clave de activación es incorrecta, usted lleva "
									+ oCuenta.getINTENTOS() + " de  "
									+ numeroIntentosCuenta
									+ " intentos fallidos");
					oCuenta.setErrores(errors);
				}

			} else {
				valida = true;
			}
		}
		return valida;
	}

	@Override
	public boolean validaReenvioClave(ACCTHIRDUSER oCuenta) {
		boolean valida = false;
		
		//validar reenvios de clave de activacion
		if (oCuenta.getNOREENVIOS() >= 3) {
			Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
			errors.put(CodigoExcepcion.ERR_3000,
					"Ha excedido el numero de reenvios (3) de clave de activación para el alta de cuenta.");
			oCuenta.setErrores(errors);
			return valida;
		} 
		
		oCuenta.setNOREENVIOS(oCuenta.getNOREENVIOS()+1);
		return cuentaTercerosDao.registraReenvioClave(oCuenta);
	}
	
	@Override
	public List<SPABANPF> getListaBancos() {
		return cuentaTercerosDao.getListaBancos();
	}

	@Override
	public boolean getValCelularBanco(String contrato, String celular, String banco) {
		return cuentaTercerosDao.getValCelularBanco(contrato, celular, banco);
	}

	
}