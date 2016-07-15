/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.awt.Point;
import java.util.Random;
import javax.swing.JOptionPane;

public class Tablero implements Runnable {

    private Jugador caballoNegro;
    private Jugador caballoBlanco;
    private int tablero[][]; // 1. cesped 3. flor 4.blanco 5. negro
    private Nodo raiz;
    // variables fijas
    private final int fila = 8;
    private final int columna = 8;
    private final int cesped = 21;  // cantidad de cesped en todo en tablero
    private final int flores = 5;
    private Random aleatorio = new Random();
    private int nivel = 2;
    private int turno = 1; // comienza el blanco (maquina)
    private boolean continua = true;

    private boolean haJugado = false;
    private int jugadaX = -1;
    private int jugadaY = -1;

    public void encontrarPosicionLibre(int cantidadValores, int valor) {
        for (int i = 0; i < cantidadValores; i++) {
            int filaDelElemento;
            int columnaDelElemento;
            do {
                filaDelElemento = (int) (aleatorio.nextDouble() * 7);
                columnaDelElemento = (int) (aleatorio.nextDouble() * 7);
            } while (tablero[filaDelElemento][columnaDelElemento] != 0);
            tablero[filaDelElemento][columnaDelElemento] = valor;

            if (valor == 4) { // Jugadores
                caballoBlanco = new Jugador(new Point(filaDelElemento, columnaDelElemento));
            } else if (valor == 5) {
                caballoNegro = new Jugador(new Point(filaDelElemento, columnaDelElemento));
            }
        }

    }

    private void iniciarTablero() {
        tablero = new int[fila][columna];

        encontrarPosicionLibre(cesped, 1);
        encontrarPosicionLibre(flores, 3);
        encontrarPosicionLibre(1, 4);
        encontrarPosicionLibre(1, 5);

        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("====================");

        caballoNegro.setContrincante(caballoBlanco.getPosicion());
        caballoBlanco.setContrincante(caballoNegro.getPosicion());
        raiz = new Nodo(tablero, turno, caballoNegro, caballoBlanco, nivel, 0);
    }

    public Tablero(int nivel) {
        this.nivel = nivel;
        iniciarTablero();
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void jugadaPorUsuario(int x, int y) {
        if (raiz.getCaballoNegro().obtenerJugadasPosibles().contains(new Point(x, y))) {
            jugadaX = x;
            jugadaY = y;
            haJugado = true;
        } else {
            JOptionPane.showMessageDialog(null, "Jugada no permitida");
        }
// eso?ps toca probarlo

    }

    @Override
    public void run() {

        while (continua) {

            if (turno == 1) {
                //System.out.println("total hijos:" + raiz.getHijos().size());
                System.out.println("Nueva posición blanco prev: " + raiz.getCaballoBlanco().getPosicion().getX() + " - " + raiz.getCaballoBlanco().getPosicion().getY());
                raiz = raiz.minimax();
                System.out.println("Nueva posición blanco post: " + raiz.getCaballoBlanco().getPosicion().getX() + " - " + raiz.getCaballoBlanco().getPosicion().getY());
                System.out.println(raiz);
                turno = 2;
            } else {
                int[][] t = raiz.copiarTablero();
                System.out.println("Nueva posición blanco turno 2 inicio: " + raiz.getCaballoBlanco().getPosicion().getX() + " - " + raiz.getCaballoBlanco().getPosicion().getY());
                for (int i = 0; i < fila; i++) {
                    for (int j = 0; j < columna; j++) {
                        System.out.print(t[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println("====================");
                int flornegro = raiz.getCaballoNegro().getCantidadFlor();
                int cespednegro = raiz.getCaballoNegro().getCantidadCesped();
                System.out.println(flornegro + cespednegro);
                int florblanco = raiz.getCaballoBlanco().getCantidadFlor();
                int cespedblanco = raiz.getCaballoBlanco().getCantidadCesped();
                System.out.println(florblanco + cespedblanco);
                System.out.println("====================");
                while (haJugado == false) {
                    System.out.println("espera");
                }
                System.out.println("ya");
                int x = jugadaX;
                int y = jugadaY;
                haJugado = false; // si entiendes hasta aqui?

                //t[x][y] = 5;
                //Point cn = raiz.getCaballoNegro().getPosicion();
                //t[(int) cn.getX()][(int) cn.getY()] = 0;
                Jugador caballoBlancoNuevo = new Jugador(raiz.getCaballoBlanco().getCantidadCesped(), raiz.getCaballoBlanco().getCantidadFlor(), raiz.getCaballoBlanco().getPosicion());

                Jugador j = new Jugador(raiz.getCaballoNegro().getCantidadCesped(), raiz.getCaballoNegro().getCantidadFlor(), new Point(x, y), caballoBlancoNuevo.getPosicion());
                caballoBlancoNuevo.setContrincante(j.getPosicion());
                raiz = new Nodo(t, 1, j, caballoBlancoNuevo, nivel, 0);
                System.out.println("Nueva posición blanco turno 2 fin: " + raiz.getCaballoBlanco().getPosicion().getX() + " - " + raiz.getCaballoBlanco().getPosicion().getY());
                turno = 1;
            }
            if ((raiz.getCaballoBlanco().getCantidadCesped() + (raiz.getCaballoBlanco().getCantidadFlor() / 3) + raiz.getCaballoNegro().getCantidadCesped() + (raiz.getCaballoNegro().getCantidadFlor() / 3)) == cesped + flores) {
                continua = false;
            }
        }
        int puntosnegro = raiz.getCaballoNegro().getTotalPuntos();
        int puntosblanco = raiz.getCaballoBlanco().getTotalPuntos();
        if (puntosnegro > puntosblanco) {

            JOptionPane.showMessageDialog(null, "El ganador es EL CABALLO NEGRO CON" + puntosnegro + "PUNTOS");
        } else {
            JOptionPane.showMessageDialog(null, "El ganador es EL CABALLO BLANCO CON" + puntosblanco + "PUNTOS");
        }
        //aqui dices qien gano°°

    }
    
    public int puntajeCaballoNegro(){
        return raiz.getCaballoNegro().getTotalPuntos();
    }
    
    public int puntajeCaballoBlanco(){
        return raiz.getCaballoBlanco().getTotalPuntos();
    }
}
