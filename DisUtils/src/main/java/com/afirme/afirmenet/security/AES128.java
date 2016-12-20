package com.afirme.afirmenet.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.utils.AfirmeNetConstants;



public class AES128 {
	
	
	/**
     * Sirve para cumplir con el encriptado que solicita Banxico para generar el CEP (No es util para utilizarse en otra area de afirmenet)
     * @param cadenaEnClaro Cadena armada con transaccion original SPEI
     * @return Una cadena encriptada AES128 CBC codificada en BASE64
     * @throws Exception
     */
    public static String encryptAES_128(String cadenaEnClaro)	throws Exception {
  	    //Llave simetrica que asigna Banxico, su numero de serie es 20150805  NOTA: esta llave cambiara cada 12 meses      	
  	    String llaveSimetricaBase64 = AfirmeNetConstants.getValorConfigPersonas( ConfigPersonas.CEP_LLAVE_SIMETRICA );  //Llave simetrica
  	
  	                            	
  	    byte[] b = Base64.decodeBase64( llaveSimetricaBase64.getBytes() );         //Arreglo de 32 bytes de la Llave Simetrica			 
		byte[] simmetricKey = new byte[16];	                                       //Arreglo de 16 bytes, los primeros 16 bytes de la Llave Simetrica corresponden a la clave simetrica para AES-128					
		byte[] IV = new byte[16];                                                  //Arreglo de 16 bytes, los primeros 16 bytes de la Llave Simetrica corresponde a la inicializacion del vector para AES-128
		
		//Toma los primeros 16 bytes, después los segundos 16 bytes
		int i = 0;
		while(i <= 15){
			simmetricKey[i] = b[i];
			i++;
		}
		while(i <= 31){
			IV[i-16] = b[i];
			i++;
		}
  	
		//Encriptacio AES-128 con CBC
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(simmetricKey,
				                              "AES");
		cipher.init(Cipher.ENCRYPT_MODE, 
				    key,
				    new IvParameterSpec( IV ) );
		byte[] aes128 = cipher.doFinal( cadenaEnClaro.getBytes("UTF-8") );
		//Codificacion a base 64 de la cadena encriptada
		byte[] base64 = Base64.encodeBase64( aes128 );
		String output = new String(base64); 
		
		return output;
	}
}
