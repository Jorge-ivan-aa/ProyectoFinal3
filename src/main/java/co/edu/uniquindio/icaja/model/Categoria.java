package co.edu.uniquindio.icaja.model;


import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

public class Categoria implements Serializable {
    private String idCategoria;
    private String nombre;
    private String descripcion;
    public static final long serialVersionID = 1L;

    public Categoria(String nombre, String descripcion) {
        this.idCategoria = generarId();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }
}