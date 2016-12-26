package com.afirme.afirmenet.service.impl.transferencia;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.consultas.CuentaDao;
import com.afirme.afirmenet.dao.impl.convenios.ConveniosDaoImpl;
import com.afirme.afirmenet.dao.impl.pagos.PagosDaoImpl;
import com.afirme.afirmenet.dao.impl.tarjetas.TDCDaoImpl;
import com.afirme.afirmenet.dao.transferencia.CuentaTercerosDao;
import com.afirme.afirmenet.enums.CodigoExcepcion;
import com.afirme.afirmenet.ibs.databeans.ACCTHIRDUSER;
import com.afirme.afirmenet.ibs.databeans.DC_CONVENIO;
import com.afirme.afirmenet.model.transferencia.TipoCuentaDestino;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.service.consultas.CuentaService;
import com.afirme.afirmenet.service.transferencia.ValidacionCuentaService;

@Service
public class ValidacionCuentaServiceImpl implements ValidacionCuentaService {

	
	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	private CuentaTercerosDao cuentaTercerosDao;
	
	@Autowired
	private ConveniosDaoImpl convenioDao;
	
	@Autowired
	private TDCDaoImpl tdcDao;
	
	@Autowired
	private PagosDaoImpl pagosDao;
	
	@Autowired
	private CuentaDao cuentaDao;
	
	public boolean getValCLABE(String ACC, String BNK) {
	      boolean ACCF = true;

	      //JBMessages messagebean;

	      //messagebean = (JBMessages) session.getAttribute("messagebean");

	      String LASTDIG = ACC.substring(ACC.length() - 1, ACC.length());

	      int D01 = Integer.parseInt(ACC.substring(0, 1));
	      int D02 = Integer.parseInt(ACC.substring(1, 2));
	      int D03 = Integer.parseInt(ACC.substring(2, 3));
	      int D04 = Integer.parseInt(ACC.substring(3, 4));
	      int D05 = Integer.parseInt(ACC.substring(4, 5));
	      int D06 = Integer.parseInt(ACC.substring(5, 6));
	      int D07 = Integer.parseInt(ACC.substring(6, 7));
	      int D08 = Integer.parseInt(ACC.substring(7, 8));
	      int D09 = Integer.parseInt(ACC.substring(8, 9));
	      int D10 = Integer.parseInt(ACC.substring(9, 10));
	      int D11 = Integer.parseInt(ACC.substring(10, 11));
	      int D12 = Integer.parseInt(ACC.substring(11, 12));
	      int D13 = Integer.parseInt(ACC.substring(12, 13));
	      int D14 = Integer.parseInt(ACC.substring(13, 14));
	      int D15 = Integer.parseInt(ACC.substring(14, 15));
	      int D16 = Integer.parseInt(ACC.substring(15, 16));
	      int D17 = Integer.parseInt(ACC.substring(16, 17));

	      BigDecimal B01 = new BigDecimal(D01 * 3);
	      BigDecimal B02 = new BigDecimal(D02 * 7);
	      BigDecimal B03 = new BigDecimal(D03 * 1);
	      BigDecimal B04 = new BigDecimal(D04 * 3);
	      BigDecimal B05 = new BigDecimal(D05 * 7);
	      BigDecimal B06 = new BigDecimal(D06 * 1);
	      BigDecimal B07 = new BigDecimal(D07 * 3);
	      BigDecimal B08 = new BigDecimal(D08 * 7);
	      BigDecimal B09 = new BigDecimal(D09 * 1);
	      BigDecimal B10 = new BigDecimal(D10 * 3);
	      BigDecimal B11 = new BigDecimal(D11 * 7);
	      BigDecimal B12 = new BigDecimal(D12 * 1);
	      BigDecimal B13 = new BigDecimal(D13 * 3);
	      BigDecimal B14 = new BigDecimal(D14 * 7);
	      BigDecimal B15 = new BigDecimal(D15 * 1);
	      BigDecimal B16 = new BigDecimal(D16 * 3);
	      BigDecimal B17 = new BigDecimal(D17 * 7);

	      BigDecimal TOT = new BigDecimal("0");
	      TOT = TOT.add(B01);
	      TOT = TOT.add(B02);
	      TOT = TOT.add(B03);
	      TOT = TOT.add(B04);
	      TOT = TOT.add(B05);
	      TOT = TOT.add(B06);
	      TOT = TOT.add(B07);
	      TOT = TOT.add(B08);
	      TOT = TOT.add(B09);
	      TOT = TOT.add(B10);
	      TOT = TOT.add(B11);
	      TOT = TOT.add(B12);
	      TOT = TOT.add(B13);
	      TOT = TOT.add(B14);
	      TOT = TOT.add(B15);
	      TOT = TOT.add(B16);
	      TOT = TOT.add(B17);

	      String LASTCHAR = String.valueOf(10 - Integer.parseInt(String.valueOf(TOT).substring(String.valueOf(TOT).length() - 1, String.valueOf(TOT).length())));

	      if(LASTCHAR.equals("10"))
	      {
	         LASTCHAR = "0";
	      }

	      if(LASTCHAR.equals(LASTDIG))
	      {
	         ACCF = false;
	      }

	      if(!ACCF)
	      {
	         ACCF = false;
	         String BNKTarj = ACC.substring(0, 3);
	         while(BNK.length() < 3)
	         {
	            BNK = "0" + BNK;
	         }
	         if(!BNK.equals(BNKTarj))
	         {
	            ACCF = true;
	         }
	      }

	      return (ACCF);
		
	}
	
