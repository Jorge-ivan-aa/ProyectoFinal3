package co.edu.uniquindio.icaja.model.enums;

import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import lombok.Getter;

import java.util.*;
import java.util.logging.Level;




@Getter
public enum CategoriasComunes {
    ALIMENTACION("alimentación"),
    ENTRETENIMIENTO("entretenimiento"),
    SALUD("salud"),
    TRANSPORTE("transporte"),
    EDUCACION("educación"),
    HOGAR("hogar"),
    ROPA("ropa"),
    VIAJES("viajes"),
    PAGOS("pagos"),
    OTROS("otros");

    private final String valor;
    private int contador;

    CategoriasComunes(String valor) {
        this.valor = valor;
        this.contador = 0;
    }

    public void incrementarContador() {
        this.contador++;
    }

    /**
     * Calcula la distancia de Levenshtein entre dos cadenas.
     */
    private static int calcularDistanciaLevenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int costo = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + costo);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    /**
     * Encuentra la categoría común más similar a una categoría de usuario.
     */
    public static CategoriasComunes encontrarCategoriaComun(String categoriaUsuario) {
        int distanciaMinima = Integer.MAX_VALUE;
        CategoriasComunes categoriaComunMasCercana = null;

        for (CategoriasComunes categoria : CategoriasComunes.values()) {
            int distancia = calcularDistanciaLevenshtein(categoriaUsuario.toLowerCase(), categoria.getValor().toLowerCase());
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                categoriaComunMasCercana = categoria;
            }
        }

        // Si ninguna distancia es razonable (por ejemplo, mayor a cierto umbral), se categoriza como OTROS
        if (distanciaMinima > 4) { // Puedes ajustar este umbral según tu necesidad
            categoriaComunMasCercana = OTROS;
        }

        return categoriaComunMasCercana;
    }

    /**
     * Procesa una lista de categorías de usuario y actualiza los contadores de las categorías comunes.
     */
    public static void procesarCategoriasDeUsuario(List<String> categoriasUsuario) {
        for (String categoriaUsuario : categoriasUsuario) {
            CategoriasComunes categoriaComun = encontrarCategoriaComun(categoriaUsuario);
            if (categoriaComun != null) {
                categoriaComun.incrementarContador();
            }
        }
    }

    /**
     * Muestra las estadísticas de las categorías comunes como una lista de cadenas.
     * Cada cadena tiene el formato "Categoria@@PorcentajeDeUso".
     */
    public static List<String> mostrarEstadisticas(int totalTransacciones) {
        List<String> estadisticas = new ArrayList<>();

        // Calcular el porcentaje para cada categoría
        for (CategoriasComunes categoria : CategoriasComunes.values()) {
            double porcentaje = totalTransacciones > 0
                    ? (categoria.getContador() * 100.0) / totalTransacciones
                    : 0.0;
            estadisticas.add(categoria.getValor() + "@@" + String.format("%.2f", porcentaje));
        }

        // Ordenar de mayor a menor porcentaje
        try {
            estadisticas.sort((a, b) -> {
                double porcentajeA = Double.parseDouble(a.split("@@")[1]);
                double porcentajeB = Double.parseDouble(b.split("@@")[1]);
                return Double.compare(porcentajeB, porcentajeA);
            });
        } catch (Exception e) {
             Seguimiento.registrarLog(3, "Error al ordenar de mayor a menor las estadisticas: " + e.getMessage());
        }

        return estadisticas;
    }


    public static List<String> calcularEstadisticas(List<String> categoriasUsuario) {
        // Procesar las categorías
        CategoriasComunes.procesarCategoriasDeUsuario(categoriasUsuario);

        return (CategoriasComunes.mostrarEstadisticas(categoriasUsuario.size()));
    }
}
