package com.meli.pronostico.sistemasolar.services;

import java.util.List;

import com.meli.pronostico.sistemasolar.bo.PlanetaBO;
import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
import com.meli.pronostico.sistemasolar.dto.PicoLluviaDTO;
import com.meli.pronostico.sistemasolar.dto.PronosticoClimaDiaDTO;

/**
 * @author martin.parrella
 *
 */
public interface IPronosticoClimaService {

	/**
	 * Pronostiva el clima del sistema solar
	 * @param ferengi
	 * @param betasoide
	 * @param vulcano
	 */
	public void pronosticarClimaSistemaSolar(PlanetaBO ferengi, PlanetaBO betasoide, PlanetaBO vulcano);
	
	/**
	 * @param dia
	 * @return el clima de un dia, cuando la frecuencia de muestreo es XDIA
	 */
    public PronosticoClimaDiaDTO findByDiaEntero(int dia);
    
	/**
	 * @param dia
	 * @return la lista de los diferentes climas que se dan en un dia, 
	 * cuando la frecuencia de muestreo es XHORA, XMINUTO o XSEGUNDO 
	 */
	public List<PronosticoClimaDiaDTO> findDistinctClimaByDiaEntero(int dia);
	
    /**
     * @param clima
     * @return Devuelve la cantidad de dias de un clima en un ciclo x 10 años
     */
    public PeriodoClimaDTO countDiasByClima(String clima);	
	
    /**
     * @return Devuelve la cantidad de dias de cada clima en un ciclo x 10 años
     */
    public List<PeriodoClimaDTO> getCantidadDiasDeUnClimaEnFuturos10Anios();	
    
    /**
     * @return Devuelve la lista de dias con pico de lluvia en un ciclo x 10 años
     */
    public List<PicoLluviaDTO> getPicoLluviaEnFuturos10Anios();
    
    /**
     * @return Devuelve la cantidad de pronosticos relevados
     */
    public long count();
    
}
