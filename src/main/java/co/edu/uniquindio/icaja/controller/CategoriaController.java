package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.CategoriaDto;
import co.edu.uniquindio.icaja.mapping.mappers.CategoriaMapper;
import co.edu.uniquindio.icaja.model.Categoria;
import static co.edu.uniquindio.icaja.controller.enums.TipoConsulta.ID_CATEGORIA;
import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;
import static co.edu.uniquindio.icaja.utils.tools.ListTools.ConsultaAvanzada;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.collections.ObservableList;
import lombok.Getter;


@Getter
public class CategoriaController implements GenericController<CategoriaDto, Categoria> {
    private final ModelFactory factory;
    private final ObservableList<Categoria> listaCategoriasObservable;

    public CategoriaController() {
        this.factory = ModelFactory.getInstance();
        this.listaCategoriasObservable = this.factory.getListaCategoriasObservable();
        this.sincronizarData();
    }

    public void sincronizarData() {
        factory.sincronizarData();
    }

    @Override
    public void crear(CategoriaDto categoriaDto) throws ElementoYaExiste {

        try {
            this.consultar(categoriaDto.id(), ID_CATEGORIA);
            registrarLog(2, "No se pudo crear el elemento, la categoria ya existe");
            throw new ElementoYaExiste("No se pudo crear el elemento, la categoria ya existe");

        } catch (ElementoNoExiste ignored) {
            Categoria nuevaCategoria = CategoriaMapper.toCategoria(categoriaDto);
            factory.getIcaja().getListaCategorias().add(nuevaCategoria);
            listaCategoriasObservable.add(nuevaCategoria);
            registrarLog(1, "Se ha creado una categoria");
        }
    }

    @Override
    public Categoria consultar(String consulta, TipoConsulta tipoConsulta) throws ElementoNoExiste {

        try {
            return (Categoria) ConsultaAvanzada(factory.getIcaja().getListaCategorias(),
                    tipoConsulta.getBuscador(),
                    consulta,
                    0);

        } catch (ElementoNoEncontrado ignore) {
            throw new ElementoNoExiste("No se encontró una categoría con el id: " + consulta);
        }
    }


    @Override
    public void eliminar(String id) throws ElementoNoExiste {

        try {
            Categoria eliminable = this.consultar(id, ID_CATEGORIA);
            listaCategoriasObservable.remove(eliminable);
            factory.getIcaja().getListaCategorias().add(eliminable);
            registrarLog(1, "Se eliminó la categoria");

        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());
        }

    }

    @Override
    public void actualizar(CategoriaDto categoriaDto) throws ElementoNoExiste {
        // No se necesita actualizar las categorias según la logica del negocio.
    }

}