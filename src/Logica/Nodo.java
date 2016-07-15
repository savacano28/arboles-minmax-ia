package Logica;

import java.awt.Point;
import java.util.ArrayList;

public class Nodo {

    private Jugador caballoNegro;
    public Jugador caballoBlanco;
    private Nodo padre;
    private ArrayList<Nodo> hijos;
    private int tablero[][];
    private int turno; // 1. Blanco

    private int maximaAltura;
    private int altura;

    private int auristica; // a favor de la maquina, es decir, caballo blanco

    public Nodo(int[][] tablero, int turno, Jugador caballoNegro, Jugador caballoBlanco, int maximaAltura, int altura) {
        this.tablero = tablero;
        this.turno = turno;
        this.hijos = new ArrayList<Nodo>();
        this.caballoNegro = caballoNegro;
        this.caballoBlanco = caballoBlanco;
        this.maximaAltura = maximaAltura;
        this.altura = altura;
        actualizarEstados();
        generarHijos();
    }

    public Nodo(Nodo padre, int[][] tablero, int turno, Jugador caballoNegro, Jugador caballoBlanco, int maximaAltura, int altura) {
        this.padre = padre;
        this.tablero = tablero;
        this.turno = turno;
        this.hijos = new ArrayList<Nodo>();
        this.caballoNegro = caballoNegro;
        this.caballoBlanco = caballoBlanco;

        this.maximaAltura = maximaAltura;
        this.altura = altura;

        actualizarEstados();
        if (maximaAltura > altura) {
            //System.out.println(altura);
            generarHijos();
            calcularAuristica();
        } else {
            calcularAuristica();
        }
    }

    public void calcularAuristica() {
        if (maximaAltura <= altura) { // nodo hoja
            auristica = caballoBlanco.getTotalPuntos() - caballoNegro.getTotalPuntos();
        } else {
            if (turno == 1) { // MAX
                auristica = Integer.MIN_VALUE;
                for (int i = 0; i < hijos.size(); i++) {
                    if (auristica < hijos.get(i).auristica) {
                        auristica = hijos.get(i).auristica;
                    }
                }
            } else { // MIN
                auristica = Integer.MAX_VALUE;
                for (int i = 0; i < hijos.size(); i++) {
                    if (auristica > hijos.get(i).auristica) {
                        auristica = hijos.get(i).auristica;
                    }
                }
            }
        }
    }

