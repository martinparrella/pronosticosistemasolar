package com.meli.pronostico.sistemasolar.bo;

/**
 * @author martin.parrella
 *
 */
public enum PeriodoClimaBO {	
	SEQUIA("sequia"), 
	LLUVIA("lluvia"), 
	OPTIMO("optimo"), 
	NORMAL("normal");

	private String descripcion;
	
	PeriodoClimaBO(String descripcion) {
        this.descripcion = descripcion;
    }
	
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

}
