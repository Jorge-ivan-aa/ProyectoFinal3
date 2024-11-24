package co.edu.uniquindio.icaja.exception.transacciones;

public class CuentaDuplicada extends RuntimeException {
    public CuentaDuplicada(String message) {
        super(message);
    }
}
