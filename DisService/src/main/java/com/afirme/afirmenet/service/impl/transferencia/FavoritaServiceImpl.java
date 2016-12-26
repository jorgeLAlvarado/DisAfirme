package com.afirme.afirmenet.service.impl.transferencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afirme.afirmenet.dao.transferencia.FavoritaDao;
import com.afirme.afirmenet.enums.TipoServicio;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;
import com.afirme.afirmenet.model.transferencia.Comprobante;
import com.afirme.afirmenet.model.transferencia.Favorita;
import com.afirme.afirmenet.model.transferencia.TipoTransferencia;
import com.afirme.afirmenet.service.consultas.CuentaService;
import com.afirme.afirmenet.service.transferencia.FavoritaService;

@Service
public class FavoritaServiceImpl implements FavoritaService {

	static final AfirmeNetLog LOG = new 
			AfirmeNetLog(FavoritaServiceImpl.class);
	@Autowired
	private FavoritaDao favoritaDao;

	@Autowired
	private CuentaService cuentaService;

	@Override
	public void procesaFavoritas(List<Comprobante> comprobantes) {
		try {
			for (Comprobante comprobante : comprobantes) {
				if (comprobante.getErrores() == null
						|| comprobante.getErrores().isEmpty()) {
					if (esProcesableFavorita(comprobante)) {
						// Se validan las transferencias ejecutadas como
						// exitosas
						if (comprobante.isAgregarFavoritas()) {
							// Se valida que la conbinacion de cuenta cargo,
							// cuenta
							// destino
							favoritaDao.agregar(getFavorita(comprobante));
							// Se marca como favorita
							comprobante.setEsFavoritas(true);
						} else {
							// Valida si es favorita
							if (favoritaDao
									.existeFavorita(getFavorita(comprobante))) {
								// Se marca como favorita
								comprobante.setEsFavoritas(true);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// Previene un error al momento de procesar a favoritos
			LOG.info("Ocurrio un error al procesaro la operacion como favorita: "
					+ e.getMessage());
			LOG.error(
					"Ocurrio un error al procesaro la operacion como favorita",
					e);
		}

	}

	private boolean esProcesableFavorita(Comprobante comprobante) {
		boolean esProcesable = false;
		List<TipoTransferencia> transferenciasValidas = Arrays
				.asList(new TipoTransferencia[] {
						TipoTransferencia.TRANSFERENCIA_SPEI,
						TipoTransferencia.TRASPASO_PROPIAS,
						TipoTransferencia.FAVORITAS,
						TipoTransferencia.PAGO_DE_SERVICIOS,
						TipoTransferencia.TRASPASO_TERCEROS,
						TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_TELCEL,
						TipoTransferencia.RECARGA_DE_TIEMPO_AIRE_MOVISTAR});
		List<TipoServicio> serviciosValidos = Arrays.asList(new TipoServicio[] {
				TipoServicio.CABLEVISION, TipoServicio.TELEFONOS_DE_MEXICO,
				TipoServicio.TARJETA_DE_CREDITO_BANCOS_RED,TipoServicio.SEGUROS_AFIRME });

		if (comprobante.getTipoTransferencia() != null) {
			if (transferenciasValidas.contains(comprobante
					.getTipoTransferencia())) {
				esProcesable = true;
			}
		}

		if (comprobante.getTipoServicio() != null) {
			if (serviciosValidos.contains(comprobante.getTipoServicio())) {
				esProcesable = true;
			}else{
				esProcesable = false;
			}
		}
		return esProcesable;
	}

	private Favorita getFavorita(Comprobante comprobante) {
		Favorita favorita = new Favorita();
		BeanUtils.copyProperties(comprobante, favorita);
		favorita.setTipoTransferencia(TipoTransferencia.findByValue(comprobante
				.getTransactionCode()));
		if (comprobante.getTipoServicio() != null) {
			favorita.setTipoServicio(comprobante.getTipoServicio());
		} else if (comprobante.getServiceType() > 0) {
			favorita.setTipoServicio(TipoServicio.findByValue(String
					.valueOf(comprobante.getServiceType())));
		}
		favorita.setOrigen(comprobante.getOrigen());
		favorita.setDestino(comprobante.getDestino());
		favorita.setNumeroServicio(comprobante.getServiceNumber());
		return favorita;
	}

	@Override
	public List<Favorita> getFavoritas(String contractId, int cuantas) {
		List<Favorita> favoritasTemp = favoritaDao.listar(contractId);
		List<Favorita> favoritas = new ArrayList<Favorita>(0);
		List<Cuenta> cuentas = cuentaService.getCuentas(contractId, "", false);

		if (cuantas == 0 || cuantas > (favoritasTemp.size())) {
			cuantas = favoritasTemp.size();
		}
		for (int i = cuantas, x = 0; i > 0; i--, x++) {
			favoritas.add(favoritasTemp.get(x));
		}
		//CUENTAS ORIGEN
		for (Favorita fav : favoritas) {
			for (Cuenta cta : cuentas) {
				if (fav.getOrigen().getNumber().equals(cta.getNumber())) {
					fav.setOrigen(cta);
					break;
				}
			}
		}

		return favoritas;
	}

	@Override
	public void actualizaFavoritas(List<Favorita> favoritas) {
		for (Favorita favorita : favoritas) {
			if (favorita.isEjecutar()) {
				favoritaDao.modificar(favorita);
			}
		}

	}

	@Override
	public void insertaTransaccionFavorita(Favorita miTransaccionFavorita) {
		favoritaDao.insertaTransaccionFavorita(miTransaccionFavorita);
		
	}

	@Override
	public void eliminarTransaccionFavorita(String contrato) {
		favoritaDao.eliminarTransaccionFavorita(contrato);
		
	}

	@Override
	public void eliminar(String contractId,int favElimina) {
		favoritaDao.eliminar(contractId, favElimina);
		
	}

}
