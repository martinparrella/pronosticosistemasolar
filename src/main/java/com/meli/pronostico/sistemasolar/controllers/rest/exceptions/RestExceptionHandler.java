package com.meli.pronostico.sistemasolar.controllers.rest.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.meli.pronostico.sistemasolar.exceptions.DiaNotFoundException;
import com.meli.pronostico.sistemasolar.exceptions.SistemaPronosticandoException;

/**
 * @author martin.parrella
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SistemaPronosticandoException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(SistemaPronosticandoException ex,
			WebRequest request) {

		return buildResponseEntity(new RestResponseMessage(HttpStatus.LOCKED, ex.getMessage(), ex));
	}

	@ExceptionHandler(NumberFormatException.class)
	public final ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex,
			WebRequest request) {

		String mensaje = "El parametro dia no es valido.";
		return buildResponseEntity(new RestResponseMessage(HttpStatus.BAD_REQUEST, mensaje, ex));
	}
	
	@ExceptionHandler(DiaNotFoundException.class)
	public final ResponseEntity<Object> handleDiaNotFoundException(DiaNotFoundException ex,
			WebRequest request) {

		return buildResponseEntity(new RestResponseMessage(HttpStatus.NOT_FOUND, ex.getMessage(), ex));
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

		return buildResponseEntity(new RestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex));
	}

	private ResponseEntity<Object> buildResponseEntity(RestResponseMessage apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
