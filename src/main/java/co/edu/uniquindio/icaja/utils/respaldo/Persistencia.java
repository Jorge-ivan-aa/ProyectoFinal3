package co.edu.uniquindio.icaja.utils.respaldo;

import co.edu.uniquindio.icaja.model.ICaja;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;

import java.io.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Persistencia {

    private static final String RUTA_ARCHIVOS = "src/main/resources/persistencia/archivos/";
    private static ResourceBundle CONFIG;

    static {
        try {
            CONFIG = ResourceBundle.getBundle("persistencia.config");
        } catch (Exception e) {
            Seguimiento.registrarLog(3, "No se pudó cargar la configuración:" + e.getMessage());
        }
    }


    public Persistencia() {

    }


    /**
    * Este metodo se usa para cargar datos del archivo de propiedades.
    *
    * @param propiedad es la configuracion que se quiere cargar
    */
    public static String cargarConfiguracion(String propiedad) {
        return  CONFIG.getString(propiedad);
    }

    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en el archivo
     *
     * @param ruta es la ruta o path donde esta ubicado el archivo
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
