package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public class CuentaBancariaController {

    private final ModelFactory factory;
    private final ObservableList<CuentaBancaria> listaCuentaBancariaObservable;

    public CuentaBancariaController() {
        this.factory = ModelFactory.getInstance();
        this.listaCuentaBancariaObservable = FXCollections.observableArrayList();
        this.sincronizarData();
    }

    private void sincronizarData() {

        registrarLog(1,"Se sincronizaron las cuentas bancarias");

        this.listaCuentaBancariaObservable.addAll(this.factory.getIcaja().getListaCuentaBancarias());
        Seguimiento.registrarLog(1,"Se sincronizo la base de datos");
    }

//    public CuentaBancaria crearCuentaBancaria(String entidad, String numeroCuenta, TipoCuenta tipoCuenta, double saldo, double limite) {
//
//        if (this.consultarCuentaBancaria(numeroCuenta) != null) {
//
//            registrarLog(1,"La cuenta bancaria ya existe");
//
//            return null;
//        }else{
//
//            registrarLog(1,"Se ha creado una cuenta bancaria");
//
//            CuentaBancaria nuevaCuentaBancaria = new CuentaBancaria(entidad, numeroCuenta, tipoCuenta, saldo, limite);
//            this.factory.getIcaja().addCuentaBancaria(nuevaCuentaBancaria);
//            this.listaCuentaBancariaObservable.add(nuevaCuentaBancaria);
//            return nuevaCuentaBancaria;
//        }
//    }


    public CuentaBancaria consultarCuentaBancaria(String numeroCuenta) {
        for (CuentaBancaria cuentaBancaria = this.factory.getIcaja().getListaCuentaBancarias().get(0); cuentaBancaria != null; cuentaBancaria = cuentaBancaria.getSiguiente()) {
            if (Objects.equals(cuentaBancaria.getNumeroCuenta(), numeroCuenta)) {
                return cuentaBancaria;
            }
        }
        return null;
    }


    public String eliminarCuentaBancaria(String numeroCuenta) {
        if (this.consultarCuentaBancaria(numeroCuenta) == null) {

            registrarLog(1,"El usuario que no existe");

            return "No existe la cuenta bancaria";
        } else {
            int index = -1;
            ArrayList<CuentaBancaria> CuentaBancarias = factory.getIcaja().getListaCuentaBancarias();
            for (int i = 0; i < CuentaBancarias.size(); i++) {
                if (Objects.equals(CuentaBancarias.get(i).getNumeroCuenta(), numeroCuenta)) {
                    index = i;
                }
            }

            registrarLog(1,"Se elimino el usuario");

            if (index != -1) {
                this.listaCuentaBancariaObservable.remove(index);
                CuentaBancarias.remove(index);
            }
            return "El usuario fué eliminado correctamente";
        }
    }

}
