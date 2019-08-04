package com.meli.pronostico.sistemasolar.controllers.rest;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author martin.parrella
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomMessageJson implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime fecha;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final int status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private HttpStatus statusMessage;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private final String error;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String path;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String trace;

	
    public CustomMessageJson(int status, HttpStatus statusMessage, String error, String message, String path, String trace) {
    	this.fecha = LocalDateTime.now();
    	this.status = status;
        this.statusMessage = statusMessage;
        this.error = error;
        this.message = message;
        this.path = path;
        this.trace = trace;
    }
	
    public CustomMessageJson(int status, String error, String message, String path, String trace) {
    	this.fecha = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.trace = trace;
    }

    /**
	 * @return the fecha
	 */
	public LocalDateTime getFecha() {
		return fecha;
	}

	/**
	 * @return the statusMessage
	 */
	public HttpStatus getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the trace
	 */
	public String getTrace() {
		return trace;
	}
    
}
