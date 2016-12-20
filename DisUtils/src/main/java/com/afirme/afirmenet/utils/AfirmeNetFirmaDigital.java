package com.afirme.afirmenet.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import com.afirme.afirmenet.utils.AfirmeNetLog;



/**
 * Clase para manejar la firma digital de manera estatica
 * 
 * @author Arturo Ivan Martinez Mata
 * 
 */
public class AfirmeNetFirmaDigital {

	static final AfirmeNetLog LOG = new AfirmeNetLog(AfirmeNetFirmaDigital.class);

	private static String rutaLLavePrivada = "";
	private static String rutaLLavePublica = "";
	private static PrivateKey privada;
	private static PublicKey publica;
	private static Signature verificador;

	public static boolean inicializaLlaves() {
		boolean listo = false;
		
		FileInputStream fprivis = null;
		FileInputStream fpublis = null;
		try {
			
			LOG.debug(rutaLLavePrivada);
			LOG.debug(rutaLLavePublica);
			
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			// Se regenera la llave privada
			File fpriv = new File(rutaLLavePrivada);
			fprivis = new FileInputStream(fpriv);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int b = 0;
			while (b != -1) {
				b = fprivis.read();
				if (b != -1)
					baos.write(b);
			}
			fprivis.close();
			byte[] privEncode = baos.toByteArray();
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privEncode);
			privada = keyFactory.generatePrivate(privKeySpec);
			// Se regenera la llave publica
			File fpubl = new File(rutaLLavePublica);
			fpublis = new FileInputStream(fpubl);
			ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			b = 0;
			while (b != -1) {
				b = fpublis.read();
				if (b != -1)
					baos2.write(b);
			}
			fpublis.close();
			byte[] publEncode = baos2.toByteArray();
			X509EncodedKeySpec publKeySpec = new X509EncodedKeySpec(publEncode);
			publica = keyFactory.generatePublic(publKeySpec);
			// Llaves regeneradas con exito
			listo = true;
		} catch (Exception e) {
			LOG.error("No se pueden cargar las llaves para la firma digital de logs " + e);
		}
		return listo;
	}
	public static String firmaTexto(String texto) {
		String firma = "";
		try {
			Signature dsa = Signature.getInstance("SHA1withDSA");
			dsa.initSign(privada);
			byte[] data = texto.getBytes();
			dsa.update(data);
			byte[] sig = dsa.sign();
			byte[] sig64 = Base64.encodeBase64(sig);
			firma = new String(sig64, "ISO-8859-1");
		} catch (Exception e) {
			LOG.error("Error en JBFirmaDigital.firmaTexto " + e);
		}
		return firma;
	}

	public static boolean setVerificador() {
		boolean exito = false;
		try {
			verificador = Signature.getInstance("SHA1withDSA");
			verificador.initVerify(publica);
			exito = true;
		} catch (Exception e) {
			LOG.error("Error en JBFirmaDigital.setVerificador " + e);
		}
		return exito;
	}

	public static boolean verificaFirma(String texto, String firma) {
		boolean igual = false;
		try {
			byte[] data = texto.getBytes();
			verificador.update(data);
			igual = verificador.verify(Base64.decodeBase64(firma.getBytes("ISO-8859-1")));
		} catch (Exception e) {
			LOG.error("Error en JBFirmaDigital.verificaFirma " + e);
		}
		return igual;
	}
	public static void setRutaLLavePrivada(String rutaLLavePrivada){
		AfirmeNetFirmaDigital.rutaLLavePrivada=rutaLLavePrivada;
	}
	public static void setRutaLLavePublica(String rutaLLavePublica){
		AfirmeNetFirmaDigital.rutaLLavePublica=rutaLLavePublica;
	}
}
