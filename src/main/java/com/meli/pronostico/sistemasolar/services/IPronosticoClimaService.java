package com.meli.pronostico.sistemasolar.services;

import java.util.List;

import com.meli.pronostico.sistemasolar.bo.PlanetaBO;
import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
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
	
    //public PronosticoClimaDiaDTO findByDiaEntero(int dia);
    
	/**
	 * @param dia
	 * @return la lista de los diferentes climas que se dan en un dia
	 */
	public List<PronosticoClimaDiaDTO> findByDiaEntero(int dia);
	
    /**
     * @param clima
     * @return Devuelve la cantidad de dias de un clima en un ciclo
     */
    public PeriodoClimaDTO findByClima(String clima);	
	
    public long count();
}
