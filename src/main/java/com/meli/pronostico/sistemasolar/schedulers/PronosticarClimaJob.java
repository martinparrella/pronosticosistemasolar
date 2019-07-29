package com.meli.pronostico.sistemasolar.schedulers;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author martin.parrella
 *
 */

public class PronosticarClimaJob {
	@Scheduled(fixedRate = 3000)
	public void tarea1() {
        System.out.println("Tarea usando fixedRate cada 3 segundos - " + System.currentTimeMillis() / 1000);
    }
}
