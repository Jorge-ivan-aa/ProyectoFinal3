package co.edu.uniquindio.icaja.exception.almacenamiento;

public class PersistenciaNoCargada extends RuntimeException {
    public PersistenciaNoCargada(String message) {
        super(message);
    }
}
