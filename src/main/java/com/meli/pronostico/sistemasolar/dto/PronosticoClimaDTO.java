package com.meli.pronostico.sistemasolar.dto;

import java.io.Serializable;

/**
 * @author martin.parrella
 *
 */
public class PronosticoClimaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private int diaEntero;
    private double diaParcial;
    private String clima;
    private double perimetro;
    
    
	/**
	 * @param diaEntero
	 * @param clima
	 */
	public PronosticoClimaDTO(int diaEntero, String clima) {
		super();
		this.diaEntero = diaEntero;
		this.clima = clima;
	}


	/**
	 * @return the diaEntero
	 */
	public int getDiaEntero() {
		return diaEntero;
	}


	/**
	 * @param diaEntero the diaEntero to set
	 */
	public void setDiaEntero(int diaEntero) {
		this.diaEntero = diaEntero;
	}


	/**
	 * @return the diaParcial
	 */
	public double getDiaParcial() {
		return diaParcial;
	}


	/**
	 * @param diaParcial the diaParcial to set
	 */
	public void setDiaParcial(double diaParcial) {
		this.diaParcial = diaParcial;
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