	@Override
	public boolean validacionAltaCuentaTerceros(ACCTHIRDUSER oCuenta, boolean esDomingoElectronico) {
		boolean valida = false;
		Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
		
		//validar cuenta bonus
		boolean isBonus =  cuentaDao.validarCuentaEsBonus(oCuenta.getACCNUM(), oCuenta.getACCTYPE());
		if(isBonus) {
			errors.put(CodigoExcepcion.ERR_3000, "El número de cuenta no es válido.");
			oCuenta.setErrores(errors);
			return valida;
		}
		
		//validar cuenta afirme
		List<String> datosCuenta = cuentaDao.getNameAndCurrencyACCThird(oCuenta.getACCNUM(), oCuenta.getACCTYPE());
		if(datosCuenta.size()>1 && !datosCuenta.get(0).equals("")) {
			oCuenta.setACCOWNER(datosCuenta.get(0));
			oCuenta.setCURRENCY(datosCuenta.get(1));
		} else {
			errors.put(CodigoExcepcion.ERR_3000, "El número de cuenta no es válido o no pertenece a Afirme.");
			oCuenta.setErrores(errors);
			return valida;
		}
		
		if(esDomingoElectronico) {
			boolean isJunior = cuentaDao.esCuentasJunior(oCuenta.getACCNUM());
			if(!isJunior) {
				errors.put(CodigoExcepcion.ERR_3000, "La cuenta no pertence al tipo Vision Junior.");
				oCuenta.setErrores(errors);
				return valida;
			}
		}			
		
		//validar el estatus de la cuenta
		if(!cuentaService.getEstatusValido(oCuenta.getACCNUM(), oCuenta.getACCTYPE())) {
			errors.put(CodigoExcepcion.ERR_3000, "La cuenta destino se encuentra cancelada o embargada.");
			oCuenta.setErrores(errors);
			return valida;
		}

		//valida que no este dada de alta la cuenta
		if(cuentaTercerosDao.getExisteCuenta(oCuenta.getENTITYID(), oCuenta.getACCNUM())) {
			errors.put(CodigoExcepcion.ERR_3000, "La cuenta destino ya fue agregada. Favor de verificar.");
			oCuenta.setErrores(errors);
			return valida;
		}
		
	 	//valida que no sea cuenta de Terceros agregada como Propia
		if(cuentaDao.esCuentaPropia(oCuenta.getENTITYID(), oCuenta.getACCNUM())) {
			errors.put(CodigoExcepcion.ERR_3000, "La cuenta destino está asignada como cuenta propia.");
			oCuenta.setErrores(errors);
			return valida;
		}
		
		oCuenta.setErrores(null);
		
		return true;
	}
	
