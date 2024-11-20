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

import static co.edu.uniquindio.icaja.controller.enums.TipoConsulta.*;
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
    }

    @Override
    public void crear(CuentaDto cuentaDto) throws ElementoYaExiste, AtributoUtilizado, MontoInvalido {
        try {
            consultar(cuentaDto.numeroCuenta(), NUMERO_CUENTA);
            registrarLog(2, "No se pudo crear el elemento, la cuenta bancaria ya existe :(");
            throw new ElementoYaExiste("No se pudo crear el elemento, la cuenta bancaria ya existe");

        } catch (ElementoNoExiste e) {
            Cuenta nuevaCuenta = CuentaBancariaMapper.toCuentaBancaria(cuentaDto);
            factory.getIcaja().add(nuevaCuenta);
            sincronizarData();
            registrarLog(1, "Se ha creado una cuenta bancaria exitosamente :)");
        }
    }


    @Override
    public Cuenta consultar(String consulta, TipoConsulta tipoConsulta) throws ElementoNoExiste {
        try {
            return (Cuenta) ConsultaAvanzada(factory.getIcaja().getListaCuentas(),
                    tipoConsulta.getBuscador(),
                    consulta,
                    0);

        } catch (ElementoNoEncontrado e) {
            throw new ElementoNoExiste("No se encontró una cuenta con el numero de cuenta: " + consulta);
        }
    }


    @Override
    public void eliminar(String consulta) throws ElementoNoExiste {
        try {
            Cuenta eliminable = this.consultar(consulta, NUMERO_CUENTA);
            factory.getIcaja().remove(eliminable);
            sincronizarData();
            registrarLog(1, "Se eliminó la cuenta Bancaria");

        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo eliminar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo eliminar el elemento, " + e.getMessage());

        } catch (Exception e) {
            registrarLog(3, "Ocurrio un error inesperado al intentar eliminar la cuenta" + e.getMessage());
        }
    }

    @Override
    public void actualizar(CuentaDto cuentaDto) throws ElementoNoExiste {
        try {
            Cuenta actualizable = this.consultar(cuentaDto.id(), ID_CUENTA);
            actualizable.setEntidad(cuentaDto.entidad());
            actualizable.setSaldo(NumTool.parseToDinero(cuentaDto.saldo()));
            actualizable.setTipo(cuentaDto.tipo());
            actualizable.setIdpropietario(cuentaDto.propietario());

            sincronizarData();
            registrarLog(1, "Se ha actualizado la cuenta bancaria de numero" + cuentaDto.numeroCuenta() + " exitosamente :)");

        } catch (ElementoNoExiste e) {
            registrarLog(2, "No se pudo actualizar el elemento, " + e.getMessage());
            throw new ElementoNoExiste("No se pudo actualizar el elemento, " + e.getMessage());
        }
    }


}
