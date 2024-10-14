package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.CategoriaDto;
import co.edu.uniquindio.icaja.mapping.mappers.CategoriaMapper;
import co.edu.uniquindio.icaja.model.Categoria;
import static co.edu.uniquindio.icaja.utils.Seguimiento.registrarLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;


@Getter
public class CategoriaController implements GenericController<CategoriaDto, Categoria> {
    private final ModelFactory factory;
    private final ObservableList<Categoria> listaCategoriasObservable;

    public CategoriaController() {
        this.factory = ModelFactory.getInstance();
        this.listaCategoriasObservable = FXCollections.observableArrayList();
        this.sincronizarData();
    }

    @Override
    public void sincronizarData() {
        listaCategoriasObservable.addAll(factory.getIcaja().getListaCategorias());
        registrarLog(1,"Se sincronizaron las categorias");
    }

    @Override
    public void crear(CategoriaDto categoriaDto) throws ElementoYaExiste {

        try {
            this.consultar(categoriaDto.nombre());
            registrarLog(2,"No se pudo crear el elemento, la categoria ya existe");
            throw new ElementoYaExiste("No se pudo crear el elemento, la categoria ya existe");

        } catch (ElementoNoExiste ignored) {
            Categoria nuevaCategoria = CategoriaMapper.categoriaDtoToCategoria(categoriaDto);
            factory.getIcaja().addCategoria(nuevaCategoria);
            listaCategoriasObservable.add(nuevaCategoria);
            registrarLog(1,"Se ha creado una categoria");
        }
    }

    @Override
    public Categoria consultar(String nombre) throws ElementoNoExiste {
        registrarLog(1,"Se ha consultado una categoria");

        for (Categoria categoria : factory.getIcaja().getListaCategorias()) {
            if (categoria.getNombre().equals(nombre)) {
                return categoria;
            }
        }

        throw new ElementoNoExiste("la categoria consultada no existe.");

    }

    @Override
    public void eliminar(String nombre) throws ElementoNoExiste {

        try {
            Categoria eliminable = this.consultar(nombre); // consultar si existe, de lo contrario propaga una excepcion.
            listaCategoriasObservable.remove(eliminable);
            factory.getIcaja().removeCategoria(eliminable);
            registrarLog(1,"Se eliminó la categoria");

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());
        }

    }

    @Override
    public void actualizar(CategoriaDto categoriaDto) throws ElementoNoExiste {
        // No se necesita actualizar las categorias según la logica del negocio.
    }

    @Override
    public void persistir() {

    }
}