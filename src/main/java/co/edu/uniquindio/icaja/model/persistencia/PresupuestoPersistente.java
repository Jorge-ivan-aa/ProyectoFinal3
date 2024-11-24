package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.Presupuesto;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.almacenamiento.Persistencia;
import co.edu.uniquindio.icaja.utils.tools.NumTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PresupuestoPersistente implements Persistible<Presupuesto> {

    @Override
    public void guardar(List<Presupuesto> presupuestos) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for(Presupuesto presupuesto:presupuestos)
        {
            contenido.append(
                            presupuesto.getNombre()).append("@@")
                    .append(presupuesto.getIdPresupuesto()).append("@@")
                    .append(presupuesto.getMontoAsignado()).append("@@")
                    .append(presupuesto.getMontoGastado()).append("@@");

            contenido.append("<<<@@");
            List.of(presupuesto.getIdCategorias()).forEach(idCategoria -> contenido.append(idCategoria).append("@@"));
            contenido.append("<<<").append("\n");
        }

        Persistencia.guardarArchivo("presupuesto.txt", contenido.toString(), false);
    }

    @Override
    public List<Presupuesto> leer(String ruta) throws IOException {
        ArrayList<Presupuesto> presupuestos = new ArrayList<>();
        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;
        for (String texto : contenido) {
            linea = texto.split("@@");
            Presupuesto presupuesto = new Presupuesto();
            presupuesto.setNombre(linea[0]);
            presupuesto.setIdPresupuesto(linea[1]);
            presupuesto.setMontoAsignado(linea[2]);
            presupuesto.setMontoGastado(linea[3]);
            int idx = 5 + 1;

            presupuesto.setIdCategorias(restaurarLista(linea, idx));
            presupuestos.add(presupuesto);
        }
        return presupuestos;
    }

    private String[] restaurarLista(String[] datosPresupuestos, int indice) {
        ArrayList<String> lista = new ArrayList<>();
        if (indice < datosPresupuestos.length) {
            while (!datosPresupuestos[indice].equals("<<<")) {
                lista.add(datosPresupuestos[indice]);
                indice++;
            }
        }

        return new String[]{String.valueOf(lista)};
    }
}
