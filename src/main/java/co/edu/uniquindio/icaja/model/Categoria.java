package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.model.enums.TipoCategoria;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

public class Categoria implements Serializable {
    private String idCategoria;
    private String nombre;
    private String descripcion;
    private TipoCategoria tipo;
    public static final long serialVersionID = 1L;

    public Categoria(String nombre, String descripcion, TipoCategoria tipo) {
        this.idCategoria = generarId();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }
}