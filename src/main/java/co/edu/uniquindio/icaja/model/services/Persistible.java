package co.edu.uniquindio.icaja.model.services;

import java.io.IOException;
import java.util.List;

public interface Persistible<T> {
    void guardar(List<T> list) throws IOException;
    List<T> leer(String ruta) throws IOException;
}