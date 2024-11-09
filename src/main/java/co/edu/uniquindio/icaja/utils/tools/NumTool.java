package co.edu.uniquindio.icaja.utils.tools;

import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalidoException;

import java.math.BigDecimal;

public class NumTool {

    /**
     * Convierte una cadena de texto a un objeto `BigDecimal` representando dinero.
     * Este método intenta convertir el valor de un `String` a un objeto `BigDecimal`. Si la conversión
     * falla (por ejemplo, si el formato del `String` no es un número válido), lanza una excepción
     * `MontoInvalidoException` con un mensaje personalizado.
     *
     * @param monto El valor a convertir, representado como un `String`.
     * @param msjException El mensaje de error que se usará en la excepción si la conversión falla.
     *
     * @return Un objeto `BigDecimal` con el valor del monto.
     *
     * @throws MontoInvalidoException Si el `String` no puede convertirse a `BigDecimal`, se lanza
     *                                una excepción con el mensaje proporcionado.
     */
    public static BigDecimal parseToDinero(String monto, String msjException) throws MontoInvalidoException {
        try {
            return new BigDecimal(monto);
        } catch (NumberFormatException e) {
            throw new MontoInvalidoException(msjException);
        }
    }

    /**
     * Convierte una cadena de texto a un objeto `BigDecimal` representando dinero.
     *
     * Este método intenta convertir el valor de un `String` a un objeto `BigDecimal`. Si la conversión
     * falla (por ejemplo, si el formato del `String` no es un número válido), lanza una excepción
     * `MontoInvalidoException` con un mensaje de error por defecto que incluye el mensaje de la excepción
     * original.
     *
     * @param monto El valor a convertir, representado como un `String`.
     *
     * @return Un objeto `BigDecimal` con el valor del monto.
     *
     * @throws MontoInvalidoException Si el `String` no puede convertirse a `BigDecimal`, se lanza
     *                                una excepción con un mensaje de error por defecto.
     */
    public static BigDecimal parseToDinero(String monto) throws MontoInvalidoException {
        try {
            return new BigDecimal(monto);
        } catch (NumberFormatException e) {
            throw new MontoInvalidoException("El monto ingresado es invalido: " + e.getMessage());
        }
    }

}
