package co.edu.uniquindio.icaja.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uniquindio.icaja.exception.constructores.ConstructorEquivocado;
import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalidoException;
import co.edu.uniquindio.icaja.exception.transacciones.SaldoInsuficiente;
import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class Transaccion implements Serializable {
    private String idTransaccion;
    private LocalDateTime fecha;
    private TipoTransaccion tipo;
    private String monto;
    private String motivo;
    private Cuenta[] cuentas;
    private Categoria[] listaCategoria;

    public static final long serialVersionID = 9L;

    /**
     * Crea una transacción de tipo TRANSFERENCIA entre dos cuentas.
     *
     * @param tipo El tipo de transacción, debe ser `TRANSFERENCIA`.
     * @param monto El monto de la transferencia como un `String`, que se convierte a `BigDecimal`.
     * @param motivo El motivo de la transacción.
     * @param cuentaOrigen La cuenta de origen de la transferencia.
     * @param cuentaDestino La cuenta de destino de la transferencia.
     * @param listaCategoria Categorías opcionales asociadas a la transacción.
     *
     * @throws ConstructorEquivocado Si el tipo no es `TRANSFERENCIA`.
     * @throws NumberFormatException Si el monto no tiene el formato adecuado.
     * @throws SaldoInsuficiente Si alguna de las cuentas no tiene saldo suficiente.
     */
    public Transaccion(TipoTransaccion tipo, String monto, String motivo, Cuenta cuentaOrigen, Cuenta cuentaDestino, Categoria... listaCategoria) throws ConstructorEquivocado, MontoInvalidoException, SaldoInsuficiente {
        if (!tipo.equals(TipoTransaccion.TRANSFERENCIA))
            throw new ConstructorEquivocado("Constructor equivocado, este es el contructor para transferencias");

        this.idTransaccion = generarId();
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.monto = monto;
        this.motivo = motivo;
        this.cuentas = new Cuenta[]{cuentaOrigen, cuentaDestino};
        this.listaCategoria = listaCategoria;

        hacerTransferencia();
    }

    /**
     * Crea una transacción de tipo RETIRO o DEPOSITO en una cuenta.
     *
     * @param tipo El tipo de transacción, debe ser `RETIRO` o `DEPOSITO`.
     * @param monto El monto de la transacción como un `String`, que se convierte a `BigDecimal`.
     * @param motivo El motivo de la transacción.
     * @param cuenta La cuenta asociada con la transacción.
     * @param listaCategoria Categorías opcionales asociadas a la transacción.
     *
     * @throws ConstructorEquivocado Si el tipo es `TRANSFERENCIA`.
     * @throws NumberFormatException Si el monto no tiene el formato adecuado.
     * @throws SaldoInsuficiente Si la cuenta no tiene saldo suficiente para el retiro.
     */
    public Transaccion(TipoTransaccion tipo, String monto, String motivo, Cuenta cuenta, Categoria... listaCategoria) throws ConstructorEquivocado, MontoInvalidoException, SaldoInsuficiente {
        if (tipo.equals(TipoTransaccion.TRANSFERENCIA))
            throw new ConstructorEquivocado("Constructor equivocado, este es el constructor para retiros o depositos");

        this.idTransaccion = generarId();
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.monto = monto;
        this.motivo = motivo;
        this.cuentas = new Cuenta[]{cuenta};
        this.listaCategoria = listaCategoria;


        if (tipo.equals(TipoTransaccion.DEPOSITO)) hacerDeposito();
        if (tipo.equals(TipoTransaccion.RETIRO)) hacerRetiro();
    }

    private void hacerTransferencia() throws MontoInvalidoException, SaldoInsuficiente {

        String msj = "No se pudó realizar la transferencia, el monto ingresado es invalido";

        try {
            cuentas[0].modificarSaldo(TipoTransaccion.RETIRO, NumTool.parseToDinero(monto));
        } catch (SaldoInsuficiente e) {
            throw new SaldoInsuficiente("No se pudó realizar la transferencia, no hay saldo suficiente en la cuenta de origen");

        } catch (MontoInvalidoException e) {
            throw new MontoInvalidoException(msj);
        }

        cuentas[1].modificarSaldo(TipoTransaccion.DEPOSITO, NumTool.parseToDinero(monto, msj));
    }

    private void hacerDeposito() throws MontoInvalidoException {
        cuentas[0].modificarSaldo(TipoTransaccion.DEPOSITO, NumTool.parseToDinero(monto,
                "No se puede realizar el deposito, el monto ingresado es invalido"));
    }


    private void hacerRetiro() throws MontoInvalidoException, SaldoInsuficiente {
        cuentas[0].modificarSaldo(TipoTransaccion.RETIRO, NumTool.parseToDinero(monto,
                "No se puede realizar el retiro, el monto ingresado es invalido"));
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }

    public String getListacategoriatoString() {
        StringBuilder categorias = new StringBuilder();
        for (Categoria categoria : listaCategoria) {
            categorias.append(categoria.getNombre()).append(", ");
        }

        return categorias.toString();
    }
}