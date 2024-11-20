package co.edu.uniquindio.icaja.controller;

import co.edu.uniquindio.icaja.controller.enums.TipoConsulta;
import co.edu.uniquindio.icaja.controller.services.GenericController;
import co.edu.uniquindio.icaja.exception.almacenamiento.ElementoNoEncontrado;
import co.edu.uniquindio.icaja.exception.crud.ElementoNoExiste;
import co.edu.uniquindio.icaja.exception.crud.ElementoYaExiste;
import co.edu.uniquindio.icaja.exception.transacciones.CuentaDuplicada;
import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalido;
import co.edu.uniquindio.icaja.exception.transacciones.SaldoInsuficiente;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.mapping.dto.RetiroODepostoDto;
import co.edu.uniquindio.icaja.mapping.dto.TransferenciaDto;
import co.edu.uniquindio.icaja.mapping.mappers.TransaccionMapper;
import co.edu.uniquindio.icaja.mapping.services.ITransaccionDto;
import co.edu.uniquindio.icaja.model.Transaccion;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import static co.edu.uniquindio.icaja.controller.enums.TipoConsulta.ID_TRANSACCION;
import static co.edu.uniquindio.icaja.utils.loggin.Seguimiento.registrarLog;
import static co.edu.uniquindio.icaja.utils.tools.ListTools.ConsultaAvanzada;

@Getter
@Setter
public class TransaccionController implements GenericController<ITransaccionDto, Transaccion> {

    private static final ModelFactory factory = ModelFactory.getInstance();
    private ObservableList<Transaccion> listaTransaccionObservable;
    private ITransaccionDto transaccionPendiente;

    public TransaccionController() {
        this.listaTransaccionObservable = factory.getListaTransaccionObservable();
        this.sincronizarData();
    }

    public void sincronizarData() {
        factory.sincronizarData();
    }

    @Override
    public void crear(ITransaccionDto transaccionDto) throws ElementoYaExiste, MontoInvalido, SaldoInsuficiente, CuentaDuplicada {
        try {
            this.consultar(transaccionDto.id(), ID_TRANSACCION);
            registrarLog(2, "No se pudo crear el elemento, " + transaccionDto.tipo() + " ya existe");
            throw new ElementoYaExiste("No se pudo crear el elemento, " + transaccionDto.tipo() + " ya existe");

        } catch (ElementoNoExiste ignored) {
            Transaccion nuevaTransaccion = getNuevaTransaccion(transaccionDto);
            factory.getIcaja().add(nuevaTransaccion);
            setTransaccionPendiente(null);
            sincronizarData();
            registrarLog(1, "Se ha realizado una transaccion exitosamente :)");
        }

    }

    private static Transaccion getNuevaTransaccion(ITransaccionDto transaccionDto) throws CuentaDuplicada {
        Transaccion nuevaTransaccion = null;
        if (transaccionDto instanceof TransferenciaDto) {
            nuevaTransaccion = TransaccionMapper.toTransaccion((TransferenciaDto) transaccionDto);

            if (nuevaTransaccion.getIdCuentas()[0].equals(nuevaTransaccion.getIdCuentas()[1])) {
                throw new CuentaDuplicada("No se pudó realizar la transferencia, la cuenta de origen es la misma de destino");
            }

        } else if (transaccionDto instanceof RetiroODepostoDto) {
            nuevaTransaccion = TransaccionMapper.toTransaccion((RetiroODepostoDto) transaccionDto);
        }
        return nuevaTransaccion;
    }

    @Override
    public Transaccion consultar(String consulta, TipoConsulta tipoConsulta) throws ElementoNoExiste {

        try {
            return (Transaccion) ConsultaAvanzada(factory.getIcaja().getListaTransacciones(),
                    tipoConsulta.getBuscador(),
                    consulta,
                    0);

        } catch (ElementoNoEncontrado ignore) {
            registrarLog(2, "No se encontró una transacción con el id: " + consulta);
            throw new ElementoNoExiste("No se encontró una transacción con el id: " + consulta);

        }
    }


    @Override
    public void eliminar(String identificador) throws ElementoNoExiste {
        //No se necesita eliminar las transacciones según la logica del negocio.
    }

    @Override
    public void actualizar(ITransaccionDto transaccionDto) throws ElementoNoExiste {
        //No se necesita actualizar las transacciones según la logica del negocio.
    }

}
