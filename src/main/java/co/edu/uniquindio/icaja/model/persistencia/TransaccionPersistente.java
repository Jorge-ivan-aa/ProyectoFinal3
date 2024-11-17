package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransaccionPersistente implements Persistible<Transaccion> {
    @Override
    public void guardar(List<Transaccion> transacciones) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Transaccion transaccion : transacciones) {
            contenido.append(
                            transaccion.getIdTransaccion()).append("@@")
                    .append(transaccion.getFecha().toString()).append("@@")
                    .append(transaccion.getTipo().toString()).append("@@")
                    .append(transaccion.getMonto()).append("@@")
                    .append(transaccion.getMotivo()).append("@@")
                    .append(transaccion.getIdCuentas()[0]).append("@@")
                    .append(transaccion.getIdCuentas()[1]).append("@@")
                    .append(transaccion.getIdCategoria()).append("@@")
                    .append("\n");
        }
        Persistencia.guardarArchivo("transaccion.txt", contenido.toString(), false);
    }


    @Override
    public List<Transaccion> leer(String ruta) throws IOException {
        ArrayList<Transaccion> transacciones = new ArrayList<>();
        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;

        try {
            for (String texto : contenido) {
                linea = texto.split("@@");
                Transaccion transaccion = new Transaccion();
                transaccion.setIdTransaccion(linea[0]);
                transaccion.setFecha(LocalDateTime.parse(linea[1]));
                transaccion.setTipo(TipoTransaccion.valueOf(linea[2]));
                transaccion.setMonto(linea[3]);
                transaccion.setMotivo(linea[4]);
                transaccion.setIdCuentas(new String[]{linea[5], linea[6]});
                transaccion.setIdCategoria(linea[7]);
                transacciones.add(transaccion);
            }

        } catch (Exception e) {
            Seguimiento.registrarLog(3, "Se generó un error inesperado al intentar cargar los usuarios:" +
                    e.getMessage() +
                    "\n Clase: " + e.getStackTrace()[0].getClassName() +
                    " | Método: " + e.getStackTrace()[0].getMethodName() +
                    " | Archivo: " + e.getStackTrace()[0].getFileName() +
                    " | Línea: " + e.getStackTrace()[0].getLineNumber());
        }

        return transacciones;
    }


}
