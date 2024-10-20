package co.edu.uniquindio.icaja.utils.respaldo;

import co.edu.uniquindio.icaja.model.ICaja;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;

public class ICajaRespaldo {

    private static final String RUTA_RESPALDO_DAT = "src/main/resources/persistencia/respaldo/respaldo.dat";
    private static final String RUTA_RESPALDO_XML = "src/main/resources/persistencia/respaldo/respaldo.xml";


    //------------------------------------SERIALIZACIÓN  y XML


    public static ICaja cargarRecursoICajaBinario() {

        ICaja icaja = null;

        try {
            icaja = (ICaja) Serializado.cargarRecursoSerializado(RUTA_RESPALDO_DAT);
            Seguimiento.registrarLog(1, "Se cargó el recurso de respaldo binario correctamente");

        } catch (Exception e) {
            Seguimiento.registrarLog(3, e.getMessage());
        }
        return icaja;
    }

    public static void guardarRecursoICajaBinario(ICaja icaja) {
        try {
            Serializado.salvarRecursoSerializado(RUTA_RESPALDO_DAT, icaja);
            Seguimiento.registrarLog(1, "Se guardó el recurso de respaldo binario correctamente");

        } catch (Exception e) {
            Seguimiento.registrarLog(3, e.getMessage());
        }
    }


    public static ICaja cargarRecursoICajaXML() {

        ICaja icaja = null;

        try {
            icaja = (ICaja)Serializado.cargarRecursoSerializadoXML(RUTA_RESPALDO_XML);
            Seguimiento.registrarLog(1, "Se cargó el recurso de respaldo xml correctamente");

        } catch (Exception e) {
            Seguimiento.registrarLog(3, e.getMessage());
        }
        return icaja;

    }



    public static void guardarRecursoICajaXML(ICaja icaja) {

        try {
            Serializado.salvarRecursoSerializadoXML(RUTA_RESPALDO_XML, icaja);
            Seguimiento.registrarLog(1, "Se guardó el recurso de respaldo xml correctamente");

        } catch (Exception e) {
            Seguimiento.registrarLog(3, e.getMessage());

        }
    }
}
