package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.mapping.dto.TransaccionDto;
import co.edu.uniquindio.icaja.mapping.mappers.CuentaBancariaMapper;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;

@Getter
public class CuentaBancariaController implements GenericController<CuentaBancariaDto, Cuenta> {

    private final ModelFactory factory;
    private final ObservableList<Cuenta> listaCuentaObservable;
    private TransaccionDto inicialData;

    public CuentaBancariaController() {
        this.factory = ModelFactory.getInstance();
        this.listaCuentaObservable = FXCollections.observableArrayList();
        this.sincronizarData();
        this.persistir();
        factory.guardarRespaldo();
        registrarLog(1,"Se sincronizaron las cuentas bancarias.");
    }

    public void sincronizarData() {
        listaCuentaObservable.clear();
        this.listaCuentaObservable.addAll(this.factory.getIcaja().getListaCuentas());
        registrarLog(1,"Se sincronizaron las cuentas bancarias");
    }

    @Override
    public void crear(CuentaBancariaDto cuentaBancariaDto) throws ElementoYaExiste {
        try {
            this.consultar(cuentaBancariaDto.numeroCuenta());
            registrarLog(2,"No se pudo crear el elemento, la cuenta bancaria ya existe :(");
            throw new ElementoYaExiste("No se pudo crear el elemento, la cuenta bancaria ya existe");

        } catch (ElementoNoExiste ignored) {
            Cuenta nuevaCuenta = CuentaBancariaMapper.toCuentaBancaria(cuentaBancariaDto);
            factory.getIcaja().addCuentaBancaria(nuevaCuenta);
            listaCuentaObservable.add(nuevaCuenta);
            registrarLog(1,"Se ha creado una cuenta bancaria exitosamente :)");
        }
    }

    @Override
    public Cuenta consultar(String identificador) throws ElementoNoExiste {

        registrarLog(1,"Se ha consultado una cuenta bancaria");

        for (Cuenta cuenta : factory.getIcaja().getListaCuentas()) {
            if (cuenta.getNumeroCuenta().equals(identificador)) {
                return cuenta;
            }
        }

        throw new ElementoNoExiste("la cuenta bancaria consultada no existe.");

    }

    @Override
    public void eliminar(String identificador) throws ElementoNoExiste {
        try {
            Cuenta eliminable = this.consultar(identificador); // consultar si existe, de lo contrario propaga una excepcion.
            listaCuentaObservable.remove(eliminable);
            factory.getIcaja().removeCuentaBancaria(eliminable);
            registrarLog(1,"Se eliminó la cuenta Bancaria");

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());
        }
    }

    @Override
    public void actualizar(CuentaBancariaDto cuentaBancariaDto) throws ElementoNoExiste {
        try {
            Cuenta actualizable = this.consultar(cuentaBancariaDto.numeroCuenta());
            actualizable.setEntidad(cuentaBancariaDto.entidad());
            actualizable.setSaldo(NumTool.parseToDinero(cuentaBancariaDto.saldo()));
            actualizable.setTipo(cuentaBancariaDto.tipo());
            actualizable.setPropietario(cuentaBancariaDto.propietario());
            sincronizarData();
            registrarLog(1,"Se ha actualizado la cuenta bancaria de numero" + cuentaBancariaDto.numeroCuenta() + " exitosamente :)");

        } catch (ElementoNoExiste e) {
            registrarLog(2,"No se pudo actualizar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo actualizar el elemento, " + e.getMessage());
        }
    }



    @Override
    public void persistir() {
        List<Cuenta> cuentas = new ArrayList<>(factory.getIcaja().getListaCuentas());
        try {
            factory.getCuentaBancariaPersistente().guardar(cuentas);
        } catch (IOException e) {
            registrarLog(3, "Error, no se pudo guardar la información de las cuentas bancarias: " + e.getMessage());
        }
    }

    public void setInicialData(TransaccionDto inicialData) {
        this.inicialData = inicialData;
        Seguimiento.registrarLog(1, "Se configuraron los datos iniciales de la cuenta");
    }
}
