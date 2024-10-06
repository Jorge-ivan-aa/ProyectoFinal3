package co.edu.uniquindio.icaja.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;
import java.nio.file.Paths;

public class Seguimiento {

    private static final String RUTALOG = "src/main/resources/persistencia/log/logs.txt";

    public static void registrarLog(int nivel, String mensaje) {
        Logger LOGGER = Logger.getLogger(Seguimiento.class.getName());
        FileHandler fileHandler =  null;
        String contexto = obtenerRutaMetodos();
        mensaje = String.format("%s, %s", contexto, mensaje);

        try {
            // Verificar si el archivo de log existe, y crearlo si no
            Path path = Paths.get(RUTALOG);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            fileHandler = new FileHandler(RUTALOG,true);
            fileHandler.setFormatter(new LogFormater());
            LOGGER.addHandler(fileHandler);

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

        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, "Error al registrar el log: " + e.getMessage(), e);

        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileHandler != null) {
                try {
                    fileHandler.close();

                } catch (SecurityException e) {
                    LOGGER.log(Level.SEVERE, "Error al cerrar el FileHandler: " + e.getMessage(), e);

                }
            }
        }
    }

    private static String obtenerRutaMetodos() {
        StringBuilder ruta = new StringBuilder();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[3];
        ruta.append(element.getClassName()).append(".").append(element.getMethodName()).append("()");
        // Recorrer la pila de métodos, omitiendo los primeros elementos de la pila
        return ruta.toString();
    }

}


