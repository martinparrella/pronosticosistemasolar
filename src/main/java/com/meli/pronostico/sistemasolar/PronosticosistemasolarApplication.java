package com.meli.pronostico.sistemasolar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

import com.meli.pronostico.sistemasolar.repositories.IPronosticoClimaRepository;

@SpringBootApplication
public class PronosticosistemasolarApplication {
	
	public static void main(String[] args) {
//		SpringApplication.run(PronosticosistemasolarApplication.class, args);
		
        ApplicationContext ctx = SpringApplication.run(PronosticosistemasolarApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}

	@Bean
	public CommandLineRunner pronosticos(IPronosticoClimaRepository pronosticoClimaRepository) {
		return (args) -> {
			//LOGGER.error("global: " + global.getCiclo());
		};

	}

}

//@RestController
//class InitController {
//	@GetMapping("/")
//	@ResponseBody
//	public String inicio() {
//		StringBuilder mensaje = new StringBuilder();
//		mensaje.append(
//				"Sistema informático encargado de pronosticar el clima en el sistema solar de una galaxia lejana. ");
//
//		return mensaje.toString();
//		
//	}
//}
