package modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EstadoJuego implements Serializable {

    public static class Bala implements Serializable {
        public int x, y;
        public int vx;

        public Bala(int x, int y, int vx) {
            this.x = x;
            this.y = y;
            this.vx = vx;
        }
    }

    // Tamaño lógico del campo
    public static final int WIDTH = 800;
    public static final int HEIGHT = 575;

    // Jugador 1 (izquierda)
    public int x1 = 50;
    public int y1 = HEIGHT / 2;
    public int vida1 = 3;

    // Jugador 2 (derecha)
    public int x2 = WIDTH - 50;
    public int y2 = HEIGHT / 2;
    public int vida2 = 3;

    // Control de disparo (cooldown 500ms)
    private long ultimoDisparoJugador1 = 0;
    private long ultimoDisparoJugador2 = 0;
    private static final long COOLDOWN_DISPARO = 500;

    public List<Bala> balas = new ArrayList<>();

    // Movimiento
    public void moverJugadorArriba(int idJugador) {
        if (idJugador == 1) {
            y1 -= 10;
            if (y1 < 40) y1 = 40;
        } else {
            y2 -= 10;
            if (y2 < 40) y2 = 40;
        }
    }

    public void moverJugadorAbajo(int idJugador) {
        if (idJugador == 1) {
            y1 += 10;
            if (y1 > HEIGHT - 40) y1 = HEIGHT - 40;
        } else {
            y2 += 10;
            if (y2 > HEIGHT - 40) y2 = HEIGHT - 40;
        }
    }

    // Disparo con cooldown
    public void disparar(int idJugador) {
        long ahora = System.currentTimeMillis();

        if (idJugador == 1) {
            if (ahora - ultimoDisparoJugador1 >= COOLDOWN_DISPARO) {
                balas.add(new Bala(x1 + 20, y1, 10));
                ultimoDisparoJugador1 = ahora;
            }
        } else {
            if (ahora - ultimoDisparoJugador2 >= COOLDOWN_DISPARO) {
                balas.add(new Bala(x2 - 20, y2, -10));
                ultimoDisparoJugador2 = ahora;
            }
        }
    }

    // Actualización lógica (colisiones y vida)
    public void actualizar() {
        Iterator<Bala> it = balas.iterator();
        while (it.hasNext()) {
            Bala b = it.next();
            b.x += b.vx;

            // Colisión con jugadores
            if (colisionaConJugador1(b)) {
                vida1 = Math.max(0, vida1 - 1);
                it.remove();
                continue;
            }

            if (colisionaConJugador2(b)) {
                vida2 = Math.max(0, vida2 - 1);
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
        return b.x >= x1 - ancho && b.x <= x1 + ancho &&
               b.y >= y1 - alto && b.y <= y1 + alto;
    }

    private boolean colisionaConJugador2(Bala b) {
        int ancho = 20;
        int alto = 40;
        return b.x >= x2 - ancho && b.x <= x2 + ancho &&
               b.y >= y2 - alto && b.y <= y2 + alto;
    }
}
