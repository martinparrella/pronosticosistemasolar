package com.meli.pronostico.sistemasolar.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.meli.pronostico.sistemasolar.entities.PronosticoClima;

/**
 * @author martin.parrella
 * 
 */
@Repository
public interface IPronosticoClimaRepository extends PagingAndSortingRepository<PronosticoClima, Long> {
    
	/**
     * @param dia
     * @return Devuelve el pronostico de ese dia
     */
    //public PronosticoClima findByDiaEntero(int dia);
    
    /**
     * @param dia
     * @return Devuelve la variacion del pronostico de ese dia
     */
    public List<PronosticoClima> findByDiaEntero(int dia);

    /**
     * @param clima
     * @return Devuelve la lista de dias con un mismo clima en un ciclo
     */
	public List<PronosticoClima> findByClima(String clima);    
}
