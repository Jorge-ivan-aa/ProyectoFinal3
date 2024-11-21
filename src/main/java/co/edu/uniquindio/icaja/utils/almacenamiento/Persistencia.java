package co.edu.uniquindio.icaja.utils.almacenamiento;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {

    private static String RUTA_ARCHIVOS = "";

    public static void setRutaArchivos(String rutaArchivos) {
        RUTA_ARCHIVOS = rutaArchivos;
    }

    public Persistencia() {

    }

    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en el archivo
     *
     * @param ruta es la ruta o path donde está ubicado el archivo
     */
    public static void guardarArchivo(String ruta, String contenido, Boolean flagAnexarContenido) throws IOException {

        FileWriter fw = new FileWriter(RUTA_ARCHIVOS + ruta, flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }

    /**
     * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la lista de cadenas.
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String> contenido = new ArrayList<String>();
        FileReader fr = new FileReader(RUTA_ARCHIVOS + ruta);
        BufferedReader bfr = new BufferedReader(fr);
        String linea = "";
        while ((linea = bfr.readLine()) != null) {
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }

}
