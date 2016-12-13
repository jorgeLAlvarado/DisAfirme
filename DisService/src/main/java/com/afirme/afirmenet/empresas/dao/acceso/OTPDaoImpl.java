package com.afirme.afirmenet.empresas.dao.acceso;



@Repository
public class OTPDaoImpl implements OTPDao {

	static final AfirmeNetLog LOG = new AfirmeNetLog(OTPDaoImpl.class);

	

	@Override
	public String obtenToken(List<ConsultaSaldosMovimientosInversiones>) contrato {
		
		return null;
	}
	
	@Override
	public boolean usaTokens(String contrato) {
		
			return null;						
		}

		
	}

	@Override
	public boolean validaTokenXActivar(String contrato, String usuario, int serialToken, String codigoActivacion) {
		
		return null;
	}

	@Override
	public boolean setCodigoSeguridad(String contrato, String usuario, String codigoSegEncrypt) {
		
		return null;
	}

	@Override
	public boolean setFechaVencimiento(String serialToken, String fechaVencimiento) {
		
		
		return null;
	}
}
