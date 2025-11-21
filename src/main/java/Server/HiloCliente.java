/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Diurno
 */
import modelos.EstadoJuego;
import utils.Mensaje;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloCliente extends Thread {

    private final Socket socket;
    private final int idJugador;
    private final MainServer server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public HiloCliente(Socket socket, int idJugador, MainServer server) {
        this.socket = socket;
        this.idJugador = idJugador;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            server.log("HiloCliente " + idJugador + " iniciado.");

            // Bucle de lectura de acciones del cliente
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof Mensaje) {
                    Mensaje msg = (Mensaje) obj;
                    server.procesarAccion(idJugador, msg.getTipo());
                }
            }
        } catch (Exception e) {
            server.log("Cliente " + idJugador + " desconectado: " + e.getMessage());
            try { socket.close(); } catch (Exception ignore) {}
        }
    }

    public void enviarEstado(EstadoJuego estado) throws Exception {
        out.reset();
        out.writeObject(estado);
        out.flush();
    }



    public int getIdJugador() {
        return idJugador;
    }
    
}
