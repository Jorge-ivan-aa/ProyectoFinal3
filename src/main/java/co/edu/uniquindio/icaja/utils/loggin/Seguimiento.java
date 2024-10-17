package co.edu.uniquindio.icaja.utils.loggin;

import java.io.IOException;
import java.util.logging.*;

public class Seguimiento {

    private static final String RUTALOG = "src/main/resources/persistencia/log/logs.txt";
    private static final Logger LOGGER = Logger.getLogger(Seguimiento.class.getName());

    static {
        // Configurar el logger para que no imprima en la consola por defecto
        LOGGER.setUseParentHandlers(false);

        // Eliminar todos los handlers existentes
        for (Handler handler : LOGGER.getHandlers()) {
            LOGGER.removeHandler(handler);
        }

        // Establecer el nivel del logger
        LOGGER.setLevel(Level.ALL);

        // Configurar el FileHandler
        try {
            FileHandler fileHandler = new FileHandler(RUTALOG, true);
            fileHandler.setFormatter(new LogFormater());
            LOGGER.addHandler(fileHandler);
        } catch (IOException | SecurityException e) {
            LOGGER.log(Level.SEVERE, "Error al configurar el FileHandler: " + e.getMessage(), e);
        }

        // Configurar un ConsoleHandler que use LogFormater
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormater()); // Usar el mismo formateador
        consoleHandler.setLevel(Level.ALL); // Puedes ajustar el nivel si es necesario
        LOGGER.addHandler(consoleHandler);
    }

    public static void registrarLog(int nivel, String mensaje) {
        String contexto = obtenerRutaMetodos();
        mensaje = String.format("%s, %s", contexto, mensaje);

        switch (nivel) {
            case 1:
                LOGGER.log(Level.INFO, mensaje);
                break;

            case 2:
                LOGGER.log(Level.WARNING, mensaje);
                break;

            case 3:
                LOGGER.log(Level.SEVERE, mensaje);
                break;

            default:
                break;
        }
    }

    private static String obtenerRutaMetodos() {
        StringBuilder ruta = new StringBuilder();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[3];
        ruta.append(element.getClassName()).append(".").append(element.getMethodName()).append("()");
        return ruta.toString();
    }
}