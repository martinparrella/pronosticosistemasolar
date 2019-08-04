/**
 * 
 */
package com.meli.pronostico.sistemasolar.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
		LOGGER.info("Ingrese al método pronosticarClimaSistemaSolar");
		
		this.ferengi = ferengi;
		this.betasoide = betasoide;
		this.vulcano = vulcano;
		
		//limpio la base de datos, con los pronosticos viejos.
		pronosticoClimaRepository.deleteAll();
		
		this.iniciarPronostico();
		LOGGER.info("Finalizó el método pronosticarClimaSistemaSolar");
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
	
	private void iniciarPronosticoBKP() {
	    //El ciclo dura 360 dias, despues las predicciones se repiten
	    int CICLO = Integer.parseInt(global.getCicloOrbital());
	    
	    //Tiempo entre muestreo y muestreo para pronosticar el periodo del clima
	    double FRECUENCIA_MUESTREO = Double.parseDouble(global.getFrecuenciaMuestreo()); 
	    
	    List<PronosticoClima> pronosticos = new ArrayList<PronosticoClima>();
	    
		double dia = 0;
		while(dia < CICLO) {
			//LOGGER.info("Pronosticando dia: " + dia);
			pronosticos.add( this.getClima(dia) );
			dia = dia + FRECUENCIA_MUESTREO;
		}
		
		//Imprimo los pronosticos calculados:
		pronosticos.forEach(pronostico -> LOGGER.debug(pronostico.toString()));
		
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
                //Estan alineados respecto al sol
                return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.SEQUIA.getDescripcion(), 0);
            }
            return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.OPTIMO.getDescripcion(), 0);
        }

        Triangulo triangulo = new Triangulo(p1, p2, p3);
        if(triangulo.incluyePunto(GeometriaUtils.UBICACION_SOL)){
            //si el sol esta dentro del triangulo
        	LOGGER.debug("Dia: " + diaEntero + " Perimetro: " + triangulo.getPerimetro());
            return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.LLUVIA.getDescripcion(), triangulo.getPerimetro());
        }

        return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.NORMAL.getDescripcion(), 0); //Si el triangulo no incluye al sol y no es un recta
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
			
//		Map<String, Long> mapCantidadDiasXClimaXCiclo =
//				listPronosticoEntity.stream()
//		        	.collect( //en el metodo collect se especifican las funciones de agregacion
//		                Collectors.groupingBy( // deseamos agrupar
//		                		PronosticoClima::getClima, // agrupamos por clima
//		                        Collectors.counting() // realizamos el conteo
//		                    )
//		                );
//		
//		mapCantidadDiasXClimaXCiclo.forEach((k, v) -> {
//			System.out.println(k + ":" + v);
//			listPronosticoDTO.add(new PronosticoClimaDiaDTO(dia, k));
//		});
		
		//listPronosticoEntity.forEach(p -> listPronosticoDTO.add(new PronosticoClimaDiaDTO(p.getDiaEntero(), p.getClima())));
		
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
        
//		List<PronosticoClima> listPronosticosDeUnCiclo = (List<PronosticoClima>) pronosticoClimaRepository.findAll();
//		// Agrupo y cuento la cantidad de dias que hay de cada clima en un clico:
//		Map<String, Long> mapCantidadDiasXClimaXCiclo =
//				listPronosticosDeUnCiclo.stream().collect(Collectors.groupingBy(PronosticoClima::getClima, Collectors.counting()));
//		
//		this.mapearPeriodos(mapCantidadDiasXClimaXCiclo, cantCiclos, listPeriodoClimaDTO);
//		
//        if(diasExtrasEnDecada > 0){
//            listPronosticosDeUnCiclo = pronosticoClimaRepository.findByDiaEnteroLessThan(diasExtrasEnDecada);
//            mapCantidadDiasXClimaXCiclo =
//            		listPronosticosDeUnCiclo.stream().collect(Collectors.groupingBy(PronosticoClima::getClima, Collectors.counting()));
//            
//            this.mapearPeriodos(mapCantidadDiasXClimaXCiclo, cantCiclos, listPeriodoClimaDTO);
//        }
        
        List<PeriodoClimaDTO> listPronosticosDeUnCiclo = pronosticoClimaRepository.findAllDistinctDiaGroupByClima();
		
		this.mapearPeriodos2(listPronosticosDeUnCiclo, cantCiclos, listPeriodoClimaDTO);
				
        if(diasExtrasEnDecada > 0){
            listPronosticosDeUnCiclo = pronosticoClimaRepository.findDistinctDiaGroupByClimaWhereDiaEnteroLessThan(diasExtrasEnDecada);

            this.mapearPeriodos2(listPronosticosDeUnCiclo, cantCiclos, listPeriodoClimaDTO);
        }
        
        //Controlo si hay algun clima que no se haya presentado y le seteo 0 días
        this.getClimasSinPronostico(listPeriodoClimaDTO);
        
		return listPeriodoClimaDTO;
	}

	@Override
	public PicoLluviaDTO getPicoLluviaEnFuturos10Anios() {
		// TODO Auto-generated method stub
		Sort sort = new Sort("perimetro").descending();
		//PronosticoClima pronostico = pronosticoClimaRepository.findFirst10ByClima(PeriodoClimaBO.LLUVIA.getDescripcion(), sort);
		
		List<PronosticoClima> pronosticos = pronosticoClimaRepository.findFirst10ByClima(PeriodoClimaBO.LLUVIA.getDescripcion(), sort);
		
		if(pronosticos.size() > 0 ) {
			PronosticoClima pronostico = pronosticos.get(0); 
			if(pronostico!=null && pronostico.getPerimetro() > 0) {
				return new PicoLluviaDTO(pronostico.getDiaEntero(), "El perimietro del triangulo del día es: " + pronostico.getPerimetro());	
			}
		}
		
		return new PicoLluviaDTO(-1, "En los días de lluvia no se registra un pico de intesidad.");
	}
	
//	private void mapearPeriodos(Map<String, Long> map, int cantCiclos, List<PeriodoClimaDTO> listPeriodosClima ) {
//        		
//		map.forEach((k, v) -> {
//			//System.out.println(k + ":" + v);
//			PeriodoClimaDTO periodoClimaDTO = new PeriodoClimaDTO(k, v.intValue());
//			if(listPeriodosClima.contains(periodoClimaDTO)) {
//				int index = listPeriodosClima.indexOf(periodoClimaDTO);
//				listPeriodosClima.get(index).addCantidadDias((periodoClimaDTO.getCantidadDias()) );
//			} else {
//				int diasX10Anios = cantCiclos * (periodoClimaDTO.getCantidadDias());
//				periodoClimaDTO.setCantidadDias(diasX10Anios);
//				listPeriodosClima.add(periodoClimaDTO);
//			}
//		});
//    }
	
	private void mapearPeriodos2(List<PeriodoClimaDTO> listPeriodoClima, int cantCiclos, List<PeriodoClimaDTO> listPeriodosClima ) {
		
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
