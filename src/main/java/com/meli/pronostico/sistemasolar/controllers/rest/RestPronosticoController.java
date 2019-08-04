package com.meli.pronostico.sistemasolar.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.pronostico.sistemasolar.bo.PeriodoClimaBO;
import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
import com.meli.pronostico.sistemasolar.dto.PicoLluviaDTO;
import com.meli.pronostico.sistemasolar.dto.PronosticoClimaDTO;
import com.meli.pronostico.sistemasolar.dto.PronosticoClimaDiaDTO;
import com.meli.pronostico.sistemasolar.exceptions.ClimaNotFoundException;
import com.meli.pronostico.sistemasolar.exceptions.DiaNotFoundException;
import com.meli.pronostico.sistemasolar.exceptions.SistemaPronosticandoException;
import com.meli.pronostico.sistemasolar.services.IPronosticoClimaService;
import com.meli.pronostico.sistemasolar.utils.GlobalConfiguration;

/**
 * @author martin.parrella
 *
 */
@RestController
@RequestMapping(RestPronosticoController.PRONOSTICO_RESOURCE)
public class RestPronosticoController {

	public static final String PRONOSTICO_RESOURCE = "/api/sistemasolar/pronostico";

	@Autowired
	private GlobalConfiguration global;

	@Autowired
	private IPronosticoClimaService pronosticoClimaService;

	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<CustomMessageJson> inicio() {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(
				"Sistema informático encargado de pronosticar el clima en el sistema solar de una galaxia lejana. ");

		int ciclo = Integer.parseInt(global.getCicloOrbital());
		if (pronosticoClimaService.count() < ciclo) {

			mensaje.append("El sistema actualmente esta pronosticando el clima. ");
			mensaje.append("Aguarde un instante e intente nuevamente. Muchas gracias!! ");
			throw new SistemaPronosticandoException(mensaje.toString());
		}
		
		CustomMessageJson apiResponse = new CustomMessageJson(HttpStatus.OK.value(),
                HttpStatus.OK,
                null,
                mensaje.toString(),
                PRONOSTICO_RESOURCE,
                null);
		
		return new ResponseEntity<CustomMessageJson>(apiResponse, apiResponse.getStatusMessage());
	}

//	/**
//	 *
//	 * Ej: http://localhost:8080/api/pronostico/sistemasolar/clima?dia=20
//	 * 
//	 * @param dia
//	 * @return el pronostico del clima del día consultado.
//	 */
//	@RequestMapping("/clima")
//	public ResponseEntity<PronosticoClimaDiaDTO> clima(@RequestParam(value = "dia") String dia) {
//		
//		try {
//			int ciclo = Integer.parseInt(global.getCicloOrbital());
//			
//			isSistemaPronosticando(ciclo);
//
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
//			int modDiaCiclo = diaInt % ciclo;
//			//PronosticoClimaDiaDTO p = pronosticoClimaService.findByDiaEntero((modDiaCiclo==0)?ciclo:modDiaCiclo);
//			
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

