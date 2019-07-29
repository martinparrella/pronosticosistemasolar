package com.meli.pronostico.sistemasolar.controllers.rest.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author martin.parrella
 *
 */
public class RestResponseMessage {
	
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime fecha;
	private String mensaje;
	private String mensajeException;

	private RestResponseMessage() {
		fecha = LocalDateTime.now();
	}

	public RestResponseMessage(HttpStatus status) {
		this();
		this.status = status;
	}

	public RestResponseMessage(HttpStatus status, String mensaje) {
		this();
		this.status = status;
		this.mensaje = mensaje;
	}
	
	public RestResponseMessage(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.mensaje = "Error inesperado";
		this.mensajeException = "";
		if(ex.getLocalizedMessage()!=null)
			this.mensajeException = ex.getLocalizedMessage();
	}

	public RestResponseMessage(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.mensaje = message;
		this.mensajeException = "";
		if(!this.mensaje.equals(ex.getLocalizedMessage()) && ex.getLocalizedMessage()!=null)
			this.mensajeException = ex.getLocalizedMessage();
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the fecha
	 */
	public LocalDateTime getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the mensajeException
	 */
	public String getMensajeException() {
		return mensajeException;
	}

	/**
	 * @param mensajeException the mensajeException to set
	 */
	public void setMensajeException(String mensajeException) {
		this.mensajeException = mensajeException;
	}

}
