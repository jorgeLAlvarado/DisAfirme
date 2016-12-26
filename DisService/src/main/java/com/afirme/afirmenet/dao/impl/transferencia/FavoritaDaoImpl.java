package com.afirme.afirmenet.dao.impl.transferencia;

import java.util.List;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.afirme.afirmenet.dao.DB2Dao;
import com.afirme.afirmenet.dao.recargas.RecargasDao;
import com.afirme.afirmenet.dao.transferencia.FavoritaDao;
import com.afirme.afirmenet.dao.transferencia.rowmapper.FavoritaRowMapper;
import com.afirme.afirmenet.model.transferencia.Favorita;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;

@Repository
public class FavoritaDaoImpl implements FavoritaDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(FavoritaDaoImpl.class);
	@Autowired
	private DB2Dao db2Dao;

	private String TELCEL="1";
	private String MOVISTAR="2";
	
	@Autowired
	private RecargasDao recargasDao;
	
	@Override
	public void agregar(Favorita favorita) {

		// Valida si existe favorita
		if (!existeFavorita(favorita)) {
			// Consulta no. de ultima transaccion registrada

			Integer newId = getSecuenciaFavoritas(favorita.getContractId());
			if (newId.intValue() == 16) {
				// Elimina la ultima transferencia favorita
				eliminar(favorita.getContractId(), 15);
			}
			newId = 1;

			// Se recorre el orden de las favoritas
			String sql = "UPDATE DC_TRANSFAV SET NTRANSC=NTRANSC+1 WHERE ENTITYID=?";
			db2Dao.getJdbcTemplate().update(sql,
					new Object[] { favorita.getContractId() });

			sql = "INSERT INTO DC_TRANSFAV(ENTITYID,NTRANSC,CORIGEN,CDESTINO,CDACCTYPE,BENEFICIARIO,TRANSTYPE,"
					+ "SUBTYPE,NOSERVICIO,INSTPAGO,EMAILBENEF,BNKRECEPT,RFC,IVA,AMOUNT, REFNUM, BANKNAME, MONEDAORIGEN,MONEDADESTINO) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			Object[] params;
			
			if (favorita.getTransactionCode().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_TELCEL.getValor()) || 
				favorita.getTransactionCode().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_MOVISTAR.getValor())) {
				
				params = new Object[] {
						favorita.getContractId(), //ENTITYID
						newId,//NTRANSC
						favorita.getOrigen().getNumber(),//CORIGEN
						"",//CDESTINO
						"",//CDACCTYPE
						"",//BENEFICIARIO
						favorita.getTipoTransferencia().getValor(),//TRANSTYPE
						"",//SUBTYPE
						favorita.getCreditAccount(),//NOSERVICIO 
						"Recarga Tiempo Aire "+favorita.getBeneficiaryName(),//INSTPAGO
						favorita.getEmailBeneficiary(),//EMAILBENEF
						"62", //BNKRECEPT
						"",//RFC
						null,//IVA 
						favorita.getAmount(),//AMOUNT
						"",//REFNUM
						"AFIRME",//BANKNAME
						favorita.getCurrency(),//MONEDAORIGEN
						favorita.getCurrency() }; //MONEDADESTINO
				
			}else{
				params = new Object[] {
						favorita.getContractId(), //ENTITYID
						newId,//NTRANSC
						favorita.getOrigen().getNumber(),//CORIGEN
						favorita.getTransactionCode().equals(TipoTransferencia.PAGO_DE_SERVICIOS.getValor())? null:favorita.getDestino().getNumber(),//CDESTINO
						favorita.getOrigen().getFlg(),//CDACCTYPE
						favorita.getBeneficiaryName(),//BENEFICIARIO
						favorita.getTipoTransferencia().getValor(),//TRANSTYPE
						favorita.getTipoServicio() != null ? favorita.getTipoServicio().getValor() : null,//SUBTYPE
						favorita.getServiceNumber(),//NOSERVICIO 
						favorita.getDescription(),//INSTPAGO
						favorita.getEmailBeneficiary(),//EMAILBENEF
						favorita.getDestino() != null ?favorita.getDestino().getBankCode():"62", //BNKRECEPT
						favorita.getRfc(),//RFC
						favorita.getIva(),//IVA 
						favorita.getAmount(),//AMOUNT
						favorita.getTipoServicio() != null? favorita.getServiceNumber():favorita.getReferenceNumber(),//REFNUM
						favorita.getDestino() != null ?favorita.getDestino().getBankName():null,//BANKNAME
						favorita.getOrigen().getCcy(),//MONEDAORIGEN
						favorita.getDestino() != null ?favorita.getDestino().getCcy():null }; //MONEDADESTINO
				
				//PARA PAGO DE TARJETAS TERCEROS, OTROS BANCOS, AMERICAN Y SEGUROS
				
				if(favorita.getTipoServicio()!=null &&
				   (favorita.getTipoServicio().getValorComercial().equals("201") || favorita.getTipoServicio().getValorComercial().equals("601"))){
				
				    params = new Object[] {
						favorita.getContractId(), //ENTITYID
						newId,//NTRANSC
						favorita.getOrigen().getNumber(),//CORIGEN
						favorita.getConvenio().getSERACC(),
						favorita.getOrigen().getFlg(),//CDACCTYPE
						favorita.getBeneficiaryName(),//BENEFICIARIO
						favorita.getTipoTransferencia().getValor(),//TRANSTYPE
						favorita.getTipoServicio() != null ? favorita.getTipoServicio().getValorComercial() : null,//SUBTYPE
						favorita.getServiceNumber(),//NOSERVICIO 
						favorita.getDescription(),//INSTPAGO
						favorita.getEmailBeneficiary(),//EMAILBENEF
						favorita.getDestino() != null ?favorita.getDestino().getBankCode():"62", //BNKRECEPT
						favorita.getRfc(),//RFC
						favorita.getIva(),//IVA 
						favorita.getAmount(),//AMOUNT
						favorita.getTipoServicio() != null? favorita.getServiceNumber():favorita.getReferenceNumber(),//REFNUM
						favorita.getDestino() != null ?favorita.getDestino().getBankName():null,//BANKNAME
						favorita.getOrigen().getCcy(),//MONEDAORIGEN
						favorita.getDestino() != null ?favorita.getDestino().getCcy():null }; //MONEDADESTINO
				}
			}
			db2Dao.getJdbcTemplate().update(sql, params);
		}

	}

	@Override
	public boolean existeFavorita(Favorita favorita) {
		boolean existe = false;
		String sql = "SELECT COUNT(*) AS NUM FROM DC_TRANSFAV WHERE ENTITYID = ? AND CORIGEN = ? AND TRANSTYPE= ? ";

		Object[] params = null;

		if ((favorita.getTransactionCode().equals(TipoTransferencia.PAGO_DE_SERVICIOS.getValor()))
				&& !(favorita.getTipoServicio().getValorComercial().equals("201") || favorita.getTipoServicio().getValorComercial().equals("601"))) {
			
			sql += " AND NOSERVICIO = ?";
			params = new Object[] { favorita.getContractId(),
					favorita.getOrigen().getNumber(),
					favorita.getTipoTransferencia().getValor(),
					favorita.getNumeroServicio()};

		}else if (favorita.getTransactionCode().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_TELCEL.getValor()) || favorita.getTransactionCode().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_MOVISTAR.getValor())) {
			
			sql += " AND NOSERVICIO = ?";
			params = new Object[] { favorita.getContractId(),
					favorita.getOrigen().getNumber(),
					favorita.getTipoTransferencia().getValor(),
					favorita.getNumeroServicio()};

		}else {
			sql += " AND CDESTINO = ?";
			params = new Object[] { favorita.getContractId(),
					favorita.getOrigen().getNumber(),					
					favorita.getTipoTransferencia().getValor(),
					favorita.getDestino()==null?favorita.getCreditAccount():favorita.getDestino().getNumber()};
			
		}

		Integer newId = db2Dao.getJdbcTemplate().queryForObject(sql, params,
				Integer.class);
		if (newId != null && newId.intValue() >= 1) {
			existe = true;
		}
		return existe;
	}

	private Integer getSecuenciaFavoritas(String contractId) {
		// Consulta no. de ultima transaccion registrada
		String sql = "SELECT MAX(NTRANSC) AS NUM  FROM DC_TRANSFAV WHERE ENTITYID= ?";
		Integer newId = db2Dao.getJdbcTemplate().queryForObject(sql,
				new Object[] { contractId }, Integer.class);
		if (newId == null) {
			newId = 0;
		}
		newId++;

		return newId;
	}

	@Override
	public List<Favorita> listar(String contractId) {
		String sql = "SELECT * FROM DC_TRANSFAV WHERE ENTITYID=? ORDER BY NTRANSC";
		Object[] params = new Object[] { contractId };
		List<Favorita> favoritas = db2Dao.getJdbcTemplate().query(sql,
				new FavoritaRowMapper(), params);
		
		for(Favorita favRecarga:favoritas)
		{
			if(favRecarga.getTipoTransferencia().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_TELCEL)){
				favRecarga.setMontosRecarga(recargasDao.obtenerMontosRecargaTelefonica(TELCEL));
				favRecarga.setBeneficiaryName(recargasDao.obtenerNombreEmpresaTelefonicaPorId(TELCEL));
				}
			
			if(favRecarga.getTipoTransferencia().equals(TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_MOVISTAR)){
				favRecarga.setMontosRecarga(recargasDao.obtenerMontosRecargaTelefonica(MOVISTAR));
				favRecarga.setBeneficiaryName(recargasDao.obtenerNombreEmpresaTelefonicaPorId(MOVISTAR));
				}
		}
		
		return favoritas;
	}

	@Override
	public void modificar(Favorita favorita) {
		if (existeFavorita(favorita)) {
			String sql = "UPDATE DC_TRANSFAV SET AMOUNT=?, REFNUM = ?, INSTPAGO = ?  WHERE ENTITYID=? AND NTRANSC= ?";
			Object[] params = new Object[] { favorita.getAmount(),
					favorita.getReferenceNumber(), favorita.getDescription(),
					favorita.getContractId(), favorita.getOrdenFavorito() };
			db2Dao.getJdbcTemplate().update(sql, params);
		}

	}

	@Override
	public void eliminar(String contractId, Integer orden) {

		String sql = "DELETE FROM DC_TRANSFAV WHERE ENTITYID=? AND NTRANSC= ?";
		Object[] params = new Object[] { contractId, orden };
		db2Dao.getJdbcTemplate().update(sql, params);

		// RECORRE REGISTROS
		sql = "UPDATE DC_TRANSFAV SET NTRANSC=NTRANSC-1 WHERE ENTITYID=? AND NTRANSC >?";
		db2Dao.getJdbcTemplate().update(sql, params);

	}

	@Override
	public Favorita consulta(String contractId, Integer orden) {
		String sql = "SELECT * FROM DC_TRANSFAV WHERE ENTITYID=? AND NTRANSC= ?";
		Object[] params = new Object[] { contractId, orden };
		Favorita favorita = db2Dao.getJdbcTemplate().queryForObject(sql,
				params, new FavoritaRowMapper());
		return favorita;
	}
	
	@Override
	public void insertaTransaccionFavorita(Favorita favorita) {


		 String sql = "INSERT INTO DC_TRANSFAV(ENTITYID,NTRANSC,CORIGEN,CDESTINO,CDACCTYPE,BENEFICIARIO,TRANSTYPE,"
					+ "SUBTYPE,NOSERVICIO,INSTPAGO,EMAILBENEF,BNKRECEPT,RFC,IVA,AMOUNT, REFNUM, BANKNAME, MONEDAORIGEN,MONEDADESTINO) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] params = new Object[] {
					favorita.getContractId(), //ENTITYID
					favorita.getOrdenFavorito(),//NTRANSC
					favorita.getOrigen().getNumber(),//CORIGEN
					favorita.getDestino().getNumber(),//CDESTINO
					favorita.getAccountType(),//CDACCTYPE
					favorita.getBeneficiaryName(),//BENEFICIARIO
					favorita.getTipoTransferencia().getValor(),//TRANSTYPE
					favorita.getTipoServicio()==null?"":favorita.getTipoServicio().getValorComercial(),//SUBTYPE
					favorita.getServiceNumber(),//NOSERVICIO 
					favorita.getDescription(),//INSTPAGO
					favorita.getEmailBeneficiary(),//EMAILBENEF
					favorita.getBankReceiving(), //BNKRECEPT
					favorita.getRfc(),//RFC
					favorita.getIva(),//IVA 
					favorita.getAmount(),//AMOUNT
					favorita.getReferenceNumber(),//REFNUM
					favorita.getDestino().getBankName(),//BANKNAME
					favorita.getOrigen().getCcy(),//MONEDAORIGEN
					favorita.getDestino().getCcy()}; //MONEDADESTINO

			
			db2Dao.getJdbcTemplate().update(sql, params);
		

	}

	@Override
	public void eliminarTransaccionFavorita(String contrato) {
			String sql = "DELETE FROM DC_TRANSFAV WHERE ENTITYID=? ";
			Object[] params = new Object[] { contrato };
			db2Dao.getJdbcTemplate().update(sql, params);

		
	}

}
