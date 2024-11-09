package co.edu.uniquindio.icaja.exception.transacciones;

public class SaldoInsuficiente extends RuntimeException {
  public SaldoInsuficiente(String message) {
    super(message);
  }
}
