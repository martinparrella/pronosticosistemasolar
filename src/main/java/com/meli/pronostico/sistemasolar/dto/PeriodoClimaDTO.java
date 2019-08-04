package com.meli.pronostico.sistemasolar.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author martin.parrella
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeriodoClimaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private String clima;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Long cantidadDias;
    
    @JsonProperty("cantidad_dias")
    private int cantidadDiasInt;
    
    
	/**
	 * @param clima
	 * @param cantidadDias
	 */
	public PeriodoClimaDTO(String clima, Long cantidadDias) {
		super();
		this.clima = clima;
		this.cantidadDias = cantidadDias;
		this.cantidadDiasInt = cantidadDias.intValue();
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
	public Long getCantidadDias() {
		return cantidadDias;
	}



	/**
	 * @param cantidadDias the cantidadDias to set
	 */
	public void setCantidadDias(Long cantidadDias) {
		this.cantidadDias = cantidadDias;
	}



	/**
	 * @return the cantidadDiasInt
	 */
	public int getCantidadDiasInt() {
		return cantidadDiasInt;
	}

	/**
	 * @param cantidadDiasInt the cantidadDiasInt to set
	 */
	public void setCantidadDiasInt(int cantidadDiasInt) {
		this.cantidadDiasInt = cantidadDiasInt;
	}
	
	/**
	 * @param dias
	 */
    public void addCantidadDias(int dias) {
    	this.cantidadDiasInt += dias;
    }

	@Override
    public boolean equals(Object obj) {
        return this.clima.equalsIgnoreCase(((PeriodoClimaDTO) obj).getClima());
    }    
}
