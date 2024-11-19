package co.edu.uniquindio.icaja.model.persistencia;

import co.edu.uniquindio.icaja.exception.almacenamiento.PersistenciaNoCargada;
import co.edu.uniquindio.icaja.model.Categoria;
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
            contenido.append(categoria.getIdCategoria()).append("@@")
                    .append(categoria.getNombre())
                    .append(categoria.getDescripcion()).append("@@")
                    .append("\n");
        }
        Persistencia.guardarArchivo("categoria.txt", contenido.toString(), false);
    }

    @Override
    public List<Categoria> leer(String ruta) throws IOException {

        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            ArrayList<String> contenido = Persistencia.leerArchivo(ruta);
            String[] linea;
            for (String texto : contenido) {
                linea = texto.split("@@");
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(linea[0]);
                categoria.setNombre(linea[1]);
                categoria.setDescripcion(linea[2]);
                categorias.add(categoria);
            }
        } catch (Exception e) {
            throw new PersistenciaNoCargada("No se pudo cargar la persistencia de categoria, error: " + e.getMessage());
        }
        return categorias;
    }
}
