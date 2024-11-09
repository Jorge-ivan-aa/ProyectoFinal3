package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.model.enums.TipoCategoria;



import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

public class Categoria implements Serializable {
    private String nombre;
    private String descripcion;
    private TipoCategoria tipoCategoria;
    private ArrayList<Transaccion> transacciones = new ArrayList<>();
    public static final long serialVersionID = 1L;

    public Categoria(String nombre, String descripcion, TipoCategoria tipoCategoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoCategoria = tipoCategoria;
    }


    public void addTransaccion(Transaccion transaccion) {
        System.out.println("transacciones es: " + transacciones.toString());
        transacciones.add(transaccion);
    }

    public void removeTransaccion(Transaccion transaccion) {
        transacciones.remove(transaccion);
    }


}