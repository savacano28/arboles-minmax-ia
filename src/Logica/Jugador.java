/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.awt.Point;
import java.util.ArrayList;

public class Jugador {

    private int cantidadCesped; // cantidad de cesped recogido
    private int cantidadFlor; // cantidad de flores recogidas
    private Point posicion;
    private Point contrincante;

    public Jugador(Point posicion) {
        this.cantidadCesped = 0;
        this.cantidadFlor = 0;
        this.posicion = posicion;
    }

    public Jugador(int cantidadCesped, int cantidadFlor, Point posicion, Point contrincante) {
        this.cantidadCesped = cantidadCesped;
        this.cantidadFlor = cantidadFlor;
        this.posicion = posicion;
        this.contrincante = contrincante;
    }
    
    public Jugador(int cantidadCesped, int cantidadFlor, Point posicion) {
        this.cantidadCesped = cantidadCesped;
        this.cantidadFlor = cantidadFlor;
        this.posicion = posicion;
    }

    public void setContrincante(Point contrincante) {
        this.contrincante = contrincante;
    }

    public Point getPosicion() {
        return posicion;
    }

    public void masCesped() {
        cantidadCesped = cantidadCesped + 1;
    }

    public void masFlor() {
        cantidadFlor = cantidadFlor + 3;
    }
    
    public int getTotalPuntos(){
        return cantidadCesped + cantidadFlor;
    }

    public int getCantidadFlor() {
        return cantidadFlor;
    }

    public int getCantidadCesped() {
        return cantidadCesped;
    }

    private boolean verificarPosicion(Point punto) {
        if (punto.getX() > 7 || punto.getX() < 0 || punto.getY() > 7 || punto.getY() < 0 || (contrincante.getX() == punto.getX() && contrincante.getY() == punto.getY())) {
            return false;
        }
        return true;
    }

    public ArrayList<Point> obtenerJugadasPosibles() {
        System.out.println("PUNTO:" + posicion.getX()+" - "+posicion.getY());
        ArrayList<Point> jugadasPosibles = new ArrayList<Point>();

        Point copiaPosicion = (Point)posicion.clone();
        copiaPosicion.translate(2, 1);
        //System.out.println(copiaPosicion.getX());
        //System.out.println(copiaPosicion.getY());
        if (verificarPosicion(copiaPosicion) == true) {
            jugadasPosibles.add(copiaPosicion);
        }

        copiaPosicion = (Point)posicion.clone();
        copiaPosicion.translate(2, -1);
        if (verificarPosicion(copiaPosicion) == true) {
            jugadasPosibles.add(copiaPosicion);
        }

        copiaPosicion = (Point)posicion.clone();
        copiaPosicion.translate(-2, 1);
        if (verificarPosicion(copiaPosicion) == true) {
            jugadasPosibles.add(copiaPosicion);
        }

        copiaPosicion = (Point)posicion.clone();
        copiaPosicion.translate(-2, -1);
        if (verificarPosicion(copiaPosicion) == true) {
            jugadasPosibles.add(copiaPosicion);
        }

        copiaPosicion = (Point)posicion.clone();
        copiaPosicion.translate(1, 2);
        if (verificarPosicion(copiaPosicion) == true) {
            jugadasPosibles.add(copiaPosicion);
        }

        copiaPosicion = (Point)posicion.clone();
        copiaPosicion.translate(1, -2);
        if (verificarPosicion(copiaPosicion) == true) {
            jugadasPosibles.add(copiaPosicion);
        }

        copiaPosicion = (Point)posicion.clone();
        copiaPosicion.translate(-1, 2);
        if (verificarPosicion(copiaPosicion) == true) {
            jugadasPosibles.add(copiaPosicion);
        }

        copiaPosicion = (Point)posicion.clone();
        copiaPosicion.translate(-1, -2);
        if (verificarPosicion(copiaPosicion) == true) {
            jugadasPosibles.add(copiaPosicion);
        }
        
        for(int i=0;i<jugadasPosibles.size();i++){
            System.out.println("PUNTO POSIBLE:" + jugadasPosibles.get(i).getX()+ " - "+ jugadasPosibles.get(i).getY());
        }
        
        return jugadasPosibles;
    }
}