package co.edu.uniquindio.icaja.model.factories;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import lombok.Getter;


@Getter
public class Deposito extends Transaccion {

    public Deposito(String fecha, double monto, Categoria[] listacategoria, CuentaBancaria cuenta, String motivo) {
        super(fecha, monto, listacategoria, cuenta, motivo);
    }

    @Override
    public String factura() {
        return "";
    }

    @Override
    public void realizarMovimiento() {

    }
}