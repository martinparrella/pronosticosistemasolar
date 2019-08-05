package com.meli.pronostico.sistemasolar.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author martin.parrella
 *
 */
public class PronosticoClimaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("pronostico_cant_dias_x_clima")
    private List<PeriodoClimaDTO> listPronosticoClima;
    
    @JsonProperty("picos_maximo_de_lluvia")
    private List<PicoLluviaDTO> picoLluvia;
	/**
	 * 
	 */
	public PronosticoClimaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param listPronosticoClima
	 * @param picoLluvia
	 */
	public PronosticoClimaDTO(List<PeriodoClimaDTO> listPronosticoClima, List<PicoLluviaDTO> picoLluvia) {
		super();
		this.listPronosticoClima = listPronosticoClima;
		this.picoLluvia = picoLluvia;
	}
	/**
	 * @return the listPronosticoClima
	 */
	public List<PeriodoClimaDTO> getListPronosticoClima() {
		return listPronosticoClima;
	}
	/**
	 * @param listPronosticoClima the listPronosticoClima to set
	 */
	public void setListPronosticoClima(List<PeriodoClimaDTO> listPronosticoClima) {
		this.listPronosticoClima = listPronosticoClima;
	}
	/**
	 * @return the picoLluvia
	 */
	public List<PicoLluviaDTO> getPicoLluvia() {
		return picoLluvia;
	}
	/**
	 * @param picoLluvia the picoLluvia to set
	 */
	public void setPicoLluvia(List<PicoLluviaDTO> picoLluvia) {
		this.picoLluvia = picoLluvia;
	}

       
}
