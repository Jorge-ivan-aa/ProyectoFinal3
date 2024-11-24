package co.edu.uniquindio.icaja.utils.almacenamiento;

import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;

import java.util.ResourceBundle;

public enum Config {
    CEDULA_ADMIN("CEDULA_ADMIN"),
    CLAVE_ADMIN("CLAVE_ADMIN"),
    CATEGORIA_ADMIN("CATEGORIA_ADMIN"),
    RUTA_RESPALDO("RUTA_RESPALDO"),
    RUTA_PERSISTENCIA("RUTA_PERSISTENCIA"),
    RUTA_LOG("RUTA_LOG");


    private static final ResourceBundle CONFIG;

    static {
        ResourceBundle tempConfig;
        try {
            tempConfig = ResourceBundle.getBundle("persistencia.config");
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "No se pudo cargar la configuración: " + e.getMessage());
            tempConfig = null;
        }
        CONFIG = tempConfig;
    }

    private final String clave;

    Config(String clave) {
        this.clave = clave;
    }


    /**
     * Obtiene el valor asociado a esta clave en el archivo de configuración.
     *
     * @return El valor de la configuración, o un mensaje de error si no se encuentra.
     */
    public String getValor() {
        if (CONFIG == null) {
            return "Configuración no disponible";
        }
        try {
            return CONFIG.getString(clave);
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Clave no encontrada en configuración: " + clave);
            return "Clave no encontrada: " + clave;
        }
    }
}
