package co.edu.uniquindio.icaja.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

public class Persistencia {

    private static final String RUTA = "src/main/resources/persistencia/archivos/";
    public Persistencia() {

    }

    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en el archivo
     * @param ruta es la ruta o path donde esta ubicado el archivo
     */
    public static void guardarArchivo(String ruta, String contenido, Boolean flagAnexarContenido) throws IOException {

        FileWriter fw = new FileWriter(RUTA+ruta,flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }

    /**
     * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la lista de cadenas.
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String>  contenido = new ArrayList<String>();
        FileReader fr=new FileReader(RUTA+ruta);
        BufferedReader bfr=new BufferedReader(fr);
        String linea="";
        while((linea = bfr.readLine())!=null)
        {
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }


    //------------------------------------SERIALIZACIÓN  y XML
    /**
     * Escribe en el fichero que se le pasa el objeto que se le envia
     *
     * @param rutaArchivo
     *            path del fichero que se quiere escribir
     */

    public static Object cargarRecursoSerializado(String rutaArchivo)throws Exception
    {
        Object aux = null;
//		Empresa empresa = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            // Se crea un ObjectInputStream

            aux = ois.readObject();

        } catch (Exception e2) {
            throw e2;
        }
        return aux;
    }


    public static void salvarRecursoSerializado(String rutaArchivo, Object object)	throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(object);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;

    }

    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {

        XMLEncoder codificadorXML;

        codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
        codificadorXML.writeObject(objeto);
        codificadorXML.close();

    }
}
