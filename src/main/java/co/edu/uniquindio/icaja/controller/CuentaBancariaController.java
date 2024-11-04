package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.CuentaBancariaDto;
import co.edu.uniquindio.icaja.mapping.mappers.CuentaBancariaMapper;
import co.edu.uniquindio.icaja.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;

@Getter
public class CuentaBancariaController implements GenericController<CuentaBancariaDto,CuentaBancaria> {

    private final ModelFactory factory;
    private final ObservableList<CuentaBancaria> listaCuentaBancariaObservable;

    public CuentaBancariaController() {
        this.factory = ModelFactory.getInstance();
        this.listaCuentaBancariaObservable = FXCollections.observableArrayList();
        this.sincronizarData();
        this.persistir();
        factory.guardarRespaldo();
        registrarLog(1,"Se sincronizaron las cuentas bancarias.");
    }

    public void sincronizarData() {
        listaCuentaBancariaObservable.clear();
        this.listaCuentaBancariaObservable.addAll(this.factory.getIcaja().getListaCuentaBancarias());
        registrarLog(1,"Se sincronizaron las cuentas bancarias");
    }

    @Override
    public void crear(CuentaBancariaDto cuentaBancariaDto) throws ElementoYaExiste {
        try {
            this.consultar(cuentaBancariaDto.numeroCuenta());
            registrarLog(2,"No se pudo crear el elemento, la cuenta bancaria ya existe :(");
            throw new ElementoYaExiste("No se pudo crear el elemento, la cuenta bancaria ya existe");

        } catch (ElementoNoExiste ignored) {
            CuentaBancaria nuevaCuentaBancaria = CuentaBancariaMapper.toCuentaBancaria(cuentaBancariaDto);
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
        try {
            CuentaBancaria actualizable = this.consultar(cuentaBancariaDto.numeroCuenta());
            actualizable.setEntidad(cuentaBancariaDto.entidad());
            actualizable.setLimite(cuentaBancariaDto.limite());
            actualizable.setSaldo(cuentaBancariaDto.saldo());
            actualizable.setTipoCuenta(cuentaBancariaDto.tipoCuenta());
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
        List<CuentaBancaria> cuentas = new ArrayList<>(factory.getIcaja().getListaCuentaBancarias());
        try {
            factory.getCuentaBancariaPersistente().guardar(cuentas);
        } catch (IOException e) {
            registrarLog(3, "Error, no se pudo guardar la información de las cuentas bancarias: " + e.getMessage());
        }
    }
    
}