	/**
	 *
	 * Ej: http://localhost:8080/api/sistemasolar/pronostico/clima?dia=20
	 * 
	 * @param dia
	 * @return el pronostico del clima del día consultado. 
	 * Si la frecuencia de muestreo no es XDIA, se muestra en el json
	 * las horas o los minutos o los segundos, de muestreo del dia. 
	 * Segun el parámetro "frecuencia.muestreo" del archivo de configuración XHORA, XMINUTO, XSEGUNDO.
	 */
	@RequestMapping("/clima")
	public ResponseEntity<PronosticoClimaDiaDTO> clima(@RequestParam(value = "dia") String dia) {

		try {
			int ciclo = Integer.parseInt(global.getCicloOrbital());

			//valido que al consumir este recurso, el sistema no este pronosticando el clima
			this.isSistemaPronosticando(ciclo);
			
			int diaInt = Integer.valueOf(dia).intValue();

			// Valido que el dia pasado como parametro sea un dia valido
			if (0 > diaInt || diaInt > global.getCantidadDiasFuturos()) {
				StringBuilder mensaje = new StringBuilder("El parametro dia no es valido. ");
				mensaje.append("Los valores permitidos para el parametro dia son numeros enteros entre ");
				mensaje.append("0 y ").append(global.getCantidadDiasFuturos());
				throw new DiaNotFoundException(mensaje.toString());
			}

			int modDiaCiclo = diaInt % ciclo;

			List<PronosticoClimaDiaDTO> pronosticos = pronosticoClimaService.findDistinctClimaByDiaEntero(modDiaCiclo);
			pronosticos.forEach(pronostico -> pronostico.setDia(diaInt)); //Seteo el día que fue consultado por parámetro

			PronosticoClimaDiaDTO pronostico = new PronosticoClimaDiaDTO();
			if(pronosticos.size() == 1) {
				pronostico = pronosticos.get(0);
				pronostico.setHora(null);
			} else {
				pronostico.setClimas(pronosticos);	
			}
			
			return new ResponseEntity<PronosticoClimaDiaDTO>(pronostico, HttpStatus.OK);

		} catch (NumberFormatException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	

	/**
	 * Ej: http://localhost:8080/api/sistemasolar/pronostico/en-una-decada/cantidad-diasxclima
	 * 
	 * @return la cantidad de dias de cierto clima (sequia, lluvia, optimo, normal)
	 *         en los proximos 10 años.
	 */
	@RequestMapping("/en-una-decada/cantidad-diasxclima")
	public ResponseEntity<List<PeriodoClimaDTO>> pronostico() {
		try {
			int ciclo = Integer.parseInt(global.getCicloOrbital());

			//valido que al consumir este recurso, el sistema no este pronosticando el clima
			this.isSistemaPronosticando(ciclo);

			List<PeriodoClimaDTO> listPeriodosClima = pronosticoClimaService.getCantidadDiasDeUnClimaEnFuturos10Anios();

			return new ResponseEntity<List<PeriodoClimaDTO>>(listPeriodosClima, HttpStatus.OK);

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Ej:
	 * http://localhost:8080/api/sistemasolar/pronostico/en-una-decada/pico-maximo-intensidad-lluvia
	 * 
	 * @return el pico maximo de lluvia en los proximos 10 años.
	 */
	@RequestMapping("/en-una-decada/pico-maximo-intensidad-lluvia")
	public ResponseEntity<PicoLluviaDTO> picoLluviaMaximaIntensidad() {
		try {
			int ciclo = Integer.parseInt(global.getCicloOrbital());
			
			//valido que al consumir este recurso, el sistema no este pronosticando el clima
			this.isSistemaPronosticando(ciclo);

			PicoLluviaDTO picoLluvia = pronosticoClimaService.getPicoLluviaEnFuturos10Anios();

			return new ResponseEntity<PicoLluviaDTO>(picoLluvia, HttpStatus.OK);

		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Ej:
	 * http://localhost:8080/api/sistemasolar/pronostico/en-una-decada/cantidad-diasxclima-con-pico-lluvia
	 * 
	 * @return la cantidad de dias de cierto clima (sequia, lluvia, optimo, normal) y el dia con pico maximos de lluvia
	 *         para los próximos 10 años 
	 */
	// @RequestMapping("/pronostico-extendido-10-anios-con-pico-lluvia")
	@RequestMapping("/en-una-decada/cantidad-diasxclima-con-pico-lluvia")
	public ResponseEntity<PronosticoClimaDTO> pronosticoConPicoLluvia() {
		try {
			int ciclo = Integer.parseInt(global.getCicloOrbital());

			//valido que al consumir este recurso, el sistema no este pronosticando el clima
			this.isSistemaPronosticando(ciclo);

			List<PeriodoClimaDTO> listPeriodosClima = pronosticoClimaService.getCantidadDiasDeUnClimaEnFuturos10Anios();

			PicoLluviaDTO picoLluvia = pronosticoClimaService.getPicoLluviaEnFuturos10Anios();

			PronosticoClimaDTO pronosticoClimaDTO = new PronosticoClimaDTO(listPeriodosClima, picoLluvia);

			return new ResponseEntity<PronosticoClimaDTO>(pronosticoClimaDTO, HttpStatus.OK);

		} catch (Exception e) {
			throw e;
		}
	}
	
    	
	/*==============================================================================================*/
	/*=====  COMMING SOOM!!!  Recursos de la API aún en construcción y test:                  ======*/
	/*==============================================================================================*/
	/**
	 * Ej: http://localhost:8080/api/sistemasolar/pronostico/anual/periodo?clima=lluvia
	 * 
	 * @param clima
	 * @return la cantidad de dias de cierto clima dentro de un ciclo. tipo de
	 *         clima: sequia, lluvia, optimo, normal
	 */
	//@RequestMapping("/anual/periodo")
	public ResponseEntity<PeriodoClimaDTO> periodo(@RequestParam(value = "clima") String clima) {
		try {
			int ciclo = Integer.parseInt(global.getCicloOrbital());

			//valido que al consumir este recurso, el sistema no este pronosticando el clima
			this.isSistemaPronosticando(ciclo);

			// Valido que el clima pasado como parametro sea un clima valido
			if (clima==null || clima.isEmpty()) {
				
				throw new ClimaNotFoundException( this.buildMessageClimaNotFound() );
			}
			
			//Valido que el parametro clima este dentro de los valores permitidos:
			PeriodoClimaBO.valueOf(clima.toUpperCase());
			
			PeriodoClimaDTO dias = pronosticoClimaService.countDiasByClima(clima.toLowerCase()); // en un ciclo
			return new ResponseEntity<PeriodoClimaDTO>(dias, HttpStatus.OK);
			 
		} catch (IllegalArgumentException e) {
			throw new ClimaNotFoundException( this.buildMessageClimaNotFound() );
		} catch (Exception e) {
			throw e;
		}
	}


	private String buildMessageClimaNotFound() {
		StringBuilder mensaje = new StringBuilder("El parametro clima no es valido. ");
		mensaje.append("Los valores permitidos para el parametro clima son: ");
		mensaje.append(PeriodoClimaBO.SEQUIA.getDescripcion()).append(", ");
		mensaje.append(PeriodoClimaBO.LLUVIA.getDescripcion()).append(", ");
		mensaje.append(PeriodoClimaBO.OPTIMO.getDescripcion()).append(", ");
		mensaje.append(PeriodoClimaBO.NORMAL.getDescripcion());
		return mensaje.toString();
	}
	
	private void isSistemaPronosticando(int ciclo) {
		ciclo = ciclo * global.getFrecuenciaMuestreoDivisor();
		if (pronosticoClimaService.count() < ciclo) {
			StringBuilder mensaje = new StringBuilder();
			mensaje.append("El sistema actualmente esta pronosticando el clima. ");
			mensaje.append("Aguarde un instante e intente nuevamente. Muchas gracias!! ");
			throw new SistemaPronosticandoException(mensaje.toString());
		}
	}
}