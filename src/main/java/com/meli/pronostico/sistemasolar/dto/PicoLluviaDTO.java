/**
 * 
 */
package com.meli.pronostico.sistemasolar.dto;

import java.io.Serializable;

/**
 * @author martin.parrella
 *
 */
public class PicoLluviaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int dia;
	private String mensaje;
	
	/**
	 * @param dia
	 * @param mensaje
	 */
	public PicoLluviaDTO(int dia, String mensaje) {
		super();
		this.dia = dia;
		this.mensaje = mensaje;
	}

	/**
	 * @return the dia
	 */
	public int getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(int dia) {
		this.dia = dia;
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
	
}
