package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.enums.TipoCategoria;
import co.edu.uniquindio.icaja.model.services.Persistible;
import co.edu.uniquindio.icaja.utils.respaldo.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaPersistente implements Persistible<Categoria> {
    @Override
    public void guardar(List<Categoria> categorias) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for(Categoria categoria:categorias)
        {
            contenido.append(
                    categoria.getNombre()).append("@@")
                    .append(categoria.getDescripcion()).append("@@")
                    .append(categoria.getTipoCategoria()).append("@@")
                    .append(categoria.getTransacciones()).append("\n");
        }
        Persistencia.guardarArchivo("categoria.txt", contenido.toString(), false);
    }

    @Override
    public List<Categoria> leer(String ruta) throws IOException {
        ArrayList<Categoria> categorias = new ArrayList<>();
        ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
        String[] linea;
        for (String texto : contenido) {
            linea = texto.split("@@");
            Categoria categoria = new Categoria();
            categoria.setNombre(linea[0]);
            categoria.setDescripcion(linea[1]);
            categoria.setTipoCategoria(TipoCategoria.valueOf(linea[2]));
            categoria.setTransacciones(linea[3]);
            categorias.add(categoria);
        }
        return categorias;
    }
}
