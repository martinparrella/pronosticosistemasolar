package com.meli.pronostico.sistemasolar;

import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.meli.pronostico.sistemasolar.entities.PronosticoClima;
import com.meli.pronostico.sistemasolar.repositories.PronosticoClimaRepository;

@SpringBootApplication
public class PronosticosistemasolarApplication {

	private static final Log LOGGER = LogFactory.getLog(PronosticosistemasolarApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PronosticosistemasolarApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner pronosticos(PronosticoClimaRepository pronosticoClimaRepository) {
		return (args) -> {
			pronosticoClimaRepository.deleteAll();

			Stream.of(new PronosticoClima(1, "SEQUIA", 0d),
					new PronosticoClima(2, "OPTIMO", 5d),
					new PronosticoClima(3, "LLUVIA", 10d))
					.forEach(pronosticoClimaRepository::save);

			LOGGER.info("Numero de registros " + pronosticoClimaRepository.count());
			pronosticoClimaRepository.findAll().forEach(
					(prediccion) -> LOGGER.info(prediccion.toString())
			);
		};
	}
	
}
