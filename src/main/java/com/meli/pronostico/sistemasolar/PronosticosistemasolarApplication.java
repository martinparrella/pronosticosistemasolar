package com.meli.pronostico.sistemasolar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.pronostico.sistemasolar.bo.PlanetaBO;
import com.meli.pronostico.sistemasolar.repositories.IPronosticoClimaRepository;
import com.meli.pronostico.sistemasolar.services.IPronosticoClimaServiceImpl;
import com.meli.pronostico.sistemasolar.utils.GlobalConfiguration;

@SpringBootApplication
@EnableScheduling
public class PronosticosistemasolarApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(PronosticosistemasolarApplication.class);
	
	public static final String PRONOSTICO_RESOURCE = "/api/pronostico/sistemasolar";
	
	//@Autowired
	private GlobalConfiguration global;

	@Autowired
	private IPronosticoClimaServiceImpl pronosticoClimaService;

	@Autowired
	private IPronosticoClimaRepository pronosticoClimaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PronosticosistemasolarApplication.class, args);
	}

	@Bean
	public CommandLineRunner pronosticos(IPronosticoClimaRepository pronosticoClimaRepository) {
		return (args) -> {
			//LOGGER.error("global: " + global.getCiclo());
		};
		/*
		return (args) -> {
			
			LOGGER.error("global: " + global.getCiclo());
			
	        Planeta ferengi = new Planeta("Ferengi", 1, 1, 500);
	        Planeta betasoide = new Planeta("Betasoide", 3, 1, 2000);
	        Planeta vulcano = new Planeta("Vulcano", 5, -1, 1000);
	        service.pronosticarClimaSistemaSolar(ferengi, betasoide, vulcano);
	        
			LOGGER.info("Numero de registros " + pronosticoClimaRepository.count());
			pronosticoClimaRepository.findAll().forEach(
					(prediccion) -> LOGGER.info(prediccion.toString()));
		};
		*/
	}

	/*======================================================================*/
	/* JOB de PRONOSTICO DEL CLIMA											*/
	/*======================================================================*/
	@Scheduled(fixedRateString = "${frecuencia.ejecucion.job.pronosticarClima}", initialDelay = 30000)
	public void pronosticarClimaJob() {
		LOGGER.info("=================================================");
		LOGGER.info("INICIA PROCESO: pronosticarClima");
		LOGGER.info("=================================================");
		
        PlanetaBO ferengi = new PlanetaBO("Ferengi", 1, 1, 500);
        PlanetaBO betasoide = new PlanetaBO("Betasoide", 3, 1, 2000);
        PlanetaBO vulcano = new PlanetaBO("Vulcano", 5, -1, 1000);
        pronosticoClimaService.pronosticarClimaSistemaSolar(ferengi, betasoide, vulcano);
        
		LOGGER.info("Numero de registros " + pronosticoClimaService.count());
		//pronosticoClimaService.findAll().forEach((prediccion) -> LOGGER.info(prediccion.toString()));
		
		LOGGER.info("===================================================");
		LOGGER.info("FINALIZA PROCESO: pronosticarClima");
		LOGGER.info("===================================================");
		
    }

//	@GetMapping("/")
//	@ResponseBody
//	public ResponseEntity<RestResponseMessage> inicio() {
//		StringBuilder mensaje = new StringBuilder();
//		mensaje.append(
//				"Sistema informático encargado de pronosticar el clima en el sistema solar de una galaxia lejana. ");
//
//		int ciclo = Integer.parseInt(global.getCicloOrbital());
//		if (pronosticoClimaService.count() < ciclo) {
//
//			mensaje.append("El sistema actualmente esta pronosticando el clima. ");
//			mensaje.append("Aguarde un instante e intente nuevamente. Muchas gracias!! ");
//			throw new SistemaPronosticandoException(mensaje.toString());
//		}
//		RestResponseMessage apiResponse = new RestResponseMessage(HttpStatus.OK, mensaje.toString());
//		return new ResponseEntity<RestResponseMessage>(apiResponse, apiResponse.getStatus());
//	}
//
//	/**
//	 *
//	 * Ej: http://localhost:8080/api/pronostico/sistemasolar/clima?dia=20
//	 * 
//	 * @param dia
//	 * @return la pronostico del clima del día consultado.
//	 */
//	@RequestMapping("/clima")
//	public ResponseEntity<PronosticoClimaDiaDTO> clima(@RequestParam(value = "dia") String dia) {
//		
//		try {
//			int diaInt = Integer.valueOf(dia).intValue();
//			
//			//Valido que el dia pasado como parametro sea un dia valido
//			if(0 > diaInt || diaInt > global.getCantidadDiasFuturos()) {
//				StringBuilder mensaje = new StringBuilder("El parametro dia no es valido. ");
//				mensaje.append("Los valores permitidos para el parametro dia son numeros enteros entre ");
//				mensaje.append("0 y ").append(global.getCantidadDiasFuturos());
//				throw new DiaNotFoundException(mensaje.toString());
//			}
//			
//			int ciclo = Integer.parseInt(global.getCicloOrbital());
//			
//			if (pronosticoClimaService.count() < ciclo) {
//				StringBuilder mensaje = new StringBuilder();
//				mensaje.append("El sistema actualmente esta pronosticando el clima. ");
//				mensaje.append("Aguarde un instante e intente nuevamente. Muchas gracias!! ");
//				throw new SistemaPronosticandoException(mensaje.toString());
//			}
//			
//			int modDiaCiclo = diaInt % ciclo;
//			//PronosticoClimaDiaDTO p = pronosticoClimaService.findByDiaEntero((modDiaCiclo==0)?ciclo:modDiaCiclo);
//			PronosticoClimaDiaDTO p = pronosticoClimaService.findByDiaEntero(modDiaCiclo);
//			p.setDia(diaInt); //Seteo el día que fue consultado por parámetro
//			return new ResponseEntity<PronosticoClimaDiaDTO>(p, HttpStatus.OK);
//		
//		} catch (NumberFormatException e){
//			throw e;
//		} catch (Exception e) {
//			throw e;
//		}
//	}	
}

//Add the controller
@RestController
class Helloworld {
	@GetMapping("/")
	@ResponseBody
	public String inicio() {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(
				"Sistema informático encargado de pronosticar el clima en el sistema solar de una galaxia lejana. ");

		return mensaje.toString();
		
	}
}