	@Override
	public boolean validacionAltaNacionales(ACCTHIRDUSER oCuenta) {
		boolean valida = false;
		Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
		
		//validar clabe interbancaria
        if(oCuenta.getACCTYPE().equals(TipoCuentaDestino.CLABE_INTERBANCARIA.getValor())) {
            if(getValCLABE(oCuenta.getACCNUM(), oCuenta.getBNKCODE())) {
				errors.put(CodigoExcepcion.ERR_3000, "La CLABE no es válida.");
				oCuenta.setErrores(errors);
				return valida;
            }
         }

		//valida que no exista el celular para ese banco
	 	if(oCuenta.getACCTYPE().equals(TipoCuentaDestino.CELULAR.getValor()) && 
	 			oCuenta.getTRANSTYPE().equals(TipoTransferencia.TRANSFERENCIA_SPEI.getValor())) {
        	//Validamos si el banco tiene el servicio de Banca Movil
        	//ACCF1 = valobj.getValBancoBancaMovil(BNKCODE, session, context, SuperServlet.as400libname);
        		if(cuentaTercerosDao.getValCelularBanco(oCuenta.getENTITYID(), 
        				oCuenta.getACCNUM(), oCuenta.getBNKCODE())) {
       				errors.put(CodigoExcepcion.ERR_3000, "Número de celular y banco ya existente.");
    				oCuenta.setErrores(errors);
    				return valida;
        		}
         }
        

		//valida que no este dada de alta la cuenta
		if(cuentaTercerosDao.getExisteCuenta(oCuenta.getENTITYID(), oCuenta.getACCNUM())) {
			errors.put(CodigoExcepcion.ERR_3000, "La cuenta destino ya fue agregada. Favor de verificar.");
			oCuenta.setErrores(errors);
			return valida;
		}
	 	oCuenta.setErrores(null);
		
		return true;
	}
	
	@Override
	public boolean validacionAltaTarjetas(DC_CONVENIO oTarjeta) {
		boolean valida = false;
		Map<CodigoExcepcion, String> errors = new HashMap<CodigoExcepcion, String>(0);
		
		//valida que no este dada de alta la tarjeta/Servicio
		if(convenioDao.getExisteConvenio( oTarjeta.getENTITYID(), oTarjeta.getSERACC(), oTarjeta.getSERTYP(), oTarjeta.getSERCOM())) {
			errors.put(CodigoExcepcion.ERR_3000, "Cuenta ya fue agregada. Favor de verificar.");
			oTarjeta.setErrores(errors);
			return valida;
		}
		
		//valida tarjeta si es TDC AFIRME
		if(oTarjeta.getTipoOperacion().equals(TipoCuentaDestino.TC_AFIRME.getValor())){
			//obtiene nombre del beneficiario
			String nombre = tdcDao.getNombreTDCAfirme(oTarjeta.getSERACC());
			if(nombre!=null && !nombre.equals(""))
				oTarjeta.setTCNOMBRE(nombre);
			else {
				errors.put(CodigoExcepcion.ERR_3000, "Número de TDC Afirme no existe. Favor de verificar.");
				oTarjeta.setErrores(errors);
				return valida;			
			}
		}
		
		//valida si existe el bean de 400
		if(!oTarjeta.getTipoOperacion().equals(TipoCuentaDestino.TC_SEGUROS.getValor())){
			if(!pagosDao.existeBIN400(oTarjeta.getSERACC().substring(0, 6))) {
				errors.put(CodigoExcepcion.ERR_3000, "Número de tarjeta de crédito inválida.");
				oTarjeta.setErrores(errors);
				return valida;
			}
		}
		
		oTarjeta.setErrores(null);
		
		return true;
	}
	
}
