package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.exception.transacciones.MontoInvalido;
import co.edu.uniquindio.icaja.exception.transacciones.SaldoInsuficiente;
import co.edu.uniquindio.icaja.model.enums.EntidadBancaria;
import co.edu.uniquindio.icaja.model.enums.TipoCuenta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import co.edu.uniquindio.icaja.model.enums.TipoTransaccion;
import co.edu.uniquindio.icaja.utils.tools.NumTool;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Cuenta implements Serializable {
    private String idCuenta;
    private EntidadBancaria entidad;
    private String numeroCuenta;
    private TipoCuenta tipo;
    private BigDecimal saldo = BigDecimal.ZERO;
    private String idpropietario;
    public static final long serialVersionID = 7L;

    public Cuenta(EntidadBancaria entidad, String numeroCuenta, TipoCuenta tipo, String saldo, String propietario) throws MontoInvalido {
        this.idCuenta = generarId();
        this.entidad = entidad;
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldo = NumTool.parseToDinero(saldo, "No se pudo crear la cuenta, el monto ingresado no es valido");
        this.idpropietario = propietario;
    }

    public Cuenta(String saldo) {
        this.saldo = NumTool.parseToDinero(saldo, "No se pudo crear la cuenta, el monto ingresado no es valido");
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }

    public void modificarSaldo(TipoTransaccion tipo, BigDecimal monto) throws SaldoInsuficiente {
        switch (tipo) {
            case DEPOSITO:
                this.saldo = this.saldo.add(monto);
                break;
            case RETIRO:
                if (this.saldo.compareTo(monto) >= 0) {
                    this.saldo = this.saldo.subtract(monto);
                } else {
                    throw new SaldoInsuficiente("No se puede hacer el retiro, saldo insuficiente");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de transacción no soportado: " + tipo);
        }
    }

}