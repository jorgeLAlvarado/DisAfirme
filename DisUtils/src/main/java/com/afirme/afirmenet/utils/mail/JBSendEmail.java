package com.afirme.afirmenet.utils.mail;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;

import com.afirme.afirmenet.utils.AfirmeNetLog;


import com.afirme.afirmenet.enums.ConfigPersonas;
import com.afirme.afirmenet.utils.AfirmeNetConstants;

public class JBSendEmail {
	static final AfirmeNetLog LOG = new 
			AfirmeNetLog(JBSendEmail.class);	
	
	private static String PATH = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"emailImages"+File.separator+"";
	

	public JBSendEmail() {
		
		
	}

	public String getSendEmail(String host, String to, String from, String sub, String msg, String CC) {
		
		String portSmtp = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_PORT);
		boolean tLSProtocoleSmpt = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_TSLPROTOCOLE).trim().equalsIgnoreCase("true");
		
		String senderror = "";
		// Get system properties
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		if(portSmtp!=null)
			props.put("mail.smtp.port", portSmtp.trim());
		if(tLSProtocoleSmpt)
			props.put("mail.smtp.starttls.enabled", "true");
		// Get session
		Session sessionM = Session.getDefaultInstance(props, null);
		// Define message
		MimeMessage message = new MimeMessage(sessionM);
		try {
			// Set the from address
			message.setFrom(new InternetAddress(from));
			// Set the to address
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set the CC address
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(CC));
			// Set the subject
			message.setSubject(sub);
			// Set the content
			message.setText(msg);
			// Send message
			Transport.send(message);
		} catch (Exception e) {
			senderror = "Error DIBS.JBSendEmail.getSendEmail(con CC) ==> " + e;
			LOG.debug(senderror);
			LOG.debug("El StackTrace del error en DIBS.JBSendEmail.getSendEmail(con CC) es el siguiente:");
			e.printStackTrace(System.out);
		}
		return (senderror);
	}

	public String getSendEmail(String host, String to, String from, String sub, String msg) {
		
		String portSmtp = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_PORT);
		boolean tLSProtocoleSmpt = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_TSLPROTOCOLE).trim().equalsIgnoreCase("true");
		
		String senderror = "";
		// Get system properties
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		if(portSmtp!=null)
			props.put("mail.smtp.port", portSmtp.trim());
		if(tLSProtocoleSmpt)
			props.put("mail.smtp.starttls.enabled", "true");
		// Get session
		Session sessionM = Session.getDefaultInstance(props, null);
		// Define message
		MimeMessage message = new MimeMessage(sessionM);
		try {
			// Set the from address
			message.setFrom(new InternetAddress(from));
			// Set the to address
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set the subject
			message.setSubject(sub);
			// Set the content
			message.setText(msg);
			// Send message
			Transport.send(message);
		} catch (Exception e) {
			senderror = "Error DIBS.JBSendEmail.getSendEmail ==> " + e;
			LOG.debug(senderror);
			LOG.debug("El StackTrace del error en DIBS.JBSendEmail.getSendEmail es el siguiente:");
			e.printStackTrace(System.out);
		}
		return (senderror);
	}

	public String getSendEmailMultipart(String host, String to, String from, String sub, MimeMultipart multipart) {
		
		String portSmtp = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_PORT);
		boolean tLSProtocoleSmpt = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_TSLPROTOCOLE).trim().equalsIgnoreCase("true");
		
		MimeBodyPart imagen = new MimeBodyPart();	
		
		File logo = null;
		try {
			logo = new File(this.getClass().getClassLoader().getResource("emailImages/logotipo.png").toURI());
		} catch (URISyntaxException e1) {
			// TODO Bloque catch generado automáticamente
			e1.printStackTrace();
		}
		
		try {
			imagen.attachFile(logo);
			imagen.setHeader("Content-ID", "<logoAfirme>");
			multipart.addBodyPart(imagen);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
		
		String senderror = "";
		// Get system properties
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		if(portSmtp!=null)
			props.put("mail.smtp.port", portSmtp.trim());
		if(tLSProtocoleSmpt)
			props.put("mail.smtp.starttls.enabled", "true");
		// Get session
		Session sessionM = Session.getDefaultInstance(props, null);
		// Define message
		MimeMessage message = new MimeMessage(sessionM);
		try {
			// Set the from address
			message.setFrom(new InternetAddress(from));
			// Set the to address
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set the subject
			message.setSubject(sub);
			// /Content
			message.setContent(multipart);
			// Send message
			Transport.send(message);
		} catch (Exception e) {
			senderror = "Error DIBS.JBSendEmail.getSendEmail ==> " + e;
			LOG.debug(senderror);
			System.out
					.println("El StackTrace del error en DIBS.JBSendEmail.getSendEmail es el siguiente:");
			e.printStackTrace(System.out);
		}
		return (senderror);
	}
	

	public String getSendEmailMassive(String host, ArrayList<String> to, String from, String sub, String msg) {
		
		String portSmtp = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_PORT);
		boolean tLSProtocoleSmpt = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_TSLPROTOCOLE).trim().equalsIgnoreCase("true");
		
		String senderror = "";
		// Get system properties
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		if(portSmtp!=null)
			props.put("mail.smtp.port", portSmtp.trim());
		if(tLSProtocoleSmpt)
			props.put("mail.smtp.starttls.enabled", "true");
		// Get session
		Session sessionM = Session.getDefaultInstance(props, null);
		// Define message
		MimeMessage message = new MimeMessage(sessionM);
		try {
			// Set the from address
			message.setFrom(new InternetAddress(from));
			// Set the to address
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to.get(0)));
			for (int i = 1; i < to.size(); i++) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(to.get(i)));
			}
			// Set the subject
			message.setSubject(sub);
			// Set the content
			message.setText(msg);
			// Send message
			Transport.send(message);
		} catch (Exception e) {
			senderror = "Error DCIBS.JBSendEmail.getSendEmail ==> " + e;
			LOG.debug(senderror);
			LOG.debug("El StackTrace del error en DCIBS.JBSendEmail.getSendEmail es el siguiente:");
			e.printStackTrace(System.out);
		}
		return (senderror);
	}
	
	
	
	
	public String getSendEmailMultipart(String to, String sub, String messaje) throws IOException, MessagingException {
		
		String portSmtp = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_PORT);
		boolean tLSProtocoleSmpt = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_TSLPROTOCOLE).trim().equalsIgnoreCase("true");
		
		MimeMultipart multipart = getMultipart(messaje, "");
		
		String host = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_SERVER);
		String from = AfirmeNetConstants.getValorConfigPersonas(ConfigPersonas.EMAIL_MUEMAIL);
		MimeBodyPart imagen = new MimeBodyPart();	
		
		File logo = null;
		try {
			logo = new File(this.getClass().getClassLoader().getResource("emailImages/logotipo.png").toURI());
		} catch (URISyntaxException e1) {
			// TODO Bloque catch generado automáticamente
			e1.printStackTrace();
		}
		
		imagen.attachFile(logo);
		imagen.setHeader("Content-ID", "<logoAfirme>");
		multipart.addBodyPart(imagen);  
		
		String senderror = "";
		// Get system properties
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		if(portSmtp!=null)
			props.put("mail.smtp.port", portSmtp.trim());
		if(tLSProtocoleSmpt)
			props.put("mail.smtp.starttls.enabled", "true");
		// Get session
		Session sessionM = Session.getDefaultInstance(props, null);
		// Define message
		MimeMessage message = new MimeMessage(sessionM);
		try {
			// Set the from address
			message.setFrom(new InternetAddress(from));
			// Set the to address
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set the subject
			message.setSubject(sub);
			// /Content
			message.setContent(multipart);
			// Send message
			Transport.send(message);
		} catch (Exception e) {
			senderror = "Error DIBS.JBSendEmail.getSendEmail ==> " + e;
			LOG.debug(senderror);
			System.out
					.println("El StackTrace del error en DIBS.JBSendEmail.getSendEmail es el siguiente:");
			e.printStackTrace(System.out);
		}
		return (senderror);
	}
	
	
	public MimeMultipart getMultipart(String htmlText, String path) {
		MimeMultipart multipart = new MimeMultipart("related");
		// first part (the html)
		BodyPart messageBodyPart = new MimeBodyPart();
		try {
			messageBodyPart.setContent(htmlText, "text/html; charset=ISO-8859-1");
			// add it
			multipart.addBodyPart(messageBodyPart);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		// put everything together
		return multipart;
	}
	
}
