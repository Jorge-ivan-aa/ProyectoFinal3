package co.edu.uniquindio.icaja.model.factories;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import lombok.Getter;

import java.io.Serializable;


@Getter
public class Deposito extends Transaccion implements Serializable {

    public Deposito(double monto, Categoria[] listacategoria, CuentaBancaria cuenta, String motivo) {
        super(monto, listacategoria, cuenta, motivo);
    }

    @Override
    public String factura() {
        return "";
    }

    @Override
    public void realizarMovimiento() {

    }
}