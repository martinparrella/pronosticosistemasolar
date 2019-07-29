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
	private double perimetro;
	
	/**
	 * @param dia
	 * @param perimetro
	 */
	public PicoLluviaDTO(int dia, double perimetro) {
		super();
		this.dia = dia;
		this.perimetro = perimetro;
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
	 * @return the perimetro
	 */
	public double getPerimetro() {
		return perimetro;
	}
	/**
	 * @param perimetro the perimetro to set
	 */
	public void setPerimetro(double perimetro) {
		this.perimetro = perimetro;
	}
	
	
}
