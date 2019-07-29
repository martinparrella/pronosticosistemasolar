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

import com.meli.pronostico.sistemasolar.controllers.rest.exceptions.RestResponseMessage;
import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
import com.meli.pronostico.sistemasolar.dto.PronosticoClimaDiaDTO;
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

	public static final String PRONOSTICO_RESOURCE = "/api/pronostico/sistemasolar";

	@Autowired
	private GlobalConfiguration global;

	@Autowired
	private IPronosticoClimaService pronosticoClimaService;

	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<RestResponseMessage> inicio() {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(
				"Sistema informático encargado de pronosticar el clima en el sistema solar de una galaxia lejana. ");

		int ciclo = Integer.parseInt(global.getCicloOrbital());
		if (pronosticoClimaService.count() < ciclo) {

			mensaje.append("El sistema actualmente esta pronosticando el clima. ");
			mensaje.append("Aguarde un instante e intente nuevamente. Muchas gracias!! ");
			throw new SistemaPronosticandoException(mensaje.toString());
		}
		RestResponseMessage apiResponse = new RestResponseMessage(HttpStatus.OK, mensaje.toString());
		return new ResponseEntity<RestResponseMessage>(apiResponse, apiResponse.getStatus());
	}

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
	 * Ej: http://localhost:8080/api/pronostico/sistemasolar/clima?dia=20
	 * 
	 * @param dia
	 * @return la pronostico del clima del día consultado.
	 */
	@RequestMapping("/clima")
	public ResponseEntity<List<PronosticoClimaDiaDTO>> clima(@RequestParam(value = "dia") String dia) {
		
		try {
			int diaInt = Integer.valueOf(dia).intValue();
			
			//Valido que el dia pasado como parametro sea un dia valido
			if(0 > diaInt || diaInt > global.getCantidadDiasFuturos()) {
				StringBuilder mensaje = new StringBuilder("El parametro dia no es valido. ");
				mensaje.append("Los valores permitidos para el parametro dia son numeros enteros entre ");
				mensaje.append("0 y ").append(global.getCantidadDiasFuturos());
				throw new DiaNotFoundException(mensaje.toString());
			}
			
			int ciclo = Integer.parseInt(global.getCicloOrbital());
			
			if (pronosticoClimaService.count() < ciclo) {
				StringBuilder mensaje = new StringBuilder();
				mensaje.append("El sistema actualmente esta pronosticando el clima. ");
				mensaje.append("Aguarde un instante e intente nuevamente. Muchas gracias!! ");
				throw new SistemaPronosticandoException(mensaje.toString());
			}
			
			int modDiaCiclo = diaInt % ciclo;
			//PronosticoClimaDiaDTO p = pronosticoClimaService.findByDiaEntero((modDiaCiclo==0)?ciclo:modDiaCiclo);
			
			List<PronosticoClimaDiaDTO> p = pronosticoClimaService.findByDiaEntero(modDiaCiclo);
			p.forEach(pronostico -> pronostico.setDia(diaInt));
			//p.setDia(diaInt); //Seteo el día que fue consultado por parámetro
			return new ResponseEntity<List<PronosticoClimaDiaDTO>>(p, HttpStatus.OK);
		
		} catch (NumberFormatException e){
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
    /**
     * Ej: http://localhost:8080/periodo?clima=lluvia&&años=1 o http://localhost:8080/periodo?clima=Optimo
     * @param clima
     * @param anio
     * @return la cantidad de dias de cierto clima dentro de los años.
     * tipo de clima: lluvia, sequia, optimo
     * por default el calculo se hace por 10 años.
     */
    @RequestMapping("/periodo")
    public ResponseEntity<PeriodoClimaDTO> periodo(@RequestParam(value="clima") String clima){
        try {
        	PeriodoClimaDTO dias = pronosticoClimaService.findByClima(clima); //un ciclo
//            int cantidad = 0;
//            if(dias.size() > 0){
//                int d = Integer.valueOf(anio).intValue() * diasXanio;
//                int cantCiclos = getCiclos(d);
//                int diasExtra = getDiasExtra(d, cantCiclos);
//                cantidad = dias.size() * cantCiclos;
//                if(diasExtra > 0){
//                    dias = repository.findByDiaLessThanAndClima(diasExtra, clima);
//                    cantidad += dias.size();
//                }
//            }
            return new ResponseEntity(dias, HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException("El parametro que estas pasando como año no es valido.");
        }
    }	
}