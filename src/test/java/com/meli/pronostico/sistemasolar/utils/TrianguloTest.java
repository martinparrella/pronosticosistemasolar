/**
 * 
 */
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
public class TrianguloTest {

	@Test
	public void testIncluyePunto() {		
		Punto v1 = new Punto(1, 1);
        Punto v2 = new Punto(-1, 0);
        Punto v3 = new Punto(1, -1);

        Triangulo t = new Triangulo(v1, v2, v3);

        Punto p = new Punto(0,0);
        assertTrue(t.incluyePunto(p));

        p = new Punto(2,2);
        assertFalse(t.incluyePunto(p));

        p = new Punto(1,0);
        assertTrue(t.incluyePunto(p));	
	} 
	
	@Test
	public void testGetPerimetro() {		
        Punto v1 = new Punto(-2, 5);
        Punto v2 = new Punto(4, 3);
        Punto v3 = new Punto(7, -2);

        Triangulo t = new Triangulo(v1, v2, v3);
        
        //System.out.println( t.getPerimetro() );
        //System.out.println( new DecimalFormat("#0.00").format(t.getPerimetro() ) );
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        
        assertEquals("23,55", df.format(t.getPerimetro()));
	} 
}
