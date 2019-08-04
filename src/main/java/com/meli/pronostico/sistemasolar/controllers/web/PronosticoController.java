package com.meli.pronostico.sistemasolar.controllers.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
import com.meli.pronostico.sistemasolar.dto.PicoLluviaDTO;
import com.meli.pronostico.sistemasolar.services.IPronosticoClimaService;
import com.meli.pronostico.sistemasolar.utils.GlobalConfiguration;

/**
 * @author martin.parrella
 *
 */
@Controller
public class PronosticoController {

	@Autowired
	private GlobalConfiguration global;
	
	@Autowired
	private IPronosticoClimaService pronosticoClimaService;
	
	
	@GetMapping("/")
	public String inicio(Model model) {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("Sistema informático encargado de pronosticar el clima en el sistema solar de una galaxia lejana.");
		
		model.addAttribute("mensaje", mensaje.toString());
        return "home";
		
	}
	
    @GetMapping("/pronostico")
    public String greeting(Model model) {
    	
    	int ciclo = Integer.parseInt(global.getCicloOrbital());
		ciclo = ciclo * global.getFrecuenciaMuestreoDivisor();
		if (pronosticoClimaService.count() < ciclo) {
			return "sistema-pronosticando";
		}
    	
    	List<PeriodoClimaDTO> listPeriodosClima = pronosticoClimaService.getCantidadDiasDeUnClimaEnFuturos10Anios();

//		List<String> pronosticos = new ArrayList<String>();
//		listPeriodosClima.forEach(pc -> {
//			mensaje.append("Clima: " ).append(pc.getClima().toUpperCase()).append(" - Cantidad de días: ").append(pc.getCantidadDiasInt()).append("\n");
//			pronosticos.add(mensaje.toString());
//		});
		
		model.addAttribute("pronosticos", listPeriodosClima);
		
		PicoLluviaDTO picoLluvia = pronosticoClimaService.getPicoLluviaEnFuturos10Anios();
    	
		model.addAttribute("picoLluvia", picoLluvia);
		
        return "pronostico";
    }
}
