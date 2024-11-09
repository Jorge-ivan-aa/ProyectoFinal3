package co.edu.uniquindio.icaja.model.factories;

import co.edu.uniquindio.icaja.model.Categoria;
import co.edu.uniquindio.icaja.model.CuentaBancaria;
import co.edu.uniquindio.icaja.model.Transaccion;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Transferencia extends Transaccion implements Serializable {
    private boolean esInterna;
    private CuentaBancaria cuentaDestino;

    public Transferencia(double monto, Categoria[] listacategoria, CuentaBancaria cuenta, String motivo, boolean esInterna, CuentaBancaria cuentaDestino) {
        super(monto, listacategoria, cuenta, motivo);
        this.esInterna = esInterna;
        this.cuentaDestino = cuentaDestino;
    }

    @Override
    public String factura() {
        return "";
    }

    @Override
    public void realizarMovimiento() {

    }
}