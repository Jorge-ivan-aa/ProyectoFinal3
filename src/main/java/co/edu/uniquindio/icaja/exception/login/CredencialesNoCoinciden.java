package co.edu.uniquindio.icaja.exception.login;

public class CredencialesNoCoinciden extends RuntimeException {
    public CredencialesNoCoinciden(String message) {
        super(message);
    }
}