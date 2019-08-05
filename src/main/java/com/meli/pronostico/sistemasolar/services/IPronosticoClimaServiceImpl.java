/**
 * 
 */
package com.meli.pronostico.sistemasolar.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.pronostico.sistemasolar.bo.PeriodoClimaBO;
import com.meli.pronostico.sistemasolar.bo.PlanetaBO;
import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
import com.meli.pronostico.sistemasolar.dto.PicoLluviaDTO;
import com.meli.pronostico.sistemasolar.dto.PronosticoClimaDiaDTO;
import com.meli.pronostico.sistemasolar.entities.PronosticoClima;
import com.meli.pronostico.sistemasolar.repositories.IPronosticoClimaRepository;
import com.meli.pronostico.sistemasolar.utils.GeometriaUtils;
import com.meli.pronostico.sistemasolar.utils.GlobalConfiguration;
import com.meli.pronostico.sistemasolar.utils.Punto;
import com.meli.pronostico.sistemasolar.utils.Triangulo;

/**
 * @author martin.parrella
 *
 */
@Service
public class IPronosticoClimaServiceImpl implements IPronosticoClimaService {
	
	@Autowired
	private GlobalConfiguration global;
	
	@Autowired
	private IPronosticoClimaRepository pronosticoClimaRepository;
	
	private static Logger LOGGER = LoggerFactory.getLogger(IPronosticoClimaServiceImpl.class);
    
    private PlanetaBO ferengi;
    private PlanetaBO betasoide;
    private PlanetaBO vulcano;
    
	
	@Override
	public void pronosticarClimaSistemaSolar(PlanetaBO ferengi, PlanetaBO betasoide, PlanetaBO vulcano) {
		// TODO Auto-generated method stub
		LOGGER.debug("Ingrese al método pronosticarClimaSistemaSolar");
		
		this.ferengi = ferengi;
		this.betasoide = betasoide;
		this.vulcano = vulcano;
		
		//limpio la base de datos, con los pronosticos viejos.
		pronosticoClimaRepository.deleteAll();
		
		this.iniciarPronostico();
		
		LOGGER.debug("Finalizó el método pronosticarClimaSistemaSolar");
	}
	
	private void iniciarPronostico() {
	    //El ciclo dura 360 dias, despues las predicciones se repiten
	    int CICLO = Integer.parseInt(global.getCicloOrbital());
	    
	    //Tiempo entre muestreo y muestreo para pronosticar el periodo del clima
	    double FRECUENCIA_MUESTREO = global.getFrecuenciaMuestreoTiempo(); 
	    
	    List<PronosticoClima> pronosticos = new ArrayList<PronosticoClima>();
	    
		double dia = 0;
		while(dia < CICLO) {
			//LOGGER.info("Pronosticando dia: " + dia);
			pronosticos.add( this.getClima(dia) );
			dia = dia + FRECUENCIA_MUESTREO;
		}
		
		//Imprimo los pronosticos calculados:
		pronosticos.forEach(pronostico -> {
			LOGGER.debug(pronostico.toString());
		});
		
		//Persisto en BD
		pronosticos.forEach(pronostico -> pronosticoClimaRepository.save(pronostico));
		
	}
	
    private PronosticoClima getClima(double diaParcial){
        Punto p1 = ferengi.getPosicion(diaParcial);
        Punto p2 = betasoide.getPosicion(diaParcial);
        Punto p3 = vulcano.getPosicion(diaParcial);

        return this.getClimaByPosicion(diaParcial, p1, p2, p3);
    }
    
