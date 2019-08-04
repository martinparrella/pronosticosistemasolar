package com.meli.pronostico.sistemasolar.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.meli.pronostico.sistemasolar.utils.GlobalConfiguration;

/**
 * @author martin.parrella
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PronosticoClimaDiaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Integer dia;
	
    private Integer hora;
	
    private Integer minuto;
	
    private Integer segundo;
	
	private String clima;
    
	@JsonInclude(Include.NON_EMPTY)
	private List climas;
    
    public PronosticoClimaDiaDTO(){
    }
    
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
	 * @param dia
	 * @param hora
	 * @param clima
	 */
	public PronosticoClimaDiaDTO(int dia, int tiempoHoraMinutoSegundo, String clima, String frecuenciaMuestreo) {
		super();
		this.clima = clima;
		this.dia = dia;
		switch (frecuenciaMuestreo) {
			case GlobalConfiguration.FRECUENCIA_MUESTREO_X_DIA:
				break;
			case GlobalConfiguration.FRECUENCIA_MUESTREO_X_HORA:
				this.hora = tiempoHoraMinutoSegundo;
				break;
			case GlobalConfiguration.FRECUENCIA_MUESTREO_X_MINUTO:
				this.minuto = tiempoHoraMinutoSegundo;
				break;
			case GlobalConfiguration.FRECUENCIA_MUESTREO_X_SEGUNDO:
				this.segundo = tiempoHoraMinutoSegundo;
				break;
			default:
				break;
		}
	}
	
	/**
	 * @return the dia
	 */
	public Integer getDia() {
		return dia;
	}


	/**
	 * @param dia the dia to set
	 */
	public void setDia(Integer dia) {
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

	/**
	 * @return the hora
	 */
	public Integer getHora() {
		return hora;
	}

	/**
	 * @param hora the hora to set
	 */
	public void setHora(Integer hora) {
		this.hora = hora;
	}

	/**
	 * @return the minuto
	 */
	public Integer getMinuto() {
		return minuto;
	}

	/**
	 * @param minuto the minuto to set
	 */
	public void setMinuto(Integer minuto) {
		this.minuto = minuto;
	}

	/**
	 * @return the segundo
	 */
	public Integer getSegundo() {
		return segundo;
	}

	/**
	 * @param segundo the segundo to set
	 */
	public void setSegundo(Integer segundo) {
		this.segundo = segundo;
	}

	/**
	 * @return the climas
	 */
	public List getClimas() {
		return climas;
	}

	/**
	 * @param climas the climas to set
	 */
	public void setClimas(List climas) {
		this.climas = climas;
	}

}
