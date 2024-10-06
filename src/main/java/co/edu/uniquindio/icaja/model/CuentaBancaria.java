package co.edu.uniquindio.icaja.model;

import co.edu.uniquindio.icaja.model.enums.TipoCuenta;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CuentaBancaria implements Serializable {
    private String entidad;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private double saldo;
    private double limite;
    public static final long serialVersionID = 7L;

    public CuentaBancaria(String entidad, String numeroCuenta, TipoCuenta tipoCuenta, double saldo, double limite) {
        this.entidad = entidad;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.limite = this.definirLimite(tipoCuenta, limite);
    }



    public double definirLimite(TipoCuenta tipoCuenta, double limite) {
        if (tipoCuenta == TipoCuenta.CREDITO) {
            return limite;
        }
        return this.saldo;
    }


    public CuentaBancaria get(int i) {

        return null;
    }

    public CuentaBancaria getSiguiente() {

        return null;
    }
}
