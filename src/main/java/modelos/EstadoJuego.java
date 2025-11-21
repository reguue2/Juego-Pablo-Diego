/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Diurno
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EstadoJuego implements Serializable {

    public static class Bala implements Serializable {
        public int x;
        public int y;
        public int vx;

        public Bala(int x, int y, int vx) {
            this.x = x;
            this.y = y;
            this.vx = vx;
        }
    }

    // Tamaño logico del "campo"
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

    // Jugador 1 (izquierda)
    public int x1 = 50;
    public int y1 = HEIGHT / 2;
    public int vida1 = 3;

    // Jugador 2 (derecha)
    public int x2 = WIDTH - 50;
    public int y2 = HEIGHT / 2;
    public int vida2 = 3;

    public List<Bala> balas = new ArrayList<>();

    public void moverJugadorArriba(int idJugador) {
        if (idJugador == 1) {
            y1 -= 10;
            if (y1 < 20) y1 = 20;
        } else {
            y2 -= 10;
            if (y2 < 20) y2 = 20;
        }
    }

    public void moverJugadorAbajo(int idJugador) {
        if (idJugador == 1) {
            y1 += 10;
            if (y1 > HEIGHT - 20) y1 = HEIGHT - 20;
        } else {
            y2 += 10;
            if (y2 > HEIGHT - 20) y2 = HEIGHT - 20;
        }
    }

    public void disparar(int idJugador) {
        if (idJugador == 1) {
            balas.add(new Bala(x1 + 20, y1, 10));
        } else {
            balas.add(new Bala(x2 - 20, y2, -10));
        }
    }

    public void actualizar() {
        Iterator<Bala> it = balas.iterator();
        while (it.hasNext()) {
            Bala b = it.next();
            b.x += b.vx;

            // Colision con jugadores
            if (colisionaConJugador1(b)) {
                vida1--;
                it.remove();
                continue;
            }
            if (colisionaConJugador2(b)) {
                vida2--;
                it.remove();
                continue;
            }

            // Fuera de pantalla
            if (b.x < 0 || b.x > WIDTH) {
                it.remove();
            }
        }
    }

    private boolean colisionaConJugador1(Bala b) {
        int ancho = 20;
        int alto = 40;
        return b.x >= x1 - ancho && b.x <= x1 + ancho
                && b.y >= y1 - alto && b.y <= y1 + alto;
    }

    private boolean colisionaConJugador2(Bala b) {
        int ancho = 20;
        int alto = 40;
        return b.x >= x2 - ancho && b.x <= x2 + ancho
                && b.y >= y2 - alto && b.y <= y2 + alto;
    }
}
