package com.afirme.afirmenet.utils.mail.messages;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class EmailMessages {
	
	public static String getHeaderMessage(String descMessage){
		StringBuffer sb = new StringBuffer();
		
		//Encabezado
		sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#f9f6f2\" width=\"600\" align=\"center\">");
		sb.append("<tr><td style=\"padding: 10px 10px 10px 10px; border-bottom: 1px solid #008d36;\"><img src=\"cid:logoAfirme\"  height=\"50\" width=\"142\" alt=\"\" style=\"display: block;\" border=\"0\" /></td></tr>");
		sb.append("</table>");
		sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#FFFFFF\" width=\"600\" align=\"center\" style=\"border-bottom: 1px solid  #bababa;\">");
		sb.append("<tr><td style=\"padding:20px;\"><p style=\"font-size: 17px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 10px 0; color: #00953b;\"><strong>Estimado Cliente:</strong></p>");
		sb.append("<p style=\"font-size: 15px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 40px 0; color: #3d3935;\">");
		sb.append(descMessage);
		sb.append("</p>");
		return sb.toString();

		
	}
	
	public static String getHeaderMessageAbono(String descMessage, String beneficiario){
		StringBuffer sb = new StringBuffer();
		
		//Encabezado
		sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#f9f6f2\" width=\"600\" align=\"center\">");
		sb.append("<tr><td style=\"padding: 10px 10px 10px 10px; border-bottom: 1px solid #008d36;\"><img src=\"cid:logoAfirme\" height=\"50\" width=\"142\" alt=\"\" style=\"display: block;\" border=\"0\" /></td></tr>");
		sb.append("</table>");
		sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#FFFFFF\" width=\"600\" align=\"center\" style=\"border-bottom: 1px solid  #bababa;\">");
		sb.append("<tr><td style=\"padding:20px;\"><p style=\"font-size: 17px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 10px 0; color: #00953b;\"><strong>Estimado(a) "+beneficiario+":</strong></p>");
		sb.append("<p style=\"font-size: 15px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 40px 0; color: #3d3935;\">");
		sb.append(descMessage);
		sb.append("</p>");
		return sb.toString();

		
	}
	
	public static String getFooterMessage(){
		StringBuffer sb = new StringBuffer();
				
				//footer
				sb.append("<p style=\"font-size: 15px; font-family: 'Source Sans Pro', sans-serif; margin: 40px 0 40px 0; color: #3d3935;\">");
				sb.append("Esperamos esta información sea de su utilidad.<br /><br />");
				sb.append("Banca Afirme <br /><br />");
				sb.append("Nota: La fecha y hora de este mensaje puede variar respecto a la fecha y hora real de la operación transaccional. Esto depende de sus servicios de correo electrónico y/o computadora respecto al horario de su zona.<br /><br />");
//				sb.append("Para facilitar la lectura del presente mensaje, se eliminaron los acentos.<br /><br />");
				sb.append("Por su comprensión gracias.");
				sb.append("</p>");
				sb.append("</td>");
				sb.append("</tr>");
				sb.append("</table>");
				sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#FFFFFF\" width=\"600\" align=\"center\" valign=\"button\">");
				sb.append("<tr>");
				sb.append("<td valign=\"top\" align=\"right\" style=\"padding-right:20px\">");
				sb.append("<p style=\"font-size: 17px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 0 0; color: #3d3935;\">Atención telefónica</p>");
				sb.append("<p style=\"font-size: 30px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 0 0; color: #93c01f;\"><strong>01 800 22 34 763</strong></p>");
				sb.append("</td>");
				sb.append("</tr>");
				sb.append("<tr>");
				sb.append("<td colspan=\"2\" align=\"center\" style=\"padding: 20px 0 20px 0;\">");
				sb.append("<p style=\"font-size: 12px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 0 0; color: #666666;\"><strong>Derechos Reservados Afirme Grupo Financiero 2015</strong></p>");
				sb.append("<p style=\"font-size: 12px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 0 0; color: #666666;\"><a style=\"text-decoration:none; color:#666;\" href=\"#\">Aviso de Privacidad</a>     |     <a style=\"text-decoration:none; color:#666;\" href=\"#\">Términos Legales</a></p>");
				sb.append("</td>");
				sb.append("</tr>");
				sb.append("</table>");
				
		return sb.toString();
		
	}
	
	public static String getAvisoExtraMessage(String aviso){
		StringBuffer sb = new StringBuffer();
				sb.append("<p style=\"font-size: 15px; font-family: 'Source Sans Pro', sans-serif; margin: 40px 0 40px 0; color: #3d3935;\">");
				sb.append(aviso);
				sb.append("<br /><br />");
				sb.append("</p>");
		return sb.toString();
		
	}

	
	public static String OpenTable(){	
		return "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#FFFFFF\" width=\"100%\" align=\"center\"><tr>";	
	}
	
	public static String CloseTable(){	
		return "</tr></table>";	
	}

	public static String OpenCol(){
		return "<td width=\"50%\" valign=\"top\">";	
	}
	
	public static String CloseCol(){
		return "</td>";	
	}
	public static String OpenRow(){
		return "<tr>";	
	}
	
	public static String CloseRow(){
		return "</tr>";	
	}

	public static String getCampo(String nombre, String valor){
		StringBuffer sb = new StringBuffer();	
		sb.append("<p style=\"font-size: 17px; font-family: 'Source Sans Pro', sans-serif; margin: 0 0 5px 0; color: #59595a;\"><strong>");
		sb.append(nombre);
		sb.append("</strong></p>");
		sb.append("<p style=\"font-size: 15px; font-family: 'Source Sans Pro', sans-serif; margin: 0 5px 15px 0; color: #00953b;\">");
		sb.append(valor);
		sb.append("</p>");
	return sb.toString();
	}
	
	/**
	 * Para obtener el texto de la plantilla html.
	 * 
	 * Para referencia de uso ver el metodo
	 * enviarNotificacionActualizacionAliasYAvatar
	 * en
	 * {@link MailServiceImpl}
	 * 
	 * @author epifanio.guzman@afirme.com
	 * 
	 * @param mailTemplate nombre de la plantilla a utilizar y se encuentra en /resources/templates/
	 * @param velocityAttributes mapa de los parametros y su valor para reemplazar en la plantilla
	 * @return una cadena del contenido de pla plantillo con los valores de los parametros.
	 * 
	 */
	public static String obtenerCadenaPlantillaCorreoHTML(String mailTemplate, Map<String,Object> velocityAttributes){
		
		String resultado = "";
		try{		
			
			// 1.- obtener e inicializar el engine
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.setProperty("resource.loader", "class");
			velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			velocityEngine.init();

			// 2.- agregar los datos a un VelocityContext 
			VelocityContext velocityContext = new VelocityContext( velocityAttributes );
			
			// 3.- obtner el template
			Template template = velocityEngine.getTemplate("templates/" + mailTemplate);
			
			// 4.- renderear el contenido de la plantilla en un Writer
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			resultado = stringWriter.toString();
			
		}catch(Exception e){
			try {
				throw new Exception("Error al obtener la cadena de la plantilla.",e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return resultado;
	}
	
}
