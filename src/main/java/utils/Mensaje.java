/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Diurno
 */
import java.io.Serializable;

public class Mensaje implements Serializable {

    public enum Tipo {
        CONECTAR,
        MOVER_ARRIBA,
        MOVER_ABAJO,
        DISPARAR
    }

    private Tipo tipo;
    private int idJugador; // 1 o 2

    public Mensaje(Tipo tipo, int idJugador) {
        this.tipo = tipo;
        this.idJugador = idJugador;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getIdJugador() {
        return idJugador;
    }
}
