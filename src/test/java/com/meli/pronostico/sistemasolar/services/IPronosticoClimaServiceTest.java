package com.meli.pronostico.sistemasolar.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.meli.pronostico.sistemasolar.bo.PeriodoClimaBO;
import com.meli.pronostico.sistemasolar.dto.PeriodoClimaDTO;
import com.meli.pronostico.sistemasolar.dto.PicoLluviaDTO;
import com.meli.pronostico.sistemasolar.dto.PronosticoClimaDiaDTO;
import com.meli.pronostico.sistemasolar.entities.PronosticoClima;
import com.meli.pronostico.sistemasolar.repositories.IPronosticoClimaRepository;
import com.meli.pronostico.sistemasolar.utils.Punto;

/**
 * @author martin.parrella
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IPronosticoClimaServiceTest {

	@Autowired
	private IPronosticoClimaRepository pronosticoClimaRepository;
	
	@Autowired
	private IPronosticoClimaServiceImpl pronosticoClimaService;
	
	@Before
    public void init(){
        this.pronosticoClimaRepository.deleteAll();
        this.pronosticoClimaRepository.save(new PronosticoClima(1, 1.0, PeriodoClimaBO.SEQUIA.getDescripcion(), 0));
        this.pronosticoClimaRepository.save(new PronosticoClima(2, 2.0, PeriodoClimaBO.LLUVIA.getDescripcion(), 25.66));
        this.pronosticoClimaRepository.save(new PronosticoClima(3, 3.0, PeriodoClimaBO.OPTIMO.getDescripcion(), 0));
        this.pronosticoClimaRepository.save(new PronosticoClima(4, 4.0, PeriodoClimaBO.LLUVIA.getDescripcion(), 13));
        this.pronosticoClimaRepository.save(new PronosticoClima(5, 5.0, PeriodoClimaBO.OPTIMO.getDescripcion(), 0));
        this.pronosticoClimaRepository.save(new PronosticoClima(6, 6.0, PeriodoClimaBO.NORMAL.getDescripcion(), 0));
        this.pronosticoClimaRepository.save(new PronosticoClima(7, 2.0, PeriodoClimaBO.LLUVIA.getDescripcion(), 25.66));
    }
	
    @Test
    public void testGetClimaByPosicion() throws Exception {
        double dia = 1d;
    	Punto p1 = new Punto(1, 1);
        Punto p2 = new Punto(2, 2);
        Punto p3 = new Punto(-3, -3);
        
        PronosticoClima pronostico = pronosticoClimaService.getClimaByPosicion(dia, p1, p2, p3);
        assertEquals(PeriodoClimaBO.SEQUIA.getDescripcion(), pronostico.getClima());
        
        p1 = new Punto(1, 1);
        p2 = new Punto(1, 2);
        p3 = new Punto(1, -3);
        pronostico = pronosticoClimaService.getClimaByPosicion(dia, p1, p2, p3);
        assertEquals(PeriodoClimaBO.OPTIMO.getDescripcion(), pronostico.getClima());
        
        p1 = new Punto(1, 1);
        p2 = new Punto(-1, 0);
        p3 = new Punto(1, -1);
        pronostico = pronosticoClimaService.getClimaByPosicion(dia, p1, p2, p3);
        assertEquals(PeriodoClimaBO.LLUVIA.getDescripcion(), pronostico.getClima());

        p1 = new Punto(1, 1);
        p2 = new Punto(2, 2);
        p3 = new Punto(0, 2);
        pronostico = pronosticoClimaService.getClimaByPosicion(dia, p1, p2, p3);
        assertEquals(PeriodoClimaBO.NORMAL.getDescripcion(), pronostico.getClima());
    }
    
    @Test
    public void testFindByDiaEntero() throws Exception {
    	PronosticoClimaDiaDTO pronostico = pronosticoClimaService.findByDiaEntero(1);
        assertEquals(PeriodoClimaBO.SEQUIA.getDescripcion(), pronostico.getClima());
    }
    
    @Test
    public void testCountDiasByClima() throws Exception {
    	PeriodoClimaDTO periodo = pronosticoClimaService.countDiasByClima(PeriodoClimaBO.SEQUIA.getDescripcion());
        assertEquals(1, periodo.getCantidadDias().intValue());
        
        periodo = pronosticoClimaService.countDiasByClima(PeriodoClimaBO.LLUVIA.getDescripcion());
        assertEquals(3, periodo.getCantidadDias().intValue());
        
        periodo = pronosticoClimaService.countDiasByClima(PeriodoClimaBO.OPTIMO.getDescripcion());
        assertEquals(2, periodo.getCantidadDias().intValue());
        
        periodo = pronosticoClimaService.countDiasByClima(PeriodoClimaBO.NORMAL.getDescripcion());
        assertEquals(1, periodo.getCantidadDias().intValue());        
    }
    
    @Test
    public void testGetPicoLluviaEnFuturos10Anios() throws Exception {
    	List<PicoLluviaDTO> picoLluvia = pronosticoClimaService.getPicoLluviaEnFuturos10Anios();
    	
        assertEquals(2, picoLluvia.get(0).getDia());
        
        assertNotEquals(4, picoLluvia.get(0).getDia());        
    }
    
    @Test
    public void testGetCantidadDiasDeUnClimaEnFuturos10Anios() throws Exception {
    	List<PeriodoClimaDTO> listPeriodoClimaDTO  = pronosticoClimaService.getCantidadDiasDeUnClimaEnFuturos10Anios();
    	listPeriodoClimaDTO.forEach(periodo -> {
    		switch (periodo.getClima()) {
				case "sequia":
					assertEquals(11, periodo.getCantidadDiasInt());  
					break;
				case "lluvia":
					assertNotEquals(22, periodo.getCantidadDiasInt());  
					break;
				case "optimo":
					assertNotEquals(20, periodo.getCantidadDiasInt());  
					break;
				case "normal":
					assertNotEquals(10, periodo.getCantidadDiasInt());  
					break;
				
				default:
					break;
			}
    		
    	});  
    }
    

}
