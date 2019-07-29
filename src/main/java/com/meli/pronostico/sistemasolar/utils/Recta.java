package com.meli.pronostico.sistemasolar.utils;


/**
 * @author martin.parrella
 * 
 * El objeto Recta representa un recta que pasa por 2 o mas puntos:
 * y = (m * x) + b
 * 
 * y donde m es la pendiente de dicha recta, y se calcula:
 * m = (y2 - y1) / (x2 - x1)
 * 
 * y donde b es la intercepcion de la recta por el eje y
 */
public class Recta {

    private double m; //pendiente
    private double b; //interseccion con y 

    private double e = 0.01;

    
    /**
     * @param p1: Punto 1 de la recta
     * @param p2: Punto 2 de la recta
     */
    public Recta(Punto p1, Punto p2) {
        //m = (y2 - y1) / (x2 - x1)
        if(Math.abs(p2.getY() - p1.getY()) < e || Math.abs(p2.getX() - p1.getX()) < e){
            m = 0;
        }else{
            m = (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
        }

        //b = y1 - (m * x1)
        b = (Math.abs((p1.getY() - (m * p1.getX()))) < e) ? 0 : (p1.getY() - (m * p1.getX()));
    }

	/**
	 * El metodo verifica si se cumple la siguiente igualdad: 
	 * y1 = (m * x1) + b
	 * 
	 * Ejemplo P1(1,3) queremos verificar si existe en la recta: y = (2 * x) + 1
	 * 
	 * 		El P1(1,3) pertenece a la recta?  => 3 = (2 * 1) + 1  => PERTENECE!!
	 * 
	 * 		El P2(2,3) pertenece a la recta?  => 3 = (2 * 2) + 1  => NO pertenece!!
	 * 
	 * @param Punto p(x,y) en el eje cartesiano
	 * 
	 * @return true si un punto pertenece a la recta, caso contrario retorna false. 
	 */
    public boolean include(Punto p){
    	
    	double y = m * p.getX() + b;
    	
    	// Verifico si es una recta horizontal, donde y == b
        if(m == 0 && Math.abs((p.getY() - b)) < e){
            return true;
        }
        
    	// Verifico si es una recta vertical, donde x == m
        if(Math.abs(m - p.getX()) < e ){
            return true;
        }
        
        //if(y == p.getY()) {
        if(Math.abs(y - p.getY()) < e){
    		return true;
    	}
        
        return false;
    }

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
     */
	public static boolean isPuntosColineales(Punto p1, Punto p2, Punto p3) {
		return (p2.getX() - p1.getX()) * (p3.getY() - p2.getY()) == (p2.getY() - p1.getY()) * (p3.getX() - p2.getX());
	}
}
