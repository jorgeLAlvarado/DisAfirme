package com.afirme.afirmenet.dao.impl.transferencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.afirme.afirmenet.daoUtil.DaoUtil;
import com.afirme.afirmenet.empresas.dao.transferencia.ComprobanteTransferenciaDao;
import com.afirme.afirmenet.enums.ConfigPersonas;

import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;

import com.afirme.afirmenet.ibs.generics.Util;

import com.afirme.afirmenet.model.transferencia.Comprobante;

import com.afirme.afirmenet.model.transferencia.TipoCuentaDestino;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;

import com.afirme.afirmenet.security.AES128;

import com.afirme.afirmenet.utils.AfirmeNetConstants;
import com.afirme.afirmenet.utils.AfirmeNetLog;
import com.afirme.afirmenet.utils.time.TimeUtils;

/**
 * @author Usuario
 *
 */
@Repository
public class ComprobanteTransferenciaDaoImpl implements ComprobanteTransferenciaDao {



	

	
	@Override
	public List<TransferenciaBase> buscarComprobantesGenericos(String contrato, String tipo, String fechaDesde, String fechaHasta, String numeroServicio) {

	

	}
	
	
	@Override
	public List<Divisa> buscarComprobantesSWIFT(String contrato, String tipo, String fechaDesde, String fechaHasta) {


	}


	@Override
	public String buscarAtributoINOTR(String ref, String contrato, String atrib){

	}
	
	

	
	
	
}
