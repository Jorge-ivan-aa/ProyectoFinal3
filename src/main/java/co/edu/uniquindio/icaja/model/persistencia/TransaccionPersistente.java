package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//public class TransaccionPersistente implements Persistible<Transaccion> {
//    @Override
//    public void guardar(List<Transaccion> transacciones) throws IOException {
//        StringBuilder contenido = new StringBuilder();
//        for (Transaccion transaccion:transacciones)
//        {
//            contenido.append(
//                    transaccion.getFecha()).append("@@")
//                    .append(transaccion.getMonto()).append("@@")
//                    .append(Arrays.toString(transaccion.getListacategoria())).append("@@")
//                    .append(transaccion.getCuenta()).append("@@")
//                    .append(transaccion.getMotivo()).append("\n");
//        }
//        Persistencia.guardarArchivo("transaccion.txt", contenido.toString(), false);
//    }
//
//    @Override
//    public List<Transaccion> leer(String ruta) throws IOException {
//        ArrayList<Transaccion> transacciones = new ArrayList<>();
//        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
//        String[] linea;
//        for (String texto : contenido) {
//            linea = texto.split("@@");
//            Transaccion transaccion = new Transaccion();
//            transaccion.setFecha(linea[0]);
//            transaccion.setMonto(Double.parseDouble(linea[1]));
//            transaccion.setCuenta(TipoCuenta.valueOf(linea[2]));
//            transaccion.setMotivo(linea[3]);
//            transacciones.add(transaccion);
//        }
//        return transacciones;
//    }
//
//
//}
