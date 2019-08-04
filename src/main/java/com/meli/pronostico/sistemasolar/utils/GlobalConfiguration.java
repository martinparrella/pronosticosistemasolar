package com.meli.pronostico.sistemasolar.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalConfiguration {

	@Value("${dias.repite.ciclo.orbital}")
	private String cicloOrbital;
	@Value("${frecuencia.muestreo}")
	private String frecuenciaMuestreo;	
	@Value("${pronosticar.aniosfuturos}")
	private String cantidadAniosFuturos;

	public static final String FRECUENCIA_MUESTREO_X_DIA = "XDIA";
	public static final String FRECUENCIA_MUESTREO_X_HORA = "XHORA";
	public static final String FRECUENCIA_MUESTREO_X_MINUTO = "XMINUTO";
	public static final String FRECUENCIA_MUESTREO_X_SEGUNDO = "XSEGUNDO";
	
	/**
	 * @return the frecuenciaMuestreo
	 */
	public String getFrecuenciaMuestreo() {
		return frecuenciaMuestreo;
	}

	/**
	 * @param frecuenciaMuestreo the frecuenciaMuestreo to set
	 */
	public void setFrecuenciaMuestreo(String frecuenciaMuestreo) {
		this.frecuenciaMuestreo = frecuenciaMuestreo;
	}

	/**
	 * @return the cicloOrbital
	 */
	public String getCicloOrbital() {
		return cicloOrbital;
	}

	/**
	 * @param cicloOrbital the cicloOrbital to set
	 */
	public void setCicloOrbital(String cicloOrbital) {
		this.cicloOrbital = cicloOrbital;
	}

	/**
	 * @return the cantidadAniosFuturos
	 */
	public String getCantidadAniosFuturos() {
		return cantidadAniosFuturos;
	}

	/**
	 * @param cantidadAniosFuturos the cantidadAniosFuturos to set
	 */
	public void setCantidadAniosFuturos(String cantidadAniosFuturos) {
		this.cantidadAniosFuturos = cantidadAniosFuturos;
	}
	
	public int getCantidadDiasFuturos() {
		int cantidadDiasFuturos = 0;
		if(this.cantidadAniosFuturos!=null && !this.cantidadAniosFuturos.equals("0")) {
			int cantidadAniosFuturosInt = Integer.valueOf(cantidadAniosFuturos).intValue();
			LocalDate hoy = LocalDate.now();
	        LocalDate futuro = hoy.plusYears(cantidadAniosFuturosInt);
	        //Duration diferencia = Duration.between(hoy, futuro);
	        cantidadDiasFuturos = (int) Duration.between(hoy.atTime(0, 0), futuro.atTime(0, 0)).toDays();
		}
		return cantidadDiasFuturos;
	}
	
	public double getFrecuenciaMuestreoTiempo() {
		BigDecimal bd = new BigDecimal(1); 
		
		switch (getFrecuenciaMuestreo()) {
			case FRECUENCIA_MUESTREO_X_DIA:
				break;
			case FRECUENCIA_MUESTREO_X_HORA:
				bd = bd.divide(new BigDecimal(24), 10, RoundingMode.HALF_UP);
				break;
			case FRECUENCIA_MUESTREO_X_MINUTO:
				bd = bd.divide(new BigDecimal(1440), 10, RoundingMode.HALF_UP);
				break;
			case FRECUENCIA_MUESTREO_X_SEGUNDO:
				bd = bd.divide(new BigDecimal(86400), 10, RoundingMode.HALF_UP);
				break;
			default:
				break;
		}
		
		return bd.doubleValue();
	}
	
	public int getFrecuenciaMuestreoDivisor() {
		BigDecimal bd = new BigDecimal(1); 
		
		switch (getFrecuenciaMuestreo()) {
			case FRECUENCIA_MUESTREO_X_DIA:
				break;
			case FRECUENCIA_MUESTREO_X_HORA:
				bd = new BigDecimal(24);
				break;
			case FRECUENCIA_MUESTREO_X_MINUTO:
				bd = new BigDecimal(1440);
				break;
			case FRECUENCIA_MUESTREO_X_SEGUNDO:
				bd = new BigDecimal(86400);
				break;
			default:
				break;
		}
		
		return bd.intValue();
	}
}
