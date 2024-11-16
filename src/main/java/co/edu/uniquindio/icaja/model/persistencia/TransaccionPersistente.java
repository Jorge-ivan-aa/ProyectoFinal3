package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransaccionPersistente implements Persistible<Transaccion> {
    @Override
    public void guardar(List<Transaccion> transacciones) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Transaccion transaccion:transacciones)
        {
            contenido.append(
                    transaccion.getIdTransaccion()).append("@@")
                    .append(transaccion.getFecha().toString()).append("@@")
                    .append(transaccion.getTipo().toString()).append("@@")
                    .append(transaccion.getMonto()).append("@@")
                    .append(transaccion.getMotivo()).append("@@")
                    .append(transaccion.getCuentas()[0].getIdCuenta()).append("@@")
                    .append(transaccion.getCuentas()[1].getIdCuenta()).append("@@")
                    .append(transaccion.getCategoria().getIdCategoria()).append("@@")
                    .append("\n");
        }
        Persistencia.guardarArchivo("transaccion.txt", contenido.toString(), false);
    }

    @Override
    public List<Transaccion> leer(String ruta) throws IOException {
//        ArrayList<Transaccion> transacciones = new ArrayList<>();
//        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
//        String[] linea;
//        for (String texto : contenido) {
//            linea = texto.split("@@");
//            transacciones.add(transaccion);
//        }
        return null;
    }


}
