package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.PresupuestoDto;
import co.edu.uniquindio.icaja.mapping.mappers.PresupuestoMapper;
import co.edu.uniquindio.icaja.model.Presupuesto;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.collections.ObservableList;
import static co.edu.uniquindio.icaja.controller.enums.TipoConsulta.ID_PRESUPUESTO;
import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;
import static co.edu.uniquindio.icaja.utils.tools.ListTools.ConsultaAvanzada;

public class PresupuestoController implements GenericController<PresupuestoDto, Presupuesto> {

    private final ModelFactory factory;
    private final ObservableList<Presupuesto> listaPresupuestoObservable;

    public PresupuestoController() {
        factory = ModelFactory.getInstance();
        this.listaPresupuestoObservable = this.factory.getListaPresupuestoObservable();
        this.sincronizarData();
    }

    @Override
    public void sincronizarData() {
        factory.sincronizarData();
    }

    @Override
    public void crear(PresupuestoDto presupuestoDto) throws ElementoYaExiste {
        try {
            consultar(presupuestoDto.id(), ID_PRESUPUESTO);
            registrarLog(2, "No se puede crear el elemento, el presupuesto ya existe");
            throw new ElementoYaExiste("No se puede crear el elemento, el presupuesto ya existe");

        } catch (ElementoNoExiste ignored) {
            Presupuesto nuevoPresupuesto = PresupuestoMapper.toPresupuesto(presupuestoDto);
            factory.getIcaja().add(nuevoPresupuesto);
            listaPresupuestoObservable.add(nuevoPresupuesto);
            sincronizarData();
            registrarLog(1, "Se ha creado el usuario " + presupuestoDto.nombre());

        }
    }


    @Override
    public Presupuesto consultar(String consulta, TipoConsulta tipoConsulta) throws ElementoNoExiste {
        Seguimiento.registrarLog(1, "Se hace una consulta de presupuesto con el id: " + tipoConsulta);

        try {
            return (Presupuesto) ConsultaAvanzada(factory.getIcaja().getListaPresupuestos(),
                    tipoConsulta.getBuscador(),
                    consulta,
                    0);

        } catch (ElementoNoEncontrado e) {
            Seguimiento.registrarLog(2, "No se encontró un presupuesto con el id: " + consulta);
            throw new ElementoNoExiste("No se encontró un presupuesto con el id: " + consulta);

        }
    }


    @Override
    public void eliminar(String identificador) throws ElementoNoExiste {
        try {
            Presupuesto eliminable = consultar(identificador, ID_PRESUPUESTO);
            factory.getIcaja().remove(eliminable);
            sincronizarData();
            registrarLog(1, "Se eliminó el presupuesto de ID " + identificador + ".");

        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo eliminar el presupuesto, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el presupuesto, " + e.getMessage());
        }

    }

    @Override
    public void actualizar(PresupuestoDto presupuestoDto) throws ElementoNoExiste {
        try {
            Presupuesto actualizable = consultar(presupuestoDto.id(), ID_PRESUPUESTO);
            actualizable.setNombre(presupuestoDto.nombre());
            actualizable.setIdCategorias(presupuestoDto.categorias());
            sincronizarData();
            registrarLog(1, "Se actualizó el Presupuesto de Id " + actualizable.getIdPresupuesto() + " correctamente.");

        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo actualizar el presupuesto, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo actualizar el presupuesto, " + e.getMessage());
        }
    }

}