    private void actualizarEstados() {

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] == 4 || tablero[i][j] == 5) {
                    tablero[i][j] = 0;
                }
            }
        }

        Point posicionCaballoNegro = caballoNegro.getPosicion();
        int posicionXcaballoNegro = (int) posicionCaballoNegro.getX();
        int posicionYcaballoNegro = (int) posicionCaballoNegro.getY();

        Point posicionCaballoBlanco = caballoBlanco.getPosicion();
        int posicionXcaballoBlanco = (int) posicionCaballoBlanco.getX();
        int posicionYcaballoBlanco = (int) posicionCaballoBlanco.getY();

        if (tablero[posicionXcaballoNegro][posicionYcaballoNegro] == 1) {
            caballoNegro.masCesped();
             
            //tablero[posicionXcaballoNegro][posicionYcaballoNegro] = 5;
        }
        if (tablero[posicionXcaballoNegro][posicionYcaballoNegro] == 3) {
            caballoNegro.masFlor();
            //tablero[posicionXcaballoNegro][posicionYcaballoNegro] = 5;
        }
        tablero[posicionXcaballoNegro][posicionYcaballoNegro] = 5;
        if (tablero[posicionXcaballoBlanco][posicionYcaballoBlanco] == 1) {
            caballoBlanco.masCesped();
            //tablero[posicionXcaballoBlanco][posicionYcaballoBlanco] = 4;
        }
        if (tablero[posicionXcaballoBlanco][posicionYcaballoBlanco] == 3) {
            caballoBlanco.masFlor();
            //tablero[posicionXcaballoBlanco][posicionYcaballoBlanco] = 4;
        }
        tablero[posicionXcaballoBlanco][posicionYcaballoBlanco] = 4;
    }

    public int[][] copiarTablero() {
        int tab[][] = new int[tablero.length][tablero.length];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tab[i][j] = tablero[i][j];
            }
        }
        return tab;
    }

    public void generarHijos() {
        System.out.println("turno" + turno);
        if (turno == 1) {
            ArrayList<Point> posicionesPosiblesDeJuego = caballoBlanco.obtenerJugadasPosibles();
            for (int i = 0; i < posicionesPosiblesDeJuego.size(); i++) {

                int[][] nuevoTablero = copiarTablero();

                //nuevoTablero[(int) posicionesPosiblesDeJuego.get(i).getX()][(int) posicionesPosiblesDeJuego.get(i).getY()] = 4;
                //nuevoTablero[(int) caballoBlanco.getPosicion().getX()][(int) caballoBlanco.getPosicion().getY()] = 0;
                Jugador caballoNegroNuevo = new Jugador(caballoNegro.getCantidadCesped(), caballoNegro.getCantidadFlor(), caballoNegro.getPosicion());

                Jugador nuevoEstado = new Jugador(caballoBlanco.getCantidadCesped(), caballoBlanco.getCantidadFlor(), posicionesPosiblesDeJuego.get(i), caballoNegroNuevo.getPosicion());
                caballoNegroNuevo.setContrincante(nuevoEstado.getPosicion());
                /*
                 for(int x=0;x<8;x++){
                 for(int y=0;y<8;y++){
                 System.out.print(nuevoTablero[x][y]+"="+tablero[x][y]+"  ");
                 }
                 System.out.println();
                 }
                 System.out.println("******************");
                 */

                Nodo nuevoNodo = new Nodo(this, nuevoTablero, 2, caballoNegroNuevo, nuevoEstado, maximaAltura, altura + 1);
                hijos.add(nuevoNodo);
            }
        } else {
            ArrayList<Point> posicionesPosiblesDeJuego = caballoNegro.obtenerJugadasPosibles();
            for (int i = 0; i < posicionesPosiblesDeJuego.size(); i++) {

                int[][] nuevoTablero = copiarTablero();

                //nuevoTablero[(int) posicionesPosiblesDeJuego.get(i).getX()][(int) posicionesPosiblesDeJuego.get(i).getY()] = 5;
                //nuevoTablero[(int) caballoNegro.getPosicion().getX()][(int) caballoNegro.getPosicion().getY()] = 0;
                Jugador caballoBlancoNuevo = new Jugador(caballoBlanco.getCantidadCesped(), caballoBlanco.getCantidadFlor(), caballoBlanco.getPosicion());

                Jugador nuevoEstado = new Jugador(caballoNegro.getCantidadCesped(), caballoNegro.getCantidadFlor(), posicionesPosiblesDeJuego.get(i), caballoBlancoNuevo.getPosicion());
                caballoBlancoNuevo.setContrincante(nuevoEstado.getPosicion());
                /*
                 for(int x=0;x<8;x++){
                 for(int y=0;y<8;y++){
                 System.out.print(nuevoTablero[x][y]+"="+tablero[x][y]+"  ");
                 }
                 System.out.println();
                 }
                 System.out.println("******************");
                 */

                Nodo nuevoNodo = new Nodo(this, nuevoTablero, 1, nuevoEstado, caballoBlancoNuevo, maximaAltura, altura + 1);
                hijos.add(nuevoNodo);
            }

        }

    }

    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    public Nodo minimax() {
        Nodo nodoEleccion = hijos.get(0);
        int max = hijos.get(0).auristica;

        System.out.println("hijos totales minimax " + hijos.size());
        for (int i = 0; i < hijos.size(); i++) {
            System.out.println(hijos.get(i).auristica);
            if (max < hijos.get(i).auristica) {
                max = hijos.get(i).auristica;
                nodoEleccion = hijos.get(i);
                System.out.println("Mejor opcion " + i);
            }
        }
        System.out.println("Altura" + nodoEleccion.altura);
        System.out.println("Nueva posición blanco: " + nodoEleccion.getCaballoBlanco().getPosicion().getX() + " - " + nodoEleccion.getCaballoBlanco().getPosicion().getY());
        //System.out.println("Nueva posición negro minimax: " + nodoEleccion.getCaballoNegro().getPosicion().getX() + " - " + nodoEleccion.getCaballoNegro().getPosicion().getY());
        nodoEleccion.altura = 0;
        return nodoEleccion;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public Jugador getCaballoBlanco() {
        return caballoBlanco;
    }

    public Jugador getCaballoNegro() {
        return caballoNegro;
    }

}
