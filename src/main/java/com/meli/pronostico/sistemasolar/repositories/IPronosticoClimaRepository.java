package com.meli.pronostico.sistemasolar.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
import com.meli.pronostico.sistemasolar.entities.PronosticoClima;

/**
 * @author martin.parrella
 * 
 */
@Repository
public interface IPronosticoClimaRepository extends JpaRepository<PronosticoClima, Long> {

	/**
	 * @param dia
	 * @return Devuelve el pronostico de ese dia
	 */
	public PronosticoClima findByDiaEntero(int dia);

	/**
	 * @param dia
	 * @return Devuelve la variacion del pronostico de ese dia
	 */
	@Query("select p from PronosticoClima p where p.diaEntero = :dia")
	public List<PronosticoClima> findDistinctClimaByDiaEntero(@Param("dia") int diaEntero);

	/**
	 * @param clima
	 * @return Devuelve la lista de dias con un mismo clima en un ciclo
	 */
	public List<PronosticoClima> findByClima(String clima);

	/**
	 * @param diasExtra
	 * @return Devuelve la lista de pronosticos menor a diasExtra where x.diaEntero
	 *         < ?1
	 */
	public List<PronosticoClima> findByDiaEnteroLessThan(int diasExtra);

	/**
	 * @return devuelve el pronostico de lluvia con mayor perimetro
	 */
	public PronosticoClima findFirstByOrderByPerimetroDesc();

	/**
	 * @return devuelve el top de pronostico de lluvia con mayor perimetro
	 */
	public PronosticoClima findTopByOrderByPerimetroDesc();

	/**
	 * @return devuelve el top de pronostico de lluvia con mayor perimetro
	 */
	public List<PronosticoClima> findFirst10ByClima(String clima, Sort perimetro);

	/**
	 * @param perimetro
	 * @return Devuelve la lista de pronosticos que tengan el perimetro
	 */
	public List<PronosticoClima> findByPerimetro(double perimetro);
	
	/**
	 * @return devuelve la cantidad de dias distintos x clima
	 */
	@Query("select new com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO(p.clima, count(distinct p.diaEntero)) from PronosticoClima p group by p.clima")
	public List<PeriodoClimaDTO> findAllDistinctDiaGroupByClima();

	/**
	 * @return devuelve la cantidad de dias distintos que hay x clima, siendo el dia menor al pasado por parametro.
	 */
	@Query("select new com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO(p.clima, count(distinct p.diaEntero)) from PronosticoClima p where p.diaEntero < :dia group by p.clima")
	public List<PeriodoClimaDTO> findDistinctDiaGroupByClimaWhereDiaEnteroLessThan(@Param("dia") int diasExtra);

	@Query(value = "SELECT * FROM PRONOSTICO_CLIMA", nativeQuery = true)
	public List<PronosticoClima> findAllNativo(int diaEntero);
}
