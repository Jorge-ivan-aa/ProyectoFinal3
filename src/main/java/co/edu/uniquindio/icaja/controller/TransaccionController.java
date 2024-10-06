package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import co.edu.uniquindio.icaja.model.Usuario;
import co.edu.uniquindio.icaja.model.factories.TransaccionDeposito;
import co.edu.uniquindio.icaja.model.factories.TransaccionRetiro;
import co.edu.uniquindio.icaja.model.factories.TransaccionTransferencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import co.edu.uniquindio.icaja.model.*;

import java.util.ArrayList;
import java.util.Objects;

public class TransaccionController {

    private final ModelFactory factory;

    private final ObservableList<Transaccion> listaTransaccionObservable;

    public TransaccionController() {
        this.factory = ModelFactory.getInstance();
        this.listaTransaccionObservable = FXCollections.observableArrayList();
        this.sincronizarData();
    }

    public ModelFactory getFactory() {
        return factory;
    }

    public ObservableList<Transaccion> getListaTransaccionObservable() {
        return listaTransaccionObservable;
    }
    public void sincronizarData() {
        this.listaTransaccionObservable.addAll(this.factory.getIcaja().getListaTransacciones());
    }

    public Transaccion crearTransaccionDeposito(int id, String fecha, double monto, String tipo, CuentaBancaria cuenta) {

        TransaccionDeposito transaccionDeposito = new TransaccionDeposito(id, fecha, monto, null, cuenta);

        this.factory.getIcaja().getListaTransacciones().add(transaccionDeposito);

        this.sincronizarData();

        return transaccionDeposito;
    }

    public Transaccion crearTransaccionRetiro(int id, String fecha, double monto, String tipo, CuentaBancaria cuenta) {

        TransaccionRetiro transaccionRetiro = new TransaccionRetiro(id, fecha, monto, null, cuenta);

        this.factory.getIcaja().getListaTransacciones().add(transaccionRetiro);

        this.sincronizarData();

        return transaccionRetiro;

    }

    public Transaccion crearTransaccionTransferencia(int id, String fecha, double monto, String tipo, CuentaBancaria cuenta) {

        TransaccionTransferencia transaccionTransferencia = new TransaccionTransferencia(id, fecha, monto, null, cuenta);

        this.factory.getIcaja().getListaTransacciones().add(transaccionTransferencia);

        this.sincronizarData();

        return transaccionTransferencia;
    }

    public Transaccion consultarTransaccion(int id) {
        ArrayList<Transaccion> Transacciones = this.factory.getIcaja().getListaTransacciones();
        for (Transaccion value : Transacciones) {
            if (value.getId() == id) {
                return value;
            }
        }
        return null;
    }

    public String eliminarTransaccion(int id) {

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