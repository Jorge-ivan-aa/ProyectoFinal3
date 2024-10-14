package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.enums.TipoCategoria;
import co.edu.uniquindio.icaja.utils.Seguimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import static co.edu.uniquindio.icaja.utils.Seguimiento.registrarLog;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public class CategoriaController {

    private final ModelFactory factory;
    private final ObservableList<Categoria> listaCategoriasObservable;

    public CategoriaController() {
        this.factory = ModelFactory.getInstance();
        this.listaCategoriasObservable = FXCollections.observableArrayList();
        this.sincronizarData();
    }

    private void sincronizarData() {

        registrarLog(1,"Se sincronizaron las categorias");

        this.listaCategoriasObservable.addAll(this.factory.getIcaja().getListaCategorias());
        Seguimiento.registrarLog(1,"Se sincronizo la base de datos");
    }

    public Categoria crearCategoria(String nombre, String descripcion, TipoCategoria tipoCategoria,ArrayList<Transaccion> transacciones) {

        if (this.consultarCategoria(nombre) != null) {

            registrarLog(1,"La categoria ya existe");

            return null;
        }else{

            registrarLog(1,"Se ha creado una categoria");

            Categoria nuevaCategoria = new Categoria(nombre, descripcion, tipoCategoria, transacciones);
            this.factory.getIcaja().addCategoria(nuevaCategoria);
            this.listaCategoriasObservable.add(nuevaCategoria);
            return nuevaCategoria;
        }

    }

    public Categoria consultarCategoria(String nombre) {

        registrarLog(1,"Se ha consultado una categoria");

        for (Categoria categoria : this.factory.getIcaja().getListaCategorias()) {
            if (Objects.equals(categoria.getNombre(), nombre)) {
                return categoria;
            }
        }
        return null;
    }

    public String eliminarCategoria(String nombre) {


        if (this.consultarCategoria(nombre) == null) {

            registrarLog(1,"La categoria no existe");

            return "La categoria ingresada no existe";
        } else {
            int index = -1;
            ArrayList<Categoria> Categorias = factory.getIcaja().getListaCategorias();
            for (int i = 0; i < Categorias.size(); i++) {
                if (Objects.equals(Categorias.get(i).getNombre(), nombre)) {
                    index = i;
                }
            }
            if (index != -1) {
                registrarLog(1,"Se elimino la categoria");
                this.listaCategoriasObservable.remove(index);
                Categorias.remove(index);
            }
            return "La categoria fue eliminada correctamente";
        }
    }


}
