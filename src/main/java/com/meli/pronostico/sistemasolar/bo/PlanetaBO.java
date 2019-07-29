package com.meli.pronostico.sistemasolar.bo;

import com.meli.pronostico.sistemasolar.utils.Punto;

/**
 * @author martin.parrella
 *
 */
public class PlanetaBO {

    private String nombre;
    private int velAngular; //medido en grados x dia
    private int sentido; //horario=1 o anti-horario=-1
    private int distanciaAlSol; //radio

    /**
     * @param nombre
     * @param velAngular
     * @param sentido
     * @param distanciaAlSol
     */
    public PlanetaBO(String nombre, int velAngular, int sentido, int distanciaAlSol) {
        this.nombre = nombre;
        this.velAngular = velAngular;
        this.sentido = sentido;
        this.distanciaAlSol = distanciaAlSol;
    }

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
    
    
    /**
     * El metodo calcula el angulo en un tiempo determinado en radianes
     * Un Radian es equivalente a (1° * (Math.PI / 180 ))
     * 
     * @param tiempo: determinado en el que se quiere calcular el angulo
     * @return El angulo que se forma en determinado tiempo en radianes
     */
    private double getAngulo(double tiempo){
    	
    	//double deg = ((sentido * dia * velAngular) * Math.PI) / 180; // Vulcano= -1 * 1 * 5
    	double deg = (sentido * tiempo * velAngular) % 360; 
        return Math.toRadians(deg);
    }

    /**
     * @param tiempo: determinado en el que se quiere calcular la posición del planeta en el eje cartesiano.
     * @return El Punto p(x,y) donde se ubica el planeta.
     */
    public Punto getPosicion(double tiempo){
        double x = Math.round(distanciaAlSol * Math.sin(getAngulo(tiempo)));
        double y = Math.round(distanciaAlSol * Math.cos(getAngulo(tiempo)));
        return new Punto(x,y);
    }
    
}
