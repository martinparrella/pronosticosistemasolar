package com.meli.pronostico.sistemasolar.dto;

import java.io.Serializable;

/**
 * @author martin.parrella
 *
 */
public class PronosticoClimaDiaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private int dia;
    private String clima;
    
    
	/**
	 * @param dia
	 * @param clima
	 */
	public PronosticoClimaDiaDTO(int dia, String clima) {
		super();
		this.dia = dia;
		this.clima = clima;
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
	 * @return the clima
	 */
	public String getClima() {
		return clima;
	}


	/**
	 * @param clima the clima to set
	 */
	public void setClima(String clima) {
		this.clima = clima;
	}

}
