package co.edu.uniquindio.icaja.mapping.dto;

import co.edu.uniquindio.icaja.model.factories.Transaccion;
import co.edu.uniquindio.icaja.model.enums.TipoCategoria;

import java.util.ArrayList;

public record CategoriaDto(String nombre,
    String descripcion,
    TipoCategoria tipoCategoria,
    ArrayList<Transaccion> transacciones){
}
