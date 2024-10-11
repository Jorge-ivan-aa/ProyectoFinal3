package co.edu.uniquindio.icaja.model;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ICaja implements Serializable {

    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Transaccion> listaTransacciones;
    private ArrayList<Categoria> listaCategorias;
    private ArrayList<CuentaBancaria> listaCuentaBancarias;
    private Sesion sesion;

    public ICaja() {
        this.listaUsuarios = new ArrayList<>();
        this.listaTransacciones = new ArrayList<>();
        this.listaCategorias = new ArrayList<>();
        this.listaCuentaBancarias = new ArrayList<>();
        this.sesion = null;
    }


    // agregar elementos -------------
    public void addUsuario(Usuario usuario) {
        this.listaUsuarios.add(usuario);
    }

    public void addTransaccion(Transaccion transaccion) {
        this.listaTransacciones.add(transaccion);
    }

    public void addCategoria(Categoria categoria) {
        this.listaCategorias.add(categoria);
    }

    public void addCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.listaCuentaBancarias.add(cuentaBancaria);
    }

    // remover elementos -------------
    public void removeUsuario(int index) {
        this.listaUsuarios.remove(index);
    }

    public void removeTransaccion(int index) {
        this.listaTransacciones.remove(index);
    }

    public void removeCategoria(int index) {
        this.listaCategorias.remove(index);
    }

    public void removeCuentaBancaria(int index) {
        this.listaCuentaBancarias.remove(index);
    }

}