package com.meli.pronostico.sistemasolar.utils;

/**
 * @author martin.parrella
 *
 */
public class Punto {

    private double x;
    private double y;

    /**
     * @param x
     * @param y
     */
    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Punto [x=" + x + ", y=" + y + "]";
	}
}
