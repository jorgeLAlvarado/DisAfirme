package com.afirme.afirmenet.web.empresas.controller.base;

import javax.servlet.http.HttpServletRequest;

import com.afirme.afirmenet.utils.AfirmeNetLog;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.afirme.afirmenet.exception.AfirmeNetException;
import com.afirme.afirmenet.exception.AfirmeNetSessionExpiredException;
import com.afirme.afirmenet.exception.ResourceNotFoundException;
import com.afirme.afirmenet.exception.SocketFactoryException;
import com.afirme.afirmenet.web.controller.base.GlobalExceptionController;
import com.afirme.afirmenet.web.model.AfirmeNetUser;
import com.afirme.afirmenet.web.utils.AfirmeNetWebConstants;
import com.ibm.db2.jcc.a.SqlException;

/**
 * {@link @Controller} global que permite cachar y manejar todas las excepciones
 * generadas por las clases anotadas con {@link @Controller}
 * 
 * @author jorge.canoc@gmail.com
 * 
 */
@ControllerAdvice
public class GlobalExceptionController {
	static final AfirmeNetLog LOG = new AfirmeNetLog(GlobalExceptionController.class);

	/**
	 * Metodo que permite manejar las excepciones de tipo
	 * {@link SocketFactoryException} que se generan en la capa de servicios y
	 * son lanzadas a la capa del controlador
	 * 
	 * @param socketFactoryException
	 * @return pagina de error generica
	 */
	@ExceptionHandler(SocketFactoryException.class)
	public ModelAndView handleCustomException(
			SocketFactoryException socketFactoryException) {
		LOG.error("********Error al generar una conexi�n por Socket********",
				socketFactoryException);
		ModelAndView model = new ModelAndView("base/error/customError");
		model.addObject("errCode", socketFactoryException.getErrCode());
		model.addObject("errMsg", socketFactoryException.getErrMsg());
		
		return model;

	}

	/**
	 * Metodo que permite tratar las excepciones producidas en el controlador por intentos de reenvio de formularios
	 * 
	 * @param exception
	 * @return pagina de error generica
	 */
	@ExceptionHandler(HttpSessionRequiredException.class)
	public ModelAndView handleHttpSessionRequiredException(HttpSessionRequiredException exception) {
		LOG.error("##########Ocurrio una excepcion Inesperada#############",
				exception);
		ModelAndView model = new ModelAndView("base/error/genericError");
		model.addObject("errMsg",
				"Se genero un error al tratar de enviar su petici�n en m�s de 1 ocasi�n, le sugerimos revisar los movimientos efectuados");
		model.addObject("titleError", "Afirmenet detecto un error al procesar su informaci�n");
		return model;

	}
	
	/**
	 * Metodo que permite tratar las excepciones generales no clasificadas generadas en
	 * la capa del controlador
	 * 
	 * @param exception
	 * @return pagina de error generica
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception exception) {
		LOG.error("##########Ocurrio una excepcion Inesperada#############",
				exception);
		ModelAndView model = new ModelAndView("base/error/genericError");
		model.addObject("errMsg",
				"Ocurrio un error, por favor intente mas tarde");
		model.addObject("titleError", "Ocurrio un Error");
		return model;

	}

	/**
	 * Metodo que permite cachar y evaluar los errores ocacionados por un codigo
	 * HTTP 404
	 * 
	 * @param resourceNotFoundException
	 * @return pagina de error generica
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handleResourceNotFoundException(
			ResourceNotFoundException resourceNotFoundException) {
		LOG.error(
				"|||||||Se genero un error al solicitar un recurso no localizado|||||||||",
				resourceNotFoundException);
		ModelAndView model = new ModelAndView("base/error/genericError");
		model.addObject("errMsg", "El recurso solicitado no fue encontrado: "
				+ resourceNotFoundException.getResourceNotFound());
		return model;
	}

	@ExceptionHandler(SqlException.class)
	public ModelAndView handleSQLException(SqlException sqlException){
		LOG.error("$$$$$$Ocurrio una excepcion de SQL $$$$$$",
				sqlException);
		ModelAndView model = new ModelAndView("base/error/genericError");
		model.addObject("errMsg",
				"Ocurrio un error, por favor intente mas tarde");
		return model;
	}

	/**
	 * Metodo que permite manejar las excepciones de tipo
	 * {@link AfirmeNetException} que se generan en la capa de servicios y
	 * son lanzadas a la capa del controlador
	 * 
	 * @param socketFactoryException
	 * @return pagina de error generica
	 */
	@ExceptionHandler(AfirmeNetException.class)
	public ModelAndView handleCustomException(
			AfirmeNetException afirmeNetException) {
		LOG.error("********Error al generar una conexi�n por Socket********",
				afirmeNetException);
		ModelAndView model = new ModelAndView("base/error/genericError");
		model.addObject("errMsg", afirmeNetException.getErrMsg());
		model.addObject("titleError", "Por el momento AfirmeNet no puede procesar su petici�n");
		return model;

	}	
	
	/**
	 * Metodo que permite manejar las excepciones de tipo
	 * {@link SocketFactoryException} que se generan en la capa de servicios y
	 * son lanzadas a la capa del controlador
	 * 
	 * @param socketFactoryException
	 * @return pagina de error generica
	 */
	@ExceptionHandler(AfirmeNetSessionExpiredException.class)
	public ModelAndView handleSessionExpiredException(
			AfirmeNetSessionExpiredException exception) {
		LOG.info("********La session expiro********",exception);
		ModelAndView model = new ModelAndView("base/error/genericError");
		model.addObject("errMsg", "La sesi�n expir�, ingrese nuevamente");
		model.addObject("titleError", "Por el momento AfirmeNet no puede procesar su petici�n");
		return model;

	}
	
	/**
	 * Metodo para redireccionar en caso que se realice un Request a un mapping cuyo m�todo (GET/POST)
	 * no es soportado
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ModelAndView handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
		LOG.info("********Error controlado: Se efect�o un Request(GET/POST) a un mapping que no soporta tal m�todo. Se realiza Redireccionamiento********");
		AfirmeNetUser user = (AfirmeNetUser) request.getSession().getAttribute(AfirmeNetWebConstants.USUARIO_SESSION);
		ModelAndView model;
		if (user == null) {
			model = new ModelAndView("redirect:/login.htm");
		} else {
			model = new ModelAndView("redirect:/home.htm");
		}
		return model;
	}
	
}
