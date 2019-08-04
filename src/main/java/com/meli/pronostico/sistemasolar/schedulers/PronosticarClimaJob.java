package com.meli.pronostico.sistemasolar.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.meli.pronostico.sistemasolar.bo.PlanetaBO;
import com.meli.pronostico.sistemasolar.services.IPronosticoClimaServiceImpl;

/**
 * @author martin.parrella
 *
 */
@Configuration
@EnableScheduling
public class PronosticarClimaJob {

	private static Logger LOGGER = LoggerFactory.getLogger(PronosticarClimaJob.class);
	
	@Autowired
	private IPronosticoClimaServiceImpl pronosticoClimaService;
	
	
	@Scheduled(fixedRateString = "${frecuencia.ejecucion.job.pronosticarClima}", initialDelay = 30000)
	public void pronosticarClima() {
		LOGGER.info("=================================================");
		LOGGER.info("INICIA PROCESO: pronosticarClima");
		LOGGER.info("=================================================");
		
        PlanetaBO ferengi = new PlanetaBO("Ferengi", 1, 1, 500);
        PlanetaBO betasoide = new PlanetaBO("Betasoide", 3, 1, 2000);
        PlanetaBO vulcano = new PlanetaBO("Vulcano", 5, -1, 1000);
        
        System.out.println("pronosticoClimaService: " + pronosticoClimaService);
        
        pronosticoClimaService.pronosticarClimaSistemaSolar(ferengi, betasoide, vulcano);
        
		LOGGER.info("Numero de registros " + pronosticoClimaService.count());
		//pronosticoClimaService.findAll().forEach((prediccion) -> LOGGER.info(prediccion.toString()));
		
		LOGGER.info("===================================================");
		LOGGER.info("FINALIZA PROCESO: pronosticarClima");
		LOGGER.info("===================================================");
		
    }
}
