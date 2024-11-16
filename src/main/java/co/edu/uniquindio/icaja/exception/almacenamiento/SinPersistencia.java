package co.edu.uniquindio.icaja.exception.almacenamiento;

public class SinPersistencia extends RuntimeException {
    public SinPersistencia(String message) {
        super(message);
    }
}
