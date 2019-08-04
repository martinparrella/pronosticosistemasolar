package com.meli.pronostico.sistemasolar.utils;

import java.util.List;

/**
 * @author martin.parrella
 *
 */
public class GeometriaUtils {

	public static final Punto UBICACION_SOL = new Punto(0d, 0d); 
	
    /**
     * Metodo que valida si 3 puntos son colineales.
     * Dados 3 puntos P1(x1,y1), P2(x2,y2), P3(x3,p3) 
     * la PendienteA(P1 a P2) = PendienteB(P2 a P3)
     * PendienteA(P1 a P2) = (y2 - y1) / (x2 - x1)
     * PendienteB(P2 a P3) = (y3 - y2) / (x3 - x2)
     * => (x2 - x1) * (y3 - y2) = (y2 - y1) * (x3 - x2)
     * @param p1
     * @param p2
     * @param p3
     * @return Retorna True si estan alineado, caso contrario retorna False. 
     * Al retornar False, podemos deducir que los tres planetas no están alineados, entonces forman entre sí un triángulo.
     */
	public static boolean isPuntosColineales(Punto p1, Punto p2, Punto p3) {
		return (p2.getX() - p1.getX()) * (p3.getY() - p2.getY()) == (p2.getY() - p1.getY()) * (p3.getX() - p2.getX());
	}
	
    /**
     * El metodo calcula la distancia entre dos puntos.
     * Aplicando el teorema de pitagoras, calculamos este dato, donde: 
     * distancia = sqrt((x2-x19)^2+(y2-y1)^2)
     * @param p1: Punto P1(x,y)
     * @param p2: Punto P2(x,y)
     * @return Distancia entre dos puntos
     */
    public static double calcularDistanciaEntrePuntos(Punto p1, Punto p2){
        return Math.sqrt(Math.pow((p2.getX() - p1.getX()),2) + Math.pow((p2.getY() - p1.getY()),2));
    }
    

	/**
     * Metodo que valida si los puntos forman un triangulo
     * @return True si es triangulo, caso contrario False.
     */
	public static boolean isTriangulo(Punto p1, Punto p2, Punto p3) {
		return !isPuntosColineales(p1, p2, p3);
	}
	
    /**
     * Metodo que calcula el perímetro de un poligono
     * @return El perimetro de un poligono
     */
    public static double calcularPerimetro(List lados) {

    	double perimetro = 0d;
    	for(int i=0; i < lados.size(); i++) {
    		perimetro += (double) lados.get(i);
    	}

        return perimetro;
    }
}
