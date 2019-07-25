/**
 * 
 */
package com.meli.pronostico.sistemasolar.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author martin.parrella
 *
 */
@RestController
@RequestMapping("/")
public class RestPronosticoController {

	@GetMapping("/")
	@ResponseBody
	public String inicio() {		
		return "Sistema informático encargado de pronosticar el clima en el sistema solar de una galaxia lejana";
	}
	
//	@Autowired
//	private ClimaService climaService;	
//
//	
//	@GetMapping("/clima")
//	@ResponseBody
//	public ClimaDto getClima(@RequestParam(value="dia", defaultValue= "1") int dia) {	
//		ClimaDto climaDto = this.climaService.getClimaByDia(dia);	
//		return 	(climaDto != null) ? climaDto : new ClimaDto();
//	}
}