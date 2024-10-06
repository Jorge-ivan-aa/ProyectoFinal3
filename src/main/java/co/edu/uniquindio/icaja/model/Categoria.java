package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.model.enums.TipoCategoria;

import java.io.Serializable;
import java.util.ArrayList;

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

    public Categoria() {
    }

    public void addTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public void removeTransaccion(Transaccion transaccion) {
        transacciones.remove(transaccion);
    }

    public ArrayList<Transaccion> getTransacciones() {
        return transacciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoCategoria getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(TipoCategoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }
}