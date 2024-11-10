package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.PresupuestoDto;
import co.edu.uniquindio.icaja.mapping.mappers.PresupuestoMapper;
import co.edu.uniquindio.icaja.mapping.mappers.UsuarioMapper;
import co.edu.uniquindio.icaja.model.Presupuesto;
import co.edu.uniquindio.icaja.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;

public class PresupuestoController implements GenericController<PresupuestoDto, Presupuesto> {

    private ModelFactory factory;
    private ObservableList<Presupuesto> listaPresupuestoObservable;

    public presupuestoController(){
        factory= ModelFactory.getInstance();
        this.listaPresupuestoObservable= FXCollections.observableArrayList();
        this.sincronizarData();
    }

    @Override
    public void sincronizarData() {
        listaPresupuestoObservable.clear();
        listaPresupuestoObservable.addAll(factory.getIcaja().getListaPresupuestos());
        persistir();
        factory.guardarRespaldo();
        registrarLog(1,"Se sincronizaron los presupuestos.");
    }

    @Override
    public void crear(PresupuestoDto presupuestoDto) throws ElementoYaExiste {
        try {
            consultar(presupuestoDto.idPresupuesto());
            registrarLog(2,"No se puede crear el elemento, el presupuesto ya existe");
            throw new ElementoYaExiste("No se puede crear el elemento, el presupuesto ya existe");

        } catch (ElementoNoExiste ignored) {
            Presupuesto nuevoPresupuesto = PresupuestoMapper.toPresupuesto(presupuestoDto);
            factory.getIcaja().addPresupuesto(nuevoPresupuesto);
            listaPresupuestoObservable.add(nuevoPresupuesto);
            sincronizarData();
            registrarLog(1,"Se ha creado el usuario " + presupuestoDto.nombre());

        }
    }

    @Override
    public Presupuesto consultar(String identificador) throws ElementoNoExiste {
        registrarLog(1,"Se consultó el presupuesto");

        ArrayList<Presupuesto> Presupuestos = factory.getIcaja().getListaPresupuestos();
        for (Presupuesto presupuesto : Presupuestos) {
            if (presupuesto.getIdPresupuesto().equals(identificador)) {
                return presupuesto;
            }
        }
        throw new ElementoNoExiste("El presupuesto no existe.");
    }

    @Override
    public void eliminar(String identificador) throws ElementoNoExiste {
        try {
            Presupuesto eliminable = consultar(identificador);
            factory.getIcaja().removePresupuesto(eliminable);
            sincronizarData();
            registrarLog(1,"Se eliminó el presupuesto de ID " + identificador + ".");

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo eliminar el presupuesto, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el presupuesto, " + e.getMessage());
        }

    }

    @Override
    public void actualizar(PresupuestoDto presupuestoDto) throws ElementoNoExiste {
        try {
            Presupuesto actualizable = consultar(presupuestoDto.idPresupuesto());
            actualizable.setNombre(presupuestoDto.nombre());
            actualizable.setCategorias(presupuestoDto.categorias());
            sincronizarData();
            registrarLog(1,"Se actualizó el Presupuesto de Id " + actualizable.getIdPresupuesto() + " correctamente.");

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo actualizar el presupuesto, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo actualizar el presupuesto, " + e.getMessage());
        }
    }


    @Override
    public void persistir() {
        List<Presupuesto> presupuestos = new ArrayList<>(factory.getIcaja().getListaPresupuestos());
        try {
            factory.getPresupuestoPersistente().guardar(presupuestos);
        } catch (IOException e) {
            registrarLog(3, "Error, no se pudo guardar la información del presupuesto: " + e.getMessage());
        }

    }
}
