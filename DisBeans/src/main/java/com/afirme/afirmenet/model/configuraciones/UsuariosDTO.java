package com.afirme.afirmenet.model.configuraciones;

import java.math.BigInteger;

import com.afirme.afirmenet.model.transferencia.ComprobanteBase;

public class UsuariosDTO extends ComprobanteBase{
	
	private String usuario;
	
	private String nombre;
	
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

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigInteger getToken() {
		return token;
	}

	public void setToken(BigInteger token) {
		this.token = token;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPermisos() {
		return permisos;
	}

	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
