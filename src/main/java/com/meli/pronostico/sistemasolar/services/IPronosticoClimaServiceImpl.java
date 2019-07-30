/**
 * 
 */
package com.meli.pronostico.sistemasolar.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.pronostico.sistemasolar.bo.PeriodoClimaBO;
import com.meli.pronostico.sistemasolar.bo.PlanetaBO;
import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
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
		LOGGER.info("Ingrese al metodo pronosticarClimaSistemaSolar");
		
		this.ferengi = ferengi;
		this.betasoide = betasoide;
		this.vulcano = vulcano;
		
		//limpio la base de datos, con los pronosticos viejos.
		pronosticoClimaRepository.deleteAll();
		
		iniciarPronostico();
	
	}
	
	private void iniciarPronostico() {
	    //El ciclo dura 360 dias, despues las predicciones se repiten
	    int CICLO = Integer.parseInt(global.getCicloOrbital());
	    
	    //Tiempo entre muestreo y muestreo para pronosticar el periodo del clima
	    double FRECUENCIA_MUESTREO = Double.parseDouble(global.getFrecuenciaMuestreo()); 
	    
	    List<PronosticoClima> pronosticos = new ArrayList<PronosticoClima>();
	    
		double diaParcial = 0;
		while(diaParcial < CICLO) {
			LOGGER.info("Pronosticando dia: " + diaParcial);
			pronosticos.add(getClima(diaParcial));
			diaParcial = diaParcial + FRECUENCIA_MUESTREO;
		}
		
		//Persisto en BD
		pronosticos.forEach(pronostico -> pronosticoClimaRepository.save(pronostico));
		
	}
	
    private PronosticoClima getClima(double diaParcial){
        Punto p1 = ferengi.getPosicion(diaParcial);
        Punto p2 = betasoide.getPosicion(diaParcial);
        Punto p3 = vulcano.getPosicion(diaParcial);

        int diaEntero = (int) diaParcial;
        String diaEnteroStr = String.valueOf(diaParcial);
        if(diaEnteroStr.indexOf('.') != -1) {
        	diaEntero = Integer.parseInt(diaEnteroStr.substring(0, diaEnteroStr.indexOf('.')));
        }
        
        
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
            return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.LLUVIA.getDescripcion(), triangulo.getPerimetro());
        }

        return new PronosticoClima(diaEntero, diaParcial, PeriodoClimaBO.NORMAL.getDescripcion(), 0); //Si el triangulo no incluye al sol y no es un recta
    }

//	@Override
//	public PronosticoClimaDiaDTO findByDiaEntero(int dia) {
//		// TODO Auto-generated method stub
//		PronosticoClima pronosticoEntity = pronosticoClimaRepository.findByDiaEntero(dia);
//		return new PronosticoClimaDiaDTO(pronosticoEntity.getDiaEntero(), pronosticoEntity.getClima());
//	}
	
	@Override
	public List<PronosticoClimaDiaDTO> findByDiaEntero(int dia) {
		// TODO Auto-generated method stub
		List<PronosticoClimaDiaDTO> listPronosticoDTO = new ArrayList<PronosticoClimaDiaDTO>();
		List<PronosticoClima> listPronosticoEntity = pronosticoClimaRepository.findByDiaEntero(dia);
		listPronosticoEntity.forEach(p -> listPronosticoDTO.add(new PronosticoClimaDiaDTO(p.getDiaEntero(), p.getClima())));
		return listPronosticoDTO;
	}
	
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return pronosticoClimaRepository.count();
	}

	@Override
	public PeriodoClimaDTO findByClima(String clima) {
		// TODO Auto-generated method stub
		List<PronosticoClima> listPronosticoEntity = pronosticoClimaRepository.findByClima(clima);
		return new PeriodoClimaDTO(clima, listPronosticoEntity.size());
	}
	
}
