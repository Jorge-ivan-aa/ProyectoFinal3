package co.edu.uniquindio.icaja.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uniquindio.icaja.exception.constructores.ConstructorEquivocado;
import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalido;
import co.edu.uniquindio.icaja.exception.transacciones.SaldoInsuficiente;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Transaccion implements Serializable {
    private String idTransaccion;
    private LocalDateTime fecha = LocalDateTime.now();
    private TipoTransaccion tipo;
    private String monto;
    private String motivo;
    private String[] idCuentas;
    private String idCategoria = "";


    public static final long serialVersionID = 9L;

    /**
     * Crea una transacción de tipo TRANSFERENCIA entre dos cuentas.
     *
     * @param tipo El tipo de transacción, debe ser `TRANSFERENCIA`.
     * @param monto El monto de la transferencia como un `String`, que se convierte a `BigDecimal`.
     * @param motivo El motivo de la transacción.
     * @param cuentaOrigen La cuenta de origen de la transferencia.
     * @param cuentaDestino La cuenta de destino de la transferencia.
     * @param idCategoria Categorías opcionales asociadas a la transacción.
     *
     * @throws ConstructorEquivocado Si el tipo no es `TRANSFERENCIA`.
     * @throws NumberFormatException Si el monto no tiene el formato adecuado.
     * @throws SaldoInsuficiente Si alguna de las cuentas no tiene saldo suficiente.
     */
    public Transaccion(TipoTransaccion tipo, String monto, String motivo, String cuentaOrigen, String cuentaDestino, String idCategoria) throws ConstructorEquivocado, MontoInvalido, SaldoInsuficiente {
        if (!tipo.equals(TipoTransaccion.TRANSFERENCIA))
            throw new ConstructorEquivocado("Constructor equivocado, este es el contructor para transferencias");

        this.idTransaccion = generarId();
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.monto = monto;
        this.motivo = motivo;
        this.idCuentas = new String[]{cuentaOrigen, cuentaDestino};
        this.idCategoria = idCategoria;
    }

    /**
     * Crea una transacción de tipo RETIRO o DEPOSITO en una cuenta.
     *
     * @param tipo El tipo de transacción, debe ser `RETIRO` o `DEPOSITO`.
     * @param monto El monto de la transacción como un `String`, que se convierte a `BigDecimal`.
     * @param motivo El motivo de la transacción.
     * @param idCuenta La cuenta asociada con la transacción.
     * @param idCategoria Categorías opcionales asociadas a la transacción.
     *
     * @throws ConstructorEquivocado Si el tipo es `TRANSFERENCIA`.
     * @throws NumberFormatException Si el monto no tiene el formato adecuado.
     * @throws SaldoInsuficiente Si la cuenta no tiene saldo suficiente para el retiro.
     */
    public Transaccion(TipoTransaccion tipo, String monto, String motivo, String idCuenta, String idCategoria) throws ConstructorEquivocado, MontoInvalido, SaldoInsuficiente {
        if (tipo.equals(TipoTransaccion.TRANSFERENCIA))
            throw new ConstructorEquivocado("Constructor equivocado, este es el constructor para retiros o depositos");

        this.idTransaccion = generarId();
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.monto = monto;
        this.motivo = motivo;
        this.idCuentas = new String[]{idCuenta, ""};
        this.idCategoria = idCategoria;

    }

    public void hacerTransferencia(Cuenta cuentaOrigen, Usuario propietarioOrigen, Cuenta cuentaDestino, Usuario propietarioDestino) throws MontoInvalido, SaldoInsuficiente {

        String msj = "No se pudó realizar la transferencia, el monto ingresado es invalido";
        if (!cuentaOrigen.getIdpropietario().equals(cuentaDestino.getIdpropietario())) {
            try {
                cuentaOrigen.modificarSaldo(TipoTransaccion.RETIRO, NumTool.parseToDinero(monto));
                propietarioOrigen.restarSaldoTotal(NumTool.parseToDinero(monto));
                propietarioOrigen.calcularGastos(NumTool.parseToDinero(monto));

            } catch (SaldoInsuficiente e) {
                throw new SaldoInsuficiente("No se pudó realizar la transferencia, no hay saldo suficiente en la cuenta de origen");

            } catch (MontoInvalido e) {
                throw new MontoInvalido(msj);
            }

            cuentaDestino.modificarSaldo(TipoTransaccion.DEPOSITO, NumTool.parseToDinero(monto, msj));
            propietarioDestino.sumarSaldoTotal(NumTool.parseToDinero(monto));
            propietarioDestino.calcularIngresos(NumTool.parseToDinero(monto));
        } else {
            try {
                cuentaOrigen.modificarSaldo(TipoTransaccion.RETIRO, NumTool.parseToDinero(monto));

            } catch (SaldoInsuficiente e) {
                throw new SaldoInsuficiente("No se pudó realizar la transferencia, no hay saldo suficiente en la cuenta de origen");

            } catch (MontoInvalido e) {
                throw new MontoInvalido(msj);
            }

            cuentaDestino.modificarSaldo(TipoTransaccion.DEPOSITO, NumTool.parseToDinero(monto, msj));
        }

    }


    public void hacerDeposito(Cuenta cuenta, Usuario propietario) throws MontoInvalido {
        cuenta.modificarSaldo(TipoTransaccion.DEPOSITO, NumTool.parseToDinero(monto,
                "No se puede realizar el deposito, el monto ingresado es invalido"));

        propietario.sumarSaldoTotal(NumTool.parseToDinero(monto));
        propietario.calcularIngresos(NumTool.parseToDinero(monto));

    }


    public void hacerRetiro(Cuenta cuenta, Usuario propietario) throws MontoInvalido, SaldoInsuficiente {
        cuenta.modificarSaldo(TipoTransaccion.RETIRO, NumTool.parseToDinero(monto,
                "No se puede realizar el retiro, el monto ingresado es invalido"));

        propietario.restarSaldoTotal(NumTool.parseToDinero(monto));
        propietario.calcularGastos(NumTool.parseToDinero(monto));
    }


    private String generarId() {
        return UUID.randomUUID().toString();
    }

}