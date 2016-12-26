package com.afirme.afirmenet.service.consultas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.afirme.afirmenet.ibs.beans.consultas.HistorialTipo;
import com.afirme.afirmenet.ibs.beans.consultas.TipoTransaccion;
import com.afirme.afirmenet.ibs.databeans.SPABANPF;
import com.afirme.afirmenet.model.pagos.ConvenioDomiciliacion;
import com.afirme.afirmenet.model.pagos.ImpuestoGDF;
import com.afirme.afirmenet.model.transferencia.TransferenciaBase;
import com.afirme.afirmenet.ibs.beans.consultas.Cuenta;

public interface HistorialService {
	List<TipoTransaccion> listaTransacciones(boolean esBasicoSinToken);
	List<String> categorias(boolean esBasicoSinToken);
	List<TransferenciaBase> buscaTransferencias(String contrtato, List<Cuenta> cuentas, HistorialTipo tipo, Date fechaDesde, Date fechaHasta);
	void obtenerInformacionExtra(TransferenciaBase comprobante);
	SPABANPF getBanco(BigDecimal numeroBanco);
	ConvenioDomiciliacion buscarInfoExtraConvenioServicios(String contrato, String referencia);
	ImpuestoGDF getComprobanteGDF(TransferenciaBase comprobante);
	ImpuestoGDF getTramiteyConcepto(ImpuestoGDF comprobante);
}
