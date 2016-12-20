package com.afirme.afirmenet.utils.base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class DecBase64 {
	
	public static String decBase64(String input){
        String output = "";
        if(input == null || input.equals("null")){
            //no hacer nada
        }else{
            if(input.equals("")){
                output = "";
            }else{
                //decodificar
                byte[] b;
                try {
                    b = Base64.decodeBase64(input.getBytes("UTF-8"));
                    output = new String(b, "UTF-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                               
            }
            
        }
        return output;
    }
	
	public static String encBase64(String input){
        String output = "";
        byte[] b;
        try {        	
        	b =  Base64.encodeBase64(input.getBytes("UTF-8"));
        	output = new String(b, "UTF-8");
        } catch (Exception ex) {
            
        }
        
        
        return output;
	}
	
	public static String encBase64Image(File file){
        String output = "";
        byte[] b;	
        try { 	        	
	        ByteArrayOutputStream baosImage=new ByteArrayOutputStream(1000);
	        BufferedImage img=ImageIO.read(file);
	        ImageIO.write(img, "png", baosImage);
	        baosImage.flush();
	       	b =  Base64.encodeBase64(baosImage.toByteArray());
        	output = new String(b, "UTF-8");
            System.out.println( output );
        } catch (Exception ex) {
            
        }
        
        return output;
	}

}
