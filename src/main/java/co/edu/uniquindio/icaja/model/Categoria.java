package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.model.enums.TipoCategoria;

import java.io.Serializable;
import java.util.ArrayList;

import co.edu.uniquindio.icaja.model.factories.Transaccion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class Categoria implements Serializable {
    private String nombre;
    private String descripcion;
    private TipoCategoria tipoCategoria;
    private ArrayList<Transaccion> transacciones;
    public static final long serialVersionID = 1L;

    public Categoria(String nombre, String descripcion, TipoCategoria tipoCategoria,ArrayList<Transaccion> transacciones ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoCategoria = tipoCategoria;
        this.transacciones = new ArrayList<>();
    }


    public void addTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public void removeTransaccion(Transaccion transaccion) {
        transacciones.remove(transaccion);
    }

    
}