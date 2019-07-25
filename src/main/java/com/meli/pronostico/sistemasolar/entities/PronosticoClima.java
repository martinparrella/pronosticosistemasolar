package com.meli.pronostico.sistemasolar.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author martin.parrella
 *
 */
@Entity
public class PronosticoClima {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private double dia;
    private String clima;
    private double perimetro;
    
    /**
	 * 
	 */
	public PronosticoClima() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param dia
	 * @param clima
	 * @param perimetro
	 */
	public PronosticoClima(double dia, String clima, double perimetro) {
		super();
		this.dia = dia;
		this.clima = clima;
		this.perimetro = perimetro;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dia
	 */
	public double getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(double dia) {
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

	@Override
	public String toString() {
		return "PronosticoClima [id=" + id + ", dia=" + dia + ", clima=" + clima + ", perimetro=" + perimetro + "]";
	}
    
    
}
