package co.edu.uniquindio.icaja.utils.tools;

import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalido;

import java.math.BigDecimal;

public class NumTool {

    /**
     * Convierte una cadena de texto a un objeto `BigDecimal` representando dinero.
     *
     * @param monto El valor a convertir, representado como un `String`.
     * @param msjException El mensaje de error que se usará en la excepción si la conversión falla.
     *
     * @return Un objeto `BigDecimal` con el valor del monto.
     *
     * @throws MontoInvalido Si el `String` no puede convertirse a `BigDecimal`, se lanza
     *                                una excepción con el mensaje proporcionado.
     */
    public static BigDecimal parseToDinero(String monto, String msjException) throws MontoInvalido {
        try {
            return new BigDecimal(monto);
        } catch (NumberFormatException e) {
            throw new MontoInvalido(msjException);
        }
    }

    /**
     * Convierte una cadena de texto a un objeto `BigDecimal` representando dinero.
     *
     * @param monto El valor a convertir, representado como un `String`.
     * @return Un objeto `BigDecimal` con el valor del monto.
     * @throws MontoInvalido Si el `String` no puede convertirse a `BigDecimal`, se lanza
     *                                una excepción con un mensaje de error por defecto.
     */
    public static BigDecimal parseToDinero(String monto) throws MontoInvalido {
        try {
            return new BigDecimal(monto);
        } catch (NumberFormatException e) {
            throw new MontoInvalido("El monto ingresado es invalido: " + e.getMessage());
        }
    }


    /**
     * Formatea un valor numérico representado como cadena de texto, añadiendo separadores
     * de miles y limitando la entrada a un máximo de 20 dígitos.
     *
     * @param input La cadena de texto que representa el valor numérico a formatear.
     *              Puede contener caracteres no numéricos, que serán eliminados.
     * @return Una cadena formateada con separadores de miles. Si la entrada está vacía
     *         o no contiene dígitos, retorna una cadena vacía.
     */
    public static String formatearMonto(String input) {

        String cleanedValue = input.replaceAll("[^0-9]", "");
        if (cleanedValue.length() > 20) {
            cleanedValue = cleanedValue.substring(0, 21); // Limitar a 20 dígitos
        }

        if (cleanedValue.isEmpty()) {
            return ""; // Si no hay nada, retornar vacío
        }

        // Invertir el número para hacer más fácil el formateo
        StringBuilder reversed = new StringBuilder(cleanedValue).reverse();

        // Insertar comas cada 3 dígitos (en la versión invertida)
        StringBuilder formatted = getFormatted(reversed, cleanedValue);

        // Invertir de nuevo para obtener el número en su formato original
        return formatted.reverse().toString();
    }


    /**
     * Aplica el formato a un número invertido añadiendo separadores cada 3 dígitos.
     * Si el número tiene más de 6 dígitos, reemplaza la última coma por una comilla
     * para distinguir la parte de los miles y millones.
     *
     * @param reversed Un {@code StringBuilder} con la representación invertida del número.
     * @param cleanedValue La cadena original sin caracteres no numéricos.
     * @return Un {@code StringBuilder} con el número formateado.
     */
    private static StringBuilder getFormatted(StringBuilder reversed, String cleanedValue) {
        StringBuilder formatted = new StringBuilder(reversed.toString());
        for (int i = 3; i < formatted.length(); i += 4) {
            formatted.insert(i, ',');
        }

        // Sí hay más de 6 dígitos, reemplazar la última coma por una comilla
        int commaIndex = formatted.lastIndexOf(",");
        if (cleanedValue.length() > 6 && commaIndex != -1) {
            // Aseguramos que el índice de la coma es válido antes de reemplazar
            formatted.replace(commaIndex, commaIndex + 1, "'");
        }
        return formatted;
    }


    /**
     * Elimina todos los caracteres no numéricos de una cadena de texto.
     *
     * @param monto La cadena de texto que representa un número, posiblemente
     *              conteniendo caracteres no numéricos.
     * @return Una cadena que contiene solo los dígitos del texto original.
     *         Si la entrada está vacía o no contiene dígitos, retorna una cadena vacía.
     */
    public static String formatearNumero(String monto) {
        return monto.replaceAll("[^0-9]", "");
    }

}
