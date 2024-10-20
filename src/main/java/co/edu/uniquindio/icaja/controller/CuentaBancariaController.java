package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.mapping.mappers.CategoriaMapper;
import co.edu.uniquindio.icaja.mapping.mappers.CuentaBancariaMapper;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public class CuentaBancariaController implements GenericController<CuentaBancariaDto,CuentaBancaria> {

    private final ModelFactory factory;
    private final ObservableList<CuentaBancaria> listaCuentaBancariaObservable;

    public CuentaBancariaController() {
        this.factory = ModelFactory.getInstance();
        this.listaCuentaBancariaObservable = FXCollections.observableArrayList();
        this.sincronizarData();
    }

    public void sincronizarData() {

        registrarLog(1,"Se sincronizaron las cuentas bancarias");

        this.listaCuentaBancariaObservable.addAll(this.factory.getIcaja().getListaCuentaBancarias());
        Seguimiento.registrarLog(1,"Se sincronizó la base de datos");
    }

    @Override
    public void crear(CuentaBancariaDto cuentaBancariaDto) throws ElementoYaExiste {
        try {
            this.consultar(cuentaBancariaDto.entidad());
            registrarLog(2,"No se pudo crear el elemento, la cuenta bancaria ya existe :(");
            throw new ElementoYaExiste("No se pudo crear el elemento, la cuenta bancaria ya existe");

        } catch (ElementoNoExiste ignored) {
            CuentaBancaria nuevaCuentaBancaria = CuentaBancariaMapper.cuentaBancariaDtoToCuentaBancaria(cuentaBancariaDto);
            factory.getIcaja().addCuentaBancaria(nuevaCuentaBancaria);
            listaCuentaBancariaObservable.add(nuevaCuentaBancaria);
            registrarLog(1,"Se ha creado una cuenta bancaria exitosamente :)");
        }
    }

    @Override
    public CuentaBancaria consultar(String identificador) throws ElementoNoExiste {

        registrarLog(1,"Se ha consultado una cuenta bancaria");

        for (CuentaBancaria cuentaBancaria : factory.getIcaja().getListaCuentaBancarias()) {
            if (cuentaBancaria.getNumeroCuenta().equals(identificador)) {
                return cuentaBancaria;
            }
        }

        throw new ElementoNoExiste("la cuenta bancaria consultada no existe.");

    }

    @Override
    public void eliminar(String identificador) throws ElementoNoExiste {
        try {
            CuentaBancaria eliminable = this.consultar(identificador); // consultar si existe, de lo contrario propaga una excepcion.
            listaCuentaBancariaObservable.remove(eliminable);
            factory.getIcaja().removeCuentaBancaria(eliminable);
            registrarLog(1,"Se eliminó la cuenta Bancaria");

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());
        }
    }

    @Override
    public void actualizar(CuentaBancariaDto cuentaBancariaDto) throws ElementoNoExiste {
//        try {
//            this.consultar(cuentaBancariaDto.entidad());
//            registrarLog(2,"No se pudo Actualizar el elemento, la cuenta bancaria no existe :(");
//            throw new ElementoYaExiste("No se pudo crear el elemento, la cuenta bancaria ya existe");
//
//        } catch (ElementoNoExiste ignored) {
//            CuentaBancaria nuevaCuentaBancaria = CuentaBancariaMapper.cuentaBancariaDtoToCuentaBancaria(cuentaBancariaDto);
//            factory.getIcaja().addCuentaBancaria(nuevaCuentaBancaria);
//            listaCuentaBancariaObservable.add(nuevaCuentaBancaria);
//            registrarLog(1,"Se ha actualizado una cuenta bancaria exitosamente :)");
//        }
    }



    @Override
    public void persistir() {

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


//    public CuentaBancaria consultarCuentaBancaria(String numeroCuenta) {
//        for (CuentaBancaria cuentaBancaria = this.factory.getIcaja().getListaCuentaBancarias().get(0); cuentaBancaria != null; cuentaBancaria = cuentaBancaria.getSiguiente()) {
//            if (Objects.equals(cuentaBancaria.getNumeroCuenta(), numeroCuenta)) {
//                return cuentaBancaria;
//            }
//        }
//        return null;
//    }
//
//
//    public String eliminarCuentaBancaria(String numeroCuenta) {
//        if (this.consultarCuentaBancaria(numeroCuenta) == null) {
//
//            registrarLog(1,"La cuenta que no existe");
//
//            return "No existe la cuenta bancaria";
//        } else {
//            int index = -1;
//            ArrayList<CuentaBancaria> CuentaBancarias = factory.getIcaja().getListaCuentaBancarias();
//            for (int i = 0; i < CuentaBancarias.size(); i++) {
//                if (Objects.equals(CuentaBancarias.get(i).getNumeroCuenta(), numeroCuenta)) {
//                    index = i;
//                }
//            }
//
//            registrarLog(1,"Se elimino la cuenta");
//
//            if (index != -1) {
//                this.listaCuentaBancariaObservable.remove(index);
//                CuentaBancarias.remove(index);
//            }
//            return "La cuenta fué eliminada correctamente :)";
//        }
//    }

}
