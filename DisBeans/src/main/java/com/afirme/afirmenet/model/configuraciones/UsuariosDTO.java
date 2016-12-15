package com.afirme.afirmenet.model.configuraciones;

import java.math.BigInteger;

import com.afirme.afirmenet.model.transferencia.ComprobanteBase;

/**
 * Created on Dic 14, 2016 3:39:05 PM by Jorge
 * <br><br>
 * @author Jorge Luis Alvarado
 * @version 1.0.0
 * 
 * 
 * Modificado on dic 13, 2016 11:12:21 AM by Selene 
 * 
 * @author Selene Mena Quiñones
 * 
 */
public class UsuariosDTO extends ComprobanteBase{
	
	private String usuario;
	
	private String nombre;
	
	private String nToken;

	private BigInteger token;
	
	private String apellidos;
	
	private boolean estatus;
	
	private String email;
	
	private String titulo;
	
	private String permisos;
	
	private String contrasena;

	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
	/**
	 * @return
	 */
	public String getnToken() {
		return nToken;
	}

	/**
	 * @param nToken
	 */
	public void setnToken(String nToken) {
		this.nToken = nToken;
	}
	/**
	 * @return
	 */
	public BigInteger getToken() {
		return token;
	}

	/**
	 * @param token
	 */
	public void setToken(BigInteger token) {
		this.token = token;
	}

	/**
	 * @return los apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return el estatus
	 */
	public boolean isEstatus() {
		return estatus;
	}

	/**
	 * @param estatus
	 */
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return el email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return el titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return los permisos
	 */
	public String getPermisos() {
		return permisos;
	}

	/**
	 * @param permisos
	 */
	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}

	/**
	 * @return la contrasena
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * @param contrasena
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
