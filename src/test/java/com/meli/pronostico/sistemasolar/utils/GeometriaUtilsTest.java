package com.meli.pronostico.sistemasolar.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author martin.parrella
 *
 */
@RunWith(SpringRunner.class)
public class GeometriaUtilsTest {

	@Test
	public void testIsPuntosColineales() {
		Punto p1 = new Punto(1, 1);
        Punto p2 = new Punto(-1, -1);
        Punto pSol = GeometriaUtils.UBICACION_SOL;
		
		assertTrue(GeometriaUtils.isPuntosColineales(p1, p2, pSol));
		
		p1 = new Punto(1, 2);
        p2 = new Punto(-1, -1);
        
        assertFalse(GeometriaUtils.isPuntosColineales(p1, p2, pSol));
	}
	
	@Test
	public void testCalcularDistanciaEntrePuntos(){
		Punto p1 = new Punto(1, 1);
        Punto p2 = new Punto(-1, -1);
        
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        
        double d = GeometriaUtils.calcularDistanciaEntrePuntos(p1, p2);
        assertEquals("2,82", df.format(d));
        
        p1 = new Punto(1, 2);
        p2 = new Punto(-1, -1);
        
        d = GeometriaUtils.calcularDistanciaEntrePuntos(p1, p2);
        //System.out.println("Distancia = " + GeometriaUtils.calcularDistanciaEntrePuntos(p1, p2));  //Distancia = 3.605551275463989
        assertEquals("3,60", df.format(d));
        
        p1 = new Punto(1, 5);
        p2 = new Punto(-1, -5);
        
        d = GeometriaUtils.calcularDistanciaEntrePuntos(p1, p2);
        //System.out.println("Distancia = " + GeometriaUtils.calcularDistanciaEntrePuntos(p1, p2));  //Distancia = 10.198039027185569
        assertEquals("10,19", df.format(d));
        
	}
}
