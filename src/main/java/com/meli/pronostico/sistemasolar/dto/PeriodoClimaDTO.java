package com.meli.pronostico.sistemasolar.dto;

import java.io.Serializable;

/**
 * @author martin.parrella
 *
 */
public class PeriodoClimaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private String clima;
    private int cantidadDias;
    
    
	/**
	 * @param clima
	 * @param cantidadDias
	 */
	public PeriodoClimaDTO(String clima, int cantidadDias) {
		super();
		this.clima = clima;
		this.cantidadDias = cantidadDias;
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
	/**
	 * @return the cantidadDias
	 */
	public int getCantidadDias() {
		return cantidadDias;
	}
	/**
	 * @param cantidadDias the cantidadDias to set
	 */
	public void setCantidadDias(int cantidadDias) {
		this.cantidadDias = cantidadDias;
	}
    
    
}