    public PronosticoClima getClimaByPosicion(double diaParcial, Punto p1, Punto p2, Punto p3){

    	int diaEntero = (int) Math.floor(diaParcial);
        
    	LOGGER.debug("En el día: " + diaEntero + " - Posicion 1: " + p1.toString() + " - Posicion 2: " + p2.toString() + " - Posicion 3: " + p3.toString());	
    	
        if(GeometriaUtils.isPuntosColineales(p1, p2, p3)){
            //los 3 planetas estan alineados
            if(GeometriaUtils.isPuntosColineales(p1, p2, GeometriaUtils.UBICACION_SOL)){
                //los 3 planetas estan alineados respecto al sol
                return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.SEQUIA.getDescripcion(), 0);
            }
            return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.OPTIMO.getDescripcion(), 0);
        }

        Triangulo triangulo = new Triangulo(p1, p2, p3);
        if(triangulo.incluyePunto(GeometriaUtils.UBICACION_SOL)){
        	
        	LOGGER.debug("Dia: " + diaEntero + " Perimetro: " + triangulo.getPerimetro());
        	
        	//si el sol esta dentro del triangulo
            return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.LLUVIA.getDescripcion(), triangulo.getPerimetro());
        }

        //Si el triangulo no incluye al sol y no es un recta
        return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.NORMAL.getDescripcion(), 0); 
    }

	@Override
	public PronosticoClimaDiaDTO findByDiaEntero(int dia) {
		PronosticoClima pronosticoEntity = pronosticoClimaRepository.findByDiaEntero(dia);
		return new PronosticoClimaDiaDTO(pronosticoEntity.getDiaEntero(), pronosticoEntity.getClima());
	}
	
	@Override
	public List<PronosticoClimaDiaDTO> findDistinctClimaByDiaEntero(int dia) {
		List<PronosticoClimaDiaDTO> listPronosticoDTO = new ArrayList<PronosticoClimaDiaDTO>();
		List<PronosticoClima> listPronosticoEntity = pronosticoClimaRepository.findDistinctClimaByDiaEntero(dia);
	
		int tiempoHoraMinutoSegundo = 0;
		for (PronosticoClima pronosticoClima : listPronosticoEntity) {
			listPronosticoDTO.add(new PronosticoClimaDiaDTO(dia, tiempoHoraMinutoSegundo, pronosticoClima.getClima(), global.getFrecuenciaMuestreo()));
			tiempoHoraMinutoSegundo++;
		}
		
		return listPronosticoDTO;
	}
	
	@Override
	public long count() {
		return pronosticoClimaRepository.count();
	}

	@Override
	public PeriodoClimaDTO countDiasByClima(String clima) {
		List<PronosticoClima> listPronosticoEntity = pronosticoClimaRepository.findByClima(clima);
		return new PeriodoClimaDTO(clima, new Long(listPronosticoEntity.size()));
	}

	@Override
	public List<PeriodoClimaDTO> getCantidadDiasDeUnClimaEnFuturos10Anios() {

		List<PeriodoClimaDTO> listPeriodoClimaDTO = new ArrayList<PeriodoClimaDTO>();
		int ciclo = Integer.parseInt(global.getCicloOrbital());
		int diasFuturos10Anios = global.getCantidadDiasFuturos();
        int cantCiclos = diasFuturos10Anios / ciclo;
        int diasExtrasEnDecada = diasFuturos10Anios % ciclo;
               
        List<PeriodoClimaDTO> listPronosticosDeUnCiclo = pronosticoClimaRepository.findAllDistinctDiaGroupByClima();
		
		this.sumarDiasXAnios(listPronosticosDeUnCiclo, cantCiclos, listPeriodoClimaDTO);
				
        if(diasExtrasEnDecada > 0){
            listPronosticosDeUnCiclo = pronosticoClimaRepository.findDistinctDiaGroupByClimaWhereDiaEnteroLessThan(diasExtrasEnDecada);

            this.sumarDiasXAnios(listPronosticosDeUnCiclo, cantCiclos, listPeriodoClimaDTO);
        }
        
        //Controlo si hay algun clima que no se haya presentado y le seteo 0 días
        this.getClimasSinPronostico(listPeriodoClimaDTO);
        
		return listPeriodoClimaDTO;
	}

	@Override
	public List<PicoLluviaDTO> getPicoLluviaEnFuturos10Anios() {

		List<PicoLluviaDTO> listPicosLluvia = new ArrayList<PicoLluviaDTO>();
		
		PronosticoClima pronostico = pronosticoClimaRepository.findFirstByOrderByPerimetroDesc();
		
		//hay algun pico de lluvia?
		if(pronostico.getPerimetro() > 0 ) {
			
			//recupero los pronosticos que tengan el mismo perimetro maximo
			List<PronosticoClima> pronosticosPicoLluvia = pronosticoClimaRepository.findByPerimetro(pronostico.getPerimetro());
			
			if(pronosticosPicoLluvia.size() > 0 ) {

				int ciclo = Integer.parseInt(global.getCicloOrbital());
				int futuros10Anios = Integer.parseInt(global.getCantidadAniosFuturos());

				pronosticosPicoLluvia.forEach(p -> {
					//seteo el dia del primer año
					listPicosLluvia.add(new PicoLluviaDTO(p.getDiaEntero(), "El perímetro del triangulo del día es: " + p.getPerimetro()));
					
					//seteo el dia en los años futuros
					for(int i = 1; i < futuros10Anios; i++  ) {
						int diaAnioFuturo = p.getDiaEntero() + ( i * ciclo );
						listPicosLluvia.add(new PicoLluviaDTO(diaAnioFuturo, "El perímetro del triangulo del día es: " + p.getPerimetro()));
					}
				});
				
				List<PicoLluviaDTO> sortedListPicosLluvia = listPicosLluvia.stream()
						.sorted(Comparator.comparingInt(PicoLluviaDTO::getDia))
						.collect(Collectors.toList());
				
				return sortedListPicosLluvia;
			}			
		
		} 
		
		//si no hay pronosticos con dias lluviosos devuelvo el siguiente mensaje:
		listPicosLluvia.add(new PicoLluviaDTO(-1, "En los días de lluvia no se registra un pico de intesidad."));
		
		return listPicosLluvia;
		 
	}
	
	
	private void sumarDiasXAnios(List<PeriodoClimaDTO> listPeriodoClima, int cantCiclos, List<PeriodoClimaDTO> listPeriodosClima ) {
		
		listPeriodoClima.forEach(p -> {
			
			PeriodoClimaDTO periodoClimaDTO = new PeriodoClimaDTO(p.getClima(), p.getCantidadDias());
			if(listPeriodosClima.contains(periodoClimaDTO)) {
				int index = listPeriodosClima.indexOf(periodoClimaDTO);
				listPeriodosClima.get(index).addCantidadDias((periodoClimaDTO.getCantidadDias().intValue()) );
			} else {
				int diasX10Anios = cantCiclos * (periodoClimaDTO.getCantidadDias().intValue());
				periodoClimaDTO.setCantidadDias(null);
				periodoClimaDTO.setCantidadDiasInt(diasX10Anios);
				listPeriodosClima.add(periodoClimaDTO);
			}
			
		});
    }
	
	
	private void getClimasSinPronostico(List<PeriodoClimaDTO> listPeriodoClimaDTO) {
		
		List<String> climasActuales = new ArrayList<String>();
		listPeriodoClimaDTO.forEach(p -> {
			climasActuales.add(p.getClima());
		});

		if(!climasActuales.isEmpty()) {

			for (PeriodoClimaBO periodo : PeriodoClimaBO.values()) { 
			    //System.out.println(periodo);
			    if(!climasActuales.contains(periodo.getDescripcion())){
			    	listPeriodoClimaDTO.add(new PeriodoClimaDTO(periodo.getDescripcion(), 0L));
			    }
			}
			
		}
	}


}
