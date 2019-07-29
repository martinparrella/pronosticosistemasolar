package com.meli.pronostico.sistemasolar.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author martin.parrella
 *
 */
@Entity
@Table(name="PRONOSTICO_CLIMA")
public class PronosticoClima {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private int diaEntero;
    private double diaParcial;
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
	public PronosticoClima(int diaEntero, double diaParcial, String clima, double perimetro) {
		super();
		this.diaEntero = diaEntero;
		this.diaParcial = diaParcial;
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

	@Override
	public String toString() {
		return "PronosticoClima [id=" + id + ", diaEntero=" + diaEntero + ", diaParcial=" + diaParcial + ", clima="
				+ clima + ", perimetro=" + perimetro + "]";
	}

    
}
