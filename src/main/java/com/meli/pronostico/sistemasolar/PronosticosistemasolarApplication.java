package com.meli.pronostico.sistemasolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class PronosticosistemasolarApplication {
	
	public static void main(String[] args) {
//		SpringApplication.run(PronosticosistemasolarApplication.class, args);
		
        ApplicationContext ctx = SpringApplication.run(PronosticosistemasolarApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}

}
