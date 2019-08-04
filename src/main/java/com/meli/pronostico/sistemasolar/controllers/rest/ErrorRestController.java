package com.meli.pronostico.sistemasolar.controllers.rest;
 
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.pronostico.sistemasolar.exceptions.ClimaNotFoundException;
import com.meli.pronostico.sistemasolar.exceptions.DiaNotFoundException;
import com.meli.pronostico.sistemasolar.exceptions.SistemaPronosticandoException;

@ControllerAdvice(annotations=RestController.class)
public class ErrorRestController {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorRestController.class);
   
    @ExceptionHandler
    @ResponseBody
    public CustomMessageJson handleException(Exception ex, HttpServletRequest request) {
        LOGGER.info("Ejecutando exception handler (REST)");
 
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
 
        return new CustomMessageJson(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getClass().getName(),
                ex.getMessage(),
                request.getRequestURI(),
                sw.toString());
    }
    
	@ExceptionHandler(SistemaPronosticandoException.class)
	public final CustomMessageJson handleSistemaPronosticandoException(SistemaPronosticandoException ex,
			HttpServletRequest request) {

		return new CustomMessageJson(
                HttpStatus.LOCKED.value(),
                HttpStatus.LOCKED,
                ex.getClass().getName(),
                ex.getMessage(),
                request.getRequestURI(),
                null);
		
	}
	
	@ExceptionHandler(DiaNotFoundException.class)
	public final CustomMessageJson handleDiaNotFoundException(DiaNotFoundException ex,
			HttpServletRequest request) {

		return new CustomMessageJson(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                ex.getClass().getName(),
                ex.getMessage(),
                request.getRequestURI(),
                null);
		
	}

	@ExceptionHandler(NumberFormatException.class)
	public final CustomMessageJson handleNumberFormatException(NumberFormatException ex,
			HttpServletRequest request) {

		return new CustomMessageJson(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                ex.getClass().getName(),
                ex.getMessage(),
                request.getRequestURI(),
                null);
		
	}
	
	@ExceptionHandler(ClimaNotFoundException.class)
	public final CustomMessageJson handleClimaNotFoundException(ClimaNotFoundException ex,
			HttpServletRequest request) {

		return new CustomMessageJson(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                ex.getClass().getName(),
                ex.getMessage(),
                request.getRequestURI(),
                null);
		
	}
}