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

	private String TELCEL = "1";
	private String MOVISTAR = "2";

	@Autowired
	private RecargasDao recargasDao;

	@Override
	public void agregar(Favorita favorita) {
	}

	@Override
	public boolean existeFavorita(Favorita favorita) {
		return (Boolean) null;
	}

	private Integer getSecuenciaFavoritas(String contractId) {
		return null;
	}

	@Override
	public List<Favorita> listar(String contractId) {
		return null;
	}

	@Override
	public void modificar(Favorita favorita) {
	}

	@Override
	public void eliminar(String contractId, Integer orden) {
	}

	@Override
	public Favorita consulta(String contractId, Integer orden) {
		return null;
	}

	@Override
	public void insertaTransaccionFavorita(Favorita favorita) {
		return;
	}

	@Override
	public void eliminarTransaccionFavorita(String contrato) {
	}

}
