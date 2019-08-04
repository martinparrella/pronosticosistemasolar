package com.meli.pronostico.sistemasolar.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author martin.parrella
 *
 */
public class Triangulo {

    private Punto v1; //Vertices 1 del triangulo
    private Punto v2; //Vertices 2 del triangulo
    private Punto v3; //Vertices 3 del triangulo

    /**
     * @param p1: Punto p1(x1,y1)
     * @param p2: Punto p2(x2,y2)
     * @param p3: Punto p3(x3,y3)
     */
    public Triangulo(Punto p1, Punto p2, Punto p3) {
        this.v1 = p1;
        this.v2 = p2;
        this.v3 = p3;
    }

    /**
     * Metodo que calcula el perímetro del triangulo
     * @return El perimetro del triangulo
     */
    public double getPerimetro() {

    	double d12 = GeometriaUtils.calcularDistanciaEntrePuntos(v1,v2);
    	double d23 = GeometriaUtils.calcularDistanciaEntrePuntos(v2,v3);
    	double d13 = GeometriaUtils.calcularDistanciaEntrePuntos(v1,v3);
        
    	List<Double> lados = new ArrayList<Double>();
    	lados.add(d12);
    	lados.add(d23);
    	lados.add(d13);
    	
    	return GeometriaUtils.calcularPerimetro(lados);
    }

    /**
     * El metodo verifica si un punto pertenece y esta dentro del triangulo.
     * En este caso, el punto recibido es el sol, que esta en el centro
     * de la galaxia => Psol(0,0)
     * @param sol: Punto p(x,y) en el eje cartesiano. 
     * @return true si el punto esta dentro del triangulo, caso contrario retorna false.
     */
    public boolean incluyePunto(Punto sol) {
        boolean b1, b2, b3;

        b1 = sign(sol, v1, v2) < 0.0;
        b2 = sign(sol, v2, v3) < 0.0;
        b3 = sign(sol, v3, v1) < 0.0;

        return ((b1 == b2) && (b2 == b3));
    }

    private double sign(Punto p1, Punto p2, Punto p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

}
