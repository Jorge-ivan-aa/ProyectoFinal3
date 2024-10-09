package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.factories.Transaccion;
import co.edu.uniquindio.icaja.model.factories.Deposito;
import co.edu.uniquindio.icaja.model.factories.Retiro;
import co.edu.uniquindio.icaja.model.factories.Transferencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import static co.edu.uniquindio.icaja.utils.Seguimiento.registrarLog;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public class TransaccionController {

    private final ModelFactory factory;

    private final ObservableList<Transaccion> listaTransaccionObservable;

    public TransaccionController() {
        this.factory = ModelFactory.getInstance();
        this.listaTransaccionObservable = FXCollections.observableArrayList();
        this.sincronizarData();
    }

    public void sincronizarData() {

        registrarLog(1,"Se sincronizo la base de datos");

        this.listaTransaccionObservable.addAll(this.factory.getIcaja().getListaTransacciones());
    }

    public Transaccion crearTransaccionDeposito(int id, String fecha, double monto, String tipo, CuentaBancaria cuenta) {

        registrarLog(1,"Se ha creado una transacción de deposito");

        Deposito transaccionDeposito = new Deposito(id, fecha, monto, null, cuenta);
        this.factory.getIcaja().getListaTransacciones().add(transaccionDeposito);
        this.sincronizarData();
        return transaccionDeposito;
    }

    public Transaccion crearTransaccionRetiro(int id, String fecha, double monto, String tipo, CuentaBancaria cuenta) {

        registrarLog(1,"Se ha creado una transacción de retiro");

        Retiro retiro = new Retiro(id, fecha, monto, null, cuenta);
        this.factory.getIcaja().getListaTransacciones().add(retiro);
        this.sincronizarData();
        return retiro;

    }

    public Transaccion crearTransaccionTransferencia(int id, String fecha, double monto, String tipo, CuentaBancaria cuenta) {

        registrarLog(1,"Se ha creado una transacción de transferencia");

        Transferencia transferencia = new Transferencia(id, fecha, monto, null, cuenta);
        this.factory.getIcaja().getListaTransacciones().add(transferencia);
        this.sincronizarData();
        return transferencia;
    }

    public Transaccion consultarTransaccion(int id) {

        registrarLog(1,"Se ha consultado una transacción");

        ArrayList<Transaccion> Transacciones = this.factory.getIcaja().getListaTransacciones();
        for (Transaccion value : Transacciones) {
            if (value.getId() == id) {
                return value;
            }
        }
        return null;
    }

    public String eliminarTransaccion(int id) {

        registrarLog(1,"Se ha eliminado una transacción");

        if (this.consultarTransaccion(id) == null) {
            return "La transacción no existe";
        } else {
            int index = -1;
            ArrayList<Transaccion> Transacciones = factory.getIcaja().getListaTransacciones();
            for (int i = 0; i < Transacciones.size(); i++) {
                if (Objects.equals(Transacciones.get(i).getId(), id)) {
                    index = i;
                }
            }
            if (index != -1) {
                this.listaTransaccionObservable.remove(index);
                Transacciones.remove(index);
            }
            return "La transacción fue eliminada correctamente";
        }
    }

}