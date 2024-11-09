package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.DepositoDto;
import co.edu.uniquindio.icaja.mapping.dto.RetiroDto;
import co.edu.uniquindio.icaja.mapping.dto.TransaccionDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.mapping.mappers.TransaccionMapper;
import co.edu.uniquindio.icaja.mapping.services.ITransaccionDto;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.TransaccionFactory;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;

@Getter
@Setter
public class TransaccionController implements GenericController<ITransaccionDto, Transaccion> {

    private final ModelFactory factory;
    private static TransaccionController instance;
    private final ObservableList<Transaccion> listaTransaccionObservable;
    private TransaccionDto transaccionPendiente;

    private TransaccionController() {
        this.factory = ModelFactory.getInstance();
        this.listaTransaccionObservable = FXCollections.observableArrayList();
        this.sincronizarData();
    }

    public static TransaccionController getInstance() {
        if (instance == null) {
            instance = new TransaccionController(); // Aquí asignamos la instancia.
        }
        return instance;
    }


    public void sincronizarData() {
        this.listaTransaccionObservable.clear();
        this.listaTransaccionObservable.addAll(this.factory.getIcaja().getListaTransacciones());
        registrarLog(1, "Se sincronizaron las transacciones");
    }

    @Override
    public void crear(ITransaccionDto transaccionDto) throws ElementoYaExiste {
        try {
            this.consultar(String.valueOf(transaccionDto.id()));
            registrarLog(2,"No se pudo crear el elemento, "+ transaccionDto.tipo() +" ya existe :(");
            setTransaccionPendiente(null);
            throw new ElementoYaExiste("No se pudo crear el elemento, "+ transaccionDto.tipo() +" ya existe");

        } catch (ElementoNoExiste ignored) {
            Transaccion nuevaTransaccion = TransaccionFactory.crearTransaccion(transaccionDto);
            factory.getIcaja().addTransaccion(nuevaTransaccion);
            setTransaccionPendiente(null);
            sincronizarData();
            registrarLog(1,"Se ha realizado una transaccion exitosamente :)");
        }

    }

    @Override
    public Transaccion consultar(String identificador) throws ElementoNoExiste {
        registrarLog(1,"Se ha consultado una transacción");

        for (Transaccion transaccion : factory.getIcaja().getListaTransacciones()) {
            if (String.valueOf(transaccion.getId()).equals(identificador)) {
                return transaccion;
            }
        }

        throw new ElementoNoExiste("la transacción consultada no existe.");

    }

    @Override
    public void eliminar(String identificador) throws ElementoNoExiste {
        try {
            Transaccion eliminable = this.consultar(identificador); // consultar si existe, de lo contrario propaga una excepcion.
            listaTransaccionObservable.remove(eliminable);
            factory.getIcaja().removeTransaccion(eliminable);
            registrarLog(1,"Se eliminó la transacción :)");
            sincronizarData();

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());
        }

    }

    @Override
    public void actualizar(ITransaccionDto transaccionDto) throws ElementoNoExiste {
        //No se necesita actualizar las transacciones según la logica del negocio.
    }


    @Override
    public void persistir() {

    }
}
