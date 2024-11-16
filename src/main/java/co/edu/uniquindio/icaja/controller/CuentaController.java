package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.crud.AtributoUtilizado;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalido;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.CuentaDto;
import co.edu.uniquindio.icaja.mapping.mappers.CuentaBancariaMapper;
import co.edu.uniquindio.icaja.model.*;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.icaja.controller.enums.TipoConsulta.ID_CUENTA;
import static co.edu.uniquindio.icaja.controller.enums.TipoConsulta.NUMERO_CUENTA;
import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;
import static co.edu.uniquindio.icaja.utils.tools.ListTools.ConsultaAvanzada;

@Getter
public class CuentaController implements GenericController<CuentaDto, Cuenta> {

    private final ModelFactory factory;
    private final ObservableList<Cuenta> listaCuentaObservable;

    public CuentaController() {
        this.factory = ModelFactory.getInstance();
        this.listaCuentaObservable = this.factory.getListaCuentaObservable();
        this.sincronizarData();
    }

    public void sincronizarData() {
        factory.sincronizarData();
        persistir();
    }

    @Override
    public void crear(CuentaDto cuentaDto) throws ElementoYaExiste, AtributoUtilizado, MontoInvalido {
        try {
            // Verificar si la cuenta ya existe usando la consulta por número de cuenta
            consultar(cuentaDto.numeroCuenta(), NUMERO_CUENTA);

            // Si llega aquí, significa que la cuenta ya existe
            registrarLog(2, "No se pudo crear el elemento, la cuenta bancaria ya existe :(");
            throw new ElementoYaExiste("No se pudo crear el elemento, la cuenta bancaria ya existe");

        } catch (ElementoNoExiste e) {
            // Si no se encuentra, creamos la nueva cuenta

            Cuenta nuevaCuenta = CuentaBancariaMapper.toCuentaBancaria(cuentaDto);

            factory.getIcaja().getListaCuentas().add(nuevaCuenta);
            listaCuentaObservable.add(nuevaCuenta);
            sincronizarData();
            registrarLog(1, "Se ha creado una cuenta bancaria exitosamente :)");
        }
    }


    @Override
    public Cuenta consultar(String consulta, TipoConsulta tipoConsulta) throws ElementoNoExiste {
        Seguimiento.registrarLog(1, "Se hace una consulta de cuenta con el id: " + consulta);
        try {
            // Llamada al método de consulta avanzada
            return (Cuenta) ConsultaAvanzada(factory.getIcaja().getListaCuentas(),
                    tipoConsulta.getBuscador(),
                    consulta,
                    0);

        } catch (ElementoNoEncontrado e) {
            // Si no se encuentra el elemento, lanzamos una excepción
            throw new ElementoNoExiste("No se encontró una cuenta con el id: " + consulta);
        }
    }


    @Override
    public void eliminar(String consulta) throws ElementoNoExiste {
        try {
            Cuenta eliminable = this.consultar(consulta, NUMERO_CUENTA); // consultar si existe, de lo contrario propaga una excepcion.
            listaCuentaObservable.remove(eliminable);
            factory.getIcaja().getListaCuentas().remove(eliminable);
            sincronizarData();
            registrarLog(1, "Se eliminó la cuenta Bancaria");

        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(CuentaDto cuentaDto) throws ElementoNoExiste {
        try {
            Cuenta actualizable = this.consultar(cuentaDto.id(), ID_CUENTA);
            actualizable.setEntidad(cuentaDto.entidad());
            actualizable.setSaldo(NumTool.parseToDinero(cuentaDto.saldo()));
            actualizable.setTipo(cuentaDto.tipo());
            actualizable.setPropietario(cuentaDto.propietario());
            sincronizarData();
            registrarLog(1, "Se ha actualizado la cuenta bancaria de numero" + cuentaDto.numeroCuenta() + " exitosamente :)");

        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo actualizar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo actualizar el elemento, " + e.getMessage());
        }
    }


    @Override
    public void persistir() {
        List<Cuenta> cuentas = new ArrayList<>(factory.getIcaja().getListaCuentas());
        try {
            factory.getCuentaPersistente().guardar(cuentas);
        } catch (IOException e) {
            registrarLog(3, "Error, no se pudo guardar la información de las cuentas bancarias: " + e.getMessage());
        }
    }

}
